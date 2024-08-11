package com.bytedesk.core.util;

/**
 *
 * @author bytedesk.com
 */
public class BDCoreConstant {

    //
    public static final String APP_NAME = "bytedesk";
    public static final String DATABASE_NAME = "bytedesk.db";

    //
    public static final int HTTP_DEFAULT_CONNECT_TIMEOUT = 60;
    public static final int HTTP_DEFAULT_WRITE_TIMEOUT = 60;
    public static final int HTTP_DEFAULT_READ_TIMEOUT = 60;

    /**
     * 消息类型：文本
     */
    public static final String MESSAGE_TYPE_TEXT = "text";
    /**
     * 图片消息
     */
    public static final String MESSAGE_TYPE_IMAGE = "image";
    /**
     * 文件类型
     */
    public static final String MESSAGE_TYPE_FILE = "file";
    /**
     * 录音消息
     */
    public static final String MESSAGE_TYPE_VOICE = "voice";
    /**
     * 短视频
     */
    public static final String MESSAGE_TYPE_VIDEO = "video";
    /**
     *
     */
    public static final String MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    /**
     *
     */
    public static final String MESSAGE_TYPE_LOCATION = "location";
    /**
     *
     */
    public static final String MESSAGE_TYPE_LINK = "link";
    /**
     *
     */
    public static final String MESSAGE_TYPE_EVENT = "event";
    /**
     * 自定义消息类型：内容放在content字段
     */
    public static final String MESSAGE_TYPE_CUSTOM = "custom";
    /**
     * 红包
     */
    public static final String MESSAGE_TYPE_RED_PACKET = "red_packet";
    /**
     * 商品
     */
    public static final String MESSAGE_TYPE_COMMODITY = "commodity";
    /**
     * subscribe(订阅)
     * TODO: 用户未关注时，进行关注后的事件推送
     */
    public static final String MESSAGE_EVENT_TYPE_SUBSCRIBE = "subscribe";
    /**
     * unsubscribe(取消订阅)
     */
    public static final String MESSAGE_EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    /**
     * 用户已关注时的事件推送
     */
    public static final String MESSAGE_EVENT_TYPE_SCAN = "SCAN";
    /**
     * 上报地理位置事件
     */
    public static final String MESSAGE_EVENT_TYPE_LOCATION = "LOCATION";
    /**
     * 点击菜单跳转链接时的事件推送
     */
    public static final String MESSAGE_EVENT_TYPE_VIEW = "VIEW";
    /**
     * 自定义菜单事件
     */
    public static final String MESSAGE_EVENT_TYPE_CLICK = "CLICK";
    /**
     * 点击自定义菜单请求客服
     */
    public static final String MESSAGE_EVENT_KEY_AGENT = "event_agent";
    /**
     * 点击自定义菜单请求'关于我们'
     */
    public static final String MESSAGE_EVENT_KEY_ABOUT = "event_about";
    /**
     * 机器人, 自动回复
     */
    public static final String MESSAGE_TYPE_ROBOT = "robot";
    /**
     * 问卷
     */
    public static final String MESSAGE_TYPE_QUESTIONNAIRE = "questionnaire";
    /**
     * 分公司，方便提取分公司所包含的国家，金吉列大学长
     */
    public static final String MESSAGE_TYPE_COMPANY = "company";
    /**
     * 选择工作组
     */
    public static final String MESSAGE_TYPE_WORK_GROUP = "workGroup";
    /**
     *
     */
    public static final String MESSAGE_TYPE_NOTIFICATION = "notification";
    /**
     * 非工作时间
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_NON_WORKING_TIME = "notification_non_working_time";
    /**
     * 客服离线，当前无客服在线
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_OFFLINE = "notification_offline";
    /**
     * 开始浏览页面
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE_START = "notification_browse_start";
    /**
     * 浏览页面结束
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE_END = "notification_browse_end";
    /**
     * 邀请访客
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE_INVITE = "notification_browse_invite";
    /**
     * 访客接受邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE_INVITE_ACCEPT = "notification_browse_invite_accept";
    /**
     * 访客拒绝邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE_INVITE_REJECT = "notification_browse_invite_reject";
    /**
     * 新进入会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_THREAD = "notification_thread";
    /**
     * 重新进入会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_THREAD_REENTRY = "notification_thread_reentry";
    /**
     * 新建工单
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_TICKET = "notification_ticket";
    /**
     * 意见反馈
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_FEEDBACK = "notification_feedback";
    /**
     * 新进入队列
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_QUEUE = "notification_queue";
    /**
     * 排队中离开
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_QUEUE_LEAVE = "notification_queue_leave";
    /**
     * 接入队列访客
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_QUEUE_ACCEPT = "notification_queue_accept";
    /**
     * 忽略队列访客
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_QUEUE_IGNORE = "notification_queue_ignore";
    /**
     * 超时队列访客
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_QUEUE_TIMEOUT = "notification_queue_timeout";
    /**
     * 自动接入会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_ACCEPT_AUTO = "notification_accept_auto";
    /**
     * 手动接入
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_ACCEPT_MANUAL = "notification_accept_manual";
    /**
     * 上线
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_CONNECT = "notification_connect";
    /**
     * 离线
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_DISCONNECT = "notification_disconnect";
    /**
     * 离开会话页面
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_LEAVE = "notification_leave";
    /**
     * 邀请评价
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_INVITE_RATE = "notification_invite_rate";
    /**
     * 评价结果
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_RATE_RESULT = "notification_rate_result";
    /**
     * 客服关闭会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_AGENT_CLOSE = "notification_agent_close";
    /**
     * 访客关闭会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_VISITOR_CLOSE = "notification_visitor_close";
    /**
     * 自动关闭会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_AUTO_CLOSE = "notification_auto_close";
    /**
     * 邀请会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_INVITE = "notification_invite";
    /**
     * 接受邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_INVITE_ACCEPT = "notification_invite_accept";
    /**
     * 拒绝邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_INVITE_REJECT = "notification_invite_reject";
    /**
     * 转接会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_TRANSFER = "notification_transfer";
    /**
     * 接受转接
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_TRANSFER_ACCEPT = "notification_transfer_accept";
    /**
     * 拒绝转接
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_TRANSFER_REJECT = "notification_transfer_reject";
    /**
     * 满意度请求
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_RATE_REQUEST = "notification_rate_request";
    /**
     * 评价
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_RATE = "notification_rate";
    /**
     * 普通用户设置 在线状态消息
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_ONLINE_STATUS = "notification_online_status";
    /**
     * 长连接状态
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_CONNECTION_STATUS = "notification_connection_status";
    /**
     * 客服设置接待状态
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_ACCEPT_STATUS = "notification_accept_status";
    /**
     * 消息预知
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_PREVIEW = "notification_preview";
    /**
     * 消息撤回
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_RECALL = "notification_recall";
    /**
     * 消息被拒绝
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BLOCK = "notification_block";
    /**
     * 访客浏览网页，通知客服端
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE = "notification_browse";
    /**
     * 非会话类消息通知
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_NOTICE = "notification_notice";
    /**
     * 频道通知
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_CHANNEL = "notification_channel";
    /**
     * 消息回执：收到消息之后回复给消息发送方
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_RECEIPT = "notification_receipt";
    /**
     * 踢掉其他客户端
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_KICKOFF = "notification_kickoff";
    /**
     * 发送表单请求
     */
//    public static final String MESSAGE_TYPE_NOTIFICATION_FORM = "notification_form";
    /**
     * 表单内嵌类型
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_FORM_REQUEST = "notification_form_request";
    public static final String MESSAGE_TYPE_NOTIFICATION_FORM_RESULT = "notification_form_result";
    /**
     * webrtc通知初始化localStream
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_INVITE_VIDEO = "notification_webrtc_invite_video";
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_INVITE_AUDIO = "notification_webrtc_invite_audio";
    /**
     * webrtc取消邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_CANCEL = "notification_webrtc_cancel";
    /**
     * webrtc邀请视频会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_OFFER_VIDEO = "notification_webrtc_offer_video";
    /**
     * webrtc邀请音频会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_OFFER_AUDIO = "notification_webrtc_offer_audio";
    /**
     * 接受webrtc邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_ANSWER = "notification_webrtc_answer";
    /**
     * webrtc candidate信息
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_CANDIDATE = "notification_webrtc_candidate";
    /**
     * 接受webrtc邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_ACCEPT = "notification_webrtc_accept";
    /**
     * 拒绝webrtc邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_REJECT = "notification_webrtc_reject";
    /**
     * 被邀请方视频设备 + peeConnection已经就绪
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_READY = "notification_webrtc_ready";
    /**
     * webrtc忙线
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_BUSY = "notification_webrtc_busy";
    /**
     * 结束webrtc会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_CLOSE = "notification_webrtc_close";
    /**
     * 用户进入页面，来源于小程序
     */
    public static final String MESSAGE_TYPE_USER_ENTER_TEMPSESSION = "user_enter_tempsession";
    /**
     * 创建群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_CREATE = "notification_group_create";
    /**
     * 更新群名称、简介等
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_UPDATE = "notification_group_update";
    /**
     * 邀请多人加入群
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_INVITE = "notification_group_invite";
    /**
     * 受邀请：同意
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_INVITE_ACCEPT = "notification_group_invite_accept";
    /**
     * 受邀请：拒绝
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_INVITE_REJECT = "notification_group_invite_reject";
    /**
     * 不需要审核加入群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_JOIN = "notification_group_join";
    /**
     * 主动申请加入群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_APPLY = "notification_group_apply";
    /**
     * 同意：主动申请加群
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_APPLY_APPROVE = "notification_group_apply_approve";
    /**
     * 拒绝：主动申请加群
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_APPLY_DENY = "notification_group_apply_deny";
    /**
     * 踢人
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_KICK = "notification_group_kick";
    /**
     * 禁言
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_MUTE = "notification_group_mute";
    /**
     * 取消禁言
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_UNMUTE = "notification_group_unmute";
    /**
     * 设置管理员
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_SET_ADMIN = "notification_group_set_admin";
    /**
     * 取消设置管理员
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_UNSET_ADMIN = "notification_group_unset_admin";
    /**
     * 移交群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_TRANSFER = "notification_group_transfer";
    /**
     * 移交群组：同意、接受
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_TRANSFER_ACCEPT = "notification_group_transfer_accept";
    /**
     * 移交群组：拒绝
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_TRANSFER_REJECT = "notification_group_transfer_reject";
    /**
     * 退出群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_WITHDRAW = "notification_group_withdraw";
    /**
     * 解散群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_DISMISS = "notification_group_dismiss";

    /**
     * 工作组请求会话
     */
    public static final String THREAD_REQUEST_TYPE_WORK_GROUP = "workGroup";
    /**
     * 指定客服会话: 用appointed替代visitor
     */
    public static final String THREAD_REQUEST_TYPE_APPOINTED = "appointed";

