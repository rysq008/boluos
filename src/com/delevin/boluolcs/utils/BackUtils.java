package com.delevin.boluolcs.utils;

import android.app.Instrumentation;
import android.view.KeyEvent;
import android.view.View;

public class BackUtils {
	/** 返回上一个状态 */
	public static void backState(View view) {
		new Thread() {
			public void run() {
				try {
					Instrumentation instrumentation = new Instrumentation();
					instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

}
