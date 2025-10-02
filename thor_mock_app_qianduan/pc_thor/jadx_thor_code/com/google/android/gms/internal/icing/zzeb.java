package com.google.android.gms.internal.icing;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzeb {
    public static final byte[] zzla;
    private static final ByteBuffer zzlb;
    private static final zzdf zzlc;
    static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    public static int zzg(boolean z) {
        return z ? 1231 : 1237;
    }

    public static int zzk(long j) {
        return (int) (j ^ (j >>> 32));
    }

    static <T> T checkNotNull(T t) {
        t.getClass();
        return t;
    }

    static <T> T zza(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static boolean zzd(byte[] bArr) {
        return zzgv.zzd(bArr);
    }

    public static String zze(byte[] bArr) {
        return new String(bArr, UTF_8);
    }

    public static int hashCode(byte[] bArr) {
        int length = bArr.length;
        int iZza = zza(length, bArr, 0, length);
        if (iZza == 0) {
            return 1;
        }
        return iZza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    static boolean zzf(zzfh zzfhVar) {
        if (!(zzfhVar instanceof zzcn)) {
            return false;
        }
        return false;
    }

    static Object zza(Object obj, Object obj2) {
        return ((zzfh) obj).zzbq().zza((zzfh) obj2).zzbw();
    }

    static {
        byte[] bArr = new byte[0];
        zzla = bArr;
        zzlb = ByteBuffer.wrap(bArr);
        zzlc = zzdf.zza(bArr, 0, bArr.length, false);
    }
}
