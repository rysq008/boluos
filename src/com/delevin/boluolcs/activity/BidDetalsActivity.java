package com.delevin.boluolcs.activity;

import java.util.ArrayList;
import com.delevin.boluolcs.view.YellowRoundProgressBar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanProductDetals;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.denglu.ZhuActivity;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.NetUtils;
import com.delevin.boluolcs.utils.PopWindowUtils;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.jsandroid.JSAndroidActivity;
import com.pusupanshi.boluolicai.R;
/**
 *     @author 李红涛  @version 创建时间：2016-12-28 下午3:06:58    类说明 
 */
public class BidDetalsActivity extends BaseActivity implements OnClickListener,
		TextWatcher {
	private PopupWindow popupWindow;
	private LinearLayout visi_layout;// 动画布局
	private ImageView visi_image;// 动画图片
	private String bidId;// 产品ID
	private List<BeanProductDetals> productDetalsList;
	private TextView titleTextView;
	private TextView startmoneyTextView;// 起投金额
	private TextView limitDayTextView;// 限制天数
	private TextView totalMoneyTextView;// 融资金额
	private TextView remianMoneyTextView;// 剩余金额
	private TextView limitTotalMoneyTextView;
	private YellowRoundProgressBar chartView;
	private Button bt_bid;
	private boolean isNewer;// 点击新手标
	private EditText etBidMoney;// 投资金额
	private String rate;
	private String phone;
	private String memberId;
	private String pay_bind;
	private Button imgJiSuanqi;
	private View popWindowlayout;
	private LinearLayout btLayout;
	private EditText addView;
	private TextView remainView;
	private TextView shouyiView;
	public static BidDetalsActivity bidDetalsActivity;
	private Handler mHandler = new Handler();
	private ScrollView mScrollView;
	private TextView tvRate;
	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_bid_detals);
		bidDetalsActivity = this;
		TitleView titleView = (TitleView) findViewById(R.id.titleView_bidDetals);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("项目详情");
		getShareData();
		getLayoutJisuanqi();
		init();
		getIntentString();
	}
	@SuppressLint("InflateParams")
	private void getLayoutJisuanqi() {
		mScrollView = (ScrollView) findViewById(R.id.scroll);
		popWindowlayout = getLayoutInflater().inflate(R.layout.touzi_detals_layout_jisuanqi, null);
		addView = (EditText) popWindowlayout.findViewById(R.id.touzi_detals_addMoney);
		addView.addTextChangedListener(this);
		remainView = (TextView) popWindowlayout.findViewById(R.id.touzi_detals_remainMoney);
		shouyiView = (TextView) popWindowlayout.findViewById(R.id.touzi_detals_shoyiMoney);
		ImageView disView = (ImageView) popWindowlayout.findViewById(R.id.touzi_detals_dissmiss);
		Button bt_bid = (Button) popWindowlayout.findViewById(R.id.touzi_detals_bid);
		
		if (memberId == null) {
			bt_bid.setText("立即登录");
		}
		
		Button btJian = (Button) popWindowlayout.findViewById(R.id.touzi_detals_jian);
		Button btJia = (Button) popWindowlayout.findViewById(R.id.touzi_detals_jia);
		btJia.setOnClickListener(this);
		btJian.setOnClickListener(this);
		bt_bid.setOnClickListener(this);
		disView.setOnClickListener(this);
	}
	private void getShareData() {
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		memberId = BoluoUtils.getShareOneData(getApplicationContext(),"memberId");
		pay_bind = BoluoUtils.getShareOneData(getApplicationContext(),"pay_bind");
	}
	// 数据解析
	@Override
	protected void getData() {
		ProessDilogs.getProessAnima(visi_image, this);
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", phone);
		if (TextUtils.isEmpty(phone)) {
			params = null;
		}
		Myapplication.okhttpManger.sendComplexForm(this, false,BeanUrl.PRODUCT_DETALS_STRING + bidId, params, new Funck4() {
					@SuppressWarnings("deprecation")
					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								JSONObject object = result.getJSONObject("content");
								BeanProductDetals detals = new BeanProductDetals();
								detals.setLimit_mount(object.getString("limit_mount"));
								detals.setPercentage(object.getString("percentage"));
								detals.setSell_time(object.getString("sell_time"));
								detals.setProduct_detail(object.getString("product_detail"));
								detals.setProduct_image(object.getString("product_image"));
								detals.setProduct_invest(object.getString("product_invest"));
								detals.setProduct_name(object.getString("product_name"));
								detals.setProduct_status(object.getString("product_status"));
								detals.setProduct_type(object.getString("product_type"));
								detals.setRaise_limit(object.getString("raise_limit"));
								detals.setRate(object.getDouble("rate") + "");
								detals.setRate_increase(object.getString("rate_increase"));
								double remain_money = object.getDouble("remain_money");
								if (!BoluoUtils.isEmpty(memberId)) {
									if (!TextUtils.equals(detals.getProduct_status(), "2")) {
										etBidMoney.setVisibility(View.GONE);
									}
									BoluoUtils.getStatusZhiJiao(getApplicationContext(),detals.getProduct_status(), bt_bid,"上线时间:" + detals.getSell_time(),imgJiSuanqi, btLayout);
								} else {
									etBidMoney.setVisibility(View.GONE);
									bt_bid.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.shape_bt_boluoyellow));
									bt_bid.setText("立即登录");
								}
								if (remain_money > 100D&& remain_money <= 10000D) {
									BoluoUtils.getDilogJiaXi(remain_money + "",BidDetalsActivity.this, etBidMoney);
								}
								if (!TextUtils.isEmpty(phone)) {
									String remain_balance = object.getString("remain_balance");
									detals.setRemain_balance(remain_balance);
									remainView.setText("账户余额（元）"+ QntUtils.getFormat(QntUtils.getDouble(remain_balance)));
								} else {
									remainView.setText("账户余额(元)XXX.XX");
								}
								detals.setRemain_money(remain_money);
								detals.setRepay_type(object.getString("repay_type"));
								detals.setSell_time(object.getString("sell_time"));
								detals.setTime_limit(object.getString("time_limit"));
								detals.setTotal_mount(object.getDouble("total_mount"));
								productDetalsList.add(detals);
								getAssignment(productDetalsList);
								ProessDilogs.closeAnimation(visi_image,visi_layout);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	// intent数据
	private void getIntentString() {
		
		bidId = getIntent().getStringExtra("bidId");
		isNewer = getIntent().getBooleanExtra("isNewer", false);
		
	}
	// 初始化数据
	private void init() {
		btLayout = (LinearLayout) findViewById(R.id.bid_detals_layoutbt);
		imgJiSuanqi = (Button) findViewById(R.id.bid_detals_jisuanqi);
		imgJiSuanqi.setOnClickListener(this);
		etBidMoney = (EditText) findViewById(R.id.bid_detals_et_bidMoney);
		etBidMoney.setOnClickListener(this);
		AndroidUtils.limitTextLenth(this, "投资金额超标", etBidMoney, 11);
		String buyMoney = getIntent().getStringExtra("buyMoney");
		if (!TextUtils.isEmpty(buyMoney)) {
			etBidMoney.setText(buyMoney);
		}
		// etBidMoney
		// .setSelection(etBidMoney.getText().toString().trim().length());//
		// 设置光标在最后
		productDetalsList = new ArrayList<BeanProductDetals>();
		chartView = (YellowRoundProgressBar) findViewById(R.id.bid_detals_PieCharView);
		visi_layout = (LinearLayout) findViewById(R.id.bid_visibility_layout);
		visi_image = (ImageView) findViewById(R.id.bid_visibility_image);
		titleTextView = (TextView) findViewById(R.id.bid_detals_title);
		startmoneyTextView = (TextView) findViewById(R.id.bid_detals_startMoney);
		limitDayTextView = (TextView) findViewById(R.id.bid_detals_limitDay);
		totalMoneyTextView = (TextView) findViewById(R.id.bid_detals_totalMoney);
		tvRate = (TextView) findViewById(R.id.bid_detalsnianhuaNum);
		remianMoneyTextView = (TextView) findViewById(R.id.bid_detals_remain_Money);
		limitTotalMoneyTextView = (TextView) findViewById(R.id.bid_detals_limit_totals);
		RelativeLayout layoutAll = (RelativeLayout) findViewById(R.id.bid_detals_objectAll);
		RelativeLayout layoutMessage = (RelativeLayout) findViewById(R.id.bid_detals_objectMessage);
		RelativeLayout layoutFile = (RelativeLayout) findViewById(R.id.bid_detals_objectFile);
		RelativeLayout layouthetong = (RelativeLayout) findViewById(R.id.bid_detals_objecthetong);
		layoutAll.setOnClickListener(this);
		layoutMessage.setOnClickListener(this);
		layoutFile.setOnClickListener(this);
		layouthetong.setOnClickListener(this);
		bt_bid = (Button) findViewById(R.id.bid_detals_bt_bid);
		bt_bid.setOnClickListener(this);
		
	}

	private void getAssignment(List<BeanProductDetals> list) {
		BeanProductDetals detals = list.get(0);
		titleTextView.setText(detals.getProduct_name());
		startmoneyTextView.setText("100.0");
		limitDayTextView.setText(detals.getTime_limit());
		totalMoneyTextView.setText(QntUtils.getFormat(QntUtils.getDouble(detals.getTotal_mount() + "")));
		remianMoneyTextView.setText(QntUtils.getFormat(QntUtils.getDouble(detals.getRemain_money() + "")));
		limitTotalMoneyTextView.setText(detals.getLimit_mount());
		rate = QntUtils.getDoubleToInt(QntUtils.getDouble(detals.getRate()) * 100)+ "+"+ QntUtils.getDoubleToInt(QntUtils.getDouble(detals.getRate_increase()) * 100) + "%";
		chartView.setProgress(QntUtils.getDoubleToInt(QntUtils.getDouble(detals
				.getPercentage())));
		tvRate.setText(rate);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bid_detals_et_bidMoney:
			// 这里必须要给一个延迟，如果不加延迟则没有效果。我现在还没想明白为什么
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// 将ScrollView滚动到底
					mScrollView.fullScroll(View.FOCUS_DOWN);
				}
			}, 100);
			break;
		case R.id.bid_detals_objectAll:
			getIntentBt("项目概况",QntUtils.getURL(BeanUrl.XIANGMUGAIKUANG_STRING, bidId)+ "0");
			break;
		case R.id.bid_detals_objectFile:
			getIntentBt("投资信息",QntUtils.getURL(BeanUrl.XIANGMUGAIKUANG_STRING, bidId)+ "2");
			break;
		case R.id.bid_detals_objectMessage:
			getIntentBt("相关文件",QntUtils.getURL(BeanUrl.XIANGMUGAIKUANG_STRING, bidId)+ "1");
			break;
		case R.id.bid_detals_objecthetong:
			Intent intent = new Intent(BidDetalsActivity.this,
					PdfListActivity.class);
			intent.putExtra("bidId", bidId);
			startActivity(intent);
			break;
		case R.id.bid_detals_bt_bid:
			if (NetUtils.getNetWorkState(bidDetalsActivity) != -1) {
				getBid(etBidMoney.getText().toString().trim());
			} else {
				BoluoUtils.getDilogDome(bidDetalsActivity, "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.bid_detals_jisuanqi:
			int windowPos[] = PopWindowUtils.calculatePopWindowPos(btLayout,
					popWindowlayout);
			int xOff = 20;// 可以自己调整偏移
			windowPos[0] -= xOff;
			popupWindow = PopWindowUtils.getPop(this, popWindowlayout, btLayout);

			popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
			
			break;
		case R.id.touzi_detals_dissmiss:
			popupWindow.dismiss();
			break;
		case R.id.touzi_detals_bid:
			if (NetUtils.getNetWorkState(bidDetalsActivity) != -1) {
				popupWindow.dismiss();
				getBid(addView.getText().toString().trim());
			} else {
				BoluoUtils.getDilogDome(bidDetalsActivity, "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.touzi_detals_jian:
			if (!TextUtils.isEmpty(addView.getText().toString().trim())) {
				double shuzi1 = QntUtils.getDouble(addView.getText().toString()
						.trim());
				if (shuzi1 >= 100D) {
					String stringShuzi1 = (shuzi1 - 100D) + "";
					double rates = QntUtils.getDouble(productDetalsList.get(0)
							.getRate())
							+ QntUtils.getDouble(productDetalsList.get(0)
									.getRate_increase());
					double time = QntUtils.getDouble(productDetalsList.get(0)
							.getTime_limit());
					double lixi = (double) (QntUtils.getDouble(stringShuzi1)
							* rates * time / 360.0f);
					double shouyi = (double) (Math.round(lixi * 100)) / 100;
					addView.setText(stringShuzi1);
					shouyiView.setText(QntUtils.getFormat(shouyi));
				} else {
					Toast.makeText(getApplicationContext(), "您好，少于100",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				addView.setText("2000");
			}
			break;
			
		case R.id.touzi_detals_jia:
			if (!TextUtils.isEmpty(addView.getText().toString())) {
				double shuzi2 = QntUtils.getDouble(addView.getText().toString());
				if (shuzi2 <= 1000000) {
					String StringShuzi2 = (shuzi2 + 100) + "";
					double rates = QntUtils.getDouble(productDetalsList.get(0)
							.getRate())
							+ QntUtils.getDouble(productDetalsList.get(0)
									.getRate_increase());
					double time = QntUtils.getDouble(productDetalsList.get(0)
							.getTime_limit());
					double lixi = (double) (QntUtils.getDouble(StringShuzi2)
							* rates * time) / 360.0D;
					double shouyi = (double) (Math.round(lixi * 100)) / 100;
					addView.setText(StringShuzi2);
					shouyiView.setText(QntUtils.getFormat(shouyi) + "");
				} else {
					Toast.makeText(getApplicationContext(), "您好！超过200000",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				addView.setText("2000");
			}
			break;
		default:
			break;
		}
	}
	private void getIntentBt(String title, String jsUrl) {
		Intent intent = new Intent(BidDetalsActivity.this,JSAndroidActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("jsUrl", jsUrl);
		intent.putExtra("bidDetals", "bidDetals");
		startActivity(intent);
	}
	private void getBid(String money) {
		if (!BoluoUtils.isEmpty(memberId)) {// 登录状态下
			if (TextUtils.equals(pay_bind, "1")) {// 是否绑卡

				if (!TextUtils.isEmpty(money)) {// 投资金额不为空
					if (Double.parseDouble(money) >= 100) {
						Intent intentBid = new Intent(BidDetalsActivity.this,BidBuyActivity.class);
						intentBid.putExtra("bidId", bidId);
						intentBid.putExtra("buyMoney", money);
						intentBid.putExtra("isNewer", isNewer);
						startActivity(intentBid);
					} else {
						Toast.makeText(getApplicationContext(), "小于100元不可投",Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "请输入投资金额",Toast.LENGTH_SHORT).show();
				}
			} else {
				BoluoUtils.getDilogHintBand(BidDetalsActivity.this,
						AddNumberActivity.class);
			}
		} else {
			startActivity(new Intent(BidDetalsActivity.this, ZhuActivity.class));
		}
	}
	@Override
	public void afterTextChanged(Editable s) {
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		System.out.println("s=" + s);
		if (!TextUtils.isEmpty(addView.getText().toString())) {
			/**
			 * 限制输入金额最多为 limit 位小数
			 */
			if (s.toString().contains(".")) {
				if (s.length() - 1 - s.toString().indexOf(".") > 2) {
					s = s.toString().subSequence(0,
							s.toString().indexOf(".") + 2 + 1);
					addView.setText(s);
					addView.setSelection(s.length());
				}
			}
			/**
			 * 第一位输入小数点的话自动变换为 0.
			 */
			if (s.toString().trim().substring(0).equals(".")) {
				
				s = "0" + s;
				addView.setText(s);
				addView.setSelection(2);
			}
			/**
			 * 避免重复输入小数点前的0 ,没有意义
			 */
			if (s.toString().startsWith("0")
					&& s.toString().trim().length() > 1) {
				if (!s.toString().substring(1, 2).equals(".")) {
					addView.setText(s.subSequence(0, 1));
					addView.setSelection(1);
					return;
				}
			}
			double shuzi1 = QntUtils.getDouble(addView.getText().toString().trim());

			if (shuzi1 < 100D) {
				Toast.makeText(getApplicationContext(), "投资金额至少为100元",Toast.LENGTH_SHORT).show();
			} else {
				double rates = QntUtils.getDouble(productDetalsList.get(0).getRate())+ QntUtils.getDouble(productDetalsList.get(0).getRate_increase());
				double time = QntUtils.getDouble(productDetalsList.get(0).getTime_limit());
				double lixi = (double) (shuzi1 * rates * time / 360.0f);
				shouyiView.setText(QntUtils.getFormat(lixi) + "");
			}
		} else {
			Toast.makeText(getApplicationContext(), "请输入投资金额",Toast.LENGTH_SHORT).show();
		}
	}
}
