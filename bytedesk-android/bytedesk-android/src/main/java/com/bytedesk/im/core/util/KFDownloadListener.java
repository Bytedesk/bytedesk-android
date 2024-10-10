package com.bytedesk.im.core.util;

import java.io.File;

/**
 * Created by 金鹏 on 2016/9/15.
 */

public interface KFDownloadListener {

    void onStart(int fileByteSize);

    void onPause();

    void onResume();

    void onProgress(int receivedBytes);

    void onFail();

    void onSuccess(File file);

    void onCancel();

}
