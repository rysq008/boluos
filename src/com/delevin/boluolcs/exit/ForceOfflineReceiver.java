package com.delevin.boluolcs.exit;

import com.delevin.boluolcs.denglu.ZhuActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.WindowManager;

/**
 *     @author 李红涛  @version 创建时间：2016-3-24 下午12:22:38    类说明 
 */
public class ForceOfflineReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		AlertDialog.Builder dilog = new AlertDialog.Builder(context);
		dilog.setTitle("Warning");
		dilog.setMessage("You are forced to be offline .Please try to login again. ");
		// 将对话框 改为不可取消；
		dilog.setCancelable(false);
		dilog.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ActivityCollector.finishActivity();
				Intent intent = new Intent(context, ZhuActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 重新启动LoginActivity
				context.startActivity(intent);
			}
		});
		// 需要设置Alertlog的类型，保证在广播接收器中可以正常弹出

		AlertDialog alertDialog = dilog.create();
		alertDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alertDialog.show();
	}

}
