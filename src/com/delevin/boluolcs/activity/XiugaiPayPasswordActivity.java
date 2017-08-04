package com.delevin.boluolcs.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

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

public class XiugaiPayPasswordActivity extends BaseActivity implements
		OnClickListener {
	private TitleView tvTitle;
	private EditText edtOldPassword, edtNewPassword, edtNewPasswordAgain;
	private Button btnQueding;

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_xiugai_pay_password);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.xiugai_paypassword_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("修改交易密码");

		edtOldPassword = (EditText) findViewById(R.id.xiugai_paypassword_oldPassword);
		edtNewPassword = (EditText) findViewById(R.id.xiugai_paypassword_newPassword);
		edtNewPasswordAgain = (EditText) findViewById(R.id.xiugai_paypassword_newPasswordAgain);
		btnQueding = (Button) findViewById(R.id.xiugai_paypassword_btn);
		btnQueding.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.xiugai_paypassword_btn:
			if (edtNewPassword.getText().toString().length() == 6) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("old_passwd", edtOldPassword.getText().toString());
				params.put("new_passwd", edtNewPassword.getText().toString());
				params.put("re_new_passwd", edtNewPasswordAgain.getText()
						.toString());
				Myapplication.okhttpManger.sendComplexForm(
						getApplicationContext(), false, QntUtils.getURL(
								BeanUrl.XiugaiPayPassword, BoluoUtils
										.getShareOneData(
												getApplicationContext(),
												"phone")), params,
						new Funck4() {

							@Override
							public void onResponse(JSONObject result) {
								// TODO Auto-generated method stub
								try {
									String code = result.getString("code");
									String strInfo = result.getString("desc")
											.toString();
									// 成功
									if (TextUtils.equals(code, "10000")) {
										Toast.makeText(getApplicationContext(),
												strInfo, Toast.LENGTH_SHORT)
												.show();
										finish();
									} else if (TextUtils.equals(code, "10001")) {
										Toast.makeText(getApplicationContext(),
												strInfo, Toast.LENGTH_SHORT)
												.show();
									}
									// 参数不能为空
									else if (TextUtils.equals(code, "10001")) {
										Toast.makeText(getApplicationContext(),
												strInfo, Toast.LENGTH_SHORT)
												.show();
									}
									// 旧密码输入错误
									else if (TextUtils.equals(code, "10002")) {
										Toast.makeText(getApplicationContext(),
												strInfo, Toast.LENGTH_SHORT)
												.show();
									}
									// 两次密码输入不一致
									else if (TextUtils.equals(code, "10003")) {
										Toast.makeText(getApplicationContext(),
												strInfo, Toast.LENGTH_SHORT)
												.show();
									}
									// 该用户未设置交易密码
									else if (TextUtils.equals(code, "10004")) {
										Toast.makeText(getApplicationContext(),
												strInfo, Toast.LENGTH_SHORT)
												.show();
									}
									// 失败
									else if (TextUtils.equals(code, "10010")) {
										Toast.makeText(getApplicationContext(),
												strInfo, Toast.LENGTH_SHORT)
												.show();
									}
									// 数据库操作失败
									else if (TextUtils.equals(code, "10007")) {
										Toast.makeText(getApplicationContext(),
												strInfo, Toast.LENGTH_SHORT)
												.show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});
			} else {
				Toast.makeText(getApplicationContext(), "请输入六位支付密码",
						Toast.LENGTH_SHORT).show();
			}

			break;

		default:
			break;
		}

	}

}
