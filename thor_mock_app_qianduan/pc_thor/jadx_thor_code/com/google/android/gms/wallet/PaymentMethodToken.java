package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class PaymentMethodToken extends AbstractSafeParcelable {
    public static final Parcelable.Creator<PaymentMethodToken> CREATOR = new zzx();
    private int zzdl;
    private String zzdm;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzdl);
        SafeParcelWriter.writeString(parcel, 3, this.zzdm, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    PaymentMethodToken(int i, String str) {
        this.zzdl = i;
        this.zzdm = str;
    }

    private PaymentMethodToken() {
    }

    public final int getPaymentMethodTokenizationType() {
        return this.zzdl;
    }

    public final String getToken() {
        return this.zzdm;
    }
}
