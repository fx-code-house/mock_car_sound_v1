package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class zzem {
    private static final zzdo zzgd = zzdo.zzaz();
    private zzct zzlt;
    private volatile zzfh zzlu;
    private volatile zzct zzlv;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzem)) {
            return false;
        }
        zzem zzemVar = (zzem) obj;
        zzfh zzfhVar = this.zzlu;
        zzfh zzfhVar2 = zzemVar.zzlu;
        if (zzfhVar == null && zzfhVar2 == null) {
            return zzad().equals(zzemVar.zzad());
        }
        if (zzfhVar != null && zzfhVar2 != null) {
            return zzfhVar.equals(zzfhVar2);
        }
        if (zzfhVar != null) {
            return zzfhVar.equals(zzemVar.zzg(zzfhVar.zzbr()));
        }
        return zzg(zzfhVar2.zzbr()).equals(zzfhVar2);
    }

    private final zzfh zzg(zzfh zzfhVar) {
        if (this.zzlu == null) {
            synchronized (this) {
                if (this.zzlu == null) {
                    try {
                        this.zzlu = zzfhVar;
                        this.zzlv = zzct.zzgi;
                    } catch (zzeh unused) {
                        this.zzlu = zzfhVar;
                        this.zzlv = zzct.zzgi;
                    }
                }
            }
        }
        return this.zzlu;
    }

    public final zzfh zzh(zzfh zzfhVar) {
        zzfh zzfhVar2 = this.zzlu;
        this.zzlt = null;
        this.zzlv = null;
        this.zzlu = zzfhVar;
        return zzfhVar2;
    }

    public final int zzbl() {
        if (this.zzlv != null) {
            return this.zzlv.size();
        }
        if (this.zzlu != null) {
            return this.zzlu.zzbl();
        }
        return 0;
    }

    public final zzct zzad() {
        if (this.zzlv != null) {
            return this.zzlv;
        }
        synchronized (this) {
            if (this.zzlv != null) {
                return this.zzlv;
            }
            if (this.zzlu == null) {
                this.zzlv = zzct.zzgi;
            } else {
                this.zzlv = this.zzlu.zzad();
            }
            return this.zzlv;
        }
    }
}
