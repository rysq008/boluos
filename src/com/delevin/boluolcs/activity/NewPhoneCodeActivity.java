package com.delevin.boluolcs.activity;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.interfaces.PhoneCodeCallBack;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.utils.RegisterCodeTimer;
import com.delevin.boluolcs.utils.RegisterCodeTimerService;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

public class NewPhoneCodeActivity extends BaseActivity implements
		OnClickListener {
	private TitleView tvTitle;
	private EditText edtPhone, edtCode;
	private Button btnGetCode, btnTiJiao;
	private Intent mIntent;
	private Map<String, String> params = new HashMap<String, String>();
	private String strPhone;

	@Override
	protected void findViews() {
		setContentView(R.layout.activity_new_phone_code);
		View statusBarview = findViewById(R.id.new_phone_code_statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.new_phone_code_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("手机验证");

		edtPhone = (EditText) findViewById(R.id.new_phone_code_edt_newPhone);
		edtCode = (EditText) findViewById(R.id.new_phone_code_edt_code);
		btnGetCode = (Button) findViewById(R.id.new_phone_code_btn_getCode);
		btnTiJiao = (Button) findViewById(R.id.new_phone_code_btn_tijiao);
		btnGetCode.setOnClickListener(this);
		btnTiJiao.setOnClickListener(this);
		strPhone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		// 倒计时
		RegisterCodeTimerService.setHandler(mCodeHandler);
		mIntent = new Intent(NewPhoneCodeActivity.this,
				RegisterCodeTimerService.class);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_phone_code_btn_getCode:
			AndroidUtils.getCallPhoneCode(edtPhone.getText().toString(),
					getApplicationContext(), new PhoneCodeCallBack() {

						@Override
						public void onResponse(JSONObject response) {
							// TODO Auto-generated method stub
							String code;
							try {
								code = response.getString("code");
								String strInfo = response.getString("desc")
										.toString();
								if (TextUtils.equals(code, "10000")) {
									btnGetCode.setEnabled(false);
									startService(mIntent);
									params.clear();
								} else if (TextUtils.equals(code, "10010")) {
									// 失败
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
								} else if (TextUtils.equals(code, "10002")) {
									// 用户身份验证失败
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
								} else if (TextUtils.equals(code, "10007")) {
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
		case R.id.new_phone_code_btn_tijiao:
			params.put("phone", edtPhone.getText().toString());
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
									postPhone();
								} else if (TextUtils.equals(code, "10001")) {
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
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

	private void postPhone() {
		params.put("phone", edtPhone.getText().toString());
		Myapplication.okhttpManger.sendComplexForm(getApplicationContext(),
				false, QntUtils.getURL(BeanUrl.newPhonePost, strPhone), params,
				new Funck4() {

					@Override
					public void onResponse(JSONObject result) {
						// TODO Auto-generated method stub
						String code;
						try {
							code = result.getString("code");
							String strInfo = result.getString("desc")
									.toString();
							if (TextUtils.equals(code, "10000")) {
								params.clear();
								Toast.makeText(getApplicationContext(),
										strInfo, Toast.LENGTH_SHORT).show();
								if (OldPhoneCodeActivity.instance != null) {
									OldPhoneCodeActivity.instance.finish();
								}
								finish();
							} else if (TextUtils.equals(code, "10001")) {
								Toast.makeText(getApplicationContext(),
										strInfo, Toast.LENGTH_SHORT).show();
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
	}

	/**
	 * 倒计时Handler
	 */
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
				btnGetCode.setText(msg.obj.toString());
			} else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
				btnGetCode.setEnabled(true);
				btnGetCode.setText(msg.obj.toString());
			}
		};
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopService(mIntent);
	}

}
