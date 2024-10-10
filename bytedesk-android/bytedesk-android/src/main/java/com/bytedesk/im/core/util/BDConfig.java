//package com.bytedesk.im.core.api;
//
//import android.content.Context;
//
//import com.bytedesk.im.core.util.BDPreferenceManager;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * TODO: 所有配置后期均修改为动态从服务器获取
// *
// * @author bytedesk.com on 2019/3/27
// */
//public class BDConfig {
//
//    public final String BD_MQTT_PORT = "BD_MQTT_PORT";
//    public final String BD_MQTT_HOST = "BD_MQTT_HOST";
//    public final String BD_MQTT_AUTH_USERNAME = "BD_MQTT_AUTH_USERNAME";
//    public final String BD_MQTT_AUTH_PASSWORD = "BD_MQTT_AUTH_PASSWORD";
//    public final String BD_WEBRTC_STUN_SERVER = "BD_WEBRTC_STUN_SERVER";
//    public final String BD_WEBRTC_TURN_SERVER = "BD_WEBRTC_TURN_SERVER";
//    public final String BD_WEBRTC_TURN_USERNAME = "BD_WEBRTC_TURN_USERNAME";
//    public final String BD_WEBRTC_TURN_PASSWORD = "BD_WEBRTC_TURN_PASSWORD";
//    public final String BD_REST_API_HOST = "BD_REST_API_HOST";
//    public final String BD_REST_API_HOST_IM = "BD_REST_API_HOST_IM";
//    public final String BD_REST_API_HOST_KF = "BD_REST_API_HOST_KF";
//
//    private static BDConfig bdConfig = null;
//    private BDPreferenceManager preferenceManager;
//
//    /**
//     * 消息服务器用户名
//     */
//    private static String mqttAuthUsername = "mqtt_android";
//    /**
//     * 消息服务器密码
//     */
//    private static String mqttAuthPassword = "mqtt_android";
//
////    D/RoomRTCClient: TURN response: {
////        "lifetimeDuration": "86400s",
////                "iceServers": [
////        {
////            "urls": [
////            "stun:108.177.98.127:19302",
////                    "stun:[2607:f8b0:400e:c06::7f]:19302"
////          ]
////        },
////        {
////            "urls": [
////            "turn:173.194.202.127:19305?transport=udp",
////                    "turn:[2607:f8b0:400e:c00::7f]:19305?transport=udp",
////                    "turn:173.194.202.127:19305?transport=tcp",
////                    "turn:[2607:f8b0:400e:c00::7f]:19305?transport=tcp"
////          ],
////            "username": "CNnGhOYFEgZvaJ17wxYYzc/s6OMTIICjBQ",
////                "credential": "rgh2pc3mvBKsZvCgWzmUq1i9cL4=",
////                "maxRateKbps": "8000"
////        }
////      ],
////        "blockStatus": "NOT_BLOCKED",
////                "iceTransportPolicy": "all"
////    }
//
//    // TODO: 后期扩展为数组，设置多个
//    private static List<String> webrtcStunServerList = new ArrayList<>();
//    private static String webrtcStunServer = "turn:stun.bytedesk.com";
//    private static String webrtcTurnServer = "turn:stun.bytedesk.com";
//    private static String webrtcTurnUsername = "jackning";
//    private static String webrtcTurnPassword = "123456";
//
//    /**
//     * 1. 消息服务器地址
//     * 2. REST API 接口地址
//     */
//    // 线上服务器地址
////    private static int LONG_LINK_PORT = 3883;
////    private static String LONG_LINK_HOST = "mqtt.bytedesk.com";
////    private static String restApiHost = "https://api.bytedesk.com/";
////    private static String imRestApiHost = "https://api.bytedesk.com/";
////    private static String kfRestApiHost = "https://api.bytedesk.com/";
//
//    // 真机调试连接localhost/10.0.2.2：1. 真机与电脑连接同一个wifi；2. ifconfig查看电脑ip；3. 修改为上述ip
//    private static int LONG_LINK_PORT = 3883;
//    private static String SHORT_LINK_HOST = "192.168.0.103";
////    private static String SHORT_LINK_HOST = "10.0.2.2";
//    private static String LONG_LINK_HOST = SHORT_LINK_HOST;
//    private static String restApiHost = "http://" + SHORT_LINK_HOST + ":8000/";
//    private static String imRestApiHost = "http://" + SHORT_LINK_HOST + ":8000/";
//    private static String kfRestApiHost = "http://" + SHORT_LINK_HOST + ":8000/";
//
//    //
//    private BDConfig(Context context) {
//        preferenceManager = BDPreferenceManager.getInstance(context);
//    }
//
//    public static BDConfig getInstance(Context context) {
//        if (bdConfig == null) {
//            bdConfig = new BDConfig(context);
//        }
//        return bdConfig;
//    }
//
//    /**
//     * 恢复默认值
//     */
//    public void restoreDefault() {
//        preferenceManager.saveSetting(BD_MQTT_HOST, LONG_LINK_HOST);
//        preferenceManager.saveSetting(BD_MQTT_PORT, LONG_LINK_PORT);
//        preferenceManager.saveSetting(BD_MQTT_AUTH_USERNAME, mqttAuthUsername);
//        preferenceManager.saveSetting(BD_MQTT_AUTH_PASSWORD, mqttAuthPassword);
//        preferenceManager.saveSetting(BD_WEBRTC_STUN_SERVER, webrtcStunServer);
//        preferenceManager.saveSetting(BD_WEBRTC_TURN_SERVER, webrtcTurnServer);
//        preferenceManager.saveSetting(BD_WEBRTC_TURN_USERNAME, webrtcTurnUsername);
//        preferenceManager.saveSetting(BD_WEBRTC_TURN_PASSWORD, webrtcTurnPassword);
//        preferenceManager.saveSetting(BD_REST_API_HOST, restApiHost);
//        preferenceManager.saveSetting(BD_REST_API_HOST_IM, imRestApiHost);
//        preferenceManager.saveSetting(BD_REST_API_HOST_KF, kfRestApiHost);
//    }
//
//    /**
//     * 本地测试，需要本地部署服务器
//     */
//    public void enableLocalHost() {
//        preferenceManager.saveSetting(BD_MQTT_HOST, "10.0.2.2");
//        preferenceManager.saveSetting(BD_MQTT_PORT, LONG_LINK_PORT);
//        preferenceManager.saveSetting(BD_MQTT_AUTH_USERNAME, mqttAuthUsername);
//        preferenceManager.saveSetting(BD_MQTT_AUTH_PASSWORD, mqttAuthPassword);
//        preferenceManager.saveSetting(BD_WEBRTC_STUN_SERVER, webrtcStunServer);
//        preferenceManager.saveSetting(BD_WEBRTC_TURN_SERVER, webrtcTurnServer);
//        preferenceManager.saveSetting(BD_WEBRTC_TURN_USERNAME, webrtcTurnUsername);
//        preferenceManager.saveSetting(BD_WEBRTC_TURN_PASSWORD, webrtcTurnPassword);
//        preferenceManager.saveSetting(BD_REST_API_HOST, "http://10.0.2.2:8000/");
//        preferenceManager.saveSetting(BD_REST_API_HOST_IM, "http://10.0.2.2:8000/");
//        preferenceManager.saveSetting(BD_REST_API_HOST_KF, "http://10.0.2.2:8000/");
//    }
//
//    public String getMqttHost() {
//        return preferenceManager.getString(BD_MQTT_HOST, LONG_LINK_HOST);
//    }
//
//    public void setMqttHost(String SHORT_LINK_HOST) {
//        preferenceManager.saveSetting(BD_MQTT_HOST, SHORT_LINK_HOST);
//    }
//
//    public int getMqttPort() {
//        return preferenceManager.getInt(BD_MQTT_PORT, LONG_LINK_PORT);
//    }
//
//    public void setMqttPort(int port) {
//        preferenceManager.saveSetting(BD_MQTT_PORT, port);
//    }
//
//    public String getMqttAuthUsername() {
//        return preferenceManager.getString(BD_MQTT_AUTH_USERNAME, mqttAuthUsername);
//    }
//
//    public void setMqttAuthUsername(String mqttAuthUsername) {
//        preferenceManager.saveSetting(BD_MQTT_AUTH_USERNAME, mqttAuthUsername);
//    }
//
//    public String getMqttAuthPassword() {
//        return preferenceManager.getString(BD_MQTT_AUTH_PASSWORD, mqttAuthPassword);
//    }
//
//    public void setMqttAuthPassword(String mqttAuthPassword) {
//        preferenceManager.saveSetting(BD_MQTT_AUTH_PASSWORD, mqttAuthPassword);
//    }
//
//    public String getWebRTCStunServer() {
//        return preferenceManager.getString(BD_WEBRTC_STUN_SERVER, webrtcStunServer);
//    }
//
//    public void setWebRTCStunServer(String stunServer) {
//        preferenceManager.saveSetting(BD_WEBRTC_STUN_SERVER, stunServer);
//    }
//
//    public String getWebRTCTurnServer() {
//        return preferenceManager.getString(BD_WEBRTC_TURN_SERVER, webrtcTurnServer);
//    }
//
//    public void setWebRTCTurnServer(String turnServer) {
//        preferenceManager.saveSetting(BD_WEBRTC_TURN_SERVER, turnServer);
//    }
//
//    public String getWebrtcTurnUsername() {
//        return preferenceManager.getString(BD_WEBRTC_TURN_USERNAME, webrtcTurnUsername);
//    }
//
//    public void setWebrtcTurnUsername(String webrtcStunUsername) {
//        preferenceManager.saveSetting(BD_WEBRTC_TURN_USERNAME, webrtcStunUsername);
//    }
//
//    public String getWebrtcTurnPassword() {
//        return preferenceManager.getString(BD_WEBRTC_TURN_PASSWORD, webrtcTurnPassword);
//    }
//
//    public void setWebrtcTurnPassword(String webrtcStunPassword) {
//        preferenceManager.saveSetting(BD_WEBRTC_TURN_PASSWORD, webrtcStunPassword);
//    }
//
//    public String getRestApiHost() {
//        return preferenceManager.getString(BD_REST_API_HOST, restApiHost);
//    }
//
//    public void setRestApiHost(String SHORT_LINK_HOST) {
//        preferenceManager.saveSetting(BD_REST_API_HOST, SHORT_LINK_HOST);
//    }
//
//    public String getRestApiHostIM() {
//        return preferenceManager.getString(BD_REST_API_HOST_IM, imRestApiHost);
//    }
//
//
//
//    public void setRestApiHostIM(String SHORT_LINK_HOST) {
//        preferenceManager.saveSetting(BD_REST_API_HOST_IM, SHORT_LINK_HOST);
//    }
//
//    public String getRestApiHostKF() {
//        return preferenceManager.getString(BD_REST_API_HOST_KF, kfRestApiHost);
//    }
//
//    public void setRestApiHostKF(String SHORT_LINK_HOST) {
//        preferenceManager.saveSetting(BD_REST_API_HOST_KF, SHORT_LINK_HOST);
//    }
//
//    /**
//     * 切换到IM+KF服务器, 默认
//     */
//    public void switchToONE() {
//        preferenceManager.saveSetting(BD_REST_API_HOST, getRestApiHost());
//    }
//
//    /**
//     * 切换到IM服务器
//     */
//    public void switchToIM() {
//        preferenceManager.saveSetting(BD_REST_API_HOST, getRestApiHostIM());
//    }
//
//    /**
//     * 切换到KF服务器
//     */
//    public void switchToKF() {
//        preferenceManager.saveSetting(BD_REST_API_HOST, getRestApiHostKF());
//    }
//
//    ///////////////////////////////////////
//
//    public String getQRCodeBaseUrl() {
//        return getRestApiHost();
//    }
//
//    public String getApiBaseUrl() {
//        return getRestApiHost() + "api/";
//    }
//
//    public String getPassportOAuthTokenUrl() {
//        return getRestApiHost() + "oauth/token";
//    }
//
//    public String getMobileOAuthTokenUrl() {
//        return getRestApiHost() + "mobile/token";
//    }
//
//    public String getEmailOAuthTokenUrl() {
//        return getRestApiHost() + "email/token";
//    }
//
//    public String getWeChatOAuthTokenUrl() {
//        return getRestApiHost() + "wechat/token";
//    }
//
//    public String getMobileCodeUrl() {
//        return getRestApiHost() + "sms/api/send";
//    }
//
//    public String getEmailCodeUrl() {
//        return getRestApiHost() + "email/api/send";
//    }
//
//    public String getApiVisitorBaseUrl() {
//        return getRestApiHost() + "visitor/api/";
//    }
//
//    public String getVisitorGenerateUsernameUrl() {
//        return getApiVisitorBaseUrl() + "username";
//    }
//
//    public String getVisitorRegisterUserUrl() {
//        return getApiVisitorBaseUrl() + "register/user";
//    }
//
//    public String getVisitorRegisterUserUidUrl() {
//        return getApiVisitorBaseUrl() + "register/user/uid";
//    }
//
//    public String getVisitorRegisterAdminUrl() {
//        return getApiVisitorBaseUrl() + "register";
//    }
//
//    public String getVisitorRegisterEmailUrl() {
//        return getApiVisitorBaseUrl() + "register/email";
//    }
//
//    public String getVisitorRegisterMobileUrl() {
//        return getApiVisitorBaseUrl() + "register/mobile";
//    }
//
//    public String getVisitorBindMobileUrl() {
//        return getApiVisitorBaseUrl() + "bind/mobile";
//    }
//
//    public String getVisitorIsWeChatRegisteredUrl() {
//        return getApiVisitorBaseUrl() + "is/wechat/registered";
//    }
//
//    public String getVisitorBindWeChatUrl() {
//        return getApiVisitorBaseUrl() + "bind/wechat";
//    }
//
//    public String getVisitorRegisterWeChatUrl() {
//        return getApiVisitorBaseUrl() + "register/wechat";
//    }
//
//    public String getVisitorTicketCategoriesUrl() {
//        return getApiVisitorBaseUrl() + "category/ticket";
//    }
//
//    public String getVisitorMineTicketUrl() {
//        return getApiBaseUrl() + "ticket/mine";
//    }
//
//    public String getCreateTicketUrl() {
//        return getApiBaseUrl() + "ticket/create";
//    }
//
//    public String getVisitorFeedbackCategoriesUrl() {
//        return getApiVisitorBaseUrl() + "category/feedback";
//    }
//
//    public String getVisitorMineFeedbackUrl() {
//        return getApiBaseUrl() + "feedback/mine";
//    }
//
//    public String getCreateFeedbackUrl() {
//        return getApiBaseUrl() + "feedback/create";
//    }
//
//    public String getVisitorSupportCategoriesUrl() {
//        return getApiVisitorBaseUrl() + "category/support";
//    }
//
//    public String getVisitorArticlesUrl() {
//        return getApiVisitorBaseUrl() + "articles";
//    }
//
//    public String getUploadAvatarUrl() {
//        return getApiVisitorBaseUrl() + "upload/avatar";
//    }
//
//    public String getUploadImageUrl() {
//        return getApiVisitorBaseUrl() + "upload/image";
//    }
//
//    public String getUploadVoiceUrl() {
//        return getApiVisitorBaseUrl() + "upload/voice";
//    }
//
//    public String getUploadFileUrl() {
//        return getApiVisitorBaseUrl() + "upload/file";
//    }
//
//    public String getUploadWechatDbUrl() {
//        return getApiVisitorBaseUrl() + "upload/wechatdb";
//    }
//
//
//}
