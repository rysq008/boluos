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
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.delevin.application.Myapplication;
import com.delevin.boluolcs.adapter.TouziAdapter;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanTouzi;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.TitleView.OnRightButtonClickListener;
import com.delevin.boluolcs.view.ListView.XListView;
import com.delevin.boluolcs.view.ListView.XListView.IXListViewListener;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-4 下午2:37:58    类说明 
 */
public class MyTouziActivity extends BaseActivity implements
		OnItemClickListener {
	private XListView touziListView;
	private List<BeanTouzi> datas;
	private LinearLayout touziJiazaiLayout;
	private ImageView touziImg;
	private Handler mHandler;
	private TouziAdapter adapter;
	private RelativeLayout rlNotData;
	private int index = 1;
	private TextView tvNotData;

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
		titleView.initViewsVisible(true, true, true, true);
		
		titleView.setAppTitle("投资明细");
		titleView.setRightTitle("截标记录");
		titleView.setOnRightButtonClickListener(new OnRightButtonClickListener() {
			
			@Override
			public void OnRightButtonClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), JiebiaoJiluActivity.class));
			}
		});
		touziListView = (XListView) findViewById(R.id.listview_jiazai_listView);
		touziJiazaiLayout = (LinearLayout) findViewById(R.id.listview_jiazai_layout);
		touziImg = (ImageView) findViewById(R.id.listview_jiazai_img);
		// touziImgNone = (ImageView) findViewById(R.id.listview_jiazai_none);
		rlNotData = (RelativeLayout) findViewById(R.id.listview_jiazai_rl_none);
		tvNotData = (TextView) findViewById(R.id.listview_jiazai_tv_none);
		datas = new ArrayList<BeanTouzi>();
		touziListView.setOnItemClickListener(this);
		touziListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		touziListView.setDividerHeight(0);
		touziListView.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {

			}

			@Override
			public void onLoadMore() {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (datas.size() == 0) {
							return;
						} else {
							getDatas();
							adapter.notifyDataSetChanged();
							onLoad();
						}
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
		ProessDilogs.getProessAnima(touziImg, this);
		Myapplication.okhttpManger.sendComplexForm(this,false,QntUtils.getURL(BeanUrl.TOUZI_MINGXI_STRING, BoluoUtils.getShareOneData(getApplicationContext(), "phone"))+ index+"?index=1", null, new Funck4() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						try {
							if (jsonObject.getString("code").equals("10000")) {
								ProessDilogs.closeAnimation(touziImg,
										touziJiazaiLayout);
							}
							JSONArray array = jsonObject
									.getJSONArray("content");
							List<BeanTouzi> list = null;
							if (array.length() == 0) {
								if (index == 1) {
									rlNotData.setVisibility(View.VISIBLE);
									tvNotData.setText("暂无在投记录");
								}
								touziListView.setPullLoadEnable(false);
								Toast.makeText(getApplicationContext(),
										"已加载完毕", Toast.LENGTH_SHORT).show();
							} else {
								list = JSON.parseArray(array.toString(),
										BeanTouzi.class);
								touziListView.setPullLoadEnable(true);
							}
							datas.addAll(list);
							if (datas.size() == 0) {
								rlNotData.setVisibility(View.VISIBLE);
								tvNotData.setText("暂无在投记录");
							}
							index += 1;
							adapter = new TouziAdapter(getApplicationContext(),
									datas, R.layout.touzi_mingxi_item);
							adapter.notifyDataSetChanged();
							touziListView.setAdapter(adapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void onLoad() {
		touziListView.stopLoadMore();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		BeanTouzi touzi = datas.get(arg2);
		double d1 = QntUtils.getDouble(touzi.getInvest_money());
		double d2 = QntUtils.getDouble(touzi.getPro_fit());
		Intent intent = new Intent(MyTouziActivity.this,
				MyTouziCoarseDetailsActivity.class);// BidDetalsActivity
		intent.putExtra("chanPinId", touzi.getId());// 项目名称
		intent.putExtra("product_name", touzi.getProduct_name());// 项目ID
		intent.putExtra("invest_time", touzi.getInvest_time());// 项目期限
		intent.putExtra("end_time", touzi.getEnd_time());// 项目状态
		intent.putExtra("interest_time", touzi.getInterest_time());//
		intent.putExtra("invest_status", touzi.getInvest_status());//
		intent.putExtra("invest_money", touzi.getInvest_money());//
		intent.putExtra("order_id", touzi.getOrder_id());//
		intent.putExtra("yuqiMoney", QntUtils.getFormat(d2 - d1));//
		intent.putExtra("rate", touzi.getRate());//
		intent.putExtra("rate_increase", touzi.getRate_increase());//
		intent.putExtra("link", touzi.getLink());//
		intent.putExtra("repay_type", touzi.getRepay_type());//
		intent.putExtra("hongbao", touzi.getHongbao());//
		intent.putExtra("isnew", touzi.getIsnew());//
		startActivity(intent);

	}

}
