package com.bytedesk.core.event;

public class BaseEvent {

    private String content;

    public BaseEvent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
