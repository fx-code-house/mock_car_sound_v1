package com.google.android.gms.internal.wearable;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzw extends zzbs<zzw, zzm> implements zzcy {
    private static final zzw zzf;
    private byte zze = 2;
    private zzbz<zzv> zzb = zzW();

    static {
        zzw zzwVar = new zzw();
        zzf = zzwVar;
        zzbs.zzR(zzw.class, zzwVar);
    }

    private zzw() {
    }

    public static zzw zzb(byte[] bArr) throws zzcc {
        return (zzw) zzbs.zzZ(zzf, bArr);
    }

    public static zzm zzc() {
        return zzf.zzM();
    }

    static /* synthetic */ void zze(zzw zzwVar, Iterable iterable) {
        zzbz<zzv> zzbzVar = zzwVar.zzb;
        if (!zzbzVar.zza()) {
            zzwVar.zzb = zzbs.zzX(zzbzVar);
        }
        zzaf.zzL(iterable, zzwVar.zzb);
    }

    @Override // com.google.android.gms.internal.wearable.zzbs
    protected final Object zzG(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zze);
        }
        if (i2 == 2) {
            return zzS(zzf, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001Л", new Object[]{"zzb", zzv.class});
        }
        if (i2 == 3) {
            return new zzw();
        }
        zzl zzlVar = null;
        if (i2 == 4) {
            return new zzm(zzlVar);
        }
        if (i2 == 5) {
            return zzf;
        }
        this.zze = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final List<zzv> zza() {
        return this.zzb;
    }
}
