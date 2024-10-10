package com.bytedesk.im.core.http;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.bytedesk.im.core.api.BDConfig;
import com.bytedesk.im.core.api.BytedeskConstants;
import com.bytedesk.im.core.api.BytedeskStompApi;
import com.bytedesk.im.core.callback.BaseCallback;
import com.bytedesk.im.core.util.BDCoreConstant;
import com.bytedesk.im.core.util.BDPreferenceManager;
import com.bytedesk.im.core.util.JsonResult;
import com.bytedesk.im.ui.activity.ChatKFActivity;
import com.bytedesk.im.ui.util.BDUiConstant;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BytedeskApi {


    public static void init(Context context, String orgUid) {
        Logger.i("HttpApi.init beforeStart: ");
        init(context, orgUid, null);
    }

    public static void init(Context context, String orgUid, final BaseCallback baseCallback) {
        Logger.i("HttpApi.init callback beforeStart: ");

        if (null == context) {
            return;
        }

        BDPreferenceManager.getInstance(context).setOrgUid(orgUid);
        String uid = BDPreferenceManager.getInstance(context).getUid();
        String nickname = BDPreferenceManager.getInstance(context).getNickname();
        String avatar = BDPreferenceManager.getInstance(context).getAvatar();
//
        String client = BDCoreConstant.CLIENT_ANDROID;
        HttpManager2.getInstance(context).getApiWebService()
                .init(orgUid, uid, nickname, avatar, client)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                        resultCallback(response, baseCallback);
                        try {
                            if (null != response.body()) {
                                String bodyJson = response.body().string();
                                // {"message":"success","code":200,"data":{"uid":"1491221637861678","nickname":"Local[192.168.0.102]","avatar":"https://cdn.weiyuai.cn/avatars/android_default_avatar.png","type":"VISITOR","extra":null}}
                                Logger.i("HttpApi.init onResponse: %s", bodyJson);
                                JSONObject jsonObject = new JSONObject(bodyJson);
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                BDPreferenceManager.getInstance(context).setUid(dataObject.getString("uid"));
                                BDPreferenceManager.getInstance(context).setNickname(dataObject.getString("nickname"));
                                BDPreferenceManager.getInstance(context).setAvatar(dataObject.getString("avatar"));
                                //
                                BytedeskStompApi.getInstance(context).connect();
                            } else {
                                Logger.i("HttpApi.init onResponse: response.body() is null");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Logger.i("HttpApi.init onFailure: ", t);
//                        failedCallback(baseCallback);
                    }
                });
    }

    public static void requestThread(Context context, String type, String sid, boolean forceAgent) {
        requestThread(context, type, sid, false, null);
    }

    public static void requestThread(Context context, String type, String sid, boolean forceAgent, final BaseCallback baseCallback) {

        String orgUid = BDPreferenceManager.getInstance(context).getOrgUid();
        String uid = BDPreferenceManager.getInstance(context).getUid();
        String nickname = BDPreferenceManager.getInstance(context).getNickname();
        String avatar = BDPreferenceManager.getInstance(context).getAvatar();
        String client = BDCoreConstant.CLIENT_ANDROID;

        HttpManager2.getInstance(context).getApiWebService()
                .requestThread(orgUid, type, sid, uid, nickname, avatar, forceAgent, client)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        resultCallback(response, baseCallback);
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Logger.i("HttpApi.requestThread onFailure: ", t);
                        failedCallback(baseCallback);
                    }
                });
    }

    public static void sendRestMessage(Context context, String json) {
        sendRestMessage(context, json, null);
    }

    public static void sendRestMessage(Context context, String json, final BaseCallback baseCallback) {

        HttpManager2.getInstance(context).getApiWebService()
                .sendRestMessage(json)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        resultCallback(response, baseCallback);
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Logger.i("HttpApi.sendRestMessage onFailure: ", t);
//                        failedCallback(baseCallback);
                    }
                });
    }

    public static void uploadFile(Context context, String filePath, String fileName, final BaseCallback baseCallback) {
        File file = new File(filePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part fileBody = MultipartBody.Part.createFormData("file", fileName, requestFile);
        //
        RequestBody fileNameBody = RequestBody.create(okhttp3.MultipartBody.FORM, fileName);
        RequestBody fileTypeBody = RequestBody.create(okhttp3.MultipartBody.FORM, "image/*");
        RequestBody isAvatarBody = RequestBody.create(okhttp3.MultipartBody.FORM, "false");
        RequestBody kbTypeBody = RequestBody.create(okhttp3.MultipartBody.FORM, BytedeskConstants.UPLOAD_TYPE_CHAT);
        RequestBody categoryUidBody = RequestBody.create(okhttp3.MultipartBody.FORM, "");
        RequestBody kbUidBody = RequestBody.create(okhttp3.MultipartBody.FORM, "");
        RequestBody clientBody = RequestBody.create(okhttp3.MultipartBody.FORM, BytedeskConstants.HTTP_CLIENT);
        // finally, execute the request
        Call<ResponseBody> call =  HttpManager2.getInstance(context).getApiWebService()
                .uploadFile(fileNameBody, fileTypeBody, isAvatarBody, kbTypeBody, categoryUidBody, kbUidBody, clientBody, fileBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                resultCallback(response, baseCallback);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.e("Upload error:"+ t.getMessage());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message", "failed");
                    jsonObject.put("code", -1);
                    jsonObject.put("data", "");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                baseCallback.onError(jsonObject);
            }
        });
    }


    public static void startWorkGroupChatActivity(Context context, String sid) {
        Intent intent = new Intent(context, ChatKFActivity.class);
        intent.putExtra(BDUiConstant.EXTRA_TYPE, "1");
        intent.putExtra(BDUiConstant.EXTRA_SID, sid);
        context.startActivity(intent);
    }

    public static void startAgentChatActivity(Context context, String sid) {
        Intent intent = new Intent(context, ChatKFActivity.class);
        intent.putExtra(BDUiConstant.EXTRA_TYPE, "0");
        intent.putExtra(BDUiConstant.EXTRA_SID, sid);
        context.startActivity(intent);
    }

    /**
     * 通用回调函数
     */
    private static void resultCallback(Response<ResponseBody> response, final BaseCallback baseCallback) {
        try {
            if (null != response.body()) {
                String bodyJson = response.body().string();
                Logger.i("resultCallback: ", bodyJson);
                if (null != baseCallback) {
                    JSONObject jsonObject = new JSONObject(bodyJson);
                    baseCallback.onSuccess(jsonObject);
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
                jsonObject.put("code", -1);
                jsonObject.put("data", "");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            baseCallback.onError(jsonObject);
        }
    }

    public static String formatThreadTopic(String type, String sid, String userUid) {
        if (type.equals("1")) {
            return String.format("org/workgroup/%s/%s", sid, userUid);
        } else {
            return String.format("org/agent/%s/%s", sid, userUid);
        }
    }

}
