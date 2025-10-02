package com.google.android.gms.internal.play_billing;

import org.mapstruct.ap.internal.util.MessageConstants;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzv {
    private final Object zza;
    private final Object zzb;
    private final Object zzc;

    zzv(Object obj, Object obj2, Object obj3) {
        this.zza = obj;
        this.zzb = obj2;
        this.zzc = obj3;
    }

    final IllegalArgumentException zza() {
        return new IllegalArgumentException("Multiple entries with same key: " + String.valueOf(this.zza) + "=" + String.valueOf(this.zzb) + MessageConstants.AND + String.valueOf(this.zza) + "=" + String.valueOf(this.zzc));
    }
}
