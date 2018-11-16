package com.bytedesk.demo.agent;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.bytedesk.demo.common.AboutFragment;
import com.bytedesk.demo.R;
import com.bytedesk.demo.common.BaseController;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author bytedesk.com on 2018/3/26.
 */

public class AgentController extends BaseController {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.groupListView) QMUIGroupListView mGroupListView;

    public AgentController(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.controller_agent, this);
        ButterKnife.bind(this);

        initTopBar();
        initGroupListView();
    }

    @Override
    protected String getTitle() {
        return "客服端接口(暂未开放)";
    }

    protected void initTopBar() {
        mTopBar.setTitle(getTitle());
        mTopBar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_color_blue));
        mTopBar.addRightImageButton(R.mipmap.icon_topbar_about, R.id.topbar_right_about_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            AboutFragment fragment = new AboutFragment();
            startFragment(fragment);
            }
        });
    }

    private void initGroupListView() {
        QMUICommonListItemView introItem = mGroupListView.createItemView("1.接口说明");

        QMUICommonListItemView loginItem = mGroupListView.createItemView("2.登录接口");

        QMUIGroupListView.newSection(getContext())
                .addItemView(introItem, new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .addItemView(loginItem, new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .addTo(mGroupListView);
    }
}
