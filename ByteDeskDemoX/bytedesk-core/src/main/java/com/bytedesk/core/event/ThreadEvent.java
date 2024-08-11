package com.bytedesk.core.event;

import org.json.JSONObject;

/**
 *
 * @author bytedesk.com
 */
public class ThreadEvent {

    private JSONObject mJsonObject;

    public ThreadEvent(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return mJsonObject;
    }

    public void setJsonObject(JSONObject mJsonObject) {
        this.mJsonObject = mJsonObject;
    }
}
