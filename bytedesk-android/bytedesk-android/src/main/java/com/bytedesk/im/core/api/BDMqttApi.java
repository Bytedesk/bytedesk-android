package com.bytedesk.im.core.api;
//
//import android.content.Context;
//
//import com.bytedesk.im.core.room.entity.ThreadEntity;
//import com.bytedesk.im.core.service.BDMqttService;
//import com.bytedesk.im.core.util.BDCoreConstant;
//import com.bytedesk.im.core.util.BDCoreUtils;
//import com.bytedesk.im.core.util.BDPreferenceManager;
//import com.bytedesk.im.core.util.ExtraParam;
//import com.orhanobut.logger.Logger;
//
//public class BDMqttApi {
//
//    /**
//     * @param context
//     */
//    public static void connect(Context context) {
//        //
////        String username = BDPreferenceManager.getInstance(context).getUsername();
//        String uid = BDPreferenceManager.getInstance(context).getUid();
//        if (uid == null || uid.isEmpty()) {
//            Logger.e("uid不能为空-请首先调用授权接口");
//        } else {
//            // 添加uuid后缀，解决同一账号异地登录的问题
////            String uuid = BDPreferenceManager.getInstance(context).getClientUUID();
////            String clientId = uid  + "/" + BDCoreConstant.CLIENT_ANDROID + "/" + uuid;
//            String clientId = uid  + "/" + BDCoreConstant.CLIENT_ANDROID;
//            connect(context, clientId);
//        }
//    }
//
//    /**
//     *
//     * @param context
//     * @param clientId
//     */
//    public static void connect(Context context, String clientId) {
//
//        BDMqttService.getInstance(context).connect(clientId);
//    }
//
//    /**
//     *
//     * @param context
//     * @param topic
//     */
//    public static void subscribeTopic(Context context, String topic) {
//
//        BDMqttService.getInstance(context).subscribeToTopic(topic);
//    }
//
//    public static void unsubscribeTopic(Context context, String topic) {
//
//        BDMqttService.getInstance(context).unSubscribeToTopic(topic);
//    }
//
//    /**
//     * 发送文本消息
//     */
//    public static void sendTextMessageProtobuf(Context context, String mid, String content, ThreadEntity threadEntity) {
//        Logger.i("sendTextMessageProtobuf %s", content);
//
//        sendMessageProtobuf(context, mid, BDCoreConstant.MESSAGE_TYPE_TEXT, content,
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                null);
//    }
//
//    /**
//     * 发送图片消息
//     */
//    public static void sendImageMessageProtobuf(Context context, String mid, String content, ThreadEntity threadEntity) {
//
//        sendMessageProtobuf(context, mid, BDCoreConstant.MESSAGE_TYPE_IMAGE, content,
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                null);
//    }
//
//
//    public static void sendFileMessageProtobuf(Context context, String mid, String content, ThreadEntity threadEntity) {
//
//        sendMessageProtobuf(context, mid, BDCoreConstant.MESSAGE_TYPE_FILE, content,
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                null);
//    }
//
//    /**
//     * 发送语音消息
//     */
//    public static void sendVoiceMessageProtobuf(Context context, String mid, String content, ThreadEntity threadEntity) {
//
//        sendMessageProtobuf(context, mid, BDCoreConstant.MESSAGE_TYPE_VOICE, content,
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                null);
//    }
//
//    /**
//     * 发送视频消息
//     *
//     * @param context
//     * @param mid
//     * @param content
//     * @param threadEntity
//     */
//    public static void sendVideoMessageProtobuf(Context context, String mid, String content, ThreadEntity threadEntity) {
//
//        sendMessageProtobuf(context, mid, BDCoreConstant.MESSAGE_TYPE_VIDEO, content,
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                null);
//    }
//
//
//    public static void sendCommodityMessageProtobuf(Context context, String mid, String content, ThreadEntity threadEntity) {
//
//        sendMessageProtobuf(context, mid, BDCoreConstant.MESSAGE_TYPE_COMMODITY, content,
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                null);
//    }
//
//    public static void sendInviteRateMessageProtobuf(Context context, String mid, String content, ThreadEntity threadEntity) {
//
//        sendMessageProtobuf(context, mid, BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_INVITE_RATE, content,
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                null);
//    }
//
//    public static void sendFormRequestMessageProtobuf(Context context, String mid, String content, ThreadEntity threadEntity) {
//
//        sendMessageProtobuf(context, mid, BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_FORM_REQUEST, content,
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                null);
//    }
//
//    /**
//     * 发送消息预知通知
//     */
//    public static void sendPreviewMessageProtobuf(Context context, ThreadEntity threadEntity, String previewContent) {
//
//        ExtraParam extraParam = new ExtraParam();
//        extraParam.setPreviewContent(previewContent);
//        //
//        sendMessageProtobuf(context, BDCoreUtils.uuid(), BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_PREVIEW, "content",
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                extraParam);
//    }
//
//    /**
//     * 撤回消息
//     */
//    public static void sendRecallMessageProtobuf(Context context, ThreadEntity threadEntity, String recallMid) {
//
//        ExtraParam extraParam = new ExtraParam();
//        extraParam.setRecallMid(recallMid);
//
//        sendMessageProtobuf(context, BDCoreUtils.uuid(), BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECALL, "content",
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                extraParam);
//    }
//
//    /**
//     * 发送消息回执通知
//     */
//    public static void sendReceiptReceivedMessageProtobuf(Context context, ThreadEntity threadEntity, String receiptMid) {
//
//        ExtraParam extraParam = new ExtraParam();
//        extraParam.setReceiptMid(receiptMid);
//        extraParam.setReceiptStatus(BDCoreConstant.MESSAGE_STATUS_RECEIVED);
//
//        sendReceiptMessageProtobuf(context, threadEntity, extraParam);
//    }
//
//    public static void sendReceiptReadMessageProtobuf(Context context, ThreadEntity threadEntity, String receiptMid) {
//
//        ExtraParam extraParam = new ExtraParam();
//        extraParam.setReceiptMid(receiptMid);
//        extraParam.setReceiptStatus(BDCoreConstant.MESSAGE_STATUS_READ);
//
//        sendReceiptMessageProtobuf(context, threadEntity, extraParam);
//    }
//
//    public static void sendReceiptMessageProtobuf(Context context, ThreadEntity threadEntity, ExtraParam extraParam) {
//
//        sendMessageProtobuf(context, BDCoreUtils.uuid(), BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECEIPT, "content",
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                extraParam);
//    }
//
//    /**
//     * 转接会话
//     */
//    public static void sendTransferMessageProtobuf(Context context, ThreadEntity threadEntity, String transferTopic, String transferContent) {
//
//        ExtraParam extraParam = new ExtraParam();
//        extraParam.setTransferTopic(transferTopic);
//        extraParam.setTransferContent(transferContent);
//
//        sendMessageProtobuf(context, BDCoreUtils.uuid(), BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_TRANSFER, "content",
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                extraParam);
//    }
//
//    public static void sendTransferAcceptMessageProtobuf(Context context, ThreadEntity threadEntity, String transferTopic) {
//
//        ExtraParam extraParam = new ExtraParam();
//        extraParam.setTransferTopic(transferTopic);
//
//        sendMessageProtobuf(context, BDCoreUtils.uuid(), BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_TRANSFER_ACCEPT, "content",
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                extraParam);
//    }
//
//    public static void sendTransferRejectMessageProtobuf(Context context, ThreadEntity threadEntity, String transferTopic) {
//
//        ExtraParam extraParam = new ExtraParam();
//        extraParam.setTransferTopic(transferTopic);
//
//        sendMessageProtobuf(context, BDCoreUtils.uuid(), BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_TRANSFER_REJECT, "content",
//                threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                extraParam);
//    }
//
//    /**
//     * 发送消息接口
//     */
//    public static void sendMessageProtobuf(Context context, String mid, String type, String content,
//                                           String tId, String topic, String threadType, String threadNickname, String threadAvatar, String client,
//                                           ExtraParam extraParam) {
//
//        BDMqttService.getInstance(context).sendMessageProtobuf(mid, type, content, tId, topic, threadType, threadNickname, threadAvatar, client, extraParam);
//    }
//
//    /**
//     * 修改在线状态
//     *
//     * @param context
//     * @param status
//     */
//    public static void setStatus(Context context, String status) {
//
////        BDMqttService.getInstance(context).setStatus(status);
//    }
//
//    /**
//     * 断开连接
//     *
//     * @param context
//     */
//    public static void disconnect(Context context) {
//
//        BDMqttService.getInstance(context).disconnect();
//    }
//
//    /**
//     * 是否连接
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isConnected(Context context) {
//
//        return BDMqttService.getInstance(context).isConnected();
//    }
//
//    public static boolean isDisconnected(Context context) {
//
//        return !BDMqttService.getInstance(context).isConnected();
//    }
//
//}




