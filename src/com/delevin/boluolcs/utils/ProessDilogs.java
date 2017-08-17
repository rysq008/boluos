package com.delevin.boluolcs.utils;


import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pusupanshi.boluolicai.R;

public class ProessDilogs extends View {
	public ProessDilogs(Context context) {
		super(context);
	}

	public static void getProess(ImageView image) {
		AnimationSet animationSet = new AnimationSet(true);
		// 参数1：从哪个旋转角度开始
		// 参数2：转到什么角度
		// 后4个参数用于设置围绕着旋转的圆的圆心在哪里
		// 参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
		// 参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
		// 参数5：确定y轴坐标的类型
		// 参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setDuration(1000);
		animationSet.addAnimation(rotateAnimation);
		animationSet.setRepeatCount(-1);
		image.startAnimation(animationSet);
	}

	/** 无限循环 */
	public static void getProessAnima(ImageView imageView, Context context) {
		Animation operatingAnim = AnimationUtils.loadAnimation(context,
				R.anim.tip);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
		imageView.startAnimation(operatingAnim);
	}

	/** 关闭动画 */
	public static void closeAnimation(ImageView imageView,
			LinearLayout linearLayout) {
		imageView.clearAnimation();
		linearLayout.setVisibility(View.GONE);
	}
}
