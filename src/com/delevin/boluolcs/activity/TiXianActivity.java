package com.delevin.boluolcs.activity;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.interfaces.DefultDilogCallBack;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.NetUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.TitleView.OnRightButtonClickListener;
import com.delevin.jsandroid.JSAndroidActivity;
import com.delevin.sorfkeyboard.SetPassword;
import com.delevin.sorfkeyboard.SetPassword.SetPwdCallback;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-3 下午4:09:54    类说明 
 */
public class TiXianActivity extends BaseActivity implements OnClickListener {

	private TextView txtBankCode;
	private TextView txtRemain_balance;
	private EditText money;
	private Button bt_tixian;
	private String bank_code;
	private String phone;
	private ImageView imgBank;
	private TextView tvBank, tvQuchuGuize;
	private String strBankName;
	private RelativeLayout rlYueGoneOr, rlGone;
	private String aa = "1";
	private TextView tvYI, tvWei, tvShouxufei, tvShiti;
	private String weitou, yitou, strZhanghuyue, tixian;
	private ImageView imgGone;
	public static TiXianActivity tiXianActivity;
	private ImageView imgLater;
	private LinearLayout linLater;
	private String pay_bankcard;
	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		setContentView(R.layout.activity_tixian);
		tiXianActivity = this;
		TitleView titleView = (TitleView) findViewById(R.id.titleView_tixian);
		View statusBarview = findViewById(R.id.statusBarview);
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(true, true, true, true);
		titleView.setAppTitle("提现");
		titleView.setRightTitle("提现记录");

		titleView
				.setOnRightButtonClickListener(new OnRightButtonClickListener() {
					@Override
					public void OnRightButtonClick(View v) {
						// 提现记录
						startActivity(new Intent(getApplicationContext(),
								TixianJiluActivity.class));
					}
				});

