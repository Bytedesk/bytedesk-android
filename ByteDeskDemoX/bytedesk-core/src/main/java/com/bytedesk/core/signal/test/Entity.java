package com.bytedesk.core.signal.test;

/**
 * @author bytedesk.com on 2019/3/13
 */
public class Entity {

//    private final SignalProtocolStore store;
//    private final PreKeyBundle preKey;
//    private final SignalProtocolAddress address;
//
//    public Entity(int preKeyId, int signedPreKeyId, String address) throws InvalidKeyException
//    {
//        this.address = new SignalProtocolAddress(address, 1);
//        //
//        this.store = new InMemorySignalProtocolStore(KeyHelper.generateIdentityKeyPair(),
//                KeyHelper.generateRegistrationId(false));
//        //
//        IdentityKeyPair identityKeyPair = store.getIdentityKeyPair();
//        int registrationId = store.getLocalRegistrationId();
//        //
//        ECKeyPair preKeyPair = Curve.generateKeyPair();
//        ECKeyPair signedPreKeyPair = Curve.generateKeyPair();
//        //
//        int deviceId = 1;
//        long timestamp = System.currentTimeMillis();
//
//        byte[] signedPreKeySignature = Curve.calculateSignature(
//                identityKeyPair.getPrivateKey(),
//                signedPreKeyPair.getPublicKey().serialize());
//
//        IdentityKey identityKey = identityKeyPair.getPublicKey();
//        ECPublicKey preKeyPublic = preKeyPair.getPublicKey();
//        ECPublicKey signedPreKeyPublic = signedPreKeyPair.getPublicKey();
//
//        this.preKey = new PreKeyBundle(
//                registrationId,
//                deviceId,
//                preKeyId,
//                preKeyPublic,
//                signedPreKeyId,
//                signedPreKeyPublic,
//                signedPreKeySignature,
//                identityKey);
//
//        PreKeyRecord preKeyRecord = new PreKeyRecord(preKey.getPreKeyId(), preKeyPair);
//        SignedPreKeyRecord signedPreKeyRecord = new SignedPreKeyRecord(
//                signedPreKeyId, timestamp, signedPreKeyPair, signedPreKeySignature);
//
//        store.storePreKey(preKeyId, preKeyRecord);
//        store.storeSignedPreKey(signedPreKeyId, signedPreKeyRecord);
//    }
//
//    public SignalProtocolStore getStore() {
//        return store;
//    }
//
//    public PreKeyBundle getPreKey() {
//        return preKey;
//    }
//
//    public SignalProtocolAddress getAddress() {
//        return address;
//    }

}
