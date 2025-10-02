package com.google.android.gms.internal.measurement;

import android.util.Log;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzdl extends zzdh<Boolean> {
    zzdl(zzdm zzdmVar, String str, Boolean bool, boolean z) {
        super(zzdmVar, str, bool, true, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzdh
    final /* synthetic */ Boolean zza(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (zzcp.zzb.matcher(str).matches()) {
                return true;
            }
            if (zzcp.zzc.matcher(str).matches()) {
                return false;
            }
        }
        String strZzb = super.zzb();
        String strValueOf = String.valueOf(obj);
        Log.e("PhenotypeFlag", new StringBuilder(String.valueOf(strZzb).length() + 28 + String.valueOf(strValueOf).length()).append("Invalid boolean value for ").append(strZzb).append(": ").append(strValueOf).toString());
        return null;
    }
}
