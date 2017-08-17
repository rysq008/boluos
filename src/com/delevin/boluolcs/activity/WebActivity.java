package com.delevin.boluolcs.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.delevin.boluolcs.main.MainActivity;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

public class WebActivity extends Activity {
	private LinearLayout layout;
	private ImageView imageView;
	private WebView webView;
	private TitleView tvTitle;
	private String strUrl;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_js_webview);
		View statusBarview = findViewById(R.id.statusBarview);
		imageView = (ImageView) findViewById(R.id.js_webview_visibility_image);
		layout = (LinearLayout) findViewById(R.id.js_webview_visibility_layout);
		ProessDilogs.getProessAnima(imageView, this);
		
		tvTitle = (TitleView) findViewById(R.id.js_webview_titleview);
		webView = (WebView) findViewById(R.id.js_webview_webview);
		WebSettings webSettings = webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		webSettings
				.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setSaveFormData(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setTextZoom(100);
		String strTitle = getIntent().getStringExtra("title");
		if (strTitle.equals("服务器维护中")) {
			statusBarview.setVisibility(View.GONE);
			tvTitle.setVisibility(View.GONE);
			strUrl=getIntent().getStringExtra("jsUrl");
			MainActivity.mainActivity.finish();
		}else {
			// 设置状态栏一体化
			if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
				getWindow().setFlags(
						WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
						WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				statusBarview.setVisibility(View.VISIBLE);
			}
			tvTitle.setVisibility(View.VISIBLE);
			tvTitle.initViewsVisible(true, true, false, false);
			tvTitle.setAppTitle(strTitle);
			strUrl=getIntent().getStringExtra("jsUrl");
		}
		try {
			webView.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(WebView view, int progress) {
					WebActivity.this.setTitle("Loading...");
					WebActivity.this.setProgress(progress);
					if (progress >= 80) {
						// JSAndroidActivity.this.setTitle("JsAndroid");
						ProessDilogs.closeAnimation(imageView, layout);
					}
				}
			});
			webView.loadUrl(strUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}