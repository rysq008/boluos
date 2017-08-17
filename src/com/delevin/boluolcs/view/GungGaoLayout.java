package com.delevin.boluolcs.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pusupanshi.boluolicai.R;

public class GungGaoLayout extends LinearLayout implements
		android.view.View.OnClickListener {
	private ImageView imageView;
	private TextView textView;

	public GungGaoLayout(Context context) {
		super(context);
		View v = LayoutInflater.from(context).inflate(R.layout.view_gonggao,
				this, true);
		init(v);
	}

	public GungGaoLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		View v = LayoutInflater.from(context).inflate(R.layout.view_gonggao,
				this, true);
		init(v);
	}

	private void init(View v) {
		textView = (TextView) v.findViewById(R.id.gonggao_txt);
	}

	/**
	 * 设置图片资源
	 */
	public void setImageResource(int resId) {

		imageView.setImageResource(resId);

	}

	/**
	 * 设置显示的文字
	 */
	public void setTextViewText(String text) {

		textView.setText(text);

	}

	/**
	 * 设置显示的文字大小
	 */
	public void setTextViewSize(float size) {

		textView.setTextSize(size);

	}

	/**
	 * 设置显示的文字颜色
	 */
	public void setTextViewColor(int color) {

		textView.setTextColor(color);

	}

	/**
	 * 设置隐藏imageView
	 */
	public void setImageVisibility(int staus) {

		imageView.setVisibility(staus);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gonggao_txt:
			Toast.makeText(getContext(), "123", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}

}
