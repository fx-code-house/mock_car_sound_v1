package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzw implements Parcelable.Creator<PaymentDataRequest> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ PaymentDataRequest[] newArray(int i) {
        return new PaymentDataRequest[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ PaymentDataRequest createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        CardRequirements cardRequirements = null;
        ShippingAddressRequirements shippingAddressRequirements = null;
        ArrayList<Integer> arrayListCreateIntegerList = null;
        PaymentMethodTokenizationParameters paymentMethodTokenizationParameters = null;
        TransactionInfo transactionInfo = null;
        String strCreateString = null;
        boolean z4 = true;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    z = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 2:
                    z2 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 3:
                    cardRequirements = (CardRequirements) SafeParcelReader.createParcelable(parcel, header, CardRequirements.CREATOR);
                    break;
                case 4:
                    z3 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 5:
                    shippingAddressRequirements = (ShippingAddressRequirements) SafeParcelReader.createParcelable(parcel, header, ShippingAddressRequirements.CREATOR);
                    break;
                case 6:
                    arrayListCreateIntegerList = SafeParcelReader.createIntegerList(parcel, header);
                    break;
                case 7:
                    paymentMethodTokenizationParameters = (PaymentMethodTokenizationParameters) SafeParcelReader.createParcelable(parcel, header, PaymentMethodTokenizationParameters.CREATOR);
                    break;
                case 8:
                    transactionInfo = (TransactionInfo) SafeParcelReader.createParcelable(parcel, header, TransactionInfo.CREATOR);
                    break;
                case 9:
                    z4 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 10:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new PaymentDataRequest(z, z2, cardRequirements, z3, shippingAddressRequirements, arrayListCreateIntegerList, paymentMethodTokenizationParameters, transactionInfo, z4, strCreateString);
    }
}
