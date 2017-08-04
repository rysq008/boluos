package com.delevin.sorfkeyboard;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.delevin.sorfkeyboard.PayPasswordView.OnPayListener;

/**
 *     @author 李红涛  @version 创建时间：2017-1-4 上午10:17:21    类说明 
 */

public class SorfKeyBoard {
	private DialogWidget mDialogWidget;
	private Activity activity;

	public SorfKeyBoard(Activity activity, String title,
			keyboardCallBack callBack) {
		this.activity = activity;
		mDialogWidget = new DialogWidget(activity, getDecorViewDialog(title,
				callBack));
		mDialogWidget.show();
	}

	protected View getDecorViewDialog(String title,
			final keyboardCallBack callBack) {
		// TODO Auto-generated method stub
		return PayPasswordView.getInstance(title, activity,
				new OnPayListener() {
					@Override
					public void onCancelPay() {

					}

					@Override
					public void onSurePay() {

						mDialogWidget.dismiss();
						mDialogWidget = null;
					}

					@Override
					public void onDismiss(String password) {
						callBack.onPassword(password);
						mDialogWidget.dismiss();
						mDialogWidget = null;
					}

					@Override
					public void onVisibility(TextView money) {
						callBack.onTextView(money);
					}
				}).getView();
	}

	public interface keyboardCallBack {
		void onPassword(String pwd);

		void onTextView(TextView t);
	}
}
