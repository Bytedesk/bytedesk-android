package com.bytedesk.im.stomp.pathmatcher;

import com.bytedesk.im.stomp.dto.StompMessage;

public interface PathMatcher {

    boolean matches(String path, StompMessage msg);
}
