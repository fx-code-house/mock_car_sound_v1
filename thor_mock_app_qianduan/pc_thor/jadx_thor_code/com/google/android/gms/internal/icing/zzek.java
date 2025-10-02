package com.google.android.gms.internal.icing;

import java.util.Map;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzek<K> implements Map.Entry<K, Object> {
    private Map.Entry<K, zzei> zzls;

    private zzek(Map.Entry<K, zzei> entry) {
        this.zzls = entry;
    }

    @Override // java.util.Map.Entry
    public final K getKey() {
        return this.zzls.getKey();
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        if (this.zzls.getValue() == null) {
            return null;
        }
        return zzei.zzca();
    }

    public final zzei zzcc() {
        return this.zzls.getValue();
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (!(obj instanceof zzfh)) {
            throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
        }
        return this.zzls.getValue().zzh((zzfh) obj);
    }
}
