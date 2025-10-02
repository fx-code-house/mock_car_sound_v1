package com.google.android.gms.internal.wearable;

import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
class zzas extends zzar {
    protected final byte[] zza;

    zzas(byte[] bArr) {
        bArr.getClass();
        this.zza = bArr;
    }

    @Override // com.google.android.gms.internal.wearable.zzau
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzau) || zzc() != ((zzau) obj).zzc()) {
            return false;
        }
        if (zzc() == 0) {
            return true;
        }
        if (!(obj instanceof zzas)) {
            return obj.equals(this);
        }
        zzas zzasVar = (zzas) obj;
        int iZzp = zzp();
        int iZzp2 = zzasVar.zzp();
        if (iZzp != 0 && iZzp2 != 0 && iZzp != iZzp2) {
            return false;
        }
        int iZzc = zzc();
        if (iZzc > zzasVar.zzc()) {
            int iZzc2 = zzc();
            StringBuilder sb = new StringBuilder(40);
            sb.append("Length too large: ");
            sb.append(iZzc);
            sb.append(iZzc2);
            throw new IllegalArgumentException(sb.toString());
        }
        if (iZzc > zzasVar.zzc()) {
            int iZzc3 = zzasVar.zzc();
            StringBuilder sb2 = new StringBuilder(59);
            sb2.append("Ran off end of other: 0, ");
            sb2.append(iZzc);
            sb2.append(", ");
            sb2.append(iZzc3);
            throw new IllegalArgumentException(sb2.toString());
        }
        if (!(zzasVar instanceof zzas)) {
            return zzasVar.zzf(0, iZzc).equals(zzf(0, iZzc));
        }
        byte[] bArr = this.zza;
        byte[] bArr2 = zzasVar.zza;
        zzasVar.zzd();
        int i = 0;
        int i2 = 0;
        while (i < iZzc) {
            if (bArr[i] != bArr2[i2]) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    @Override // com.google.android.gms.internal.wearable.zzau
    public byte zza(int i) {
        return this.zza[i];
    }

    @Override // com.google.android.gms.internal.wearable.zzau
    byte zzb(int i) {
        return this.zza[i];
    }

    @Override // com.google.android.gms.internal.wearable.zzau
    public int zzc() {
        return this.zza.length;
    }

    protected int zzd() {
        return 0;
    }

    @Override // com.google.android.gms.internal.wearable.zzau
    protected void zze(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zza, 0, bArr, 0, i3);
    }

    @Override // com.google.android.gms.internal.wearable.zzau
    public final zzau zzf(int i, int i2) {
        int iZzq = zzq(0, i2, zzc());
        return iZzq == 0 ? zzau.zzb : new zzap(this.zza, 0, iZzq);
    }

    @Override // com.google.android.gms.internal.wearable.zzau
    final void zzg(zzal zzalVar) throws IOException {
        ((zzaz) zzalVar).zzp(this.zza, 0, zzc());
    }

    @Override // com.google.android.gms.internal.wearable.zzau
    protected final String zzh(Charset charset) {
        return new String(this.zza, 0, zzc(), charset);
    }

    @Override // com.google.android.gms.internal.wearable.zzau
    public final boolean zzi() {
        return zzel.zzb(this.zza, 0, zzc());
    }

    @Override // com.google.android.gms.internal.wearable.zzau
    protected final int zzj(int i, int i2, int i3) {
        return zzca.zzh(i, this.zza, 0, i3);
    }
}
