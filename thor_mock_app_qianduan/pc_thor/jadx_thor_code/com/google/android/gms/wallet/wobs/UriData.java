package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class UriData extends AbstractSafeParcelable {
    public static final Parcelable.Creator<UriData> CREATOR = new zzl();
    private String description;
    private String zzfk;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzfk, false);
        SafeParcelWriter.writeString(parcel, 3, this.description, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public UriData(String str, String str2) {
        this.zzfk = str;
        this.description = str2;
    }

    UriData() {
    }

    public final String getUri() {
        return this.zzfk;
    }

    public final String getDescription() {
        return this.description;
    }
}
