package com.bytedesk.demo.common;

import android.view.LayoutInflater;
import android.view.View;

import com.bytedesk.demo.R;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.bytedesk.core.api.BDConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 配置
 */
public class ServerFragment extends BaseFragment {

    @BindView(R.id.topbar) QMUITopBarLayout mTopBar;
    @BindView(R.id.groupListView) QMUIGroupListView mGroupListView;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_server, null);
        ButterKnife.bind(this, root);

        initTopBar();
        initGroupListView();
//        testProto();

        return root;
    }

    private void initTopBar() {
        //
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        //
        mTopBar.addRightImageButton(R.mipmap.icon_topbar_about, R.id.topbar_right_about_button).setOnClickListener(view -> {
            AboutFragment fragment = new AboutFragment();
            startFragment(fragment);
        });
        //
        mTopBar.setTitle(getResources().getString(R.string.bytedesk_self_server));
    }

    private void initGroupListView() {

        QMUICommonListItemView authAddressItem = mGroupListView.createItemView("地址");
        authAddressItem.setDetailText(BDConfig.getInstance().getRestApiHost());
        QMUIGroupListView.newSection(getContext())
                .setTitle("REST服务器,注意：以'/'结尾")
                .addItemView(authAddressItem, view -> {

                    // 修改为自己的服务器地址，注意：地址以 http或https开头, '/'结尾
//                    BDConfig.getInstance().setRestApiHost("https://api.bytedesk.com/");

                }).addTo(mGroupListView);

        //
        QMUICommonListItemView stunAddressItem = mGroupListView.createItemView("STUN");
        stunAddressItem.setDetailText(BDConfig.getInstance().getWebRTCStunServer());
        QMUICommonListItemView turnAddressItem = mGroupListView.createItemView("TURN");
        turnAddressItem.setDetailText(BDConfig.getInstance().getWebRTCTurnServer());
        QMUICommonListItemView turnUsernameItem = mGroupListView.createItemView("username");
        turnUsernameItem.setDetailText(BDConfig.getInstance().getWebrtcTurnUsername());
        QMUICommonListItemView turnPasswordItem = mGroupListView.createItemView("password");
        turnPasswordItem.setDetailText(BDConfig.getInstance().getWebrtcTurnPassword());
        QMUIGroupListView.newSection(getContext())
                .setTitle("STUN/TURN for WebRTC")
                .addItemView(stunAddressItem, view -> {

                })
                .addItemView(turnAddressItem, v -> {

                })
                .addItemView(turnUsernameItem, v -> {

                })
                .addItemView(turnPasswordItem, v -> {

                }).addTo(mGroupListView);

        //
        QMUICommonListItemView mqAddressItem = mGroupListView.createItemView("地址");
        mqAddressItem.setDetailText(BDConfig.getInstance().getMqttHost());
        QMUICommonListItemView mqPortItem = mGroupListView.createItemView("端口号");
        mqPortItem.setDetailText(String.valueOf(BDConfig.getInstance().getMqttPort()));
//        QMUICommonListItemView mqAuthUsername = mGroupListView.createItemView("用户名");
//        mqAuthUsername.setDetailText(BDConfig.getInstance().getMqttAuthUsername());
//        QMUICommonListItemView mqAuthPassword = mGroupListView.createItemView("密码");
//        mqAuthPassword.setDetailText(BDConfig.getInstance().getMqttAuthPassword());
        QMUIGroupListView.newSection(getContext())
                .setTitle("消息服务器, 注意：地址没有http前缀")
                .addItemView(mqAddressItem, view -> {

//              修改为自己消息服务器地址, 注意：地址没有http前缀
//              BDConfig.getInstance().setMqttHost("mq.bytedesk.com");

                }).addItemView(mqPortItem, view -> {

//              修改为自己消息服务器端口号
//             BDConfig.getInstance().setMqttPort(1883);

        })
//                .addItemView(mqAuthUsername, view -> {
//
////              修改为自己消息服务器用户名
////              BDConfig.getInstance().setMqttAuthUsername("mqtt_android");
//
//        }).addItemView(mqAuthPassword, view -> {
//
////              修改为自己消息服务器密码
////              BDConfig.getInstance().setMqttAuthPassword("mqtt_android");
//
//        })
                .addTo(mGroupListView);

        //
        QMUICommonListItemView restoreDefault = mGroupListView.createItemView("恢复默认值");
        QMUIGroupListView.newSection(getContext()).addItemView(restoreDefault, view -> {

            // 恢复默认值
            BDConfig.getInstance().restoreDefault();

            //
            authAddressItem.setDetailText(BDConfig.getInstance().getRestApiHost());
            mqAddressItem.setDetailText(BDConfig.getInstance().getMqttHost());
            mqPortItem.setDetailText(String.valueOf(BDConfig.getInstance().getMqttPort()));
//            mqAuthUsername.setDetailText(BDConfig.getInstance().getMqttAuthUsername());
//            mqAuthPassword.setDetailText(BDConfig.getInstance().getMqttAuthPassword());

        }).addTo(mGroupListView);

    }


    private void testProto() {

//        BDCoreApi.getProto(getContext(), new BaseCallback() {
//
//            @Override
//            public void onSuccess(JSONObject object) {
//
//            }
//
//            @Override
//            public void onError(JSONObject object) {
//
//            }
//        });

//        BDCoreApi.getProto2(getContext(), "android test get proto content", new BaseCallback() {
//
//            @Override
//            public void onSuccess(JSONObject object) {
//
//            }
//
//            @Override
//            public void onError(JSONObject object) {
//
//            }
//        });
//
//        BDCoreApi.postProto(getContext(), "android test post proto content", new BaseCallback() {
//
//            @Override
//            public void onSuccess(JSONObject object) {
//
//            }
//
//            @Override
//            public void onError(JSONObject object) {
//
//            }
//        });
    }

}
