package com.bytedesk.ui.api;

import android.content.Context;
import android.content.Intent;

import com.bytedesk.core.util.BDCoreConstant;
import com.bytedesk.ui.activity.BrowserActivity;
import com.bytedesk.ui.activity.ChatActivity;
import com.bytedesk.ui.util.BDUiConstant;

/**
 *
 * @author bytedesk.com
 */

public class BDUiApi {

    /**
     * 访客端接口，开启原生会话页面Activity
     *
     * @param context
     * @param uId
     * @param wId
     * @param title
     */
    public static void visitorStartChatActivity(Context context, String uId, String wId, String title) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(BDUiConstant.EXTRA_VISITOR, true);
        intent.putExtra(BDUiConstant.EXTRA_UID, uId);
        intent.putExtra(BDUiConstant.EXTRA_WID, wId);
        intent.putExtra(BDUiConstant.EXTRA_TITLE, title);
        intent.putExtra(BDUiConstant.EXTRA_CHAT_TYPE, BDCoreConstant.THREAD_TYPE_THREAD);
        context.startActivity(intent);
    }

    /**
     * 访客端接口，开启h5会话页面
     *
     * @param context
     * @param url
     */
    public static void visitorStartChatHtml5(Context context, String url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(BDUiConstant.EXTRA_URL, url);
        context.startActivity(intent);
    }

    /**
     * 客服端接口，开启原生会话页面Activity
     * 访客会话
     *
     * @param context
     * @param tId
     * @param title
     */
    public static void startThreadChatActivity(Context context, String tId, String uId, String title) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(BDUiConstant.EXTRA_VISITOR, false);
        intent.putExtra(BDUiConstant.EXTRA_TID, tId);
        intent.putExtra(BDUiConstant.EXTRA_UID, uId);
        intent.putExtra(BDUiConstant.EXTRA_TITLE, title);
        intent.putExtra(BDUiConstant.EXTRA_CHAT_TYPE, BDCoreConstant.THREAD_TYPE_THREAD);
        context.startActivity(intent);
    }

    /**
     * 同事会话
     *
     * @param context
     * @param cId
     * @param title
     */
    public static void startContactChatActivity(Context context, String cId, String title) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(BDUiConstant.EXTRA_VISITOR, false);
        intent.putExtra(BDUiConstant.EXTRA_UID, cId);
        intent.putExtra(BDUiConstant.EXTRA_TITLE, title);
        intent.putExtra(BDUiConstant.EXTRA_CHAT_TYPE, BDCoreConstant.THREAD_TYPE_CONTACT);
        context.startActivity(intent);
    }

    /**
     * 群聊
     *
     * @param context
     * @param gId
     * @param title
     */
    public static void startGroupChatActivity(Context context, String gId, String title) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(BDUiConstant.EXTRA_VISITOR, false);
        intent.putExtra(BDUiConstant.EXTRA_UID, gId);
        intent.putExtra(BDUiConstant.EXTRA_TITLE, title);
        intent.putExtra(BDUiConstant.EXTRA_CHAT_TYPE, BDCoreConstant.THREAD_TYPE_GROUP);
        context.startActivity(intent);
    }





}










