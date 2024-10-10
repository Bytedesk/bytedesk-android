package com.bytedesk.im.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bytedesk.im.core.api.BDCoreApi;
import com.bytedesk.im.core.api.BytedeskConstants;
import com.bytedesk.im.core.api.BytedeskStompApi;
import com.bytedesk.im.core.callback.BaseCallback;
import com.bytedesk.im.core.http.BytedeskApi;
import com.bytedesk.im.ui.listener.ChatItemClickListener;
import com.bytedesk.im.ui.util.BDUiConstant;
import com.bytedesk.im.core.event.PreviewEvent;
import com.bytedesk.im.core.event.QueryAnswerEvent;
import com.bytedesk.im.core.event.SendCommodityEvent;
import com.bytedesk.im.core.repository.BDRepository;
import com.bytedesk.im.core.util.BDCoreConstant;
import com.bytedesk.im.core.util.BDCoreUtils;
import com.bytedesk.im.core.util.BDPreferenceManager;
import com.bytedesk.im.core.viewmodel.MessageViewModel;
import com.bytedesk.im.R;
import com.bytedesk.im.ui.adapter.ChatAdapter;
import com.bytedesk.im.ui.util.BDPermissionUtils;
import com.bytedesk.im.ui.widget.InputAwareLayout;
import com.bytedesk.im.ui.widget.KeyboardAwareLinearLayout;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author bytedesk.com
 */
