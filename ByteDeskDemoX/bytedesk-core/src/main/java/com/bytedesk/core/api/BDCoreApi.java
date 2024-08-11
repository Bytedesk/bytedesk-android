package com.bytedesk.core.api;

import android.content.Context;

import com.bytedesk.core.callback.BaseCallback;
import com.bytedesk.core.http.HttpMethod;
import com.bytedesk.core.util.BDCoreConstant;
import com.bytedesk.core.util.BDPreferenceManager;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author bytedesk.com on 2017/8/23.
 * https://developer.android.com/topic/libraries/architecture/guide.html#common_architectural_principles
 */
public class BDCoreApi {

    /**
     * 初始化：访客匿名登录
     *
     * @param context
     * @param appKey
     * @param subDomain
     * @param baseCallback
     */
    public static void init(final Context context, final String appKey, final String subDomain, final BaseCallback baseCallback) {
        visitorLogin(context, appKey, subDomain, baseCallback);
    }

    public static void initWithUsername(final Context context, String username, final String appKey, final String subDomain, final BaseCallback baseCallback) {
        initWithUsernameAndNickname(context, username, "", appKey, subDomain, baseCallback);
    }

    public static void initWithUsernameAndNickname(final Context context, String username, String nickname, final String appKey, final String subDomain, final BaseCallback baseCallback) {
        initWithUsernameAndNicknameAndAvatar(context, username, nickname, "", appKey, subDomain, baseCallback);
    }

