package com.delevin.boluolcs.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.delevin.boluolcs.base.adapter.MyBaseAdapter;
import com.delevin.boluolcs.base.adapter.ViewHolder;
import com.delevin.boluolcs.bean.BeanMoreObject;
import com.delevin.boluolcs.utils.AppUtil;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-2 下午1:31:20 类说明
 */
public class TouziMoreObjectAdapter extends MyBaseAdapter<BeanMoreObject> {

	public TouziMoreObjectAdapter(Context mContext,
			List<BeanMoreObject> listDatas, int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, final BeanMoreObject item) {
		TextView nameView = holder.getView(R.id.TouziMoreObject_name);
		TextView limitTmeView = holder.getView(R.id.TouziMoreObject_limitTme);
		TextView rateView = holder.getView(R.id.TouziMoreObject_rate);
		TextView ratesView = holder.getView(R.id.TouziMoreObject_rates);
		ProgressBar progressBar = holder.getView(R.id.TouziMoreObject_progress);
		ImageView imageView = holder.getView(R.id.TouziMoreObject_progressImg);
		TextView rongziMoney = holder.getView(R.id.TouziMoreObject_rongziMoney);
		TextView moneyRate = holder.getView(R.id.TouziMoreObject_moneyRate);
		TextView btNone = holder.getView(R.id.TouziMoreObject_btNone);
		TextView bt = holder.getView(R.id.TouziMoreObject_bt);
		bt.setBackgroundDrawable(mContext.getResources().getDrawable(
				R.drawable.shape_bt_boluoyellow));
		btNone.setBackgroundDrawable(mContext.getResources().getDrawable(
				R.drawable.shape_gray));
		String status = item.getProduct_status();
		BoluoUtils.getStatus(mContext, status, bt, null);
		// android:background="@drawable/shape_bt_boluoyellow"
		nameView.setText(item.getProduct_name());
		limitTmeView.setText(item.getTime_limit() + "天");
		// rateView.setText(QntUtils.getFormatOne(QntUtils.getDouble(item.getRate())*100));
		// ratesView.setText("+"+QntUtils.getFormatOne(QntUtils.getDouble(item.getRate_increase())*100));
		rateView.setText(""
				+ QntUtils.getDoubleToInt(QntUtils.getDouble(item.getRate()) * 100));
		ratesView.setText("+"
				+ QntUtils.getDoubleToInt(QntUtils.getDouble(item
						.getRate_increase()) * 100) + "%");
		rongziMoney.setText("融资金额|" + item.getTotal_mount());
		moneyRate.setText("|" + item.getPercentage()+"%");
		int percentage = item.getPercentage();
		// 屏幕宽高
		int result[] = AppUtil.getScreenDispaly(mContext);
		int width = result[0] - AppUtil.dip2px(mContext, 50);// 屏幕宽
		Double double1 = (double) (width / 100.00);
		LayoutParams para = (LayoutParams) imageView.getLayoutParams();
		if (percentage == 100) {
			para.leftMargin = result[0] - AppUtil.dip2px(mContext, 35);
		} else {
			para.leftMargin = (int) (percentage * double1)
					+ AppUtil.dip2px(mContext, 13);
		}
		imageView.setLayoutParams(para);
		progressBar.setMax(100); // 进度最大值
		progressBar.setProgress(percentage);
		btNone.setFocusable(false);
		if (item.getProduct_status() != null) {

		}
	}

}
