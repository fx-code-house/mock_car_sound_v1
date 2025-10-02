package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
public abstract class zzgp implements Serializable, Iterable<Byte> {
    public static final zzgp zza = new zzgz(zzia.zzb);
    private static final zzgv zzb;
    private static final Comparator<zzgp> zzd;
    private int zzc = 0;

    zzgp() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzb(byte b) {
        return b & 255;
    }

    public abstract boolean equals(Object obj);

    public abstract byte zza(int i);

    public abstract int zza();

    protected abstract int zza(int i, int i2, int i3);

    public abstract zzgp zza(int i, int i2);

    protected abstract String zza(Charset charset);

    abstract void zza(zzgq zzgqVar) throws IOException;

    abstract byte zzb(int i);

    public abstract boolean zzc();

    public static zzgp zza(byte[] bArr, int i, int i2) {
        zzb(i, i + i2, bArr.length);
        return new zzgz(zzb.zza(bArr, i, i2));
    }

    static zzgp zza(byte[] bArr) {
        return new zzgz(bArr);
    }

    public static zzgp zza(String str) {
        return new zzgz(str.getBytes(zzia.zza));
    }

    public final String zzb() {
        return zza() == 0 ? "" : zza(zzia.zza);
    }

    public final int hashCode() {
        int iZza = this.zzc;
        if (iZza == 0) {
            int iZza2 = zza();
            iZza = zza(iZza2, 0, iZza2);
            if (iZza == 0) {
                iZza = 1;
            }
            this.zzc = iZza;
        }
        return iZza;
    }

    static zzgx zzc(int i) {
        return new zzgx(i, null);
    }

    protected final int zzd() {
        return this.zzc;
    }

    static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(32).append("Beginning index: ").append(i).append(" < 0").toString());
        }
        if (i2 < i) {
            throw new IndexOutOfBoundsException(new StringBuilder(66).append("Beginning index larger than ending index: ").append(i).append(", ").append(i2).toString());
        }
        throw new IndexOutOfBoundsException(new StringBuilder(37).append("End index: ").append(i2).append(" >= ").append(i3).toString());
    }

    public final String toString() {
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.toHexString(System.identityHashCode(this));
        objArr[1] = Integer.valueOf(zza());
        objArr[2] = zza() <= 50 ? zzkp.zza(this) : String.valueOf(zzkp.zza(zza(0, 47))).concat("...");
        return String.format(locale, "<ByteString@%s size=%d contents=\"%s\">", objArr);
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzgs(this);
    }

    static {
        zzgs zzgsVar = null;
        zzb = zzgm.zza() ? new zzhc(zzgsVar) : new zzgt(zzgsVar);
        zzd = new zzgr();
    }
}
