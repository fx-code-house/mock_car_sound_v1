package com.google.android.gms.internal.vision;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zziq<K> implements Map.Entry<K, Object> {
    private Map.Entry<K, zzio> zzzs;

    private zziq(Map.Entry<K, zzio> entry) {
        this.zzzs = entry;
    }

    @Override // java.util.Map.Entry
    public final K getKey() {
        return this.zzzs.getKey();
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        if (this.zzzs.getValue() == null) {
            return null;
        }
        return zzio.zzhp();
    }

    public final zzio zzhr() {
        return this.zzzs.getValue();
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (!(obj instanceof zzjn)) {
            throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
        }
        return this.zzzs.getValue().zzi((zzjn) obj);
    }
}
