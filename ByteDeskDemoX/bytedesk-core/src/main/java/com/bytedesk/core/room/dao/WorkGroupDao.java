package com.bytedesk.core.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedesk.core.room.entity.WorkGroupEntity;

import java.util.List;


/**
 *
 * @author bytedesk.com
 */
@Dao
public interface WorkGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWorkGroup(WorkGroupEntity workGroupEntity);

    @Query("SELECT * FROM workGroup WHERE current_uid = :currentUid")
    LiveData<List<WorkGroupEntity>> loadWorkGroups(String currentUid);

    @Query("SELECT * FROM workGroup WHERE current_uid = :currentUid")
    List<WorkGroupEntity> loadWorkGroupList(String currentUid);

    @Query("DELETE FROM workGroup where id = :id")
    void deleteWorkGroup(Long id);

    @Query("DELETE FROM workGroup")
    void deleteAllWorkGroups();
}
