package com.google.android.gms.vision.clearcut;

import android.content.Context;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.internal.vision.zzfd;
import com.google.android.gms.internal.vision.zzfl;
import com.google.android.gms.internal.vision.zzho;
import com.google.android.gms.vision.L;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class VisionClearcutLogger {
    private final ClearcutLogger zzcd;
    private boolean zzce = true;

    public VisionClearcutLogger(Context context) {
        this.zzcd = new ClearcutLogger(context, "VISION", null);
    }

    public final void zzb(int i, zzfl.zzo zzoVar) {
        byte[] byteArray = zzoVar.toByteArray();
        if (i < 0 || i > 3) {
            L.i("Illegal event code: %d", Integer.valueOf(i));
            return;
        }
        try {
            if (this.zzce) {
                this.zzcd.newEvent(byteArray).setEventCode(i).log();
                return;
            }
            zzfl.zzo.zza zzaVarZzec = zzfl.zzo.zzec();
            try {
                zzaVarZzec.zza(byteArray, 0, byteArray.length, zzho.zzgg());
                L.e("Would have logged:\n%s", zzaVarZzec.toString());
            } catch (Exception e) {
                L.e(e, "Parsing error", new Object[0]);
            }
        } catch (Exception e2) {
            zzfd.zza(e2);
            L.e(e2, "Failed to log", new Object[0]);
        }
    }
}
