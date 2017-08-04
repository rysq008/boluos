package com.delevin.boluolcs.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * APP工具类
 * 
 * @author 王洋 创建日期 : 2015-11-27上午11:50:08
 * 
 * 
 * 
 *         修改者，修改日期，修改内容。
 */

public class AppUtil {

	/** 获取屏幕分辨率 */
	public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
