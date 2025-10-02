package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.identity.intents.model.UserAddress;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class CardInfo extends AbstractSafeParcelable {
    public static final Parcelable.Creator<CardInfo> CREATOR = new zzc();
    private int zzaa;
    private UserAddress zzab;
    private String zzx;
    private String zzy;
    private String zzz;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzx, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzy, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzz, false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzaa);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzab, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    CardInfo(String str, String str2, String str3, int i, UserAddress userAddress) {
        this.zzx = str;
        this.zzy = str2;
        this.zzz = str3;
        this.zzaa = i;
        this.zzab = userAddress;
    }

    private CardInfo() {
    }

    public final String getCardDescription() {
        return this.zzx;
    }

    public final String getCardNetwork() {
        return this.zzy;
    }

    public final String getCardDetails() {
        return this.zzz;
    }

    public final int getCardClass() {
        int i = this.zzaa;
        if (i == 1 || i == 2 || i == 3) {
            return i;
        }
        return 0;
    }

    public final UserAddress getBillingAddress() {
        return this.zzab;
    }
}
