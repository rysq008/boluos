package com.delevin.boluolcs.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.mipmap;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.interfaces.PhoneCodeCallBack;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.RegisterCodeTimer;
import com.delevin.boluolcs.utils.RegisterCodeTimerService;
import com.delevin.boluolcs.utils.CodeTimerUtils;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

public class OldPhoneCodeActivity extends BaseActivity implements
		OnClickListener {

	private TitleView tvTitle;
	private TextView tvPhone;
	private EditText edtCode;
	private Button btnGetCode, btnNext;
	private Intent mIntent;
	private String strPhone;
	private Map<String, String> params = new HashMap<String, String>();
	public static OldPhoneCodeActivity instance;

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_old_phone_code);
		instance = this;
		View statusBarview = findViewById(R.id.old_phone_code_statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.old_phone_code_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("手机验证");

		tvPhone = (TextView) findViewById(R.id.old_phone_code_tv_phone);
		edtCode = (EditText) findViewById(R.id.old_phone_code_edt_code);
		btnGetCode = (Button) findViewById(R.id.old_phone_code_btn_getcode);
		btnNext = (Button) findViewById(R.id.old_phone_code_btn_next);
		btnGetCode.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		strPhone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		tvPhone.setText(strPhone.subSequence(0, 4) + "****"
				+ strPhone.substring(8, 11));
		// 倒计时
		mIntent = CodeTimerUtils.getIntents(this, btnGetCode);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.old_phone_code_btn_getcode:
			AndroidUtils.getCallPhoneCode(strPhone, getApplicationContext(),
					new PhoneCodeCallBack() {

						@Override
						public void onResponse(JSONObject response) {
							String code;
							try {
								code = response.getString("code");
								String strInfo = response.getString("desc")
										.toString();
								if (TextUtils.equals(code, "10000")) {
									CodeTimerUtils.startIntent(
											getApplicationContext(),
											btnGetCode, mIntent);
									params.clear();
								} else if (TextUtils.equals(code, "10010")) {
									// 失败
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
								} else if (TextUtils.equals(code, "10002")) {
									// 用户身份验证失败
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
								} else if (TextUtils.equals(code, "10002")) {
									// 数据库操作失败
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

			break;
		case R.id.old_phone_code_btn_next:
			params.put("phone", strPhone);
			params.put("verify_code", edtCode.getText().toString());
			Myapplication.okhttpManger.sendComplexForm(getApplicationContext(),
					false, BeanUrl.YANZHENGMAPOST_STRING, params, new Funck4() {

						@Override
						public void onResponse(JSONObject result) {
							// TODO Auto-generated method stub
							String code;
							try {
								code = result.getString("code");
								String strInfo = result.getString("desc")
										.toString();
								if (TextUtils.equals(code, "10000")) {
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
									params.clear();
									startActivity(new Intent(
											getApplicationContext(),
											NewPhoneCodeActivity.class));

								} else if (TextUtils.equals(code, "10002")) {
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		CodeTimerUtils.stopIntent(this, mIntent);
	}

}
