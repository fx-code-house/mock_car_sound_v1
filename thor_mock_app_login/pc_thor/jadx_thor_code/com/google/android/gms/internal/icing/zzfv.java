package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzfv implements zzff {
    private final int flags;
    private final String info;
    private final zzfh zzmn;
    private final Object[] zzmu;

    zzfv(zzfh zzfhVar, String str, Object[] objArr) {
        this.zzmn = zzfhVar;
        this.info = str;
        this.zzmu = objArr;
        char cCharAt = str.charAt(0);
        if (cCharAt < 55296) {
            this.flags = cCharAt;
            return;
        }
        int i = cCharAt & 8191;
        int i2 = 13;
        int i3 = 1;
        while (true) {
            int i4 = i3 + 1;
            char cCharAt2 = str.charAt(i3);
            if (cCharAt2 < 55296) {
                this.flags = i | (cCharAt2 << i2);
                return;
            } else {
                i |= (cCharAt2 & 8191) << i2;
                i2 += 13;
                i3 = i4;
            }
        }
    }

    final String zzcw() {
        return this.info;
    }

    final Object[] zzcx() {
        return this.zzmu;
    }

    @Override // com.google.android.gms.internal.icing.zzff
    public final zzfh zzcq() {
        return this.zzmn;
    }

    @Override // com.google.android.gms.internal.icing.zzff
    public final int zzco() {
        return (this.flags & 1) == 1 ? zzdx.zze.zzku : zzdx.zze.zzkv;
    }

    @Override // com.google.android.gms.internal.icing.zzff
    public final boolean zzcp() {
        return (this.flags & 2) == 2;
    }
}
