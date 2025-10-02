package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.util.ArrayUtils;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zze implements Parcelable.Creator<LabelValueRow> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ LabelValueRow[] newArray(int i) {
        return new LabelValueRow[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ LabelValueRow createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList arrayListNewArrayList = ArrayUtils.newArrayList();
        String strCreateString = null;
        String strCreateString2 = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 2) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 3) {
                strCreateString2 = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 4) {
                arrayListNewArrayList = SafeParcelReader.createTypedList(parcel, header, LabelValue.CREATOR);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new LabelValueRow(strCreateString, strCreateString2, arrayListNewArrayList);
    }
}
