package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzn implements Parcelable.Creator<WalletObjectMessage> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ WalletObjectMessage[] newArray(int i) {
        return new WalletObjectMessage[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ WalletObjectMessage createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        String strCreateString2 = null;
        TimeInterval timeInterval = null;
        UriData uriData = null;
        UriData uriData2 = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 2) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 3) {
                strCreateString2 = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 4) {
                timeInterval = (TimeInterval) SafeParcelReader.createParcelable(parcel, header, TimeInterval.CREATOR);
            } else if (fieldId == 5) {
                uriData = (UriData) SafeParcelReader.createParcelable(parcel, header, UriData.CREATOR);
            } else if (fieldId == 6) {
                uriData2 = (UriData) SafeParcelReader.createParcelable(parcel, header, UriData.CREATOR);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new WalletObjectMessage(strCreateString, strCreateString2, timeInterval, uriData, uriData2);
    }
}
