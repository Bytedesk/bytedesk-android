package com.bytedesk.core.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author bytedesk.com on 2019/3/28
 */
@Entity(tableName = "subscription")
public class SubscriptionEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;






}
