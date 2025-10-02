package com.google.android.gms.common.api.internal;

import java.lang.ref.WeakReference;

/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes2.dex */
final class zaar extends com.google.android.gms.signin.internal.zac {
    private final WeakReference<zaaw> zaa;

    zaar(zaaw zaawVar) {
        this.zaa = new WeakReference<>(zaawVar);
    }

    @Override // com.google.android.gms.signin.internal.zac, com.google.android.gms.signin.internal.zae
    public final void zab(com.google.android.gms.signin.internal.zak zakVar) {
        zaaw zaawVar = this.zaa.get();
        if (zaawVar == null) {
            return;
        }
        zaawVar.zaa.zal(new zaaq(this, zaawVar, zaawVar, zakVar));
    }
}
