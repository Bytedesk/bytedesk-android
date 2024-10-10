package com.bytedesk.im.core.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedesk.im.core.room.entity.ThreadEntity;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
@Dao
public interface ThreadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertThread(ThreadEntity threadEntity);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertThreadAbort(ThreadEntity threadEntity);

    // 判断某条记录是否存在
    @Query("select 1 from thread where topic = :topic limit 1")
    int existThread(String topic);

    @Query("SELECT * FROM thread WHERE uid = :uid")
    ThreadEntity getThread(String uid);

//    @Query("DELETE FROM thread where id = :id")
//    void deleteThread(Long id);

    @Query("DELETE FROM thread where topic = :topic")
    void deleteThread(String topic);


    @Query("DELETE FROM thread")
    void deleteAllThreads();

}