public class ChatKFActivity extends ChatBaseActivity implements ChatItemClickListener,
        KeyboardAwareLinearLayout.OnKeyboardShownListener,
        KeyboardAwareLinearLayout.OnKeyboardHiddenListener,
        View.OnClickListener {

//    private Toolbar mTopBar;
    private SwipeRefreshLayout mPullRefreshLayout;
    private RecyclerView mRecyclerView;
    private ChatAdapter mChatAdapter;
    private EditText mInputEditText;
    private MessageViewModel mMessageViewModel;
//
    private String mSid;
    private String mThreadTopic;
    private final Handler mHandler = new Handler();
//    private String mCustom;
//    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
//    private final Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bytedesk_activity_chat_kf);
        //
        if (null != getIntent()) {
            mPreferenceManager = BDPreferenceManager.getInstance(this);
            mPreferenceManager.setVisitor(true);
            mRepository = BDRepository.getInstance(this);
            //
            mSid = getIntent().getStringExtra(BDUiConstant.EXTRA_SID);
            mType = getIntent().getStringExtra(BDUiConstant.EXTRA_TYPE);
//            mTitle = getIntent().getStringExtra(BDUiConstant.EXTRA_TITLE);
            assert mType != null;
            mThreadTopic = BytedeskApi.formatThreadTopic(mType, mSid, mPreferenceManager.getUid());
        }
        //
        initTopBar();
        initView();
        initModel();
        // 访客端请求会话
        requestThread(false);
        // 从服务器端加载聊天记录，默认暂不加载
        getMessages();
//        initTimerTask();
    }

    /**
     * TODO: 客服端输入框显示常用回复按钮
     */
    @Override
    public void onClick(View view) {
        //
        if (view.getId() == R.id.bytedesk_chat_input_send_button) {
            //
            final String content = mInputEditText.getText().toString();
            sendMessage(content, BytedeskConstants.MESSAGE_TYPE_TEXT);
            mInputEditText.setText(null);
        }
        else if (view.getId() == R.id.bytedesk_chat_input_plus_button) {
            // TODO: 上传图片
            requestAlbumPermission();

//            new QMUIBottomSheet.BottomListSheetBuilder(this)
//                .addItem("相册")
//                .addItem("拍照")
//                .addItem("上传视频")
//                .addItem("录制视频")
//                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
//                    switch (position) {
//                        case 0:
//                            Logger.d("album");
//                            requestAlbumPermission();
//                            break;
//                        case 1:
//                            Logger.d("camera");
//                            requestCameraPermission();
//                            break;
//                        case 2:
//                            Logger.d("上传视频");
//                            requestAlbumVideoPermission();
//                            break;
//                        case 3:
//                            Logger.d("录制视频");
//                            requestCameraVideoPermission();
//                            break;
//                    }
//                    dialog.dismiss();
//                })
//                .build().show();
        }
    }

    private void initTopBar() {
        //
//        mTopBar = findViewById(R.id.bytedesk_chat_topbarlayout);
//        mTopBar.setTitle("Chat");
//        if (!BDMqttApi.isConnected(this)) {
//            mTopBar.setTitle(mTitle+"(未连接)");
//        } else {
//            mTopBar.setTitle(mTitle);
//        }
//        mTopBar.addLeftBackImageButton().setOnClickListener(view -> finish());
        //
//            // 访客会话
//            mTopBar.addRightImageButton(R.mipmap.icon_topbar_overflow, QMUIViewHelper.generateViewId())
//                    .setOnClickListener(view -> visitorTopRightSheet());
    }

    /**
     * 界面初始化
     */
    private void initView () {
        //
        InputAwareLayout mInputAwaireLayout = findViewById(R.id.bytedesk_activity_chat_kf);
        mInputAwaireLayout.addOnKeyboardShownListener(this);
        mInputAwaireLayout.addOnKeyboardHiddenListener(this);
        //
        mPullRefreshLayout = findViewById(R.id.bytedesk_chat_pulltorefresh);
//        mPullRefreshLayout.setOnPullListener(pullListener);
        // TODO: 增加点击聊天界面，去除输入框焦点，让其缩回底部
        // 初始化
        mRecyclerView = findViewById(R.id.bytedesk_chat_fragment_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // 设置适配器adapter
        mChatAdapter = new ChatAdapter(this, this);
        mRecyclerView.setAdapter(mChatAdapter);

        // 选择图片、拍照
        Button mPlusButton = findViewById(R.id.bytedesk_chat_input_plus_button);
        mPlusButton.setOnClickListener(this);

        // 发送文本消息
        Button mSendButton = findViewById(R.id.bytedesk_chat_input_send_button);
        mSendButton.setOnClickListener(this);
        mInputEditText = findViewById(R.id.bytedesk_chat_fragment_input);
        mInputEditText.addTextChangedListener(inputTextWatcher);

        // 图片大图预览
//        imagePreview = findViewById(R.id.bytedesk_image_preivew);
//        mScreenSize = ImageViewerUtil.getScreenSize(this);
//        imagePreview.setDefSize(mScreenSize.x, mScreenSize.y);
//        imagePreview.setImageDraggerType(ImageDraggerType.DRAG_TYPE_WX);
    }

    /**
     * 初始化ModelView
     *
     * TODO: 完善收发消息界面出现闪动的情况
     */
    private void initModel () {
        //
        mMessageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        // FIXME: 当工作组设置有值班工作组的情况下，则界面无法显示值班工作组新消息
        mMessageViewModel.getThreadMessages(mThreadTopic).observe(this, messageEntities -> {
                mChatAdapter.setMessages(messageEntities);
                mRecyclerView.scrollToPosition(messageEntities.size() - 1);
        });
    }

//    private void initTimerTask() {
//        //
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                getMessages();
//            }
//        };
//        timer.schedule(task,0,10000);
//    }

    /**
     * 请求会话
     * 请求工作组会话和指定客服会话统一接口
     */
    private void requestThread(boolean forceAgent) {

        BytedeskApi.requestThread(this, mType, mSid, forceAgent, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

                dealWithThread(object);
            }

            @Override
            public void onError(JSONObject object) {
                try {
                    Logger.d("request thread message: " + object.get("message")
                            + " code:" + object.get("code")
                            + " data:" + object.get("data"));
                    Toast.makeText(ChatKFActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
                    // TODO: token过期，要求重新登录
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
//        Logger.i("getMessages 访客端");
        mPullRefreshLayout.setRefreshing(false);
//            34213536, 34213713, 34213891
            //
//            BDCoreApi.getMessagesWithUser(getBaseContext(), mPage, mSize, new BaseCallback() {
//                @Override
//                public void onSuccess(JSONObject object) {
//                    try {
//                        JSONArray jsonArray = object.getJSONObject("data").getJSONArray("content");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            mMessageViewModel.insertMessageJson(jsonArray.getJSONObject(i));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
////                    mPullRefreshLayout.finishRefresh();
//                    mPage++;
//                }
//
//                @Override
//                public void onError(JSONObject object) {
//
////                    mPullRefreshLayout.finishRefresh();
//
//                    try {
//                        Toast.makeText(ChatKFActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
    }

    /**
     * 处理thread返回结果
     */
    private void dealWithThread(JSONObject object) {
        //
        try {
            Logger.d("request thread success message: "
                    + object.get("message")
                    + " code:" + object.get("code"));
//            mIsRobot = false;
            int code = object.getInt("code");
            if (code == 200) {
                // 创建新会话

                JSONObject message = object.getJSONObject("data");
                mMessageViewModel.insertMessageJson(message);

                JSONObject thread = message.getJSONObject("thread");
                mThreadEntity.setUid(thread.getString("uid"));
                mThreadEntity.setTopic(thread.getString("topic"));
                mThreadEntity.setType(thread.getString("type"));
                mThreadEntity.setStatus(thread.getString("status"));
                mThreadEntity.setExtra(thread.getString("extra"));

                mThreadEntity.setUserUid(thread.getJSONObject("user").getString("uid"));
                mThreadEntity.setUserNickname(thread.getJSONObject("user").getString("nickname"));
                mThreadEntity.setUserAvatar(thread.getJSONObject("user").getString("avatar"));

                BytedeskStompApi.getInstance(this).subscribe(mThreadEntity.getTopic());

//                if (mCustom != null && mCustom.trim().length() > 0) {
//                    sendCommodityMessage(mCustom);
//                }

            } else {
                // 请求会话失败
                String message = object.getString("message");
                Toast.makeText(ChatKFActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送文本消息
     */
    private void sendMessage(String content, String type) {
        Logger.d("send text message content:%s", content);
        if (TextUtils.isEmpty(content)) {
            return;
        }
        // 自定义本地消息id，用于判断消息发送状态. 消息通知或者回调接口中会返回此id
        final String uid = BDCoreUtils.uuid();
        mRepository.insertTextMessageLocal(uid, content, mThreadTopic);
        //
        JSONObject messageObject = new JSONObject();
        try {
            //
            messageObject.put("uid", uid);
            messageObject.put("type", type);
            messageObject.put("content", content);
            messageObject.put("status", BytedeskConstants.MESSAGE_STATUS_SENDING);
            messageObject.put("createdAt", BDCoreUtils.currentDate());
            messageObject.put("client", BytedeskConstants.HTTP_CLIENT);
//
            JSONObject extra = new JSONObject();
            extra.put("orgUid", mPreferenceManager.getOrgUid());
            messageObject.put("extra", extra);
//
            JSONObject threadObject = new JSONObject();
            threadObject.put("uid", mThreadEntity.getUid());
            threadObject.put("topic", mThreadEntity.getTopic());
            threadObject.put("type", mThreadEntity.getType());
            threadObject.put("status", mThreadEntity.getStatus());
            JSONObject threadUserObject = new JSONObject();
            threadUserObject.put("uid", mThreadEntity.getUserUid());
            threadUserObject.put("nickname", mThreadEntity.getUserNickname());
            threadUserObject.put("avatar", mThreadEntity.getUserAvatar());
            threadObject.put("user", threadUserObject);
            messageObject.put("thread", threadObject);
            //
            JSONObject userObject = new JSONObject();
            userObject.put("uid", mPreferenceManager.getUid());
            userObject.put("nickname", mPreferenceManager.getNickname());
            userObject.put("avatar", mPreferenceManager.getAvatar());
            messageObject.put("user", userObject);
            //
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonContent = messageObject.toString();
        Logger.d("send message rest %s", jsonContent);
        if (BytedeskStompApi.getInstance(this).isConnected()) {
            BytedeskStompApi.getInstance(this).sendMessage(jsonContent);
        } else {
            BytedeskApi.sendRestMessage(this, jsonContent, new BaseCallback() {
                @Override
                public void onSuccess(JSONObject object) {
                    // 插入本地消息
//                    mRepository.insertTextMessageLocal(mUUID, mWorkGroupWid, mUid, content, uid, mThreadType);
                }
                @Override
                public void onError(JSONObject object) {
                    Toast.makeText(getApplicationContext(), "网络断开，请稍后重试", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * 从相册中选择图片
     * FIXME: 当前版本，禁止选择视频文件
     */
    private void pickImageFromAlbum() {
        // 目前仅允许一次选一张图片
        Album.image(this)
                .singleChoice()
                .camera(false)
                .onResult(result -> {
                    if (!result.isEmpty()) {
                        AlbumFile albumFile = result.get(0);
                        uploadImage(albumFile.getPath());
                    }
                })
                .onCancel(result -> {
                    Toast.makeText(ChatKFActivity.this, "取消发送图片", Toast.LENGTH_LONG).show();
                })
                .start();

        // TODO: 待删除
//        Intent intent;
//        if (Build.VERSION.SDK_INT < 19) {
//            intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//        } else {
//            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        }
//        startActivityForResult(intent, BDUiConstant.SELECT_PIC_BY_PICK_PHOTO);
    }

    /**
     * 摄像头拍摄图片
     *
     * FIXME: 当前版本，禁止拍摄视频，仅支持拍照
     */
    private void takeCameraImage() {
        // TODO: 判断是否模拟器，如果是，则弹出tip提示，并返回
        // 调用第三方库album
        Album.camera(this)
                .image()
                .onResult(this::uploadImage)
                .onCancel(result -> {
                    Toast.makeText(ChatKFActivity.this, "取消拍照", Toast.LENGTH_LONG).show();
                })
                .start();

        // TODO: 待删除
//        if (BDCoreUtils.isSDCardExist()) {
//            //
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            mImageCaptureFileName = mPreferenceManager.getUsername() + "_" + BDCoreUtils.getPictureTimestamp();
//            mPhotoUri = BDCoreUtils.getUri(BDCoreUtils.getTempImage(mImageCaptureFileName), this);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
//            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mPhotoUri);
//            startActivityForResult(intent, BDUiConstant.SELECT_PIC_BY_TAKE_PHOTO);
//        }
//        else {
//            Toast.makeText(this, "SD卡不存在，不能拍照", Toast.LENGTH_SHORT).show();
//        }

    }

    private void pickVideoFromAlbum() {
        Logger.i("pickVideoFromAlbum");

        // 目前仅允许一次选一张视频
        Album.video(this)
                .singleChoice()
                .camera(false)
                .onResult(result -> {

                    if (result.size() > 0) {
                        AlbumFile albumFile = result.get(0);
                        String videoName = BDCoreUtils.getPictureTimestamp()  + "_" + mPreferenceManager.getUsername()  + ".mp4";
                        uploadVideo(albumFile.getPath(), videoName);
                    }
                })
                .onCancel(result -> {
                    //
                    Toast.makeText(ChatKFActivity.this, "取消发送视频", Toast.LENGTH_LONG).show();
                })
                .start();

    }

    private void takeCameraVideo() {
        Logger.i("takeCameraVideo");

        // TODO: 判断是否模拟器，如果是，则弹出tip提示，并返回

        // 调用第三方库album
        Album.camera(this)
                .video()
                .onResult(result -> {

                    String videoFileName = BDCoreUtils.getPictureTimestamp()  + "_" + mPreferenceManager.getUsername() + ".mp4";
                    uploadVideo(result, videoFileName);
                })
                .onCancel(result -> {
                    //
                    Toast.makeText(ChatKFActivity.this, "取消录像", Toast.LENGTH_LONG).show();
                })
                .start();

    }


    private void visitorTopRightSheet() {
        //
        Context contextActivity = this;
//        new QMUIBottomSheet.BottomListSheetBuilder(this)
//                .addItem("评价")
//                .addItem("留言")
//                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
//                    //
//                    dialog.dismiss();
//
//                    if (position == 0) {
//
//                        final String[] items = new String[]{"非常满意", "满意", "一般", "不满意", "非常差"};
//                        final int checkedIndex = 0;
//                        new QMUIDialog.CheckableDialogBuilder(contextActivity)
//                                .setCheckedIndex(checkedIndex)
//                                .addItems(items, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Toast.makeText(getApplicationContext(), "评分：" + (5-which), Toast.LENGTH_SHORT).show();
//
//                                        // TODO: 暂未增加评价页面，开发者可自行设计。具体参数意思可跟进函数定义查看
//                                        BDCoreApi.rate(contextActivity, mUUID, 5 - which, "附言", false, new BaseCallback() {
//                                            @Override
//                                            public void onSuccess(JSONObject object) {
//
//                                            }
//                                            @Override
//                                            public void onError(JSONObject object) {
//
//                                            }
//                                        });
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .create(mCurrentDialogStyle)
//                                .show();
//
//                    } else if (position == 1) {
//
//                        // 跳转留言页面
//                        Intent intent = new Intent(ChatKFActivity.this, LeaveMessageActivity.class);
//                        intent.putExtra(BDUiConstant.EXTRA_WID, mWorkGroupWid);
//                        intent.putExtra(BDUiConstant.EXTRA_REQUEST_TYPE, mRequestType);
//                        intent.putExtra(BDUiConstant.EXTRA_AID, mAgentUid);
//                        startActivity(intent);
//                    }
//                })
//                .build()
//                .show();
    }

    /**
     * 客服端点击右上角按钮
     */
    private void showTopRightSheet() {
//        new QMUIBottomSheet.BottomListSheetBuilder(this)
//                .addItem("关闭会话")
////                .addItem("访客资料") // TODO: 查看访客资料
//                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
//                    dialog.dismiss();
//                    //
//                    BDCoreApi.agentCloseThread(getApplication(), mUUID, new BaseCallback() {
//
//                        @Override
//                        public void onSuccess(JSONObject object) {
//                            // 关闭页面
//                            finish();
//                        }
//
//                        @Override
//                        public void onError(JSONObject object) {
//                            Toast.makeText(getApplication(), "关闭会话错误", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                })
//                .build()
//                .show();
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
                // 请求授权
                ActivityCompat.requestPermissions(ChatKFActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    BDUiConstant.PERMISSION_REQUEST_ALBUM);

                // 首先提示用户，待确认之后，请求用户授权
//                new QMUIDialog.MessageDialogBuilder(this)
//                        .setTitle("请求授权")
//                        .setMessage("相册需要授权，请授权")
//                        .addAction("取消", (dialog, index) -> dialog.dismiss())
//                        .addAction("确定", (dialog, index) -> {
//                            dialog.dismiss();
//                            // 请求授权
//                            ActivityCompat.requestPermissions(ChatKFActivity.this,
//                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
//                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                    BDUiConstant.PERMISSION_REQUEST_ALBUM);
//                        }).show();
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
//                new QMUIDialog.MessageDialogBuilder(this)
//                        .setTitle("请求授权")
//                        .setMessage("拍照需要授权，请授权")
//                        .addAction("取消", (dialog, index) -> dialog.dismiss())
//                        .addAction("确定", (dialog, index) -> {
//                            dialog.dismiss();
//                            // 请求授权
//                            ActivityCompat.requestPermissions(ChatKFActivity.this,
//                                    new String[] { Manifest.permission.CAMERA,
//                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                    BDUiConstant.PERMISSION_REQUEST_CAMERA);
//                        }).show();
            }
            else {
                takeCameraImage();
            }
        }
        else {
            takeCameraImage();
        }
    }

    private void requestAlbumVideoPermission() {

        // android 6.0动态授权机制
        // http://jijiaxin89.com/2015/08/30/Android-s-Runtime-Permission/
        // http://inthecheesefactory.com/blog/things-you-need-to-know-about-android-m-permission-developer-edition/en
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // 首先提示用户，待确认之后，请求用户授权
//                new QMUIDialog.MessageDialogBuilder(this)
//                        .setTitle("请求授权")
//                        .setMessage("相册视频需要授权，请授权")
//                        .addAction("取消", (dialog, index) -> dialog.dismiss())
//                        .addAction("确定", (dialog, index) -> {
//                            dialog.dismiss();
//                            // 请求授权
//                            ActivityCompat.requestPermissions(ChatKFActivity.this,
//                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
//                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                    BDUiConstant.PERMISSION_REQUEST_ALBUM_VIDEO);
//                        }).show();
            }
            else {
                pickVideoFromAlbum();
            }
        }
        else {
            pickVideoFromAlbum();
        }
    }

    private void requestCameraVideoPermission() {

        // android 6.0动态授权机制
        // http://jijiaxin89.com/2015/08/30/Android-s-Runtime-Permission/
        // http://inthecheesefactory.com/blog/things-you-need-to-know-about-android-m-permission-developer-edition/en
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {

                // 首先提示用户，待确认之后，请求用户授权
//                new QMUIDialog.MessageDialogBuilder(this)
//                        .setTitle("请求授权")
//                        .setMessage("录像需要授权，请授权")
//                        .addAction("取消", (dialog, index) -> dialog.dismiss())
//                        .addAction("确定", (dialog, index) -> {
//                            dialog.dismiss();
//                            // 请求授权
//                            ActivityCompat.requestPermissions(ChatKFActivity.this,
//                                    new String[] { Manifest.permission.CAMERA,
//                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                    BDUiConstant.PERMISSION_REQUEST_CAMERA_VIDEO);
//                        }).show();
            }
            else {
                takeCameraVideo();
            }
        }
        else {
            takeCameraVideo();
        }
    }

    /**
     * 点击图片消息回调
     */
    @Override
    public void onMessageImageItemClick(String imageUrl) {
        Logger.d("imageUrl:" + imageUrl);

//        Glide.with(this).load(imageUrl).into(new SimpleTarget<Drawable>() {
//            @Override
//            public void onLoadCleared(@Nullable Drawable placeholder) {
//                super.onLoadCleared(placeholder);
//                imagePreview.getImageView().setImageDrawable(placeholder);
//            }
//
//            @Override
//            public void onLoadStarted(@Nullable Drawable placeholder) {
//                super.onLoadStarted(placeholder);
//                imagePreview.showProgess();
//                imagePreview.getImageView().setImageDrawable(placeholder);
//            }
//
//            @Override
//            public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                super.onLoadFailed(errorDrawable);
//                imagePreview.hideProgress();
//                imagePreview.getImageView().setImageDrawable(errorDrawable);
//            }
//
//            @Override
//            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                if (resource != null) {
//                    imagePreview.hideProgress();
//                    imagePreview.getImageView().setImageDrawable(resource);
////                    mViewData.setImageWidth(resource.getIntrinsicWidth());
////                    mViewData.setImageHeight(resource.getIntrinsicHeight());
//                }
//            }
//        });
//        imagePreview.setViewData(viewData);
//        imagePreview.start();

        Intent intent = new Intent(this, BigImageViewActivity.class);
        intent.putExtra("image_url", imageUrl);
        startActivity(intent);
    }

    @Override
    public void onMessageVideoItemClick(String videoUrl) {
        Logger.d("videoUrl:" + videoUrl);
//        Intent intent = new Intent(this, ChatVideoActivity.class);
//        intent.putExtra("video_url", videoUrl);
//        startActivity(intent);
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
                //
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
            case BDUiConstant.PERMISSION_REQUEST_CAMERA_VIDEO:
                //
                if (BDPermissionUtils.verifyPermissions(grantResults)) {
                    // Permission Granted
                    takeCameraVideo();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "录像授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case BDUiConstant.PERMISSION_REQUEST_ALBUM_VIDEO:
                if (BDPermissionUtils.verifyPermissions(grantResults)) {
                    // Permission Granted
                    pickVideoFromAlbum();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "相册视频授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 上传并发送图片
     */
    private void uploadImage(String filePath) {
        String fileName = BDCoreUtils.getPictureTimestamp()  + "_" + mPreferenceManager.getUid() ;
        Logger.i("upload image:", filePath);
        // TODO: 压缩，显示上传进度
//        final QMUITipDialog loadingDialog = new QMUITipDialog.Builder(this)
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
//                .setTipWord("上传中...")
//                .create();
//        loadingDialog.show();
        BytedeskApi.uploadFile(this, filePath, fileName, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {
//                loadingDialog.dismiss();
                try {
                    // 自定义本地消息id，用于判断消息发送状态。消息通知或者回调接口中会返回此id
//                    final String localId = BDCoreUtils.uuid();
                    String imageUrl = object.getString("data");
                    Logger.i("uploadFile imageUrl:", imageUrl);
                    sendMessage(imageUrl, BytedeskConstants.MESSAGE_TYPE_IMAGE);
                    // 插入本地消息
//                    mRepository.insertImageMessageLocal(mUUID, mSid, mUid, imageUrl, localId, mThreadType);
                    // 发送消息方式有两种：1. 异步发送消息，通过监听通知来判断是否发送成功，2. 同步发送消息，通过回调判断消息是否发送成功
//                    BDMqttApi.sendImageMessageProtobuf(ChatKFActivity.this, localId, imageUrl, mThreadEntity);
//                    BDMqttApi.sendImageMessageProtobuf(ChatKFActivity.this, localId, imageUrl,
//                            mUUID, mThreadEntity.getTopic(), mThreadEntity.getType(), mThreadEntity.getNickname(), mThreadEntity.getAvatar());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject object) {
//                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), "上传图片失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadVideo(String filePath, String fileName) {

        // TODO: 压缩，显示上传进度
//        final QMUITipDialog loadingDialog = new QMUITipDialog.Builder(this)
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
//                .setTipWord("上传中...")
//                .create();
//        loadingDialog.show();

        // TODO: 无客服在线时，禁止发送图片
        // TODO: 收到客服关闭会话 或者 自动关闭会话消息之后，禁止访客发送消息

        BDCoreApi.uploadVoice(this, filePath, fileName, new BaseCallback() {

            @Override
            public void onSuccess(JSONObject object) {

//                loadingDialog.dismiss();

                try {

                    // 自定义本地消息id，用于判断消息发送状态。消息通知或者回调接口中会返回此id
                    final String localId = BDCoreUtils.uuid();
                    String videoUrl = object.getString("data");
                    Logger.i("videoUrl:", videoUrl);

                    // 插入本地消息
//                    mRepository.insertVideoMessageLocal(mUUID, mSid, mUid, videoUrl, localId, mThreadType);
                    // 发送消息方式有两种：1. 异步发送消息，通过监听通知来判断是否发送成功，2. 同步发送消息，通过回调判断消息是否发送成功
//                    BDMqttApi.sendVideoMessageProtobuf(ChatKFActivity.this, localId, videoUrl, mThreadEntity);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject object) {

//                loadingDialog.dismiss();

                Toast.makeText(getApplicationContext(), "上传视频失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * TODO: 区分是否当前会话
     *
     * @param previewEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPreviewEvent(PreviewEvent previewEvent) {
        Logger.i("onPreviewEvent");

        if (previewEvent.getContent().trim().isEmpty()) {
            return;
        }

//        mTopBar.setTitle("对方正在输入...");
        mHandler.postDelayed(() -> {
//            mTopBar.setTitle(mTitle);
        }, 3000);
    }

    /**
     * 监听 EventBus 广播消息: 发送商品信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSendCommodityEvent(SendCommodityEvent sendCommodityEvent) {
        Logger.i("SendCommodityEvent");
//        if (mCustom != null && mCustom.trim().length() > 0) {
//            sendCommodityMessage(mCustom);
//        }
    }

    /**
     * 监听 EventBus 广播消息: 点击智能问答消息记录上面的问题
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onQueryAnswerEvent(QueryAnswerEvent queryAnswerEvent) {
        Logger.i("QueryAnswerEvent aid: %s, question %s", queryAnswerEvent.getAid(), queryAnswerEvent.getQuestion());

        if (queryAnswerEvent.getAid().equals("00001")) {

            // 请求人工客服
            requestThread(true);

        } else {

            // 请求服务器答案
//            BDCoreApi.queryAnswer(this, mUUID, queryAnswerEvent.getAid(), new BaseCallback() {
//
//                @Override
//                public void onSuccess(JSONObject object) {
//                    //
//                    try {
//
//                        int code = object.getInt("code");
//                        if (code == 200) {
//                            //
//                            JSONObject queryMessageObject = object.getJSONObject("data").getJSONObject("query");
//                            JSONObject replyMessageObject = object.getJSONObject("data").getJSONObject("reply");
//
//                            // TODO: 答案中添加 '有帮助'、'无帮助'，访客点击可反馈答案是否有用
//
//                            //持久化到数据库
//                            mRepository.insertMessageJson(queryMessageObject);
//                            mRepository.insertMessageJson(replyMessageObject);
////                            mRepository.insertRobotRightAnswerMessageJson(replyMessageObject);
//
//                        } else {
//
//                            // 发送消息失败
//                            String message = object.getString("message");
//                            Toast.makeText(ChatKFActivity.this, message, Toast.LENGTH_LONG).show();
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(JSONObject object) {
//
//                    try {
//                        Logger.d("robot message: " + object.get("message") + " code:" + object.get("code") + " data:" + object.get("data"));
//                        Toast.makeText(ChatKFActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            });
        }

    }

    /**
     * 下拉刷新
     */
//    private QMUIPullRefreshLayout.OnPullListener pullListener = new QMUIPullRefreshLayout.OnPullListener() {
//
//        @Override
//        public void onMoveTarget(int offset) {
//
//        }
//
//        @Override
//        public void onMoveRefreshView(int offset) {
//
//        }
//
//        @Override
//        public void onRefresh() {
//            Logger.i("refresh");
//            getMessages();
//        }
//    };

    /**
     * 监听输入框
     */
    private final TextWatcher inputTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // TODO: 查询常见问题，并高亮提示
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //
            String content = editable.toString();
            if (content != null) {
                // 输入框文字变化时，发送消息输入状态消息
//                BDMqttApi.sendPreviewMessageProtobuf(ChatKFActivity.this, mThreadEntity, content);
            }
        }
    };

    /**
     * 发送商品消息等
     * @param custom
     */
    private void sendCommodityMessage(String custom) {
        // 自定义本地消息id，用于判断消息发送状态. 消息通知或者回调接口中会返回此id
        final String localId = BDCoreUtils.uuid();
        // 插入本地消息
//        mRepository.insertCommodityMessageLocal(mUUID, mSid, mUid, custom, localId, mThreadType);
//        BDMqttApi.sendCommodityMessageProtobuf(this, localId, custom, mThreadEntity);
    }

    @Override
    public void onKeyboardHidden() {
//        Logger.i("onKeyboardHidden");
    }

    @Override
    public void onKeyboardShown() {
//        Logger.i("onKeyboardShown");
        mRecyclerView.scrollToPosition(mChatAdapter.getItemCount() - 1);
    }


    @Override
    public void onStart() {
        super.onStart();
        // 注册监听
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        // 销毁监听
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: 清理
//        timer.cancel();
    }



}


