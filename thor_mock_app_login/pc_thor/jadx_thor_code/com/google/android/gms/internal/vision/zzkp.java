package com.google.android.gms.internal.vision;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzkp<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private final /* synthetic */ zzkh zzabx;
    private Iterator<Map.Entry<K, V>> zzaby;
    private boolean zzacc;

    private zzkp(zzkh zzkhVar) {
        this.zzabx = zzkhVar;
        this.pos = -1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.pos + 1 < this.zzabx.zzabs.size() || (!this.zzabx.zzabt.isEmpty() && zzix().hasNext());
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzacc) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzacc = false;
        this.zzabx.zziv();
        if (this.pos < this.zzabx.zzabs.size()) {
            zzkh zzkhVar = this.zzabx;
            int i = this.pos;
            this.pos = i - 1;
            zzkhVar.zzcd(i);
            return;
        }
        zzix().remove();
    }

    private final Iterator<Map.Entry<K, V>> zzix() {
        if (this.zzaby == null) {
            this.zzaby = this.zzabx.zzabt.entrySet().iterator();
        }
        return this.zzaby;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        this.zzacc = true;
        int i = this.pos + 1;
        this.pos = i;
        if (i >= this.zzabx.zzabs.size()) {
            return zzix().next();
        }
        return (Map.Entry) this.zzabx.zzabs.get(this.pos);
    }

    /* synthetic */ zzkp(zzkh zzkhVar, zzkg zzkgVar) {
        this(zzkhVar);
    }
}
