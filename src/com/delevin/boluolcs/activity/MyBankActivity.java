package com.delevin.boluolcs.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.TitleView.OnRightButtonClickListener;
import com.delevin.jsandroid.JSAndroidActivity;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-5 下午2:57:49    类说明 我的银行卡
 */
public class MyBankActivity extends BaseActivity {
	private ImageView bankImg;
	private TextView bankName;
	private TextView name;
	private TextView bankCode;
	private TextView limitTop;
	private TextView limitDay;
	private LinearLayout layout;
	private ImageView img;

	@Override
	protected void findViews() {

		setContentView(R.layout.boluo_my_bank);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_myBank);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("我的银行卡");
		titleView.setRightIcon(R.drawable.bang_help);
		titleView
				.setOnRightButtonClickListener(new OnRightButtonClickListener() {

					@Override
					public void OnRightButtonClick(View v) {
						Intent intent = new Intent(MyBankActivity.this,
								JSAndroidActivity.class);
						intent.putExtra("title", "帮助中心");
						intent.putExtra("jsUrl", BeanUrl.ChangeBankcradExplain);
						startActivity(intent);
					}
				});
		bankImg = (ImageView) findViewById(R.id.myBank_img);
		bankCode = (TextView) findViewById(R.id.myBank_bankCode);
		bankName = (TextView) findViewById(R.id.myBank_bankName);
		name = (TextView) findViewById(R.id.myBank_name);
		limitTop = (TextView) findViewById(R.id.myBank_single_amt);
		limitDay = (TextView) findViewById(R.id.myBank_day_amt);
		layout = (LinearLayout) findViewById(R.id.myBank_jiazai_layout);
		img = (ImageView) findViewById(R.id.myBank_jiazai_img);
	}

	@Override
	protected void getData() {
		ProessDilogs.getProessAnima(img, this);
		Myapplication.okhttpManger.sendComplexForm(this, false, QntUtils
				.getURL(BeanUrl.TIXIAN_INIT_STRING, BoluoUtils.getShareOneData(
						getApplicationContext(), "phone")), null, new Funck4() {

			@Override
			public void onResponse(JSONObject result) {
				try {
					if (TextUtils.equals(result.getString("code"), "10000")) {
						ProessDilogs.closeAnimation(img, layout);
						JSONObject object = result.getJSONObject("content");
						String bank_address = object.getString("bank_address");
						AndroidUtils.getImg(getApplicationContext(),
								bank_address, bankImg, R.drawable.boluo_center,
								R.drawable.boluo_fail);
						// BoluoUtils.getBankCode(bank_code, bankImg);
						bankName.setText(object.getString("bank_name"));
						String pay_bankcard = object.getString("pay_bankcard");
						bankCode.setText(QntUtils.getBankCode(pay_bankcard));
						String real_name = object.getString("real_name");
						QntUtils.getReanName(real_name, name);
						limitTop.setText(object.getString("single_amt") + "元");
						limitDay.setText(object.getString("day_amt") + "元");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
