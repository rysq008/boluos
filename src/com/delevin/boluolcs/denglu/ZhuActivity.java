package com.delevin.boluolcs.denglu;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.NetUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.view.ClearEditText;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-21 下午3:30:52    类说明 
 */
public class ZhuActivity extends BaseActivity {

	private ClearEditText phonEdit;
	public static ZhuActivity zhuActivity;
	private LinearLayout layout;
	private ImageView img;

	@Override
	protected void findViews() {
		setContentView(R.layout.boluos_activity_zhu);
		phonEdit = (ClearEditText) findViewById(R.id.zhu_phone);
		Button bt = (Button) findViewById(R.id.zhu_bt);
		layout = (LinearLayout) findViewById(R.id.zhu_visibility_layout);
		img = (ImageView) findViewById(R.id.zhu_visibility_image);
		zhuActivity = this;
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (NetUtils.getNetWorkState(ZhuActivity.this) != -1) {
					if (phonEdit.length() == 11) {
						layout.setVisibility(View.VISIBLE);
						ProessDilogs.getProessAnima(img,
								getApplicationContext());
						String url = String.format(
								BeanUrl.LOGINORDENGLU_STRING, phonEdit
										.getText().toString());
						Myapplication.okhttpManger.sendComplexForm(
								getApplicationContext(), false, url, null,
								new Funck4() {
									@Override
									public void onResponse(JSONObject result) {
										String code;
										try {
											code = result.getString("code");
											ProessDilogs.closeAnimation(img,
													layout);
											if (TextUtils.equals(code, "10000")) {

												Intent intent = new Intent(
														ZhuActivity.this,
														DengluActvity.class);
												intent.putExtra("phone",
														phonEdit.getText()
																.toString()
																.trim());
												startActivity(intent);
												ZhuActivity.this.finish();

											} else if (TextUtils.equals(code,
													"10001")) {
												Intent intent = new Intent(
														ZhuActivity.this,
														LoginActivity.class);
												intent.putExtra("phone",
														phonEdit.getText()
																.toString()
																.trim());
												startActivity(intent);
											} else if (TextUtils.equals(code,
													"10002")) {
												Intent intent = new Intent(
														ZhuActivity.this,
														LoginActivity.class);
												intent.putExtra("phone",
														phonEdit.getText()
																.toString()
																.trim());
												startActivity(intent);
											} else {
												Toast.makeText(
														getApplicationContext(),
														result.getString("desc"),
														Toast.LENGTH_SHORT)
														.show();
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
								});
					}
				} else {
					Toast.makeText(getApplicationContext(), "您当前的网络不可用",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

}
