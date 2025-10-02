package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class TransactionInfo extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TransactionInfo> CREATOR = new zzai();
    int zzdx;
    String zzdy;
    String zzdz;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private Builder() {
        }

        public final Builder setTotalPriceStatus(int i) {
            TransactionInfo.this.zzdx = i;
            return this;
        }

        public final Builder setTotalPrice(String str) {
            TransactionInfo.this.zzdy = str;
            return this;
        }

        public final Builder setCurrencyCode(String str) {
            TransactionInfo.this.zzdz = str;
            return this;
        }

        public final TransactionInfo build() {
            Preconditions.checkNotEmpty(TransactionInfo.this.zzdz, "currencyCode must be set!");
            boolean z = true;
            if (TransactionInfo.this.zzdx != 1 && TransactionInfo.this.zzdx != 2 && TransactionInfo.this.zzdx != 3) {
                z = false;
            }
            if (!z) {
                throw new IllegalArgumentException("totalPriceStatus must be set to one of WalletConstants.TotalPriceStatus!");
            }
            if (TransactionInfo.this.zzdx == 2) {
                Preconditions.checkNotEmpty(TransactionInfo.this.zzdy, "An estimated total price must be set if totalPriceStatus is set to WalletConstants.TOTAL_PRICE_STATUS_ESTIMATED!");
            }
            if (TransactionInfo.this.zzdx == 3) {
                Preconditions.checkNotEmpty(TransactionInfo.this.zzdy, "An final total price must be set if totalPriceStatus is set to WalletConstants.TOTAL_PRICE_STATUS_FINAL!");
            }
            return TransactionInfo.this;
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzdx);
        SafeParcelWriter.writeString(parcel, 2, this.zzdy, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzdz, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public TransactionInfo(int i, String str, String str2) {
        this.zzdx = i;
        this.zzdy = str;
        this.zzdz = str2;
    }

    private TransactionInfo() {
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final int getTotalPriceStatus() {
        return this.zzdx;
    }

    public final String getTotalPrice() {
        return this.zzdy;
    }

    public final String getCurrencyCode() {
        return this.zzdz;
    }
}
