package com.google.android.gms.wallet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class PaymentMethodTokenizationParameters extends AbstractSafeParcelable {
    public static final Parcelable.Creator<PaymentMethodTokenizationParameters> CREATOR = new zzaa();
    int zzdl;
    Bundle zzdn;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private Builder() {
        }

        public final Builder setPaymentMethodTokenizationType(int i) {
            PaymentMethodTokenizationParameters.this.zzdl = i;
            return this;
        }

        public final Builder addParameter(String str, String str2) {
            Preconditions.checkNotEmpty(str, "Tokenization parameter name must not be empty");
            Preconditions.checkNotEmpty(str2, "Tokenization parameter value must not be empty");
            PaymentMethodTokenizationParameters.this.zzdn.putString(str, str2);
            return this;
        }

        public final PaymentMethodTokenizationParameters build() {
            return PaymentMethodTokenizationParameters.this;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzdl);
        SafeParcelWriter.writeBundle(parcel, 3, this.zzdn, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    PaymentMethodTokenizationParameters(int i, Bundle bundle) {
        new Bundle();
        this.zzdl = i;
        this.zzdn = bundle;
    }

    private PaymentMethodTokenizationParameters() {
        this.zzdn = new Bundle();
    }

    public final int getPaymentMethodTokenizationType() {
        return this.zzdl;
    }

    public final Bundle getParameters() {
        return new Bundle(this.zzdn);
    }
}
