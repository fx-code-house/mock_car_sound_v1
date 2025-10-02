package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/* loaded from: classes2.dex */
public final class GoogleMapOptions extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<GoogleMapOptions> CREATOR = new zzaa();
    private int mapType;
    private Boolean zzaj;
    private Boolean zzak;
    private CameraPosition zzal;
    private Boolean zzam;
    private Boolean zzan;
    private Boolean zzao;
    private Boolean zzap;
    private Boolean zzaq;
    private Boolean zzar;
    private Boolean zzas;
    private Boolean zzat;
    private Boolean zzau;
    private Float zzav;
    private Float zzaw;
    private LatLngBounds zzax;
    private Boolean zzay;

    GoogleMapOptions(byte b, byte b2, int i, CameraPosition cameraPosition, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8, byte b9, byte b10, byte b11, Float f, Float f2, LatLngBounds latLngBounds, byte b12) {
        this.mapType = -1;
        this.zzav = null;
        this.zzaw = null;
        this.zzax = null;
        this.zzaj = com.google.android.gms.maps.internal.zza.zza(b);
        this.zzak = com.google.android.gms.maps.internal.zza.zza(b2);
        this.mapType = i;
        this.zzal = cameraPosition;
        this.zzam = com.google.android.gms.maps.internal.zza.zza(b3);
        this.zzan = com.google.android.gms.maps.internal.zza.zza(b4);
        this.zzao = com.google.android.gms.maps.internal.zza.zza(b5);
        this.zzap = com.google.android.gms.maps.internal.zza.zza(b6);
        this.zzaq = com.google.android.gms.maps.internal.zza.zza(b7);
        this.zzar = com.google.android.gms.maps.internal.zza.zza(b8);
        this.zzas = com.google.android.gms.maps.internal.zza.zza(b9);
        this.zzat = com.google.android.gms.maps.internal.zza.zza(b10);
        this.zzau = com.google.android.gms.maps.internal.zza.zza(b11);
        this.zzav = f;
        this.zzaw = f2;
        this.zzax = latLngBounds;
        this.zzay = com.google.android.gms.maps.internal.zza.zza(b12);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeByte(parcel, 2, com.google.android.gms.maps.internal.zza.zza(this.zzaj));
        SafeParcelWriter.writeByte(parcel, 3, com.google.android.gms.maps.internal.zza.zza(this.zzak));
        SafeParcelWriter.writeInt(parcel, 4, getMapType());
        SafeParcelWriter.writeParcelable(parcel, 5, getCamera(), i, false);
        SafeParcelWriter.writeByte(parcel, 6, com.google.android.gms.maps.internal.zza.zza(this.zzam));
        SafeParcelWriter.writeByte(parcel, 7, com.google.android.gms.maps.internal.zza.zza(this.zzan));
        SafeParcelWriter.writeByte(parcel, 8, com.google.android.gms.maps.internal.zza.zza(this.zzao));
        SafeParcelWriter.writeByte(parcel, 9, com.google.android.gms.maps.internal.zza.zza(this.zzap));
        SafeParcelWriter.writeByte(parcel, 10, com.google.android.gms.maps.internal.zza.zza(this.zzaq));
        SafeParcelWriter.writeByte(parcel, 11, com.google.android.gms.maps.internal.zza.zza(this.zzar));
        SafeParcelWriter.writeByte(parcel, 12, com.google.android.gms.maps.internal.zza.zza(this.zzas));
        SafeParcelWriter.writeByte(parcel, 14, com.google.android.gms.maps.internal.zza.zza(this.zzat));
        SafeParcelWriter.writeByte(parcel, 15, com.google.android.gms.maps.internal.zza.zza(this.zzau));
        SafeParcelWriter.writeFloatObject(parcel, 16, getMinZoomPreference(), false);
        SafeParcelWriter.writeFloatObject(parcel, 17, getMaxZoomPreference(), false);
        SafeParcelWriter.writeParcelable(parcel, 18, getLatLngBoundsForCameraTarget(), i, false);
        SafeParcelWriter.writeByte(parcel, 19, com.google.android.gms.maps.internal.zza.zza(this.zzay));
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public GoogleMapOptions() {
        this.mapType = -1;
        this.zzav = null;
        this.zzaw = null;
        this.zzax = null;
    }

    public final GoogleMapOptions zOrderOnTop(boolean z) {
        this.zzaj = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions useViewLifecycleInFragment(boolean z) {
        this.zzak = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions mapType(int i) {
        this.mapType = i;
        return this;
    }

    public final GoogleMapOptions camera(CameraPosition cameraPosition) {
        this.zzal = cameraPosition;
        return this;
    }

    public final GoogleMapOptions zoomControlsEnabled(boolean z) {
        this.zzam = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions compassEnabled(boolean z) {
        this.zzan = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions scrollGesturesEnabled(boolean z) {
        this.zzao = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions zoomGesturesEnabled(boolean z) {
        this.zzap = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions tiltGesturesEnabled(boolean z) {
        this.zzaq = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions rotateGesturesEnabled(boolean z) {
        this.zzar = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions scrollGesturesEnabledDuringRotateOrZoom(boolean z) {
        this.zzay = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions liteMode(boolean z) {
        this.zzas = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions mapToolbarEnabled(boolean z) {
        this.zzat = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions ambientEnabled(boolean z) {
        this.zzau = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions minZoomPreference(float f) {
        this.zzav = Float.valueOf(f);
        return this;
    }

    public final GoogleMapOptions maxZoomPreference(float f) {
        this.zzaw = Float.valueOf(f);
        return this;
    }

    public final GoogleMapOptions latLngBoundsForCameraTarget(LatLngBounds latLngBounds) {
        this.zzax = latLngBounds;
        return this;
    }

    public final Boolean getZOrderOnTop() {
        return this.zzaj;
    }

    public final Boolean getUseViewLifecycleInFragment() {
        return this.zzak;
    }

    public final int getMapType() {
        return this.mapType;
    }

    public final CameraPosition getCamera() {
        return this.zzal;
    }

    public final Boolean getZoomControlsEnabled() {
        return this.zzam;
    }

    public final Boolean getCompassEnabled() {
        return this.zzan;
    }

    public final Boolean getScrollGesturesEnabled() {
        return this.zzao;
    }

    public final Boolean getZoomGesturesEnabled() {
        return this.zzap;
    }

    public final Boolean getTiltGesturesEnabled() {
        return this.zzaq;
    }

    public final Boolean getRotateGesturesEnabled() {
        return this.zzar;
    }

    public final Boolean getScrollGesturesEnabledDuringRotateOrZoom() {
        return this.zzay;
    }

    public final Boolean getLiteMode() {
        return this.zzas;
    }

    public final Boolean getMapToolbarEnabled() {
        return this.zzat;
    }

    public final Boolean getAmbientEnabled() {
        return this.zzau;
    }

    public final Float getMinZoomPreference() {
        return this.zzav;
    }

    public final Float getMaxZoomPreference() {
        return this.zzaw;
    }

    public final LatLngBounds getLatLngBoundsForCameraTarget() {
        return this.zzax;
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attributeSet) {
        if (context == null || attributeSet == null) {
            return null;
        }
        TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_mapType)) {
            googleMapOptions.mapType(typedArrayObtainAttributes.getInt(R.styleable.MapAttrs_mapType, -1));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_zOrderOnTop)) {
            googleMapOptions.zOrderOnTop(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_zOrderOnTop, false));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_useViewLifecycle)) {
            googleMapOptions.useViewLifecycleInFragment(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_useViewLifecycle, false));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_uiCompass)) {
            googleMapOptions.compassEnabled(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_uiCompass, true));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_uiRotateGestures)) {
            googleMapOptions.rotateGesturesEnabled(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_uiRotateGestures, true));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_uiScrollGesturesDuringRotateOrZoom)) {
            googleMapOptions.scrollGesturesEnabledDuringRotateOrZoom(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_uiScrollGesturesDuringRotateOrZoom, true));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_uiScrollGestures)) {
            googleMapOptions.scrollGesturesEnabled(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_uiScrollGestures, true));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_uiTiltGestures)) {
            googleMapOptions.tiltGesturesEnabled(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_uiTiltGestures, true));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_uiZoomGestures)) {
            googleMapOptions.zoomGesturesEnabled(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_uiZoomGestures, true));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_uiZoomControls)) {
            googleMapOptions.zoomControlsEnabled(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_uiZoomControls, true));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_liteMode)) {
            googleMapOptions.liteMode(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_liteMode, false));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_uiMapToolbar)) {
            googleMapOptions.mapToolbarEnabled(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_uiMapToolbar, true));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_ambientEnabled)) {
            googleMapOptions.ambientEnabled(typedArrayObtainAttributes.getBoolean(R.styleable.MapAttrs_ambientEnabled, false));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_cameraMinZoomPreference)) {
            googleMapOptions.minZoomPreference(typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_cameraMinZoomPreference, Float.NEGATIVE_INFINITY));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_cameraMinZoomPreference)) {
            googleMapOptions.maxZoomPreference(typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_cameraMaxZoomPreference, Float.POSITIVE_INFINITY));
        }
        googleMapOptions.latLngBoundsForCameraTarget(zza(context, attributeSet));
        googleMapOptions.camera(zzb(context, attributeSet));
        typedArrayObtainAttributes.recycle();
        return googleMapOptions;
    }

    public static LatLngBounds zza(Context context, AttributeSet attributeSet) {
        if (context == null || attributeSet == null) {
            return null;
        }
        TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        Float fValueOf = typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_latLngBoundsSouthWestLatitude) ? Float.valueOf(typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_latLngBoundsSouthWestLatitude, 0.0f)) : null;
        Float fValueOf2 = typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_latLngBoundsSouthWestLongitude) ? Float.valueOf(typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_latLngBoundsSouthWestLongitude, 0.0f)) : null;
        Float fValueOf3 = typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_latLngBoundsNorthEastLatitude) ? Float.valueOf(typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_latLngBoundsNorthEastLatitude, 0.0f)) : null;
        Float fValueOf4 = typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_latLngBoundsNorthEastLongitude) ? Float.valueOf(typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_latLngBoundsNorthEastLongitude, 0.0f)) : null;
        typedArrayObtainAttributes.recycle();
        if (fValueOf == null || fValueOf2 == null || fValueOf3 == null || fValueOf4 == null) {
            return null;
        }
        return new LatLngBounds(new LatLng(fValueOf.floatValue(), fValueOf2.floatValue()), new LatLng(fValueOf3.floatValue(), fValueOf4.floatValue()));
    }

    public static CameraPosition zzb(Context context, AttributeSet attributeSet) {
        if (context == null || attributeSet == null) {
            return null;
        }
        TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        LatLng latLng = new LatLng(typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_cameraTargetLat) ? typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_cameraTargetLat, 0.0f) : 0.0f, typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_cameraTargetLng) ? typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_cameraTargetLng, 0.0f) : 0.0f);
        CameraPosition.Builder builder = CameraPosition.builder();
        builder.target(latLng);
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_cameraZoom)) {
            builder.zoom(typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_cameraZoom, 0.0f));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_cameraBearing)) {
            builder.bearing(typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_cameraBearing, 0.0f));
        }
        if (typedArrayObtainAttributes.hasValue(R.styleable.MapAttrs_cameraTilt)) {
            builder.tilt(typedArrayObtainAttributes.getFloat(R.styleable.MapAttrs_cameraTilt, 0.0f));
        }
        typedArrayObtainAttributes.recycle();
        return builder.build();
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("MapType", Integer.valueOf(this.mapType)).add("LiteMode", this.zzas).add("Camera", this.zzal).add("CompassEnabled", this.zzan).add("ZoomControlsEnabled", this.zzam).add("ScrollGesturesEnabled", this.zzao).add("ZoomGesturesEnabled", this.zzap).add("TiltGesturesEnabled", this.zzaq).add("RotateGesturesEnabled", this.zzar).add("ScrollGesturesEnabledDuringRotateOrZoom", this.zzay).add("MapToolbarEnabled", this.zzat).add("AmbientEnabled", this.zzau).add("MinZoomPreference", this.zzav).add("MaxZoomPreference", this.zzaw).add("LatLngBoundsForCameraTarget", this.zzax).add("ZOrderOnTop", this.zzaj).add("UseViewLifecycleInFragment", this.zzak).toString();
    }
}
