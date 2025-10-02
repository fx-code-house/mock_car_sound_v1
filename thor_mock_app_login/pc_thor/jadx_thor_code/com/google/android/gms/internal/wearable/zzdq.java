package com.google.android.gms.internal.wearable;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzdq implements Iterator<Map.Entry> {
    final /* synthetic */ zzds zza;
    private int zzb = -1;
    private boolean zzc;
    private Iterator<Map.Entry> zzd;

    /* synthetic */ zzdq(zzds zzdsVar, zzdl zzdlVar) {
        this.zza = zzdsVar;
    }

    private final Iterator<Map.Entry> zza() {
        if (this.zzd == null) {
            this.zzd = this.zza.zzc.entrySet().iterator();
        }
        return this.zzd;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.zzb + 1 >= this.zza.zzb.size()) {
            return !this.zza.zzc.isEmpty() && zza().hasNext();
        }
        return true;
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Map.Entry next() {
        this.zzc = true;
        int i = this.zzb + 1;
        this.zzb = i;
        return i < this.zza.zzb.size() ? (Map.Entry) this.zza.zzb.get(this.zzb) : zza().next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzc) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzc = false;
        this.zza.zzm();
        if (this.zzb >= this.zza.zzb.size()) {
            zza().remove();
            return;
        }
        zzds zzdsVar = this.zza;
        int i = this.zzb;
        this.zzb = i - 1;
        zzdsVar.zzk(i);
    }
}
