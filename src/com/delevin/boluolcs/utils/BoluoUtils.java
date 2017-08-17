package com.delevin.boluolcs.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.activity.BidDetalsActivity;
import com.delevin.boluolcs.activity.TouziMoreObjectActivity;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.exit.ActivityCollector;
import com.delevin.boluolcs.gestureedit.GestureEditActivity;
import com.delevin.boluolcs.interfaces.DefultDilogCallBack;
import com.delevin.boluolcs.interfaces.KefuCallBack;
import com.delevin.boluolcs.interfaces.PhoneCodeCallBack;
import com.delevin.boluolcs.main.MainActivity;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.view.CustomDialog;
import com.delevin.boluolcs.view.GesturesPasswordCustomDialog;
import com.pusupanshi.boluolicai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-23 下午1:44:27    类说明 
 */
@SuppressLint("CommitPrefEdits")
public class BoluoUtils {
	// 手机号判断
	public static boolean isPhone(Context context, String phone) {
		if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
			return true;
		} else {
			Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	// 验证码格式
	public static boolean getPhoneCode(Context context, String phoneCode) {
		if (!TextUtils.isEmpty(phoneCode)) {
			if (phoneCode.length() == 6) {
				return true;
			} else {
				Toast.makeText(context, "验证码格式错误", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, "验证码不能为空", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	// 确认密码判断
	public static boolean getEquals(Context context, String s1, String s2) {
		if (!TextUtils.isEmpty(s1)) {
			if (!TextUtils.isEmpty(s2)) {
				if (s1.equals(s2)) {
					return true;
				} else {
					Toast.makeText(context, "两次输入密码不一样，请重新输入",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "确认密码不能为空", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	public static void getBankCode(String bankCode, ImageView img) {
		if (bankCode.equals("01020000")) {
			img.setBackgroundResource(R.drawable.li01020000);
		} else if (bankCode.equals("01030000")) {
			img.setBackgroundResource(R.drawable.li01030000);
		} else if (bankCode.equals("01040000")) {
			img.setBackgroundResource(R.drawable.li01040000);
		} else if (bankCode.equals("01050000")) {
			img.setBackgroundResource(R.drawable.li01050000);
		} else if (bankCode.equals("03080000")) {
			img.setBackgroundResource(R.drawable.li03080000);
		} else if (bankCode.equals("03100000")) {
			img.setBackgroundResource(R.drawable.li03100000);
		} else if (bankCode.equals("03030000")) {
			img.setBackgroundResource(R.drawable.li03030000);
		} else if (bankCode.equals("03070000")) {
			img.setBackgroundResource(R.drawable.li03070000);
		} else if (bankCode.equals("03040000")) {
			img.setBackgroundResource(R.drawable.li03040000);
		} else if (bankCode.equals("03090000")) {
			img.setBackgroundResource(R.drawable.li03090000);
		} else if (bankCode.equals("03020000")) {
			img.setBackgroundResource(R.drawable.li03020000);
		} else if (bankCode.equals("01000000")) {
			img.setBackgroundResource(R.drawable.li01000000);
		} else if (bankCode.equals("03060000")) {
			img.setBackgroundResource(R.drawable.li03050000);
		} else if (bankCode.equals("03160000")) {
			img.setBackgroundResource(R.drawable.li03160000);
		} else if (bankCode.equals("04233310")) {
			img.setBackgroundResource(R.drawable.li04233310);
		} else if (bankCode.equals("04031000")) {
			img.setBackgroundResource(R.drawable.li04031000);
		} else if (bankCode.equals("04012900")) {
			img.setBackgroundResource(R.drawable.li04012900);
		} else if (bankCode.equals("03010000")) {
			img.setBackgroundResource(R.drawable.li03010000);
		} else if (bankCode.equals("03050000")) {
			img.setBackgroundResource(R.drawable.li03060000);
		}
	}

	/**
	 * 设置progressBar getProgress( ProgressBar,精度最大值，精度)；
	 * */
	public static void getProgress(ProgressBar bar, int total, int num) {
		bar.setMax(total);
		bar.setProgress(num);
	}

	@SuppressWarnings("deprecation")
	public static void getStatusZhiJiao(Context mContext, String status,
			TextView bt, String teshu, Button btJi, LinearLayout layout) {
		if (TextUtils.equals(status, "2")) {
			bt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.shape_bt_boluoyellow));
			bt.setText("抢购");
		} else if (TextUtils.equals(status, "3")) {
			layout.setBackgroundColor(mContext.getResources().getColor(
					R.color.gggggg));
			btJi.setVisibility(View.GONE);
			bt.setText("已售出");
			bt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.shape_gray));
			bt.setClickable(false);
		} else if (TextUtils.equals(status, "4")) {
			layout.setBackgroundColor(mContext.getResources().getColor(
					R.color.gggggg));
			btJi.setVisibility(View.GONE);
			bt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.shape_gray));
			bt.setText("还款中");
			bt.setClickable(false);
		} else if (TextUtils.equals(status, "5")) {
			layout.setBackgroundColor(mContext.getResources().getColor(
					R.color.gggggg));
			btJi.setVisibility(View.GONE);
			bt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.shape_gray));
			bt.setText("已完成");
			bt.setClickable(false);
		} else if (TextUtils.equals(status, "8")) {
			layout.setBackgroundColor(mContext.getResources().getColor(
					R.color.gggggg));
			bt.setText(teshu);
			btJi.setVisibility(View.GONE);
			bt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.shape_gray));
			bt.setClickable(false);
		}
	}