    /**
     *
     */
    public static final String THREAD_TYPE_WORKGROUP = "workgroup";
    public static final String THREAD_TYPE_APPOINTED = "appointed";
    public static final String THREAD_TYPE_CONTACT = "contact";
    public static final String THREAD_TYPE_GROUP = "group";
    public static final String THREAD_TYPE_ROBOT = "robot";
    public static final String THREAD_TYPE_LEAVEMSG = "leavemsg";
    public static final String THREAD_TYPE_FEEDBACK = "feedback";
    // 机器人转人工
    public static final String THREAD_TYPE_ROBOT_TO_AGENT = "robot_to_agent";
    // 工单
    public static final String THREAD_TYPE_TICKET_WORKGROUP = "ticket_workgroup";
    public static final String THREAD_TYPE_TICKET_APPOINTED = "ticket_appointed";

    /**
     *  访客会话、同事一对一、群组会话
     */
    public static final String MESSAGE_SESSION_TYPE_WORKGROUP = THREAD_TYPE_WORKGROUP;
    public static final String MESSAGE_SESSION_TYPE_APPOINTED= THREAD_TYPE_APPOINTED;
    public static final String MESSAGE_SESSION_TYPE_CONTACT = THREAD_TYPE_CONTACT;
    public static final String MESSAGE_SESSION_TYPE_GROUP = THREAD_TYPE_GROUP;
    public static final String MESSAGE_SESSION_TYPE_ROBOT = THREAD_TYPE_ROBOT;

