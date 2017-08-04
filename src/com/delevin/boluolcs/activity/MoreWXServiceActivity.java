package com.delevin.boluolcs.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.view.TitleView;
import com.delevin.boluolcs.view.TitleView.OnRightButtonClickListener;
import com.pusupanshi.boluolicai.R;

public class MoreWXServiceActivity extends BaseActivity {
	private TitleView tvTitle;

	@Override
	protected void findViews() {

		setContentView(R.layout.activity_more_wx_service);
		View statusBarview = findViewById(R.id.more_wx_service_statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.VISIBLE);
		}
		tvTitle = (TitleView) findViewById(R.id.more_wx_service_titleview);
		tvTitle.initViewsVisible(true, true, true, true);
		tvTitle.setAppTitle("微信客服");
		tvTitle.setRightTitle("打开微信");
		tvTitle.setOnRightButtonClickListener(new OnRightButtonClickListener() {

			@Override
			public void OnRightButtonClick(View v) {
				// TODO Auto-generated method stub
				
				if (isWeixinAvilible(getApplicationContext())==true) {
					copy("菠萝理财", MoreWXServiceActivity.this);
					Intent intent = new Intent();
					ComponentName cmp = new ComponentName("com.tencent.mm",
							"com.tencent.mm.ui.LauncherUI");
					intent.setAction(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_LAUNCHER);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setComponent(cmp);
					startActivityForResult(intent, 0);
					
				}else {
					Toast.makeText(getApplicationContext(), "您的手机未安装微信", 0).show();
				}
				
				
			}
		});
	}

	@SuppressLint("NewApi")
	public static void copy(String content, Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}

	/**
	 * 实现粘贴功能
	 * 
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String paste(Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return cmb.getText().toString().trim();
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}
	
	public static boolean isWeixinAvilible(Context context) {
		// 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

}
