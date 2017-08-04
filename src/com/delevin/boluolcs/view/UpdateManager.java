package com.delevin.boluolcs.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.interfaces.GengXinCallBack;
import com.delevin.boluolcs.utils.APPName;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

/**
 * @author coolszy
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */

public class UpdateManager {

	int versionCode = 0;
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 保存解析的XML信息 */
	HashMap<String, String> mHashMap;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;
	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;
	private InputStream instream;
	private HttpURLConnection conn;
	private CustomDialog dialog;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	public void inputstreamtofile(InputStream ins, File file) {
		OutputStream os;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Android 最新版更新
	public boolean getGengxinS(final Context context, final String b,
			final GengXinCallBack callBack) {
		mHashMap = new HashMap<String, String>();
		Myapplication.okhttpManger.sendComplexForms(context, false,
				BeanUrl.GENGXINS_STRING, null, new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						try {
							int visonCode = APPName.getVersionCode(context);
							JSONArray array = result.getJSONArray("data");
							JSONObject object = array.getJSONObject(0);
							String code = object.getString("code");
							String constraint_code = object
									.getString("constraint_code");
							String constraint_status = object
									.getString("constraint_status");
							String content = object.getString("content");
							String status = object.getString("status");
							String url = object.getString("url");
							String strSerivce=result.getString("serivce");
							int codeI = QntUtils.getInt(code);
							callBack.onSerivce(strSerivce);
							
							if (TextUtils.equals(status, "1")) {
								if (codeI > visonCode) {
									mHashMap.put("code", code);
									mHashMap.put("content", content);
									mHashMap.put("is_forceupdate", status);
									mHashMap.put("url", url);
									callBack.onCode(code);
									if (null != mHashMap) {
										showNoticeDialog(mHashMap.get("is_forceupdate"));
									}
								} else {
									if (TextUtils.equals(b, "0")) {
										Toast.makeText(mContext, "当前已是最新版本",
												Toast.LENGTH_SHORT).show();
									}
								}
							} else {
								if (TextUtils.equals(constraint_status, "1")) {
									if (TextUtils.equals(constraint_code,
											visonCode + "")) {
										// z
										mHashMap.put("code", constraint_code);
										mHashMap.put("content", content);
										mHashMap.put("is_forceupdate",
												constraint_status);
										mHashMap.put("url", url);
										callBack.onCode(constraint_code);
										if (null != mHashMap) {
											showNoticeDialog("3");
										}
									} else {
										// n
										if (TextUtils.equals(b, "0")) {
											Toast.makeText(mContext,"当前已是最新版本",Toast.LENGTH_SHORT).show();
										}
									}
								} else {
									// n
									if (TextUtils.equals(b, "0")) {
										Toast.makeText(mContext, "当前已是最新版本",Toast.LENGTH_SHORT).show();
									}
								}
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		return false;
	}

	/**
	 * 检测软件更新
	 */
	public void checkUpdate(String b, GengXinCallBack callBack) {
		if (getGengxinS(mContext, b, callBack)) {
			// 显示提示对话框
			Toast.makeText(mContext, R.string.soft_update_no, 0).show();
		}
	}

	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog(String leixing) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 实例化自定义主题的对话框
		final CustomDialog dialog = new CustomDialog(mContext,R.style.gesturesPassword_dialog);
		View layout = inflater.inflate(R.layout.view_layoutgeng, null);
		dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		TextView gengMessage = (TextView) layout.findViewById(R.id.gengxin_message);
		gengMessage.setText(mHashMap.get("content"));
		// 构造对话框
		Button bt_geng = (Button) layout.findViewById(R.id.bt_gengxin);
		Button bt_shao = (Button) layout.findViewById(R.id.bt_shaodeng);
		bt_geng.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				showDownloadDialog();

			}
		});
		if (!TextUtils.equals(leixing, "3")) {
			bt_shao.setOnClickListener(new android.view.View.OnClickListener() {

				public void onClick(View v) {

					dialog.dismiss();
					SharedPreferences shareInit = BoluoUtils
							.getShareInit(mContext);
					Editor editor = shareInit.edit();
					editor.putBoolean("geng", false);
					editor.commit();
					dialog.dismiss();
				}
			});
		} else {
			bt_shao.setVisibility(View.GONE);
		}
		dialog.setCancelable(false);
		dialog.show();
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 实例化自定义主题的对话框
		dialog = new CustomDialog(mContext, R.style.gesturesPassword_dialog);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		dialog.addContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		// 构造软件下载对话框
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		Button dilog_zhengzai_dissminss = (Button) v
				.findViewById(R.id.dilog_zhengzai_dissminss);
		dilog_zhengzai_dissminss
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// 设置取消状态
						cancelUpdate = true;
						dialog.dismiss();
					}
				});
		// // 取消更新
		// builder.setNegativeButton(R.string.soft_update_cancel, new
		// OnClickListener(){
		// @Override
		// public void onClick(DialogInterface dialog, int which){
		// dialog.dismiss();
		//
		// }
		// });
		dialog.show();
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * 
	 * @author coolszy
	 * @date 2012-4-26
	 * @blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					String urls = mHashMap.get("url");
					URL url = new URL(urls);
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath,
							mHashMap.get("is_forceupdate"));
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			dialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, mHashMap.get("is_forceupdate"));
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
