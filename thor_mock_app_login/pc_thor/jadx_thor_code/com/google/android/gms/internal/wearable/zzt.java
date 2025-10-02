package com.google.android.gms.internal.wearable;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzt extends zzbs<zzt, zzs> implements zzcy {
    private static final zzt zzt;
    private int zzb;
    private double zzg;
    private float zzh;
    private long zzi;
    private int zzj;
    private int zzk;
    private boolean zzl;
    private long zzr;
    private byte zzs = 2;
    private zzau zze = zzau.zzb;
    private String zzf = "";
    private zzbz<zzv> zzm = zzW();
    private zzbz<zzu> zzn = zzW();
    private zzbz<String> zzo = zzbs.zzW();
    private zzby zzp = zzU();
    private zzbx zzq = zzV();

    static {
        zzt zztVar = new zzt();
        zzt = zztVar;
        zzbs.zzR(zzt.class, zztVar);
    }

    private zzt() {
    }

    static /* synthetic */ void zzA(zzt zztVar, Iterable iterable) {
        zzbz<zzv> zzbzVar = zztVar.zzm;
        if (!zzbzVar.zza()) {
            zztVar.zzm = zzbs.zzX(zzbzVar);
        }
        zzaf.zzL(iterable, zztVar.zzm);
    }

    static /* synthetic */ void zzB(zzt zztVar, zzu zzuVar) {
        zzuVar.getClass();
        zzbz<zzu> zzbzVar = zztVar.zzn;
        if (!zzbzVar.zza()) {
            zztVar.zzn = zzbs.zzX(zzbzVar);
        }
        zztVar.zzn.add(zzuVar);
    }

    static /* synthetic */ void zzC(zzt zztVar, Iterable iterable) {
        zzbz<String> zzbzVar = zztVar.zzo;
        if (!zzbzVar.zza()) {
            zztVar.zzo = zzbs.zzX(zzbzVar);
        }
        zzaf.zzL(iterable, zztVar.zzo);
    }

    static /* synthetic */ void zzD(zzt zztVar, Iterable iterable) {
        zzby zzbyVar = zztVar.zzp;
        if (!zzbyVar.zza()) {
            int size = zzbyVar.size();
            zztVar.zzp = zzbyVar.zze(size == 0 ? 10 : size + size);
        }
        zzaf.zzL(iterable, zztVar.zzp);
    }

    static /* synthetic */ void zzE(zzt zztVar, Iterable iterable) {
        zzbx zzbxVar = zztVar.zzq;
        if (!zzbxVar.zza()) {
            int size = zzbxVar.size();
            zztVar.zzq = zzbxVar.zze(size == 0 ? 10 : size + size);
        }
        zzaf.zzL(iterable, zztVar.zzq);
    }

    static /* synthetic */ void zzF(zzt zztVar, long j) {
        zztVar.zzb |= 256;
        zztVar.zzr = j;
    }

    public static zzs zzp() {
        return zzt.zzM();
    }

    public static zzt zzq() {
        return zzt;
    }

    static /* synthetic */ void zzs(zzt zztVar, zzau zzauVar) {
        zztVar.zzb |= 1;
        zztVar.zze = zzauVar;
    }

    static /* synthetic */ void zzt(zzt zztVar, String str) {
        zztVar.zzb |= 2;
        zztVar.zzf = str;
    }

    static /* synthetic */ void zzu(zzt zztVar, double d) {
        zztVar.zzb |= 4;
        zztVar.zzg = d;
    }

    static /* synthetic */ void zzv(zzt zztVar, float f) {
        zztVar.zzb |= 8;
        zztVar.zzh = f;
    }

    static /* synthetic */ void zzw(zzt zztVar, long j) {
        zztVar.zzb |= 16;
        zztVar.zzi = j;
    }

    static /* synthetic */ void zzx(zzt zztVar, int i) {
        zztVar.zzb |= 32;
        zztVar.zzj = i;
    }

    static /* synthetic */ void zzy(zzt zztVar, int i) {
        zztVar.zzb |= 64;
        zztVar.zzk = i;
    }

    static /* synthetic */ void zzz(zzt zztVar, boolean z) {
        zztVar.zzb |= 128;
        zztVar.zzl = z;
    }

    @Override // com.google.android.gms.internal.wearable.zzbs
    protected final Object zzG(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzs);
        }
        if (i2 == 2) {
            return zzS(zzt, "\u0001\u000e\u0000\u0001\u0001\u000e\u000e\u0000\u0005\u0002\u0001ည\u0000\u0002ဈ\u0001\u0003က\u0002\u0004ခ\u0003\u0005ဂ\u0004\u0006င\u0005\u0007ဏ\u0006\bဇ\u0007\tЛ\nЛ\u000b\u001a\f\u0014\rဂ\b\u000e\u0013", new Object[]{"zzb", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", zzv.class, "zzn", zzu.class, "zzo", "zzp", "zzr", "zzq"});
        }
        if (i2 == 3) {
            return new zzt();
        }
        zzl zzlVar = null;
        if (i2 == 4) {
            return new zzs(zzlVar);
        }
        if (i2 == 5) {
            return zzt;
        }
        this.zzs = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final zzau zza() {
        return this.zze;
    }

    public final String zzb() {
        return this.zzf;
    }

    public final double zzc() {
        return this.zzg;
    }

    public final float zzd() {
        return this.zzh;
    }

    public final long zze() {
        return this.zzi;
    }

    public final int zzf() {
        return this.zzj;
    }

    public final int zzg() {
        return this.zzk;
    }

    public final boolean zzh() {
        return this.zzl;
    }

    public final List<zzv> zzi() {
        return this.zzm;
    }

    public final List<zzu> zzj() {
        return this.zzn;
    }

    public final int zzk() {
        return this.zzn.size();
    }

    public final List<String> zzl() {
        return this.zzo;
    }

    public final List<Long> zzm() {
        return this.zzp;
    }

    public final List<Float> zzn() {
        return this.zzq;
    }

    public final long zzo() {
        return this.zzr;
    }
}
