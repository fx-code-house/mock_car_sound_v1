package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public abstract class zzct implements Serializable, Iterable<Byte> {
    public static final zzct zzgi = new zzdd(zzeb.zzla);
    private static final zzcz zzgj;
    private static final Comparator<zzct> zzgk;
    private int zzef = 0;

    zzct() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zza(byte b) {
        return b & 255;
    }

    public abstract boolean equals(Object obj);

    public abstract int size();

    protected abstract int zza(int i, int i2, int i3);

    public abstract zzct zza(int i, int i2);

    protected abstract String zza(Charset charset);

    abstract void zza(zzcu zzcuVar) throws IOException;

    public abstract boolean zzao();

    public abstract byte zzk(int i);

    abstract byte zzl(int i);

    public static zzct zzp(String str) {
        return new zzdd(str.getBytes(zzeb.UTF_8));
    }

    public final String zzan() {
        return size() == 0 ? "" : zza(zzeb.UTF_8);
    }

    public final int hashCode() {
        int iZza = this.zzef;
        if (iZza == 0) {
            int size = size();
            iZza = zza(size, 0, size);
            if (iZza == 0) {
                iZza = 1;
            }
            this.zzef = iZza;
        }
        return iZza;
    }

    static zzdb zzm(int i) {
        return new zzdb(i, null);
    }

    protected final int zzap() {
        return this.zzef;
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
        objArr[1] = Integer.valueOf(size());
        objArr[2] = size() <= 50 ? zzgi.zzd(this) : String.valueOf(zzgi.zzd(zza(0, 47))).concat("...");
        return String.format(locale, "<ByteString@%s size=%d contents=\"%s\">", objArr);
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzcw(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        zzcw zzcwVar = null;
        zzgj = zzcs.zzal() ? new zzdg(zzcwVar) : new zzcx(zzcwVar);
        zzgk = new zzcv();
    }
}
