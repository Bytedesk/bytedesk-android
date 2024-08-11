package com.bytedesk.core.event;

import com.bytedesk.core.room.entity.MessageEntity;

/**
 *
 * @author bytedesk.com
 */
public class MessageEntityEvent {

    private MessageEntity messageEntity;

    public MessageEntityEvent(MessageEntity entity) {
        messageEntity = entity;
    }

    public MessageEntity getMessageEntity() {
        return messageEntity;
    }

    public void setMessageEntity(MessageEntity messageEntity) {
        this.messageEntity = messageEntity;
    }
}
