package com.delevin.boluolcs.fragmnet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.delevin.application.Myapplication;
import com.delevin.boluolcs.adapter.RedPacketAdapter;
import com.delevin.boluolcs.bean.BeanRedPacket;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.ListView.XListView;
import com.delevin.boluolcs.view.ListView.XListView.IXListViewListener;
import com.pusupanshi.boluolicai.R;

public class MyRedPacketFragment extends Fragment {
	private XListView zijinListView;
	private List<BeanRedPacket> datas;
	private LinearLayout zijinJiaZaiLayout;
	private ImageView zijinJiaZaiImg;
	private Handler mHandler;
	private RedPacketAdapter adapter;
	// private ImageView zijinJiaZaiImgNone;
	private RelativeLayout rlNotData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.biluos_listview_hongbao,
				container, false);
		zijinListView = (XListView) view
				.findViewById(R.id.listview_hongbao_listView);
		zijinJiaZaiLayout = (LinearLayout) view
				.findViewById(R.id.listview_hongbao_layout);
		zijinJiaZaiImg = (ImageView) view
				.findViewById(R.id.listview_hongbao_img);
		// zijinJiaZaiImgNone = (ImageView)
		// findViewById(R.id.listview_jiazai_none);
		rlNotData = (RelativeLayout) view
				.findViewById(R.id.listview_hongbao_rl_none);
		datas = new ArrayList<BeanRedPacket>();
		// zijinListView.setOnItemClickListener(this);
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
						// onLoad();
					}
				}, 2000);
			}
		});
		getDatas();
		mHandler = new Handler();
		return view;
	}

	private void getDatas() {
		ProessDilogs.getProessAnima(zijinJiaZaiImg, getActivity());
		Myapplication.okhttpManger.sendComplexForm(getActivity(),
				false,
				QntUtils.getURL(BeanUrl.RED_PACKET_STRING,BoluoUtils.getShareOneData(getActivity(), "phone")),
				null, new Funck4() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						try {
							if (jsonObject.getString("code").equals("10000")) {
								ProessDilogs.closeAnimation(zijinJiaZaiImg,zijinJiaZaiLayout);
							}
							JSONArray array = jsonObject.getJSONArray("content");
							List<BeanRedPacket> list = null;
							if (array.length() == 0) {
								rlNotData.setVisibility(View.VISIBLE);
								// zijinListView.setPullLoadEnable(false);
								Toast.makeText(getActivity(), "已加载完毕",
										Toast.LENGTH_SHORT).show();
							} else {

								list = JSON.parseArray(array.toString(),
										BeanRedPacket.class);
								// zijinListView.setPullLoadEnable(true);
							}
							datas.addAll(list);
							list.clear();
							if (datas.size() == 0)
								rlNotData.setVisibility(View.VISIBLE);
							// index += 1;
							adapter = new RedPacketAdapter(getActivity(),
									datas, R.layout.item_red_packet);
							adapter.notifyDataSetChanged();
							zijinListView.setAdapter(adapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

}
