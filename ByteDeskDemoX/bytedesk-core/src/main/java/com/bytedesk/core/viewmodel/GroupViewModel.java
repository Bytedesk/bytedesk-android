package com.bytedesk.core.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.bytedesk.core.repository.BDRepository;
import com.bytedesk.core.room.entity.GroupEntity;

import org.json.JSONObject;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
public class GroupViewModel extends AndroidViewModel {

    private BDRepository mBDRepository;

    public GroupViewModel(@NonNull Application application) {
        super(application);
        mBDRepository = BDRepository.getInstance(application);
    }

    public LiveData<List<GroupEntity>> getGroups() {
        return mBDRepository.getGroups();
    }

    public LiveData<List<GroupEntity>> searchGroups(String search) {
        return mBDRepository.searchGroups(search);
    }

    public void insertGroupJson(JSONObject group) {
        mBDRepository.insertGroupJson(group);
    }

    public void insertGroupEntity(GroupEntity groupEntity) {
        mBDRepository.insertGroupEntity(groupEntity);
    }

    public void deleteGroupEntity(GroupEntity groupEntity) {
        mBDRepository.deleteGroupEntity(groupEntity);
    }



}
