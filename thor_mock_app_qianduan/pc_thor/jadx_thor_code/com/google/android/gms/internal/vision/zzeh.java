package com.google.android.gms.internal.vision;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class zzeh<K, V> extends zzdn<K, V> implements Serializable {
    private final transient int size;
    private final transient zzef<K, ? extends zzeb<V>> zznc;

    zzeh(zzef<K, ? extends zzeb<V>> zzefVar, int i) {
        this.zznc = zzefVar;
        this.size = i;
    }

    @Override // com.google.android.gms.internal.vision.zzdo
    public final boolean containsValue(@NullableDecl Object obj) {
        return obj != null && super.containsValue(obj);
    }

    @Override // com.google.android.gms.internal.vision.zzdo
    final Map<K, Collection<V>> zzcd() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.android.gms.internal.vision.zzdo
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // com.google.android.gms.internal.vision.zzdo
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.android.gms.internal.vision.zzdo
    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.android.gms.internal.vision.zzdo, com.google.android.gms.internal.vision.zzen
    public final /* synthetic */ Map zzcc() {
        return this.zznc;
    }
}
