package com.bytedesk.im.core.event;

import org.json.JSONObject;

/**
 *
 * @author bytedesk.com
 */
public class ProfileEvent {

    private JSONObject mJsonObject;

    public ProfileEvent(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return mJsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.mJsonObject = jsonObject;
    }

}
