package com.facebook.android.crypto.keychain;

import com.facebook.crypto.Conceal;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;

/* loaded from: classes.dex */
public class AndroidConceal extends Conceal {
    private static AndroidConceal sInstance;

    public static synchronized AndroidConceal get() {
        if (sInstance == null) {
            sInstance = new AndroidConceal();
        }
        return sInstance;
    }

    private AndroidConceal() {
        super(new SystemNativeCryptoLibrary(), new FixedSecureRandom());
    }
}
