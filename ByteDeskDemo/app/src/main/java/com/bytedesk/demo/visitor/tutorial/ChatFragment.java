package com.bytedesk.demo.visitor.tutorial;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.bytedesk.demo.R;
import com.bytedesk.demo.common.BaseFragment;
import com.bytedesk.ui.api.BDUiApi;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 *  TODO:
 *    1. 普通会话
 *    2. 电商会话：传递商品参数
 *    3. 默认机器人会话
 *    4. activity方式打开
 *    5. fragment方式打开
 *
 */

public class ChatFragment extends BaseFragment {

    @BindView(R.id.topbar) QMUITopBarLayout mTopBar;
    @BindView(R.id.groupListView) QMUIGroupListView mGroupListView;

    private String uId = "201808221551193";
    private String wId = "201807171659201";

    private String mTitle = "人工客服";

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_chat, null);
        ButterKnife.bind(this, root);

        initTopBar();
        initGroupListView();

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

        mTopBar.setTitle("开始新会话接口");
    }

    private void initGroupListView() {
        //
        QMUICommonListItemView chatItem = mGroupListView.createItemView("开始会话：");
        QMUIGroupListView.newSection(getContext())
                .setTitle("默认会话接口")
                .setDescription("在web管理后台开启/关闭机器人")
                .addItemView(chatItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                        BDUiApi.visitorStartChatActivity(getContext(), uId, wId, mTitle);
                    }
                })
                .addTo(mGroupListView);


        /*
        TODO: 待上线
        QMUICommonListItemView buyChatItem = mGroupListView.createItemView("开始会话：");
        QMUIGroupListView.newSection(getContext())
                .setTitle("电商会话接口")
                .addItemView(buyChatItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .addTo(mGroupListView);
        */

        //
        /*
        TODO: 待上线
        QMUICommonListItemView oneChatItem = mGroupListView.createItemView("开始会话：");
        QMUIGroupListView.newSection(getContext())
                .setTitle("一对一会话接口")
                .addItemView(oneChatItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .addTo(mGroupListView);
         */


    }

    @Override
    public QMUIFragment.TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }
}
