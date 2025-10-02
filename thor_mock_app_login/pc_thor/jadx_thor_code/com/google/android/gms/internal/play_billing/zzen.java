package com.google.android.gms.internal.play_billing;

import sun.misc.Unsafe;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzen extends zzep {
    zzen(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.play_billing.zzep
    public final double zza(Object obj, long j) {
        return Double.longBitsToDouble(this.zza.getLong(obj, j));
    }

    @Override // com.google.android.gms.internal.play_billing.zzep
    public final float zzb(Object obj, long j) {
        return Float.intBitsToFloat(this.zza.getInt(obj, j));
    }

    /* JADX WARN: Failed to inline method: com.google.android.gms.internal.play_billing.zzeq.zzi(java.lang.Object, long, boolean):void */
    /* JADX WARN: Failed to inline method: com.google.android.gms.internal.play_billing.zzeq.zzj(java.lang.Object, long, boolean):void */
    /* JADX WARN: Unknown register number '(r5v0 'z' boolean)' in method call: com.google.android.gms.internal.play_billing.zzeq.zzj(java.lang.Object, long, boolean):void */
    /* JADX WARN: Unknown register number '(r5v0 boolean)' in method call: com.google.android.gms.internal.play_billing.zzeq.zzi(java.lang.Object, long, boolean):void */
    @Override // com.google.android.gms.internal.play_billing.zzep
    public final void zzc(Object obj, long j, boolean z) {
        if (zzeq.zzb) {
            zzeq.zzi(obj, j, z);
        } else {
            zzeq.zzj(obj, j, z);
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzep
    public final void zzd(Object obj, long j, byte b) {
        if (zzeq.zzb) {
            zzeq.zzD(obj, j, b);
        } else {
            zzeq.zzE(obj, j, b);
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzep
    public final void zze(Object obj, long j, double d) {
        this.zza.putLong(obj, j, Double.doubleToLongBits(d));
    }

    @Override // com.google.android.gms.internal.play_billing.zzep
    public final void zzf(Object obj, long j, float f) {
        this.zza.putInt(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.gms.internal.play_billing.zzep
    public final boolean zzg(Object obj, long j) {
        return zzeq.zzb ? zzeq.zzt(obj, j) : zzeq.zzu(obj, j);
    }
}
