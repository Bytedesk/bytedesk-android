package com.bytedesk.im.core.callback;

import org.json.JSONObject;

/**
 * @author bytedesk.com on 2017/9/21.
 */

public interface BaseCallback {

    void onSuccess(JSONObject object);
    void onError(JSONObject object);

//    void onSuccess(JsonResult object);
//    void onError(JsonResult object);
}
