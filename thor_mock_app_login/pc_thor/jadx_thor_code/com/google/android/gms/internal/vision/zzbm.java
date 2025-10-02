package com.google.android.gms.internal.vision;

import android.util.Base64;
import android.util.Log;
import java.io.IOException;

/* JADX INFO: Add missing generic type declarations: [T] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzbm<T> extends zzbi<T> {
    private final /* synthetic */ zzbp zzgs;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbm(zzbo zzboVar, String str, Object obj, boolean z, zzbp zzbpVar) {
        super(zzboVar, str, obj, true, null);
        this.zzgs = zzbpVar;
    }

    @Override // com.google.android.gms.internal.vision.zzbi
    final T zza(Object obj) {
        if (obj instanceof String) {
            try {
                return (T) this.zzgs.zzb(Base64.decode((String) obj, 3));
            } catch (IOException | IllegalArgumentException unused) {
            }
        }
        String strZzag = super.zzag();
        String strValueOf = String.valueOf(obj);
        Log.e("PhenotypeFlag", new StringBuilder(String.valueOf(strZzag).length() + 27 + String.valueOf(strValueOf).length()).append("Invalid byte[] value for ").append(strZzag).append(": ").append(strValueOf).toString());
        return null;
    }
}
