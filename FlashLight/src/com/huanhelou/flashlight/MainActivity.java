package com.huanhelou.flashlight;

import java.util.TimerTask;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	private WakeLock wakeLock;
	private CheckBox lightCheckBox;
	private TextView msgTextView;
	private Activity mActivity;
	private int originalScreenBrightness = -1;
	private LinearLayout contentLayout;
	private SoundPool soundPool;
	private int music;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window window = MainActivity.this.getWindow();
		window.setFlags(flag, flag);

		setContentView(R.layout.activity_light_layout);
		mActivity = MainActivity.this;

		init();
		initView();
	}

	private void init() {
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "tag");
		wakeLock.acquire(); // 设置保持唤醒
		// 初始化SoundPool
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		// 加载deep 音频文件
    	music = soundPool.load(getBaseContext(), R.raw.switch_in, 1);
	}

	private void initView() {
		contentLayout = (LinearLayout) findViewById(R.id.light_content_ly);
		lightCheckBox = (CheckBox) findViewById(R.id.light_setting_checkbox);
		msgTextView = (TextView) findViewById(R.id.light_msg_tv);

		lightCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				soundPlay();
				if (arg1) {
					openLight();
					msgTextView.setVisibility(View.GONE);
					msgTextView.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out));
				} else {
					closeLight();
					msgTextView.setVisibility(View.VISIBLE);
					msgTextView.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
				}
			}
		});
	}

	private void openLight() {
		try {
			contentLayout.setBackgroundResource(R.drawable.light_content_open_bg);
			originalScreenBrightness = LightUtils.getScreenBrightness(mActivity);
			LightUtils.setScreenBrightness(mActivity, 255);

			new TimeCount(60 * 1000 * 3, 1000).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void closeLight() {
		try {
			// 如果是倒计时结束 则将选择按钮设置为关闭状态
			if (lightCheckBox.isChecked()) {
				lightCheckBox.setChecked(false);
			}
			contentLayout.setBackgroundResource(R.drawable.light_content_close_bg);
			// 在关系闪灯时恢复屏幕原始亮度
			LightUtils.resetScreenBrightness(mActivity);
			closeLock();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// System.out.println(millisUntilFinished);
		}

		@Override
		public void onFinish() {
			closeLight();
		}
	}
	
	private void soundPlay() {
		// 播放deep
		soundPool.play(music, 1, 1, 0, 0, 1);
	}
	
	private void closeLock() {
		// 解除保持唤醒
		if (null != wakeLock && wakeLock.isHeld()) {
			wakeLock.setReferenceCounted(false);
			wakeLock.release();
			wakeLock = null;
		}
	}
	
	private void close() {
		closeLock();
		if (originalScreenBrightness != -1) {
			closeLight();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		close();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		close();
	}
}