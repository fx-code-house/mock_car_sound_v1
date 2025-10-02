package com.google.android.gms.internal.wearable;

import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzaa extends zzab {
    public static List<Float> zza(float... fArr) {
        int length = fArr.length;
        return length == 0 ? Collections.emptyList() : new zzz(fArr, 0, length);
    }

    static /* synthetic */ int zzb(float[] fArr, float f, int i, int i2) {
        while (i < i2) {
            if (fArr[i] == f) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
