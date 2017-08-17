package com.delevin.boluolcs.fragmnet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.adapter.ShareYaoqingYaoqingFragmentAdapter;
import com.delevin.boluolcs.base.fragment.BaseFragment;
import com.delevin.boluolcs.bean.BeanShareYaoqingYaoqingFragment;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.JSONUtil;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.ListView.XListView;
import com.delevin.boluolcs.view.ListView.XListView.IXListViewListener;
import com.pusupanshi.boluolicai.R;

public class ShareYaoqingYaoqingFragment extends BaseFragment {
	private XListView listView;
	private List<BeanShareYaoqingYaoqingFragment> list;
	private int index = 1;
	private LinearLayout linearLayout;
	private ImageView img;
	private RelativeLayout rlNone;
	private Handler mHandler;
	private ShareYaoqingYaoqingFragmentAdapter adapter;

	// private String
	// strUrl="http://apitest.boluolc.com:8012/v1/asset/%s/0/detail/";

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.fragment_share_yaoqing_yaoqing,
				container, false);

		return view;
	}

	@Override
	protected void getFindById(View view) {
		listView = (XListView) view
				.findViewById(R.id.fragment_share_yaoqing_yaooqing_listview);
		// LayoutInflater inflater=LayoutInflater.from(getActivity());
		// View vHead=
		// inflater.inflate(R.layout.fragment_share_yaoqing_yaoqing_head, null);
		// // 添加头视图
		// listView.addHeaderView(vHead);
		// adapter=new SupportListAdapter(totalList, SupportListActivity.this);
		// listView.setAdapter(adapter);
		linearLayout = (LinearLayout) view
				.findViewById(R.id.fragment_share_yaoqing_yaooqing_kfcs_layout);
		img = (ImageView) view
				.findViewById(R.id.fragment_share_yaoqing_yaooqing_kfcs_img);
		rlNone = (RelativeLayout) view
				.findViewById(R.id.fragment_share_yaoqing_yaooqing__kfc_none);
		list = new ArrayList<BeanShareYaoqingYaoqingFragment>();
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		listView.setDividerHeight(0);
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (list.size() == 0) {
							return;
						} else {
							getData();
							adapter.notifyDataSetChanged();
							onLoad();
						}

					}

				}, 1000);
			}
		});
		mHandler = new Handler();
	}

	private void onLoad() {
		// TODO Auto-generated method stub
		listView.stopLoadMore();
	}

	@Override
	protected void getData() {
		ProessDilogs.getProessAnima(img, getActivity());
		// /Map<String, String> params = new HashMap<String, String>();
		// Toast.makeText(getActivity(), "哈哈哈哈哈哈", 0).show();//BeanUrl.Fanlijilu

		String url = BeanUrl.Fanlijilu + index;
		Myapplication.okhttpManger.sendComplexForm(
				getActivity(),
				false,
				QntUtils.getURL(url,
						BoluoUtils.getShareOneData(getActivity(), "phone")),
				null, new Funck4() {

					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								// Toast.makeText(getActivity(), "请求成功",
								// 0).show();
								JSONArray array = result
										.getJSONArray("content");
								// int length = array.length();
								if (array.length() == 0 && index == 1) {
									listView.setVisibility(View.GONE);
									rlNone.setVisibility(View.VISIBLE);
									ProessDilogs.closeAnimation(img,
											linearLayout);
									linearLayout.setVisibility(View.GONE);

								} else {
									listView.setVisibility(View.VISIBLE);
									rlNone.setVisibility(View.GONE);
									if (array.length() == 0) {
										if (getActivity() == null)
											return;
										listView.setPullLoadEnable(false);
										ProessDilogs.closeAnimation(img,
												linearLayout);
										linearLayout.setVisibility(View.GONE);
										Toast.makeText(getActivity(), "已加载完毕",
												Toast.LENGTH_SHORT).show();
									} else {
										list.addAll(JSONUtil
												.getList(
														result,
														"content",
														BeanShareYaoqingYaoqingFragment.class));
										listView.setPullLoadEnable(true);
										ProessDilogs.closeAnimation(img,
												linearLayout);
										linearLayout.setVisibility(View.GONE);
										if (list.size() == 0) {
											rlNone.setVisibility(View.VISIBLE);
										}
										index += 1;
										adapter = new ShareYaoqingYaoqingFragmentAdapter(
												getActivity(), list,
												R.layout.item_yaoqingjiliu);
										adapter.notifyDataSetChanged();
										listView.setAdapter(adapter);
									}
								}

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// TODO Auto-generated method stub
					}
				});

	}
}
