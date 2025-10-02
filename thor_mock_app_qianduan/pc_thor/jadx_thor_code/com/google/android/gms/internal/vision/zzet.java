package com.google.android.gms.internal.vision;

import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzet<K> extends zzej<K> {
    private final transient zzee<K> zzmv;
    private final transient zzef<K, ?> zznc;

    zzet(zzef<K, ?> zzefVar, zzee<K> zzeeVar) {
        this.zznc = zzefVar;
        this.zzmv = zzeeVar;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final boolean zzcu() {
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    /* renamed from: zzcp */
    public final zzfa<K> iterator() {
        return (zzfa) zzct().iterator();
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final int zza(Object[] objArr, int i) {
        return zzct().zza(objArr, i);
    }

    @Override // com.google.android.gms.internal.vision.zzej, com.google.android.gms.internal.vision.zzeb
    public final zzee<K> zzct() {
        return this.zzmv;
    }

    @Override // com.google.android.gms.internal.vision.zzeb, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@NullableDecl Object obj) {
        return this.zznc.get(obj) != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zznc.size();
    }

    @Override // com.google.android.gms.internal.vision.zzej, com.google.android.gms.internal.vision.zzeb, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
