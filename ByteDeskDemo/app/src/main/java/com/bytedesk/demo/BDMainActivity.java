package com.bytedesk.demo;

import android.os.Bundle;

import com.bytedesk.core.api.BDCoreApi;
import com.bytedesk.core.api.BDMqttApi;
import com.bytedesk.core.callback.LoginCallback;
import com.bytedesk.demo.common.BaseFragment;
import com.bytedesk.demo.common.TabFragment;
import com.bytedesk.ui.base.BaseFragmentActivity;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class BDMainActivity extends BaseFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.id.kefudemo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            BaseFragment fragment = new TabFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }

        // TODO: 参考文档：https://github.com/pengjinning/bytedesk-android
        // appkey和subDomain请替换为真实值
        final String appKey = "201809171553111";
        final String subDomain = "vip";

        // 授权登录接口
        BDCoreApi.visitorLogin(getApplicationContext(), appKey, subDomain, new LoginCallback() {

            @Override
            public void onSuccess(JSONObject object) {
                //
                try {
                    Logger.d("login success message: " + object.get("message") + " status_code:" + object.get("status_code"));

                    // 建立长连接
                    BDMqttApi.login(getApplicationContext());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject object) {
                try {
                    Logger.e("login failed message: " + object.get("message")
                            + " status_code:" + object.get("status_code")
                            + " data:" + object.get("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}