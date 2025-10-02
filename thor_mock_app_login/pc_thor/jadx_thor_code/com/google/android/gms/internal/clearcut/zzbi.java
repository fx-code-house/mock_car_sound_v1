package com.google.android.gms.internal.clearcut;

import java.io.IOException;
import java.nio.charset.Charset;

/* loaded from: classes2.dex */
class zzbi extends zzbh {
    protected final byte[] zzfp;

    zzbi(byte[] bArr) {
        this.zzfp = bArr;
    }

    @Override // com.google.android.gms.internal.clearcut.zzbb
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbb) || size() != ((zzbb) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzbi)) {
            return obj.equals(this);
        }
        zzbi zzbiVar = (zzbi) obj;
        int iZzab = zzab();
        int iZzab2 = zzbiVar.zzab();
        if (iZzab == 0 || iZzab2 == 0 || iZzab == iZzab2) {
            return zza(zzbiVar, 0, size());
        }
        return false;
    }

    @Override // com.google.android.gms.internal.clearcut.zzbb
    public int size() {
        return this.zzfp.length;
    }

    @Override // com.google.android.gms.internal.clearcut.zzbb
    protected final int zza(int i, int i2, int i3) {
        return zzci.zza(i, this.zzfp, zzac(), i3);
    }

    @Override // com.google.android.gms.internal.clearcut.zzbb
    public final zzbb zza(int i, int i2) {
        int iZzb = zzb(0, i2, size());
        return iZzb == 0 ? zzbb.zzfi : new zzbe(this.zzfp, zzac(), iZzb);
    }

    @Override // com.google.android.gms.internal.clearcut.zzbb
    protected final String zza(Charset charset) {
        return new String(this.zzfp, zzac(), size(), charset);
    }

    @Override // com.google.android.gms.internal.clearcut.zzbb
    final void zza(zzba zzbaVar) throws IOException {
        zzbaVar.zza(this.zzfp, zzac(), size());
    }

    @Override // com.google.android.gms.internal.clearcut.zzbh
    final boolean zza(zzbb zzbbVar, int i, int i2) {
        if (i2 > zzbbVar.size()) {
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(size()).toString());
        }
        if (i2 > zzbbVar.size()) {
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: 0, ").append(i2).append(", ").append(zzbbVar.size()).toString());
        }
        if (!(zzbbVar instanceof zzbi)) {
            return zzbbVar.zza(0, i2).equals(zza(0, i2));
        }
        zzbi zzbiVar = (zzbi) zzbbVar;
        byte[] bArr = this.zzfp;
        byte[] bArr2 = zzbiVar.zzfp;
        int iZzac = zzac() + i2;
        int iZzac2 = zzac();
        int iZzac3 = zzbiVar.zzac();
        while (iZzac2 < iZzac) {
            if (bArr[iZzac2] != bArr2[iZzac3]) {
                return false;
            }
            iZzac2++;
            iZzac3++;
        }
        return true;
    }

    @Override // com.google.android.gms.internal.clearcut.zzbb
    public final boolean zzaa() {
        int iZzac = zzac();
        return zzff.zze(this.zzfp, iZzac, size() + iZzac);
    }

    protected int zzac() {
        return 0;
    }

    @Override // com.google.android.gms.internal.clearcut.zzbb
    public byte zzj(int i) {
        return this.zzfp[i];
    }
}
