package com.bytedesk.im.core.service;

import android.content.Context;
import android.content.Intent;

import com.bytedesk.im.core.event.ConnectionEvent;
import com.bytedesk.im.core.event.MessageEntityEvent;
import com.bytedesk.im.core.event.PreviewEvent;
import com.bytedesk.im.core.event.ThreadEntityEvent;
import com.bytedesk.im.core.event.TransferAcceptEvent;
import com.bytedesk.im.core.event.TransferEvent;
import com.bytedesk.im.core.event.TransferRejectEvent;
import com.bytedesk.im.core.event.WebRTCEvent;
import com.bytedesk.im.core.repository.BDRepository;
import com.bytedesk.im.core.room.entity.MessageEntity;
import com.bytedesk.im.core.room.entity.ThreadEntity;
import com.bytedesk.im.core.service.mqtt.model.ConnectionModel;
import com.bytedesk.im.core.util.BDCoreConstant;
import com.bytedesk.im.core.util.BDCoreUtils;
import com.bytedesk.im.core.util.BDFileUtils;
import com.bytedesk.im.core.util.BDPreferenceManager;
import com.bytedesk.im.core.util.ExtraParam;
import com.bytedesk.paho.android.service.MqttAndroidClient;
import com.google.protobuf.InvalidProtocolBufferException;
import com.orhanobut.logger.Logger;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.xiaper.protobuf.model.MessageProto;
import io.xiaper.protobuf.model.ThreadProto;
import io.xiaper.protobuf.model.UserProto;

/**
 * 官方文档：
 * http://www.eclipse.org/paho/clients/java/
 * https://www.hivemq.com/blog/mqtt-client-library-encyclopedia-eclipse-paho-java
 *
 * TODO: 支持同时多账号登录多个服务器
 */
public class BDMqttService {
    //
    // FIXME: 有待合理处理此上下文context，否则会造成内存泄露
    private Context mContext;
    private String mClientId;
    //
    private BDRepository mBDRepository;
    private ConnectionModel mConnectionModel;
    private MqttAndroidClient mqttAndroidClient;
    private BDPreferenceManager mPreferenceManager;

    // TODO: 缓存已经订阅主题，防止重复订阅
    private List<String> mTopicSubscribed = new ArrayList<>();

    private static BDMqttService BDMqttService = null;
    private static boolean mConnected = false;
    private static boolean mConnecting = false;

    private BDMqttService(Context context) {
        mContext = context;
        mConnectionModel = ConnectionModel.getInstance(context);
        mPreferenceManager = BDPreferenceManager.getInstance(context);
        mBDRepository = BDRepository.getInstance(context);
    }

    public static BDMqttService getInstance(Context context) {
        if (null == BDMqttService) {
            BDMqttService = new BDMqttService(context);
        }
        return BDMqttService;
    }

