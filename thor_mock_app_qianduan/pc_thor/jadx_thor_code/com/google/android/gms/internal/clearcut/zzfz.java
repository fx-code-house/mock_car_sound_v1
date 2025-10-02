package com.google.android.gms.internal.clearcut;

import java.io.IOException;

/* loaded from: classes2.dex */
public class zzfz {
    protected volatile int zzrs = -1;

    public static final void zza(zzfz zzfzVar, byte[] bArr, int i, int i2) {
        try {
            zzfs zzfsVarZzh = zzfs.zzh(bArr, 0, i2);
            zzfzVar.zza(zzfsVarZzh);
            zzfsVarZzh.zzem();
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public String toString() {
        return zzga.zza(this);
    }

    public void zza(zzfs zzfsVar) throws IOException {
    }

    public final int zzas() {
        int iZzen = zzen();
        this.zzrs = iZzen;
        return iZzen;
    }

    protected int zzen() {
        return 0;
    }

    @Override // 
    /* renamed from: zzep, reason: merged with bridge method [inline-methods] */
    public zzfz clone() throws CloneNotSupportedException {
        return (zzfz) super.clone();
    }
}
