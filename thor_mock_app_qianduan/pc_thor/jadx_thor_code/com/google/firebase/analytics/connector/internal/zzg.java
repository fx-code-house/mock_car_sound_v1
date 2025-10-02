package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* compiled from: com.google.android.gms:play-services-measurement-api@@18.0.0 */
/* loaded from: classes2.dex */
final class zzg implements AppMeasurementSdk.OnEventListener {
    private final /* synthetic */ zze zza;

    public zzg(zze zzeVar) {
        this.zza = zzeVar;
    }

    @Override // com.google.android.gms.measurement.api.AppMeasurementSdk.OnEventListener, com.google.android.gms.measurement.internal.zzgz
    public final void onEvent(String str, String str2, Bundle bundle, long j) {
        if (str == null || str.equals(AppMeasurement.CRASH_ORIGIN) || !zzd.zzb(str2)) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.NAME, str2);
        bundle2.putLong("timestampInMillis", j);
        bundle2.putBundle("params", bundle);
        this.zza.zza.onMessageTriggered(3, bundle2);
    }
}
