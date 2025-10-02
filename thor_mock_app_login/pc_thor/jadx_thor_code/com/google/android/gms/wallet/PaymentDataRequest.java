package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class PaymentDataRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<PaymentDataRequest> CREATOR = new zzw();
    ArrayList<Integer> zzbu;
    String zzbw;
    boolean zzdc;
    boolean zzdd;
    CardRequirements zzde;
    boolean zzdf;
    ShippingAddressRequirements zzdg;
    PaymentMethodTokenizationParameters zzdh;
    TransactionInfo zzdi;
    boolean zzdj;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    @Deprecated
    public final class Builder {
        private Builder() {
        }

        public final Builder setEmailRequired(boolean z) {
            PaymentDataRequest.this.zzdc = z;
            return this;
        }

        public final Builder setPhoneNumberRequired(boolean z) {
            PaymentDataRequest.this.zzdd = z;
            return this;
        }

        public final Builder setCardRequirements(CardRequirements cardRequirements) {
            PaymentDataRequest.this.zzde = cardRequirements;
            return this;
        }

        public final Builder setShippingAddressRequired(boolean z) {
            PaymentDataRequest.this.zzdf = z;
            return this;
        }

        public final Builder setShippingAddressRequirements(ShippingAddressRequirements shippingAddressRequirements) {
            PaymentDataRequest.this.zzdg = shippingAddressRequirements;
            return this;
        }

        public final Builder addAllowedPaymentMethod(int i) {
            if (PaymentDataRequest.this.zzbu == null) {
                PaymentDataRequest.this.zzbu = new ArrayList<>();
            }
            PaymentDataRequest.this.zzbu.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedPaymentMethods(Collection<Integer> collection) {
            Preconditions.checkArgument((collection == null || collection.isEmpty()) ? false : true, "allowedPaymentMethods can't be null or empty!");
            if (PaymentDataRequest.this.zzbu == null) {
                PaymentDataRequest.this.zzbu = new ArrayList<>();
            }
            PaymentDataRequest.this.zzbu.addAll(collection);
            return this;
        }

        public final Builder setPaymentMethodTokenizationParameters(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters) {
            PaymentDataRequest.this.zzdh = paymentMethodTokenizationParameters;
            return this;
        }

        public final Builder setTransactionInfo(TransactionInfo transactionInfo) {
            PaymentDataRequest.this.zzdi = transactionInfo;
            return this;
        }

        public final Builder setUiRequired(boolean z) {
            PaymentDataRequest.this.zzdj = z;
            return this;
        }

        public final PaymentDataRequest build() {
            if (PaymentDataRequest.this.zzbw == null) {
                Preconditions.checkNotNull(PaymentDataRequest.this.zzbu, "Allowed payment methods must be set! You can set it through addAllowedPaymentMethod() or addAllowedPaymentMethods() in the PaymentDataRequest Builder.");
                Preconditions.checkNotNull(PaymentDataRequest.this.zzde, "Card requirements must be set!");
                if (PaymentDataRequest.this.zzdh != null) {
                    Preconditions.checkNotNull(PaymentDataRequest.this.zzdi, "Transaction info must be set if paymentMethodTokenizationParameters is set!");
                }
            }
            return PaymentDataRequest.this;
        }
    }

    PaymentDataRequest(boolean z, boolean z2, CardRequirements cardRequirements, boolean z3, ShippingAddressRequirements shippingAddressRequirements, ArrayList<Integer> arrayList, PaymentMethodTokenizationParameters paymentMethodTokenizationParameters, TransactionInfo transactionInfo, boolean z4, String str) {
        this.zzdc = z;
        this.zzdd = z2;
        this.zzde = cardRequirements;
        this.zzdf = z3;
        this.zzdg = shippingAddressRequirements;
        this.zzbu = arrayList;
        this.zzdh = paymentMethodTokenizationParameters;
        this.zzdi = transactionInfo;
        this.zzdj = z4;
        this.zzbw = str;
    }

    private PaymentDataRequest() {
        this.zzdj = true;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 1, this.zzdc);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzdd);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzde, i, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzdf);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzdg, i, false);
        SafeParcelWriter.writeIntegerList(parcel, 6, this.zzbu, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzdh, i, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzdi, i, false);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzdj);
        SafeParcelWriter.writeString(parcel, 10, this.zzbw, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Deprecated
    public final boolean isEmailRequired() {
        return this.zzdc;
    }

    @Deprecated
    public final boolean isPhoneNumberRequired() {
        return this.zzdd;
    }

    @Deprecated
    public final CardRequirements getCardRequirements() {
        return this.zzde;
    }

    @Deprecated
    public final boolean isShippingAddressRequired() {
        return this.zzdf;
    }

    @Deprecated
    public final ShippingAddressRequirements getShippingAddressRequirements() {
        return this.zzdg;
    }

    @Deprecated
    public final ArrayList<Integer> getAllowedPaymentMethods() {
        return this.zzbu;
    }

    @Deprecated
    public final PaymentMethodTokenizationParameters getPaymentMethodTokenizationParameters() {
        return this.zzdh;
    }

    @Deprecated
    public final TransactionInfo getTransactionInfo() {
        return this.zzdi;
    }

    @Deprecated
    public final boolean isUiRequired() {
        return this.zzdj;
    }

    @Deprecated
    public static Builder newBuilder() {
        return new Builder();
    }

    public static PaymentDataRequest fromJson(String str) {
        Builder builderNewBuilder = newBuilder();
        PaymentDataRequest.this.zzbw = (String) Preconditions.checkNotNull(str, "paymentDataRequestJson cannot be null!");
        return builderNewBuilder.build();
    }

    public final String toJson() {
        return this.zzbw;
    }
}
