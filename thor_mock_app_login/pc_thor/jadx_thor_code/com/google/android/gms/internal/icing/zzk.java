package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzk extends AbstractSafeParcelable {
    private final String zzq;
    private final zzt zzr;
    public final int zzs;
    private final byte[] zzt;
    private static final int zzo = Integer.parseInt("-1");
    public static final Parcelable.Creator<zzk> CREATOR = new zzn();
    private static final zzt zzp = new zzs("SsbContext").zzb(true).zzc("blob").zzc();

    zzk(String str, zzt zztVar, int i, byte[] bArr) {
        int i2 = zzo;
        Preconditions.checkArgument(i == i2 || zzq.zza(i) != null, new StringBuilder(32).append("Invalid section type ").append(i).toString());
        this.zzq = str;
        this.zzr = zztVar;
        this.zzs = i;
        this.zzt = bArr;
        String string = (i == i2 || zzq.zza(i) != null) ? (str == null || bArr == null) ? null : "Both content and blobContent set" : new StringBuilder(32).append("Invalid section type ").append(i).toString();
        if (string != null) {
            throw new IllegalArgumentException(string);
        }
    }

    public zzk(String str, zzt zztVar) {
        this(str, zztVar, zzo, null);
    }

    public zzk(String str, zzt zztVar, String str2) {
        this(str, zztVar, zzq.zzb(str2), null);
    }

    public zzk(byte[] bArr, zzt zztVar) {
        this(null, zztVar, zzo, bArr);
    }

    public static zzk zza(byte[] bArr) {
        return new zzk(bArr, zzp);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzq, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzr, i, false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzs);
        SafeParcelWriter.writeByteArray(parcel, 5, this.zzt, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
