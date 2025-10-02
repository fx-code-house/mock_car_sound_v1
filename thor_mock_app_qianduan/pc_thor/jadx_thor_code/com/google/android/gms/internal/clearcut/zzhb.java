package com.google.android.gms.internal.clearcut;

import java.io.IOException;

/* loaded from: classes2.dex */
public final class zzhb extends zzfu<zzhb> implements Cloneable {
    private static volatile zzhb[] zzbkd;
    private String zzbke = "";
    private String value = "";

    public zzhb() {
        this.zzrj = null;
        this.zzrs = -1;
    }

    public static zzhb[] zzge() {
        if (zzbkd == null) {
            synchronized (zzfy.zzrr) {
                if (zzbkd == null) {
                    zzbkd = new zzhb[0];
                }
            }
        }
        return zzbkd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.clearcut.zzfu, com.google.android.gms.internal.clearcut.zzfz
    /* renamed from: zzgf, reason: merged with bridge method [inline-methods] */
    public final zzhb clone() {
        try {
            return (zzhb) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzhb)) {
            return false;
        }
        zzhb zzhbVar = (zzhb) obj;
        String str = this.zzbke;
        if (str == null) {
            if (zzhbVar.zzbke != null) {
                return false;
            }
        } else if (!str.equals(zzhbVar.zzbke)) {
            return false;
        }
        String str2 = this.value;
        if (str2 == null) {
            if (zzhbVar.value != null) {
                return false;
            }
        } else if (!str2.equals(zzhbVar.value)) {
            return false;
        }
        return (this.zzrj == null || this.zzrj.isEmpty()) ? zzhbVar.zzrj == null || zzhbVar.zzrj.isEmpty() : this.zzrj.equals(zzhbVar.zzrj);
    }

    public final int hashCode() {
        int iHashCode = (getClass().getName().hashCode() + 527) * 31;
        String str = this.zzbke;
        int iHashCode2 = 0;
        int iHashCode3 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.value;
        int iHashCode4 = (iHashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        if (this.zzrj != null && !this.zzrj.isEmpty()) {
            iHashCode2 = this.zzrj.hashCode();
        }
        return iHashCode4 + iHashCode2;
    }

    @Override // com.google.android.gms.internal.clearcut.zzfu, com.google.android.gms.internal.clearcut.zzfz
    public final void zza(zzfs zzfsVar) throws IOException {
        String str = this.zzbke;
        if (str != null && !str.equals("")) {
            zzfsVar.zza(1, this.zzbke);
        }
        String str2 = this.value;
        if (str2 != null && !str2.equals("")) {
            zzfsVar.zza(2, this.value);
        }
        super.zza(zzfsVar);
    }

    @Override // com.google.android.gms.internal.clearcut.zzfu, com.google.android.gms.internal.clearcut.zzfz
    protected final int zzen() {
        int iZzen = super.zzen();
        String str = this.zzbke;
        if (str != null && !str.equals("")) {
            iZzen += zzfs.zzb(1, this.zzbke);
        }
        String str2 = this.value;
        return (str2 == null || str2.equals("")) ? iZzen : iZzen + zzfs.zzb(2, this.value);
    }

    @Override // com.google.android.gms.internal.clearcut.zzfu
    /* renamed from: zzeo */
    public final /* synthetic */ zzfu clone() throws CloneNotSupportedException {
        return (zzhb) clone();
    }

    @Override // com.google.android.gms.internal.clearcut.zzfu, com.google.android.gms.internal.clearcut.zzfz
    /* renamed from: zzep */
    public final /* synthetic */ zzfz clone() throws CloneNotSupportedException {
        return (zzhb) clone();
    }
}
