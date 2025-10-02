package com.google.android.gms.internal.icing;

import java.util.List;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzgn extends RuntimeException {
    private final List<String> zzoe;

    public zzgn(zzfh zzfhVar) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
        this.zzoe = null;
    }
}
