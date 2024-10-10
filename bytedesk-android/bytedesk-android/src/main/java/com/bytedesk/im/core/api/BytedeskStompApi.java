/*
 * @Author: jackning 270580156@qq.com
 * @Date: 2024-10-09 15:18:02
 * @LastEditors: jackning 270580156@qq.com
 * @LastEditTime: 2024-10-10 13:41:19
 * @Description: bytedesk.com https://github.com/Bytedesk/bytedesk
 *   Please be aware of the BSL license restrictions before installing Bytedesk IM – 
 *  selling, reselling, or hosting Bytedesk IM as a service is a breach of the terms and automatically terminates your rights under the license. 
 *  仅支持企业内部员工自用，严禁私自用于销售、二次销售或者部署SaaS方式销售 
 *  Business Source License 1.1: https://github.com/Bytedesk/bytedesk/blob/main/LICENSE 
 *  contact: 270580156@qq.com 
 *  联系：270580156@qq.com
 * Copyright (c) 2024 by bytedesk.com, All Rights Reserved. 
 */
package com.bytedesk.im.core.api;

import android.annotation.SuppressLint;
import android.content.Context;

import com.bytedesk.im.core.repository.BDRepository;
import com.bytedesk.im.core.room.entity.MessageEntity;
import com.bytedesk.im.core.util.BDPreferenceManager;
import com.bytedesk.im.stomp.Stomp;
import com.bytedesk.im.stomp.StompClient;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// https://github.com/NaikSoftware/StompProtocolAndroid
public class BytedeskStompApi {

    private static volatile BytedeskStompApi instance = null;
    private final StompClient mStompClient;
    private final List<String> subscribedTopics;
    private String transformedTopic;
    private final BDRepository mBDRepository;
    private final BDPreferenceManager mPreferenceManager;

    @SuppressLint("CheckResult")
    private BytedeskStompApi(Context context, String url) {
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);
        mStompClient.withServerHeartbeat(10 * 1000);
        mStompClient.withClientHeartbeat(10 * 1000);
        this.subscribedTopics = new ArrayList<>();
        mBDRepository = BDRepository.getInstance(context);
        mPreferenceManager = BDPreferenceManager.getInstance(context);
        mStompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Logger.i("Stomp connection opened");
                    break;
                case ERROR:
                    Logger.e(lifecycleEvent.getException(), "Error");
                    break;
                case CLOSED:
                    Logger.i("Stomp connection closed");
                    break;
                case FAILED_SERVER_HEARTBEAT:
                    Logger.e("Error", "Failed to send/receive heartbeat on time!");
                    break;
                default:
                    Logger.i("Stomp connection default");
                    break;
            }
        });
    }

    public static BytedeskStompApi getInstance(Context context) {
        if (instance == null) {
            synchronized (BytedeskStompApi.class) {
                if (instance == null) {
                    instance = new BytedeskStompApi(context, BytedeskConstants.STOMP_WS_URL);
                }
            }
        }
        // 如果url不同，可以考虑抛出异常或重新创建一个新的StompClient实例
        return instance;
    }

