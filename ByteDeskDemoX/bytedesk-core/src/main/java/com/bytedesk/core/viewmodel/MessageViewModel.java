package com.bytedesk.core.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.bytedesk.core.repository.BDRepository;
import com.bytedesk.core.room.entity.MessageEntity;

import org.json.JSONObject;

import java.util.List;

/**
 * @author bytedesk.com on 2017/9/13.
 *
 * https://developer.android.com/topic/libraries/architecture/viewmodel.html
 *
 * Best practices for Lifecycles
 *  https://developer.android.com/topic/libraries/architecture/lifecycle.html#lc-bp
 *
 *  1. Keep your UI controllers (activities and fragments) as lean as possible.
 *      They should not try to acquire their own data; instead, use a ViewModel to do that,
 *      and observe the LiveData to reflect the changes back to the views.
 *
 *  2. Try to write data-driven UIs where your UI controller’s responsibility is to update
 *      the views as data changes, or notify user actions back to the ViewModel.
 *
 *  3. Put your data logic in your ViewModel class. ViewModel should serve as the connector
 *      between your UI controller and the rest of your application. Be careful though,
 *      it is not ViewModel's responsibility to fetch data (for example, from a network).
 *      Instead, ViewModel should call the appropriate component to do that work, then provide
 *      the result back to the UI controller.
 *
 *  4. Use AdminInitResultData Binding to maintain a clean interface between your views and the UI controller.
 *      This allows you to make your views more declarative and minimize the update code you
 *      need to write in your activities and fragments. If you prefer to do this in Java,
 *      use a library like Butter Knife to avoid boilerplate code and have a better abstraction.
 *      注：
 *          AdminInitResultData Binding Library：
 *          https://developer.android.com/topic/libraries/data-binding/index.html
 *
 *  5. If your UI is complex, consider creating a Presenter class to handle UI modifications.
 *      This is usually overkill, but might make your UIs easier to test.
 *
 *  6. Never reference a View or Activity context in your ViewModel. If the ViewModel outlives
 *      the activity (in case of configuration changes), your activity will be leaked and not
 *      properly garbage-collected.
 *
 * @author bytedesk.com
 */

public class MessageViewModel extends AndroidViewModel {

    private BDRepository mBDRepository;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        mBDRepository = BDRepository.getInstance(application);
    }

    public LiveData<List<MessageEntity>> getWorkGroupMessages(String wId) {
        return mBDRepository.getWorkGroupMessages(wId);
    }

    public LiveData<List<MessageEntity>> getThreadMessages(String tId) {
        return mBDRepository.getThreadMessages(tId);
    }

    public LiveData<List<MessageEntity>> getVisitorMessages(String uId) {
        return mBDRepository.getVisitorMessages(uId);
    }

    public LiveData<List<MessageEntity>> getContactMessages(String cId) {
        return mBDRepository.getContactMessages(cId);
    }

    public LiveData<List<MessageEntity>> getGroupMessages(String gId) {
        return mBDRepository.getGroupMessages(gId);
    }

    public void insertMessageJson(JSONObject message) {
        mBDRepository.insertMessageJson(message);
    }

    public void insertMessageEntity(MessageEntity messageEntity) {
        mBDRepository.insertMessageEntity(messageEntity);
    }

    public void deleteMessageEntity(MessageEntity messageEntity) {
        mBDRepository.deleteMessageEntity(messageEntity);
    }




}
