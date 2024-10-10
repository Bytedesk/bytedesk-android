package com.bytedesk.demo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.bytedesk.im.core.http.BytedeskApi;
import com.bytedesk.im.ui.api.BDUiApi;

/**
 */
public class ChatTypeActivity extends AppCompatActivity {

    Toolbar mTopBar;
    ListView mGroupListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_type_activity);
        //
        mTopBar = findViewById(R.id.topbar);
        mGroupListView = findViewById(R.id.groupListView);

        initTopBar();
        initGroupListView();
    }

    private void initTopBar() {
        //
        mTopBar.setTitle("会话类型");
    }

    private void initGroupListView() {
        //
        // 填充ListView
        // 创建一个简单的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new String[] {
                        "1.技能组对话",
                        "2.指定客服对话",
                });

        mGroupListView.setAdapter(adapter);

        mGroupListView.setOnItemClickListener((parent, view, position, id) -> {
            // 在这里处理点击事件
            // 例如，根据位置启动不同的Fragment
            switch (position) {
                case 0:
                    // 获取技能组uid，登录后台->客服->渠道->android
                    // http://www.weiyuai.cn/admin/cs/channel
                    BytedeskApi.startWorkGroupChatActivity(this,  "df_wg_uid");
                    break;
                case 1:
                    // 获取指定客服uid，登录后台->客服->渠道->android
                    // http://www.weiyuai.cn/admin/cs/channel
                    BytedeskApi.startAgentChatActivity(this,  "df_ag_uid");
                    break;
                default:
                    break;
            }
        });
    }


}
