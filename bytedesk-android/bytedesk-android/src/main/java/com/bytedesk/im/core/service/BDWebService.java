package com.bytedesk.im.core.service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * http请求service，所有的http rest api请求均经过此类
 * 此类中接口不对开发者直接开放，开发者只需要调用BDCoreApi中接口即可
 * @see <a href="https://square.github.io/retrofit/">Retrofit2</a> A type-safe HTTP client for Android and Java
 *
 * @author bytedesk.com
 */
public interface BDWebService {

    /**
     * 注册接口：注册匿名用户
     *
     * @param url url
     * @param subDomain subDomain
     * @param client
     * @return
     */
    @GET
    Call<ResponseBody> registerAnonymousUser(@Url String url,
                                             @Query("subDomain") String subDomain,
                                             @Query("client") String client);

    /**
     * 注册接口：自定义用户名、昵称、密码等注册用户
     *
     * @param requestBody
     * @return
     */
    @POST
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> registerUser(@Url String url, @Body RequestBody requestBody);

    /**
     * 注册接口：自定义用户名、昵称、密码等注册用户
     *
     * @param requestBody
     * @return
     */
    @POST
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> registerUserUid(@Url String url, @Body RequestBody requestBody);

    /**
     * 注册管理员
     *
     * @param requestBody
     * @return
     */
    @POST
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> registerAdmin(@Url String url, @Body RequestBody requestBody);

    /**
     * 邮箱注册
     * @param url
     * @param requestBody
     * @return
     */
    @POST
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> registerEmail(@Url String url, @Body RequestBody requestBody);


    /**
     * 登录接口：匿名用户和自定义用户名注册用户均通过此接口登录
     *
     * @param Authorization
     * @param grant_type
     * @param username
     * @param password
     * @param scope
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> login(@Url String url,
                             @Header("Authorization") String Authorization,
                             @Field("grant_type") String grant_type,
                             @Field("username") String username,
                             @Field("password") String password,
                             @Field("scope") String scope);

    /**
     * 手机号登录
     *
     * @param Authorization
     * @param grant_type
     * @param mobile
     * @param code
     * @param scope
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> loginMobile(@Url String url,
                             @Header("Authorization") String Authorization,
                             @Field("grant_type") String grant_type,
                             @Field("mobile") String mobile,
                             @Field("code") String code,
                             @Field("scope") String scope);

    /**
     * 邮箱登录
     *
     * @param Authorization
     * @param grant_type
     * @param email
     * @param code
     * @param scope
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> loginEmail(@Url String url,
                                   @Header("Authorization") String Authorization,
                                   @Field("grant_type") String grant_type,
                                   @Field("email") String email,
                                   @Field("code") String code,
                                   @Field("scope") String scope);

    /**
     * 微信登录
     *
     * @param Authorization
     * @param grant_type
     * @param unionId
     * @param scope
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> loginUnionId(@Url String url,
                                  @Header("Authorization") String Authorization,
                                  @Field("grant_type") String grant_type,
                                  @Field("unionid") String unionId,
                                  @Field("scope") String scope);

    /**
     * 请求手机短信
     *
     * @param mobile
     * @param client
     * @return
     */
    @GET
    Call<ResponseBody> requestMobileCode(@Url String url,
                                         @Query("mobile") String mobile,
                                         @Query("client") String client);

    /**
     * 请求邮箱验证码
     *
     * @param email
     * @param client
     * @return
     */
    @GET
    Call<ResponseBody> requestEmailCode(@Url String url, @Query("email") String email, @Query("client") String client);

    @POST
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> registerMobile(@Url String url, @Body RequestBody requestBody);


    @POST
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> bindMobile(@Url String url, @Body RequestBody requestBody);


    @GET
    Call<ResponseBody> isWeChatRegistered(@Url String url, @Query("unionid") String unionId, @Query("client") String client);

    /**
     * 微信登录之后注册微信用户信息到自有用户系统
     */
    @POST
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> registerWeChat(@Url String url, @Body RequestBody requestBody);


    @POST
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> bindWeChat(@Url String url, @Body RequestBody requestBody);

    /**
     * 获取客服代码
     */
    @GET
    Call<ResponseBody> getChatCode(@Url String url, @Query("client") String client);

