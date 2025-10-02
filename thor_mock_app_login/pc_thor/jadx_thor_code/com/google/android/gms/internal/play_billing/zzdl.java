package com.google.android.gms.internal.play_billing;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzdl {
    private static final zzdk zza;
    private static final zzdk zzb;

    static {
        zzdk zzdkVar;
        try {
            zzdkVar = (zzdk) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzdkVar = null;
        }
        zza = zzdkVar;
        zzb = new zzdk();
    }

    static zzdk zza() {
        return zza;
    }

    static zzdk zzb() {
        return zzb;
    }
}
