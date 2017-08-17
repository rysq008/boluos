package com.delevin.boluolcs.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.bean.BeanjieKuan;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

public class PdfListActivity extends BaseActivity implements
		OnItemClickListener {
	// private XListView zijinListView;
	// private List<PdfList> datas;
	// private LinearLayout zijinJiaZaiLayout;
	// private ImageView zijinJiaZaiImg;
	// private Handler mHandler;
	// private PdfListAdapter adapter;
	// // private ImageView zijinJiaZaiImgNone;
	// private RelativeLayout rlNotData;
	// private int index = 1;
	private RelativeLayout rlJiekuanHetong, rlDanbaoHetong, rlDanbaohan;

	@Override
	protected void findViews() {

		setContentView(R.layout.activity_pdf_list);
		rlJiekuanHetong = (RelativeLayout) findViewById(R.id.paf_list_rl_jiekuanhetong);
		rlDanbaoHetong = (RelativeLayout) findViewById(R.id.paf_list_rl_danbaohetong);
		rlDanbaohan = (RelativeLayout) findViewById(R.id.paf_list_rl_danbaohan);
		TitleView titleView = (TitleView) findViewById(R.id.paf_list_titleView);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("项目合同");
	}

	@Override
	protected void getData() {
		getDatas();
	}

	private void getDatas() {
		String bidId = getIntent().getStringExtra("bidId");
		Myapplication.okhttpManger.sendComplexForm(
				this,
				false,
				QntUtils.getURL(BeanUrl.XIANGMUGAIKUANG_STRING,
						bidId.replace("-", ""))
						+ "3?p=1", null, new Funck4() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						try {
							JSONObject object = jsonObject
									.getJSONObject("content");
							JSONArray borrowJson = object
									.getJSONArray("borrow");
							JSONArray tongyiJson = object
									.getJSONArray("tongyi");
							JSONArray zhiyaJson = object.getJSONArray("zhiya");
							final List<BeanjieKuan> listborrow = new ArrayList<BeanjieKuan>();
							if (borrowJson.length() != 0) {
								listborrow.clear();
								for (int i = 0; i < borrowJson.length(); i++) {
									BeanjieKuan beanjieKuan = new BeanjieKuan();
									JSONObject objectborrow = borrowJson
											.getJSONObject(i);
									String path = objectborrow
											.getString("path");
									String name = objectborrow
											.getString("name");
									beanjieKuan.setName(name);
									beanjieKuan.setPath(path);
									listborrow.add(beanjieKuan);
								}
								rlJiekuanHetong
										.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												Intent intent = new Intent(
														PdfListActivity.this,
														HeTongActivity.class);
												intent.putExtra(
														"list",
														(Serializable) listborrow);
												startActivity(intent);
											}
										});
							} else {
								rlJiekuanHetong.setVisibility(View.GONE);
							}
							if (tongyiJson.length() != 0) {
								final List<BeanjieKuan> listtongyi = new ArrayList<BeanjieKuan>();
								for (int i = 0; i < tongyiJson.length(); i++) {
									BeanjieKuan beanjieKuan = new BeanjieKuan();
									JSONObject objecttongyi = tongyiJson
											.getJSONObject(i);
									String path = objecttongyi
											.getString("path");
									String name = objecttongyi
											.getString("name");
									beanjieKuan.setName(name);
									beanjieKuan.setPath(path);
									listtongyi.add(beanjieKuan);
								}
								rlDanbaohan
										.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												Intent intent = new Intent(
														PdfListActivity.this,
														HeTongActivity.class);
												intent.putExtra(
														"list",
														(Serializable) listtongyi);
												startActivity(intent);
											}
										});
							} else {
								rlDanbaohan.setVisibility(View.GONE);
							}
							if (zhiyaJson.length() != 0) {
								final List<BeanjieKuan> listzhiya = new ArrayList<BeanjieKuan>();
								for (int i = 0; i < zhiyaJson.length(); i++) {
									BeanjieKuan beanjieKuan = new BeanjieKuan();
									JSONObject objectzhiya = zhiyaJson
											.getJSONObject(i);
									String path = objectzhiya.getString("path");
									String name = objectzhiya.getString("name");
									beanjieKuan.setName(name);
									beanjieKuan.setPath(path);
									listzhiya.add(beanjieKuan);
								}
								rlDanbaoHetong
										.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												Intent intent = new Intent(
														PdfListActivity.this,
														HeTongActivity.class);
												intent.putExtra(
														"list",
														(Serializable) listzhiya);
												startActivity(intent);
											}
										});
							} else {
								rlDanbaoHetong.setVisibility(View.GONE);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}
	// private void onLoad() {
	// zijinListView.stopLoadMore();
	// }
}