package com.google.android.gms.internal.firebase_messaging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Queue;

/* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
/* loaded from: classes2.dex */
public final class zzh {
    private static final OutputStream zza = new zzg();

    private static byte[] zza(Queue<byte[]> queue, int i) {
        byte[] bArr = new byte[i];
        int i2 = i;
        while (i2 > 0) {
            byte[] bArrRemove = queue.remove();
            int iMin = Math.min(i2, bArrRemove.length);
            System.arraycopy(bArrRemove, 0, bArr, i - i2, iMin);
            i2 -= iMin;
        }
        return bArr;
    }

    public static byte[] zza(InputStream inputStream) throws IOException {
        zze.zza(inputStream);
        ArrayDeque arrayDeque = new ArrayDeque(20);
        int iZza = 8192;
        int i = 0;
        while (i < 2147483639) {
            int iMin = Math.min(iZza, 2147483639 - i);
            byte[] bArr = new byte[iMin];
            arrayDeque.add(bArr);
            int i2 = 0;
            while (i2 < iMin) {
                int i3 = inputStream.read(bArr, i2, iMin - i2);
                if (i3 == -1) {
                    return zza(arrayDeque, i);
                }
                i2 += i3;
                i += i3;
            }
            iZza = zzl.zza(iZza, 2);
        }
        if (inputStream.read() == -1) {
            return zza(arrayDeque, 2147483639);
        }
        throw new OutOfMemoryError("input is too large to fit in a byte array");
    }

    public static InputStream zza(InputStream inputStream, long j) {
        return new zzj(inputStream, 1048577L);
    }
}
