package com.google.firebase.appindexing.internal;

import java.util.Comparator;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final /* synthetic */ class zzac implements Comparator {
    static final Comparator zzfy = new zzac();

    private zzac() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Thing.zzb((String) obj, (String) obj2);
    }
}
