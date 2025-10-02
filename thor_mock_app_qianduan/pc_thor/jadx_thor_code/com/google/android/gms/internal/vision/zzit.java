package com.google.android.gms.internal.vision;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzit<K> implements Iterator<Map.Entry<K, Object>> {
    private Iterator<Map.Entry<K, Object>> zzzw;

    public zzit(Iterator<Map.Entry<K, Object>> it) {
        this.zzzw = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzzw.hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.zzzw.remove();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        Map.Entry<K, Object> next = this.zzzw.next();
        return next.getValue() instanceof zzio ? new zziq(next) : next;
    }
}