    /**
     * 访客pc网站
     */
    public static String CLIENT_WEB = "web";
    /**
     * 访客手机网站
     */
    public static String CLIENT_WAP = "wap";
    /**
     * 访客安卓
     */
    public static String CLIENT_ANDROID = "android";
    /**
     * 访客苹果
     */
    public static String CLIENT_IOS = "ios";
    /**
     * 访客小程序
     */
    public static String CLIENT_WECHAT_MINI = "wechat_mini";
    /**
     * 访客微信客服接口
     */
    public static String CLIENT_WECHAT_MP = "wechat_mp";
    /**
     * 访客微信自定义菜单
     */
    public static String CLIENT_WECHAT_URL = "wechat_url";

    // 客服端
    /**
     * Windwow客服端
     */
    public static String CLIENT_WINDOW_ADMIN = "window_admin";
    /**
     * MAC客服端
     */
    public static String CLIENT_MAC_ADMIN = "mac_admin";
    /**
     * 安卓手机客服端
     */
    public static String CLIENT_ANDROID_ADMIN = "android_admin";
    /**
     * 苹果手机客服端
     */
    public static String CLIENT_IOS_ADMIN = "ios_admin";
    /**
     * web客服端
     */
    public static String CLIENT_WEB_ADMIN = "web_admin";
    /**
     * 小程序客服端
     */
    public static String CLIENT_WECHAT_MINI_ADMIN = "wechat_mini_admin";
    /**
     * 系统端
     */
    public static String CLIENT_SYSTEM = "system";


