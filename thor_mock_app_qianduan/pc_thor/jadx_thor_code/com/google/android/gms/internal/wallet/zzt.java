package com.google.android.gms.internal.wallet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.zzam;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public abstract class zzt extends zza implements zzq {
    public zzt() {
        super("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
    }

    @Override // com.google.android.gms.internal.wallet.zza
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zza(parcel.readInt(), (MaskedWallet) zzd.zza(parcel, MaskedWallet.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 2:
                zza(parcel.readInt(), (FullWallet) zzd.zza(parcel, FullWallet.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 3:
                zza(parcel.readInt(), zzd.zza(parcel), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 4:
                zza(parcel.readInt(), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 5:
            default:
                return false;
            case 6:
                zzb(parcel.readInt(), zzd.zza(parcel), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 7:
                zza((Status) zzd.zza(parcel, Status.CREATOR), (zzg) zzd.zza(parcel, zzg.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 8:
                zza((Status) zzd.zza(parcel, Status.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 9:
                zza((Status) zzd.zza(parcel, Status.CREATOR), zzd.zza(parcel), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 10:
                zza((Status) zzd.zza(parcel, Status.CREATOR), (zzi) zzd.zza(parcel, zzi.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 11:
                zzb((Status) zzd.zza(parcel, Status.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 12:
                zza((Status) zzd.zza(parcel, Status.CREATOR), (zzam) zzd.zza(parcel, zzam.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 13:
                zzc((Status) zzd.zza(parcel, Status.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 14:
                zza((Status) zzd.zza(parcel, Status.CREATOR), (PaymentData) zzd.zza(parcel, PaymentData.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 15:
                zza((Status) zzd.zza(parcel, Status.CREATOR), (zzm) zzd.zza(parcel, zzm.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
            case 16:
                zza((Status) zzd.zza(parcel, Status.CREATOR), (zzk) zzd.zza(parcel, zzk.CREATOR), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                return true;
        }
    }
}
