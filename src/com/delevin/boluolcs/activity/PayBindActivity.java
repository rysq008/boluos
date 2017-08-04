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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.LianlianPay;
import com.delevin.boluolcs.utils.NetUtils;
import com.delevin.boluolcs.utils.LianlianPay.GetCodeCallBack;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.TitleView.OnRightButtonClickListener;
import com.delevin.jsandroid.JSAndroidActivity;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-3 下午3:22:18    类说明 绑定充值
 */
public class PayBindActivity extends BaseActivity implements OnClickListener {
	private TextView txtBankName;
	private TextView txtBankCode;
	private EditText money;
	private Button bt_bind_pay;
	private String real_name;
	private String phone;
	private String strIdCard;
	private ImageView imgBank;
	private TextView tvSeeXiane;
	private LinearLayout linLater;
	private ImageView imgLater;
	private String bankCard;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		setContentView(R.layout.activity_pay_bind);
		View statusBarview = findViewById(R.id.statusBarview);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_payBind);
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");

		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(true, true, true, true);
		titleView.setAppTitle("账户充值");
		titleView.setRightTitle("充值记录");
		titleView
				.setOnRightButtonClickListener(new OnRightButtonClickListener() {
					@Override
					public void OnRightButtonClick(View v) {
						startActivity(new Intent(getApplicationContext(),
								ChongzhiJiluActivity.class));
					}
				});
		txtBankName = (TextView) findViewById(R.id.add_pay_tv_bank_name);
		txtBankCode = (TextView) findViewById(R.id.add_pay_tv_bank_id);
		money = (EditText) findViewById(R.id.pay_bind_money);
		AndroidUtils.setPricePoint(money);
		bt_bind_pay = (Button) findViewById(R.id.bt_pay_bind);
		imgBank = (ImageView) findViewById(R.id.add_pay_img_bank);
		tvSeeXiane = (TextView) findViewById(R.id.add_pay_tv_see_xiane);
		linLater = (LinearLayout) findViewById(R.id.pay_bind_lin_later);
		imgLater = (ImageView) findViewById(R.id.pay_bind_img_later);
		bt_bind_pay.setOnClickListener(this);
		tvSeeXiane.setOnClickListener(this);
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
								JSONObject object = result
										.getJSONObject("content");
								txtBankName.setText(object
										.getString("bank_name"));
								strIdCard = object.getString("id_card");
								bankCard = object.getString("pay_bankcard");
								txtBankCode.setText(QntUtils
										.getBankCode(bankCard));
								real_name = object.getString("real_name");
								String bank_address = object
										.getString("bank_address");
								AndroidUtils.getImg(getApplicationContext(),
										bank_address, imgBank,
										R.drawable.boluo_center,
										R.drawable.boluo_fail);
							}
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}

				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_pay_bind:

			if (NetUtils.getNetWorkState(PayBindActivity.this) != -1) {
				if (!TextUtils.isEmpty(real_name)) {
					if (!TextUtils.isEmpty(txtBankCode.getText().toString()
							.trim())) {
						if (!TextUtils.isEmpty(money.getText().toString()
								.trim())) {
							if (QntUtils.getDouble(money.getText().toString()
									.trim()) >= 1D) {
								LianlianPay.getPay(PayBindActivity.this, phone,
										bankCard, strIdCard, real_name, money
												.getText().toString().trim(),
										imgLater, linLater,
										new GetCodeCallBack() {
											@Override
											public void onResponse(String msg,
													String code, boolean b) {
												if (b) {
													Intent intent = new Intent(
															PayBindActivity.this,
															PayOrTianSuccessActivity.class);
													intent.putExtra("money",
															money.getText()
																	.toString());
													intent.putExtra("flag",
															"pay");
													startActivity(intent);
													finish();
												}
											}
										});
							} else {
								Toast.makeText(getApplicationContext(),
										"充值不能小于1元", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getApplicationContext(), "请输入充值金额",
									Toast.LENGTH_SHORT).show();
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
				BoluoUtils.getDilogDome(PayBindActivity.this, "温馨提示",
						"您当前的网络不可用", "确定");
			}
			break;
		case R.id.add_pay_tv_see_xiane:

			Intent intent = new Intent(PayBindActivity.this,
					JSAndroidActivity.class);
			intent.putExtra("title", "查看限额");
			intent.putExtra("jsUrl", BeanUrl.addPayChakanXiane + phone);
			startActivity(intent);

			break;

		default:
			break;
		}
	}

	class OnRightCallBack implements OnRightButtonClickListener {
		@Override
		public void OnRightButtonClick(View v) {
			Toast.makeText(getApplicationContext(), "充值记录", Toast.LENGTH_SHORT)
					.show();

		}
	}
}
