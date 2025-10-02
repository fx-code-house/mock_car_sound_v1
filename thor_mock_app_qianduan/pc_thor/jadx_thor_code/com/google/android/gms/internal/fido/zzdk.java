package com.google.android.gms.internal.fido;

import com.google.common.primitives.SignedBytes;
import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
public final class zzdk extends zzdr {
    private final zzcz zza;

    zzdk(zzcz zzczVar) {
        this.zza = zzczVar;
    }

    @Override // java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        zzdr zzdrVar = (zzdr) obj;
        if (zzd(SignedBytes.MAX_POWER_OF_TWO) != zzdrVar.zza()) {
            return zzd(SignedBytes.MAX_POWER_OF_TWO) - zzdrVar.zza();
        }
        zzdk zzdkVar = (zzdk) zzdrVar;
        zzcz zzczVar = this.zza;
        int iZzd = zzczVar.zzd();
        zzcz zzczVar2 = zzdkVar.zza;
        if (iZzd != zzczVar2.zzd()) {
            return zzczVar.zzd() - zzczVar2.zzd();
        }
        return zzco.zza().compare(zzczVar.zzm(), zzdkVar.zza.zzm());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return this.zza.equals(((zzdk) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(zzd(SignedBytes.MAX_POWER_OF_TWO)), this.zza});
    }

    public final String toString() {
        zzch zzchVarZzd = zzch.zzf().zzd();
        byte[] bArrZzm = this.zza.zzm();
        return "h'" + zzchVarZzd.zzg(bArrZzm, 0, bArrZzm.length) + "'";
    }

    @Override // com.google.android.gms.internal.fido.zzdr
    protected final int zza() {
        return zzd(SignedBytes.MAX_POWER_OF_TWO);
    }

    public final zzcz zzc() {
        return this.zza;
    }
}
