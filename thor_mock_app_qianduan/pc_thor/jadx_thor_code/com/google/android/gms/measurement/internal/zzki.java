package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
abstract class zzki extends zzkj {
    private boolean zzb;

    zzki(zzkl zzklVar) {
        super(zzklVar);
        this.zza.zza(this);
    }

    protected abstract boolean zzd();

    final boolean zzai() {
        return this.zzb;
    }

    protected final void zzaj() {
        if (!zzai()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzak() {
        if (this.zzb) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzd();
        this.zza.zzs();
        this.zzb = true;
    }
}
