package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzt extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzt> CREATOR = new zzv();
    private final String name;
    private final int weight;
    private final String zzaa;
    private final boolean zzab;
    private final boolean zzac;
    private final String zzae;
    private final String zzaf;

    @Nullable
    private final zzm[] zzag;
    private final zzu zzah;

    zzt(String str, String str2, boolean z, int i, boolean z2, String str3, zzm[] zzmVarArr, String str4, zzu zzuVar) {
        this.name = str;
        this.zzaa = str2;
        this.zzab = z;
        this.weight = i;
        this.zzac = z2;
        this.zzaf = str3;
        this.zzag = zzmVarArr;
        this.zzae = str4;
        this.zzah = zzuVar;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.name, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzaa, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzab);
        SafeParcelWriter.writeInt(parcel, 4, this.weight);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzac);
        SafeParcelWriter.writeString(parcel, 6, this.zzaf, false);
        SafeParcelWriter.writeTypedArray(parcel, 7, this.zzag, i, false);
        SafeParcelWriter.writeString(parcel, 11, this.zzae, false);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzah, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final int hashCode() {
        return Objects.hashCode(this.name, this.zzaa, Boolean.valueOf(this.zzab), Integer.valueOf(this.weight), Boolean.valueOf(this.zzac), this.zzaf, Integer.valueOf(Arrays.hashCode(this.zzag)), this.zzae, this.zzah);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzt)) {
            return false;
        }
        zzt zztVar = (zzt) obj;
        return this.zzab == zztVar.zzab && this.weight == zztVar.weight && this.zzac == zztVar.zzac && Objects.equal(this.name, zztVar.name) && Objects.equal(this.zzaa, zztVar.zzaa) && Objects.equal(this.zzaf, zztVar.zzaf) && Objects.equal(this.zzae, zztVar.zzae) && Objects.equal(this.zzah, zztVar.zzah) && Arrays.equals(this.zzag, zztVar.zzag);
    }
}
