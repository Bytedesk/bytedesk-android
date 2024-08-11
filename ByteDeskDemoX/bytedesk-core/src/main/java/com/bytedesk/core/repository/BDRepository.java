package com.bytedesk.core.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bytedesk.core.room.dao.ContactDao;
import com.bytedesk.core.room.dao.FriendDao;
import com.bytedesk.core.room.dao.GroupDao;
import com.bytedesk.core.room.dao.MessageDao;
import com.bytedesk.core.room.dao.NoticeDao;
import com.bytedesk.core.room.dao.QueueDao;
import com.bytedesk.core.room.dao.ThreadDao;
import com.bytedesk.core.room.dao.WorkGroupDao;
import com.bytedesk.core.room.database.AppDatabase;
import com.bytedesk.core.room.entity.ContactEntity;
import com.bytedesk.core.room.entity.FriendEntity;
import com.bytedesk.core.room.entity.GroupEntity;
import com.bytedesk.core.room.entity.MessageEntity;
import com.bytedesk.core.room.entity.NoticeEntity;
import com.bytedesk.core.room.entity.QueueEntity;
import com.bytedesk.core.room.entity.ThreadEntity;
import com.bytedesk.core.room.entity.WorkGroupEntity;
import com.bytedesk.core.util.BDCoreConstant;
import com.bytedesk.core.util.BDCoreUtils;
import com.bytedesk.core.util.BDPreferenceManager;
import com.github.promeg.pinyinhelper.Pinyin;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html?index=..%2F..%2Findex#7
 */
public class BDRepository {

    private static BDRepository sInstance;

    private MessageDao mMessageDao;
    private ThreadDao mThreadDao;
    private QueueDao mQueueDao;
    private ContactDao mContactDao;
    private WorkGroupDao mWorkGroupDao;
    private GroupDao mGroupDao;
    private NoticeDao mNoticeDao;
    private FriendDao mFriendDao;
    private BDPreferenceManager mPreferenceManager;

    //
    public static BDRepository getInstance(Context context) {
        if (null == sInstance) {
            sInstance = new BDRepository(context);
        }
        return sInstance;
    }

    //
    private BDRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);

        mMessageDao = database.messageDao();
        mThreadDao = database.threadDao();
        mQueueDao = database.queueDao();
        mContactDao = database.contactDao();
        mWorkGroupDao = database.workGroupDao();
        mGroupDao = database.groupDao();
        mNoticeDao = database.noticeDao();
        mFriendDao = database.friendDao();

        mPreferenceManager = BDPreferenceManager.getInstance(context);
    }

    public LiveData<List<MessageEntity>> getWorkGroupMessages(String wId) {
        return mMessageDao.loadWorkGroupMessages(mPreferenceManager.getUid(), wId);
    }

    public LiveData<List<MessageEntity>> getThreadMessages(String tId) {
        return mMessageDao.loadThreadMessages(mPreferenceManager.getUid(), tId);
    }

    public LiveData<List<MessageEntity>> getVisitorMessages(String uId) {
        return mMessageDao.loadVisitorMessages(mPreferenceManager.getUid(), uId);
    }

    public LiveData<List<MessageEntity>> getContactMessages(String cId) {
        return mMessageDao.loadContactMessages(mPreferenceManager.getUid(), cId);
    }

    public LiveData<List<MessageEntity>> getGroupMessages(String gId) {
        return mMessageDao.loadGroupMessages(mPreferenceManager.getUid(), gId);
    }

    public LiveData<List<ThreadEntity>> getProcessingThreads() {
        return mThreadDao.loadProcessingThreads(true, mPreferenceManager.getUid());
    }

    public LiveData<List<ThreadEntity>> getThreads() {
        return mThreadDao.loadThreads(mPreferenceManager.getUid());
    }

    public LiveData<List<ThreadEntity>> getIMThreads() {
        return mThreadDao.loadIMThreads(mPreferenceManager.getUid());
    }

    public LiveData<List<ThreadEntity>> getThreadsWithType(String type) {
        return mThreadDao.loadThreadsWithType(mPreferenceManager.getUid(), type);
    }

    public List<ThreadEntity> getProcessingThreads2() {
        try {
            return new getProcessingThreadsAsyncTask(mThreadDao).execute(mPreferenceManager.getUid()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<ThreadEntity> getThreadListWithType(String type) {
        try {
            return new getThreadListWithTypeAsyncTask(mThreadDao).execute(mPreferenceManager.getUid(), type).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ThreadEntity getThread(String tid) {
        try {
            return new getThreadAsyncTask(mThreadDao).execute(tid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ThreadEntity();
    }

    public LiveData<List<WorkGroupEntity>> getWorkGroups() {
        return mWorkGroupDao.loadWorkGroups(mPreferenceManager.getUid());
    }

    public LiveData<List<NoticeEntity>> getNotices() {
        return mNoticeDao.loadNotices(mPreferenceManager.getUid());
    }

    public LiveData<List<FriendEntity>> getFriends() {
        return mFriendDao.loadFriends(mPreferenceManager.getUid());
    }

    public LiveData<List<ThreadEntity>> searchThreads(String search) {
        return mThreadDao.searchThreads("%" + search + "%", mPreferenceManager.getUid());
    }

    public LiveData<List<QueueEntity>> getQueues() {
        return mQueueDao.loadQueues(mPreferenceManager.getUid());
    }

    public LiveData<List<QueueEntity>> searchQueues(String search) {
        return mQueueDao.searchQueues("%" + search + "%", mPreferenceManager.getUid());
    }

    public LiveData<List<ContactEntity>> getContacts() {
        return mContactDao.loadContacts(mPreferenceManager.getUid());
    }

    public LiveData<List<ContactEntity>> searchContacts(String search) {
        return mContactDao.searchContacts("%" + search + "%", mPreferenceManager.getUid());
    }

    public LiveData<List<FriendEntity>> searchFriends(String search) {
        return mFriendDao.searchFriends("%" + search + "%", mPreferenceManager.getUid());
    }

    public List<WorkGroupEntity> getWorkGroupList() {
        try {
            return new getWorkGroupListAsyncTask(mWorkGroupDao).execute(mPreferenceManager.getUid()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public LiveData<List<GroupEntity>> getGroups() {
        return mGroupDao.loadGroups(mPreferenceManager.getUid());
    }

    public List<GroupEntity> getGroupList() {
        try {
            return new getGroupListAsyncTask(mGroupDao).execute(mPreferenceManager.getUid()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public LiveData<List<GroupEntity>> searchGroups(String search) {
        return mGroupDao.searchGroups("%" + search + "%", mPreferenceManager.getUid());
    }

    public LiveData<List<NoticeEntity>> searchNotices(String search) {
        return mNoticeDao.searchNotices("%" + search + "%", mPreferenceManager.getUid());
    }

    public void insertNotificationMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_NOTIFICATION, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, 0, "", "", "");
    }

    public void insertTextMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_TEXT, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, 0, "", "", "");
    }

    public void insertTextMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType, String status) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_TEXT, sessionType, status, 0, "", "", "");
    }

    public void insertImageMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_IMAGE, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, 0, "", "", "");
    }

    public void insertFileMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType, String format, String fileName, String fileSize) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_FILE, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, 0, format, fileName, fileSize);
    }

    public void insertVoiceMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType, int length) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_VOICE, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, length, "amr", "", "");
    }

    public void insertVideoMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_VIDEO, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, 0, "", "", "");
    }

    public void insertCustomMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_CUSTOM, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, 0, "", "", "");
    }

    public void insertCommodityMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_COMMODITY, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, 0, "", "", "");
    }

    public void insertRedPacketMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_RED_PACKET, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, 0, "", "", "");
    }

    public void insertInviteRateMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_INVITE_RATE, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, 0, "", "", "");
    }

    public void insertFormRequestMessageLocal(String tid, String wid, String uid, String content, String localId, String sessionType) {
        insertMessageLocal(tid, wid, uid, content, localId, BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_FORM_REQUEST, sessionType, BDCoreConstant.MESSAGE_STATUS_SENDING, 0, "", "", "");
    }

    /**
     * 插入本地发送消息
     * TODO: 添加发送消息频率限制，每秒钟1条
     * TODO: 限制消息体content的长度最大512
     */
    public void insertMessageLocal(String tid, String wid, String uid, String content, String localId, String type, String sessionType, String status,
                                   int length, String format, String fileName, String fileSize) {

        Logger.i("insert local content: %s localId: %s", content, localId);

        MessageEntity messageEntity = new MessageEntity();
//        messageEntity.setId(System.currentTimeMillis()/1000);
//        if (sessionType.equals(BDCoreConstant.THREAD_TYPE_WORKGROUP)) {
//            messageEntity.setWid(wid);
//            messageEntity.setThreadTid(tid);
//            messageEntity.setVisitorUid(uid);
//        } else

        if (sessionType.equals(BDCoreConstant.THREAD_TYPE_CONTACT)) {
            messageEntity.setCid(tid);
        } else if (sessionType.equals(BDCoreConstant.THREAD_TYPE_GROUP)) {
            messageEntity.setGid(tid);
        } else {
            messageEntity.setWid(wid);
            messageEntity.setThreadTid(tid);
            messageEntity.setVisitorUid(uid);
        }
        messageEntity.setMid(localId);
        messageEntity.setLocalId(localId);
        messageEntity.setType(type);

        // 统一使用android
//        String client = BDCoreConstant.CLIENT_ANDROID;
//        if (mPreferenceManager.loginAsVisitor()) {
//            client = BDCoreConstant.CLIENT_ANDROID;
//        } else {
//            // 一对一会话和群组会话
//            client = BDCoreConstant.CLIENT_ANDROID_ADMIN;
//        }
        messageEntity.setClient(BDCoreConstant.CLIENT_ANDROID);

        if (type.equals(BDCoreConstant.MESSAGE_TYPE_TEXT)) {
            messageEntity.setContent(content);
        } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_IMAGE)) {
            messageEntity.setImageUrl(content);
        } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_VOICE)) {
            messageEntity.setFormat(format);
            messageEntity.setVoiceUrl(content);
            messageEntity.setLength(length);
            // 自己发送的，默认设置为true
            messageEntity.setPlayed(true);
        } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_FILE)) {
            messageEntity.setFormat(format);
            messageEntity.setFileUrl(content);
            messageEntity.setFileName(fileName);
            messageEntity.setFileSize(fileSize);
        } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_COMMODITY)) {
            messageEntity.setContent(content);
        } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_RED_PACKET)) {
            messageEntity.setContent(content);
        } else {
            messageEntity.setContent(content);
        }
        messageEntity.setStatus(status);
        messageEntity.setSessionType(sessionType);
        messageEntity.setCreatedAt(BDCoreUtils.currentDate());
        //
        messageEntity.setUid(mPreferenceManager.getUid());
        messageEntity.setUsername(mPreferenceManager.getUsername());
        messageEntity.setNickname(mPreferenceManager.getNickname());
        messageEntity.setAvatar(mPreferenceManager.getAvatar());
        messageEntity.setCurrentUid(mPreferenceManager.getUid());
        //
        messageEntity.setVisitor(mPreferenceManager.isVisitor());

        new insertMessageAsyncTask(mMessageDao).execute(messageEntity);
    }

    public void insertMessageJson(JSONObject message) {
        Logger.d("insert message：%s", message.toString());
        //
        MessageEntity messageEntity = new MessageEntity();
        try {

            String type = message.getString("type");
            messageEntity.setType(type);
            //
            if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECEIPT)) {
                Logger.i("消息回执");
                return;
            }
            //
            if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECALL)) {
                Logger.i("消息撤回");
                return;
            }
            // TODO: 消息回执和消息预知 没有id字段, 暂不处理此类型，后续处理
            if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_PREVIEW) ||
                // TODO: 对于访客端暂时忽略连接状态信息
                type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_CONNECT) ||
                type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_DISCONNECT) ||
                // 浏览相关类型暂不处理
                type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_BROWSE_START) ||
                type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_BROWSE_END) ||
                // 账号已经在其他客户端登录
                type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_KICKOFF) ||
                type.equals(BDCoreConstant.MESSAGE_TYPE_QUESTIONNAIRE) ||
                type.equals(BDCoreConstant.MESSAGE_TYPE_WORK_GROUP)) {
                return;
            }

            // 更新本地发送消息
