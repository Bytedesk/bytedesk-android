package com.bytedesk.ui.api;

import android.content.Context;
import android.content.Intent;

import com.bytedesk.ui.activity.BrowserActivity;
import com.bytedesk.ui.activity.ChatActivity;
import com.bytedesk.ui.util.BDUiConstant;

/**
 *
 * Created by ningjinpeng on 2017/8/23.
 */

public class BDUiApi {

    //
    public static void visitorStartChatActivity(Context context, String uId, String wId, String title) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(BDUiConstant.EXTRA_UID, uId);
        intent.putExtra(BDUiConstant.EXTRA_WID, wId);
        intent.putExtra(BDUiConstant.EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    //
    public static void visitorStartChatWap(Context context, String url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(BDUiConstant.EXTRA_URL, url);
        context.startActivity(intent);
    }


}
