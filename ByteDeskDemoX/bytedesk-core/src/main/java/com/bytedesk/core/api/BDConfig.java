package com.bytedesk.core.api;

import android.content.Context;
import com.bytedesk.core.util.BDPreferenceManager;
//import com.tencent.mmkv.MMKV;
/**
 * TODO: 所有配置后期均修改为动态从服务器获取
 *
 * @author bytedesk.com on 2019/3/27
 */
public class BDConfig {
    //
    public final String BD_MQTT_PORT = "BD_MQTT_PORT";
    public final String BD_MQTT_HOST = "BD_MQTT_HOST";
    public final String BD_MQTT_WEBSOCKET_WSS_URL = "BD_MQTT_WEBSOCKET_WSS_URL";
//    public final String BD_MQTT_AUTH_USERNAME = "BD_MQTT_AUTH_USERNAME";
//    public final String BD_MQTT_AUTH_PASSWORD = "BD_MQTT_AUTH_PASSWORD";
    public final String BD_WEBRTC_STUN_SERVER = "BD_WEBRTC_STUN_SERVER";
    public final String BD_WEBRTC_TURN_SERVER = "BD_WEBRTC_TURN_SERVER";
    public final String BD_WEBRTC_TURN_USERNAME = "BD_WEBRTC_TURN_USERNAME";
    public final String BD_WEBRTC_TURN_PASSWORD = "BD_WEBRTC_TURN_PASSWORD";
    public final String BD_REST_API_HOST = "BD_REST_API_HOST";
    public final String BD_UPLOAD_API_HOST = "BD_UPLOAD_API_HOST";
//    public final String BD_REST_API_HOST_IM = "BD_REST_API_HOST_IM";
//    public final String BD_REST_API_HOST_KF = "BD_REST_API_HOST_KF";

    private static BDConfig bdConfig = null;
//    private MMKV mmkv;
    private BDPreferenceManager bdPreferenceManager;

    // 自定义服务器，请跟萝卜丝联系获取，私有部署开发者调用。公有云用户无需调用
//        BDConfig.getInstance(this).setRestApiHost("https://zovwus.iczhl.com/");
//        BDConfig.getInstance(this).setMqttWebSocketWssURL("wss://zovwus.iczhl.com/websocket");

    /**
     * 消息服务器用户名
     */
//    private static String mqttAuthUsername = "mqtt_android";
//    private static String mqttAuthPassword = "mqtt_android";
//    D/RoomRTCClient: TURN response: {
//        "lifetimeDuration": "86400s",
//                "iceServers": [
//        {
//            "urls": [
//            "stun:108.177.98.127:19302",
//                    "stun:[2607:f8b0:400e:c06::7f]:19302"
//          ]
//        },
//        {
//            "urls": [
//            "turn:173.194.202.127:19305?transport=udp",
//                    "turn:[2607:f8b0:400e:c00::7f]:19305?transport=udp",
//                    "turn:173.194.202.127:19305?transport=tcp",
//                    "turn:[2607:f8b0:400e:c00::7f]:19305?transport=tcp"
//          ],
//            "username": "CNnGhOYFEgZvaJ17wxYYzc/s6OMTIICjBQ",
//                "credential": "rgh2pc3mvBKsZvCgWzmUq1i9cL4=",
//                "maxRateKbps": "8000"
//        }
//      ],
//        "blockStatus": "NOT_BLOCKED",
//                "iceTransportPolicy": "all"
//    }

    // TODO: 后期扩展为数组，设置多个
//    private static List<String> webrtcStunServerList = new ArrayList<>();
    private static String webrtcStunServer = "turn:turn.bytedesk.com";
    private static String webrtcTurnServer = "turn:turn.bytedesk.com";
    private static String webrtcTurnUsername = "jackning";
    private static String webrtcTurnPassword = "kX1JiyPGVTtO3y0o"; //"123456";

    /**
     * 1. 消息服务器地址
     * 2. REST API 接口地址
     */
    // 线上服务器地址
    public static final boolean IS_TLS_CONNECTION = true;
    public static final boolean IS_WEBSOCKET_WSS_CONNECTION = true;
    public static final int LONG_LINK_PORT = 13883;
//    public static final int[] LONG_LINK_PORTS = new int[]{ 13883 };
//    public static final int SHORT_LINK_PORT = 2889;
    public static final String SHORT_LINK_HOST = "android.bytedesk.com";
    public static final String UPLOAD_HOST = "upload.bytedesk.com";
    public static final String MQTT_WEBSOCKET_WSS_URL = "wss://" + SHORT_LINK_HOST + "/websocket";
    public static final String LONG_LINK_HOST = SHORT_LINK_HOST;
    public static final String restApiHost = "https://" + SHORT_LINK_HOST + "/";
    public static final String uploadApiHost = "https://" + UPLOAD_HOST + "/";

