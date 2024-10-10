/*
 * @Author: jackning 270580156@qq.com
 * @Date: 2024-10-09 15:16:45
 * @LastEditors: jackning 270580156@qq.com
 * @LastEditTime: 2024-10-09 18:12:08
 * @Description: bytedesk.com https://github.com/Bytedesk/bytedesk
 *   Please be aware of the BSL license restrictions before installing Bytedesk IM – 
 *  selling, reselling, or hosting Bytedesk IM as a service is a breach of the terms and automatically terminates your rights under the license. 
 *  仅支持企业内部员工自用，严禁私自用于销售、二次销售或者部署SaaS方式销售 
 *  Business Source License 1.1: https://github.com/Bytedesk/bytedesk/blob/main/LICENSE 
 *  contact: 270580156@qq.com 
 *  联系：270580156@qq.com
 * Copyright (c) 2024 by bytedesk.com, All Rights Reserved. 
 */
package com.bytedesk.im.core.service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface BDWebService2 {

        @GET("/visitor/api/v1/init")
        @Headers({"Content-Type:application/json; charset=utf-8"})
        Call<ResponseBody> init(@Query("orgUid") String orgUid,
                                @Query("uid") String uid,
                                @Query("nickname") String nickname,
                                @Query("avatar") String avatar,
                                @Query("client") String client);

        @GET("/visitor/api/v1/thread")
        @Headers({"Content-Type:application/json; charset=utf-8"})
        Call<ResponseBody> requestThread(@Query("orgUid") String orgUid,
                                         @Query("type") String type,
                                         @Query("sid") String sid,
                                         @Query("uid") String uid,
                                         @Query("nickname") String nickname,
                                         @Query("avatar") String avatar,
                                         @Query("forceAgent") boolean forceAgent,
                                         @Query("client") String client);

        @POST("/visitor/api/v1/message/send")
        Call<ResponseBody> sendRestMessage(@Part("json") String json);

        @Multipart
        @POST("/visitor/api/v1/upload/file")
        Call<ResponseBody> uploadFile(@Part("file_name") RequestBody fileName,
                                      @Part("file_type") RequestBody fileType,
                                      @Part("is_avatar") RequestBody isAvatar,
                                      @Part("kb_type") RequestBody kbType,
                                      @Part("category_uid") RequestBody categoryUid,
                                      @Part("kb_uid") RequestBody kbUid,
                                      @Part("client") RequestBody client,
                                      @Part MultipartBody.Part file);


}
