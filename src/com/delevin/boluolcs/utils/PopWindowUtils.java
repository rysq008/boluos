package com.delevin.boluolcs.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 *     @author 李红涛  @version 创建时间：2017-1-10 下午3:08:05    类说明 
 */

public class PopWindowUtils {
	/**
	 * getPop(上下文，加载的布局，点击的按钮);
	 * 
	 * */

	@SuppressWarnings("deprecation")
	public static PopupWindow getPop(Context context, View view, View layout) {

		PopupWindow pop = new PopupWindow(context);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.MATCH_PARENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		return pop;
	}

	/**
	 * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
	 * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
	 * 
	 * @param anchorView
	 *            呼出window的view
	 * @param contentView
	 *            window的内容布局
	 * @return window显示的左上角的xOff,yOff坐标
	 */
	public static int[] calculatePopWindowPos(final View anchorView,
			final View contentView) {
		final int windowPos[] = new int[2];
		final int anchorLoc[] = new int[2];
		// 获取锚点View在屏幕上的左上角坐标位置
		anchorView.getLocationOnScreen(anchorLoc);

		final int anchorHeight = anchorView.getHeight();

		// 获取屏幕的高宽
		final int screenHeight = getScreenHeight(anchorView.getContext());

		final int screenWidth = getScreenWidth(anchorView.getContext());

		contentView.measure(View.MeasureSpec.UNSPECIFIED,
				View.MeasureSpec.UNSPECIFIED);

		// 计算contentView的高宽
		final int windowHeight = contentView.getMeasuredHeight();

		final int windowWidth = contentView.getMeasuredWidth();
		// 判断需要向上弹出还是向下弹出显示
		final boolean isNeedShowUp = (screenHeight - anchorLoc[1]
				- anchorHeight < windowHeight);
		if (isNeedShowUp) {
			windowPos[0] = screenWidth - windowWidth;
			windowPos[1] = anchorLoc[1] - windowHeight;
		} else {
			windowPos[0] = screenWidth - windowWidth;
			windowPos[1] = anchorLoc[1] + anchorHeight;
		}
		return windowPos;
	}

	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 获取屏幕宽度(px)
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}
}
