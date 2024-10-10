package com.bytedesk.im.core.service.mqtt.model;

import android.content.Context;

import com.bytedesk.im.core.api.BDConfig;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class ConnectionModel {

    private MqttConnectOptions mqttConnectOptions;
    private Context mContext;
//    private BDPreferenceManager preferenceManager;
//    private String clientHandle;

    // 每个客户端唯一标识
    private String clientId;
    private String serverHostName;
    private int serverPort;
    private boolean cleanSession = false;
//    private String username;
//    private String password;

//    private boolean tlsConnection = true;
//    private String tlsServerKey;
//    private String tlsClientKey;

    private boolean autoReconnect = true;
    // FIXME: 设置自动重连，但对于同一账号被踢掉线的情况，无法处理，故暂且设置其未false
    // private boolean autoReconnect = false;
    private int timeout = 80;
    // 10秒钟
    private int keepAlive = 10;

    // 遗愿or遗嘱，如果客户端异常地断开连接，代理程序(the broker)将会广播 LWT 消息到所有订阅者的客户端中。
//    private boolean lwtFlag = true;
//    private String lwtTopic = BDCoreConstant.BD_MQTT_TOPIC_LASTWILL;
//    private String lwtMessage = BDCoreConstant.USER_STATUS_DISCONNECTED;
    // 注意：只能设置为0或者1，不能设置为2，否则lastWill不起作用
//    private int lwtQos = 0;
//    private boolean lwtRetain = true;

    private static ConnectionModel connectionModel = null;

    private ConnectionModel(Context context) {
//        clientHandle = "";
        clientId = "";
        serverPort = BDConfig.getInstance(context).getMqttPort();
        serverHostName = BDConfig.getInstance(context).getMqttHost();
//        username = BDConfig.getInstance().getMqttAuthUsername();
//        password = BDConfig.getInstance().getMqttAuthPassword();
//        tlsServerKey = "";
//        tlsClientKey = "";
        mqttConnectOptions = new MqttConnectOptions();
        mContext = context;
//        preferenceManager = BDPreferenceManager.getInstance(context);
    }

    public static ConnectionModel getInstance(Context context) {
        if (null == connectionModel) {
            connectionModel = new ConnectionModel(context);
        }
        return connectionModel;
    }

    public String getUri() {
        String uri;
        if (BDConfig.IS_WEBSOCKET_WSS_CONNECTION) {
            uri = BDConfig.getInstance(mContext).getMqttWebSocketWssURL();
        }else if(BDConfig.IS_TLS_CONNECTION) {
            uri = "ssl://" + serverHostName + ":" + serverPort;
        } else {
            uri = "tcp://" + serverHostName + ":" + serverPort;
        }
        return uri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MqttConnectOptions getMqttConnectOptions() {

        mqttConnectOptions.setAutomaticReconnect(autoReconnect);
        mqttConnectOptions.setCleanSession(cleanSession);
        mqttConnectOptions.setConnectionTimeout(timeout);
        mqttConnectOptions.setKeepAliveInterval(keepAlive);

//        if(!username.equals(BDCoreConstant.empty)){
//            mqttConnectOptions.setUserName(username);
//        }
//
//        if(!password.equals(BDCoreConstant.empty)){
//            mqttConnectOptions.setPassword(password.toCharArray());
//        }

//        if(!lwtTopic.equals(BDCoreConstant.empty) && !getLwtMessage().equals(BDCoreConstant.empty)){
//            mqttConnectOptions.setWill(lwtTopic, getLwtMessage().getBytes(), lwtQos, lwtRetain);
//        }

        return mqttConnectOptions;
    }


//    public String getClientHandle() {
//        return clientHandle;
//    }
//
//    public void setClientHandle(String clientHandle) {
//        this.clientHandle = clientHandle;
//    }

//    public String getServerHostName() {
//        // 需要用10.0.2.2代替localhost
//        return serverHostName;
//    }

//    public void setServerHostName(String serverHostName) {
//        this.serverHostName = serverHostName;
//    }
//
//    public int getServerPort() {
//        return serverPort;
//    }
//
//    public void setServerPort(int serverPort) {
//        this.serverPort = serverPort;
//    }
//
//    public boolean isCleanSession() {
//        return cleanSession;
//    }
//
//    public void setCleanSession(boolean cleanSession) {
//        this.cleanSession = cleanSession;
//    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

//    public String getTlsServerKey() {
//        return tlsServerKey;
//    }
//
//    public void setTlsServerKey(String tlsServerKey) {
//        this.tlsServerKey = tlsServerKey;
//    }
//
//    public String getTlsClientKey() {
//        return tlsClientKey;
//    }
//
//    public void setTlsClientKey(String tlsClientKey) {
//        this.tlsClientKey = tlsClientKey;
//    }
//
//    public int getTimeout() {
//        return timeout;
//    }
//
//    public void setTimeout(int timeout) {
//        this.timeout = timeout;
//    }
//
//    public int getKeepAlive() {
//        return keepAlive;
//    }
//
//    public void setKeepAlive(int keepAlive) {
//        this.keepAlive = keepAlive;
//    }
//
//    public boolean isLwtFlag() {
//        return lwtFlag;
//    }
//
//    public void setLwtFlag(boolean lwtFlag) {
//        this.lwtFlag = lwtFlag;
//    }

//    public String getLwtTopic() {
//        return lwtTopic;
//    }
//
//    public void setLwtTopic(String lwtTopic) {
//        this.lwtTopic = lwtTopic;
//    }

//    public String getLwtMessage() {
//
//        JsonMessage jsonMessage = new JsonMessage();
////        jsonMessage.setTid(tId);
////        jsonMessage.setLocalId(localId);
//        jsonMessage.setType(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_ONLINE_STATUS);
//        jsonMessage.setClient(BDCoreConstant.CLIENT_ANDROID);
////        jsonMessage.setContent(content);
//        jsonMessage.setStatus(lwtMessage);
////        jsonMessage.setUsername(mPreferenceManager.getUsername());
//        jsonMessage.setClientId(clientId);
////        jsonMessage.setSessionType(sessionType);
//        jsonMessage.setVersion("2");
//
//        return new Gson().toJson(jsonMessage);
//    }
//
//    public void setLwtMessage(String lwtMessage) {
//        this.lwtMessage = lwtMessage;
//    }
//
//    public int getLwtQos() {
//        return lwtQos;
//    }
//
//    public void setLwtQos(int lwtQos) {
//        this.lwtQos = lwtQos;
//    }
//
//    public boolean isLwtRetain() {
//        return lwtRetain;
//    }
//
//    public void setLwtRetain(boolean lwtRetain) {
//        this.lwtRetain = lwtRetain;
//    }
//
//    public boolean isTlsConnection() {
//        return tlsConnection;
//    }
//
//    public void setTlsConnection(boolean tlsConnection) {
//        this.tlsConnection = tlsConnection;
//    }



//    public void setMqttConnectOptions(MqttConnectOptions mqttConnectOptions) {
//        this.mqttConnectOptions = mqttConnectOptions;
//    }

//    @Override
//    public String toString() {
//        return "ConnectionModel{" +
//                "clientHandle='" + clientHandle + '\'' +
//                ", clientId='" + clientId + '\'' +
//                ", serverHostName='" + serverHostName + '\'' +
//                ", serverPort=" + serverPort +
////                ", cleanSession=" + cleanSession +
////                ", username='" + username + '\'' +
////                ", password='" + password + '\'' +
//                ", tlsConnection=" + tlsConnection +
//                ", tlsServerKey='" + tlsServerKey + '\'' +
//                ", tlsClientKey='" + tlsClientKey + '\'' +
//                ", timeout=" + timeout +
//                ", keepAlive=" + keepAlive +
////                ", lwtTopic='" + lwtTopic + '\'' +
////                ", lwtMessage='" + lwtMessage + '\'' +
//                ", lwtQos=" + lwtQos +
//                ", lwtRetain=" + lwtRetain +
//                '}';
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        ConnectionModel that = (ConnectionModel) o;
//
//        if (serverPort != that.serverPort) return false;
//        if (cleanSession != that.cleanSession) return false;
//        if (tlsConnection != that.tlsConnection) return false;
//        if (timeout != that.timeout) return false;
//        if (keepAlive != that.keepAlive) return false;
//        if (lwtQos != that.lwtQos) return false;
//        if (lwtRetain != that.lwtRetain) return false;
////
//        if (clientHandle != null ? !clientHandle.equals(that.clientHandle) : that.clientHandle != null)
//            return false;
//        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null)
//            return false;
//        if (serverHostName != null ? !serverHostName.equals(that.serverHostName) : that.serverHostName != null)
//            return false;
////        if (username != null ? !username.equals(that.username) : that.username != null)
////            return false;
////        if (password != null ? !password.equals(that.password) : that.password != null)
////            return false;
//        if (tlsServerKey != null ? !tlsServerKey.equals(that.tlsServerKey) : that.tlsServerKey != null)
//            return false;
//        if (tlsClientKey != null ? !tlsClientKey.equals(that.tlsClientKey) : that.tlsClientKey != null)
//            return false;
////        if (lwtTopic != null ? !lwtTopic.equals(that.lwtTopic) : that.lwtTopic != null)
////            return false;
////        return !(lwtMessage != null ? !lwtMessage.equals(that.lwtMessage) : that.lwtMessage != null);
//
//        return true;
//    }

//    @Override
//    public int hashCode() {
//        int result = clientHandle != null ? clientHandle.hashCode() : 0;
//        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
//        result = 31 * result + (serverHostName != null ? serverHostName.hashCode() : 0);
//        result = 31 * result + serverPort;
//        result = 31 * result + (cleanSession ? 1 : 0);
////        result = 31 * result + (username != null ? username.hashCode() : 0);
////        result = 31 * result + (password != null ? password.hashCode() : 0);
//        result = 31 * result + (tlsConnection ? 1 : 0);
//        result = 31 * result + (tlsServerKey != null ? tlsServerKey.hashCode() : 0);
//        result = 31 * result + (tlsClientKey != null ? tlsClientKey.hashCode() : 0);
//        result = 31 * result + timeout;
//        result = 31 * result + keepAlive;
////        result = 31 * result + (lwtTopic != null ? lwtTopic.hashCode() : 0);
////        result = 31 * result + (lwtMessage != null ? lwtMessage.hashCode() : 0);
//        result = 31 * result + lwtQos;
//        result = 31 * result + (lwtRetain ? 1 : 0);
//        return result;
//    }


}
