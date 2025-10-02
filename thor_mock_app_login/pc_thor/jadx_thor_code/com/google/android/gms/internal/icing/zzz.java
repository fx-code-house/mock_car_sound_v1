package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzz {
    private zzi zzaj;
    private zzh zzan;
    private long zzak = -1;
    private int zzal = -1;
    private int zzap = -1;
    private boolean zzao = false;
    private int zzaq = 0;

    public final zzz zza(zzi zziVar) {
        this.zzaj = zziVar;
        return this;
    }

    public final zzz zza(long j) {
        this.zzak = j;
        return this;
    }

    public final zzz zzb(int i) {
        this.zzal = i;
        return this;
    }

    public final zzz zza(zzh zzhVar) {
        this.zzan = zzhVar;
        return this;
    }

    public final zzz zzd(boolean z) {
        this.zzao = z;
        return this;
    }

    public final zzz zzc(int i) {
        this.zzaq = i;
        return this;
    }

    public final zzw zzd() {
        return new zzw(this.zzaj, this.zzak, this.zzal, null, this.zzan, this.zzao, this.zzap, this.zzaq, null);
    }
}
