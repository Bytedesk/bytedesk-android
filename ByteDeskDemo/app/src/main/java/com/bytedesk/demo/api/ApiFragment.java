package com.bytedesk.demo.api;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bytedesk.core.api.BDCoreApi;
import com.bytedesk.core.callback.BaseCallback;
import com.bytedesk.core.callback.LoginCallback;
import com.bytedesk.core.event.ConnectionEvent;
import com.bytedesk.core.util.BDCoreConstant;
import com.bytedesk.core.util.BDPreferenceManager;
import com.bytedesk.demo.R;
import com.bytedesk.demo.common.BaseFragment;
import com.bytedesk.demo.im.fragment.contact.ContactFragment;
import com.bytedesk.demo.im.fragment.group.GroupFragment;
import com.bytedesk.demo.im.fragment.queue.QueueFragment;
import com.bytedesk.demo.im.fragment.setting.SettingFragment;
import com.bytedesk.demo.kefu.fragment.ChatFragment;
import com.bytedesk.demo.kefu.fragment.ProfileFragment;
import com.bytedesk.demo.kefu.fragment.StatusFragment;
import com.bytedesk.demo.kefu.fragment.ThreadFragment;
import com.bytedesk.ui.api.BDUiApi;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 接口列表
 */
public class ApiFragment extends BaseFragment {

    @BindView(R.id.topbar) QMUITopBarLayout mTopBar;
    @BindView(R.id.groupListView) QMUIGroupListView mGroupListView;

    private BDPreferenceManager mPreferenceManager;
    private QMUICommonListItemView loginItem;

    @Override
    protected View onCreateView() {
        //
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_api, null);
        ButterKnife.bind(this, root);

        mPreferenceManager = BDPreferenceManager.getInstance(getContext());
        EventBus.getDefault().register(this);

        initTopBar();
        initGroupListView();

