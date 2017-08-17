package com.delevin.boluolcs.fragmentactivity;


import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.delevin.boluolcs.base.activity.BaseFragmentActivity;
import com.delevin.boluolcs.fragmnet.ShareYaoqingYaoqingFragment;
import com.delevin.boluolcs.fragmnet.ShareYaoqingfanliFragment;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

/**
 *  @author 李红涛  @version 创建时间：2016-2-18 下午6:16:29    类说明 
 */
public class ShareYaoqingActivity extends BaseFragmentActivity implements
		OnClickListener {
	private RelativeLayout rlYaoqingjilu, rlFanlijilu;
	private Fragment yaoqingFragment, fanliFragment, currentFragment;
	private TitleView tvTitle;

	@Override
	protected void findViews() {
		setContentView(R.layout.activity_share_fragments);
		initUI();
		String stringExtraTag = getIntent().getStringExtra("tag");
		if (stringExtraTag.equals("0")) {
			initTab();

		} else {
			initTab2();
		}

	}

	private void initUI() {
		rlYaoqingjilu = (RelativeLayout) findViewById(R.id.shareyaoqing_rl_yaoqingjilu);
		rlFanlijilu = (RelativeLayout) findViewById(R.id.shareyaoqing_rl_fanlijilu);
		rlYaoqingjilu.setOnClickListener(this);
		rlFanlijilu.setOnClickListener(this);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.shareyaoqing_titleView);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("我的邀请");
	}

	private void initTab() {
		if (yaoqingFragment == null) {
			yaoqingFragment = new ShareYaoqingYaoqingFragment();
		}
		if (!yaoqingFragment.isAdded()) {
			// 提交事务
			getSupportFragmentManager().beginTransaction()
					.add(R.id.shareyaoqing_lin_content_layout, yaoqingFragment)
					.commit();
			// 记住当前的fragment
			currentFragment = yaoqingFragment;
			// 设置文本变化
			rlFanlijilu.setBackgroundDrawable(getResources().getDrawable(R.drawable.yaoqing_or_fanli_bar_false));
			rlYaoqingjilu.setBackgroundDrawable(getResources().getDrawable(R.drawable.yaoqing_or_fanli_bar_true));

		}

	}

	private void initTab2() {
		if (fanliFragment == null) {
			fanliFragment = new ShareYaoqingfanliFragment();
		}
		if (!fanliFragment.isAdded()) {
			// 提交事务
			getSupportFragmentManager().beginTransaction()
					.add(R.id.shareyaoqing_lin_content_layout, fanliFragment)
					.commit();
			// 记住当前的fragment
			currentFragment = fanliFragment;
			// 设置文本变化
			rlYaoqingjilu.setBackgroundDrawable(getResources().getDrawable(R.drawable.yaoqing_or_fanli_bar_false));
			rlFanlijilu.setBackgroundDrawable(getResources().getDrawable(R.drawable.yaoqing_or_fanli_bar_true));

		}

	}

	// 点击第一个底部tab
	private void clickTab1Layout() {
		if (yaoqingFragment == null) {
			yaoqingFragment = new ShareYaoqingYaoqingFragment();
		}
		addOrShowFragment(getSupportFragmentManager().beginTransaction(),
				yaoqingFragment);
		rlYaoqingjilu.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.yaoqing_or_fanli_bar_true));
		rlFanlijilu.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.yaoqing_or_fanli_bar_false));
	}

	// 点击第二个底部tab
	private void clickTab2Layout() {
		if (fanliFragment == null) {
			fanliFragment = new ShareYaoqingfanliFragment();
		}
		addOrShowFragment(getSupportFragmentManager().beginTransaction(),fanliFragment);
		rlYaoqingjilu.setBackgroundDrawable(getResources().getDrawable(R.drawable.yaoqing_or_fanli_bar_false));
		rlFanlijilu.setBackgroundDrawable(getResources().getDrawable(R.drawable.yaoqing_or_fanli_bar_true));
	}

	@Override
	protected void getData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shareyaoqing_rl_yaoqingjilu:
			clickTab1Layout();
			break;
		case R.id.shareyaoqing_rl_fanlijilu:
			clickTab2Layout();
			break;

		default:
			break;
		}

	}

	private void addOrShowFragment(FragmentTransaction transaction,
			Fragment fragment) {
		if (currentFragment == fragment)
			return;

		if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
			transaction.hide(currentFragment)
					.add(R.id.shareyaoqing_lin_content_layout, fragment)
					.commit();
		} else {
			transaction.hide(currentFragment).show(fragment).commit();
		}

		currentFragment = fragment;
	}

}
