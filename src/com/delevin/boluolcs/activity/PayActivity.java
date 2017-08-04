package com.delevin.boluolcs.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.LianlianPay;
import com.delevin.boluolcs.utils.NetUtils;
import com.delevin.boluolcs.utils.LianlianPay.GetCodeCallBack;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.TitleView.OnRightButtonClickListener;
import com.delevin.sorfkeyboard.SetPassword;
import com.delevin.sorfkeyboard.SetPassword.SetPwdCallback;
import com.pusupanshi.boluolicai.R;

/**
 *  @author 李红涛  @version 创建时间：2016-12-23 下午3:45:05    类说明 
 */

public class PayActivity extends BaseActivity implements OnClickListener {
	private LinearLayout layout;// 根据是否绑定身份证号添加布局
	private EditText editName;// 输入姓名
	private EditText editIdCode;// 输入身份证号
	private EditText editBankCode;// 输入银行卡号
	private TextView txtName;// 姓名
	private TextView txtIdCode;// 身份证号
	private EditText txtBankCode;// 银行卡号
	private TextView money;// 充值金额
	private Button bt_pay;// 充值按钮
	private String id_bind;
	private String is_pay_passwd;
	private String phone;
	private ImageView imgLater;
	private LinearLayout linLater;

	@Override
	protected void findViews() {
		setContentView(R.layout.activity_pay);
		getshareData();
		TitleView titleView = (TitleView) findViewById(R.id.titleView_pay);
		View statusBarview = findViewById(R.id.statusBarview);
		layout = (LinearLayout) findViewById(R.id.pay_layout);
		bt_pay = (Button) findViewById(R.id.bt_pay);
		imgLater = (ImageView) findViewById(R.id.pay_img_later);
		linLater = (LinearLayout) findViewById(R.id.pay_lin_later);
		bt_pay.setOnClickListener(this);
		money = (TextView) findViewById(R.id.pay_money);
		money.setText(getIntent().getStringExtra("number"));
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(true, true, true, true);
		titleView.setAppTitle("绑定银行卡");
		titleView.setRightTitle("支持银行");
		titleView
				.setOnRightButtonClickListener(new OnRightButtonClickListener() {

					@Override
					public void OnRightButtonClick(View v) {
						// TODO Auto-generated method stub
						startActivity(new Intent(PayActivity.this,
								ZhichiBankActivity.class));
					}
				});
		if (TextUtils.equals(id_bind, "1")) {
			getIsIdBindLayout();
		} else {
			getIsNotIdBindLayout();
		}
	}

	private void getshareData() {
		BoluoUtils.getShareOneData(getApplicationContext(), "pay_bind");
		id_bind = BoluoUtils
				.getShareOneData(getApplicationContext(), "id_bind");
		is_pay_passwd = BoluoUtils.getShareOneData(getApplicationContext(),
				"is_pay_passwd");
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");

	}

	@SuppressLint("InflateParams")
	private void getIsNotIdBindLayout() {
		View view = LayoutInflater.from(this).inflate(
				R.layout.view_pay_not_id_bind, null);
		editName = (EditText) view.findViewById(R.id.view_pay_not_idBind_Name);
		editIdCode = (EditText) view
				.findViewById(R.id.view_pay_not_idBind_idCode);
		editBankCode = (EditText) view
				.findViewById(R.id.view_pay_not_idBind_bankCode);
		layout.addView(view);
	}

	@SuppressLint("InflateParams")
	private void getIsIdBindLayout() {
		View view = LayoutInflater.from(this).inflate(
				R.layout.view_pay_is_id_bind, null);
		txtName = (TextView) view.findViewById(R.id.view_pay_isId_name);
		txtIdCode = (TextView) view.findViewById(R.id.view_pay_isId_code);
		txtBankCode = (EditText) view.findViewById(R.id.view_pay_isId_BankCode);
		layout.addView(view);
	}

	@Override
	protected void getData() {

		if (TextUtils.equals(id_bind, "1")) {
			getIDatas();
		}
	}

