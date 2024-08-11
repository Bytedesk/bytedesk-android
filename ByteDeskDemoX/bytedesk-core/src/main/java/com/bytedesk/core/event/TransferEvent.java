package com.bytedesk.core.event;

import com.bytedesk.core.room.entity.MessageEntity;

/**
 *
 * @author bytedesk.com
 */
public class TransferEvent {

    private MessageEntity messageEntity;

    public TransferEvent(MessageEntity entity) {
        messageEntity = entity;
    }

    public MessageEntity getMessageEntity() {
        return messageEntity;
    }

    public void setMessageEntity(MessageEntity messageEntity) {
        this.messageEntity = messageEntity;
    }
}