    /**
     * 消息发送状态:
     *
     * 1. 发送中
     */
    public static final String MESSAGE_STATUS_SENDING = "sending";
    /**
     * 2. 已经存储到服务器
     */
    public static final String MESSAGE_STATUS_STORED = "stored";
    /**
     * 3. 对方已收到
     */
    public static final String MESSAGE_STATUS_RECEIVED = "received";
    /**
     * 4. 对方已读
     */
    public static final String MESSAGE_STATUS_READ = "read";
    /**
     * 5. 发送错误
     */
    public static final String MESSAGE_STATUS_ERROR =  "error";
    /**
     * 6. 阅后即焚已销毁
     */
    public static final String MESSAGE_STATUS_DESTROYED = "destroyed";
    /**
     * 7. 消息撤回
     */
    public static final String MESSAGE_STATUS_RECALL = "recall";


    /**
     * app、网站已经上线
     */
    public static final String APP_STATUS_RELEASE = "release";
    /**
     * 开发对接中...
     */
    public static final String APP_STATUS_DEBUG = "debug";
    /**
     * 已上线，开发新版本中
     */
    public static final String APP_STATUS_VERSION = "version";


    /**
     * 用户在线状态：
     */
    /**
     * 连接中
     */
    public static final String USER_STATUS_CONNECTING = "connecting";
    /**
     * 跟服务器建立长连接
     */
    public static final String USER_STATUS_CONNECTED = "connected";
    /**
     * 断开长连接
     */
    public static final String USER_STATUS_DISCONNECTED = "disconnected";
    /**
     * 被其他客户端踢掉线
     */
    public static final String USER_STATUS_KICKOFF = "kickoff";
    /**
     * 网络断开
     */
    public static final String USER_STATUS_NETWORK_ERROR = "network_error";
    /**
     * 在线状态
     */
    public static final String USER_STATUS_ONLINE = "online";
    /**
     * 离线状态
     */
    public static final String USER_STATUS_OFFLINE = "offline";
    /**
     * 忙
     */
    public static final String USER_STATUS_BUSY = "busy";
    /**
     * 离开
     */
    public static final String USER_STATUS_AWAY = "away";
    /**
     * 登出
     */
    public static final String USER_STATUS_LOGOUT = "logout";
    /**
     * 登录
     */
    public static final String USER_STATUS_LOGIN = "login";
    /**
     * 离开
     */
    public static final String USER_STATUS_LEAVE = "leave";
    /**
     * 话后
     */
    public static final String USER_STATUS_AFTER  = "after";
    /**
     * 就餐
     */
    public static final String USER_STATUS_EAT = "eat";
    /**
     * 小休
     */
    public static final String USER_STATUS_REST = "rest";


    /**
     * 排队状态: 排队中
     */
    public static final String QUEUE_STATUS_QUEUING = "queuing";
    /**
     * 已接入
     */
    public static final String QUEUE_STATUS_ACCEPTED = "accepted";
    /**
     * 已离开
     */
    public static final String QUEUE_STATUS_LEAVED = "leaved";

    /**
     * 超级管理员
     */
    public static final String ROLE_SUPER = "ROLE_SUPER";
    /**
     * 注册管理员：注册用户默认角色
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    /**
     * 付费注册用户
     */
    public static final String ROLE_VIP = "ROLE_VIP";
    /**
     * 试用会员
     */
    public static final String ROLE_TRY = "ROLE_TRY";
    /**
     * 第三方代理公司
     */
    public static final String ROLE_PROXY = "ROLE_PROXY";
    /**
     * 免费注册用户
     */
    public static final String ROLE_FREE = "ROLE_FREE";
    /**
     * 访客
     */
    public static final String ROLE_VISITOR = "ROLE_VISITOR";
    /**
     * 普通员工：非客服人员
     */
    public static final String ROLE_MEMBER = "ROLE_MEMBER";
    /**
     * 智能客服：客服机器人
     */
    public static final String ROLE_ROBOT = "ROLE_ROBOT";

