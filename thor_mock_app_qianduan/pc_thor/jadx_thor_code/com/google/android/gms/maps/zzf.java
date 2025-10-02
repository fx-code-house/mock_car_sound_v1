package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/* loaded from: classes2.dex */
final class zzf extends com.google.android.gms.maps.internal.zzae {
    private final /* synthetic */ GoogleMap.OnInfoWindowCloseListener zzn;

    zzf(GoogleMap googleMap, GoogleMap.OnInfoWindowCloseListener onInfoWindowCloseListener) {
        this.zzn = onInfoWindowCloseListener;
    }

    @Override // com.google.android.gms.maps.internal.zzad
    public final void zzg(com.google.android.gms.internal.maps.zzt zztVar) {
        this.zzn.onInfoWindowClose(new Marker(zztVar));
    }
}
