package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.identity.intents.model.UserAddress;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class FullWallet extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<FullWallet> CREATOR = new zzi();
    private String zzat;
    private String zzau;
    private zzac zzav;
    private String zzaw;
    private zzb zzax;
    private zzb zzay;
    private String[] zzaz;
    private UserAddress zzba;
    private UserAddress zzbb;
    private InstrumentInfo[] zzbc;
    private PaymentMethodToken zzbd;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzat, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzau, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzav, i, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzaw, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzax, i, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzay, i, false);
        SafeParcelWriter.writeStringArray(parcel, 8, this.zzaz, false);
        SafeParcelWriter.writeParcelable(parcel, 9, this.zzba, i, false);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzbb, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 11, this.zzbc, i, false);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzbd, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    FullWallet(String str, String str2, zzac zzacVar, String str3, zzb zzbVar, zzb zzbVar2, String[] strArr, UserAddress userAddress, UserAddress userAddress2, InstrumentInfo[] instrumentInfoArr, PaymentMethodToken paymentMethodToken) {
        this.zzat = str;
        this.zzau = str2;
        this.zzav = zzacVar;
        this.zzaw = str3;
        this.zzax = zzbVar;
        this.zzay = zzbVar2;
        this.zzaz = strArr;
        this.zzba = userAddress;
        this.zzbb = userAddress2;
        this.zzbc = instrumentInfoArr;
        this.zzbd = paymentMethodToken;
    }

    private FullWallet() {
    }
}
