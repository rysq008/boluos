package com.delevin.boluolcs.activity;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.delevin.boluolcs.adapter.HetongAdapter;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanjieKuan;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.ListView.XListView;
import com.pusupanshi.boluolicai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-4-18 下午4:32:06 类说明
 */
public class HeTongActivity extends BaseActivity {
	private LinearLayout layout;
	private ImageView img;
	private XListView listView;
	private List<BeanjieKuan> datas;

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
		titleView.setAppTitle("项目合同");
		layout = (LinearLayout) findViewById(R.id.listview_jiazai_layout);
		img = (ImageView) findViewById(R.id.listview_jiazai_img);
		layout.setVisibility(View.GONE);
		listView = (XListView) findViewById(R.id.listview_jiazai_listView);
		datas = new ArrayList<BeanjieKuan>();
	}

	@Override
	protected void getData() {
		@SuppressWarnings("unchecked")
		List<BeanjieKuan> list = (List<BeanjieKuan>) getIntent()
				.getSerializableExtra("list");
		for (int i = 0; i < list.size(); i++) {
			BeanjieKuan jiBeanjieKuan = new BeanjieKuan();
			String name = list.get(i).getName();
			String path = list.get(i).getPath();
			jiBeanjieKuan.setName(name);
			jiBeanjieKuan.setPath(path);
			datas.add(jiBeanjieKuan);
		}
		listView.setAdapter(new HetongAdapter(this, datas,
				R.layout.activity_hetong_img));

	}

}
