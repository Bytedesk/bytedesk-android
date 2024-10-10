package com.bytedesk.im.core.event;

import com.bytedesk.im.core.room.entity.ThreadEntity;

/**
 *
 * @author bytedesk.com
 */
public class ThreadEntityEvent {

    private ThreadEntity threadEntity;

    public ThreadEntityEvent(ThreadEntity entity) {
        threadEntity = entity;
    }

    public ThreadEntity getThreadEntity() {
        return threadEntity;
    }

    public void setThreadEntity(ThreadEntity threadEntity) {
        this.threadEntity = threadEntity;
    }
}
