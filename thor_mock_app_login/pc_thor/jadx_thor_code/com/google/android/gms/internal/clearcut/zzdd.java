package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzcg;

/* loaded from: classes2.dex */
final class zzdd implements zzeg {
    private static final zzdn zzlz = new zzde();
    private final zzdn zzly;

    public zzdd() {
        this(new zzdf(zzcf.zzay(), zzby()));
    }

    private zzdd(zzdn zzdnVar) {
        this.zzly = (zzdn) zzci.zza(zzdnVar, "messageInfoFactory");
    }

    private static boolean zza(zzdm zzdmVar) {
        return zzdmVar.zzcf() == zzcg.zzg.zzkl;
    }

    private static zzdn zzby() {
        try {
            return (zzdn) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzlz;
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzeg
    public final <T> zzef<T> zzd(Class<T> cls) {
        zzeh.zzf((Class<?>) cls);
        zzdm zzdmVarZzb = this.zzly.zzb(cls);
        if (zzdmVarZzb.zzcg()) {
            return zzcg.class.isAssignableFrom(cls) ? zzdu.zza(zzeh.zzdo(), zzbx.zzap(), zzdmVarZzb.zzch()) : zzdu.zza(zzeh.zzdm(), zzbx.zzaq(), zzdmVarZzb.zzch());
        }
        if (!zzcg.class.isAssignableFrom(cls)) {
            boolean zZza = zza(zzdmVarZzb);
            zzdw zzdwVarZzcj = zzdy.zzcj();
            zzcy zzcyVarZzbv = zzcy.zzbv();
            return zZza ? zzds.zza(cls, zzdmVarZzb, zzdwVarZzcj, zzcyVarZzbv, zzeh.zzdm(), zzbx.zzaq(), zzdl.zzcc()) : zzds.zza(cls, zzdmVarZzb, zzdwVarZzcj, zzcyVarZzbv, zzeh.zzdn(), (zzbu<?>) null, zzdl.zzcc());
        }
        boolean zZza2 = zza(zzdmVarZzb);
        zzdw zzdwVarZzck = zzdy.zzck();
        zzcy zzcyVarZzbw = zzcy.zzbw();
        zzex<?, ?> zzexVarZzdo = zzeh.zzdo();
        return zZza2 ? zzds.zza(cls, zzdmVarZzb, zzdwVarZzck, zzcyVarZzbw, zzexVarZzdo, zzbx.zzap(), zzdl.zzcd()) : zzds.zza(cls, zzdmVarZzb, zzdwVarZzck, zzcyVarZzbw, zzexVarZzdo, (zzbu<?>) null, zzdl.zzcd());
    }
}
