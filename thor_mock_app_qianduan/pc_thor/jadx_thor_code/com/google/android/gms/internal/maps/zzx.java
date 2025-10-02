package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes2.dex */
public abstract class zzx extends zzb implements zzw {
    public static zzw zzh(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
        if (iInterfaceQueryLocalInterface instanceof zzw) {
            return (zzw) iInterfaceQueryLocalInterface;
        }
        return new zzy(iBinder);
    }
}
