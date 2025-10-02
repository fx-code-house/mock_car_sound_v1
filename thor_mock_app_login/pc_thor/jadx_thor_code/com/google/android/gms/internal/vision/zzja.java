package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzid;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzja implements zzkf {
    private static final zzjk zzaae = new zzjd();
    private final zzjk zzaad;

    public zzja() {
        this(new zzjc(zzib.zzgq(), zzhw()));
    }

    private zzja(zzjk zzjkVar) {
        this.zzaad = (zzjk) zzie.zza(zzjkVar, "messageInfoFactory");
    }

    @Override // com.google.android.gms.internal.vision.zzkf
    public final <T> zzkc<T> zze(Class<T> cls) {
        zzke.zzg(cls);
        zzjl zzjlVarZzb = this.zzaad.zzb(cls);
        if (zzjlVarZzb.zzie()) {
            if (zzid.class.isAssignableFrom(cls)) {
                return zzjt.zza(zzke.zzip(), zzhu.zzgk(), zzjlVarZzb.zzif());
            }
            return zzjt.zza(zzke.zzin(), zzhu.zzgl(), zzjlVarZzb.zzif());
        }
        if (zzid.class.isAssignableFrom(cls)) {
            if (zza(zzjlVarZzb)) {
                return zzjr.zza(cls, zzjlVarZzb, zzjx.zzih(), zzix.zzhv(), zzke.zzip(), zzhu.zzgk(), zzji.zzib());
            }
            return zzjr.zza(cls, zzjlVarZzb, zzjx.zzih(), zzix.zzhv(), zzke.zzip(), (zzhq<?>) null, zzji.zzib());
        }
        if (zza(zzjlVarZzb)) {
            return zzjr.zza(cls, zzjlVarZzb, zzjx.zzig(), zzix.zzhu(), zzke.zzin(), zzhu.zzgl(), zzji.zzia());
        }
        return zzjr.zza(cls, zzjlVarZzb, zzjx.zzig(), zzix.zzhu(), zzke.zzio(), (zzhq<?>) null, zzji.zzia());
    }

    private static boolean zza(zzjl zzjlVar) {
        return zzjlVar.zzid() == zzid.zzf.zzyp;
    }

    private static zzjk zzhw() {
        try {
            return (zzjk) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzaae;
        }
    }
}
