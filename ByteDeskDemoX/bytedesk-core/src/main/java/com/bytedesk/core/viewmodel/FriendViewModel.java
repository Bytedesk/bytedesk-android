package com.bytedesk.core.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.bytedesk.core.repository.BDRepository;
import com.bytedesk.core.room.entity.FriendEntity;

import org.json.JSONObject;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
public class FriendViewModel extends AndroidViewModel {

    private BDRepository mBDRepository;

    public FriendViewModel(@NonNull Application application) {
        super(application);
        mBDRepository = BDRepository.getInstance(application);
    }

    public LiveData<List<FriendEntity>> getFriends() {
        return mBDRepository.getFriends();
    }

    public LiveData<List<FriendEntity>> searchFriends(String search) {
        return mBDRepository.searchFriends(search);
    }

    public void insertFriendJson(JSONObject friend) {
        mBDRepository.insertFriendJson(friend);
    }


    public void insertFriendEntity(FriendEntity friendEntity) {
        mBDRepository.insertFriendEntity(friendEntity);
    }

    public void deleteFriendEntity(FriendEntity friendEntity) {
        mBDRepository.deleteFriendEntity(friendEntity);
    }

}
