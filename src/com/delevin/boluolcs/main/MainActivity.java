package com.delevin.boluolcs.main;

import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.delevin.boluolcs.activity.WebActivity;
import com.delevin.boluolcs.base.activity.BaseFragmentActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.denglu.ZhuActivity;
import com.delevin.boluolcs.interfaces.DilogCallBack;
import com.delevin.boluolcs.interfaces.GengXinCallBack;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.StatusBarUtil;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.UpdateManager;
import com.delevin.jsandroid.JSAndroidActivity;
import com.pusupanshi.boluolicai.R;

public class MainActivity extends BaseFragmentActivity implements
		OnClickListener {
	private RadioButton bt_home;// 按钮 “主页”
	private RadioButton bt_touzi;// 按钮 “我的投资”
	private RadioButton bt_my;// 按钮 “我的账户”
	private RadioButton bt_faxian;// 按钮 “发现”
	public static Fragment[] mFragments;
	private String memberId;
	private boolean isFirstred;
	private String phone;
	private String login_token;
	private Editor editor;
	private LinearLayout my_none;
	private Button bt_denglu;
	private TextView tlogin;
	private TitleView titleView;
	public static MainActivity mainActivity;
	private int statusBarHeight = 0;

	// 初始化控件
	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {

		// View content = View.inflate(this, R.layout.activity_main, null);
		setContentView(R.layout.activity_main);
		mainActivity = this;
		statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
		titleView = (TitleView) findViewById(R.id.titleView_main_activity);
		titleView.initViewsVisible(false, true, true, false);
		titleView.setAppTitle("首页");

		// if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
		// getWindow().addFlags(
		// WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// }
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("isProgramFirstIn", MODE_PRIVATE);
		editor = preferences.edit();
		Map<String, String> shareData = BoluoUtils.getShareData(this);
		phone = shareData.get("phone");
		login_token = shareData.get("login_token");
		memberId = shareData.get("memberId");
		isFirstred = preferences.getBoolean("isDilogFirstIn", true); // isFirstIn
		bt_home = (RadioButton) findViewById(R.id.boluos_bt_home);
		bt_touzi = (RadioButton) findViewById(R.id.boluos_bt_touzi);
		bt_my = (RadioButton) findViewById(R.id.boluos_bt_my);
		bt_faxian = (RadioButton) findViewById(R.id.boluos_bt_faxian);
		bt_denglu = (Button) findViewById(R.id.bt_dilog_login);
		tlogin = (TextView) findViewById(R.id.bt_dilog_resger);
		bt_home.setOnClickListener(this);
		bt_denglu.setOnClickListener(this);
		tlogin.setOnClickListener(this);
		bt_touzi.setOnClickListener(this);
		bt_my.setOnClickListener(this);
		bt_faxian.setOnClickListener(this);
		String main = getIntent().getStringExtra("main");
		if (TextUtils.isEmpty(main)) {
			setFragmentIndicator(0);
		} else {
			setFragmentIndicator(2);
			bt_my.setChecked(true);
			bt_touzi.setChecked(false);
		}
	}

	// 获取数据
	@Override
	protected void getData() {

		SharedPreferences shareInit = BoluoUtils.getShareInit(this);
		Boolean b = shareInit.getBoolean("geng", true);
		if (b) {
			// 初始化更新管理
			UpdateManager manager = new UpdateManager(MainActivity.this);
			// 检查软件更新
			manager.checkUpdate("1", new GengXinCallBack() {

				@Override
				public void onCode(String code) {
				}

				@Override
				public void onSerivce(String ser) {
					// TODO Auto-generated method stub
					// Toast.makeText(MainActivity.this,
					// "hahhahhahh",Toast.LENGTH_SHORT).show();
					if (ser.equals("1")) {
						Intent serivce = new Intent(MainActivity.this,
								WebActivity.class);
						serivce.putExtra("jsUrl", BeanUrl.URLZB
								+ BeanUrl.SERIVCE_STRING);
						serivce.putExtra("title", "服务器维护中");
						startActivity(serivce);
					} else {

					}
				}
			});
		}

		if (isFirstred) {
			int imgIcon = 0;
			String url = null;
			if (!BoluoUtils.isEmpty(memberId)) {
				imgIcon = R.drawable.huodongtanchuang1;
				// url = BeanUrl.DAFUWEN_STRING;
				url = BeanUrl.DAFUWEN_STRING;
				editor.putBoolean("isDilogFirstIn", false); // isFirstIn
				editor.commit();
			} else {
				imgIcon = R.drawable.huodongtanchuang1;
				url = BeanUrl.DAFUWEN_STRING;
			}
			final String urls = url + "?phone=" + phone + "&" + "token="
					+ login_token;
			AndroidUtils.getDilog(imgIcon, this, new DilogCallBack() {
				@Override
				public void onPaseat() {

					Intent intent2 = new Intent(MainActivity.this,
							JSAndroidActivity.class);
					intent2.putExtra("jsUrl", urls);
					intent2.putExtra("title", "大富翁活动");
					startActivity(intent2);
				}
			});
		}
	}

	/**
	 * 初始化fragment
	 */
	private void setFragmentIndicator(int whichIsDefault) {
		if (whichIsDefault == 0) {
			titleView.setVisibility(View.GONE);
			StatusBarUtil.setColor(this, Color.TRANSPARENT, 0);
		} else {
			titleView.setVisibility(View.VISIBLE);
			StatusBarUtil.setColor(this,
					getResources().getColor(R.color.boluo_Yellow), 0);
		}
		my_none = (LinearLayout) findViewById(R.id.my_none);
		my_none.setVisibility(View.GONE);
		// 实例化 Fragment 集合
		mFragments = new Fragment[4];

		mFragments[0] = getSupportFragmentManager().findFragmentById(
				R.id.boluos_fragment_home);

		mFragments[1] = getSupportFragmentManager().findFragmentById(
				R.id.boluos_fragment_touzi);

		mFragments[2] = getSupportFragmentManager().findFragmentById(
				R.id.boluos_fragment_my);

		mFragments[3] = getSupportFragmentManager().findFragmentById(
				R.id.boluos_fragment_faxian);
		// 显示默认的Fragment
		getSupportFragmentManager().beginTransaction().hide(mFragments[0])
				.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3])
				.show(mFragments[whichIsDefault]).commit();
	}

	public void showFragment(int which) {
		int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
		if (which == 0) {
			titleView.setVisibility(View.GONE);

			StatusBarUtil.setColor(this, Color.TRANSPARENT, 0);
		} else {
			titleView.setVisibility(View.VISIBLE);
			StatusBarUtil.setColor(this,
					getResources().getColor(R.color.boluo_Yellow), 0);
		}

		getSupportFragmentManager().beginTransaction().hide(mFragments[0])
				.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3])
				.show(mFragments[which]).commit();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 主页
		case R.id.boluos_bt_home:
			if (!BoluoUtils.isEmpty(memberId)) {
				showFragment(0);
			} else {
				showFragment(0);
				my_none.setVisibility(View.GONE);
			}
			titleView.setAppTitle("首页");
			break;
		// 我的投资显示
		case R.id.boluos_bt_touzi:
			if (!BoluoUtils.isEmpty(memberId)) {
				showFragment(1);
			} else {
				showFragment(1);
				my_none.setVisibility(View.GONE);
			}
			titleView.setAppTitle("项目");
			break;
		// 我的账户显示
		case R.id.boluos_bt_my:
			if (!BoluoUtils.isEmpty(memberId)) {
				showFragment(2);
			} else {
				showFragment(2);
				my_none.setVisibility(View.VISIBLE);
			}
			titleView.setAppTitle("发现");
			break;
		// 发现显示
		case R.id.boluos_bt_faxian:
			if (!BoluoUtils.isEmpty(memberId)) {
				showFragment(3);
			} else {
				showFragment(3);
				my_none.setVisibility(View.VISIBLE);
			}
			titleView.setAppTitle("账户");
			break;
		case R.id.bt_dilog_login:
			startActivity(new Intent(MainActivity.this, ZhuActivity.class));
			break;
		case R.id.bt_dilog_resger:
			startActivity(new Intent(MainActivity.this, ZhuActivity.class));
			break;

		default:
			break;
		}
	}
}
