package com.delevin.jsandroid;

import java.util.Timer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.delevin.boluolcs.activity.WebActivity;
import com.delevin.boluolcs.bean.BeanFirstEvent;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.denglu.ZhuActivity;
import com.delevin.boluolcs.interfaces.ShareCallBack;
import com.delevin.boluolcs.main.MainActivity;
import com.delevin.boluolcs.umeng.share.ShareUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.TitleView.OnRightButtonClickListener;
import com.pusupanshi.boluolicai.R;
import com.pusupanshi.boluolicai.wxapi.ShareActivity;

import de.greenrobot.event.EventBus;

public class JSAndroidActivity extends Activity implements OnClickListener {

	private WebView mWebView = null;
	private String memberId;
	private String phone;
	private TitleView tvTitle;
	private LinearLayout layout;
	private ImageView imageView;
	private long TIME_OUT = 5000;
	public static final int MSG_PAGE_TIMEOUT = 11;
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// mWebView !=
			// null&&mWebView.getProgress()<100&&mWebView.getContentHeight()
			case MSG_PAGE_TIMEOUT:
				// 这里对已经显示出页面且加载超时的情况不做处理
				if (mWebView != null && mWebView.getProgress() < 100)
					if (TextUtils.isEmpty(getIntent().getStringExtra("js"))) {

						mWebView.loadUrl(BeanUrl.URLZB
								+ getIntent().getStringExtra("jsUrl"));
					} else {
						mWebView.loadUrl(getIntent().getStringExtra("jsUrl"));
					}
				break;
			}
		}
	};
	private Timer mTimer;
	private String type;
	private String right;
	public static final String contentshare = "菠萝迎新年活动，豪礼大富翁    活动隆重上线。iphone7、	mini4,话费，现金红包丰厚豪礼只等您来拿";

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		phone=BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		memberId = BoluoUtils.getShareOneData(getApplicationContext(),
				"memberId");
		setContentView(R.layout.activity_js_webview);
		View statusBarview = findViewById(R.id.statusBarview);
		tvTitle = (TitleView) findViewById(R.id.js_webview_titleview);
		imageView = (ImageView) findViewById(R.id.js_webview_visibility_image);
		layout = (LinearLayout) findViewById(R.id.js_webview_visibility_layout);
		right = getIntent().getStringExtra("right");
		mWebView = (WebView) findViewById(R.id.js_webview_webview);
		type = getIntent().getStringExtra("type");
		ProessDilogs.getProessAnima(imageView, this);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		String title = getIntent().getStringExtra("title");
		tvTitle.setAppTitle(title);
		if (!TextUtils.isEmpty(right)) {
			if (TextUtils.equals(type, "1")) {
				tvTitle.setRightIcon(R.drawable.icon_fenxiang);
				tvTitle.initViewsVisible(true, true, true, false);
				tvTitle.setOnRightButtonClickListener(new RightButton());
			} else if (TextUtils.equals(type, "3")) {
				tvTitle.setRightIcon(R.drawable.bang_help);
				tvTitle.initViewsVisible(true, true, true, false);
				tvTitle.setOnRightButtonClickListener(new RightButton());
			} else {
				tvTitle.initViewsVisible(true, true, false, false);
			}
		} else {
			tvTitle.initViewsVisible(true, true, false, false);
		}
		mWebView.reload();

		showWebView();
	}

	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	private void showWebView() {
		// webView与js交互代码

		try {
			mWebView.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(WebView view, int progress) {
					JSAndroidActivity.this.setTitle("Loading...");
					JSAndroidActivity.this.setProgress(progress);
					if (progress >= 80) {
						// JSAndroidActivity.this.setTitle("JsAndroid");
						ProessDilogs.closeAnimation(imageView, layout);
					}
				}
			});
			WebSettings webSettings = mWebView.getSettings();
			webSettings.setBuiltInZoomControls(true);
			webSettings
					.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
			webSettings.setUseWideViewPort(true);
			webSettings.setLoadWithOverviewMode(true);
			webSettings.setSaveFormData(true);
			webSettings.setGeolocationEnabled(true);
			webSettings.setTextZoom(100);
			webSettings.setDomStorageEnabled(true);
			mWebView.requestFocus();
			mWebView.setScrollBarStyle(0);
			webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
			webSettings.setJavaScriptEnabled(true);
			webSettings.setLoadWithOverviewMode(true);
			webSettings.setJavaScriptEnabled(true);
			webSettings.setDefaultTextEncodingName("utf-8");
			mWebView.addJavascriptInterface(getHtmlObject(), "jsObj");
			if (TextUtils.isEmpty(getIntent().getStringExtra("js"))) {

				mWebView.loadUrl(BeanUrl.URLZ
						+ getIntent().getStringExtra("jsUrl"));
			} else {
				mWebView.loadUrl(getIntent().getStringExtra("jsUrl"));
			}
			mWebView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					//
					view.loadUrl(url);
					return super.shouldOverrideUrlLoading(view, url);
				}

