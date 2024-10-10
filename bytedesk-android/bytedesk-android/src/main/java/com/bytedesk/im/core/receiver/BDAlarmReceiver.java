package com.bytedesk.im.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//import com.bytedesk.im.core.api.BDMqttApi;
import com.bytedesk.im.core.api.BytedeskStompApi;
import com.orhanobut.logger.Logger;

/**
 * @author bytedesk.com on 2018/12/15
 */
public class BDAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.i("BDAlarmReceiver");
        // 尝试重连
//        BDMqttApi.connect(context);
        BytedeskStompApi.getInstance(context).connect();
    }

}
