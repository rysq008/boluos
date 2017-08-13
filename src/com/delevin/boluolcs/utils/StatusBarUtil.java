package com.delevin.boluolcs.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.pusupanshi.boluolicai.R;

public class StatusBarUtil {

	private static final int FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view;

	/**
	 * 设置状态栏颜色
	 * 
	 * @param activity
	 *            需要设置的activity
	 * @param color
	 *            状态栏颜色值
	 * @param statusBarAlpha
	 *            状态栏透明度
	 */
	public static void setColor(Activity activity, int color,
			int statusBarAlpha, boolean needShowStatusBar) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			activity.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////			 ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();// 同下句等效
//			ViewGroup decorView = (ViewGroup) activity
//					.findViewById(android.R.id.content);
//			 View fakeStatusBarView = decorView
//					 .findViewById(FAKE_STATUS_BAR_VIEW_ID);
//			 if (fakeStatusBarView != null) {
//				 if (fakeStatusBarView.getVisibility() == View.GONE) {
//					 fakeStatusBarView.setVisibility(View.VISIBLE);
//				 }
//				 fakeStatusBarView.setBackgroundColor(calculateStatusColor(
//						 color, statusBarAlpha));
//			 } else {
//				 View view = createStatusBarView(activity, color, statusBarAlpha);
//				 decorView.addView(view);
//			 }
//			 setRootView(activity);
		}
	}

	/**
	 * 隐藏伪状态栏 View
	 * 
	 * @param activity
	 *            调用的 Activity
	 */
	public static void hideFakeStatusBarView(Activity activity) {
		ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
		View fakeStatusBarView = decorView
				.findViewById(FAKE_STATUS_BAR_VIEW_ID);
		if (fakeStatusBarView != null) {
			fakeStatusBarView.setVisibility(View.GONE);
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void clearPreviousSetting(Activity activity) {
		ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
		View fakeStatusBarView = decorView
				.findViewById(FAKE_STATUS_BAR_VIEW_ID);
		if (fakeStatusBarView != null) {
			decorView.removeView(fakeStatusBarView);
			ViewGroup rootView = (ViewGroup) ((ViewGroup) activity
					.findViewById(android.R.id.content)).getChildAt(0);
			rootView.setPadding(0, 0, 0, 0);
		}
	}

	/**
	 * 生成一个和状态栏大小相同的彩色矩形条
	 * 
	 * @param activity
	 *            需要设置的 activity
	 * @param color
	 *            状态栏颜色值
	 * @return 状态栏矩形条
	 */
	public static View createStatusBarView(Activity activity, int color) {
		return createStatusBarView(activity, color, 0);
	}

	/**
	 * 生成一个和状态栏大小相同的半透明矩形条
	 * 
	 * @param activity
	 *            需要设置的activity
	 * @param color
	 *            状态栏颜色值
	 * @param alpha
	 *            透明值
	 * @return 状态栏矩形条
	 */
	public static View createStatusBarView(Activity activity, int color,
			int alpha) {
		// 绘制一个和状态栏一样高的矩形
		View statusBarView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				getStatusBarHeight(activity));
		statusBarView.setLayoutParams(params);
		statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
		statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
		return statusBarView;
	}

	/**
	 * 设置根布局参数
	 */
	public static void setRootView(Activity activity) {
		ViewGroup parent = (ViewGroup) activity
				.findViewById(android.R.id.content);
		for (int i = 0, count = parent.getChildCount(); i < count; i++) {
			View childView = parent.getChildAt(i);
			if (childView instanceof ViewGroup) {
				childView.setFitsSystemWindows(true);
				((ViewGroup) childView).setClipToPadding(true);
			}
		}
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @param context
	 *            context
	 * @return 状态栏高度
	 */
	public static int getStatusBarHeight(Context context) {
		// 获得状态栏高度
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}

	/**
	 * 计算状态栏颜色
	 * 
	 * @param color
	 *            color值
	 * @param alpha
	 *            alpha值
	 * @return 最终的状态栏颜色
	 */
	public static int calculateStatusColor(int color, int alpha) {
		if (alpha == 0) {
			return color;
		}
		float a = 1 - alpha / 255f;
		int red = color >> 16 & 0xff;
		int green = color >> 8 & 0xff;
		int blue = color & 0xff;
		red = (int) (red * a + 0.5);
		green = (int) (green * a + 0.5);
		blue = (int) (blue * a + 0.5);
		return 0xff << 24 | red << 16 | green << 8 | blue;
	}

	/**
	 * 设置与顶部状态栏的间距
	 */
	public static void setStatusBarPadding(Context context, int StatusBarHeight) {
		if (StatusBarHeight == 0) {
			int statusBarHeight = StatusBarUtil.getStatusBarHeight(context);
			StatusBarHeight = statusBarHeight;
		}
		ViewGroup contentView = ((ViewGroup) ((Activity) context)
				.findViewById(android.R.id.content));
		contentView.setPadding(0, StatusBarHeight, 0, 0);
	}

	public static void setStatusBarPadding(Context context) {
		setStatusBarPadding(context, 0);
	}

}
