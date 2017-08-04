/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.delevin.boluolcs.chat.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.pusupanshi.boluolicai.R;

/**
 * �������������
 * @author Administrator
 *
 */
public class CommonUtils {
	private static final String TAG = "CommonUtils";
	/**
	 * ��������Ƿ����
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}

	/**
	 * ���Sdcard�Ƿ����
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
	

	/**
     * ������Ϣ���ݺ���Ϣ���ͻ�ȡ��Ϣ������ʾ
     * 
     * @param message
     * @param context
     * @return
     */
    public static String getMessageDigest(EMMessage message, Context context) {
        String digest = "";
        switch (message.getType()) {
//        case LOCATION: // λ����Ϣ
//            if (message.direct == EMMessage.Direct.RECEIVE) {
//                //��sdk���ᵽ��ui�У�ʹ�ø��򵥲�����Ļ�ȡstring����
////              digest = EasyUtils.getAppResourceString(context, "location_recv");
//                digest = getString(context, R.string.location_recv);
//                digest = String.format(digest, message.getFrom());
//                return digest;
//            } else {
////              digest = EasyUtils.getAppResourceString(context, "location_prefix");
//                digest = getString(context, R.string.location_prefix);
//            }
//            break;
        case IMAGE: // ͼƬ��Ϣ
            digest = getString(context, R.string.picture);
            break;
        case VOICE:// ������Ϣ
            digest = getString(context, R.string.voice);
            break;
//        case VIDEO: // ��Ƶ��Ϣ
//            digest = getString(context, R.string.video);
//            break;
        case TXT: // �ı���Ϣ
            if(!message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL,false)){
                TextMessageBody txtBody = (TextMessageBody) message.getBody();
                digest = txtBody.getMessage();
            }else{
                TextMessageBody txtBody = (TextMessageBody) message.getBody();
                digest = getString(context, R.string.voice_call) + txtBody.getMessage();
            }
            break;
//        case FILE: //��ͨ�ļ���Ϣ
//            digest = getString(context, R.string.file);
//            break;
        default:
//            EMLog.e(TAG, "error, unknow type");
            return "";
        }

        return digest;
    }
    /**
     * ��ȡָ��ID���ַ���
     * @param context
     * @param resId
     * @return
     */
    static String getString(Context context, int resId){
        return context.getResources().getString(resId);
    }
	
	/**
	 * ��ȡջ��Activity�������
	 * @param context
	 * @return
	 */
	public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity.getClassName();
		else
			return "";
	}

}
