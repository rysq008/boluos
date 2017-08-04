package com.delevin.boluolcs.broderecaster;

import com.delevin.application.Myapplication;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-27 上午11:01:35 类说明
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
	static ConnectionChangeReceiver myReceiver;

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
			Myapplication.Internet = false;
			// BoluoUtils.getDilogDefult(context, true, "温馨提示", "您当前的网络不可用",
			// "确定", "取消");
			// Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
			// 改变背景或者 处理网络的全局变量
		} else {
			Myapplication.Internet = true;
		}
	}

	// 注册广播
	public static void registerReceiver(Context context) {
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		myReceiver = new ConnectionChangeReceiver();
		context.registerReceiver(myReceiver, filter);
		// callBack.onRepose(internet);
	}

	// 取消广播
	public static void unregisterReceiver(Context context) {
		context.unregisterReceiver(myReceiver);
	}
}