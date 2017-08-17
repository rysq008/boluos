package com.delevin.sorfkeyboard;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.delevin.sorfkeyboard.KeyboardEnum.ActionEnum;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnLongClick;
import com.pusupanshi.boluolicai.R;

/**
 * Dialog 显示视图
 * 
 * @author LanYan
 * 
 */
@SuppressLint("InflateParams")
public class PayPasswordView {

	@ViewInject(R.id.pay_keyboard_del)
	private ImageButton del;
	@ViewInject(R.id.pay_keyboard_zero)
	private Button zero;
	@ViewInject(R.id.pay_keyboard_one)
	private Button one;
	@ViewInject(R.id.pay_keyboard_two)
	private Button two;
	@ViewInject(R.id.pay_keyboard_three)
	private Button three;
	@ViewInject(R.id.pay_keyboard_four)
	private Button four;
	@ViewInject(R.id.pay_keyboard_five)
	private Button five;
	@ViewInject(R.id.pay_keyboard_sex)
	private Button sex;
	@ViewInject(R.id.pay_keyboard_seven)
	private Button seven;
	@ViewInject(R.id.pay_keyboard_eight)
	private Button eight;
	@ViewInject(R.id.pay_keyboard_nine)
	private Button nine;
	@ViewInject(R.id.pay_cancels)
	private TextView cancel;
	@ViewInject(R.id.pay_sure)
	private TextView sure;
	@ViewInject(R.id.pay_box1)
	private TextView box1;
	@ViewInject(R.id.pay_box2)
	private TextView box2;
	@ViewInject(R.id.pay_box3)
	private TextView box3;
	@ViewInject(R.id.pay_box4)
	private TextView box4;
	@ViewInject(R.id.pay_box5)
	private TextView box5;
	@ViewInject(R.id.pay_box6)
	private TextView box6;
	@ViewInject(R.id.pay_title)
	private TextView Stitle;
	@ViewInject(R.id.pay_content)
	private TextView content;

	private ArrayList<String> mList = new ArrayList<String>();
	private View mView;
	private OnPayListener listener;

	public PayPasswordView(String title, Context mContext,
			OnPayListener listener) {
		getDecorView(title, mContext, listener);
	}

	public static PayPasswordView getInstance(String title, Context mContext,
			OnPayListener listener) {
		return new PayPasswordView(title, mContext, listener);
	}

	// 无需验证密码的方法

	public static void getInstance1(String monney, Context mContext) {

	};

	public void getDecorView(String title, Context mContext,
			OnPayListener listener) {

		this.listener = listener;
		mView = LayoutInflater.from(mContext).inflate(
				R.layout.boluos_paypassword, null);
		ViewUtils.inject(this, mView);
		// content.setText( monney + "元");
		listener.onVisibility(content);
		Stitle.setText(title);
	}

	@OnClick({ R.id.pay_keyboard_del, R.id.pay_keyboard_zero,
			R.id.pay_keyboard_one, R.id.pay_keyboard_two,
			R.id.pay_keyboard_three, R.id.pay_keyboard_four,
			R.id.pay_keyboard_five, R.id.pay_keyboard_sex,
			R.id.pay_keyboard_seven, R.id.pay_keyboard_eight,
			R.id.pay_keyboard_nine, R.id.pay_cancels, R.id.pay_sure })
	private void onClick(View v) {
		if (v == zero) {
			parseActionType(KeyboardEnum.zero);
		} else if (v == one) {
			parseActionType(KeyboardEnum.one);
		} else if (v == two) {
			parseActionType(KeyboardEnum.two);
		} else if (v == three) {
			parseActionType(KeyboardEnum.three);
		} else if (v == four) {
			parseActionType(KeyboardEnum.four);
		} else if (v == five) {
			parseActionType(KeyboardEnum.five);
		} else if (v == sex) {
			parseActionType(KeyboardEnum.sex);
		} else if (v == seven) {
			parseActionType(KeyboardEnum.seven);
		} else if (v == eight) {
			parseActionType(KeyboardEnum.eight);
		} else if (v == nine) {
			parseActionType(KeyboardEnum.nine);
		} else if (v == cancel) {
			parseActionType(KeyboardEnum.cancel);
		} else if (v == sure) {
			parseActionType(KeyboardEnum.sure);
		} else if (v == del) {
			parseActionType(KeyboardEnum.del);
		}
	}

	@OnLongClick(R.id.pay_keyboard_del)
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		parseActionType(KeyboardEnum.longdel);
		return false;
	}

	private void parseActionType(KeyboardEnum type) {
		// TODO Auto-generated method stub
		if (type.getType() == ActionEnum.add) {
			if (mList.size() < 6) {
				mList.add(type.getValue());
				updateUi();
			}
		} else if (type.getType() == ActionEnum.delete) {
			if (mList.size() > 0) {
				mList.remove(mList.get(mList.size() - 1));
				updateUi();
			}
		} else if (type.getType() == ActionEnum.cancel) {
			listener.onCancelPay();
		} else if (type.getType() == ActionEnum.sure) {

			listener.onSurePay();
		} else if (type.getType() == ActionEnum.longClick) {
			mList.clear();
			updateUi();
		}
	}

	private void updateUi() {
		// TODO Auto-generated method stub
		if (mList.size() == 0) {
			box1.setText("");
			box2.setText("");
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		} else if (mList.size() == 1) {
			box1.setText(mList.get(0));
			box2.setText("");
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		} else if (mList.size() == 2) {
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		} else if (mList.size() == 3) {
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText("");
			box5.setText("");
			box6.setText("");
		} else if (mList.size() == 4) {
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText("");
			box6.setText("");
		} else if (mList.size() == 5) {
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText(mList.get(4));
			box6.setText("");
		} else if (mList.size() == 6) {
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText(mList.get(4));
			box6.setText(mList.get(5));
			String payValue = "";
			for (int i = 0; i < mList.size(); i++) {
				payValue += mList.get(i);
			}
			listener.onDismiss(payValue);
		}
	}

	public interface OnPayListener {
		void onCancelPay();

		void onSurePay();

		void onDismiss(String password);

		void onVisibility(TextView money);

	}

	public View getView() {
		return mView;
	}
}
