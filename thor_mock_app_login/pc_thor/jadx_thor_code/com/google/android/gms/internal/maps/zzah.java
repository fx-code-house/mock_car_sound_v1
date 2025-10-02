package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.model.Tile;

/* loaded from: classes2.dex */
public final class zzah extends zza implements zzaf {
    zzah(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.ITileProviderDelegate");
    }

    @Override // com.google.android.gms.internal.maps.zzaf
    public final Tile getTile(int i, int i2, int i3) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        parcelZza.writeInt(i2);
        parcelZza.writeInt(i3);
        Parcel parcelZza2 = zza(1, parcelZza);
        Tile tile = (Tile) zzc.zza(parcelZza2, Tile.CREATOR);
        parcelZza2.recycle();
        return tile;
    }
}
