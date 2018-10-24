package com.bytedesk.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bytedesk.core.api.BDMqttApi;
import com.bytedesk.core.event.MessageEvent;
import com.bytedesk.core.util.BDPreferenceManager;
import com.bytedesk.ui.util.BDUiConstant;
import com.bytedesk.core.api.BDCoreApi;
import com.bytedesk.core.callback.BaseCallback;
import com.bytedesk.core.room.entity.MessageEntity;
import com.bytedesk.core.util.BDCoreUtils;
import com.bytedesk.core.viewmodel.MessageViewModel;
import com.bytedesk.ui.R;
import com.bytedesk.ui.adapter.ChatAdapter;
import com.bytedesk.ui.listener.ChatItemClickListener;
import com.bytedesk.ui.util.BDPermissionUtils;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 *  用途：
 *  1. 访客聊天界面
 *  2. 客服 工作组、1v1、群聊 聊天界面
 *
 *  TODO:
 *    1. 访客关闭会话窗口的时候通知客服
 *    2. 客服端关闭会话之后，禁止访客继续发送消息
 *
 */
public class ChatActivity extends AppCompatActivity
        implements ChatItemClickListener, View.OnClickListener {

    private QMUITopBar mTopBar;
    private QMUIPullRefreshLayout mPullRefreshLayout;
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button mPlusButton;
    private Button mSendButton;
    private EditText mInputEditText;

    private MessageViewModel mMessageViewModel;

    private String mImageCaptureFileName;
    private String mPicturePath;
    private Uri mPhotoUri;

    private String uId;
    private String wId;
    private String tId;
    private String mTitle;
    private int mPage = 0;

    private BDPreferenceManager mPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bytedesk_activity_chat);
        //
        if (null != getIntent()) {
            uId = getIntent().getStringExtra(BDUiConstant.EXTRA_UID);
            wId = getIntent().getStringExtra(BDUiConstant.EXTRA_WID);
            mTitle = getIntent().getStringExtra(BDUiConstant.EXTRA_TITLE);
        }
        mPreferenceManager = BDPreferenceManager.getInstance(this);
        //
        mTopBar = findViewById(R.id.kfds_chat_topbar);
        mTopBar.setTitle(mTitle);
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                popBackStack();
                finish();
            }
        });
        //
        mPullRefreshLayout = findViewById(R.id.kfds_chat_pulltorefresh);
        mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {

            @Override
            public void onMoveTarget(int offset) {}

            @Override
            public void onMoveRefreshView(int offset) {}

            @Override
            public void onRefresh() {
                getMessages();
            }

        });

        // 初始化
        mRecyclerView = findViewById(R.id.kfds_chat_fragment_recyclerview);
        // 固定大小可以优化性能
