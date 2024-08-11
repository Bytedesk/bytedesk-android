package com.bytedesk.core.signal.storage;

//public class SignalProtocolStoreImpl implements SignalProtocolStore {
//
//  private final PreKeyStore       preKeyStore;
//  private final SignedPreKeyStore signedPreKeyStore;
//  private final IdentityKeyStore  identityKeyStore;
//  private final SessionStore      sessionStore;
//
//  public SignalProtocolStoreImpl(Context context) {
//    this.preKeyStore       = new BDPreKeyStore(context);
//    this.signedPreKeyStore = new BDSignedPreKeyStore(context);
//    this.identityKeyStore  = new BDIdentityKeyStore(context);
//    this.sessionStore      = new BDSessionStore(context);
//  }
//
//  @Override
//  public IdentityKeyPair getIdentityKeyPair() {
//    return identityKeyStore.getIdentityKeyPair();
//  }
//
//  @Override
//  public int getLocalRegistrationId() {
//    return identityKeyStore.getLocalRegistrationId();
//  }
//
//  @Override
//  public boolean saveIdentity(SignalProtocolAddress address, IdentityKey identityKey) {
//    return identityKeyStore.saveIdentity(address, identityKey);
//  }
//
//  @Override
//  public boolean isTrustedIdentity(SignalProtocolAddress address, IdentityKey identityKey, Direction direction) {
//    return identityKeyStore.isTrustedIdentity(address, identityKey, direction);
//  }
//
//  @Override
//  public IdentityKey getIdentity(SignalProtocolAddress address) {
//    return identityKeyStore.getIdentity(address);
//  }
//
//  @Override
//  public PreKeyRecord loadPreKey(int preKeyId) throws InvalidKeyIdException {
//    return preKeyStore.loadPreKey(preKeyId);
//  }
//
//  @Override
//  public void storePreKey(int preKeyId, PreKeyRecord record) {
//    preKeyStore.storePreKey(preKeyId, record);
//  }
//
//  @Override
//  public boolean containsPreKey(int preKeyId) {
//    return preKeyStore.containsPreKey(preKeyId);
//  }
//
//  @Override
//  public void removePreKey(int preKeyId) {
//    preKeyStore.removePreKey(preKeyId);
//  }
//
//  @Override
//  public SessionRecord loadSession(SignalProtocolAddress axolotlAddress) {
//    return sessionStore.loadSession(axolotlAddress);
//  }
//
//  @Override
//  public List<Integer> getSubDeviceSessions(String number) {
//    return sessionStore.getSubDeviceSessions(number);
//  }
//
//  @Override
//  public void storeSession(SignalProtocolAddress axolotlAddress, SessionRecord record) {
//    sessionStore.storeSession(axolotlAddress, record);
//  }
//
//  @Override
//  public boolean containsSession(SignalProtocolAddress axolotlAddress) {
//    return sessionStore.containsSession(axolotlAddress);
//  }
//
//  @Override
//  public void deleteSession(SignalProtocolAddress axolotlAddress) {
//    sessionStore.deleteSession(axolotlAddress);
//  }
//
//  @Override
//  public void deleteAllSessions(String number) {
//    sessionStore.deleteAllSessions(number);
//  }
//
//  @Override
//  public SignedPreKeyRecord loadSignedPreKey(int signedPreKeyId) throws InvalidKeyIdException {
//    return signedPreKeyStore.loadSignedPreKey(signedPreKeyId);
//  }
//
//  @Override
//  public List<SignedPreKeyRecord> loadSignedPreKeys() {
//    return signedPreKeyStore.loadSignedPreKeys();
//  }
//
//  @Override
//  public void storeSignedPreKey(int signedPreKeyId, SignedPreKeyRecord record) {
//    signedPreKeyStore.storeSignedPreKey(signedPreKeyId, record);
//  }
//
//  @Override
//  public boolean containsSignedPreKey(int signedPreKeyId) {
//    return signedPreKeyStore.containsSignedPreKey(signedPreKeyId);
//  }
//
//  @Override
//  public void removeSignedPreKey(int signedPreKeyId) {
//    signedPreKeyStore.removeSignedPreKey(signedPreKeyId);
//  }
//}
