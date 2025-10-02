package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes2.dex */
public abstract class zzf extends zzb implements zze {
    public static zze zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
        if (iInterfaceQueryLocalInterface instanceof zze) {
            return (zze) iInterfaceQueryLocalInterface;
        }
        return new zzg(iBinder);
    }
}
