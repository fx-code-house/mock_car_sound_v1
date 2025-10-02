package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
class zzdd extends zzde {
    protected final byte[] zzgp;

    zzdd(byte[] bArr) {
        bArr.getClass();
        this.zzgp = bArr;
    }

    protected int zzaq() {
        return 0;
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public byte zzk(int i) {
        return this.zzgp[i];
    }

    @Override // com.google.android.gms.internal.icing.zzct
    byte zzl(int i) {
        return this.zzgp[i];
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public int size() {
        return this.zzgp.length;
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public final zzct zza(int i, int i2) {
        int iZzb = zzb(0, i2, size());
        if (iZzb == 0) {
            return zzct.zzgi;
        }
        return new zzda(this.zzgp, zzaq(), iZzb);
    }

    @Override // com.google.android.gms.internal.icing.zzct
    final void zza(zzcu zzcuVar) throws IOException {
        zzcuVar.zza(this.zzgp, zzaq(), size());
    }

    @Override // com.google.android.gms.internal.icing.zzct
    protected final String zza(Charset charset) {
        return new String(this.zzgp, zzaq(), size(), charset);
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public final boolean zzao() {
        int iZzaq = zzaq();
        return zzgv.zzc(this.zzgp, iZzaq, size() + iZzaq);
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzct) || size() != ((zzct) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (obj instanceof zzdd) {
            zzdd zzddVar = (zzdd) obj;
            int iZzap = zzap();
            int iZzap2 = zzddVar.zzap();
            if (iZzap == 0 || iZzap2 == 0 || iZzap == iZzap2) {
                return zza(zzddVar, 0, size());
            }
            return false;
        }
        return obj.equals(this);
    }

    @Override // com.google.android.gms.internal.icing.zzde
    final boolean zza(zzct zzctVar, int i, int i2) {
        if (i2 > zzctVar.size()) {
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(size()).toString());
        }
        if (i2 > zzctVar.size()) {
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: 0, ").append(i2).append(", ").append(zzctVar.size()).toString());
        }
        if (zzctVar instanceof zzdd) {
            zzdd zzddVar = (zzdd) zzctVar;
            byte[] bArr = this.zzgp;
            byte[] bArr2 = zzddVar.zzgp;
            int iZzaq = zzaq() + i2;
            int iZzaq2 = zzaq();
            int iZzaq3 = zzddVar.zzaq();
            while (iZzaq2 < iZzaq) {
                if (bArr[iZzaq2] != bArr2[iZzaq3]) {
                    return false;
                }
                iZzaq2++;
                iZzaq3++;
            }
            return true;
        }
        return zzctVar.zza(0, i2).equals(zza(0, i2));
    }

    @Override // com.google.android.gms.internal.icing.zzct
    protected final int zza(int i, int i2, int i3) {
        return zzeb.zza(i, this.zzgp, zzaq(), i3);
    }
}