    public static void initWithUsernameAndNicknameAndAvatar(final Context context, String username, String nickname, String avatar, final String appKey, final String subDomain, final BaseCallback baseCallback) {
        //
        String usernameLocal = BDPreferenceManager.getInstance(context).getUsername();
        String passwordLocal = BDPreferenceManager.getInstance(context).getPassword();
        if (usernameLocal != null && usernameLocal.trim().length() > 0) {
            Logger.i("login");
            // 直接登录
            login(context, usernameLocal, passwordLocal, appKey, subDomain, BDCoreConstant.ROLE_VISITOR, baseCallback);
        }
        else {
            Logger.i("register");
            // 走注册流程
            String password = username;
            registerUser(context, username, nickname, avatar, password, subDomain, new BaseCallback() {

                @Override
                public void onSuccess(JSONObject object) {
                    // 登录
                    try {
                        int status_code = object.getInt("status_code");
                        if (status_code == 200) {
                            // 注册成功
                            String uid = object.getJSONObject("data").getString("uid");
                            String usernameServer = object.getJSONObject("data").getString("username");
                            String nicknameServer = object.getJSONObject("data").getString("nickname");
                            String avatarServer = object.getJSONObject("data").getString("avatar");
                            Logger.d("username:", usernameServer, " avatar:", avatarServer);
                            //
                            BDPreferenceManager.getInstance(context).setUid(uid);
                            BDPreferenceManager.getInstance(context).setUsername(usernameServer);
                            BDPreferenceManager.getInstance(context).setPassword(password);
                            BDPreferenceManager.getInstance(context).setNickname(nicknameServer);
                            BDPreferenceManager.getInstance(context).setAvatar(avatarServer);
                            BDPreferenceManager.getInstance(context).setSubDomain(subDomain);
                            BDPreferenceManager.getInstance(context).setRole(BDCoreConstant.ROLE_VISITOR);
                            //
                            login(context, usernameServer, password, appKey, subDomain, BDCoreConstant.ROLE_VISITOR, baseCallback);
                        } else {
                            // 用户已经存在
                            String uid = object.getString("data");
                            //
                            String usernameCompose = username + "@" + subDomain;
                            BDPreferenceManager.getInstance(context).setUid(uid);
                            BDPreferenceManager.getInstance(context).setUsername(usernameCompose);
                            BDPreferenceManager.getInstance(context).setPassword(password);
                            BDPreferenceManager.getInstance(context).setNickname(nickname);
                            BDPreferenceManager.getInstance(context).setAvatar(avatar);
                            BDPreferenceManager.getInstance(context).setSubDomain(subDomain);
                            BDPreferenceManager.getInstance(context).setRole(BDCoreConstant.ROLE_VISITOR);
                            //
                            login(context, usernameCompose, password, appKey, subDomain, BDCoreConstant.ROLE_VISITOR, baseCallback);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(JSONObject object) {

                }
            });
        }
    }


    /**
     * 访客匿名登录
     *
     * @param appKey
     * @param subDomain
     * @param baseCallback
     */
    public static void visitorLogin(final Context context, final String appKey, final String subDomain, final BaseCallback baseCallback) {
        //
        String username = BDPreferenceManager.getInstance(context).getUsername();
        if (username == null || username.trim().length() == 0) {
            // 需要从服务器请求username
            HttpMethod.registerAnonymousUser(context, subDomain, new BaseCallback() {

                @Override
                public void onSuccess(JSONObject object) {
                    // 登录
                    try {
                        String uid = object.getJSONObject("data").getString("uid");
                        String username = object.getJSONObject("data").getString("username");
                        String nickname = object.getJSONObject("data").getString("nickname");
                        String avatar = object.getJSONObject("data").getString("avatar");
                        Logger.d("username:", username, " avatar:", avatar);
                        //
                        BDPreferenceManager.getInstance(context).setUid(uid);
                        BDPreferenceManager.getInstance(context).setUsername(username);
                        BDPreferenceManager.getInstance(context).setNickname(nickname);
                        BDPreferenceManager.getInstance(context).setAvatar(avatar);
                        BDPreferenceManager.getInstance(context).setSubDomain(subDomain);
                        BDPreferenceManager.getInstance(context).setRole(BDCoreConstant.ROLE_VISITOR);
                        //
                        // 目前设置默认密码：password == username
                        String password = username;
                        login(context, username, password, appKey, subDomain, BDCoreConstant.ROLE_VISITOR, baseCallback);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(JSONObject object) {
                    // 获取获取用户名失败
                    baseCallback.onError(object);
                }
            });
        }
        else {
            // 直接登录
            // 目前设置默认密码：password == username
            String password = username;
            login(context, username, password, appKey, subDomain, BDCoreConstant.ROLE_VISITOR, baseCallback);
        }
    }

    /**
     * 判断是否处于登录状态
     *
     * @param context context
     */
    public static boolean isLogin(Context context) {
        //
        String access_token = BDPreferenceManager.getInstance(context).getAccessToken();
        if (access_token.trim().length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 自定义用户名登录
     *
     * @param username
     * @param password
     * @param appKey
     * @param subDomain
     * @param baseCallback
     */
    public static void login(Context context, String username, String password, String appKey, String subDomain, BaseCallback baseCallback) {
        //
        // 如果用户名含有'@'则证明是管理员账号，可以直接登录，否则就是客服账号，
        // 客服账号需要添加 '@subDomain' 后缀登录
        if (!username.contains("@")) {
            username = username + "@" + subDomain;
        }
        //
        login(context, username, password, appKey, subDomain, BDCoreConstant.ROLE_ADMIN, baseCallback);
    }

    /**
     * 用户名密码登录
     *
     * @param context
     * @param username
     * @param password
     * @param appKey
     * @param subDomain
     * @param baseCallback
     */
    public static void login(final Context context, String username, String password, String appKey, String subDomain, String role, final BaseCallback baseCallback) {

        // 客服端登录
        if (role.equals(BDCoreConstant.ROLE_ADMIN)) {
            // 如果用户名含有'@'则证明是管理员账号，可以直接登录，否则就是客服账号，
            // 客服账号需要添加 '@subDomain' 后缀登录
            if (!username.contains("@")) {
                username = username + "@" + subDomain;
            }
        }
        // 授权登录
        HttpMethod.login(context, username, password, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                // 建立长连接
                BDMqttApi.connect(context);

                baseCallback.onSuccess(object);

//                if (role.equals(BDCoreConstant.ROLE_VISITOR)) {
//
//                    // 建立长连接
//                    BDMqttApi.connect(context);
//
//                    baseCallback.onSuccess(object);
//
//                } else if (role.equals(BDCoreConstant.ROLE_ADMIN)) {
//                    // todo: 替换成多个函数
//                    // 加载个人资料、会话、排队信息
////                    BDCoreApi.initData(context, new BaseCallback() {
////
////                        @Override
////                        public void onSuccess(JSONObject object) {
////
////                            // 建立长连接
////                            BDMqttApi.connect(context);
////
////                            // 数据加载成功之后回调
////                            baseCallback.onSuccess(object);
////                        }
////
////                        @Override
////                        public void onError(JSONObject object) {
////                            //
////                            baseCallback.onError(object);
////                        }
////                    });
//                }
//                // 判断是否已经发送设备信息，如果没有，则发送设备信息
//                if (!BDPreferenceManager.getInstance(context).hasSetDeviceInfo()) {
//
//                    setDeviceInfo(context, new BaseCallback() {
//
//                        @Override
//                        public void onSuccess(JSONObject object) {
//
//                            BDPreferenceManager.getInstance(context).setDeviceInfoFlag(true);
//                        }
//
//                        @Override
//                        public void onError(JSONObject object) {
//
//                        }
//                    });
//                }

            }

            @Override
            public void onError(JSONObject object) {

                baseCallback.onError(object);
            }
        });
    }

    /**
     * 手机号登录
     *
     * @param context
     * @param mobile
     * @param code
     * @param baseCallback
     */
    public static void loginMobile(final Context context, String mobile, String code, final BaseCallback baseCallback) {
        // 授权登录
        HttpMethod.loginMobile(context, mobile, code, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                // 加载个人资料、会话、排队信息
                BDCoreApi.initData(context, new BaseCallback() {

                    @Override
                    public void onSuccess(JSONObject object) {

                        // 建立长连接
                        BDMqttApi.connect(context);

                        // 数据加载成功之后回调
                        baseCallback.onSuccess(object);
                    }

                    @Override
                    public void onError(JSONObject object) {
                        //
                        baseCallback.onError(object);
                    }
                });

                // 发送设备信息
                setDeviceInfo(context, new BaseCallback() {

                    @Override
                    public void onSuccess(JSONObject object) {

                    }

                    @Override
                    public void onError(JSONObject object) {

                    }
                });
            }

            @Override
            public void onError(JSONObject object) {

                baseCallback.onError(object);
            }
        });
    }

    /**
     * 邮箱验证码登录
     *
     * @param context
     * @param email
     * @param code
     * @param baseCallback
     */
    public static void loginEmail(final Context context, String email, String code, final BaseCallback baseCallback) {
        // 授权登录
        HttpMethod.loginEmail(context, email, code, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                // 加载个人资料、会话、排队信息
                BDCoreApi.initData(context, new BaseCallback() {

                    @Override
                    public void onSuccess(JSONObject object) {

                        // 建立长连接
                        BDMqttApi.connect(context);

                        // 数据加载成功之后回调
                        baseCallback.onSuccess(object);
                    }

                    @Override
                    public void onError(JSONObject object) {
                        //
                        baseCallback.onError(object);
                    }
                });

                // 发送设备信息
                setDeviceInfo(context, new BaseCallback() {

                    @Override
                    public void onSuccess(JSONObject object) {

                    }

                    @Override
                    public void onError(JSONObject object) {

                    }
                });
            }

            @Override
            public void onError(JSONObject object) {

                baseCallback.onError(object);
            }
        });
    }

    /**
     * 微信登录
     *
     * @param context
     * @param unionId
     * @param baseCallback
     */
    public static void loginUnionId(final Context context, String unionId, final BaseCallback baseCallback) {
        // 授权登录
        HttpMethod.loginUnionId(context, unionId, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

//                baseCallback.onSuccess(object);

                // 加载个人资料、会话、排队信息
                BDCoreApi.initData(context, new BaseCallback() {

                    @Override
                    public void onSuccess(JSONObject object) {

                        // 建立长连接
                        BDMqttApi.connect(context);

                        // 数据加载成功之后回调
                        baseCallback.onSuccess(object);
                    }

                    @Override
                    public void onError(JSONObject object) {
                        //
                        baseCallback.onError(object);
                    }
                });

                // 发送设备信息
                setDeviceInfo(context, new BaseCallback() {

                    @Override
                    public void onSuccess(JSONObject object) {

                    }

                    @Override
                    public void onError(JSONObject object) {

                    }
                });
            }

            @Override
            public void onError(JSONObject object) {

                baseCallback.onError(object);
            }
        });
    }

    /**
     * 请求短信验证码
     *
     * @param context
     * @param mobile
     * @param baseCallback
     */
    public static void requestMobileCode(Context context, String mobile, BaseCallback baseCallback) {
        //
        HttpMethod.requestMobileCode(context, mobile, baseCallback);
    }

    /**
     * 请求邮箱验证码
     *
     * @param context
     * @param email
     * @param baseCallback
     */
    public static void requestEmailCode(Context context, String email, BaseCallback baseCallback) {
        //
        HttpMethod.requestEmailCode(context, email, baseCallback);
    }

    public static void registerMobile(Context context, String mobile, String email, String nickname, BaseCallback baseCallback) {
        //
        HttpMethod.registerMobile(context, mobile, email, nickname, baseCallback);
    }

    public static void bindMobile(Context context, String mobile, String email, BaseCallback baseCallback) {
        //
        HttpMethod.bindMobile(context, mobile, email, baseCallback);
    }

    public static void isWeChatRegistered(Context context, String unionId, BaseCallback baseCallback) {
        //
        HttpMethod.isWeChatRegistered(context, unionId, baseCallback);
    }

    /**
     * 微信登录之后注册微信用户信息到自有用户系统
     */
    public static void registerWeChat(Context context, String unionid, String email, String openid, String nickname, String avatar,  final BaseCallback baseCallback) {

        HttpMethod.registerWeChat(context, unionid, email, openid, nickname, avatar, baseCallback);
    }

    public static void bindWeChat(Context context, String unionid, String email, final BaseCallback baseCallback) {

        HttpMethod.bindWeChat(context, unionid, email, baseCallback);
    }

    /**
     * 获取客服代码链接
     *
     * @param context
     * @param baseCallback
     */
    public static void getChatCode(Context context, final BaseCallback baseCallback) {

        HttpMethod.getChatCode(context, baseCallback);
    }

    /**
     * 注册普通用户
     *
     * @param username
     * @param nickname
     * @param password
     * @param subDomain
     * @param baseCallback
     */
    public static void registerUser(Context context, String username, String nickname, String password, String subDomain, final BaseCallback baseCallback) {

        HttpMethod.registerUser(context, username, nickname, password, subDomain, baseCallback);
    }

    public static void registerUser(Context context, String username, String nickname, String avatar, String password, String subDomain, final BaseCallback baseCallback) {

        HttpMethod.registerUser(context, username, nickname, avatar, password, subDomain, baseCallback);
    }

    /**
     * 注册普通用户
     *
     * @param username
     * @param nickname
     * @param uid
     * @param password
     * @param subDomain
     * @param baseCallback
     */
    public static void registerUserUid(Context context, String username, String nickname, String uid, String password, String subDomain, final BaseCallback baseCallback) {

        HttpMethod.registerUserUid(context, username, nickname, uid, password, subDomain, baseCallback);
    }

    /**
     * 注册管理员
     *
     * @param email
     * @param password
     * @param baseCallback
     */
    public static void registerAdmin(Context context, String email, String password,  final BaseCallback baseCallback) {

        HttpMethod.registerAdmin(context, email, password, baseCallback);
    }

    public static void registerEmail(Context context, String email, String nickname, String password, String subDomain,  final BaseCallback baseCallback) {

        HttpMethod.registerEmail(context, email, nickname, password, subDomain, baseCallback);
    }

    /**
     * 请求工作组会话 或者 请求指定客服会话
     *
     * @param context
     * @param requestType
     * @param agentUid
     * @param baseCallback
     */
    public static void requestThread(Context context, String workGroupWid, String requestType, String agentUid, BaseCallback baseCallback) {
        //
        HttpMethod.requestThread(context, workGroupWid, requestType, agentUid, 0, baseCallback);
    }

    public static void getContactThread(Context context, String cid, BaseCallback baseCallback) {
        //
        HttpMethod.getContactThread(context, cid, baseCallback);
    }

    public static void getGroupThread(Context context, String gid, BaseCallback baseCallback) {
        //
        HttpMethod.getGroupThread(context, gid, baseCallback);
    }

    public static void requestThreadWebRTCVideo(Context context, String workGroupWid, String requestType, String agentUid, BaseCallback baseCallback) {
        //
        HttpMethod.requestThread(context, workGroupWid, requestType, agentUid, 1, baseCallback);
    }

    public static void requestThreadWebRTCAudio(Context context, String workGroupWid, String requestType, String agentUid, BaseCallback baseCallback) {
        //
        HttpMethod.requestThread(context, workGroupWid, requestType, agentUid, 2, baseCallback);
    }

    /**
     * 请求人工客服，不管此工作组是否设置为默认机器人，只要有人工客服在线，则可以直接对接人工
     *
     * @param context
     * @param workGroupWid
     * @param requestType
     * @param agentUid
     * @param baseCallback
     */
    public static void requestAgent(Context context, String workGroupWid, String requestType, String agentUid, BaseCallback baseCallback) {
        //
        HttpMethod.requestAgent(context, workGroupWid, requestType, agentUid, baseCallback);
    }


    /**
     * 选择问卷答案
     *
     * @param context
     * @param threadTid
     * @param questionnaireItemItemQid
     * @param baseCallback
     */
    public static void requestQuestionnaire(Context context, String threadTid, String questionnaireItemItemQid, BaseCallback baseCallback) {
        //
        HttpMethod.requestQuestionnaire(context, threadTid, questionnaireItemItemQid, baseCallback);
    }


    /**
     * 问卷答案中选择工作组
     *
     * @param context
     * @param workGroupWid
     * @param baseCallback
     */
    public static void chooseWorkGroup(Context context, String workGroupWid, BaseCallback baseCallback) {
        //
        HttpMethod.chooseWorkGroup(context, workGroupWid, baseCallback);
    }

    public static void chooseWorkGroupLiuXue(Context context, String workGroupWid, String workGroupNickname, BaseCallback baseCallback) {
        //
        HttpMethod.chooseWorkGroupLiuXue(context, workGroupWid, workGroupNickname, baseCallback);
    }


    public static void chooseWorkGroupLiuXueLBS(Context context, String workGroupWid, String workGroupNickname, String province, String city, BaseCallback baseCallback) {
        //
        HttpMethod.chooseWorkGroupLiuXueLBS(context, workGroupWid, workGroupNickname, province, city, baseCallback);
    }


    /**
     * 留言
     *
     * @param context 上下文
     * @param type 区分工作组会话 'workGroup'、指定坐席会话 'appointed'
     * @param workGroupWid 工作组 wid
     * @param agentUid 指定坐席客服 uid
     * @param mobile 手机
     * @param email 邮箱
     * @param content 留言内容
     * @param baseCallback
     */
    public static void leaveMessage(Context context, String type, String workGroupWid, String agentUid,
                                       String mobile, String email, String content,
                                       BaseCallback baseCallback) {
        //
        HttpMethod.leaveMessage(context, type, workGroupWid, agentUid, mobile,
                    email, content, baseCallback);
    }

    /**
     * 获取留言列表
     * 分页
     *
     * @param context
     * @param page
     * @param size
     * @param callback
     */
    public static void getLeaveMessages(Context context, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getLeaveMessages(context, page, size, callback);
    }

    /**
     * 回复留言
     *
     * @param context
     * @param lid
     * @param content
     * @param callback
     */
    public static void replyLeaveMessage(Context context, String lid, String content, final BaseCallback callback) {
        //
        HttpMethod.replyLeaveMessage(context, lid, content, callback);
    }

    /**
     * 初始化机器人
     *
     * @param context
     * @param baseCallback
     */
    public static void initAnswer(Context context, String type, String workGroupWid, String agentUid, BaseCallback baseCallback) {
        //
            HttpMethod.initAnswer(context, type, workGroupWid, agentUid, baseCallback);
    }


    /**
     * 热门智能问答
     *
     * @param context
     * @param baseCallback
     */
    public static void topAnswer(Context context, BaseCallback baseCallback) {
        //
            HttpMethod.topAnswer(context, baseCallback);
    }


    /**
     * 根据aid，请求智能答案
     *
     * @param context
     * @param baseCallback
     */
    public static void queryAnswer(Context context, String tid, String aid, BaseCallback baseCallback) {
        //
            HttpMethod.queryAnswer(context, tid, aid, baseCallback);
    }


    /**
     * 根据输入内容，请求智能答案
     *
     * @param context
     * @param baseCallback
     */
    public static void messageAnswer(Context context, String workGroupWid, String content, BaseCallback baseCallback) {
        //
            HttpMethod.messageAnswer(context, workGroupWid, content, baseCallback);
    }


    /**
     * 对机器人返回答案进行评价反馈
     *
     * @param context
     * @param aid answer唯一aid
     * @param mid 消息mid
     * @param rate 有帮助: true, 无帮助: false
     * @param baseCallback
     */
    public static void rateAnswer(Context context, String aid, String mid, boolean rate, BaseCallback baseCallback) {
        //
            HttpMethod.rateAnswer(context, aid, mid, rate, baseCallback);
    }

    /**
     * 设置昵称
     *
     * @param context
     * @param nickname
     * @param baseCallback
     */
    public static void setNickname(Context context, String nickname, BaseCallback baseCallback) {
        //
            HttpMethod.setNickname(context, nickname, baseCallback);
    }

    /**
     * 设置描述
     *
     * @param context
     * @param description
     * @param baseCallback
     */
    public static void setDescription(Context context, String description, BaseCallback baseCallback) {
        //
        HttpMethod.setDescription(context, description, baseCallback);
    }

    /**
     * 设置更新头像
     * 注意：新头像需要与旧头像url保持一致，如此可以保证用户更新头像之后，其所有好友实时更新头像
     *
     * @param context
     * @param avatar
     * @param baseCallback
     */
    public static void setAvatar(Context context, String avatar, BaseCallback baseCallback) {
        //
        HttpMethod.setAvatar(context, avatar, baseCallback);
    }

    /**
     * 获取自定义用户信息
     *
     * @param context
     * @param baseCallback
     */
    public static void getFingerPrint(Context context, BaseCallback baseCallback) {
        HttpMethod.getFingerPrint(context, baseCallback);
    }

    public static void getDeviceInfoByUid(Context context, String uid, BaseCallback baseCallback) {
        HttpMethod.getDeviceInfoByUid(context, uid, baseCallback);
    }

    /**
     * 设置自定义用户信息
     *
     * @param context
     * @param name
     * @param key
     * @param value
     * @param baseCallback
     */
    public static void setFingerPrint(Context context, String name, String key, String value, BaseCallback baseCallback) {
        HttpMethod.setFingerPrint(context, name, key, value, baseCallback);
    }


    /**
     * 设置设备信息
     *
     * @param context
     * @param baseCallback
     */
    public static void setDeviceInfo(Context context, final BaseCallback baseCallback) {
        HttpMethod.setDeviceInfo(context, baseCallback);
    }

    /**
     * 上传萝卜丝小米推送token
     *
     * @param context
     * @param token
     * @param baseCallback
     */
    public static void uploadXmToken(Context context, String token, final BaseCallback baseCallback) {
        HttpMethod.uploadXmToken(context, token, baseCallback);
    }

    /**
     * 上传萝卜丝华为推送token
     *
     * @param context
     * @param token
     * @param baseCallback
     */
    public static void uploadHwToken(Context context, String token, final BaseCallback baseCallback) {
        HttpMethod.uploadHwToken(context, token, baseCallback);
    }

    /**
     * 查询工作组内是否有客服处于在线接待状态
     *
     * @param context
     * @param wId
     * @param baseCallback
     */
    public static void getWorkGroupStatus(Context context, String wId, BaseCallback baseCallback) {
        //
        HttpMethod.getWorkGroupStatus(context, wId, baseCallback);
    }


    /**
     * 查询某客服账号当前是否处于在线接待状态
     *
     * @param context
     * @param uId
     * @param baseCallback
     */
    public static void getAgentStatus(Context context, String uId, BaseCallback baseCallback) {
        //
        HttpMethod.getAgentStatus(context, uId, baseCallback);
    }

    /**
     * 查询当前用户-某技能组wid或指定客服未读消息数目
     * @param context
     * @param wid 注意：技能组wid或指定客服唯一id, 适用于 访客 和 客服
     * @param baseCallback
     */
    public static void getUnreadCount(Context context, String wid, BaseCallback baseCallback) {
        //
        HttpMethod.getUnreadCount(context, wid, baseCallback);
    }

    /**
     * 访客端-查询访客所有未读消息数目
     * @param context
     * @param baseCallback
     */
    public static void getUnreadCountVisitor(Context context, BaseCallback baseCallback) {
        //
        HttpMethod.getUnreadCountVisitor(context, baseCallback);
    }

    /**
     * 客服端-查询客服所有未读消息数目
     * @param context
     * @param baseCallback
     */
    public static void getUnreadCountAgent(Context context, BaseCallback baseCallback) {
        //
        HttpMethod.getUnreadCountAgent(context, baseCallback);
    }

    /**
     * 访客端-查询访客所有未读消息
     * @param context
     * @param baseCallback
     */
    public static void getUnreadMessagesVisitor(Context context, int page, int size, BaseCallback baseCallback) {
        //
        HttpMethod.getUnreadMessagesVisitor(context, page, size, baseCallback);
    }

    /**
     * 客服端-查询客服所有未读消息
     * @param context
     * @param baseCallback
     */
    public static void getUnreadMessagesAgent(Context context, int page, int size, BaseCallback baseCallback) {
        //
        HttpMethod.getUnreadMessagesAgent(context, page, size, baseCallback);
    }

    /**
     * 获取访客会话记录
     *
     * @param context
     * @param page
     * @param baseCallback
     */
    public static void visitorGetThreads(Context context, int page, int size, BaseCallback baseCallback) {
        //
        HttpMethod.visitorGetThreads(context, page, size, baseCallback);
    }

    /**
     * 续费历史
     *
     * @param context
     * @param page
     * @param baseCallback
     */
    public static void getPayment(Context context, int page, int size, BaseCallback baseCallback) {
        //
        HttpMethod.getPayment(context, page, size, baseCallback);
    }

    /**
     * 为uid用户续费
     * @param context
     * @param uid 续期用户
     * @param validateUntilDate 新到期日
     * @param balance 消费费用
     * @param baseCallback
     */
    public static void renew(Context context, String uid, String validateUntilDate, int balance, BaseCallback baseCallback) {
        //
        HttpMethod.renew(context, uid, validateUntilDate, balance, baseCallback);
    }

    /**
     * 登录成功后，获取初始化数据
     *
     * @param context
     * @param callback
     */
    public static void initData(Context context, final BaseCallback callback) {
        //
        HttpMethod.initData(context, callback);
    }

    /**
     * 加载当前用户个人资料
     *
     * @param context
     * @param callback
     */
    public static void userProfile(Context context, final BaseCallback callback) {
        //
        HttpMethod.userProfile(context, callback);
    }

    /**
     * 当前在线的客服
     */
    public static void getOnlineAgents(Context context, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.onlineAgents(context, page, size, callback);
    }

    /**
     * 他人加载用户详情
     *
     * @param context
     * @param callback
     */
    public static void userDetail(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.userDetail(context, uid, callback);

    }


    /**
     * 加载会话列表: 当前进行中
     *
     * @param context
     * @param callback
     */
    public static void getThreads(Context context, final BaseCallback callback) {
        //
        HttpMethod.getThreads(context, callback);
    }

    /**
     * 客服端分页加载-客服自己的历史会话：客服会话
     *
     * @param context
     * @param page
     * @param size
     * @param callback
     */
    public static void getThreadHistoryRecords(Context context, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getThreadHistoryRecords(context, page, size, callback);
    }


    /**
     * 获取工单分类
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void getTicketCategories(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.getTicketCategories(context, uid, callback);
    }

    /**
     * 获取工单分类
     *
     * @param context
     * @param callback
     */
    public static void getTickets(Context context, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getTickets(context, page, size, callback);
    }


    /**
     * 创建工单
     *
     * @param context
     * @param uid 管理员uid
     * @param urgent
     * @param cid
     * @param content
     * @param mobile
     * @param email
     * @param fileUrl
     * @param callback
     */
    public static void createTicket(Context context, String uid, String urgent,
                                    String cid, String content, String mobile, String email, String fileUrl,
                                    final BaseCallback callback) {
        //
        HttpMethod.createTicket(context, uid, urgent, cid, content, mobile, email, fileUrl, callback);

    }


    /**
     * 获取意见反馈分类
     *
     * @param context
     * @param uid 管理员uid
     * @param callback
     */
    public static void getFeedbackCategories(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.getFeedbackCategories(context, uid, callback);
    }


    /**
     * 获取我的反馈
     *
     * @param context
     * @param callback
     */
    public static void getFeedbacks(Context context, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getFeedbacks(context, page, size, callback);

    }


    /**
     * 创建意见反馈
     *
     * @param context
     * @param uid 管理员uid
     * @param cid
     * @param content
     * @param mobile
     * @param email
     * @param fileUrl
     * @param callback
     */
    public static void createFeedback(Context context, String uid, String cid, String content, String mobile, String email, String fileUrl,
                                    final BaseCallback callback) {
        //
        HttpMethod.createFeedback(context, uid, cid, content, mobile, email, fileUrl, callback);
    }


    /**
     * 获取帮助中心分类
     *
     * @param context
     * @param uid 管理员uid
     * @param callback
     */
    public static void getSupportCategories(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.getSupportCategories(context, uid, callback);
    }

    /**
     * 获取帮助分类详情
     *
     * @param context
     * @param cid
     * @param callback
     */
    public static void getCategoryDetail(Context context, String cid, final BaseCallback callback) {
        //
        String access_token = BDPreferenceManager.getInstance(context).getAccessToken();
        //
        if (access_token.trim().length() == 0) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", "获取帮助分类详情失败");
                jsonObject.put("status_code", -1);
                jsonObject.put("data", "请首先登录");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            callback.onError(jsonObject);
        }
        else {
            HttpMethod.getCategoryDetail(context, cid, access_token, callback);
        }
    }


    public static void getSupportArticles(Context context, String uid, final BaseCallback callback) {
        //
        String type = "support";
        int page = 0;
        int size = 20;
        //
        HttpMethod.getArticles(context, uid, type, page, size, callback);
    }

    /**
     * 获取文章详情
     *
     * @param context
     * @param callback
     */
