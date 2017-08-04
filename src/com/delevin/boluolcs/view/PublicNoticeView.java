package com.delevin.boluolcs.view;

import java.util.List;
import com.delevin.boluolcs.bean.BeanConvert;
import com.delevin.boluolcs.bean.BeanNotice;
import com.pusupanshi.boluolicai.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class PublicNoticeView extends LinearLayout {
	int i = 0;
	private Context mContext;
	private ViewFlipper mViewFlipper;
	private View mScrollTitleView;

	public PublicNoticeView(Context context) {
		super(context);
		mContext = context;
		bindLinearLayout();
		// init();
	}

	public ViewFlipper getViewFlipper() {
		return mViewFlipper;
	}

	public PublicNoticeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// init();
		bindLinearLayout();
	}

	// private void init() {
	// bindLinearLayout();
	// bindNotices();
	// }

	/**
	 * 初始化自定义的布局
	 */
	@SuppressLint("InflateParams")
	private void bindLinearLayout() {
		mScrollTitleView = LayoutInflater.from(mContext).inflate(
				R.layout.scrollnoticebar, null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		addView(mScrollTitleView, params);

		mViewFlipper = (ViewFlipper) mScrollTitleView
				.findViewById(R.id.id_scrollNoticeTitle);
		mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.slide_in_bottom));
		mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.slide_out_top));
		mViewFlipper.startFlipping();

	}

	/**
	 * 网络请求内容后进行适配
	 * 
	 * @param <T>
	 */

	public void bindNotices(List<BeanNotice> list) {
		mViewFlipper.removeAllViews();
		while (i < list.size()) {
			GungGaoLayout t1 = new GungGaoLayout(mContext);
			BeanNotice beanNotice = list.get(i);
			t1.setId(i);
			t1.setTextViewText(beanNotice.getTitle());
			BeanConvert convert = new BeanConvert();
			convert.setiString(i);
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			// layoutParams.gravity = Gravity.CENTER_VERTICAL;
			// MyAplication.NoticeId = mViewFlipper.getId();
			mViewFlipper.addView(t1, layoutParams);
			// mViewFlipper.addView(t1, i, layoutParams);
			// mViewFlipper.addTouchables(list2);
			i++;
		}
	}

}
