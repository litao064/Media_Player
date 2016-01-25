package bai.yun.music;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by litao on 2016/1/21.
 */
public class MusicServices extends Service {
    final static private String TAG="MusicServices";
    MyReceiver myreceiver;
    List<String> uriList;
    MediaPlayer mediaPlayer;
    int status = 0x11;
    int current;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        myreceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicPlay.CTL_ACTION);
        registerReceiver(myreceiver, filter);
        mediaPlayer = new MediaPlayer();
        uriList = getUriList();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //按照播放策略播放下一首；
                playNext();
            }
        });
        super.onCreate();
    }

    private void playNext() {

        current=(current+1)%uriList.size();

        Intent sendintent=new Intent();
        sendintent.putExtra("update",status);
       // sendintent.setData(uriList.get(current));
        sendBroadcast(sendintent);
        prepareAndPlay(uriList.get(current));
    }

    private List<String> getUriList() {
        return new ArrayList<>();
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: ");
            int control = intent.getIntExtra("control", -1);
           String uri = (intent.getStringExtra("uri"));
            switch (control) {
                case 1://播放；
                    if (status == 0x11) {
                        checkOrInsert(uri);
                        prepareAndPlay(uriList.get(current));
                        status = 0x12;
                    } else if (status == 0x12) {
                        mediaPlayer.pause();
                        status = 0x13;
                    } else if (status == 0x13) {
                        mediaPlayer.start();
                        status = 0x12;
                    }
                    break;
                //停止
                case 2:
                    if (status == 0x12 || status == 0x13) {
                        mediaPlayer.stop();
                        status = 0x11;
                    }
            }
            Intent sendintent=new Intent();
            sendintent.putExtra("update",status);
           // sendintent.setData(uriList.get(current));
            sendBroadcast(sendintent);
        }
    }

    private void checkOrInsert(String uri) {
        if (!uriList.contains(uri)){
            uriList.add(0, uri);
            current=0;
        }
    }

    private void prepareAndPlay(String path) {
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}