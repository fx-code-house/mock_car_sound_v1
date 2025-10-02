package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class WalletObjectMessage extends AbstractSafeParcelable {
    public static final Parcelable.Creator<WalletObjectMessage> CREATOR = new zzn();
    String zzfg;
    String zzfh;
    TimeInterval zzfl;

    @Deprecated
    UriData zzfm;

    @Deprecated
    UriData zzfn;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private Builder() {
        }

        public final Builder setHeader(String str) {
            WalletObjectMessage.this.zzfg = str;
            return this;
        }

        public final Builder setBody(String str) {
            WalletObjectMessage.this.zzfh = str;
            return this;
        }

        public final Builder setDisplayInterval(TimeInterval timeInterval) {
            WalletObjectMessage.this.zzfl = timeInterval;
            return this;
        }

        @Deprecated
        public final Builder setActionUri(UriData uriData) {
            WalletObjectMessage.this.zzfm = uriData;
            return this;
        }

        @Deprecated
        public final Builder setImageUri(UriData uriData) {
            WalletObjectMessage.this.zzfn = uriData;
            return this;
        }

        public final WalletObjectMessage build() {
            return WalletObjectMessage.this;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzfg, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzfh, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzfl, i, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzfm, i, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzfn, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    WalletObjectMessage(String str, String str2, TimeInterval timeInterval, UriData uriData, UriData uriData2) {
        this.zzfg = str;
        this.zzfh = str2;
        this.zzfl = timeInterval;
        this.zzfm = uriData;
        this.zzfn = uriData2;
    }

    WalletObjectMessage() {
    }

    public final String getHeader() {
        return this.zzfg;
    }

    public final String getBody() {
        return this.zzfh;
    }

    public final TimeInterval getDisplayInterval() {
        return this.zzfl;
    }

    @Deprecated
    public final UriData getActionUri() {
        return this.zzfm;
    }

    @Deprecated
    public final UriData getImageUri() {
        return this.zzfn;
    }
}
