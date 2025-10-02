package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzeu implements zzfx {
    private static final zzfe zzme = new zzex();
    private final zzfe zzmd;

    public zzeu() {
        this(new zzew(zzdy.zzbs(), zzch()));
    }

    private zzeu(zzfe zzfeVar) {
        this.zzmd = (zzfe) zzeb.zza(zzfeVar, "messageInfoFactory");
    }

    @Override // com.google.android.gms.internal.icing.zzfx
    public final <T> zzfu<T> zzd(Class<T> cls) {
        zzfw.zzf((Class<?>) cls);
        zzff zzffVarZzc = this.zzmd.zzc(cls);
        if (zzffVarZzc.zzcp()) {
            if (zzdx.class.isAssignableFrom(cls)) {
                return zzfk.zza(zzfw.zzda(), zzdp.zzbb(), zzffVarZzc.zzcq());
            }
            return zzfk.zza(zzfw.zzcy(), zzdp.zzbc(), zzffVarZzc.zzcq());
        }
        if (zzdx.class.isAssignableFrom(cls)) {
            if (zza(zzffVarZzc)) {
                return zzfl.zza(cls, zzffVarZzc, zzfo.zzcs(), zzer.zzcg(), zzfw.zzda(), zzdp.zzbb(), zzfc.zzcm());
            }
            return zzfl.zza(cls, zzffVarZzc, zzfo.zzcs(), zzer.zzcg(), zzfw.zzda(), null, zzfc.zzcm());
        }
        if (zza(zzffVarZzc)) {
            return zzfl.zza(cls, zzffVarZzc, zzfo.zzcr(), zzer.zzcf(), zzfw.zzcy(), zzdp.zzbc(), zzfc.zzcl());
        }
        return zzfl.zza(cls, zzffVarZzc, zzfo.zzcr(), zzer.zzcf(), zzfw.zzcz(), null, zzfc.zzcl());
    }

    private static boolean zza(zzff zzffVar) {
        return zzffVar.zzco() == zzdx.zze.zzku;
    }

    private static zzfe zzch() {
        try {
            return (zzfe) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzme;
        }
    }
}
