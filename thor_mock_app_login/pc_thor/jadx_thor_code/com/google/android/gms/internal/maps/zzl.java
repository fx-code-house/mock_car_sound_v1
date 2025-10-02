package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes2.dex */
public abstract class zzl extends zzb implements zzk {
    public static zzk zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
        if (iInterfaceQueryLocalInterface instanceof zzk) {
            return (zzk) iInterfaceQueryLocalInterface;
        }
        return new zzm(iBinder);
    }
}
