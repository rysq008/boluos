package com.delevin.boluolcs.fragmnet;

import java.text.DecimalFormat;

import lecho.lib.hellocharts.view.PieChartView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delevin.boluolcs.base.fragment.BaseFragment;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.PieChartViewUtils;
import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

public class MyZongShouyiFragment extends BaseFragment {
	private TextView tvToubiaoShouyi, tvJiaxiShouyi, tvHongbaoShouyi,
			tvYaoqingShouyi;
	private PieChartView pChartView;
	private String phone;
	private DecimalFormat df = new DecimalFormat("##0.00");
	private float[] value;
	private int[] color;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.fragment_my_zongshouyi,
				container, false);
		phone = BoluoUtils.getShareOneData(getActivity(), "phone");
		return view;
	}

	@Override
	protected void getFindById(View view) {
		tvToubiaoShouyi = (TextView) view
				.findViewById(R.id.my_zongshouyi_tv_toubiaolixi_shouyi);
		tvJiaxiShouyi = (TextView) view
				.findViewById(R.id.my_zongshouyi_tv_jiaxi_shouyi);
		tvHongbaoShouyi = (TextView) view
				.findViewById(R.id.my_zongshouyi_tv_hongbao_shouyi);
		tvYaoqingShouyi = (TextView) view
				.findViewById(R.id.my_zongshouyi_tv_yaoqingguanxi_shouyi);
		pChartView = (PieChartView) view
				.findViewById(R.id.my_zongshouyi_PieCharView);
	}

	@Override
	protected void getData() {
		String z_activity_interest = getActivity().getIntent().getStringExtra(
				"z_activity_interest");
		String z_hongbao = getActivity().getIntent()
				.getStringExtra("z_hongbao");
		String z_interest = getActivity().getIntent().getStringExtra(
				"z_interest");
		String z_yaoqing_shouyi = getActivity().getIntent().getStringExtra(
				"z_yaoqing_shouyi");
		String sum_profit = getActivity().getIntent().getStringExtra(
				"sum_profit");
		if (!TextUtils.isEmpty(z_activity_interest)
				&& !TextUtils.isEmpty(z_hongbao)
				&& !TextUtils.isEmpty(z_interest)
				&& !TextUtils.isEmpty(z_yaoqing_shouyi)
				&& !TextUtils.isEmpty(sum_profit)) {

			tvToubiaoShouyi.setText(df.format(QntUtils.getDouble(z_interest))
					+ "");
			tvJiaxiShouyi.setText(df.format(QntUtils
					.getDouble(z_activity_interest)) + "");
			tvHongbaoShouyi.setText(df.format(QntUtils.getDouble(z_hongbao))
					+ "");
			tvYaoqingShouyi.setText(df.format(QntUtils
					.getDouble(z_yaoqing_shouyi)) + "");
			float toubiao2 = QntUtils.getFloat(z_interest);
			float jaixi2 = QntUtils.getFloat(z_activity_interest);
			float hongbao2 = QntUtils.getFloat(z_hongbao);
			float yaoqing2 = QntUtils.getFloat(z_yaoqing_shouyi);
			if (yaoqing2 == 0 && hongbao2 != 0 && toubiao2 != 0 && jaixi2 != 0) {
				value = new float[] { toubiao2, jaixi2, hongbao2 };
				color = new int[] {
						getActivity().getResources()
								.getColor(R.color.juhuangse),
						getActivity().getResources().getColor(
								R.color.boluo_Yellow),
						getActivity().getResources().getColor(R.color.danlanse) };
			} else if (yaoqing2 != 0 && hongbao2 == 0 && toubiao2 != 0
					&& jaixi2 != 0) {
				value = new float[] { toubiao2, jaixi2, yaoqing2 };
				color = new int[] {
						getActivity().getResources()
								.getColor(R.color.juhuangse),
						getActivity().getResources().getColor(
								R.color.boluo_Yellow),
						getActivity().getResources().getColor(R.color.qingse) };
			} else if (yaoqing2 != 0 && hongbao2 != 0 && toubiao2 != 0
					&& jaixi2 == 0) {
				value = new float[] { toubiao2, hongbao2, yaoqing2 };
				color = new int[] {
						getActivity().getResources()
								.getColor(R.color.juhuangse),
						getActivity().getResources().getColor(R.color.danlanse),
						getActivity().getResources().getColor(R.color.qingse) };
			} else if (yaoqing2 != 0 && hongbao2 != 0 && toubiao2 == 0
					&& jaixi2 != 0) {
				value = new float[] { jaixi2, hongbao2, yaoqing2 };
				color = new int[] {
						getActivity().getResources().getColor(
								R.color.boluo_Yellow),
						getActivity().getResources().getColor(R.color.danlanse),
						getActivity().getResources().getColor(R.color.qingse) };
			} else if (yaoqing2 != 0 && hongbao2 != 0 && toubiao2 == 0
					&& jaixi2 == 0) {
				value = new float[] { hongbao2, yaoqing2 };
				color = new int[] {
						getActivity().getResources().getColor(R.color.danlanse),
						getActivity().getResources().getColor(R.color.qingse) };
			} else if (yaoqing2 != 0 && hongbao2 == 0 && toubiao2 == 0
					&& jaixi2 != 0) {
				value = new float[] { jaixi2, yaoqing2 };
				color = new int[] {
						getActivity().getResources().getColor(
								R.color.boluo_Yellow),
						getActivity().getResources().getColor(R.color.qingse) };
			} else if (yaoqing2 == 0 && hongbao2 != 0 && toubiao2 == 0
					&& jaixi2 != 0) {
				value = new float[] { jaixi2, hongbao2 };
				color = new int[] {
						getActivity().getResources().getColor(
								R.color.boluo_Yellow),
						getActivity().getResources().getColor(R.color.danlanse) };
			} else if (yaoqing2 == 0 && hongbao2 == 0 && toubiao2 != 0
					&& jaixi2 != 0) {
				value = new float[] { toubiao2, jaixi2 };
				color = new int[] {
						getActivity().getResources()
								.getColor(R.color.juhuangse),
						getActivity().getResources().getColor(
								R.color.boluo_Yellow) };
			} else if (yaoqing2 == 0 && hongbao2 != 0 && toubiao2 != 0
					&& jaixi2 == 0) {
				value = new float[] { jaixi2, hongbao2 };
				color = new int[] {
						getActivity().getResources().getColor(
								R.color.boluo_Yellow),
						getActivity().getResources().getColor(R.color.danlanse) };
			} else if (yaoqing2 != 0 && hongbao2 == 0 && toubiao2 != 0
					&& jaixi2 != 0) {
				value = new float[] { yaoqing2, jaixi2 };
				color = new int[] {
						getActivity().getResources().getColor(
								R.color.boluo_Yellow),
						getActivity().getResources().getColor(R.color.qingse) };
			} else if (yaoqing2 != 0 && hongbao2 == 0 && toubiao2 == 0
					&& jaixi2 == 0) {
				value = new float[] { yaoqing2 };
				color = new int[] { getActivity().getResources().getColor(
						R.color.qingse) };
			} else if (yaoqing2 == 0 && hongbao2 != 0 && toubiao2 == 0
					&& jaixi2 == 0) {
				value = new float[] { hongbao2 };
				color = new int[] { getActivity().getResources().getColor(
						R.color.danlanse) };
			} else if (yaoqing2 == 0 && hongbao2 == 0 && toubiao2 != 0
					&& jaixi2 == 0) {
				value = new float[] { toubiao2 };
				color = new int[] { getActivity().getResources().getColor(
						R.color.juhuangse) };
			} else if (yaoqing2 == 0 && hongbao2 == 0 && toubiao2 == 0
					&& jaixi2 != 0) {
				value = new float[] { jaixi2 };
				color = new int[] { getActivity().getResources().getColor(
						R.color.boluo_Yellow) };
			} else {
				value = new float[] { toubiao2, jaixi2, hongbao2, yaoqing2 };
				color = new int[] {
						getActivity().getResources()
								.getColor(R.color.juhuangse),
						getActivity().getResources().getColor(
								R.color.boluo_Yellow),
						getActivity().getResources().getColor(R.color.danlanse),
						getActivity().getResources().getColor(R.color.qingse) };
			}

			int zicolor = getActivity().getResources().getColor(R.color.gray);
			int gray_shou = getActivity().getResources().getColor(
					R.color.wwwwww);
			PieChartViewUtils.getSetChart(pChartView, color, value, "总收益(元)",
					df.format(QntUtils.getDouble(sum_profit)), getActivity(),
					zicolor, gray_shou, 30, 60);
		}
	}
}
