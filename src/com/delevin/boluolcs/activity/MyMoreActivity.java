package com.delevin.boluolcs.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.interfaces.GengXinCallBack;
import com.delevin.boluolcs.main.MainActivity;
import com.delevin.boluolcs.utils.APPName;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.UpdateManager;
import com.delevin.jsandroid.JSAndroidActivity;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-6 上午9:13:44    类说明 
 */

public class MyMoreActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layout_current_version;// 当前版本
	private LinearLayout layout_weixin_service;// 微信客服
//	private LinearLayout layout_service_phone;// 客服电话
	private LinearLayout layout_fengxian_pinggu;// 风险评估
	private LinearLayout layout_guanwang;// 官网
	private TextView txt_current_version_number;// 当前版本号
	private Button bt_exit;// 退出
	private int versioncode, curr_version_code;;
	private String strContent, strUpdateUrl;
	public static MyMoreActivity myMoreActivity;
	private String type;
	private String phone;
	private TextView tvFengxian;
	private TextView more_current_ziti;

	@Override
	protected void findViews() {
		myMoreActivity = this;
		setContentView(R.layout.activity_more);
		more_current_ziti = (TextView) findViewById(R.id.more_current_ziti);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_more);
		View statusBarview = findViewById(R.id.statusBarview);
		layout_current_version = (LinearLayout) findViewById(R.id.more_current_version);
//		layout_service_phone = (LinearLayout) findViewById(R.id.more_service_phone);
		layout_weixin_service = (LinearLayout) findViewById(R.id.more_weixin_service);
		layout_fengxian_pinggu = (LinearLayout) findViewById(R.id.more_fengxian_pinggu);
		layout_guanwang = (LinearLayout) findViewById(R.id.more_guanwang);
		txt_current_version_number = (TextView) findViewById(R.id.more_current_version_number);
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
			String version = packInfo.versionName;
			txt_current_version_number.setText(version);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		type = getIntent().getStringExtra("type");
		if (!TextUtils.isEmpty(type)) {
			tvFengxian = (TextView) findViewById(R.id.more_weixin_fengxian);
			tvFengxian.setText(type);
		}
		phone = BoluoUtils.getShareOneData(MyMoreActivity.this, "phone");
		bt_exit = (Button) findViewById(R.id.boluos_exit);
		layout_current_version.setOnClickListener(this);
//		layout_service_phone.setOnClickListener(this);
		layout_weixin_service.setOnClickListener(this);
		layout_fengxian_pinggu.setOnClickListener(this);
		layout_guanwang.setOnClickListener(this);
		bt_exit.setOnClickListener(this);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("更多");
	}
	@Override
	protected void getData() {
		final int visonCode = APPName.getVersionCode(this);
		Myapplication.okhttpManger.sendComplexForms(getApplicationContext(),
				false, BeanUrl.GENGXINS_STRING, null, new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						String code;
						try {
							code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								JSONArray array = result.getJSONArray("data");
								for (int i = 0; i < array.length(); i++) {
									JSONObject object2 = array.getJSONObject(i);
									versioncode = object2.getInt("code"); // 版本
									strContent = object2.getString("content"); // 内容
									strUpdateUrl = object2.getString("url");
									if (versioncode > visonCode) {
										more_current_ziti.setText("（发现新版本）");
										more_current_ziti.setTextColor(getApplicationContext().getResources().getColor(R.color.ff3939));
									}
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.more_current_version:
			// 初始化更新管理
			UpdateManager manager = new UpdateManager(MyMoreActivity.this);
			// 检查软件更新
			manager.checkUpdate("0", new GengXinCallBack() {

				@Override
				public void onCode(String code) {
				}
				@Override
				public void onSerivce(String ser) {
					
				}
			});

			// update();
			break;
//		case R.id.more_service_phone:
//			Intent intentTel = new Intent();
//			intentTel.setAction(Intent.ACTION_DIAL);
//			intentTel.setData(Uri.parse("tel:400-032-0596"));
//			startActivity(intentTel);
//			break;
		case R.id.more_fengxian_pinggu:
			Intent intent = new Intent(MyMoreActivity.this,
					JSAndroidActivity.class);
			intent.putExtra("title", "风险评估");
			intent.putExtra("jsUrl", BeanUrl.FENGXIANPINGGU_STRING + phone);
			startActivity(intent);
			break;
		case R.id.more_weixin_service:
			startActivity(new Intent(MyMoreActivity.this,
					MoreWXServiceActivity.class));
			break;
		case R.id.boluos_exit:
			BoluoUtils.getDilogExit(MyMoreActivity.this, MainActivity.class);
			break;
		case R.id.more_guanwang:
			Intent intentF = new Intent();
			intentF.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse("https://www.boluolc.com/?");
			intentF.setData(content_url);
			startActivity(intentF);
			break;
		default:
			break;
		}
	}
}
