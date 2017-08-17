package com.delevin.boluolcs.denglu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.activity.RegisterOtherActivity;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.interfaces.PhoneCodeCallBack;
import com.delevin.boluolcs.main.MainActivity;
import com.delevin.boluolcs.utils.APPName;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.CodeTimerUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-16 上午10:00:16    类说明 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	private TitleView titleView;
	private EditText phoneCodeEdit;
	private EditText setSecretEdit;
	private EditText copySecretEdit;
	private Button bt_opt_Code;
	private Button bt_login;
	private String channelid;
	private Intent mIntent;
	private EditText toPhone;
	private boolean b = true;
	private Button btnZhucefuwuxieyi, btnFengxiangaozhi;
	private ImageView imgCheck;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		setContentView(R.layout.boluos_activity_login);
		Myapplication.loginActivity = this;
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView = (TitleView) findViewById(R.id.titleView_login);
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("注册");
		// 获得渠道信息 并对其截取获得后四位
		ApplicationInfo appInfo;
		try {
			appInfo = this.getPackageManager().getApplicationInfo(
					getPackageName(), PackageManager.GET_META_DATA);
			String msg = appInfo.metaData.getString("UMENG_CHANNEL");
			channelid = msg.substring((msg.length()) - 4, msg.length());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		initView();
	}

	/** 控件初始化 **/
	private void initView() {
		imgCheck = (ImageView) findViewById(R.id.login_checked);
		imgCheck.setOnClickListener(this);
		imgCheck.setBackgroundResource(R.drawable.boluo_img_register_ok);
		toPhone = (EditText) findViewById(R.id.login_copy_toPhone);
		phoneCodeEdit = (EditText) findViewById(R.id.login_phoneCode);
		setSecretEdit = (EditText) findViewById(R.id.login_set_password);
		copySecretEdit = (EditText) findViewById(R.id.login_copy_password);
		bt_opt_Code = (Button) findViewById(R.id.login_obtCode);
		bt_login = (Button) findViewById(R.id.bt_login);
		btnZhucefuwuxieyi = (Button) findViewById(R.id.btnzhucexieyi);
		btnFengxiangaozhi = (Button) findViewById(R.id.btnfengxiangaozhishu);
		btnZhucefuwuxieyi.setOnClickListener(this);
		btnFengxiangaozhi.setOnClickListener(this);
		bt_login.setOnClickListener(this);
		bt_opt_Code.setOnClickListener(this);
		bt_login.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_bt_boluoyellow));
		// 倒计时
		mIntent = CodeTimerUtils.getIntents(this, bt_opt_Code);
	}

	@Override
	protected void getData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_obtCode:
			AndroidUtils.getCallPhoneCode(getIntent().getStringExtra("phone"),
					getApplicationContext(), new PhoneCodeCallBack() {

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
											bt_opt_Code, mIntent);
									// params.clear();
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
		case R.id.bt_login:
			if (!TextUtils.isEmpty(setSecretEdit.getText().toString())) {
				if (TextUtils.equals(setSecretEdit.getText().toString(),
						copySecretEdit.getText().toString())) {
					final Map<String, String> params1 = new HashMap<String, String>();
					params1.put("phone", getIntent().getStringExtra("phone"));
					params1.put("verify_code", phoneCodeEdit.getText()
							.toString());
					Myapplication.okhttpManger.sendComplexForm(
							getApplicationContext(), false,
							BeanUrl.YANZHENGMAPOST_STRING, params1,
							new Funck4() {
								@Override
								public void onResponse(JSONObject result) {
									String code;
									try {
										code = result.getString("code");
										String strInfo = result.getString(
												"desc").toString();
										if (TextUtils.equals(code, "10000")) {
											params1.clear();
											getCommit();
										} else if (TextUtils.equals(code,
												"10002")) {
											Toast.makeText(
													getApplicationContext(),
													strInfo, Toast.LENGTH_SHORT)
													.show();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
				} else {
					Toast.makeText(getApplicationContext(), "两次输入密码不一致",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		case R.id.btnzhucexieyi:
			Intent intent = new Intent(LoginActivity.this,
					RegisterOtherActivity.class);
			intent.putExtra("xieyi", getResources().getString(R.string.xieyi));
			intent.putExtra("title", "注册协议");
			startActivity(intent);
			break;

		case R.id.btnfengxiangaozhishu:
			Intent intent2 = new Intent(LoginActivity.this,
					RegisterOtherActivity.class);
			intent2.putExtra("xieyi",
					getResources().getString(R.string.safe_string));
			intent2.putExtra("title", "风险告知书");
			startActivity(intent2);
			break;
		case R.id.login_checked:
			if (b) {
				imgCheck.setBackgroundResource(R.drawable.boluo_img_register_no);
				bt_login.setEnabled(false);
				bt_login.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.shape_gray));
				b = false;
			} else {
				imgCheck.setBackgroundResource(R.drawable.boluo_img_register_ok);
				bt_login.setEnabled(true);
				bt_login.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.shape_bt_boluoyellow));
				b = true;
			}
			break;
		default:
			break;
		}
	}

	private void getCommit() {
		final String secret = copySecretEdit.getText().toString();
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", getIntent().getStringExtra("phone"));
		params.put("channel_id", channelid);
		params.put("device_info", android.os.Build.MODEL.toString());
		params.put("passwd", secret);
		params.put("equipment_name", Myapplication.INFORMATION);
		params.put("equipment_token", Myapplication.DEVICE_ID);
		params.put("to_phone", toPhone.getText().toString().trim());
		Myapplication.okhttpManger.sendComplexForm(this, false,
				BeanUrl.LOGIN_STRING, params, new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								JSONObject object = result
										.getJSONObject("content");
								Map<String, String> map = new HashMap<String, String>();
								map.put("memberId",
										object.getString("memberId"));// 是否登录
								map.put("id_bind", object.getString("id_bind"));// 身份证是否绑定
								map.put("pay_bind",
										object.getString("pay_bind"));// 银行卡是否绑定
								map.put("is_pay_passwd",
										object.getString("is_pay_passwd"));// 支付密码是否设置
								map.put("phone", object.getString("phone"));// 手机号
								map.put("newer", object.getString("newer"));// 是否是新手
								map.put("visonCode",
										APPName.getVersionCode(getApplicationContext())
												+ "");
								// map.put("login_token",
								// object.getString("login_token"));
								map.put("AUTHORIZATION", QntUtils.getBase64(
										getIntent().getStringExtra("phone"),
										copySecretEdit.getText().toString()
												.trim()));// 加密Hander
								BoluoUtils.getShareCommit(
										getApplicationContext(), map);
								Toast.makeText(getApplicationContext(), "注册成功",
										Toast.LENGTH_SHORT).show();
								ZhuActivity.zhuActivity.finish();
								// MainActivity.mainActivity.finish();
								if (MainActivity.mainActivity != null) {
									MainActivity.mainActivity.finish();
								}
								startActivity(new Intent(LoginActivity.this,
										MainActivity.class));
								finish();
								// ZhuActivity.zhuActivity.finish();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		CodeTimerUtils.stopIntent(this, mIntent);
	}

}