    /**
     * 联系客服
     * 需要登录后调用
     * @param wId
     * @param type
     * @param aId
     * @param client
     * @return
     */
    @GET("thread/request")
    Call<ResponseBody> requestThread(
                                     @Query("wId") String wId,
                                     @Query("type") String type,
                                     @Query("aId") String aId,
                                     @Query("client") String client,
                                     @Query("webrtc") int webrtc);

    /**
     * 请求联系人会话
     *
     * @param cid
     * @param client
     * @return
     */
    @GET("thread/contact")
    Call<ResponseBody> getContactThread(@Query("cid") String cid,
                                        @Query("client") String client);

    /**
     * 请求群组会话
     *
     * @param gid
     * @param client
     * @return
     */
    @GET("thread/group")
    Call<ResponseBody> getGroupThread(@Query("gid") String gid,
                                      @Query("client") String client);

    /**
     * 请求人工客服，不管此工作组是否设置为默认机器人，只要有人工客服在线，则可以直接对接人工
     *
     * @param wId
     * @param type
     * @param aId
     * @param client
     * @return
     */
    @GET("thread/request/agent")
    Call<ResponseBody> requestAgent(@Query("wId") String wId,
                                     @Query("type") String type,
                                     @Query("aId") String aId,
                                     @Query("client") String client);

    /**
     * 选择问卷答案
     *
     * @param tId
     * @param itemQid
     * @param client
     * @return
     */
    @GET("thread/questionnaire")
    Call<ResponseBody> requestQuestionnaire(@Query("tId") String tId,
                                            @Query("itemQid") String itemQid,
                                            @Query("client") String client);

    /**
     * 选择工作组
     *
     * @param wId
     * @param client
     * @return
     */
    @GET("thread/choose/workGroup")
    Call<ResponseBody> chooseWorkGroup(@Query("wId") String wId,
                                       @Query("client") String client);

    /**
     * 留学，针对大学长定制
     *
     * @param wId
     * @param workGroupNickname
     * @param client
     * @return
     */
    @GET("thread/choose/workGroup/liuxue")
    Call<ResponseBody> chooseWorkGroupLiuXue(@Query("wId") String wId,
                                             @Query("nickname") String workGroupNickname,
                                             @Query("client") String client);

    /**
     * 留学，针对大学长定制 LBS 版
     * @param wId
     * @param workGroupNickname
     * @param province
     * @param city
     * @param client
     * @return
     */
    @GET("thread/choose/workGroup/liuxue/lbs")
    Call<ResponseBody> chooseWorkGroupLiuXueLBS(@Query("wId") String wId,
                                             @Query("nickname") String workGroupNickname,
                                             @Query("province") String province,
                                             @Query("city") String city,
                                             @Query("client") String client);

    /**
     * 留言
     *
     * @param requestBody
     * @return
     */
    @POST("leavemsg/save")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> leaveMessage(@Body RequestBody requestBody);

//    @POST("v2/leavemsg/save/dxz")
//    @Headers({"Content-Type:application/json; charset=utf-8"})
//    Call<ResponseBody> leaveMessageDxz(@Body RequestBody requestBody);

    @GET("leavemsg/query")
    Call<ResponseBody> getLeaveMessages(@Query("page") int page,
                                 @Query("size") int size,
                                 @Query("client") String client);

    /**
     * 回复留言
     *
     * @param requestBody
     * @return
     */
    @POST("leavemsg/reply")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> replyLeaveMessage(@Body RequestBody requestBody);

    /**
     * 初始化机器人
     *
     * @param client
     * @return
     */
    @GET("v2/answer/init")
    Call<ResponseBody> initAnswer(@Query("type") String type,
                                  @Query("wid") String workGroupWid,
                                  @Query("aid") String agentUid,
                                  @Query("client") String client);

    /**
     * 热门智能问答
     *
     * @param client
     * @return
     */
    @GET("answer/top")
    Call<ResponseBody> topAnswer(@Query("client") String client);

    /**
     * 根据aid，请求智能答案
     *
     * @param client
     * @return
     */
    @GET("answer/query")
    Call<ResponseBody> queryAnswer(@Query("tid") String tid,
                                   @Query("aid") String aid,
                                   @Query("client") String client);

