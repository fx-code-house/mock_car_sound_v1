package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes2.dex */
public abstract class zzu extends zzb implements zzt {
    public static zzt zzg(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
        if (iInterfaceQueryLocalInterface instanceof zzt) {
            return (zzt) iInterfaceQueryLocalInterface;
        }
        return new zzv(iBinder);
    }
}
