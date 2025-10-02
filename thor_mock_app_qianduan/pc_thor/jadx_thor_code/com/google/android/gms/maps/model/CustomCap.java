package com.google.android.gms.maps.model;

import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes2.dex */
public final class CustomCap extends Cap {
    public final BitmapDescriptor bitmapDescriptor;
    public final float refWidth;

    /* JADX WARN: Illegal instructions before constructor call */
    public CustomCap(BitmapDescriptor bitmapDescriptor, float f) {
        BitmapDescriptor bitmapDescriptor2 = (BitmapDescriptor) Preconditions.checkNotNull(bitmapDescriptor, "bitmapDescriptor must not be null");
        if (f <= 0.0f) {
            throw new IllegalArgumentException("refWidth must be positive");
        }
        super(bitmapDescriptor2, f);
        this.bitmapDescriptor = bitmapDescriptor;
        this.refWidth = f;
    }

    public CustomCap(BitmapDescriptor bitmapDescriptor) {
        this(bitmapDescriptor, 10.0f);
    }

    @Override // com.google.android.gms.maps.model.Cap
    public final String toString() {
        String strValueOf = String.valueOf(this.bitmapDescriptor);
        return new StringBuilder(String.valueOf(strValueOf).length() + 55).append("[CustomCap: bitmapDescriptor=").append(strValueOf).append(" refWidth=").append(this.refWidth).append("]").toString();
    }
}
