package com.google.android.gms.internal.wearable;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzu extends zzbs<zzu, zzo> implements zzcy {
    private static final zzu zzh;
    private int zzb;
    private zzt zzf;
    private byte zzg = 2;
    private int zze = 1;

    static {
        zzu zzuVar = new zzu();
        zzh = zzuVar;
        zzbs.zzR(zzu.class, zzuVar);
    }

    private zzu() {
    }

    public static zzo zzc() {
        return zzh.zzM();
    }

    public static zzu zzd() {
        return zzh;
    }

    static /* synthetic */ void zzf(zzu zzuVar, zzr zzrVar) {
        zzuVar.zze = zzrVar.zza();
        zzuVar.zzb |= 1;
    }

    static /* synthetic */ void zzg(zzu zzuVar, zzt zztVar) {
        zztVar.getClass();
        zzuVar.zzf = zztVar;
        zzuVar.zzb |= 2;
    }

    @Override // com.google.android.gms.internal.wearable.zzbs
    protected final Object zzG(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzg);
        }
        if (i2 == 2) {
            return zzS(zzh, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001ᔌ\u0000\u0002ᐉ\u0001", new Object[]{"zzb", "zze", zzr.zzc(), "zzf"});
        }
        if (i2 == 3) {
            return new zzu();
        }
        zzl zzlVar = null;
        if (i2 == 4) {
            return new zzo(zzlVar);
        }
        if (i2 == 5) {
            return zzh;
        }
        this.zzg = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final zzr zza() {
        zzr zzrVarZzb = zzr.zzb(this.zze);
        return zzrVarZzb == null ? zzr.BYTE_ARRAY : zzrVarZzb;
    }

    public final zzt zzb() {
        zzt zztVar = this.zzf;
        return zztVar == null ? zzt.zzq() : zztVar;
    }
}
