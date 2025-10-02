package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.identity.intents.model.UserAddress;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class MaskedWallet extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<MaskedWallet> CREATOR = new zzq();
    private String zzat;
    private String zzau;
    private String zzaw;
    private zzb zzax;
    private zzb zzay;
    private String[] zzaz;
    private UserAddress zzba;
    private UserAddress zzbb;
    private InstrumentInfo[] zzbc;
    private LoyaltyWalletObject[] zzcu;
    private OfferWalletObject[] zzcv;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzat, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzau, false);
        SafeParcelWriter.writeStringArray(parcel, 4, this.zzaz, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzaw, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzax, i, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzay, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 8, this.zzcu, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 9, this.zzcv, i, false);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzba, i, false);
        SafeParcelWriter.writeParcelable(parcel, 11, this.zzbb, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 12, this.zzbc, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    MaskedWallet(String str, String str2, String[] strArr, String str3, zzb zzbVar, zzb zzbVar2, LoyaltyWalletObject[] loyaltyWalletObjectArr, OfferWalletObject[] offerWalletObjectArr, UserAddress userAddress, UserAddress userAddress2, InstrumentInfo[] instrumentInfoArr) {
        this.zzat = str;
        this.zzau = str2;
        this.zzaz = strArr;
        this.zzaw = str3;
        this.zzax = zzbVar;
        this.zzay = zzbVar2;
        this.zzcu = loyaltyWalletObjectArr;
        this.zzcv = offerWalletObjectArr;
        this.zzba = userAddress;
        this.zzbb = userAddress2;
        this.zzbc = instrumentInfoArr;
    }

    private MaskedWallet() {
    }
}