//            Long id = message.getLong("id");
            String mid = message.getString("mid");
            //
            if (!message.isNull("status")) {
                String status = message.getString("status");
                messageEntity.setStatus(status);
            }
//            messageEntity.setId(id);
//            messageEntity.setId(System.currentTimeMillis()/1000);
            messageEntity.setMid(mid);
            //
            String threadType = message.getJSONObject("thread").getString("type"); // message.getString("sessionType");
            messageEntity.setSessionType(threadType);

            if (threadType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_ROBOT) ||
                    threadType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_WORKGROUP) ||
                    threadType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_APPOINTED)) {
                messageEntity.setWid(message.getString("wid"));
                //
                String tid = message.getJSONObject("thread").getString("tid");
                String visitorUid = tid.split("_")[1];
                messageEntity.setThreadTid(tid);
                // message.getJSONObject("thread").getJSONObject("visitor").getString("uid")
                messageEntity.setVisitorUid(visitorUid);
            } else if (threadType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_GROUP)) {
//                Logger.i("gid %s", message.getString("gid"));
                messageEntity.setGid(message.getString("gid"));
            }
            //
            messageEntity.setClient(message.getString("client"));
//            messageEntity.setDestroyAfterReading(message.getBoolean("destroyAfterReading"));
//            messageEntity.setDestroyAfterLength(message.getInt("destroyAfterLength"));
            if (type.equals(BDCoreConstant.MESSAGE_TYPE_TEXT)) {
                messageEntity.setContent(message.getString("content"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_ROBOT)) {
                //
                String content = message.getString("content");
                // 附属问题
                // FIXME: 查询出的历史聊天记录里面没有 附属问题？
                if (!message.isNull("answers")) {
                    JSONArray jsonArray = message.getJSONArray("answers");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject answerObject = jsonArray.getJSONObject(i);
                        String aid = answerObject.getString("aid");
                        String question = answerObject.getString("question");
                        content += "\n\n" + aid + ":" + question;
                    }
                }
                messageEntity.setContent(content);

                // TODO: 添加 有帮助 和 无帮助 评价按钮

                // TODO: 添加 ‘人工客服’ 点击按钮

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_IMAGE)) {
                // messageEntity.setPicUrl(message.getString("picUrl"));
                messageEntity.setImageUrl(message.getString("imageUrl"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_VOICE)) {
                messageEntity.setMediaId(message.getString("mediaId"));
                messageEntity.setFormat(message.getString("format"));
                messageEntity.setVoiceUrl(message.getString("voiceUrl"));
                messageEntity.setLength(message.getInt("length"));
                messageEntity.setPlayed(message.getBoolean("played"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_FILE)) {
                messageEntity.setFormat(message.getString("format"));
                messageEntity.setFileUrl(message.getString("fileUrl"));
                messageEntity.setFileName(message.getString("fileName"));
                messageEntity.setFileSize(message.getString("fileSize"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_QUESTIONNAIRE)) {
                // TODO: 用弹窗的方式替代，暂不存储
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_WORK_GROUP)) {
                // TODO: 用弹窗的方式替代，暂不存储
                return;
            } else if (type.startsWith(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION)) {
                messageEntity.setContent(message.getString("content"));
                if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_AGENT_CLOSE) ||
                        type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_AUTO_CLOSE)) {
                    // TODO: 会话自动关闭 或者 客服关闭会话，添加 ‘联系客服’ 按钮
                }
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_SHORTVIDEO)
                    || type.equals(BDCoreConstant.MESSAGE_TYPE_VIDEO)) {

                messageEntity.setThumbMediaId(message.getString("thumbMediaId"));
                messageEntity.setVideoOrShortUrl(message.getString("videoOrShortUrl"));
                messageEntity.setVideoOrShortThumbUrl(message.getString("videoOrShortThumbUrl"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_LOCATION)) {

                messageEntity.setLocationX(message.getDouble("locationX"));
                messageEntity.setLocationY(message.getDouble("locationY"));
                messageEntity.setScale(message.getDouble("scale"));
                messageEntity.setLabel(message.getString("label"));

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_COMMODITY)) {

                messageEntity.setContent(message.getString("content"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_RED_PACKET)) {

                messageEntity.setContent(message.getString("content"));
            } else {

                messageEntity.setContent(message.getString("content"));
            }
            messageEntity.setCreatedAt(message.getString("createdAt"));

            if (!message.isNull("user")) {
                String senderUid = message.getJSONObject("user").getString("uid");
                //
                messageEntity.setUid(senderUid);
                messageEntity.setUsername(message.getJSONObject("user").getString("username"));
                messageEntity.setNickname(message.getJSONObject("user").getString("nickname"));
                messageEntity.setAvatar(message.getJSONObject("user").getString("avatar"));
                //
                if (threadType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_WORKGROUP)) {

                    messageEntity.setVisitor(message.getJSONObject("user").getBoolean("visitor"));

                } else if (threadType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_CONTACT)) {
                    //
                    if (senderUid.equals(mPreferenceManager.getUid())) {
                        messageEntity.setCid(message.getString("cid"));
                    } else {
                        messageEntity.setCid(message.getJSONObject("user").getString("uid"));
                    }
                }
            }
            messageEntity.setCurrentUid(mPreferenceManager.getUid());

            // FIXME: 消息发送者 收到服务器推送的自己发送的消息，首先删除处于发送状态的此条本地消息，然后再插入收到的消息，待优化
            if (messageEntity.getUid().equals(messageEntity.getCurrentUid())) {
                //
                if (!message.isNull("localId")) {
                    String localId = message.getString("localId");
                    if (!localId.equals("null") && localId.trim().length() > 0) {
//                    Logger.d("delete localId: " + localId);
//                    messageEntity.setLocalId(localId);
                        new deleteMessageLocalAsyncTask(mMessageDao).execute(localId);
//                    new updateMessageAsyncTask(mMessageDao).execute(localId, String.valueOf(id), mid, status);
//                    new updateMessageAsyncTask(mMessageDao).execute(localId, mid, status);
//                    return;
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        Logger.i("messageEntity %s", messageEntity.toString());
        //
        new insertMessageAsyncTask(mMessageDao).execute(messageEntity);
    }

    // TODO: 重构精简
    public void insertRobotRightAnswerMessageJson(JSONObject message) {
        Logger.d("insertRobotRightAnswerMessageJson：" + message.toString());
        //
        MessageEntity messageEntity = new MessageEntity();
        //
        try {

            String type = message.getString("type");
            messageEntity.setType(type);

            // TODO: 消息回执和消息预知 没有id字段, 暂不处理此类型，后续处理
            if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECEIPT) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_PREVIEW) ||
                    // TODO: 对于访客端暂时忽略连接状态信息
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_CONNECT) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_DISCONNECT) ||
                    // 浏览相关类型暂不处理
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_BROWSE_START) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_BROWSE_END) ||
                    // 账号已经在其他客户端登录
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_KICKOFF) ||
                    //
                    type.equals(BDCoreConstant.MESSAGE_TYPE_QUESTIONNAIRE) ||
                    //
                    type.equals(BDCoreConstant.MESSAGE_TYPE_WORK_GROUP)) {
                return;
            }

            // 更新本地发送消息
            Long id = message.getLong("id");
            String mid = message.getString("mid");
            String status = message.getString("status");
            String localId = message.getString("localId");
            //
