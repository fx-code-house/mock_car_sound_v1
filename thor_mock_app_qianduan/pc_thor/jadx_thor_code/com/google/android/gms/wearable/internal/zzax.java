package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.wearable.ChannelApi;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzax extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzax> CREATOR = new zzay();
    final zzbi zza;
    final int zzb;
    final int zzc;
    final int zzd;

    public zzax(zzbi zzbiVar, int i, int i2, int i3) {
        this.zza = zzbiVar;
        this.zzb = i;
        this.zzc = i2;
        this.zzd = i3;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        int i = this.zzb;
        String string = i != 1 ? i != 2 ? i != 3 ? i != 4 ? Integer.toString(i) : "OUTPUT_CLOSED" : "INPUT_CLOSED" : "CHANNEL_CLOSED" : "CHANNEL_OPENED";
        int i2 = this.zzc;
        String string2 = i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? Integer.toString(i2) : "CLOSE_REASON_LOCAL_CLOSE" : "CLOSE_REASON_REMOTE_CLOSE" : "CLOSE_REASON_DISCONNECTED" : "CLOSE_REASON_NORMAL";
        int i3 = this.zzd;
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 81 + String.valueOf(string).length() + String.valueOf(string2).length());
        sb.append("ChannelEventParcelable[, channel=");
        sb.append(strValueOf);
        sb.append(", type=");
        sb.append(string);
        sb.append(", closeReason=");
        sb.append(string2);
        sb.append(", appErrorCode=");
        sb.append(i3);
        sb.append("]");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zza, i, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzb);
        SafeParcelWriter.writeInt(parcel, 4, this.zzc);
        SafeParcelWriter.writeInt(parcel, 5, this.zzd);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final void zza(ChannelApi.ChannelListener channelListener) {
        int i = this.zzb;
        if (i == 1) {
            channelListener.onChannelOpened(this.zza);
            return;
        }
        if (i == 2) {
            channelListener.onChannelClosed(this.zza, this.zzc, this.zzd);
            return;
        }
        if (i == 3) {
            channelListener.onInputClosed(this.zza, this.zzc, this.zzd);
            return;
        }
        if (i == 4) {
            channelListener.onOutputClosed(this.zza, this.zzc, this.zzd);
            return;
        }
        StringBuilder sb = new StringBuilder(25);
        sb.append("Unknown type: ");
        sb.append(i);
        Log.w("ChannelEventParcelable", sb.toString());
    }
}