	/**
	 * 
	 * 充值初始化
	 * 
	 * **/
	private void getIDatas() {
		Myapplication.okhttpManger.sendComplexForm(this, false,
				QntUtils.getURL(BeanUrl.PAY_INIT_STRING, phone), null,
				new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								JSONObject object = result
										.getJSONObject("content");
								txtName.setText(object.getString("real_name"));
								txtIdCode.setText(object.getString("id_card"));
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
		case R.id.bt_pay:
			if (NetUtils.getNetWorkState(PayActivity.this) != -1) {
				if (TextUtils.equals(is_pay_passwd, "true")) {
					getPay();
				} else {
					// 设置密码
					SetPassword.thod(PayActivity.this, new SetPwdCallback() {
						// private String pwd;
						@Override
						public void onPwd(String pwd) {
							getPassword(pwd, getApplicationContext(), QntUtils
									.getURL(BeanUrl.BANKCHONGZHIMIMA_STRING,
											phone));
							// getPay();
						}

						@Override
						public void onTextView(TextView t) {
							t.setVisibility(View.GONE);
						}
					});
				}
			} else {
				BoluoUtils.getDilogDome(PayActivity.this, "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;

		default:
			break;
		}
	}

	// 提交支付
	private void getPay() {
		if (TextUtils.equals(id_bind, "1")) {

			if (!TextUtils.isEmpty(txtBankCode.getText().toString().trim())) {
				if (!TextUtils.isEmpty(txtName.getText().toString().trim())) {
					if (!TextUtils.isEmpty(txtIdCode.getText().toString()
							.trim())) {
						if (!TextUtils.isEmpty(money.getText().toString()
								.trim())) {
							LianlianPay.getPay(PayActivity.this, phone,txtBankCode.getText().toString().trim(),
									txtIdCode.getText().toString().trim(),txtName.getText().toString().trim(), money.getText().toString().trim(),
									imgLater, linLater, new GetCodeCallBack() {

										@Override
										public void onResponse(String msg,String code, boolean b) {
											if (b) {
												Map<String, String> map = new HashMap<String, String>();
												map.put("id_bind", "1");
												map.put("pay_bind", "1");
												map.put("is_pay_passwd", "true");
												AddNumberActivity.addNumberActivity.finish();
												BoluoUtils.getShareCommit(getApplicationContext(),map);
												Intent intent = new Intent(PayActivity.this,PayOrTianSuccessActivity.class);
												intent.putExtra("money", money.getText().toString());
												intent.putExtra("flag", "pay");
												startActivity(intent);
												finish();
											}
										}
									});
						} else {
							Toast.makeText(getApplicationContext(),
									"请重新选择充值金额", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "请求失败",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "请求失败",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "请您输入银行卡号",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			if (!TextUtils.isEmpty(editName.getText().toString().trim())) {
				if (!TextUtils.isEmpty(editIdCode.getText().toString().trim())) {
					if (!TextUtils.isEmpty(editBankCode.getText().toString()
							.trim())) {
						if (!TextUtils.isEmpty(money.getText().toString()
								.trim())) {
							// zhizhi
							LianlianPay.getPay(PayActivity.this, phone,
									editBankCode.getText().toString().trim(),
									editIdCode.getText().toString().trim(),
									editName.getText().toString().trim(), money
											.getText().toString().trim(),
									imgLater, linLater, new GetCodeCallBack() {
										@Override
										public void onResponse(String msg,
												String code, boolean b) {
											if (b) {
												Map<String, String> map = new HashMap<String, String>();
												map.put("id_bind", "1");
												map.put("pay_bind", "1");
												map.put("is_pay_passwd", "true");
												BoluoUtils
														.getShareCommit(
																getApplicationContext(),
																map);
												Intent intent = new Intent(
														PayActivity.this,
														PayOrTianSuccessActivity.class);
												intent.putExtra("money", money
														.getText().toString());
												intent.putExtra("flag", "pay");
												startActivity(intent);
												AddNumberActivity.addNumberActivity
														.finish();
												finish();
												// Toast.makeText(getApplicationContext(),
												// code,
												// Toast.LENGTH_SHORT).show();
											}
										}
									});
						} else {
							Toast.makeText(getApplicationContext(),
									"请重新选择充值金额", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "请您输入您的银行卡号",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "请您输入您的身份证号",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "请您输入您的姓名",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void getPassword(String password, final Context context, String url) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("new_passwd", password);
		params.put("re_new_passwd", password);
		Myapplication.okhttpManger.sendComplexForm(context, false, url, params,
				new Funck4() {

					@Override
					public void onResponse(JSONObject result) {
						String stringCode;
						try {
							stringCode = result.getString("code");
							if (stringCode.equals("10000")) {
								Map<String, String> paMap = new HashMap<String, String>();
								paMap.put("is_pay_passwd", "false");
								BoluoUtils.getShareCommit(
										getApplicationContext(), paMap);
								Toast.makeText(context, "密码设置成功",
										Toast.LENGTH_SHORT).show();
								getPay();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}
}
