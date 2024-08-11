package com.bytedesk.core.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedesk.core.room.entity.MessageEntity;

import java.util.List;

/**
 * https://developer.android.com/topic/libraries/architecture/room.html#daos
 *
 * Data Access Objects (DAOs)
 *
 * 默认禁止在主线程访问database，因容易造成UI界面卡顿较长时间，但是livedata或者RxJava的异步查询例外
 *
 * @author bytedesk.com
 */
@Dao
public interface MessageDao {

    /**
     *
     * @param messageEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(MessageEntity messageEntity);

    // 判断某条记录是否存在
    @Query("select 1 from message where mid = :mid limit 1")
    int existMessage(String mid);

    /**
     * 访客端接口：根据工作组wid
     * 工作组会话
     * 
     * @param currentUid
     * @param wId
     * @return
     */
    @Query("SELECT * FROM message WHERE current_uid = :currentUid and wid = :wId order by created_at asc")
    LiveData<List<MessageEntity>> loadWorkGroupMessages(String currentUid, String wId);

    /**
     * 访客端接口：指定坐席会话
     * FIXME: 通过uid仅能够加载指定坐席的消息，暂未考虑转接会话的情况, 暂是使用loadThreadMessages替代
     *
     * @param currentUid
     * @param uId
     * @return
     */
    @Query("SELECT * FROM message WHERE current_uid = :currentUid and uid = :uId order by created_at asc")
    LiveData<List<MessageEntity>> loadAppointMessages(String currentUid, String uId);

    /**
     * 访客端接口：根据会话tid加载聊天记录
     *
     * @param currentUid
     * @param tId
     * @return
     */
    @Query("SELECT * FROM message WHERE current_uid = :currentUid and thread_tid = :tId order by created_at asc")
    LiveData<List<MessageEntity>> loadThreadMessages(String currentUid, String tId);

    /**
     * 客服端接口：根据访客uid查询
     *
     * @param currentUid
     * @param uId
     * @return
     */
    @Query("SELECT * FROM message WHERE current_uid = :currentUid and visitor_uid = :uId order by created_at asc")
    LiveData<List<MessageEntity>> loadVisitorMessages(String currentUid, String uId);

    /**
     * 客服端接口：联系人一对一会话消息
     * @param currentUid
     * @param uId
     * @return
     */
    @Query("SELECT * FROM message WHERE current_uid = :currentUid and cid = :uId order by created_at asc")
    LiveData<List<MessageEntity>> loadContactMessages(String currentUid, String uId);

    /**
     * 客服端接口：群组会话消息
     *
     * @param currentUid
     * @param gId
     * @return
     */
    @Query("SELECT * FROM message WHERE current_uid = :currentUid and gid = :gId order by created_at asc")
    LiveData<List<MessageEntity>> loadGroupMessages(String currentUid, String gId);


    @Query("SELECT * FROM message WHERE current_uid = :currentUid and local_id = :localId order by created_at asc")
    List<MessageEntity> loadLocalMessages(String currentUid, String localId);


    @Query("SELECT * FROM message where mid = :mid")
    MessageEntity getMessage(String mid);

//   UPDATE message SET id = 1234, mid = '123', status = 'sending' WHERE mid = '201904032155121' AND NOT EXISTS (SELECT id FROM (select * from message) as msg2 WHERE msg2.id = 1234)
//    FIXME: SQLiteConstraintException: UNIQUE constraint failed: message.id (code 1555)
    // select id, mid, local_id, status, content from message
    // (local_id = :localId and (:id not in (SELECT id FROM message)))
//    @Query("UPDATE message SET id = :id, mid = :mid, status = :status WHERE local_id = :localId")
//    @Query("UPDATE message SET id = :id, mid = :mid, status = :status WHERE local_id = :localId AND NOT EXISTS (SELECT id FROM (select * from message) as msg2 WHERE msg2.id = :id)")
//    int updateMessage(String localId, Long id, String mid, String status);

//    @Query("UPDATE message SET mid = :mid, status = :status WHERE local_id = :localId")
//    int updateMessage(String localId, String mid, String status);

    @Query("UPDATE message SET status = :status WHERE local_id = :localId and status != 'read'")
    int updateMessageStatus(String localId, String status);

    //  and status != 'read'
    @Query("UPDATE message SET status = :status WHERE mid = :mId")
    int updateMessageStatusMid(String mId, String status);

    @Query("UPDATE message SET is_mark_deleted = 1 WHERE mid = :mId")
    int markDeletedMessage(String mId);

    /**
     * 通过mId判断某条消息是否已经存在
     * @param mId
     * @return
     */
    @Query("SELECT count(*) FROM message where mid = :mId")
    int countMessageByMid(String mId);

    // TODO: markClearThreadMessage

    /**
     *
     * @param mid
     */
//    @Query("DELETE FROM message where id = :id")
//    void deleteMessage(Long id);

//    @Query("DELETE FROM message where mid = :mid")
//    void deleteMessage(String mid);

    /**
     *
     * @param localId
     */
    @Query("DELETE FROM message where local_id = :localId")
    void deleteMessage(String localId);


    @Query("DELETE FROM message where mid = :mid")
    void deleteMessageMid(String mid);

    /**
     *
     * @param tid
     */
    @Query("DELETE FROM message where thread_tid = :tid")
    void deleteThreadMessages(String tid);

    /**
     *
     * @param cid
     */
    @Query("DELETE FROM message where cid = :cid")
    void deleteContactMessages(String cid);

    /**
     *
     * @param gid
     */
    @Query("DELETE FROM message where gid = :gid")
    void deleteGroupMessages(String gid);

    /**
     * 清空消息表
     */
    @Query("DELETE FROM message")
    void deleteAllMessages();

}


