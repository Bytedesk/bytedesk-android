package com.bytedesk.im.core.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 *
 * @author bytedesk.com
 */
@Entity(tableName = "thread", indices = {@Index("topic")})
public class ThreadEntity {

//    /**
//     * TODO: 去除服务器端消息id，替换
//     */
//    @PrimaryKey
//    @ColumnInfo(name = "id")
//    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @ColumnInfo(name = "uid")
    private String uid;

    /**
     * topic
     */
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "topic")
    private String topic;

    /**
     * 会话类型: 工作组会话、指定客服、同事一对一、群组会话
     */
    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "extra")
    private String extra;

    /**
     * 访客UID
     */
    @ColumnInfo(name = "user_uid")
    private String userUid;

    /**
     * 昵称
     */
    @ColumnInfo(name = "user_nickname")
    private String userNickname;

    /**
     * 头像
     */
    @ColumnInfo(name = "user_avatar")
    private String userAvatar;


    public ThreadEntity() {
        topic = "";
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

    public void setUid(String tid) {
        this.uid = tid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getExtra() {
        return extra;
    }
    public void setExtra(String extra) {
        this.extra = extra;
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
        return userAvatar;
    }
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }


    @NonNull
    @Override
    public String toString() {
        return "ThreadEntity{" +
                "Uid='" + uid + '\'' +
                ", type='" + type + '\'' +
                ", topic='" + topic + '\'' +
                ", status='" + status + '\'' +
                ", extra='" + extra + '\'' +
                ", userUid='" + userUid + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                '}';
    }
}
