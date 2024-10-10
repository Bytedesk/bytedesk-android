package com.bytedesk.im.core.util;

/**
 * @author bytedesk.com on 2019/4/1
 */
public class JsonWebRTC {

    /**
     * 当前客户端clientId
     */
    private String clientId;

    /**
     * 消息本地Id
     */
    private String localId;

    /**
     * 会话tid、用户uid 或者 群组gid
     */
    private String uuid;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 会话类型
     */
    private String sessionType;

    /**
     * ice candidate or sdp
     */
    private String content;

    /**
     * 版本
     */
    private String version;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
