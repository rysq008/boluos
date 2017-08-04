package com.delevin.boluolcs.fragmnet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.activity.BidDetalsActivity;
import com.delevin.boluolcs.activity.NoticeListActivity;
import com.delevin.boluolcs.activity.TouziMeiTiActivity;
import com.delevin.boluolcs.activity.TouziMoreObjectActivity;
import com.delevin.boluolcs.adapter.BannerAdapter;
import com.delevin.boluolcs.base.adapter.ViewPagerScroller;
import com.delevin.boluolcs.base.fragment.BaseFragment;
import com.delevin.boluolcs.bean.BeanBanner;
import com.delevin.boluolcs.bean.BeanNotice;
import com.delevin.boluolcs.bean.BeanTJCP;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.chat.activity.ChatActivity;
import com.delevin.boluolcs.chat.activity.Utils;
import com.delevin.boluolcs.interfaces.KefuCallBack;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.NetUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.PullToRefreshView;
import com.delevin.boluolcs.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.PublicNoticeView;
import com.delevin.boluolcs.view.RoundProgressBar;
import com.delevin.jsandroid.JSAndroidActivity;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-15 下午12:59:15    类说明 投资首页
 */

public class HomeFragment extends BaseFragment implements OnPageChangeListener, OnClickListener, OnHeaderRefreshListener {
	private ViewPager viewPager; // banner viewpager 初始化
	private LinearLayout viewGroup; // banner 底部切换点 初始化
	private ArrayList<View> list; // banner 点 添加集合
	private ImageView[] imageViews; // banner 存储点数组
	private BannerAdapter adapters; // banner 切换图片适配器
	private List<BeanBanner> bannersList; // banner 添加banner 图片uri
	private List<BeanNotice> noticeList;// 公告
	private List<BeanTJCP> newerList; // 新手专享
	private List<BeanTJCP> tjcpList;// 推荐产品
	private LinearLayout layout_xinshoubangzhu; // 新手帮助
	private LinearLayout layout_anquanbaozhang; // 安全保障
	private LinearLayout layout_guanyuwomen; 	// 关于我们
	private LinearLayout home_Layout_tjcp;
	private PublicNoticeView noticeView;
	private LinearLayout draweeView;// 新手红包点击
	private String memberId;
	private String phone;
	private String apiToken;
	private LinearLayout layoutNewerObject;
	private String newReder;
	private PullToRefreshView pullToRefreshView;
	private ImageView imgNew;
	private TextView tvNew;
	private LinearLayout layout_V;
	private ImageView img_V;
	private String strUserName;

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.boluos_fragment_home,
				container, false);
		return view;
	}

	@Override
	protected void getFindById(View view) {
		
		strUserName=BoluoUtils.getShareOneData(getActivity(), "phone");
		if (!TextUtils.isEmpty(strUserName)) {
			strUserName=BoluoUtils.getShareOneData(getActivity(), "phone");
		}else {
			strUserName=Utils.createRandomString(11);
		}
		
		//注册   环信用户
		new Thread(new Runnable() {
			public void run() {
				try {
					// call method in SDK
					EMChatManager.getInstance().createAccountOnServer(strUserName,
							"123");
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
//									Toast.makeText(getActivity(), "注册成功",Toast.LENGTH_SHORT).show();
						}
					});
				} catch (final Exception e) {
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
//									Toast.makeText(getApplicationContext(), "注册失败",Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}).start();
		
		
		
		
		
		
		
		layout_V = (LinearLayout) view.findViewById(R.id.home_visibility_layout);
		img_V = (ImageView) view.findViewById(R.id.home_visibility_image);
		imgNew = (ImageView) view.findViewById(R.id.home_more_meiti_img);
		tvNew = (TextView) view.findViewById(R.id.home_more_meiti_tv);
		pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.home_pull);
		pullToRefreshView.setOnHeaderRefreshListener(this);
		LinearLayout layoutKefu = (LinearLayout) view.findViewById(R.id.home_kefu);
		layoutKefu.setOnClickListener(this);
		LinearLayout layoutPai = (LinearLayout) view.findViewById(R.id.home_paihangbang);
		LinearLayout layoutPi = (LinearLayout) view.findViewById(R.id.home_pilou);
		
		layoutPai.setOnClickListener(this);
		layoutPi.setOnClickListener(this);
		layoutNewerObject = (LinearLayout) view.findViewById(R.id.home_newer_object);
		layoutNewerObject.setOnClickListener(this);
		TextView newMoreObject = (TextView) view.findViewById(R.id.home_moreNew_object);
		LinearLayout moreObject = (LinearLayout) view.findViewById(R.id.home_more_object);
		LinearLayout layoutMeiTi = (LinearLayout) view.findViewById(R.id.home_more_meiti);
		layoutMeiTi.setOnClickListener(this);
		viewGroup = (LinearLayout) view.findViewById(R.id.home_banner_group);
		viewPager = (ViewPager) view.findViewById(R.id.home_banner_viewPager);
		layout_xinshoubangzhu = (LinearLayout) view.findViewById(R.id.home_xinshoubangzhu);
		layout_anquanbaozhang = (LinearLayout) view.findViewById(R.id.home_anquanbaozhang);
		layout_guanyuwomen = (LinearLayout) view.findViewById(R.id.home_guanyuwomen);
		draweeView = (LinearLayout) view.findViewById(R.id.home_img_view);
		draweeView.setOnClickListener(this);
		layout_guanyuwomen.setOnClickListener(this);
		layout_xinshoubangzhu.setOnClickListener(this);
		layout_anquanbaozhang.setOnClickListener(this);
		noticeView = (PublicNoticeView) view.findViewById(R.id.home_notice);
		noticeView.setOnClickListener(this);
		home_Layout_tjcp = (LinearLayout) view.findViewById(R.id.home_Layout_tjcp);
		bannersList = new ArrayList<BeanBanner>();
		tjcpList = new ArrayList<BeanTJCP>();
		newerList = new ArrayList<BeanTJCP>();
		noticeList = new ArrayList<BeanNotice>();
		newMoreObject.setOnClickListener(this);
		moreObject.setOnClickListener(this);
		list = new ArrayList<View>();
		getShareInit();
	}

	private void getShareInit() {
		Map<String, String> shareData = BoluoUtils.getShareData(getActivity());
		phone = shareData.get("phone");
		apiToken = shareData.get("login_token");
		memberId = shareData.get("memberId");
	}

	@Override
	protected void getData() {
		ProessDilogs.getProessAnima(img_V, getActivity());
		// 请求数据
		Myapplication.okhttpManger.sendComplexForm(getActivity(), false,
				BeanUrl.TOUZI_STRING, null, new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							ProessDilogs.closeAnimation(img_V, layout_V);
							if (TextUtils.equals(code, "10000")) {
								JSONObject contentObject = result.getJSONObject("content");
								JSONArray bannerArray = contentObject.getJSONArray("banner");
								JSONArray noticeArray = contentObject.getJSONArray("notice");
								JSONArray newerArray = contentObject.getJSONArray("newer");
								JSONArray tjcpArray = contentObject.getJSONArray("tjcp");
								JSONArray imgArray = contentObject.getJSONArray("img");
								bannersList.clear();
								newerList.clear();
								tjcpList.clear();
								for (int i = 0; i < bannerArray.length(); i++) {

									BeanBanner banner = new BeanBanner();
									JSONObject bannerObject = bannerArray.getJSONObject(i);
									String webViewPath = bannerObject.getString("img");
									String ImgPath = bannerObject.getString("url");
									String type = bannerObject.getString("type");
									banner.setType(type);
									banner.setImg(webViewPath);
									banner.setUrl(ImgPath);
									bannersList.add(banner);
								}
								getbanner(bannersList.size());
								for (int i = 0; i < newerArray.length(); i++) {
									JSONObject newerObject = newerArray.getJSONObject(0);
									BeanTJCP newer = new BeanTJCP();
									newer.setProduct_name(newerObject.getString("product_name"));
									newer.setFeature_name(newerObject.getString("feature_name"));
									newer.setRate(QntUtils.getFormatOne(QntUtils.getDouble(newerObject.getString("rate")) * 100)+ "");
									newer.setRate_increase(QntUtils.getFormatOne(QntUtils.getDouble(newerObject.getString("rate_increase")) * 100)+ "");
									newer.setTime_limit(newerObject.getString("time_limit"));
									newer.setId(newerObject.getString("id"));
									newer.setPercentage(newerObject.getString("percentage"));
									newer.setProduct_status(newerObject.getString("product_status"));
									newer.setProduct_remain(newerObject.getString("product_remain"));
									newerList.add(newer);
								}
								getLayoutRecommended(newerList, true,
										layoutNewerObject);
								for (int i = 0; i < tjcpArray.length(); i++) {
									BeanTJCP tjcp = new BeanTJCP();
									JSONObject tjcpObject = tjcpArray.getJSONObject(i);
									String product_name = tjcpObject.getString("product_name");
									String feature_name = tjcpObject.getString("feature_name");
									String rate = tjcpObject.getString("rate");
									String rate_increase = tjcpObject.getString("rate_increase");
									String time_limit = tjcpObject.getString("time_limit");
									String percentage = tjcpObject.getString("percentage");
									String product_status = tjcpObject
											.getString("product_status");
									String idString = tjcpObject
											.getString("id");
									String product_remain = tjcpObject
											.getString("product_remain");
									tjcp.setProduct_remain(product_remain);
									tjcp.setProduct_name(product_name);
									tjcp.setFeature_name(feature_name);
									tjcp.setRate(QntUtils.getFormatOne(QntUtils
											.getDouble(rate) * 100) + "");
									tjcp.setRate_increase(QntUtils.getFormatOne(QntUtils
											.getDouble(rate_increase) * 100)
											+ "");
									tjcp.setTime_limit(time_limit);
									tjcp.setPercentage(percentage);
									tjcp.setProduct_status(product_status);
									tjcp.setId(idString);
									tjcpList.add(tjcp);
								}
								getLayoutRecommended(tjcpList, false,home_Layout_tjcp);
								for (int i = 0; i < noticeArray.length(); i++) {

									BeanNotice notice = new BeanNotice();
									JSONObject noticeObject = noticeArray.getJSONObject(i);
									String url = noticeObject.getString("url");
									String title = noticeObject.getString("title");
									notice.setTitle(title);
									notice.setUrl(url);
									noticeList.add(notice);
								}
								noticeView.bindNotices(noticeList);
								JSONObject imgObject = imgArray.getJSONObject(0);
								newReder = imgObject.getString("url");
								JSONObject newObject = result.getJSONObject("new");
								String strNewTitle = newObject.getString("title");
								tvNew.setText(strNewTitle);
//								String strNewImg = newObject.getString("image");
//								AndroidUtils.getImg(getActivity(), strNewImg,imgNew, R.drawable.boluo_center,R.drawable.boluo_fail);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/************************************** banner banner滑动图片展示设置开始 *********************************************/
	/**
	 * banner滑动图片展示
	 * */
	@SuppressLint({ "NewApi", "InflateParams" })
	private void getbanner(int size) {
		list.clear();
		imageViews = new ImageView[0];
		viewPager.removeAllViews();
		viewGroup.removeAllViews();
		int i;
		for (i = 0; i < size; i++) {
			final BeanBanner banner = bannersList.get(i);
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.banner_item_image, null);
			ImageView draweeView = (ImageView) view.findViewById(R.id.my_image_view);
			draweeView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent intent = new Intent(getActivity(),JSAndroidActivity.class);
					intent.putExtra("type", banner.getType());
					intent.putExtra("js", "js");
					if (memberId != null) {

						intent.putExtra("jsUrl", banner.getUrl() + "?phone="+ phone + "&" + "token=" + apiToken);
					} else {
						intent.putExtra("jsUrl", banner.getUrl() + "?phone="+ "" + "&" + "token=" + "");
					}
					intent.putExtra("title", "活动中心");
					intent.putExtra("right", "rightA");
					startActivity(intent);
				}
			});
			AndroidUtils.getImg(getActivity(), banner.getImg(), draweeView,R.drawable.boluo_center, R.drawable.boluo_fail);
			list.add(view);
		}
		// 添加底部切换点
		imageViews = new ImageView[list.size()];
		for (int j = 0; j < list.size(); j++) {
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.banner_item_indicator, null);
			imageViews[j] = (ImageView) view.findViewById(R.id.image_indicator);
			// imageViews[i] = imageView;
			if (j == 0) {
				// 默认进入程序后第一张图片被选中;
				imageViews[j].setBackgroundResource(R.drawable.boluo_dot_true);
			} else {
				imageViews[j].setBackgroundResource(R.drawable.boluo_dot_false);
			}
			viewGroup.addView(view);
		}
		adapters = new BannerAdapter(getActivity(), list);
		ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
		scroller.setScrollDuration(1000);
		scroller.initViewPagerScroll(viewPager);// 这个是设置切换过渡时间为2秒
		viewPager.setAdapter(adapters);
		viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem(300);
	}

	/**
	 * 实现循环播放功能
	 */
	@SuppressLint("HandlerLeak")
	private Handler mHandlers = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				int totalCount = list.size();
				int currentItem = viewPager.getCurrentItem();
				int toItem = currentItem + 1 == totalCount ? 0: currentItem + 1;
				viewPager.setCurrentItem(toItem, true);
				// 每两秒钟发送一个message，用于切换viewPager中的图片
				this.sendEmptyMessageDelayed(1, 4000);
				break;
			}
		}
	};

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 > 2) {
			arg0 = arg0 % list.size();
		}
		for (int i = 0; i < imageViews.length; i++) {
			imageViews[arg0].setBackgroundResource(R.drawable.boluo_dot_true);
			if (arg0 != i) {
				imageViews[i].setBackgroundResource(R.drawable.boluo_dot_false);
			}
		}
	}

	/************************************** banner banner滑动图片展示设置结束 *********************************************/
	/** 推荐产品布局与加载数据 **/
	@SuppressLint("InflateParams")
	private void getLayoutRecommended(List<BeanTJCP> list, final Boolean b,
			LinearLayout layouts) {
		layouts.removeAllViews();
		for (int i = 0; i < list.size(); i++) {
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_object, null);
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.object_layout);
			TextView object_product_name = (TextView) view.findViewById(R.id.object_product_name);
			TextView object_rate = (TextView) view.findViewById(R.id.object_rate);
			TextView object_rate_increase = (TextView) view.findViewById(R.id.object_rate_increase);
			TextView object_time_limit = (TextView) view.findViewById(R.id.object_time_limit);
			TextView object_yues = (TextView) view.findViewById(R.id.object_yues);
			ImageView imgLimit = (ImageView) view.findViewById(R.id.item_newLimit);
			ImageView imgGone = (ImageView) view.findViewById(R.id.object_gone);
			if (b) {
				imgLimit.setVisibility(View.VISIBLE);
			}
			RoundProgressBar grProgressBar = (RoundProgressBar) view.findViewById(R.id.object_PieCharViewBuy);
			grProgressBar.setMax(100);
			object_product_name.setText(list.get(i).getProduct_name());
			object_rate.setText(""+ QntUtils.getDoubleToInt(QntUtils.getDouble(list.get(i).getRate())));
			object_rate_increase.setText(QntUtils.getDoubleToInt(QntUtils
					.getDouble(list.get(i).getRate_increase())) + "%");
			object_time_limit.setText(list.get(i).getTime_limit());
			String product_remain = list.get(i).getProduct_remain();
			int gress = QntUtils.getDoubleToInt(QntUtils.getDouble(list.get(i)
					.getPercentage()));
			if (gress == 100) {
				grProgressBar.setVisibility(View.GONE);
				imgGone.setVisibility(View.VISIBLE);
			} else {
				grProgressBar.setProgress(gress);
			}
			object_yues.setText(QntUtils.getFormat(QntUtils
					.getDouble(product_remain)) + "元");
			final String id = list.get(i).getId();
			final String status = list.get(i).getProduct_status();
			layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (NetUtils.getNetWorkState(getActivity()) != -1) {
						// if (!BoluoUtils.isEmpty(memberId)) {

						Intent intent = new Intent(getActivity(),
								BidDetalsActivity.class);
						intent.putExtra("bidId", id);
						intent.putExtra("status", status);

						if (b) {
							intent.putExtra("isNewer", true);
						} else {
							intent.putExtra("isNewer", false);
						}
						startActivity(intent);

						// }else {
						// startActivity(new
						// Intent(getActivity(),ZhuActivity.class));
						// }
					} else {
						BoluoUtils.getDilogDome(getActivity(), "温馨提示",
								"您当前的网络不可用", "确定");
					}
				}
			});
			layouts.addView(view);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_more_object:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				Intent intentMore = new Intent(getActivity(),
						TouziMoreObjectActivity.class);
				intentMore.putExtra("type", "1");
				startActivity(intentMore);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.home_moreNew_object:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				Intent intentMoreNew = new Intent(getActivity(),
						TouziMoreObjectActivity.class);
				intentMoreNew.putExtra("type", "0");
				startActivity(intentMoreNew);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.home_xinshoubangzhu:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				Intent intent = new Intent(getActivity(),
						JSAndroidActivity.class);
				intent.putExtra("jsUrl", BeanUrl.XINSHOU_HELP_STRING);
				intent.putExtra("title", "新手帮助");
				startActivity(intent);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.home_anquanbaozhang:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				Intent intentSafe = new Intent(getActivity(),
						JSAndroidActivity.class);
				intentSafe.putExtra("jsUrl", BeanUrl.SAFE_STRING);
				intentSafe.putExtra("title", "安全保障");
				startActivity(intentSafe);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		// 公告
		case R.id.home_notice:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				ViewFlipper viewFlipper = noticeView.getViewFlipper();
				View currentView = viewFlipper.getCurrentView();
				int id = currentView.getId();
				Intent intentNotice = new Intent();
