package com.delevin.boluolcs.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 *     @author 李红涛  @version 创建时间：2016-12-23 下午1:08:40    类说明 
 */

public class TimeCount extends CountDownTimer {

	private TextView bt_code;

	// 参数依次为总时长、计时的时间间隔
	public TimeCount(long millisInFuture, long countDownInterval,
			TextView bt_opt_Code) {
		super(millisInFuture, countDownInterval);
		this.bt_code = bt_opt_Code;
	}

	// 计时完毕时触发
	@Override
	public void onFinish() {
		bt_code.setText("重新验证");
		bt_code.setClickable(true);
	}

	// 计时过程显示
	@Override
	public void onTick(long millisUntilFinished) {
		bt_code.setClickable(false);
		bt_code.setText(millisUntilFinished / 1000 + "s");
	}
}
