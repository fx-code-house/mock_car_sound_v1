package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzg implements Parcelable.Creator<PutDataRequest> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ PutDataRequest createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        Uri uri = null;
        Bundle bundleCreateBundle = null;
        byte[] bArrCreateByteArray = null;
        long j = 0;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 2) {
                uri = (Uri) SafeParcelReader.createParcelable(parcel, header, Uri.CREATOR);
            } else if (fieldId == 4) {
                bundleCreateBundle = SafeParcelReader.createBundle(parcel, header);
            } else if (fieldId == 5) {
                bArrCreateByteArray = SafeParcelReader.createByteArray(parcel, header);
            } else if (fieldId != 6) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                j = SafeParcelReader.readLong(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new PutDataRequest(uri, bundleCreateBundle, bArrCreateByteArray, j);
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ PutDataRequest[] newArray(int i) {
        return new PutDataRequest[i];
    }
}