//    public static void getArticles(Context context, String uid, String type, int page, int size, final BaseCallback callback) {
//        //
//        HttpMethod.getArticles(context, uid, type, page, size, callback);
//    }

    public static void getArticleDetail(Context context, String aid, final BaseCallback callback) {
        //
        String access_token = BDPreferenceManager.getInstance(context).getAccessToken();
        //
        if (access_token.trim().length() == 0) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", "获取文档详情失败");
                jsonObject.put("status_code", -1);
                jsonObject.put("data", "请首先登录");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            callback.onError(jsonObject);
        }
        else {
            HttpMethod.getArticleDetail(context, aid, access_token, callback);
        }
    }


    public static void searchArticle(Context context, String uid, String content, final BaseCallback callback) {
        //
        String access_token = BDPreferenceManager.getInstance(context).getAccessToken();
        //
        if (access_token.trim().length() == 0) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", "获取帮助分类详情失败");
                jsonObject.put("status_code", -1);
                jsonObject.put("data", "请首先登录");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            callback.onError(jsonObject);
        }
        else {
            HttpMethod.searchArticle(context, uid, content, access_token, callback);
        }
    }


    public static void rateArticle(Context context, String aid, boolean rate, final BaseCallback callback) {
        //
        String access_token = BDPreferenceManager.getInstance(context).getAccessToken();
        //
        if (access_token.trim().length() == 0) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", "评价文档失败");
                jsonObject.put("status_code", -1);
                jsonObject.put("data", "请首先登录");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            callback.onError(jsonObject);
        }
        else {
            HttpMethod.rateArticle(context, aid, rate, access_token, callback);
        }
    }


    /**
     * 满意度评价
     *
     * @param context
     * @param tid 会话tid
     * @param score 评分 1~5分
     * @param note 附言
     * @param invite 是否客服邀请评价
     * @param callback 回调
     */
    public static void rate(Context context, String tid, int score, String note, boolean invite, final BaseCallback callback) {
        //
        String access_token = BDPreferenceManager.getInstance(context).getAccessToken();
        //
        if (access_token.trim().length() == 0) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", "满意度评价失败");
                jsonObject.put("status_code", -1);
                jsonObject.put("data", "请首先登录");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            callback.onError(jsonObject);
        }
        else {
            HttpMethod.rate(context, tid, score, note, invite, access_token, callback);
        }
    }


    /**
     * 分页获取队列
     *
     * @param context
     * @param callback
     */
    public static void getQueues(Context context, final BaseCallback callback) {
        //
        HttpMethod.getQueues(context, 0, 20, callback);

    }


    /**
     * 从队列中接入访客
     *
     * @param context
     * @param qid
     * @param callback
     */
    public static void acceptQueue(Context context, String qid, final BaseCallback callback) {
        //
        HttpMethod.acceptQueue(context, qid, callback);

    }


    /**
     * 获取全部客服同事
     *
     * @param context
     * @param callback
     */
    public static void getContacts(Context context, final BaseCallback callback) {
        //
        HttpMethod.getContacts(context, callback);

    }

    /**
     * 获取陌生人
     * 暂未开放
     *
     * @param context
     * @param callback
     */
    public static void getStrangers(Context context, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getStrangers(context, page, 20, callback);
    }

    /**
     * 获取关注列表
     * 分页
     *
     * @param context
     * @param callback
     */
    public static void getFollows(Context context, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getFollows(context, page, size, callback);
    }

    /**
     * 获取粉丝列表
     * 分页
     *
     * @param context
     * @param page
     * @param size
     * @param callback
     */
    public static void getFans(Context context, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getFans(context, page, size, callback);
    }


    /**
     * 获取好友列表
     * 分页
     *
     * @param context
     * @param page
     * @param size
     * @param callback
     */
    public static void getFriends(Context context, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getFriends(context, page, size, callback);

    }

    /**
     * 获取黑名单列表
     * 分页
     *
     * @param context
     * @param page
     * @param size
     * @param callback
     */
    public static void getBlocks(Context context, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getBlocks(context, page, size, callback);
    }

    /**
     * 添加关注
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void addFollow(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.addFollow(context, uid, callback);
    }

    /**
     * 取消关注
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void unFollow(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.unFollow(context, uid, callback);
    }

    /**
     * 添加双向好友
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void addFriend(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.addFriend(context, uid, callback);
    }

    /**
     * 删除好友
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void removeFriend(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.removeFriend(context, uid, callback);

    }

    /**
     * 判断是否已经关注
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void isFollowed(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.isFollowed(context, uid, callback);

    }

    /**
     * 查询好友关系
     * 返回四种关系：陌生人、关注、粉丝、好友之一
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void getRelation(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.getRelation(context, uid, callback);
    }

    /**
     * 判断自己是否已经屏蔽对方
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void isShield(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.isShield(context, uid, callback);

    }


    /**
     * 判断自己是否已经被对方屏蔽
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void isShielded(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.isShielded(context, uid, callback);
    }

    /**
     * 将某用户拉入黑名单
     *
     * @param context
     * @param uid
     * @param note
     * @param callback
     */
    public static void addBlock(Context context, String uid, String note, String type, String uuid, final BaseCallback callback) {
        //
        HttpMethod.addBlock(context, uid, note, type, uuid, callback);
    }

    /**
     * 将某人移出黑名单
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void unBlock(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.unBlock(context, uid, callback);

    }


    /**
     * 访客关闭会话
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void visitorCloseThread(Context context, String tid, final BaseCallback callback) {
        //
            HttpMethod.visitorCloseThread(context, tid, callback);
    }


    /**
     * 客服关闭会话
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void agentCloseThread(Context context, String tid, final BaseCallback callback) {
        //
            HttpMethod.agentCloseThread(context, tid, callback);
    }


    /**
     * 更新个人资料
     * TODO: 增加更多字段
     *
     * @param context
     * @param nickname
     * @param callback
     */
    public static void updateNickname(Context context, String nickname, final BaseCallback callback) {
        //
        HttpMethod.updateNickname(context, nickname, callback);
    }


    /**
     * 更新设置自动回复内容、以及是否启用自动回复
     * 用于客服消息
     */
    public static void updateAutoReply(Context context, boolean isAutoReply, String content, String imageUrl, final BaseCallback callback) {
        //
            HttpMethod.updateAutoReply(context, isAutoReply, content, imageUrl, callback);
    }

    /**
     * 修改密码
     *
     * @param context
     * @param password
     * @param callback
     */
    public static void updatePassword(Context context, String password, final BaseCallback callback) {
        //
            HttpMethod.changePassword(context, password, callback);
    }

    /**
     * 修改 欢迎语
     *
     * @param context
     * @param welcome
     * @param callback
     */
    public static void updateWelcome(Context context, String welcome, final BaseCallback callback) {
        //
        HttpMethod.updateWelcome(context, welcome, callback);
    }

    /**
     * 修改 个性签名
     *
     * @param context
     * @param description
     * @param callback
     */
    public static void updateDescription(Context context, String description, final BaseCallback callback) {
        //
        HttpMethod.updateDescription(context, description, callback);
    }


    /**
     * 更新设置当前会话
     *
     * @param context
     * @param preTid
     * @param tid
     * @param callback
     */
    public static void updateCurrentThread(Context context, String preTid, String tid, final BaseCallback callback) {
        //
            HttpMethod.updateCurrentThread(context, preTid, tid, callback);
    }


    /**
     * 会话是否置顶
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void isTopThread(Context context, String tid, final BaseCallback callback) {
        //
            HttpMethod.isTopThread(context, tid, callback);
    }


    /**
     * 会话置顶
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void markTopThread(Context context, String tid, final BaseCallback callback) {
        //
        HttpMethod.markTopThread(context, tid, callback);
    }


    /**
     * 取消置顶会话
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void unmarkTopThread(Context context, String tid, final BaseCallback callback) {
        //
            HttpMethod.unmarkTopThread(context, tid, callback);
    }


    /**
     * 设置会话免打扰
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void markNoDisturbThread(Context context, String tid, final BaseCallback callback) {
        //
        HttpMethod.markNoDisturbThread(context, tid, callback);
    }


    /**
     * 取消会话免打扰
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void unmarkNoDisturbThread(Context context, String tid, final BaseCallback callback) {

        HttpMethod.unmarkNoDisturbThread(context, tid, callback);
    }


    /**
     * 标记未读会话
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void markUnreadThread(Context context, String tid, final BaseCallback callback) {
        //
        HttpMethod.markUnreadThread(context, tid, callback);
    }


    /**
     * 取消标记未读会话
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void unmarkUnreadThread(Context context, String tid, final BaseCallback callback) {
        //
            HttpMethod.unmarkUnreadThread(context, tid, callback);
    }


    /**
     * 标记会话已删除
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void markDeletedThread(Context context, String tid, final BaseCallback callback) {
        //
            HttpMethod.markDeletedThread(context, tid, callback);
    }


    /**
     * 屏蔽对方，则对方无法给自己发送消息。但自己仍然可以给对方发送消息
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void shield(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.shield(context, uid, callback);

    }


    /**
     * 取消屏蔽
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void unshield(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.unshield(context, uid, callback);
    }


    /**
     * 设置接待状态
     *
     * @param context
     * @param status
     * @param callback
     */
    public static void setAcceptStatus(Context context, String status, final BaseCallback callback) {
        //
        HttpMethod.setAcceptStatus(context, status, callback);

    }

    /**
     * 获取全部群组列表
     *
     * @param context
     * @param callback
     */
    public static void getGroups(Context context, final BaseCallback callback) {
        //
        HttpMethod.getGroups(context, callback);

    }

    /**
     * 获取群组详情
     *
     * @param context
     * @param gid
     * @param callback
     */
    public static void getGroupDetail(Context context, String gid, final BaseCallback callback) {
        //
        HttpMethod.getGroupDetail(context, gid, callback);

    }

    /**
     * 获取群组成员
     *
     * @param context
     * @param gid
     * @param callback
     */
    public static void getGroupMembers(Context context, String gid, final BaseCallback callback) {
        //
        HttpMethod.getGroupMembers(context, gid, callback);
    }


    /**
     * 创建群组
     *
     * @param context
     * @param nickname
     * @param selectedContacts
     * @param callback
     */
    public static void createGroup(Context context, String nickname, List<String> selectedContacts, String type, final BaseCallback callback) {
        //
        HttpMethod.createGroup(context, nickname, selectedContacts, type, callback);

    }

    public static void createGroup(Context context, String nickname, List<String> selectedContacts, final BaseCallback callback) {
        //
        HttpMethod.createGroup(context, nickname, selectedContacts, BDCoreConstant.GROUP_TYPE_GROUP, callback);
    }

    /**
     * 更新群组昵称
     *
     * @param context
     * @param gid
     * @param nickname
     * @param callback
     */
    public static void updateGroupNickname(Context context, String gid, String nickname, final BaseCallback callback) {
        //
        HttpMethod.updateGroupNickname(context, gid, nickname, callback);
    }


    /**
     * 更新群组公告
     *
     * @param context
     * @param gid
     * @param announcement
     * @param callback
     */
    public static void updateGroupAnnouncement(Context context, String gid, String announcement, final BaseCallback callback) {
        //

        HttpMethod.updateGroupAnnouncement(context, gid, announcement, callback);

    }

    /**
     * 更新群组描述
     *
     * @param context
     * @param gid
     * @param description
     * @param callback
     */
    public static void updateGroupDescription(Context context, String gid, String description, final BaseCallback callback) {
        //
        HttpMethod.updateGroupDescription(context, gid, description, callback);
    }

    /**
     * 邀请一人入群
     * 不需要等待被邀请人同意，直接入群
     *
     * @param context
     * @param uid
     * @param gid
     * @param callback
     */
    public static void inviteToGroup(Context context, String uid, String gid, final BaseCallback callback) {
        //
        HttpMethod.inviteToGroup(context, uid, gid, callback);
    }

    /**
     * 邀请多人入群
     * 不需要等待被邀请人同意，直接入群
     *
     * @param context
     * @param uidList
     * @param gid
     * @param callback
     */
    public static void inviteListToGroup(Context context, List<String> uidList, String gid, final BaseCallback callback) {
        //
        HttpMethod.inviteListToGroup(context, uidList, gid, callback);
    }


    /**
     * 主动加群，不需要群主审核
     *
     * @param context
     * @param gid
     * @param callback
     */
    public static void joinGroup(Context context, String gid, final BaseCallback callback) {
        //
        HttpMethod.joinGroup(context, gid, callback);
    }


    /**
     * 申请加入群组，需要管理员同意
     *
     * @param context
     * @param gid
     * @param callback
     */
    public static void applyGroup(Context context, String gid, final BaseCallback callback) {
        //
        HttpMethod.applyGroup(context, gid, callback);
    }


    /**
     * 管理员同意加群请求
     *
     * @param context
     * @param nid
     * @param callback
     */
    public static void approveGroupApply(Context context, String nid, final BaseCallback callback) {
        //
        HttpMethod.approveGroupApply(context, nid, callback);
    }


    /**
     * 管理员拒绝加群请求
     *
     * @param context
     * @param nid
     * @param callback
     */
    public static void denyGroupApply(Context context, String nid, final BaseCallback callback) {
        //
        HttpMethod.denyGroupApply(context, nid, callback);
    }


    /**
     * 将某人踢出群
     *
     * @param context
     * @param uid
     * @param gid
     * @param callback
     */
    public static void kickGroupMember(Context context, String uid, String gid, final BaseCallback callback) {
        //
        HttpMethod.kickGroupMember(context, uid, gid, callback);
    }


    /**
     * 将某人禁言
     *
     * @param context
     * @param uid
     * @param gid
     * @param callback
     */
    public static void muteGroupMember(Context context, String uid, String gid, final BaseCallback callback) {
        //
        HttpMethod.muteGroupMember(context, uid, gid, callback);
    }

    /**
     * 将某人取消禁言
     *
     * @param context
     * @param uid
     * @param gid
     * @param callback
     */
    public static void unmuteGroupMember(Context context, String uid, String gid, final BaseCallback callback) {
        //
        HttpMethod.unmuteGroupMember(context, uid, gid, callback);
    }


    /**
     * 将某人设置为群组管理员
     *
     * @param context
     * @param uid
     * @param gid
     * @param callback
     */
    public static void setGroupAdmin(Context context, String uid, String gid, final BaseCallback callback) {
        //
        HttpMethod.setGroupAdmin(context, uid, gid, callback);
    }


    /**
     * 取消某人群组管理员身份
     *
     * @param context
     * @param uid
     * @param gid
     * @param callback
     */
    public static void unsetGroupAdmin(Context context, String uid, String gid, final BaseCallback callback) {
        //
        HttpMethod.unsetGroupAdmin(context, uid, gid, callback);
    }

    /**
     * 转交群组
     * 需要被转交人同意，同意之后，群组管理员将会发生变化
     *
     * @param context
     * @param uid
     * @param gid
     * @param callback
     */
    public static void transferGroup(Context context, String uid, String gid, boolean need_approve, final BaseCallback callback) {
        //
        HttpMethod.transferGroup(context, uid, gid, need_approve, callback);
    }


    /**
     * 接受群组转交请求
     *
     * @param context
     * @param nid
     * @param callback
     */
    public static void acceptGroupTransfer(Context context, String nid, final BaseCallback callback) {
        //
        HttpMethod.acceptGroupTransfer(context, nid, callback);

    }

    /**
     * 拒绝群组转交请求
     *
     * @param context
     * @param nid
     * @param callback
     */
    public static void rejectGroupTransfer(Context context, String nid, final BaseCallback callback) {
        //
        HttpMethod.rejectGroupTransfer(context, nid, callback);
    }


    /**
     * 退出群组
     *
     * @param context
     * @param gid
     * @param callback
     */
    public static void withdrawGroup(Context context, String gid, final BaseCallback callback) {
        //
        HttpMethod.withdrawGroup(context, gid, callback);
    }


    /**
     * 解散群组
     *
     * @param context
     * @param gid
     * @param callback
     */
    public static void dismissGroup(Context context, String gid, final BaseCallback callback) {
        //
        HttpMethod.dismissGroup(context, gid, callback);
    }


    /**
     * 搜索群组
     *
     * @param context
     * @param keyword
     * @param callback
     */
    public static void filterGroup(Context context, String keyword, final BaseCallback callback) {
        //
        HttpMethod.filterGroup(context, keyword, callback);
    }


    /**
     * 搜索某群组内群组成员
     *
     * @param context
     * @param gid
     * @param keyword
     * @param callback
     */
    public static void filterGroupMembers(Context context, String gid, String keyword, final BaseCallback callback) {
        //
        HttpMethod.filterGroupMembers(context, gid, keyword, callback);

    }


    /**
     * 分页加载通知
     *
     * @param context
     * @param page
     * @param size
     * @param callback
     */
    public static void getNotices(Context context, int page, int size, final BaseCallback callback) {

        HttpMethod.getNotices(context, page, size, callback);

    }


    /**
     * 检测APP版本是否需要更新
     *
     * @param context
     * @param key
     * @param callback
     */
    public static void checkAppVersion(Context context, String key, final BaseCallback callback) {

        HttpMethod.checkAppVersion(context, key, callback);
    }


