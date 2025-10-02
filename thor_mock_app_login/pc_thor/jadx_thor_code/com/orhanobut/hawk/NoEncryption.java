package com.orhanobut.hawk;

import android.util.Base64;

/* loaded from: classes2.dex */
public class NoEncryption implements Encryption {
    @Override // com.orhanobut.hawk.Encryption
    public boolean init() {
        return true;
    }

    @Override // com.orhanobut.hawk.Encryption
    public String encrypt(String str, String str2) throws Exception {
        return encodeBase64(str2.getBytes());
    }

    @Override // com.orhanobut.hawk.Encryption
    public String decrypt(String str, String str2) throws Exception {
        return new String(decodeBase64(str2));
    }

    String encodeBase64(byte[] bArr) {
        return Base64.encodeToString(bArr, 0);
    }

    byte[] decodeBase64(String str) {
        return Base64.decode(str, 0);
    }
}
