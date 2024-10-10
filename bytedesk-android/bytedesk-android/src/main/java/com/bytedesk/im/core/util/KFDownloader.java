package com.bytedesk.im.core.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 金鹏 on 2016/9/15.
 */

public class KFDownloader {

    String m_fileUrl;
    String m_fileSavePath;

    KFDownloadListener downloadListener;

    public void setDownloadListener(KFDownloadListener listener) {
        this.downloadListener = listener;
    }

    public KFDownloader(String fileUrl, String fileSavePath) {
        this.m_fileUrl = fileUrl;
        this.m_fileSavePath = fileSavePath;
    }

    //文件保存路径..开始下载的方法
    public void start() {

        URL url = null;
        try {
            url = new URL(m_fileUrl);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setDoInput(true);
            urlCon.setRequestMethod("GET");
            urlCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            // 建立连接
            urlCon.connect();
            int length = urlCon.getContentLength();
            downloadListener.onStart(length);

            if (urlCon.getResponseCode() == 200) {

                File file = new File(m_fileSavePath);
                BufferedInputStream is = new BufferedInputStream(urlCon.getInputStream());
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));

                byte[] buffer = new byte[10240];
                int len = 0;
                int receivedBytes = 0;
                label:
                while(true)
                {
                    //这里如果暂停下载，并没有真正的销毁线程，而是处于等待状态
                    //但如果这时候用户退出了，要做处理，比如取消任务；或做其他处理
                    if(isPause) downloadListener.onPause();
                    if(isCancel) {
                        downloadListener.onCancel();
                        break label;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    while (!isPause && (len = is.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                        receivedBytes += len;
                        downloadListener.onProgress(receivedBytes);
                        if(receivedBytes == length){
                            downloadListener.onSuccess(file);
                            break label;
                        }
                        if(isCancel) {
                            downloadListener.onCancel();
                            file.delete();
                            break label;
                        }
                    }
                }

                is.close();
                out.close();
            } else {
                Log.e("jlf", "ResponseCode:" + urlCon.getResponseCode()
                        + ", msg:" + urlCon.getResponseMessage());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            downloadListener.onFail();
        } catch (IOException e) {
            e.printStackTrace();
            downloadListener.onFail();
        }
    }

    private boolean isPause;

    public void pause() {
        isPause = true;
    }

    public void resume() {
        isPause = false;
        isCancel = false;
        downloadListener.onResume();
    }

    private boolean isCancel;
    public void cancel()
    {
        isCancel = true;
    }

}
