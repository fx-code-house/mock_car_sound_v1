package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes2.dex */
public final class zaq implements Parcelable.Creator<SafeParcelResponse> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ SafeParcelResponse createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        Parcel parcelCreateParcel = null;
        zan zanVar = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                i = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 2) {
                parcelCreateParcel = SafeParcelReader.createParcel(parcel, header);
            } else if (fieldId != 3) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                zanVar = (zan) SafeParcelReader.createParcelable(parcel, header, zan.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new SafeParcelResponse(i, parcelCreateParcel, zanVar);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ SafeParcelResponse[] newArray(int i) {
        return new SafeParcelResponse[i];
    }
}