//    public StompClient getStompClient() {
//        return mStompClient;
//    }

    public void connect() {
        mStompClient.connect();
    }

    @SuppressLint("CheckResult")
    public void subscribe(String topic) {
        transformedTopic = topic.replaceAll("/", ".");
        Logger.i("stompSubscribe");
        if (mStompClient == null) {
            Logger.i("stompClient is null");
            return;
        }
        // 检查是否已订阅该主题
        if (subscribedTopics.contains(transformedTopic)) {
            return; // 如果已订阅，则直接返回
        }
        // 如果未订阅，则添加到订阅列表
        subscribedTopics.add(transformedTopic);
        // 订阅主题
        mStompClient.topic("/topic/" + transformedTopic).subscribe(topicMessage -> {
            Logger.json(topicMessage.getPayload());
            String messagePayload = topicMessage.getPayload();
            JSONObject jsonObject = new JSONObject(messagePayload);
            //
            MessageEntity message = MessageEntity.fromJson(jsonObject, mPreferenceManager.getUid());
            if (!message.isSend()) {
                // 接收的非自己发送消息
                switch (message.getType()) {
                    case BytedeskConstants.MESSAGE_TYPE_READ:
                    case BytedeskConstants.MESSAGE_TYPE_DELIVERED:
                        // 回执消息
                        updateMessageStatus(message);
                        return;
                    case BytedeskConstants.MESSAGE_TYPE_TYPING:
                    case BytedeskConstants.MESSAGE_TYPE_PROCESSING:
                        // 非自己发送的：正在输入
                        handleTypingMessage(message);
                        return;
                    case BytedeskConstants.MESSAGE_TYPE_PREVIEW:
                        // 非自己发送的：消息预知
                        handlePreviewMessage(message);
                        return;
                    case BytedeskConstants.MESSAGE_TYPE_FAQ_UP:
                    case BytedeskConstants.MESSAGE_TYPE_FAQ_DOWN:
                    case BytedeskConstants.MESSAGE_TYPE_ROBOT_UP:
                    case BytedeskConstants.MESSAGE_TYPE_ROBOT_DOWN:
                    case BytedeskConstants.MESSAGE_TYPE_RATE_SUBMIT:
                    case BytedeskConstants.MESSAGE_TYPE_RATE_CANCEL:
                        // 访客提交评价或取消评价
                        updateMessageStatus(message);
                        return;
                    case BytedeskConstants.MESSAGE_TYPE_STREAM:
                        // handleTypingMessage(currentThread, thread, messageProtobuf.getType());
                        break;
                    case BytedeskConstants.MESSAGE_TYPE_TRANSFER:
                        // 转接
                        Logger.i("transfer message");
                        // handleTransferMessage(message, thread);
                        break;
                    case BytedeskConstants.MESSAGE_TYPE_TRANSFER_ACCEPT:
                        // 转接被接受
                        Logger.i("transfer accept message");
                        // handleTransferAcceptMessage(message, thread);
                        return;
                    case BytedeskConstants.MESSAGE_TYPE_TRANSFER_REJECT:
                        // 转接被拒绝
                        Logger.i("transfer reject message");
                        // handleTransferRejectMessage(message, thread);
                        return;
                    default:
                        // 向服务器发送消息送达回执
//                        var vibrateType = FeedbackType.success;
//                        Vibrate.feedback(vibrateType);
                        //
                        // if (BytedeskUtils.shouldSendReceipt(messageProto.type)) {
                        //   sendReceiptReceivedMessage(messageProto.uid, thread);
                        // }
                }
            } else {
                // 自己发送的消息
                switch (message.getType()) {
                    case BytedeskConstants.MESSAGE_TYPE_READ:
                    case BytedeskConstants.MESSAGE_TYPE_DELIVERED:
                        // 自己发送的消息回执
                        updateMessageStatus(message);
                        return;
                    case BytedeskConstants.MESSAGE_TYPE_TYPING:
                    case BytedeskConstants.MESSAGE_TYPE_PROCESSING:
                        // 自己发送的在输入
                        return;
                    case BytedeskConstants.MESSAGE_TYPE_PREVIEW:
                        return;
                    case BytedeskConstants.MESSAGE_TYPE_TRANSFER:
                        // 转接
                        Logger.i("transfer message");
                        // handleTransferMessage(message, thread);
                        break;
                    case BytedeskConstants.MESSAGE_TYPE_TRANSFER_ACCEPT:
                        // 转接被接受
                        Logger.i("transfer accept message");
                        // handlTransferAcceptMessage(message, thread);
                        return;
                    case BytedeskConstants.MESSAGE_TYPE_TRANSFER_REJECT:
                        // 转接被拒绝
                        Logger.i("transfer reject message");
                        // handleTransferRejectMessage(message, thread);
                        return;
                    default:
                        // 收到从服务器返回自己发的消息，发送成功
//                        updateMessageSuccess(uid);
                }
            }

            mBDRepository.insertMessageEntity(message);
        });
    }

    public void unsubscribe(String topic) {
//        mStompClient.unsubscribe();
//        mStompClient.topic("/topic/" + topic).unsubscribe();
    }

    public void sendMessage(String message) {
        Logger.i("stompSend topic: %s, content: %s", transformedTopic, message);
//        mStompClient.send(topic, message);
        mStompClient.send("/app/" + transformedTopic, message).subscribe();
    }

    public boolean isConnected() {
        return mStompClient.isConnected();
    }

    public void disconnect() {
        mStompClient.disconnect();
    }

    //
    public void updateMessageStatus(MessageEntity message) {
//        String uid = message.content!;
//        String status = message.type!;
//        messageProvider?.update(uid, status);
//        //
//        bytedeskEventBus.fire(ReceiveMessageReceiptEventBus(uid, status));
    }

    public void updateMessageSuccess(String uid) {
//        messageProvider?.update(uid, BytedeskConstants.MESSAGE_STATUS_SUCCESS);
//        //
//        bytedeskEventBus.fire(ReceiveMessageReceiptEventBus(
//                uid, BytedeskConstants.MESSAGE_STATUS_SUCCESS));
    }

    public void handleTypingMessage(MessageEntity message) {
//        bytedeskEventBus.fire(ReceiveMessagePreviewEventBus(message));
    }

    public void handlePreviewMessage(MessageEntity message) {
//        bytedeskEventBus.fire(ReceiveMessagePreviewEventBus(message));
    }

    public void handleTransferMessage() {}

}
