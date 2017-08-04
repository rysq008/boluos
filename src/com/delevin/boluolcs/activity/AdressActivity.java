package com.delevin.boluolcs.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.interfaces.AddressCallBack;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

public class AdressActivity extends BaseActivity implements OnClickListener{
private EditText etPhone;
private EditText etName;
private EditText etadress;
private LinearLayout layout;
private ImageView imgView;
	@Override
	protected void findViews() {
		setContentView(R.layout.item_adress);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		TitleView tvTitle = (TitleView) findViewById(R.id.item_adress_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("收货地址管理");
		layout = (LinearLayout) findViewById(R.id.listview_jiazai_slayout);
		imgView = (ImageView) findViewById(R.id.listview_jiazai_simg);
		etName = (EditText) findViewById(R.id.adress_name);
		etPhone = (EditText) findViewById(R.id.adress_phone);
		etadress = (EditText) findViewById(R.id.adress_adress);
		Button btButton = (Button) findViewById(R.id.adress_bt);
		btButton.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		
		TijiaoorInitAdress(this, new AddressCallBack() {
			
			@Override
			public void getRespose(JSONObject result) {
				
				try {
					JSONObject object = result.getJSONObject("content");
					String people_name = object.getString("name");
					if (TextUtils.isEmpty(people_name)) {
						return;
					}else {
						String people_phone = object.getString("phone");
						String people_address = object.getString("address");
						etName.setText(people_name);
						etPhone.setText(people_phone);
						etadress.setText(people_address);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},"s");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.adress_bt:
			if (!TextUtils.isEmpty(etName.getText().toString().trim())) {
				if (!TextUtils.isEmpty(etPhone.getText().toString().trim())) {
					if (!TextUtils.isEmpty(etadress.getText().toString().trim())) {
						TijiaoorInitAdress(this, new AddressCallBack() {
							
							@Override
							public void getRespose(JSONObject result) {
								Toast.makeText(getApplicationContext(), "地址保存成功", Toast.LENGTH_SHORT).show();
							}
						},"t");
					}else {
						Toast.makeText(getApplicationContext(), "请详细填写收货地址", Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(getApplicationContext(), "请输入联系人手机号", Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(getApplicationContext(), "请输入联系人姓名", Toast.LENGTH_SHORT).show();
			}
			
			break;

		default:
			break;
		}
	}
	private void TijiaoorInitAdress(Context context,final AddressCallBack callBack,final String t) {
		ProessDilogs.getProessAnima(imgView, getApplicationContext());
		Map<String, String> params;
		if (TextUtils.equals(t, "t")) {
			params = new HashMap<String, String>();
			params.put("people_name", etName.getText().toString().trim());
			params.put("people_phone", etPhone.getText().toString().trim());
			params.put("people_address", etadress.getText().toString().trim());
		}else {
			params = null;
		}
		
		Myapplication.okhttpManger.sendComplexForm(context, false, QntUtils.getURL(BeanUrl.ADRESS_STRING, getIntent().getStringExtra("phone")), params, new Funck4(){

			@Override
			public void onResponse(JSONObject result) {
				try {
					String code = result.getString("code");
					ProessDilogs.closeAnimation(imgView, layout);
					if (TextUtils.equals(code, "10000")) {
						callBack.getRespose(result);
						if (TextUtils.equals(t, "t")) {
							finish();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}});
	}

	

}
