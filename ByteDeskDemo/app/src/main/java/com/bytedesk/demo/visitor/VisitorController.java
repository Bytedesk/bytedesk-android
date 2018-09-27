package com.bytedesk.demo.visitor;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.bytedesk.demo.common.AboutFragment;
import com.bytedesk.demo.visitor.tutorial.ChatFragment;
import com.bytedesk.demo.visitor.tutorial.IntroFragment;
import com.bytedesk.demo.visitor.tutorial.LoginFragment;
import com.bytedesk.demo.visitor.tutorial.StatusFragment;
import com.bytedesk.demo.visitor.tutorial.ProfileFragment;
import com.bytedesk.demo.visitor.tutorial.ThreadFragment;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.bytedesk.demo.R;
import com.bytedesk.demo.common.BaseController;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ningjinpeng on 2018/3/26.
 *
 */

public class VisitorController extends BaseController {

    @BindView(R.id.topbar) QMUITopBarLayout mTopBar;
    @BindView(R.id.groupListView) QMUIGroupListView mGroupListView;

    public VisitorController(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.controller_visitor, this);
        ButterKnife.bind(this);

        initTopBar();
        initGroupListView();
    }

    @Override
    protected String getTitle() {
        return "访客端接口";
    }

    protected void initTopBar() {
        mTopBar.setTitle(getTitle());
//        mTopBar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_color_blue));
        mTopBar.addRightImageButton(R.mipmap.icon_topbar_about, R.id.topbar_right_about_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutFragment fragment = new AboutFragment();
                startFragment(fragment);
            }
        });
    }

    private void initGroupListView() {
//        QMUICommonListItemView introItem = mGroupListView.createItemView("接口说明");

//        QMUICommonListItemView loginItem = mGroupListView.createItemView("登录接口");

        QMUICommonListItemView chatItem = mGroupListView.createItemView("开始新会话接口");

        QMUICommonListItemView userinfoItem = mGroupListView.createItemView("设置用户信息接口");

        QMUICommonListItemView statusItem = mGroupListView.createItemView("查询在线状态接口");

        QMUICommonListItemView sessionHistoryItem = mGroupListView.createItemView("会话历史记录接口");

        QMUIGroupListView.newSection(getContext())
//                .addItemView(introItem, new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        IntroFragment introFragment = new IntroFragment();
//                        startFragment(introFragment);
//                    }
//                })
//                .addItemView(loginItem, new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        LoginFragment loginFragment = new LoginFragment();
//                        startFragment(loginFragment);
//                    }
//                })
                .addItemView(chatItem, new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChatFragment chatFragment = new ChatFragment();
                        startFragment(chatFragment);
                    }
                })
                .addItemView(userinfoItem, new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProfileFragment profileFragment = new ProfileFragment();
                        startFragment(profileFragment);
                    }
                })
                .addItemView(statusItem, new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StatusFragment statusFragment = new StatusFragment();
                        startFragment(statusFragment);
                    }
                })
                .addItemView(sessionHistoryItem, new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ThreadFragment threadFragment = new ThreadFragment();
                        startFragment(threadFragment);
                    }
                })
                .addTo(mGroupListView);
    }

}