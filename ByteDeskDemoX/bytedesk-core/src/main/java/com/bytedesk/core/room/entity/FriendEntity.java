package com.bytedesk.core.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "friend", indices = {@Index("current_uid")})
public class FriendEntity {

    /**
     * 用户信息表:
     * 限制：用户名、昵称、邮箱、手机 均唯一
     * TODO: 去除服务器端消息id，替换
     */
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @ColumnInfo(name = "uid")
    private String uid;

    /**
     * 全平台唯一，公司子账号通过添加@subDomain来区分唯一性
     */
    @ColumnInfo(name = "username")
    private String username;

    /**
     * 邮箱
     */
    @ColumnInfo(name = "email")
    private String email;

    /**
     * 手机号
     */
    @ColumnInfo(name = "mobile")
    private String mobile;

    /**
     * 对话页面显示
     */
    @ColumnInfo(name = "nickname")
    private String nickname;

    /**
     * 后台统计显示
     */
    @ColumnInfo(name = "real_name")
    private String realName;

    /**
     * 头像
     */
    @ColumnInfo(name = "avatar")
    private String avatar;

    /**
     * 企业号
     */
    @ColumnInfo(name = "sub_domain")
    private String subDomain;

    /**
     * 长连接状态
     */
    @ColumnInfo(name = "connection_status")
    private String connectionStatus;

    /**
     * 客服设置接待状态
     */
    @ColumnInfo(name = "accept_status")
    private String acceptStatus;

    /**
     * false 为离职禁止登录，true 为在职
     */
    @ColumnInfo(name = "is_enabled")
    private boolean enabled = true;

    /**
     * 是否是机器人, 默认非机器人
     */
    @ColumnInfo(name = "is_robot")
    private boolean robot = false;

    /**
     * 进入页面欢迎语
     * 设置默认值
     */
    @ColumnInfo(name = "welcome_tip")
    private String welcomeTip;

    /**
     * 是否自动回复
     */
    @ColumnInfo(name = "is_auto_reply")
    private boolean autoReply = false;

    /**
     * 自动回复内容
     * TODO: 添加默认值
     */
    @ColumnInfo(name = "auto_reply_content")
    private String autoReplyContent;

    /**
     * 个性签名
     */
    @ColumnInfo(name = "description")
    private String description;

    /**
     * 同时最大接待会话数目, 默认10个
     */
    @ColumnInfo(name = "max_thread_count")
    private int maxThreadCount;

    /**
     * 当前登录用户Uid
     */
    @ColumnInfo(name = "current_uid")
    private String currentUid;

    /**
     * 不入库，仅行判断是否选中
     */
    @Ignore
    private boolean selected = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRobot() {
        return robot;
    }

    public void setRobot(boolean robot) {
        this.robot = robot;
    }

    public String getWelcomeTip() {
        return welcomeTip;
    }

    public void setWelcomeTip(String welcomeTip) {
        this.welcomeTip = welcomeTip;
    }

    public boolean isAutoReply() {
        return autoReply;
    }

    public void setAutoReply(boolean autoReply) {
        this.autoReply = autoReply;
    }

    public String getAutoReplyContent() {
        return autoReplyContent;
    }

    public void setAutoReplyContent(String autoReplyContent) {
        this.autoReplyContent = autoReplyContent;
    }

    public String getDescription() {
        return (description == null || description.equals("null")) ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxThreadCount() {
        return maxThreadCount;
    }

    public void setMaxThreadCount(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
