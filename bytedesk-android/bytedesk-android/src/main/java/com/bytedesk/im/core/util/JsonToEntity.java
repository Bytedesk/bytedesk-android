package com.bytedesk.im.core.util;

import android.content.Context;

import com.bytedesk.im.core.api.BytedeskConstants;
import com.bytedesk.im.core.room.entity.MessageEntity;
import com.bytedesk.im.core.room.entity.ThreadEntity;

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


    public MessageEntity messageEntity(JSONObject message, ThreadEntity threadEntity) {
        //
        MessageEntity messageEntity = new MessageEntity();
        //
        JSONObject messageObject = new JSONObject();
//        try {
            //
//            messageObject.put("uid", uid);
//            messageObject.put("type", BDCoreConstant.MESSAGE_TYPE_TEXT);
//            messageObject.put("content", content);
//            messageObject.put("status", BytedeskConstants.MESSAGE_STATUS_SENDING);
//            messageObject.put("createdAt", BDCoreUtils.currentDate());
//            messageObject.put("client", BytedeskConstants.HTTP_CLIENT);
////
//            JSONObject extra = new JSONObject();
//            extra.put("orgUid", mPreferenceManager.getOrgUid());
//            messageObject.put("extra", extra);
////
//            JSONObject threadObject = new JSONObject();
//            threadObject.put("uid", mThreadEntity.getUid());
//            threadObject.put("topic", mThreadEntity.getTopic());
//            threadObject.put("type", mThreadEntity.getType());
//            threadObject.put("status", mThreadEntity.getStatus());
//            JSONObject threadUserObject = new JSONObject();
//            threadUserObject.put("uid", mThreadEntity.getUserUid());
//            threadUserObject.put("nickname", mThreadEntity.getUserNickname());
//            threadUserObject.put("avatar", mThreadEntity.getUserAvatar());
//            threadObject.put("user", threadUserObject);
//            messageObject.put("thread", threadObject);
//            //
//            JSONObject userObject = new JSONObject();
//            userObject.put("uid", mPreferenceManager.getUid());
//            userObject.put("nickname", mPreferenceManager.getNickname());
//            userObject.put("avatar", mPreferenceManager.getAvatar());
//            messageObject.put("user", userObject);
            //
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return messageEntity;
    }


}













