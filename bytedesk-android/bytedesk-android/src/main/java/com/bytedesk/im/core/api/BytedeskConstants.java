package com.bytedesk.im.core.api;

public class BytedeskConstants {

    // 本地测试
//    public static final boolean IS_DEBUG = true;
//    public static final String API_BASE_URL = "http://10.0.2.2:9003";
//    public static final String BASE_HOST = "10.0.2.2:9003";
//    public static final String BASE_URL = "http://10.0.2.2:9003";
//    public static final String STOMP_WS_URL = "ws://10.0.2.2:9003/stomp";
//    public static final String STOMP_SOCKJS_URL = "http://10.0.2.2:9003/sockjs";
//    public static final String UPLOAD_FILE_URL = "http://10.0.2.2:9003/visitor/api/v1/upload/file";

    // 线上环境
     public static final boolean IS_DEBUG = false;
     public static final String API_BASE_URL = "https://api.weiyuai.cn";
     public static final String BASE_HOST = "api.weiyuai.cn";
     public static final String BASE_URL = "https://api.weiyuai.cn";
     public static final String STOMP_WS_URL = "wss://api.weiyuai.cn/stomp";
     public static final String STOMP_SOCKJS_URL = "https://api.weiyuai.cn/sockjs";
     public static final String UPLOAD_FILE_URL = "https://api.weiyuai.cn/visitor/api/v1/upload/file";
//
    public static final String HTTP_CLIENT = "android";
    public static final String PLATFORM = "weiyuai";
    public static final String LOCALE = "locale";
    public static final String VISITOR_UID = "visitor_uid";
    public static final String VISITOR_NICKNAME = "visitor_nickname";
    public static final String VISITOR_AVATAR = "visitor_avatar";
    public static final String VISITOR_ORGUID = "visitor_orguid";
    public static final String VISITOR_DEVICEUID = "visitor_deviceuid";
    public static final String I18N_PREFIX = "i18n.";
// 登录超时
    public static final String EVENT_BUS_LOGIN_TIMEOUT = "EVENT_BUS_LOGIN_TIMEOUT";
// 用户名或密码错误
    public static final String EVENT_BUS_LOGIN_ERROR_400 = "EVENT_BUS_LOGIN_ERROR_400";
// 服务器错误500
    public static final String EVENT_BUS_SERVER_ERROR_500 = "EVENT_BUS_SERVER_ERROR_500";
// token失效
    public static final String EVENT_BUS_TOKEN_INVALID = "EVENT_BUS_TOKEN_INVALID";
    public static final String EVENT_BUS_SWITCH_THEME = "EVENT_BUS_SWITCH_THEME";
//
    public static final String EVENT_BUS_MESSAGE_TYPE_STATUS = "EVENT_BUS_MESSAGE_TYPE_STATUS";
    public static final String EVENT_BUS_MESSAGE_TYPE_TYPING = "EVENT_BUS_MESSAGE_TYPE_TYPING";
    public static final String EVENT_BUS_MESSAGE_TYPE_PROCESSING =
            "EVENT_BUS_MESSAGE_TYPE_PROCESSING";
    public static final String EVENT_BUS_MESSAGE_TYPE_STREAM = "EVENT_BUS_MESSAGE_TYPE_STREAM";
    public static final String EVENT_BUS_MESSAGE_TYPE_PREVIEW = "EVENT_BUS_MESSAGE_TYPE_PREVIEW";
    public static final String EVENT_BUS_MESSAGE_TYPE_CONTENT = "EVENT_BUS_MESSAGE_TYPE_CONTENT";
//
    public static final String THEME_MODE_TYPE = "THEME_MODE_TYPE";
    public static final String THEME_MODE_TYPE_LIGHT = "light";
    public static final String THEME_MODE_TYPE_DARK = "dark";
    public static final String THEME_MODE_TYPE_SYSTEM = "system";
//
    public static final String THEME_NAME_TYPE = "THEME_NAME_TYPE";
    public static final String THEME_NAME_TYPE_DARK = "dark";
    public static final String THEME_NAME_TYPE_LIGHT = "light";
//
    public static final String PLAY_AUDIO = "PLAY_AUDIO";
//
    public static final String CONFIG_ENABLED = "CONFIG_ENABLED";
    public static final String CONFIG_API_HOST = "CONFIG_API_HOST";
    public static final String CONFIG_HTML_HTML = "CONFIG_HTML_HOST";
//
    public static final String USER_TYPE_AGENT = "AGENT";
    public static final String USER_TYPE_SYSTEM = "SYSTEM";
    public static final String USER_TYPE_VISITOR = "VISITOR";
    public static final String USER_TYPE_ROBOT = "ROBOT";
    public static final String USER_TYPE_MEMBER = "MEMBER";
    public static final String USER_TYPE_ASSISTANT = "ASSISTANT";
    public static final String USER_TYPE_CHANNEL = "CHANNEL";
    public static final String USER_TYPE_LOCAL = "LOCAL";
    public static final String USER_TYPE_USER = "USER";
//
// 会话类型 = 工作组会话、访客跟客服一对一、同事一对一、群组会话
    public static final String THREAD_TYPE_AGENT = "AGENT";
    public static final String THREAD_TYPE_WORKGROUP = "WORKGROUP";
    public static final String THREAD_TYPE_KB = "KB";
    public static final String THREAD_TYPE_LLM = "LLM";
    public static final String THREAD_TYPE_MEMBER = "MEMBER";
    public static final String THREAD_TYPE_GROUP = "GROUP";
    public static final String THREAD_TYPE_LEAVEMSG = "LEAVEMSG";
    public static final String THREAD_TYPE_FEEDBACK = "FEEDBACK";
    public static final String THREAD_TYPE_ASISTANT = "ASISTANT";
    public static final String THREAD_TYPE_CHANNEL = "CHANNEL";
    public static final String THREAD_TYPE_LOCAL = "LOCAL";
//
    public static final String THREAD_STATUS_QUEUING = "QUEUING"; // 排队中
    public static final String THREAD_STATUS_NORMAL = "NORMAL"; // 正常
    public static final String THREAD_STATUS_REENTER = "REENTER"; // 会话进行中，访客关闭会话页面之后，重新进入
    public static final String THREAD_STATUS_REOPEN = "REOPEN"; // 会话关闭之后，重新进入
    public static final String THREAD_STATUS_OFFLINE = "OFFLINE"; // 客服不在线
    public static final String THREAD_STATUS_RATED = "RATED"; // rated, prevent repeated rate
    public static final String THREAD_STATUS_AUTO_CLOSED = "AUTO_CLOSED";
    public static final String THREAD_STATUS_AGENT_CLOSED = "AGENT_CLOSED";
    public static final String THREAD_STATUS_DISMISSED = "DISMISSED"; // 会话解散
    public static final String THREAD_STATUS_MUTED = "MUTED"; // 会话静音
    public static final String THREAD_STATUS_FORBIDDEN = "FORBIDDEN"; // 会话禁言
    public static final String THREAD_STATUS_MONITORED = "MONITORED"; // 会话监控
// 消息发送状态
// 发送中
    public static final String MESSAGE_STATUS_SENDING = "SENDING"; // sending
    public static final String MESSAGE_STATUS_TIMEOUT = "TIMEOUT"; // network send failed
    public static final String MESSAGE_STATUS_BLOCKED = "BLOCKED"; // in black list
    public static final String MESSAGE_STATUS_NOTFRIEND = "NOTFRIEND"; // not friend
    public static final String MESSAGE_STATUS_ERROR = "ERROR"; // other send error
    public static final String MESSAGE_STATUS_SUCCESS = "SUCCESS"; // send success
    public static final String MESSAGE_STATUS_RECALL = "RECALL"; // recall back
    public static final String MESSAGE_STATUS_DELIVERED = "DELIVERED"; // send to the other client
    public static final String MESSAGE_STATUS_READ = "READ"; // read by the other client
    public static final String MESSAGE_STATUS_DESTROYED = "DESTROYED"; // destroyed after read
    public static final String MESSAGE_STATUS_UNPRECESSED = "UNPRECESSED"; // not processed
    public static final String MESSAGE_STATUS_PROCESSED = "PROCESSED"; // leave message processed
    public static final String MESSAGE_STATUS_LEAVE_MSG_SUBMIT = "LEAVE_MSG_SUBMIT"; // 提交留言
    public static final String MESSAGE_STATUS_RATE_SUBMIT = "RATE_SUBMIT"; // 提交会话评价
    public static final String MESSAGE_STATUS_RATE_CANCEL = "RATE_CANCEL"; // 取消评价会话
    public static final String MESSAGE_STATUS_RATE_UP = "RATE_UP"; // 评价消息up
    public static final String MESSAGE_STATUS_RATE_DOWN = "RATE_DOWN"; // 评价消息down
//
// 消息类型
    public static final String MESSAGE_TYPE_WELCOME = "WELCOME";
    public static final String MESSAGE_TYPE_CONTINUE = "CONTINUE";
    public static final String MESSAGE_TYPE_SYSTEM = "SYSTEM";
    public static final String MESSAGE_TYPE_TEXT = "TEXT"; // 文本消息类型
    public static final String MESSAGE_TYPE_IMAGE = "IMAGE"; // 图片消息类型
    public static final String MESSAGE_TYPE_FILE = "FILE"; // 文件消息类型
    public static final String MESSAGE_TYPE_AUDIO = "AUDIO"; // 语音消息类型
    public static final String MESSAGE_TYPE_VIDEO = "VIDEO"; // 视频消息类型
    public static final String MESSAGE_TYPE_MUSIC = "MUSIC";
    public static final String MESSAGE_TYPE_LOCATION = "LOCATION";
    public static final String MESSAGE_TYPE_GOODS = "GOODS";
    public static final String MESSAGE_TYPE_CARD = "CARD";
    public static final String MESSAGE_TYPE_EVENT = "EVENT";
//
    public static final String MESSAGE_TYPE_GUESS = "GUESS"; // 猜你想问
    public static final String MESSAGE_TYPE_HOT = "HOT"; // 热门问题
    public static final String MESSAGE_TYPE_SHORTCUT = "SHORTCUT"; // 快捷路径
    public static final String MESSAGE_TYPE_ORDER = "ORDER"; // 订单
    public static final String MESSAGE_TYPE_POLL = "POLL"; // 投票
    public static final String MESSAGE_TYPE_FORM = "FORM"; // 表单：询前表单
    public static final String MESSAGE_TYPE_LEAVE_MSG = "LEAVE_MSG"; // 留言
    public static final String MESSAGE_TYPE_LEAVE_MSG_SUBMIT = "LEAVE_MSG_SUBMIT"; // 留言提交
    public static final String MESSAGE_TYPE_TICKET = "TICKET"; // 客服工单
    public static final String MESSAGE_TYPE_TYPING = "TYPING"; // 正在输入
    public static final String MESSAGE_TYPE_PROCESSING = "PROCESSING"; // 正在处理，等待大模型回复中
    public static final String MESSAGE_TYPE_STREAM = "STREAM"; // 流式消息TEXT，大模型回复
    public static final String MESSAGE_TYPE_PREVIEW = "PREVIEW"; // 消息预知
    public static final String MESSAGE_TYPE_RECALL = "RECALL"; // 撤回
    public static final String MESSAGE_TYPE_DELIVERED = "DELIVERED"; // 回执: 已送达
    public static final String MESSAGE_TYPE_READ = "READ"; // 回执: 已读
    public static final String MESSAGE_TYPE_QUOTATION = "QUOTATION"; // qoute message
    public static final String MESSAGE_TYPE_KICKOFF = "KICKOFF"; // kickoff other clients
    public static final String MESSAGE_TYPE_SHAKE = "SHAKE"; // shake window
//
    public static final String MESSAGE_TYPE_FAQ = "FAQ"; // 常见问题FAQ
    public static final String MESSAGE_TYPE_FAQ_Q = "FAQ_Q"; // 常见问题FAQ-问题
    public static final String MESSAGE_TYPE_FAQ_A = "FAQ_A"; // 常见问题FAQ-答案
    public static final String MESSAGE_TYPE_FAQ_UP = "FAQ_UP"; // 常见问题答案评价:UP
    public static final String MESSAGE_TYPE_FAQ_DOWN = "FAQ_DOWN"; // 常见问题答案评价:DOWN
    public static final String MESSAGE_TYPE_ROBOT = "ROBOT"; // 机器人
    public static final String MESSAGE_TYPE_ROBOT_UP = "ROBOT_UP"; // 机器人答案评价:UP
    public static final String MESSAGE_TYPE_ROBOT_DOWN = "ROBOT_DOWN"; // 机器人答案评价:DOWN
//
    public static final String MESSAGE_TYPE_RATE = "RATE"; // 访客主动评价
    public static final String MESSAGE_TYPE_RATE_INVITE = "RATE_INVITE"; // 客服邀请评价
    public static final String MESSAGE_TYPE_RATE_SUBMIT = "RATE_SUBMIT"; // 访客提交评价
    public static final String MESSAGE_TYPE_RATE_CANCEL = "RATE_CANCEL"; // 访客取消评价
//
    public static final String MESSAGE_TYPE_AUTO_CLOSED = "AUTO_CLOSED"; // 自动关闭
    public static final String MESSAGE_TYPE_AGENT_CLOSED = "AGENT_CLOSED"; // 客服关闭
//
    public static final String MESSAGE_TYPE_TRANSFER = "TRANSFER"; // 转接
    public static final String MESSAGE_TYPE_TRANSFER_ACCEPT = "TRANSFER_ACCEPT"; // 转接-接受
    public static final String MESSAGE_TYPE_TRANSFER_REJECT = "TRANSFER_REJECT"; // 转接-拒绝
//
    public static final String MESSAGE_TYPE_INVITE = "INVITE"; // 邀请
    public static final String MESSAGE_TYPE_INVITE_ACCEPT = "INVITE_ACCEPT"; // 邀请-接受
    public static final String MESSAGE_TYPE_INVITE_REJECT = "INVITE_REJECT"; // 邀请-拒绝
//
    public static final String TOPIC_FILE_ASISTANT = "file";
    public static final String TOPIC_SYSTEM_NOTIFICATION = "system";
// 注意：没有 "/" 开头，防止stomp主题中奖 "/" 替换为 "."之后，在最前面多余一个 "."
    public static final String TOPIC_USER_PREFIX = "user/";
// public static final String TOPIC_PRIVATE_PREFIX = "private/";
// public static final String TOPIC_GROUP_PREFIX = "group/";
    public static final String TOPIC_FILE_PREFIX = "file/";
    public static final String TOPIC_SYSTEM_PREFIX = "system/";
// public static final String TOPIC_ROBOT_PREFIX = "robot/";
//
    public static final String TOPIC_ORGNIZATION_PREFIX = "org/";
    public static final String TOPIC_ORG_MEMBER_PREFIX = "org/member/";
    public static final String TOPIC_ORG_DEPARTMENT_PREFIX = "org/department/";
    public static final String TOPIC_ORG_GROUP_PREFIX = "org/group/";
    public static final String TOPIC_ORG_PRIVATE_PREFIX = "org/private/";
    public static final String TOPIC_ORG_ROBOT_PREFIX = "org/robot/";
    public static final String TOPIC_ORG_AGENT_PREFIX = "org/agent/";
    public static final String TOPIC_ORG_WORKGROUP_PREFIX = "org/workgroup/";
    public static final String TOPIC_ORG_KB_PREFIX = "org/kb/";
    public static final String TOPIC_ORG_KBDOC_PREFIX = "org/kbdoc/";
//
    public static final String KB_TYPE_ASISTANT = "ASISTANT";
    public static final String KB_TYPE_HELPDOC = "HELPDOC";
    public static final String KB_TYPE_LLM = "LLM";
    public static final String KB_TYPE_KEYWORD = "KEYWORD";
    public static final String KB_TYPE_FAQ = "FAQ";
    public static final String KB_TYPE_QUICKREPLY = "QUICKREPLY";
    public static final String KB_TYPE_AUTOREPLY = "AUTOREPLY";
    public static final String KB_TYPE_BLOG = "BLOG";
    public static final String KB_TYPE_EMAIL = "EMAIL";
    public static final String KB_TYPE_TABOO = "TABOO";
//
    public static final String UPLOAD_TYPE_CHAT = "CHAT";
//
    public static final String AUTO_REPLY_TYPE_FIXED = "FIXED";
    public static final String AUTO_REPLY_TYPE_KEYWORD = "KEYWORD";
    public static final String AUTO_REPLY_TYPE_LLM = "LLM";
// 
    public static final String EVENT_BUS_MESSAGE = "BYTEDESK_EVENT_BUS_MESSAGE";
// 连接中
    public static final String CONNECTION_STATUS_CONNECTING = "connecting";
// 连接成功
    public static final String CONNECTION_STATUS_CONNECTED = "connected";
// 连接断开
    public static final String CONNECTION_STATUS_DISCONNECTED = "disconnncted";
// 长连接状态
    public static final String EVENT_BUS_CONNECTION_STATUS = "EVENT_BUS_CONNECTION_STATUS";

}
