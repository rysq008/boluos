package com.delevin.boluolcs.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.delevin.application.Myapplication;
import com.delevin.boluolcs.adapter.ZhichiBankAdapter;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.bean.BeanZhichiBank;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.ListView.XListView;
import com.delevin.boluolcs.view.ListView.XListView.IXListViewListener;
import com.pusupanshi.boluolicai.R;

public class ZhichiBankActivity extends BaseActivity {

	private XListView zijinListView;
	private List<BeanZhichiBank> datas;
	private LinearLayout zijinJiaZaiLayout;
	private ImageView zijinJiaZaiImg;
	private Handler mHandler;
	private ZhichiBankAdapter adapter;
	// private ImageView zijinJiaZaiImgNone;
	private RelativeLayout rlNotData;
	private int index = 1;

	@Override
	protected void findViews() {

		setContentView(R.layout.boluos_listview_jiazai);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_listview_jiazai);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("支持银行");

		zijinListView = (XListView) findViewById(R.id.listview_jiazai_listView);
		zijinJiaZaiLayout = (LinearLayout) findViewById(R.id.listview_jiazai_layout);
		zijinJiaZaiImg = (ImageView) findViewById(R.id.listview_jiazai_img);
		// zijinJiaZaiImgNone = (ImageView)
		// findViewById(R.id.listview_jiazai_none);
		rlNotData = (RelativeLayout) findViewById(R.id.listview_jiazai_rl_none);
		datas = new ArrayList<BeanZhichiBank>();
		zijinListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		zijinListView.setDividerHeight(0);
		zijinListView.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
			}

			@Override
			public void onLoadMore() {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (datas.size() == 0)
							return;
						else
							getDatas();
						adapter.notifyDataSetChanged();
						onLoad();
					}
				}, 2000);
			}
		});
		mHandler = new Handler();
	}

	@Override
	protected void getData() {
		getDatas();
	}

	private void getDatas() {
		// QntUtils.getURL(BeanUrl.zhichiBank, BoluoUtils.getShareOneData(
		// getApplicationContext(), "phone"))
		// + index
		String strUrl = BeanUrl.ZHICHIBANK_STRING + index;
		ProessDilogs.getProessAnima(zijinJiaZaiImg, this);
		Myapplication.okhttpManger.sendComplexForm(this, false, strUrl, null,
				new Funck4() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						try {

							if (jsonObject.getString("code").equals("10000")) {
								ProessDilogs.closeAnimation(zijinJiaZaiImg,
										zijinJiaZaiLayout);
							}
							JSONArray array = jsonObject
									.getJSONArray("content");
							List<BeanZhichiBank> list = null;
							if (array.length() == 0) {
								if (index == 1) {
									rlNotData.setVisibility(View.VISIBLE);
								}
								zijinListView.setPullLoadEnable(false);
								Toast.makeText(getApplicationContext(),
										"已加载完毕", Toast.LENGTH_SHORT).show();
							} else {
								list = JSON.parseArray(array.toString(),
										BeanZhichiBank.class);
								zijinListView.setPullLoadEnable(true);
							}
							datas.addAll(list);
							if (datas.size() == 0)
								rlNotData.setVisibility(View.VISIBLE);
							index += 1;
							adapter = new ZhichiBankAdapter(
									getApplicationContext(), datas,
									R.layout.item_zhichi_bank);
							adapter.notifyDataSetChanged();
							zijinListView.setAdapter(adapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void onLoad() {
		zijinListView.stopLoadMore();
	}

}
