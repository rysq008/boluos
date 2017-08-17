package com.delevin.boluolcs.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delevin.boluolcs.utils.BoluoUtils;
import com.pusupanshi.boluolicai.R;

/**
 *  @author 李红涛  @version 创建时间：2016-12-19 下午1:17:07    类说明 
 */

public class TouziObjectHotItemView extends LinearLayout {
	private TextView product_name;
	private TextView object_rate;
	private TextView object_rate_add;
	private TextView object_time_limit;
	private TextView limitMountTxt;
	private TextView bt_staus;

	public TouziObjectHotItemView(Context context) {
		super(context);
		View v = LayoutInflater.from(context).inflate(R.layout.item_object_hot,
				this, true);
		init(v);
	}

	public TouziObjectHotItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View v = LayoutInflater.from(context).inflate(R.layout.item_object_hot,
				this, true);
		init(v);
	}

	private void init(View v) {
		product_name = (TextView) v.findViewById(R.id.hot_object_product_name);
		object_rate = (TextView) v.findViewById(R.id.hot_object_rate);
		object_rate_add = (TextView) v.findViewById(R.id.hot_object_rate_add);
		object_time_limit = (TextView) v
				.findViewById(R.id.hot_object_time_limit);
		limitMountTxt = (TextView) v
				.findViewById(R.id.item_object_tv_shangxianjine);
		bt_staus = (TextView) v.findViewById(R.id.hot_bt_staus);
	}

	public void setTextDatas(String product_names, String object_rates,
			String object_rate_p, String object_time_limits,
			String limit_mount, String bt_stauss) {

		product_name.setText(product_names);
		object_rate.setText(object_rates);
		object_time_limit.setText(object_time_limits);
		object_rate_add.setText(object_rate_p);
		limitMountTxt.setText("上限" + limit_mount + "元");
		BoluoUtils.getStatusOne(bt_stauss, bt_staus, "未上线");
	}

	public void setTextAddRate(String addRate) {
		object_rate_add.setText(addRate);
	}
}
