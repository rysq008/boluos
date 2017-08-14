package com.delevin.boluolcs.gestureedit;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.boluolcs.base.activity.BaseActivity;
import com.delevin.boluolcs.denglu.DengluActvity;
import com.delevin.boluolcs.denglu.ZhuActivity;
import com.delevin.boluolcs.gestureedit.fund.widget.GestureContentView;
import com.delevin.boluolcs.main.MainActivity;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

/**
 * 
 * 手势绘制/校验界面
 * 
 */
public class GestureVerifyActivity extends BaseActivity implements
		android.view.View.OnClickListener {
	/** 手机号码 */
	public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
	/** 意图 */
	public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";
	// private RelativeLayout mTopLayout;
	private TextView mTextTitle;
	// private ImageView mImgUserLogo;
	private TextView mTextPhoneNumber;
	private TextView mTextCancel;
	private TextView mTextTip;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;
	private TextView mTextForget;
	private TextView mTextOther;
	private String strPhone;

	@Override
	protected void findViews() {
		setContentView(R.layout.activity_gesture_verify);
		ObtainExtraData();
		setUpViews();
		setUpListeners();
	}

	@Override
	protected void getData() {
	}

	private void ObtainExtraData() {
		getIntent().getStringExtra(PARAM_PHONE_NUMBER);
		getIntent().getIntExtra(PARAM_INTENT_CODE, 0);
	}

	private void setUpViews() {

		// mTopLayout = (RelativeLayout) findViewById(R.id.top_layout);
		mTextTitle = (TextView) findViewById(R.id.text_title);
		mTextTitle.setText("输入手势密码");
		// mImgUserLogo = (ImageView) findViewById(R.id.user_logo);
		mTextPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
		strPhone = BoluoUtils.getShareOneData(GestureVerifyActivity.this,
				"phone");
		if (!BoluoUtils.isEmpty(strPhone)) {
			mTextPhoneNumber.setText("账户:" + strPhone);
		} else {
			mTextPhoneNumber.setVisibility(View.GONE);
		}
		mTextCancel = (TextView) findViewById(R.id.text_cancel);
		mTextTip = (TextView) findViewById(R.id.text_tip);
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
		mTextForget = (TextView) findViewById(R.id.text_forget_gesture);
		mTextForget.setOnClickListener(this);
		mTextOther = (TextView) findViewById(R.id.text_other_account);
		SharedPreferences preferences = getSharedPreferences("is_set_pwd", 0);
		String pwd = preferences.getString("pwd", "pwd");

		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, true, pwd,
				new GestureCallBack() {

					@Override
					public void onGestureCodeInput(String inputCode) {

					}

					String shareNums = BoluoUtils.getShareOneData(
							getApplicationContext(), "gesNum");

					@Override
					public void checkedSuccess() {
						if (!TextUtils.isEmpty(shareNums)) {
							if (QntUtils.getInt(shareNums) <= 0) {
								getForget();
								return;
							}
						}
						Map<String, String> map = new HashMap<String, String>();
						map.put("gesNum", "5");
						BoluoUtils.getShareCommit(getApplicationContext(), map);

						mGestureContentView.clearDrawlineState(0L);
						startActivity(new Intent(GestureVerifyActivity.this,
								MainActivity.class));
						GestureVerifyActivity.this.finish();

					}

					@Override
					public void checkedFail() {
						String shareNum = BoluoUtils.getShareOneData(
								getApplicationContext(), "gesNum");
						int num = QntUtils.getInt(shareNum);
						num = num - 1;
						mGestureContentView.clearDrawlineState(1300L);
						mTextTip.setVisibility(View.VISIBLE);
						Map<String, String> map = new HashMap<String, String>();
						map.put("gesNum", num + "");
						BoluoUtils.getShareCommit(getApplicationContext(), map);

						String shareNums = BoluoUtils.getShareOneData(
								getApplicationContext(), "gesNum");
						if (QntUtils.getInt(shareNums) >= 0) {

							String numString = "手势密码错误，您还可以输入" + shareNums
									+ "次";
							if (TextUtils.equals(shareNums + "", "0")) {
								getForget();
								finish();
							}
							// mTextTip.setText(Html.fromHtml("<font color='#c70c1e'>numString</font>"));
							mTextTip.setTextColor(getResources().getColor(
									R.color.c70c1e));
							mTextTip.setText(numString);
							// 左右移动动画
							Animation shakeAnimation = AnimationUtils
									.loadAnimation(GestureVerifyActivity.this,
											R.anim.shake);
							mTextTip.startAnimation(shakeAnimation);
						} else {
							Toast.makeText(getApplicationContext(),
									"手势密码错误，五次尝试机会已用完", Toast.LENGTH_SHORT)
									.show();
							getForget();
						}

					}
				});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
	}

	private void setUpListeners() {
		mTextCancel.setOnClickListener(this);
		mTextForget.setOnClickListener(this);
		mTextOther.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_cancel:
			this.finish();
			break;
		case R.id.text_forget_gesture:

			getForget();
			break;
		default:
			break;
		}
	}

	private void getForget() {
		if (!BoluoUtils.isEmpty(strPhone)) {
			Intent intent = new Intent(GestureVerifyActivity.this,
					DengluActvity.class);
			intent.putExtra("phone", strPhone);
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent(GestureVerifyActivity.this,
					ZhuActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
