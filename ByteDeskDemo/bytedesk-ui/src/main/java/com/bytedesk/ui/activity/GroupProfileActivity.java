package com.bytedesk.ui.activity;

import android.content.Context;
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
import com.bytedesk.ui.R;
import com.bytedesk.ui.util.BDUiConstant;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
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
 *
 * @author bytedesk.com on 2018/11/22
 */
public class GroupProfileActivity extends AppCompatActivity {

    private QMUITopBarLayout mTopBar;
    private QMUIFloatLayout mMembersFloatLayout;
    private QMUIGroupListView mGroupListView;

    private String gId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bytedesk_activity_group_profile);

        //
        gId = getIntent().getStringExtra(BDUiConstant.EXTRA_UID);

        //
        initTopBar();
        initView();
        initModel();
    }


    private void initTopBar() {
        //
        mTopBar = findViewById(R.id.bytedesk_group_profile_topbarlayout);
        mTopBar.setTitle("聊天信息");
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
        mTopBar.addRightImageButton(R.mipmap.icon_topbar_overflow, QMUIViewHelper.generateViewId())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Logger.i("搜索聊天记录");
                    }
                });
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
        QMUICommonListItemView nicknameItem = mGroupListView.createItemView("群聊名称");
        nicknameItem.setDetailText("未命名");
        nicknameItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView announcementItem = mGroupListView.createItemView("群公告");
        announcementItem.setDetailText("未设置");
        announcementItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        final Context context = this;
        QMUIGroupListView.newSection(this).addItemView(nicknameItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("群聊名称");

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

                                } else {
                                    Toast.makeText(getApplication(), "请填入群聊名称", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
            }
        }).addItemView(announcementItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("群公告");

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

                                } else {
                                    Toast.makeText(getApplication(), "请填入群公告", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
            }
        }).addTo(mGroupListView);

        //
        QMUICommonListItemView transferItem = mGroupListView.createItemView("移交群");
        QMUICommonListItemView withdrawItem = mGroupListView.createItemView("退出群");
        QMUICommonListItemView dismissItem = mGroupListView.createItemView("解散群");

        QMUIGroupListView.newSection(this).addItemView(transferItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("移交群");

            }
        }).addItemView(withdrawItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("退出群");

                new QMUIDialog.MessageDialogBuilder(context)
                        .setTitle("退出群")
                        .setMessage("确定要删除吗？")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction(0, "删除", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                Toast.makeText(getApplication(), "删除成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).show();

            }
        }).addItemView(dismissItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("解散群");

                new QMUIDialog.MessageDialogBuilder(context)
                        .setTitle("解散群")
                        .setMessage("确定要删除吗？")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction(0, "删除", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                Toast.makeText(getApplication(), "删除成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).show();
            }
        }).addTo(mGroupListView);

        //
//        QMUICommonListItemView clearMessageItem = mGroupListView.createItemView("清空聊天记录");
//        QMUIGroupListView.newSection(this).addItemView(clearMessageItem, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Logger.d("清空聊天记录");
//
//                new QMUIDialog.MessageDialogBuilder(context)
//                        .setTitle("标题")
//                        .setMessage("确定要删除吗？")
//                        .addAction("取消", new QMUIDialogAction.ActionListener() {
//                            @Override
//                            public void onClick(QMUIDialog dialog, int index) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .addAction(0, "删除", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
//                            @Override
//                            public void onClick(QMUIDialog dialog, int index) {
//                                Toast.makeText(getApplication(), "删除成功", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                        }).show();
//
//            }
//        }).addTo(mGroupListView);
    }

    /**
     * 初始化ModelView
     *
     * 加载群成员
     */
    private void initModel () {

        BDCoreApi.agentGroupMembers(this, gId, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                try {

                    JSONObject dataObject = object.getJSONObject("data");
                    JSONArray membersArray = dataObject.getJSONArray("members");
                    JSONArray adminsArray = dataObject.getJSONArray("admins");
                    //
                    for (int i = 0; i < membersArray.length(); i++) {
                        JSONObject memberObject = membersArray.getJSONObject(i);
                        //
                        String realName = memberObject.getString("realName");
                        String avatarUrl = memberObject.getString("avatar");
                        //
                        addItemToFloatLayout(realName, avatarUrl);
                    }

                    //
                    addPlusItemToFloatLayout();

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
            }
        });

        int textViewSize = QMUIDisplayHelper.dpToPx(60);
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
