package com.bytedesk.core.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作工具包
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 */
public class BDFileUtils {
	
	public static boolean isSDCardExist() {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	public static boolean isFileExit(String filePath) {
		return new File(filePath).exists();
	}
	
	public static String getPictureWritePath(String filename) {
		String filePath = "";
		if(isSDCardExist()) {
			filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AppKeFu/Picture/";
			File fileDir = new File(filePath);
			if(!fileDir.exists()) {
				fileDir.mkdirs();
			}	
		}
		return filePath + filename;
	}


	public static String getVoiceFilePathFromUrl(String voiceUrl) {

		String[] urlParts = voiceUrl.split("/");
		String filename = "";
		if (urlParts.length > 0)
			filename = urlParts[urlParts.length - 1];

		return getVoiceWritePath(filename);
	}
	
	public static String getVoiceWritePath(String filename) {
		String filePath = "";
		if(isSDCardExist()) {
			filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + BDCoreConstant.SAMPLE_DEFAULT_DIR;
			File fileDir = new File(filePath);
			if(!fileDir.exists()) {
				fileDir.mkdirs();
			}
		}
		else {
			//如写到内存中，则直接返回文件名
			//http://blog.csdn.net/love_xiaozhao/article/details/6557504
			filePath = "";
		}
	
		return filePath + filename;
	}


	public static String getFilePathFromUrl(String fileUrl) {

		String[] urlParts = fileUrl.split("/");
		String filename = "";
		if (urlParts.length > 0)
			filename = urlParts[urlParts.length - 1];

		return getFileWritePath(filename);
	}

	public static String getFileWritePath(String filename) {
		String filePath = "";
		if(isSDCardExist()) {
			filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + BDCoreConstant.SAMPLE_DEFAULT_DIR;
			File fileDir = new File(filePath);
			if(!fileDir.exists()) {
				fileDir.mkdirs();
			}
		}
		else {
			//如写到内存中，则直接返回文件名
			//http://blog.csdn.net/love_xiaozhao/article/details/6557504
			filePath = "";
		}

		return filePath + filename;
	}
	
	public static String getAvatarWritePath(String filename) {
		String filePath = "";
		if(BDFileUtils.isSDCardExist()) {
			filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AppKeFu/Avatar/";
			File fileDir = new File(filePath);
			if(!fileDir.exists()) {
				fileDir.mkdirs();
			}	
		}
		else {
			filePath = "";
		}
		return filePath + filename + ".jpg";
	}
	
	public static String getSaveFilePath() {
		String path = getRootFilePath() + "appkefu/files/";
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


	// Storage Permissions
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE
	};

	/**
	 * Checks if the app has permission to write to device storage
	 * If the app does not has permission then the user will be prompted to grant permissions
	 * @param context
	 */
	public static void verifyStoragePermissions(Activity context) {
		// Check if we have write permission
		int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(
					context,
					PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE
			);
		}
	}

	public static void checkPermissionOrViewLargeImage(File imageFile, Activity context) {

		if (Build.VERSION.SDK_INT >= 23) {

			if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
			{

			}
		}
		else {
			viewLargeImage(imageFile, context);
		}

	}

	public static void viewLargeImage(File imageFile, Context context) {

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		//https://gold.xitu.io/entry/57bbb1de165abd00662b0f49
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//					intent.setDataAndType(Uri.fromFile(imageFile), "image/*");
		intent.setDataAndType(BDFileUtils.getUri(imageFile, context), "image/*");
		context.startActivity(intent);
	}

	public static Uri getUri(File file, Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return FileProvider.getUriForFile(context,
//						BuildConfig.APPLICATION_ID + ".provider",
					context.getApplicationContext().getPackageName() + ".provider",
					file);
		}
		else {
			return Uri.fromFile(file);
		}
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
	
	/**
	 * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @param context
	 * @param fileName
	 */
	public static void write(Context context, String fileName, String content) {
		if (content == null)
			content = "";

		try {
			FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
			fos.write(content.getBytes());

			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文本文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String read(Context context, String fileName) {
		try {
			FileInputStream in = context.openFileInput(fileName);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String readInStream(FileInputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}

			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i("FileTest", e.getMessage());
		}
		return null;
	}

	public static boolean createDirectory(String filePath) {
		if (null == filePath) {
			return false;
		}

		File file = new File(filePath);
		if (file.exists()){
			return true;
		}
		return file.mkdirs();
	}
	
	
	public static boolean deleteDirectory(String filePath) {
		if (null == filePath) {
			Logger.d("Invalid param. filePath: " + filePath);
			return false;
		}

		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return false;
		}

		if (file.isDirectory()) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				Logger.d("delete filePath: " + list[i].getAbsolutePath());
				if (list[i].isDirectory()) {
					deleteDirectory(list[i].getAbsolutePath());
				} else {
					list[i].delete();
				}
			}
		}

		Logger.d("delete filePath: " + file.getAbsolutePath());
		file.delete();
		return true;
	}

	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath: /data/data/
		}
	}


	public static String getRealPathFromURI(Context context, Uri contentUri) {
		String res = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
		if(null!=cursor&&cursor.moveToFirst()){
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			res = cursor.getString(column_index);
			cursor.close();
		}
		return res;
	}

	/**
	 * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
	 */
	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[]{split[1]};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context       The context.
	 * @param uri           The Uri to query.
	 * @param selection     (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
								String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {column};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

}