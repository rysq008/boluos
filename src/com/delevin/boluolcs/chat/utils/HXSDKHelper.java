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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.delevin.boluolcs.chat.utils.HXNotifier.HXNotificationInfoProvider;
import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMValueCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatConfig.EMEnvMode;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;

/**
 * The developer can derive from this class to talk with HuanXin SDK
 * All the Huan Xin related initialization and global listener are implemented in this class which will 
 * help developer to speed up the SDK integration��
 * this is a global instance class which can be obtained in any codes through getInstance()
 * 
 * ������Ա����ѡ��̳��������SDK������ȥ�ӿ��ʼ�������ٶȡ�������ʼ������SDK��
 * �����ó�ʼ�������ͳ�ʼ����Ӧ�ļ�����
 * �����̳�����Ҫ����Ҫ�����ṩ��Ӧ�ĺ������������ṩһ��{@link HXSDKModel}. 
 * ������ʵ��abstract protected HXSDKModel createModel();
 * ȫ�ֽ���һ�������ʵ�����ڣ����Կ���������ط�ͨ��getInstance()������ȡ��ȫ��ʵ��
 * 
 * @author easemob
 *
 */
public abstract class HXSDKHelper {

	/**
	 * Ⱥ��������
	 */
	static public interface HXSyncListener {
		public void onSyncSucess(boolean success);
	}
	
    private static final String TAG = "HXSDKHelper";
    
    /**
     * application context
     */
    protected Context appContext = null;
    
    /**
     * HuanXin mode helper, which will manage the user data and user preferences
     */
    protected HXSDKModel hxModel = null;
    
    /**
     * MyConnectionListener
     */
    protected EMConnectionListener connectionListener = null;
    
    /**
     * HuanXin ID in cache
     */
    protected String hxId = null;
    
    /**
     * password in cache
     */
    protected String password = null;
    
    /**
     * init flag: test if the sdk has been inited before, we don't need to init again
     */
    private boolean sdkInited = false;

    /**
     * the global HXSDKHelper instance
     */
    private static HXSDKHelper me = null;
    
    /**
     * the notifier
     */
    protected HXNotifier notifier = null;

	/**
	 * HuanXin sync groups status listener
	 */
	private List<HXSyncListener> syncGroupsListeners;

	/**
	 * HuanXin sync contacts status listener
	 */
	private List<HXSyncListener> syncContactsListeners;

	/**
	 * HuanXin sync blacklist status listener
	 */
	private List<HXSyncListener> syncBlackListListeners;

	private boolean isSyncingGroupsWithServer = false;

	private boolean isSyncingContactsWithServer = false;

	private boolean isSyncingBlackListWithServer = false;
	
	private boolean isGroupsSyncedWithServer = false;

	private boolean isContactsSyncedWithServer = false;

	private boolean isBlackListSyncedWithServer = false;
	
	private boolean alreadyNotified = false;

    protected HXSDKHelper(){
        me = this;
    }
    
