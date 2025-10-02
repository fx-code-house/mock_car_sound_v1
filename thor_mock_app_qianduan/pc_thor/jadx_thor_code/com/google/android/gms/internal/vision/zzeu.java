package com.google.android.gms.internal.vision;

import java.util.AbstractMap;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzeu<K, V> extends zzee<Map.Entry<K, V>> {
    private final /* synthetic */ zzer zznp;

    zzeu(zzer zzerVar) {
        this.zznp = zzerVar;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    public final boolean zzcu() {
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zznp.size;
    }

    @Override // java.util.List
    public final /* synthetic */ Object get(int i) {
        zzde.zzd(i, this.zznp.size);
        int i2 = i * 2;
        return new AbstractMap.SimpleImmutableEntry(this.zznp.zznd[i2], this.zznp.zznd[i2 + 1]);
    }
}
