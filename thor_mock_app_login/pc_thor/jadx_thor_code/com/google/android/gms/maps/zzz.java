package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzao;
import com.google.android.gms.maps.model.LatLng;

/* loaded from: classes2.dex */
final class zzz extends zzao {
    private final /* synthetic */ GoogleMap.OnMapLongClickListener zzah;

    zzz(GoogleMap googleMap, GoogleMap.OnMapLongClickListener onMapLongClickListener) {
        this.zzah = onMapLongClickListener;
    }

    @Override // com.google.android.gms.maps.internal.zzan
    public final void onMapLongClick(LatLng latLng) {
        this.zzah.onMapLongClick(latLng);
    }
}
