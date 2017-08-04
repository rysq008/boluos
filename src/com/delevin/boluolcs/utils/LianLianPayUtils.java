package com.delevin.boluolcs.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.yintong.android.app.IPayService;
import com.yintong.android.app.IRemoteServiceCallback;
import com.yintong.secure.service.PayService;

/**
 *     @author 李红涛  @version 创建时间：2016-12-23 下午3:55:14    类说明 
 */
public class LianLianPayUtils {
	Integer lock = 0;
	IPayService payService = null;
	boolean mbPaying = false;
	static final String TAG = "MobileSecurePayer";

	Activity mActivity = null;
	// 和安全支付服务建立连接
	private ServiceConnection mSecurePayConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			try {
				//
				// wake up the binder to continue.
				// 获得通信通道
				synchronized (lock) {
					payService = IPayService.Stub.asInterface(service);
					lock.notify();
				}
			} catch (Exception e) {
				Log.d(TAG, e.getLocalizedMessage());
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			payService = null;
		}
	};

	/**
	 * 向银通支付发送支付请求
	 * 
	 * @param strOrderInfo
	 *            订单信息
	 * @param callback
	 *            回调handler
	 * @param myWhat
	 *            回调信息
	 * @param activity
	 *            目标activity
	 * @param signCard
	 *            是否是单独签约
	 * @param isTest
	 *            是否是测试环境，true为测试环境，但不推荐使用。
	 * @return
	 */
	public boolean pay(String strOrderInfo, final Handler callback,
			final int myWhat, final Activity activity, boolean signCard,
			boolean isTest) {
		if (mbPaying)
			return false;
		mbPaying = true;

		try {
			if (isTest) {
				strOrderInfo = new JSONObject(strOrderInfo).put("test_mode",
						"1").toString();
			}
			if (signCard) {
				strOrderInfo = new JSONObject(strOrderInfo).put("sign_mode",
						"1").toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//
		mActivity = activity;

		// bind the service.
		// 绑定服务
		if (payService == null) {
			// 绑定安全支付服务需要获取上下文环境，
			// 如果绑定不成功使用mActivity.getApplicationContext().bindService
			// 解绑时同理
			mActivity.getApplicationContext().bindService(
					new Intent(activity, PayService.class),
					mSecurePayConnection, Context.BIND_AUTO_CREATE);
		}
		// else ok.

		final String payinfo = strOrderInfo;

		// 实例一个线程来进行支付
		new Thread(new Runnable() {
			public void run() {
				try {
					// wait for the service bind operation to completely
					// finished.
					// Note: this is important,otherwise the next
					// payService.Pay()
					// will fail.
					// 等待安全支付服务绑定操作结束
					// 注意：这里很重要，否则payService.pay()方法会失败
					synchronized (lock) {
						if (payService == null)
							lock.wait();
					}

					// register a Callback for the service.
					// 为安全支付服务注册一个回调
					payService.registerCallback(mCallback);

					// call the MobileSecurePay service.
					// 调用安全支付服务的pay方法
					String strRet = payService.pay(payinfo);
					log(TAG, "服务端支付结果：" + strRet);

					// set the flag to indicate that we have finished.
					// unregister the Callback, and unbind the service.
					// 将mbPaying置为false，表示支付结束
					// 移除回调的注册，解绑安全支付服务
					mbPaying = false;
					payService.unregisterCallback(mCallback);
					mActivity.getApplicationContext().unbindService(
							mSecurePayConnection);

					// send the result back to caller.
					// 发送交易结果
					Message msg = new Message();
					msg.what = myWhat;
					msg.obj = strRet;
					callback.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();

					// send the result back to caller.
					// 发送交易结果
					Message msg = new Message();
					msg.what = myWhat;
					msg.obj = e.toString();
					callback.sendMessage(msg);
				}
			}
		}).start();

		return true;
	}

	/**
	 * This implementation is used to receive callbacks from the remote service.
	 * 实现安全支付的回调
	 */
	private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
		/**
		 * This is called by the remote service regularly to tell us about new
		 * values. Note that IPC calls are dispatched through a thread pool
		 * running in each process, so the code executing here will NOT be
		 * running in our main thread like most other things -- so, to update
		 * the UI, we need to use a Handler to hop over there. 通过IPC机制启动安全支付服务
		 */
		public void startActivity(String packageName, String className,
				int iCallingPid, Bundle bundle) throws RemoteException {
			Intent intent = new Intent(Intent.ACTION_MAIN, null);

			if (bundle == null)
				bundle = new Bundle();
			// else ok.

			try {
				bundle.putInt("CallingPid", iCallingPid);
				intent.putExtras(bundle);
			} catch (Exception e) {
				e.printStackTrace();
			}

			intent.setClassName(packageName, className);
			mActivity.startActivity(intent);
		}

		/**
		 * when the msp loading dialog gone, call back this method.
		 */
		@Override
		public boolean isHideLoadingScreen() throws RemoteException {
			return false;
		}

		/**
		 * when the current trade is finished or cancelled, call back this
		 * method.
		 */
		@Override
		public void payEnd(boolean arg0, String arg1) throws RemoteException {

		}

	};

	/**
	 * 打印信息
	 * 
	 * @param tag
	 *            标签
	 * @param info
	 *            信息
	 */
	public static void log(String tag, String info) {
		Log.i(tag, info);
	}

	/**
	 * 显示dialog
	 * 
	 * @param context
	 *            环境
	 * @param strTitle
	 *            标题
	 * @param strText
	 *            内容
	 * @param icon
	 *            图标
	 */
	public static void showDialog(Activity context, String strTitle,
			String strText, int icon) {
		try {
			AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
			tDialog.setIcon(icon);
			tDialog.setTitle(strTitle);
			tDialog.setMessage(strText);
			tDialog.setPositiveButton("确定", null);
			tDialog.show();
		} catch (Exception e) {

		}
	}

	public static JSONObject string2JSON(String str) {
		try {
			return new JSONObject(str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}

	public static String toJSONString(Object obj) {
		JSONObject json = new JSONObject();
		try {
			List<NameValuePair> list = bean2Parameters(obj);
			for (NameValuePair nv : list) {
				json.put(nv.getName(), nv.getValue());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * 将bean转换成键值对列表
	 * 
	 * @param bean
	 * @return
	 */
	public static List<NameValuePair> bean2Parameters(Object bean) {
		if (bean == null) {
			return null;
		}
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		// 取得bean所有public 方法
		Method[] Methods = bean.getClass().getMethods();
		for (Method method : Methods) {
			if (method != null && method.getName().startsWith("get")
					&& !method.getName().startsWith("getClass")) {
				// 得到属性的类名
				String value = "";
				// 得到属性值
				try {
					String className = method.getReturnType().getSimpleName();
					if (className.equalsIgnoreCase("int")) {
						int val = 0;
						try {
							val = (Integer) method.invoke(bean);
						} catch (InvocationTargetException e) {
							Log.e("InvocationTargetException", e.getMessage(),
									e);
						}
						value = String.valueOf(val);
					} else if (className.equalsIgnoreCase("String")) {
						try {
							value = (String) method.invoke(bean);
						} catch (InvocationTargetException e) {
							Log.e("InvocationTargetException", e.getMessage(),
									e);
						}
					}
					if (value != null && value != "") {
						// 添加参数
						// 将方法名称转化为id，去除get，将方法首字母改为小写
						String param = method.getName().replaceFirst("get", "");
						if (param.length() > 0) {
							String first = String.valueOf(param.charAt(0))
									.toLowerCase();
							param = first + param.substring(1);
						}
						parameters.add(new BasicNameValuePair(param, value));
					}
				} catch (IllegalArgumentException e) {
					Log.e("IllegalArgumentException", e.getMessage(), e);
				} catch (IllegalAccessException e) {
					Log.e("IllegalAccessException", e.getMessage(), e);
				}
			}
		}
		return parameters;
	}
}
