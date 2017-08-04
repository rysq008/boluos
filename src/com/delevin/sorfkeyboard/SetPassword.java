package com.delevin.sorfkeyboard;

import com.delevin.boluolcs.activity.MySafetyManagmentActivity;
import com.delevin.boluolcs.activity.TiXianActivity;
import com.delevin.sorfkeyboard.PayPasswordView.OnPayListener;
import com.delevin.sorfkeyboard.SorfKeyBoard.keyboardCallBack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 *     @author 李红涛  @version 创建时间：2017-1-4 上午10:40:00    类说明 
 */

public class SetPassword {

	private static String password01;
	private static String password02;
	private static DialogWidget mDialogWidget;

	public SetPassword(Activity activity, SetPwdCallback callback) {
		thod(activity, callback);
	}

	public interface SetPwdCallback {
		void onPwd(String pwd);

		void onTextView(TextView t);
	}

	public static void thod(final Activity activity,
			final SetPwdCallback callback) {
		new SorfKeyBoard(activity, "设置密码", new keyboardCallBack() {

			@Override
			public void onPassword(String pwd) {
				// TODO Auto-generated method stub
				password01 = pwd;
				thod02(activity, callback);
			}

			@Override
			public void onTextView(TextView t) {
				// TODO Auto-generated method stub

			}
		});
	}

	private static void thod02(final Activity activity,
			final SetPwdCallback callback) {
		new SorfKeyBoard(activity, "确认密码", new keyboardCallBack() {

			@Override
			public void onPassword(String pwd) {
				// TODO Auto-generated method stub
				password02 = pwd;
				if (!TextUtils.equals(password01, password02)) {

					Toast.makeText(activity, "两次输入密码不一致", Toast.LENGTH_SHORT)
							.show();
					thod(activity, callback);
				} else {
					callback.onPwd(password02);
				}
			}

			@Override
			public void onTextView(TextView t) {
				// TODO Auto-generated method stub

			}
		});
	}

	public static void getJIanpan(SetPwdCallback callback, Activity activity,
			String title) {
		mDialogWidget = new DialogWidget(activity, getDecorViewDialog(callback,
				activity, title));
		mDialogWidget.show();
	}

	public static void ChongPassword(SetPwdCallback callback) {

	}

	private static View getDecorViewDialog(final SetPwdCallback callback,
			final Activity activity, String title) {
		return PayPasswordView.getInstance(title, activity,
				new OnPayListener() {
					@Override
					public void onCancelPay() {
						activity.startActivity(new Intent(activity,
								MySafetyManagmentActivity.class));
						mDialogWidget.dismiss();
						mDialogWidget = null;
					}

					@Override
					public void onSurePay() {

						mDialogWidget.dismiss();
						mDialogWidget = null;
					}

					@Override
					public void onDismiss(String password) {
						callback.onPwd(password);
						mDialogWidget.dismiss();
						mDialogWidget = null;
					}

					@Override
					public void onVisibility(TextView money) {
						callback.onTextView(money);
					}
				}).getView();
	}

}
