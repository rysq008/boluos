package com.delevin.boluolcs.activity;

import java.util.HashMap;
import java.util.Map;

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
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.utils.RegisterCodeTimer;
import com.delevin.boluolcs.utils.RegisterCodeTimerService;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.sorfkeyboard.SetPassword;
import com.delevin.sorfkeyboard.SetPassword.SetPwdCallback;
import com.pusupanshi.boluolicai.R;

public class IDCardNextPhoneCodeActivity extends BaseActivity implements
		OnClickListener {
	private TitleView tvTitle;
	private TextView tvPhone;
	private Button btnGetCode, btnNext;
	private EditText edtCode;
	private Intent mIntent;
	private String phone;

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_idcard_next_phonecode);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.idcard_next_phonecode_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("验证手机");

		tvPhone = (TextView) findViewById(R.id.idcard_next_phonecode_tv_phone);
		edtCode = (EditText) findViewById(R.id.idcard_next_phonecode_edt__code);
		btnGetCode = (Button) findViewById(R.id.idcard_next_phonecode_btn_getcode);
		btnNext = (Button) findViewById(R.id.idcard_next_phonecode_btn_next);
		btnGetCode.setOnClickListener(this);
		btnNext.setOnClickListener(this);

		RegisterCodeTimerService.setHandler(mCodeHandler);
		mIntent = new Intent(IDCardNextPhoneCodeActivity.this,
				RegisterCodeTimerService.class);
	}

	@Override
	protected void getData() {
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.idcard_next_phonecode_btn_getcode:
			AndroidUtils.getCallPhoneCode(phone, getApplicationContext(),
					new PhoneCodeCallBack() {

						@Override
						public void onResponse(JSONObject response) {
							// TODO Auto-generated method stub
							try {
								String code = response.getString("code");
								String strInfo = response.getString("desc")
										.toString();
								// 成功
								if (TextUtils.equals(code, "10000")) {
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
									btnGetCode.setEnabled(false);
									startService(mIntent);
									tvPhone.setText("已向"
											+ phone.substring(0, 4) + "****"
											+ phone.substring(8, 11)
											+ "发送短信验证码");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
			break;
		case R.id.idcard_next_phonecode_btn_next:
			String verify_code = edtCode.getText().toString().trim();
			if (!BoluoUtils.isEmpty(verify_code)) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("phone", phone);
				params.put("verify_code", verify_code);
				Myapplication.okhttpManger.sendComplexForm(
						getApplicationContext(), false,
						BeanUrl.YANZHENGMAPOST_STRING, params, new Funck4() {

							@Override
							public void onResponse(JSONObject result) {
								String code;
								try {
									code = result.getString("code");
									if (TextUtils.equals(code, "10000")) {
										// 设置密码
										SetPassword
												.thod(IDCardNextPhoneCodeActivity.this,
														new SetPwdCallback() {
															// private String
															// pwd;
															@Override
															public void onPwd(
																	String pwd) {
																getCommitPassword(pwd);

															}

															@Override
															public void onTextView(
																	TextView t) {
																t.setVisibility(View.GONE);
															}

														});
									} else {
										Toast.makeText(
												getApplicationContext(),
												result.getString("desc")
														.toString(),
												Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch blockresult
									e.printStackTrace();
								}

							}
						});
			}

			break;

		default:
			break;
		}
	}

	private void getCommitPassword(String pwd) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("new_passwd", pwd);
		params.put("re_new_passwd", pwd);
		Myapplication.okhttpManger.sendComplexForm(getApplicationContext(),
				false, QntUtils.getURL(BeanUrl.BANKCHONGZHIMIMA_STRING, phone),
				params, new Funck4() {

					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								Toast.makeText(getApplicationContext(),
										"密码设置成功", Toast.LENGTH_SHORT).show();
								finish();
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
			if (msg.what == com.delevin.boluolcs.utils.RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
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
