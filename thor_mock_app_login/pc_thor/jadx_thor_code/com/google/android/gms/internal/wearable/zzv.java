package com.google.android.gms.internal.wearable;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzv extends zzbs<zzv, zzn> implements zzcy {
    private static final zzv zzh;
    private int zzb;
    private zzu zzf;
    private byte zzg = 2;
    private String zze = "";

    static {
        zzv zzvVar = new zzv();
        zzh = zzvVar;
        zzbs.zzR(zzv.class, zzvVar);
    }

    private zzv() {
    }

    public static zzn zzc() {
        return zzh.zzM();
    }

    static /* synthetic */ void zze(zzv zzvVar, String str) {
        str.getClass();
        zzvVar.zzb |= 1;
        zzvVar.zze = str;
    }

    static /* synthetic */ void zzf(zzv zzvVar, zzu zzuVar) {
        zzuVar.getClass();
        zzvVar.zzf = zzuVar;
        zzvVar.zzb |= 2;
    }

    @Override // com.google.android.gms.internal.wearable.zzbs
    protected final Object zzG(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzg);
        }
        if (i2 == 2) {
            return zzS(zzh, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001ᔈ\u0000\u0002ᔉ\u0001", new Object[]{"zzb", "zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzv();
        }
        zzl zzlVar = null;
        if (i2 == 4) {
            return new zzn(zzlVar);
        }
        if (i2 == 5) {
            return zzh;
        }
        this.zzg = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final String zza() {
        return this.zze;
    }

    public final zzu zzb() {
        zzu zzuVar = this.zzf;
        return zzuVar == null ? zzu.zzd() : zzuVar;
    }
}
