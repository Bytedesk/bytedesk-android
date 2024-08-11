package com.bytedesk.core.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedesk.core.room.entity.ContactEntity;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(ContactEntity contactEntity);

    @Query("SELECT * FROM contact WHERE current_uid = :currentUid")
    LiveData<List<ContactEntity>> loadContacts(String currentUid);

    @Query("SELECT * FROM contact WHERE nickname like :search and current_uid = :currentUid")
    LiveData<List<ContactEntity>> searchContacts(String search, String currentUid);

    @Query("DELETE FROM contact where id = :id")
    void deleteContact(Long id);

    @Query("DELETE FROM contact where uid = :uid")
    void deleteContact(String uid);

    @Query("DELETE FROM contact")
    void deleteAllContacts();

}