    /**
     * this function will initialize the HuanXin SDK
     * 
     * @return boolean true if caller can continue to call HuanXin related APIs after calling onInit, otherwise false.
     * 
     * ���ų�ʼ��SDK��������
     * ����true�����ȷ��ʼ��������false���������Ϊfalse�����ں����ĵ����в�Ҫ�����κκͻ�����صĴ���
     * 
     * for example:
     * ���ӣ�
     * 
     * public class DemoHXSDKHelper extends HXSDKHelper
     * 
     * HXHelper = new DemoHXSDKHelper();
     * if(HXHelper.onInit(context)){
     *     // do HuanXin related work
     * }
     */
    public synchronized boolean onInit(Context context){
        if(sdkInited){
            return true;
        }

        appContext = context;

        // create HX SDK model
        hxModel = createModel();
        
        // create a defalut HX SDK model in case subclass did not provide the model
        if(hxModel == null){
            hxModel = new DefaultHXSDKModel(appContext);
        }
        
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        
        Log.d(TAG, "process app name : " + processAppName);
        
        // ���app������Զ�̵�service����application:onCreate�ᱻ����2��
        // Ϊ�˷�ֹ����SDK����ʼ��2�Σ��Ӵ��жϻᱣ֤SDK����ʼ��1��
        // Ĭ�ϵ�app�����԰���ΪĬ�ϵ�process name�����У�����鵽��process name����app��process name����������
        if (processAppName == null || !processAppName.equalsIgnoreCase(hxModel.getAppProcessName())) {
            Log.e(TAG, "enter the service process!");
            
            // ���application::onCreate �Ǳ�service ���õģ�ֱ�ӷ���
            return false;
        }

        // ��ʼ������SDK,һ��Ҫ�ȵ���init()
        EMChat.getInstance().init(context);
        
        // ����sandbox���Ի���
        // ���鿪���߿���ʱ���ô�ģʽ
        if(hxModel.isSandboxMode()){
            EMChat.getInstance().setEnv(EMEnvMode.EMSandboxMode);
        }
        
        if(hxModel.isDebugMode()){
            // set debug mode in development process
            EMChat.getInstance().setDebugMode(true);
        }

        Log.d(TAG, "initialize EMChat SDK");
                
        initHXOptions();
        initListener();
        
        syncGroupsListeners = new ArrayList<HXSyncListener>();
        syncContactsListeners = new ArrayList<HXSyncListener>();
        syncBlackListListeners = new ArrayList<HXSyncListener>();
        
        isGroupsSyncedWithServer = hxModel.isGroupsSynced();
        isContactsSyncedWithServer = hxModel.isContactSynced();
        isBlackListSyncedWithServer = hxModel.isBacklistSynced();
        
        sdkInited = true;
        return true;
    }
    
    /**
     * get global instance
     * @return
     */
    public static HXSDKHelper getInstance(){
        return me;
    }
    
    public Context getAppContext(){
        return appContext;
    }
    
    public HXSDKModel getModel(){
        return hxModel;
    }
    
    public String getHXId(){
        if(hxId == null){
            hxId = hxModel.getHXId();
        }
        return hxId;
    }
    
    public String getPassword(){
        if(password == null){
            password = hxModel.getPwd();
        }
        return password;    
    }
    
    public void setHXId(String hxId){
        if (hxId != null) {
            if(hxModel.saveHXId(hxId)){
                this.hxId = hxId;
            }
        }
    }
    
    public void setPassword(String password){
        if(hxModel.savePassword(password)){
            this.password = password;
        }
    }
    
    /**
     * the subclass must override this class to provide its own model or directly use {@link DefaultHXSDKModel}
     * @return
     */
    abstract protected HXSDKModel createModel();
    
    /**
     * please make sure you have to get EMChatOptions by following method and set related options
     *      EMChatOptions options = EMChatManager.getInstance().getChatOptions();
     */
    protected void initHXOptions(){
        Log.d(TAG, "init HuanXin Options");
        
        // ��ȡ��EMChatOptions����
        EMChatOptions options = EMChatManager.getInstance().getChatOptions();
        // Ĭ����Ӻ���ʱ���ǲ���Ҫ��֤�ģ��ĳ���Ҫ��֤
//        options.setAcceptInvitationAlways(hxModel.getAcceptInvitationAlways());
        // Ĭ�ϻ����ǲ�ά�����ѹ�ϵ�б�ģ����app�������ŵĺ��ѹ�ϵ���������������Ϊtrue
        options.setUseRoster(hxModel.getUseHXRoster());
        // �����Ƿ���Ҫ�Ѷ���ִ
        options.setRequireAck(hxModel.getRequireReadAck());
        // �����Ƿ���Ҫ���ʹ��ִ
        options.setRequireDeliveryAck(hxModel.getRequireDeliveryAck());
        // ���ô�db��ʼ������ʱ, ÿ��conversation��Ҫ����msg�ĸ���
        options.setNumberOfMessagesLoaded(1);
        
        notifier = createNotifier();
        notifier.init(appContext);
        
        notifier.setNotificationInfoProvider(getNotificationListener());
    }
    
    /**
     * subclass can override this api to return the customer notifier
     * 
     * @return
     */
    protected HXNotifier createNotifier(){
        return new HXNotifier();
    }
    
    public HXNotifier getNotifier(){
        return notifier;
    }
    
    /**
     * logout HuanXin SDK
     */
    public void logout(final EMCallBack callback){
        setPassword(null);
        reset();
        EMChatManager.getInstance().logout(new EMCallBack(){

            @Override
            public void onSuccess() {
                if(callback != null){
                    callback.onSuccess();
                }
            }

            @Override
            public void onError(int code, String message) {
            }

            @Override
            public void onProgress(int progress, String status) {
                if(callback != null){
                    callback.onProgress(progress, status);
                }
            }
            
        });
    }
    
