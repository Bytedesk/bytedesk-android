package com.bytedesk.core.room.entity;

/**
 * 留言
 *
 * 暂不入库
 */
public class LeaveMessageEntity {

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    private String lid;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 时间
     */
    private String createdAt;

    /**
     * 回复内容
     */
    private String reply;

    /**
     * 是否已经回复
     */
    private boolean replied;


    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }

    public boolean isReplied() {
        return replied;
    }

}
