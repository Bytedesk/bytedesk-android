package com.bytedesk.core.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author bytedesk.com on 2019/3/28
 */
@Entity(tableName = "category")
public class CategoryEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @ColumnInfo(name = "cid")
    private String cid;

    /**
     * 类别名称
     */
    @ColumnInfo(name = "name")
    private String name;


    public CategoryEntity() {

    }

    public CategoryEntity(JSONObject jsonObject) {

        try {

            this.cid = jsonObject.getString("cid");
            this.name = jsonObject.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
