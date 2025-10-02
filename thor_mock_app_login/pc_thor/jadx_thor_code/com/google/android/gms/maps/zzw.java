package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;

/* loaded from: classes2.dex */
final class zzw extends com.google.android.gms.maps.internal.zzq {
    private final /* synthetic */ GoogleMap.OnCameraMoveCanceledListener zzae;

    zzw(GoogleMap googleMap, GoogleMap.OnCameraMoveCanceledListener onCameraMoveCanceledListener) {
        this.zzae = onCameraMoveCanceledListener;
    }

    @Override // com.google.android.gms.maps.internal.zzp
    public final void onCameraMoveCanceled() {
        this.zzae.onCameraMoveCanceled();
    }
}
