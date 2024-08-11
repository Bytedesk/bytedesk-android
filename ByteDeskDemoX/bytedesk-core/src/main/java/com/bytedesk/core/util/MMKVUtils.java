//package com.bytedesk.core.util;
//
//import android.content.Context;
//
////import com.tencent.mmkv.MMKV;
//
///**
// * https://github.com/Tencent/MMKV
// *
// * @author bytedesk.com on 2019-10-09
// */
//public class MMKVUtils {
//
//    public static final String UID = "uid";
//    public static final String AVATAR = "avatar";
//    public static final String ROLE = "role";
//    public static final String USERNAME = "username";
//    public static final String NICKNAME = "nickname";
//    public static final String REALNAME = "realname";
//    public static final String PASSWORD = "password";
//    public static final String APP_KEY = "appkey";
//    public static final String SUB_DOMAIN = "sub_domain";
//    public static final String DESCRIPTION = "description";
//    public static final String ACCEPT_STATUS = "acceptStatus";
//    public static final String AUTO_REPLY_CONTENT = "autoReplyContent";
//
//    public static final String init(Context context) {
//        return MMKV.initialize(context);
//    }
//
//    public static final String getUid() {
//        return MMKV.defaultMMKV().decodeString(UID);
//    }
//
//    public static final void setUid(String uid) {
//        MMKV.defaultMMKV().encode(UID, uid);
//    }
//
//    public static final String getAvatar() {
//        return MMKV.defaultMMKV().decodeString(AVATAR);
//    }
//
//    public static final void setAvatar(String avatar) {
//        MMKV.defaultMMKV().encode(AVATAR, avatar);
//    }
//
//    public static final String getRole() {
//        return MMKV.defaultMMKV().decodeString(ROLE);
//    }
//
//    public static final void setRole(String role) {
//        MMKV.defaultMMKV().encode(ROLE, role);
//    }
//
//    public static final String getUsername() {
//        return MMKV.defaultMMKV().decodeString(USERNAME);
//    }
//
//    public static final void setUsername(String username) {
//        MMKV.defaultMMKV().encode(USERNAME, username);
//    }
//
//    public static final String getNickname() {
//        return MMKV.defaultMMKV().decodeString(NICKNAME);
//    }
//
//    public static final void setNickname(String nickname) {
//        MMKV.defaultMMKV().encode(NICKNAME, nickname);
//    }
//
//    public static final String getRealname() {
//        return MMKV.defaultMMKV().decodeString(REALNAME);
//    }
//
//    public static final void setRealname(String realname) {
//        MMKV.defaultMMKV().encode(REALNAME, realname);
//    }
//
//    public static final String getPassword() {
//        return MMKV.defaultMMKV().decodeString(PASSWORD);
//    }
//
//    public static final void setPassword(String password) {
//        MMKV.defaultMMKV().encode(PASSWORD, password);
//    }
//
//    public static final String getAppKey() {
//        return MMKV.defaultMMKV().decodeString(APP_KEY);
//    }
//
//    public static final void setAppKey(String appKey) {
//        MMKV.defaultMMKV().encode(APP_KEY, appKey);
//    }
//
//    public static final String getSubDomain() {
//        return MMKV.defaultMMKV().decodeString(SUB_DOMAIN);
//    }
//
//    public static final void setSubDomain(String subDomain) {
//        MMKV.defaultMMKV().encode(SUB_DOMAIN, subDomain);
//    }
//
//    public static final String getDescription() {
//        return MMKV.defaultMMKV().decodeString(DESCRIPTION);
//    }
//
//    public static final void setDescription(String description) {
//        MMKV.defaultMMKV().encode(DESCRIPTION, description);
//    }
//
//    public static final String getAcceptStatus() {
//        String acceptStatus = MMKV.defaultMMKV().decodeString(ACCEPT_STATUS);
//        if (acceptStatus.equals(BDCoreConstant.USER_STATUS_ONLINE)) {
//            return BDCoreConstant.STATUS_ONLINE;
//        } else if (acceptStatus.equals(BDCoreConstant.USER_STATUS_BUSY)) {
//            return BDCoreConstant.STATUS_BUSY;
//        } else if (acceptStatus.equals(BDCoreConstant.USER_STATUS_REST)) {
//            return BDCoreConstant.STATUS_REST;
//        }
//        return acceptStatus;
//    }
//
//    public static void setAcceptStatus(String acceptStatus) {
//        MMKV.defaultMMKV().encode(ACCEPT_STATUS, acceptStatus);
//    }
//
//    public static final String getAutoReplyContent() {
//        return MMKV.defaultMMKV().decodeString(AUTO_REPLY_CONTENT);
//    }
//
//    public static void setAutoReplyContent(String autoReplyContent) {
//        MMKV.defaultMMKV().encode(AUTO_REPLY_CONTENT, autoReplyContent);
//    }
//
//
//}
