package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.wallet.wobs.CommonWalletObject;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzs implements Parcelable.Creator<OfferWalletObject> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ OfferWalletObject[] newArray(int i) {
        return new OfferWalletObject[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ OfferWalletObject createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        String strCreateString = null;
        String strCreateString2 = null;
        CommonWalletObject commonWalletObject = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                i = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 2) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 3) {
                strCreateString2 = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 4) {
                commonWalletObject = (CommonWalletObject) SafeParcelReader.createParcelable(parcel, header, CommonWalletObject.CREATOR);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new OfferWalletObject(i, strCreateString, strCreateString2, commonWalletObject);
    }
}
