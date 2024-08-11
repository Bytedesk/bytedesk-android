//package com.bytedesk.core.util;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.UnsupportedEncodingException;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import android.util.Base64;
//
//import static com.bytedesk.core.util.NativeLibUtils.keyFromJNI;
//
//
///**
// * @author bytedesk.com on 2019-08-31
// */
//public class AESCipher {
//
//
//    private static final String IV_STRING = "A-16-Byte-String";
//    private static final String charset = "UTF-8";
//
//    public static String aesEncryptString(String content) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
//        byte[] contentBytes = content.getBytes(charset);
//        byte[] keyBytes = keyFromJNI().getBytes(charset);
//        byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes);
////        Base64.Encoder encoder = Base64.getEncoder();
////        return encoder.encodeToString(encryptedBytes);
//        return  Base64.encodeToString(encryptedBytes, android.util.Base64.NO_WRAP);
//    }
//
//    public static String aesDecryptString(String content) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
////        Base64.Decoder decoder = Base64.getDecoder();
////        byte[] encryptedBytes = decoder.decode(content);
//        byte[] encryptedBytes = Base64.decode(content, android.util.Base64.NO_WRAP);
//        byte[] keyBytes = keyFromJNI().getBytes(charset);
//        byte[] decryptedBytes = aesDecryptBytes(encryptedBytes, keyBytes);
//        return new String(decryptedBytes, charset);
//    }
//
//    public static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
//        return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
//    }
//
//    public static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
//        return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
//    }
//
//    private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
//
//        byte[] initParam = IV_STRING.getBytes(charset);
//        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
//
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(mode, secretKey, ivParameterSpec);
//
//        return cipher.doFinal(contentBytes);
//    }
//
//}
