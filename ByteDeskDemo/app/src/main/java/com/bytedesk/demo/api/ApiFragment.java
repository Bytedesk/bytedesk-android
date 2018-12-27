package com.bytedesk.demo.api;

import android.text.InputType;
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
import com.bytedesk.demo.im.fragment.notice.NoticeFragment;
import com.bytedesk.demo.im.fragment.queue.QueueFragment;
import com.bytedesk.demo.im.fragment.setting.SettingFragment;
import com.bytedesk.demo.im.fragment.social.TabFragment;
import com.bytedesk.demo.kefu.fragment.ChatFragment;
import com.bytedesk.demo.kefu.fragment.ProfileFragment;
import com.bytedesk.demo.kefu.fragment.StatusFragment;
import com.bytedesk.demo.kefu.fragment.ThreadFragment;
import com.bytedesk.ui.api.BDUiApi;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
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
        registerItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        loginItem = mGroupListView.createItemView("2. 登录接口");
        loginItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView logoutItem = mGroupListView.createItemView("3. 退出登录接口");
        logoutItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
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
        chatItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView userInfoItem = mGroupListView.createItemView("2.自定义用户信息接口");
        userInfoItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView statusItem = mGroupListView.createItemView("3.在线状态接口");
        statusItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView sessionHistoryItem = mGroupListView.createItemView("4.历史会话记录接口");
        sessionHistoryItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView wapChatItem = mGroupListView.createItemView("5.网页会话演示");
        wapChatItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
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
                        String url = "https://vip.bytedesk.com/chat?uid=201808221551193&wid=201807171659201&type=workGroup&aid=&ph=ph";
                        BDUiApi.startHtml5Chat(getContext(), url);
                    }
                })
                .addTo(mGroupListView);

        // IM接口
        QMUICommonListItemView friendItem = mGroupListView.createItemView("1.好友接口");
        friendItem.setDetailText("社交：关注/粉丝/好友/黑名单");
        friendItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView contactItem = mGroupListView.createItemView("2.联系人接口");
        contactItem.setDetailText("客服同事");
        contactItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView groupItem = mGroupListView.createItemView("3.群组接口");
        groupItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView threadItem = mGroupListView.createItemView("4.会话接口");
        threadItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView queueItem = mGroupListView.createItemView("5.排队接口");
        queueItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView noticeItem = mGroupListView.createItemView("6.通知接口");
        noticeItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView settingItem = mGroupListView.createItemView("7.设置接口");
        settingItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUIGroupListView.newSection(getContext())
                .setTitle("IM接口")
                .addItemView(friendItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TabFragment tabFragment = new TabFragment();
                        startFragment(tabFragment);
                    }
                })
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
                }).addItemView(noticeItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NoticeFragment noticeFragment = new NoticeFragment();
                        startFragment(noticeFragment);
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
     *
     * TODO: 当多个安卓客户端同时登录同一个账号的时候，会被踢掉线，此客户端会自动重连，导致不断重新登录的情况，待处理：弹出提示框
     * TODO：弹出登录框让用户手动输入用户名/密码
     */
    private void login() {

        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("自定义用户名登录")
            .setPlaceholder("在此输入自定义用户名")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction("取消", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    dialog.dismiss();
                }
            })
            .addAction("确定", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    final CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {

                        //
                        String username = text.toString();
                        String password = "123456";
                        String appKey = "201809171553112";
                        // 获取subDomain，也即企业号：登录后台->所有设置->客服账号->企业号
                        String subDomain = "vip";

                        // 调用登录接口
                        BDCoreApi.login(getContext(), username, password, appKey, subDomain, new BaseCallback() {

                            @Override
                            public void onSuccess(JSONObject object) {

                                Toast.makeText(getContext(), "登录成功", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(JSONObject object) {
                                Logger.e("login failed message");

                                Toast.makeText(getContext(), "登录失败", Toast.LENGTH_LONG).show();
                            }
                        });

                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "请填入自定义用户名", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
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
     *
     * TODO：弹出登录框让用户手动输入用户名/密码
     */
    private void registerUser() {

        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("自定义用户登录名")
            .setPlaceholder("在此输入您的用户名(只能包含字母和数字)")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction("取消", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    dialog.dismiss();
                }
            })
            .addAction("确定", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    final CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {

                        //
                        String username = text.toString();
                        String nickname = "自定义测试账号"+username;
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

                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "请填入昵称", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();

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
        } else if (connectionStatus.equals(BDCoreConstant.USER_STATUS_KICKOFF)) {

            title = "萝卜丝(异地登录，被踢掉线)";
            // 弹窗提示
            new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("异地登录提示")
                .setMessage("此账号在其他安卓设备登录，被踢掉线")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                }).show();
        }
        mTopBar.setTitle(title);
    }


}
