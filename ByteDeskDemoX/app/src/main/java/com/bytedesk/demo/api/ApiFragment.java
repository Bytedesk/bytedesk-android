package com.bytedesk.demo.api;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bytedesk.core.api.BDCoreApi;
import com.bytedesk.core.callback.BaseCallback;
import com.bytedesk.core.callback.LoginCallback;
import com.bytedesk.core.event.ConnectionEvent;
import com.bytedesk.core.event.KickoffEvent;
import com.bytedesk.core.event.MessageEntityEvent;
import com.bytedesk.core.event.MessageEvent;
import com.bytedesk.core.util.BDCoreConstant;
import com.bytedesk.core.util.BDPreferenceManager;
import com.bytedesk.demo.R;
import com.bytedesk.demo.common.BaseFragment;
import com.bytedesk.demo.common.ScanQRFragment;
import com.bytedesk.demo.common.ServerFragment;
import com.bytedesk.demo.common.SettingFragment;
import com.bytedesk.demo.im.fragment.contact.ContactFragment;
import com.bytedesk.demo.im.fragment.group.GroupFragment;
import com.bytedesk.demo.im.fragment.notice.NoticeFragment;
import com.bytedesk.demo.im.fragment.profile.ProfileFragment;
import com.bytedesk.demo.im.fragment.queue.QueueFragment;
import com.bytedesk.demo.im.fragment.social.TabFragment;
import com.bytedesk.demo.kefu.fragment.AppRateFragment;
import com.bytedesk.demo.kefu.fragment.AppUpgradeFragment;
import com.bytedesk.demo.kefu.fragment.ChatFragment;
import com.bytedesk.demo.kefu.fragment.StatusFragment;
import com.bytedesk.demo.kefu.fragment.SupportFragment;
import com.bytedesk.demo.kefu.fragment.ThreadFragment;
import com.bytedesk.demo.utils.BDDemoConst;
import com.bytedesk.ui.api.BDUiApi;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIPackageHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
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

    private String version;

    @Override
    protected View onCreateView() {
        //
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_api, null);
        ButterKnife.bind(this, root);

        mPreferenceManager = BDPreferenceManager.getInstance(getContext());
        EventBus.getDefault().register(this);

        version = QMUIPackageHelper.getAppVersion(getContext());

        initTopBar();
        initGroupListView();

        return root;
    }

    private void initTopBar() {

        mTopBar.setTitle("萝卜丝" + version + "(未连接)");
    }

    private void initGroupListView() {
        // 公共接口
        QMUICommonListItemView registerItem = mGroupListView.createItemView("1. 点我注册");
        registerItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        loginItem = mGroupListView.createItemView("2. 点我登录");
        loginItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView logoutItem = mGroupListView.createItemView("3. 退出登录");
        logoutItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUIGroupListView.newSection(getContext())
                .setTitle("公共接口")
                .addItemView(registerItem, view -> showRegisterSheet())
                .addItemView(loginItem, view -> showLoginSheet())
                .addItemView(logoutItem, view -> logout())
                .addTo(mGroupListView);

        // 客服接口
        QMUICommonListItemView chatItem = mGroupListView.createItemView("1.联系客服");
        chatItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView userInfoItem = mGroupListView.createItemView("2.自定义用户信息");
        userInfoItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView statusItem = mGroupListView.createItemView("3.在线状态");
        statusItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView sessionHistoryItem = mGroupListView.createItemView("4.历史会话记录");
        sessionHistoryItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView ticketItem = mGroupListView.createItemView("5.提交工单");
        ticketItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView feedbackItem = mGroupListView.createItemView("6.意见反馈");
        feedbackItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView helpCenterItem = mGroupListView.createItemView("7.常见问题");
        helpCenterItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView wapChatItem = mGroupListView.createItemView("8.网页会话");
        wapChatItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        QMUICommonListItemView upgrateItem = mGroupListView.createItemView("9.引导新版本升级");
//        upgrateItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUIGroupListView.newSection(getContext())
                .setTitle("客服接口")
                .addItemView(chatItem, view -> startFragment(new ChatFragment()))
                .addItemView(userInfoItem, view -> {
                    com.bytedesk.demo.kefu.fragment.ProfileFragment profileFragment = new com.bytedesk.demo.kefu.fragment.ProfileFragment();
                    startFragment(profileFragment);
                })
                .addItemView(statusItem, view -> startFragment(new StatusFragment()))
                .addItemView(sessionHistoryItem, view -> startFragment(new ThreadFragment()))
                .addItemView(ticketItem, view -> BDUiApi.startTicketActivity(getContext(), BDDemoConst.DEFAULT_TEST_ADMIN_UID))
                .addItemView(feedbackItem, view -> BDUiApi.startFeedbackActivity(getContext(), BDDemoConst.DEFAULT_TEST_ADMIN_UID))
                .addItemView(helpCenterItem, view -> BDUiApi.startSupportApiActivity(getContext(), BDDemoConst.DEFAULT_TEST_ADMIN_UID))
                .addItemView(wapChatItem, view -> {
                    // 注意: 登录后台->客服管理->技能组->获取代码 获取相应URL
                    String url = "https://www.bytedesk.com/chat?sub=vip&uid=" + BDDemoConst.DEFAULT_TEST_ADMIN_UID + "&wid=201807171659201&type=workGroup&aid=&ph=ph";
                    BDUiApi.startHtml5Chat(getContext(), url, "在线客服");
                })
//                .addItemView(upgrateItem,  view -> {
//                    // TODO: 引导新版本升级
//                    startFragment(new AppUpgradeFragment());
//                })
                .addTo(mGroupListView);
    }

    /**
     * 注册扩展
     */
    private void showRegisterSheet() {
        //
        new QMUIBottomSheet.BottomListSheetBuilder(getActivity())
                .addItem("自定义用户名")
                .addItem("匿名用户")
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    dialog.dismiss();

                    if (tag.equals("自定义用户名")) {
                        registerUser();
                    } else {
                        Toast.makeText(getContext(), "匿名用户不需要注册，直接调用匿名登录接口即可", Toast.LENGTH_LONG).show();
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
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    dialog.dismiss();
                    if (tag.equals("自定义用户名")) {
                        login();
                    } else {
                        anonymousLogin();
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
            .addAction("取消", (dialog, index) -> dialog.dismiss())
            .addAction("确定", (dialog, index) -> {

                final CharSequence text = builder.getEditText().getText();
                if (text != null && text.length() > 0) {
                    //
                    final QMUITipDialog loadingDialog = new QMUITipDialog.Builder(getContext())
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord(getResources().getString(R.string.bytedesk_logining))
                            .create();
                    loadingDialog.show();

                    //
                    String username = text.toString();
                    String password = "123456";

                    // 调用登录接口
                    BDCoreApi.login(getContext(), username, password, BDDemoConst.DEFAULT_TEST_APPKEY, BDDemoConst.DEFAULT_TEST_SUBDOMAIN, new BaseCallback() {

                        @Override
                        public void onSuccess(JSONObject object) {
                            loadingDialog.dismiss();

                            Toast.makeText(getContext(), "登录成功", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(JSONObject object) {
                            Logger.e("login failed message");
                            loadingDialog.dismiss();

                            Toast.makeText(getContext(), "登录失败", Toast.LENGTH_LONG).show();
                        }
                    });

                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "请填入自定义用户名", Toast.LENGTH_SHORT).show();
                }
            })
            .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    /**
     * 匿名登录
     */
    private void anonymousLogin() {
        //
        final QMUITipDialog loadingDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(getResources().getString(R.string.bytedesk_logining))
                .create();
        loadingDialog.show();

        // 授权登录接口
        BDCoreApi.visitorLogin(getContext(), BDDemoConst.DEFAULT_TEST_APPKEY, BDDemoConst.DEFAULT_TEST_SUBDOMAIN, new LoginCallback() {

            @Override
            public void onSuccess(JSONObject object) {
                loadingDialog.dismiss();
                try {
                    Logger.d("login success message: " + object.get("message")
                            + " status_code:" + object.get("status_code"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject object) {
                loadingDialog.dismiss();
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

        new QMUIDialog.MessageDialogBuilder(getContext())
                .setTitle("提示")
                .setMessage("确定要退出登录吗？")
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    dialog.dismiss();

                    ///
                    final QMUITipDialog loadingDialog = new QMUITipDialog.Builder(getContext())
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord(getResources().getString(R.string.bytedesk_logouting))
                            .create();
                    loadingDialog.show();
                    //
                    BDCoreApi.logout(getContext(), new BaseCallback() {
                        @Override
                        public void onSuccess(JSONObject object) {
                            loadingDialog.dismiss();

                            Toast.makeText(getContext(), "退出登录成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(JSONObject object) {
                            loadingDialog.dismiss();

                            Logger.e("退出登录失败");
                            Toast.makeText(getContext(), "退出登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();

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
            .addAction("取消", (dialog, index) -> dialog.dismiss())
            .addAction("确定", (dialog, index) -> {
                final CharSequence text = builder.getEditText().getText();
                if (text != null && text.length() > 0) {

                    //
                    String username = text.toString();
                    String nickname = "自定义测试账号"+username;
                    String password = "123456";
                    //
                    BDCoreApi.registerUser(getContext(), username, nickname, password, BDDemoConst.DEFAULT_TEST_SUBDOMAIN, new BaseCallback() {

                        @Override
                        public void onSuccess(JSONObject object) {

                            try {
                                //
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
            })
            .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    /**
     * 监听 EventBus 长连接状态
     *
     * @param connectionEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionEvent(ConnectionEvent connectionEvent) {

        String connectionStatus = connectionEvent.getContent();
        Logger.i("onConnectionEvent: " + connectionStatus);

        String title = connectionStatus;
        if (connectionStatus.equals(BDCoreConstant.USER_STATUS_CONNECTING)) {

            title = "萝卜丝" + version + "(连接中...)";
        } else if (connectionStatus.equals(BDCoreConstant.USER_STATUS_CONNECTED)) {

            title = "萝卜丝" + version + "(已连接)";
//            loginItem.setDetailText("连接已建立: " + mPreferenceManager.getUsername());
        } else if (connectionStatus.equals(BDCoreConstant.USER_STATUS_DISCONNECTED)) {

            title = "萝卜丝" + version + "(连接断开)";
//            loginItem.setDetailText("当前未连接");
        }
        mTopBar.setTitle(title);
    }

    /**
     * 监听 EventBus 广播消息
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        Logger.i("MessageEvent");

        try {

            JSONObject messageObject = messageEvent.getJsonObject();
            String type = messageObject.getString("type");
            String content = messageObject.getString("content");
            Logger.i("type %s,  content %s ", type, content);
            // TODO: 收到新信息，开发者可自行决定处理，如：通知栏显示消息提示


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听账号异地登录通知
     *
     * @param kickoffEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKickoffEvent(KickoffEvent kickoffEvent) {

        String content = kickoffEvent.getContent();
        Logger.w("onKickoffEvent: " + content);

        // 弹窗提示
        new QMUIDialog.MessageDialogBuilder(getActivity())
            .setTitle("异地登录提示")
            .setMessage(content)
            .addAction("确定", (dialog, index) -> {
                dialog.dismiss();

                // 开发者可自行决定是否退出登录
                // 注意: 同一账号同时登录多个客户端不影响正常会话
                logout();

            }).show();
    }


    /**
     * 监听接收消息
     *
     * @param messageEntityEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEntityEvent(MessageEntityEvent messageEntityEvent) {

        String type = messageEntityEvent.getMessageEntity().getType();
        if (type.equals(BDCoreConstant.MESSAGE_TYPE_TEXT)) {
            Logger.i("messageEntityEvent 文本消息 %s", messageEntityEvent.getMessageEntity().getContent());

        } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_IMAGE)) {
            Logger.i("messageEntityEvent 图片消息 %s", messageEntityEvent.getMessageEntity().getImageUrl());

        } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_VOICE)) {
            Logger.i("messageEntityEvent 语音消息 %s", messageEntityEvent.getMessageEntity().getVoiceUrl());

        } else if (type.equals(BDCoreConstant.MESSAGE_TYPE_FILE)) {
            Logger.i("messageEntityEvent 文件消息 %s", messageEntityEvent.getMessageEntity().getFileUrl());

        } else {
            Logger.i("messageEntityEvent 其他类型消息 %s", type);
        }
    }


}
