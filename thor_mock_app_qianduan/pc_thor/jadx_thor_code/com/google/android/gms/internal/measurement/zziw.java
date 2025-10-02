package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zziw implements zzka {
    private static final zzjg zzb = new zziz();
    private final zzjg zza;

    public zziw() {
        this(new zziy(zzhz.zza(), zza()));
    }

    private zziw(zzjg zzjgVar) {
        this.zza = (zzjg) zzia.zza(zzjgVar, "messageInfoFactory");
    }

    @Override // com.google.android.gms.internal.measurement.zzka
    public final <T> zzkb<T> zza(Class<T> cls) {
        zzkd.zza((Class<?>) cls);
        zzjh zzjhVarZzb = this.zza.zzb(cls);
        if (zzjhVarZzb.zzb()) {
            if (zzhy.class.isAssignableFrom(cls)) {
                return zzjp.zza(zzkd.zzc(), zzho.zza(), zzjhVarZzb.zzc());
            }
            return zzjp.zza(zzkd.zza(), zzho.zzb(), zzjhVarZzb.zzc());
        }
        if (zzhy.class.isAssignableFrom(cls)) {
            if (zza(zzjhVarZzb)) {
                return zzjn.zza(cls, zzjhVarZzb, zzjt.zzb(), zzit.zzb(), zzkd.zzc(), zzho.zza(), zzje.zzb());
            }
            return zzjn.zza(cls, zzjhVarZzb, zzjt.zzb(), zzit.zzb(), zzkd.zzc(), (zzhn<?>) null, zzje.zzb());
        }
        if (zza(zzjhVarZzb)) {
            return zzjn.zza(cls, zzjhVarZzb, zzjt.zza(), zzit.zza(), zzkd.zza(), zzho.zzb(), zzje.zza());
        }
        return zzjn.zza(cls, zzjhVarZzb, zzjt.zza(), zzit.zza(), zzkd.zzb(), (zzhn<?>) null, zzje.zza());
    }

    private static boolean zza(zzjh zzjhVar) {
        return zzjhVar.zza() == zzju.zza;
    }

    private static zzjg zza() {
        try {
            return (zzjg) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzb;
        }
    }
}
