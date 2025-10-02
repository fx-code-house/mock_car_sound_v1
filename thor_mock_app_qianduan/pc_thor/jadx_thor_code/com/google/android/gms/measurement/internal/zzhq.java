package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzhq implements zzky {
    private final /* synthetic */ zzhb zza;

    zzhq(zzhb zzhbVar) {
        this.zza = zzhbVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzky
    public final void zza(String str, Bundle bundle) throws IllegalStateException {
        if (TextUtils.isEmpty(str)) {
            this.zza.zza("auto", "_err", bundle);
        } else {
            this.zza.zza("auto", "_err", bundle, str);
        }
    }
}
