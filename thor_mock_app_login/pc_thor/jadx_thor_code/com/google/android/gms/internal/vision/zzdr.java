package com.google.android.gms.internal.vision;

import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzdr<K, V> extends zzdw<Map.Entry<K, V>> {
    private final /* synthetic */ zzdp zzmo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdr(zzdp zzdpVar) {
        super(zzdpVar, null);
        this.zzmo = zzdpVar;
    }

    @Override // com.google.android.gms.internal.vision.zzdw
    final /* synthetic */ Object zzu(int i) {
        return new zzdy(this.zzmo, i);
    }
}
