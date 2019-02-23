package com.bytedesk.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bytedesk.core.api.BDCoreApi;
import com.bytedesk.core.callback.BaseCallback;
import com.bytedesk.core.util.BDPreferenceManager;
import com.bytedesk.ui.R;
import com.bytedesk.ui.util.BDUiConstant;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 群组详情
 *
 * @author bytedesk.com on 2018/11/22
 */
public class GroupProfileActivity extends AppCompatActivity {

    private QMUITopBarLayout mTopBar;
    private QMUIFloatLayout mMembersFloatLayout;
    private QMUIGroupListView mGroupListView;

    private String gId;
    private QMUICommonListItemView nicknameItem;
    private QMUICommonListItemView qrCodeItem;
    private QMUICommonListItemView descriptionItem;
    private QMUICommonListItemView announcementItem;
    private QMUICommonListItemView transferItem;
    private QMUICommonListItemView withdrawItem;
    private QMUICommonListItemView dismissItem;

    private BDPreferenceManager mPreferenceManager;

    private Boolean mIsAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.bytedesk_activity_group_profile);
        mPreferenceManager = BDPreferenceManager.getInstance(this);
        //
        gId = getIntent().getStringExtra(BDUiConstant.EXTRA_UID);
        mIsAdmin = false;
        //
        initTopBar();
        initView();
        initModel();
    }


    private void initTopBar() {
        //
        mTopBar = findViewById(R.id.bytedesk_group_profile_topbarlayout);
        mTopBar.setTitle("群组详情");
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
//        mTopBar.addRightImageButton(R.mipmap.icon_topbar_overflow, QMUIViewHelper.generateViewId())
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Logger.i("搜索聊天记录");
//                    }
//                });
        //
        QMUIStatusBarHelper.translucent(this);
    }

    /**
     * 界面初始化
     */
    private void initView () {

        mMembersFloatLayout = findViewById(R.id.bytedesk_group_profile_floatlayout);
        mMembersFloatLayout.setOnLineCountChangeListener(new QMUIFloatLayout.OnLineCountChangeListener() {
            @Override
            public void onChange(int oldLineCount, int newLineCount) {
                Logger.i("oldLineCount = " + oldLineCount + " ;newLineCount = " + newLineCount);
            }
        });
        //
        mGroupListView = findViewById(R.id.bytedesk_group_profile_groupListView);
        //
        nicknameItem = mGroupListView.createItemView("群名称");
        nicknameItem.setDetailText("未命名");
        nicknameItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        //
        qrCodeItem = mGroupListView.createItemView("群二维码");
        qrCodeItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        //
        descriptionItem = mGroupListView.createItemView("群简介");
        descriptionItem.setDetailText("未设置");
        descriptionItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        //
        announcementItem = mGroupListView.createItemView("群公告");
        announcementItem.setDetailText("未设置");
        announcementItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        final Context context = this;
        QMUIGroupListView.newSection(this).addItemView(nicknameItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Logger.d("群聊名称");

            if (!mIsAdmin) {
                return;
            }

            // FIXME: android.view.InflateException: Binary XML file line #17: Error inflating class com.qmuiteam.qmui.widget.dialog.QMUIDialogView

            final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context);
            builder.setTitle("群聊名称")
                .setPlaceholder("在此输入群聊名称")
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
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        dialog.dismiss();

                        final String nickname = text.toString();

                        // TODO: 调用服务器接口
                        BDCoreApi.updateGroupNickname(context, gId, nickname, new BaseCallback() {
                            @Override
                            public void onSuccess(JSONObject object) {

                                nicknameItem.setDetailText(nickname);

                                // TODO: 更新本地群组会话thread昵称
                            }

                            @Override
                            public void onError(JSONObject object) {

                                Toast.makeText(getApplication(), "更新群昵称失败", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(getApplication(), "请填入群聊名称", Toast.LENGTH_SHORT).show();
                    }
                    }
                }).create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
            }
        }).addItemView(qrCodeItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  TODO: 群二维码
                Logger.i("群二维码");

            }
        }).addItemView(descriptionItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("群简介");

                if (!mIsAdmin) {
                    return;
                }

                // FIXME: android.view.InflateException: Binary XML file line #17: Error inflating class com.qmuiteam.qmui.widget.dialog.QMUIDialogView

                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context);
                builder.setTitle("群简介")
                        .setPlaceholder("在此输入群描述")
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
                                CharSequence text = builder.getEditText().getText();
                                if (text != null && text.length() > 0) {
                                    dialog.dismiss();

                                    final String description = text.toString();
                                    // TODO: 调用服务器接口
                                    BDCoreApi.updateGroupDescription(context, gId, description, new BaseCallback() {

                                        @Override
                                        public void onSuccess(JSONObject object) {

                                            descriptionItem.setDetailText(description);
                                        }

                                        @Override
                                        public void onError(JSONObject object) {

                                            Toast.makeText(getApplication(), "更新群简介失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {
                                    Toast.makeText(getApplication(), "请填入群公告", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();

            }
        }).addItemView(announcementItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("群公告");

            if (!mIsAdmin) {
                return;
            }

            // FIXME: android.view.InflateException: Binary XML file line #17: Error inflating class com.qmuiteam.qmui.widget.dialog.QMUIDialogView

            final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context);
            builder.setTitle("群公告")
                .setPlaceholder("在此输入群公告")
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
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        dialog.dismiss();

                        final String announcement = text.toString();
                        // TODO: 调用服务器接口
                        BDCoreApi.updateGroupAnnouncement(context, gId, announcement, new BaseCallback() {

                            @Override
                            public void onSuccess(JSONObject object) {

                                announcementItem.setDetailText(announcement);
                            }

                            @Override
                            public void onError(JSONObject object) {

                                Toast.makeText(getApplication(), "更新群公告失败", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(getApplication(), "请填入群公告", Toast.LENGTH_SHORT).show();
                    }
                    }
                }).create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();

            }
        }).addTo(mGroupListView);


    }

    /**
     * 初始化ModelView
     *
     * 加载群成员
     */
    private void initModel () {

        BDCoreApi.getGroupDetail(this, gId, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                try {

                    JSONObject dataObject = object.getJSONObject("data");
                    //
                    String nickname = dataObject.getString("nickname");
                    nicknameItem.setDetailText(nickname);
                    //
                    String description = dataObject.getString("description");
                    descriptionItem.setDetailText(description);
                    //
                    String announcement = dataObject.getString("announcement");
                    announcementItem.setDetailText(announcement);
                    //
                    JSONArray membersArray = dataObject.getJSONArray("members");
                    //
                    for (int i = 0; i < membersArray.length(); i++) {
                        JSONObject memberObject = membersArray.getJSONObject(i);
                        //
                        String realName = memberObject.getString("realName");
                        String avatarUrl = memberObject.getString("avatar");
                        //
                        addItemToFloatLayout(realName, avatarUrl);
                    }
                    // TODO: 待完善
                    // addPlusItemToFloatLayout();
                    //
                    JSONArray adminsArray = dataObject.getJSONArray("admins");
                    for (int i = 0; i < adminsArray.length(); i++) {
                        JSONObject adminObject = adminsArray.getJSONObject(i);
                        //
                        String adminUid = adminObject.getString("uid");
                        if (adminUid.equals(mPreferenceManager.getUid())) {
                            mIsAdmin = true;
                            addAdminItemToLayout();
                            // TODO: 待完善
                            // addMinusItemToFloatLayout();
                        }
                    }

                    if (!mIsAdmin) {
                        addWithdrawItemToLayout();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(JSONObject object) {
                Toast.makeText(getApplicationContext(), "加载群成员失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addWithdrawItemToLayout() {
        final Context context = this;
        withdrawItem = mGroupListView.createItemView("退出群");
        QMUIGroupListView.newSection(this).addItemView(withdrawItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("退出群");

                new QMUIDialog.MessageDialogBuilder(context)
                        .setTitle("退出群")
                        .setMessage("确定要退群吗？")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction(0, "退群", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {

                                // TODO: 调用退群接口
                                BDCoreApi.withdrawGroup(context, gId, new BaseCallback() {

                                    @Override
                                    public void onSuccess(JSONObject object) {

                                        // TODO: 删除本地会话，关闭会话窗口，直接退出到thread会话列表

                                        Toast.makeText(getApplication(), "退群成功", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(JSONObject object) {

                                        Toast.makeText(getApplication(), "退群失败", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                dialog.dismiss();
                            }
                        }).show();
            }
        }).addTo(mGroupListView);
    }

    private void addAdminItemToLayout() {

        // 暂不添加
        // transferItem = mGroupListView.createItemView("移交群");
        dismissItem = mGroupListView.createItemView("解散群");

        final Context context = this;
        QMUIGroupListView.newSection(this).addItemView(dismissItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("解散群");

                new QMUIDialog.MessageDialogBuilder(context)
                    .setTitle("解散群")
                    .setMessage("确定要解散吗？")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .addAction(0, "解散", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {

                            // TODO: 调用服务器接口
                            BDCoreApi.dismissGroup(context, gId, new BaseCallback() {

                                @Override
                                public void onSuccess(JSONObject object) {

                                    // TODO: 删除本地group、及thread

                                    Toast.makeText(getApplication(), "解散成功", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(JSONObject object) {

                                    Toast.makeText(getApplication(), "解散失败", Toast.LENGTH_SHORT).show();
                                }
                            });

                            dialog.dismiss();
                        }
                    }).show();

            }
        }).addTo(mGroupListView);
    }

    private void addItemToFloatLayout(String realName, final String avatarUrl) {

        //
        QMUIRadiusImageView avatarImageView = new QMUIRadiusImageView(this);
        Glide.with(this).load(avatarUrl).into(avatarImageView);
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("avatar clicked:" + avatarUrl);
            }
        });
        avatarImageView.setBorderWidth(0);

        //


        //
        TextView textView = new TextView(this);
        textView.setText(realName);

        int textViewSize = QMUIDisplayHelper.dpToPx(60);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(textViewSize, textViewSize);
        mMembersFloatLayout.addView(avatarImageView, lp);
    }

    private void addPlusItemToFloatLayout() {

        //
        QMUIRadiusImageView avatarImageView = new QMUIRadiusImageView(this);
        avatarImageView.setImageResource(R.drawable.bytedesk_group_plus);
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("plus clicked:");
                //
                Intent intent = new Intent(GroupProfileActivity.this, ContactActivity.class);
                intent.putExtra(BDUiConstant.EXTRA_UID, gId);
                startActivity(intent);
            }
        });
        avatarImageView.setBorderWidth(0);

        int textViewSize = QMUIDisplayHelper.dpToPx(60);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(textViewSize, textViewSize);
        mMembersFloatLayout.addView(avatarImageView, lp);
    }

    private void addMinusItemToFloatLayout() {

        //
        QMUIRadiusImageView avatarImageView = new QMUIRadiusImageView(this);
        avatarImageView.setImageResource(R.drawable.bytedesk_group_minus);
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("minus clicked:");
            }
        });
        avatarImageView.setBorderWidth(0);

        int textViewSize = QMUIDisplayHelper.dpToPx(65);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(textViewSize, textViewSize);
        mMembersFloatLayout.addView(avatarImageView, lp);
    }

    private void removeItemFromFloatLayout(QMUIFloatLayout floatLayout) {
        if (floatLayout.getChildCount() == 0) {
            return;
        }
        floatLayout.removeView(floatLayout.getChildAt(floatLayout.getChildCount() - 1));
    }


}
