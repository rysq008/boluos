package com.delevin.boluolcs.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.base.adapter.MeiTiAdapter;
import com.delevin.boluolcs.bean.BeanMeiTi;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.ListView.XListView;
import com.delevin.jsandroid.JSAndroidActivity;
import com.pusupanshi.boluolicai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-2 上午11:02:29 类说明
 */
public class TouziMeiTiActivity extends BaseActivity implements
		OnItemClickListener {
	private XListView MeitiListView;
	private List<BeanMeiTi> datas;
	private LinearLayout MeiTiJiaZaiLayout;
	private ImageView MeiTiJiaZaiImg;
	// private Handler mHandler;
	private MeiTiAdapter adapter;
	private RelativeLayout rlNotData;

	// private int index = 1;
	// private String type;
	@SuppressLint("InlinedApi")
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
		titleView.setAppTitle("媒体报道");
		// type= getIntent().getStringExtra("type");
		MeitiListView = (XListView) findViewById(R.id.listview_jiazai_listView);
		MeiTiJiaZaiLayout = (LinearLayout) findViewById(R.id.listview_jiazai_layout);
		MeiTiJiaZaiImg = (ImageView) findViewById(R.id.listview_jiazai_img);
		rlNotData = (RelativeLayout) findViewById(R.id.listview_jiazai_rl_none);
		datas = new ArrayList<BeanMeiTi>();
		MeitiListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		MeitiListView.setDividerHeight(0);
		MeitiListView.setOnItemClickListener(this);
		// MeitiListView.setXListViewListener(new IXListViewListener() {
		// @Override
		// public void onRefresh() {}
		// @Override
		// public void onLoadMore() {
		// mHandler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// if(datas.size()==0) return;
		// else getDatas();adapter.notifyDataSetChanged();
		// }},2000);}});
		// mHandler = new Handler();
	}

	@Override
	protected void getData() {
		getDatas();
	}

	private void getDatas() {
		ProessDilogs.getProessAnima(MeiTiJiaZaiImg, this);

		Myapplication.okhttpManger.sendComplexForm(this, false,
				BeanUrl.MEITIBAODAO_STRING, null, new Funck4() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						try {

							if (jsonObject.getString("code").equals("10000")) {
								ProessDilogs.closeAnimation(MeiTiJiaZaiImg,
										MeiTiJiaZaiLayout);
							}
							JSONArray array = jsonObject
									.getJSONArray("content");
							List<BeanMeiTi> list = null;
							if (array.length() == 0) {
								// if (index==1) {
								rlNotData.setVisibility(View.VISIBLE);
								// }
								// MeitiListView.setPullLoadEnable(false);
								Toast.makeText(getApplicationContext(),
										"已加载完毕", Toast.LENGTH_SHORT).show();
							} else {
								list = JSON.parseArray(array.toString(),
										BeanMeiTi.class);
								// MeitiListView.setPullLoadEnable(true);
							}
							datas.addAll(list);
							if (datas.size() == 0)
								rlNotData.setVisibility(View.VISIBLE);
							// index += 1;
							adapter = new MeiTiAdapter(getApplicationContext(),
									datas, R.layout.activity_touzi_meiti_item);
							adapter.notifyDataSetChanged();
							MeitiListView.setAdapter(adapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(TouziMeiTiActivity.this,
				JSAndroidActivity.class);
		intent.putExtra("jsUrl", datas.get(arg2).getUrl());
		intent.putExtra("title", "媒体报道");
		intent.putExtra("js", "js");
		startActivity(intent);
	}
}
