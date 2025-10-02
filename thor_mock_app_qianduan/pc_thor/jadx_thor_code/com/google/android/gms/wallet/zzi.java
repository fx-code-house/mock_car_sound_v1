package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.identity.intents.model.UserAddress;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzi implements Parcelable.Creator<FullWallet> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ FullWallet[] newArray(int i) {
        return new FullWallet[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ FullWallet createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        String strCreateString2 = null;
        zzac zzacVar = null;
        String strCreateString3 = null;
        zzb zzbVar = null;
        zzb zzbVar2 = null;
        String[] strArrCreateStringArray = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        InstrumentInfo[] instrumentInfoArr = null;
        PaymentMethodToken paymentMethodToken = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 2:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 3:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
                case 4:
                    zzacVar = (zzac) SafeParcelReader.createParcelable(parcel, header, zzac.CREATOR);
                    break;
                case 5:
                    strCreateString3 = SafeParcelReader.createString(parcel, header);
                    break;
                case 6:
                    zzbVar = (zzb) SafeParcelReader.createParcelable(parcel, header, zzb.CREATOR);
                    break;
                case 7:
                    zzbVar2 = (zzb) SafeParcelReader.createParcelable(parcel, header, zzb.CREATOR);
                    break;
                case 8:
                    strArrCreateStringArray = SafeParcelReader.createStringArray(parcel, header);
                    break;
                case 9:
                    userAddress = (UserAddress) SafeParcelReader.createParcelable(parcel, header, UserAddress.CREATOR);
                    break;
                case 10:
                    userAddress2 = (UserAddress) SafeParcelReader.createParcelable(parcel, header, UserAddress.CREATOR);
                    break;
                case 11:
                    instrumentInfoArr = (InstrumentInfo[]) SafeParcelReader.createTypedArray(parcel, header, InstrumentInfo.CREATOR);
                    break;
                case 12:
                    paymentMethodToken = (PaymentMethodToken) SafeParcelReader.createParcelable(parcel, header, PaymentMethodToken.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new FullWallet(strCreateString, strCreateString2, zzacVar, strCreateString3, zzbVar, zzbVar2, strArrCreateStringArray, userAddress, userAddress2, instrumentInfoArr, paymentMethodToken);
    }
}
