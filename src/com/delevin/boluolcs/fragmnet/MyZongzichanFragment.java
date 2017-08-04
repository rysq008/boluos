package com.delevin.boluolcs.fragmnet;

import java.text.DecimalFormat;

import lecho.lib.hellocharts.view.PieChartView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delevin.boluolcs.activity.TouziMoreObjectActivity;
import com.delevin.boluolcs.base.fragment.BaseFragment;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.PieChartViewUtils;
import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

public class MyZongzichanFragment extends BaseFragment implements
		OnClickListener {
	private TextView tvZhanghu, tvDongjie, tvDaishou;
	private RelativeLayout rlDongjie, rlDaishou;
	private PieChartView pChartView;
	private String strZong;
	private DecimalFormat df = new DecimalFormat("##0.00");
	private ImageView imgRight;
	private RelativeLayout rlGoneOr;
	private RelativeLayout rlGone1, rlGone2;
	private String aa = "1";
	private TextView tvWei, tvYi;
	private float[] value;
	private Button btnWoyaoTouzi;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_zongzichan_rl_dongjieyue:
			BoluoUtils.getDilogDome(getActivity(), "冻结金额说明",
					"是指已投资还未计息的金额和提现正在审核中的金额", "知道了");
			break;
		case R.id.my_zongzichan_rl_daishoueyue:
			BoluoUtils.getDilogDome(getActivity(), "待收金额说明",
					"是指回款中的投资本金加上对应的利息之和", "知道了");
			break;
		case R.id.my_zongzichan_relativeLayout2:

			if (aa.equals("1")) {
				imgRight.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_down));
				rlGone1.setVisibility(View.VISIBLE);
				rlGone2.setVisibility(View.VISIBLE);
				aa = "2";
			} else if (aa.equals("2")) {
				imgRight.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_right));
				rlGone1.setVisibility(View.GONE);
				rlGone2.setVisibility(View.GONE);
				aa = "1";
			}
			break;
		case R.id.my_zongzichan_btn_woyaotouzi:
			Intent intentMore = new Intent(getActivity(),
					TouziMoreObjectActivity.class);
			intentMore.putExtra("type", "1");
			startActivity(intentMore);

			break;
		default:
			break;
		}

	}

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.fragment_my_zongzichan,
				container, false);
		return view;
	}

	@Override
	protected void getFindById(View view) {
		tvZhanghu = (TextView) view
				.findViewById(R.id.my_zongzichan_tv_zhanghuyue);
		tvDongjie = (TextView) view
				.findViewById(R.id.my_zongzichan_tv_dongjieyue);
		tvDaishou = (TextView) view
				.findViewById(R.id.my_zongzichan_tv_daishoueyue);
		pChartView = (PieChartView) view
				.findViewById(R.id.my_zongzichan_PieCharView);
		rlDongjie = (RelativeLayout) view
				.findViewById(R.id.my_zongzichan_rl_dongjieyue);
		rlDaishou = (RelativeLayout) view
				.findViewById(R.id.my_zongzichan_rl_daishoueyue);
		imgRight = (ImageView) view.findViewById(R.id.my_zongzichan_img_right);
		rlGone1 = (RelativeLayout) view.findViewById(R.id.relativeLayout1_gone);
		rlGone2 = (RelativeLayout) view.findViewById(R.id.relativeLayout2_gone);
		tvWei = (TextView) view.findViewById(R.id.my_zongzichan_tv_wei);
		tvYi = (TextView) view.findViewById(R.id.my_zongzichan_tv_yi);
		rlGoneOr = (RelativeLayout) view
				.findViewById(R.id.my_zongzichan_relativeLayout2);
		btnWoyaoTouzi = (Button) view
				.findViewById(R.id.my_zongzichan_btn_woyaotouzi);
		btnWoyaoTouzi.setOnClickListener(this);
		rlDongjie.setOnClickListener(this);
		rlDaishou.setOnClickListener(this);
		rlGoneOr.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		String freezeamount = getActivity().getIntent().getStringExtra(
				"freezeamount");
		String remain_balance = getActivity().getIntent().getStringExtra(
				"remain_balance");
		String uncollectedamount = getActivity().getIntent().getStringExtra(
				"uncollectedamount");
		strZong = getActivity().getIntent().getStringExtra("total_money");
		String remainamount = getActivity().getIntent().getStringExtra(
				"remainamount");
		String rechargeamount = getActivity().getIntent().getStringExtra(
				"rechargeamount");
		if (!TextUtils.isEmpty(freezeamount)
				&& !TextUtils.isEmpty(remain_balance)
				&& !TextUtils.isEmpty(uncollectedamount)
				&& !TextUtils.isEmpty(strZong)
				&& !TextUtils.isEmpty(remainamount)
				&& !TextUtils.isEmpty(rechargeamount)) {

			tvWei.setText("  " + df.format(QntUtils.getDouble(rechargeamount))
					+ "");
			tvYi.setText("  " + df.format(QntUtils.getDouble(remainamount))
					+ "");

			tvZhanghu.setText(df.format(QntUtils.getDouble(remain_balance))
					+ "");
			tvDongjie.setText(df.format(QntUtils.getDouble(freezeamount)) + "");
			tvDaishou.setText(df.format(QntUtils.getDouble(uncollectedamount))
					+ "");

			float bb = Float.parseFloat(remain_balance);
			float cc = Float.parseFloat(freezeamount);
			float dd = Float.parseFloat(uncollectedamount);
			int[] color;
			if (cc != 0 && dd != 0 && bb == 0) {
				value = new float[] { cc, dd };
				color = new int[] {
						getActivity().getResources()
								.getColor(R.color.boluo_Yellow),
						getActivity().getResources().getColor(
								R.color.danlanse) };
			} else if (dd != 0 && bb != 0 && cc == 0) {
				value = new float[] { bb, dd };
				color = new int[] {
						getActivity().getResources()
								.getColor(R.color.juhuangse),
						getActivity().getResources().getColor(R.color.danlanse) };
			} else if (cc != 0 && bb != 0 && dd == 0) {
				value = new float[] { bb, cc };
				color = new int[] {
						getActivity().getResources()
								.getColor(R.color.juhuangse),
						getActivity().getResources().getColor(
								R.color.boluo_Yellow) };
			} else if (cc == 0 && dd == 0) {
				value = new float[] { bb };
				color = new int[] { getActivity().getResources().getColor(
						R.color.juhuangse) };
			} else if (cc != 0 && dd == 0 && bb == 0) {
				value = new float[] { cc };
				color = new int[] { getActivity().getResources().getColor(
						R.color.boluo_Yellow) };
			} else if (cc == 0 && dd != 0 && bb == 0) {
				value = new float[] { dd };
				color = new int[] { getActivity().getResources().getColor(
						R.color.danlanse) };
			} else {
				value = new float[] { bb, cc, dd };
				color = new int[] {
						getActivity().getResources()
								.getColor(R.color.juhuangse),
						getActivity().getResources().getColor(
								R.color.boluo_Yellow),
						getActivity().getResources().getColor(R.color.danlanse) };
			}
			// 进度圈设置
			PieChartViewUtils.getSetChart(pChartView, color, value, "总资产(元)",
					df.format(QntUtils.getDouble(strZong)) + "", getActivity(),
					getActivity().getResources().getColor(R.color.gray),
					Color.BLACK, 30, 60);
		}
	}

}
