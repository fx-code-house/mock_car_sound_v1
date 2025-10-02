package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import org.apache.commons.lang3.StringUtils;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzc extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzc> CREATOR = new zzv();
    private int zzaq;
    private final boolean zzel;
    private final boolean zzem;
    private final String zzes;
    private final String zzet;
    private final byte[] zzeu;

    zzc(int i, boolean z, String str, String str2, byte[] bArr, boolean z2) {
        this.zzaq = i;
        this.zzel = z;
        this.zzes = str;
        this.zzet = str2;
        this.zzeu = bArr;
        this.zzem = z2;
    }

    public zzc(boolean z, String str, String str2, byte[] bArr, boolean z2) {
        this.zzaq = 0;
        this.zzel = z;
        this.zzes = null;
        this.zzet = null;
        this.zzeu = null;
        this.zzem = false;
    }

    public final void zzf(int i) {
        this.zzaq = i;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzaq);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzel);
        SafeParcelWriter.writeString(parcel, 3, this.zzes, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzet, false);
        SafeParcelWriter.writeByteArray(parcel, 5, this.zzeu, false);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzem);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MetadataImpl { { eventStatus: '");
        sb.append(this.zzaq).append("' } { uploadable: '");
        sb.append(this.zzel).append("' } ");
        if (this.zzes != null) {
            sb.append("{ completionToken: '").append(this.zzes).append("' } ");
        }
        if (this.zzet != null) {
            sb.append("{ accountName: '").append(this.zzet).append("' } ");
        }
        if (this.zzeu != null) {
            sb.append("{ ssbContext: [ ");
            for (byte b : this.zzeu) {
                sb.append("0x").append(Integer.toHexString(b)).append(StringUtils.SPACE);
            }
            sb.append("] } ");
        }
        sb.append("{ contextOnly: '").append(this.zzem).append("' } }");
        return sb.toString();
    }
}
