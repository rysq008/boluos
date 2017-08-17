package com.delevin.boluolcs.utils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;
import android.content.Context;
import android.graphics.Color;

/**
 *     @author 李红涛  @version 创建时间：2016-12-28 下午1:04:29    类说明 
 */

public class PieChartViewUtils {

	private static PieChartData data;
	private static boolean hasLabels = false;//  是否显示数据
	private static boolean hasLabelsOutside = true;//  数据是否显示在外面
	private static boolean hasCenterCircle = true;//  是否含有中圈，显示下面的内容这个必须为true  
	private static boolean hasCenterText1 = true;//  圆中是否含有内容1  
	private static boolean hasCenterText2 = true;//  圆中是否含有内容2  
	private static boolean isExploded = false;//  是否爆破形式  
	private static boolean hasLabelForSelected = false;//  是否选中显示数据，一般为false 
	private static boolean isValueTouchEnabled = false;// 饼图是否有点击效果
	private static boolean isChartRotationEnabled = false;// 饼图是否可以旋转

	/**
	 * getSetChart(PieChartView,颜色，占的比例，圈内标题，圈内数据，Context，标题颜色，数据颜色，标题大小，数据大小)
	 * */
	public static void getSetChart(PieChartView dChartView, int[] colors,
			float[] numbers, String certerTexttitle, String certerTextValue,
			Context context, int titleColor, int valueColor, int titleSize,
			int valueSize) {

		List<SliceValue> values = new ArrayList<SliceValue>();
		for (int i = 0; i < numbers.length; i++) {
			values.add(new SliceValue(numbers[i], colors[i]));
		}
		data = new PieChartData(values);
		data.setHasLabels(hasLabels);
		data.setHasLabelsOnlyForSelected(hasLabelForSelected);
		data.setHasCenterCircle(hasCenterCircle);
		data.setHasLabelsOutside(hasLabelsOutside);
		data.setValueLabelBackgroundEnabled(false);
		data.setSlicesSpacing(0);
		data.setCenterCircleColor(Color.WHITE);
		data.setCenterCircleScale(0.8f);// 设置中心圆占圆的比例
		data.setSlicesSpacing(0);
		if (isExploded) {

			data.setCenterCircleColor(Color.WHITE);
		}
		if (hasCenterText1) {
			data.setCenterText1(certerTexttitle);
			if (context == null)
				return;
			data.setCenterText1FontSize(ChartUtils.px2sp(context.getResources()
					.getDisplayMetrics().scaledDensity, titleSize));
			data.setCenterText1Color(titleColor);
		}
		if (hasCenterText2) {
			data.setCenterText2(certerTextValue);

			if (context == null)
				return;
			data.setCenterText2FontSize(ChartUtils.px2sp(context.getResources()
					.getDisplayMetrics().scaledDensity, valueSize));
			data.setCenterText2Color(valueColor);
		}

		dChartView.setChartRotationEnabled(isChartRotationEnabled);
		dChartView.setValueTouchEnabled(isValueTouchEnabled);
		dChartView.setPieChartData(data);

	}
}
