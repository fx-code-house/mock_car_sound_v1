package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.IndoorBuilding;

/* loaded from: classes2.dex */
final class zza extends com.google.android.gms.maps.internal.zzaa {
    private final /* synthetic */ GoogleMap.OnIndoorStateChangeListener zzi;

    zza(GoogleMap googleMap, GoogleMap.OnIndoorStateChangeListener onIndoorStateChangeListener) {
        this.zzi = onIndoorStateChangeListener;
    }

    @Override // com.google.android.gms.maps.internal.zzz
    public final void onIndoorBuildingFocused() {
        this.zzi.onIndoorBuildingFocused();
    }

    @Override // com.google.android.gms.maps.internal.zzz
    public final void zza(com.google.android.gms.internal.maps.zzn zznVar) {
        this.zzi.onIndoorLevelActivated(new IndoorBuilding(zznVar));
    }
}
