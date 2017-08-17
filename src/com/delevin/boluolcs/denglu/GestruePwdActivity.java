package com.delevin.boluolcs.denglu;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.view.NinePointLineView;
import com.pusupanshi.boluolicai.R;

public class GestruePwdActivity extends BaseActivity {
	private LinearLayout nine_con;// 九宫格容器

	NinePointLineView nv;// 九宫格View
	TextView showInfo;
	boolean isSetFirst = false;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置标题不显示

		setContentView(R.layout.boluos_activity_gest);

		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {

			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		}
		nv = new NinePointLineView(GestruePwdActivity.this);

		nine_con = (LinearLayout) findViewById(R.id.nine_con);

		nine_con.addView(nv);

		showInfo = (TextView) findViewById(R.id.show_set_info);

		getSetPwd();
	}

	@Override
	protected void getData() {
	}

	@SuppressLint("ResourceAsColor")
	public void getSetPwd() {

		SharedPreferences shareDate = getSharedPreferences("GUE_PWD", 0);

		isSetFirst = shareDate.getBoolean("IS_SET_FIRST", false);

		boolean isSet = shareDate.getBoolean("IS_SET", false);
		if (!isSetFirst) {
			if (isSet) {
				showInfo.setText("请输入密码");
			} else {
				showInfo.setText("请设置手势密码");
			}
		} else {
			showInfo.setText("请再次确认手势密码");
		}
		boolean is_sure_error = shareDate.getBoolean("SURE_ERROR", false);

		if (is_sure_error) {
			showInfo.setText("密码错误 ，请重新输入");

			showInfo.setTextColor(Color.RED);
			// VibratorUtil.Vibrate(GestruePwd.this, 100); //震动100ms

			shareDate.edit().putBoolean("SURE_ERROR", false).commit();
		}
		boolean is_second_error = shareDate.getBoolean("SECOND_ERROR", false);

		if (is_second_error) {

			showInfo.setText("和第一次输入手势密码不一致，重新输入");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		GestruePwdActivity.this.finish();
	}
}
