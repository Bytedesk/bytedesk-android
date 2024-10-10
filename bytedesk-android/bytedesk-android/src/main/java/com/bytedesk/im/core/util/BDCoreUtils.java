package com.bytedesk.im.core.util;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;

import androidx.core.content.FileProvider;

import com.orhanobut.logger.Logger;
import com.bytedesk.im.core.api.BDConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author bytedesk.com on 2017/8/24.
 */

public class BDCoreUtils {

    /**
     * 登录二维码url
     *
     * @return string
     */
    public static String getQRCodeLogin(Context context) {
        return BDConfig.getInstance(context).getQRCodeBaseUrl() + "qrcode/login?code=" + BDCoreUtils.uuid();
    }

    /**
     * 用户个人资料二维码url
     *
     * @param uid uid
     * @return string
     */
    public static String getQRCodeUser(Context context, String uid) {
        return BDConfig.getInstance(context).getQRCodeBaseUrl() + "qrcode/user?uid=" + uid;
    }

    /**
     * 群组资料二维码url
     *
     * @param gid gid
     * @return string
     */
    public static String getQRCodeGroup(Context context, String gid) {
        return BDConfig.getInstance(context).getQRCodeBaseUrl() + "qrcode/group?gid=" + gid;
    }

    /**
     * UUID
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 实现文本复制功能
     */
    public static void copy(Context context, String content)
    {
        ClipData clipData = ClipData.newPlainText("Label", content);
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(clipData);
    }

    public static String compressImage(String filePath, String fileName) {

        String smallImagePath = getPictureWritePath(fileName);

        Logger.i("filePath:"  +filePath  + " smallImagePath:" + smallImagePath + " fileName:", fileName);

        try {
            Bitmap bm = getSmallBitmap(filePath);
            FileOutputStream fos = new FileOutputStream(new File(smallImagePath));
            bm.compress(Bitmap.CompressFormat.JPEG, 10, fos);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return smallImagePath;
    }

    public static boolean isSimulator() {
//        return SystemProperties.get("ro.kernel.qemu").equals("1");
        return true;
    }

    public static String currentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    @SuppressLint("MissingPermission")
    public static void doVibrate(Context context) {

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 100, 400 };
        vibrator.vibrate(pattern, -1);
    }

    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public static boolean isSDCardExist() {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static Uri getUri(File file, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider",
                    file);
        }
        else {
            return Uri.fromFile(file);
        }
    }

    public static String getSaveFilePath() {
        String path = getRootFilePath() + "bytedesk/files/";
        File fileDir = new File(path);
        if (!fileDir.exists())
            fileDir.mkdirs();
        return path;
    }

    public static File getTempImage(String fileName) {
        File tempFile = new File(getSaveFilePath(), fileName+".jpg");
        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public static String getRootFilePath() {
        if (isSDCardExist()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// filePath:/sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath: /data/data/
        }
    }


    public static String getPictureTimestamp() {
        return getVoiceTimestamp();
    }

    public static String getVoiceTimestamp() {
        //
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);

        return str;
    }

    /**Save image to the SD card, return image path**/
    public static String savePhotoToSDCard(String photoName, Bitmap photoBitmap) {
        File photoFile = new File(getSaveFilePath(), photoName); //在指定路径下创建文件
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(photoFile);
            if (photoBitmap != null) {
                if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                        fileOutputStream)) {
                    fileOutputStream.flush();
                }
            }
        } catch (FileNotFoundException e) {
            photoFile.delete();
            e.printStackTrace();
        } catch (IOException e) {
            photoFile.delete();
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  getSaveFilePath() + photoName;
    }


    public static String getPictureWritePath(String filename) {
        String filePath = "";
        if(isSDCardExist()) {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bd/image/";
            File fileDir = new File(filePath);
            if(!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }
        return filePath + filename;
    }

    public static boolean isNumeric (String str) {
        //
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param filePath
     * @return
     */

    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    public final static String getPackageName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public final static String getVersionName(Context context) {

        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public final static int getVersionCode(Context context) {

        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

}
