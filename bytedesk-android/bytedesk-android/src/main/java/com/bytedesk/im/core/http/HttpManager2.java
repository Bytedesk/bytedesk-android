package com.bytedesk.im.core.http;

import android.content.Context;

import com.bytedesk.im.core.api.BDConfig;
import com.bytedesk.im.core.api.BytedeskConstants;
import com.bytedesk.im.core.service.BDWebService;
import com.bytedesk.im.core.service.BDWebService2;
import com.bytedesk.im.core.util.BDCoreConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager2 {

    private static HttpManager2 instance;

    private final BDWebService2 apiWebService;

    /**
     * 构造方法私有
     */
    private HttpManager2(Context context) {
        //
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // TODO: 首先判断是否为debug，如是，则添加log
                .addInterceptor(logging)
                // 超时设置
                .connectTimeout(BDCoreConstant.HTTP_DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(BDCoreConstant.HTTP_DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(BDCoreConstant.HTTP_DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 错误重连
                .retryOnConnectionFailure(true);
        //
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(BytedeskConstants.API_BASE_URL)
                .build();
        apiWebService = retrofit.create(BDWebService2.class);
    }

    // 获取单例
    public static HttpManager2 getInstance(Context context) {
        if (instance == null) {
            synchronized (HttpManager2.class) {
                if (instance == null) {
                    instance = new HttpManager2(context);
                }
            }
        }
        return instance;
    }

    public BDWebService2 getApiWebService() {
        return apiWebService;
    }

}
