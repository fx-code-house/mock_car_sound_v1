package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes2.dex */
public final class zzb implements Parcelable.Creator<Cap> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Cap[] newArray(int i) {
        return new Cap[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Cap createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        IBinder iBinder = null;
        Float floatObject = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 2) {
                i = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 3) {
                iBinder = SafeParcelReader.readIBinder(parcel, header);
            } else if (fieldId == 4) {
                floatObject = SafeParcelReader.readFloatObject(parcel, header);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new Cap(i, iBinder, floatObject);
    }
}
