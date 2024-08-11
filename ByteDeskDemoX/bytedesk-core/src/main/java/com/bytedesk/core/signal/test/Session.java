package com.bytedesk.core.signal.test;

import java.nio.charset.Charset;

/**
 * @author bytedesk.com on 2019/3/13
 */
public class Session {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private /* static */ enum Operation { ENCRYPT, DECRYPT; }

//    private final SignalProtocolStore self;
//    private PreKeyBundle otherKeyBundle;
//    private SignalProtocolAddress otherAddress;
//    private Operation lastOp;
//    private SessionCipher cipher;
//
//    public Session(SignalProtocolStore self,
//                   PreKeyBundle otherKeyBundle,
//                   SignalProtocolAddress otherAddress)
//    {
//        this.self = self;
//        this.otherKeyBundle = otherKeyBundle;
//        this.otherAddress = otherAddress;
//    }
//
//    private synchronized SessionCipher getCipher(Operation operation) {
//        if (operation == lastOp) {
//            return cipher;
//        }
//
//        SignalProtocolAddress toAddress = otherAddress;
//        SessionBuilder builder = new SessionBuilder(self, toAddress);
//
//        try {
//            builder.process(otherKeyBundle);
//        } catch (InvalidKeyException | UntrustedIdentityException e) {
//            throw new RuntimeException(e);
//        }
//
//        this.cipher = new SessionCipher(self, toAddress);
//        this.lastOp = operation;
//
//        return cipher;
//    }
//
//    public PreKeySignalMessage encrypt(String message) {
//        SessionCipher cipher = getCipher(Operation.ENCRYPT);
//
//        CiphertextMessage ciphertext = null;
//        try {
//            ciphertext = cipher.encrypt(message.getBytes(UTF8));
//        } catch (UntrustedIdentityException e) {
//            e.printStackTrace();
//        }
//        byte[] rawCiphertext = ciphertext.serialize();
//
//        try {
//            PreKeySignalMessage encrypted = new PreKeySignalMessage(rawCiphertext);
//
//            return encrypted;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public String decrypt(PreKeySignalMessage ciphertext) {
//        SessionCipher cipher = getCipher(Operation.DECRYPT);
//
//        try {
//            byte[] decrypted = cipher.decrypt(ciphertext);
//
//            return new String(decrypted, UTF8);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
