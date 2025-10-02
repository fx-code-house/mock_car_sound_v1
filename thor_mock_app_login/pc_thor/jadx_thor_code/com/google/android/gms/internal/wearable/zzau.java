package com.google.android.gms.internal.wearable;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public abstract class zzau implements Iterable<Byte>, Serializable {
    public static final zzau zzb = new zzas(zzca.zzc);
    private static final Comparator<zzau> zzc;
    private static final zzat zzd;
    private int zza = 0;

    static {
        int i = zzah.zza;
        zzd = new zzat(null);
        zzc = new zzan();
    }

    zzau() {
    }

    public static zzau zzk(byte[] bArr, int i, int i2) {
        zzq(i, i + i2, bArr.length);
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return new zzas(bArr2);
    }

    public static zzau zzl(byte[] bArr) {
        return zzk(bArr, 0, bArr.length);
    }

    public static zzau zzm(String str) {
        return new zzas(str.getBytes(zzca.zza));
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int iZzj = this.zza;
        if (iZzj == 0) {
            int iZzc = zzc();
            iZzj = zzj(iZzc, 0, iZzc);
            if (iZzj == 0) {
                iZzj = 1;
            }
            this.zza = iZzj;
        }
        return iZzj;
    }

    @Override // java.lang.Iterable
    public final /* bridge */ /* synthetic */ Iterator<Byte> iterator() {
        return new zzam(this);
    }

    public final String toString() {
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.toHexString(System.identityHashCode(this));
        objArr[1] = Integer.valueOf(zzc());
        objArr[2] = zzc() <= 50 ? zzdu.zza(this) : String.valueOf(zzdu.zza(zzf(0, 47))).concat("...");
        return String.format(locale, "<ByteString@%s size=%d contents=\"%s\">", objArr);
    }

    public abstract byte zza(int i);

    abstract byte zzb(int i);

    public abstract int zzc();

    protected abstract void zze(byte[] bArr, int i, int i2, int i3);

    public abstract zzau zzf(int i, int i2);

    abstract void zzg(zzal zzalVar) throws IOException;

    protected abstract String zzh(Charset charset);

    public abstract boolean zzi();

    protected abstract int zzj(int i, int i2, int i3);

    public final byte[] zzn() {
        int iZzc = zzc();
        if (iZzc == 0) {
            return zzca.zzc;
        }
        byte[] bArr = new byte[iZzc];
        zze(bArr, 0, 0, iZzc);
        return bArr;
    }

    public final String zzo(Charset charset) {
        return zzc() == 0 ? "" : zzh(charset);
    }

    protected final int zzp() {
        return this.zza;
    }

    static int zzq(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        }
        if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder(37);
        sb3.append("End index: ");
        sb3.append(i2);
        sb3.append(" >= ");
        sb3.append(i3);
        throw new IndexOutOfBoundsException(sb3.toString());
    }
}
