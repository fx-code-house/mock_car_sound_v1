package com.google.android.gms.internal.wearable;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.common.base.Ascii;
import okio.Utf8;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzeh {
    static /* synthetic */ boolean zza(byte b) {
        return b >= 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0016 A[PHI: r2
      0x0016: PHI (r2v3 byte) = (r2v2 byte), (r2v9 byte) binds: [B:9:0x0011, B:11:0x0015] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static /* synthetic */ void zzc(byte r2, byte r3, byte r4, char[] r5, int r6) throws com.google.android.gms.internal.wearable.zzcc {
        /*
            boolean r0 = zze(r3)
            if (r0 != 0) goto L2c
            r0 = -96
            r1 = -32
            if (r2 != r1) goto Lf
            if (r3 < r0) goto L2c
            r2 = r1
        Lf:
            r1 = -19
            if (r2 != r1) goto L16
            if (r3 >= r0) goto L2c
            r2 = r1
        L16:
            boolean r0 = zze(r4)
            if (r0 != 0) goto L2c
            r2 = r2 & 15
            int r2 = r2 << 12
            r3 = r3 & 63
            int r3 = r3 << 6
            r2 = r2 | r3
            r3 = r4 & 63
            r2 = r2 | r3
            char r2 = (char) r2
            r5[r6] = r2
            return
        L2c:
            com.google.android.gms.internal.wearable.zzcc r2 = com.google.android.gms.internal.wearable.zzcc.zzg()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.wearable.zzeh.zzc(byte, byte, byte, char[], int):void");
    }

    private static boolean zze(byte b) {
        return b > -65;
    }

    static /* synthetic */ void zzb(byte b, byte b2, char[] cArr, int i) throws zzcc {
        if (b < -62 || zze(b2)) {
            throw zzcc.zzg();
        }
        cArr[i] = (char) (((b & Ascii.US) << 6) | (b2 & Utf8.REPLACEMENT_BYTE));
    }

    static /* synthetic */ void zzd(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) throws zzcc {
        if (zze(b2) || (((b << Ascii.FS) + (b2 + 112)) >> 30) != 0 || zze(b3) || zze(b4)) {
            throw zzcc.zzg();
        }
        int i2 = ((b & 7) << 18) | ((b2 & Utf8.REPLACEMENT_BYTE) << 12) | ((b3 & Utf8.REPLACEMENT_BYTE) << 6) | (b4 & Utf8.REPLACEMENT_BYTE);
        cArr[i] = (char) ((i2 >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
        cArr[i + 1] = (char) ((i2 & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) + Utf8.LOG_SURROGATE_HEADER);
    }
}
