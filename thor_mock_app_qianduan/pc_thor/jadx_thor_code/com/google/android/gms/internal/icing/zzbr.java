package com.google.android.gms.internal.icing;

import android.util.Log;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzbr extends zzbq<Boolean> {
    zzbr(zzbu zzbuVar, String str, Boolean bool) {
        super(zzbuVar, str, bool, null);
    }

    @Override // com.google.android.gms.internal.icing.zzbq
    final /* synthetic */ Boolean zza(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (zzax.zzbt.matcher(str).matches()) {
                return true;
            }
            if (zzax.zzbu.matcher(str).matches()) {
                return false;
            }
        }
        String strZzu = super.zzu();
        String strValueOf = String.valueOf(obj);
        Log.e("PhenotypeFlag", new StringBuilder(String.valueOf(strZzu).length() + 28 + String.valueOf(strValueOf).length()).append("Invalid boolean value for ").append(strZzu).append(": ").append(strValueOf).toString());
        return null;
    }
}
