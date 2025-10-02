package com.orhanobut.hawk;

import android.content.Context;
import android.util.Base64;
import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.facebook.crypto.Entity;

/* loaded from: classes2.dex */
class ConcealEncryption implements Encryption {
    private final Crypto crypto;

    public ConcealEncryption(Context context) {
        this.crypto = AndroidConceal.get().createDefaultCrypto(new SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256));
    }

    @Override // com.orhanobut.hawk.Encryption
    public boolean init() {
        return this.crypto.isAvailable();
    }

    @Override // com.orhanobut.hawk.Encryption
    public String encrypt(String str, String str2) throws Exception {
        return Base64.encodeToString(this.crypto.encrypt(str2.getBytes(), Entity.create(str)), 2);
    }

    @Override // com.orhanobut.hawk.Encryption
    public String decrypt(String str, String str2) throws Exception {
        Entity entityCreate = Entity.create(str);
        return new String(this.crypto.decrypt(Base64.decode(str2, 2), entityCreate));
    }
}