//            messageEntity.setId(id);
            messageEntity.setMid(mid);
            messageEntity.setStatus(status);

            String sessionType = message.getString("sessionType");
            messageEntity.setSessionType(sessionType);

            if (sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_WORKGROUP) ||
                    sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_ROBOT)) {
                messageEntity.setWid(message.getString("wid"));
                //
                messageEntity.setThreadTid(message.getJSONObject("thread").getString("tid"));
                messageEntity.setVisitorUid(message.getJSONObject("thread").getJSONObject("visitor").getString("uid"));

            } else if (sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_GROUP)) {
                messageEntity.setGid(message.getString("gid"));
            }

            messageEntity.setClient(message.getString("client"));
//            messageEntity.setDestroyAfterReading(message.getBoolean("destroyAfterReading"));
//            messageEntity.setDestroyAfterLength(message.getInt("destroyAfterLength"));
            if (type.equals(BDCoreConstant.MESSAGE_TYPE_TEXT)) {
                messageEntity.setContent(message.getString("content"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_ROBOT)) {
                String content = message.getString("content");
                // 附属问题
                // FIXME: 查询出的历史聊天记录里面没有 附属问题？
                if (!message.isNull("answers")) {
                    JSONArray jsonArray = message.getJSONArray("answers");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject answerObject = jsonArray.getJSONObject(i);
                        String aid = answerObject.getString("aid");
                        String question = answerObject.getString("question");
                        content += "\n\n" + aid + ":" + question;
                    }
                }
                // TODO: 添加 有帮助 和 无帮助 评价按钮
                if (!message.isNull("answer")) {
//                    "helpfull:aid:mid"
//                    "helpless:aid:mid"
                }
                messageEntity.setContent(content);

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_IMAGE)) {
                // messageEntity.setPicUrl(message.getString("picUrl"));
                messageEntity.setImageUrl(message.getString("imageUrl"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_VOICE)) {
                messageEntity.setMediaId(message.getString("mediaId"));
                messageEntity.setFormat(message.getString("format"));
                messageEntity.setVoiceUrl(message.getString("voiceUrl"));
                messageEntity.setLength(message.getInt("length"));
                messageEntity.setPlayed(message.getBoolean("played"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_FILE)) {
                messageEntity.setFormat(message.getString("format"));
                messageEntity.setFileUrl(message.getString("fileUrl"));
                messageEntity.setFileName(message.getString("fileName"));
                messageEntity.setFileSize(message.getString("fileSize"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_QUESTIONNAIRE)) {
                // TODO: 用弹窗的方式替代，暂不存储
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_WORK_GROUP)) {
                // TODO: 用弹窗的方式替代，暂不存储
                return;
            } else if (type.startsWith(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION)) {
                messageEntity.setContent(message.getString("content"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_SHORTVIDEO)
                    || type.equals(BDCoreConstant.MESSAGE_TYPE_VIDEO)) {

                messageEntity.setThumbMediaId(message.getString("thumbMediaId"));
                messageEntity.setVideoOrShortUrl(message.getString("videoOrShortUrl"));
                messageEntity.setVideoOrShortThumbUrl(message.getString("videoOrShortThumbUrl"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_LOCATION)) {

                messageEntity.setLocationX(message.getDouble("locationX"));
                messageEntity.setLocationY(message.getDouble("locationY"));
                messageEntity.setScale(message.getDouble("scale"));
                messageEntity.setLabel(message.getString("label"));

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_COMMODITY)) {

                messageEntity.setContent(message.getString("content"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_RED_PACKET)) {

                messageEntity.setContent(message.getString("content"));
            } else {

                messageEntity.setContent(message.getString("content"));
            }
            messageEntity.setCreatedAt(message.getString("createdAt"));

            if (!message.isNull("user")) {
                String senderUid = message.getJSONObject("user").getString("uid");

                messageEntity.setUid(senderUid);
                messageEntity.setUsername(message.getJSONObject("user").getString("username"));
                messageEntity.setNickname(message.getJSONObject("user").getString("nickname"));
                messageEntity.setAvatar(message.getJSONObject("user").getString("avatar"));
                //
                if (sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_WORKGROUP)) {

                    messageEntity.setVisitor(message.getJSONObject("user").getBoolean("visitor"));

                } else if (sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_CONTACT)) {
                    //
                    if (senderUid.equals(mPreferenceManager.getUid())) {
                        messageEntity.setCid(message.getString("cid"));
                    } else {
                        messageEntity.setCid(message.getJSONObject("user").getString("uid"));
                    }
                }
            }
            messageEntity.setCurrentUid(mPreferenceManager.getUid());

            // 是否标记删除
//            if (!message.isNull("deletedSet")) {
//                JSONArray jsonArray = message.getJSONArray("deletedSet");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject userObject = jsonArray.getJSONObject(i);
//                    if (!userObject.isNull("uid")) {
//                        String uid = userObject.getString("uid");
//                        if (uid.equals(mPreferenceManager.getUid())) {
//                            messageEntity.setMarkDeleted(true);
//                        }
//                    }
//                }
//            }

            //
            if (messageEntity.getUid().equals(messageEntity.getCurrentUid())) {
                if (!localId.equals("null") && localId.trim().length() > 0) {
                    Logger.d("delete localId: " + localId);
//                    messageEntity.setLocalId(localId);
                    new deleteMessageLocalAsyncTask(mMessageDao).execute(localId);
//                    new updateMessageAsyncTask(mMessageDao).execute(localId, String.valueOf(id), mid, status);
//                    new updateMessageAsyncTask(mMessageDao).execute(localId, mid, status);
//                    return;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        new insertMessageAsyncTask(mMessageDao).execute(messageEntity);
    }

    // TODO: 重构精简
    public void insertRobotNoAnswerMessageJson(JSONObject message) {
        Logger.d("insertRobotNoAnswerMessageJson：" + message.toString());
        //
        MessageEntity messageEntity = new MessageEntity();
        //
        try {

            String type = message.getString("type");
            messageEntity.setType(type);

            // TODO: 消息回执和消息预知 没有id字段, 暂不处理此类型，后续处理
            if (type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_RECEIPT) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_PREVIEW) ||
                    // TODO: 对于访客端暂时忽略连接状态信息
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_CONNECT) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_DISCONNECT) ||
                    // 浏览相关类型暂不处理
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_BROWSE_START) ||
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_BROWSE_END) ||
                    // 账号已经在其他客户端登录
                    type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_KICKOFF) ||
                    //
                    type.equals(BDCoreConstant.MESSAGE_TYPE_QUESTIONNAIRE) ||
                    //
                    type.equals(BDCoreConstant.MESSAGE_TYPE_WORK_GROUP)) {
                return;
            }

            // 更新本地发送消息
            Long id = message.getLong("id");
            String mid = message.getString("mid");
            String status = message.getString("status");
            String localId = message.getString("localId");
            //
