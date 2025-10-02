package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class TimeInterval extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TimeInterval> CREATOR = new zzk();
    private long zzfi;
    private long zzfj;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 2, this.zzfi);
        SafeParcelWriter.writeLong(parcel, 3, this.zzfj);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public TimeInterval(long j, long j2) {
        this.zzfi = j;
        this.zzfj = j2;
    }

    TimeInterval() {
    }

    public final long getStartTimestamp() {
        return this.zzfi;
    }

    public final long getEndTimestamp() {
        return this.zzfj;
    }
}
