package com.bytedesk.im.core.room.entity;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.bytedesk.im.core.api.BytedeskConstants;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <a href="https://developer.android.com/topic/libraries/architecture/room.html#entities">...</a>
 * // @Index(value = {"msgid"}, unique = true),
 * @author bytedesk.com
 */
@Entity(tableName = "message", indices = {@Index("current_uid")})
public class MessageEntity {

//    /**
//     * 服务器消息id
//     * TODO: 去除服务器端消息id，替换
//     */
//    @PrimaryKey
//    public Long id;

    /**
     * 客户端本地id
     */
    @ColumnInfo(name = "local_id")
    private String localId;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "uid")
    public String uid;

    /**
     * 消息类型
     */
    @ColumnInfo(name = "by_type")
    private String type;

    /**
     * 来源客户端
     */
    @ColumnInfo(name = "client")
    private String client;

    @ColumnInfo(name = "extra")
    private String extra;

    /**
     * 文本消息
     * FIXME: 长度不够，待调整
     */
    @ColumnInfo(name = "content")
    private String content;

    /**
     * 消息创建时间
     */
    @ColumnInfo(name = "created_at")
    private String createdAt;

    /**
     * 消息发送状态
     */
    @ColumnInfo(name = "status")
    private String status;

    /**
     * 消息所属会话tid
     */
    @ColumnInfo(name = "thread_topic")
    private String threadTopic;

    /**
     * 消息对应会话的visitor uid
     */
    @ColumnInfo(name = "user_uid")
    private String userUid;

    @ColumnInfo(name = "user_nickname")
    private String userNickname;

    @ColumnInfo(name = "user_avatar")
    private String userAvatar;

    /**
     * 当前登录用户Uid
     */
    @ColumnInfo(name = "current_uid")
    private String currentUid;

    public MessageEntity() {
        uid = "";
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getExtra() {
        return extra;
    }
    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getThreadTopic() {
        return threadTopic;
    }

    public void setThreadTopic(String threadTopic) {
        this.threadTopic = threadTopic;
    }

    public String getUserUid() {
        return userUid;
    }
    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
    public String getUserNickname() {
        return userNickname;
    }
    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
    public String getUserAvatar() {
        if (TextUtils.isEmpty(userAvatar)) {
            return "https://www.weiyuai.cn/logo.png";
        }
        if (userAvatar.contains("127.0.0.1")) {
            return userAvatar.replace("127.0.0.1", "10.0.2.2");
        }
        return userAvatar;
    }
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    // 暂时不用枚举Enum
    // https://developer.android.com/topic/performance/memory.html#Overhead
    // You should strictly avoid using enums on Android.
    public static final int TYPE_TEXT_ID =  0;
    public static final int TYPE_TEXT_SELF_ID =  1;
    public static final int TYPE_IMAGE_ID = 2;
    public static final int TYPE_IMAGE_SELF_ID = 3;
    public static final int TYPE_VOICE_ID = 4;
    public static final int TYPE_VOICE_SELF_ID = 5;
    public static final int TYPE_VIDEO_ID = 6;
    public static final int TYPE_VIDEO_SELF_ID = 7;
    public static final int TYPE_SHORT_VIDEO_ID = 8;
    public static final int TYPE_SHORT_VIDEO_SELF_ID = 9;
    public static final int TYPE_LOCATION_ID = 10;
    public static final int TYPE_LOCATION_SELF_ID = 11;
    public static final int TYPE_LINK_ID = 12;
    public static final int TYPE_LINK_SELF_ID = 13;
    public static final int TYPE_EVENT_ID = 14;
    public static final int TYPE_EVENT_SELF_ID = 15;
    //
    public static final int TYPE_QUESTIONNAIRE_ID = 16;
    public static final int TYPE_NOTIFICATION_ID = 17;
    public static final int TYPE_COMMODITY_ID = 18;
    //
    public static final int TYPE_RED_PACKET_ID = 19;
    public static final int TYPE_RED_PACKET_SELF_ID = 20;
    //
    public static final int TYPE_FILE_ID =  21;
    public static final int TYPE_FILE_SELF_ID =  22;
    //
    public static final int TYPE_ROBOT_ID =  23;
    public static final int TYPE_ROBOT_SELF_ID =  24;
    //
    public static final int TYPE_CUSTOM_ID = 25;
    //

    //
    public int getTypeId() {
        if (this.type.equals(BytedeskConstants.MESSAGE_TYPE_SYSTEM)
                || this.type.equals(BytedeskConstants.MESSAGE_TYPE_CONTINUE)) {
            return TYPE_NOTIFICATION_ID;
        }
        else if (this.type.equals(BytedeskConstants.MESSAGE_TYPE_WELCOME) ||
                this.type.equals(BytedeskConstants.MESSAGE_TYPE_TEXT)) {
            if (isSend())
                return TYPE_TEXT_SELF_ID;
            return TYPE_TEXT_ID;
        }
        else if (this.type.equals(BytedeskConstants.MESSAGE_TYPE_IMAGE)) {
            if (isSend())
                return TYPE_IMAGE_SELF_ID;
            return TYPE_IMAGE_ID;
        }
        else if (this.type.equals(BytedeskConstants.MESSAGE_TYPE_AUDIO)) {
            if (isSend())
                return TYPE_VOICE_SELF_ID;
            return TYPE_VOICE_ID;
        }
        else if (this.type.equals(BytedeskConstants.MESSAGE_TYPE_VIDEO)) {
            if (isSend())
                return TYPE_VIDEO_SELF_ID;
            return TYPE_VIDEO_ID;
        }
        else if (this.type.equals(BytedeskConstants.MESSAGE_TYPE_LOCATION)) {
            if (isSend())
                return TYPE_LOCATION_SELF_ID;
            return TYPE_LOCATION_ID;
        }
        else if (this.type.equals(BytedeskConstants.MESSAGE_TYPE_EVENT)) {
            if (isSend())
                return TYPE_EVENT_SELF_ID;
            return TYPE_EVENT_ID;
        }
        else if (this.type.equals(BytedeskConstants.MESSAGE_TYPE_FILE)) {
            if (isSend())
                return TYPE_FILE_SELF_ID;
            return TYPE_FILE_ID;
        } else if (this.type.equals(BytedeskConstants.MESSAGE_TYPE_ROBOT)) {
            if (isSend())
                return TYPE_ROBOT_SELF_ID;
            return TYPE_ROBOT_ID;
        }

        return TYPE_NOTIFICATION_ID;
    }

    /**
     * 判断消息是否为当前登录用户发送
     *
     * @return
     */
    public boolean isSend() {
        if (null != currentUid)
            return currentUid.equals(userUid);
        return false;
    }

    public static MessageEntity fromJson(JSONObject message, String currentUid) {
        //
        MessageEntity messageEntity = new MessageEntity();
        try {
            messageEntity.setUid(message.getString("uid"));
            messageEntity.setLocalId(message.getString("uid"));
            messageEntity.setType(message.getString("type"));
            messageEntity.setContent(message.getString("content"));
            messageEntity.setStatus(message.getString("status"));
            messageEntity.setCreatedAt(message.getString("createdAt"));
            messageEntity.setClient(message.getString("client"));
            messageEntity.setExtra(message.getString("extra"));
            //
            String threadTopic = message.getJSONObject("thread").getString("topic");
            messageEntity.setThreadTopic(threadTopic);
            //
            messageEntity.setUserUid(message.getJSONObject("user").getString("uid"));
            messageEntity.setUserNickname(message.getJSONObject("user").getString("nickname"));
            messageEntity.setUserAvatar(message.getJSONObject("user").getString("avatar"));
            messageEntity.setCurrentUid(currentUid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.i("fromJson %s", messageEntity.toString());
        return messageEntity;
    }

    @NonNull
    @Override
    public String toString() {
        return "MessageEntity{" +
                "Uid='" + uid + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", threadTopic='" + threadTopic + '\'' +
                ", userUid='" + userUid + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                '}';
    }

}

