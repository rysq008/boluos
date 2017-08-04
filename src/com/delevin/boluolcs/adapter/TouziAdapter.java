package com.delevin.boluolcs.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.delevin.boluolcs.base.adapter.MyBaseAdapter;
import com.delevin.boluolcs.base.adapter.ViewHolder;
import com.delevin.boluolcs.bean.BeanTouzi;
import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-4 下午2:46:39    类说明 
 */

public class TouziAdapter extends MyBaseAdapter<BeanTouzi> {

	public TouziAdapter(Context mContext, List<BeanTouzi> listDatas,
			int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, BeanTouzi item) {
		TextView tvName = holder.getView(R.id.item_Touzi_product_name);
		TextView tvTime = holder.getView(R.id.item_Touzi_Date);
		TextView tvTimeState = holder.getView(R.id.item_Touzi_Time_state);
		TextView tvBenJin = holder.getView(R.id.item_Touzi_pro_fit);
		TextView tvShouyi = holder.getView(R.id.item_Touzi_YuqiShouyi);
		ImageView imgState = holder.getView(R.id.item_Touzi_img);

		
		if (item.getInvest_status().equals("已完成")) {
//			tvTimeState.setText("还款时间");
//			imgState.setBackgroundResource(R.drawable.yiwancheng);
		} else if (item.getInvest_status().equals("还款中")) {
			tvName.setText(item.getProduct_name());
			tvTime.setText(QntUtils.getSubStringW(item.getEnd_time(), 0, 10));
			double d1 = QntUtils.getDouble(item.getInvest_money());
			double d2 = QntUtils.getDouble(item.getPro_fit());
			tvBenJin.setText(QntUtils.getFormat(d1));
			tvShouyi.setText(QntUtils.getFormat(d2 - d1));
			tvTimeState.setText("还款时间");
			imgState.setBackgroundResource(R.drawable.huankuanzhong);
		} else if (item.getInvest_status().equals("投资中")) {
			tvName.setText(item.getProduct_name());
			tvTime.setText(QntUtils.getSubStringW(item.getEnd_time(), 0, 10));
			double d1 = QntUtils.getDouble(item.getInvest_money());
			double d2 = QntUtils.getDouble(item.getPro_fit());
			tvBenJin.setText(QntUtils.getFormat(d1));
			tvShouyi.setText(QntUtils.getFormat(d2 - d1));
			tvTimeState.setText("预期还款时间");
			imgState.setBackgroundResource(R.drawable.touzizhong);
		}

	}
}
