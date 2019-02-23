package com.bytedesk.demo.kefu.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.bytedesk.demo.R;
import com.bytedesk.demo.common.BaseFragment;
import com.bytedesk.ui.api.BDUiApi;
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

    // 默认设置工作组wid
    private String wId = "201807171659201";
    // 指定坐席uid
    private String agentUid = "201808221551193";
    // 设置有前置选择的工作组
    private String preWid = "201808101819291";

    @Override
    protected View onCreateView() {
        //
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_chat, null);
        ButterKnife.bind(this, root);

        initTopBar();
        initGroupListView();

        return root;
    }

    private void initTopBar() {
        //
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
        QMUICommonListItemView workGroupChatItem = mGroupListView.createItemView("工作组会话:" + wId);
        QMUICommonListItemView appointChatItem = mGroupListView.createItemView("指定客服会话:" + agentUid);
        //
        QMUIGroupListView.newSection(getContext())
                .setTitle("默认会话接口")
                .setDescription("在web管理后台开启/关闭机器人")
                .addItemView(workGroupChatItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                        BDUiApi.startWorkGroupChatActivity(getContext(), wId, "工作组会话");
                    }
                })
                .addItemView(appointChatItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                        BDUiApi.startAppointChatActivity(getContext(), agentUid, "指定客服会话");
                    }
                })
                .addTo(mGroupListView);
        //
        QMUICommonListItemView shopWorkGroupChatItem = mGroupListView.createItemView("电商客服工作组:" + wId);
        QMUICommonListItemView shopAppointedChatItem = mGroupListView.createItemView("指定客服会话:" + agentUid);
        //
        QMUIGroupListView.newSection(getContext())
                .setTitle("电商客服")
                .setDescription("在web管理后台开启/关闭机器人")
                .addItemView(shopWorkGroupChatItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                    }
                })
                .addItemView(shopAppointedChatItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                    }
                }).addTo(mGroupListView);

        //
        QMUICommonListItemView preChoiceChatItem = mGroupListView.createItemView("工作组会话:" +  preWid);
        //
        QMUIGroupListView.newSection(getContext())
                .setTitle("前置选择")
                .setDescription("在后台启用问卷选择")
                .addItemView(preChoiceChatItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                        BDUiApi.startWorkGroupChatActivity(getContext(), preWid, "工作组会话");
                    }
            }).addTo(mGroupListView);


    }

}
