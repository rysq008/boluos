package com.delevin.boluolcs.activity;

import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.bean.BeanFirstEvent;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;
import de.greenrobot.event.EventBus;

/**
 * 
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-2-21 下午3:23:40 类说明
 */
public class BidSuccessActivity extends BaseActivity {
	BidSuccessActivity bidSuccessActivity;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		bidSuccessActivity = this;
		setContentView(R.layout.activity_bid_sucess);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_bidSucess);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(false, true, false, false);
		titleView.setAppTitle("购买成功");
		init();
	}

	private void getIntentString(TextView tvName, TextView tvMoney,
			TextView tvLimitTime, TextView tvRate) {
		String money = getIntent().getStringExtra("buyMoney");
		// double moneyD = QntUtils.getDouble(money);
		String rate = getIntent().getStringExtra("rate");
		String isVip = BoluoUtils.getShareOneData(bidSuccessActivity, "is_vip");
		double rateD = QntUtils.getDouble(rate);
		// if (moneyD>=10000D) {
		if (isVip != null) {
			if (isVip.equals("1")) {
				tvRate.setText(QntUtils.getFormat(rateD * 100 + 1) + "%");
			} else if (isVip.equals("0")) {
				tvRate.setText(QntUtils.getFormat(rateD * 100) + "%");
			}
		} else {
			
			tvRate.setText(rateD * 100 + "%");
			
		}

		tvLimitTime.setText(getIntent().getStringExtra("limitTime") + "天");
		tvMoney.setText(money + "元");
		tvName.setText(getIntent().getStringExtra("product_name"));
		findViewById(R.id.bidSucess_bt).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (bidSuccessActivity != null) {

							EventBus.getDefault().post(new BeanFirstEvent("payOrTian"));
							bidSuccessActivity.finish();

						}
					}

				});
	}

	private void getDatas() {

		String v = BeanUrl.CHANPIN_STRING + getIntent().getStringExtra("bidId");
		Myapplication.okhttpManger.sendComplexForms(bidSuccessActivity, false,
				v, null, new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
//						Toast.makeText(bidSuccessActivity, "java success",
//								Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void init() {
		TextView tvName = (TextView) findViewById(R.id.bidSucess_productName);
		TextView tvMoney = (TextView) findViewById(R.id.bidSucess_startMoney);
		TextView tvLimitTime = (TextView) findViewById(R.id.bidSucess_TimeLimit);
		TextView tvRate = (TextView) findViewById(R.id.bidSucess_rate);
		getIntentString(tvName, tvMoney, tvLimitTime, tvRate);
	}

	@Override
	protected void getData() {
		getDatas();
	}
}
