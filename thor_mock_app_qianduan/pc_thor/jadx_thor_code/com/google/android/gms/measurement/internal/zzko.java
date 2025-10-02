package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzko implements zzky {
    final /* synthetic */ zzkl zza;

    zzko(zzkl zzklVar) {
        this.zza = zzklVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzky
    public final void zza(String str, Bundle bundle) throws IllegalStateException {
        if (TextUtils.isEmpty(str)) {
            this.zza.zzk.zzq().zze().zza("AppId not known when logging error event");
        } else {
            this.zza.zzp().zza(new zzkq(this, str, bundle));
        }
    }
}
