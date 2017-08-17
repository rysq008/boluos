package com.delevin.boluolcs.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

public class WangjiPayPasswordNextActivity extends BaseActivity implements
		OnClickListener {
	private TitleView tvTitle;
	private EditText edtIDCard;
	private Button btnNext;
	private String phone;

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_wangji_pay_password_next);
		View statusBarview = findViewById(R.id.statusBarview);
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.wangji_paypassword_next_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("验证身份");

		edtIDCard = (EditText) findViewById(R.id.wangji_paypassword_next_edt_IDCard);
		btnNext = (Button) findViewById(R.id.wangji_paypassword_next_btn_next);
		btnNext.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wangji_paypassword_next_btn_next:
			Map<String, String> params = new HashMap<String, String>();
			params.put("id_card", edtIDCard.getText().toString());
			Myapplication.okhttpManger.sendComplexForm(getApplicationContext(),
					false, QntUtils.getURL(BeanUrl.ShenfenIDCard, phone),
					params, new Funck4() {

						@Override
						public void onResponse(JSONObject result) {
							// TODO Auto-generated method stub
							try {
								String code = result.getString("code");
								String strInfo = result.getString("desc")
										.toString();
								if (TextUtils.equals(code, "10000")) {
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
									startActivity(new Intent(
											WangjiPayPasswordNextActivity.this,
											IDCardNextPhoneCodeActivity.class));
									finish();
								} else if (TextUtils.equals(code, "10001")) {
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
								} else if (TextUtils.equals(code, "10002")) {
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
								} else if (TextUtils.equals(code, "10007")) {
									Toast.makeText(getApplicationContext(),
											strInfo, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
			break;

		default:
			break;
		}

	}

}
