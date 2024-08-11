package com.bytedesk.core.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author bytedesk.com on 2019/3/1
 */
@Entity(tableName = "notice", indices = {@Index("current_uid")})
public class NoticeEntity {

    /**
     * 通知表:
     * TODO: 去除服务器端消息id，替换
     */
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;


    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @ColumnInfo(name = "nid")
    private String nid;

    /**
     * 标题
     */
    @ColumnInfo(name = "title")
    private String title;

    /**
     * 内容
     * 注意：content为Oracle关键字
     * , ColumnInfoDefinition=" COMMENT '通知内容'"
     */
    @ColumnInfo(name = "content")
    private String content;

    /**
     * 类型：针对个人通知、群组通知
     */
    @ColumnInfo(name = "by_type")
    private String type;

    /**
     * 是否已经被处理
     */
    @ColumnInfo(name = "is_processed")
    private boolean processed;


    @ColumnInfo(name = "git")
    private String gid;

    /**
     * 创建者
     */
    @ColumnInfo(name = "user_uid")
    private String userUid;

    /**
     * 当前登录用户Uid
     */
    @ColumnInfo(name = "current_uid")
    private String currentUid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }
}
