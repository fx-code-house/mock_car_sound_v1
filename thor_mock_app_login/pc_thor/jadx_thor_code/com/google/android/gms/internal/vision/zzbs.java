package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzbs {
    private final boolean zzhf;

    public zzbs(zzbv zzbvVar) {
        zzde.checkNotNull(zzbvVar, "BuildInfo must be non-null");
        this.zzhf = !zzbvVar.zzai();
    }

    public final boolean zzg(String str) {
        zzde.checkNotNull(str, "flagName must not be null");
        if (this.zzhf) {
            return zzbu.zzhh.get().containsValue(str);
        }
        return true;
    }
}
