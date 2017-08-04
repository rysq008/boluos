package com.delevin.boluolcs.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.jsandroid.JSAndroidActivity;
import com.pusupanshi.boluolicai.R;

public class MyTouziCoarseDetailsActivity extends BaseActivity implements
		OnClickListener {

	private TitleView titleView;
	private TextView tvTitle, tvNianlilv, tvShouyi, tvTouzijine,
			tvHongbaoshiyong, tvHuikuanfangshi, tvTouzitime, tvJixiState,
			tvDaoqitime, tvHuikuanState, tvDaoqi, tvShouyiBiao,
			tvJixiStateTime;
	private RelativeLayout rlDetails, rlHetongxieyi, rlWeituoXieyi;
	private String strPhone;
	private String strWebUrl;
	private String isnew;
	private String preservation_url;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_my_touzi_coarse_details);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		strPhone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView = (TitleView) findViewById(R.id.mytouzi_coarse_details_titleview);
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("投资详情");
		// 初始化控件findviewbyid
		tvTitle = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_title);
		tvNianlilv = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_nianlilv);
		tvShouyi = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_shouyi);
		tvTouzijine = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_touzijine);
		tvHongbaoshiyong = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_hongbaoshiyong);
		tvHuikuanfangshi = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_huikuanfangshi);
		tvTouzitime = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_touzitime);
		tvJixiState = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_jixi_state);
		tvDaoqitime = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_daoqitime);
		tvHuikuanState = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_huankuan_state);
		tvDaoqi = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_daoqi);
		tvShouyiBiao = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_shouyi_biao);
		tvJixiStateTime = (TextView) findViewById(R.id.mytouzi_coarse_details_tv_jixi_state_time);
		rlDetails = (RelativeLayout) findViewById(R.id.mytouzi_coarse_details_rl_details);
		rlHetongxieyi = (RelativeLayout) findViewById(R.id.mytouzi_coarse_details_rl_hetongxieyi);
		rlWeituoXieyi = (RelativeLayout) findViewById(R.id.mytouzi_coarse_details_rl_weituoxieyi);
		rlDetails.setOnClickListener(this);
		rlHetongxieyi.setOnClickListener(this);
		rlWeituoXieyi.setOnClickListener(this);
		strWebUrl = getIntent().getStringExtra("link");
		isnew = getIntent().getStringExtra("isnew");
		if (TextUtils.equals(isnew, "1")) {
			getLink(strWebUrl);
		} else {
		}
		if (getIntent().getStringExtra("invest_status").equals("投资中")) {
			rlHetongxieyi.setVisibility(View.GONE);
			rlWeituoXieyi.setVisibility(View.GONE);
			tvDaoqi.setText("预期到期时间");
			tvShouyiBiao.setText("预期收益");
			tvJixiState.setText("等待计息");
		} else {
			rlHetongxieyi.setVisibility(View.VISIBLE);
			rlWeituoXieyi.setVisibility(View.VISIBLE);
			String start_time = getIntent().getStringExtra("interest_time");
			tvJixiStateTime.setText(QntUtils.getSubStringW(start_time, 0, 10));
		}
		// 投资时间
		String strTouziTime = getIntent().getStringExtra("invest_time");
		tvTouzitime.setText(QntUtils.getSubStringW(strTouziTime, 0, 10));
		// 到期时间
		String strDaoqiTime = getIntent().getStringExtra("end_time");
		tvDaoqitime.setText(QntUtils.getSubStringW(strDaoqiTime, 0, 10));
		// 还款状态
		tvHuikuanState.setText(getIntent().getStringExtra("invest_status"));
		// 项目标题
		tvTitle.setText(getIntent().getStringExtra("product_name"));
		// 投资金额
		tvTouzijine.setText(getIntent().getStringExtra("invest_money") + "元");
		// 预期收益
		tvShouyi.setText(getIntent().getStringExtra("yuqiMoney") + "元");
		// 回款方式
		tvHuikuanfangshi.setText(getIntent().getStringExtra("repay_type"));
		// 年利率
		String strNianlilv = getIntent().getStringExtra("rate");
		String rate_increase = getIntent().getStringExtra("rate_increase");
		tvNianlilv.setText("年利率"
				+ QntUtils.getFormat((QntUtils.getDouble(strNianlilv)) * 100)
				+ "%" + "+"
				+ QntUtils.getFormat(QntUtils.getDouble(rate_increase) * 100)
				+ "%");
		// 红包状态
		String hongbao = getIntent().getStringExtra("hongbao");
		if (!TextUtils.isEmpty("" + hongbao)) {
			tvHongbaoshiyong.setText(hongbao);
		} else {
			tvHongbaoshiyong.setText("没有使用优惠券");
		}
	}

	private void getLink(String links) {

		Myapplication.okhttpManger.sendComplexForm(this, false, BeanUrl.URL
				+ links, null, new Funck4() {

			@Override
			public void onResponse(JSONObject result) {

				try {
					preservation_url = result.getString("preservation_url");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void getData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mytouzi_coarse_details_rl_details:
			Intent intent = new Intent(MyTouziCoarseDetailsActivity.this,
					BidDetalsActivity.class);
			intent.putExtra("bidId", getIntent().getStringExtra("chanPinId"));
			startActivity(intent);
			break;
		case R.id.mytouzi_coarse_details_rl_hetongxieyi:
			Intent intent2 = new Intent(getApplicationContext(),
					JSAndroidActivity.class);
			if (TextUtils.equals(isnew, "1")) {
				intent2.putExtra("jsUrl", preservation_url);
				intent2.putExtra("js", "js");
			} else {
				intent2.putExtra("jsUrl", BeanUrl.URL + strWebUrl);
			}
			intent2.putExtra("title", "项目合同");
			startActivity(intent2);
			break;
		case R.id.mytouzi_coarse_details_rl_weituoxieyi:
			String strProjectName = getIntent().getStringExtra("product_name");
			Intent intentweituo = new Intent(MyTouziCoarseDetailsActivity.this,
					JSAndroidActivity.class);
			intentweituo.putExtra("jsUrl", BeanUrl.WeituoXieyi + strPhone + "/"
					+ strProjectName);
			intentweituo.putExtra("title", "委托授权书");
			startActivity(intentweituo);
			break;
		default:
			break;
		}

	}

}
