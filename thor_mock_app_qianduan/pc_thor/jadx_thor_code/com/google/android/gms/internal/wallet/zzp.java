package com.google.android.gms.internal.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzp implements Parcelable.Creator<zzm> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzm[] newArray(int i) {
        return new zzm[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzm createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String[] strArrCreateStringArray = null;
        int[] iArrCreateIntArray = null;
        RemoteViews remoteViews = null;
        byte[] bArrCreateByteArray = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                strArrCreateStringArray = SafeParcelReader.createStringArray(parcel, header);
            } else if (fieldId == 2) {
                iArrCreateIntArray = SafeParcelReader.createIntArray(parcel, header);
            } else if (fieldId == 3) {
                remoteViews = (RemoteViews) SafeParcelReader.createParcelable(parcel, header, RemoteViews.CREATOR);
            } else if (fieldId == 4) {
                bArrCreateByteArray = SafeParcelReader.createByteArray(parcel, header);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzm(strArrCreateStringArray, iArrCreateIntArray, remoteViews, bArrCreateByteArray);
    }
}
