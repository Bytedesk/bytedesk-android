package com.bytedesk.core.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "groups", indices = {@Index("current_uid")})
public class GroupEntity {


    /**
     * TODO: 去除服务器端消息id，替换
     */
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @ColumnInfo(name = "gid")
    private String gid;

    /**
     * 群昵称
     */
    @ColumnInfo(name = "nickname")
    private String nickname;

    /**
     * 头像
     */
    @ColumnInfo(name = "avatar")
    private String avatar;

    /**
     * 类型：群、讨论组
     */
    @ColumnInfo(name = "by_type")
    private String type;

    /**
     * 当前成员数
     */
    @ColumnInfo(name = "member_count")
    private int memberCount;

    /**
     * 群简介
     */
    @ColumnInfo(name = "description")
    private String description;

    /**
     * 群公告
     */
    @ColumnInfo(name = "announcement")
    private String announcement;

    /**
     * 群组是否已经解散
     */
    @ColumnInfo(name = "is_dismissed")
    private boolean dismissed;

    /**
     * 群成员
     */

    /**
     * 管理员
     */

    /**
     * 当前登录用户Uid
     */
    @ColumnInfo(name = "current_uid")
    private String currentUid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }
}
