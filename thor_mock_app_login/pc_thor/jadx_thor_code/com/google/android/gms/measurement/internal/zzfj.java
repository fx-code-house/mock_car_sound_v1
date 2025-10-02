package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzfj {
    private final String zza;
    private final String zzb;
    private final String zzc;
    private final long zzd;
    private final /* synthetic */ zzfc zze;

    private zzfj(zzfc zzfcVar, String str, long j) {
        this.zze = zzfcVar;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkArgument(j > 0);
        this.zza = String.valueOf(str).concat(":start");
        this.zzb = String.valueOf(str).concat(":count");
        this.zzc = String.valueOf(str).concat(":value");
        this.zzd = j;
    }

    private final void zzb() {
        this.zze.zzc();
        long jCurrentTimeMillis = this.zze.zzl().currentTimeMillis();
        SharedPreferences.Editor editorEdit = this.zze.zzf().edit();
        editorEdit.remove(this.zzb);
        editorEdit.remove(this.zzc);
        editorEdit.putLong(this.zza, jCurrentTimeMillis);
        editorEdit.apply();
    }

    public final void zza(String str, long j) {
        this.zze.zzc();
        if (zzc() == 0) {
            zzb();
        }
        if (str == null) {
            str = "";
        }
        long j2 = this.zze.zzf().getLong(this.zzb, 0L);
        if (j2 <= 0) {
            SharedPreferences.Editor editorEdit = this.zze.zzf().edit();
            editorEdit.putString(this.zzc, str);
            editorEdit.putLong(this.zzb, 1L);
            editorEdit.apply();
            return;
        }
        long j3 = j2 + 1;
        boolean z = (this.zze.zzo().zzg().nextLong() & Long.MAX_VALUE) < Long.MAX_VALUE / j3;
        SharedPreferences.Editor editorEdit2 = this.zze.zzf().edit();
        if (z) {
            editorEdit2.putString(this.zzc, str);
        }
        editorEdit2.putLong(this.zzb, j3);
        editorEdit2.apply();
    }

    public final Pair<String, Long> zza() {
        long jAbs;
        this.zze.zzc();
        this.zze.zzc();
        long jZzc = zzc();
        if (jZzc == 0) {
            zzb();
            jAbs = 0;
        } else {
            jAbs = Math.abs(jZzc - this.zze.zzl().currentTimeMillis());
        }
        long j = this.zzd;
        if (jAbs < j) {
            return null;
        }
        if (jAbs > (j << 1)) {
            zzb();
            return null;
        }
        String string = this.zze.zzf().getString(this.zzc, null);
        long j2 = this.zze.zzf().getLong(this.zzb, 0L);
        zzb();
        if (string == null || j2 <= 0) {
            return zzfc.zza;
        }
        return new Pair<>(string, Long.valueOf(j2));
    }

    private final long zzc() {
        return this.zze.zzf().getLong(this.zza, 0L);
    }
}
