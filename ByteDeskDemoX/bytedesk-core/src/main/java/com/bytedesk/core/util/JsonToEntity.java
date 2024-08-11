package com.bytedesk.core.util;

import android.content.Context;

import com.bytedesk.core.room.entity.ContactEntity;
import com.bytedesk.core.room.entity.GroupEntity;
import com.bytedesk.core.room.entity.MessageEntity;
import com.bytedesk.core.room.entity.QueueEntity;
import com.bytedesk.core.room.entity.ThreadEntity;
import com.bytedesk.core.room.entity.WorkGroupEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author bytedesk.com on 2018/12/25
 */
public class JsonToEntity {

    private static JsonToEntity sJsonToEntity = null;
    private BDPreferenceManager mPreferenceManager;
    private Context mContext;

    private JsonToEntity(Context context) {
        mContext = context;
        mPreferenceManager = BDPreferenceManager.getInstance(context);
    }

    public static JsonToEntity instance(Context context) {
        if (sJsonToEntity == null) {
            sJsonToEntity = new JsonToEntity(context);
        }
        return sJsonToEntity;
    }

    public ContactEntity contactEntity(JSONObject contact) {

        ContactEntity contactEntity = new ContactEntity();

        try {

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
            contactEntity.setWelcomeTip(contact.getString("welcomeTip"));
            contactEntity.setAutoReply(contact.getBoolean("autoReply"));
            contactEntity.setAutoReplyContent(contact.getString("autoReplyContent"));
            contactEntity.setDescription(contact.getString("description"));
            contactEntity.setMaxThreadCount(contact.getInt("maxThreadCount"));

            contactEntity.setCurrentUid(mPreferenceManager.getUid());

            if (contactEntity.getUid().equals(mPreferenceManager.getUid())) {
                mPreferenceManager.setDescription(contactEntity.getDescription());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contactEntity;
    }

    public GroupEntity groupEntity(JSONObject group) {
        //
        GroupEntity groupEntity = new GroupEntity();

        try {

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
        //
        return groupEntity;
    }

    public MessageEntity messageEntity(JSONObject message) {
        //
        MessageEntity messageEntity = new MessageEntity();
        //
        try {

            String type = message.getString("type");
            messageEntity.setType(type);

            // 更新本地发送消息
            Long id = message.getLong("id");
            String mid = message.getString("mid");
            String status = message.getString("status");
            String localId = message.getString("localId");
            messageEntity.setLocalId(localId);
//            messageEntity.setId(id);
            messageEntity.setMid(mid);
            messageEntity.setStatus(status);

            String sessionType = message.getString("sessionType");
            messageEntity.setSessionType(sessionType);

            if (sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_WORKGROUP)) {
                messageEntity.setWid(message.getString("wid"));
                //
                messageEntity.setThreadTid(message.getJSONObject("thread").getString("tid"));
                messageEntity.setClient(message.getJSONObject("thread").getJSONObject("visitor").getString("client"));
                messageEntity.setVisitorUid(message.getJSONObject("thread").getJSONObject("visitor").getString("uid"));

            } else if (sessionType.equals(BDCoreConstant.MESSAGE_SESSION_TYPE_GROUP)) {
                messageEntity.setGid(message.getString("gid"));
            }

            if (type.equals(BDCoreConstant.MESSAGE_TYPE_TEXT)
                    || type.startsWith(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION)) {
                messageEntity.setContent(message.getString("content"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_IMAGE)) {
                // messageEntity.setPicUrl(message.getString("picUrl"));
                messageEntity.setImageUrl(message.getString("imageUrl"));
            } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_VOICE)) {
                messageEntity.setMediaId(message.getString("mediaId"));
                messageEntity.setFormat(message.getString("format"));
                messageEntity.setVoiceUrl(message.getString("voiceUrl"));
                messageEntity.setLength(message.getInt("length"));
                messageEntity.setPlayed(message.getBoolean("played"));
            } else {
                messageEntity.setThumbMediaId(message.getString("thumbMediaId"));
                messageEntity.setVideoOrShortUrl(message.getString("videoOrShortUrl"));
                messageEntity.setVideoOrShortThumbUrl(message.getString("videoOrShortThumbUrl"));

                messageEntity.setLocationX(message.getDouble("locationX"));
                messageEntity.setLocationY(message.getDouble("locationY"));
                messageEntity.setScale(message.getDouble("scale"));
                messageEntity.setLabel(message.getString("label"));

                messageEntity.setTitle(message.getString("title"));
                messageEntity.setDescription(message.getString("description"));
                messageEntity.setUrl(message.getString("url"));
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messageEntity;
    }

    public QueueEntity queueEntity(JSONObject queue) {
        //
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

        return queueEntity;
    }

    public ThreadEntity threadEntity(JSONObject thread) {
        //
        ThreadEntity threadEntity = new ThreadEntity();

        try {

//            threadEntity.setId(thread.getLong("id"));
            threadEntity.setTid(thread.getString("tid"));
            threadEntity.setUnreadCount(thread.getInt("unreadCount"));
            threadEntity.setContent(thread.getString("content"));

            if (!thread.isNull("token")) {
                threadEntity.setToken(thread.getString("token"));
            }

            //
            String type = thread.getString("type");
            threadEntity.setType(type);

            threadEntity.setNickname(thread.getString("nickname"));
            threadEntity.setAvatar(thread.getString("avatar"));
            threadEntity.setClient(thread.getString("client"));


//            if (type.equals(BDCoreConstant.THREAD_TYPE_THREAD)) {
//
//                if (!thread.isNull("visitor")) {
//                    threadEntity.setVisitorUid(thread.getJSONObject("visitor").getString("uid"));
//                    threadEntity.setNickname(thread.getJSONObject("visitor").getString("nickname"));
//                    threadEntity.setAvatar(thread.getJSONObject("visitor").getString("avatar"));
//                    threadEntity.setClient(thread.getJSONObject("visitor").getString("client"));
//                }
//
//            } else if (type.equals(BDCoreConstant.THREAD_TYPE_CONTACT)) {
//
//                if (!thread.isNull("contact")) {
//                    threadEntity.setContactUid(thread.getJSONObject("contact").getString("uid"));
//                    // threadEntity.setNickname(thread.getJSONObject("contact").getString("realName"));
//                    threadEntity.setNickname(thread.getJSONObject("contact").getString("nickname"));
//                    threadEntity.setAvatar(thread.getJSONObject("contact").getString("avatar"));
//                }
//
//            } else if (type.equals(BDCoreConstant.THREAD_TYPE_GROUP)) {
//
//                if (!thread.isNull("group")) {
//                    threadEntity.setGroupGid(thread.getJSONObject("group").getString("gid"));
//                    threadEntity.setNickname(thread.getJSONObject("group").getString("nickname"));
//                    threadEntity.setAvatar(thread.getJSONObject("group").getString("avatar"));
//                }
//            }

            threadEntity.setTimestamp(thread.getString("timestamp"));
            threadEntity.setCurrentUid(mPreferenceManager.getUid());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return threadEntity;
    }

    public WorkGroupEntity workGroupEntity(JSONObject workGroup) {
        //
        WorkGroupEntity workGroupEntity = new WorkGroupEntity();

        try {

            workGroupEntity.setId(workGroup.getLong("id"));
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

        return workGroupEntity;
    }

}













