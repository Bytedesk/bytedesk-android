package com.bytedesk.core.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 *
 * @author bytedesk.com
 */
@Entity(tableName = "thread", indices = {@Index("current_uid")})
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
    @ColumnInfo(name = "tid")
    private String tid;

    /**
     * topic
     */
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "topic")
    private String topic;

    /**
     * 公众号、小程序 token
     */
    @ColumnInfo(name = "token")
    private String token;

    /**
     * 来自stomp的session id
     */
    @ColumnInfo(name = "session_id")
    private String sessionId;

    /**
     * 最新消息内容
     */
    @ColumnInfo(name = "content")
    private String content;

    /**
     * 最新消息时间戳
     */
    @ColumnInfo(name = "timestamp")
    private String timestamp;

    /**
     * 未读消息数
     */
    @ColumnInfo(name = "unread_count")
    private int unreadCount;

    /**
     * 是否是当前会话，客户端点击会话之后，通知服务器并保存，同时通知其他所有端
     */
    @ColumnInfo(name = "is_current")
    private boolean current = false;

    /**
     * 是否进行中会话
     */
    @ColumnInfo(name = "is_processing")
    private boolean processing = false;

    /**
     * 会话类型: 工作组会话、指定客服、同事一对一、群组会话
     */
    @ColumnInfo(name = "type")
    private String type;

    /**
     * 访客、联系人、群组 昵称
     */
    @ColumnInfo(name = "nickname")
    private String nickname;

    /**
     * 访客、联系人、群组 头像
     */
    @ColumnInfo(name = "avatar")
    private String avatar;

    /**
     * 对应队列
     */
    @ColumnInfo(name = "queue_qid")
    private String queueQid;

    /**
     * 访客，只能有一个访客
     */
    @ColumnInfo(name = "visitor_uid")
    private String visitorUid;

    /**
     * 访客来源客户端
     */
    @ColumnInfo(name = "visitor_client")
    private String client;

    /**
     * 一对一会话：Contact
     */
    @ColumnInfo(name = "contact_uid")
    private String contactUid;

    /**
     * 群组会话：Group
     * 注：group为Oracle保留字
     */
    @ColumnInfo(name = "group_gid")
    private String groupGid;

    /**
     * 主客服，accept会话的客服账号，或被转接客服
     */
    @ColumnInfo(name = "agent_uid")
    private String agentUid;

    /**
     * 所属工作组
     */
    @ColumnInfo(name = "workgroup_wid")
    private String workGroupWid;

    /**
     * 会话开始时间
     */
    @ColumnInfo(name = "started_at")
    private String startedAt;

    /**
     * 会话是否结束
     */
    @ColumnInfo(name = "is_closed")
    private boolean closed;

    /**
     * 是否为系统自动关闭
     */
    @ColumnInfo(name = "is_auto_close")
    private boolean autoClose;

    /**
     * 会话结束时间
     */
    @ColumnInfo(name = "closed_at")
    private String closedAt;

    /**
     * 当前登录用户Uid
     */
    @ColumnInfo(name = "current_uid")
    private String currentUid;

    /**
     * 本地输入草稿，跟服务器无关
     */
    @ColumnInfo(name = "draft")
    private String draft;

    /**
     * 是否置顶
     */
    @ColumnInfo(name = "is_mark_top")
    private boolean markTop = false;

    /**
     * 是否设置消息免打扰
     */
    @ColumnInfo(name = "is_mark_disturb")
    private boolean markDisturb = false;

    /**
     * 是否标记未读
     */
    @ColumnInfo(name = "is_mark_unread")
    private boolean markUnread = false;

    /**
     * 是否标记删除
     */
    @ColumnInfo(name = "is_mark_deleted")
    private boolean markDeleted = false;

    /**
     * 是否临时会话
     */
    @ColumnInfo(name = "is_temp")
    private boolean temp;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQueueQid() {
        return queueQid;
    }

    public void setQueueQid(String queueQid) {
        this.queueQid = queueQid;
    }

    public String getVisitorUid() {
        return visitorUid;
    }

    public void setVisitorUid(String visitorUid) {
        this.visitorUid = visitorUid;
    }

    public String getContactUid() {
        return contactUid;
    }

    public void setContactUid(String contactUid) {
        this.contactUid = contactUid;
    }

    public String getGroupGid() {
        return groupGid;
    }

    public void setGroupGid(String groupGid) {
        this.groupGid = groupGid;
    }

    public String getAgentUid() {
        return agentUid;
    }

    public void setAgentUid(String agentUid) {
        this.agentUid = agentUid;
    }

    public String getWorkGroupWid() {
        return workGroupWid;
    }

    public void setWorkGroupWid(String workGroupWid) {
        this.workGroupWid = workGroupWid;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isAutoClose() {
        return autoClose;
    }

    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public boolean isMarkTop() {
        return markTop;
    }

    public void setMarkTop(boolean markTop) {
        this.markTop = markTop;
    }

    public boolean isMarkDisturb() {
        return markDisturb;
    }

    public void setMarkDisturb(boolean markDisturb) {
        this.markDisturb = markDisturb;
    }

    public boolean isMarkUnread() {
        return markUnread;
    }

    public void setMarkUnread(boolean markUnread) {
        this.markUnread = markUnread;
    }

    public boolean isMarkDeleted() {
        return markDeleted;
    }

    public void setMarkDeleted(boolean markDeleted) {
        this.markDeleted = markDeleted;
    }

    public boolean isTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "ThreadEntity{" +
//                "id=" + id +
                ", tid='" + tid + '\'' +
                ", token='" + token + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", unreadCount=" + unreadCount +
                ", current=" + current +
                ", type='" + type + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", queueQid='" + queueQid + '\'' +
                ", visitorUid='" + visitorUid + '\'' +
                ", contactUid='" + contactUid + '\'' +
                ", groupGid='" + groupGid + '\'' +
                ", agentUid='" + agentUid + '\'' +
                ", workGroupWid='" + workGroupWid + '\'' +
                ", startedAt='" + startedAt + '\'' +
                ", closed=" + closed +
                ", autoClose=" + autoClose +
                ", closedAt='" + closedAt + '\'' +
                ", currentUid='" + currentUid + '\'' +
                '}';
    }
}
