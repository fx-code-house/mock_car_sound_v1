package com.google.android.gms.vision.clearcut;

import android.content.Context;
import com.google.android.gms.internal.vision.zze;
import com.google.android.gms.internal.vision.zzfl;
import com.google.android.gms.internal.vision.zzi;
import com.google.android.gms.vision.L;
import java.util.concurrent.ExecutorService;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class DynamiteClearcutLogger {
    private static final ExecutorService zzbv = zze.zzb().zza(2, zzi.zzu);
    private zzb zzbw = new zzb(0.03333333333333333d);
    private VisionClearcutLogger zzbx;

    public DynamiteClearcutLogger(Context context) {
        this.zzbx = new VisionClearcutLogger(context);
    }

    public final void zza(int i, zzfl.zzo zzoVar) {
        if (i == 3 && !this.zzbw.tryAcquire()) {
            L.v("Skipping image analysis log due to rate limiting", new Object[0]);
        } else {
            zzbv.execute(new zza(this, i, zzoVar));
        }
    }
}
