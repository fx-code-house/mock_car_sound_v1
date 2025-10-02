package com.google.android.gms.internal.vision;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zzaa implements Parcelable.Creator<zzab> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzab[] newArray(int i) {
        return new zzab[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzab createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        float f = 0.0f;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 2) {
                i = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 3) {
                i2 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 4) {
                i3 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 5) {
                i4 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 6) {
                f = SafeParcelReader.readFloat(parcel, header);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzab(i, i2, i3, i4, f);
    }
}
