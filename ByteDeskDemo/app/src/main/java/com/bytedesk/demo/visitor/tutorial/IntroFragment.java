package com.bytedesk.demo.visitor.tutorial;


import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.bytedesk.demo.R;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.bytedesk.demo.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IntroFragment extends BaseFragment {

    @BindView(R.id.topbar) QMUITopBarLayout mTopBar;
    @BindView(R.id.groupListView) QMUIGroupListView mGroupListView;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_intro, null);
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

        mTopBar.setTitle("萝卜丝·Robot");
    }

    private void initGroupListView() {

        QMUICommonListItemView introItem = mGroupListView.createItemView("：");
//        introItem.setDetailText("online");

        QMUIGroupListView.newSection(getContext())
                .setTitle("萝卜丝·Robot简介")
//                .setDescription("默认描述")
                .addItemView(introItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .addTo(mGroupListView);

    }

    @Override
    public QMUIFragment.TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

}
