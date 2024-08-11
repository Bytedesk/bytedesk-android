package com.bytedesk.core.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.bytedesk.core.room.entity.ConnectionEntity;

/**
 * 多账号管理
 * @author bytedesk.com on 2019/3/28
 */
@Dao
public interface ConnectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConnection(ConnectionEntity connectionEntity);





}
