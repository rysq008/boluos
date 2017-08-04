package com.delevin.boluolcs.fragmnet;

import com.delevin.boluolcs.adapter.DescfRedPacketAdapter;
import com.delevin.boluolcs.adapter.RedPacketAdapter;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.delevin.application.Myapplication;
import com.delevin.boluolcs.bean.BeanRedPacket;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.ListView.XListView;
import com.delevin.boluolcs.view.ListView.XListView.IXListViewListener;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-5 下午1:45:22    类说明 
 */
public class FilterRedPacketFragment extends BaseFragment implements
		OnItemClickListener {

	private XListView zijinListView;
	private List<BeanRedPacket> datas;
	private LinearLayout zijinJiaZaiLayout;
	private ImageView zijinJiaZaiImg;
	private Handler mHandler;
	private DescfRedPacketAdapter adapter;
	// private ImageView zijinJiaZaiImgNone;
	private RelativeLayout rlNotData;

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.boluos_listview_jiazai,
				container, false);

		TitleView titleView = (TitleView) view
				.findViewById(R.id.titleView_listview_jiazai);
		View statusBarview = view.findViewById(R.id.statusBarview);
		statusBarview.setVisibility(View.GONE);
		titleView.setVisibility(View.GONE);
		zijinListView = (XListView) view
				.findViewById(R.id.listview_jiazai_listView);
		zijinJiaZaiLayout = (LinearLayout) view
				.findViewById(R.id.listview_jiazai_layout);
		zijinJiaZaiImg = (ImageView) view
				.findViewById(R.id.listview_jiazai_img);
		// zijinJiaZaiImgNone = (ImageView)
		// findViewById(R.id.listview_jiazai_none);
		rlNotData = (RelativeLayout) view
				.findViewById(R.id.listview_jiazai_rl_none);
		datas = new ArrayList<BeanRedPacket>();
		zijinListView.setOnItemClickListener(this);
		zijinListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		zijinListView.setDividerHeight(0);
		// zijinListView.setXListViewListener(new IXListViewListener() {
		// @Override
		// public void onRefresh() {}
		// @Override
		// public void onLoadMore() {
		// mHandler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// if(datas.size()==0) return;
		// else getDatas();adapter.notifyDataSetChanged();}},2000);}});
		// mHandler = new Handler();
		return view;
	}

	@Override
	protected void getFindById(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getData() {
		getDatas();
	}

	private void getDatas() {
		ProessDilogs.getProessAnima(zijinJiaZaiImg, getActivity());
		Map<String, String> params = new HashMap<String, String>();
		params.put("start_money",
				getActivity().getIntent().getStringExtra("start_money"));
		Myapplication.okhttpManger.sendComplexForm(
				getActivity(),
				false,
				QntUtils.getURL(BeanUrl.SHAIXUAN_RED_STRING,
						BoluoUtils.getShareOneData(getActivity(), "phone")),
				params, new Funck4() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						try {

							if (jsonObject.getString("code").equals("10000")) {
								ProessDilogs.closeAnimation(zijinJiaZaiImg,
										zijinJiaZaiLayout);
							}

							JSONArray array = jsonObject
									.getJSONArray("content");
							List<BeanRedPacket> list = null;
							if (array.length() == 0) {
								zijinListView.setPullLoadEnable(false);
								Toast.makeText(getActivity(), "已加载完毕",
										Toast.LENGTH_SHORT).show();
								rlNotData.setVisibility(View.VISIBLE);
							} else {
								list = JSON.parseArray(array.toString(),
										BeanRedPacket.class);
							}
							datas.addAll(list);
							if (datas.size() == 0)
								rlNotData.setVisibility(View.VISIBLE);
							adapter = new DescfRedPacketAdapter(getActivity(),
									datas, R.layout.item_red_packet);
							adapter.notifyDataSetChanged();
							zijinListView.setAdapter(adapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	// private void onLoad() {
	// zijinListView.stopLoadMore();
	// }
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

}
