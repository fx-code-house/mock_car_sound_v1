package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
abstract class zzg extends zzd {
    private boolean zza;

    zzg(zzfu zzfuVar) {
        super(zzfuVar);
        this.zzy.zza(this);
    }

    protected abstract boolean zzy();

    protected void zzz() {
    }

    final boolean zzu() {
        return this.zza;
    }

    protected final void zzv() {
        if (!zzu()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzw() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        if (zzy()) {
            return;
        }
        this.zzy.zzae();
        this.zza = true;
    }

    public final void zzx() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzz();
        this.zzy.zzae();
        this.zza = true;
    }
}
