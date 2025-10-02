package com.google.android.gms.common.api.internal;

import java.lang.ref.WeakReference;

/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes2.dex */
final class zabd extends zabw {
    private final WeakReference<zabe> zaa;

    zabd(zabe zabeVar) {
        this.zaa = new WeakReference<>(zabeVar);
    }

    @Override // com.google.android.gms.common.api.internal.zabw
    public final void zaa() {
        zabe zabeVar = this.zaa.get();
        if (zabeVar == null) {
            return;
        }
        zabe.zai(zabeVar);
    }
}
