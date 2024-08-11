package com.bytedesk.core.room.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author bytedesk.com on 2020/9/25
 */
class CuwEntity {

    /**
     *
     */
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    private String cid;

    /**
     *
     */
    private String name;

    /**
     * 内容
     */
    private String content;


    private Long categoryId;


    public CuwEntity(JSONObject jsonObject) {

        try {

            this.id = jsonObject.getLong("id");
            this.name = jsonObject.getString("name");
            this.content = jsonObject.getString("content");
            this.cid = jsonObject.getString("cid");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
