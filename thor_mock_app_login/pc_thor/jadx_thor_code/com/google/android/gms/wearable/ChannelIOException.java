package com.google.android.gms.wearable;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public class ChannelIOException extends IOException {
    private final int zza;
    private final int zzb;

    public ChannelIOException(String str, int i, int i2) {
        super(str);
        this.zza = i;
        this.zzb = i2;
    }

    public int getAppSpecificErrorCode() {
        return this.zzb;
    }

    public int getCloseReason() {
        return this.zza;
    }
}
