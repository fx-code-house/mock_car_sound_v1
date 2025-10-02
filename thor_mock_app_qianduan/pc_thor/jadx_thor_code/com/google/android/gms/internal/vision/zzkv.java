package com.google.android.gms.internal.vision;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzkv extends RuntimeException {
    private final List<String> zzace;

    public zzkv(zzjn zzjnVar) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
        this.zzace = null;
    }
}