    /**
     * 根据输入内容，请求智能答案
     *
     * @param client
     * @return
     */
//    @GET("v2/answer/message")
//    Call<ResponseBody> messageAnswer(@Query("type") String type,
//                                     @Query("wid") String workGroupWid,
//                                     @Query("aid") String agentUid,
//                                     @Query("content") String content,
//                                     @Query("client") String client);
    @GET("elastic/robot/message")
    Call<ResponseBody> messageAnswer(@Query("wid") String workGroupWid,
                                     @Query("content") String content,
                                     @Query("client") String client);

    @POST("answer/rate")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> rateAnswer(@Body RequestBody requestBody);

    /**
     * 设置、修改用户昵称
     * 需要登录后调用
     *
     * @param requestBody
     * @return
     */
    @POST("user/nickname")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> setNickname(@Body RequestBody requestBody);

    /**
     * 设置、修改头像
     * @param requestBody
     * @return
     */
    @POST("user/avatar")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> setAvatar(@Body RequestBody requestBody);


    /**
     * 获取开发者自定义用户key/value信息
     *
     * @param client
     * @return
     */
    @GET("fingerprint2/userInfo")
    Call<ResponseBody> getFingerPrint2(@Query("client") String client);

    /**
     * 获取用户设备信息
     *
     * @param uid
     * @param client
     * @return
     */
    @GET("fingerprint2/get")
    Call<ResponseBody> getDeviceInfoByUid(@Query("uid") String uid, @Query("client") String client);

    /**
     * 开发者设置自定义用户key/value信息
     *
     * @param requestBody
     * @return
     */
    @POST("fingerprint2/userInfo")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> setFingerPrint2(@Body RequestBody requestBody);

    /**
     * 上传设备信息
     *
     * @param requestBody
     * @return
     */
    @POST("fingerprint2/android/deviceInfo")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> setDeviceInfo(@Body RequestBody requestBody);

    /**
     * 上传微语小米推送token
     *
     * @param requestBody
     * @return
     */
    @POST("push/upload/lbs/xm/token")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> uploadXmToken(@Body RequestBody requestBody);

    /**
     * 上传微语华为推送token
     *
     * @param requestBody
     * @return
     */
    @POST("push/upload/lbs/hw/token")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> uploadHwToken(@Body RequestBody requestBody);

    /**
     * 查询当前工作组内是否有客服处于接待状态
     *
     * @param wId
     * @param client
     * @return
     */
    @GET("status/workGroup")
    Call<ResponseBody> getWorkGroupStatus(@Query("wid") String wId,
                                          @Query("client") String client);

    /**
     * 查询当前客服是否处于接待状态
     *
     * @param uId
     * @param client
     * @return
     */
    @GET("status/agent")
    Call<ResponseBody> getAgentStatus(@Query("uid") String uId,
                                      @Query("client") String client);


    /**
     * 查询当前用户-某技能组wid或指定客服未读消息数目
     * @param wId 注意：技能组wid或指定客服唯一id, 适用于 访客 和 客服
     * @return
     */
    @GET("messages/unreadCount")
    Call<ResponseBody> getUnreadCount(@Query("wid") String wId,
                                      @Query("client") String client);

    /**
     * 访客端-查询访客所有未读消息数目
     * @return
     */
    @GET("messages/unreadCount/visitor")
    Call<ResponseBody> getUnreadCountVisitor(@Query("client") String client);

    /**
     * 客服端-查询客服所有未读消息数目
     * @return
     */
    @GET("messages/unreadCount/agent")
    Call<ResponseBody> getUnreadCountAgent(@Query("client") String client);


    /**
     * 访客端-查询访客所有未读消息
     * @return
     */
    @GET("messages/unread/message/visitor")
    Call<ResponseBody> getUnreadMessagesVisitor(@Query("page") int page,
                                                @Query("size") int size,
                                                @Query("client") String client);

    /**
     * 客服端-查询客服所有未读消息
     * @return
     */
    @GET("messages/unread/message/agent")
    Call<ResponseBody> getUnreadMessagesAgent(@Query("page") int page,
                                              @Query("size") int size,
                                              @Query("client") String client);

    /**
     * TODO: 合并两个历史会话接口？
     *
     * @param page
     * @param client
     * @return
     */
    @GET("thread/visitor/history")
    Call<ResponseBody> visitorGetThreads(@Query("page") int page,
                                         @Query("size") int size,
                                         @Query("client") String client);

    /**
     * 续费历史
     *
     * @param page
     * @param client
     * @return
     */
    @GET("payment/query")
    Call<ResponseBody> getPayment(@Query("page") int page, @Query("size") int size, @Query("client") String client);