    public void connect(String clientId) {

        // 判断是否为空，如果为空，则直接返回
        if (clientId.isEmpty()) {
            return;
        }
//        Logger.i("test cpp:" + NativeLibUtils.stringFromJNI());

        mClientId = clientId;
        mConnectionModel.setClientId(clientId);
        // Logger.i("start connect to mqtt:" + mConnectionModel.getUri() + ", "+ clientId);

        // 避免重复登录
        if (isConnected()) {
            Logger.e("已经连接成功，不需要重复连接");
            return;
        }

        // EventBus广播: 连接中
        EventBus.getDefault().post(new ConnectionEvent(BDCoreConstant.USER_STATUS_CONNECTING));
        //
        mqttAndroidClient = new MqttAndroidClient(mContext, mConnectionModel.getUri(), clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                //
                mConnected = true;
                mPreferenceManager.setConnected(true);

//                if (reconnect) {
//                    Logger.i("Reconnected to : " + serverURI);
//                } else {
//                    Logger.i("Connected to: " + serverURI);
//                }

                // 连接成功
//                setStatus(BDCoreConstant.USER_STATUS_CONNECTED);

                // TODO: 通过安卓自身机制广播状态，不依赖于event bus
                // EventBus广播：连接成功
                EventBus.getDefault().post(new ConnectionEvent(BDCoreConstant.USER_STATUS_CONNECTED));

                // 添加订阅
//                initSubscription();
            }

            @Override
            public void connectionLost(Throwable cause) {
                //
                mConnected = false;
                mPreferenceManager.setConnected(false);
                // EventBus广播：连接断开
                EventBus.getDefault().post(new ConnectionEvent(BDCoreConstant.USER_STATUS_DISCONNECTED));
                //
                if (cause == null) {
                    Logger.w("连接断开 cause null");
                    return;
                }
                // The Connection was lost.Connection lost (32109) - java.io.EOFException
                // 断网的情况连接断开: 已断开连接 (32109) - java.net.SocketException: Software caused connection abort
                Logger.i(cause.toString());
                // 清空已经订阅topic, 方便重连之后重新订阅
                mTopicSubscribed.clear();

                // FIXME: 踢掉线的情况，应该直接禁止用户自动重连，现在是重连之后断开。待优化
//                if (cause.toString().contains("32109")) {
                    Logger.e("网络断开");
                    // FIXME: EventBus广播：被踢掉线, 网络断开也会走此处
//                    EventBus.getDefault().post(new ConnectionEvent(BDCoreConstant.USER_STATUS_KICKOFF));
//                } else {
                    // Timed out waiting for a response from the server (32000)
                    connect(mClientId);
//                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                Logger.i("收到消息, topic: " + topic);
                onMessagesArrived(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

                try {
//                    Logger.i("发送成功 %s", token.getMessage().toString());
                    // 解析 protobuf 消息，测试成功
                    MessageProto.Message messageProto = MessageProto.Message.parseFrom(token.getMessage().getPayload());
//                    Logger.i("deliveryComplete type %s, content %s", messageProto.getType(), messageProto.getText().getContent());
                    //
                    String localId = messageProto.getMid();
                    mBDRepository.updateMessageStatus(localId, BDCoreConstant.MESSAGE_STATUS_STORED);
                    // 解密
//                    Logger.i("decrypt content: %s", AESCipher.aesDecryptString(messageProto.getContent()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {

            Logger.i("开始建立长链接");
            if (mConnecting || mConnected || mPreferenceManager.isConnecting() || mPreferenceManager.isConnected()) {
                // 避免：建立长连接失败: wss://www.bytedesk.com/websocket exception:已在进行连接 (32110)
                return;
            }

            mqttAndroidClient.connect(mConnectionModel.getMqttConnectOptions(), null, new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Logger.i("成功建立长连接");
                    mConnecting = false;
                    mConnected = true;
                    //
                    mPreferenceManager.setConnecting(false);
                    mPreferenceManager.setConnected(true);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // 建立长连接失败: tcp://mq.bytedesk.com:1883 exception:Unable to connect to server (32103) -
                    // java.net.ConnectException: failed to connect to mq.bytedesk.com/47.106.239.170 (port 1883)
                    // from /192.168.232.2 (port 37198) after 80000ms: isConnected failed: ENETUNREACH (Network is unreachable)
                    mConnecting = false;
                    mPreferenceManager.setConnecting(false);
                    // FIXME：大量此条错误，导致app无响应
                    // FIXME: 建立长连接失败: wss://www.bytedesk.com/websocket exception:已在进行连接 (32110)
                    Logger.i("建立长连接失败: " + mConnectionModel.getUri() + " exception:" + exception.toString());
                    mConnected = false;
                    mPreferenceManager.setConnected(false);
                    if (exception.toString().contains("32110")) {
                        mConnecting = true;
                        mPreferenceManager.setConnecting(true);
                    }
                    // 建立长连接失败: wss://android.bytedesk.com/websocket exception:已连接客户机 (32100)
                    if (exception.toString().contains("32100")) {
                        mConnected = true;
                        mPreferenceManager.setConnected(true);
                    }
                    // 重连
                    // FIXME: 网络断开的情况，反复重连，有导致界面卡顿的情况
//                    connect(mClientId);
                }
            });

        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }

    public void sendMessageProtobuf(String mid, String type, String content,
//                                    ThreadEntity threadEntity,
                                    String tId, String topic, String threadType, String threadNickname, String threadAvatar, String client,
                                    ExtraParam extraParam) {
        //
        if (!isConnected() || mPreferenceManager.getUsername().trim().length() == 0) {
            Logger.e("请首先登录");
            return;
        }
        Logger.i("sendMessageProtobuf mid = %s, type = %s, content = %s", mid, type, content);
//        下面为protobuf相关代码，测试成功
        try {
            // 加密
//            String encryptString = AESCipher.aesEncryptString(content);
            //
            ThreadProto.Thread threadProto = ThreadProto.Thread.newBuilder()
                    .setTid(tId)
                    .setType(threadType)
                    .setTopic(topic)
                    .setNickname(threadNickname)
                    .setAvatar(threadAvatar)
                    .setClient(client)
                    .setTimestamp(BDCoreUtils.currentDate())
                    .setUnreadCount(0)
                    .build();
            //
            JSONObject userExtraObject = new JSONObject();
            userExtraObject.put("agent", !mPreferenceManager.loginAsVisitor());
            //
            UserProto.User userProto = UserProto.User.newBuilder()
                    .setUid(mPreferenceManager.getUid())
                    .setUsername(mPreferenceManager.getUsername())
                    .setNickname(mPreferenceManager.getNickname())
                    .setAvatar(mPreferenceManager.getAvatar())
                    .setExtra(userExtraObject.toString())
                    .build();
            //
            MessageProto.Message messageProto = MessageProto.Message.newBuilder()
                    .setMid(mid)
                    .setType(type)
                    .setTimestamp(BDCoreUtils.currentDate())
                    .setClient(BDCoreConstant.CLIENT_ANDROID)
                    .setVersion("1")
                    .setEncrypted(false)
                    .build();

            if (type.equals(BDCoreConstant.MESSAGE_TYPE_TEXT)) {
                //
                threadProto = threadProto.toBuilder()
                        .setContent(content)
                        .build();
                //
                MessageProto.Text textProto = MessageProto.Text.newBuilder()
                        .setContent(content)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setText(textProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_IMAGE)) {
                //
                threadProto = threadProto.toBuilder()
                        .setContent("[图片]")
                        .build();
                //
                MessageProto.Image imageProto = MessageProto.Image.newBuilder()
                        .setImageUrl(content)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setImage(imageProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_VOICE)) {
                //
                threadProto = threadProto.toBuilder()
                        .setContent("[语音]")
                        .build();
                //
                MessageProto.Voice voiceProto = MessageProto.Voice.newBuilder()
                        .setVoiceUrl(content)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setVoice(voiceProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_FILE)) {
                //
                threadProto = threadProto.toBuilder()
                        .setContent("[文件]")
                        .build();
                //
                MessageProto.File fileProto = MessageProto.File.newBuilder()
                        .setFileUrl(content)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setFile(fileProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_VIDEO)) {
                //
                threadProto = threadProto.toBuilder()
                        .setContent("[视频]")
                        .build();
                //
                MessageProto.Video videoProto = MessageProto.Video.newBuilder()
                        .setVideoOrShortUrl(content)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setVideo(videoProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_COMMODITY)) {
                //
                threadProto = threadProto.toBuilder()
                        .setContent("[商品]")
                        .build();
                //
                MessageProto.Text textProto = MessageProto.Text.newBuilder()
                        .setContent(content)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setText(textProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_PREVIEW)) {
                //
                MessageProto.Preview previewProto = MessageProto.Preview.newBuilder()
                        .setContent(extraParam.getPreviewContent())
                        .build();
                messageProto = messageProto.toBuilder()
                        .setPreview(previewProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECEIPT)) {
                //
                MessageProto.Receipt receiptProto = MessageProto.Receipt.newBuilder()
                        .setMid(extraParam.getReceiptMid())
                        .setStatus(extraParam.getReceiptStatus())
                        .build();
                messageProto = messageProto.toBuilder()
                        .setReceipt(receiptProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECALL)) {
                //
                MessageProto.Recall recallProto = MessageProto.Recall.newBuilder()
                        .setMid(extraParam.getRecallMid())
                        .build();
                messageProto = messageProto.toBuilder()
                        .setRecall(recallProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_INVITE_RATE)) {
                Logger.i("发送表单");
                //
                threadProto = threadProto.toBuilder()
                        .setContent("[发送表单]")
                        .build();
                //
                MessageProto.Extra extraProto = MessageProto.Extra.newBuilder()
                        .setContent(content)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setExtra(extraProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_TRANSFER)) {
                Logger.i("会话转接");
                //
                MessageProto.Transfer transferProto = MessageProto.Transfer.newBuilder()
                        .setTopic(extraParam.getTransferTopic())
                        .setContent(extraParam.getInviteContent())
                        .build();
                messageProto = messageProto.toBuilder()
                        .setTransfer(transferProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_TRANSFER_ACCEPT)) {
                Logger.i("接受会话转接");
                //
                MessageProto.Transfer transferProto = MessageProto.Transfer.newBuilder()
                        .setTopic(extraParam.getTransferTopic())
                        .setAccept(true)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setTransfer(transferProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_TRANSFER_REJECT)) {
                Logger.i("拒绝会话转接");
                //
                MessageProto.Transfer transferProto = MessageProto.Transfer.newBuilder()
                        .setTopic(extraParam.getTransferTopic())
                        .setAccept(false)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setTransfer(transferProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_INVITE)) {
                // TODO
                Logger.i("会话邀请");
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_INVITE_ACCEPT)) {
                // TODO
                Logger.i("接受会话邀请");
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_INVITE_REJECT)) {
                // TODO
                Logger.i("拒绝会话邀请");
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_INVITE_RATE)) {
                Logger.i("邀请评价");
                MessageProto.Extra extraProto = MessageProto.Extra.newBuilder()
                        .setContent(content)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setExtra(extraProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RATE_RESULT)) {
                // TODO
                Logger.i("评价结果");
                return;
            } else if (type.startsWith("notification_webrtc")) {
                //
                if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_OFFER_VIDEO)) {
                    //
                    threadProto = threadProto.toBuilder()
                            .setContent("[邀请视频]")
                            .build();
                } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_OFFER_AUDIO)) {
                    //
                    threadProto = threadProto.toBuilder()
                            .setContent("[邀请音频]")
                            .build();
                } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_ANSWER) ||
                        type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_CANDIDATE)) {
                    // 不设置
                } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_ACCEPT)) {
                    threadProto = threadProto.toBuilder()
                            .setContent("[接受邀请]")
                            .build();
                } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_REJECT)) {
                    threadProto = threadProto.toBuilder()
                            .setContent("[拒绝邀请]")
                            .build();
                } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_READY)) {
                    threadProto = threadProto.toBuilder()
                            .setContent("[音视频就绪]")
                            .build();
                } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_BUSY)) {
                    threadProto = threadProto.toBuilder()
                            .setContent("[忙线中]")
                            .build();
                } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_CLOSE)) {
                    threadProto = threadProto.toBuilder()
                            .setContent("[结束音视频]")
                            .build();
                } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_CANCEL)) {
                    threadProto = threadProto.toBuilder()
                            .setContent("[取消音视频]")
                            .build();
                }
                //
                MessageProto.Extra extraProto = MessageProto.Extra.newBuilder()
                        .setContent(content)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setExtra(extraProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();

            } else {
                // TODO: 其他类型
                MessageProto.Text textProto = MessageProto.Text.newBuilder()
                        .setContent(content)
                        .build();
                messageProto = messageProto.toBuilder()
                        .setText(textProto)
                        .setUser(userProto)
                        .setThread(threadProto)
                        .build();
            }

            // 发送
            MqttMessage message = new MqttMessage();
            message.setRetained(false);
            message.setQos(1);
            message.setPayload(messageProto.toByteArray());
            mqttAndroidClient.publish(topic, message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理、路由收到的消息
     *
     * @param topic
     * @param message
     */
    private void onMessagesArrived(String topic, MqttMessage message) {
        // TODO: 后台收到消息，在通知栏提示+声音提示，点击通知栏进入相应会话页面
        // 判断当前消息是否为当前用户所发
        boolean isSelfSend = false;
        boolean isNotificationClose = false;
        boolean sendReceipt = false;
        try {
            //
            MessageProto.Message messageProto = MessageProto.Message.parseFrom(message.getPayload());
            //
            Logger.i("received message mid %s, type %s, content %s, client %s",
                    messageProto.getMid(), messageProto.getType(),
                    messageProto.getText().getContent(), messageProto.getClient());
            //
            if (messageProto.getUser().getUid().equals(mPreferenceManager.getUid())) {
                // 当前用户发送的消息
                isSelfSend = true;
            }

            ThreadEntity threadEntity = new ThreadEntity();
//            threadEntity.setProcessing(true);
            threadEntity.setUid(messageProto.getThread().getTid());
            threadEntity.setType(messageProto.getThread().getType());
//            threadEntity.setNickname(messageProto.getThread().getNickname());
//            threadEntity.setAvatar(messageProto.getThread().getAvatar());
//            threadEntity.setContent(messageProto.getThread().getContent());
//            threadEntity.setTimestamp(messageProto.getThread().getTimestamp());
            threadEntity.setTopic(messageProto.getThread().getTopic());
//            threadEntity.setClient(messageProto.getThread().getClient());
//            threadEntity.setCurrentUid(mPreferenceManager.getUid());

            MessageEntity messageEntity = new MessageEntity();
//            messageEntity.setMid(messageProto.getMid());
            messageEntity.setLocalId(messageProto.getMid());
            // topic格式为: 工作组wid/访客uid
            String[] array = threadEntity.getTopic().split("/");
//            if (array.length > 0) {
//                messageEntity.setWid(array[0]);
//                messageEntity.setVisitorUid(array[1]);
//            }
            messageEntity.setCreatedAt(messageProto.getTimestamp());
            messageEntity.setType(messageProto.getType());
            //
//            messageEntity.setCid(messageProto.getThread().getTid());
//            messageEntity.setGid(messageProto.getThread().getTid());
//            messageEntity.setThreadTid(messageProto.getThread().getTid());
            //
            messageEntity.setUid(messageProto.getUser().getUid());
//            messageEntity.setNickname(messageProto.getUser().getNickname());
//            messageEntity.setAvatar(messageProto.getUser().getAvatar());
            messageEntity.setCurrentUid(mPreferenceManager.getUid());
            messageEntity.setClient(messageProto.getClient());
            messageEntity.setStatus(BDCoreConstant.MESSAGE_STATUS_STORED);
            //
            String type = messageProto.getType();

            if (type.equals(BDCoreConstant.MESSAGE_TYPE_TEXT)) {
                sendReceipt = true;
                messageEntity.setContent(messageProto.getText().getContent());
                //
                // 广播收到的消息
                EventBus.getDefault().post(new MessageEntityEvent(messageEntity));
                EventBus.getDefault().post(new ThreadEntityEvent(threadEntity));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_IMAGE)) {
                sendReceipt = true;
                messageEntity.setContent(messageProto.getImage().getImageUrl());
                //
                // 广播收到的消息
                EventBus.getDefault().post(new MessageEntityEvent(messageEntity));
                EventBus.getDefault().post(new ThreadEntityEvent(threadEntity));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_VOICE)) {
                sendReceipt = true;
                messageEntity.setContent(messageProto.getVoice().getVoiceUrl());
                //
                // 广播收到的消息
                EventBus.getDefault().post(new MessageEntityEvent(messageEntity));
                EventBus.getDefault().post(new ThreadEntityEvent(threadEntity));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_FILE)) {
                sendReceipt = true;
                messageEntity.setContent(messageProto.getFile().getFileUrl());
                //
                // 广播收到的消息
                EventBus.getDefault().post(new MessageEntityEvent(messageEntity));
                EventBus.getDefault().post(new ThreadEntityEvent(threadEntity));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_VIDEO) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_SHORTVIDEO)) {
                sendReceipt = true;
//                messageEntity.setContent(messageProto.getVideo().getVideoOrShortUrl());
                //
                // 广播收到的消息
                EventBus.getDefault().post(new MessageEntityEvent(messageEntity));
                EventBus.getDefault().post(new ThreadEntityEvent(threadEntity));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_PREVIEW)) {
                // 消息预知
                Logger.i("消息预知");
                String uid = messageProto.getUser().getUid();
                String content = messageProto.getPreview().getContent();
                if (!uid.equals(mPreferenceManager.getUid())) {
                    // TODO: tid 区分会话
                    EventBus.getDefault().post(new PreviewEvent(content));
                }
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECEIPT)) {
                // 消息送达/已读
                String mid = messageProto.getReceipt().getMid();
                String status = messageProto.getReceipt().getStatus();
                Logger.i("消息送达/已读 %s %s", mid, status);
                mBDRepository.updateMessageStatus(mid, status);
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECALL)) {
                // 消息撤回
                Logger.i("消息撤回");
                String mid = messageProto.getRecall().getMid();
                mBDRepository.deleteMessage(mid);
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_TRANSFER)) {
                Logger.i("会话转接");
                // 过滤掉自己发送的消息
                if (mPreferenceManager.getUid().equals(messageProto.getUser().getUid())) {
                    return;
                }
                EventBus.getDefault().post(new TransferEvent(messageEntity));

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_TRANSFER_ACCEPT)) {
                Logger.i("接受会话转接");
                // 过滤掉自己发送的消息
                if (mPreferenceManager.getUid().equals(messageProto.getUser().getUid())) {
                    return;
                }
                EventBus.getDefault().post(new TransferAcceptEvent(messageEntity));

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_TRANSFER_REJECT)) {
                Logger.i("拒绝会话转接");
                // 过滤掉自己发送的消息
                if (mPreferenceManager.getUid().equals(messageProto.getUser().getUid())) {
                    return;
                }
                EventBus.getDefault().post(new TransferRejectEvent(messageEntity));

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_INVITE)) {
                // TODO
                Logger.i("会话邀请");
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_INVITE_ACCEPT)) {
                // TODO
                Logger.i("接受会话邀请");
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_INVITE_REJECT)) {
                // TODO
                Logger.i("拒绝会话邀请");
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_INVITE_VIDEO)) {

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_INVITE_AUDIO)) {

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_OFFER_VIDEO) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_OFFER_AUDIO) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_ANSWER) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_CANDIDATE) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_ACCEPT) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_REJECT) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_READY) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_BUSY) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_CLOSE) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_WEBRTC_CANCEL)) {
                // 过滤掉自己发送的消息
                if (isSelfSend) {
                    return;
                }
                Logger.i("received message type %s, extra content %s", messageProto.getType(), messageProto.getExtra().getContent());
                if (messageProto.getExtra().getContent().trim().length() > 0) {
                    messageEntity.setContent(messageProto.getExtra().getContent());
                    EventBus.getDefault().post(new WebRTCEvent(messageEntity));
                } else {
                    Logger.e("type %s extra content is null", messageProto.getType());
                }
                return;
            } else {
                // TODO: 其他消息类型
                messageEntity.setContent(messageProto.getText().getContent());
            }

            // 发送消息回执：送达
            if (sendReceipt && !isSelfSend) {
                ExtraParam extraParam = new ExtraParam();
                extraParam.setReceiptMid(messageProto.getMid());
                extraParam.setReceiptStatus(BDCoreConstant.MESSAGE_STATUS_RECEIVED);
                //
//                sendMessageProtobuf(BDCoreUtils.uuid(), BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECEIPT, "content",
//                        threadEntity.getUid(), threadEntity.getTopic(), threadEntity.getType(), threadEntity.getNickname(), threadEntity.getAvatar(), threadEntity.getClient(),
//                        extraParam);
            }
            //
            if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_AGENT_CLOSE) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_VISITOR_CLOSE) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_AUTO_CLOSE)) {
                isNotificationClose = true;
            }
            int unreadCount = messageProto.getThread().getUnreadCount();
            if (!mPreferenceManager.getCurrentThreadTid().equals(messageProto.getThread().getTid()) && !isSelfSend && !isNotificationClose) {
                // TODO: 非实际计数，仅用于提示未读数目，后期待完善，显示真实未读数
                unreadCount = 1;
            }
