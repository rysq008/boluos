package com.delevin.boluolcs.denglu;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.interfaces.PhoneCodeCallBack;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.CodeTimerUtils;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-8 上午10:43:46 类说明
 */
public class ZhaoHuiSecretActivity extends BaseActivity implements
		OnClickListener {
	private TitleView titleView;
	private TextView phoneView;
	private EditText phoneCode;
	private Button bt_huoqu;
	private Button bt_next;
	private String phone;
	private Intent intent;
	public static ZhaoHuiSecretActivity zhaoHuiSecretActivity;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		setContentView(R.layout.activity_bid_zhaohuisecret);
		zhaoHuiSecretActivity = this;
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView = (TitleView) findViewById(R.id.titleView_bidZhao);
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("找回密码");
		init();
	}

	private void init() {
		phone = getIntent().getStringExtra("phone");
		phoneView = (TextView) findViewById(R.id.zhao_phone);
		phoneCode = (EditText) findViewById(R.id.zhao_yanzhengma);
		bt_huoqu = (Button) findViewById(R.id.zhao_bt_huoquyazhengma);
		bt_next = (Button) findViewById(R.id.zhao_bt);
		phoneView.setText(phone);
		bt_huoqu.setOnClickListener(this);
		bt_next.setOnClickListener(this);
		intent = CodeTimerUtils.getIntents(this, bt_huoqu);
	}

	@Override
	protected void getData() {

	}

	private void getCallCode() {
		AndroidUtils.getCallPhoneCode(phone, getApplicationContext(),
				new PhoneCodeCallBack() {

					@Override
					public void onResponse(JSONObject response) {
						// 成功
						try {
							if (response.getString("code").equals("10000")) {
								Toast.makeText(getApplicationContext(),
										response.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
								CodeTimerUtils.startIntent(
										getApplicationContext(), bt_huoqu,
										intent);// 开始计时
							} else if (response.getString("code").equals(
									"10001")) {
								Toast.makeText(getApplicationContext(),
										response.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
							}
							// 失败
							else if (response.getString("code").equals("10010")) {
								Toast.makeText(getApplicationContext(),
										response.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
							}
							// 用户身份验证失败
							else if (response.getString("code").equals("10002")) {
								Toast.makeText(getApplicationContext(),
										response.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
							}
							// 数据库操作失败
							else if (response.getString("code").equals("10007")) {
								Toast.makeText(getApplicationContext(),
										response.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
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
		case R.id.zhao_bt_huoquyazhengma:
			getCallCode();
			break;
		case R.id.zhao_bt:
			if (!TextUtils.isEmpty(phoneCode.getText().toString())) {
				if (phoneCode.getText().toString().length() == 6) {
					BoluoUtils.getYanCode(zhaoHuiSecretActivity, phone,
							phoneCode.getText().toString(),
							ZhaoSureActivity.class);
				} else {
					Toast.makeText(getApplicationContext(), "验证码需要六位数字组合",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "验证码不能为空",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CodeTimerUtils.stopIntent(getApplicationContext(), intent);
	}
}
