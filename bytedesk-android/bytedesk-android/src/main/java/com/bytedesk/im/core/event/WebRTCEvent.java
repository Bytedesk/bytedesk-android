package com.bytedesk.im.core.event;

import com.bytedesk.im.core.room.entity.MessageEntity;

/**
 * @author bytedesk.com on 2019/4/1
 */
public class WebRTCEvent {

    private MessageEntity mMessageEntity;

    public WebRTCEvent(MessageEntity messageEntity) {
        mMessageEntity = messageEntity;
    }

    public MessageEntity getMessageEntity() {
        return mMessageEntity;
    }

    public void setMessageEntity(MessageEntity messageEntity) {
        this.mMessageEntity = messageEntity;
    }
}
