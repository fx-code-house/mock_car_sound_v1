package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
abstract class zzgq extends zzgr {
    private boolean zza;

    zzgq(zzfu zzfuVar) {
        super(zzfuVar);
        this.zzy.zza(this);
    }

    protected void g_() {
    }

    protected abstract boolean zzd();

    final boolean zzaa() {
        return this.zza;
    }

    protected final void zzab() {
        if (!zzaa()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzac() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        if (zzd()) {
            return;
        }
        this.zzy.zzae();
        this.zza = true;
    }

    public final void zzad() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        g_();
        this.zzy.zzae();
        this.zza = true;
    }
}
