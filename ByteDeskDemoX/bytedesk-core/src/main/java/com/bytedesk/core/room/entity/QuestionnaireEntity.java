package com.bytedesk.core.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author bytedesk.com on 2019/2/20
 */
@Entity(tableName = "questionnaire")
public class QuestionnaireEntity {

    /**
     * 服务器消息id
     */
    @PrimaryKey
    @ColumnInfo(name = "id")
    public Long id;





}
