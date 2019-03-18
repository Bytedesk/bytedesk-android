package com.bytedesk.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bytedesk.core.api.BDCoreApi;
import com.bytedesk.core.callback.BaseCallback;
import com.bytedesk.core.room.entity.ContactEntity;
import com.bytedesk.core.viewmodel.ContactViewModel;
import com.bytedesk.ui.R;
import com.bytedesk.ui.adapter.SelectAdapter;
import com.bytedesk.ui.listener.ContactItemClickListener;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ContactSelectActivity extends AppCompatActivity implements ContactItemClickListener {

    private QMUITopBarLayout mTopBar;
    private QMUIPullRefreshLayout mPullRefreshLayout;
    private RecyclerView mRecyclerView;

    private SelectAdapter mSelectAdapter;
    private ContactViewModel mContactViewModel;
    private List<ContactEntity> mContactEntities;
    private List<ContactEntity> mSelectedEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bytedesk_activity_contact_select);
        //
        initTopBar();
        initRecycleView();
        initModel();
    }

    private void initTopBar() {
        //
        mTopBar = findViewById(R.id.bytedesk_contact_topbar);
        mTopBar.setTitle("邀请人");
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
        QMUIStatusBarHelper.translucent(this);
    }

    /**
     * 界面初始化
     */
    private void initRecycleView () {
        //
        mPullRefreshLayout = findViewById(R.id.bytedesk_contact_pulltorefresh);
        mPullRefreshLayout.setOnPullListener(pullListener);

        // TODO: 增加点击聊天界面，去除输入框焦点，让其缩回底部
        // 初始化
        mRecyclerView = findViewById(R.id.bytedesk_contact_fragment_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // 设置适配器adapter
        mSelectAdapter = new SelectAdapter(this);
        mRecyclerView.setAdapter(mSelectAdapter);
    }

    /**
     *
     */
    private void initModel() {
        //
        mContactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        mContactViewModel.getContacts().observe(this, new Observer<List<ContactEntity>>() {
            @Override
            public void onChanged(@Nullable List<ContactEntity> contactEntities) {
                //
                mContactEntities = contactEntities;
                mSelectAdapter.setContacts(mContactEntities);
            }
        });
    }

    /**
     *
     * @param position
     */
    @Override
    public void onItemClicked(int position) {
        Logger.i("contact item clicked: " + position);

    }

    /**
     * 加载联系人
     */
    private void getContacts() {
        Logger.i("getContacts");

        BDCoreApi.getContacts(this, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                try {
                    JSONArray jsonArray = object.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        mContactViewModel.insertContactJson(jsonArray.getJSONObject(i));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mPullRefreshLayout.finishRefresh();
            }

            @Override
            public void onError(JSONObject object) {

                try {
                    Logger.e(object.getString("message") +":"+ object.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mPullRefreshLayout.finishRefresh();
            }
        });
    }

    /**
     * 下拉刷新
     */
    private QMUIPullRefreshLayout.OnPullListener pullListener = new QMUIPullRefreshLayout.OnPullListener() {

        @Override
        public void onMoveTarget(int offset) {

        }

        @Override
        public void onMoveRefreshView(int offset) {

        }

        @Override
        public void onRefresh() {
            Logger.i("refresh");
            getContacts();
        }
    };


}
