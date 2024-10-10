package com.bytedesk.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class BDMainActivity extends AppCompatActivity {

    ListView mGroupListView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mGroupListView = findViewById(R.id.groupListView);

        initGroupListView();
    }

    private void initGroupListView() {

        // 填充ListView
        // 创建一个简单的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new String[] {
                        "1.联系客服",
                });

        mGroupListView.setAdapter(adapter);

        mGroupListView.setOnItemClickListener((parent, view, position, id) -> {
            // 在这里处理点击事件
            // 例如，根据位置启动不同的Fragment
            switch (position) {
                case 0:
                startActivity(new Intent(BDMainActivity.this, ChatTypeActivity.class));
                    break;
                default:
                    break;
            }
        });
    }

}
