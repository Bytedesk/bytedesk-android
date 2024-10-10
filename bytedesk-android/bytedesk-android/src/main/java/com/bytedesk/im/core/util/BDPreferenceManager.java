package com.bytedesk.im.core.util;

import android.app.backup.BackupManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.bytedesk.im.R;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

/**
 *
 * @author bytedesk.com
 */
public class BDPreferenceManager {
    //
	private static BDPreferenceManager sPreferenceManager = null;
    //
    private SharedPreferences 	        mSharedPreferences;
    private Context 					mContext;
    private SoundPool                   mSoundPool;
    private HashMap<Integer, Integer>   mSoundIDMap = new HashMap<>();
    //
    private BDPreferenceManager(Context context) {
        mContext = context;
        mSoundPool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundIDMap.put(1, mSoundPool.load(context, R.raw.appkefu_alarm, 1));
        mSoundIDMap.put(2, mSoundPool.load(context, R.raw.appkefu_newmsg, 1));
        mSoundIDMap.put(3, mSoundPool.load(context, R.raw.appkefu_waiting, 1));

        mSharedPreferences = mContext.getSharedPreferences(BDCoreConstant.APP_NAME, 0);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(mChangeListener);        
        updatePreferenceValues();
        //
        setConnecting(false);
        setConnected(false);
    }

	//
    public static BDPreferenceManager getInstance(Context context) {
        if (sPreferenceManager == null) {
        	sPreferenceManager = new BDPreferenceManager(context);
        } 
        return sPreferenceManager;        
    }

    private OnSharedPreferenceChangeListener mChangeListener = (sharedPreferences, key) -> {
        //
        updatePreferenceValues();
    };

    private void updatePreferenceValues() {
//    	mUsername  = getString("username", "");
//    	mPassword  = getString("password", "");
    }
    
    public SharedPreferences.Editor getEditor() {
        return mSharedPreferences.edit();
    }
    
    public Boolean saveSetting(String key, Boolean value) {
        getEditor().putBoolean(key, value).commit();
        OnPreferencesUpdated(key);
        return value;
    }

    public String saveSetting(String key, String value) {
        getEditor().putString(key, value).commit();
        OnPreferencesUpdated(key);
        return value;
    }

    public Integer saveSetting(String key, Integer value) {
        getEditor().putInt(key, value).commit();
        OnPreferencesUpdated(key);
        return value;
    }

    public Float saveSetting(String key, Float value) {
    	getEditor().putFloat(key, value).commit();
        OnPreferencesUpdated(key);
        return value;
    }

    public void OnPreferencesUpdated(String key) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            BackupManager bm = new BackupManager(mContext);
            bm.dataChanged();
        }
    }
    