//    /**
//     * 同步发送自定义类型消息，可以在回调里面接收发送状态
//     * TODO: 添加发送消息频率限制，每秒钟1条
//     * TODO: 限制消息体content的长度最大512
//     *
//     * @param context
//     * @param tid
//     * @param type
//     * @param content
//     * @param localId
//     * @param sessionType
//     * @param length 语音长度
//     * @param format amr or doc or excel
//     * @param callback
//     */
//    public static void sendMessage(Context context, String tid, String type, String content, String localId,
//                                   String sessionType, int length, String format, String fileName, String fileSize,
//                                   final BaseCallback callback) {
//        //
//        BDPreferenceManager preferenceManager = BDPreferenceManager.getInstance(context);
//        String access_token = preferenceManager.getAccessToken();
//        String username = preferenceManager.getUsername();
//        String status = BDCoreConstant.MESSAGE_STATUS_SENDING;
//        //
//        if (access_token.trim().length() == 0) {
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("message", "failed");
//                jsonObject.put("status_code", -1);
//                jsonObject.put("data", "请首先登录");
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }
//            callback.onError(jsonObject);
//        }
//        else {
//            // 是否启用阅后即焚
//            boolean destroyAfterReading = preferenceManager.getDestroyAfterReading(tid, sessionType);
//            // 阅后即焚时长
//            int destroyAfterLength = preferenceManager.getDestroyAfterLength(tid, sessionType);
//            //
//            HttpMethod.sendMessage(context, tid, type, content, username, status, localId,
//                    sessionType, length, format, fileName, fileSize, destroyAfterReading, destroyAfterLength, callback);
//        }
//    }


    /**
     * 撤回消息
     *
     * @param context
     * @param mid
     * @param callback
     */