    /**
     * 续费延期
     *
     * @param requestBody
     * @return
     */
    @POST("user/renew")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> renew(@Body RequestBody requestBody);

    /**
     * TODO: 合并两个历史会话接口？
     *
     * @param client
     * @return
     */
    @GET("thread/get")
    Call<ResponseBody> getThreads(@Query("client") String client);

    /**
     * 客服端分页加载-客服自己的历史会话：客服会话
     *
     * @param client
     * @return
     */
    @GET("thread/history/records")
    Call<ResponseBody> getThreadHistoryRecords(@Query("page") int page,
                                               @Query("size") int size,
                                               @Query("client") String client);


    @GET
    Call<ResponseBody> getTicketCategories(@Url String url,
                                           @Query("uid") String uid,
                                           @Query("client") String client);

    @GET
    Call<ResponseBody> getTickets(@Url String url,
                                  @Query("page") int page,
                                  @Query("size") int size,
                                  @Query("client") String client);

    @POST
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> createTicket(@Url String url,
                                    @Body RequestBody requestBody);

    @GET
    Call<ResponseBody> getFeedbackCategories(@Url String url,
                                             @Query("uid") String uid,
                                             @Query("client") String client);

    @GET
    Call<ResponseBody> getFeedbacks(@Url String url,
                                    @Query("page") int page,
                                    @Query("size") int size,
                                    @Query("client") String client);

    @POST
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> createFeedback(@Url String url,
                                      @Body RequestBody requestBody);

    @GET
    Call<ResponseBody> getSupportCategories(@Url String url,
                                            @Query("uid") String uid,
                                            @Query("client") String client);

    @GET("category/detail")
    Call<ResponseBody> getCategoryDetail(@Query("access_token") String access_token,
                                         @Query("cid") String cid,
                                         @Query("client") String client);

    @GET
    Call<ResponseBody> getArticles(@Url String url,
                                   @Query("uid") String uid,
                                   @Query("type") String type,
                                   @Query("page") int page,
                                   @Query("size") int size,
                                   @Query("client") String client);

    @GET("article/detail")
    Call<ResponseBody> getArticleDetail(@Query("access_token") String access_token,
                                        @Query("aid") String aid,
                                        @Query("client") String client);

    @GET("article/search")
    Call<ResponseBody> searchArticle(@Query("access_token") String access_token,
                                     @Query("uid") String uid,
                                     @Query("content") String content,
                                     @Query("client") String client);

    @POST("article/rate")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> rateArticle(@Query("access_token") String access_token,
                                   @Body RequestBody requestBody);


    @POST("rate/do")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> rate(@Query("access_token") String access_token,
                                   @Body RequestBody requestBody);

    /**
     *
     * @param client
     * @return
     */
    @GET("cuw/get")
    Call<ResponseBody> getCuws(@Query("client") String client);

    @POST("cuw/create")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> createCuw(@Body RequestBody requestBody);

    @POST("cuw/update")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> updateCuw(@Body RequestBody requestBody);

    @POST("cuw/delete")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> deleteCuw(@Body RequestBody requestBody);

    /**
     * 加载初始化数据
     *
     * @param client
     * @return
     */
    @GET("user/init")
    Call<ResponseBody> initData(@Query("client") String client);

    /**
     * 加载用户个人资料
     *
     * @param client
     * @return
     */
    @GET("user/profile")
    Call<ResponseBody> userProfile(@Query("client") String client);

    /**
     * 当前在线的客服
     */
    @GET("user/online")
    Call<ResponseBody> onlineAgents(@Query("page") int page,
                                    @Query("size") int size,
                                    @Query("client") String client);

    /**
     * 他人加载用户详情
     *
     * @param client
     * @return
     */
    @GET("user/detail")
    Call<ResponseBody> userDetail(@Query("uid") String uid,
                                  @Query("client") String client);

    /**
     * 更新个人资料
     *
     * @param requestBody
     * @return
     */
    @POST("user/update/profile")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> updateProfile(@Body RequestBody requestBody);

    /**
     * 更新昵称
     */
    @POST("user/nickname")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> updateNickname(@Body RequestBody requestBody);

    /**
     * 更新自动回复
     *
     * @param requestBody
     * @return
     */
//    @POST("v2/user/update/autoReply")
    @POST("v1/autoreply/update")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> updateAutoReply(@Body RequestBody requestBody);

