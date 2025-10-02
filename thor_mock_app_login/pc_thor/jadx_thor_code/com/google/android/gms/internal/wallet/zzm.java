package com.google.android.gms.internal.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzm extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzm> CREATOR = new zzp();
    private String[] zzem;
    private int[] zzen;
    private RemoteViews zzeo;
    private byte[] zzep;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringArray(parcel, 1, this.zzem, false);
        SafeParcelWriter.writeIntArray(parcel, 2, this.zzen, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzeo, i, false);
        SafeParcelWriter.writeByteArray(parcel, 4, this.zzep, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzm(String[] strArr, int[] iArr, RemoteViews remoteViews, byte[] bArr) {
        this.zzem = strArr;
        this.zzen = iArr;
        this.zzeo = remoteViews;
        this.zzep = bArr;
    }

    private zzm() {
    }
}
