package com.delevin.boluolcs.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-4-7 上午10:08:09 类说明
 */
public class NetUtils {
	/**
	 * 
	 * @author cj 判断网络工具类
	 * 
	 */

	/**
	 * 没有连接网络
	 */
	private static final int NETWORK_NONE = -1;
	/**
	 * 移动网络
	 */
	private static final int NETWORK_MOBILE = 0;
	/**
	 * 无线网络
	 */
	private static final int NETWORK_WIFI = 1;

	public static int getNetWorkState(Context context) {
		// 得到连接管理器对象
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

			if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
				return NETWORK_WIFI;
			} else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
				return NETWORK_MOBILE;
			}
		} else {
			return NETWORK_NONE;
		}
		return NETWORK_NONE;
	}
}