		txtBankCode = (TextView) findViewById(R.id.tixian_bankCode);
		txtRemain_balance = (TextView) findViewById(R.id.tixian_remain_balance);
		money = (EditText) findViewById(R.id.tixian_money);
		AndroidUtils.setPricePoint(money);
		bt_tixian = (Button) findViewById(R.id.bt_tixian);
		imgBank = (ImageView) findViewById(R.id.tixian_img_bank_icon);
		tvBank = (TextView) findViewById(R.id.tixian_tv_bank);
		tvQuchuGuize = (TextView) findViewById(R.id.tixian_tv_quchuguize);
		rlYueGoneOr = (RelativeLayout) findViewById(R.id.tixian_relativeLayout_zhanghuyue);
		rlGone = (RelativeLayout) findViewById(R.id.tixian_rl_gone);
		tvYI = (TextView) findViewById(R.id.tixian_tv_yitouzi_money);
		tvWei = (TextView) findViewById(R.id.tixian_tv_weitouzi_money);
		tvQuchuGuize.setOnClickListener(this);
		bt_tixian.setOnClickListener(this);
		rlYueGoneOr.setOnClickListener(this);
		tvShouxufei = (TextView) findViewById(R.id.tixian_tv_shouxufei);
		tvShiti = (TextView) findViewById(R.id.tixian_tv_shiti);
		imgGone = (ImageView) findViewById(R.id.tixian_img_gone);
		imgLater = (ImageView) findViewById(R.id.tixian_img_later);
		linLater = (LinearLayout) findViewById(R.id.tixian_lin_later);
	}
	@Override
	protected void getData() {
		Myapplication.okhttpManger.sendComplexForm(this, false,
				QntUtils.getURL(BeanUrl.TIXIAN_INIT_STRING, phone), null,
				new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								JSONObject object = result.getJSONObject("content");
								pay_bankcard = object.getString("pay_bankcard");
								txtBankCode.setText(QntUtils.getBankCode(pay_bankcard));
								strZhanghuyue = object.getString("remain_balance");
								txtRemain_balance.setText(QntUtils.formateRate(strZhanghuyue) + " 元");
								bank_code = object.getString("bank_code");
								String bank_address = object.getString("bank_address");
								AndroidUtils.getImg(getApplicationContext(),bank_address, imgBank,R.drawable.boluo_center,R.drawable.boluo_fail);
								strBankName = object.getString("bank_name");
								tvBank.setText(strBankName);
								yitou = object.getString("remainamount");// yitou
								weitou = object.getString("rechargeamount");
								tvYI.setText(QntUtils.formateRate(yitou)+ "(元)");
								tvWei.setText(QntUtils.formateRate(weitou)+ "(元)");
								shitiMoney();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});
	}

	private void shitiMoney() {
		// TODO Auto-generated method stub
		money.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

				if (!TextUtils.isEmpty(money.getText().toString())) {
					tixian = money.getText().toString();
					double dTixian = QntUtils.getDouble(tixian);
					double dYi = QntUtils.getDouble(yitou);
					double dZhanghuyue = QntUtils.getDouble(strZhanghuyue);
					double dShouxufei = ((dTixian - dYi) * 0.01);
					if (dTixian < 100) {
						tvShiti.setText("提现金额不得小于100元");
					} else if (dTixian > dZhanghuyue) {
						tvShiti.setText("余额不足");
					} else {
						if (dTixian <= dYi && dTixian <= dZhanghuyue) {
							tvShouxufei.setText("0.00" + "元");
							tvShiti.setText(QntUtils.formateRate(dTixian + "")
									+ "(元)");
						} else if (dTixian > dYi
								&& (dTixian + dShouxufei) <= dZhanghuyue) {
							tvShouxufei.setText(QntUtils.getFormat(dShouxufei)
									+ "(元)");
							tvShiti.setText(QntUtils.getFormat(dTixian) + "(元)");
						} else if (dTixian > dYi
								&& (dTixian + dShouxufei) > dZhanghuyue) {
							tvShouxufei.setText(QntUtils.getFormat(dShouxufei)
									+ "(元)");
							if (dTixian == dZhanghuyue) {
								tvShiti.setText(QntUtils.getFormat(dTixian
										- dShouxufei)
										+ "(元)");
							} else {
								double dShiti2 = dTixian
										- (dShouxufei - (dZhanghuyue - dTixian));
								tvShiti.setText(QntUtils.getFormat(dShiti2)
										+ "(元)");
							}
						}
					}
				} else {
					tvShouxufei.setText("未投资金额*0.01%(元)");
					tvShiti.setText("0(元)");
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_tixian:
			if (NetUtils.getNetWorkState(TiXianActivity.this) != -1) {
				if (!TextUtils.isEmpty(money.getText().toString())) {
					if (QntUtils.getDouble(money.getText().toString()) >= 100) {
						tixian = money.getText().toString();
						double dTixian = QntUtils.getDouble(tixian);
						double dYi = QntUtils.getDouble(yitou);
						double dZhanghuyue = QntUtils.getDouble(strZhanghuyue);
						double dShouxufei = ((dTixian - dYi) * 0.01);
						if (dTixian > dZhanghuyue) {
							Toast.makeText(getApplicationContext(), "余额不足",
									Toast.LENGTH_SHORT).show();
						} else {
							if (dTixian <= dYi && dTixian <= dZhanghuyue) {
								SetPassword.getJIanpan(new SetPwdCallback() {

									@Override
									public void onPwd(String pwd) {
										commitData(pwd);
									}

									@Override
									public void onTextView(TextView t) {
										t.setText("提现金额:"
												+ money.getText().toString()
												+ "元");
									}
								}, TiXianActivity.this, "请输入密码");
							} else if (dTixian > dYi
									&& (dTixian + dShouxufei) <= dZhanghuyue) {
								BoluoUtils.getDilogDefult(
										TiXianActivity.this,
										true,
										"提现",
										"账户余额支付提现手续费"
												+ QntUtils
														.getFormat(dShouxufei)
												+ "元，当前提现金额为"
												+ QntUtils.getFormat(dTixian)
												+ "元", "提现", "取消",
										new DefultDilogCallBack() {
											@Override
											public void onPositiveButton() {
												SetPassword.getJIanpan(
														new SetPwdCallback() {
															@Override
															public void onPwd(
																	String pwd) {
																commitData(pwd);
															}

															@Override
															public void onTextView(
																	TextView t) {
																t.setText("提现金额:"
																		+ money.getText()
																				.toString()
																		+ "元");
															}
														}, TiXianActivity.this,
														"请输入密码");
											}

											@Override
											public void onNegativeButton() {

											}
										});
							} else if (dTixian > dYi
									&& (dTixian + dShouxufei) > dZhanghuyue) {
								if (dTixian == dZhanghuyue) {
									BoluoUtils
											.getDilogDefult(
													TiXianActivity.this,
													true,
													"提现",
													"账户余额不足支付提现手续费"
															+ QntUtils
																	.getFormat(dShouxufei)
															+ "元，当前提现金额为"
															+ QntUtils
																	.getFormat(dTixian
																			- dShouxufei)
															+ "元", "提现", "取消",
													new DefultDilogCallBack() {
														@Override
														public void onPositiveButton() {
															SetPassword
																	.getJIanpan(
																			new SetPwdCallback() {

																				@Override
																				public void onPwd(
																						String pwd) {
																					commitData(pwd);
																				}

																				@Override
																				public void onTextView(
																						TextView t) {
																					t.setText("提现金额:"
																							+ money.getText()
																									.toString()
																							+ "元");

																				}
																			},
																			TiXianActivity.this,
																			"请输入密码");
														}

														@Override
														public void onNegativeButton() {

														}
													});
								} else {
									double dShiti2 = dTixian
											- (dShouxufei - (dZhanghuyue - dTixian));
									BoluoUtils
											.getDilogDefult(
													TiXianActivity.this,
													true,
													"提现",
													"账户余额不足支付提现手续费"
															+ QntUtils
																	.getFormat(dShouxufei)
															+ "元，当前提现金额为"
															+ QntUtils
																	.getFormat(dShiti2)
															+ "元", "提现", "取消",
													new DefultDilogCallBack() {

														@Override
														public void onPositiveButton() {
															SetPassword
																	.getJIanpan(
																			new SetPwdCallback() {

																				@Override
																				public void onPwd(
																						String pwd) {
																					commitData(pwd);
																				}

																				@Override
																				public void onTextView(
																						TextView t) {
																					t.setText("提现金额:"
																							+ money.getText()
																									.toString()
																							+ "元");

																				}
																			},
																			TiXianActivity.this,
																			"请输入密码");
														}

														@Override
														public void onNegativeButton() {

														}
													});
								}

							}
						}
					} else {
						Toast.makeText(getApplicationContext(), "提现不能小于100",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "请输入提现金额",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				BoluoUtils.getDilogDome(TiXianActivity.this, "温馨提示",
						"您当前的网络不可用", "确定");
			}
			break;
		case R.id.tixian_tv_quchuguize:
			Intent intent = new Intent(TiXianActivity.this,
					JSAndroidActivity.class);
			intent.putExtra("title", "提现规则");
			intent.putExtra("jsUrl", BeanUrl.TixianGuize);
			startActivity(intent);
			break;
		case R.id.tixian_relativeLayout_zhanghuyue:
			if (aa.equals("1")) {
				rlGone.setVisibility(View.VISIBLE);
				imgGone.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_down));
				aa = "2";
			} else if (aa.equals("2")) {
				rlGone.setVisibility(View.GONE);
				imgGone.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_right));
				aa = "1";
			}
			break;
		default:
			break;
		}
	}

	private void commitData(String password) {
		// 显示
		linLater.setVisibility(View.VISIBLE);
		ProessDilogs.getProessAnima(imgLater, TiXianActivity.this);
		Map<String, String> params = new HashMap<String, String>();
		params.put("bank_code", bank_code);
		params.put("bank_card", pay_bankcard);
		params.put("reflect_money", money.getText().toString());
		params.put("pay_passwd", password);
		Myapplication.okhttpManger.sendComplexForm(this, false,
				QntUtils.getURL(BeanUrl.TIXIAN_STRING, phone), params,
				new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						try {
							if (TextUtils.equals(result.getString("code"),
									"10000")) {
								// 取消and隐藏
								ProessDilogs.closeAnimation(imgLater, linLater);
								linLater.setVisibility(View.GONE);
								Intent intent = new Intent(TiXianActivity.this,
										PayOrTianSuccessActivity.class);
								intent.putExtra("money", money.getText()
										.toString());
								intent.putExtra("flag", "tixian");
								startActivity(intent);
								finish();
								Toast.makeText(getApplicationContext(),
										result.getString("desc"),
										Toast.LENGTH_SHORT).show();
							} else {
								// 取消and隐藏
								ProessDilogs.closeAnimation(imgLater, linLater);
								linLater.setVisibility(View.GONE);
								Toast.makeText(getApplicationContext(),
										result.getString("desc"),
										Toast.LENGTH_SHORT).show();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

}
