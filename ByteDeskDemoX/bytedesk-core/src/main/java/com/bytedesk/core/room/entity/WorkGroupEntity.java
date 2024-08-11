package com.bytedesk.core.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "workGroup", indices = {@Index("current_uid")})
public class WorkGroupEntity {


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
    @ColumnInfo(name = "wid")
    private String wid;

    /**
     * 工作组名称
     */
    @ColumnInfo(name = "nickname")
    private String nickname;

    /**
     * 默认头像
     */
    @ColumnInfo(name = "avatar")
    private String avatar;

    /**
     * 是否默认机器人接待
     */
    @ColumnInfo(name = "is_default_robot")
    private boolean defaultRobot;

    /**
     * 是否无客服在线时，启用机器人接待
     */
    @ColumnInfo(name = "is_offline_robot")
    private boolean offlineRobot;

    /**
     * 宣传语，对话框顶部，
     * 活动、宣传语
     */
    @ColumnInfo(name = "slogan")
    private String slogan;

    /**
     * 进入页面欢迎语
     */
    @ColumnInfo(name = "welcome_tip")
    private String welcomeTip;

    /**
     * 接入客服欢迎语，
     * 如果客服账号没有设置，则启用工作组欢迎语
     */
    @ColumnInfo(name = "accept_tip")
    private String acceptTip;

    /**
     * 非工作时间提示
     */
    @ColumnInfo(name = "non_working_time_tip")
    private String nonWorkingTimeTip;

    /**
     * 离线提示
     */
    @ColumnInfo(name = "offline_tip")
    private String offlineTip;

    /**
     * 客服关闭会话提示语
     */
    @ColumnInfo(name = "close_tip")
    private String closeTip;

    /**
     * 会话自动关闭会话提示语
     */
    @ColumnInfo(name = "auto_close_tip")
    private String autoCloseTip;

    /**
     * 是否强制评价
     */
    @ColumnInfo(name = "is_force_rate")
    private boolean forceRate = false;

    /**
     * 路由类型：
     *
     * 广播pubsub、
     * 轮询robin、
     * 熟客优先recent、
     * 最少接待least、
     * 智能分配smart
     */
    @ColumnInfo(name = "route_type")
    private String routeType;

    /**
     * 是否是系统分配的默认工作组，不允许删除
     */
    @ColumnInfo(name = "is_default")
    private boolean defaulted = false;

    /**
     * 描述、介绍, 对话框 右侧 "关于我们"
     */
    @ColumnInfo(name = "about")
    private String about = "关于我们";

    /**
     * 左上角，昵称下面描述语
     */
    @ColumnInfo(name = "description")
    private String description;

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

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
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

    public boolean isDefaultRobot() {
        return defaultRobot;
    }

    public void setDefaultRobot(boolean defaultRobot) {
        this.defaultRobot = defaultRobot;
    }

    public boolean isOfflineRobot() {
        return offlineRobot;
    }

    public void setOfflineRobot(boolean offlineRobot) {
        this.offlineRobot = offlineRobot;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getWelcomeTip() {
        return welcomeTip;
    }

    public void setWelcomeTip(String welcomeTip) {
        this.welcomeTip = welcomeTip;
    }

    public String getAcceptTip() {
        return acceptTip;
    }

    public void setAcceptTip(String acceptTip) {
        this.acceptTip = acceptTip;
    }

    public String getNonWorkingTimeTip() {
        return nonWorkingTimeTip;
    }

    public void setNonWorkingTimeTip(String nonWorkingTimeTip) {
        this.nonWorkingTimeTip = nonWorkingTimeTip;
    }

    public String getOfflineTip() {
        return offlineTip;
    }

    public void setOfflineTip(String offlineTip) {
        this.offlineTip = offlineTip;
    }

    public String getCloseTip() {
        return closeTip;
    }

    public void setCloseTip(String closeTip) {
        this.closeTip = closeTip;
    }

    public String getAutoCloseTip() {
        return autoCloseTip;
    }

    public void setAutoCloseTip(String autoCloseTip) {
        this.autoCloseTip = autoCloseTip;
    }

    public boolean isForceRate() {
        return forceRate;
    }

    public void setForceRate(boolean forceRate) {
        this.forceRate = forceRate;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public boolean isDefaulted() {
        return defaulted;
    }

    public void setDefaulted(boolean defaulted) {
        this.defaulted = defaulted;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
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
}
