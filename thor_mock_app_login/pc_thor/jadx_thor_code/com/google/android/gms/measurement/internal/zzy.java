package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzy implements Parcelable.Creator<zzz> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzz[] newArray(int i) {
        return new zzz[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzz createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        String strCreateString2 = null;
        zzku zzkuVar = null;
        String strCreateString3 = null;
        zzaq zzaqVar = null;
        zzaq zzaqVar2 = null;
        zzaq zzaqVar3 = null;
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        boolean z = false;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 2:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 3:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
                case 4:
                    zzkuVar = (zzku) SafeParcelReader.createParcelable(parcel, header, zzku.CREATOR);
                    break;
                case 5:
                    j = SafeParcelReader.readLong(parcel, header);
                    break;
                case 6:
                    z = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 7:
                    strCreateString3 = SafeParcelReader.createString(parcel, header);
                    break;
                case 8:
                    zzaqVar = (zzaq) SafeParcelReader.createParcelable(parcel, header, zzaq.CREATOR);
                    break;
                case 9:
                    j2 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 10:
                    zzaqVar2 = (zzaq) SafeParcelReader.createParcelable(parcel, header, zzaq.CREATOR);
                    break;
                case 11:
                    j3 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 12:
                    zzaqVar3 = (zzaq) SafeParcelReader.createParcelable(parcel, header, zzaq.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzz(strCreateString, strCreateString2, zzkuVar, j, z, strCreateString3, zzaqVar, j2, zzaqVar2, j3, zzaqVar3);
    }
}
