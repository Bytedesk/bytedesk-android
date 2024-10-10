package com.bytedesk.im.core.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.bytedesk.im.core.repository.BDRepository;
import com.bytedesk.im.core.room.entity.MessageEntity;

import org.json.JSONObject;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {

    private final BDRepository mBDRepository;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        mBDRepository = BDRepository.getInstance(application);
    }

    public LiveData<List<MessageEntity>> getThreadMessages(String threadTopic) {
        return mBDRepository.getThreadMessages(threadTopic);
    }

    public void insertMessageJson(JSONObject message) {
        mBDRepository.insertMessageJson(message);
    }

//    public void insertMessageEntity(MessageEntity messageEntity) {
//        mBDRepository.insertMessageEntity(messageEntity);
//    }
//
//    public void deleteMessageEntity(MessageEntity messageEntity) {
//        mBDRepository.deleteMessageEntity(messageEntity);
//    }




}
