package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzv implements Parcelable.Creator<zzt> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzt[] newArray(int i) {
        return new zzt[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzt createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        String strCreateString2 = null;
        String strCreateString3 = null;
        zzm[] zzmVarArr = null;
        String strCreateString4 = null;
        zzu zzuVar = null;
        boolean z = false;
        boolean z2 = false;
        int i = 1;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 11) {
                strCreateString4 = SafeParcelReader.createString(parcel, header);
            } else if (fieldId != 12) {
                switch (fieldId) {
                    case 1:
                        strCreateString = SafeParcelReader.createString(parcel, header);
                        break;
                    case 2:
                        strCreateString2 = SafeParcelReader.createString(parcel, header);
                        break;
                    case 3:
                        z = SafeParcelReader.readBoolean(parcel, header);
                        break;
                    case 4:
                        i = SafeParcelReader.readInt(parcel, header);
                        break;
                    case 5:
                        z2 = SafeParcelReader.readBoolean(parcel, header);
                        break;
                    case 6:
                        strCreateString3 = SafeParcelReader.createString(parcel, header);
                        break;
                    case 7:
                        zzmVarArr = (zzm[]) SafeParcelReader.createTypedArray(parcel, header, zzm.CREATOR);
                        break;
                    default:
                        SafeParcelReader.skipUnknownField(parcel, header);
                        break;
                }
            } else {
                zzuVar = (zzu) SafeParcelReader.createParcelable(parcel, header, zzu.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzt(strCreateString, strCreateString2, z, i, z2, strCreateString3, zzmVarArr, strCreateString4, zzuVar);
    }
}
