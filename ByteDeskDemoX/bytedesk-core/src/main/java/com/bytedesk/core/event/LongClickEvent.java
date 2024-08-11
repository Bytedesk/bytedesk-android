package com.bytedesk.core.event;

import com.bytedesk.core.room.entity.MessageEntity;

/**
 * @author bytedesk.com on 2019-07-11
 */
public class LongClickEvent {

    private MessageEntity messageEntity;

    public LongClickEvent(MessageEntity messageEntity) {
        this.messageEntity = messageEntity;
    }

    public MessageEntity getMessageEntity() {
        return messageEntity;
    }

    public void setMessageEntity(MessageEntity messageEntity) {
        this.messageEntity = messageEntity;
    }
}
