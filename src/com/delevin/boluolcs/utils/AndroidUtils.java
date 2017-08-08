package com.delevin.boluolcs.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.delevin.application.Myapplication;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.denglu.ZhuActivity;
import com.delevin.boluolcs.interfaces.DilogCallBack;
import com.delevin.boluolcs.interfaces.PhoneCodeCallBack;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.view.CustomDialog;
import com.pusupanshi.boluolicai.R;
import com.squareup.picasso.Picasso;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

/**
 *     @author 李红涛  @version 创建时间：2016-12-23 上午11:48:54    类说明 
 */

public class AndroidUtils {

	public static void limitTextLenth(final Context context,
			final String content, final EditText textView, final int num) {
		textView.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;
			private int limit = 2;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				temp = s;
				System.out.println("s=" + s);
				/**
				 * 限制输入金额最多为 limit 位小数
				 */
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > limit) {
						s = s.toString().subSequence(0,
								s.toString().indexOf(".") + limit + 1);
						textView.setText(s);
						textView.setSelection(s.length());
					}
				}
				/**
				 * 第一位输入小数点的话自动变换为 0.
				 */
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					textView.setText(s);
					textView.setSelection(2);
				}
				/**
				 * 避免重复输入小数点前的0 ,没有意义
				 */
				if (s.toString().startsWith("0")
						&& s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						textView.setText(s.subSequence(0, 1));
						textView.setSelection(1);
						return;
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// tv_num.setText("" + number);
				selectionStart = textView.getSelectionStart();
				selectionEnd = textView.getSelectionEnd();
				// System.out.println("start="+selectionStart+",end="+selectionEnd);
				if (temp.length() > num) {

					s.delete(selectionStart - 1, selectionEnd);
					textView.setText(s);
					Toast.makeText(context, content + num + "位",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public static int getTime() {
		long time = System.currentTimeMillis();
		final Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(time);
		int apm = mCalendar.get(Calendar.AM_PM);
		return apm;
	}

	public static void getCallPhoneCode(String phone, final Context context,
			final PhoneCodeCallBack callBack) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", phone);
		params.put("tem_id", "42447");
		params.put("key", HEXAndMd5("eb62f6b930" + phone));
		Myapplication.okhttpManger.sendComplexForm(context, false,
				BeanUrl.OBTPHONECODE_STRING, params, new Funck4() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							callBack.onResponse(response);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}

	@SuppressLint("InflateParams")
	public static void getDilog(int img, final Context context,
			final DilogCallBack callBack) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 实例化自定义主题的对话框
		final CustomDialog dialog = new CustomDialog(context,
				R.style.gesturesPassword_dialog);
		View layout = inflater.inflate(R.layout.dilog_guanggao, null);
		dialog.addContentView(layout, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		ImageView img1 = (ImageView) layout
				.findViewById(R.id.window_dilog_red_comeOn);
		img1.setBackgroundResource(img);
		img1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callBack.onPaseat();
				dialog.dismiss();
			}
		});
		layout.findViewById(R.id.window_dilog_red_dissmis).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();
					}
				});
		dialog.show();
	}

	public static void getImg(Context context, String url, ImageView imageView,
			int place, int error) {
		// Picasso.with(context).load(url).placeholder(place).error(error)
		//
		// .into(imageView);
		Glide.with(context).load(url).placeholder(place).error(error)
				.into(imageView);
	}

	public static void getDilogs(final Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 实例化自定义主题的对话框
		CustomDialog dialog = new CustomDialog(context,
				R.style.gesturesPassword_dialog);
		View layout = inflater.inflate(R.layout.layout_dilog_login, null);
		dialog.addContentView(layout, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		TextView dilogReger = (TextView) layout
				.findViewById(R.id.bt_dilog_resger);
		Button dilogLgin = (Button) layout.findViewById(R.id.bt_dilog_login);
		dilogLgin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, ZhuActivity.class));

			}
		});

		dilogReger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, ZhuActivity.class));

			}
		});
		dialog.show();
	}

	// 打电话
	public static void dial(Context context, String number) {
		Class<TelephonyManager> c = TelephonyManager.class;
		Method getITelephonyMethod = null;
		try {
			getITelephonyMethod = c.getDeclaredMethod("getITelephony",
					(Class[]) null);
			getITelephonyMethod.setAccessible(true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		try {
			TelephonyManager tManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			Object iTelephony;
			iTelephony = (Object) getITelephonyMethod.invoke(tManager,
					(Object[]) null);
			Method dial = iTelephony.getClass().getDeclaredMethod("dial",
					String.class);
			dial.invoke(iTelephony, number);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 赋值数字保留一位
	 * 
	 * **/
	public static void getTextViewFormotOne(TextView textView, String str) {
		textView.setText(QntUtils.getFormatOne(QntUtils.getDouble(str)));
	}

	/**
	 * 
	 * 赋值数字保留两位
	 * 
	 * **/
	public static void getTextViewFormotTwo(TextView textView, String str) {
		textView.setText(QntUtils.getFormat(QntUtils.getDouble(str)));
	}

	public static String HEXAndMd5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				md.update(plainText.getBytes("UTF8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer(200);
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset] & 0xff;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// LoggerUtil.error();
			return null;
		}
	}

	/**
	 * 获取手机ip地址 inetAddress instanceof Inet4Address
	 * 
	 * @return
	 */
	public static String getPhoneIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	public static void setPricePoint(final EditText editText) {
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0,
								s.toString().indexOf(".") + 3);
						editText.setText(s);
						editText.setSelection(s.length());
					}
				}
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					editText.setText(s);
					editText.setSelection(2);
				}

				if (s.toString().startsWith("0")
						&& s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						editText.setText(s.subSequence(0, 1));
						editText.setSelection(1);
						return;
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

		});

	}

}
