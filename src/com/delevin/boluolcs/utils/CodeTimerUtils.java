package com.delevin.boluolcs.utils;

import com.delevin.boluolcs.activity.OldPhoneCodeActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

/**
 * @author 李红涛E-mail:
 * @version 创建时间：2017-3-8 上午11:48:52 类说明
 */
public class CodeTimerUtils {

	private static Handler getHander(final Button btCode) {
		/**
		 * 倒计时Handler
		 */
		Handler mCodeHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
					btCode.setText(msg.obj.toString());
				} else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
					btCode.setEnabled(true);
					btCode.setText(msg.obj.toString());
				}
			};
		};
		return mCodeHandler;
	}

	public static Intent getIntents(Context context, Button btCode) {
		// 倒计时
		RegisterCodeTimerService.setHandler(getHander(btCode));
		return new Intent(context, RegisterCodeTimerService.class);
	}

	public static void startIntent(Context context, Button btCode, Intent intent) {
		btCode.setEnabled(false);
		context.startService(intent);
	}

	public static void stopIntent(Context context, Intent intent) {
		context.stopService(intent);
	}
}
