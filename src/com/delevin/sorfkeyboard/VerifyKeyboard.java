package com.delevin.sorfkeyboard;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.sorfkeyboard.SorfKeyBoard.keyboardCallBack;

/**
 *     @author 李红涛  @version 创建时间：2017-1-4 上午11:46:34    类说明 
 */
public class VerifyKeyboard {
	public static void thod(final Activity activity, final boolean b,
			final String pwd_old, final String money,
			final VerifyCallBack callBack) {

		new SorfKeyBoard(activity, "提现密码", new keyboardCallBack() {

			@Override
			public void onPassword(String pwd) {

				if (b) {
				} else {
					Toast.makeText(activity, "密码错误", Toast.LENGTH_SHORT).show();
					thod(activity, b, pwd_old, money, callBack);
				}
			}

			@Override
			public void onTextView(TextView t) {
				t.setText("提现密码");
			}
		});

	}

	public interface VerifyCallBack {
		void onPassword(String password);
	}
}