	@SuppressWarnings("deprecation")
	public static void getStatus(Context mContext, String status, TextView bt,
			String teshu) {
		if (TextUtils.equals(status, "2")) {
			bt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.shape_bt_boluoyellow));
			bt.setText("抢购");
		} else if (TextUtils.equals(status, "3")) {
			bt.setText("已售出");
			bt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.shape_gray));
			bt.setClickable(false);
		} else if (TextUtils.equals(status, "4")) {
			bt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.shape_gray));
			bt.setText("还款中");
			bt.setClickable(false);
		} else if (TextUtils.equals(status, "5")) {
			bt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.shape_gray));
			bt.setText("已完成");
			bt.setClickable(false);
		} else if (TextUtils.equals(status, "8")) {
			bt.setText("待上线");
			bt.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.shape_gray));
			bt.setClickable(false);
		}
	}

	public static void getStatusOne(String status, TextView bt, String teshu) {
		if (TextUtils.equals(status, "2")) {
			bt.setText("抢购");
		} else if (TextUtils.equals(status, "3")) {

			bt.setText("已售出");
			bt.setClickable(false);
		} else if (TextUtils.equals(status, "4")) {

			bt.setText("还款中");
			bt.setClickable(false);
		} else if (TextUtils.equals(status, "5")) {

			bt.setText("已完成");
			bt.setClickable(false);
		} else if (TextUtils.equals(status, "8")) {
			bt.setText(teshu);
			bt.setClickable(false);
		}
	}

	public static void getRed(Boolean isRedPacget, String buyMoney,
			String redPacgetMoney) {
		if (isRedPacget) {
			double buyMoneyD = QntUtils.getDouble(buyMoney);
			if (buyMoneyD >= 10000) {
				redPacgetMoney = "150";
			} else if (buyMoneyD < 10000 && buyMoneyD >= 1000) {
				redPacgetMoney = "100";
			} else if (buyMoneyD < 1000 && buyMoneyD >= 100) {
				redPacgetMoney = "50";
			} else {
				redPacgetMoney = "10";
			}
		}
	}

	public static <T> void getDilogExit(final Context context,
			final Class<T> classs) {
		GesturesPasswordCustomDialog.Builder builder = new GesturesPasswordCustomDialog.Builder(
				context);
		builder.setTitle("温馨提示");
		builder.setMessage("您确定要安全退出菠萝理财吗？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				closeShare(context);
				Map<String, String> map = new HashMap<String, String>();
				map.put("gesNum", "5");
				BoluoUtils.getShareCommit(context, map);
				ActivityCollector.finishActivity();
				context.startActivity(new Intent(context, classs));
				SharedPreferences preferences = context.getSharedPreferences(
						"is_set_pwd", 0);
				Editor editor = preferences.edit();
				editor.clear();
				editor.commit();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public static void getDilogJiaXi(final String money, Context context,
			final EditText edit) {
		GesturesPasswordCustomDialog.Builder dilog = new GesturesPasswordCustomDialog.Builder(
				context);
		dilog.setTitle("提示");
		dilog.setMessage("剩余金额" + money + "元，满标享额外1%加息");
		dilog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				edit.setText(money);
				dialog.dismiss();
			}
		});
		dilog.create().show();
	}

	public static <T> void getDilogHintBand(final Context context,
			final Class<T> classs) {
		GesturesPasswordCustomDialog.Builder builder = new GesturesPasswordCustomDialog.Builder(
				context);
		builder.setTitle("温馨提示");
		builder.setMessage("您还没有绑定银行卡");
		builder.setPositiveButton("绑定银行卡",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						context.startActivity(new Intent(context, classs));
						((Activity) context).finish();
					}
				});
		builder.setNegativeButton("稍后绑定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	// 设置密码
	public static <T> void getDilogSetPassWord(final Context context,
			final Class<T> classs, String title, String message,
			String btSureStr, String btdismissTr, final String chuanzhi1) {
		GesturesPasswordCustomDialog.Builder builder = new GesturesPasswordCustomDialog.Builder(
				context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(btSureStr,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(context, classs);
						intent.putExtra("paypasswordONE", chuanzhi1);
						context.startActivity(intent);
						((Activity) context).finish();
					}
				});
		builder.setNegativeButton(btdismissTr,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	public static void getLimitEditViewType(EditText editText) {

		editText.setKeyListener(new NumberKeyListener() {

			protected char[] getAcceptedChars() {
				char[] numberChars = { '1', '2', '3', '4', '5', '6', '7', '8',
						'9', '0', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
						'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
						'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
						'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
						'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
				return numberChars;
			}

			@Override
			public int getInputType() {
				// TODO Auto-generated method stub
				return 0;
			}

		});
	}

	public static void getZhiFuMoney(String startMoney, String redMoney,
			String banlanceMoney, TextView zhifuTextView) {
		String redMoneys;
		String startMoneys = QntUtils.getFormat(QntUtils.getDouble(startMoney));
		String banlanceMoneys = QntUtils.getFormat(QntUtils
				.getDouble(banlanceMoney));
		if (!TextUtils.isEmpty(redMoney)) {
			redMoneys = QntUtils.getFormat(QntUtils.getDouble(redMoney));
		} else {
			redMoneys = "0";
		}
		double startMoneyD = QntUtils.getDouble(startMoneys);
		double redMoneyD = QntUtils.getDouble(redMoneys);
		double banlanceMoneyD = QntUtils.getDouble(banlanceMoneys);
		if ((banlanceMoneyD + redMoneyD) >= startMoneyD) {
			zhifuTextView.setText("0");
		} else {
			zhifuTextView.setText(QntUtils.getFormat(startMoneyD
					- banlanceMoneyD - redMoneyD));
		}
	}

	public static void getPayZhiFuMoney(String startMoney, String redMoney,
			TextView zhifuTextView) {
		String redMoneys;
		String startMoneys = QntUtils.getFormat(QntUtils.getDouble(startMoney));
		if (!TextUtils.isEmpty(redMoney)) {
			redMoneys = QntUtils.getFormat(QntUtils.getDouble(redMoney));
		} else {
			redMoneys = "0";
		}
		double startMoneyD = QntUtils.getDouble(startMoneys);
		double redMoneyD = QntUtils.getDouble(redMoneys);
		zhifuTextView.setText(QntUtils.getFormat(startMoneyD - redMoneyD));
	}

	// 初始化share
	public static SharedPreferences getShareInit(Context context) {
		@SuppressLint("InlinedApi")
		SharedPreferences share = context.getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		return share;
	}

	// 提交share数据
	@SuppressLint("CommitPrefEdits")
	public static void getShareCommit(Context context, Map<String, String> map) {
		Editor edit = getShareInit(context).edit();
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				edit.putString(entry.getKey(), entry.getValue());
			}
		}
		edit.commit();
	}

	// 获取share数据
	public static Map<String, String> getShareData(Context context) {

		@SuppressWarnings("unchecked")
		Map<String, String> all = (Map<String, String>) getShareInit(context)
				.getAll();
		return all;
	}

	// 销毁share数据
	public static void closeShare(Context context) {
		@SuppressLint("InlinedApi")
		SharedPreferences share = context.getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		Editor edit = share.edit();
		edit.clear();
		edit.commit();
	}

	public static String getShareOneData(Context context, String key) {
		@SuppressLint("InlinedApi")
		SharedPreferences share = context.getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		@SuppressWarnings("unchecked")
		Map<String, String> all = (Map<String, String>) share.getAll();
		return all.get(key);
	}

	// 初始化share
	public static SharedPreferences getShareInitGengxin(Context context) {
		@SuppressLint("InlinedApi")
		SharedPreferences share = context.getSharedPreferences("gengxin",
				Context.MODE_PRIVATE);
		return share;
	}

	// 提交share数据
	@SuppressLint("CommitPrefEdits")
	public static void getShareCommitGengxin(Context context,
			Map<String, String> map) {
		Editor edit = getShareInitGengxin(context).edit();
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				edit.putString(entry.getKey(), entry.getValue());
			}
		}
		edit.commit();
	}

	public static String getShareOneDataGengxin(Context context, String key) {
		@SuppressLint("InlinedApi")
		SharedPreferences share = context.getSharedPreferences("gengxin",
				Context.MODE_PRIVATE);
		@SuppressWarnings("unchecked")
		Map<String, String> all = (Map<String, String>) share.getAll();
		return all.get(key);
	}

	public static boolean isEmpty(String str) {
		return str == null || TextUtils.equals(str, "")
				|| TextUtils.equals(str, "null") || TextUtils.equals(str, "[]")
				|| TextUtils.equals(str, "{}");
	}

	public static void getDilogDome(Context context, String title,String message, String btStr) {
		
		GesturesPasswordCustomDialog.Builder dilog = new GesturesPasswordCustomDialog.Builder(context);
		dilog.setTitle(title);
		dilog.setMessage(message);
		dilog.setPositiveButton(btStr, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dilog.create().show();
	}

	public static void getDilogDefult(Context context, Boolean b, String title,String message, String btStr, String dis,final DefultDilogCallBack callBack) {
		GesturesPasswordCustomDialog.Builder dilog = new GesturesPasswordCustomDialog.Builder(context);
		dilog.setTitle(title);
		dilog.setMessage(message);
		dilog.setPositiveButton(btStr, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				callBack.onPositiveButton();
				dialog.dismiss();
			}
		});
		if (b) {
			dilog.setNegativeButton(dis, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					callBack.onNegativeButton();
					dialog.dismiss();
				}
			});
		}
		dilog.create().show();
	}

	public static void getDilogDefultNotCallBack(Context context, Boolean b,
			String title, String message, String btStr, String dis) {
		final GesturesPasswordCustomDialog.Builder dilog = new GesturesPasswordCustomDialog.Builder(
				context);
		dilog.setTitle(title);
		dilog.setMessage(message);
		dilog.setPositiveButton(btStr, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		if (b) {
			dilog.setNegativeButton(dis, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		}
		dilog.create().show();
	}

	public static void getDenglu(final String phone, String passwordKey,
			final String passwd, final Activity context, final boolean is_pwd,
			final ImageView imgView, final LinearLayout linearLayout) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name_phone", phone);
		params.put(passwordKey, passwd);
		params.put("equipment_name", Myapplication.INFORMATION);
		params.put("equipment_token", Myapplication.DEVICE_ID);
		linearLayout.setVisibility(View.VISIBLE);
		ProessDilogs.getProessAnima(imgView, context);
		Myapplication.okhttpManger.sendComplexForm(context, false,
				BeanUrl.DENGLU_STRING, params, new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								Map<String, String> mapGengXin = new HashMap<String, String>();
								Map<String, String> map = new HashMap<String, String>();
								JSONObject object = result
										.getJSONObject("content");
								map.put("memberId",object.getString("memberId"));// 是否登录
								map.put("id_bind", object.getString("id_bind"));// 身份证是否绑定
								map.put("pay_bind",
										object.getString("pay_bind"));// 银行卡是否绑定
								map.put("is_pay_passwd",
										object.getString("is_pay_passwd"));// 支付密码是否设置
								map.put("phone", object.getString("phone"));// 手机号
								map.put("newer", object.getString("newer"));// 是否是新手
								map.put("login_token",
										object.getString("login_token"));
								map.put("AUTHORIZATION",
										QntUtils.getBase64(phone, passwd));// 加密Hander
								map.put("gesNum", "5");
								mapGengXin.put("visonCode",
										APPName.getVersionCode(context) + "");
								BoluoUtils.getShareCommitGengxin(context,
										mapGengXin);
								BoluoUtils.getShareCommit(context, map);
								if (is_pwd) {

									context.startActivity(new Intent(context,
											MainActivity.class));
									context.finish();
								} else {
									context.startActivity(new Intent(context,
											GestureEditActivity.class));
									context.finish();
								}
								if (MainActivity.mainActivity != null) {
									MainActivity.mainActivity.finish();
								}
								if (BidDetalsActivity.bidDetalsActivity != null) {
									BidDetalsActivity.bidDetalsActivity
											.finish();
								}
								if (TouziMoreObjectActivity.touziMoreObjectActivity != null) {
									TouziMoreObjectActivity.touziMoreObjectActivity
											.finish();
								}
								ProessDilogs.closeAnimation(imgView,
										linearLayout);
								linearLayout.setVisibility(View.GONE);
								context.finish();
							}
							// 参数为空
							else if (result.getString("code").equals("10001")) {
								ProessDilogs.closeAnimation(imgView,
										linearLayout);
								linearLayout.setVisibility(View.GONE);
								Toast.makeText(context,
										result.getString("desc"),
										Toast.LENGTH_SHORT).show();
							}
							// 用户名不存在
							else if (result.getString("code").equals("10002")) {
								ProessDilogs.closeAnimation(imgView,
										linearLayout);
								linearLayout.setVisibility(View.GONE);
								Toast.makeText(context,
										result.getString("desc"),
										Toast.LENGTH_SHORT).show();
							}
							// 密码输入错误
							else if (result.getString("code").equals("10004")) {
								ProessDilogs.closeAnimation(imgView,
										linearLayout);
								linearLayout.setVisibility(View.GONE);
								Toast.makeText(context,
										result.getString("desc"),
										Toast.LENGTH_SHORT).show();
							}
							// 未知异常
							else if (result.getString("code").equals("10005")) {
								ProessDilogs.closeAnimation(imgView,
										linearLayout);
								linearLayout.setVisibility(View.GONE);
								Toast.makeText(context,
										result.getString("desc"),
										Toast.LENGTH_SHORT).show();
							} else {
								ProessDilogs.closeAnimation(imgView,
										linearLayout);
								linearLayout.setVisibility(View.GONE);
								Toast.makeText(context, "登录失败",
										Toast.LENGTH_SHORT).show();
								// probarLogin.setVisibility(View.GONE);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	public static void getCallPhoneCode(String phone, final Context context,
			final PhoneCodeCallBack callBack) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", phone);
		params.put("key", AndroidUtils.HEXAndMd5("eb62f6b930" + phone));
		params.put("tem_id", "42447");

		Myapplication.okhttpManger.sendComplexForm(context, false,
				BeanUrl.Yanzhengma, params, new Funck4() {

					@Override
					public void onResponse(JSONObject result) {
						// TODO Auto-generated method stub
						callBack.onResponse(result);
					}
				});
	}

	public static void getYanCode(final Context context, final String phone,
			String verify_code, final Class<?> caClass) {
		Map<String, String> params = new HashMap<String, String>();
		// 根据控件中输入得信息提交到数据库

		params.put("phone", phone);
		params.put("verify_code", verify_code);
		Myapplication.okhttpManger.sendComplexForm(context, false,
				BeanUrl.YANZHENGMAPOST_STRING, params, new Funck4() {

					@Override
					public void onResponse(JSONObject result) {
						// 成功
						try {
							if (result.getString("code").equals("10000")) {
								Toast.makeText(context,
										result.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(context, caClass);
								intent.putExtra("phone", phone);
								context.startActivity(intent);
							}// 失败
							else {
								Toast.makeText(context,
										result.getString("desc").toString(),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});

	}
	public static void getKefuDilog(final Context mContext,final KefuCallBack callBack){
		


			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// 实例化自定义主题的对话框
			final CustomDialog dialog = new CustomDialog(mContext,R.style.gesturesPassword_dialog);
			View layout = inflater.inflate(R.layout.layout_touzi_kefu, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			 Window window = dialog.getWindow();  
			 window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置  
			TextView tvCall= (TextView) layout.findViewById(R.id.kefu_call);
			LinearLayout lyCallPhone = (LinearLayout) layout.findViewById(R.id.kefu_callphone);
			Button btDis= (Button) layout.findViewById(R.id.kefu_dissmiss);
			tvCall.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					callBack.onCall();
					
					dialog.dismiss();
				}
			});
			lyCallPhone.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								callBack.onCallPhone();
								dialog.dismiss();
							}
						});
			btDis.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					
				}
			});
//			dialog.setCancelable(false);
			dialog.show();
		}
		
}
