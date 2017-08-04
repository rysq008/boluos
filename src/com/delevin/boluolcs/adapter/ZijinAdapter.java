package com.delevin.boluolcs.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.delevin.boluolcs.base.adapter.MyBaseAdapter;
import com.delevin.boluolcs.base.adapter.ViewHolder;
import com.delevin.boluolcs.bean.BeanZijin;
import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-5 上午10:03:40    类说明 
 */

public class ZijinAdapter extends MyBaseAdapter<BeanZijin> {

	public ZijinAdapter(Context mContext, List<BeanZijin> listDatas,
			int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, BeanZijin item) {
		TextView title = holder.getView(R.id.zijin_title);
		TextView money = holder.getView(R.id.zijin_money);
		TextView time = holder.getView(R.id.zijin_time);
		TextView staus = holder.getView(R.id.zijin_staus);
		if (TextUtils.isEmpty(item.getProduct_info())) {
			// title.setText(item.getInfo().replaceAll("\\d+",""));
			title.setText(item.getType());
		} else {
			title.setText(item.getProduct_info());
		}
		if (TextUtils.equals(item.getType(), "充值")) {
			staus.setText(item.getType() + item.getMoney() + "元");
		} else {
			staus.setText(item.getInfo());
		}
		money.setText(item.getMoney() + "元");
		time.setText(QntUtils.getSubStringW(item.getTime(), 0, 10));

	}

}
