package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzag extends AbstractSafeParcelable implements CapabilityInfo {
    public static final Parcelable.Creator<zzag> CREATOR = new zzah();
    private final String zzb;
    private final List<zzfw> zzc;
    private final Object zza = new Object();
    private Set<Node> zzd = null;

    public zzag(String str, List<zzfw> list) {
        this.zzb = str;
        this.zzc = list;
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(list);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzag zzagVar = (zzag) obj;
        String str = this.zzb;
        if (str == null ? zzagVar.zzb != null : !str.equals(zzagVar.zzb)) {
            return false;
        }
        List<zzfw> list = this.zzc;
        return list == null ? zzagVar.zzc == null : list.equals(zzagVar.zzc);
    }

    @Override // com.google.android.gms.wearable.CapabilityInfo
    public final String getName() {
        return this.zzb;
    }

    @Override // com.google.android.gms.wearable.CapabilityInfo
    public final Set<Node> getNodes() {
        Set<Node> set;
        synchronized (this.zza) {
            if (this.zzd == null) {
                this.zzd = new HashSet(this.zzc);
            }
            set = this.zzd;
        }
        return set;
    }

    public final int hashCode() {
        String str = this.zzb;
        int iHashCode = ((str != null ? str.hashCode() : 0) + 31) * 31;
        List<zzfw> list = this.zzc;
        return iHashCode + (list != null ? list.hashCode() : 0);
    }

    public final String toString() {
        String str = this.zzb;
        String strValueOf = String.valueOf(this.zzc);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 18 + String.valueOf(strValueOf).length());
        sb.append("CapabilityInfo{");
        sb.append(str);
        sb.append(", ");
        sb.append(strValueOf);
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzb, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzc, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
