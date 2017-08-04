package com.delevin.boluolcs.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.delevin.boluolcs.base.adapter.MyBaseAdapter;
import com.delevin.boluolcs.base.adapter.ViewHolder;
import com.delevin.boluolcs.bean.BeanTixianJilu;
import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

public class TixianJiluAdapter extends MyBaseAdapter<BeanTixianJilu> {
	public TixianJiluAdapter(Context mContext, List<BeanTixianJilu> listDatas,
			int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, BeanTixianJilu item) {
		// TODO Auto-generated method stub
		TextView tvOR = holder
				.getView(R.id.item_chongzhi_jilu_tv_chongzhi_or_tixian);
		TextView tvTime = holder.getView(R.id.item_chongzhi_jilu_tv_time);
		TextView tvMoney = holder.getView(R.id.item_chongzhi_jilu_tv_money);
		TextView tvState = holder.getView(R.id.item_chongzhi_jilu_tv_state);
		TextView tvShouxufei = holder
				.getView(R.id.item_chongzhi_jilu_tv_tixianshouxxufei);
		tvOR.setText("提现");
		tvTime.setText(QntUtils.getSubStringW(item.getTime(), 0, 10));
		tvMoney.setText(item.getMoney() + "元");
		tvShouxufei.setVisibility(View.VISIBLE);
		tvShouxufei.setText("提现手续费" + item.getFee() + "元");
		if (item.getType().equals("1")) {
			tvState.setText("审核中");
		} else if (item.getType().equals("2")) {
			tvState.setText("处理中");
		} else if (item.getType().equals("3")) {
			tvState.setText("已完成");
		} else if (item.getType().equals("4")) {
			tvState.setText("未通过");
		} else if (item.getType().equals("5")) {
			tvState.setText("提现失败");
		}

	}

}
