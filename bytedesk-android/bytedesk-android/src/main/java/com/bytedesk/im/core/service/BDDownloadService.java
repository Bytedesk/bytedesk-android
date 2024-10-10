package com.bytedesk.im.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.bytedesk.im.core.util.BDCoreConstant;
import com.bytedesk.im.core.util.KFDownloadListener;
import com.bytedesk.im.core.util.KFDownloader;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * Created by 金鹏 on 2016/9/15.
 */

public class BDDownloadService extends Service {

    String fileUrl;
    String fileSavePath;

    // 下载进度
    private int progress = 0;

    public int getProgress() {
        return progress;
    }

    // 下载状态标志
    private int flag;

    public int getFlag() {
        return flag;
    }

    KFDownThread mThread;
    KFDownloader downloader;

    private static BDDownloadService instance;
    public static BDDownloadService getInstance() {
        return instance;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Logger.d("onCreate");

        instance = this;
        flag = BDCoreConstant.DOWNLOAD_FLAG_INIT;
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Logger.d("onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null)
            return super.onStartCommand(intent, flags, startId);

        Logger.d("onStartCommand");

        String msg = intent.getExtras().getString("flag");
        fileUrl = intent.getExtras().getString("fileurl");
        fileSavePath = intent.getExtras().getString("fileSavePath");
        //
        if (mThread == null){
            mThread = new KFDownThread();
        }
        //
        if(downloader == null) {
            downloader = new KFDownloader(fileUrl, fileSavePath);
        }
        downloader.setDownloadListener(downListener);

        if (msg.equals("start"))
        {
            startDownload();
        }
        else if (msg.equals("pause"))
        {
            downloader.pause();
        }
        else if (msg.equals("resume"))
        {
            downloader.resume();
        }
        else if (msg.equals("stop"))
        {
            downloader.cancel();
            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        if (flag == BDCoreConstant.DOWNLOAD_FLAG_INIT || flag == BDCoreConstant.DOWNLOAD_FLAG_PAUSE) {
            if (mThread != null && !mThread.isAlive()) {
                mThread = new KFDownThread();
            }
            mThread.start();
        }
    }

    @Override
    public void onDestroy() {
        Logger.d("onDestroy");

        try {
            flag = 0;
            mThread.join();
        } catch (InterruptedException e) {
//            e.printStackTrace();
            Logger.d(e.toString());
        }
        mThread = null;
        super.onDestroy();
    }

    class KFDownThread extends Thread {
        @Override
        public void run() {
            if (flag == BDCoreConstant.DOWNLOAD_FLAG_INIT || flag == BDCoreConstant.DOWNLOAD_FLAG_DONE) {
                flag = BDCoreConstant.DOWNLOAD_FLAG_DOWN;
            }
            downloader.start();
        }
    }

    private KFDownloadListener downListener = new KFDownloadListener() {

        int fileSize;
        Intent intent = new Intent();

        @Override
        public void onSuccess(File file) {
            Logger.d("download success");

            intent.setAction(BDCoreConstant.ACTION_DOWNLOAD_SUCCESS);
            intent.putExtra("progress", 100);
            intent.putExtra("file", file);
            sendBroadcast(intent);

            //
            stopSelf();
        }

        @Override
        public void onStart(int fileByteSize) {
            Logger.d("download start");

            fileSize = fileByteSize;
            flag = BDCoreConstant.DOWNLOAD_FLAG_DOWN;
        }

        @Override
        public void onResume() {
            Logger.d("download resume");

            flag = BDCoreConstant.DOWNLOAD_FLAG_DOWN;
        }

        @Override
        public void onProgress(int receivedBytes) {
            Logger.d("download progress");

            if(flag == BDCoreConstant.DOWNLOAD_FLAG_DOWN) {

                progress = (int)((receivedBytes / (float)fileSize) * 100);
                intent.setAction(BDCoreConstant.ACTION_DOWNLOAD_PROGRESS);
                intent.putExtra("progress", progress);
                sendBroadcast(intent);
                if (progress == 100) {
                    flag = BDCoreConstant.DOWNLOAD_FLAG_DONE;
                }

            }
        }

        @Override
        public void onPause() {
            Logger.d("download pause");

            flag = BDCoreConstant.DOWNLOAD_FLAG_PAUSE;
        }

        @Override
        public void onFail() {
            Logger.d("download failed");

            intent.setAction(BDCoreConstant.ACTION_DOWNLOAD_FAIL);
            sendBroadcast(intent);
            flag = BDCoreConstant.DOWNLOAD_FLAG_INIT;
        }

        @Override
        public void onCancel(){
            Logger.d("download cancel");

            progress = 0;
            flag = BDCoreConstant.DOWNLOAD_FLAG_INIT;
        }
    };


}