//    public boolean SharedPreferencesContains(String key) {
//        return mSharedPreferences.contains(key);
//    }
    
    public String getString(String key, String defaultValue) {
        try {
            return mSharedPreferences.getString(key, defaultValue);
        } catch (ClassCastException  e) {
            Logger.d("Failed to retrive setting " + key);
        }
        return defaultValue;
    }
    
    public int getInt(String key, int defaultValue) {
        try {
            return mSharedPreferences.getInt(key, defaultValue);
        } catch (ClassCastException  e) {
            Logger.d("Failed to retrive setting " + key);
        }
        return defaultValue;
    }
    
    public boolean getBoolean(String key, boolean defaultValue) {
        try {
            return mSharedPreferences.getBoolean(key, defaultValue);
        } catch (ClassCastException  e) {
            Logger.d("Failed to retrive setting " + key);
        }
        return defaultValue;
    }
    
    public float getFloat(String key, float defaultVaule) {
    	try {
            return mSharedPreferences.getFloat(key, defaultVaule);
        } catch (ClassCastException  e) {
            Logger.d("Failed to retrive setting " + key);
        }
        return defaultVaule;
    }

    public void playAlarmMessage() {
        mSoundPool.play(mSoundIDMap.get(1), 1, 1, 0, 0, 1);
    }

    public void playReceiveMessage() {
        mSoundPool.play(mSoundIDMap.get(2), 1, 1, 0, 0, 1);
    }

    public void playSendMessage() {
        mSoundPool.play(mSoundIDMap.get(3), 1, 1, 0, 0, 1);
    }

    private void release() {
        mSoundPool.release();
    }

    ////////////////////////////////////////////////////////////////////
    public boolean isFirstTimeLogin() {
        String username = getUsername();
        if (username == null || username.trim().length() == 0)
            return true;
        return false;
    }

    public void setUserInfo(String role, String username, String password, String appKey, String subDomain) {
        setRole(role.trim());
        setUsername(username.trim());
        setPassword(password.trim());
        setAppKey(appKey.trim());
        setSubDomain(subDomain.trim());
    }

    public String getAvatar() {
        return getString("avatar", "");
    }

    public void setAvatar(String avatar) {
        saveSetting("avatar", avatar);
    }

    public String getRole() {
        return getString("role", "");
    }

    public void setRole(String role) {
        saveSetting("role", role);
    }

    public String getUid() {
        return getString("uid", "");
    }

    public void setUid(String value) {
        saveSetting("uid", value);
    }

    public String getUsername() {
        return getString("username", "");
    }

    public void setUsername(String value) {
        saveSetting("username", value);
    }

    public String getNickname() {
        String nickname = getString("nickname", "");
        return nickname.trim().length() > 0 ? nickname : getUsername();
    }

    public void setNickname(String value) {
        saveSetting("nickname", value);
    }

    public String getRealName() {
        String realName = getString("realName", "");
        return realName.trim().length() > 0 ? realName : getUsername();
    }

    public void setRealName(String value) {
        saveSetting("realName", value);
    }

    public String getPassword() {
        return getString("password", "");
    }

    public void setPassword(String value) {
        saveSetting("password", value);
    }

    public String getAppKey() {
        return getString("appKey", "");
    }

    public void setAppKey(String appKey) {
        saveSetting("appKey", appKey);
    }

    public String getSubDomain() {
        return getString("subDomain", "");
    }

    public void setSubDomain(String subDomain) {
        saveSetting("subDomain", subDomain);
    }

    public String getDescription() {
        return getString("description", "");
    }

    public void setDescription(String description) {
        saveSetting("description", description);
    }

    public void setOrgUid(String orgUid) {
        saveSetting("orgUid", orgUid);
    }

    public String getOrgUid() {
        return getString("orgUid", "");
    }

    public String getAcceptStatus() {
        String acceptStatus = getString("acceptStatus", "");
        if (acceptStatus.equals(BDCoreConstant.USER_STATUS_ONLINE)) {
            return BDCoreConstant.STATUS_ONLINE;
        } else if (acceptStatus.equals(BDCoreConstant.USER_STATUS_BUSY)) {
            return BDCoreConstant.STATUS_BUSY;
        } else if (acceptStatus.equals(BDCoreConstant.USER_STATUS_REST)) {
            return BDCoreConstant.STATUS_REST;
        }
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        saveSetting("acceptStatus", acceptStatus);
    }

    public String getAutoReplyContent() {
        return getString("autoReplyContent", "");
    }

    public void setAutoReplyContent(String autoReplyContent) {
        saveSetting("autoReplyContent", autoReplyContent);
    }

    public String getWelcomeTip() {
        return getString("welcomeTip", "");
    }

    public void setWelcomeTip(String autoReplyContent) {
        saveSetting("welcomeTip", autoReplyContent);
    }

    public String getCurrentThreadTid() {
        return getString("currentThreadTid", "");
    }

    public void setCurrentThreadTid(String value) {
        saveSetting("currentThreadTid", value);
    }

    public boolean isConnected() {
        return getBoolean("connected", false);
    }

    public void setConnected(boolean connected) {
        saveSetting("connected", connected);
    }

    public boolean isConnecting() {
        return getBoolean("connecting", false);
    }

    public void setConnecting(boolean connecting) {
        saveSetting("connecting", connecting);
    }

    public void clearCurrentThreadTid() {
        saveSetting("currentThreadTid", "");
    }

    public String getClientUUID() {
        String clientId = getString("clientId", "");
        if (clientId.trim().length() > 0) {
            return clientId;
        } else {
            String newClientId = BDCoreUtils.uuid();
            saveSetting("clientId", newClientId);
            return newClientId;
        }
    }

    /**
     * 登录角色为visitor
     * @return
     */
    public boolean loginAsVisitor() {
        return getRole().equals(BDCoreConstant.ROLE_VISITOR);
    }

    /**
     * 调用访客会话接口，发起访客会话
     * @return
     */
    public boolean isVisitor() {
        return getBoolean("isVisitor", true);
    }

    public void setVisitor(boolean visitor) {
        saveSetting("isVisitor", visitor);
    }

