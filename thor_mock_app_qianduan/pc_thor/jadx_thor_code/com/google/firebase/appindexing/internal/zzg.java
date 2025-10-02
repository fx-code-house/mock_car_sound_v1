package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzg extends AbstractSafeParcelable {
    public final int status;
    private static final zzg zzew = new zzg(1);
    private static final zzg zzex = new zzg(2);
    private static final zzg zzey = new zzg(3);
    public static final Parcelable.Creator<zzg> CREATOR = new zzf();

    public zzg(int i) {
        this.status = i;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.status);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
