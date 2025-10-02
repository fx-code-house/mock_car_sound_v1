package com.google.android.gms.vision.face.internal.client;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zza extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zza> CREATOR = new zzc();
    public final int type;
    public final PointF[] zzdf;

    public zza(PointF[] pointFArr, int i) {
        this.zzdf = pointFArr;
        this.type = i;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zzdf, i, false);
        SafeParcelWriter.writeInt(parcel, 3, this.type);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
