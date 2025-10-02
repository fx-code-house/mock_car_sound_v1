package com.google.android.gms.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.identity.intents.model.UserAddress;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class PaymentData extends AbstractSafeParcelable implements AutoResolvableResult {
    public static final Parcelable.Creator<PaymentData> CREATOR = new zzu();
    private String zzat;
    private String zzaw;
    private PaymentMethodToken zzbd;
    private String zzbw;
    private CardInfo zzcy;
    private UserAddress zzcz;
    private Bundle zzda;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class zza {
        private zza() {
        }
    }

    PaymentData(String str, CardInfo cardInfo, UserAddress userAddress, PaymentMethodToken paymentMethodToken, String str2, Bundle bundle, String str3) {
        this.zzaw = str;
        this.zzcy = cardInfo;
        this.zzcz = userAddress;
        this.zzbd = paymentMethodToken;
        this.zzat = str2;
        this.zzda = bundle;
        this.zzbw = str3;
    }

    private PaymentData() {
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzaw, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzcy, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzcz, i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzbd, i, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzat, false);
        SafeParcelWriter.writeBundle(parcel, 6, this.zzda, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzbw, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Deprecated
    public final String getEmail() {
        return this.zzaw;
    }

    @Deprecated
    public final CardInfo getCardInfo() {
        return this.zzcy;
    }

    @Deprecated
    public final UserAddress getShippingAddress() {
        return this.zzcz;
    }

    @Deprecated
    public final PaymentMethodToken getPaymentMethodToken() {
        return this.zzbd;
    }

    @Deprecated
    public final String getGoogleTransactionId() {
        return this.zzat;
    }

    @Deprecated
    public final Bundle getExtraData() {
        return this.zzda;
    }

    public static PaymentData fromJson(String str) {
        zza zzaVar = new zza();
        PaymentData.this.zzbw = (String) Preconditions.checkNotNull(str, "paymentDataJson cannot be null!");
        return PaymentData.this;
    }

    public final String toJson() {
        return this.zzbw;
    }

    public static PaymentData getFromIntent(Intent intent) {
        return (PaymentData) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.android.gms.wallet.PaymentData", CREATOR);
    }

    @Override // com.google.android.gms.wallet.AutoResolvableResult
    public final void putIntoIntent(Intent intent) {
        SafeParcelableSerializer.serializeToIntentExtra(this, intent, "com.google.android.gms.wallet.PaymentData");
    }
}
