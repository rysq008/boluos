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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.gestureedit.GestureEditActivity;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-6 上午9:12:08    类说明 
 */

public class MySafetyManagmentActivity extends BaseActivity implements
		OnClickListener {

	private TitleView tvTitle;
	private TextView tvState;
	private ImageView imgSuo;
	private RelativeLayout rlBtn;
	private String phone;
	public static MySafetyManagmentActivity activity;
	public static MySafetyManagmentActivity managmentActivity;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		setContentView(R.layout.activity_safety_managment);
		activity = this;
		RelativeLayout layoutShoushi = (RelativeLayout) findViewById(R.id.safety_shoushi_rl_dianji);
		layoutShoushi.setOnClickListener(this);
		managmentActivity = this;
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.titleView_safety_managment);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("安全管理");

		rlBtn = (RelativeLayout) findViewById(R.id.safety_manament_rl_dianji);
		tvState = (TextView) findViewById(R.id.safety_manament_tv_state);
		imgSuo = (ImageView) findViewById(R.id.safety_manament_img_suo);
	}

	@Override
	protected void getData() {
		String url = BeanUrl.SetPossword;
		Myapplication.okhttpManger.sendComplexForm(getApplicationContext(),
				false, QntUtils.getURL(url, phone), null, new Funck4() {

					@Override
					public void onResponse(JSONObject result) {
						// TODO Auto-generated method stub
						try {
							String code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								String strContent = result.getString("content");
								if (strContent.equals("0")) {
									tvState.setText("未设置");
									imgSuo.setImageDrawable(getResources()
											.getDrawable(
													R.drawable.jiyimima_one));
									rlBtn.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {

											BoluoUtils
													.getDilogSetPassWord(
															MySafetyManagmentActivity.this,
															AddNumberActivity.class,
															"提示：", "请设置交易密码",
															"确定", "取消",
															"第一次设置支付密码");
										}
									});

								} else if (strContent.equals("1")) {
									tvState.setText("已设置");
									imgSuo.setImageDrawable(getResources()
											.getDrawable(
													R.drawable.jianyimima_two));
									rlBtn.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											Intent intent = new Intent();
											intent.setClass(
													getApplicationContext(),
													YiSetPayPasswordNextActivity.class);
											startActivity(intent);

										}
									});
								}
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.safety_shoushi_rl_dianji:
			startActivity(new Intent(MySafetyManagmentActivity.this,
					GestureEditActivity.class));
			break;

		default:
			break;
		}
	}
}
