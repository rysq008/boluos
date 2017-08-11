package com.delevin.boluolcs.welcome;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import cn.jpush.android.api.JPushInterface;
import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.gestureedit.GestureEditActivity;
import com.delevin.boluolcs.gestureedit.GestureVerifyActivity;
import com.delevin.boluolcs.main.MainActivity;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.StatusBarUtil;
import com.delevin.boluolcs.view.CountDownProgress;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-15 下午5:09:43    类说明 
 */
public class WelcomeActivity extends BaseActivity {
	private SharedPreferences preferences;
	private Boolean isFirstIn;
	Handler handler;
	Runnable runnable;
	private CountDownProgress gProgress;
	private android.content.SharedPreferences.Editor editor;
	private String memberId;
	public static boolean isForeground = false;

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		// String visonCode =
		// BoluoUtils.getShareOneDataGengxin(WelcomeActivity.this, "visonCode");
		Map<String, String> shareData = BoluoUtils.getShareData(this);
		memberId = shareData.get("memberId");
		Myapplication.registrationID = JPushInterface
				.getRegistrationID(getApplicationContext());
		setContentView(R.layout.boluos_activity_welcome);
		StatusBarUtil.setColor(this, Color.TRANSPARENT, 0);

//		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
//			getWindow().setFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		}
		getApp();
		gProgress = (CountDownProgress) findViewById(R.id.countdownProgressView);
		gProgress.start();
		gProgress
				.setProgressListener(new CountDownProgress.OnProgressListener() {
					@Override
					public void onProgress(int progress) {
						if (progress == 0) {
							getgress();
						}
					}
				});
		gProgress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gProgress.stop();
				getgress();
			}
		});
		findViewById(R.id.Welcome_imgView).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						gProgress.stop();
						getgress();
					}
				});
	}
	@Override
	protected void getData() {
		// 获得设备信息 以及设备唯一ID
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		Myapplication.DEVICE_ID = tm.getDeviceId();
		Myapplication.INFORMATION = Build.MODEL;
	}

	/**
	 * 判断APP是否第一次安装
	 * */
	@SuppressLint("CommitPrefEdits")
	private void getApp() {

		preferences = getApplicationContext().getSharedPreferences("isProgramFirstIn", MODE_PRIVATE);

		isFirstIn = preferences.getBoolean("isFirstIn", true); // isFirstIn

		editor = preferences.edit();

	}

	private void getgress() {

		if (isFirstIn) {
			editor = preferences.edit();
			editor.putBoolean("isFirstIn", false); // isFirstIn
			editor.commit();
			startActivity(new Intent(WelcomeActivity.this, GuideActivity.class)); // 第一次在安装运行
			WelcomeActivity.this.finish();
		} else {
			if (memberId != null) {
				SharedPreferences shareDate = getSharedPreferences("is_set_pwd", 0);
				boolean is_pwd = shareDate.getBoolean("is_pwd", false);
				if (is_pwd) {
					startActivity(new Intent(WelcomeActivity.this,GestureVerifyActivity.class));
					WelcomeActivity.this.finish();
				} else {
					startActivity(new Intent(WelcomeActivity.this,GestureEditActivity.class)); // 不是第一次安装运行
					WelcomeActivity.this.finish();
				}
			} else {
				startActivity(new Intent(WelcomeActivity.this,
						MainActivity.class));
				WelcomeActivity.this.finish();
			}
		}
	}
}
