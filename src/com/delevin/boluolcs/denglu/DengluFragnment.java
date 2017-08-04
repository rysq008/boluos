package com.delevin.boluolcs.denglu;

import java.util.Map;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.delevin.boluolcs.base.fragment.BaseFragment;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.NetUtils;
import com.pusupanshi.boluolicai.R;

/**
 * 
 * @author 李红涛E-mail:
 * @version 创建时间：2017-3-13 下午1:13:48 类说明
 * 
 */
public class DengluFragnment extends BaseFragment implements OnClickListener {

	private EditText etPasswd;
	private Button bt_denglu;
	public static LoginActivity loginActivity;
	private TextView fragment__goZhaomima;
	private String phone;
	private boolean is_pwd;
	private RelativeLayout rlCallPhone;
	private LinearLayout linQ;
	private ImageView imgQ;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.denglu_bt_a:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				BoluoUtils.getDenglu(phone, "passwd", etPasswd.getText()
						.toString().trim(), getActivity(), is_pwd, imgQ, linQ);
			} else {
				Toast.makeText(getActivity(), "您当前的网络不可用", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.fragment__goZhaomima:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				Intent intent = new Intent(getActivity(),
						ZhaoHuiSecretActivity.class);
				intent.putExtra("phone", phone);
				startActivity(intent);
			} else {
				Toast.makeText(getActivity(), "您当前的网络不可用", Toast.LENGTH_SHORT)
						.show();
			}

			break;
		case R.id.denglu_callPhone01:
			AndroidUtils.dial(getActivity(), "400-032-0596");
			break;
		default:
			break;
		}
	}

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {

		View view = inflaters.inflate(R.layout.fragment_denglus, container,
				false);
		fragment__goZhaomima = (TextView) view
				.findViewById(R.id.fragment__goZhaomima);
		fragment__goZhaomima.setOnClickListener(this);
		etPasswd = (EditText) view.findViewById(R.id.fragment_Login_password);
		bt_denglu = (Button) view.findViewById(R.id.denglu_bt_a);
		linQ = (LinearLayout) view.findViewById(R.id.denglu_layout_Q);
		imgQ = (ImageView) view.findViewById(R.id.denglu_img_Q);
		rlCallPhone = (RelativeLayout) view
				.findViewById(R.id.denglu_callPhone01);
		rlCallPhone.setOnClickListener(this);
		bt_denglu.setOnClickListener(this);
		return view;
	}

	@Override
	protected void getFindById(View view) {
		getShareData();
	}

	private void getShareData() {
		SharedPreferences preferences = getActivity().getSharedPreferences(
				"is_set_pwd", 0);
		is_pwd = preferences.getBoolean("is_pwd", false);
		Map<String, String> data = BoluoUtils.getShareData(getActivity());
		phone = getActivity().getIntent().getStringExtra("phone");
	}

	@Override
	protected void getData() {

	}
}
