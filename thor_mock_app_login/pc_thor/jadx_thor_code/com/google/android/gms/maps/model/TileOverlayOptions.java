package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.maps.zzaf;
import com.google.android.gms.internal.maps.zzag;

/* loaded from: classes2.dex */
public final class TileOverlayOptions extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TileOverlayOptions> CREATOR = new zzu();
    private float zzcs;
    private boolean zzct;
    private float zzda;
    private zzaf zzei;
    private TileProvider zzej;
    private boolean zzek;

    public TileOverlayOptions() {
        this.zzct = true;
        this.zzek = true;
        this.zzda = 0.0f;
    }

    TileOverlayOptions(IBinder iBinder, boolean z, float f, boolean z2, float f2) {
        this.zzct = true;
        this.zzek = true;
        this.zzda = 0.0f;
        zzaf zzafVarZzk = zzag.zzk(iBinder);
        this.zzei = zzafVarZzk;
        this.zzej = zzafVarZzk == null ? null : new zzs(this);
        this.zzct = z;
        this.zzcs = f;
        this.zzek = z2;
        this.zzda = f2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIBinder(parcel, 2, this.zzei.asBinder(), false);
        SafeParcelWriter.writeBoolean(parcel, 3, isVisible());
        SafeParcelWriter.writeFloat(parcel, 4, getZIndex());
        SafeParcelWriter.writeBoolean(parcel, 5, getFadeIn());
        SafeParcelWriter.writeFloat(parcel, 6, getTransparency());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final TileOverlayOptions tileProvider(TileProvider tileProvider) {
        this.zzej = tileProvider;
        this.zzei = tileProvider == null ? null : new zzt(this, tileProvider);
        return this;
    }

    public final TileOverlayOptions zIndex(float f) {
        this.zzcs = f;
        return this;
    }

    public final TileOverlayOptions visible(boolean z) {
        this.zzct = z;
        return this;
    }

    public final TileOverlayOptions fadeIn(boolean z) {
        this.zzek = z;
        return this;
    }

    public final TileOverlayOptions transparency(float f) {
        Preconditions.checkArgument(f >= 0.0f && f <= 1.0f, "Transparency must be in the range [0..1]");
        this.zzda = f;
        return this;
    }

    public final TileProvider getTileProvider() {
        return this.zzej;
    }

    public final float getZIndex() {
        return this.zzcs;
    }

    public final boolean isVisible() {
        return this.zzct;
    }

    public final boolean getFadeIn() {
        return this.zzek;
    }

    public final float getTransparency() {
        return this.zzda;
    }
}
