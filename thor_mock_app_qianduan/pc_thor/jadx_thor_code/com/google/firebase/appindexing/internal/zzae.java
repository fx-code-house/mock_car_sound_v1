package com.google.firebase.appindexing.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.firebase.appindexing.internal.Thing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzae implements Parcelable.Creator<Thing> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Thing[] newArray(int i) {
        return new Thing[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Thing createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        Bundle bundleCreateBundle = null;
        Thing.zza zzaVar = null;
        String strCreateString = null;
        String strCreateString2 = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                bundleCreateBundle = SafeParcelReader.createBundle(parcel, header);
            } else if (fieldId == 2) {
                zzaVar = (Thing.zza) SafeParcelReader.createParcelable(parcel, header, Thing.zza.CREATOR);
            } else if (fieldId == 3) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 4) {
                strCreateString2 = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 1000) {
                i = SafeParcelReader.readInt(parcel, header);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new Thing(i, bundleCreateBundle, zzaVar, strCreateString, strCreateString2);
    }
}