    /**
     * ����Ƿ��Ѿ���¼��
     * @return
     */
    public boolean isLogined(){
       return EMChat.getInstance().isLoggedIn();
    }
    
    protected HXNotificationInfoProvider getNotificationListener(){
        return null;
    }

    /**
     * init HuanXin listeners
     */
    protected void initListener(){
        Log.d(TAG, "init listener");
        
        // create the global connection listener
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
            	if (error == EMError.USER_REMOVED) {
            		onCurrentAccountRemoved();
            	}else if (error == EMError.CONNECTION_CONFLICT) {
                    onConnectionConflict();
                }else{
                    onConnectionDisconnected(error);
                }
            }

            @Override
            public void onConnected() {
                onConnectionConnected();
            }
        };
        
        //ע�����Ӽ���
        EMChatManager.getInstance().addConnectionListener(connectionListener);
    }

    /**
     * the developer can override this function to handle connection conflict error
     */
    protected void onConnectionConflict(){}

    
    /**
     * the developer can override this function to handle user is removed error
     */
    protected void onCurrentAccountRemoved(){}
    
    
    /**
     * handle the connection connected
     */
    protected void onConnectionConnected(){}
    
    /**
     * handle the connection disconnect
     * @param error see {@link EMError}
     */
    protected void onConnectionDisconnected(int error){}

    /**
     * check the application process name if process name is not qualified, then we think it is a service process and we will not init SDK
     * @param pID
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = appContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                     Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                     info.processName +"  Label: "+c.toString());
                     processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    
        
    public void addSyncGroupListener(HXSyncListener listener) {
	    if (listener == null) {
		    return;
	    }
	    if (!syncGroupsListeners.contains(listener)) {
		    syncGroupsListeners.add(listener);
	    }
    }

    public void removeSyncGroupListener(HXSyncListener listener) {
	    if (listener == null) {
		    return;
	    }
	    if (syncGroupsListeners.contains(listener)) {
		    syncGroupsListeners.remove(listener);
	    }
    }

    public void addSyncContactListener(HXSyncListener listener) {
	    if (listener == null) {
		    return;
	    }
	    if (!syncContactsListeners.contains(listener)) {
		    syncContactsListeners.add(listener);
	    }
    }

    public void removeSyncContactListener(HXSyncListener listener) {
	    if (listener == null) {
		    return;
	    }
	    if (syncContactsListeners.contains(listener)) {
		    syncContactsListeners.remove(listener);
	    }
    }

    public void addSyncBlackListListener(HXSyncListener listener) {
	    if (listener == null) {
		    return;
	    }
	    if (!syncBlackListListeners.contains(listener)) {
		    syncBlackListListeners.add(listener);
	    }
    }

    public void removeSyncBlackListListener(HXSyncListener listener) {
	    if (listener == null) {
		    return;
	    }
	    if (syncBlackListListeners.contains(listener)) {
		    syncBlackListListeners.remove(listener);
	    }
    }
   
    /**
     * ͬ���������ӷ�������ȡȺ���б�
     * �÷������¼����״̬������ͨ��isSyncingGroupsFromServer��ȡ�Ƿ����ڸ���
     * ��HXPreferenceUtils.getInstance().getSettingSyncGroupsFinished()��ȡ�Ƿ�����Ѿ����
     * @throws EaseMobException
     */
    public synchronized void asyncFetchGroupsFromServer(final EMCallBack callback){
        if(isSyncingGroupsWithServer){
            return;
        }
        
        isSyncingGroupsWithServer = true;
        
        new Thread(){
            @Override
            public void run(){
                try {
                    EMGroupManager.getInstance().getGroupsFromServer();
                    
                    // in case that logout already before server returns, we should return immediately
                    if(!EMChat.getInstance().isLoggedIn()){
                        return;
                    }
                    
                    hxModel.setGroupsSynced(true);
                    
                    isGroupsSyncedWithServer = true;
                    isSyncingGroupsWithServer = false;
                    if(callback != null){
                        callback.onSuccess();
                    }
                } catch (EaseMobException e) {
                    hxModel.setGroupsSynced(false);
                    isGroupsSyncedWithServer = false;
                    isSyncingGroupsWithServer = false;
                    if(callback != null){
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }
            
            }
        }.start();
    }

    public void noitifyGroupSyncListeners(boolean success){
        for (HXSyncListener listener : syncGroupsListeners) {
            listener.onSyncSucess(success);
        }
    }
    
    public void asyncFetchContactsFromServer(final EMValueCallBack<List<String>> callback){
        if(isSyncingContactsWithServer){
            return;
        }
        
        isSyncingContactsWithServer = true;
        
        new Thread(){
            @Override
            public void run(){
                List<String> usernames = null;
                try {
                    usernames = EMContactManager.getInstance().getContactUserNames();
                    
                    // in case that logout already before server returns, we should return immediately
                    if(!EMChat.getInstance().isLoggedIn()){
                        return;
                    }
                    
                    hxModel.setContactSynced(true);
                    
                    isContactsSyncedWithServer = true;
                    isSyncingContactsWithServer = false;
                    if(callback != null){
                        callback.onSuccess(usernames);
                    }
                } catch (EaseMobException e) {
                    hxModel.setContactSynced(false);
                    isContactsSyncedWithServer = false;
                    isSyncingContactsWithServer = false;
                    e.printStackTrace();
                    if(callback != null){
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }
                
            }
        }.start();
    }

    public void notifyContactsSyncListener(boolean success){
        for (HXSyncListener listener : syncContactsListeners) {
            listener.onSyncSucess(success);
        }
    }
    
    public void asyncFetchBlackListFromServer(final EMValueCallBack<List<String>> callback){
        
        if(isSyncingBlackListWithServer){
            return;
        }
        
        isSyncingBlackListWithServer = true;
        
        new Thread(){
            @Override
            public void run(){
                try {
                    List<String> usernames = null;
                    usernames = EMContactManager.getInstance().getBlackListUsernamesFromServer();
                    
                    // in case that logout already before server returns, we should return immediately
                    if(!EMChat.getInstance().isLoggedIn()){
                        return;
                    }
                    
                    hxModel.setBlacklistSynced(true);
                    
                    isBlackListSyncedWithServer = true;
                    isSyncingBlackListWithServer = false;
                    if(callback != null){
                        callback.onSuccess(usernames);
                    }
                } catch (EaseMobException e) {
                    hxModel.setBlacklistSynced(false);
                    
                    isBlackListSyncedWithServer = false;
                    isSyncingBlackListWithServer = true;
                    e.printStackTrace();
                    
                    if(callback != null){
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }
                
            }
        }.start();
    }

    public void notifyBlackListSyncListener(boolean success){
        for (HXSyncListener listener : syncBlackListListeners) {
            listener.onSyncSucess(success);
        }
    }
    
    public boolean isSyncingGroupsWithServer() {
	    return isSyncingGroupsWithServer;
    }

    public boolean isSyncingContactsWithServer() {
	    return isSyncingContactsWithServer;
    }

    public boolean isSyncingBlackListWithServer() {
	    return isSyncingBlackListWithServer;
    }
    
    public boolean isGroupsSyncedWithServer() {
	    return isGroupsSyncedWithServer;
    }

    public boolean isContactsSyncedWithServer() {
	    return isContactsSyncedWithServer;
    }

    public boolean isBlackListSyncedWithServer() {
	    return isBlackListSyncedWithServer;
    }
    
    public synchronized void notifyForRecevingEvents(){
        if(alreadyNotified){
            return;
        }
        // ֪ͨsdk��UI �Ѿ���ʼ����ϣ�ע������Ӧ��receiver��listener, ���Խ���broadcast��
        EMChat.getInstance().setAppInited();
        alreadyNotified = true;
    }
    
    synchronized void reset(){
        isSyncingGroupsWithServer = false;
        isSyncingContactsWithServer = false;
        isSyncingBlackListWithServer = false;
        
        hxModel.setGroupsSynced(false);
        hxModel.setContactSynced(false);
        hxModel.setBlacklistSynced(false);
        
        isGroupsSyncedWithServer = false;
        isContactsSyncedWithServer = false;
        isBlackListSyncedWithServer = false;
        
        alreadyNotified = false;
    }
}
