package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzap extends AbstractSafeParcelable implements Iterable<String> {
    public static final Parcelable.Creator<zzap> CREATOR = new zzar();
    private final Bundle zza;

    zzap(Bundle bundle) {
        this.zza = bundle;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, zzb(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    final Object zza(String str) {
        return this.zza.get(str);
    }

    final Long zzb(String str) {
        return Long.valueOf(this.zza.getLong(str));
    }

    final Double zzc(String str) {
        return Double.valueOf(this.zza.getDouble(str));
    }

    final String zzd(String str) {
        return this.zza.getString(str);
    }

    public final int zza() {
        return this.zza.size();
    }

    public final String toString() {
        return this.zza.toString();
    }

    public final Bundle zzb() {
        return new Bundle(this.zza);
    }

    @Override // java.lang.Iterable
    public final Iterator<String> iterator() {
        return new zzao(this);
    }
}
