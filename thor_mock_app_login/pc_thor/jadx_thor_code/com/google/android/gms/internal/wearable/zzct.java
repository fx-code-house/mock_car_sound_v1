package com.google.android.gms.internal.wearable;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzct {
    private static final zzcs zza;
    private static final zzcs zzb;

    static {
        zzcs zzcsVar;
        try {
            zzcsVar = (zzcs) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzcsVar = null;
        }
        zza = zzcsVar;
        zzb = new zzcs();
    }

    static zzcs zza() {
        return zza;
    }

    static zzcs zzb() {
        return zzb;
    }
}
