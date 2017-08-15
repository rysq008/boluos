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
import android.text.TextUtils;
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
import com.delevin.boluolcs.adapter.TouziMoreObjectAdapter;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanMoreObject;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.NetUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.ListView.XListView;
import com.delevin.boluolcs.view.ListView.XListView.IXListViewListener;
import com.pusupanshi.boluolicai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-2 上午11:02:29 类说明
 */
public class TouziMoreObjectActivity extends BaseActivity implements
		OnItemClickListener {

	private XListView zijinListView;
	private List<BeanMoreObject> datas;
	private LinearLayout zijinJiaZaiLayout;
	private ImageView zijinJiaZaiImg;
	private Handler mHandler;
	private TouziMoreObjectAdapter adapter;
	// private ImageView zijinJiaZaiImgNone;
	private RelativeLayout rlNotData;
	private int index = 1;
	private String type;
	public static TouziMoreObjectActivity touziMoreObjectActivity;

	@Override
	protected void findViews() {

		setContentView(R.layout.boluos_listview_jiazai);
		touziMoreObjectActivity = this;
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
		titleView.setAppTitle("项目列表");
		type = getIntent().getStringExtra("type");
		zijinListView = (XListView) findViewById(R.id.listview_jiazai_listView);
		zijinJiaZaiLayout = (LinearLayout) findViewById(R.id.listview_jiazai_layout);
		zijinJiaZaiImg = (ImageView) findViewById(R.id.listview_jiazai_img);
		// zijinJiaZaiImgNone = (ImageView)
		// findViewById(R.id.listview_jiazai_none);
		rlNotData = (RelativeLayout) findViewById(R.id.listview_jiazai_rl_none);
		datas = new ArrayList<BeanMoreObject>();
		zijinListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		zijinListView.setDividerHeight(0);
		zijinListView.setOnItemClickListener(this);
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
		ProessDilogs.getProessAnima(zijinJiaZaiImg, this);
		Myapplication.okhttpManger.sendComplexForm(this, false,
				BeanUrl.MOREOBJECT_STRING.replace("type", type) + index, null,
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
							List<BeanMoreObject> list = null;
							if (array.length() == 0) {
								if (index == 1) {
									rlNotData.setVisibility(View.VISIBLE);
								}
								zijinListView.setPullLoadEnable(false);
								Toast.makeText(getApplicationContext(),
										"已加载完毕", Toast.LENGTH_SHORT).show();
							} else {
								list = JSON.parseArray(array.toString(),
										BeanMoreObject.class);
								zijinListView.setPullLoadEnable(true);
							}
							datas.addAll(list);
							if (datas.size() == 0)
								rlNotData.setVisibility(View.VISIBLE);
							index += 1;
							adapter = new TouziMoreObjectAdapter(
									getApplicationContext(), datas,
									R.layout.boluo_touzi_more_item);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// String memberId = BoluoUtils.getShareOneData(getApplicationContext(),
		// "memberId");
		// if (!BoluoUtils.isEmpty(memberId)) {
		// if (TextUtils.equals(datas.get(arg2).getProduct_status(), "2")) {
		if (NetUtils.getNetWorkState(touziMoreObjectActivity) != -1) {
			Intent intent = new Intent(TouziMoreObjectActivity.this,
					BidDetalsActivity.class);
			intent.putExtra("bidId", datas.get(arg2).getId());
			intent.putExtra("status", datas.get(arg2).getProduct_status());
			if (TextUtils.equals(type, "1")) {
				intent.putExtra("isNewer", false);
			} else {
				intent.putExtra("isNewer", true);
			}
			startActivity(intent);
		} else {
			BoluoUtils.getDilogDome(touziMoreObjectActivity, "温馨提示",
					"您当前的网络不可用", "确定");
		}
		// }
		// }else {
		// startActivity(new
		// Intent(TouziMoreObjectActivity.this,ZhuActivity.class));
		// finish();
		// }

	}
}
