package com.bytedesk.core.event;

import org.json.JSONObject;

/**
 *
 * @author bytedesk.com
 */
public class MessageEvent {

    private JSONObject mJsonObject;

    public MessageEvent(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return mJsonObject;
    }

    public void setJsonObject(JSONObject mJsonObject) {
        this.mJsonObject = mJsonObject;
    }
}
