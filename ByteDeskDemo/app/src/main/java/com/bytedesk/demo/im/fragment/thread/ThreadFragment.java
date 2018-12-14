package com.bytedesk.demo.im.fragment.thread;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bytedesk.core.api.BDCoreApi;
import com.bytedesk.core.api.BDMqttApi;
import com.bytedesk.core.callback.BaseCallback;
import com.bytedesk.core.event.ConnectionEvent;
import com.bytedesk.core.event.ThreadEvent;
import com.bytedesk.core.room.entity.ThreadEntity;
import com.bytedesk.core.util.BDCoreConstant;
import com.bytedesk.core.viewmodel.ThreadViewModel;
import com.bytedesk.demo.R;
import com.bytedesk.demo.common.BaseFragment;
import com.bytedesk.demo.common.ListViewDecoration;
import com.bytedesk.demo.im.adapter.ThreadAdapter;
import com.bytedesk.ui.api.BDUiApi;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author bytedesk.com on 2018/3/26.
 *
 */
public class ThreadFragment extends BaseFragment implements SwipeItemClickListener {

    @BindView(R.id.bytedesk_thread_topbar) QMUITopBarLayout mTopBar;
    //    @BindView(R.id.emptyView) QMUIEmptyView mEmptyView;
    @BindView(R.id.pull_to_refresh) QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.bytedesk_search_edittext) EditText mSearchEditText;
    @BindView(R.id.recycler_view) SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private ThreadAdapter mThreadAdapter;
    private ThreadViewModel mThreadViewModel;
    private List<ThreadEntity> mThreadEntities;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_im_thread, null);
        ButterKnife.bind(this, root);

        EventBus.getDefault().register(this);

        initTopBar();
        initRecycleView();
        initModel();

        // 加载会话
        getThreads();

        return root;
    }

    /**
     * 初始化topbar
     */
    protected void initTopBar() {
        //
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        mTopBar.setTitle(getResources().getString(R.string.bytedesk_thread));
    }

    /**
     * 初始化recycleview
     */
    private void initRecycleView() {
        // 下拉刷新
        mPullRefreshLayout.setOnPullListener(pullListener);
        // 搜索框
        mSearchEditText.addTextChangedListener(textWatcher);
        //
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration(getContext()));// 添加分割线。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
        mSwipeMenuRecyclerView.setSwipeItemClickListener(this);
        //
        mThreadAdapter = new ThreadAdapter(getContext());
        mSwipeMenuRecyclerView.setAdapter(mThreadAdapter);
    }

    /**
     * model初始化
     */
    private void initModel() {
        //
        mThreadViewModel = ViewModelProviders.of(this).get(ThreadViewModel.class);
        //
        mThreadViewModel.getThreads().observe(this, new Observer<List<ThreadEntity>>() {
            @Override
            public void onChanged(@Nullable List<ThreadEntity> threadEntities) {
                mThreadEntities = threadEntities;
                mThreadAdapter.setThreads(threadEntities);
            }
        });
    }

    /**
     * 搜索初始化
     * @param search
     */
    private void searchModel(String search) {
        //
        mThreadViewModel.searchThreads(search).observe(this, new Observer<List<ThreadEntity>>() {
            @Override
            public void onChanged(@Nullable List<ThreadEntity> threadEntities) {
                mThreadEntities = threadEntities;
                mThreadAdapter.setThreads(threadEntities);
            }
        });
    }

    /**
     * 请求thread
     */
    private void getThreads() {
        Logger.i("getThreads");

        BDCoreApi.agentThreads(getContext(), new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                try {

                    JSONArray agentThreadJsonArray = object.getJSONObject("data").getJSONArray("agentThreads");
                    for (int i = 0; i < agentThreadJsonArray.length(); i++) {
                        mThreadViewModel.insertThreadJson(agentThreadJsonArray.getJSONObject(i));

                        // 添加订阅
                        String threadTopic = "thread/" + agentThreadJsonArray.getJSONObject(i).getString("tid");
                        BDMqttApi.subscribeTopic(getContext(), threadTopic);
                    }

                    //
                    JSONArray contactThreadJsonArray = object.getJSONObject("data").getJSONArray("contactThreads");
                    for (int i = 0; i < contactThreadJsonArray.length(); i++) {
                        mThreadViewModel.insertThreadJson(contactThreadJsonArray.getJSONObject(i));

                    }

                    //
                    JSONArray groupThreadJsonArray = object.getJSONObject("data").getJSONArray("groupThreads");
                    for (int i = 0; i < groupThreadJsonArray.length(); i++) {
                        mThreadViewModel.insertThreadJson(groupThreadJsonArray.getJSONObject(i));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mPullRefreshLayout.finishRefresh();
            }

            @Override
            public void onError(JSONObject object) {

                try {
                    Logger.e(object.getString("message") + object.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mPullRefreshLayout.finishRefresh();
            }

        });
    }

    /**
     * SwipeItemClickListener
     */
    @Override
    public void onItemClick(View itemView, int position) {
        //
        ThreadEntity threadEntity = mThreadEntities.get(position);
        Logger.d("thread item clicked " + position + " string: " + threadEntity.toString());

        if (threadEntity.getType().equals(BDCoreConstant.THREAD_TYPE_THREAD)) {

            BDUiApi.startThreadChatActivity(getContext(),
                    threadEntity.getTid(),
                    threadEntity.getVisitorUid(),
                    threadEntity.getNickname());

        } else if (threadEntity.getType().equals(BDCoreConstant.THREAD_TYPE_CONTACT)) {

            BDUiApi.startContactChatActivity(getContext(),
                    threadEntity.getContactUid(),
                    threadEntity.getNickname());

        } else if (threadEntity.getType().equals(BDCoreConstant.THREAD_TYPE_GROUP)) {

            BDUiApi.startGroupChatActivity(getContext(),
                    threadEntity.getGroupGid(),
                    threadEntity.getNickname());

        }

        // 清空本地未读数目
        threadEntity.setUnreadCount(0);
        mThreadViewModel.insertThreadEntity(threadEntity);
    }

    /**
     * 监听 EventBus 连接消息
     *
     * @param connectionEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionEvent(ConnectionEvent connectionEvent) {

        String connectionStatus = connectionEvent.getContent();
        Logger.i("onConnectionEvent: " + connectionStatus);

        if (connectionStatus.equals(BDCoreConstant.USER_STATUS_CONNECTING)) {

            mTopBar.setTitle("萝卜丝(连接中...)");
        } else if (connectionStatus.equals(BDCoreConstant.USER_STATUS_CONNECTED)) {

            mTopBar.setTitle("萝卜丝");
        } else if (connectionStatus.equals(BDCoreConstant.USER_STATUS_DISCONNECTED)) {

            mTopBar.setTitle("萝卜丝(连接断开)");
        }
    }

    /**
     * 监听 EventBus 广播消息
     *
     * @param threadEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThreadEvent(ThreadEvent threadEvent) {
        Logger.i("onThreadEvent");

    }


    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(data != null){
            Logger.i("onFragmentResult");
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
            getThreads();
        }
    };

    /**
     * 监听搜索框
     */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String search = charSequence.toString();
            if (search != null && search.trim().length() > 0) {
                searchModel(search);
            } else {
                initModel();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    /**
     * 创建右划菜单
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {

            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext())
                    .setBackground(R.drawable.bytedesk_selector_red)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
        }
    };

    /**
     * 点击右划菜单监听
     */
    private SwipeMenuItemClickListener swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Logger.d( "list第" + adapterPosition + "; 右侧菜单第" + menuPosition);
                // 仅删除本地会话记录
                ThreadEntity threadEntity = mThreadEntities.get(adapterPosition);
                mThreadViewModel.deleteThreadEntity(threadEntity);

            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Logger.d("list第" + adapterPosition + "; 左侧菜单第" + menuPosition);
            }
        }
    };


}
