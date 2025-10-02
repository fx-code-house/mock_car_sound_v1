package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.MessageOptions;
import com.google.android.gms.wearable.PutDataRequest;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzeu extends com.google.android.gms.internal.wearable.zza implements IInterface {
    zzeu(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wearable.internal.IWearableService");
    }

    public final void zzA(zzeq zzeqVar, String str, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        com.google.android.gms.internal.wearable.zzc.zzc(parcelZza, parcelFileDescriptor);
        parcelZza.writeLong(j);
        parcelZza.writeLong(j2);
        zzG(39, parcelZza);
    }

    public final void zzd(zzeq zzeqVar, PutDataRequest putDataRequest) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        com.google.android.gms.internal.wearable.zzc.zzc(parcelZza, putDataRequest);
        zzG(6, parcelZza);
    }

    public final void zze(zzeq zzeqVar, Uri uri) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        com.google.android.gms.internal.wearable.zzc.zzc(parcelZza, uri);
        zzG(7, parcelZza);
    }

    public final void zzf(zzeq zzeqVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        zzG(8, parcelZza);
    }

    public final void zzg(zzeq zzeqVar, Uri uri, int i) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        com.google.android.gms.internal.wearable.zzc.zzc(parcelZza, uri);
        parcelZza.writeInt(i);
        zzG(40, parcelZza);
    }

    public final void zzh(zzeq zzeqVar, Uri uri, int i) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        com.google.android.gms.internal.wearable.zzc.zzc(parcelZza, uri);
        parcelZza.writeInt(i);
        zzG(41, parcelZza);
    }

    public final void zzi(zzeq zzeqVar, String str, String str2, byte[] bArr) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        parcelZza.writeString(str2);
        parcelZza.writeByteArray(bArr);
        zzG(12, parcelZza);
    }

    public final void zzj(zzeq zzeqVar, String str, String str2, byte[] bArr, MessageOptions messageOptions) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        parcelZza.writeString(str2);
        parcelZza.writeByteArray(bArr);
        com.google.android.gms.internal.wearable.zzc.zzc(parcelZza, messageOptions);
        zzG(59, parcelZza);
    }

    public final void zzk(zzeq zzeqVar, Asset asset) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        com.google.android.gms.internal.wearable.zzc.zzc(parcelZza, asset);
        zzG(13, parcelZza);
    }

    public final void zzl(zzeq zzeqVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        zzG(14, parcelZza);
    }

    public final void zzm(zzeq zzeqVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        zzG(15, parcelZza);
    }

    public final void zzn(zzeq zzeqVar, String str) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        zzG(63, parcelZza);
    }

    public final void zzo(zzeq zzeqVar, String str, int i) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        parcelZza.writeInt(i);
        zzG(42, parcelZza);
    }

    public final void zzp(zzeq zzeqVar, int i) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeInt(i);
        zzG(43, parcelZza);
    }

    public final void zzq(zzeq zzeqVar, String str) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        zzG(46, parcelZza);
    }

    public final void zzr(zzeq zzeqVar, String str) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        zzG(47, parcelZza);
    }

    public final void zzs(zzeq zzeqVar, zzd zzdVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        com.google.android.gms.internal.wearable.zzc.zzc(parcelZza, zzdVar);
        zzG(16, parcelZza);
    }

    public final void zzt(zzeq zzeqVar, zzgg zzggVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        com.google.android.gms.internal.wearable.zzc.zzc(parcelZza, zzggVar);
        zzG(17, parcelZza);
    }

    public final void zzu(zzeq zzeqVar, String str, String str2) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        parcelZza.writeString(str2);
        zzG(31, parcelZza);
    }

    public final void zzv(zzeq zzeqVar, String str) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        zzG(32, parcelZza);
    }

    public final void zzw(zzeq zzeqVar, String str, int i) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        parcelZza.writeInt(i);
        zzG(33, parcelZza);
    }

    public final void zzx(zzeq zzeqVar, zzen zzenVar, String str) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzenVar);
        parcelZza.writeString(str);
        zzG(34, parcelZza);
    }

    public final void zzy(zzeq zzeqVar, zzen zzenVar, String str) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzenVar);
        parcelZza.writeString(str);
        zzG(35, parcelZza);
    }

    public final void zzz(zzeq zzeqVar, String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.wearable.zzc.zzd(parcelZza, zzeqVar);
        parcelZza.writeString(str);
        com.google.android.gms.internal.wearable.zzc.zzc(parcelZza, parcelFileDescriptor);
        zzG(38, parcelZza);
    }
}
