package com.delevin.boluolcs.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.TextView;

import com.delevin.boluolcs.interfaces.PhoneCodeCallBack;

/*
 * 整形转化为字符串
 * */

public class QntUtils {
	public static int getInt(String value) {
		int v = Integer.parseInt(value);
		return v;
	}

	public static String getString(int value) {
		String s = Integer.toString(value);
		return s;
	}

	@SuppressLint("UseValueOf")
	public static int getDoubleToInt(double value) {
		Double d = new Double(value);
		int doubleToInt = d.intValue();
		return doubleToInt;

	}

	@SuppressLint("UseValueOf")
	public static int getFloatToInt(Float value) {
		Double d = new Double(value);
		int doubleToInt = d.intValue();
		return doubleToInt;

	}

	public static String getURL(String url, Object params) {
		String format = String.format(url, params);
		return format;
	}

	public static String getURLs(String url, Object param, Object params) {
		String format = String.format(url, params, params);
		return format;
	}

	public static Boolean getBoolean(String value) {
		boolean b = Boolean.getBoolean(value);
		return b;
	}

	public static double getDouble(String value) {

		double d = Double.parseDouble(value);
		return d;
	}

	public static float getFloat(String value) {
		float f = Float.parseFloat(value);
		return f;

	}

	public static String getFormat(double value) {

		String f = new DecimalFormat("#0.00").format(value);
		return f;
	}

	public static String getFormatOne(double value) {

		String f = new DecimalFormat("#0.0").format(value);
		return f;
	}

	public static String getdoubleToString(double value) {
		String ds = String.valueOf(value);
		return ds;
	}

	public static String getSubString(String value) {
		String substring = value.substring(0, value.length() - 2);
		return substring;
	}

	public static Long getLong(String value) {
		return Long.parseLong(value);
	}

	public static String getSubStringW(String value, int start, int end) {
		return value.substring(start, end);
	}

	public static void bubble(Integer[] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length - 1 - i; j++) {
				if (data[j] > data[j + 1]) { // 如果后一个数小于前一个数交换
					int tmp = data[j];
					data[j] = data[j + 1];
					data[j + 1] = tmp;
				}
			}
		}
	}

	public static <T> List<T> getList(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).equals(list.get(i))) {
					list.remove(j);
				}
			}
		}
		return list;
	}

	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return Base64.encodeToString(buffer, Base64.DEFAULT);
	}

	public static String encodeTobase64(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
		return imageEncoded;
	}

	public static String getRadom(String strRand) {
		for (int i = 0; i < 20; i++) {
			strRand += String.valueOf((int) (Math.random() * 10));
		}
		return strRand;
	}

	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	// 保留一位小数
	public static String getNumberFormat(int value) {

		java.text.DecimalFormat df = new java.text.DecimalFormat("#.0");

		if (value < 10000) {

			return value + "";

		} else if (value < 100000000) {

			return df.format(value / 10000.0) + "万";

		} else {

			return df.format(value / 100000000.0) + "亿";

		}

	}

	public static String getBase64(String username, String password) {
		if (!TextUtils.equals(username, "") && !TextUtils.equals(password, "")
				&& username != null && password != null) {
			String credentials = username + ":" + password;
			return "Basic "
					+ Base64.encodeToString(credentials.getBytes(),
							Base64.DEFAULT).toString().trim();
		} else {
			return "";
		}

	}

	public static double getjingdu(double value, int num) {
		DecimalFormat df = null;
		if (num == 1) {
			df = new DecimalFormat("##0.0");
		} else if (num == 2) {
			df = new DecimalFormat("##0.00");
		}
		df.format(value);
		return 0;

	}

	public static String getYMD(String timeString) {
		String pat = "yyyy-MM-dd"; // 格式
		SimpleDateFormat sdf = new SimpleDateFormat(pat); // 实例化模板对象
		Date date = new Date();
		try {
			date = sdf.parse(timeString); // 将给定的字符串中的日期提取出来
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(date); // 将日期变为新的格式
	}

	/**
	 * 对字符串md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			return null;
		}
	}

	// 银行卡号处理
	public static String getBankCode(String bankCode) {
		return QntUtils.getSubStringW(bankCode, 0, 4)
				+ "****"
				+ QntUtils.getSubStringW(bankCode, bankCode.length() - 4,
						bankCode.length());
	}

	// 持卡人姓名处理
	public static void getReanName(String reanName, TextView realNameView) {
		if (reanName.length() == 2) {
			realNameView.setText("*"
					+ (QntUtils.getSubStringW(reanName, 1, reanName.length())));
		} else {
			realNameView.setText(reanName.substring(0, reanName.length() - 1)
					+ "*");
		}
	}

	public static void getCallPhoneCode(String phone, Context context,
			PhoneCodeCallBack phoneCodeCallBack) {
		// TODO Auto-generated method stub

	}

	// 保留小数点两位
	public static String formateRate(String rateStr) {
		if (rateStr.indexOf(".") != -1) {
			// 获取小数点的位置
			int num = 0;
			num = rateStr.indexOf(".");

			// 获取小数点后面的数字 是否有两位 不足两位补足两位
			String dianAfter = rateStr.substring(0, num + 1);
			String afterData = rateStr.replace(dianAfter, "");
			if (afterData.length() < 2) {
				afterData = afterData + "0";
			} else {
				afterData = afterData;
			}
			return rateStr.substring(0, num) + "." + afterData.substring(0, 2);
		} else {
			if (rateStr == "1") {
				return "100";
			} else {
				return rateStr;
			}
		}
	}

}
