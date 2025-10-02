package com.google.android.gms.internal.vision;

import android.content.Context;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzav extends zzbr {
    private final zzdf<zzcy<zzbe>> zzfw;
    private final Context zzg;

    zzav(Context context, @Nullable zzdf<zzcy<zzbe>> zzdfVar) {
        if (context == null) {
            throw new NullPointerException("Null context");
        }
        this.zzg = context;
        this.zzfw = zzdfVar;
    }

    @Override // com.google.android.gms.internal.vision.zzbr
    final Context zzaa() {
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.vision.zzbr
    @Nullable
    final zzdf<zzcy<zzbe>> zzab() {
        return this.zzfw;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzg);
        String strValueOf2 = String.valueOf(this.zzfw);
        return new StringBuilder(String.valueOf(strValueOf).length() + 46 + String.valueOf(strValueOf2).length()).append("FlagsContext{context=").append(strValueOf).append(", hermeticFileOverrides=").append(strValueOf2).append("}").toString();
    }

    public final boolean equals(Object obj) {
        zzdf<zzcy<zzbe>> zzdfVar;
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzbr) {
            zzbr zzbrVar = (zzbr) obj;
            if (this.zzg.equals(zzbrVar.zzaa()) && ((zzdfVar = this.zzfw) != null ? zzdfVar.equals(zzbrVar.zzab()) : zzbrVar.zzab() == null)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (this.zzg.hashCode() ^ 1000003) * 1000003;
        zzdf<zzcy<zzbe>> zzdfVar = this.zzfw;
        return iHashCode ^ (zzdfVar == null ? 0 : zzdfVar.hashCode());
    }
}
