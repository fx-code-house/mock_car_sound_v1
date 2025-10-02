package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class LoyaltyPoints extends AbstractSafeParcelable {
    public static final Parcelable.Creator<LoyaltyPoints> CREATOR = new zzh();
    String label;

    @Deprecated
    TimeInterval zzcj;
    LoyaltyPointsBalance zzey;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private Builder() {
        }

        @Deprecated
        public final Builder setType(String str) {
            return this;
        }

        public final Builder setLabel(String str) {
            LoyaltyPoints.this.label = str;
            return this;
        }

        public final Builder setBalance(LoyaltyPointsBalance loyaltyPointsBalance) {
            LoyaltyPoints.this.zzey = loyaltyPointsBalance;
            return this;
        }

        @Deprecated
        public final Builder setValidTimeInterval(TimeInterval timeInterval) {
            LoyaltyPoints.this.zzcj = timeInterval;
            return this;
        }

        public final LoyaltyPoints build() {
            return LoyaltyPoints.this;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Deprecated
    public final String getType() {
        return null;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.label, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzey, i, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzcj, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    LoyaltyPoints(String str, LoyaltyPointsBalance loyaltyPointsBalance, TimeInterval timeInterval) {
        this.label = str;
        this.zzey = loyaltyPointsBalance;
        this.zzcj = timeInterval;
    }

    LoyaltyPoints() {
    }

    public final String getLabel() {
        return this.label;
    }

    public final LoyaltyPointsBalance getBalance() {
        return this.zzey;
    }

    @Deprecated
    public final TimeInterval getValidTimeInterval() {
        return this.zzcj;
    }
}