    // 真机调试连接localhost/10.0.2.2：1. 真机与电脑连接同一个wifi；2. ifconfig查看电脑ip；3. 修改为上述ip
//    public static final boolean IS_TLS_CONNECTION = false;
//    public static final boolean IS_WEBSOCKET_WSS_CONNECTION = false;
//    public static final int LONG_LINK_PORT = 3883;
////    public static final int[] LONG_LINK_PORTS = new int[]{ 3883 };
////    public static final int SHORT_LINK_PORT = 2889;
//    public static final String SHORT_LINK_HOST = "172.16.0.143";
//    public static final String UPLOAD_LINK_HOST = SHORT_LINK_HOST;
//    public static final String MQTT_WEBSOCKET_WSS_URL = "wss://www.bytedesk.com/websocket";
//    public static final String LONG_LINK_HOST = SHORT_LINK_HOST;
//    public static final String restApiHost = "http://" + SHORT_LINK_HOST + ":8000/";
//    public static final String uploadApiHost = "http://" + UPLOAD_LINK_HOST + ":8000/";

    //
    private BDConfig(Context context) {
//        MMKV.initialize(context);
//        mmkv = MMKV.defaultMMKV();
        bdPreferenceManager = BDPreferenceManager.getInstance(context);
    }

    public static BDConfig getInstance(Context context) {
        if (bdConfig == null) {
            bdConfig = new BDConfig(context);
        }
        return bdConfig;
    }

    /**
     * 恢复默认值
     */
    public void restoreDefault() {
//        mmkv.encode(BD_MQTT_HOST, LONG_LINK_HOST);
        bdPreferenceManager.saveSetting(BD_MQTT_HOST, LONG_LINK_HOST);
//        mmkv.encode(BD_MQTT_PORT, LONG_LINK_PORT);
        bdPreferenceManager.saveSetting(BD_MQTT_PORT, LONG_LINK_PORT);
//        mmkv.encode(BD_MQTT_AUTH_USERNAME, mqttAuthUsername);
//        mmkv.encode(BD_MQTT_AUTH_PASSWORD, mqttAuthPassword);
//        mmkv.encode(BD_WEBRTC_STUN_SERVER, webrtcStunServer);
        bdPreferenceManager.saveSetting(BD_WEBRTC_STUN_SERVER, webrtcStunServer);
//        mmkv.encode(BD_WEBRTC_TURN_SERVER, webrtcTurnServer);
        bdPreferenceManager.saveSetting(BD_WEBRTC_TURN_SERVER, webrtcTurnServer);
//        mmkv.encode(BD_WEBRTC_TURN_USERNAME, webrtcTurnUsername);
        bdPreferenceManager.saveSetting(BD_WEBRTC_TURN_USERNAME, webrtcTurnUsername);
//        mmkv.encode(BD_WEBRTC_TURN_PASSWORD, webrtcTurnPassword);
        bdPreferenceManager.saveSetting(BD_WEBRTC_TURN_PASSWORD, webrtcTurnPassword);
        //
//        mmkv.encode(BD_MQTT_WEBSOCKET_WSS_URL, MQTT_WEBSOCKET_WSS_URL);
        bdPreferenceManager.saveSetting(BD_MQTT_WEBSOCKET_WSS_URL, MQTT_WEBSOCKET_WSS_URL);
//        mmkv.encode(BD_REST_API_HOST, restApiHost);
        bdPreferenceManager.saveSetting(BD_REST_API_HOST, restApiHost);
//        mmkv.encode(BD_REST_API_HOST_IM, imRestApiHost);
//        mmkv.encode(BD_REST_API_HOST_KF, kfRestApiHost);
    }

    /**
     * 本地测试，需要本地部署服务器
     */
//    public void enableLocalHost() {
//        mmkv.encode(BD_MQTT_HOST, "10.0.2.2");
//        mmkv.encode(BD_MQTT_PORT, LONG_LINK_PORT);
////        mmkv.encode(BD_MQTT_AUTH_USERNAME, mqttAuthUsername);
////        mmkv.encode(BD_MQTT_AUTH_PASSWORD, mqttAuthPassword);
//        mmkv.encode(BD_WEBRTC_STUN_SERVER, webrtcStunServer);
//        mmkv.encode(BD_WEBRTC_TURN_SERVER, webrtcTurnServer);
//        mmkv.encode(BD_WEBRTC_TURN_USERNAME, webrtcTurnUsername);
//        mmkv.encode(BD_WEBRTC_TURN_PASSWORD, webrtcTurnPassword);
//        mmkv.encode(BD_REST_API_HOST, "http://10.0.2.2:8000/");
//        mmkv.encode(BD_UPLOAD_API_HOST, "http://10.0.2.2:8000/");
////        mmkv.encode(BD_REST_API_HOST_IM, "http://10.0.2.2:8000/");
////        mmkv.encode(BD_REST_API_HOST_KF, "http://10.0.2.2:8000/");
//    }

