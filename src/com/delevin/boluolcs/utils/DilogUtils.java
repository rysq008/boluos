package com.delevin.boluolcs.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;

import com.delevin.boluolcs.view.CustomDialog;
import com.pusupanshi.boluolicai.R;

public class DilogUtils {
	/**
	 * 显示软件更新对话框
	 */
	public static  void showJihuoDialog(Context mContext) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 实例化自定义主题的对话框
		final CustomDialog dialog = new CustomDialog(mContext,R.style.gesturesPassword_dialog);
		View layout = inflater.inflate(R.layout.dilog_jihuo, null);
		dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
//		// 构造对话框
		Button bt_geng = (Button) layout.findViewById(R.id.dilog_jihuo);
		Button bt_shao = (Button) layout.findViewById(R.id.dilog_jihuo_dismiss);
		bt_geng.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();

			}
		});
		
			bt_shao.setOnClickListener(new android.view.View.OnClickListener() {

				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		dialog.setCancelable(false);
		dialog.show();
	}

}
