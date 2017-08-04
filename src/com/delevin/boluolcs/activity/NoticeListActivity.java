package com.delevin.boluolcs.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
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
import com.delevin.boluolcs.adapter.NoticeListAdapter;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.bean.NoticeList;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.ListView.XListView;
import com.delevin.boluolcs.view.ListView.XListView.IXListViewListener;
import com.delevin.jsandroid.JSAndroidActivity;
import com.pusupanshi.boluolicai.R;

public class NoticeListActivity extends BaseActivity implements OnItemClickListener{
	
	private XListView xListView;
	private List<NoticeList> datas;
	private LinearLayout linJiaZai;
	private ImageView imgJiaZai;
	private Handler mHandler;
	private NoticeListAdapter adapter;
	// private ImageView zijinJiaZaiImgNone;
	private RelativeLayout rlNotData;

	@Override
	protected void findViews() {
		setContentView(R.layout.boluos_listview_gonggao);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_listview_gonggao);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("公告");
		xListView = (XListView) findViewById(R.id.listview_gonggao_listView);
		linJiaZai = (LinearLayout) findViewById(R.id.listview_gonggao_layout);
		imgJiaZai = (ImageView) findViewById(R.id.listview_gonggao_img);
		// zijinJiaZaiImgNone = (ImageView)
		// findViewById(R.id.listview_jiazai_none);
		rlNotData = (RelativeLayout) findViewById(R.id.listview_gonggao_rl_none);
		datas = new ArrayList<NoticeList>();
		xListView.setOnItemClickListener(this);
		xListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		xListView.setDividerHeight(0);
		xListView.setXListViewListener(new IXListViewListener() {
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
							getData();
						adapter.notifyDataSetChanged();
						// onLoad();
					}
				}, 2000);
			}
		});
		getData();
		mHandler = new Handler();
		

	}

	@Override
	protected void getData() {
		ProessDilogs.getProessAnima(imgJiaZai, this);
		Myapplication.okhttpManger.sendComplexForm(this,false,
				QntUtils.getURL(BeanUrl.NOTICELIST_STRING,BoluoUtils.getShareOneData(getApplicationContext(), "phone")),
				null, new Funck4() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						try {
							if (jsonObject.getString("code").equals("10000")) {
								ProessDilogs.closeAnimation(imgJiaZai,linJiaZai);
								linJiaZai.setVisibility(View.GONE);
							}
							JSONArray array = jsonObject.getJSONArray("content");
							List<NoticeList> list = null;
							if (array.length() == 0) {
								rlNotData.setVisibility(View.VISIBLE);
								// zijinListView.setPullLoadEnable(false);
								Toast.makeText(getApplicationContext(), "已加载完毕",
										Toast.LENGTH_SHORT).show();
							} else {
								rlNotData.setVisibility(View.GONE);
								list = JSON.parseArray(array.toString(),
										NoticeList.class);
								// zijinListView.setPullLoadEnable(true);
							}
							datas.addAll(list);
							list.clear();
							if (datas.size() == 0)
								rlNotData.setVisibility(View.VISIBLE);
							// index += 1;
							adapter = new NoticeListAdapter(getApplicationContext(),
									datas, R.layout.item_notice_list);
							adapter.notifyDataSetChanged();
							xListView.setAdapter(adapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		NoticeList noticeList=datas.get(position);
		Intent intentNotice = new Intent(NoticeListActivity.this,
				JSAndroidActivity.class);// BidDetalsActivity
		intentNotice.putExtra("title", "公告");
		intentNotice.putExtra("jsUrl", noticeList.getUrl());
		intentNotice.putExtra("js", "js");
		startActivity(intentNotice);
		
	}

}
