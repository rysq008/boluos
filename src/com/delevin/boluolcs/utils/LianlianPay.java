package com.delevin.boluolcs.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;

/**
 *     @author 李红涛  @version 创建时间：2016-12-27 上午10:32:18    类说明 
 */

public class LianlianPay {

	public static final int BASE_ID = 0;
	public static final int RQF_PAY = BASE_ID + 1;
	public static final int RQF_INSTALL_CHECK = RQF_PAY + 1;
	public static final String SERVER_URL = "http://yintong.com.cn/secure_server/x.htm";
	public static final String PAY_PACKAGE = "com.yintong.secure";
	// 银通支付安全支付服务apk的名称，必须与assets目录下的apk名称一致
	public static final String YT_PLUGIN_NAME = "SecurePay.apk";
	public static final String RET_CODE_SUCCESS = "0000";// 0000 交易成功
	public static final String RET_CODE_PROCESS = "2008";// 2008 支付处理中
	public static final String RESULT_PAY_SUCCESS = "SUCCESS";
	public static final String RESULT_PAY_PROCESSING = "PROCESSING";
	public static final String RESULT_PAY_FAILURE = "FAILURE";
	public static final String RESULT_PAY_REFUND = "REFUND";

	/**
	 * 
	 * 充值向服务器提交数据 ，服务器返回数据，将返回数据传给连连；getPay(activity,手机号,银行卡号,身份证号,姓名,金额)
	 * 
	 * */
	public static void getPay(final Activity activity, String phone,
			String bank_card, String id_card, String real_name,
			String change_money, final ImageView imgView,
			final LinearLayout linearLayout, final GetCodeCallBack callBack) {
		// 开始
		linearLayout.setVisibility(View.VISIBLE);
		ProessDilogs.getProessAnima(imgView, activity);
		String format = String.format(BeanUrl.PAY_STRING, phone);
		String AUTHORIZATION = BoluoUtils.getShareOneData(activity,"AUTHORIZATION");
		Map<String, String> params = new HashMap<String, String>();
		params.put("equipment_token", Myapplication.DEVICE_ID);
		params.put("Authorization", "Basic " + AUTHORIZATION);
		params.put("bank_card", bank_card);
		params.put("id_card", id_card);
		params.put("real_name", real_name);
		params.put("charge_money", change_money);
		Myapplication.okhttpManger.sendComplexForm(activity, true, format,
				params, new Funck4() {

					@Override
					public void onResponse(JSONObject result) {
						try {

							String code = result.getString("code");
							String desc = result.getString("desc");
							if (TextUtils.equals(code, "10000")) {
								// 结束
								ProessDilogs.closeAnimation(imgView,linearLayout);
								linearLayout.setVisibility(View.GONE);
								JSONObject content = result.getJSONObject("content");
								JSONObject sub_mit = content.getJSONObject("sub_mit");
								String content4Pay = sub_mit.toString();
								MobileSecurePayer msp = new MobileSecurePayer();
								// 连接连连支付
								msp.payAuth(content4Pay,createHandler(activity, callBack),Constants.RQF_PAY, activity, false);
							} else {
								// 结束
								ProessDilogs.closeAnimation(imgView,linearLayout);
								linearLayout.setVisibility(View.GONE);
								Toast.makeText(activity, desc,Toast.LENGTH_SHORT).show();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	/**
	 * //连连回掉返回相应
	 * */
	@SuppressLint("HandlerLeak")
	private static Handler createHandler(final Activity context,
			final GetCodeCallBack callBack) {
		return new Handler() {
			public void handleMessage(Message msg) {
				String strRet = (String) msg.obj;
				switch (msg.what) {
				case RQF_PAY: {
					JSONObject objContent = LianLianPayUtils.string2JSON(strRet);
					String retCode = objContent.optString("ret_code");
					String retMsg = objContent.optString("ret_msg");

					// 先判断状态码，状态码为 成功或处理中 的需要 验签
					if (RET_CODE_SUCCESS.equals(retCode)) {
						String resulPay = objContent.optString("result_pay");
						if (RESULT_PAY_SUCCESS.equalsIgnoreCase(resulPay)) {
							callBack.onResponse(retMsg, retCode, true);
						} else {
							// LianLianPayUtils.showDialog(context, "提示", retMsg
							// + "，交易状态码:" + retCode,
							// android.R.drawable.ic_dialog_alert);
							Toast.makeText(context, retMsg, Toast.LENGTH_SHORT)
									.show();
						}
					} else if (RET_CODE_PROCESS.equals(retCode)) {
						String resulPay = objContent.optString("result_pay");
						if (RESULT_PAY_PROCESSING.equalsIgnoreCase(resulPay)) {
							// LianLianPayUtils.showDialog(context, "提示",
							// objContent.optString("ret_msg") + "交易状态码："
							// + retCode,
							// android.R.drawable.ic_dialog_alert);
							Toast.makeText(context,
									objContent.optString("ret_msg"),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						// callBack.onResponse(retMsg,retCode,false);
						// LianLianPayUtils.showDialog(context, "提示", retMsg
						// + "，交易状态码:" + retCode,
						// android.R.drawable.ic_dialog_alert);
						Toast.makeText(context, retMsg, Toast.LENGTH_SHORT)
								.show();
					}
				}
					break;
				}
				super.handleMessage(msg);
			}
		};
	}

	public interface GetCodeCallBack {
		void onResponse(String msg, String code, boolean b);
	}
}
