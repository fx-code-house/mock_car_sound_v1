package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes2.dex */
public final class zzf implements Parcelable.Creator<LatLng> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ LatLng[] newArray(int i) {
        return new LatLng[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ LatLng createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        double d = 0.0d;
        double d2 = 0.0d;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 2) {
                d = SafeParcelReader.readDouble(parcel, header);
            } else if (fieldId == 3) {
                d2 = SafeParcelReader.readDouble(parcel, header);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new LatLng(d, d2);
    }
}
