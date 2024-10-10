package com.bytedesk.im.core.http;

import android.content.Context;

import com.bytedesk.im.core.api.BDConfig;
import com.bytedesk.im.core.service.BDWebService;
import com.bytedesk.im.core.service.BDWebService2;
import com.bytedesk.im.core.util.BDCoreConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author bytedesk.com on 2017/8/30.
 */

public class HttpManager {

    private static HttpManager instance;

    private final BDWebService webService;

    private final BDWebService2 apiWebService;

    private final BDWebService loginWebService;

    /**
     * 构造方法私有
     */
    private HttpManager(Context context) {
        //
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // http://facebook.github.io/stetho/
//                .addNetworkInterceptor(new StethoInterceptor())
                // 添加拦截器：header、缓存控制器、http log、调试器
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request().newBuilder()
//                                //通过修改此处v1,v2等,修改restapi版本
//                                // application/vnd.bytedesk.v1+json
//                                .addHeader("Accept", "application/json")
//                                .build();
//                        return chain.proceed(request);
//                    }
//                })
                // TODO: 首先判断是否为debug，如是，则添加log
//                .addInterceptor(logging)
                // 超时设置
                .connectTimeout(BDCoreConstant.HTTP_DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(BDCoreConstant.HTTP_DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(BDCoreConstant.HTTP_DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 错误重连
                .retryOnConnectionFailure(true);
                // 支持HTTPS
//                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
                // cookie管理
//                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getInstance())))
//                .build();
        //
//        String access_token = BDPreferenceManager.getInstance(context).getAccessToken();
//        if (access_token != null && !access_token.trim().isEmpty()) {
////            Logger.i("Authorization " + access_token);
//            builder.addInterceptor( chain -> {
//                Request request = chain.request().newBuilder()
//                        .header("Authorization", "Bearer " + access_token)
//                        .build();
//                return chain.proceed(request);
//            });
//        }
        //
//        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        // 一定要在gsonconvert的前面
        //                .addConverterFactory(ProtoConverterFactory.createWithRegistry(registry))
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                // 一定要在gsonconvert的前面
//                .addConverterFactory(ProtoConverterFactory.createWithRegistry(registry))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(BDConfig.getInstance(context).getApiBaseUrl())
                .build();
        //
        webService = retrofit.create(BDWebService.class);
        apiWebService = retrofit.create(BDWebService2.class);

        //
        OkHttpClient.Builder loginBuilder = new OkHttpClient.Builder()
                // TODO: 首先判断是否为debug，如是，则添加log
//                .addInterceptor(logging)
                // 超时设置
                .connectTimeout(BDCoreConstant.HTTP_DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(BDCoreConstant.HTTP_DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(BDCoreConstant.HTTP_DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 错误重连
                .retryOnConnectionFailure(true);
        Retrofit loginRetrofit = new Retrofit.Builder()
                .client(loginBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(BDConfig.getInstance(context).getApiBaseUrl())
                .build();
        loginWebService = loginRetrofit.create(BDWebService.class);
    }

    //在访问HttpManagers时创建单例
//    private static class SingletonHolder {
//        private static final HttpManager INSTANCE = new HttpManager();
//    }

    //获取单例
//    public static HttpManager getInstance(Context context) {
////        TODO: 待优化复用
////       if (httpManager == null) {
//        HttpManager httpManager = new HttpManager(context);
////       }
//       return httpManager;
//    }
    public static HttpManager getInstance(Context context) {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager(context);
                }
            }
        }
        return instance;
    }

    public BDWebService getWebService() {
        return webService;
    }

    public BDWebService2 getApiWebService() {
        return apiWebService;
    }

    public BDWebService getLoginWebService() {
        return loginWebService;
    }

}
