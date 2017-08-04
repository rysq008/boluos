package com.delevin.application;

import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import cn.jpush.android.api.JPushInterface;

import com.delevin.boluolcs.chat.utils.DemoHXSDKHelper;
import com.delevin.boluolcs.chat.utils.User;
import com.delevin.boluolcs.denglu.LoginActivity;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.OkhttpManger;
import com.easemob.EMCallBack;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

/**
 *     @author 李红涛  @version 创建时间：2016-12-13 下午3:46:18    类说明 
 */
public class Myapplication extends Application {

	public static boolean reSetCode = false;// 是否设置密码
	public static OkhttpManger okhttpManger;
	public static String DEVICE_ID;// 设备Id
	public static String INFORMATION;// 设备名称
	public static String publicKey = AndroidUtils.HEXAndMd5("eb62f6b930");
	public static LoginActivity loginActivity;
	public static boolean Internet = true;
	public static String registrationID = null;// 设备标识
	
	public static Context applicationContext;
	private static Application instance;
	// login user name
	public final String PREF_USERNAME = "username";
	
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";
	public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();

	@Override
	public void onCreate() {
		super.onCreate();
		okhttpManger = OkhttpManger.getInstance();
		// 获得设备信息 以及设备唯一ID
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		DEVICE_ID = tm.getDeviceId();
		INFORMATION = Build.MODEL;
		Config.DEBUG = true;
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		Config.isJumptoAppStore = true;
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.CART_BROADCAST");// 建议把它写一个公共的变量，这里方便阅读就不写了。
		
		
		applicationContext = this;
        instance = this;

        /**
         * this function will initialize the HuanXin SDK
         * 
         * @return boolean true if caller can continue to call HuanXin related APIs
         *  after calling onInit, otherwise false.
         * 
         * 环信初始化SDK帮助函数
         * 返回true如果正确初始化，否则false，如果返回为false，请在后续的调用中不要调用任何和环信相关的代码
         * 
         * for example:
         * 例子：
         * 
         * public class DemoHXSDKHelper extends HXSDKHelper
         * 
         * HXHelper = new DemoHXSDKHelper();
         * if(HXHelper.onInit(context)){
         *     // do HuanXin related work
         * }
         */
        hxSDKHelper.onInit(applicationContext);
        
        //防止字体被从新设置
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        displayMetrics.scaledDensity = displayMetrics.density;
	}

	{
		PlatformConfig.setWeixin("wxe94d0f3253c0fa2e",
				"0440904e1f163344948abf0e263dbb52");
		PlatformConfig.setQQZone("1104974044", "KEYnDtIZJu58SKoiRtp");
	}
	
	
	public static Application getInstance() {
		return instance;
	}
 
	/**
	 * 获取内存中好友user list
	 * @return
	 */
	public Map<String, User> getContactList() {
	    return hxSDKHelper.getContactList();
	}

	/**
	 * 设置好友user list到内存中
	 * @param contactList
	 */
	public void setContactList(Map<String, User> contactList) {
	    hxSDKHelper.setContactList(contactList);
	}

	/**
	 * 获取当前登陆用户名
	 * @return
	 */
	public String getUserName() {
	    return hxSDKHelper.getHXId();
	}

	/**
	 * 获取密码
	 * @return
	 */
	public String getPassword() {
		return hxSDKHelper.getPassword();
	}

	/**
	 * 设置用户名
	 * @param user
	 */
	public void setUserName(String username) {
	    hxSDKHelper.setHXId(username);
	}

	/**
	 * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
	 * 内部的自动登录需要的密码，已经加密存储了
	 *
	 * @param pwd
	 */
	public void setPassword(String pwd) {
	    hxSDKHelper.setPassword(pwd);
	}

	/**
	 * 退出登录,清空数据
	 */
	public void logout(final EMCallBack emCallBack) {
		// 先调用sdk logout，在清理app中自己的数据
	    hxSDKHelper.logout(emCallBack);
	}
	//防止字体被从新设置
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    if (newConfig.fontScale != 1)//非默认值
	    	//防止字体被从新设置
	        getResources();    
	    super.onConfigurationChanged(newConfig);
	}

	@Override
	public Resources getResources() {
	     Resources res = super.getResources();
	     if (res.getConfiguration().fontScale != 1) {//非默认值
	        Configuration newConfig = new Configuration();       
	        newConfig.setToDefaults();//设置默认        
	        res.updateConfiguration(newConfig, res.getDisplayMetrics()); 
	     }    
	     return res;
	}
}
