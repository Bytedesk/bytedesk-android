package com.bytedesk.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bytedesk.core.api.BDCoreApi;
import com.bytedesk.core.callback.BaseCallback;
import com.bytedesk.ui.R;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 常用语列表
 *
 * @author bytedesk.com
 */
public class CuwActivity extends AppCompatActivity {

    private QMUITopBarLayout mTopBar;
    private QMUIGroupListView mGroupListView;

    private String mTitle = "常用语";
    private QMUIGroupListView.Section mCuwSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bytedesk_activity_cuw);
        //
        mTopBar = findViewById(R.id.bytedesk_support_category_topbar);
        mGroupListView = findViewById(R.id.bytedesk_support_category_groupListView);
        //
        QMUIStatusBarHelper.translucent(this);
        //
        initTopBar();
        initGroupListView();
        // 加载常用语
        getCuws();
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        // TODO: 下一版添加
//        mTopBar.addRightImageButton(R.mipmap.icon_topbar_plus2, QMUIViewHelper.generateViewId()).setOnClickListener(view -> {
//            Logger.i("add cuw");
//        });
        mTopBar.setTitle(mTitle);
    }

    private void initGroupListView() {
        //
        mCuwSection = QMUIGroupListView.newSection(this);
    }

    private String getDetail(String type, String content) {
        //
        if (type.equals("text")) {
            return "[文字]" + content;
        } else if (type.equals("image")) {
            return "[图片]";
        } else if (type.equals("file")) {
            return "[文件]";
        } else if (type.equals("voice")) {
            return "[语音]";
        } else if (type.equals("video")) {
            return "[视频]";
        }
        return "[文字]" + content;
    }

    private void getCuws() {

        BDCoreApi.getCuws(this, new BaseCallback() {
            @Override
            public void onSuccess(JSONObject object) {

                try {

                    JSONArray mineArray = object.getJSONObject("data").getJSONArray("mine");
                    for (int i = 0; i < mineArray.length(); i++) {
                        JSONObject category = mineArray.getJSONObject(i);
                        JSONArray cuwChildren = category.getJSONArray("cuwChildren");
                        for (int j = 0; j < cuwChildren.length(); j++) {
                            JSONObject cuw = cuwChildren.getJSONObject(j);
//                            Long id = cuw.getLong("id");
//                            String cid = cuw.getString("cid");
                            String type = cuw.getString("type");
                            String name = cuw.getString("name");
                            String content = cuw.getString("content");
//                            Logger.i("mine type %s, name: %s, content: %s", type, name, content);
                            //
                            QMUICommonListItemView articleItem = mGroupListView.createItemView(name);
//                            String detail = type.equals("text") ? "[文字]" + content : (type.equals("image") ? "[图片]": "["+type+"]");
                            articleItem.setDetailText(getDetail(type, content));
                            mCuwSection.addItemView(articleItem, v -> {
                                //
                                Intent intent = new Intent();
                                intent.putExtra("type", type);
                                intent.putExtra("content", content);
                                setResult(1, intent);
                                finish();
                            });
                        }
                    }

                    JSONArray companyArray = object.getJSONObject("data").getJSONArray("company");
                    for (int i = 0; i < companyArray.length(); i++) {
                        JSONObject category = companyArray.getJSONObject(i);
                        JSONArray cuwChildren = category.getJSONArray("cuwChildren");
                        for (int j = 0; j < cuwChildren.length(); j++) {
                            JSONObject cuw = cuwChildren.getJSONObject(j);
//                            Long id = cuw.getLong("id");
//                            String cid = cuw.getString("cid");
                            String type = cuw.getString("type");
                            String name = cuw.getString("name");
                            String content = cuw.getString("content");
//                            Logger.i("company type %s, name: %s, content: %s", type, name, content);
                            //
                            QMUICommonListItemView articleItem = mGroupListView.createItemView(name);
//                            String detail = type.equals("text") ? "[文字]" + content : "[图片]" + content;
                            articleItem.setDetailText(getDetail(type, content));
                            mCuwSection.addItemView(articleItem, v -> {
                                //
                                Intent intent = new Intent();
                                intent.putExtra("type", type);
                                intent.putExtra("content", content);
                                setResult(1, intent);
                                finish();
                            });
                        }
                    }

                    JSONArray platformArray = object.getJSONObject("data").getJSONArray("platform");
                    for (int i = 0; i < platformArray.length(); i++) {
                        JSONObject category = platformArray.getJSONObject(i);
                        JSONArray cuwChildren = category.getJSONArray("cuwChildren");
                        for (int j = 0; j < cuwChildren.length(); j++) {
                            JSONObject cuw = cuwChildren.getJSONObject(j);
//                            Long id = cuw.getLong("id");
//                            String cid = cuw.getString("cid");
                            String type = cuw.getString("type");
                            String name = cuw.getString("name");
                            String content = cuw.getString("content");
//                            Logger.i("platform type %s, name: %s, content: %s", type, name, content);
                            //
                            QMUICommonListItemView articleItem = mGroupListView.createItemView(name);
//                            String detail = type.equals("text") ? "[文字]" + content : "[图片]" + content;
                            articleItem.setDetailText(getDetail(type, content));
                            mCuwSection.addItemView(articleItem, v -> {
                                //
                                Intent intent = new Intent();
                                intent.putExtra("type", type);
                                intent.putExtra("content", content);
                                setResult(1, intent);
                                finish();
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mCuwSection.addTo(mGroupListView);
            }

            @Override
            public void onError(JSONObject object) {

            }
        });
    }

}
