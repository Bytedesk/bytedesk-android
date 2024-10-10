package com.bytedesk.im.core.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bytedesk.im.core.repository.BDRepository;
import com.bytedesk.im.core.room.entity.ThreadEntity;

import org.json.JSONObject;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
public class ThreadViewModel extends AndroidViewModel {

    private BDRepository mBDRepository;

    public ThreadViewModel(@NonNull Application application) {
        super(application);
        mBDRepository = BDRepository.getInstance(application);
    }

//    public LiveData<List<ThreadEntity>> getThreads() {
//        return mBDRepository.getThreads();
//    }
//
//    public LiveData<List<ThreadEntity>> getIMThreads() {
//        return mBDRepository.getIMThreads();
//    }

//    public LiveData<List<ThreadEntity>> getThreadsWithType(String type) {
//        return mBDRepository.getThreadsWithType(type);
//    }

//    public LiveData<List<ThreadEntity>> searchThreads(String search) {
//        return mBDRepository.searchThreads(search);
//    }

    public void insertThreadJson(JSONObject thread) {
        mBDRepository.insertThreadJson(thread);
    }


    public void insertThreadEntity(ThreadEntity threadEntity) {
        mBDRepository.insertThreadEntity(threadEntity);
    }

    public void deleteThreadEntity(ThreadEntity threadEntity) {
        mBDRepository.deleteThreadEntity(threadEntity);
    }




}
