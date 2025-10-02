package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzy implements Parcelable.Creator<zzw> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzw[] newArray(int i) {
        return new zzw[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzw createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        zzi zziVar = null;
        String strCreateString = null;
        zzh zzhVar = null;
        String strCreateString2 = null;
        long j = 0;
        int i = 0;
        boolean z = false;
        int i2 = 0;
        int i3 = -1;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    zziVar = (zzi) SafeParcelReader.createParcelable(parcel, header, zzi.CREATOR);
                    break;
                case 2:
                    j = SafeParcelReader.readLong(parcel, header);
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, header);
                    break;
                case 4:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 5:
                    zzhVar = (zzh) SafeParcelReader.createParcelable(parcel, header, zzh.CREATOR);
                    break;
                case 6:
                    z = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 7:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 8:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 9:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzw(zziVar, j, i, strCreateString, zzhVar, z, i3, i2, strCreateString2);
    }
}
