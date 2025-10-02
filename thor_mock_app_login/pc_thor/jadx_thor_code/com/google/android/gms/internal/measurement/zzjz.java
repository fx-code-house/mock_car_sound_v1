package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjz implements zzjh {
    private final zzjj zza;
    private final String zzb;
    private final Object[] zzc;
    private final int zzd;

    zzjz(zzjj zzjjVar, String str, Object[] objArr) {
        this.zza = zzjjVar;
        this.zzb = str;
        this.zzc = objArr;
        char cCharAt = str.charAt(0);
        if (cCharAt < 55296) {
            this.zzd = cCharAt;
            return;
        }
        int i = cCharAt & 8191;
        int i2 = 13;
        int i3 = 1;
        while (true) {
            int i4 = i3 + 1;
            char cCharAt2 = str.charAt(i3);
            if (cCharAt2 < 55296) {
                this.zzd = i | (cCharAt2 << i2);
                return;
            } else {
                i |= (cCharAt2 & 8191) << i2;
                i2 += 13;
                i3 = i4;
            }
        }
    }

    final String zzd() {
        return this.zzb;
    }

    final Object[] zze() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzjh
    public final zzjj zzc() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzjh
    public final int zza() {
        return (this.zzd & 1) == 1 ? zzju.zza : zzju.zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzjh
    public final boolean zzb() {
        return (this.zzd & 2) == 2;
    }
}
