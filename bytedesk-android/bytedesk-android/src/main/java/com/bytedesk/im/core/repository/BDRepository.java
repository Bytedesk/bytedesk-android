package com.bytedesk.im.core.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bytedesk.im.core.api.BytedeskConstants;
import com.bytedesk.im.core.room.dao.MessageDao;
import com.bytedesk.im.core.room.dao.ThreadDao;
import com.bytedesk.im.core.room.database.AppDatabase;
import com.bytedesk.im.core.room.entity.MessageEntity;
import com.bytedesk.im.core.room.entity.ThreadEntity;
import com.bytedesk.im.core.util.BDCoreConstant;
import com.bytedesk.im.core.util.BDCoreUtils;
import com.bytedesk.im.core.util.BDPreferenceManager;
import com.github.promeg.pinyinhelper.Pinyin;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <a href="https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html?index=..%2F..%2Findex#7">...</a>
 */
public class BDRepository {

    private static BDRepository sInstance;
    private MessageDao mMessageDao;
    private ThreadDao mThreadDao;

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

        mPreferenceManager = BDPreferenceManager.getInstance(context);
    }

    public void insertTextMessageLocal(String uid, String content, String threadTopic) {
        insertMessageLocal(uid, content, BytedeskConstants.MESSAGE_TYPE_TEXT, threadTopic);
    }

    public LiveData<List<MessageEntity>> getThreadMessages(String threadTopic) {
        return mMessageDao.loadThreadMessages(threadTopic);
    }

//    public LiveData<List<ThreadEntity>> getThreads() {
//        return mThreadDao.loadThreads(mPreferenceManager.getUid());
//    }

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

//    public LiveData<List<ThreadEntity>> searchThreads(String search) {
//        return mThreadDao.searchThreads("%" + search + "%", mPreferenceManager.getUid());
//    }

    /**
     * 插入本地发送消息
     */
    public void insertMessageLocal(String uid, String content, String type, String threadTopic) {

        Logger.i("insert local content: %s uid: %s, threadTopic %s", content, uid, threadTopic);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setUid(uid);
        messageEntity.setLocalId(uid);
        messageEntity.setType(type);
        messageEntity.setClient(BDCoreConstant.CLIENT_ANDROID);
        messageEntity.setContent(content);
        messageEntity.setStatus(BytedeskConstants.MESSAGE_STATUS_SENDING);
        messageEntity.setCreatedAt(BDCoreUtils.currentDate());
        //
        messageEntity.setThreadTopic(threadTopic);
        messageEntity.setUserUid(mPreferenceManager.getUid());
        messageEntity.setUserNickname(mPreferenceManager.getNickname());
        messageEntity.setUserAvatar(mPreferenceManager.getAvatar());
//
        messageEntity.setCurrentUid(mPreferenceManager.getUid());

        Logger.i("insert insertMessageLocal %s", messageEntity.toString());

        new insertMessageAsyncTask(mMessageDao).execute(messageEntity);
    }

    public void insertMessageJson(JSONObject message) {
        Logger.d("insert message：%s", message.toString());
        //
        MessageEntity messageEntity = MessageEntity.fromJson(message, mPreferenceManager.getUid());
//        MessageEntity messageEntity = new MessageEntity();
//        try {
//
//            messageEntity.setUid(message.getString("uid"));
//            messageEntity.setLocalId(message.getString("uid"));
//            messageEntity.setType(message.getString("type"));
//            messageEntity.setContent(message.getString("content"));
//            messageEntity.setStatus(message.getString("status"));
//            messageEntity.setCreatedAt(message.getString("createdAt"));
//            messageEntity.setClient(message.getString("client"));
//            messageEntity.setExtra(message.getString("extra"));
//            //
//            String threadTopic = message.getJSONObject("thread").getString("topic");
//            messageEntity.setThreadTopic(threadTopic);
//            //
//            messageEntity.setUserUid(message.getJSONObject("user").getString("uid"));
//            messageEntity.setUserNickname(message.getJSONObject("user").getString("nickname"));
//            messageEntity.setUserAvatar(message.getJSONObject("user").getString("avatar"));
//
//            messageEntity.setCurrentUid(mPreferenceManager.getUid());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Logger.i("insert insertMessageJson %s", messageEntity.toString());
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

    public void insertThreadEntity(ThreadEntity threadEntity) {
        new insertThreadAsyncTask(mThreadDao).execute(threadEntity);
    }

    public void insertThreadJson(JSONObject thread) {

        ThreadEntity threadEntity = new ThreadEntity();
//        try {
////            threadEntity.setId(thread.getLong("id"));
////            threadEntity.setUid(thread.getString("tid"));
////            threadEntity.setTopic(thread.getString("topic"));
////            threadEntity.setUnreadCount(thread.getInt("unreadCount"));
////            threadEntity.setContent(thread.getString("content"));
////            threadEntity.setClosed(thread.getBoolean("closed"));
////            threadEntity.setType(thread.getString("type"));
////            threadEntity.setNickname(thread.getString("nickname"));
////            threadEntity.setAvatar(thread.getString("avatar"));
////            threadEntity.setClient(thread.getString("client"));
////            threadEntity.setTimestamp(thread.getString("timestamp"));
////            threadEntity.setCurrentUid(mPreferenceManager.getUid());
//            // 是否临时会话，根据好友关系
////            Logger.i("insert thread json nickname %s, topic %s",
////                    threadEntity.getNickname(), threadEntity.getTopic());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        // FIXME: 按照 visitor uid/contact uid/group gid，一个id列表中仅显示一条thread, 所以先删除，后插入；待优化
//        new deleteThreadAsyncTask(mThreadDao).execute(thread);
        new insertThreadAsyncTask(mThreadDao).execute(threadEntity);
//        new insertThreadAbortAsyncTask(mThreadDao).equals(threadEntity);
    }


    public void deleteThreadEntity(ThreadEntity threadEntity) {
        new deleteThreadEntityAsyncTask(mThreadDao).execute(threadEntity);
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


    public void deleteAll() {
        deleteAllMessages();
        deleteAllThreads();
    }


    public void deleteAllMessages() {
        new deleteAllMessagesAsyncTask(mMessageDao).execute();
    }


    public void deleteAllThreads() {
        new deleteAllThreadsAsyncTask(mThreadDao).execute();
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
//        new deleteThreadAsyncTask(mThreadDao).execute(thread);
    }

    public void insertMessageEntity(MessageEntity messageEntity) {
        new insertMessageAsyncTask(mMessageDao).execute(messageEntity);
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

    private static class deleteMessageAsyncTask extends AsyncTask<String, Void, Void> {

        private MessageDao messageDao;

        deleteMessageAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String mId = strings[0];
            messageDao.deleteMessageUid(mId);
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

    private static class deleteMessageEntityAsyncTask extends AsyncTask<MessageEntity, Void, Void> {

        private MessageDao messageDao;

        deleteMessageEntityAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(MessageEntity... messageEntities) {
            MessageEntity messageEntity = messageEntities[0];
            messageDao.deleteMessageUid(messageEntity.getUid());
            return null;
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


}






