package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class LoyaltyPointsBalance extends AbstractSafeParcelable {
    public static final Parcelable.Creator<LoyaltyPointsBalance> CREATOR = new zzi();
    String zzbl;
    int zzfa;
    String zzfb;
    double zzfc;
    long zzfd;
    int zzfe;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private Builder() {
        }

        public final Builder setInt(int i) {
            LoyaltyPointsBalance.this.zzfa = i;
            LoyaltyPointsBalance.this.zzfe = 0;
            return this;
        }

        public final Builder setString(String str) {
            LoyaltyPointsBalance.this.zzfb = str;
            LoyaltyPointsBalance.this.zzfe = 1;
            return this;
        }

        public final Builder setDouble(double d) {
            LoyaltyPointsBalance.this.zzfc = d;
            LoyaltyPointsBalance.this.zzfe = 2;
            return this;
        }

        public final Builder setMoney(String str, long j) {
            LoyaltyPointsBalance.this.zzbl = str;
            LoyaltyPointsBalance.this.zzfd = j;
            LoyaltyPointsBalance.this.zzfe = 3;
            return this;
        }

        public final LoyaltyPointsBalance build() {
            return LoyaltyPointsBalance.this;
        }
    }

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public interface Type {
        public static final int DOUBLE = 2;
        public static final int INT = 0;
        public static final int MONEY = 3;
        public static final int STRING = 1;
        public static final int UNDEFINED = -1;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzfa);
        SafeParcelWriter.writeString(parcel, 3, this.zzfb, false);
        SafeParcelWriter.writeDouble(parcel, 4, this.zzfc);
        SafeParcelWriter.writeString(parcel, 5, this.zzbl, false);
        SafeParcelWriter.writeLong(parcel, 6, this.zzfd);
        SafeParcelWriter.writeInt(parcel, 7, this.zzfe);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    LoyaltyPointsBalance(int i, String str, double d, String str2, long j, int i2) {
        this.zzfa = i;
        this.zzfb = str;
        this.zzfc = d;
        this.zzbl = str2;
        this.zzfd = j;
        this.zzfe = i2;
    }

    LoyaltyPointsBalance() {
        this.zzfe = -1;
        this.zzfa = -1;
        this.zzfc = -1.0d;
    }

    public final int getInt() {
        return this.zzfa;
    }

    public final String getString() {
        return this.zzfb;
    }

    public final double getDouble() {
        return this.zzfc;
    }

    public final String getCurrencyCode() {
        return this.zzbl;
    }

    public final long getCurrencyMicros() {
        return this.zzfd;
    }

    public final int getType() {
        return this.zzfe;
    }
}
