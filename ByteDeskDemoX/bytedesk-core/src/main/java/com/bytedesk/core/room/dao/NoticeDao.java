package com.bytedesk.core.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedesk.core.room.entity.NoticeEntity;

import java.util.List;

/**
 * @author bytedesk.com on 2019/3/1
 */
@Dao
public interface NoticeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotice(NoticeEntity noticeEntity);

    @Query("SELECT * FROM notice WHERE current_uid = :currentUid")
    LiveData<List<NoticeEntity>> loadNotices(String currentUid);

    @Query("SELECT * FROM notice WHERE current_uid = :currentUid")
    List<NoticeEntity> loadNoticeList(String currentUid);

    @Query("SELECT * FROM NOTICE WHERE title like :search and current_uid = :currentUid")
    LiveData<List<NoticeEntity>> searchNotices(String search, String currentUid);

    @Query("DELETE FROM notice where id = :id")
    void deleteNotice(Long id);

    @Query("DELETE FROM notice where nid = :nid")
    void deleteNotice(String nid);

    @Query("DELETE FROM notice")
    void deleteAllNotices();
}
