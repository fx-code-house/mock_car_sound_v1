package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class zzho {
    private static volatile boolean zzur = false;
    private static boolean zzus = true;
    private static volatile zzho zzut;
    private static volatile zzho zzuu;
    private static final zzho zzuv = new zzho(true);
    private final Map<zza, zzid.zzg<?, ?>> zzuw;

    public static zzho zzge() {
        return new zzho();
    }

    public static zzho zzgf() {
        zzho zzhoVar = zzut;
        if (zzhoVar == null) {
            synchronized (zzho.class) {
                zzhoVar = zzut;
                if (zzhoVar == null) {
                    zzhoVar = zzuv;
                    zzut = zzhoVar;
                }
            }
        }
        return zzhoVar;
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static final class zza {
        private final int number;
        private final Object object;

        zza(Object obj, int i) {
            this.object = obj;
            this.number = i;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.object) * 65535) + this.number;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) obj;
            return this.object == zzaVar.object && this.number == zzaVar.number;
        }
    }

    public static zzho zzgg() {
        zzho zzhoVar = zzuu;
        if (zzhoVar != null) {
            return zzhoVar;
        }
        synchronized (zzho.class) {
            zzho zzhoVar2 = zzuu;
            if (zzhoVar2 != null) {
                return zzhoVar2;
            }
            zzho zzhoVarZzc = zzic.zzc(zzho.class);
            zzuu = zzhoVarZzc;
            return zzhoVarZzc;
        }
    }

    public final <ContainingType extends zzjn> zzid.zzg<ContainingType, ?> zza(ContainingType containingtype, int i) {
        return (zzid.zzg) this.zzuw.get(new zza(containingtype, i));
    }

    public final void zza(zzid.zzg<?, ?> zzgVar) {
        this.zzuw.put(new zza(zzgVar.zzyv, zzgVar.zzyx.number), zzgVar);
    }

    zzho() {
        this.zzuw = new HashMap();
    }

    private zzho(boolean z) {
        this.zzuw = Collections.emptyMap();
    }
}
