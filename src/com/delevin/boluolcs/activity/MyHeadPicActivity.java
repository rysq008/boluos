package com.delevin.boluolcs.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

public class MyHeadPicActivity extends BaseActivity implements OnClickListener {
	
	private TitleView tvTitle;
	private TextView tvPhone, tvIdCardOrState, tvRealName;
	private RelativeLayout rlPhone, rlShiming;
	private String strBankCard, strBankName, strIdCard, strRealName;
	private String phone;
	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		
		setContentView(R.layout.activity_my_head_pic);
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		View statusBarview = findViewById(R.id.statusBarview);
		
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.my_head_pic_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("个人中心");
		LinearLayout layoutAdress = (LinearLayout) findViewById(R.id.my_head_pic_rl_shouhuoAdress);
		layoutAdress.setOnClickListener(this);
//		rlPhone = (RelativeLayout) findViewById(R.id.my_head_pic_rl_phone);
		rlShiming = (RelativeLayout) findViewById(R.id.my_head_pic_rl_shiming);
//		rlPhone.setOnClickListener(this);
		rlShiming.setOnClickListener(this);
		tvPhone = (TextView) findViewById(R.id.my_head_pic_tv_phone);
		tvIdCardOrState = (TextView) findViewById(R.id.my_head_pic_tv_idcard_or_state);
		tvRealName = (TextView) findViewById(R.id.my_head_pic_tv_realname);
//		tvPhone.setText(phone);
		tvPhone.setText(phone.subSequence(0, 4) + "****"
				+ phone.substring(8, 11));
	}

	@Override
	protected void getData() {
		Myapplication.okhttpManger.sendComplexForm(getApplicationContext(),
				false, QntUtils.getURL(BeanUrl.PAY_INIT_STRING, phone), null,
				new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						// TODO Auto-generated method stub
						String code;
						try {
							code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								JSONObject jsonObject = result.getJSONObject("content");
								strBankCard = jsonObject.getString("bank_card");
								strBankName = jsonObject.getString("bank_name");
								strIdCard = jsonObject.getString("id_card");
								strRealName = jsonObject.getString("real_name");
								if (strRealName.length() == 2) {
									tvRealName.setText("*"+ QntUtils.getSubStringW(
									strRealName, 1,strRealName.length()));
								} else if (strRealName.equals("")) {
									tvRealName.setText("");
								} else {
									tvRealName.setText(strRealName.substring(0,strRealName.length() - 1) + "*");
								}
								if (strIdCard.equals("")) {
									tvIdCardOrState.setText("未认证");
								} else {
								tvIdCardOrState.setText(QntUtils.getSubStringW(strIdCard, 0, 4)+ "****"+ 
								QntUtils.getSubStringW(strIdCard,strIdCard.length() - 4,strIdCard.length()));
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.my_head_pic_rl_phone:
//			startActivity(new Intent(getApplicationContext(),
//					OldPhoneCodeActivity.class));
//			break;
		case R.id.my_head_pic_rl_shiming:
			if (strIdCard.equals("")) {
				// startActivity(new Intent(getApplicationContext(),
				// NoShimingRenzhengActivity.class));
				BoluoUtils.getDilogHintBand(MyHeadPicActivity.this,
						AddNumberActivity.class);
			} else {
				Toast.makeText(getApplicationContext(), "已实名认证",
						Toast.LENGTH_SHORT).show();

			}
			break;
		case R.id.my_head_pic_rl_shouhuoAdress:
			Intent intent = new Intent(MyHeadPicActivity.this,AdressActivity.class);
			intent.putExtra("phone", phone);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

}
