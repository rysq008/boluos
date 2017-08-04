package com.delevin.boluolcs.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-27 下午3:18:55 类说明
 */
public class BrodeCasterNotice {
	// 通知初始化及接受值
	public static void getdata(Context context, final NoticeCallBsack callBack) {
		LocalBroadcastManager broadcastManager = LocalBroadcastManager
				.getInstance(context);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.CART_BROADCAST");// 建议把它写一个公共的变量，这里方便阅读就不写了。
		BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				callBack.onDome();
			}
		};
		broadcastManager.registerReceiver(mItemViewListClickReceiver,
				intentFilter);
	}

	public static void getdatas(Context context, final NoticeCallBsack callBack) {
		LocalBroadcastManager broadcastManager = LocalBroadcastManager
				.getInstance(context);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.CART_BROADCASTS");// 建议把它写一个公共的变量，这里方便阅读就不写了。
		BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				callBack.onDome();
			}
		};
		broadcastManager.registerReceiver(mItemViewListClickReceiver,
				intentFilter);
	}

	public interface NoticeCallBsack {
		void onDome();
	}

	// 发送
	public static void getFaSong(Context context) {
		Intent intent = new Intent("android.intent.action.CART_BROADCAST");
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}

	public static void getFaSongs(Context context) {
		Intent intent = new Intent("android.intent.action.CART_BROADCASTS");
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}
}
