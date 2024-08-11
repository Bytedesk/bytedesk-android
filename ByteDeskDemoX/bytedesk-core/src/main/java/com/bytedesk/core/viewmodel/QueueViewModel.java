package com.bytedesk.core.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bytedesk.core.repository.BDRepository;
import com.bytedesk.core.room.entity.QueueEntity;

import org.json.JSONObject;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
public class QueueViewModel extends AndroidViewModel {

    private BDRepository mBDRepository;

    public QueueViewModel(@NonNull Application application) {
        super(application);
        mBDRepository = BDRepository.getInstance(application);
    }

    public LiveData<List<QueueEntity>> getQueues() {
        return mBDRepository.getQueues();
    }

    public LiveData<List<QueueEntity>> searchQueues(String search) {
        return mBDRepository.searchQueues(search);
    }

    public void insertQueueJson(JSONObject queue) {
        mBDRepository.insertQueueJson(queue);
    }

    public void deleteQueueEntity(QueueEntity queueEntity) {
        mBDRepository.deleteQueueEntity(queueEntity);
    }


}
