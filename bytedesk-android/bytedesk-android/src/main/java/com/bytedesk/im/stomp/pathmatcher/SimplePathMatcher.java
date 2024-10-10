package com.bytedesk.im.stomp.pathmatcher;

import com.bytedesk.im.stomp.dto.StompHeader;
import com.bytedesk.im.stomp.dto.StompMessage;

public class SimplePathMatcher implements PathMatcher {

    @Override
    public boolean matches(String path, StompMessage msg) {
        String dest = msg.findHeader(StompHeader.DESTINATION);
        if (dest == null) return false;
        else return path.equals(dest);
    }
}
