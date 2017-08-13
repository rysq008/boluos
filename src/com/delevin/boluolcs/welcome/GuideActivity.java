package com.delevin.boluolcs.welcome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.delevin.boluolcs.base.activity.BaseFragmentActivity;
import com.delevin.boluolcs.denglu.ZhuActivity;
import com.delevin.boluolcs.main.MainActivity;
import com.delevin.boluolcs.utils.APPName;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.pusupanshi.boluolicai.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class GuideActivity extends BaseFragmentActivity {

	// private ImageView[] dotsImages; // 存放引导小圆点
	//
	// private ImageView dotsImage; // 小圆点图片

	private List<Fragment> fragments; // 创建存放Fragment集合

	private GuideFragment1 fragment1;

	private GuideFragment2 fragment2;
	private GuideFragment3 fragment3;

	private GuideFragment4 fragment4;
	/* 初始化控件 */
	private ViewPager vpGuide;

	// private ViewGroup llGuideDots;
	@Override
	protected void findViews() {
		// 设置状态栏一体化
		setContentView(R.layout.boluos_main_guide);

		vpGuide = (ViewPager) findViewById(R.id.vpGuide);
		// llGuideDots = (ViewGroup) findViewById(R.id.llGuideDots);

//		// 设置状态栏一体化
//		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
//
//			getWindow().setFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//		}

		setViewPager();

		// setDots();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	// /** 设置小圆点 */
	// private void setDots() {
	//
	// dotsImages = new ImageView[fragments.size()]; // 小圆点数组，大小是图片的个数
	//
	// for (int i = 0; i < fragments.size(); i++) {
	//
	// dotsImage = new ImageView(GuideActivity.this);
	//
	// dotsImage.setLayoutParams(new LayoutParams(40, 40)); // 小圆点大小
	//
	// dotsImage.setPadding(10, 0, 10, 0); // 小圆点间隔
	//
	// dotsImages[i] = dotsImage; // 将小圆点添加到数组中
	//
	// if (i == 0) {
	//
	// dotsImages[i].setImageDrawable(getResources().getDrawable(R.drawable.boluo_guide_ture));
	//
	// } else {
	//
	// dotsImages[i].setImageDrawable(getResources().getDrawable(R.drawable.boluo_guide_false));
	//
	// }
	//
	// llGuideDots.addView(dotsImages[i]); // 将imageviews添加到小圆点视图组
	// }
	// }

	/** 设置viewPager */
	private void setViewPager() {

		/* 添加碎片 */
		fragments = new ArrayList<Fragment>();

		fragment1 = new GuideFragment1();

		fragment2 = new GuideFragment2();

		fragment3 = new GuideFragment3();

		fragment4 = new GuideFragment4();

		fragments.add(fragment1);

		fragments.add(fragment2);
		fragments.add(fragment3);

		fragments.add(fragment4);

		vpGuide.setAdapter(new guideFragmentAdapter(getSupportFragmentManager()));

		vpGuide.setCurrentItem(0); // 默认第一个界面

		vpGuide.setOffscreenPageLimit(4); // 设置缓存页数

		vpGuide.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int position) {

				// for (int i = 0; i < dotsImages.length; i++) {
				//
				// dotsImages[i].setImageDrawable(getResources().getDrawable(R.drawable.boluo_guide_ture));
				// // 选中
				// if (position != i) {
				//
				// dotsImages[i].setImageDrawable(getResources().getDrawable(R.drawable.boluo_guide_false));
				// // 未选中
				//
				// }
				// }
			}
		});
	}

	/** Fragment适配器 */
	class guideFragmentAdapter extends FragmentPagerAdapter {

		public guideFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			return fragments.get(position);
		}

		@Override
		public int getCount() {

			return fragments.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			super.destroyItem(container, position, object);
		}
	}

	/**
	 * 引导最后一页的进入主程序按钮
	 * 
	 * 将按钮在本activity中实例化并设置点击监听但报错 用xutils解析注解也不好使,但不报错 在xml中设置onClick点击可以
	 */
	public void startButton(View view) {

		startActivity(new Intent(GuideActivity.this, MainActivity.class));

		GuideActivity.this.finish();
	}

	@Override
	protected void getData() {
		Map<String, String> map = new HashMap<String, String>();
		// map.put("visonCode", APPName.getVersionCode(this)+"");
		map.put("gesNum", "5");
		BoluoUtils.getShareCommit(this, map);
	}
}
