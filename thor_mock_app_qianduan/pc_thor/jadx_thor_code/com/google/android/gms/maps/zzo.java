package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;

/* loaded from: classes2.dex */
final class zzo extends com.google.android.gms.maps.internal.zzw {
    private final /* synthetic */ GoogleMap.OnCircleClickListener zzw;

    zzo(GoogleMap googleMap, GoogleMap.OnCircleClickListener onCircleClickListener) {
        this.zzw = onCircleClickListener;
    }

    @Override // com.google.android.gms.maps.internal.zzv
    public final void zza(com.google.android.gms.internal.maps.zzh zzhVar) {
        this.zzw.onCircleClick(new Circle(zzhVar));
    }
}
