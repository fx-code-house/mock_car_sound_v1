package com.google.android.gms.internal.vision;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzkj<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private final /* synthetic */ zzkh zzabx;
    private Iterator<Map.Entry<K, V>> zzaby;

    private zzkj(zzkh zzkhVar) {
        this.zzabx = zzkhVar;
        this.pos = zzkhVar.zzabs.size();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.pos;
        return (i > 0 && i <= this.zzabx.zzabs.size()) || zzix().hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Iterator<Map.Entry<K, V>> zzix() {
        if (this.zzaby == null) {
            this.zzaby = this.zzabx.zzabv.entrySet().iterator();
        }
        return this.zzaby;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        if (zzix().hasNext()) {
            return zzix().next();
        }
        List list = this.zzabx.zzabs;
        int i = this.pos - 1;
        this.pos = i;
        return (Map.Entry) list.get(i);
    }

    /* synthetic */ zzkj(zzkh zzkhVar, zzkg zzkgVar) {
        this(zzkhVar);
    }
}