//    public String getCurrentTid() {
//        return getString("currentTid", "");
//    }
//
//    public void setCurrentTid(String currentTid) {
//        saveSetting("currentTid", currentTid);
//    }

    public String getAccessToken() {
        return getString("access_token", "");
    }

    public void setAccessToken(String accessToken) {
        saveSetting("access_token", accessToken);
    }

    public int getExpireIn() {
        return getInt("expire_in", 0);
    }

    public void setExpireIn(int expireIn) {
        saveSetting("expire_in", expireIn);
    }

    public String getRefreshToken() {
        return getString("refresh_token", "");
    }

    public void setRefreshToken(String refreshToken) {
        saveSetting("refresh_token", refreshToken);
    }

    public String getTokenType() {
        return getString("token_type", "");
    }

    public void setTokenType(String tokenType) {
        saveSetting("token_type", tokenType);
    }

    /**
     * 是否启用阅后即焚
     */
    public boolean getDestroyAfterReading(String tidOrUidOrGid, String sessionType) {
        String key = tidOrUidOrGid + "_" + sessionType;
        return getBoolean(key, false);
    }

    public void setDestroyAfterReading(String tidOrUidOrGid, String sessionType, boolean flag) {
        String key = tidOrUidOrGid + "_" + sessionType;
        saveSetting(key, flag);
    }

    /**
     * 阅后即焚时长
     */
    public int getDestroyAfterLength(String tidOrUidOrGid, String sessionType) {
        String key = tidOrUidOrGid + "_" + sessionType + "_length";
        return getInt(key, 0);
    }

    public void setDestroyAfterLength(String tidOrUidOrGid, String sessionType, int length) {
        String key = tidOrUidOrGid + "_" + sessionType + "_length";
        saveSetting(key, length);
    }

    /**
     * 是否播放发送消息提示音
     */
    public boolean shouldRingWhenSendMessage() {
        return getBoolean(BDCoreConstant.BD_SHOULD_PLAY_SEND_MESSAGE_SOUND, false);
    }

    public void setRingWhenSendMessage(boolean flag) {
        saveSetting(BDCoreConstant.BD_SHOULD_PLAY_SEND_MESSAGE_SOUND, flag);
    }

    /**
     * 是否播放接收消息提示音
     */
    public boolean shouldRingWhenReceiveMessage() {
        return getBoolean(BDCoreConstant.BD_SHOULD_PLAY_RECEIVE_MESSAGE_SOUND, false);
    }

    public void setRingWhenReceiveMessage(boolean flag) {
        saveSetting(BDCoreConstant.BD_SHOULD_PLAY_RECEIVE_MESSAGE_SOUND, flag);
    }

    /**
     * 收到消息是否震动
     */
    public boolean shouldVibrateWhenReceiveMessage() {
        return getBoolean(BDCoreConstant.BD_SHOULD_VIBRATE_ON_RECEIVE_MESSAGE, false);
    }

    public void setVibrateWhenReceiveMessage(boolean flag) {
        saveSetting(BDCoreConstant.BD_SHOULD_VIBRATE_ON_RECEIVE_MESSAGE, flag);
    }

    /**
     *
     */
    public boolean hasSetDeviceInfo() {
        return getBoolean(BDCoreConstant.BD_SHOULD_SET_DEVICE_INFO, false);
    }

    public void setDeviceInfoFlag(boolean flag) {
        saveSetting(BDCoreConstant.BD_SHOULD_SET_DEVICE_INFO, flag);
    }


    /**
     * 退出登录，清空本地数据
     */
    public void clear() {

        setUid("");
        setUsername("");
        setPassword("");
        setNickname("");
        setAvatar("");
        //
        setAccessToken("");
        setRefreshToken("");
        setTokenType("");
    }


}





























