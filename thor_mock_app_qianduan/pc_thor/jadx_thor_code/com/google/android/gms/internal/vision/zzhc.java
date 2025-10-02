package com.google.android.gms.internal.vision;

import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
class zzhc extends zzhd {
    protected final byte[] zzua;

    zzhc(byte[] bArr) {
        bArr.getClass();
        this.zzua = bArr;
    }

    protected int zzfo() {
        return 0;
    }

    @Override // com.google.android.gms.internal.vision.zzgs
    public byte zzau(int i) {
        return this.zzua[i];
    }

    @Override // com.google.android.gms.internal.vision.zzgs
    byte zzav(int i) {
        return this.zzua[i];
    }

    @Override // com.google.android.gms.internal.vision.zzgs
    public int size() {
        return this.zzua.length;
    }

    @Override // com.google.android.gms.internal.vision.zzgs
    public final zzgs zzi(int i, int i2) {
        int iZze = zze(0, i2, size());
        if (iZze == 0) {
            return zzgs.zztt;
        }
        return new zzgz(this.zzua, zzfo(), iZze);
    }

    @Override // com.google.android.gms.internal.vision.zzgs
    protected void zza(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzua, 0, bArr, 0, i3);
    }

    @Override // com.google.android.gms.internal.vision.zzgs
    final void zza(zzgt zzgtVar) throws IOException {
        zzgtVar.zzc(this.zzua, zzfo(), size());
    }

    @Override // com.google.android.gms.internal.vision.zzgs
    protected final String zza(Charset charset) {
        return new String(this.zzua, zzfo(), size(), charset);
    }

    @Override // com.google.android.gms.internal.vision.zzgs
    public final boolean zzfm() {
        int iZzfo = zzfo();
        return zzld.zzf(this.zzua, iZzfo, size() + iZzfo);
    }

    @Override // com.google.android.gms.internal.vision.zzgs
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgs) || size() != ((zzgs) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (obj instanceof zzhc) {
            zzhc zzhcVar = (zzhc) obj;
            int iZzfn = zzfn();
            int iZzfn2 = zzhcVar.zzfn();
            if (iZzfn == 0 || iZzfn2 == 0 || iZzfn == iZzfn2) {
                return zza(zzhcVar, 0, size());
            }
            return false;
        }
        return obj.equals(this);
    }

    @Override // com.google.android.gms.internal.vision.zzhd
    final boolean zza(zzgs zzgsVar, int i, int i2) {
        if (i2 > zzgsVar.size()) {
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(size()).toString());
        }
        if (i2 > zzgsVar.size()) {
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: 0, ").append(i2).append(", ").append(zzgsVar.size()).toString());
        }
        if (zzgsVar instanceof zzhc) {
            zzhc zzhcVar = (zzhc) zzgsVar;
            byte[] bArr = this.zzua;
            byte[] bArr2 = zzhcVar.zzua;
            int iZzfo = zzfo() + i2;
            int iZzfo2 = zzfo();
            int iZzfo3 = zzhcVar.zzfo();
            while (iZzfo2 < iZzfo) {
                if (bArr[iZzfo2] != bArr2[iZzfo3]) {
                    return false;
                }
                iZzfo2++;
                iZzfo3++;
            }
            return true;
        }
        return zzgsVar.zzi(0, i2).equals(zzi(0, i2));
    }

    @Override // com.google.android.gms.internal.vision.zzgs
    protected final int zzd(int i, int i2, int i3) {
        return zzie.zza(i, this.zzua, zzfo(), i3);
    }
}
