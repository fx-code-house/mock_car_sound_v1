package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes2.dex */
public abstract class zzo extends zzb implements zzn {
    public static zzn zze(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
        if (iInterfaceQueryLocalInterface instanceof zzn) {
            return (zzn) iInterfaceQueryLocalInterface;
        }
        return new zzp(iBinder);
    }
}
