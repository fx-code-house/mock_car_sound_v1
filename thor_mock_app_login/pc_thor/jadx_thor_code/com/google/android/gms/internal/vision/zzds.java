package com.google.android.gms.internal.vision;

/* JADX INFO: Add missing generic type declarations: [K] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzds<K> extends zzdw<K> {
    private final /* synthetic */ zzdp zzmo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzds(zzdp zzdpVar) {
        super(zzdpVar, null);
        this.zzmo = zzdpVar;
    }

    @Override // com.google.android.gms.internal.vision.zzdw
    final K zzu(int i) {
        return (K) this.zzmo.zzmi[i];
    }
}
