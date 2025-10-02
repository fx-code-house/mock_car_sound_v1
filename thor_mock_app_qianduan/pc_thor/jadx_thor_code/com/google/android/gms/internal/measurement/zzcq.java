package com.google.android.gms.internal.measurement;

import android.content.Context;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzcq extends zzdp {
    private final Context zza;
    private final zzec<zzdy<zzdd>> zzb;

    zzcq(Context context, @Nullable zzec<zzdy<zzdd>> zzecVar) {
        if (context == null) {
            throw new NullPointerException("Null context");
        }
        this.zza = context;
        this.zzb = zzecVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    final Context zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    @Nullable
    final zzec<zzdy<zzdd>> zzb() {
        return this.zzb;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        String strValueOf2 = String.valueOf(this.zzb);
        return new StringBuilder(String.valueOf(strValueOf).length() + 46 + String.valueOf(strValueOf2).length()).append("FlagsContext{context=").append(strValueOf).append(", hermeticFileOverrides=").append(strValueOf2).append("}").toString();
    }

    public final boolean equals(Object obj) {
        zzec<zzdy<zzdd>> zzecVar;
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzdp) {
            zzdp zzdpVar = (zzdp) obj;
            if (this.zza.equals(zzdpVar.zza()) && ((zzecVar = this.zzb) != null ? zzecVar.equals(zzdpVar.zzb()) : zzdpVar.zzb() == null)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (this.zza.hashCode() ^ 1000003) * 1000003;
        zzec<zzdy<zzdd>> zzecVar = this.zzb;
        return iHashCode ^ (zzecVar == null ? 0 : zzecVar.hashCode());
    }
}
