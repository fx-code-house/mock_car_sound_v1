package com.google.android.gms.internal.wearable;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public class zzcf {
    private static final zzbg zzb = zzbg.zza();
    protected volatile zzcx zza;
    private volatile zzau zzc;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcf)) {
            return false;
        }
        zzcf zzcfVar = (zzcf) obj;
        zzcx zzcxVar = this.zza;
        zzcx zzcxVar2 = zzcfVar.zza;
        if (zzcxVar == null && zzcxVar2 == null) {
            return zzb().equals(zzcfVar.zzb());
        }
        if (zzcxVar != null && zzcxVar2 != null) {
            return zzcxVar.equals(zzcxVar2);
        }
        if (zzcxVar != null) {
            zzcfVar.zzc(zzcxVar.zzac());
            return zzcxVar.equals(zzcfVar.zza);
        }
        zzc(zzcxVar2.zzac());
        return this.zza.equals(zzcxVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final int zza() {
        if (this.zzc != null) {
            return ((zzas) this.zzc).zza.length;
        }
        if (this.zza != null) {
            return this.zza.zzP();
        }
        return 0;
    }

    public final zzau zzb() {
        if (this.zzc != null) {
            return this.zzc;
        }
        synchronized (this) {
            if (this.zzc != null) {
                return this.zzc;
            }
            if (this.zza == null) {
                this.zzc = zzau.zzb;
            } else {
                this.zzc = this.zza.zzH();
            }
            return this.zzc;
        }
    }

    protected final void zzc(zzcx zzcxVar) {
        if (this.zza != null) {
            return;
        }
        synchronized (this) {
            if (this.zza == null) {
                try {
                    this.zza = zzcxVar;
                    this.zzc = zzau.zzb;
                } catch (zzcc unused) {
                    this.zza = zzcxVar;
                    this.zzc = zzau.zzb;
                }
            }
        }
    }
}