//            threadEntity.setUnreadCount(unreadCount);

            if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_THREAD) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_THREAD_REENTRY) ||

                    type.equals(BDCoreConstant.MESSAGE_TYPE_TEXT) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_IMAGE) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_VOICE) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_FILE) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_VIDEO) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_SHORTVIDEO) ||

                    type.equals(BDCoreConstant.MESSAGE_TYPE_LOCATION) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_LINK) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_EVENT) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_CUSTOM) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_RED_PACKET) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_COMMODITY) ||

                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_AGENT_CLOSE) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_VISITOR_CLOSE) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_AUTO_CLOSE)
            ) {
                // 持久化到本地数据库
                Logger.i("insert protobuf message type: %s content: %s", type, messageEntity.getContent());
                mBDRepository.insertThreadEntity(threadEntity);
                mBDRepository.insertMessageEntity(messageEntity);
            } else {
                Logger.i("其他类型消息 %s", type);
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        // 非自己发送的消息 并且 非会话关闭通知，才会 震动或提示音，只有需要发送回执的才提示
        if (!isSelfSend && !isNotificationClose && sendReceipt) {
            // 收到消息后震动
            if (mPreferenceManager.shouldVibrateWhenReceiveMessage()) {
                BDCoreUtils.doVibrate(mContext);
            }
            // 播放收到消息提示音
            if (mPreferenceManager.shouldRingWhenReceiveMessage()) {
                mPreferenceManager.playReceiveMessage();
            }
        }
    }

    /**
     * 退出登录
     *
     * FIXME: MqttService has leaked IntentReceiver com.bytedesk.paho.android.service.AlarmPingSender$AlarmReceiver@a6aa11c
     */
    public void disconnect() {

        if (!isConnected()) {
            Logger.e("请首先登录");
            return;
        }

        // 清空已经订阅topic
        mTopicSubscribed.clear();
        // 设置状态
//        setStatus(BDCoreConstant.USER_STATUS_DISCONNECTED);
        // EventBus广播
        EventBus.getDefault().post(new ConnectionEvent(BDCoreConstant.USER_STATUS_DISCONNECTED));

        // 断开连接
        mConnected = false;
        mPreferenceManager.setConnected(false);

        if (mqttAndroidClient != null) {
            mqttAndroidClient.unregisterResources();

            try {
                mqttAndroidClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }

            // FIXME: java.lang.IllegalArgumentException: Invalid ClientHandle
            // mqttAndroidClient.close();
            mqttAndroidClient = null;
        }

        mClientId = "";
    }

    /**
     * mqttAndroidClient.getBufferedMessageCount()
     *
     * FIXME: java.lang.IllegalArgumentException: Invalid ClientHandle
     * com.bytedesk.paho.android.service.MqttService.getConnection(MqttService.java:588)
     *
     * @return
     */
    public boolean isConnected() {

        return mConnected;

        //        return mPreferenceManager.isConnected();

//        FIXME:
//        if (null != mqttAndroidClient) {
//            return mqttAndroidClient.isConnected();
//        }
//        return false;
    }

    private void startDownloadVoice(String voiceUrl) {

        String filePath = BDFileUtils.getVoiceFilePathFromUrl(voiceUrl);

        Intent itDownloader = new Intent(mContext, BDDownloadService.class);
        itDownloader.putExtra("flag", "start");
        itDownloader.putExtra("fileSavePath", filePath);
        itDownloader.putExtra("fileurl", voiceUrl);
        mContext.startService(itDownloader);
    }

    private void startDownloadFile(String fileUrl) {

        String filePath = BDFileUtils.getFilePathFromUrl(fileUrl);

        Intent itDownloader = new Intent(mContext, BDDownloadService.class);
        itDownloader.putExtra("flag", "start");
        itDownloader.putExtra("fileSavePath", filePath);
        itDownloader.putExtra("fileurl", fileUrl);
        mContext.startService(itDownloader);
    }

    /**
     * 订阅消息有2个要素：
     * 1. topic
     * 2. QoS
     *
     * @param topic
     */
    public void subscribeToTopic(final String topic) {

        // 未登录
        if (!isConnected()) {
            Logger.e("请首先登录");
            return;
        }

        if (mTopicSubscribed.contains(topic)) {
            Logger.i("不能重复订阅: " + topic);
            return;
        }

        try {

            mqttAndroidClient.subscribe(topic, 0, null, new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
//                    Logger.i(topic + " Subscribed");

                    mTopicSubscribed.add(topic);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Logger.i("Failed to subscribe " + topic);
                }
            });

        } catch (MqttException ex){
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    /**
     * 取消订阅
     *
     * @param topic
     */
    public void unSubscribeToTopic(final String topic) {

        // 未登录
        if (!isConnected()) {
            Logger.e("请首先登录");
            return;
        }

        try {
            mqttAndroidClient.unsubscribe(topic, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Logger.i(topic + " unSubscribed");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Logger.i("Failed to unsubscribe " +  topic);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}
