package com.bytedesk.core.event;

/**
 * 消息预知
 * @author bytedesk.com
 */
public class PreviewEvent extends BaseEvent {

    public PreviewEvent(String content) {
        super(content);
    }
}
