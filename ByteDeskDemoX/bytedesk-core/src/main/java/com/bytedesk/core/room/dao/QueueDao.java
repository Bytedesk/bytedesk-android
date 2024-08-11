package com.bytedesk.core.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedesk.core.room.entity.QueueEntity;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
@Dao
public interface QueueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQueue(QueueEntity queueEntity);

    @Query("SELECT * FROM queue WHERE current_uid = :currentUid")
    LiveData<List<QueueEntity>> loadQueues(String currentUid);

    @Query("SELECT * FROM queue WHERE nickname like :search and current_uid = :currentUid")
    LiveData<List<QueueEntity>> searchQueues(String search, String currentUid);

    @Query("DELETE FROM queue where id = :id")
    void deleteQueue(Long id);

    @Query("DELETE FROM queue")
    void deleteAllQueues();
}