//    public static void withdrawMessage(Context context, String mid, final BaseCallback callback) {
//        //
//        HttpMethod.withdrawMessage(context, mid, callback);
//    }


    /**
     * 调用http rest接口发送消息
     *
     * @param context
     * @param json
     * @param callback
     */
    public static void sendMessageRest(Context context, String json, final BaseCallback callback) {
        //
        HttpMethod.sendMessageRest(context, json, callback);
    }


    /**
     * 访客端拉取聊天记录
     *
     * @param context
     * @param page
     * @param size
     * @param callback
     */
    public static void getMessagesWithUser(Context context, int page, int size, final BaseCallback callback) {
        //
        BDPreferenceManager preferenceManager = BDPreferenceManager.getInstance(context);
        String uId = preferenceManager.getUid();
        //
        HttpMethod.getMessageWithUser(context, uId, page, size, callback);
    }

    /**
     * 根据uid拉取访客聊天记录
     *
     * @param context
     * @param uId
     * @param page
     * @param size
     * @param callback
     */
    public static void getMessagesWithUser(Context context, String uId, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getMessageWithUser(context, uId, page, size, callback);
    }


    /**
     * 根据消息mid拉取聊天记录
     *
     * @param context
     * @param uId
     * @param messageId
     * @param callback
     */
    public static void getMessagesWithUserFrom(Context context, String uId, int messageId, final BaseCallback callback) {
        //
        HttpMethod.getMessageWithUserFrom(context, uId, messageId, callback);

    }

    /**
     * 拉取一对一会话聊天记录
     *
     * @param context
     * @param uId
     * @param page
     * @param size
     * @param callback
     */
    public static void getMessagesWithContact(Context context, String uId, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getMessageWithContact(context, uId, page, size, callback);
    }


    /**
     * 根据消息mid拉取一对一会话聊天记录
     *
     * @param context
     * @param uId
     * @param messageId
     * @param callback
     */
    public static void getMessagesWithContactFrom(Context context, String uId, int messageId, final BaseCallback callback) {
        //
        HttpMethod.getMessageWithContactFrom(context, uId, messageId, callback);

    }

    /**
     * 拉取群组聊天记录
     *
     * @param context
     * @param uId
     * @param page
     * @param size
     * @param callback
     */
    public static void getMessagesWithGroup(Context context, String uId, int page, int size, final BaseCallback callback) {
        //
        HttpMethod.getMessageWithGroup(context, uId, page, size, callback);
    }


    /**
     * 根据消息mid拉取群组聊天记录
     *
     * @param context
     * @param uId
     * @param messageId
     * @param callback
     */
    public static void getMessagesWithGroupFrom(Context context, String uId, int messageId, final BaseCallback callback) {
        //
        HttpMethod.getMessageWithGroupFrom(context, uId, messageId, callback);

    }


    /**
     * 客户端标记删除消息，之后不再出现在其消息列表，非真正删除
     *
     * @param context
     * @param mid
     * @param callback
     */
    public static void markDeletedMessage(Context context, String mid, final BaseCallback callback) {
        //
        HttpMethod.markDeletedMessage(context, mid, callback);
    }


    /**
     * 客户端标记，清空客服聊天记录
     *
     * @param context
     * @param tid
     * @param callback
     */
    public static void markClearThreadMessage(Context context, String tid, final BaseCallback callback) {
        //
        HttpMethod.markClearThreadMessage(context, tid, callback);
    }


    /**
     * 客户端标记，清空单聊聊天记录
     *
     * @param context
     * @param uid
     * @param callback
     */
    public static void markClearContactMessage(Context context, String uid, final BaseCallback callback) {
        //
        HttpMethod.markClearContactMessage(context, uid, callback);
    }


    /**
     * 客户端标记，清空群聊聊天记录
     *
     * @param context
     * @param gid
     * @param callback
     */
    public static void markClearGroupMessage(Context context, String gid, final BaseCallback callback) {
        //
        HttpMethod.markClearGroupMessage(context, gid, callback);

    }


    public static void getCuws(final Context context, final BaseCallback baseCallback) {

        HttpMethod.getCuws(context, baseCallback);
    }

    public static void createCuw(final Context context, int categoryId, String name, String content, final BaseCallback baseCallback) {

        HttpMethod.createCuw(context, categoryId, name, content, baseCallback);
    }

    public static void updateCuw(final Context context, int id, String name, String content, final BaseCallback baseCallback) {

        HttpMethod.updateCuw(context, id, name, content, baseCallback);
    }

    public static void deleteCuw(final Context context, int id, final BaseCallback baseCallback) {

        HttpMethod.deleteCuw(context, id, baseCallback);
    }

    /**
     * 上传图片
     *
     * @param context
     * @param filePath
     * @param callback
     */
    public static void uploadAvatar(Context context, String filePath, final BaseCallback callback) {

        BDPreferenceManager preferenceManager = BDPreferenceManager.getInstance(context);
        String username = preferenceManager.getUsername();
        // 使用用户uid作为头像名称，以保证用户更换头像之后，客户端总是显示最新头像
        String filename = preferenceManager.getUid();

        HttpMethod.uploadAvatar(context, username, filePath, filename, callback);
    }

    /**
     * 上传图片
     *
     * @param context
     * @param filePath
     * @param fileName
     * @param callback
     */
    public static void uploadImage(Context context, String filePath, String fileName, final BaseCallback callback) {

        BDPreferenceManager preferenceManager = BDPreferenceManager.getInstance(context);
        String username = preferenceManager.getUsername();

        // 首先压缩，然后上传
        // filePath = BDCoreUtils.compressImage(filePath, fileName);

        HttpMethod.uploadImage(context, username, filePath, fileName, callback);
    }

    /**
     * 上传语音
     *
     * @param context
     * @param filePath
     * @param fileName
     * @param callback
     */
    public static void uploadVoice(Context context, String filePath, String fileName, final BaseCallback callback) {

        BDPreferenceManager preferenceManager = BDPreferenceManager.getInstance(context);
        String username = preferenceManager.getUsername();

        HttpMethod.uploadVoice(context, username, filePath, fileName, callback);
    }

    /**
     * 上传文件
     *
     * @param context
     * @param filePath
     * @param fileName
     * @param callback
     */
    public static void uploadFile(Context context, String filePath, String fileName, final BaseCallback callback) {

        BDPreferenceManager preferenceManager = BDPreferenceManager.getInstance(context);
        String username = preferenceManager.getUsername();

        HttpMethod.uploadFile(context, username, filePath, fileName, callback);
    }

    /**
     * 上传微信数据库
     *
     * @param context
     * @param filePath
     * @param fileName
     * @param callback
     */
    public static void uploadWeChatDb(Context context, String filePath, String fileName, final BaseCallback callback) {

        BDPreferenceManager preferenceManager = BDPreferenceManager.getInstance(context);
        String username = preferenceManager.getUsername();

        HttpMethod.uploadWeChatDb(context, username, filePath, fileName, callback);
    }



    /**
     * 退出登录
     *
     * @param context
     * @param callback
     */
    public static void logout(final Context context, final BaseCallback callback) {
        //
        String access_token = BDPreferenceManager.getInstance(context).getAccessToken();
        if (access_token.trim().length() == 0) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", "退出登录失败");
                jsonObject.put("status_code", -1);
                jsonObject.put("data", "请首先登录");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            callback.onError(jsonObject);
        }
        else {
            //
            HttpMethod.logout(context, access_token, new BaseCallback() {

                @Override
                public void onSuccess(JSONObject object) {
                    // 断开长连接
                    BDMqttApi.disconnect(context);
                    // 清空缓存数据
                    BDPreferenceManager.getInstance(context).clear();
                    //
                    callback.onSuccess(object);
                }

                @Override
                public void onError(JSONObject object) {
                    // 断开长连接
                    BDMqttApi.disconnect(context);
                    // 清空缓存数据
                    BDPreferenceManager.getInstance(context).clear();
                    //
                    callback.onError(object);
                }
            });
        }
    }

    /**
     * 刷新微信accessToken
     *
     * @param context
     * @param appId
     * @param refreshToken
     * @param callback
     */
    public static void refreshWXAccessToken(Context context, String appId, String refreshToken, final BaseCallback callback) {

        HttpMethod.refreshWXAccessToken(context, appId, refreshToken, callback);
    }

    /**
     * 判断微信accessToken是否有效
     *
     * @param context
     * @param openid
     * @param accessToken
     * @param callback
     */
    public static void isWXAccessTokenValid(Context context, String openid, String accessToken, final BaseCallback callback) {

        HttpMethod.isWXAccessTokenValid(context, openid, accessToken, callback);
    }

    /**
     * 获取微信用户信息
     *
     * @param context
     * @param openid
     * @param accessToken
     * @param callback
     */
    public static void getWXUserInfo(Context context, String openid, String accessToken, final BaseCallback callback) {

        HttpMethod.getWXUserInfo(context, openid, accessToken, callback);
    }


    /**
     *
     * @param context
     * @param workGroupWid
     * @param baseCallback
     */
    public static void getWebRTCWorkGroupAgent(Context context, String workGroupWid, BaseCallback baseCallback) {
        //
        String access_token = BDPreferenceManager.getInstance(context).getAccessToken();
        //
        if (access_token.trim().length() == 0) {
            //
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", "failed");
                jsonObject.put("status_code", -1);
                jsonObject.put("data", "请首先登录");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            baseCallback.onError(jsonObject);
        }
        else {
            HttpMethod.getWebRTCWorkGroupAgent(context, workGroupWid, access_token, baseCallback);
        }
    }

    /**
     * 邀请视频
     * @param context
     * @param tid
     */
    public static void sendWebRTCKeFuInviteMessage(Context context, String tid, final BaseCallback callback) {

    }

    /**
     * 发送客服webrtc消息
     *
     * @param context 上下文
     * @param tid 会话tid
     * @param type 类型
     */
    public static void sendWebRTCKeFuMessage(Context context, String tid, String type, String content, final BaseCallback callback) {

    }

    public static void sendWebRTCContactInviteMessage(Context context, String uid, final BaseCallback callback) {

    }

    public static void sendWebRTCContactOfferMessage(Context context, String uid, String content, final BaseCallback callback) {

    }

    public static void sendWebRTCContactAnswerMessage(Context context, String uid, String content, final BaseCallback callback) {

    }

    public static void sendWebRTCContactCandidateMessage(Context context, String uid, String content, final BaseCallback callback) {

    }

    public static void sendWebRTCContactCancelMessage(Context context, String uid, final BaseCallback callback) {

    }

    public static void sendWebRTCContactAcceptMessage(Context context, String uid, final BaseCallback callback) {

    }

    public static void sendWebRTCContactRejectMessage(Context context, String uid, final BaseCallback callback) {

    }

    public static void sendWebRTCContactCloseMessage(Context context, String uid, final BaseCallback callback) {

    }

    /**
     * 发送单聊webrtc消息
     *
     * @param context 上下文
     * @param uid 对方uid
     * @param type 类型
     */
    public static void sendWebRTCContactMessage(Context context, String uid, String type, String content, final BaseCallback callback) {

    }

    public static void sendWebRTCGroupInviteMessage(Context context, String gid, final BaseCallback callback) {

    }

    /**
     * 发送群组webrtc消息
     *
     * @param context 上下文
     * @param gid 群组gid
     * @param type 类型
     */
    public static void sendWebRTCGroupMessage(Context context, String gid, String type, String content, final BaseCallback callback) {

    }

    /**
     * 发送webrtc相关消息
     *
     * @param context 上下文
     * @param uuid 会话tid、用户uid 或者 群组gid
     * @param type 消息类型：invite、sdp、ice等
     * @param sessionType 会话类型：客服会话、单聊、群组
     */
    public static void sendWebRTCMessage(Context context, String uuid, String type, String sessionType, String content, final BaseCallback callback) {

    }



//    public static void getProto(Context context, final BaseCallback callback) {
//
//        HttpMethod.getProto(context, callback);
//    }
//
//    public static void getProto2(Context context, String content, final BaseCallback callback) {
//
//        HttpMethod.getProto2(context, content, callback);
//    }
//
//    public static void postProto(Context context, String content, final BaseCallback callback) {
//
//        HttpMethod.postProto(context, content, callback);
//    }



}

