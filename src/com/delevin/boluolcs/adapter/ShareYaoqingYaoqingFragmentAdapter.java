package com.delevin.boluolcs.adapter;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.delevin.boluolcs.base.adapter.MyBaseAdapter;
import com.delevin.boluolcs.base.adapter.ViewHolder;
import com.delevin.boluolcs.bean.BeanShareYaoqingYaoqingFragment;
import com.pusupanshi.boluolicai.R;

public class ShareYaoqingYaoqingFragmentAdapter extends
		MyBaseAdapter<BeanShareYaoqingYaoqingFragment> {

	public ShareYaoqingYaoqingFragmentAdapter(Context mContext,
			List<BeanShareYaoqingYaoqingFragment> listDatas, int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, BeanShareYaoqingYaoqingFragment item) {
		TextView tvYonghuming = holder
				.getView(R.id.item_yaoqingjilu_tv_yonghuming);
		TextView tvZhuceTime = holder
				.getView(R.id.item_yaoqingjilu_tv_zhuceshijian);
		TextView tvHongbao = holder.getView(R.id.item_yaoqingjilu_tv_hongbao);

		tvYonghuming.setText(item.getName());
		tvZhuceTime.setText(item.getReg_time());
		if (item.getSign() == 1) {
			tvHongbao.setText("是");
		} else {
			tvHongbao.setText("否");
		}

	}

}
