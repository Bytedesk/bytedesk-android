package com.bytedesk.demo.im.fragment.notice;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.bytedesk.core.event.MessageEvent;
import com.bytedesk.demo.R;
import com.bytedesk.demo.common.BaseFragment;
import com.bytedesk.demo.common.ListViewDecoration;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author bytedesk.com on 2018/3/26.
 *
 * TODO: 暂未上线
 *
 */
public class NoticeFragment extends BaseFragment implements SwipeItemClickListener {

    @BindView(R.id.topbar) QMUITopBarLayout mTopBar;
    @BindView(R.id.emptyView) QMUIEmptyView mEmptyView;
    @BindView(R.id.pull_to_refresh) QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.recycler_view) SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_queue, null);
        ButterKnife.bind(this, root);

        initTopBar();
        initRecycleView();
        initModel();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     *
     */
    protected void initTopBar() {
        //
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        //
        mTopBar.setTitle(getResources().getString(R.string.bytedesk_notice));
    }

    /**
     *
     */
    private void initRecycleView() {
        //
        mPullRefreshLayout.setOnPullListener(pullListener);
        //
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration(getContext()));// 添加分割线。
        mSwipeMenuRecyclerView.setSwipeItemClickListener(this); //
    }

    /**
     *
     */
    private void initModel() {

    }

    /**
     * TODO: 加载通知
     */
    private void getNotices() {
        Logger.d("getNotices");

    }

    @Override
    public void onItemClick(View itemView, int position) {
        // SwipeItemClickListener
        Logger.d("item clicked");

    }

    /**
     * 监听 EventBus 广播消息
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {

        try {
            JSONObject jsonObject = new JSONObject(messageEvent.getContent());

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            //
            getNotices();
        }
    };


}
