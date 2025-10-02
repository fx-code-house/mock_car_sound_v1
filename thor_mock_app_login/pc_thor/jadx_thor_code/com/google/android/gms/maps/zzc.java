package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzau;
import com.google.android.gms.maps.model.Marker;

/* loaded from: classes2.dex */
final class zzc extends zzau {
    private final /* synthetic */ GoogleMap.OnMarkerDragListener zzk;

    zzc(GoogleMap googleMap, GoogleMap.OnMarkerDragListener onMarkerDragListener) {
        this.zzk = onMarkerDragListener;
    }

    @Override // com.google.android.gms.maps.internal.zzat
    public final void zzb(com.google.android.gms.internal.maps.zzt zztVar) {
        this.zzk.onMarkerDragStart(new Marker(zztVar));
    }

    @Override // com.google.android.gms.maps.internal.zzat
    public final void zzc(com.google.android.gms.internal.maps.zzt zztVar) {
        this.zzk.onMarkerDragEnd(new Marker(zztVar));
    }

    @Override // com.google.android.gms.maps.internal.zzat
    public final void zzd(com.google.android.gms.internal.maps.zzt zztVar) {
        this.zzk.onMarkerDrag(new Marker(zztVar));
    }
}
