package com.bytedesk.core.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "queue", indices = {@Index("current_uid")})
public class QueueEntity {

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
    @ColumnInfo(name = "qid")
    private String qid;

    /**
     * 访客昵称
     */
    @ColumnInfo(name = "nickname")
    private String nickname;

    /**
     * 访客头像
     */
    @ColumnInfo(name = "avatar")
    private String avatar;

    /**
     * 访客uid
     */
    @ColumnInfo(name = "visitor_uid")
    private String visitorUid;

    /**
     * 访客来源客户端
     */
    @ColumnInfo(name = "visitor_client")
    private String visitorClient;

    /**
     * 客服uid
     */
    @ColumnInfo(name = "agent_uid")
    private String agentUid;

    /**
     * 客服接入端
     */
    @ColumnInfo(name = "agent_client")
    private String agentClient;

    /**
     * 所属thread tid
     */
    @ColumnInfo(name = "thread_tid")
    private String threadTid;

    /**
     * 所属工作组 wid
     */
    @ColumnInfo(name = "workgroup_wid")
    private String workGroupWid;

    /**
     * 接入客服时间
     */
    @ColumnInfo(name = "actioned_at")
    private String actionedAt;

    /**
     * queuing/accepted/leaved
     */
    @ColumnInfo(name = "status")
    private String status;

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

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
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

    public String getVisitorUid() {
        return visitorUid;
    }

    public void setVisitorUid(String visitorUid) {
        this.visitorUid = visitorUid;
    }

    public String getAgentUid() {
        return agentUid;
    }

    public void setAgentUid(String agentUid) {
        this.agentUid = agentUid;
    }

    public String getAgentClient() {
        return agentClient;
    }

    public void setAgentClient(String agentClient) {
        this.agentClient = agentClient;
    }

    public String getThreadTid() {
        return threadTid;
    }

    public void setThreadTid(String threadTid) {
        this.threadTid = threadTid;
    }

    public String getWorkGroupWid() {
        return workGroupWid;
    }

    public void setWorkGroupWid(String workGroupWid) {
        this.workGroupWid = workGroupWid;
    }

    public String getActionedAt() {
        return actionedAt;
    }

    public void setActionedAt(String actionedAt) {
        this.actionedAt = actionedAt;
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

    public String getVisitorClient() {
        return visitorClient;
    }

    public void setVisitorClient(String visitorClient) {
        this.visitorClient = visitorClient;
    }
}
