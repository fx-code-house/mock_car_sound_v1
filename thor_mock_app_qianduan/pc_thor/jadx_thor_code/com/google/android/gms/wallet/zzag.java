package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzag implements Parcelable.Creator<ShippingAddressRequirements> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ ShippingAddressRequirements[] newArray(int i) {
        return new ShippingAddressRequirements[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ ShippingAddressRequirements createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList<String> arrayListCreateStringList = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            if (SafeParcelReader.getFieldId(header) == 1) {
                arrayListCreateStringList = SafeParcelReader.createStringList(parcel, header);
            } else {
                SafeParcelReader.skipUnknownField(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new ShippingAddressRequirements(arrayListCreateStringList);
    }
}