//				intentNotice.putExtra("title", "公告");
//				intentNotice.putExtra("jsUrl", noticeList.get(id).getUrl());
//				intentNotice.putExtra("js", "js");
				intentNotice.setClass(getActivity(), NoticeListActivity.class);
				startActivity(intentNotice);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		// 新手标投资
		case R.id.home_newer_object:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				// if (!BoluoUtils.isEmpty(memberId)) {
				Intent intentNewer = new Intent(getActivity(),
						BidDetalsActivity.class);
				intentNewer.putExtra("bidId", newerList.get(0).getId());
				intentNewer.putExtra("status", newerList.get(0)
						.getProduct_status());
				intentNewer.putExtra("isNewer", true);
				startActivity(intentNewer);
				// }else {
				// startActivity(new Intent(getActivity(),ZhuActivity.class));
				// }
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.home_more_meiti:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				startActivity(new Intent(getActivity(),
						TouziMeiTiActivity.class));
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.home_img_view:
			Intent intent = new Intent(getActivity(), JSAndroidActivity.class);
			intent.putExtra("title", "活动中心");
			intent.putExtra("js", "js");
			if (memberId != null) {

				intent.putExtra("jsUrl", newReder + "?islogin=1");
			} else {
				intent.putExtra("jsUrl", newReder + "?islogin=0");
			}
			startActivity(intent);
			break;
		case R.id.home_pilou:
		case R.id.home_guanyuwomen:
			// startActivity(new Intent(getActivity(),PdfActivity.class));
			Intent intentPi = new Intent(getActivity(), JSAndroidActivity.class);
			intentPi.putExtra("title", "信息披露");
			intentPi.putExtra("jsUrl", BeanUrl.MESSAGEPILOU_STRING);
			startActivity(intentPi);
			break;
		case R.id.home_paihangbang:
			Intent intentBang = new Intent(getActivity(),JSAndroidActivity.class);
			intentBang.putExtra("title", "投资排行榜");
			intentBang.putExtra("jsUrl", BeanUrl.PAIHANGBANG_STRING);
			startActivity(intentBang);
			break;
		case R.id.home_kefu:
			BoluoUtils.getKefuDilog(getActivity(), new KefuCallBack() {
				
				@Override
				public void onCallPhone() {
					Intent intentTel = new Intent();
					intentTel.setAction(Intent.ACTION_DIAL);
					intentTel.setData(Uri.parse("tel:400-032-0596"));
					startActivity(intentTel);
				}
				
				@Override
				public void onCall() {
//					Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
					// 调用sdk  登陆方法      登陆聊天服务器
					EMChatManager.getInstance().login(strUserName, "123", new EMCallBack() {

						@Override
						public void onSuccess() {
							// 登陆成功，保存用户名密码
							((Myapplication) Myapplication.getInstance()).setUserName(strUserName);
							((Myapplication) Myapplication.getInstance()).setPassword("123");

							new Thread(new Runnable() {
								public void run() {

									try {
										// 参数为要添加的好友的username和添加理由
										EMContactManager.getInstance().addContact("boluolicai", "添加理由");
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												// 跳转到聊天页面
												Intent intent = new Intent(getActivity(),ChatActivity.class);
												intent.putExtra("userId", "boluolicai");
												startActivity(intent);
											}
										});
									} catch (final Exception e) {
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
//												Toast.makeText(getActivity(),"添加好友失败", 1).show();
											}
										});
									}
								}
							}).start();
						}
						@Override
						public void onProgress(int progress, String status) {
						}
						@Override
						public void onError(final int code, final String message) {
							getActivity().runOnUiThread(new Runnable() {
								public void run() {
//									Toast.makeText(getActivity(),"登录异常",Toast.LENGTH_SHORT).show();
								}
							});
						}
					});
				}
			});
			break;
		default:
			break;
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		// 停止viewPager中图片的自动切换
		mHandlers.removeMessages(1);
	}

	@Override
	public void onResume() {
		super.onResume();
		// activity启动两秒钟后，发送一个message，用来将viewPager中的图片切换到下一个
		mHandlers.sendEmptyMessageDelayed(1, 2000);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		pullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				pullToRefreshView.onHeaderRefreshComplete();
				getShareInit();
				getData();
			}

		}, 1000);

	}
}