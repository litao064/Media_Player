package bai.yun.domain;

import java.util.Timer;
import java.util.TimerTask;

import com.music.R;

import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MP3���Ž���ʵ��
 * 
 * @author Yun
 *
 */
public class MainActivity extends Activity implements OnSeekBarChangeListener {

	// ��λ���ŵ�MP3�ļ���
	private static final String MP3_MUSIC_FILE_NAME = "you_raise_me_up.mp3";

	// ����SD���д��MP3�ļ���·��
	private static final String MP3_MUSIC_SDCARD_PATH = "";

	// ������Դ��ʽ����MP3��·��
	private static final String MP3_MUSIC_RESOURCE_PATH = "/res/drawable/";

	// ��������MP3��ַ
	private static final String MP3_MUSIC_NETWORK_PATH = "";

	// ��ѡ��
	private RadioGroup radioGroup;

	// ��ʼ��ť
	private Button buttonPlay;

	// ��ͣ��ť
	private Button buttonPause;

	// ������ť
	private Button buttonStop;

	// ��ǰ���ֲ��ŵ�·��
	private TextView currentPlayMusicPath;

	// �������������
	private SeekBar audioSeekBar = null;

	// �������϶���־
	private boolean progressFlag = false;

	// ����MediaPlayer����
	private MediaPlayer mediaPlayer;

	// ��������������
	private Timer mTimer;

	private TimerTask mTimerTask;

	// ����ѡ�
	private TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		// �����ʼ��
		initControls();

		// ��ʼ��MediaPlayer����
		mediaPlayer = new MediaPlayer();

		// ע���¼�����
		registerButtonHanlder();

