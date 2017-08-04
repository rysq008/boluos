package com.delevin.boluolcs.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonObjectUtils {
	/**
	 * 登录，注册 ，找回密码
	 * */
	public String getDenglu(String data) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(data);
			return jsonObject.getString("msg");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static void getContent(JSONObject jsonObject, String[] key) {
		try {
			JSONObject object = jsonObject.getJSONObject("content");
			for (int i = 0; i < key.length; i++) {
				object.getString(key[i]);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

}