        return root;
    }

    private void initTopBar() {
        mTopBar.setTitle("萝卜丝(未连接)");
    }

    private void initGroupListView() {

        // 公共接口
        QMUICommonListItemView registerItem = mGroupListView.createItemView("1. 注册接口");
        loginItem = mGroupListView.createItemView("2. 登录接口");
        QMUICommonListItemView logoutItem = mGroupListView.createItemView("3. 退出登录接口");
        QMUIGroupListView.newSection(getContext())
                .setTitle("公共接口")
                .addItemView(registerItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showRegisterSheet();
                    }
                })
                .addItemView(loginItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showLoginSheet();
                    }
                })
                .addItemView(logoutItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        logout();
                    }
                })
                .addTo(mGroupListView);

        // 客服接口
        QMUICommonListItemView chatItem = mGroupListView.createItemView("1.联系客服接口");
        QMUICommonListItemView userInfoItem = mGroupListView.createItemView("2.自定义用户信息接口");
        QMUICommonListItemView statusItem = mGroupListView.createItemView("3.在线状态接口");
        QMUICommonListItemView sessionHistoryItem = mGroupListView.createItemView("4.历史会话记录接口");
        QMUICommonListItemView wapChatItem = mGroupListView.createItemView("5.网页会话演示");
        QMUIGroupListView.newSection(getContext())
                .setTitle("客服接口")
                .addItemView(chatItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChatFragment chatFragment = new ChatFragment();
                        startFragment(chatFragment);
                    }
                })
                .addItemView(userInfoItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProfileFragment profileFragment = new ProfileFragment();
                        startFragment(profileFragment);
                    }
                })
                .addItemView(statusItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StatusFragment statusFragment = new StatusFragment();
                        startFragment(statusFragment);
                    }
                })
                .addItemView(sessionHistoryItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ThreadFragment threadFragment = new ThreadFragment();
                        startFragment(threadFragment);
                    }
                })
                .addItemView(wapChatItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 注意: 登录后台->所有设置->所有客服->工作组->获取代码 获取相应URL
                        String url = "https://vip.bytedesk.com/visitor/chat?uid=201808221551193&wid=201807171659201&type=workGroup&aid=&ph=ph";
                        BDUiApi.visitorStartChatHtml5(getContext(), url);
                    }
                })
                .addTo(mGroupListView);

        // IM接口
        QMUICommonListItemView contactItem = mGroupListView.createItemView("1.联系人接口");
        QMUICommonListItemView groupItem = mGroupListView.createItemView("2.群组接口");
        QMUICommonListItemView threadItem = mGroupListView.createItemView("3.会话接口");
        QMUICommonListItemView queueItem = mGroupListView.createItemView("4.排队接口");
        QMUICommonListItemView settingItem = mGroupListView.createItemView("5.设置接口");
        QMUIGroupListView.newSection(getContext())
                .setTitle("IM接口")
                .addItemView(contactItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContactFragment contactFragment = new ContactFragment();
                        startFragment(contactFragment);
                    }
                })
                .addItemView(groupItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GroupFragment groupFragment = new GroupFragment();
                        startFragment(groupFragment);
                    }
                })
                .addItemView(threadItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        com.bytedesk.demo.im.fragment.thread.ThreadFragment threadFragment = new com.bytedesk.demo.im.fragment.thread.ThreadFragment();
                        startFragment(threadFragment);
                    }
                })
                .addItemView(queueItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        QueueFragment queueFragment = new QueueFragment();
                        startFragment(queueFragment);
                    }
                })
                .addItemView(settingItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SettingFragment settingFragment = new SettingFragment();
                        startFragment(settingFragment);
                    }
                })
                .addTo(mGroupListView);
    }

    /**
     * 注册扩展
     */
    private void showRegisterSheet() {
        new QMUIBottomSheet.BottomListSheetBuilder(getActivity())
                .addItem("自定义用户名")
                .addItem("匿名用户")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();

                        if (tag.equals("自定义用户名")) {

                            registerUser();
                        } else {

                            Toast.makeText(getContext(), "匿名用户不需要注册，直接调用匿名登录接口即可", Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .build()
                .show();
    }

    /**
     * 登录扩展
     */
    private void showLoginSheet() {
        new QMUIBottomSheet.BottomListSheetBuilder(getActivity())
                .addItem("自定义用户名")
                .addItem("匿名用户")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();

                        if (tag.equals("自定义用户名")) {

                            login();
                        } else {

                            anonymousLogin();
                        }

                    }
                })
                .build()
                .show();
    }

    /**
     * 自定义用户名登录
     */
    private void login() {
        // 测试账号：test1，密码：123456
        // 或者：test1~test15 共15个测试账号，密码均为：123456
        String username = "test2";
        String password = "123456";
        String appKey = "201809171553112";
        // 获取subDomain，也即企业号：登录后台->所有设置->客服账号->企业号
        String subDomain = "vip";

        // 调用登录接口
        BDCoreApi.agentLogin(getContext(), username, password, appKey, subDomain, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

            }

            @Override
            public void onError(JSONObject object) {
                Logger.e("login failed message");
            }
        });
    }


    /**
     * 匿名登录
     */
    private void anonymousLogin() {

        // TODO: 参考文档：https://github.com/pengjinning/bytedesk-android
        // appkey和subDomain请替换为真实值
        final String appKey = "201809171553112";
        // 获取subDomain，也即企业号：登录后台->所有设置->客服账号->企业号
        final String subDomain = "vip";

        // 授权登录接口
        BDCoreApi.visitorLogin(getContext(), appKey, subDomain, new LoginCallback() {

            @Override
            public void onSuccess(JSONObject object) {
                try {
                    Logger.d("login success message: " + object.get("message")
                            + " status_code:" + object.get("status_code"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject object) {
                try {
                    Logger.e("login failed message: " + object.get("message")
                            + " status_code:" + object.get("status_code")
                            + " data:" + object.get("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 退出登录
     */
    private void logout() {
        //
        BDCoreApi.logout(getContext(), new BaseCallback() {
            @Override
            public void onSuccess(JSONObject object) {

                Toast.makeText(getContext(), "退出登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(JSONObject object) {

                Logger.e("退出登录失败");
                Toast.makeText(getContext(), "退出登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 自定义用户名注册
     */
    private void registerUser() {
        //
        String username = "androidtest1";
        String nickname = "安卓测试1";
        String password = "123456";
        // 获取subDomain，也即企业号：登录后台->所有设置->客服账号->企业号
        String subDomain = "vip";
        //
        BDCoreApi.registerUser(username, nickname, password, subDomain, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                try {
                    String message = object.getString("message");
                    int status_code = object.getInt("status_code");
                    //
                    if (status_code == 200) {
                        Toast.makeText(getContext(), "注册成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject object) {
                Toast.makeText(getContext(), "注册失败", Toast.LENGTH_LONG).show();
            }
        });
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

        String title = connectionStatus;
        if (connectionStatus.equals(BDCoreConstant.USER_STATUS_CONNECTING)) {

            title = "萝卜丝(连接中...)";
        } else if (connectionStatus.equals(BDCoreConstant.USER_STATUS_CONNECTED)) {

            title = "萝卜丝(已连接)";
            loginItem.setDetailText("连接已建立: " + mPreferenceManager.getUsername());
        } else if (connectionStatus.equals(BDCoreConstant.USER_STATUS_DISCONNECTED)) {

            title = "萝卜丝(连接断开)";
            loginItem.setDetailText("当前未连接");
        }
        mTopBar.setTitle(title);
    }


}