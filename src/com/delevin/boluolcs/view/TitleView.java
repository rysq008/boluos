package com.delevin.boluolcs.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-15 下午1:41:15    类说明 
 */
public class TitleView extends LinearLayout {
	private OnRightButtonClickListener mRightButtonClickListener;
	private MyViewHolder mViewHolder;
	private View viewAppTitle;

	public TitleView(Context context) {
		super(context);

		init(context);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	@SuppressLint("InflateParams")
	private void init(Context context) {
		// LayoutInflater inflater = (LayoutInflater) this.getContext()
		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// LayoutParams layoutParams = new
		// LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT);
		// viewAppTitle = inflater.inflate(R.layout.boluos_view_title, null);
		//
		// this.addView(viewAppTitle, layoutParams);
		View.inflate(context, R.layout.boluos_view_title, this);

		mViewHolder = new MyViewHolder(this);
		mViewHolder.llLeftGoBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread() {
					public void run() {
						try {
							Instrumentation instrumentation = new Instrumentation();
							instrumentation
									.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
						} catch (Exception e) {
							e.printStackTrace();
						}
					};
				}.start();
			}
		});
		mViewHolder.llRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mRightButtonClickListener != null) {
					mRightButtonClickListener.OnRightButtonClick(v);
				}
			}
		});
	}

	public void initViewsVisible(boolean isLeftButtonVisile,
			boolean isCenterTitleVisile, boolean isRightIconVisile,
			boolean isRightTitleVisile)

	{
		// 左侧返回
		mViewHolder.llLeftGoBack
				.setVisibility(isLeftButtonVisile ? View.VISIBLE
						: View.INVISIBLE);

		// 中间标题
		mViewHolder.tvCenterTitle
				.setVisibility(isCenterTitleVisile ? View.VISIBLE
						: View.INVISIBLE);

		// 右侧返回图标,文字
		if (!isRightIconVisile && !isRightTitleVisile) {
			mViewHolder.llRight.setVisibility(View.INVISIBLE);
		} else {
			mViewHolder.llRight.setVisibility(View.VISIBLE);
		}
		mViewHolder.ivRightComplete
				.setVisibility(isRightIconVisile ? View.VISIBLE : View.GONE);
		mViewHolder.tvRightComplete
				.setVisibility(isRightTitleVisile ? View.VISIBLE
						: View.INVISIBLE);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setAppTitle(String title) {
		if (!TextUtils.isEmpty(title)) {
			mViewHolder.tvCenterTitle.setText(title);
		}
	}

	public void setRightTitle(String text) {
		if (!TextUtils.isEmpty(text)) {
			mViewHolder.tvRightComplete.setText(text);
		}
	}

	public void setRightIcon(int sourceID) {
		mViewHolder.ivRightComplete.setImageResource(sourceID);
	}

	public void setLeftOnclick(
			OnLeftButtonClickListener mOnLeftButtonClickListener) {
		if (mOnLeftButtonClickListener != null) {
		}
	}

	public void setAppBackground(int color) {
		viewAppTitle.setBackgroundColor(color);
	}

	public void setOnLeftButtonClickListener(OnLeftButtonClickListener listen) {
	}

	public void setOnRightButtonClickListener(OnRightButtonClickListener listen) {
		mRightButtonClickListener = listen;
	}

	public static abstract interface OnLeftButtonClickListener {
		public abstract void onLeftButtonClick(View v);
	}

	public static abstract interface OnRightButtonClickListener {
		public abstract void OnRightButtonClick(View v);
	}

	static class MyViewHolder {
		LinearLayout llLeftGoBack;
		TextView tvCenterTitle;
		LinearLayout llRight;
		ImageView ivRightComplete;
		TextView tvRightComplete;

		public MyViewHolder(View v) {
			llLeftGoBack = (LinearLayout) v.findViewById(R.id.llLeftGoBack);
			tvCenterTitle = (TextView) v.findViewById(R.id.tvCenterTitle);
			llRight = (LinearLayout) v.findViewById(R.id.llRight);
			ivRightComplete = (ImageView) v.findViewById(R.id.ivRightComplete);
			tvRightComplete = (TextView) v.findViewById(R.id.tvRightComplete);
		}
	}
}
