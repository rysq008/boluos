package com.delevin.boluolcs.adapter;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.delevin.boluolcs.base.adapter.MyBaseAdapter;
import com.delevin.boluolcs.base.adapter.ViewHolder;
import com.delevin.boluolcs.bean.BeanShareYaoqingFanliFragment;
import com.pusupanshi.boluolicai.R;

public class ShareYaoqingFanliFragmentAdapter extends
		MyBaseAdapter<BeanShareYaoqingFanliFragment> {

	public ShareYaoqingFanliFragmentAdapter(Context mContext,
			List<BeanShareYaoqingFanliFragment> listDatas, int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, BeanShareYaoqingFanliFragment item) {
		// TODO Auto-generated method stub
		TextView tvRiqi = holder.getView(R.id.item_fanlijilu_tv_riqi);
		TextView tvHaoyou = holder.getView(R.id.item_fanlijilu_tv_haoyou);
		TextView tvMoney = holder.getView(R.id.item_fanlijilu_tv_money);
		TextView tvQixian = holder.getView(R.id.item_fanlijilu_tv_qixian);
		TextView tvShouru = holder.getView(R.id.item_fanlijilu_tv_shouru);

		tvRiqi.setText(item.getTime());
//		tvHaoyou.setText(item.getName());
		tvHaoyou.setText(item.getName().subSequence(0, 4) + "****"
				+ item.getName().substring(8, 11));
		tvMoney.setText(item.getMoney());
		tvQixian.setText(item.getTime_limit());
		tvShouru.setText(item.getInvite_profit());

	}

}
