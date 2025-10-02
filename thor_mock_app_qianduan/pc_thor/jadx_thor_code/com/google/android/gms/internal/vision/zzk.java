package com.google.android.gms.internal.vision;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zzk extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzk> CREATOR = new zzj();
    public int zzbt;
    private boolean zzbu;

    public zzk() {
    }

    public zzk(int i, boolean z) {
        this.zzbt = i;
        this.zzbu = z;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzbt);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzbu);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
