package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

/* loaded from: classes2.dex */
public final class zzg extends com.google.android.gms.internal.maps.zza implements IGoogleMapDelegate {
    zzg(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IGoogleMapDelegate");
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final CameraPosition getCameraPosition() throws RemoteException {
        Parcel parcelZza = zza(1, zza());
        CameraPosition cameraPosition = (CameraPosition) com.google.android.gms.internal.maps.zzc.zza(parcelZza, CameraPosition.CREATOR);
        parcelZza.recycle();
        return cameraPosition;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final float getMaxZoomLevel() throws RemoteException {
        Parcel parcelZza = zza(2, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final float getMinZoomLevel() throws RemoteException {
        Parcel parcelZza = zza(3, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void moveCamera(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iObjectWrapper);
        zzb(4, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void animateCamera(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iObjectWrapper);
        zzb(5, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void animateCameraWithCallback(IObjectWrapper iObjectWrapper, zzc zzcVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iObjectWrapper);
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzcVar);
        zzb(6, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void animateCameraWithDurationAndCallback(IObjectWrapper iObjectWrapper, int i, zzc zzcVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iObjectWrapper);
        parcelZza.writeInt(i);
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzcVar);
        zzb(7, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void stopAnimation() throws RemoteException {
        zzb(8, zza());
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final com.google.android.gms.internal.maps.zzz addPolyline(PolylineOptions polylineOptions) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, polylineOptions);
        Parcel parcelZza2 = zza(9, parcelZza);
        com.google.android.gms.internal.maps.zzz zzzVarZzi = com.google.android.gms.internal.maps.zzaa.zzi(parcelZza2.readStrongBinder());
        parcelZza2.recycle();
        return zzzVarZzi;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final com.google.android.gms.internal.maps.zzw addPolygon(PolygonOptions polygonOptions) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, polygonOptions);
        Parcel parcelZza2 = zza(10, parcelZza);
        com.google.android.gms.internal.maps.zzw zzwVarZzh = com.google.android.gms.internal.maps.zzx.zzh(parcelZza2.readStrongBinder());
        parcelZza2.recycle();
        return zzwVarZzh;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final com.google.android.gms.internal.maps.zzt addMarker(MarkerOptions markerOptions) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, markerOptions);
        Parcel parcelZza2 = zza(11, parcelZza);
        com.google.android.gms.internal.maps.zzt zztVarZzg = com.google.android.gms.internal.maps.zzu.zzg(parcelZza2.readStrongBinder());
        parcelZza2.recycle();
        return zztVarZzg;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final com.google.android.gms.internal.maps.zzk addGroundOverlay(GroundOverlayOptions groundOverlayOptions) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, groundOverlayOptions);
        Parcel parcelZza2 = zza(12, parcelZza);
        com.google.android.gms.internal.maps.zzk zzkVarZzd = com.google.android.gms.internal.maps.zzl.zzd(parcelZza2.readStrongBinder());
        parcelZza2.recycle();
        return zzkVarZzd;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final com.google.android.gms.internal.maps.zzac addTileOverlay(TileOverlayOptions tileOverlayOptions) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, tileOverlayOptions);
        Parcel parcelZza2 = zza(13, parcelZza);
        com.google.android.gms.internal.maps.zzac zzacVarZzj = com.google.android.gms.internal.maps.zzad.zzj(parcelZza2.readStrongBinder());
        parcelZza2.recycle();
        return zzacVarZzj;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void clear() throws RemoteException {
        zzb(14, zza());
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final int getMapType() throws RemoteException {
        Parcel parcelZza = zza(15, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setMapType(int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        zzb(16, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final boolean isTrafficEnabled() throws RemoteException {
        Parcel parcelZza = zza(17, zza());
        boolean zZza = com.google.android.gms.internal.maps.zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setTrafficEnabled(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.writeBoolean(parcelZza, z);
        zzb(18, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final boolean isIndoorEnabled() throws RemoteException {
        Parcel parcelZza = zza(19, zza());
        boolean zZza = com.google.android.gms.internal.maps.zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final boolean setIndoorEnabled(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.writeBoolean(parcelZza, z);
        Parcel parcelZza2 = zza(20, parcelZza);
        boolean zZza = com.google.android.gms.internal.maps.zzc.zza(parcelZza2);
        parcelZza2.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final boolean isMyLocationEnabled() throws RemoteException {
        Parcel parcelZza = zza(21, zza());
        boolean zZza = com.google.android.gms.internal.maps.zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setMyLocationEnabled(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.writeBoolean(parcelZza, z);
        zzb(22, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final Location getMyLocation() throws RemoteException {
        Parcel parcelZza = zza(23, zza());
        Location location = (Location) com.google.android.gms.internal.maps.zzc.zza(parcelZza, Location.CREATOR);
        parcelZza.recycle();
        return location;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setLocationSource(ILocationSourceDelegate iLocationSourceDelegate) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iLocationSourceDelegate);
        zzb(24, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final IUiSettingsDelegate getUiSettings() throws RemoteException {
        IUiSettingsDelegate zzbxVar;
        Parcel parcelZza = zza(25, zza());
        IBinder strongBinder = parcelZza.readStrongBinder();
        if (strongBinder == null) {
            zzbxVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
            if (iInterfaceQueryLocalInterface instanceof IUiSettingsDelegate) {
                zzbxVar = (IUiSettingsDelegate) iInterfaceQueryLocalInterface;
            } else {
                zzbxVar = new zzbx(strongBinder);
            }
        }
        parcelZza.recycle();
        return zzbxVar;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final IProjectionDelegate getProjection() throws RemoteException {
        IProjectionDelegate zzbrVar;
        Parcel parcelZza = zza(26, zza());
        IBinder strongBinder = parcelZza.readStrongBinder();
        if (strongBinder == null) {
            zzbrVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
            if (iInterfaceQueryLocalInterface instanceof IProjectionDelegate) {
                zzbrVar = (IProjectionDelegate) iInterfaceQueryLocalInterface;
            } else {
                zzbrVar = new zzbr(strongBinder);
            }
        }
        parcelZza.recycle();
        return zzbrVar;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnCameraChangeListener(zzl zzlVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzlVar);
        zzb(27, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnMapClickListener(zzaj zzajVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzajVar);
        zzb(28, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnMapLongClickListener(zzan zzanVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzanVar);
        zzb(29, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnMarkerClickListener(zzar zzarVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzarVar);
        zzb(30, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnMarkerDragListener(zzat zzatVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzatVar);
        zzb(31, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnInfoWindowClickListener(zzab zzabVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzabVar);
        zzb(32, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setInfoWindowAdapter(zzh zzhVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzhVar);
        zzb(33, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final com.google.android.gms.internal.maps.zzh addCircle(CircleOptions circleOptions) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, circleOptions);
        Parcel parcelZza2 = zza(35, parcelZza);
        com.google.android.gms.internal.maps.zzh zzhVarZzc = com.google.android.gms.internal.maps.zzi.zzc(parcelZza2.readStrongBinder());
        parcelZza2.recycle();
        return zzhVarZzc;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnMyLocationChangeListener(zzax zzaxVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzaxVar);
        zzb(36, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnMyLocationButtonClickListener(zzav zzavVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzavVar);
        zzb(37, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void snapshot(zzbs zzbsVar, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzbsVar);
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iObjectWrapper);
        zzb(38, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setPadding(int i, int i2, int i3, int i4) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        parcelZza.writeInt(i2);
        parcelZza.writeInt(i3);
        parcelZza.writeInt(i4);
        zzb(39, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final boolean isBuildingsEnabled() throws RemoteException {
        Parcel parcelZza = zza(40, zza());
        boolean zZza = com.google.android.gms.internal.maps.zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setBuildingsEnabled(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.writeBoolean(parcelZza, z);
        zzb(41, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnMapLoadedCallback(zzal zzalVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzalVar);
        zzb(42, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final com.google.android.gms.internal.maps.zzn getFocusedBuilding() throws RemoteException {
        Parcel parcelZza = zza(44, zza());
        com.google.android.gms.internal.maps.zzn zznVarZze = com.google.android.gms.internal.maps.zzo.zze(parcelZza.readStrongBinder());
        parcelZza.recycle();
        return zznVarZze;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnIndoorStateChangeListener(zzz zzzVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzzVar);
        zzb(45, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setWatermarkEnabled(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.writeBoolean(parcelZza, z);
        zzb(51, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void getMapAsync(zzap zzapVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzapVar);
        zzb(53, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, bundle);
        zzb(54, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void onResume() throws RemoteException {
        zzb(55, zza());
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void onPause() throws RemoteException {
        zzb(56, zza());
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void onDestroy() throws RemoteException {
        zzb(57, zza());
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void onLowMemory() throws RemoteException {
        zzb(58, zza());
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final boolean useViewLifecycleWhenInFragment() throws RemoteException {
        Parcel parcelZza = zza(59, zza());
        boolean zZza = com.google.android.gms.internal.maps.zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, bundle);
        Parcel parcelZza2 = zza(60, parcelZza);
        if (parcelZza2.readInt() != 0) {
            bundle.readFromParcel(parcelZza2);
        }
        parcelZza2.recycle();
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setContentDescription(String str) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzb(61, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void snapshotForTest(zzbs zzbsVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzbsVar);
        zzb(71, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnPoiClickListener(zzbb zzbbVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzbbVar);
        zzb(80, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void onEnterAmbient(Bundle bundle) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, bundle);
        zzb(81, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void onExitAmbient() throws RemoteException {
        zzb(82, zza());
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnGroundOverlayClickListener(zzx zzxVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzxVar);
        zzb(83, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnInfoWindowLongClickListener(zzaf zzafVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzafVar);
        zzb(84, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnPolygonClickListener(zzbd zzbdVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzbdVar);
        zzb(85, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnInfoWindowCloseListener(zzad zzadVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzadVar);
        zzb(86, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnPolylineClickListener(zzbf zzbfVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzbfVar);
        zzb(87, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnCircleClickListener(zzv zzvVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzvVar);
        zzb(89, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setMinZoomPreference(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(92, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setMaxZoomPreference(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(93, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void resetMinMaxZoomPreference() throws RemoteException {
        zzb(94, zza());
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setLatLngBoundsForCameraTarget(LatLngBounds latLngBounds) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, latLngBounds);
        zzb(95, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnCameraMoveStartedListener(zzt zztVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zztVar);
        zzb(96, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnCameraMoveListener(zzr zzrVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzrVar);
        zzb(97, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnCameraMoveCanceledListener(zzp zzpVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzpVar);
        zzb(98, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnCameraIdleListener(zzn zznVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zznVar);
        zzb(99, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final boolean setMapStyle(MapStyleOptions mapStyleOptions) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, mapStyleOptions);
        Parcel parcelZza2 = zza(91, parcelZza);
        boolean zZza = com.google.android.gms.internal.maps.zzc.zza(parcelZza2);
        parcelZza2.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void onStart() throws RemoteException {
        zzb(101, zza());
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void onStop() throws RemoteException {
        zzb(102, zza());
    }

    @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
    public final void setOnMyLocationClickListener(zzaz zzazVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzazVar);
        zzb(107, parcelZza);
    }
}
