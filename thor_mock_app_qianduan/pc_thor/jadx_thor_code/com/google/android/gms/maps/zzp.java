package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzbe;
import com.google.android.gms.maps.model.Polygon;

/* loaded from: classes2.dex */
final class zzp extends zzbe {
    private final /* synthetic */ GoogleMap.OnPolygonClickListener zzx;

    zzp(GoogleMap googleMap, GoogleMap.OnPolygonClickListener onPolygonClickListener) {
        this.zzx = onPolygonClickListener;
    }

    @Override // com.google.android.gms.maps.internal.zzbd
    public final void zza(com.google.android.gms.internal.maps.zzw zzwVar) {
        this.zzx.onPolygonClick(new Polygon(zzwVar));
    }
}
