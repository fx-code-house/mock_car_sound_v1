package com.google.android.gms.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class GoogleNowAuthState extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GoogleNowAuthState> CREATOR = new zza();
    private String zzbj;
    private String zzbk;
    private long zzbl;

    GoogleNowAuthState(String str, String str2, long j) {
        this.zzbj = str;
        this.zzbk = str2;
        this.zzbl = j;
    }

    @Nullable
    public String getAuthCode() {
        return this.zzbj;
    }

    @Nullable
    public String getAccessToken() {
        return this.zzbk;
    }

    public long getNextAllowedTimeMillis() {
        return this.zzbl;
    }

    public String toString() {
        String str = this.zzbj;
        String str2 = this.zzbk;
        return new StringBuilder(String.valueOf(str).length() + 74 + String.valueOf(str2).length()).append("mAuthCode = ").append(str).append("\nmAccessToken = ").append(str2).append("\nmNextAllowedTimeMillis = ").append(this.zzbl).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getAuthCode(), false);
        SafeParcelWriter.writeString(parcel, 2, getAccessToken(), false);
        SafeParcelWriter.writeLong(parcel, 3, getNextAllowedTimeMillis());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
