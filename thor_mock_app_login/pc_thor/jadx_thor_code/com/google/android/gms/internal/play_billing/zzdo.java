package com.google.android.gms.internal.play_billing;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzdo implements zzdc {
    private final zzdf zza;
    private final String zzb;
    private final Object[] zzc;
    private final int zzd;

    zzdo(zzdf zzdfVar, String str, Object[] objArr) {
        this.zza = zzdfVar;
        this.zzb = str;
        this.zzc = objArr;
        char cCharAt = str.charAt(0);
        if (cCharAt < 55296) {
            this.zzd = cCharAt;
            return;
        }
        int i = cCharAt & 8191;
        int i2 = 1;
        int i3 = 13;
        while (true) {
            int i4 = i2 + 1;
            char cCharAt2 = str.charAt(i2);
            if (cCharAt2 < 55296) {
                this.zzd = i | (cCharAt2 << i3);
                return;
            } else {
                i |= (cCharAt2 & 8191) << i3;
                i3 += 13;
                i2 = i4;
            }
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzdc
    public final zzdf zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.play_billing.zzdc
    public final boolean zzb() {
        return (this.zzd & 2) == 2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzdc
    public final int zzc() {
        return (this.zzd & 1) != 0 ? 1 : 2;
    }

    final String zzd() {
        return this.zzb;
    }

    final Object[] zze() {
        return this.zzc;
    }
}
