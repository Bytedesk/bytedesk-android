package com.bytedesk.core.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedesk.core.room.entity.GroupEntity;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
@Dao
public interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGroup(GroupEntity groupEntity);

    @Query("SELECT * FROM groups WHERE current_uid = :currentUid")
    LiveData<List<GroupEntity>> loadGroups(String currentUid);

    @Query("SELECT * FROM groups WHERE current_uid = :currentUid")
    List<GroupEntity> loadGroupList(String currentUid);

    @Query("SELECT * FROM groups WHERE nickname like :search and current_uid = :currentUid")
    LiveData<List<GroupEntity>> searchGroups(String search, String currentUid);

    @Query("DELETE FROM groups where id = :id")
    void deleteGroup(Long id);

    @Query("DELETE FROM groups where gid = :gid")
    void deleteGroup(String gid);

    @Query("DELETE FROM groups")
    void deleteAllGroups();

}
