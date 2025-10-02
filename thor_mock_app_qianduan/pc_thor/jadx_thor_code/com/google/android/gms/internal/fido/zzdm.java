package com.google.android.gms.internal.fido;

import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
public final class zzdm extends zzdr {
    private final long zza;

    zzdm(long j) {
        this.zza = j;
    }

    @Override // java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        zzdr zzdrVar = (zzdr) obj;
        if (zza() != zzdrVar.zza()) {
            return zza() - zzdrVar.zza();
        }
        long jAbs = Math.abs(this.zza);
        long jAbs2 = Math.abs(((zzdm) zzdrVar).zza);
        if (jAbs < jAbs2) {
            return -1;
        }
        return jAbs > jAbs2 ? 1 : 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.zza == ((zzdm) obj).zza;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(zza()), Long.valueOf(this.zza)});
    }

    public final String toString() {
        return Long.toString(this.zza);
    }

    @Override // com.google.android.gms.internal.fido.zzdr
    protected final int zza() {
        return zzd(this.zza >= 0 ? (byte) 0 : (byte) 32);
    }

    public final long zzc() {
        return this.zza;
    }
}
