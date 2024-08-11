package com.bytedesk.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bytedesk.core.api.BDMqttApi;
import com.orhanobut.logger.Logger;

/**
 * 监听屏幕解锁
 *
 * @author bytedesk.com on 2018/12/15
 */
public class BDScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.i("BDScreenReceiver");
        // FIXME: java.lang.RuntimeException: Unable to start receiver com.bytedesk.core.receiver.BDScreenReceiver:
        // FIXME: android.content.ReceiverCallNotAllowedException: BroadcastReceiver components are not allowed to bind to services
        // 尝试重连
//        BDMqttApi.connect(context);
    }
}
