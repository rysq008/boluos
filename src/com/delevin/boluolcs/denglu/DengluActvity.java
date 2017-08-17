package com.delevin.boluolcs.denglu;

import java.util.ArrayList;
import java.util.List;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delevin.boluolcs.base.activity.BaseFragmentActivity;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-13 上午10:10:09 类说明
 */
public class DengluActvity extends BaseFragmentActivity implements
		OnPageChangeListener {

	// 两个滑动页面
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mDatas;
	// 控件
	private TextView text_seller_description = null;
	private TextView text_purchase_process = null;
	private LinearLayout ll_seller_description = null;
	private LinearLayout ll_purchase_process = null;
	private ImageView img_line;

	// 滑动条颜色
	private int select_color;
	private int unselect_color;

	private int mScreen1_4;

	/** 当前视图宽度 **/
	private Integer viewPagerW = 0;

	private static int width = 0;

	@Override
	protected void findViews() {
		setContentView(R.layout.login_denglu);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		TitleView titleView = (TitleView) findViewById(R.id.titleView_denglu);
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("登录");
		initLine();
		initView();
		initFragment();
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化line
	 */
	private void initLine() {
		img_line = (ImageView) findViewById(R.id.imgV_line);
		mScreen1_4 = getScreenWidth(this) / 2;
		LayoutParams lp = img_line.getLayoutParams();
		lp.width = mScreen1_4;
		img_line.setLayoutParams(lp);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		// 获取颜色
		select_color = getResources().getColor(R.color.boluo_Yellow);
		unselect_color = getResources().getColor(R.color.black);
		text_seller_description = (TextView) findViewById(R.id.textV_seller_description);
		text_purchase_process = (TextView) findViewById(R.id.textV_purchase_process);
		ll_seller_description = (LinearLayout) findViewById(R.id.linearLa_seller_description);
		ll_purchase_process = (LinearLayout) findViewById(R.id.linearLa_purchase_process);
		ll_seller_description.setOnClickListener(new MyOnClickListenser(0));
		ll_purchase_process.setOnClickListener(new MyOnClickListenser(1));
		mViewPager = (ViewPager) findViewById(R.id.mViewpagerS);
		mDatas = new ArrayList<Fragment>();
	}

	/**
	 * 初始化fragment
	 */
	private void initFragment() {
		DengluFragnment mSDF = new DengluFragnment();
		YanZhengMaDengluFragment mCPF = new YanZhengMaDengluFragment();
		mDatas.add(mSDF);
		mDatas.add(mCPF);
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mDatas == null ? 0 : mDatas.size();
			}

			@Override
			public Fragment getItem(int position) {
				return mDatas.get(position);
			}
		};
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(0);
	}

	@Override
	public void onPageScrollStateChanged(int position) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		viewPagerW = mViewPager.getWidth() + mViewPager.getPageMargin();
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) img_line
				.getLayoutParams();
		// 关键算法
		lp.leftMargin = (int) ((int) (mScreen1_4 * position) + (((double) positionOffsetPixels / viewPagerW) * mScreen1_4));
		img_line.setLayoutParams(lp);
	}

	@Override
	public void onPageSelected(int position) {
		resetTextColor();
		switch (mViewPager.getCurrentItem()) {
		case 0:
			text_seller_description.setTextColor(select_color);
			break;
		case 1:
			text_purchase_process.setTextColor(select_color);
			break;
		}
	}

	/**
	 * 点击文字进行切换
	 * 
	 * @author wuxl
	 * 
	 */
	public class MyOnClickListenser implements OnClickListener {

		private int index = 0;

		public MyOnClickListenser(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			resetTextColor();
			switch (v.getId()) {
			case R.id.linearLa_seller_description:
				text_seller_description.setTextColor(select_color);
				break;

			case R.id.linearLa_purchase_process:
				text_purchase_process.setTextColor(select_color);
				break;

			}
			mViewPager.setCurrentItem(index);
		}
	}

	/**
	 * 将文字设置为未选中时的颜色
	 */
	private void resetTextColor() {
		text_seller_description.setTextColor(unselect_color);
		text_purchase_process.setTextColor(unselect_color);
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		if (width == 0) {
			WindowManager manager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = manager.getDefaultDisplay();
			width = display.getWidth();
		}
		return width;
	}

	/** 返回上一个状态 */
	public void fanhui(View view) {
		new Thread() {
			public void run() {
				try {
					Instrumentation instrumentation = new Instrumentation();
					instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

}
