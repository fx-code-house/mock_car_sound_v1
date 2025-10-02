package com.google.android.gms.internal.vision;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.vision.Frame;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzu extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzu> CREATOR = new zzt();
    public int height;
    public int id;
    public int rotation;
    public int width;
    public long zzaz;

    public zzu() {
    }

    public zzu(int i, int i2, int i3, long j, int i4) {
        this.width = i;
        this.height = i2;
        this.id = i3;
        this.zzaz = j;
        this.rotation = i4;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.width);
        SafeParcelWriter.writeInt(parcel, 3, this.height);
        SafeParcelWriter.writeInt(parcel, 4, this.id);
        SafeParcelWriter.writeLong(parcel, 5, this.zzaz);
        SafeParcelWriter.writeInt(parcel, 6, this.rotation);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public static zzu zzd(Frame frame) {
        zzu zzuVar = new zzu();
        zzuVar.width = frame.getMetadata().getWidth();
        zzuVar.height = frame.getMetadata().getHeight();
        zzuVar.rotation = frame.getMetadata().getRotation();
        zzuVar.id = frame.getMetadata().getId();
        zzuVar.zzaz = frame.getMetadata().getTimestampMillis();
        return zzuVar;
    }
}