//            messageEntity.setId(id);
            messageEntity.setMid(mid);
            messageEntity.setStatus(status);

            String sessionType = message.getString("sessionType");
            messageEntity.setSessionType(sessionType);

            if (sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_WORKGROUP) ||
                    sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_ROBOT)) {
                messageEntity.setWid(message.getString("wid"));
                //
                messageEntity.setThreadTid(message.getJSONObject("thread").getString("tid"));
                messageEntity.setVisitorUid(message.getJSONObject("thread").getJSONObject("visitor").getString("uid"));

            } else if (sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_GROUP)) {
                messageEntity.setGid(message.getString("gid"));
            }

            messageEntity.setClient(message.getString("client"));
//            messageEntity.setDestroyAfterReading(message.getBoolean("destroyAfterReading"));
//            messageEntity.setDestroyAfterLength(message.getInt("destroyAfterLength"));
            if (type.equals(BDCoreConstant.MESSAGE_TYPE_TEXT)) {
                messageEntity.setContent(message.getString("content"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_ROBOT)) {
                String content = message.getString("content");
                // 附属问题
                // FIXME: 查询出的历史聊天记录里面没有 附属问题？
                if (!message.isNull("answers")) {
                    JSONArray jsonArray = message.getJSONArray("answers");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject answerObject = jsonArray.getJSONObject(i);
                        String aid = answerObject.getString("aid");
                        String question = answerObject.getString("question");
                        content += "\n\n" + aid + ":" + question;
                    }
                }
                // TODO: 添加 ‘人工客服’ 点击按钮
                content += "\n\n00001:人工客服";
                messageEntity.setContent(content);

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_IMAGE)) {
                // messageEntity.setPicUrl(message.getString("picUrl"));
                messageEntity.setImageUrl(message.getString("imageUrl"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_VOICE)) {
                messageEntity.setMediaId(message.getString("mediaId"));
                messageEntity.setFormat(message.getString("format"));
                messageEntity.setVoiceUrl(message.getString("voiceUrl"));
                messageEntity.setLength(message.getInt("length"));
                messageEntity.setPlayed(message.getBoolean("played"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_FILE)) {
                messageEntity.setFormat(message.getString("format"));
                messageEntity.setFileUrl(message.getString("fileUrl"));
                messageEntity.setFileName(message.getString("fileName"));
                messageEntity.setFileSize(message.getString("fileSize"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_QUESTIONNAIRE)) {
                // TODO: 用弹窗的方式替代，暂不存储
                return;
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_WORK_GROUP)) {
                // TODO: 用弹窗的方式替代，暂不存储
                return;
            } else if (type.startsWith(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION)) {
                messageEntity.setContent(message.getString("content"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_SHORTVIDEO)
                    || type.equals(BDCoreConstant.MESSAGE_TYPE_VIDEO)) {

                messageEntity.setThumbMediaId(message.getString("thumbMediaId"));
                messageEntity.setVideoOrShortUrl(message.getString("videoOrShortUrl"));
                messageEntity.setVideoOrShortThumbUrl(message.getString("videoOrShortThumbUrl"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_LOCATION)) {

                messageEntity.setLocationX(message.getDouble("locationX"));
                messageEntity.setLocationY(message.getDouble("locationY"));
                messageEntity.setScale(message.getDouble("scale"));
                messageEntity.setLabel(message.getString("label"));

            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_COMMODITY)) {

                messageEntity.setContent(message.getString("content"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_RED_PACKET)) {

                messageEntity.setContent(message.getString("content"));
            } else {

                messageEntity.setContent(message.getString("content"));
            }
            messageEntity.setCreatedAt(message.getString("createdAt"));

            if (!message.isNull("user")) {
                String senderUid = message.getJSONObject("user").getString("uid");

                messageEntity.setUid(senderUid);
                messageEntity.setUsername(message.getJSONObject("user").getString("username"));
                messageEntity.setNickname(message.getJSONObject("user").getString("nickname"));
                messageEntity.setAvatar(message.getJSONObject("user").getString("avatar"));
                //
                if (sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_WORKGROUP)) {

                    messageEntity.setVisitor(message.getJSONObject("user").getBoolean("visitor"));

                } else if (sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_CONTACT)) {
                    //
                    if (senderUid.equals(mPreferenceManager.getUid())) {
                        messageEntity.setCid(message.getString("cid"));
                    } else {
                        messageEntity.setCid(message.getJSONObject("user").getString("uid"));
                    }
                }
            }
            messageEntity.setCurrentUid(mPreferenceManager.getUid());

//            // 是否标记删除
//            if (!message.isNull("deletedSet")) {
//                JSONArray jsonArray = message.getJSONArray("deletedSet");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject userObject = jsonArray.getJSONObject(i);
//                    if (!userObject.isNull("uid")) {
//                        String uid = userObject.getString("uid");
//                        if (uid.equals(mPreferenceManager.getUid())) {
//                            messageEntity.setMarkDeleted(true);
//                        }
//                    }
//                }
//            }

            //
            if (messageEntity.getUid().equals(messageEntity.getCurrentUid())) {
                if (!localId.equals("null") && localId.trim().length() > 0) {
                    Logger.d("delete localId: " + localId);
//                    messageEntity.setLocalId(localId);
                    new deleteMessageLocalAsyncTask(mMessageDao).execute(localId);
//                    new updateMessageAsyncTask(mMessageDao).execute(localId, String.valueOf(id), mid, status);
//                    new updateMessageAsyncTask(mMessageDao).execute(localId, mid, status);
//                    return;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        new insertMessageAsyncTask(mMessageDao).execute(messageEntity);
    }

    public void updateMessageStatusSuccess(String localId) {
        updateMessageStatus(localId, BDCoreConstant.MESSAGE_STATUS_STORED);
    }

    public void updateMessageStatusError(String localId) {
        updateMessageStatus(localId, BDCoreConstant.MESSAGE_STATUS_ERROR);
    }


    public void updateMessageStatus(String localId, String status) {
        new updateMessageStatusAsyncTask(mMessageDao).execute(localId, status);
    }

    public void updateMessageStatusMidReceived(String mId) {
        updateMessageStatusMid(mId, BDCoreConstant.MESSAGE_STATUS_RECEIVED);
    }

    public void updateMessageStatusMidRead(String mId) {
        updateMessageStatusMid(mId, BDCoreConstant.MESSAGE_STATUS_READ);
    }

    public void updateMessageStatusMidRecall(String mId) {
        updateMessageStatusMid(mId, BDCoreConstant.MESSAGE_STATUS_RECALL);
    }

    public void updateMessageStatusMid(String mId, String status) {
        new updateMessageStatusMidAsyncTask(mMessageDao).execute(mId, status);
    }

    public void markDeletedMessage(String mid) {
        new markDeletedMessageAsyncTask(mMessageDao).execute(mid);
    }

    public void insertThreadEntity(ThreadEntity threadEntity) {
        new insertThreadAsyncTask(mThreadDao).execute(threadEntity);
    }

    public void insertThreadJson(JSONObject thread) {

        ThreadEntity threadEntity = new ThreadEntity();
        try {
//            threadEntity.setId(thread.getLong("id"));
            threadEntity.setTid(thread.getString("tid"));
            threadEntity.setTopic(thread.getString("topic"));
            threadEntity.setUnreadCount(thread.getInt("unreadCount"));
            threadEntity.setContent(thread.getString("content"));
            threadEntity.setClosed(thread.getBoolean("closed"));
            threadEntity.setType(thread.getString("type"));
            threadEntity.setNickname(thread.getString("nickname"));
            threadEntity.setAvatar(thread.getString("avatar"));
            threadEntity.setClient(thread.getString("client"));
            threadEntity.setTimestamp(thread.getString("timestamp"));
            threadEntity.setCurrentUid(mPreferenceManager.getUid());
            // 是否临时会话，根据好友关系
//            Logger.i("insert thread json nickname %s, topic %s",
//                    threadEntity.getNickname(), threadEntity.getTopic());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // FIXME: 按照 visitor uid/contact uid/group gid，一个id列表中仅显示一条thread, 所以先删除，后插入；待优化
//        new deleteThreadAsyncTask(mThreadDao).execute(thread);
        new insertThreadAsyncTask(mThreadDao).execute(threadEntity);
//        new insertThreadAbortAsyncTask(mThreadDao).equals(threadEntity);
    }

    public void insertThreadProcessingJson(JSONObject thread) {

        ThreadEntity threadEntity = new ThreadEntity();
        threadEntity.setProcessing(true);

        try {

//            threadEntity.setId(thread.getLong("id"));
            threadEntity.setTid(thread.getString("tid"));
            threadEntity.setTopic(thread.getString("topic"));
            threadEntity.setUnreadCount(thread.getInt("unreadCount"));
            threadEntity.setContent(thread.getString("content"));
            threadEntity.setClosed(thread.getBoolean("closed"));
            //
            String type = thread.getString("type");
            threadEntity.setType(type);
            threadEntity.setNickname(thread.getString("nickname"));
            threadEntity.setAvatar(thread.getString("avatar"));
            threadEntity.setClient(thread.getString("client"));
            threadEntity.setTimestamp(thread.getString("timestamp"));
            threadEntity.setCurrentUid(mPreferenceManager.getUid());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // FIXME: 按照 visitor uid/contact uid/group gid，一个id列表中仅显示一条thread, 所以先删除，后插入；待优化
//        new deleteThreadAsyncTask(mThreadDao).execute(thread);
        new insertThreadAsyncTask(mThreadDao).execute(threadEntity);
    }

    public void markTopThread(String tid) {
        new markTopThreadAsyncTask(mThreadDao).execute(tid);
    }

    public void unmarkTopThread(String tid) {
        new unmarkTopThreadAsyncTask(mThreadDao).execute(tid);
    }

    public void markDisturbThread(String tid) {
        new markDisturbThreadAsyncTask(mThreadDao).execute(tid);
    }

    public void unmarkDisturbThread(String tid) {
        new unmarkDisturbThreadAsyncTask(mThreadDao).execute(tid);
    }

    public void markUnreadThread(String tid) {
        new markUnreadThreadAsyncTask(mThreadDao).execute(tid);
    }

    public void unmarkUnreadThread(String tid) {
        new unmarkUnreadThreadAsyncTask(mThreadDao).execute(tid);
    }

    public void clearUnreadCountThread(String tid) {
        new clearUnreadCountThreadAsyncTask(mThreadDao).execute(tid);
    }

    public void markDeletedThread(String tid) {
        new markDeletedThreadAsyncTask(mThreadDao).execute(tid);
    }

    public void deleteThreadEntity(ThreadEntity threadEntity) {
        new deleteThreadEntityAsyncTask(mThreadDao).execute(threadEntity);
    }

    public void deleteContactEntity(ContactEntity contactEntity) {
        new deleteContactEntityAsyncTask(mContactDao).execute(contactEntity);
    }

    public void deleteContact(String uid) {
        new deleteContactAsyncTask(mContactDao).execute(uid);
    }

    public void deleteFriendEntity(FriendEntity friendEntity) {
        new deleteFriendEntityAsyncTask(mFriendDao).execute(friendEntity);
    }

    public void deleteFriend(String uid) {
        new deleteFriendAsyncTask(mFriendDao).execute(uid);
    }

    public void deleteGroupEntity(GroupEntity groupEntity) {
        new deleteGroupEntityAsyncTask(mGroupDao).execute(groupEntity);
    }

    public void deleteGroup(String gid) {
        new deleteGroupAsyncTask(mGroupDao).execute(gid);
    }

    public void deleteMessageEntity(MessageEntity messageEntity) {
        new deleteMessageEntityAsyncTask(mMessageDao).execute(messageEntity);
    }

    public void deleteMessage(String mid) {
        new deleteMessageAsyncTask(mMessageDao).execute(mid);
    }

    public void deleteMessageLocal(String localId) {
        new deleteMessageLocalAsyncTask(mMessageDao).execute(localId);
    }

    public void deleteThreadMessage(String threadTid) {
        new deleteThreadMessageAsyncTask(mMessageDao).execute(threadTid);
    }

    public void deleteContactMessage(String uid) {
        new deleteContactMessageAsyncTask(mMessageDao).execute(uid);
    }

    public void deleteGroupMessage(String gid) {
        new deleteGroupMessageAsyncTask(mMessageDao).execute(gid);
    }

    public void deleteNoticeEntity(NoticeEntity noticeEntity) {
        new deleteNoticeEntityAsyncTask(mNoticeDao).execute(noticeEntity);
    }

    public void deleteAll() {
        deleteAllContacts();
        deleteAllFriends();
        deleteAllGroups();
        deleteAllMessages();
        deleteAllNotices();
        deleteAllQueues();
        deleteAllThreads();
        deleteAllWorkGroups();
    }

    public void deleteAllContacts() {
        new deleteAllContactsAsyncTask(mContactDao).execute();
    }

    public void deleteAllFriends() {
        new deleteAllFriendsAsyncTask(mFriendDao).execute();
    }

    public void deleteAllGroups() {
        new deleteAllGroupsAsyncTask(mGroupDao).execute();
    }

    public void deleteAllMessages() {
        new deleteAllMessagesAsyncTask(mMessageDao).execute();
    }

    public void deleteAllNotices() {
        new deleteAllNoticesAsyncTask(mNoticeDao).execute();
    }

    public void deleteAllQueues() {
        new deleteAllQueuesAsyncTask(mQueueDao).execute();
    }

    public void deleteAllThreads() {
        new deleteAllThreadsAsyncTask(mThreadDao).execute();
    }

    public void deleteAllWorkGroups() {
        new deleteAllWorkGroupsAsyncTask(mWorkGroupDao).execute();
    }

    /**
     * 删除本地访客会话
     * @param uid
     */
    public void delelteVisitorThread(String uid) {
        deleteThread("visitor", uid);
    }

    /**
     * 删除本地一对一会话
     * @param uid
     */
    public void deleteContactThread(String uid) {
        deleteThread("contact", uid);
    }

    /**
     * 删除本地群组会话
     * @param gid
     */
    public void deleteGroupThread(String gid) {
        deleteThread("group", gid);
    }

    private void deleteThread(String type, String uId) {
        //
        JSONObject thread = new JSONObject();

        try {
            //
            JSONObject jsonObject = new JSONObject();

            if (type.equals("visitor")) {
                jsonObject.put("uid", uId);
            } else if (type.equals("contact")) {
                jsonObject.put("uid", uId);
            } else if (type.equals("group")) {
                jsonObject.put("gid", uId);
            }

            thread.put(type, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new deleteThreadAsyncTask(mThreadDao).execute(thread);
    }

    public void insertQueueJson(JSONObject queue) {
//        Logger.d("insert queue");
        QueueEntity queueEntity = new QueueEntity();

        try {

            queueEntity.setId(queue.getLong("id"));
            queueEntity.setQid(queue.getString("qid"));

            if (!queue.isNull("visitor")) {
                queueEntity.setVisitorUid(queue.getJSONObject("visitor").getString("uid"));
                queueEntity.setNickname(queue.getJSONObject("visitor").getString("nickname"));
                queueEntity.setAvatar(queue.getJSONObject("visitor").getString("avatar"));
                queueEntity.setVisitorClient(queue.getJSONObject("visitor").getString("client"));
            }
//            queueEntity.setAgentUid(queue.getJSONObject("agent").getString("uid"));
//            queueEntity.setAgentClient(queue.getString("agentClient"));
//            queueEntity.setThreadTid(queue.getJSONObject("thread").getString("tid"));
//            queueEntity.setWorkGroupWid(queue.getJSONObject("workGroup").getString("wid"));

            queueEntity.setActionedAt(queue.getString("actionedAt"));
            queueEntity.setStatus(queue.getString("status"));

            queueEntity.setCurrentUid(mPreferenceManager.getUid());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new insertQueueAsyncTask(mQueueDao).execute(queueEntity);
    }

    public void deleteQueueEntity(QueueEntity queueEntity) {
        new deleteQueueEntityAsyncTask(mQueueDao).execute(queueEntity);
    }

    public void insertContactEntity(ContactEntity contactEntity) {
        new insertContactAsyncTask(mContactDao).execute(contactEntity);
    }

    public void insertFriendEntity(FriendEntity friendEntity) {
        new insertFriendAsyncTask(mFriendDao).execute(friendEntity);
    }

    public void insertGroupEntity(GroupEntity groupEntity) {
        new insertGroupAsyncTask(mGroupDao).execute(groupEntity);
    }

    public void insertMessageEntity(MessageEntity messageEntity) {
        new insertMessageAsyncTask(mMessageDao).execute(messageEntity);
    }

    public void insertNoticeEntity(NoticeEntity noticeEntity) {
        new insertNoticeAsyncTask(mNoticeDao).execute(noticeEntity);
    }

    public void insertContactJson(JSONObject contact) {
//        Logger.d("insert contact");
        ContactEntity contactEntity = new ContactEntity();

        try {

            if (contact.getString("uid").equals(mPreferenceManager.getUid()) ||
                    contact.getBoolean("robot") ||
                    contact.getString("username").equals("bytedesk_notification")) {
                return;
            }

            contactEntity.setId(contact.getLong("id"));
            contactEntity.setUid(contact.getString("uid"));
            contactEntity.setUsername(contact.getString("username"));
            contactEntity.setEmail(contact.getString("email"));
            contactEntity.setMobile(contact.getString("mobile"));
            contactEntity.setNickname(contact.getString("nickname"));
            contactEntity.setRealName(contact.getString("realName"));
            contactEntity.setAvatar(contact.getString("avatar"));
            contactEntity.setSubDomain(contact.getString("subDomain"));
            contactEntity.setConnectionStatus(contact.getString("connectionStatus"));
            contactEntity.setAcceptStatus(contact.getString("acceptStatus"));
            contactEntity.setEnabled(contact.getBoolean("enabled"));
            contactEntity.setRobot(contact.getBoolean("robot"));
//            contactEntity.setWelcomeTip(contact.getString("welcomeTip"));
//            contactEntity.setAutoReply(contact.getBoolean("autoReply"));
//            contactEntity.setAutoReplyContent(contact.getString("autoReplyContent"));
//            contactEntity.setDescription(contact.getString("description"));
//            contactEntity.setMaxThreadCount(contact.getInt("maxThreadCount"));

            if (!contact.isNull("nickname") && contactEntity.getNickname() != null && contactEntity.getNickname().length() > 0) {
                char firstWord = contactEntity.getNickname().charAt(0);
                if (Pinyin.isChinese(firstWord)) {
                    String cap = Pinyin.toPinyin(firstWord);
                    if (cap != null && cap.length() > 0) {
                        contactEntity.setPinyinCapNickname(String.valueOf(cap.charAt(0)));
                    }
                } else {
                    contactEntity.setPinyinCapNickname(String.valueOf(firstWord).toUpperCase());
                }
            }

            if (!contact.isNull("realName") && contactEntity.getRealName() != null && contactEntity.getRealName().length() > 0) {
                char firstWord = contactEntity.getRealName().charAt(0);
                if (Pinyin.isChinese(firstWord)) {
                    String cap = Pinyin.toPinyin(firstWord);
                    if (cap != null && cap.length() > 0) {
                        contactEntity.setPinyinCapRealname(String.valueOf(cap.charAt(0)));
                    }
                } else {
                    contactEntity.setPinyinCapRealname(String.valueOf(firstWord).toUpperCase());
                }
            }

            contactEntity.setCurrentUid(mPreferenceManager.getUid());

            if (contactEntity.getUid().equals(mPreferenceManager.getUid())) {
                mPreferenceManager.setDescription(contactEntity.getDescription());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new insertContactAsyncTask(mContactDao).execute(contactEntity);
    }

    public void insertWorkGroupJson(JSONObject workGroup) {
//        Logger.i("insert workGroup");
        WorkGroupEntity workGroupEntity = new WorkGroupEntity();

        try {

//            workGroupEntity.setId(workGroup.getLong("id"));
            workGroupEntity.setWid(workGroup.getString("wid"));
            workGroupEntity.setNickname(workGroup.getString("nickname"));
            workGroupEntity.setAvatar(workGroup.getString("avatar"));
            workGroupEntity.setDefaultRobot(workGroup.getBoolean("defaultRobot"));
            workGroupEntity.setOfflineRobot(workGroup.getBoolean("offlineRobot"));
            workGroupEntity.setSlogan(workGroup.getString("slogan"));
            workGroupEntity.setWelcomeTip(workGroup.getString("welcomeTip"));
            workGroupEntity.setAcceptTip(workGroup.getString("acceptTip"));
            workGroupEntity.setNonWorkingTimeTip(workGroup.getString("nonWorkingTimeTip"));
            workGroupEntity.setOfflineTip(workGroup.getString("offlineTip"));
            workGroupEntity.setCloseTip(workGroup.getString("closeTip"));
            workGroupEntity.setAutoCloseTip(workGroup.getString("autoCloseTip"));
            workGroupEntity.setForceRate(workGroup.getBoolean("forceRate"));
            workGroupEntity.setRouteType(workGroup.getString("routeType"));
            workGroupEntity.setDefaulted(workGroup.getBoolean("defaulted"));
            workGroupEntity.setAbout(workGroup.getString("about"));
            workGroupEntity.setDescription(workGroup.getString("description"));

            //
//            workGroupEntity.setDepartment(workGroup.getBoolean("department"));
//            workGroupEntity.setAutoPop(workGroup.getBoolean("autoPop"));
//            workGroupEntity.setPopAfterTimeLength(workGroup.getInt("popAfterTimeLength"));

            // FIXME: 值有可能为空
//            workGroupEntity.setQuestionnaireId(workGroup.getJSONObject("questionnaire").getLong("id"));
//            workGroupEntity.setOnDutyWorkGroupId(workGroup.getJSONObject("onDutyWorkGroup").getLong("id"));

            workGroupEntity.setCurrentUid(mPreferenceManager.getUid());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new insertWorkGroupAsyncTask(mWorkGroupDao).execute(workGroupEntity);
    }

    public void insertGroupJson(JSONObject group) {

        GroupEntity groupEntity = new GroupEntity();

        try {
            Logger.i("insert group: " + group.getString("nickname"));

            groupEntity.setId(group.getLong("id"));
            groupEntity.setGid(group.getString("gid"));
            groupEntity.setNickname(group.getString("nickname"));
            groupEntity.setAvatar(group.getString("avatar"));

            if (!group.isNull("type")) {
                groupEntity.setType(group.getString("type"));
            }

            if (!group.isNull("memberCount")) {
                groupEntity.setMemberCount(group.getInt("memberCount"));
            }

            if (!group.isNull("description")) {
                groupEntity.setDescription(group.getString("description"));
            }

            if (!group.isNull("announcement")) {
                groupEntity.setAnnouncement(group.getString("announcement"));
            }

            if (!group.isNull("dismissed")) {
                groupEntity.setDismissed(group.getBoolean("dismissed"));
            }

            groupEntity.setCurrentUid(mPreferenceManager.getUid());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new insertGroupAsyncTask(mGroupDao).execute(groupEntity);
    }

    public void insertNoticeJson(JSONObject notice) {

        NoticeEntity noticeEntity = new NoticeEntity();

        try {

            noticeEntity.setId(notice.getLong("id"));
            noticeEntity.setNid(notice.getString("nid"));
            noticeEntity.setTitle(notice.getString("title"));
            noticeEntity.setContent(notice.getString("content"));
            noticeEntity.setProcessed(notice.getBoolean("processed"));

            if (!notice.isNull("user")) {
                noticeEntity.setUserUid(notice.getJSONObject("user").getString("uid"));
            }

            if (!notice.isNull("group")) {
                noticeEntity.setGid(notice.getJSONObject("group").getString("gid"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new insertNoticeAsyncTask(mNoticeDao).execute(noticeEntity);
    }

    public void insertFriendJson(JSONObject friend) {
//        Logger.d("insert contact");
        FriendEntity friendEntity = new FriendEntity();

        try {

            if (friend.getString("uid").equals(mPreferenceManager.getUid())) {
                return;
            }

            friendEntity.setId(friend.getLong("id"));
            friendEntity.setUid(friend.getString("uid"));
            friendEntity.setUsername(friend.getString("username"));
            friendEntity.setEmail(friend.getString("email"));
            friendEntity.setMobile(friend.getString("mobile"));
            friendEntity.setNickname(friend.getString("nickname"));
            friendEntity.setRealName(friend.getString("realName"));
            friendEntity.setAvatar(friend.getString("avatar"));
            friendEntity.setSubDomain(friend.getString("subDomain"));
            friendEntity.setConnectionStatus(friend.getString("connectionStatus"));
            friendEntity.setAcceptStatus(friend.getString("acceptStatus"));
            friendEntity.setEnabled(friend.getBoolean("enabled"));
            friendEntity.setRobot(friend.getBoolean("robot"));
            friendEntity.setWelcomeTip(friend.getString("welcomeTip"));
            friendEntity.setAutoReply(friend.getBoolean("autoReply"));
            friendEntity.setAutoReplyContent(friend.getString("autoReplyContent"));
            friendEntity.setDescription(friend.getString("description"));
            friendEntity.setMaxThreadCount(friend.getInt("maxThreadCount"));

            friendEntity.setCurrentUid(mPreferenceManager.getUid());

            if (friendEntity.getUid().equals(mPreferenceManager.getUid())) {
                mPreferenceManager.setDescription(friendEntity.getDescription());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new insertFriendAsyncTask(mFriendDao).execute(friendEntity);
    }

    private static class insertMessageAsyncTask extends AsyncTask<MessageEntity, Void, Void> {

        private MessageDao messageDao;

        insertMessageAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(MessageEntity... messageEntities) {
            messageDao.insertMessage(messageEntities[0]);
            return null;
        }
    }

//    private static class updateMessageAsyncTask extends AsyncTask<String, Void, String> {
//
//        private MessageDao messageDao;
//
//        updateMessageAsyncTask(MessageDao dao) {
//            messageDao = dao;
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String localId = strings[0];
//
//            //
//            Long id = Long.valueOf(strings[1]);
//            String mid = strings[2];
//            String status = strings[3];
//            messageDao.updateMessage(localId, id, mid, status);
//
////            String mid = strings[1];
////            String status = strings[2];
////            messageDao.updateMessage(localId, mid, status);
//
//            Logger.i( "id: " + id + " localId: " + localId  + " mid: " + mid + " status: " + status);
//
//            return null;
//        }
//    }

    private static class updateMessageStatusAsyncTask extends AsyncTask<String, Void, String> {

        private MessageDao messageDao;

        updateMessageStatusAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected String doInBackground(String... strings) {
            String localId = strings[0];
            String status = strings[1];
//            Logger.i("更新状态：%s, %s", localId, status);
            messageDao.updateMessageStatus(localId, status);
            return null;
        }
    }

    private static class updateMessageStatusMidAsyncTask extends AsyncTask<String, Void, String> {

        private MessageDao messageDao;

        updateMessageStatusMidAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected String doInBackground(String... strings) {
            String mId = strings[0];
            String status = strings[1];
            messageDao.updateMessageStatusMid(mId, status);
            return null;
        }
    }

    private static class markDeletedMessageAsyncTask extends AsyncTask<String, Void, Void> {

        private MessageDao messageDao;

        markDeletedMessageAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String mid = strings[0];
            messageDao.markDeletedMessage(mid);
            return null;
        }
    }

    private static class deleteMessageAsyncTask extends AsyncTask<String, Void, Void> {

        private MessageDao messageDao;

        deleteMessageAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String mId = strings[0];
            messageDao.deleteMessageMid(mId);
            return null;
        }
    }

    private static class deleteMessageLocalAsyncTask extends AsyncTask<String, Void, Void> {

        private MessageDao messageDao;

        deleteMessageLocalAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String localId = strings[0];
            messageDao.deleteMessage(localId);
            return null;
        }
    }

    private static class deleteThreadMessageAsyncTask extends AsyncTask<String, Void, Void> {

        private MessageDao messageDao;

        deleteThreadMessageAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String threadTid = strings[0];
            messageDao.deleteThreadMessages(threadTid);
            return null;
        }
    }

    private static class deleteContactMessageAsyncTask extends AsyncTask<String, Void, Void> {

        private MessageDao messageDao;

        deleteContactMessageAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String uid = strings[0];
            messageDao.deleteContactMessages(uid);
            return null;
        }
    }

    private static class deleteGroupMessageAsyncTask extends AsyncTask<String, Void, Void> {

        private MessageDao messageDao;

        deleteGroupMessageAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String gid = strings[0];
            messageDao.deleteGroupMessages(gid);
            return null;
        }
    }


    private static class insertThreadAsyncTask extends AsyncTask<ThreadEntity, Void, Void> {

        private ThreadDao threadDao;

        insertThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(ThreadEntity... threadEntities) {
            threadDao.insertThread(threadEntities[0]);
            return null;
        }
    }

    private static class insertThreadAbortAsyncTask extends AsyncTask<ThreadEntity, Void, Void> {

        private ThreadDao threadDao;

        insertThreadAbortAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(ThreadEntity... threadEntities) {
            threadDao.insertThreadAbort(threadEntities[0]);
            return null;
        }
    }

    private static class insertContactAsyncTask extends AsyncTask<ContactEntity, Void, Void> {

        private ContactDao contactDao;

        insertContactAsyncTask(ContactDao dao) {
            contactDao = dao;
        }

        @Override
        protected Void doInBackground(ContactEntity... contactEntities) {
            contactDao.insertContact(contactEntities[0]);
            return null;
        }
    }

    private static class markTopThreadAsyncTask extends AsyncTask<String, Void, Void> {

        private ThreadDao threadDao;

        markTopThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String tid = strings[0];
            threadDao.markTopThread(tid);
            return null;
        }
    }

    private static class unmarkTopThreadAsyncTask extends AsyncTask<String, Void, Void> {

        private ThreadDao threadDao;

        unmarkTopThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String tid = strings[0];
            threadDao.unmarkTopThread(tid);
            return null;
        }
    }

    private static class markDisturbThreadAsyncTask extends AsyncTask<String, Void, Void> {

        private ThreadDao threadDao;

        markDisturbThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String tid = strings[0];
            threadDao.markDisturbThread(tid);
            return null;
        }
    }

    private static class unmarkDisturbThreadAsyncTask extends AsyncTask<String, Void, Void> {

        private ThreadDao threadDao;

        unmarkDisturbThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String tid = strings[0];
            threadDao.unmarkDisturbThread(tid);
            return null;
        }
    }

    private static class markUnreadThreadAsyncTask extends AsyncTask<String, Void, Void> {

        private ThreadDao threadDao;

        markUnreadThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String tid = strings[0];
            threadDao.markUnreadThread(tid);
            return null;
        }
    }

    private static class unmarkUnreadThreadAsyncTask extends AsyncTask<String, Void, Void> {

        private ThreadDao threadDao;

        unmarkUnreadThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String tid = strings[0];
            threadDao.unmarkUnreadThread(tid);
            return null;
        }
    }

    private static class clearUnreadCountThreadAsyncTask extends AsyncTask<String, Void, Void> {

        private ThreadDao threadDao;

        clearUnreadCountThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String tid = strings[0];
            threadDao.clearUnreadCountThread(tid);
            return null;
        }
    }


    private static class markDeletedThreadAsyncTask extends AsyncTask<String, Void, Void> {

        private ThreadDao threadDao;

        markDeletedThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String tid = strings[0];
            threadDao.markDeletedThread(tid);
            return null;
        }
    }


    private static class deleteThreadAsyncTask extends AsyncTask<JSONObject, Void, Void> {

        private ThreadDao threadDao;

        deleteThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(JSONObject... jsonObjects) {
            JSONObject thread = jsonObjects[0];

            try {
                if (!thread.isNull("visitor")) {
                    threadDao.deleteThreadWithVisitor(thread.getJSONObject("visitor").getString("uid"));
                }

                if (!thread.isNull("contact")) {
                    threadDao.deleteThreadWithContact(thread.getJSONObject("contact").getString("uid"));
                }

                if (!thread.isNull("group")) {
                    threadDao.deleteThreadWithGroup(thread.getJSONObject("group").getString("gid"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    private static class deleteThreadEntityAsyncTask extends AsyncTask<ThreadEntity, Void, Void> {

        private ThreadDao threadDao;

        deleteThreadEntityAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(ThreadEntity... threadEntities) {
            ThreadEntity threadEntity = threadEntities[0];
//            threadDao.deleteThread(threadEntity.getId());
            threadDao.deleteThread(threadEntity.getTopic());
            return null;
        }
    }

    private static class deleteContactEntityAsyncTask extends AsyncTask<ContactEntity, Void, Void> {

        private ContactDao contactDao;

        deleteContactEntityAsyncTask(ContactDao dao) {
            contactDao = dao;
        }

        @Override
        protected Void doInBackground(ContactEntity... contactEntities) {
            ContactEntity contactEntity = contactEntities[0];
            contactDao.deleteContact(contactEntity.getId());
            return null;
        }
    }

    private static class deleteContactAsyncTask extends AsyncTask<String, Void, Void> {

        private ContactDao contactDao;

        deleteContactAsyncTask(ContactDao dao) {
            contactDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String uid = strings[0];
            contactDao.deleteContact(uid);
            return null;
        }
    }

    private static class deleteFriendEntityAsyncTask extends AsyncTask<FriendEntity, Void, Void> {

        private FriendDao friendDao;

        deleteFriendEntityAsyncTask(FriendDao dao) {
            friendDao = dao;
        }

        @Override
        protected Void doInBackground(FriendEntity... friendEntities) {
            FriendEntity friendEntity = friendEntities[0];
            friendDao.deleteFriend(friendEntity.getId());
            return null;
        }
    }

    private static class deleteFriendAsyncTask extends AsyncTask<String, Void, Void> {

        private FriendDao friendDao;

        deleteFriendAsyncTask(FriendDao dao) {
            friendDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String uid = strings[0];
            friendDao.deleteFriend(uid);
            return null;
        }
    }

    private static class deleteGroupEntityAsyncTask extends AsyncTask<GroupEntity, Void, Void> {

        private GroupDao groupDao;

        deleteGroupEntityAsyncTask(GroupDao dao) {
            groupDao = dao;
        }

        @Override
        protected Void doInBackground(GroupEntity... groupEntities) {
            GroupEntity groupEntity = groupEntities[0];
            groupDao.deleteGroup(groupEntity.getId());
            return null;
        }
    }

    private static class deleteGroupAsyncTask extends AsyncTask<String, Void, Void> {

        private GroupDao groupDao;

        deleteGroupAsyncTask(GroupDao dao) {
            groupDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String gid = strings[0];
            groupDao.deleteGroup(gid);
            return null;
        }
    }

    private static class deleteMessageEntityAsyncTask extends AsyncTask<MessageEntity, Void, Void> {

        private MessageDao messageDao;

        deleteMessageEntityAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(MessageEntity... messageEntities) {
            MessageEntity messageEntity = messageEntities[0];
            messageDao.deleteMessageMid(messageEntity.getMid());
//            messageDao.deleteMessage(messageEntity.getId());
            return null;
        }
    }

    private static class deleteNoticeEntityAsyncTask extends AsyncTask<NoticeEntity, Void, Void> {

        private NoticeDao noticeDao;

        deleteNoticeEntityAsyncTask(NoticeDao dao) {
            noticeDao = dao;
        }

        @Override
        protected Void doInBackground(NoticeEntity... noticeEntities) {
            NoticeEntity noticeEntity = noticeEntities[0];
            noticeDao.deleteNotice(noticeEntity.getId());
            return null;
        }
    }

    private static class deleteNoticeAsyncTask extends AsyncTask<String, Void, Void> {

        private NoticeDao noticeDao;

        deleteNoticeAsyncTask(NoticeDao dao) {
            noticeDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String nid = strings[0];
            noticeDao.deleteNotice(nid);
            return null;
        }
    }

    private static class deleteQueueEntityAsyncTask extends AsyncTask<QueueEntity, Void, Void> {

        private QueueDao queueDao;

        deleteQueueEntityAsyncTask(QueueDao dao) {
            queueDao = dao;
        }

        @Override
        protected Void doInBackground(QueueEntity... queueEntities) {
            QueueEntity queueEntity = queueEntities[0];
            queueDao.deleteQueue(queueEntity.getId());
            return null;
        }
    }

    private static class insertQueueAsyncTask extends AsyncTask<QueueEntity, Void, Void> {

        private QueueDao queueDao;

        insertQueueAsyncTask(QueueDao dao) {
            queueDao = dao;
        }

        @Override
        protected Void doInBackground(QueueEntity... queueEntities) {
            queueDao.insertQueue(queueEntities[0]);
            return null;
        }
    }

    private static class insertFriendAsyncTask extends AsyncTask<FriendEntity, Void, Void> {

        private FriendDao friendDao;

        insertFriendAsyncTask(FriendDao dao) {
            friendDao = dao;
        }

        @Override
        protected Void doInBackground(FriendEntity... friendEntities) {
            friendDao.insertFriend(friendEntities[0]);
            return null;
        }
    }

    private static class insertWorkGroupAsyncTask extends AsyncTask<WorkGroupEntity, Void, Void> {

        private WorkGroupDao workGroupDao;

        insertWorkGroupAsyncTask(WorkGroupDao dao) {
            workGroupDao = dao;
        }

        @Override
        protected Void doInBackground(WorkGroupEntity... workGroupEntities) {
            workGroupDao.insertWorkGroup(workGroupEntities[0]);
            return null;
        }
    }

    private static class insertGroupAsyncTask extends AsyncTask<GroupEntity, Void, Void> {

        private GroupDao groupDao;

        insertGroupAsyncTask(GroupDao dao) {
            groupDao = dao;
        }

        @Override
        protected Void doInBackground(GroupEntity... groupEntities) {
            groupDao.insertGroup(groupEntities[0]);
            return null;
        }
    }

    private static class insertNoticeAsyncTask extends AsyncTask<NoticeEntity, Void, Void> {

        private NoticeDao noticeDao;

        insertNoticeAsyncTask(NoticeDao dao) {
            noticeDao = dao;
        }

        @Override
        protected Void doInBackground(NoticeEntity... noticeEntities) {
            noticeDao.insertNotice(noticeEntities[0]);
            return null;
        }
    }


    private static class getWorkGroupListAsyncTask extends AsyncTask<String, Void, List<WorkGroupEntity>> {

        private WorkGroupDao workGroupDao;

        getWorkGroupListAsyncTask(WorkGroupDao dao) {
            workGroupDao = dao;
        }

        @Override
        protected List<WorkGroupEntity> doInBackground(String... strings) {
            return workGroupDao.loadWorkGroupList(strings[0]);
        }
    }

    private static class getGroupListAsyncTask extends AsyncTask<String, Void, List<GroupEntity>> {

        private GroupDao groupDao;

        getGroupListAsyncTask(GroupDao dao) {
            groupDao = dao;
        }

        @Override
        protected List<GroupEntity> doInBackground(String... strings) {
            return groupDao.loadGroupList(strings[0]);
        }
    }

    private static class getProcessingThreadsAsyncTask extends AsyncTask<String, Void, List<ThreadEntity>> {

        private ThreadDao threadDao;

        getProcessingThreadsAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected List<ThreadEntity> doInBackground(String... strings) {
            return threadDao.getProcessingThreads(true, strings[0]);
        }
    }

    private static class getThreadListWithTypeAsyncTask extends AsyncTask<String, Void, List<ThreadEntity>> {

        private ThreadDao threadDao;

        getThreadListWithTypeAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected List<ThreadEntity> doInBackground(String... strings) {
            return threadDao.loadThreadListWithType(strings[0], strings[1]);
        }
    }


    private static class getNoticeListAsyncTask extends AsyncTask<String, Void, List<NoticeEntity>> {

        private NoticeDao noticeDao;

        getNoticeListAsyncTask(NoticeDao dao) {
            noticeDao = dao;
        }

        @Override
        protected List<NoticeEntity> doInBackground(String... strings) {
            return noticeDao.loadNoticeList(strings[0]);
        }
    }

    private static class getThreadAsyncTask extends AsyncTask<String, Void, ThreadEntity> {

        private ThreadDao threadDao;

        getThreadAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected ThreadEntity doInBackground(String... strings) {
            return threadDao.getThread(strings[0]);
        }
    }


    /**
     * 清空聊天记录表
     */
    private static class deleteAllMessagesAsyncTask extends AsyncTask<Void, Void, Void> {

        private MessageDao messageDao;

        deleteAllMessagesAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            messageDao.deleteAllMessages();
            return null;
        }
    }

    private static class deleteAllThreadsAsyncTask extends AsyncTask<Void, Void, Void> {

        private ThreadDao threadDao;

        deleteAllThreadsAsyncTask(ThreadDao dao) {
            threadDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            threadDao.deleteAllThreads();
            return null;
        }
    }


    private static class deleteAllContactsAsyncTask extends AsyncTask<Void, Void, Void> {

        private ContactDao contactDao;

        deleteAllContactsAsyncTask(ContactDao dao) {
            contactDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.deleteAllContacts();
            return null;
        }
    }

    private static class deleteAllFriendsAsyncTask extends AsyncTask<Void, Void, Void> {

        private FriendDao friendDao;

        deleteAllFriendsAsyncTask(FriendDao dao) {
            friendDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            friendDao.deleteAllFriends();
            return null;
        }
    }

    private static class deleteAllGroupsAsyncTask extends AsyncTask<Void, Void, Void> {

        private GroupDao groupDao;

        deleteAllGroupsAsyncTask(GroupDao dao) {
            groupDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            groupDao.deleteAllGroups();
            return null;
        }
    }


    private static class deleteAllWorkGroupsAsyncTask extends AsyncTask<Void, Void, Void> {

        private WorkGroupDao workGroupDao;

        deleteAllWorkGroupsAsyncTask(WorkGroupDao dao) {
            workGroupDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            workGroupDao.deleteAllWorkGroups();
            return null;
        }
    }

    private static class deleteAllQueuesAsyncTask extends AsyncTask<Void, Void, Void> {

        private QueueDao queueDao;

        deleteAllQueuesAsyncTask(QueueDao dao) {
            queueDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            queueDao.deleteAllQueues();
            return null;
        }
    }

    private static class deleteAllNoticesAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoticeDao noticeDao;

        deleteAllNoticesAsyncTask(NoticeDao dao) {
            noticeDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noticeDao.deleteAllNotices();
            return null;
        }
    }


}






