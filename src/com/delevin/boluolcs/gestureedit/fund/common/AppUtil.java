/**
 * 
 */
package com.delevin.boluolcs.gestureedit.fund.common;

import android.content.Context;
import android.view.WindowManager;

public class AppUtil {

	/**
	 * 获取屏幕分辨率
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		int width = windowManager.getDefaultDisplay().getWidth() - 80;// 手机屏幕的宽度
		@SuppressWarnings("deprecation")
		int height = windowManager.getDefaultDisplay().getHeight() - 80;// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}

}
