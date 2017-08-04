package com.delevin.boluolcs.fragmentactivity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delevin.boluolcs.adapter.FragmentAdapter;
import com.delevin.boluolcs.fragmnet.FilterJiaxiFragment;
import com.delevin.boluolcs.fragmnet.FilterRedPacketFragment;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

/**
 *  @author 李红涛  @version 创建时间：2017-1-6 下午1:53:47    类说明 
 */

public class FilterFragmentActivity extends FragmentActivity {

	// 两个滑动页面
	private ViewPager viewPager;
	private FragmentAdapter adapter;
	private List<Fragment> listFragment;
	private TitleView tvTitle;
	private TextView redpackget;
	private TextView jiaxijuan;
	private LinearLayout layoutRed;
	private LinearLayout layoutJiaxi;
	private ImageView imgLine;

	// ViewPager的当前选中页
	private int currentIndex;
	// 屏幕的宽度
	private int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fiter_redpacket);
		// 设置状态栏一体化
		View statusBarview = findViewById(R.id.statusBarview);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.fiter_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("可用优惠券");

		imgLine = (ImageView) findViewById(R.id.fiter_img_line);
		redpackget = (TextView) findViewById(R.id.fiter_redPacket);
		jiaxijuan = (TextView) findViewById(R.id.fiter_jiaxijuan);
		layoutRed = (LinearLayout) findViewById(R.id.fiter_layout_redPacket);
		layoutJiaxi = (LinearLayout) findViewById(R.id.fiter_layout_jiaxijuan);
		viewPager = (ViewPager) findViewById(R.id.fiter_viewpager);

		layoutRed.setOnClickListener(new MyOnClickListenser(0));
		layoutJiaxi.setOnClickListener(new MyOnClickListenser(1));

		initFragment();
		initTabLineWidth();
	}

	private void initFragment() {
		FilterRedPacketFragment zichanFragment = new FilterRedPacketFragment();
		FilterJiaxiFragment shouyiFragment = new FilterJiaxiFragment();
		listFragment = new ArrayList<Fragment>();
		listFragment.add(zichanFragment);
		listFragment.add(shouyiFragment);
		adapter = new FragmentAdapter(this.getSupportFragmentManager(),listFragment);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				resetTextView();
				switch (position) {
				case 0:
					redpackget.setTextColor(getResources().getColor(R.color.boluo_Yellow));
					break;
				case 1:
					jiaxijuan.setTextColor(getResources().getColor(R.color.boluo_Yellow));
					break;
				// case 2:
				// mTabContactsTv.setTextColor(Color.BLUE);
				// break;
				}
				currentIndex = position;

			}

			/**
			 * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
			 * offsetPixels:当前页面偏移的像素位置
			 */
			@Override
			public void onPageScrolled(int position, float offset,
					int offsetPixels) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imgLine
						.getLayoutParams();

				Log.e("offset:", offset + "");
				/**
				 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
				 * 设置mTabLineIv的左边距 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1;
				 * 1->0
				 */

				if (currentIndex == 0 && position == 0)// 0->1
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex
							* (screenWidth / 2));

				} else if (currentIndex == 1 && position == 0) // 1->0
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 2) + currentIndex
							* (screenWidth / 2));

				} else if (currentIndex == 1 && position == 1) // 1->2
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex
							* (screenWidth / 2));
				} else if (currentIndex == 2 && position == 1) // 2->1
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 2) + currentIndex
							* (screenWidth / 2));
				}
				imgLine.setLayoutParams(lp);

			}

			// state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
	 */
	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imgLine
				.getLayoutParams();
		lp.width = screenWidth / 2;
		imgLine.setLayoutParams(lp);
	}

	/**
	 * 重置颜色
	 */
	private void resetTextView() {
		redpackget.setTextColor(Color.BLACK);
		jiaxijuan.setTextColor(Color.BLACK);
		// mTabContactsTv.setTextColor(Color.BLACK);
	}

	public class MyOnClickListenser implements OnClickListener {

		private int index = 0;

		public MyOnClickListenser(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			resetTextView();
			switch (v.getId()) {
			case R.id.my_assets_lin_zongzichan:
				redpackget.setTextColor(getResources().getColor(
						R.color.boluo_Yellow));
				break;

			case R.id.my_assets_lin_zongshouyi:
				jiaxijuan.setTextColor(getResources().getColor(
						R.color.boluo_Yellow));
				break;

			}
			viewPager.setCurrentItem(index);
		}
	}

}
