package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzfk<T> implements zzfu<T> {
    private final zzfh zzmn;
    private final zzgm<?, ?> zzmo;
    private final boolean zzmp;
    private final zzdn<?> zzmq;

    private zzfk(zzgm<?, ?> zzgmVar, zzdn<?> zzdnVar, zzfh zzfhVar) {
        this.zzmo = zzgmVar;
        this.zzmp = zzdnVar.zze(zzfhVar);
        this.zzmq = zzdnVar;
        this.zzmn = zzfhVar;
    }

    static <T> zzfk<T> zza(zzgm<?, ?> zzgmVar, zzdn<?> zzdnVar, zzfh zzfhVar) {
        return new zzfk<>(zzgmVar, zzdnVar, zzfhVar);
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final boolean equals(T t, T t2) {
        if (!this.zzmo.zzp(t).equals(this.zzmo.zzp(t2))) {
            return false;
        }
        if (this.zzmp) {
            return this.zzmq.zzd(t).equals(this.zzmq.zzd(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final int hashCode(T t) {
        int iHashCode = this.zzmo.zzp(t).hashCode();
        return this.zzmp ? (iHashCode * 53) + this.zzmq.zzd(t).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final void zzc(T t, T t2) {
        zzfw.zza(this.zzmo, t, t2);
        if (this.zzmp) {
            zzfw.zza(this.zzmq, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final void zza(T t, zzhg zzhgVar) throws IOException {
        Iterator it = this.zzmq.zzd(t).iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzdu zzduVar = (zzdu) entry.getKey();
            if (zzduVar.zzbh() != zzhh.MESSAGE || zzduVar.zzbi() || zzduVar.zzbj()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zzek) {
                zzhgVar.zza(zzduVar.zzbf(), (Object) ((zzek) entry).zzcc().zzad());
            } else {
                zzhgVar.zza(zzduVar.zzbf(), entry.getValue());
            }
        }
        zzgm<?, ?> zzgmVar = this.zzmo;
        zzgmVar.zzc(zzgmVar.zzp(t), zzhgVar);
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final void zzf(T t) {
        this.zzmo.zzf(t);
        this.zzmq.zzf(t);
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final boolean zzm(T t) {
        return this.zzmq.zzd(t).isInitialized();
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final int zzn(T t) {
        zzgm<?, ?> zzgmVar = this.zzmo;
        int iZzq = zzgmVar.zzq(zzgmVar.zzp(t)) + 0;
        return this.zzmp ? iZzq + this.zzmq.zzd(t).zzbe() : iZzq;
    }
}
