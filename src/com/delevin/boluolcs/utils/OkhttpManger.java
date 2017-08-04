package com.delevin.boluolcs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

import com.delevin.boluolcs.bean.BeanUrl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *     @author 李红涛  @version 创建时间：2016-12-19 下午4:45:21    类说明 
 */
public class OkhttpManger {

	private OkHttpClient okHttpClient;
	private volatile static OkhttpManger okhttpManger;
	private Request request;
	private Handler handler;
	// private final String TAG = okhttpManger.getClass().getSimpleName();
	// 提交JSON数据
	// private static final MediaType JSON =
	// MediaType.parse("application/json;charset=uft-8");
	// 提交字符串
	private static final MediaType MEDIA_TYPE_MARKDOWW = MediaType
			.parse("text/x-markdown;charset=uft-8");

	private OkhttpManger() {
		handler = new Handler(Looper.getMainLooper());
		okHttpClient = new OkHttpClient.Builder()
				.readTimeout(10, TimeUnit.SECONDS)// 设置读取超时时间
				.writeTimeout(10, TimeUnit.SECONDS)// 设置写的超时时间
				.connectTimeout(10, TimeUnit.SECONDS)// 设置连接超时时间
				.build();
	}

	public static OkhttpManger getInstance() {
		OkhttpManger instance = null;
		if (okhttpManger == null) {
			synchronized (OkhttpManger.class) {
				if (instance == null) {
					instance = new OkhttpManger();
					okhttpManger = instance;
				}
			}
		}
		return instance;
	}

	// 请求返回结果JSON数据串
	private void getSuccessJsonStringMethod(final String jsonValue,
			final Funck1 callBack) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				if (callBack != null) {
					try {
						callBack.onResponse(jsonValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	// 请求返回结果JSONObject
	private void getSuccessJsonObjectMethod(final Context context,
			final String jsonValue, final Funck4 callBack) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				if (callBack != null) {
					try {
						JSONObject object = new JSONObject(jsonValue);

						callBack.onResponse(object);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	// 请求返回结果byte
	private void getSuccessJsonByteMethod(final byte[] bs, final Funck2 callBack) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (callBack != null) {
					try {
						callBack.onResponse(bs);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	// 请求返回结果byte
	private void getSuccessJsonByteInputStreamMethod(final InputStream bs,
			final Funck5 callBack) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (callBack != null) {
					try {
						callBack.onResponse(bs);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	// 同步请求/GET Android 不常用。
	public String syncGetByUrl(String url) {
		Request request = new Request.Builder().url(url).build();
		Response response = null;
		try {
			response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	// 请求指定的Url，返回String
	public void doGetJsonStirngByUrl(String url, final Funck1 callBack) {
		final Request request = new Request.Builder().url(url).build();
		okHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				if (response != null && response.isSuccessful()) {
					getSuccessJsonStringMethod(response.body().string(),
							callBack);

				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				arg1.printStackTrace();
			}
		});
	}

	// 请求指定的Url，返回byte
	public void doGetJsonByteByUrl(String url, final Funck2 callBack) {
		final Request request = new Request.Builder().url(url).build();
		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				if (response != null && response.isSuccessful()) {
					getSuccessJsonByteMethod(response.body().bytes(), callBack);
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				arg1.printStackTrace();
			}
		});
	}

	// 请求指定的Url，返回byte
	public void doGetJsonByteByUrlInput(String url, final Funck5 callBack) {
		final Request request = new Request.Builder().url(url).build();
		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				if (response != null && response.isSuccessful()) {
					getSuccessJsonByteInputStreamMethod(response.body()
							.byteStream(), callBack);
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				arg1.printStackTrace();

			}
		});
	}

	// 请求指定的Url，返回bitmap
	public void doGetDownLoadImageByUrl(String url, final Funck3 callBack) {
		final Request request = new Request.Builder().url(url).build();
		okHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				if (response != null && response.isSuccessful()) {
					byte[] data = response.body().bytes();
					Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					callBack.onResponse(bitmap);
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				arg1.printStackTrace();
			}
		});
	}

	// 提交表单数据
	public void sendComplexForm(final Context context, final Boolean flag,
			final String url, final Map<String, String> params,
			final Funck4 callBack) {

		Request request2 = getRequest(context, flag, BeanUrl.URLZ + url, params);
		okHttpClient.newCall(request2).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				if (response != null && response.isSuccessful()) {
					String result = response.body().string();

					getSuccessJsonObjectMethod(context, result, callBack);
				} else {
				}
			}

			@Override
			public void onFailure(Call callback, IOException e) {
				Request request23 = getRequest(context, flag, BeanUrl.URLZB
						+ url, params);
				okHttpClient.newCall(request23).enqueue(this);

			}
		});
	}

	// 获取responseCode
	public void getResponseCode(final Context context, final Boolean flag,
			final String urlZ, final Map<String, String> params,
			final Funck6 callBack) {

		Request request2 = getRequest(context, flag, urlZ, params);
		okHttpClient.newCall(request2).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				int code = response.code();
				callBack.onResponse(code);
			}

			@Override
			public void onFailure(Call callback, IOException e) {
				callBack.onResponse(404);
			}
		});
	}

	// 提交表单数据
	public void sendComplexForms(final Context context, final Boolean flag,
			final String url, final Map<String, String> params,
			final Funck4 callBack) {

		Request request2 = getRequest(context, flag, url, params);
		okHttpClient.newCall(request2).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				if (response != null && response.isSuccessful()) {
					String result = response.body().string();
					int code = response.code();
					getSuccessJsonObjectMethod(context, result, callBack);
				} else {
				}
			}

			@Override
			public void onFailure(Call callback, IOException e) {
				Request request23 = getRequest(context, flag, url, params);
				okHttpClient.newCall(request23).enqueue(this);
			}
		});
	}

	private Request getRequest(final Context context, Boolean flag, String url,
			Map<String, String> params) {
		FormBody.Builder form_builder = new FormBody.Builder();// 表单对象，包含以input开始的对象，以html表单为主。
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				form_builder.add(entry.getKey(), entry.getValue());
			}
		}
		RequestBody requestBody = form_builder.build();

		Request.Builder builder = new Request.Builder().url(url);// 采用POST提交

		String AUTHORIZATION = BoluoUtils.getShareOneData(context,
				"AUTHORIZATION");
		if (flag) {
			request = builder.addHeader("Authorization", AUTHORIZATION)
					.post(requestBody).build();
		} else {
			request = builder.post(requestBody).build();
		}
		return request;
	}

	// 向服务器提交字符串
	public void sendStringByPostMethod(final Context content, String url,
			String string, final Funck4 callBack) {
		Request request = new Request.Builder().url(url)
				.post(RequestBody.create(MEDIA_TYPE_MARKDOWW, string)).build();// 采用POST提交
		okHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				if (response != null && response.isSuccessful()) {
					getSuccessJsonObjectMethod(content, response.body()
							.string(), callBack);
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	//
	public interface Funck1 {
		void onResponse(String result);
	}

	public interface Funck2 {
		void onResponse(byte[] result);

	}

	public interface Funck3 {
		void onResponse(Bitmap result);
	}

	public interface Funck4 {
		void onResponse(JSONObject result);
	}

	public interface Funck5 {
		void onResponse(InputStream result);
	}

	public interface Funck6 {

		void onResponse(int code);
	}
}
