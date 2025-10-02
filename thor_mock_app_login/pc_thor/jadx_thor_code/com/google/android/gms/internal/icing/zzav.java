package com.google.android.gms.internal.icing;

import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.search.GoogleNowAuthState;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzav extends zzar {
    private final /* synthetic */ zzaw zzbq;

    zzav(zzaw zzawVar) {
        this.zzbq = zzawVar;
    }

    @Override // com.google.android.gms.internal.icing.zzar, com.google.android.gms.internal.icing.zzam
    public final void zza(Status status, GoogleNowAuthState googleNowAuthState) {
        if (this.zzbq.zzbp) {
            Log.d("SearchAuth", "GetGoogleNowAuthImpl success");
        }
        this.zzbq.setResult((zzaw) new zzay(status, googleNowAuthState));
    }
}