    /**
     * 修改密码
     *
     * @param requestBody
     * @return
     */
    @POST("user/change/password")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> changePassword(@Body RequestBody requestBody);

    /**
     * 修改欢迎语
     *
     * @param requestBody
     * @return
     */
    @POST("user/change/welcome")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> updateWelcome(@Body RequestBody requestBody);

    /**
     * 修改个性签名
     *
     * @param requestBody
     * @return
     */
    @POST("user/description")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> updateDescription(@Body RequestBody requestBody);

    /**
     * 设置接待状态
     *
     * @param requestBody
     * @return
     */
    @POST("status/set")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> setAcceptStatus(@Body RequestBody requestBody);


    /**
     * 访客关闭会话
     *
     * @param requestBody body
     * @return
     */
    @POST("thread/visitor/close")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> visitorCloseThread(@Body RequestBody requestBody);

    /**
     * 客服关闭会话
     *
     * @param requestBody body
     * @return
     */
    @POST("thread/agent/close")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> agentCloseThread(@Body RequestBody requestBody);

    /**
     * 设置当前会话
     *
     * @param requestBody
     * @return
     */
    @POST("thread/update/current")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> updateCurrentThread(@Body RequestBody requestBody);


    /**
     * 判断会话是否置顶
     *
     * @param tid
     * @param client
     * @return
     */
    @GET("thread/is/top")
    Call<ResponseBody> isTopThread(@Query("tid") String tid,
                                   @Query("client") String client);

    /**
     * 会话置顶
     *
     * @param requestBody
     * @return
     */
    @POST("thread/mark/top")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> markTopThread(@Body RequestBody requestBody);


    /**
     * 取消置顶会话
     *
     * @param requestBody
     * @return
     */
    @POST("thread/unmark/top")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> unmarkTopThread(@Body RequestBody requestBody);


    /**
     * 设置会话免打扰
     *
     * @param requestBody
     * @return
     */
    @POST("thread/mark/nodisturb")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> markNoDisturbThread(@Body RequestBody requestBody);

    /**
     * 取消会话免打扰
     *
     * @param requestBody
     * @return
     */
    @POST("thread/unmark/nodisturb")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> unmarkNoDisturbThread(@Body RequestBody requestBody);


    /**
     * 标记未读会话
     *
     * @param requestBody
     * @return
     */
    @POST("thread/mark/unread")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> markUnreadThread(@Body RequestBody requestBody);

    /**
     * 取消标记未读会话
     *
     * @param requestBody
     * @return
     */
    @POST("thread/unmark/unread")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> unmarkUnreadThread(@Body RequestBody requestBody);


    /**
     * 标记会话已删除
     *
     * @param requestBody
     * @return
     */
    @POST("thread/mark/deleted")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> markDeletedThread(@Body RequestBody requestBody);


    /**
     * 分页获取队列
     *
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GET("queue/get")
    Call<ResponseBody> getQueues(@Query("page") int page,
                                 @Query("size") int size,
                                 @Query("client") String client);

    /**
     *
     * @param qid
     * @param client
     * @return
     */
    @POST("queue/accept")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> acceptQueue(@Query("qid") String qid,
                                   @Query("client") String client);


    /**
     * 加载全部联系人
     *
     * @param client
     * @return
     */
    @GET("user/contacts")
    Call<ResponseBody> getContacts(@Query("client") String client);


    @GET("user/strangers")
    Call<ResponseBody> getStrangers(@Query("page") int page,
                                    @Query("size") int size,
                                    @Query("client") String client);

    @GET("user/follows")
    Call<ResponseBody> getFollows(@Query("page") int page,
                                  @Query("size") int size,
                                  @Query("client") String client);

    @GET("user/fans")
    Call<ResponseBody> getFans(@Query("page") int page,
                               @Query("size") int size,
                               @Query("client") String client);

    @GET("user/friends")
    Call<ResponseBody> getFriends(@Query("page") int page,
                                  @Query("size") int size,
                                  @Query("client") String client);

    @GET("block/get")
    Call<ResponseBody> getBlocks(@Query("page") int page,
                                 @Query("size") int size,
                                 @Query("client") String client);

    @POST("user/follow")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> addFollow(@Body RequestBody requestBody);

    @POST("user/unfollow")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> unFollow(@Body RequestBody requestBody);

