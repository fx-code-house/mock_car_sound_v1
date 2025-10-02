package com.google.android.gms.internal.vision;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzer<K, V> extends zzej<Map.Entry<K, V>> {
    private final transient int size;
    private final transient zzef<K, V> zznc;
    private final transient Object[] zznd;
    private final transient int zznm = 0;

    zzer(zzef<K, V> zzefVar, Object[] objArr, int i, int i2) {
        this.zznc = zzefVar;
        this.zznd = objArr;
        this.size = i2;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final boolean zzcu() {
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    /* renamed from: zzcp */
    public final zzfa<Map.Entry<K, V>> iterator() {
        return (zzfa) zzct().iterator();
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final int zza(Object[] objArr, int i) {
        return zzct().zza(objArr, i);
    }

    @Override // com.google.android.gms.internal.vision.zzej
    final zzee<Map.Entry<K, V>> zzda() {
        return new zzeu(this);
    }

    @Override // com.google.android.gms.internal.vision.zzeb, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value != null && value.equals(this.zznc.get(key))) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.vision.zzej, com.google.android.gms.internal.vision.zzeb, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
