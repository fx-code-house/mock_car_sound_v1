package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class zzis {
    private static final zzho zzth = zzho.zzgf();
    private zzgs zzzt;
    private volatile zzjn zzzu;
    private volatile zzgs zzzv;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzis)) {
            return false;
        }
        zzis zzisVar = (zzis) obj;
        zzjn zzjnVar = this.zzzu;
        zzjn zzjnVar2 = zzisVar.zzzu;
        if (zzjnVar == null && zzjnVar2 == null) {
            return zzee().equals(zzisVar.zzee());
        }
        if (zzjnVar != null && zzjnVar2 != null) {
            return zzjnVar.equals(zzjnVar2);
        }
        if (zzjnVar != null) {
            return zzjnVar.equals(zzisVar.zzh(zzjnVar.zzgx()));
        }
        return zzh(zzjnVar2.zzgx()).equals(zzjnVar2);
    }

    private final zzjn zzh(zzjn zzjnVar) {
        if (this.zzzu == null) {
            synchronized (this) {
                if (this.zzzu == null) {
                    try {
                        this.zzzu = zzjnVar;
                        this.zzzv = zzgs.zztt;
                    } catch (zzin unused) {
                        this.zzzu = zzjnVar;
                        this.zzzv = zzgs.zztt;
                    }
                }
            }
        }
        return this.zzzu;
    }

    public final zzjn zzi(zzjn zzjnVar) {
        zzjn zzjnVar2 = this.zzzu;
        this.zzzt = null;
        this.zzzv = null;
        this.zzzu = zzjnVar;
        return zzjnVar2;
    }

    public final int zzgz() {
        if (this.zzzv != null) {
            return this.zzzv.size();
        }
        if (this.zzzu != null) {
            return this.zzzu.zzgz();
        }
        return 0;
    }

    public final zzgs zzee() {
        if (this.zzzv != null) {
            return this.zzzv;
        }
        synchronized (this) {
            if (this.zzzv != null) {
                return this.zzzv;
            }
            if (this.zzzu == null) {
                this.zzzv = zzgs.zztt;
            } else {
                this.zzzv = this.zzzu.zzee();
            }
            return this.zzzv;
        }
    }
}
