package com.bytedesk.core.room.entity;

/**
 * 暂不入库
 */
//@Entity(tableName = "feedback", indices = {@Index("feedback_fid")})
public class FeedbackEntity {

    /**
     *
     */
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    private String fid;

    /**
     * 内容
     */
    private String content;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 附件url
     */
    private String fileUrl;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 来源客户端
     */
    private String client;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
