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
import com.delevin.boluolcs.fragmnet.MyZongShouyiFragment;
import com.delevin.boluolcs.fragmnet.MyZongzichanFragment;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

/**
 *  @author 李红涛  @version 创建时间：2017-1-6 下午1:53:47    类说明 
 */

public class MyAssetsActivity extends FragmentActivity {

	// 两个滑动页面
	private ViewPager viewPager;
	private FragmentAdapter adapter;
	private List<Fragment> listFragment;
	private TitleView tvTitle;
	private TextView tvZongzichan;
	private TextView tvZongshouyi;
	private LinearLayout linZongzichan;
	private LinearLayout linZongshouyi;
	private ImageView imgLine;

	// ViewPager的当前选中页
	private int currentIndex;
	// 屏幕的宽度
	private int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityragment_assets);
		// 设置状态栏一体化
		View statusBarview = findViewById(R.id.statusBarview);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.my_assets_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("资产信息");

		imgLine = (ImageView) findViewById(R.id.my_assets_img_line);
		tvZongzichan = (TextView) findViewById(R.id.my_assets_tv_zongzichan);
		tvZongshouyi = (TextView) findViewById(R.id.my_assets_tv_zongshouyi);
		linZongzichan = (LinearLayout) findViewById(R.id.my_assets_lin_zongzichan);
		linZongshouyi = (LinearLayout) findViewById(R.id.my_assets_lin_zongshouyi);
		viewPager = (ViewPager) findViewById(R.id.my_assets_viewpager);

		linZongzichan.setOnClickListener(new MyOnClickListenser(0));
		linZongshouyi.setOnClickListener(new MyOnClickListenser(1));

		initFragment();
		initTabLineWidth();
	}

	private void initFragment() {
		MyZongzichanFragment zichanFragment = new MyZongzichanFragment();
		MyZongShouyiFragment shouyiFragment = new MyZongShouyiFragment();
		listFragment = new ArrayList<Fragment>();
		listFragment.add(zichanFragment);
		listFragment.add(shouyiFragment);
		adapter = new FragmentAdapter(this.getSupportFragmentManager(),
				listFragment);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				resetTextView();
				switch (position) {
				case 0:
					tvZongzichan.setTextColor(getResources().getColor(
							R.color.boluo_Yellow));
					break;
				case 1:
					tvZongshouyi.setTextColor(getResources().getColor(
							R.color.boluo_Yellow));
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
		tvZongzichan.setTextColor(Color.BLACK);
		tvZongshouyi.setTextColor(Color.BLACK);
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
				tvZongzichan.setTextColor(getResources().getColor(
						R.color.boluo_Yellow));
				break;

			case R.id.my_assets_lin_zongshouyi:
				tvZongshouyi.setTextColor(getResources().getColor(
						R.color.boluo_Yellow));
				break;

			}
			viewPager.setCurrentItem(index);
		}
	}

}