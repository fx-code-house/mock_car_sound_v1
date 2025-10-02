package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;

/* loaded from: classes2.dex */
final class zzx extends com.google.android.gms.maps.internal.zzo {
    private final /* synthetic */ GoogleMap.OnCameraIdleListener zzaf;

    zzx(GoogleMap googleMap, GoogleMap.OnCameraIdleListener onCameraIdleListener) {
        this.zzaf = onCameraIdleListener;
    }

    @Override // com.google.android.gms.maps.internal.zzn
    public final void onCameraIdle() {
        this.zzaf.onCameraIdle();
    }
}
