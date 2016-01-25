package bai.yun.music;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.music.R;

import java.util.Timer;
import java.util.TimerTask;

public class MusicPlay extends Activity implements SeekBar.OnSeekBarChangeListener {


    public static final String CTL_ACTION = "bai.yun.music.CTL_ACTION";
    private static final String UPDATE_ACTION = "bai.yun.music.UPDATE_ACTION";
    private Button buttonPlay;

    private Button buttonPause;

    private Button buttonStop;

    private TextView currentPlayMusicPath;

    private SeekBar audioSeekBar = null;

    private boolean progressFlag = false;


    private Timer mTimer;
    IntentFilter filter;
    private TimerTask mTimerTask;

   // private MediaPlayer mediaPlayer;
    private Uri uri;
    String TAG = "MusicPlay";
    ActivityReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_activity);
     //   mediaPlayer = new MediaPlayer();
        Intent intent = getIntent();
        uri = intent.getData();
        myReceiver=new ActivityReceiver();
         filter = new IntentFilter();
        filter.addAction(MusicPlay.UPDATE_ACTION);
        registerReceiver(myReceiver, filter);
        Intent services = new Intent(this, MusicServices.class);
        startService(services);
        initControl();
    }

    void initControl() {


        buttonPlay = (Button) findViewById(R.id.play_music);

        // buttonPause = (Button) findViewById(R.id.button_pause);

        //  buttonStop = (Button) findViewById(R.id.s);


        audioSeekBar = (SeekBar) findViewById(R.id.audioTrack);
        audioSeekBar.setOnSeekBarChangeListener(this);
//        buttonStop.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                stopMusic();
//
//            }
//        });
//        buttonPause.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                pauseMusic();
//
//            }
//        });
        buttonPlay.setOnClickListener(new View.OnClickListener() {

                                                  @Override
                                                  public void onClick(View v) {
                                                      Log.i(TAG, "onClick: ");
                                                      Intent sendIntent = new Intent();
                                                      sendIntent.setAction(CTL_ACTION);
                                                      sendIntent.putExtra("uri",uri.toString());
                                                     // sendIntent.putExtra("pkg", getPackageName());

                                                    //  sendIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                                                      sendIntent.putExtra("control", 1);
                                                      sendBroadcast(sendIntent);

                                                  }
                                              }

        );
    }


    private void doPlayMusic() {

//        try {
//
//
//            mediaPlayer.setDataSource(this, uri);
////            mediaPlayer.setDataSource(musicPath);
//
//            mediaPlayer.prepare();
//
//
//            audioSeekBar.setMax(mediaPlayer.getDuration());
//
//            mTimer = new Timer();
//
//            mTimerTask = new TimerTask() {
//
//                @Override
//                public void run() {
//
//                    if (progressFlag)
//
//                        return;
//
//                    audioSeekBar.setProgress(mediaPlayer.getCurrentPosition());
//
//                }
//            };
//
//            mTimer.schedule(mTimerTask, 0, 10);
//
//            mediaPlayer.start();
//
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//
//                    Toast.makeText(MusicPlay.this, "", Toast.LENGTH_SHORT).show();
//
//                    mTimer.cancel();
//
//                    mTimerTask.cancel();
//
//                    audioSeekBar.setProgress(0);
//
//                    mediaPlayer.reset();
//
//                }
//            });
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
    }

//    private void pauseMusic() {
//
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//
//            mediaPlayer.pause();
//
//            Toast.makeText(this, "������ͣ", Toast.LENGTH_SHORT).show();
//
//        } else {
//
//            mediaPlayer.start();
//
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }
//
//    private void stopMusic() {
//
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//
//            mediaPlayer.reset();
//
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//
//            mTimer.cancel();
//
//            mTimerTask.cancel();
//
//        }
//    }

    @Override
    public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar arg0) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar arg0) {

//        mediaPlayer.seekTo(arg0.getProgress());

        progressFlag = false;

    }

    public class ActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int update = intent.getIntExtra("update", -1);
            Uri uri = intent.getData();
            if (uri != null) {

            }
            switch (update) {

            }
            Log.i(TAG, "onReceive: " + update);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerReceiver(myReceiver,filter);
    }
}
