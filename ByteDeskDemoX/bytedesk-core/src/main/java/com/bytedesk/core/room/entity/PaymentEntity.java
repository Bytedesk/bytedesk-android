package com.bytedesk.core.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "payment", indices = {@Index("current_uid")})
public class PaymentEntity {

    /**
     * TODO: 去除服务器端消息id，替换
     */
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @ColumnInfo(name = "pid")
    private String pid;

    /**
     *
     */
    @ColumnInfo(name = "agentUsername")
    private String agentUsername;

    /**
     *
     */
    @ColumnInfo(name = "money")
    private int money;

    /**
     *
     */
    @ColumnInfo(name = "validateUntilDate")
    private String validateUntilDate;

    /**
     *
     */
    @ColumnInfo(name = "createdAt")
    private String createdAt;

    /**
     *
     */
    @ColumnInfo(name = "type")
    private String type;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }

    public String getValidateUntilDate() {
        return validateUntilDate;
    }

    public void setValidateUntilDate(String validateUntilDate) {
        this.validateUntilDate = validateUntilDate;
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

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

}
