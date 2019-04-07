package com.bytedesk.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bytedesk.core.api.BDCoreApi;
import com.bytedesk.core.callback.BaseCallback;
import com.bytedesk.ui.R;
import com.bytedesk.ui.util.BDUiConstant;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.json.JSONObject;


public class LeaveMessageActivity extends AppCompatActivity {

    QMUITopBarLayout mTopBar;

    private String workGroupWid;
    private String type;
    private String agentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bytedesk_activity_leave_message);

        workGroupWid = getIntent().getStringExtra(BDUiConstant.EXTRA_WID);
        type = getIntent().getStringExtra(BDUiConstant.EXTRA_REQUEST_TYPE);
        agentUid = getIntent().getStringExtra(BDUiConstant.EXTRA_AID);

        initTopBar();
    }

    private void initTopBar() {
        //
        mTopBar = findViewById(R.id.bytedesk_leave_msg_topbarlayout);
        mTopBar.setTitle("留言");
        mTopBar.setBackgroundColor(getResources().getColor(R.color.albumColorPrimary));
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> finish());
        //
        QMUIStatusBarHelper.translucent(this);
    }


    /**
     * TODO: 待完善UI
     * 保存留言
     */
    private void save() {
        //
        BDCoreApi.leaveMessage(this, type, workGroupWid, agentUid,
                "mobile", "email", "content", new BaseCallback() {
                    @Override
                    public void onSuccess(JSONObject object) {

                    }

                    @Override
                    public void onError(JSONObject object) {

                    }
                });
    }







}
