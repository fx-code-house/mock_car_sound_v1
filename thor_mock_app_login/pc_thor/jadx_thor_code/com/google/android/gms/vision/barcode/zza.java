package com.google.android.gms.vision.barcode;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.vision.barcode.Barcode;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zza implements Parcelable.Creator<Barcode.Address> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Barcode.Address[] newArray(int i) {
        return new Barcode.Address[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Barcode.Address createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        String[] strArrCreateStringArray = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 2) {
                i = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 3) {
                strArrCreateStringArray = SafeParcelReader.createStringArray(parcel, header);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new Barcode.Address(i, strArrCreateStringArray);
    }
}
