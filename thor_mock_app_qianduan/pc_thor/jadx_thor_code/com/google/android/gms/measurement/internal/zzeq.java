package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzeq extends zzgq {
    private char zza;
    private long zzb;
    private String zzc;
    private final zzes zzd;
    private final zzes zze;
    private final zzes zzf;
    private final zzes zzg;
    private final zzes zzh;
    private final zzes zzi;
    private final zzes zzj;
    private final zzes zzk;
    private final zzes zzl;

    zzeq(zzfu zzfuVar) {
        super(zzfuVar);
        this.zza = (char) 0;
        this.zzb = -1L;
        this.zzd = new zzes(this, 6, false, false);
        this.zze = new zzes(this, 6, true, false);
        this.zzf = new zzes(this, 6, false, true);
        this.zzg = new zzes(this, 5, false, false);
        this.zzh = new zzes(this, 5, true, false);
        this.zzi = new zzes(this, 5, false, true);
        this.zzj = new zzes(this, 4, false, false);
        this.zzk = new zzes(this, 3, false, false);
        this.zzl = new zzes(this, 2, false, false);
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    protected final boolean zzd() {
        return false;
    }

    public final zzes zze() {
        return this.zzd;
    }

    public final zzes zzf() {
        return this.zze;
    }

    public final zzes zzg() {
        return this.zzf;
    }

    public final zzes zzh() {
        return this.zzg;
    }

    public final zzes zzi() {
        return this.zzh;
    }

    public final zzes zzj() {
        return this.zzi;
    }

    public final zzes zzu() {
        return this.zzj;
    }

    public final zzes zzv() {
        return this.zzk;
    }

    public final zzes zzw() {
        return this.zzl;
    }

    protected static Object zza(String str) {
        if (str == null) {
            return null;
        }
        return new zzev(str);
    }

    protected final void zza(int i, boolean z, boolean z2, String str, Object obj, Object obj2, Object obj3) throws IllegalStateException {
        if (!z && zza(i)) {
            zza(i, zza(false, str, obj, obj2, obj3));
        }
        if (z2 || i < 5) {
            return;
        }
        Preconditions.checkNotNull(str);
        zzfr zzfrVarZzf = this.zzy.zzf();
        if (zzfrVarZzf == null) {
            zza(6, "Scheduler not set. Not logging error/warn");
            return;
        }
        if (!zzfrVarZzf.zzaa()) {
            zza(6, "Scheduler not initialized. Not logging error/warn");
            return;
        }
        if (i < 0) {
            i = 0;
        }
        if (i >= 9) {
            i = 8;
        }
        zzfrVarZzf.zza(new zzet(this, i, str, obj, obj2, obj3));
    }

    protected final boolean zza(int i) {
        return Log.isLoggable(zzy(), i);
    }

    protected final void zza(int i, String str) {
        Log.println(i, zzy(), str);
    }

    private final String zzy() {
        String str;
        synchronized (this) {
            if (this.zzc == null) {
                this.zzc = this.zzy.zzr() != null ? this.zzy.zzr() : "FA";
            }
            str = this.zzc;
        }
        return str;
    }

    static String zza(boolean z, String str, Object obj, Object obj2, Object obj3) {
        String str2 = "";
        if (str == null) {
            str = "";
        }
        String strZza = zza(z, obj);
        String strZza2 = zza(z, obj2);
        String strZza3 = zza(z, obj3);
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        String str3 = ", ";
        if (!TextUtils.isEmpty(strZza)) {
            sb.append(str2);
            sb.append(strZza);
            str2 = ", ";
        }
        if (TextUtils.isEmpty(strZza2)) {
            str3 = str2;
        } else {
            sb.append(str2);
            sb.append(strZza2);
        }
        if (!TextUtils.isEmpty(strZza3)) {
            sb.append(str3);
            sb.append(strZza3);
        }
        return sb.toString();
    }

    private static String zza(boolean z, Object obj) {
        String className;
        if (obj == null) {
            return "";
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf(((Integer) obj).intValue());
        }
        int i = 0;
        if (obj instanceof Long) {
            if (!z) {
                return String.valueOf(obj);
            }
            Long l = (Long) obj;
            if (Math.abs(l.longValue()) < 100) {
                return String.valueOf(obj);
            }
            String str = String.valueOf(obj).charAt(0) == '-' ? "-" : "";
            String strValueOf = String.valueOf(Math.abs(l.longValue()));
            return new StringBuilder(str.length() + 43 + str.length()).append(str).append(Math.round(Math.pow(10.0d, strValueOf.length() - 1))).append("...").append(str).append(Math.round(Math.pow(10.0d, strValueOf.length()) - 1.0d)).toString();
        }
        if (obj instanceof Boolean) {
            return String.valueOf(obj);
        }
        if (obj instanceof Throwable) {
            Throwable th = (Throwable) obj;
            StringBuilder sb = new StringBuilder(z ? th.getClass().getName() : th.toString());
            String strZzb = zzb(zzfu.class.getCanonicalName());
            StackTraceElement[] stackTrace = th.getStackTrace();
            int length = stackTrace.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                StackTraceElement stackTraceElement = stackTrace[i];
                if (!stackTraceElement.isNativeMethod() && (className = stackTraceElement.getClassName()) != null && zzb(className).equals(strZzb)) {
                    sb.append(": ");
                    sb.append(stackTraceElement);
                    break;
                }
                i++;
            }
            return sb.toString();
        }
        if (obj instanceof zzev) {
            return ((zzev) obj).zza;
        }
        return z ? "-" : String.valueOf(obj);
    }

    private static String zzb(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf == -1 ? str : str.substring(0, iLastIndexOf);
    }

    public final String zzx() {
        Pair<String, Long> pairZza = zzr().zzb.zza();
        if (pairZza == null || pairZza == zzfc.zza) {
            return null;
        }
        String strValueOf = String.valueOf(pairZza.second);
        String str = (String) pairZza.first;
        return new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(str).length()).append(strValueOf).append(":").append(str).toString();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzak zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ Clock zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ Context zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzeo zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzkv zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzfr zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzeq zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzfc zzr() {
        return super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzab zzs() {
        return super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzw zzt() {
        return super.zzt();
    }
}
