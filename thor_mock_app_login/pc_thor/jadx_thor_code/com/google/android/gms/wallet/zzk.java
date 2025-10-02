package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.wallet.wobs.CommonWalletObject;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzk implements Parcelable.Creator<GiftCardWalletObject> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ GiftCardWalletObject[] newArray(int i) {
        return new GiftCardWalletObject[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ GiftCardWalletObject createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        CommonWalletObject commonWalletObject = null;
        String strCreateString = null;
        String strCreateString2 = null;
        String strCreateString3 = null;
        String strCreateString4 = null;
        String strCreateString5 = null;
        long j = 0;
        long j2 = 0;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 2:
                    commonWalletObject = (CommonWalletObject) SafeParcelReader.createParcelable(parcel, header, CommonWalletObject.CREATOR);
                    break;
                case 3:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 4:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
                case 5:
                    strCreateString3 = SafeParcelReader.createString(parcel, header);
                    break;
                case 6:
                    j = SafeParcelReader.readLong(parcel, header);
                    break;
                case 7:
                    strCreateString4 = SafeParcelReader.createString(parcel, header);
                    break;
                case 8:
                    j2 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 9:
                    strCreateString5 = SafeParcelReader.createString(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new GiftCardWalletObject(commonWalletObject, strCreateString, strCreateString2, strCreateString3, j, strCreateString4, j2, strCreateString5);
    }
}