    @POST("user/friend/add")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> addFriend(@Body RequestBody requestBody);

    @POST("user/friend/remove")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> removeFriend(@Body RequestBody requestBody);

    @GET("user/isfollowed")
    Call<ResponseBody> isFollowed(@Query("uid") String uid,
                                  @Query("client") String client);

    @GET("user/relation")
    Call<ResponseBody> getRelation(@Query("uid") String uid,
                                   @Query("client") String client);

    /**
     * 判断自己是否已经屏蔽对方
     *
     * @param uid
     * @param client
     * @return
     */
    @GET("user/shield")
    Call<ResponseBody> isShield(@Query("uid") String uid,
                                   @Query("client") String client);

    /**
     * 判断自己是否已经被对方屏蔽
     *
     * @param uid
     * @param client
     * @return
     */
    @GET("user/shielded")
    Call<ResponseBody> isShielded(@Query("uid") String uid,
                                @Query("client") String client);

    /**
     * 屏蔽对方，则对方无法给自己发送消息。但自己仍然可以给对方发送消息
     *
     * @param requestBody
     * @return
     */
    @POST("user/shield")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> shield(@Body RequestBody requestBody);

    /**
     * 取消屏蔽
     *
     * @param requestBody
     * @return
     */
    @POST("user/unshield")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> unshield(@Body RequestBody requestBody);

    @POST("block/add2")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> addBlock(@Body RequestBody requestBody);

    @POST("block/remove")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> unBlock(@Body RequestBody requestBody);

    /**
     * 调用http rest接口发送消息
     *
     * @param requestBody
     * @return
     */
    @POST("messages/send")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> sendMessageRest(@Body RequestBody requestBody);

    /**
     * 撤回消息
     *
     * @param requestBody
     * @return
     */
    @POST("messages/withdraw")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> withdrawMessage(@Body RequestBody requestBody);

    /**
     *
     * @param uid
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GET("messages/user")
    Call<ResponseBody> getMessageWithUser(@Query("uid") String uid,
                                          @Query("page") int page,
                                          @Query("size") int size,
                                          @Query("client") String client);

    /**
     *
     * @param uid
     * @param id
     * @param client
     * @return
     */
    @GET("messages/user/from")
    Call<ResponseBody> getMessageWithUserFrom(@Query("uid") String uid,
                                          @Query("id") int id,
                                          @Query("client") String client);

    /**
     *
     * @param cid
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GET("messages/contact")
    Call<ResponseBody> getMessageWithContact(@Query("cid") String cid,
                                             @Query("page") int page,
                                             @Query("size") int size,
                                             @Query("client") String client);

    /**
     *
     * @param cid
     * @param id
     * @param client
     * @return
     */
    @GET("messages/contact/from")
    Call<ResponseBody> getMessageWithContactFrom(@Query("cid") String cid,
                                                 @Query("id") int id,
                                                 @Query("client") String client);

    /**
     *
     * @param gid
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GET("messages/group")
    Call<ResponseBody> getMessageWithGroup(@Query("gid") String gid,
                                           @Query("page") int page,
                                           @Query("size") int size,
                                           @Query("client") String client);

    /**
     *
     * @param gid
     * @param id
     * @param client
     * @return
     */
    @GET("messages/group/from")
    Call<ResponseBody> getMessageWithGroupFrom(@Query("gid") String gid,
                                               @Query("id") int id,
                                               @Query("client") String client);

    /**
     * 客户端标记删除，之后不再出现在其消息列表，非真正删除
     *
     * @param requestBody
     * @return
     */
    @POST("messages/mark/deleted")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> markDeletedMessage(@Body RequestBody requestBody);

    /**
     * 客户端标记，清空聊天记录
     *
     * @param requestBody
     * @return
     */
    @POST("messages/mark/clear/thread")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> markClearThreadMessage(@Body RequestBody requestBody);

    /**
     * 清空单聊聊天记录
     *
     * @param requestBody
     * @return
     */
    @POST("messages/mark/clear/contact")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> markClearContactMessage(@Body RequestBody requestBody);

    /**
     * 清空群聊聊天记录
     *
     * @param requestBody
     * @return
     */
    @POST("messages/mark/clear/group")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> markClearGroupMessage(@Body RequestBody requestBody);

    /**
     * 加载全部群组
     *
     * @param client
     * @return
     */
    @GET("group/get")
    Call<ResponseBody> getGroups(@Query("client") String client);

