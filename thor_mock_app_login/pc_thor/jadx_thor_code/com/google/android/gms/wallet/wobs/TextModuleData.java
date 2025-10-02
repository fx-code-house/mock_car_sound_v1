package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class TextModuleData extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TextModuleData> CREATOR = new zzj();
    private String zzfg;
    private String zzfh;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzfg, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzfh, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public TextModuleData(String str, String str2) {
        this.zzfg = str;
        this.zzfh = str2;
    }

    TextModuleData() {
    }

    public final String getHeader() {
        return this.zzfg;
    }

    public final String getBody() {
        return this.zzfh;
    }
}
