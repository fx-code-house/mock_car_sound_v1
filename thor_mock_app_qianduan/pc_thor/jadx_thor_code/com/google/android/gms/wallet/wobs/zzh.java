package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzh implements Parcelable.Creator<LoyaltyPoints> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ LoyaltyPoints[] newArray(int i) {
        return new LoyaltyPoints[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ LoyaltyPoints createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        LoyaltyPointsBalance loyaltyPointsBalance = null;
        TimeInterval timeInterval = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 2) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 3) {
                loyaltyPointsBalance = (LoyaltyPointsBalance) SafeParcelReader.createParcelable(parcel, header, LoyaltyPointsBalance.CREATOR);
            } else if (fieldId == 5) {
                timeInterval = (TimeInterval) SafeParcelReader.createParcelable(parcel, header, TimeInterval.CREATOR);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new LoyaltyPoints(strCreateString, loyaltyPointsBalance, timeInterval);
    }
}
