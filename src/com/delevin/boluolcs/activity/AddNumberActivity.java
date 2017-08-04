package com.delevin.boluolcs.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.view.LayoutEditText;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-27 下午1:59:18    类说明 
 */
public class AddNumberActivity extends BaseActivity {

	private LayoutEditText editText;
	public static AddNumberActivity addNumberActivity;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		setContentView(R.layout.activity_addnumber_layout);
		addNumberActivity = this;
		View statusBarview = findViewById(R.id.statusBarview);
		Button bt = (Button) findViewById(R.id.bt_add_number);
		editText = (LayoutEditText) findViewById(R.id.addNumber_view);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddNumberActivity.this,
						PayActivity.class);
				intent.putExtra("number", editText.getNumber());
				startActivity(intent);
			}
		});
		TitleView titleView = (TitleView) findViewById(R.id.titleView_addNumber);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("充值金额");
	}

	@Override
	protected void getData() {
	}

}
