package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class CreateWalletObjectsRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<CreateWalletObjectsRequest> CREATOR = new zzg();
    public static final int REQUEST_IMMEDIATE_SAVE = 1;
    public static final int SHOW_SAVE_PROMPT = 0;
    LoyaltyWalletObject zzao;
    OfferWalletObject zzap;
    GiftCardWalletObject zzaq;
    int zzar;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private Builder() {
        }

        public final Builder setLoyaltyWalletObject(LoyaltyWalletObject loyaltyWalletObject) {
            CreateWalletObjectsRequest.this.zzao = loyaltyWalletObject;
            return this;
        }

        public final Builder setOfferWalletObject(OfferWalletObject offerWalletObject) {
            CreateWalletObjectsRequest.this.zzap = offerWalletObject;
            return this;
        }

        public final Builder setGiftCardWalletObject(GiftCardWalletObject giftCardWalletObject) {
            CreateWalletObjectsRequest.this.zzaq = giftCardWalletObject;
            return this;
        }

        public final Builder setCreateMode(int i) {
            CreateWalletObjectsRequest.this.zzar = i;
            return this;
        }

        public final CreateWalletObjectsRequest build() {
            Preconditions.checkState(((CreateWalletObjectsRequest.this.zzaq == null ? 0 : 1) + (CreateWalletObjectsRequest.this.zzao == null ? 0 : 1)) + (CreateWalletObjectsRequest.this.zzap == null ? 0 : 1) == 1, "CreateWalletObjectsRequest must have exactly one Wallet Object");
            return CreateWalletObjectsRequest.this;
        }
    }

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    @Retention(RetentionPolicy.SOURCE)
    public @interface CreateMode {
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzao, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzap, i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzaq, i, false);
        SafeParcelWriter.writeInt(parcel, 5, this.zzar);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    CreateWalletObjectsRequest(LoyaltyWalletObject loyaltyWalletObject, OfferWalletObject offerWalletObject, GiftCardWalletObject giftCardWalletObject, int i) {
        this.zzao = loyaltyWalletObject;
        this.zzap = offerWalletObject;
        this.zzaq = giftCardWalletObject;
        this.zzar = i;
    }

    CreateWalletObjectsRequest() {
    }

    @Deprecated
    public CreateWalletObjectsRequest(LoyaltyWalletObject loyaltyWalletObject) {
        this.zzao = loyaltyWalletObject;
    }

    @Deprecated
    public CreateWalletObjectsRequest(OfferWalletObject offerWalletObject) {
        this.zzap = offerWalletObject;
    }

    @Deprecated
    public CreateWalletObjectsRequest(GiftCardWalletObject giftCardWalletObject) {
        this.zzaq = giftCardWalletObject;
    }

    public final LoyaltyWalletObject getLoyaltyWalletObject() {
        return this.zzao;
    }

    public final OfferWalletObject getOfferWalletObject() {
        return this.zzap;
    }

    public final GiftCardWalletObject getGiftCardWalletObject() {
        return this.zzaq;
    }

    public final int getCreateMode() {
        return this.zzar;
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
