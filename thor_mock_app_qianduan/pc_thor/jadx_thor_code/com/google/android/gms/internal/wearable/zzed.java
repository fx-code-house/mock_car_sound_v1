package com.google.android.gms.internal.wearable;

import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzed extends zzef {
    zzed(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final void zza(Object obj, long j, byte b) {
        if (zzeg.zzb) {
            zzeg.zzD(obj, j, b);
        } else {
            zzeg.zzE(obj, j, b);
        }
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final boolean zzb(Object obj, long j) {
        return zzeg.zzb ? zzeg.zzv(obj, j) : zzeg.zzw(obj, j);
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final void zzc(Object obj, long j, boolean z) {
        if (zzeg.zzb) {
            zzeg.zzD(obj, j, z ? (byte) 1 : (byte) 0);
        } else {
            zzeg.zzE(obj, j, z ? (byte) 1 : (byte) 0);
        }
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final float zzd(Object obj, long j) {
        return Float.intBitsToFloat(zzk(obj, j));
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final void zze(Object obj, long j, float f) {
        zzl(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final double zzf(Object obj, long j) {
        return Double.longBitsToDouble(zzm(obj, j));
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final void zzg(Object obj, long j, double d) {
        zzn(obj, j, Double.doubleToLongBits(d));
    }
}
