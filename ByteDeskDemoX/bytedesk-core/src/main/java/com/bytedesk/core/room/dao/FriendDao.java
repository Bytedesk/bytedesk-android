package com.bytedesk.core.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedesk.core.room.entity.FriendEntity;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
@Dao
public interface FriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFriend(FriendEntity friendEntity);

    @Query("SELECT * FROM friend WHERE current_uid = :currentUid")
    LiveData<List<FriendEntity>> loadFriends(String currentUid);

    @Query("SELECT * FROM friend WHERE nickname like :search and current_uid = :currentUid")
    LiveData<List<FriendEntity>> searchFriends(String search, String currentUid);

    @Query("DELETE FROM friend where id = :id")
    void deleteFriend(Long id);

    @Query("DELETE FROM friend where uid = :uid")
    void deleteFriend(String uid);

    @Query("DELETE FROM friend")
    void deleteAllFriends();

}
