package com.google.android.gms.internal.vision;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzie {
    public static final byte[] zzyy;
    private static final ByteBuffer zzyz;
    private static final zzhe zzza;
    static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    public static int zzab(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public static int zzm(boolean z) {
        return z ? 1231 : 1237;
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

    public static boolean zzg(byte[] bArr) {
        return zzld.zzg(bArr);
    }

    public static String zzh(byte[] bArr) {
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

    static boolean zzf(zzjn zzjnVar) {
        if (!(zzjnVar instanceof zzgg)) {
            return false;
        }
        return false;
    }

    static Object zzb(Object obj, Object obj2) {
        return ((zzjn) obj).zzhc().zza((zzjn) obj2).zzgv();
    }

    static {
        byte[] bArr = new byte[0];
        zzyy = bArr;
        zzyz = ByteBuffer.wrap(bArr);
        zzza = zzhe.zza(bArr, 0, bArr.length, false);
    }
}
