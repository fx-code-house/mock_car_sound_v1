package com.google.android.gms.internal.icing;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;
import java.util.BitSet;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzh extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzh> CREATOR = new zzj();
    private final Account account;
    private final String zzj;
    private final boolean zzk;
    private final zzk[] zzl;

    zzh(zzk[] zzkVarArr, String str, boolean z, Account account) {
        this.zzl = zzkVarArr;
        this.zzj = str;
        this.zzk = z;
        this.account = account;
    }

    zzh(String str, boolean z, Account account, zzk... zzkVarArr) {
        this(zzkVarArr, str, z, account);
        if (zzkVarArr != null) {
            BitSet bitSet = new BitSet(zzq.zzy.length);
            for (zzk zzkVar : zzkVarArr) {
                int i = zzkVar.zzs;
                if (i != -1) {
                    if (bitSet.get(i)) {
                        String strValueOf = String.valueOf(zzq.zza(i));
                        throw new IllegalArgumentException(strValueOf.length() != 0 ? "Duplicate global search section type ".concat(strValueOf) : new String("Duplicate global search section type "));
                    }
                    bitSet.set(i);
                }
            }
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedArray(parcel, 1, this.zzl, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzj, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzk);
        SafeParcelWriter.writeParcelable(parcel, 4, this.account, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzj, Boolean.valueOf(this.zzk), this.account, Integer.valueOf(Arrays.hashCode(this.zzl)));
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzh) {
            zzh zzhVar = (zzh) obj;
            if (Objects.equal(this.zzj, zzhVar.zzj) && Objects.equal(Boolean.valueOf(this.zzk), Boolean.valueOf(zzhVar.zzk)) && Objects.equal(this.account, zzhVar.account) && Arrays.equals(this.zzl, zzhVar.zzl)) {
                return true;
            }
        }
        return false;
    }
}