    public String getMqttHost() {
//        return mmkv.getString(BD_MQTT_HOST, LONG_LINK_HOST);
        return bdPreferenceManager.getString(BD_MQTT_HOST, LONG_LINK_HOST);
    }

    public void setMqttHost(String host) {
//        mmkv.encode(BD_MQTT_HOST, host);
        bdPreferenceManager.saveSetting(BD_MQTT_HOST, host);
    }
    
    public int getMqttPort() {
//        return mmkv.getInt(BD_MQTT_PORT, LONG_LINK_PORT);
        return bdPreferenceManager.getInt(BD_MQTT_PORT, LONG_LINK_PORT);
    }

    public void setMqttPort(int port) {
//        mmkv.encode(BD_MQTT_PORT, port);
        bdPreferenceManager.saveSetting(BD_MQTT_PORT, port);
    }

    public String getMqttWebSocketWssURL() {
//        return mmkv.getString(BD_MQTT_WEBSOCKET_WSS_URL, MQTT_WEBSOCKET_WSS_URL);
        return bdPreferenceManager.getString(BD_MQTT_WEBSOCKET_WSS_URL, MQTT_WEBSOCKET_WSS_URL);
    }

    public void setMqttWebSocketWssURL(String mqttWebSocketWssURL) {
//        mmkv.encode(BD_MQTT_WEBSOCKET_WSS_URL, mqttWebSocketWssURL);
        bdPreferenceManager.saveSetting(BD_MQTT_WEBSOCKET_WSS_URL, mqttWebSocketWssURL);
    }

//    public String getMqttAuthUsername() {
//        return mmkv.getString(BD_MQTT_AUTH_USERNAME, mqttAuthUsername);
//    }
//
//    public void setMqttAuthUsername(String mqttAuthUsername) {
//        mmkv.encode(BD_MQTT_AUTH_USERNAME, mqttAuthUsername);
//    }
//
//    public String getMqttAuthPassword() {
//        return mmkv.getString(BD_MQTT_AUTH_PASSWORD, mqttAuthPassword);
//    }
//
//    public void setMqttAuthPassword(String mqttAuthPassword) {
//        mmkv.encode(BD_MQTT_AUTH_PASSWORD, mqttAuthPassword);
//    }

    public String getWebRTCStunServer() {
        return bdPreferenceManager.getString(BD_WEBRTC_STUN_SERVER, webrtcStunServer);
    }

    public void setWebRTCStunServer(String stunServer) {
        bdPreferenceManager.saveSetting(BD_WEBRTC_STUN_SERVER, stunServer);
    }

    public String getWebRTCTurnServer() {
        return bdPreferenceManager.getString(BD_WEBRTC_TURN_SERVER, webrtcTurnServer);
    }

    public void setWebRTCTurnServer(String turnServer) {
        bdPreferenceManager.saveSetting(BD_WEBRTC_TURN_SERVER, turnServer);
    }

    public String getWebrtcTurnUsername() {
        return bdPreferenceManager.getString(BD_WEBRTC_TURN_USERNAME, webrtcTurnUsername);
    }

    public void setWebrtcTurnUsername(String webrtcStunUsername) {
        bdPreferenceManager.saveSetting(BD_WEBRTC_TURN_USERNAME, webrtcStunUsername);
    }

    public String getWebrtcTurnPassword() {
        return bdPreferenceManager.getString(BD_WEBRTC_TURN_PASSWORD, webrtcTurnPassword);
    }

    public void setWebrtcTurnPassword(String webrtcStunPassword) {
        bdPreferenceManager.saveSetting(BD_WEBRTC_TURN_PASSWORD, webrtcStunPassword);
    }

    public String getRestApiHost() {
        return bdPreferenceManager.getString(BD_REST_API_HOST, restApiHost);
//        return restApiHost;
    }

    public void setRestApiHost(String host) {
        bdPreferenceManager.saveSetting(BD_REST_API_HOST, host);
    }

    public String getUploadApiHost() {
        return bdPreferenceManager.getString(BD_UPLOAD_API_HOST, uploadApiHost);
    }

    public void setUploadApiHost(String host) {
        bdPreferenceManager.saveSetting(BD_UPLOAD_API_HOST, host);
    }

//    public String getRestApiHostIM() {
//        return mmkv.getString(BD_REST_API_HOST_IM, imRestApiHost);
////        return imRestApiHost;
//    }
//
//    public void setRestApiHostIM(String host) {
//        mmkv.encode(BD_REST_API_HOST_IM, host);
//    }
//
//    public String getRestApiHostKF() {
//        return mmkv.getString(BD_REST_API_HOST_KF, kfRestApiHost);
////        return kfRestApiHost;
//    }
//
//    public void setRestApiHostKF(String host) {
//        mmkv.encode(BD_REST_API_HOST_KF, host);
//    }

