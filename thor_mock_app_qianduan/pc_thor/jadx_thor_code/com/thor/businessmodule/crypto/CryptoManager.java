package com.thor.businessmodule.crypto;

import kotlin.Metadata;

/* compiled from: CryptoManager.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0086 J)\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0086 ¨\u0006\u000e"}, d2 = {"Lcom/thor/businessmodule/crypto/CryptoManager;", "", "()V", "coreAesEncrypt", "", "data", "coreAesInit", "", "iv", "hardware_version", "", "firmware_version", "serial_number", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class CryptoManager {
    public final native byte[] coreAesEncrypt(byte[] data);

    public final native void coreAesInit(byte[] iv, int hardware_version, int firmware_version, int serial_number);

    static {
        System.loadLibrary("bridge");
    }
}
