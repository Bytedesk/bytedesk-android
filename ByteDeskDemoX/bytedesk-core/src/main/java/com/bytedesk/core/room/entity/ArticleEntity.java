package com.bytedesk.core.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author bytedesk.com on 2019/3/28
 */
@Entity(tableName = "article")
public class ArticleEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @ColumnInfo(name = "aid")
    private String aid;

    /**
     * 标题
     */
    @ColumnInfo(name = "title")
    private String title;

    /**
     * 标题
     */
    @ColumnInfo(name = "content")
    private String content;

    /**
     * 是否有用
     */
    @ColumnInfo(name = "rate_helpful")
    private int rateHelpful;

    /**
     * 无用
     */
    @ColumnInfo(name = "rate_useless")
    private int rateUseless;

    /**
     * 阅读次数
     */
    @ColumnInfo(name = "read_count")
    private int readCount;

    /**
     * 推荐
     */
    @ColumnInfo(name = "is_recommend")
    private boolean recommend;

    /**
     * 置顶
     */
    @ColumnInfo(name = "is_top")
    private boolean top;

    /**
     * 类型
     */
    @ColumnInfo(name = "type")
    private String type;

    /**
     * 创建时间
     */
    @ColumnInfo(name = "created_at")
    private String createdAt;

    /**
     * 更新时间
     */
    @ColumnInfo(name = "updated_at")
    private String updatedAt;


    public ArticleEntity() {
    }

    public ArticleEntity(JSONObject jsonObject) {

        try {

            this.aid = jsonObject.getString("aid");
            this.title = jsonObject.getString("title");
            this.content = jsonObject.getString("content");
            this.rateHelpful = jsonObject.getInt("rateHelpful");
            this.rateUseless = jsonObject.getInt("rateUseless");
            this.readCount = jsonObject.getInt("readCount");
            this.recommend = jsonObject.getBoolean("recommend");
            this.top = jsonObject.getBoolean("top");
            this.type = jsonObject.getString("type");
            this.createdAt = jsonObject.getString("createdAt");
            this.updatedAt = jsonObject.getString("updatedAt");

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

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRateHelpful() {
        return rateHelpful;
    }

    public void setRateHelpful(int rateHelpful) {
        this.rateHelpful = rateHelpful;
    }

    public int getRateUseless() {
        return rateUseless;
    }

    public void setRateUseless(int rateUseless) {
        this.rateUseless = rateUseless;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


}
