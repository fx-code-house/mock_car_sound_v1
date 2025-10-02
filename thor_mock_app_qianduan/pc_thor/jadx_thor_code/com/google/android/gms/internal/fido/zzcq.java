package com.google.android.gms.internal.fido;

import java.util.NoSuchElementException;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzcq extends zzcs {
    final /* synthetic */ zzcz zza;
    private int zzb = 0;
    private final int zzc;

    zzcq(zzcz zzczVar) {
        this.zza = zzczVar;
        this.zzc = zzczVar.zzd();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb < this.zzc;
    }

    @Override // com.google.android.gms.internal.fido.zzcu
    public final byte zza() {
        int i = this.zzb;
        if (i >= this.zzc) {
            throw new NoSuchElementException();
        }
        this.zzb = i + 1;
        return this.zza.zzb(i);
    }
}
