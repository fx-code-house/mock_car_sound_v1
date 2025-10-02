package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzhl implements zzcc<zzhk> {
    private static zzhl zzre = new zzhl();
    private final zzcc<zzhk> zzrf;

    public static boolean zzeb() {
        return ((zzhk) zzre.get()).zzeb();
    }

    private zzhl(zzcc<zzhk> zzccVar) {
        this.zzrf = zzcb.zza(zzccVar);
    }

    public zzhl() {
        this(zzcb.zzc(new zzhn()));
    }

    @Override // com.google.android.gms.internal.icing.zzcc
    public final /* synthetic */ zzhk get() {
        return this.zzrf.get();
    }
}
