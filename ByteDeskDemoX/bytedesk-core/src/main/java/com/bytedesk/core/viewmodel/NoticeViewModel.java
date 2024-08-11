package com.bytedesk.core.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.bytedesk.core.repository.BDRepository;
import com.bytedesk.core.room.entity.NoticeEntity;

import org.json.JSONObject;

import java.util.List;

/**
 * @author bytedesk.com on 2019/3/1
 */
public class NoticeViewModel extends AndroidViewModel {

    private BDRepository mBDRepository;

    public NoticeViewModel(@NonNull Application application) {
        super(application);
        mBDRepository = BDRepository.getInstance(application);
    }

    public LiveData<List<NoticeEntity>> getNotices() {
        return mBDRepository.getNotices();
    }

    public LiveData<List<NoticeEntity>> searchNotices(String search) {
        return mBDRepository.searchNotices(search);
    }

    public void insertNoticeJson(JSONObject group) {
        mBDRepository.insertNoticeJson(group);
    }

    public void insertNoticeEntity(NoticeEntity noticeEntity) {
        mBDRepository.insertNoticeEntity(noticeEntity);
    }

    public void deleteNoticeEntity(NoticeEntity noticeEntity) {
        mBDRepository.deleteNoticeEntity(noticeEntity);
    }


}
