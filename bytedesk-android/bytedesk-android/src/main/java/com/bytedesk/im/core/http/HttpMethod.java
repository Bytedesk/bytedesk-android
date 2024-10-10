package com.bytedesk.im.core.http;

import android.content.Context;

import com.bytedesk.im.core.api.BDConfig;
import com.bytedesk.im.core.callback.BaseCallback;
import com.bytedesk.im.core.repository.BDRepository;
import com.bytedesk.im.core.util.BDCoreConstant;
import com.bytedesk.im.core.util.BDCoreUtils;
import com.bytedesk.im.core.util.BDPreferenceManager;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TODO: 重构，对应某些函数 去除HttpMethod类，在BDCoreApi中直接调用WebService
 * @author bytedesk.com on 2017/8/30.
 */
public class HttpMethod {

    /**
     *
     * @param subDomain
     * @param baseCallback
     */
    public static void registerAnonymousUser(Context context, String subDomain, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .registerAnonymousUser(BDConfig.getInstance(context).getVisitorGenerateUsernameUrl(), subDomain, client)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    //
                    String bodyJson = response.body().string();
                    Logger.i("generate username: " + bodyJson);
                    if (null != bodyJson) {
                        //
                        JSONObject jsonObject = new JSONObject(bodyJson);
                        baseCallback.onSuccess(jsonObject);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                failedCallback(baseCallback);
            }
        });
    }


    /**
     *
     * @param username
     * @param nickname
     * @param password
     * @param subDomain
     * @param baseCallback
     */
    public static void registerUser(Context context, String username, String nickname, String password, String subDomain, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("nickname", nickname);
            jsonObject.put("password", password);
            jsonObject.put("subDomain", subDomain);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
            .registerUser(BDConfig.getInstance(context).getVisitorRegisterUserUrl(), requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    try {
                        //
                        String bodyJson = response.body().string();
                        Logger.i("register user: " + bodyJson);
                        if (null != bodyJson) {
                            //
                            JSONObject jsonObject = new JSONObject(bodyJson);
                            baseCallback.onSuccess(jsonObject);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param username
     * @param nickname
     * @param avatar
     * @param password
     * @param subDomain
     * @param baseCallback
     */
    public static void registerUser(Context context, String username, String nickname, String avatar, String password, String subDomain, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("nickname", nickname);
            jsonObject.put("avatar", avatar);
            jsonObject.put("password", password);
            jsonObject.put("subDomain", subDomain);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
                .registerUser(BDConfig.getInstance(context).getVisitorRegisterUserUrl(), requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        try {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("register user: " + bodyJson);
                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                baseCallback.onSuccess(jsonObject);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     *
     * @param username
     * @param nickname
     * @param uid
     * @param password
     * @param subDomain
     * @param baseCallback
     */
    public static void registerUserUid(Context context, String username, String nickname, String uid, String password, String subDomain, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("nickname", nickname);
            jsonObject.put("uid", uid);
            jsonObject.put("password", password);
            jsonObject.put("subDomain", subDomain);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
            .registerUserUid(BDConfig.getInstance(context).getVisitorRegisterUserUidUrl(), requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        //
                        String bodyJson = response.body().string();
                        Logger.i("register user uid: " + bodyJson);
                        if (null != bodyJson) {
                            //
                            JSONObject jsonObject = new JSONObject(bodyJson);
                            baseCallback.onSuccess(jsonObject);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    public static void registerAdmin(Context context, String email, String password, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
            .registerAdmin(BDConfig.getInstance(context).getVisitorRegisterAdminUrl(), requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        //
                        String bodyJson = response.body().string();
                        Logger.i("register admin: " + bodyJson);
                        if (null != bodyJson) {
                            //
                            JSONObject jsonObject = new JSONObject(bodyJson);
                            baseCallback.onSuccess(jsonObject);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    public static void registerEmail(Context context, String email, String nickname, String password, String subDomain, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("nickname", nickname);
            jsonObject.put("password", password);
            jsonObject.put("subDomain", subDomain);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
                .registerAdmin(BDConfig.getInstance(context).getVisitorRegisterEmailUrl(), requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("register admin: " + bodyJson);
                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                baseCallback.onSuccess(jsonObject);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     *
     * Authorization: Basic Y2xpZW50OnNlY3JldA==
     *
     * @param context
     * @param username
     * @param password
     * @param baseCallback
     */
    public static void login(final Context context, String username, String password, final BaseCallback baseCallback) {
        //
//        String client = "client";
//        String secret = "secret";
        String grant_type = "password";
        String scope = "all";
//        //
//        String clientAndSecret = client + ":" +secret;
//        String authorization = "Basic " + Base64.encodeToString(clientAndSecret.getBytes(), Base64.NO_WRAP);
        String authorization = "Basic Y2xpZW50OnNlY3JldA==";
//        Logger.i("authorization %s", authorization);
        //
        HttpManager.getInstance(context).getLoginWebService()
            .login(BDConfig.getInstance(context).getPassportOAuthTokenUrl(), authorization, grant_type, username, password, scope)
            .enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    loginCallback(context, response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * Authorization: Basic Y2xpZW50OnNlY3JldA==
     *
     * @param context
     * @param mobile
     * @param code
     * @param baseCallback
     */
    public static void loginMobile(final Context context, String mobile, String code, final BaseCallback baseCallback) {
        //
//        String client = "client";
//        String secret = "secret";
        String grant_type = "mobile";
        String scope = "all";
        //
//        String clientAndSecret = client + ":" +secret;
//        String authorization = "Basic " + Base64.encodeToString(clientAndSecret.getBytes(), Base64.NO_WRAP);
        String authorization = "Basic Y2xpZW50OnNlY3JldA==";
        //
        HttpManager.getInstance(context).getLoginWebService()
                .loginMobile(BDConfig.getInstance(context).getMobileOAuthTokenUrl(), authorization, grant_type, mobile, code, scope)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        loginCallback(context, response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    public static void loginEmail(final Context context, String email, String code, final BaseCallback baseCallback) {
        //
//        String client = "client";
//        String secret = "secret";
        String grant_type = "email";
        String scope = "all";
        //
//        String clientAndSecret = client + ":" +secret;
//        String authorization = "Basic " + Base64.encodeToString(clientAndSecret.getBytes(), Base64.NO_WRAP);
        String authorization = "Basic Y2xpZW50OnNlY3JldA==";
        //
        HttpManager.getInstance(context).getLoginWebService()
                .loginEmail(BDConfig.getInstance(context).getEmailOAuthTokenUrl(), authorization, grant_type, email, code, scope)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        loginCallback(context, response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    public static void loginUnionId(final Context context, String unionid, final BaseCallback baseCallback) {
        //
//        String client = "client";
//        String secret = "secret";
        String grant_type = "unionid";
        String scope = "all";
        //
//        String clientAndSecret = client + ":" +secret;
//        String authorization = "Basic " + Base64.encodeToString(clientAndSecret.getBytes(), Base64.NO_WRAP);
        String authorization = "Basic Y2xpZW50OnNlY3JldA==";
        //
        HttpManager.getInstance(context).getLoginWebService()
                .loginUnionId(BDConfig.getInstance(context).getWeChatOAuthTokenUrl(), authorization, grant_type, unionid, scope)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        loginCallback(context, response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }
    

    public static void requestMobileCode(Context context, String mobile, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getLoginWebService()
                .requestMobileCode(BDConfig.getInstance(context).getMobileCodeUrl(), mobile, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    public static void requestEmailCode(Context context,
                                         String email,
                                         final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getLoginWebService()
                .requestEmailCode(BDConfig.getInstance(context).getEmailCodeUrl(), email, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }


    public static void registerMobile(final Context context, String mobile, String email, String nickname, final BaseCallback baseCallback) {
        //
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
            jsonObject.put("email", email);
            jsonObject.put("nickname", nickname);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getLoginWebService()
                .registerMobile(BDConfig.getInstance(context).getVisitorRegisterMobileUrl(),requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }

    public static void bindMobile(final Context context, String mobile, String email, final BaseCallback baseCallback) {
        //
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
            jsonObject.put("email", email);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .bindMobile(BDConfig.getInstance(context).getVisitorBindMobileUrl(),requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    public static void isWeChatRegistered(Context context, String unionId, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .isWeChatRegistered(BDConfig.getInstance(context).getVisitorIsWeChatRegisteredUrl(), unionId, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }

    public static void registerWeChat(Context context, String unionid, String email, String openid, String nickname, String avatar, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("unionid", unionid);
            jsonObject.put("email", email);
            jsonObject.put("openid", openid);
            jsonObject.put("nickname", nickname);
            jsonObject.put("avatar", avatar);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
                .registerWeChat(BDConfig.getInstance(context).getVisitorRegisterWeChatUrl(), requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("register wechat: " + bodyJson);
                            //
                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                baseCallback.onSuccess(jsonObject);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    public static void bindWeChat(Context context, String unionid, String email, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("unionid", unionid);
            jsonObject.put("email", email);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
                .bindWeChat(BDConfig.getInstance(context).getVisitorBindWeChatUrl(), requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("register wechat: " + bodyJson);
                            //
                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                baseCallback.onSuccess(jsonObject);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }


    public static void getChatCode(Context context,final BaseCallback baseCallback) {
        //
        HttpManager.getInstance(context).getWebService()
                .getChatCode(BDConfig.getInstance(context).getVisitorChatCode(), BDCoreConstant.CLIENT_ANDROID)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            //
                            String bodyJson = response.body().string();
//                            Logger.i("chat code: " + bodyJson);
                            //
                            if (null != bodyJson) {
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                baseCallback.onSuccess(jsonObject);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     *
     * @param workGroupWid
     * @param requestType
     * @param agentUid
     * @param webrtc 0: 文字会话 ，1：视频，2：音频, 默认为0
     * @param baseCallback
     */
    public static void requestThread(Context context, String workGroupWid, String requestType, String agentUid, int webrtc,
                                     final BaseCallback baseCallback) {

        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .requestThread(workGroupWid, requestType, agentUid, client, webrtc)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                failedCallback(baseCallback);
            }
        });
    }

    public static void getContactThread(Context context, String cid, final BaseCallback baseCallback) {

        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getContactThread(cid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    public static void getGroupThread(Context context, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getGroupThread(gid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    public static void requestAgent(Context context, String workGroupWid, String requestType, String agentUid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .requestAgent(workGroupWid, requestType, agentUid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }

    /**
     * 选择问卷答案
     *
     * @param threadTid
     * @param questionnaireItemItemQid
     * @param baseCallback
     */
    public static void requestQuestionnaire(Context context, String threadTid, String questionnaireItemItemQid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .requestQuestionnaire(threadTid, questionnaireItemItemQid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }

    /**
     * 问卷答案中选择工作组
     *
     * @param workGroupWid
     * @param baseCallback
     */
    public static void chooseWorkGroup(Context context, String workGroupWid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .chooseWorkGroup(workGroupWid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    public static void chooseWorkGroupLiuXue(Context context,  String workGroupWid, String workGroupNickname, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .chooseWorkGroupLiuXue(workGroupWid, workGroupNickname, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }

    public static void chooseWorkGroupLiuXueLBS(Context context, String workGroupWid, String workGroupNickname, String province, String city, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .chooseWorkGroupLiuXueLBS(workGroupWid, workGroupNickname, province, city, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 留言
     *
     * @param context
     * @param baseCallback
     */
    public static void leaveMessage(Context context, String type, String workGroupWid, String agentUid,
                                       String mobile, String email, String content,
                                       final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        //
        try {

            jsonObject.put("type", type);
            jsonObject.put("wid", workGroupWid);
            jsonObject.put("aid", agentUid);

            jsonObject.put("mobile", mobile);
            jsonObject.put("email", email);
//            jsonObject.put("nickname", nickname);
//            jsonObject.put("location", location);
//            jsonObject.put("country", country);
            jsonObject.put("content", content);

            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .leaveMessage(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     * 拉取留言列表
     *
     * @param context
     * @param page
     * @param size
     * @param baseCallback
     */
    public static void getLeaveMessages(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getLeaveMessages(page, size, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }

                });
    }

    /**
     * 回复留言
     *
     * @param context
     * @param lid
     * @param content
     * @param baseCallback
     */
    public static void replyLeaveMessage(Context context, String lid, String content,
                                    final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        //
        try {

            jsonObject.put("lid", lid);
            jsonObject.put("replied", true);
            jsonObject.put("reply", content);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .replyLeaveMessage(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     * 初始化机器人
     *
     * @param context
     */
    public static void initAnswer(Context context, String type, String workGroupWid, String agentUid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .initAnswer(type, workGroupWid, agentUid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }

    /**
     * 热门智能问答
     *
     * @param context
     * @param baseCallback
     */
    public static void topAnswer(Context context, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .topAnswer(client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 根据aid，请求智能答案
     *
     * @param context
     * @param baseCallback
     */
    public static void queryAnswer(Context context, String tid, String aid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .queryAnswer(tid, aid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 根据输入内容，请求智能答案
     *
     * @param context
     */
    public static void messageAnswer(Context context, String workGroupWid, String content, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .messageAnswer(workGroupWid, content, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    public static void rateAnswer(Context context, String aid, String mid, boolean rate, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("aid", aid);
            jsonObject.put("mid", mid);
            jsonObject.put("rate", rate);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .rateAnswer(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     *
     * @param nickname
     * @param baseCallback
     */
    public static void setNickname(Context context, String nickname, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickname", nickname);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .setNickname(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    public static void setDescription(Context context, String description, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("description", description);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .setNickname(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }


    public static void setAvatar(Context context, String avatar, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("avatar", avatar);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .setAvatar(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }

    /**
     *
     * @param baseCallback
     */
    public static void getFingerPrint(Context context, final BaseCallback baseCallback) {

        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getFingerPrint2(client)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                failedCallback(baseCallback);
            }

        });
    }

    public static void getDeviceInfoByUid(Context context, String uid, final BaseCallback baseCallback) {

        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getDeviceInfoByUid(uid, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }

                });
    }


    /**
     *
     * @param name
     * @param key
     * @param value
     * @param baseCallback
     */
    public static void setFingerPrint(Context context, String name, String key, String value,
                                      final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("key", key);
            jsonObject.put("value", value);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .setFingerPrint2(requestBody)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                failedCallback(baseCallback);
            }
        });
    }

    /**
     * 上传设备信息
     * @param context
     * @param baseCallback
     */
    public static void setDeviceInfo(Context context, final BaseCallback baseCallback) {

        // sdk版本
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        // 手机系统版本号
        String osVersion = android.os.Build.VERSION.RELEASE;
        // 手机型号
        String deviceModel = android.os.Build.MODEL;
        // 手机厂商
        String brand = android.os.Build.BRAND;
        // 系统语言
        String language = Locale.getDefault().getLanguage();
        // 应用版本号
        int appVersion = BDCoreUtils.getVersionCode(context);
        // 应用版本名
        String appVersionName = BDCoreUtils.getVersionName(context);
        //
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sdkVersion", sdkVersion);
            jsonObject.put("osVersion", osVersion);
            jsonObject.put("deviceModel", deviceModel);
            jsonObject.put("brand", brand);
            jsonObject.put("language", language);
            jsonObject.put("appVersion", appVersion);
            jsonObject.put("appVersionName", appVersionName);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .setDeviceInfo(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     * 上传微语小米推送token
     *
     * @param context
     * @param token
     * @param baseCallback
     */
    public static void uploadXmToken(Context context, String token, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pushTokenLbsXm", token);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .uploadXmToken(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     * 上传微语华为推送token
     *
     * @param context
     * @param token
     * @param baseCallback
     */
    public static void uploadHwToken(Context context, String token, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pushTokenLbsHw", token);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .uploadHwToken(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     *
     * @param wId
     * @param baseCallback
     */
    public static void getWorkGroupStatus(Context context, String wId, final BaseCallback baseCallback) {

        // 1512561951
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getWorkGroupStatus(wId, client)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.e("workgroup status error");

                failedCallback(baseCallback);
            }
        });
    }


    /**
     *
     * @param uId
     * @param baseCallback
     */
    public static void getAgentStatus(Context context, String uId, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getAgentStatus(uId, client)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                failedCallback(baseCallback);
            }
        });
    }

    /**
     * 查询当前用户-某技能组wid或指定客服未读消息数目
     * @param context
     * @param wid wId 注意：技能组wid或指定客服唯一id, 适用于 访客 和 客服
     * @param baseCallback
     */
    public static void getUnreadCount(Context context, String wid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getUnreadCount(wid, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     * 访客端-查询访客所有未读消息数目
     * @param context
     * @param baseCallback
     */
    public static void getUnreadCountVisitor(Context context, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getUnreadCountVisitor(client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     * 客服端-查询客服所有未读消息数目
     * @param context
     * @param baseCallback
     */
    public static void getUnreadCountAgent(Context context, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getUnreadCountAgent(client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     * 访客端-查询访客所有未读消息
     * @param context
     * @param baseCallback
     */
    public static void getUnreadMessagesVisitor(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getUnreadMessagesVisitor(page, size, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     * 客服端-查询客服所有未读消息
     * @param context
     * @param baseCallback
     */
    public static void getUnreadMessagesAgent(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getUnreadMessagesAgent(page, size, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     *
     * @param page
     * @param baseCallback
     */
    public static void visitorGetThreads(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .visitorGetThreads(page, size, client)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                failedCallback(baseCallback);
            }
        });
    }

    /**
     *
     * @param page
     * @param baseCallback
     */
    public static void getPayment(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getPayment(page, size, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    public static void renew(final Context context, String uid, String validateUntilDate, int balance, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("validateUntilDate", validateUntilDate);
            jsonObject.put("balance", balance);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
                .renew(requestBody)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        try {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("renew: " + bodyJson);
                            //
                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                baseCallback.onSuccess(jsonObject);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     * 调用http rest接口发送消息
     *
     * @param context
     * @param json
     * @param baseCallback
     */
    public static void sendMessageRest(Context context, String json, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("json", json);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .sendMessageRest(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     * 获取访客会话聊天记录
     *
     * @param uid
     * @param page
     * @param size
     * @param baseCallback
     */
    public static void getMessageWithUser(Context context, String uid, int page, int size,
                                          final BaseCallback baseCallback) {
        Logger.i("uid:" + uid + " page:" + page + " size:" + size);

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getMessageWithUser(uid, page, size, client)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                failedCallback(baseCallback);
            }
        });
    }


    /**
     *
     * @param uid
     * @param messageId
     * @param baseCallback
     */
    public static void getMessageWithUserFrom(Context context, String uid, int messageId, final BaseCallback baseCallback) {
        Logger.i("uid:" + uid + " messageId:" + messageId);

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getMessageWithUserFrom(uid, messageId, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }

    /**
     * 拉取一对一会话聊天记录
     *
     * @param cid
     * @param page
     * @param size
     * @param baseCallback
     */
    public static void getMessageWithContact(Context context, String cid, int page, int size, final BaseCallback baseCallback) {
        Logger.i("cid:" + cid + " page:" + page + " size:" + size);

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getMessageWithContact(cid, page, size, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param cid
     * @param messageId
     * @param baseCallback
     */
    public static void getMessageWithContactFrom(Context context, String cid, int messageId, final BaseCallback baseCallback) {
        Logger.i("cid:" + cid + " messageId:" + messageId);

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getMessageWithContactFrom(cid, messageId, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }

    /**
     * 拉取群组聊天记录
     *
     * @param gid
     * @param page
     * @param size
     * @param baseCallback
     */
    public static void getMessageWithGroup(Context context, String gid, int page, int size, final BaseCallback baseCallback) {
        Logger.i("gid:" + gid + " page:" + page + " size:" + size);

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getMessageWithGroup(gid, page, size, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param gid
     * @param messageId
     * @param baseCallback
     */
    public static void getMessageWithGroupFrom(Context context, String gid, int messageId, final BaseCallback baseCallback) {
        Logger.i("gid:" + gid + " messageId:" + messageId);

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getMessageWithGroupFrom(gid, messageId, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 客户端标记删除，之后不再出现在其消息列表，非真正删除
     *
     * @param mid
     * @param baseCallback
     */
    public static void markDeletedMessage(Context context, String mid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mid", mid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .markDeletedMessage(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    Logger.i("delete message %s", mid);
                    //
                    // BDRepository.getInstance(context).deleteMessage(mid);
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }



    public static void getCuws(final Context context, final BaseCallback baseCallback) {

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getCuws(client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    public static void createCuw(final Context context, int categoryId, String name, String content, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("categoryId", categoryId);
            jsonObject.put("name", name);
            jsonObject.put("content", content);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
            .createCuw(requestBody)
            .enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    try {
                        //
                        String bodyJson = response.body().string();
                        Logger.i("create cuw: " + bodyJson);
                        //
                        if (null != bodyJson) {
                            //
                            JSONObject jsonObject = new JSONObject(bodyJson);
                            baseCallback.onSuccess(jsonObject);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    public static void updateCuw(final Context context, int id, String name, String content, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            jsonObject.put("content", content);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
            .updateCuw(requestBody)
            .enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    try {
                        //
                        String bodyJson = response.body().string();
                        Logger.i("update cuw: " + bodyJson);
                        //
                        if (null != bodyJson) {
                            //
                            JSONObject jsonObject = new JSONObject(bodyJson);
                            baseCallback.onSuccess(jsonObject);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    public static void deleteCuw(final Context context, int id, final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
                .deleteCuw(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        try {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("delete cuw: " + bodyJson);
                            //
                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                baseCallback.onSuccess(jsonObject);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     *
     * @param context
     * @param baseCallback
     */
    public static void initData(final Context context, final BaseCallback baseCallback) {

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .initData(client)
            .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    //
                    if (response.code() == 401) {
                        Logger.e("401 token 无效");

                        if (null != baseCallback) {

                            // 清空本地数据
                            // BDRepository.getInstance(context).deleteAll();
                            BDPreferenceManager.getInstance(context).clear();

                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put("message", "token过期，请重新登录");
                            jsonObject.put("status_code", 401);
                            jsonObject.put("data", "");

                            baseCallback.onError(jsonObject);
                        }

                    } else {

                        String bodyJson = response.body().string();
                        Logger.i("initData: " + bodyJson);

                        if (null != bodyJson) {
                            //
                            JSONObject jsonObject = new JSONObject(bodyJson);
                            JSONObject dataJsonObject = jsonObject.getJSONObject("data");

                            // 数据持久化
                            BDPreferenceManager bdPreferenceManager = BDPreferenceManager.getInstance(context);
                            JSONObject infoObject = dataJsonObject.getJSONObject("info");
                            bdPreferenceManager.setUid(infoObject.getString("uid"));
                            bdPreferenceManager.setUsername(infoObject.getString("username"));
                            bdPreferenceManager.setNickname(infoObject.getString("nickname"));
                            bdPreferenceManager.setAvatar(infoObject.getString("avatar"));
                            bdPreferenceManager.setRealName(infoObject.getString("realName"));
                            bdPreferenceManager.setSubDomain(infoObject.getString("subDomain"));
                            bdPreferenceManager.setDescription(infoObject.getString("description"));
                            bdPreferenceManager.setAcceptStatus(infoObject.getString("acceptStatus"));
                            bdPreferenceManager.setAutoReplyContent(infoObject.getString("autoReplyContent"));
                            bdPreferenceManager.setWelcomeTip(infoObject.getString("welcomeTip"));

                            //
                            BDRepository bdRepository = BDRepository.getInstance(context);
                            // 工作组
                            JSONArray workGroups = dataJsonObject.getJSONArray("workGroups");
                            for (int i = 0; i < workGroups.length(); i++) {
//                                bdRepository.insertWorkGroupJson(workGroups.getJSONObject(i));
                            }

                            // 队列
                            JSONArray queues = dataJsonObject.getJSONObject("queues").getJSONArray("content");
                            for (int i = 0; i < queues.length(); i++) {
//                                bdRepository.insertQueueJson(queues.getJSONObject(i));
                            }

                            // 联系人
                            JSONArray contactsArray = dataJsonObject.getJSONArray("contacts");
                            for (int i = 0; i < contactsArray.length(); i++) {
//                                bdRepository.insertContactJson(contactsArray.getJSONObject(i));
                            }

                            // 群组
                            JSONArray groupsArray = dataJsonObject.getJSONArray("groups");
                            for (int i = 0; i < groupsArray.length(); i++) {
                                JSONObject groupObject = groupsArray.getJSONObject(i);
//                                bdRepository.insertGroupJson(groupObject);
                            }

                            // 客服会话Thread消息
                            JSONArray agentThreadsArray = dataJsonObject.getJSONArray("agentThreads");
                            for (int i = 0; i < agentThreadsArray.length(); i++) {
                                JSONObject threadObject = agentThreadsArray.getJSONObject(i);
                                bdRepository.insertThreadJson(threadObject);
                            }

                            // 一对一会话contactThreads
                            JSONArray contactThreadsArray = dataJsonObject.getJSONArray("contactThreads");
                            for (int i = 0; i < contactThreadsArray.length(); i++) {
                                JSONObject threadObject = contactThreadsArray.getJSONObject(i);
                                bdRepository.insertThreadJson(threadObject);
                            }

                            // 群组groupThreads
                            JSONArray groupThreadsArray = dataJsonObject.getJSONArray("groupThreads");
                            for (int i = 0; i < groupThreadsArray.length(); i++) {
                                JSONObject threadObject = groupThreadsArray.getJSONObject(i);
                                bdRepository.insertThreadJson(threadObject);
                            }

                            if (null != baseCallback) {
                                // 回调
                                baseCallback.onSuccess(jsonObject);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                failedCallback(baseCallback);
            }
        });
    }


    public static void userProfile(final Context context, final BaseCallback baseCallback) {

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .userProfile(client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
//                    resultCallback(response, baseCallback);
                    try {
                        //
                        if (response.code() == 401) {
                            Logger.e("401 token 无效");

                            if (null != baseCallback) {

                                // 清空本地数据
                                // BDRepository.getInstance(context).deleteAll();
                                BDPreferenceManager.getInstance(context).clear();

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("message", "token过期，请重新登录");
                                jsonObject.put("status_code", 401);
                                jsonObject.put("data", "");

                                baseCallback.onError(jsonObject);
                            }

                        } else {
//                            Logger.i("user profile: " + bodyJson);
                            if (null != response.body()) {
                                //
                                String bodyJson = response.body().string();
                                JSONObject jsonObject = new JSONObject(bodyJson);

                                // 数据持久化
                                BDPreferenceManager bdPreferenceManager = BDPreferenceManager.getInstance(context);
                                //
                                JSONObject infoObject = jsonObject.getJSONObject("data");
                                bdPreferenceManager.setUid(infoObject.getString("uid"));
                                bdPreferenceManager.setUsername(infoObject.getString("username"));
                                bdPreferenceManager.setNickname(infoObject.getString("nickname"));
                                bdPreferenceManager.setAvatar(infoObject.getString("avatar"));
                                bdPreferenceManager.setRealName(infoObject.getString("realName"));
                                bdPreferenceManager.setSubDomain(infoObject.getString("subDomain"));
                                bdPreferenceManager.setDescription(infoObject.getString("description"));
                                bdPreferenceManager.setAcceptStatus(infoObject.getString("acceptStatus"));
                                bdPreferenceManager.setAutoReplyContent(infoObject.getString("autoReplyContent"));
                                bdPreferenceManager.setWelcomeTip(infoObject.getString("welcomeTip"));

                                if (null != baseCallback) {
                                    // 回调
                                    baseCallback.onSuccess(jsonObject);
                                }
                            } else {
                                //
                                failedCallback(baseCallback);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    public static void onlineAgents(final Context context, int page, int size, final BaseCallback baseCallback) {

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .onlineAgents(page, size, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    public static void userDetail(final Context context, String uid, final BaseCallback baseCallback) {

        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .userDetail(uid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 客服端加载正在进行中会话
     *
     * @param baseCallback
     */
    public static void getThreads(Context context, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getThreads(client)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                failedCallback(baseCallback);
            }
        });
    }

    /**
     * 客服端分页加载-客服自己的历史会话：客服会话
     *
     * @param context
     * @param page
     * @param size
     * @param baseCallback
     */
    public static void getThreadHistoryRecords(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getThreadHistoryRecords(page, size, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     *
     * @param context
     * @param uid
     * @param baseCallback
     */
    public static void getTicketCategories(Context context, String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getTicketCategories(BDConfig.getInstance(context).getVisitorTicketCategoriesUrl(), uid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param context
     * @param baseCallback
     */
    public static void getTickets(Context context, int page, int size,  final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getTickets(BDConfig.getInstance(context).getVisitorMineTicketUrl(), page, size, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     *
     * @param context
     * @param uid
     * @param urgent
     * @param cid
     * @param content
     * @param mobile
     * @param email
     * @param fileUrl
     * @param baseCallback
     */
    public static void createTicket(Context context, String uid, String urgent, String cid, String content,
                                    String mobile, String email, String fileUrl,
                                    final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("urgent", urgent);
            jsonObject.put("cid", cid);
            jsonObject.put("content", content);
            jsonObject.put("mobile", mobile);
            jsonObject.put("email", email);
            jsonObject.put("fileUrl", fileUrl);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
            .createTicket(BDConfig.getInstance(context).getCreateTicketUrl(), requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    try {
                        //
                        String bodyJson = response.body().string();
                        Logger.i("create ticket: " + bodyJson);
                        //
                        if (null != bodyJson) {
                            //
                            JSONObject jsonObject = new JSONObject(bodyJson);
                            baseCallback.onSuccess(jsonObject);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }

    /**
     *
     * @param context
     * @param uid
     * @param baseCallback
     */
    public static void getFeedbackCategories(Context context, String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getFeedbackCategories(BDConfig.getInstance(context).getVisitorFeedbackCategoriesUrl(), uid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 我的访客
     *
     * @param context
     * @param baseCallback
     */
    public static void getFeedbacks(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getFeedbacks(BDConfig.getInstance(context).getVisitorMineFeedbackUrl(), page, size, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     *
     * @param context
     * @param uid
     * @param cid
     * @param content
     * @param mobile
     * @param email
     * @param fileUrl
     * @param baseCallback
     */
    public static void createFeedback(Context context, String uid, String cid, String content,
                                      String mobile, String email, String fileUrl,
                                      final BaseCallback baseCallback) {
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("cid", cid);
            jsonObject.put("content", content);
            jsonObject.put("mobile", mobile);
            jsonObject.put("email", email);
            jsonObject.put("fileUrl", fileUrl);
            jsonObject.put("client", BDCoreConstant.CLIENT_ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
            .createFeedback(BDConfig.getInstance(context).getCreateFeedbackUrl(), requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    try {
                        //
                        String bodyJson = response.body().string();
                        Logger.i("create ticket: " + bodyJson);
                        //
                        if (null != bodyJson) {
                            //
                            JSONObject jsonObject = new JSONObject(bodyJson);
                            baseCallback.onSuccess(jsonObject);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param context
     * @param uid
     * @param baseCallback
     */
    public static void getSupportCategories(Context context, String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getSupportCategories(BDConfig.getInstance(context).getVisitorSupportCategoriesUrl(), uid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param context
     * @param cid
     * 
     * @param baseCallback
     */
    public static void getCategoryDetail(Context context, String cid, String access_token, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getCategoryDetail(access_token, cid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param context
     * @param uid
     * @param baseCallback
     */
    public static void getArticles(Context context, String uid, String type, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getArticles(BDConfig.getInstance(context).getVisitorArticlesUrl(), uid, type, page, size, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }
                });
    }


    public static void getArticleDetail(Context context, String aid, String access_token, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getArticleDetail(access_token, aid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }

    /**
     *
     * @param context
     * @param uid
     * @param content
     * 
     * @param baseCallback
     */
    public static void searchArticle(Context context, String uid, String content, String access_token, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .searchArticle(access_token, uid, content, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    public static void rateArticle(Context context, String aid, boolean rate, String access_token, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("aid", aid);
            jsonObject.put("rate", rate);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .rateArticle(access_token, requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    public static void rate(Context context, String tid, int score, String note, boolean invite, String access_token, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tid", tid);
            jsonObject.put("score", score);
            jsonObject.put("note", note);
            jsonObject.put("invite", invite);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .rate(access_token, requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     * 分页获取队列
     *
     * @param page
     * @param size
     * @param baseCallback
     */
    public static void getQueues(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getQueues(page, size, client)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                failedCallback(baseCallback);
            }
        });
    }


    /**
     *
     * @param qid
     * @param baseCallback
     */
    public static void acceptQueue(Context context, String qid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .acceptQueue(qid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param baseCallback
     */
    public static void getContacts(Context context, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .getContacts(client)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                failedCallback(baseCallback);
            }

        });
    }


    public static void getStrangers(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getStrangers(page, size, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    public static void getFollows(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getFollows(page, size, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }

    public static void getFans(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getFans(page, size, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }

    public static void getFriends(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getFriends(page, size, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }

    public static void getBlocks(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getBlocks(page, size, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }

    public static void addFollow(Context context,  String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .addFollow(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    public static void unFollow(Context context, String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .unFollow(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    public static void addFriend(Context context, String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("notify", true);
            jsonObject.put("approve", false);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .addFriend(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    public static void removeFriend(Context context, String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .removeFriend(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    public static void isFollowed(Context context, String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .isFollowed(uid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }

    public static void getRelation(Context context, String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getRelation(uid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }

    /**
     * 判断自己是否已经屏蔽对方
     *
     * @param uid
     * @param baseCallback
     */
    public static void isShield(Context context, String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .isShield( uid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    /**
     * 判断自己是否已经被对方屏蔽
     *
     * @param uid
     * @param baseCallback
     */
    public static void isShielded(Context context, String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .isShielded(uid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }



    public static void addBlock(Context context, String uid, String note, String type, String uuid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("note", note);
            jsonObject.put("type", type);
            jsonObject.put("uuid", uuid); // 客服端拉黑，工作组wid
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .addBlock(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }

    public static void unBlock(Context context, String bid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("bid", bid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .unBlock(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    /**
     *
     * @param tid
     * @param baseCallback
     */
    public static void visitorCloseThread(Context context, String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tid", tid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
            .visitorCloseThread(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    /**
     *
     * @param tid
     * @param baseCallback
     */
    public static void agentCloseThread(Context context, String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tid", tid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        //
        HttpManager.getInstance(context).getWebService()
            .agentCloseThread(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param nickname
     * @param baseCallback
     */
    public static void updateNickname(Context context, String nickname, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickname", nickname);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .updateNickname(requestBody)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                failedCallback(baseCallback);
            }
        });
    }


    /**
     *
     * 
     * @param isAutoReply
     * @param content
     * @param baseCallback
     */
    public static void updateAutoReply(Context context,  boolean isAutoReply, String content, String imageUrl, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("autoReply", isAutoReply);
//            jsonObject.put("autoReplyContent", content);
            jsonObject.put("content", content);
            jsonObject.put("imageUrl", imageUrl);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .updateAutoReply(requestBody)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                failedCallback(baseCallback);
            }
        });
    }


    /**
     * 修改密码
     *
     * @param password
     * @param baseCallback
     */
    public static void changePassword(Context context, String password, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .changePassword( requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    /**
     * 修改 欢迎语
     *
     * @param welcome
     * @param baseCallback
     */
    public static void updateWelcome(Context context, String welcome, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("welcomeTip", welcome);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .updateWelcome( requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }

    /**
     * 修改 个性签名
     *
     * @param description
     * @param baseCallback
     */
    public static void updateDescription(Context context, String description, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("description", description);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .updateDescription( requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }


    public static void updateCurrentThread(Context context, String preTid, String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("preTid", preTid);
            jsonObject.put("tid", tid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .updateCurrentThread(requestBody)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                failedCallback(baseCallback);
            }
        });
    }


    /**
     * 会话是否被置顶
     *
     * 
     * @param tid
     * @param baseCallback
     */
    public static void isTopThread(Context context, String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .isTopThread(tid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    /**
     * 会话置顶
     *
     * 
     * @param tid
     * @param baseCallback
     */
    public static void markTopThread(final Context context, final String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tid", tid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .markTopThread(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        try {
                            //
                            if (response.code() == 401) {
                                Logger.e("401 token 无效");

                                if (null != baseCallback) {

                                    JSONObject jsonObject = new JSONObject();

                                    jsonObject.put("message", "token过期，请重新登录");
                                    jsonObject.put("status_code", 401);
                                    jsonObject.put("data", "");

                                    baseCallback.onError(jsonObject);
                                }

                            } else {
                                String bodyJson = response.body().string();
                                Logger.i("update current thread : " + bodyJson);

                                if (null != bodyJson) {
                                    //
                                    JSONObject jsonObject = new JSONObject(bodyJson);

                                    int status_code = jsonObject.getInt("status_code");
                                    if (status_code == 200) {
                                        // 更新本地数据
//                                        // BDRepository.getInstance(context).markTopThread(tid);
                                    }
                                    //
                                    baseCallback.onSuccess(jsonObject);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     * 取消置顶会话
     *
     * 
     * @param tid
     * @param baseCallback
     */
    public static void unmarkTopThread(final Context context, final String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tid", tid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .unmarkTopThread(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String bodyJson = response.body().string();
                            Logger.i("update current thread : " + bodyJson);

                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                int status_code = jsonObject.getInt("status_code");
                                if (status_code == 200) {
                                    // 更新本地数据
                                    // BDRepository.getInstance(context).unmarkTopThread(tid);
                                }
                                //
                                baseCallback.onSuccess(jsonObject);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     * 设置会话免打扰
     *
     * 
     * @param tid
     * @param baseCallback
     */
    public static void markNoDisturbThread(final Context context, final String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tid", tid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .markNoDisturbThread(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        //
                        if (response.code() == 401) {
                            Logger.e("401 token 无效");

                            if (null != baseCallback) {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("message", "token过期，请重新登录");
                                jsonObject.put("status_code", 401);
                                jsonObject.put("data", "");

                                baseCallback.onError(jsonObject);
                            }

                        } else {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("update current thread : " + bodyJson);

                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                int status_code = jsonObject.getInt("status_code");
                                if (status_code == 200) {
                                    // 更新本地数据
                                    // BDRepository.getInstance(context).markDisturbThread(tid);
                                }
                                //
                                baseCallback.onSuccess(jsonObject);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 取消会话免打扰
     *
     * 
     * @param tid
     * @param baseCallback
     */
    public static void unmarkNoDisturbThread(final Context context, final String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tid", tid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .unmarkNoDisturbThread(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        //
                        if (response.code() == 401) {
                            Logger.e("401 token 无效");

                            if (null != baseCallback) {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("message", "token过期，请重新登录");
                                jsonObject.put("status_code", 401);
                                jsonObject.put("data", "");

                                baseCallback.onError(jsonObject);
                            }

                        } else {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("update current thread : " + bodyJson);

                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                int status_code = jsonObject.getInt("status_code");
                                if (status_code == 200) {
                                    // 更新本地数据
                                    // BDRepository.getInstance(context).unmarkDisturbThread(tid);
                                }
                                //
                                baseCallback.onSuccess(jsonObject);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 标记未读会话
     *
     * 
     * @param tid
     * @param baseCallback
     */
    public static void markUnreadThread(final Context context, final String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tid", tid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .markUnreadThread(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        //
                        if (response.code() == 401) {
                            Logger.e("401 token 无效");

                            if (null != baseCallback) {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("message", "token过期，请重新登录");
                                jsonObject.put("status_code", 401);
                                jsonObject.put("data", "");

                                baseCallback.onError(jsonObject);
                            }

                        } else {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("update current thread : " + bodyJson);

                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                int status_code = jsonObject.getInt("status_code");
                                if (status_code == 200) {
                                    // 更新本地数据
                                    // BDRepository.getInstance(context).markUnreadThread(tid);
                                }
                                //
                                baseCallback.onSuccess(jsonObject);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 取消标记未读会话
     *
     * 
     * @param tid
     * @param baseCallback
     */
    public static void unmarkUnreadThread(final Context context, final String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tid", tid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .unmarkUnreadThread(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        //
                        if (response.code() == 401) {
                            Logger.e("401 token 无效");

                            if (null != baseCallback) {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("message", "token过期，请重新登录");
                                jsonObject.put("status_code", 401);
                                jsonObject.put("data", "");

                                baseCallback.onError(jsonObject);
                            }

                        } else {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("update current thread : " + bodyJson);

                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                int status_code = jsonObject.getInt("status_code");
                                if (status_code == 200) {
                                    // 更新本地数据
                                    // BDRepository.getInstance(context).unmarkUnreadThread(tid);
                                }
                                //
                                baseCallback.onSuccess(jsonObject);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 标记会话已删除
     *
     * @param tid
     * @param baseCallback
     */
    public static void markDeletedThread(final Context context, final String tid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tid", tid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .markDeletedThread(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        //
                        if (response.code() == 401) {
                            Logger.e("401 token 无效");

                            if (null != baseCallback) {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("message", "token过期，请重新登录");
                                jsonObject.put("status_code", 401);
                                jsonObject.put("data", "");

                                baseCallback.onError(jsonObject);
                            }

                        } else {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("update current thread : " + bodyJson);

                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                int status_code = jsonObject.getInt("status_code");
                                if (status_code == 200) {
                                    // 更新本地数据
//                                    // BDRepository.getInstance(context).markDeletedMessage(tid);
                                }
                                //
                                baseCallback.onSuccess(jsonObject);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    /**
     * 屏蔽对方，则对方无法给自己发送消息。但自己仍然可以给对方发送消息
     *
     * @param context
     * @param uid
     * @param baseCallback
     */
    public static void shield(final Context context, final String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .shield(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 取消屏蔽
     *
     * @param context
     * @param uid
     * @param baseCallback
     */
    public static void unshield(final Context context, final String uid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .unshield(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    /**
     *
     * @param status
     * @param baseCallback
     */
    public static void setAcceptStatus(Context context, String status, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .setAcceptStatus(requestBody)
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                failedCallback(baseCallback);
            }
        });
    }


    /**
     *
     * @param baseCallback
     */
    public static void getGroups(Context context,  final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getGroups(client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    /**
     *
     * @param gid
     * @param baseCallback
     */
    public static void getGroupDetail(Context context, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getGroupDetail(gid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    /**
     *
     * @param gid
     * @param baseCallback
     */
    public static void getGroupMembers(Context context, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getGroupMembers(gid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    /**
     *
     * @param nickname
     * @param selectedContacts
     * @param baseCallback
     */
    public static void createGroup(final Context context, String nickname, List<String> selectedContacts, String type, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < selectedContacts.size(); i++) {
            jsonArray.put(selectedContacts.get(i));
        }
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickname", nickname);
            jsonObject.put("type", type);
            jsonObject.put("selectedContacts", jsonArray);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .createGroup(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        //
                        if (response.code() == 401) {
                            Logger.e("401 token 无效");

                            if (null != baseCallback) {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("message", "token过期，请重新登录");
                                jsonObject.put("status_code", 401);
                                jsonObject.put("data", "");

                                baseCallback.onError(jsonObject);
                            }

                        } else {
                            //
                            String bodyJson = response.body().string();
                            Logger.i("createGroup : " + bodyJson);

                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                //
                                BDRepository bdRepository = BDRepository.getInstance(context);
//                                bdRepository.insertGroupJson(jsonObject.getJSONObject("data"));
                                //
                                baseCallback.onSuccess(jsonObject);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param gid
     * @param nickname
     * @param baseCallback
     */
    public static void updateGroupNickname(Context context, String gid, String nickname, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gid", gid);
            jsonObject.put("nickname", nickname);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .updateGroupNickname(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * 
     * @param gid
     * @param announcement
     * @param baseCallback
     */
    public static void updateGroupAnnouncement(Context context, String gid, String announcement, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gid", gid);
            jsonObject.put("announcement", announcement);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .updateGroupAnnouncement(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * 
     * @param gid
     * @param description
     * @param baseCallback
     */
    public static void updateGroupDescription(Context context, String gid, String description, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gid", gid);
            jsonObject.put("description", description);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .updateGroupDescription(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 邀请一个人入群
     *
     * @param uid
     * @param gid
     * @param baseCallback
     */
    public static void inviteToGroup(Context context, String uid, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .inviteToGroup(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 同时邀请多人入群
     *
     * @param selectedContacts
     * @param gid
     * @param baseCallback
     */
    public static void inviteListToGroup(Context context, List<String> selectedContacts, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < selectedContacts.size(); i++) {
            jsonArray.put(selectedContacts.get(i));
        }
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uidList", jsonArray);
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
                .inviteListToGroup(requestBody)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failedCallback(baseCallback);
                    }
                });
    }


    /**
     * 主动加群，不需要群主审核
     *
     * @param gid
     * @param baseCallback
     */
    public static void joinGroup(Context context, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .joinGroup(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    /**
     * 申请加群，需要群主审核
     *
     * @param gid
     * @param baseCallback
     */
    public static void applyGroup(Context context, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .applyGroup(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 同意加群请求
     *
     * @param nid
     * @param baseCallback
     */
    public static void approveGroupApply(Context context, String nid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nid", nid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .approveGroupApply(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 拒绝加群请求
     *
     * @param nid
     * @param baseCallback
     */
    public static void denyGroupApply(Context context, String nid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nid", nid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .denyGroupApply(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 将某人踢出群
     *
     * @param uid
     * @param gid
     * @param baseCallback
     */
    public static void kickGroupMember(Context context, String uid, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .kickGroupMember(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 将某人禁言
     *
     * @param uid
     * @param gid
     * @param baseCallback
     */
    public static void muteGroupMember(Context context, String uid, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .muteGroupMember(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    /**
     * 将某人取消禁言
     *
     * @param uid
     * @param gid
     * @param baseCallback
     */
    public static void unmuteGroupMember(Context context, String uid, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .unmuteGroupMember(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 将某人设置为群组管理员
     *
     * @param uid
     * @param gid
     * @param baseCallback
     */
    public static void setGroupAdmin(Context context, String uid, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .setGroupAdmin(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     * 取消某人群组管理员身份
     *
     * @param uid
     * @param gid
     * @param baseCallback
     */
    public static void unsetGroupAdmin(Context context, String uid, String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .unsetGroupAdmin(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param uid
     * @param gid
     * @param baseCallback
     */
    public static void transferGroup(Context context, String uid, String gid, boolean need_approve, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("gid", gid);
            // 默认不需要对方同意即可转交群
            jsonObject.put("need_approve", need_approve);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .transferGroup(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param nid
     * @param baseCallback
     */
    public static void acceptGroupTransfer(Context context, String nid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nid", nid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .acceptGroupTransfer(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param nid
     * @param baseCallback
     */
    public static void rejectGroupTransfer(Context context, String nid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nid", nid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .rejectGroupTransfer(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param gid
     * @param baseCallback
     */
    public static void withdrawGroup(final Context context, final String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .withdrawGroup(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    //
                    try {
                        //
                        if (response.code() == 401) {
                            Logger.e("401 token 无效");

                            if (null != baseCallback) {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("message", "token过期，请重新登录");
                                jsonObject.put("status_code", 401);
                                jsonObject.put("data", "");

                                baseCallback.onError(jsonObject);
                            }

                        } else {
                            String bodyJson = response.body().string();

                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);

                                int status_code = jsonObject.getInt("status_code");
                                if (status_code == 200) {
                                    // 更新本地数据
                                    // TODO: 删除本地会话，关闭会话窗口，直接退出到thread会话列表
                                    // BDRepository.getInstance(context).deleteGroupThread(gid);
                                    // BDRepository.getInstance(context).deleteGroup(gid);
                                }
                                //
                                baseCallback.onSuccess(jsonObject);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //
//                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param gid
     * @param baseCallback
     */
    public static void dismissGroup(final Context context, final String gid, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gid", gid);
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .dismissGroup(requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    //
                    try {
                        //
                        if (response.code() == 401) {
                            Logger.e("401 token 无效");

                            if (null != baseCallback) {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("message", "token过期，请重新登录");
                                jsonObject.put("status_code", 401);
                                jsonObject.put("data", "");

                                baseCallback.onError(jsonObject);
                            }

                        } else {
                            String bodyJson = response.body().string();

                            if (null != bodyJson) {
                                //
                                JSONObject jsonObject = new JSONObject(bodyJson);

                                int status_code = jsonObject.getInt("status_code");
                                if (status_code == 200) {
                                    // 更新本地数据
                                    // TODO: 删除本地group、及thread
                                    // BDRepository.getInstance(context).deleteGroupThread(gid);
                                    // BDRepository.getInstance(context).deleteGroup(gid);
                                }
                                //
                                baseCallback.onSuccess(jsonObject);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //
//                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }


    /**
     *
     * @param baseCallback
     */
    public static void filterGroup(Context context, String keyword, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .filterGroup(keyword, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    /**
     *
     * 
     * @param baseCallback
     */
    public static void filterGroupMembers(Context context, String gid, String keyword, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .filterGroupMembers(gid, keyword, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    /**
     * 分页加载通知
     *
     * @param page
     * @param size
     * @param baseCallback
     */
    public static void getNotices(Context context, int page, int size, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getNotices(page, size, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }


    /**
     * 检测APP版本是否需要更新
     *
     * @param context
     * @param key
     * @param baseCallback
     */
    public static void checkAppVersion(Context context, String key, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
                .checkAppVersion(key, client)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //
                        resultCallback(response, baseCallback);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        failedCallback(baseCallback);
                    }

                });
    }


    /**
     * 上传头像
     *
     * @param filePath
     * @param fileName
     * @param baseCallback
     */
    public static void uploadAvatar(Context context, String username, String filePath, String fileName, final BaseCallback baseCallback) {

        File file = new File(filePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestFile);
        // finally, execute the request
        RequestBody fileNameBody = RequestBody.create(okhttp3.MultipartBody.FORM, fileName);
        RequestBody usernameBody = RequestBody.create(okhttp3.MultipartBody.FORM, username);
        Call<ResponseBody> call =  HttpManager.getInstance(context).getWebService().uploadAvatar(BDConfig.getInstance(context).getUploadAvatarUrl(), fileNameBody, usernameBody, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.d("Upload error:"+ t.getMessage());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message", "failed");
                    jsonObject.put("status_code", -1);
                    jsonObject.put("data", "");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                baseCallback.onError(jsonObject);

            }
        });
    }

    /**
     * 上传图片
     *
     * @param filePath
     * @param fileName
     * @param baseCallback
     */
    public static void uploadImage(Context context, String username, String filePath, String fileName, final BaseCallback baseCallback) {

        File file = new File(filePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestFile);
        // finally, execute the request
        RequestBody fileNameBody = RequestBody.create(okhttp3.MultipartBody.FORM, fileName);
        RequestBody usernameBody = RequestBody.create(okhttp3.MultipartBody.FORM, username);
        Call<ResponseBody> call =  HttpManager.getInstance(context).getWebService().uploadImage(BDConfig.getInstance(context).getUploadImageUrl(), fileNameBody, usernameBody, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.d("Upload error:"+ t.getMessage());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message", "failed");
                    jsonObject.put("status_code", -1);
                    jsonObject.put("data", "");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                baseCallback.onError(jsonObject);

            }
        });
    }

    /**
     * 上传语音
     *
     * @param username
     * @param filePath
     * @param fileName
     * @param baseCallback
     */
    public static void uploadVoice(Context context, String username, String filePath, String fileName, final BaseCallback baseCallback) {

        File file = new File(filePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestFile);
        // finally, execute the request
        RequestBody fileNameBody = RequestBody.create(okhttp3.MultipartBody.FORM, fileName);
        RequestBody usernameBody = RequestBody.create(okhttp3.MultipartBody.FORM, username);
        Call<ResponseBody> call =  HttpManager.getInstance(context).getWebService().uploadVoice(BDConfig.getInstance(context).getUploadVoiceUrl(), fileNameBody, usernameBody, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                //
                resultCallback(response, baseCallback);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.d("Upload error:"+ t.getMessage());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message", "failed");
                    jsonObject.put("status_code", -1);
                    jsonObject.put("data", "");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                baseCallback.onError(jsonObject);

            }
        });
    }


    /**
     *
     * @param username
     * @param filePath
     * @param fileName
     * @param baseCallback
     */
    public static void uploadFile(Context context, String username, String filePath, String fileName, final BaseCallback baseCallback) {

        File file = new File(filePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part fileBody = MultipartBody.Part.createFormData("file", fileName, requestFile);
        //
        RequestBody fileNameBody = RequestBody.create(okhttp3.MultipartBody.FORM, fileName);
        RequestBody usernameBody = RequestBody.create(okhttp3.MultipartBody.FORM, username);
        // finally, execute the request
        Call<ResponseBody> call =  HttpManager.getInstance(context).getWebService().uploadFile(BDConfig.getInstance(context).getUploadFileUrl(), fileNameBody, usernameBody, fileBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                //
                resultCallback(response, baseCallback);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.e("Upload error:"+ t.getMessage());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message", "failed");
                    jsonObject.put("status_code", -1);
                    jsonObject.put("data", "");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

                baseCallback.onError(jsonObject);

            }
        });
    }


    /**
     *
     * @param username
     * @param filePath
     * @param fileName
     * @param baseCallback
     */
    public static void uploadVideo(Context context, String username, String filePath, String fileName, final BaseCallback baseCallback) {

        File file = new File(filePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part fileBody = MultipartBody.Part.createFormData("file", fileName, requestFile);
        //
        RequestBody fileNameBody = RequestBody.create(okhttp3.MultipartBody.FORM, fileName);
        RequestBody usernameBody = RequestBody.create(okhttp3.MultipartBody.FORM, username);
        // finally, execute the request
        Call<ResponseBody> call =  HttpManager.getInstance(context).getWebService().uploadVideo(BDConfig.getInstance(context).getUploadVideoUrl(), fileNameBody, usernameBody, fileBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                //
                resultCallback(response, baseCallback);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.e("Upload error:"+ t.getMessage());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message", "failed");
                    jsonObject.put("status_code", -1);
                    jsonObject.put("data", "");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

                baseCallback.onError(jsonObject);

            }
        });
    }

    /**
     *
     * @param username
     * @param filePath
     * @param fileName
     * @param baseCallback
     */
    public static void uploadWeChatDb(Context context, String username, String filePath, String fileName, final BaseCallback baseCallback) {

        File file = new File(filePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestFile);
        // finally, execute the request
        RequestBody fileNameBody = RequestBody.create(okhttp3.MultipartBody.FORM, fileName);
        RequestBody usernameBody = RequestBody.create(okhttp3.MultipartBody.FORM, username);
        Call<ResponseBody> call =  HttpManager.getInstance(context).getWebService().uploadWeChatDb(BDConfig.getInstance(context).getUploadWechatDbUrl(), fileNameBody, usernameBody, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                //
                resultCallback(response, baseCallback);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.d("Upload error:"+ t.getMessage());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message", "failed");
                    jsonObject.put("status_code", -1);
                    jsonObject.put("data", "");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                baseCallback.onError(jsonObject);

            }
        });
    }


    /**
     *
     * @param context
     * 
     * @param baseCallback
     */
    public static void logout(final Context context, String access_token, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        //
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client", client);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        HttpManager.getInstance(context).getWebService()
            .logout(access_token, requestBody)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    // 清空本地数据
                    // BDRepository.getInstance(context).deleteAll();
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failedCallback(baseCallback);
                }
            });
    }

    public static void refreshWXAccessToken(Context context, String appId, String refreshToken, final BaseCallback baseCallback) {
        //
        HttpManager.getInstance(context).getWebService()
            .refreshWXAccessToken(appId, refreshToken, "refresh_token")
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }

    public static void isWXAccessTokenValid(Context context, String openid, String accessToken, final BaseCallback baseCallback) {
        //
        HttpManager.getInstance(context).getWebService()
            .isWXAccessTokenValid(openid, accessToken)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }

    public static void getWXUserInfo(Context context, String openid, String accessToken, final BaseCallback baseCallback) {
        //
        HttpManager.getInstance(context).getWebService()
            .getWXUserInfo(openid, accessToken)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }
            });
    }


    private static void loginCallback(Context context, Response<ResponseBody> response, final BaseCallback baseCallback) {

        try {

            if (response.body() == null) {
                //
                if (null != baseCallback) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("message", "用户名或密码错误");
                        jsonObject.put("status_code", -1);
                        jsonObject.put("data", "");
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    baseCallback.onError(jsonObject);
                }
                return;
            }

            //
            String bodyJson = response.body().string();
//                        Logger.i("login %s:", bodyJson);

            if (null != bodyJson) {
                //
                JSONObject jsonObject = new JSONObject(bodyJson);
                jsonObject.put("message", "登录成功");
                jsonObject.put("status_code", 200);
                jsonObject.put("data", jsonObject.getString("access_token"));

                BDPreferenceManager bdPreferenceManager = BDPreferenceManager.getInstance(context);
                bdPreferenceManager.setAccessToken(jsonObject.getString("access_token"));
                bdPreferenceManager.setRefreshToken(jsonObject.getString("refresh_token"));
                bdPreferenceManager.setExpireIn(jsonObject.getInt("expires_in"));
                bdPreferenceManager.setTokenType(jsonObject.getString("token_type"));
//                            bdPreferenceManager.setRole(role);

                baseCallback.onSuccess(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用回调函数
     *
     * @param response
     * @param baseCallback
     */
    private static void resultCallback(Response<ResponseBody> response, final BaseCallback baseCallback) {
        try {
            //
            if (response.code() == 401) {
                Logger.e("401 token 无效");

                if (null != baseCallback) {

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("message", "未登录或token过期，请(重新)登录");
                    jsonObject.put("status_code", 401);
                    jsonObject.put("data", "");

                    baseCallback.onError(jsonObject);
                }

            } else {
                //
                if (null != response.body()) {
                    //
                    String bodyJson = response.body().string();
//                    Logger.i(bodyJson);
                    //
                    JSONObject jsonObject = new JSONObject(bodyJson);
                    //
                    if (null != baseCallback) {
                        baseCallback.onSuccess(jsonObject);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void failedCallback(final BaseCallback baseCallback) {

        if (null != baseCallback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", "failed");
                jsonObject.put("status_code", -1);
                jsonObject.put("data", "");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            baseCallback.onError(jsonObject);
        }
    }


    public static void getWebRTCWorkGroupAgent(Context context, String workGroupWid, String accessToken, final BaseCallback baseCallback) {
        //
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager.getInstance(context).getWebService()
            .getWebRTCWorkGroupAgent(accessToken, workGroupWid, client)
            .enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //
                    resultCallback(response, baseCallback);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    failedCallback(baseCallback);
                }

            });
    }



//    public static void getProto(Context context, final BaseCallback callback) {
//        //
//        HttpManager.getInstance(context).getWebService().getProto().enqueue(new Callback<HelloProto.HelloData>() {
//
//            @Override
//            public void onResponse(Call<HelloProto.HelloData> call, Response<HelloProto.HelloData> response) {
//
//                HelloProto.HelloData helloData = response.body();
//
//                Logger.i("get content: %s", helloData.getContent());
//            }
//
//            @Override
//            public void onFailure(Call<HelloProto.HelloData> call, Throwable t) {
//
//            }
//        });
//    }
//
//
//    public static void getProto2(Context context, String content, final BaseCallback callback) {
//        //
//        HttpManager.getInstance(context).getWebService().getProto2(content).enqueue(new Callback<HelloProto.HelloData>() {
//
//            @Override
//            public void onResponse(Call<HelloProto.HelloData> call, Response<HelloProto.HelloData> response) {
//
//                HelloProto.HelloData helloData = response.body();
//
//                Logger.i("get2 content: %s", helloData.getContent());
//            }
//
//            @Override
//            public void onFailure(Call<HelloProto.HelloData> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public static void postProto(Context context, String content, final BaseCallback callback) {
//        //
//        HelloProto.HelloData helloData = HelloProto.HelloData.newBuilder().setContent(content).build();
//        //
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-protobuf"), helloData.toByteArray());
//        //
//        HttpManager.getInstance(context).getWebService().postProto(requestBody).enqueue(new Callback<HelloProto.HelloData>() {
//            @Override
//            public void onResponse(Call<HelloProto.HelloData> call, Response<HelloProto.HelloData> response) {
//
//                HelloProto.HelloData helloData = response.body();
//
//                Logger.i("post content: %s", helloData.getContent());
//            }
//
//            @Override
//            public void onFailure(Call<HelloProto.HelloData> call, Throwable t) {
//
//            }
//        });
//    }

}