//        mRecyclerView.setHasFixedSize(true);
        // 设置 LinearLayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // 设置适配器adapter
        mAdapter = new ChatAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        //
        mPlusButton = findViewById(R.id.kfds_chat_input_plus_button);
        mPlusButton.setOnClickListener(this);
        //
        mSendButton = findViewById(R.id.kfds_chat_input_send_button);
        mSendButton.setOnClickListener(this);
        mInputEditText = findViewById(R.id.kfds_chat_fragment_input);

        //
        mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        mMessageViewModel.getWorkgroupMessages(wId).observe(this, new Observer<List<MessageEntity>>() {
            @Override
            public void onChanged(@Nullable List<MessageEntity> messageEntities) {
            //
            mAdapter.setMessages(messageEntities);
            mRecyclerView.scrollToPosition(messageEntities.size() - 1);
            }
        });
        //
        startThread();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onClick(View view) {
        //
        if (view.getId() == R.id.kfds_chat_input_send_button) {
            //
            final String content = mInputEditText.getText().toString();
            if (content.trim().length() > 0) {

                // 发送文字消息
                BDMqttApi.sendTextMessage(this, content, tId);

                mInputEditText.setText(null);
            }
        }
        else if (view.getId() == R.id.kfds_chat_input_plus_button) {

            new QMUIBottomSheet.BottomListSheetBuilder(this)
                    .addItem("相册")
                    .addItem("拍照")
                    .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                        @Override
                        public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                            switch (position) {
                                case 0:
                                    Logger.d("album");
                                    requestAlbumPermission();
                                    break;
                                case 1:
                                    Logger.d("camera");
                                    requestCameraPermission();
                                    break;
                            }
                            dialog.dismiss();
                        }
                    })
                    .build().show();
        }

    }


    /**
     * 点击图片消息回调
     */
    @Override
    public void onMessageImageItemClick(String imageUrl) {
        Logger.d("imageUrl:" + imageUrl);

        Intent intent = new Intent(this, BigImageViewActivity.class);
        intent.putExtra("image_url", imageUrl);
        startActivity(intent);
    }


    /**
     * 请求相册读取权限
     */
    private void requestAlbumPermission() {

        // android 6.0动态授权机制
        // http://jijiaxin89.com/2015/08/30/Android-s-Runtime-Permission/
        // http://inthecheesefactory.com/blog/things-you-need-to-know-about-android-m-permission-developer-edition/en
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // 首先提示用户，待确认之后，请求用户授权
                new QMUIDialog.MessageDialogBuilder(this)
                        .setTitle("请求授权")
                        .setMessage("相册需要授权，请授权")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                // 请求授权
                                ActivityCompat.requestPermissions(ChatActivity.this,
                                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        BDUiConstant.PERMISSION_REQUEST_ALBUM);
                            }
                        })
                        .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
            }
            else {
                pickImageFromAlbum();
            }
        }
        else {
            pickImageFromAlbum();
        }
    }

    /**
     * 从相册中选择图片
     */
    private void pickImageFromAlbum() {

        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, BDUiConstant.SELECT_PIC_BY_PICK_PHOTO);
    }

    
    /**
     * 请求摄像头权限
     */
    private void requestCameraPermission() {

        // android 6.0动态授权机制
        // http://jijiaxin89.com/2015/08/30/Android-s-Runtime-Permission/
        // http://inthecheesefactory.com/blog/things-you-need-to-know-about-android-m-permission-developer-edition/en
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                
                // 首先提示用户，待确认之后，请求用户授权
                new QMUIDialog.MessageDialogBuilder(this)
                        .setTitle("请求授权")
                        .setMessage("拍照需要授权，请授权")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                // 请求授权
                                ActivityCompat.requestPermissions(ChatActivity.this,
                                        new String[] { Manifest.permission.CAMERA,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        BDUiConstant.PERMISSION_REQUEST_CAMERA);
                            }
                        })
                        .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
            }
            else {
                takeCameraImage();
            }
        }
        else {
            takeCameraImage();
        }
    }

    /**
     * 摄像头拍摄图片
     */
    private void takeCameraImage() {

        // TODO: 判断是否模拟器，如果是，则弹出tip提示，并返回

        //
        if (BDCoreUtils.isSDCardExist()) {
            //
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mImageCaptureFileName = mPreferenceManager.getUsername() + "_" + BDCoreUtils.getPictureTimestamp();
            mPhotoUri = BDCoreUtils.getUri(BDCoreUtils.getTempImage(mImageCaptureFileName), this);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mPhotoUri);
            startActivityForResult(intent, BDUiConstant.SELECT_PIC_BY_TAKE_PHOTO);
        }
        else {
            Toast.makeText(this, "SD卡不存在，不能拍照", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == BDUiConstant.SELECT_PIC_BY_PICK_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                //
                mImageCaptureFileName =  mPreferenceManager.getUsername() + "_" + BDCoreUtils.getPictureTimestamp() + ".jpg";
                if (data == null) {
                    Logger.d("data == null");
                    Toast.makeText(this, "appkefu_choose_picture_error", Toast.LENGTH_SHORT).show();
                    return;
                }

                mPhotoUri = data.getData();
                if (mPhotoUri == null) {
                    Logger.d("m_photourl == null");
                    Toast.makeText(this, "appkefu_choose_picture_error", Toast.LENGTH_SHORT).show();
                    return;
                }

                String[] pojo = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(mPhotoUri, pojo, null, null, null);
                if (cursor != null) {
                    Logger.d("cursor != null");
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                        mPicturePath = cursor.getString(columnIndex);
                    } else {
                        Logger.d("cursor.getCount() == 0");
                    }
                } else {
                    Logger.d("cursor == null");
                    mPicturePath = mPhotoUri.getPath();
                }
                //
                if (mPicturePath != null) {
                    Logger.d("mPicturePath != null");
                    uploadImage(mPicturePath, mImageCaptureFileName);
                } else {
                    try {
                        Bitmap photoBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mPhotoUri);
                        if (photoBitmap != null) {
                            //
                            mPicturePath = BDCoreUtils.savePhotoToSDCard(mImageCaptureFileName, photoBitmap);
                            if (mPicturePath != null) {
                                //
                                uploadImage(mPicturePath, mImageCaptureFileName);
                            } else {
                                Logger.d("mPicturePath == null, appkefu_choose_picture_error_4");
                            }
                            photoBitmap.recycle();
                        } else {
                            Logger.d("mPicturePath == null, appkefu_choose_picture_error");
                            Toast.makeText(this, "appkefu_choose_picture_error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        Logger.d(e.toString());
                    } catch (IOException e) {
                        Logger.d(e.toString());
                    }
                }
            }
        } else if (requestCode == BDUiConstant.SELECT_PIC_BY_TAKE_PHOTO) {

            if (resultCode == Activity.RESULT_OK) {
                //
                String filePath = BDCoreUtils.getTempImage(mImageCaptureFileName).getPath();
                String fileName = mImageCaptureFileName;
                uploadImage(filePath, fileName);
            }

        } 
    }

    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case BDUiConstant.PERMISSION_REQUEST_RECORD_AUDIO:
                if (BDPermissionUtils.verifyPermissions(grantResults)) {
                    // Permission Granted, 录音
                }
                else {
                    // Permission Denied
                    Toast.makeText(this, "录音授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case BDUiConstant.PERMISSION_REQUEST_CAMERA:
                if (BDPermissionUtils.verifyPermissions(grantResults)) {
                    // Permission Granted
                    takeCameraImage();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "拍照授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case BDUiConstant.PERMISSION_REQUEST_ALBUM:
                if (BDPermissionUtils.verifyPermissions(grantResults)) {
                    // Permission Granted
                    pickImageFromAlbum();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "相册授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    /**
     *
     * 请求会话
     *
     */
    private void startThread() {
        // 请求会话request thread
        BDCoreApi.visitorRequestThread(this, uId, wId, new BaseCallback() {
            @Override
            public void onSuccess(JSONObject object) {
                //
                try {
                    Logger.d("request thread success message: " + object.get("message")
                            + " status_code:" + object.get("status_code"));

                    int status_code = object.getInt("status_code");
                    if (status_code == 200) {
                        // 创建新会话

                        JSONObject message = object.getJSONObject("data");
                        mMessageViewModel.insertMessageJson(message);

                        tId = message.getJSONObject("thread").getString("tid");
                        String threadTopic = "thread/" + tId;
                        BDMqttApi.subscribeTopic(ChatActivity.this, threadTopic);

                    } else if (status_code == 201) {
                        // 继续进行中会话

                        JSONObject thread = object.getJSONObject("data");

                        tId = thread.getString("tid");
                        String threadTopic = "thread/" + tId;
                        BDMqttApi.subscribeTopic(ChatActivity.this, threadTopic);

                    } else if (status_code == 202) {
                        // 提示排队中

                        JSONObject message = object.getJSONObject("data");
                        mMessageViewModel.insertMessageJson(message);

                        tId = message.getJSONObject("thread").getString("tid");
                        String threadTopic = "thread/" + tId;
                        BDMqttApi.subscribeTopic(ChatActivity.this, threadTopic);

                    } else if (status_code == 203) {
                        // 当前非工作时间，请自助查询或留言

                        JSONObject message = object.getJSONObject("data");
                        mMessageViewModel.insertMessageJson(message);

                        tId = message.getJSONObject("thread").getString("tid");
                        String threadTopic = "thread/" + tId;
                        BDMqttApi.subscribeTopic(ChatActivity.this, threadTopic);

                    } else if (status_code == 204) {
                        // 当前无客服在线，请自助查询或留言

                        JSONObject message = object.getJSONObject("data");
                        mMessageViewModel.insertMessageJson(message);

                        tId = message.getJSONObject("thread").getString("tid");
                        String threadTopic = "thread/" + tId;
                        BDMqttApi.subscribeTopic(ChatActivity.this, threadTopic);

                    } else {
                        // 请求会话失败

                        JSONObject message = object.getJSONObject("data");
                        String tip = message.getString("message");
                        Toast.makeText(ChatActivity.this, tip, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject object) {
                try {
                    Logger.d("request thread message: " + object.get("message")
                            + " status_code:" + object.get("status_code")
                            + " data:" + object.get("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 从服务器加载聊天记录
     */
    private void getMessages() {

        BDCoreApi.getMessageWithUser(getBaseContext(), mPage, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                try {

                    JSONArray jsonArray = object.getJSONObject("data").getJSONArray("content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        mMessageViewModel.insertMessageJson(jsonArray.getJSONObject(i));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mPullRefreshLayout.finishRefresh();
                mPage++;
            }

            @Override
            public void onError(JSONObject object) {

                mPullRefreshLayout.finishRefresh();
            }
        });

        // TODO: 客服端根据访客id获取其全部聊天记录

    }


    /**
     * 上传并发送图片
     *
     *  {"message":"upload image",
     *  "status_code":200,
     *  "data":"http://chainsnow.oss-cn-shenzhen.aliyuncs.com/images/201808281417141_20180829105542.jpg"}
     *
     * @param filePath
     * @param fileName
     */
    private void uploadImage(String filePath, String fileName) {

        BDCoreApi.uploadImage(this, filePath, fileName, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                try {

                    String image_url = object.getString("data");
                    BDMqttApi.sendImageMessage(ChatActivity.this, image_url, tId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject object) {
                Toast.makeText(getApplicationContext(), "上传图片失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 监听 EventBus 广播消息
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
//        Logger.d("message received: " + messageEvent.getMessage());

        try {
            JSONObject jsonObject = new JSONObject(messageEvent.getMessage());
            mMessageViewModel.insertMessageJson(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}








