package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes2.dex */
public abstract class zzr extends zzb implements zzq {
    public static zzq zzf(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
        if (iInterfaceQueryLocalInterface instanceof zzq) {
            return (zzq) iInterfaceQueryLocalInterface;
        }
        return new zzs(iBinder);
    }
}