    /**
     * 切换到IM+KF服务器, 默认
     */
//    public void switchToONE() {
//        mmkv.encode(BD_REST_API_HOST, getRestApiHost());
//    }

    /**
     * 切换到IM服务器
     */
//    public void switchToIM() {
//        mmkv.encode(BD_REST_API_HOST, getRestApiHostIM());
//    }

    /**
     * 切换到KF服务器
     */
//    public void switchToKF() {
//        mmkv.encode(BD_REST_API_HOST, getRestApiHostKF());
//    }

    ///////////////////////////////////////

    public String getQRCodeBaseUrl() {
        return getRestApiHost();
    }

    public String getApiBaseUrl() {
        return getRestApiHost() + "api/";
    }

    public String getPassportOAuthTokenUrl() {
        return getRestApiHost() + "oauth/token";
    }

    public String getMobileOAuthTokenUrl() {
        return getRestApiHost() + "mobile/token";
    }

    public String getEmailOAuthTokenUrl() {
        return getRestApiHost() + "email/token";
    }

    public String getWeChatOAuthTokenUrl() {
        return getRestApiHost() + "wechat/token";
    }

    public String getMobileCodeUrl() {
        return getRestApiHost() + "sms/api/send";
    }

    public String getEmailCodeUrl() {
        return getRestApiHost() + "email/api/send";
    }

    public String getApiVisitorBaseUrl() {
        return getRestApiHost() + "visitor/api/";
    }

    public String getUploadApiVisitorBaseUrl() {
        return getUploadApiHost() + "visitor/api/";
    }

    public String getVisitorGenerateUsernameUrl() {
        return getApiVisitorBaseUrl() + "username";
    }

    public String getVisitorRegisterUserUrl() {
        return getApiVisitorBaseUrl() + "register/user";
    }

    public String getVisitorRegisterUserUidUrl() {
        return getApiVisitorBaseUrl() + "register/user/uid";
    }

    public String getVisitorRegisterAdminUrl() {
        return getApiVisitorBaseUrl() + "register";
    }

    public String getVisitorRegisterEmailUrl() {
        return getApiVisitorBaseUrl() + "register/email";
    }

    public String getVisitorRegisterMobileUrl() {
        return getApiVisitorBaseUrl() + "register/mobile";
    }

    public String getVisitorBindMobileUrl() {
        return getApiVisitorBaseUrl() + "bind/mobile";
    }

    public String getVisitorIsWeChatRegisteredUrl() {
        return getApiVisitorBaseUrl() + "is/wechat/registered";
    }

    public String getVisitorBindWeChatUrl() {
        return getApiVisitorBaseUrl() + "bind/wechat";
    }

    public String getVisitorChatCode() {
        return getApiVisitorBaseUrl() + "wechatUrl";
    }

    public String getVisitorRegisterWeChatUrl() {
        return getApiVisitorBaseUrl() + "register/wechat";
    }

    public String getVisitorTicketCategoriesUrl() {
        return getApiVisitorBaseUrl() + "category/ticket";
    }

    public String getVisitorMineTicketUrl() {
        return getApiBaseUrl() + "ticket/mine";
    }

    public String getCreateTicketUrl() {
        return getApiBaseUrl() + "ticket/create";
    }

    public String getVisitorFeedbackCategoriesUrl() {
        return getApiVisitorBaseUrl() + "category/feedback";
    }

    public String getVisitorMineFeedbackUrl() {
        return getApiBaseUrl() + "feedback/mine";
    }

    public String getCreateFeedbackUrl() {
        return getApiBaseUrl() + "feedback/create";
    }

    public String getVisitorSupportCategoriesUrl() {
        return getApiVisitorBaseUrl() + "category/support";
    }

    public String getVisitorArticlesUrl() {
        return getApiVisitorBaseUrl() + "articles";
    }

    public String getUploadAvatarUrl() {
        return getUploadApiVisitorBaseUrl() + "upload/avatar";
    }

    public String getUploadImageUrl() {
        return getUploadApiVisitorBaseUrl() + "upload/image";
    }

    public String getUploadVoiceUrl() {
        return getUploadApiVisitorBaseUrl() + "upload/voice";
    }

    public String getUploadFileUrl() {
        return getUploadApiVisitorBaseUrl() + "upload/file";
    }

    public String getUploadVideoUrl() {
        return getUploadApiVisitorBaseUrl() + "upload/video";
    }

    public String getUploadWechatDbUrl() {
        return getUploadApiVisitorBaseUrl() + "upload/wechatdb";
    }


}
