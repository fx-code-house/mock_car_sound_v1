package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzid;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzka implements zzjl {
    private final int flags;
    private final String info;
    private final Object[] zzaar;
    private final zzjn zzaau;

    zzka(zzjn zzjnVar, String str, Object[] objArr) {
        this.zzaau = zzjnVar;
        this.info = str;
        this.zzaar = objArr;
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

    final String zzik() {
        return this.info;
    }

    final Object[] zzil() {
        return this.zzaar;
    }

    @Override // com.google.android.gms.internal.vision.zzjl
    public final zzjn zzif() {
        return this.zzaau;
    }

    @Override // com.google.android.gms.internal.vision.zzjl
    public final int zzid() {
        return (this.flags & 1) == 1 ? zzid.zzf.zzyp : zzid.zzf.zzyq;
    }

    @Override // com.google.android.gms.internal.vision.zzjl
    public final boolean zzie() {
        return (this.flags & 2) == 2;
    }
}
