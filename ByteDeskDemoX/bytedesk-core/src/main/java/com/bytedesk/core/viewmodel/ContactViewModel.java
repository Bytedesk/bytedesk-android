package com.bytedesk.core.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.bytedesk.core.repository.BDRepository;
import com.bytedesk.core.room.entity.ContactEntity;

import org.json.JSONObject;

import java.util.List;

/**
 *
 * @author bytedesk.com
 */
public class ContactViewModel extends AndroidViewModel {

    private BDRepository mBDRepository;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        mBDRepository = BDRepository.getInstance(application);
    }

    public LiveData<List<ContactEntity>> getContacts() {
        return mBDRepository.getContacts();
    }

    public LiveData<List<ContactEntity>> searchContacts(String search) {
        return mBDRepository.searchContacts(search);
    }

    public void insertContactJson(JSONObject contact) {
        mBDRepository.insertContactJson(contact);
    }

    public void insertContactEntity(ContactEntity contactEntity) {
        mBDRepository.insertContactEntity(contactEntity);
    }

    public void deleteContactEntity(ContactEntity contactEntity) {
        mBDRepository.deleteContactEntity(contactEntity);
    }


}
