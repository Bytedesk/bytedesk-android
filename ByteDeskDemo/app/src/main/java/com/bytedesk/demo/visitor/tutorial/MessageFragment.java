package com.bytedesk.demo.visitor.tutorial;


import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.bytedesk.demo.R;
import com.bytedesk.demo.common.BaseFragment;


import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * TODO： 待上线
 *
 */
public class MessageFragment extends BaseFragment {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    private String mDefaultWorkgroupId = "14";
    private String mTitle = "反馈";

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_feedback, null);
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

        mTopBar.setTitle("聊天记录");
    }

    private void initGroupListView() {

//        QMUICommonListItemView chatItem = mGroupListView.createItemView("开始会话：");
//        QMUIGroupListView.newSection(getContext())
//                .setTitle("默认会话接口")
//                .setDescription("在web管理后台开启/关闭机器人")
//                .addItemView(chatItem, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        //
//                        WXUIApi.visitorStartChatActivity(getContext(), mDefaultWorkgroupId, mTitle);
//
//                    }
//                })
//                .addTo(mGroupListView);


    }

    @Override
    public QMUIFragment.TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

}
