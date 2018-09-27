package com.bytedesk.demo;

import android.app.Application;

import com.bytedesk.core.api.BDMqttApi;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by ningjinpeng on 2017/9/20.
 */

public class BDApplication extends Application{

    public void onCreate() {
        super.onCreate();

        // SQLite数据库调试库
        // http://facebook.github.io/stetho/
        // 使用方法： 浏览器中打开 chrome://inspect and 点击 "Inspect"
        Stetho.initializeWithDefaults(this);
        // https://github.com/orhanobut/logger
        Logger.addLogAdapter(new AndroidLogAdapter());

        //
        BDMqttApi.login(getApplicationContext());
    }


}
