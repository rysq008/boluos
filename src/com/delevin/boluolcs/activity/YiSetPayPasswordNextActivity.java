package com.delevin.boluolcs.activity;


import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

public class YiSetPayPasswordNextActivity extends BaseActivity implements
		OnClickListener {
	private TitleView tvTitle;
	private RelativeLayout rlJide, rlWangji;

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_yi_set_pay_password_next);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.yi_set_pay_password_next_tv_title);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("修改交易密码");
		rlJide = (RelativeLayout) findViewById(R.id.yi_set_pay_password_next_rl_jide);
		rlWangji = (RelativeLayout) findViewById(R.id.yi_set_pay_password_next_rl_wangji);
		rlJide.setOnClickListener(this);
		rlWangji.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.yi_set_pay_password_next_rl_jide:
			startActivity(new Intent(YiSetPayPasswordNextActivity.this,
					XiugaiPayPasswordActivity.class));
			finish();
			break;
		case R.id.yi_set_pay_password_next_rl_wangji:
			startActivity(new Intent(YiSetPayPasswordNextActivity.this,
					WangjiPayPasswordNextActivity.class));
			finish();
			break;

		default:
			break;
		}
	}

}
