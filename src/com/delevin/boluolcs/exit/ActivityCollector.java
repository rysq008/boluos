package com.delevin.boluolcs.exit;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;

/**
 *     @author 李红涛  @version 创建时间：2016-3-24 上午11:54:11    类说明 
 */

public class ActivityCollector {

	private static List<Activity> activities = new ArrayList<Activity>();

	public static void addActicity(Activity activity) {
		activities.add(activity);
	}

	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	public static void finishActivity() {
		for (Activity activity : activities) {
			activity.finish();
		}
	}
}
