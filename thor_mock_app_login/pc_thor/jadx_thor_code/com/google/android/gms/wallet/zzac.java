package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
@Deprecated
/* loaded from: classes2.dex */
public final class zzac extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzac> CREATOR = new zzae();
    private String zzdq;
    private String zzdr;
    private int zzds;
    private int zzdt;

    public zzac(String str, String str2, int i, int i2) {
        this.zzdq = str;
        this.zzdr = str2;
        this.zzds = i;
        this.zzdt = i2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzdq, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzdr, false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzds);
        SafeParcelWriter.writeInt(parcel, 5, this.zzdt);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
