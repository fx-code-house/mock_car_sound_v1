package com.google.android.gms.internal.measurement;

import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzfz extends zzfy {
    public static int zza(int i, int i2, int i3) {
        if (!(i2 <= 1073741823)) {
            throw new IllegalArgumentException(zzed.zza("min (%s) must be less than or equal to max (%s)", Integer.valueOf(i2), Integer.valueOf(LockFreeTaskQueueCore.MAX_CAPACITY_MASK)));
        }
        return Math.min(Math.max(i, i2), LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
    }
}
