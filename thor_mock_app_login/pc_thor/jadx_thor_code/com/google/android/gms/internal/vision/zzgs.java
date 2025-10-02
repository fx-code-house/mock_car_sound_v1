package com.google.android.gms.internal.vision;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class zzgs implements Serializable, Iterable<Byte> {
    public static final zzgs zztt = new zzhc(zzie.zzyy);
    private static final zzgy zztu;
    private static final Comparator<zzgs> zztv;
    private int zzoc = 0;

    zzgs() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zza(byte b) {
        return b & 255;
    }

    public abstract boolean equals(Object obj);

    public abstract int size();

    protected abstract String zza(Charset charset);

    abstract void zza(zzgt zzgtVar) throws IOException;

    protected abstract void zza(byte[] bArr, int i, int i2, int i3);

    public abstract byte zzau(int i);

    abstract byte zzav(int i);

    protected abstract int zzd(int i, int i2, int i3);

    public abstract boolean zzfm();

    public abstract zzgs zzi(int i, int i2);

    public static zzgs zza(byte[] bArr, int i, int i2) {
        zze(i, i + i2, bArr.length);
        return new zzhc(zztu.zzd(bArr, i, i2));
    }

    static zzgs zzd(byte[] bArr) {
        return new zzhc(bArr);
    }

    static zzgs zzb(byte[] bArr, int i, int i2) {
        return new zzgz(bArr, i, i2);
    }

    public static zzgs zzv(String str) {
        return new zzhc(str.getBytes(zzie.UTF_8));
    }

    public final String zzfl() {
        return size() == 0 ? "" : zza(zzie.UTF_8);
    }

    public final int hashCode() {
        int iZzd = this.zzoc;
        if (iZzd == 0) {
            int size = size();
            iZzd = zzd(size, 0, size);
            if (iZzd == 0) {
                iZzd = 1;
            }
            this.zzoc = iZzd;
        }
        return iZzd;
    }

    static zzha zzaw(int i) {
        return new zzha(i, null);
    }

    protected final int zzfn() {
        return this.zzoc;
    }

    static int zze(int i, int i2, int i3) {
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
        objArr[1] = Integer.valueOf(size());
        objArr[2] = size() <= 50 ? zzkq.zzd(this) : String.valueOf(zzkq.zzd(zzi(0, 47))).concat("...");
        return String.format(locale, "<ByteString@%s size=%d contents=\"%s\">", objArr);
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzgv(this);
    }

    static {
        zzgv zzgvVar = null;
        zztu = zzgl.zzel() ? new zzhf(zzgvVar) : new zzgw(zzgvVar);
        zztv = new zzgu();
    }
}
