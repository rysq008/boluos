package com.delevin.boluolcs.denglu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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
import com.delevin.boluolcs.utils.QntUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.view.TitleView;
import com.pusupanshi.boluolicai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-8 上午11:19:23 类说明
 */
public class ZhaoSureActivity extends BaseActivity implements OnClickListener {
	private TitleView titleView;
	private EditText idCard;
	private EditText firstCode;
	private EditText endCode;
	private Button btSure;
	private String phone;
	private String id_bind;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		setContentView(R.layout.activity_zhao_sure);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView = (TitleView) findViewById(R.id.titleView_sureSecret);
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("找回密码");
		init();
	}

	private void init() {
		phone = getIntent().getStringExtra("phone");
		idCard = (EditText) findViewById(R.id.zhao_sure_idCard);
		firstCode = (EditText) findViewById(R.id.zhao_sure_secret);
		endCode = (EditText) findViewById(R.id.zhao_sure_sure_secret);
		btSure = (Button) findViewById(R.id.zhao_btSure);
		btSure.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		Myapplication.okhttpManger.sendComplexForm(getApplicationContext(),
				false, QntUtils.getURL(BeanUrl.ISIDBAND_STRING, phone), null,
				new Funck4() {

					@Override
					public void onResponse(JSONObject object) {
						try {

							String code = object.getString("code");
							if (TextUtils.equals(code, "10000")) {

								JSONObject object2 = object
										.getJSONObject("content");
								id_bind = object2.getString("id_bind");
								if (TextUtils.equals(id_bind, "0")) {
									idCard.setVisibility(View.GONE);
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
		case R.id.zhao_btSure:
			getSureSecret();
			break;

		default:
			break;
		}
	}

	private void getSureSecret() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("new_passwd", firstCode.getText().toString());
		params.put("re_new_passwd", endCode.getText().toString());
		params.put("phone", phone);
		if (TextUtils.equals(id_bind, "1")) {
			params.put("id_card", idCard.getText().toString());
		}
		Myapplication.okhttpManger.sendComplexForm(getApplicationContext(),
				false, BeanUrl.LOGINPASSWROD_STRING, params, new Funck4() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							// 成功
							if (response.getString("code").equals("10000")) {
								Toast.makeText(getApplicationContext(),
										response.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
								ZhaoHuiSecretActivity.zhaoHuiSecretActivity
										.finish();
								finish();
							}
							// 参数不能为空
							else if (response.getString("code").equals("10008")) {
								Toast.makeText(getApplicationContext(),
										response.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
							}
							// 旧密码输入错误
							else if (response.getString("code").equals("10002")) {
								Toast.makeText(getApplicationContext(),
										response.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
							}
							// 两次密码输入不一致
							else if (response.getString("code").equals("10005")) {
								Toast.makeText(getApplicationContext(),
										response.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
							}
							// 该用户未设置交易密码
							else if (response.getString("code").equals("10004")) {
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
							// 数据库操作失败
							else if (response.getString("code").equals("10007")) {
								Toast.makeText(getApplicationContext(),
										response.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

	}

}
