package com.bytedesk.demo;

import android.os.Bundle;

import com.bytedesk.core.api.BDMqttApi;
import com.bytedesk.demo.common.BaseFragment;
import com.bytedesk.demo.common.TabFragment;
import com.bytedesk.ui.base.BaseFragmentActivity;

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

        // 建立长连接
        BDMqttApi.connect(this);
    }

}