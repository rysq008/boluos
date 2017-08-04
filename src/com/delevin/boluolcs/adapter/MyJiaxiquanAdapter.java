package com.delevin.boluolcs.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delevin.boluolcs.activity.BidDetalsActivity;
import com.delevin.boluolcs.base.adapter.MyBaseAdapter;
import com.delevin.boluolcs.base.adapter.ViewHolder;
import com.delevin.boluolcs.bean.BeanJiaxiquan;
import com.pusupanshi.boluolicai.R;

/**
 *@author 李红涛  @version 创建时间：2017-1-5 下午1:46:25    类说明 
 */
public class MyJiaxiquanAdapter extends MyBaseAdapter<BeanJiaxiquan> {

	public MyJiaxiquanAdapter(Context mContext, List<BeanJiaxiquan> listDatas,
			int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void holdItem(ViewHolder holder, BeanJiaxiquan item) {
		LinearLayout layout = holder.getView(R.id.jiaxiquan_layout);
		TextView baifenbi = holder.getView(R.id.jiaxiquan_money);
		TextView icon = holder.getView(R.id.jiaxiquan_icon);
		TextView title = holder.getView(R.id.jiaxiquan_title);
		TextView time = holder.getView(R.id.jiaxiquan_time);
		TextView bidMoney = holder.getView(R.id.jiaxiquan_bidMoney);
		ImageView dian01 = holder.getView(R.id.jiaxiquan_dian01);
		ImageView dian = holder.getView(R.id.jiaxiquan_dian);
		Button stauts = holder.getView(R.id.jiaxiquan_stauts);
		int grays = mContext.getResources().getColor(R.color.gggggg);
		int coloryellow = mContext.getResources().getColor(R.color.juhuangse);
		int redpackethui = mContext.getResources().getColor(R.color.redpackethui);
		float fBaifenbi = Float.valueOf(item.getRate()) * 100;
		baifenbi.setText(fBaifenbi + "");
		bidMoney.setText("起投金额" + item.getStart_money() + "元");
		title.setText(item.getName());
		time.setText("有效期至" + item.getDead_time());
		String strType = item.getType();
		if (!TextUtils.isEmpty(strType)) {
			if (strType.equals("0")) {
				
				stauts.setText("去使用");
				dian01.setBackgroundResource(R.drawable.redpacget_liang);
				dian.setBackgroundResource(R.drawable.redpacget_liang);
				baifenbi.setTextColor(coloryellow);
				title.setTextColor(coloryellow);
				icon.setTextColor(coloryellow);
				bidMoney.setTextColor(grays);
				time.setTextColor(grays);
				final String product_id = item.getProduct_id();
				final String start_money = item.getStart_money();
				final String id = item.getId();
				final String money2 = item.getRate();
				final String is_new_product = item.getIs_new_product();
				layout.setBackgroundResource(R.drawable.redpacket_weishiyong);
				stauts.setBackgroundDrawable(mContext.getResources()
						.getDrawable(R.drawable.shape_button_yellow));
				stauts.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext,
								BidDetalsActivity.class);
						intent.putExtra("bidId", product_id);
						intent.putExtra("buyMoney", start_money);
						if (!TextUtils.isEmpty(is_new_product)) {
							if (TextUtils.equals(is_new_product, "1")) {
								intent.putExtra("isNewer", true);
							}else if(TextUtils.equals(is_new_product, "0")){
								intent.putExtra("isNewer", false);
							}
						}
						
						mContext.startActivity(intent);
					}
				});
			} else if (strType.equals("1")) {
				stauts.setText("已使用");
				title.setTextColor(redpackethui);
				icon.setTextColor(redpackethui);
				baifenbi.setTextColor(redpackethui);
				bidMoney.setTextColor(redpackethui);
				time.setTextColor(redpackethui);
				dian.setBackgroundResource(R.drawable.redpacket_dian);
				dian01.setBackgroundResource(R.drawable.redpacket_dian);
				layout.setBackgroundResource(R.drawable.redpacket_yishiyong);
				stauts.setBackgroundDrawable(mContext.getResources()
						.getDrawable(R.drawable.shape_button_hui));
			} else {
				stauts.setText("已过期");
				dian.setBackgroundResource(R.drawable.redpacket_dian);
				dian01.setBackgroundResource(R.drawable.redpacket_dian);
				title.setTextColor(redpackethui);
				baifenbi.setTextColor(redpackethui);
				icon.setTextColor(redpackethui);
				bidMoney.setTextColor(redpackethui);
				time.setTextColor(redpackethui);
				layout.setBackgroundResource(R.drawable.redpacket_yiguoqi);
				stauts.setBackgroundDrawable(mContext.getResources()
						.getDrawable(R.drawable.shape_button_hui));
			}
		}
	}
}
