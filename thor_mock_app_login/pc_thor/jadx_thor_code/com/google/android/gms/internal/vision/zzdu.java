package com.google.android.gms.internal.vision;

/* JADX INFO: Add missing generic type declarations: [V] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzdu<V> extends zzdw<V> {
    private final /* synthetic */ zzdp zzmo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdu(zzdp zzdpVar) {
        super(zzdpVar, null);
        this.zzmo = zzdpVar;
    }

    @Override // com.google.android.gms.internal.vision.zzdw
    final V zzu(int i) {
        return (V) this.zzmo.zzmj[i];
    }
}
