package com.delevin.boluolcs.utils;

import java.util.List;

import org.json.JSONObject;

import android.util.Log;

import com.alibaba.fastjson.JSON;

public class JSONUtil {

	private static final String TAG = "JSONUtil";

	public static <T> T getObject(JSONObject response, String jsonkey,
			Class<T> clazz) {
		String result = "";
		result = response.optString(jsonkey);
		T object = getData(result, clazz);
		if (object != null) {
			Log.i(TAG, object.toString());
		}
		return object;
	}

	public static <T> T getObject(JSONObject response, Class<T> clazz) {
		T object = getData(response.toString(), clazz);
		return object;
	}

	public static String getString(JSONObject jsonObject, String jsonkey,
			String valuekey) {
		String result = "";
		JSONObject subJsonObject = null;
		subJsonObject = jsonObject.optJSONObject(jsonkey);
		if (subJsonObject == null) {
			return null;
		}
		result = subJsonObject.optString(valuekey);
		return result;
	}

	public static <T> List<T> getList(JSONObject response, String jsonkey,
			Class<T> clazz) {
		String result = "";
		result = response.optString(jsonkey);
		List<T> list = JSON.parseArray(result, clazz);
		return list;
	}

	private static <T> T getData(String result, Class<T> clazz) {
		T object = null;
		try {
			object = JSON.parseObject(result, clazz);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> List<T> getList(String result, Class<T> clazz) {
		try {
			List<T> list = JSON.parseArray(result, clazz);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
