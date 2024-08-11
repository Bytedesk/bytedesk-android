package com.bytedesk.core.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.bytedesk.core.repository.BDRepository;
import com.bytedesk.core.room.entity.WorkGroupEntity;

import org.json.JSONObject;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
public class WorkGroupViewModel extends AndroidViewModel {

    private BDRepository mBDRepository;

    public WorkGroupViewModel(@NonNull Application application) {
        super(application);
        mBDRepository = BDRepository.getInstance(application);
    }

    public LiveData<List<WorkGroupEntity>> getWorkGroups() {
        return mBDRepository.getWorkGroups();
    }

    public void insertWorkGroupJson(JSONObject workGroup) {
        //
        mBDRepository.insertWorkGroupJson(workGroup);
    }





}
