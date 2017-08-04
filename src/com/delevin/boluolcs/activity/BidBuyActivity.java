package com.delevin.boluolcs.activity;

import java.util.HashMap;
import java.util.Map;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.fragmentactivity.FilterFragmentActivity;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.LianlianPay;
import com.delevin.boluolcs.utils.LianlianPay.GetCodeCallBack;
import com.delevin.boluolcs.utils.NetUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.RoundProgressBar;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.jsandroid.JSAndroidActivity;
import com.delevin.sorfkeyboard.SetPassword;
import com.delevin.sorfkeyboard.SetPassword.SetPwdCallback;
import com.pusupanshi.boluolicai.R;

public class BidBuyActivity extends BaseActivity implements OnClickListener {

	private ImageView bankIcon; // 银行icon
	private TextView bankName; // 银行名称
	private TextView bankNumber; // 银行卡号
	private TextView buyMoneyView;// 购买金额
	private LinearLayout redLayout; // 筛选红包
	private TextView redMoneyView;// 红包金额
	private TextView balanceMoneyView;// 剩余金额
	private TextView zhimoneyView;// 支付金额
	private Button bt_buy;// 购买按钮
	private LinearLayout visi_layout;// 圆圈
	private ImageView visi_img; // 圆圈图片
	private CheckBox checkBox;
	private String redId; // 红包Id
	private String redMoney; // 红包金额
	private final static int BIAO = 1;// 红包跳转标记
	private boolean isNewer;
	private String bidMoney;
	private String bidId;
	private Boolean newerOrIs;// 是否是新手
	private String balance;
	private String bank_number;
	private String id_card;
	private String real_name;
	private String phone;
	private String result_name;
	private double rates;
	private String time_limit;
	private String product_remain;
	private boolean CheckedIs = true;
	private TextView productName;
	private TextView productBidOne;
	private TextView productBidTwo;
	private TextView productTime;
	private RoundProgressBar chartView; // 圆
	private ImageView weitoubt;
	private TextView txtWeituoShu;
	private boolean isChecked = true;
	private LinearLayout layoutJiaXi;
	private LinearLayout layoutBank;
	private String red_rate;
	private String type;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {

		setContentView(R.layout.activity_buy_bid);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_bidBuy);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("确认购买");
		init();
		getShareData();
		getIntentString();
	}

	private void getShareData() {
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
	}

	/**
	 * 控件初始化
	 * */
	@SuppressWarnings("deprecation")
	private void init() {
		
		layoutBank = (LinearLayout) findViewById(R.id.bid_buy__MyBank);
		layoutBank.setOnClickListener(this);
		layoutJiaXi = (LinearLayout) findViewById(R.id.bid_buy_jiaxijuan);
		layoutJiaXi.setOnClickListener(this);
		txtWeituoShu = (TextView) findViewById(R.id.weituoshu_txt_bt);
		txtWeituoShu.setOnClickListener(this);
		weitoubt = (ImageView) findViewById(R.id.weituoshu_check);
		weitoubt.setBackgroundResource(R.drawable.boluo_img_register_ok);
		weitoubt.setOnClickListener(this);
		chartView = (RoundProgressBar) findViewById(R.id.PieCharViewBuy);
		productName = (TextView) findViewById(R.id.buy_name);
		productBidOne = (TextView) findViewById(R.id.buy_bidOne);
		productBidTwo = (TextView) findViewById(R.id.buy_bidTwo);
		productTime = (TextView) findViewById(R.id.buy_time);
		bankIcon = (ImageView) findViewById(R.id.bid_buy_bankIcon);
		bankName = (TextView) findViewById(R.id.bid_buy_BankName);
		bankNumber = (TextView) findViewById(R.id.bid_buy_BankCard);
		buyMoneyView = (TextView) findViewById(R.id.bid_buy_BuyMoney);
		redLayout = (LinearLayout) findViewById(R.id.bid_buy_redLayout);
		redMoneyView = (TextView) findViewById(R.id.bid_buy_redPacketMoney);
		balanceMoneyView = (TextView) findViewById(R.id.bid_buy_balanceMoney);
		zhimoneyView = (TextView) findViewById(R.id.bid_buy_zhiMoney);
		bt_buy = (Button) findViewById(R.id.bid_buy_bt);
		bt_buy.setOnClickListener(this);
		checkBox = (CheckBox) findViewById(R.id.bid_buy_checkbox);
		redLayout.setOnClickListener(this);
		checkBox.setOnCheckedChangeListener(new checkBoxListener());
		visi_layout = (LinearLayout) findViewById(R.id.bid_buy_visibility_layout);
		visi_img = (ImageView) findViewById(R.id.bid_buy_visibility_image);
		bt_buy.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_bt_boluoyellow));
	}

	/**
	 * shard取值
	 * */
	private void getIntentString() {

		isNewer = getIntent().getBooleanExtra("isNewer", false);
		bidId = getIntent().getStringExtra("bidId").replace("-", "");
		bidMoney = getIntent().getStringExtra("buyMoney");
		buyMoneyView.setText(QntUtils.getFormat(QntUtils.getDouble(bidMoney))+ "元");
	}
	/**
	 * 数据请求
	 * */
	@Override
	protected void getData() {
		visi_layout.setVisibility(View.VISIBLE);
		ProessDilogs.getProessAnima(visi_img, getApplicationContext());
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", phone);
		params.put("money", bidMoney);
		params.put("product_id", bidId);
		Myapplication.okhttpManger.sendComplexForm(this, false,BeanUrl.BIDBUYINIT_STRING, params,new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						try {
							ProessDilogs.closeAnimation(visi_img, visi_layout);
							JSONObject object = result.getJSONObject("content");
							type = object.getString("type");
							String bank_name = object.getString("bank_name");// 银行名称
							id_card = object.getString("id_card");
							real_name = object.getString("real_name");
							bank_number = object.getString("bank_number");// 银行卡号
							double rate_increase = object.getDouble("rate_increase");
							double rate = object.getDouble("rate");//
							result_name = object.getString("product_name");
							rates = rate + rate_increase;
							time_limit = object.getString("time_limit");
							String bank_address = object.getString("bank_address");// 银行icon
							product_remain = object.getString("product_remain");// 余额
							String total_mount = object.getString("total_mount");
							float total_mountF = QntUtils.getFloat(total_mount);
							float product_remainF = QntUtils.getFloat(product_remain);
							float weiMonut = total_mountF - product_remainF;
							int toInt = QntUtils.getFloatToInt((weiMonut/ total_mountF * 100));
							chartView.setProgress(toInt);
//							String bank_code = object.getString("bank_code");// 银行code
							String day_amt = object.getString("day_amt");// 单日
							String single_amt = object.getString("single_amt");// 单笔
							balance = QntUtils.getFormat(QntUtils.getDouble(object.getString("balance")));// 余额
							redId = object.getString("red_id");// 红包Id;
							redMoney = object.getString("red_money");// 红包金额
							red_rate = object.getString("red_rate");// jiaxi金额
							newerOrIs = object.getBoolean("is_new_member");// 是否是新手
							object.getString("money");
							String bankNumber = QntUtils.getSubStringW(bank_number, 0, 4)+ "****"+ QntUtils.getSubStringW(bank_number,bank_number.length() - 4,bank_number.length());
							getLayoutInit(type, red_rate, bankNumber,bank_address, bank_name, day_amt, single_amt,balance, redMoney);
							productName.setText(result_name);
							productBidOne.setText(QntUtils.getDoubleToInt(rate * 100) + "");
							productBidTwo.setText(QntUtils.getDoubleToInt(rate_increase * 100) + "%");
							productTime.setText(time_limit);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void getLayoutInit(String type, String red_rate,String bank_number, String bank_address, String bank_name,String day_amt, String single_amt, String balance, String redMoney) {

		bankName.setText(bank_name);

		bankNumber.setText(bank_number);

		AndroidUtils.getImg(getApplicationContext(), bank_address, bankIcon, R.drawable.boluo_center, R.drawable.boluo_fail);

		balanceMoneyView.setText(balance);

		if (!isNewer) {
			if (TextUtils.equals(type, "0")) {
				if (!TextUtils.isEmpty(redMoney)) {
					redMoneyView.setText("可用红包" + redMoney + "元");
				} else {
					redMoneyView.setText("暂无优惠券");
				}
			} else {
				if (!TextUtils.isEmpty(red_rate)) {
					redMoneyView.setText("可用加息卷"+ QntUtils.getFormat(QntUtils.getDouble(red_rate) * 100) + "%");
				} else {
					redMoneyView.setText("暂无优惠券");
				}
			}
			double remainMoney = QntUtils.getDouble(product_remain);
			double buyMoneyD = QntUtils.getDouble(bidMoney);
			if (remainMoney == buyMoneyD) {
				BoluoUtils.getZhiFuMoney(bidMoney, "0", balance, zhimoneyView);

			}else {
				
				BoluoUtils.getZhiFuMoney(bidMoney, redMoney, balance, zhimoneyView);
			}

		} else {

			redMoney = "0";

			BoluoUtils.getZhiFuMoney(bidMoney, redMoney, balance, zhimoneyView);
			redMoneyView.setText("新手不能使用优惠券");
			redLayout.setFocusable(false);
		}
	}

	class checkBoxListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			CheckedIs = isChecked;
			if (isNewer) {
				redMoney = "0";
			}
			if (isChecked) {
				double remainMoney = QntUtils.getDouble(product_remain);
				double buyMoneyD = QntUtils.getDouble(bidMoney);
				if (TextUtils.equals(type, "0")) {
					if (remainMoney == buyMoneyD) {
						BoluoUtils.getZhiFuMoney(bidMoney, "0", balance,
								zhimoneyView);
					}else {
						
						BoluoUtils.getZhiFuMoney(bidMoney, redMoney, balance,
								zhimoneyView);
					}
				} else {
					BoluoUtils.getZhiFuMoney(bidMoney, "0", balance,
							zhimoneyView);
				}

			} else {
				if (TextUtils.equals(type, "0")) {
					double remainMoney = QntUtils.getDouble(product_remain);
					double buyMoneyD = QntUtils.getDouble(bidMoney);
					if (remainMoney == buyMoneyD) {
						BoluoUtils.getPayZhiFuMoney(bidMoney, "0",
								zhimoneyView);
					}else{
						BoluoUtils.getPayZhiFuMoney(bidMoney, redMoney,
								zhimoneyView);
					}
					
				} else {
					BoluoUtils.getPayZhiFuMoney(bidMoney, "0", zhimoneyView);
				}
			}
		}
	}

	/**
	 * 监听
	 * */
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.bid_buy_bt:
			if (NetUtils.getNetWorkState(BidBuyActivity.this) != -1) {
				if (isNewer) {
					if (!newerOrIs) {
						Toast.makeText(getApplicationContext(), "您已不是新手，请重新投标",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}
				String zhimoney = zhimoneyView.getText().toString().trim();
				if (!TextUtils.equals(zhimoney, "0")) {
					getpay(bank_number, id_card, real_name, zhimoney);
				} else {
					SetPassword.getJIanpan(new SetPwdCallback() {

						@Override
						public void onPwd(String pwd) {
							getBuyBid(pwd);
						}

						@Override
						public void onTextView(TextView t) {
							t.setText("投资金额:" + bidMoney + "元");
						}
					}, BidBuyActivity.this, "请输入密码");
				}
			} else {
				BoluoUtils.getDilogDome(BidBuyActivity.this, "温馨提示","您当前的网络不可用", "确定");
			}
			break;
		case R.id.bid_buy_redLayout:
			if (NetUtils.getNetWorkState(BidBuyActivity.this) != -1) {
				if (!TextUtils.equals(redMoneyView.getText().toString(),
						"暂无优惠券")) {
					if (!TextUtils.equals(redMoneyView.getText().toString(),"新手不能使用优惠券")) {
						Intent intent = new Intent(BidBuyActivity.this,FilterFragmentActivity.class);
						intent.putExtra("start_money", buyMoneyView.getText().toString());
						startActivityForResult(intent, BIAO);
					} else {
						Toast.makeText(getApplicationContext(), "新手不能使用优惠券",Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "您没有优惠券可用",Toast.LENGTH_SHORT).show();
				}
			} else {
				BoluoUtils.getDilogDome(BidBuyActivity.this, "温馨提示","您当前的网络不可用", "确定");
			}
			break;
		case R.id.weituoshu_txt_bt:
			Intent intent = new Intent(BidBuyActivity.this,JSAndroidActivity.class);
			intent.putExtra("title", "委托授权书");
			intent.putExtra("jsUrl", BeanUrl.WEITUOXIEYISHU_STRING + phone + "/" + result_name);
			startActivity(intent);
			break;
		case R.id.weituoshu_check:
			if (isChecked) {
				// bt_hui.setVisibility(View.VISIBLE);
				bt_buy.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.shape_gray));
				bt_buy.setEnabled(false);
				weitoubt.setBackgroundResource(R.drawable.boluo_img_register_no);
				isChecked = false;
			} else {
				bt_buy.setVisibility(View.VISIBLE);
				// bt_hui.setVisibility(View.GONE);
				bt_buy.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_bt_boluoyellow));
				weitoubt.setBackgroundResource(R.drawable.boluo_img_register_ok);
				isChecked = true;
			}
			break;
		case R.id.bid_buy_jiaxijuan:
			break;
		case R.id.bid_buy__MyBank:
			if (NetUtils.getNetWorkState(BidBuyActivity.this) != -1) {
				startActivity(new Intent(BidBuyActivity.this,MyBankActivity.class));
			} else {
				BoluoUtils.getDilogDome(BidBuyActivity.this, "温馨提示","您当前的网络不可用", "确定");
			}
			break;
		default:
			break;
		}
	}

	// 支付
	public void getpay(String bankCode, String idCode, String real_name,
			String money) {
		LianlianPay.getPay(this, phone, bankCode, idCode, real_name, money,
				visi_img, visi_layout, new GetCodeCallBack() {
					@Override
					public void onResponse(String msg, String code, boolean b) {
						if (b) {
							getBuyBid("");
						}
					}
				});
	}

	// 购买
	private void getBuyBid(String pwd) {
		visi_layout.setVisibility(View.VISIBLE);
		ProessDilogs.getProessAnima(visi_img, getApplicationContext());
		double remainMoney = QntUtils.getDouble(product_remain);
		double buyMoneyD = QntUtils.getDouble(bidMoney);
		Map<String, String> params = new HashMap<String, String>();
		if (!isNewer) {
			if (!TextUtils.isEmpty(redId)) {
				if (TextUtils.equals(type, "0")) {
					if (remainMoney == buyMoneyD) {
						params.put("red_pocket_id", redId); // 红包id
						params.put("total_fee", bidMoney); // 购买金额
					}else{
					params.put("red_pocket_id", redId); // 红包id
					String money = QntUtils.getFormatOne(QntUtils.getDouble(bidMoney)- QntUtils.getDouble(redMoney));
					params.put("total_fee", money); // 购买金额
					}
				} else {
					params.put("red_pocket_id", redId); // 红包id
					params.put("total_fee", bidMoney); // 购买金额
				}

			} else {
				params.put("total_fee", bidMoney); // 购买金额
			}
		} else {
			if (!newerOrIs) {
				Toast.makeText(getApplicationContext(), "您已不是新手，请重新投标",Toast.LENGTH_SHORT).show();
			} else {
				params.put("total_fee", bidMoney); // 购买金额
			}
		}

		params.put("pay_passwd", pwd);
		params.put("is_new_product", "false");
		params.put("product_id", bidId); // 产品id
		// params.put("total_fee", bidMoney); // 购买金额
		Myapplication.okhttpManger.sendComplexForm(this, true,
				QntUtils.getURL(BeanUrl.BUY_BID_STRING, phone), params,
				new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								ProessDilogs.closeAnimation(visi_img,
										visi_layout);
								Intent intent = new Intent(BidBuyActivity.this,
										BidSuccessActivity.class);
								BidDetalsActivity.bidDetalsActivity.finish();
								intent.putExtra("limitTime", time_limit);
								intent.putExtra("buyMoney", bidMoney);
								intent.putExtra("rate", rates + "");
								intent.putExtra("product_name", result_name);
								intent.putExtra("bidId", bidId);
								startActivity(intent);
								finish();
							} else {
								ProessDilogs.closeAnimation(visi_img,
										visi_layout);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 1:
			type = data.getStringExtra("type");
			double remainMoney = QntUtils.getDouble(product_remain);
			double buyMoneyD = QntUtils.getDouble(bidMoney);
				
			if (TextUtils.equals(type, "0")) {
				redId = data.getStringExtra("redId");
				redMoney = data.getStringExtra("redMoney");
				redMoneyView.setText("可使用红包" + redMoney + "元");
				if (CheckedIs) {
					if (remainMoney == buyMoneyD) {
						
						BoluoUtils.getZhiFuMoney(bidMoney, "0", balance,
								zhimoneyView);
					}else{
						
						BoluoUtils.getZhiFuMoney(bidMoney, redMoney, balance,
								zhimoneyView);
						
					}
					
				} else {
					BoluoUtils.getPayZhiFuMoney(bidMoney, redMoney,
							zhimoneyView);
				}
			} else {
				redId = data.getStringExtra("redId");
				redMoney = data.getStringExtra("redMoney");
				redMoneyView.setText("可使用加息卷" + QntUtils.getDouble(redMoney)* 100 + "%");
				if (CheckedIs) {
					BoluoUtils.getZhiFuMoney(bidMoney, "0", balance,
							zhimoneyView);
				} else {
					BoluoUtils.getPayZhiFuMoney(bidMoney, "0", zhimoneyView);
				}
			}

			break;
		default:
			break;
		}
	}
}
