package com.bytedesk.core.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedesk.core.room.entity.ThreadEntity;

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

    @Query("SELECT * FROM thread WHERE is_processing =:processing and current_uid = :currentUid And is_closed = 0 AND is_mark_deleted != 1 ORDER BY is_mark_top DESC, timestamp DESC")
    LiveData<List<ThreadEntity>> loadProcessingThreads(boolean processing, String currentUid);

    @Query("SELECT * FROM thread WHERE is_processing =:processing and current_uid = :currentUid And is_closed = 0 AND is_mark_deleted != 1 ORDER BY is_mark_top DESC, timestamp DESC")
    List<ThreadEntity> getProcessingThreads(boolean processing, String currentUid);

    @Query("SELECT * FROM thread WHERE current_uid = :currentUid AND is_mark_deleted != 1 ORDER BY is_mark_top DESC, timestamp DESC")
    LiveData<List<ThreadEntity>> loadThreads(String currentUid);

    @Query("SELECT * FROM thread WHERE current_uid = :currentUid and (type = 'contact' or type = 'group') AND is_mark_deleted != 1 ORDER BY is_mark_top DESC, timestamp DESC")
    LiveData<List<ThreadEntity>> loadIMThreads(String currentUid);

    @Query("SELECT * FROM thread WHERE current_uid = :currentUid and type = :type AND is_mark_deleted != 1 ORDER BY is_mark_top DESC, timestamp DESC")
    LiveData<List<ThreadEntity>> loadThreadsWithType(String currentUid, String type);

    @Query("SELECT * FROM thread WHERE current_uid = :currentUid and type = :type AND is_mark_deleted != 1 ORDER BY is_mark_top DESC, timestamp DESC")
    List<ThreadEntity> loadThreadListWithType(String currentUid, String type);

    @Query("SELECT * FROM thread WHERE nickname like :search and current_uid = :currentUid AND is_mark_deleted != 1 ORDER BY is_mark_top DESC, timestamp DESC")
    LiveData<List<ThreadEntity>> searchThreads(String search, String currentUid);

    @Query("SELECT * FROM thread WHERE tid = :tid")
    ThreadEntity getThread(String tid);

    @Query("UPDATE thread SET is_mark_top = 1 WHERE tid = :tId")
    int markTopThread(String tId);

    @Query("UPDATE thread SET is_mark_top = 0 WHERE tid = :tId")
    int unmarkTopThread(String tId);

    @Query("UPDATE thread SET is_mark_disturb = 1 WHERE tid = :tId")
    int markDisturbThread(String tId);

    @Query("UPDATE thread SET is_mark_disturb = 0 WHERE tid = :tId")
    int unmarkDisturbThread(String tId);

    @Query("UPDATE thread SET is_mark_unread = 1 WHERE tid = :tId")
    int markUnreadThread(String tId);

    @Query("UPDATE thread SET is_mark_unread = 0 WHERE tid = :tId")
    int unmarkUnreadThread(String tId);

    @Query("UPDATE thread SET unread_count = 0 WHERE tid = :tId")
    int clearUnreadCountThread(String tId);

    @Query("UPDATE thread SET is_mark_deleted = 1 WHERE tid = :tId")
    int markDeletedThread(String tId);



//    @Query("DELETE FROM thread where id = :id")
//    void deleteThread(Long id);

    @Query("DELETE FROM thread where topic = :topic")
    void deleteThread(String topic);

    @Query("DELETE FROM thread where visitor_uid = :visitorUid")
    void deleteThreadWithVisitor(String visitorUid);

    @Query("DELETE FROM thread where contact_uid = :contactUid")
    void deleteThreadWithContact(String contactUid);

    @Query("DELETE FROM thread where group_gid = :groupGid")
    void deleteThreadWithGroup(String groupGid);

    @Query("DELETE FROM thread")
    void deleteAllThreads();

}
