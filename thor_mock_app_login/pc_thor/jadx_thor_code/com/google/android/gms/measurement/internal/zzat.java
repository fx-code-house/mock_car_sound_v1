package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzat implements Parcelable.Creator<zzaq> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaq[] newArray(int i) {
        return new zzaq[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaq createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        zzap zzapVar = null;
        String strCreateString2 = null;
        long j = 0;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 2) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 3) {
                zzapVar = (zzap) SafeParcelReader.createParcelable(parcel, header, zzap.CREATOR);
            } else if (fieldId == 4) {
                strCreateString2 = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 5) {
                j = SafeParcelReader.readLong(parcel, header);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzaq(strCreateString, zzapVar, strCreateString2, j);
    }
}
