package com.thor.businessmodule.crypto;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import kotlin.Metadata;

/* compiled from: EncryptionHelper.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/crypto/EncryptionHelper;", "", "()V", "crypto", "Lcom/thor/businessmodule/crypto/CryptoManager;", "getCrypto", "()Lcom/thor/businessmodule/crypto/CryptoManager;", "generateIVH", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class EncryptionHelper {
    public static final EncryptionHelper INSTANCE = new EncryptionHelper();
    private static final CryptoManager crypto = new CryptoManager();

    private EncryptionHelper() {
    }

    public final CryptoManager getCrypto() {
        return crypto;
    }

    public final byte[] generateIVH() {
        byte[] bArr = new byte[Cipher.getInstance("AES/CBC/PKCS5Padding").getBlockSize() / 2];
        new SecureRandom().nextBytes(bArr);
        return bArr;
    }
}
