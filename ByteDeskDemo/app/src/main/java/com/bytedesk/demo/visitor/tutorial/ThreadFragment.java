package com.bytedesk.demo.visitor.tutorial;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.bytedesk.core.api.BDCoreApi;
import com.bytedesk.core.callback.BaseCallback;
import com.bytedesk.core.http.model.http.VisitorGetThreadsResult;
import com.bytedesk.demo.R;
import com.bytedesk.demo.common.BaseFragment;
import com.bytedesk.demo.common.ListViewDecoration;
import com.bytedesk.demo.visitor.adapter.ThreadAdapter;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ThreadFragment extends BaseFragment {

    private int page;

    @BindView(R.id.session_topbar) QMUITopBarLayout mTopBar;
    @BindView(R.id.session_refreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.session_recyclerview) RecyclerView mRecyclerView;

    private ThreadAdapter mThreadAdapter;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_thread, null);
        ButterKnife.bind(this, root);

        page = 0;

        initTopBar();
        initRecyclerView();

        return root;
    }

    private void initTopBar() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_color_blue));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        mTopBar.setTitle("会话历史记录接口");
    }


    private void initRecyclerView() {
        //
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getThreads();
            }
        });
        //
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mRecyclerView.addItemDecoration(new ListViewDecoration(getContext()));// 添加分割线。
        //
        mThreadAdapter = new ThreadAdapter(getContext());
        mRecyclerView.setAdapter(mThreadAdapter);
        //
        getThreads();
    }

    @Override
    public QMUIFragment.TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }


    private void getThreads() {

        //
        BDCoreApi.visitorGetThreads(getContext(), page, new BaseCallback() {
            @Override
            public void onSuccess(JSONObject object) {

                page++;


                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(JSONObject object) {

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }



}
