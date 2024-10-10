package com.bytedesk.im.core.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedesk.im.core.room.entity.MessageEntity;

import java.util.List;

/**
 * <a href="https://developer.android.com/topic/libraries/architecture/room.html#daos">...</a>
 * Data Access Objects (DAOs)
 * 默认禁止在主线程访问database，因容易造成UI界面卡顿较长时间，但是livedata或者RxJava的异步查询例外
 *
 * @author bytedesk.com
 */
@Dao
public interface MessageDao {

    /**
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(MessageEntity messageEntity);

    // 判断某条记录是否存在
    @Query("select 1 from message where uid = :uid limit 1")
    int existMessage(String uid);

    /**
     * 访客端接口：根据会话topic加载聊天记录
     */
    @Query("SELECT * FROM message WHERE thread_topic = :topic order by created_at asc")
    LiveData<List<MessageEntity>> loadThreadMessages(String topic);

    @Query("SELECT * FROM message WHERE current_uid = :currentUid and local_id = :localId order by created_at asc")
    List<MessageEntity> loadLocalMessages(String currentUid, String localId);

    @Query("SELECT * FROM message where uid = :uid")
    MessageEntity getMessage(String uid);

    @Query("UPDATE message SET status = :status WHERE local_id = :localId and status != 'read'")
    int updateMessageStatus(String localId, String status);

    //  and status != 'read'
    @Query("UPDATE message SET status = :status WHERE uid = :uId")
    int updateMessageStatusMid(String uId, String status);

    /**
     * 通过uId判断某条消息是否已经存在
     */
    @Query("SELECT count(*) FROM message where uid = :uId")
    int countMessageByMid(String uId);

    /**
     */
    @Query("DELETE FROM message where local_id = :localId")
    void deleteMessage(String localId);


    @Query("DELETE FROM message where uid = :uid")
    void deleteMessageUid(String uid);

    /**
     * 清空消息表
     */
    @Query("DELETE FROM message")
    void deleteAllMessages();

}


