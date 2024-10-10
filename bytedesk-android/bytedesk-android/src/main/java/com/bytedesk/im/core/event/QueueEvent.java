package com.bytedesk.im.core.event;

import org.json.JSONObject;

/**
 *
 * @author bytedesk.com
 */
public class QueueEvent {


    private JSONObject mJsonObject;

    public QueueEvent(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return mJsonObject;
    }

    public void setJsonObject(JSONObject mJsonObject) {
        this.mJsonObject = mJsonObject;
    }


}
