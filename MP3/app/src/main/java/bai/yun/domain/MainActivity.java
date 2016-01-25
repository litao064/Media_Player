package bai.yun.domain;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.music.R;

/**
 * @author Yun
 */
public class MainActivity extends Activity {

    private static final String MP3_MUSIC_FILE_NAME = "westlife_you_raise_me_up.mp3";

    private static final String MP3_MUSIC_SDCARD_PATH = "";

    private static final String MP3_MUSIC_RESOURCE_PATH = "/res/raw/";

    private static final String MP3_MUSIC_NETWORK_PATH = "";


    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        initControls();






    }

    private void initControls() {

        Resources res = getResources();

        tabHost = (TabHost) findViewById(R.id.tabhost);

        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec(res.getString(R.string.tab_1))
                .setIndicator(res.getString(R.string.tab_1), res.getDrawable(R.drawable.pic_tab_1))
                .setContent(R.id.tabcontent_1));

        tabHost.addTab(tabHost.newTabSpec(res.getString(R.string.tab_2))
                .setIndicator(res.getString(R.string.tab_2), res.getDrawable(R.drawable.pic_tab_2))
                .setContent(R.id.tabcontent_2));

        tabHost.addTab(tabHost.newTabSpec(res.getString(R.string.tab_3))
                .setIndicator(res.getString(R.string.tab_3), res.getDrawable(R.drawable.pic_tab_3))
                .setContent(R.id.tabcontent_3));

        tabHost.setCurrentTab(0);


    }








}