//				@Override
//				public void onPageStarted(WebView view, String url,
//						Bitmap favicon) {
//					// TODO Auto-generated method stub
//					super.onPageStarted(view, url, favicon);
//					// mTimer = new Timer();
//					// TimerTask tt = new TimerTask() {
//					// @Override
//					// public void run() {
//					// Message m = new Message();
//					// m.what = MSG_PAGE_TIMEOUT ;
//					// mHandler.sendMessage(m);
//					//
//					// mTimer.cancel();
//					// mTimer.purge();
//					// }
//					// };
//					// mTimer.schedule(tt, TIME_OUT);
//				}
//
//				@Override
//				public void onPageFinished(WebView view, String url) {
//					// TODO Auto-generated method stub
//					super.onPageFinished(view, url);
//					// mTimer.cancel();
//					// mTimer.purge();
//				}
//
//				@Override
//				public void onReceivedError(WebView view, int errorCode,
//						String description, String failingUrl) {
//					// TODO Auto-generated method stub
//					super.onReceivedError(view, errorCode, description,
//							failingUrl);
//					Toast.makeText(JSAndroidActivity.this, "同步失败，请稍候再试",
//							Toast.LENGTH_SHORT).show();
//				}
			});
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private Object getHtmlObject() {
		Object insertObj = new Object() {
			@JavascriptInterface
			public void HtmlcallJavaFinishJava() {
				mWebView.loadUrl(getIntent().getStringExtra("jsUrl"));
			}

			/**
			 * 跳转登录
			 * */
			@JavascriptInterface
			public String HtmlcallJava2() {

				if (memberId == null) {
					startActivity(new Intent(JSAndroidActivity.this,
							ZhuActivity.class));
					finish();
				} else {

				}
				return "0";
			}

			@JavascriptInterface
			public String HtmlcallJava3() {
				startActivity(new Intent(JSAndroidActivity.this,
						MainActivity.class));
				finish();
				return "0";
			}

			/**
			 * 跳转分享
			 * */
			@JavascriptInterface
			public String HtmlcallJava4() {

				// mWebView.loadUrl("javascript:show('"+memberId+"')");
				if (memberId != null) {
					Intent intent = new Intent(JSAndroidActivity.this,
							ShareActivity.class);
					intent.putExtra("phonelog", phone);
					startActivity(intent);
					finish();
				} else {
					startActivity(new Intent(JSAndroidActivity.this,
							ZhuActivity.class));
					finish();
				}
				return "0";
			}

			/**
			 * 四月加息
			 * */
			@JavascriptInterface
			public String HtmlcallJava5() {

				EventBus.getDefault().post(new BeanFirstEvent("payOrTian"));
				// startActivity(new Intent(JSAndroidActivity.this,
				// MainActivity.class));
				finish();
				return "0";
			}

			/**
			 * 跳转登录
			 * */
			@JavascriptInterface
			public String HtmlcallJava6() {

				// if(memberId!=null){
				startActivity(new Intent(JSAndroidActivity.this,
						ZhuActivity.class));
				finish();
				// }else {
				//
				// }
				return "0";
			}

			@JavascriptInterface
			public void JavacallHtml() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mWebView.loadUrl("javascript: showFromHtml()");
					}
				});
			}
		};
		return insertObj;
	}

	@Override
	public void onClick(View v) {

	}

	public class RightButton implements OnRightButtonClickListener {

		@Override
		public void OnRightButtonClick(View v) {
			if (memberId != null) {
				// String type = getIntent().getStringExtra("type");
				// ShareUtils.initShare(JSAndroidActivity.this,
				// "菠萝理财：安全、短期、稳健收益", contentshare,
				// BeanUrl.SHARE_STRING+phone+"/"+type,
				// R.drawable.icon_fenxiang);
				// ShareUtils.getOpen();
				if (TextUtils.equals(right, "rightQ")) {

					Intent qiandao = new Intent(JSAndroidActivity.this,
							WebActivity.class);
					qiandao.putExtra("jsUrl", BeanUrl.URLZ
							+ BeanUrl.QIANDAOGUIZE_STRING);
					qiandao.putExtra("title", "签到规则");
					startActivity(qiandao);
				} else if (TextUtils.equals(right, "rightA")) {
					String type = getIntent().getStringExtra("type");
					ShareUtils.initShare(new ShareCallBack() {

						@Override
						public void onrespone() {

						}
					}, JSAndroidActivity.this, "菠萝理财：安全、短期、稳健收益", contentshare,
							BeanUrl.SHARE_STRING + phone + "/" + type,
							R.drawable.icon_fenxiang);
					ShareUtils.getOpen();
				}
			} else {
				startActivity(new Intent(JSAndroidActivity.this,
						ZhuActivity.class));
			}
		}

	}
}