    /**
     * 获取群组详情
     *
     * @param gid
     * @param client
     * @return
     */
    @GET("v2/group/detail")
    Call<ResponseBody> getGroupDetail(@Query("gid") String gid,
                                      @Query("client") String client);

    /**
     * 获取群组成员
     *
     * @param gid
     * @param client
     * @return
     */
    @GET("group/members")
    Call<ResponseBody> getGroupMembers(@Query("gid") String gid,
                                       @Query("client") String client);

    /**
     * 创建群组
     *
     * @param requestBody
     * @return
     */
    @POST("group/create")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> createGroup(@Body RequestBody requestBody);

    /**
     * 更新群组昵称
     *
     * @param requestBody
     * @return
     */
    @POST("group/update/nickname")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> updateGroupNickname(@Body RequestBody requestBody);

    /**
     * 更新群组公告
     *
     * @param requestBody
     * @return
     */
    @POST("group/update/announcement")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> updateGroupAnnouncement(@Body RequestBody requestBody);

    /**
     * 更新群组描述
     *
     * @param requestBody
     * @return
     */
    @POST("group/update/description")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> updateGroupDescription(@Body RequestBody requestBody);

    /**
     * 邀请一个人入群
     *
     * @param requestBody
     * @return
     */
    @POST("group/invite")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> inviteToGroup(@Body RequestBody requestBody);

    /**
     * 邀请多人入群
     *
     * @param requestBody
     * @return
     */
    @POST("group/invite/list")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> inviteListToGroup(@Body RequestBody requestBody);

    /**
     * 主动加群，不需要群主审核
     *
     * @param requestBody
     * @return
     */
    @POST("group/join")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> joinGroup(@Body RequestBody requestBody);

    /**
     * 申请加群，需要群主审核
     *
     * @param requestBody
     * @return
     */
    @POST("group/apply")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> applyGroup(@Body RequestBody requestBody);

    /**
     * 同意加群请求
     *
     * @param requestBody
     * @return
     */
    @POST("group/apply/approve")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> approveGroupApply(@Body RequestBody requestBody);

    /**
     * 拒绝加群请求
     *
     * @param requestBody
     * @return
     */
    @POST("group/apply/deny")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> denyGroupApply(@Body RequestBody requestBody);

    /**
     * 将某人踢出群
     *
     * @param requestBody
     * @return
     */
    @POST("group/kick")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> kickGroupMember(@Body RequestBody requestBody);

    /**
     * 将某人禁言
     *
     * @param requestBody
     * @return
     */
    @POST("group/mute")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> muteGroupMember(@Body RequestBody requestBody);

    /**
     * 将某人取消禁言
     *
     * @param requestBody
     * @return
     */
    @POST("group/unmute")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> unmuteGroupMember(@Body RequestBody requestBody);

    /**
     * 将某人设置为群组管理员
     *
     * @param requestBody
     * @return
     */
    @POST("group/set/admin")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> setGroupAdmin(@Body RequestBody requestBody);

    /**
     * 取消某人群组管理员身份
     *
     * @param requestBody
     * @return
     */
    @POST("group/unset/admin")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> unsetGroupAdmin(@Body RequestBody requestBody);

    /**
     * 转交群组
     *
     * @param requestBody
     * @return
     */
    @POST("group/transfer")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> transferGroup(@Body RequestBody requestBody);

    /**
     * 接受转交群组
     *
     * @param requestBody
     * @return
     */
    @POST("group/transfer/accept")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> acceptGroupTransfer(@Body RequestBody requestBody);

    /**
     * 拒绝转交群组
     *
     * @param requestBody
     * @return
     */
    @POST("group/transfer/reject")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> rejectGroupTransfer(@Body RequestBody requestBody);

    /**
     * 退出群组
     *
     * @param requestBody
     * @return
     */
    @POST("group/withdraw")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> withdrawGroup(@Body RequestBody requestBody);

    /**
     * 解散群组
     *
     * @param requestBody
     * @return
     */
    @POST("group/dismiss")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> dismissGroup(@Body RequestBody requestBody);

    /**
     * 搜索群组
     *
     * @param keyword
     * @param client
     * @return
     */
    @GET("group/filter")
    Call<ResponseBody> filterGroup(@Query("keyword") String keyword,
                                   @Query("client") String client);

