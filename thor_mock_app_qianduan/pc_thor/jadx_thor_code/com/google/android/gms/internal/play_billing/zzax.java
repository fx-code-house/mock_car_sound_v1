package com.google.android.gms.internal.play_billing;

import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
class zzax extends zzaw {
    protected final byte[] zza;

    zzax(byte[] bArr) {
        bArr.getClass();
        this.zza = bArr;
    }

    @Override // com.google.android.gms.internal.play_billing.zzba
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzba) || zzd() != ((zzba) obj).zzd()) {
            return false;
        }
        if (zzd() == 0) {
            return true;
        }
        if (!(obj instanceof zzax)) {
            return obj.equals(this);
        }
        zzax zzaxVar = (zzax) obj;
        int iZzk = zzk();
        int iZzk2 = zzaxVar.zzk();
        if (iZzk != 0 && iZzk2 != 0 && iZzk != iZzk2) {
            return false;
        }
        int iZzd = zzd();
        if (iZzd > zzaxVar.zzd()) {
            throw new IllegalArgumentException("Length too large: " + iZzd + zzd());
        }
        if (iZzd > zzaxVar.zzd()) {
            throw new IllegalArgumentException("Ran off end of other: 0, " + iZzd + ", " + zzaxVar.zzd());
        }
        if (!(zzaxVar instanceof zzax)) {
            return zzaxVar.zzf(0, iZzd).equals(zzf(0, iZzd));
        }
        byte[] bArr = this.zza;
        byte[] bArr2 = zzaxVar.zza;
        zzaxVar.zzc();
        int i = 0;
        int i2 = 0;
        while (i < iZzd) {
            if (bArr[i] != bArr2[i2]) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    @Override // com.google.android.gms.internal.play_billing.zzba
    public byte zza(int i) {
        return this.zza[i];
    }

    @Override // com.google.android.gms.internal.play_billing.zzba
    byte zzb(int i) {
        return this.zza[i];
    }

    protected int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.play_billing.zzba
    public int zzd() {
        return this.zza.length;
    }

    @Override // com.google.android.gms.internal.play_billing.zzba
    protected final int zze(int i, int i2, int i3) {
        return zzcg.zzb(i, this.zza, 0, i3);
    }

    @Override // com.google.android.gms.internal.play_billing.zzba
    public final zzba zzf(int i, int i2) {
        int iZzj = zzj(0, i2, zzd());
        return iZzj == 0 ? zzba.zzb : new zzau(this.zza, 0, iZzj);
    }

    @Override // com.google.android.gms.internal.play_billing.zzba
    protected final String zzg(Charset charset) {
        return new String(this.zza, 0, zzd(), charset);
    }

    @Override // com.google.android.gms.internal.play_billing.zzba
    final void zzh(zzaq zzaqVar) throws IOException {
        ((zzbf) zzaqVar).zzc(this.zza, 0, zzd());
    }

    @Override // com.google.android.gms.internal.play_billing.zzba
    public final boolean zzi() {
        return zzev.zze(this.zza, 0, zzd());
    }
}
