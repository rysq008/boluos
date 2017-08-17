package com.delevin.boluolcs.fragmnet;

import lecho.lib.hellocharts.view.PieChartView;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delevin.boluolcs.base.fragment.BaseFragment;
import com.delevin.boluolcs.utils.PieChartViewUtils;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-6 下午2:08:00    类说明 
 */

public class EarningsFragment extends BaseFragment {
	private int[] color = new int[] { Color.RED, Color.GRAY, Color.BLUE,
			Color.YELLOW };
	private float[] value = new float[] { 12.9f, 200f, 100f, 47.1f };
	private PieChartView chartView;

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.fragment_earnings, container,
				false);

		return view;
	}

	@Override
	protected void getFindById(View view) {
		chartView = (PieChartView) view
				.findViewById(R.id.fragment_earnings_PieCharView);
	}

	@Override
	protected void getData() {
		PieChartViewUtils.getSetChart(chartView, color, value, "总收益(元)",
				"57654.67", getActivity(), Color.WHITE, Color.WHITE, 28, 28);
	}

}