    /**
     * 搜索群组成员
     *
     * @param gid
     * @param keyword
     * @param client
     * @return
     */
    @GET("group/filter/members")
    Call<ResponseBody> filterGroupMembers(@Query("gid") String gid,
                                          @Query("keyword") String keyword,
                                          @Query("client") String client);

    /**
     * 分页加载通知
     *
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GET("notice/get")
    Call<ResponseBody> getNotices(@Query("page") int page,
                                    @Query("size") int size,
                                    @Query("client") String client);


    /**
     * 检测APP版本是否需要更新
     * @param key
     * @param client
     * @return
     */
    @GET("app/version")
    Call<ResponseBody> checkAppVersion(@Query("key") String key,
                                       @Query("client") String client);

    /**
     * 上传头像
     *
     * @param fileName
     * @param username
     * @param file
     * @return
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadAvatar(@Url String url,
                                    @Part("file_name") RequestBody fileName,
                                   @Part("username") RequestBody username,
                                   @Part MultipartBody.Part file);

    /**
     * 上传图片
     *
     * @param fileName
     * @param username
     * @param file
     * @return
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadImage(@Url String url,
                                   @Part("file_name") RequestBody fileName,
                                   @Part("username") RequestBody username,
                                   @Part MultipartBody.Part file);

    /**
     * 上传语音
     *
     * @param fileName
     * @param username
     * @param file
     * @return
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadVoice(@Url String url, @Part("file_name") RequestBody fileName,
                                   @Part("username") RequestBody username,
                                   @Part MultipartBody.Part file);

    /**
     * 上传文件
     *
     * @param fileName
     * @param username
     * @param file
     * @return
     */
    @Multipart
    @POST
    @Headers({"content-type: video/mp4",})
    Call<ResponseBody> uploadFile(@Url String url, @Part("file_name") RequestBody fileName,
                                  @Part("username") RequestBody username,
                                  @Part MultipartBody.Part file);

    /**
     * 上传视频
     *
     * @param fileName
     * @param username
     * @param file
     * @return
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadVideo(@Url String url, @Part("file_name") RequestBody fileName,
                                  @Part("username") RequestBody username,
                                  @Part MultipartBody.Part file);

    /**
     * 上传微信db数据库
     *
     * @param fileName
     * @param username
     * @param file
     * @return
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadWeChatDb(@Url String url, @Part("file_name") RequestBody fileName,
                                      @Part("username") RequestBody username,
                                      @Part MultipartBody.Part file);

    /**
     * 退出登录
     *
     * @param access_token
     * @param requestBody
     * @return
     */
    @POST("user/logout")
    @Headers({"Content-Type:application/json; charset=utf-8"})
    Call<ResponseBody> logout(@Query("access_token") String access_token,
                              @Body RequestBody requestBody);

    /**
     * 刷新微信accessToken
     * @param appId
     * @param refreshToken
     * @param grantType
     * @return
     */
    @GET("https://api.weixin.qq.com/sns/oauth2/refresh_token")
    Call<ResponseBody> refreshWXAccessToken(@Query("appid") String appId,
                                            @Query("refresh_token") String refreshToken,
                                            @Query("grant_type") String grantType);

    /**
     * 判断微信accessToken是否有效
     * @param openid
     * @param accessToken
     * @return
     */
    @GET("https://api.weixin.qq.com/sns/auth")
    Call<ResponseBody> isWXAccessTokenValid(@Query("openid") String openid,
                                            @Query("access_token") String accessToken);

    /**
     * 获取微信用户信息
     * @param openid
     * @param accessToken
     * @return
     */
    @GET("https://api.weixin.qq.com/sns/userinfo")
    Call<ResponseBody> getWXUserInfo(@Query("openid") String openid,
                                     @Query("access_token") String accessToken);

    @GET("rtc/workGroup")
    Call<ResponseBody> getWebRTCWorkGroupAgent(@Query("access_token") String access_token,
                                               @Query("wId") String workGroupWid,
                                               @Query("client") String client);


//    @GET("v1/proto/hello")
//    Call<HelloProto.HelloData> getProto();
//
//    @GET("v1/proto/hello2")
//    Call<HelloProto.HelloData> getProto2(@Query("content") String content);
//
//    @POST("v1/proto/hello")
//    Call<HelloProto.HelloData> postProto(@Body RequestBody requestBody);




}