    /**
     * 工作组：
     * 客服组长
     *
     */
    public static final String ROLE_WORKGROUP_ADMIN = "ROLE_WORKGROUP_ADMIN";
    /**
     * 客服账号：
     * 接待访客角色，如果要接待访客，必须赋予此角色
     */
    public static final String ROLE_WORKGROUP_AGENT = "ROLE_WORKGROUP_AGENT";
    /**
     * 质检账号
     */
    public static final String ROLE_WORKGROUP_CHECKER = "ROLE_WORKGROUP_CHECKER";

    // 自动回复
    public static final String AUTO_REPLY_NO = "无自动回复";
    //
    public static final String AUTO_REPLY_EAT = "外出就餐，请稍后";
    //
    public static final String AUTO_REPLY_LEAVE = "不在电脑旁，请稍后";
    //
    public static final String AUTO_REPLY_BACK = "马上回来";
    //
    public static final String AUTO_REPLY_PHONE = "接听电话中";
    //
    public static final String AUTO_REPLY_SELF = "自定义";

    //
    public static final String STATUS_ONLINE = "在线接待";
    //
    public static final String STATUS_BUSY = "接待忙线";
    //
    public static final String STATUS_REST = "接待小休";

    //
    public static final String AVATAR_ALBUM = "相册";
    public static final String AVATAR_CAMERA = "拍照";


    /**
     * 群组相关: 普通群组、讨论组
     */
    public static final String GROUP_TYPE_GROUP = "group";
    public static final String GROUP_TYPE_DISCUSS = "discuss";

    /**
     * 语音格式、文件类型
     */
    public static final String MESSAGE_VOICE_FORMAT_AMR = "amr";
    public static final String MESSAGE_FILE_FORMAT_DOC = "doc";
    public static final String MESSAGE_FILE_FORMAT_EXCEL = "excel";

    /**
     * Event Bus
     */

    /**
     * preference
     */
    public static final String PREFERENCE_PREFIX = "bytedesk_";

    ///////////////////////MQTT//////////////////////////

    /** Bundle key for passing a connection around by it's name **/
    public static final String CONNECTION_KEY = "CONNECTION_KEY";

    public static final String AUTO_CONNECT = "AUTO_CONNECT";
    public static final String CONNECTED = "CONNECTEd";

    public static final String LOGGING_KEY = "LOGGING_ENABLED";

    /** Property name for the history field in {@link } object for use with {@link java.beans.PropertyChangeEvent} **/
    public static final String historyProperty = "history";

    /** Property name for the connection status field in {@link } object for use with {@link java.beans.PropertyChangeEvent} **/
    public static final String ConnectionStatusProperty = "connectionStatus";

    /** Empty String for comparisons **/
    public static final String empty = "";


    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String ACTION_DOWNLOAD_PROGRESS = "my_download_progress";
    public static final String ACTION_DOWNLOAD_SUCCESS = "my_download_success";
    public static final String ACTION_DOWNLOAD_FAIL = "my_download_fail";

    public   static final int DOWNLOAD_FLAG_INIT = 0; // 初始状态
    public  static final int  DOWNLOAD_FLAG_DOWN = 1; // 下载状态
    public   static final int DOWNLOAD_FLAG_PAUSE = 2; // 暂停状态
    public   static final int DOWNLOAD_FLAG_DONE = 3; // 完成状态

    //////////////////////录音机///////////////////////////////////////////
    public static final String 	RECORDER_STATE_KEY = "recorder_state";
    public static final String 	MAX_FILE_SIZE_KEY = "max_file_size";

    public static final int 	BITRATE_AMR = 2 * 1024 * 8;
    public static final String EXT_AMR = ".amr";
    public static final String EXT_WAV = ".wav";

    public static final String SAMPLE_DEFAULT_DIR = "/bytedesk/file/";

    public static final String BD_SHOULD_PLAY_SEND_MESSAGE_SOUND = "bd_should_play_send_message_sound";
    public static final String BD_SHOULD_PLAY_RECEIVE_MESSAGE_SOUND = "bd_should_play_receive_message_sound";
    public static final String BD_SHOULD_VIBRATE_ON_RECEIVE_MESSAGE = "bd_should_vibrate_on_receiving_message";
    public static final String BD_SHOULD_SET_DEVICE_INFO = "bd_should_set_device_info";

    public static final String BD_UPLOAD_NOTIFICATION_PERCENTAGE = "bd_upload_notification_percentage";
    public static final String BD_UPLOAD_NOTIFICATION_ERROR = "bd_upload_notification_error";



}