		// SeekBar���ȸı��¼�
		audioSeekBar.setOnSeekBarChangeListener(this);
	}

	/**
	 * ��ʼ������ؼ�
	 */
	@SuppressWarnings("deprecation")
	private void initControls() {

		// ��ȡ��Դ
		Resources res = getResources();

		tabHost = (TabHost) findViewById(R.id.tabhost);

		// ����ѡ�
		tabHost.setup();

		// ������ǩ��Ȼ�����ã�����/ͼ��/�������
		//��ǩһ
		tabHost.addTab(tabHost.newTabSpec(res.getString(R.string.tab_1))
				.setIndicator(res.getString(R.string.tab_1), res.getDrawable(R.drawable.pic_tab_1))
				.setContent(R.id.tabcontent_1));
		
		//��ǩ��
		tabHost.addTab(tabHost.newTabSpec(res.getString(R.string.tab_2))
				.setIndicator(res.getString(R.string.tab_2), res.getDrawable(R.drawable.pic_tab_2))
				.setContent(R.id.tabcontent_2));
		
		//��ǩ��
		tabHost.addTab(tabHost.newTabSpec(res.getString(R.string.tab_3))
				.setIndicator(res.getString(R.string.tab_3), res.getDrawable(R.drawable.pic_tab_3))
				.setContent(R.id.tabcontent_3));
		
		//���õ�ǰ�ѡ�
		tabHost.setCurrentTab(0);

		radioGroup = (RadioGroup) findViewById(R.id.radio_group);

		buttonPlay = (Button) findViewById(R.id.button_start);

		buttonPause = (Button) findViewById(R.id.button_pause);

		buttonStop = (Button) findViewById(R.id.button_stop);

		currentPlayMusicPath = (TextView) findViewById(R.id.mp3_file_path);

		audioSeekBar = (SeekBar) findViewById(R.id.audio_seekbar);
	}

	/**
	 * ע���¼��ļ�����
	 */
	private void registerButtonHanlder() {

		// ע�ᵥѡ��ť�����¼�
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				// ����MediaPlayer����
				if (mediaPlayer != null) {

					mediaPlayer.reset();

				}
			}

		});

		// ע�Ὺʼ��ť�����¼�
		buttonPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// ��ʼ����
				playMusic();

			}
		});

		// ע����ͣ��ť�����¼�
		buttonPause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// ��ͣ����
				pauseMusic();

			}
		});

		// ע��ֹͣ��ť�����¼�
		buttonStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// ֹͣ����
				stopMusic();

			}
		});
	}

	/**
	 * ��ʼ����
	 */
	private void playMusic() {

		// ����MediaPlayer
		mediaPlayer.reset();

		// ���岥���ļ�·��
		String musicPath = "";

		// �ж��Բ�ͬ�ķ�ʽ����MP3��Դ�ļ�
		switch (radioGroup.getCheckedRadioButtonId()) {

		// ����Դ�ļ�res/drawable����MP3
		case R.id.button_load_from_res:

			// ����MP3�ļ�·��
			musicPath = MP3_MUSIC_RESOURCE_PATH + MP3_MUSIC_FILE_NAME;

			currentPlayMusicPath.setText(musicPath);

			// ͨ������λ��res/drawable�µ�MP3��Դ������MediaPlayer����
			mediaPlayer = MediaPlayer.create(MainActivity.this, R.drawable.you_raise_me_up);

			// ִ�в��Ų���
			doPlayMusic(musicPath, true);

			break;

		// ��SD������MP3
		case R.id.button_load_from_sdcard:

			// ����MP3�ļ�·��
			musicPath = MP3_MUSIC_SDCARD_PATH + MP3_MUSIC_FILE_NAME;

			currentPlayMusicPath.setText(musicPath);

			// ִ�в��Ų���
			doPlayMusic(musicPath, false);

			break;

		case R.id.button_load_from_url:

			// ����MP3�ļ�·��
			musicPath = MP3_MUSIC_NETWORK_PATH + MP3_MUSIC_FILE_NAME;

			currentPlayMusicPath.setText(musicPath);

			// ִ�в��Ų���
			doPlayMusic(musicPath, false);

			break;

		}

		// ��ʾ��ǰ״̬
		Toast.makeText(MainActivity.this, "�������ڲ���", Toast.LENGTH_LONG).show();

	}

	/**
	 * ������ֵĲ��Ų���
	 * 
	 * @param musicPath
	 *            ������MP3���ֵ�·��
	 * @param isResWay
	 *            �Ƿ�Ϊ�ڲ���Դ���ط�ʽ
	 * 
	 */
	private void doPlayMusic(String musicPath, boolean isResWay) {

		try {

			// �жϵ�ǰ�Ƿ����ڲ���Դ��ʽ����MP3�ļ�
			if (!isResWay) {

				// ����Ҫ���ŵ�MP3�ļ�·��
				mediaPlayer.setDataSource(musicPath);

				// ���в���ǰ׼������
				mediaPlayer.prepare();

			}

			// ���ý��������ֵΪMP3����ʱ��
			audioSeekBar.setMax(mediaPlayer.getDuration());

			// �ö�ʱ����¼�����ٶ�
			mTimer = new Timer();

			mTimerTask = new TimerTask() {

				@Override
				public void run() {

					if (progressFlag)

						return;

					// ���ý�����Ϊ��ǰ���Ž���
					audioSeekBar.setProgress(mediaPlayer.getCurrentPosition());

				}
			};

			mTimer.schedule(mTimerTask, 0, 10);

			// ��ʼ����
			mediaPlayer.start();

			// ע�Ქ������¼�������
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {

					// ���������ʱ������ʾ
					Toast.makeText(MainActivity.this, "�������", Toast.LENGTH_SHORT).show();

					// ȡ����ʱ����
					mTimer.cancel();

					mTimerTask.cancel();

					// ���ý�����Ϊ��ʼ״̬
					audioSeekBar.setProgress(0);

					// ����MediaPlayerΪ��ʼ״̬
					mediaPlayer.reset();

				}
			});

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/**
	 * ��ͣ����
	 */
	private void pauseMusic() {

		// �жϵ�ǰ�Ƿ����ڲ�������
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {

			// ��ͣ����
			mediaPlayer.pause();

			// ��ʾ״̬
			Toast.makeText(MainActivity.this, "������ͣ", Toast.LENGTH_SHORT).show();

		} else {

			// ��������
			mediaPlayer.start();

			// ��ʾ״̬
			Toast.makeText(MainActivity.this, "��ʼ����", Toast.LENGTH_SHORT).show();

		}

	}

	/**
	 * ֹͣ����
	 */
	private void stopMusic() {

		// �ж��Ƿ����ڲ�������
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {

			// ����MediaPlayer����ʼ��״̬
			mediaPlayer.reset();

			// ��ʾ״̬
			Toast.makeText(MainActivity.this, "ֹͣ����", Toast.LENGTH_SHORT).show();

			mTimer.cancel();

			mTimerTask.cancel();

		}
	}

	/**
	 * �������ı��¼�
	 */
	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {

	}

	/**
	 * ��ʼ�϶��������¼�
	 */
	@Override
	public void onStartTrackingTouch(SeekBar arg0) {

	}

	/**
	 * �������϶�����¼�
	 */
	@Override
	public void onStopTrackingTouch(SeekBar arg0) {

		// ��ý�岥�������ŵ���������λ��
		mediaPlayer.seekTo(arg0.getProgress());

		progressFlag = false;

	}
}
