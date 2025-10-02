package com.google.android.gms.flags;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes2.dex */
public final class zze extends com.google.android.gms.internal.flags.zza implements zzc {
    zze(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.flags.IFlagProvider");
    }

    @Override // com.google.android.gms.flags.zzc
    public final void init(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.flags.zzc.zza(parcelZza, iObjectWrapper);
        zzb(1, parcelZza);
    }

    @Override // com.google.android.gms.flags.zzc
    public final boolean getBooleanFlagValue(String str, boolean z, int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        com.google.android.gms.internal.flags.zzc.writeBoolean(parcelZza, z);
        parcelZza.writeInt(i);
        Parcel parcelZza2 = zza(2, parcelZza);
        boolean zZza = com.google.android.gms.internal.flags.zzc.zza(parcelZza2);
        parcelZza2.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.flags.zzc
    public final int getIntFlagValue(String str, int i, int i2) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeInt(i);
        parcelZza.writeInt(i2);
        Parcel parcelZza2 = zza(3, parcelZza);
        int i3 = parcelZza2.readInt();
        parcelZza2.recycle();
        return i3;
    }

    @Override // com.google.android.gms.flags.zzc
    public final long getLongFlagValue(String str, long j, int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeLong(j);
        parcelZza.writeInt(i);
        Parcel parcelZza2 = zza(4, parcelZza);
        long j2 = parcelZza2.readLong();
        parcelZza2.recycle();
        return j2;
    }

    @Override // com.google.android.gms.flags.zzc
    public final String getStringFlagValue(String str, String str2, int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeString(str2);
        parcelZza.writeInt(i);
        Parcel parcelZza2 = zza(5, parcelZza);
        String string = parcelZza2.readString();
        parcelZza2.recycle();
        return string;
    }
}
