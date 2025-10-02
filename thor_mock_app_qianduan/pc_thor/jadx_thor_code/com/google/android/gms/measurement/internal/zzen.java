package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzml;
import com.google.android.gms.internal.measurement.zznv;
import com.google.android.gms.internal.measurement.zzpg;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzen extends zzg {
    private String zza;
    private String zzb;
    private int zzc;
    private String zzd;
    private String zze;
    private long zzf;
    private long zzg;
    private List<String> zzh;
    private int zzi;
    private String zzj;
    private String zzk;
    private String zzl;

    zzen(zzfu zzfuVar, long j) {
        super(zzfuVar);
        this.zzg = j;
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zzy() {
        return true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(20:0|2|(1:4)(28:119|6|(1:10)(2:11|(1:13))|117|14|(4:16|(1:18)(1:20)|121|21)|26|(1:31)(1:30)|32|(1:37)(1:36)|38|(1:(1:41)(1:42))|(3:44|45|(1:57)(1:58))(0)|59|(1:61)|123|62|(1:67)(1:66)|68|(1:70)(1:71)|72|73|(2:86|(1:88))(4:77|(1:79)(1:80)|81|(1:85))|(3:90|(1:92)(1:93)|94)|98|(3:100|(1:102)(3:104|(3:107|(1:126)(1:127)|105)|125)|103)|(1:111)|(2:113|114)(2:115|116))|5|26|(2:28|31)(0)|32|(2:34|37)(0)|38|(0)|(0)(0)|59|(0)|123|62|(7:64|67|68|(0)(0)|72|73|(4:75|86|(0)|(0))(0))(0)|98|(0)|(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0253, code lost:
    
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0254, code lost:
    
        zzq().zze().zza("Fetching Google App Id failed with exception. appId", com.google.android.gms.measurement.internal.zzeq.zza(r0), r2);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01b9 A[Catch: IllegalStateException -> 0x0253, TryCatch #3 {IllegalStateException -> 0x0253, blocks: (B:62:0x01b3, B:64:0x01b9, B:66:0x01c5, B:68:0x01d4, B:72:0x01dd, B:75:0x01e7, B:77:0x01f3, B:81:0x020a, B:83:0x0212, B:90:0x0236, B:92:0x024a, B:94:0x024f, B:93:0x024d, B:85:0x0218, B:86:0x021f, B:88:0x0225, B:67:0x01d0), top: B:123:0x01b3 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01d0 A[Catch: IllegalStateException -> 0x0253, TryCatch #3 {IllegalStateException -> 0x0253, blocks: (B:62:0x01b3, B:64:0x01b9, B:66:0x01c5, B:68:0x01d4, B:72:0x01dd, B:75:0x01e7, B:77:0x01f3, B:81:0x020a, B:83:0x0212, B:90:0x0236, B:92:0x024a, B:94:0x024f, B:93:0x024d, B:85:0x0218, B:86:0x021f, B:88:0x0225, B:67:0x01d0), top: B:123:0x01b3 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x021f A[Catch: IllegalStateException -> 0x0253, TryCatch #3 {IllegalStateException -> 0x0253, blocks: (B:62:0x01b3, B:64:0x01b9, B:66:0x01c5, B:68:0x01d4, B:72:0x01dd, B:75:0x01e7, B:77:0x01f3, B:81:0x020a, B:83:0x0212, B:90:0x0236, B:92:0x024a, B:94:0x024f, B:93:0x024d, B:85:0x0218, B:86:0x021f, B:88:0x0225, B:67:0x01d0), top: B:123:0x01b3 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0225 A[Catch: IllegalStateException -> 0x0253, TryCatch #3 {IllegalStateException -> 0x0253, blocks: (B:62:0x01b3, B:64:0x01b9, B:66:0x01c5, B:68:0x01d4, B:72:0x01dd, B:75:0x01e7, B:77:0x01f3, B:81:0x020a, B:83:0x0212, B:90:0x0236, B:92:0x024a, B:94:0x024f, B:93:0x024d, B:85:0x0218, B:86:0x021f, B:88:0x0225, B:67:0x01d0), top: B:123:0x01b3 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0236 A[Catch: IllegalStateException -> 0x0253, TryCatch #3 {IllegalStateException -> 0x0253, blocks: (B:62:0x01b3, B:64:0x01b9, B:66:0x01c5, B:68:0x01d4, B:72:0x01dd, B:75:0x01e7, B:77:0x01f3, B:81:0x020a, B:83:0x0212, B:90:0x0236, B:92:0x024a, B:94:0x024f, B:93:0x024d, B:85:0x0218, B:86:0x021f, B:88:0x0225, B:67:0x01d0), top: B:123:0x01b3 }] */
    @Override // com.google.android.gms.measurement.internal.zzg
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected final void zzz() throws java.lang.IllegalStateException, android.content.res.Resources.NotFoundException, android.content.pm.PackageManager.NameNotFoundException {
        /*
            Method dump skipped, instructions count: 720
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzen.zzz():void");
    }

    final zzn zza(String str) {
        long jMin;
        zzc();
        String strZzaa = zzaa();
        String strZzab = zzab();
        zzv();
        String str2 = this.zzb;
        long jZzae = zzae();
        zzv();
        String str3 = this.zzd;
        zzv();
        zzc();
        if (this.zzf == 0) {
            this.zzf = this.zzy.zzh().zza(zzm(), zzm().getPackageName());
        }
        long j = this.zzf;
        boolean zZzaa = this.zzy.zzaa();
        boolean z = !zzr().zzq;
        zzc();
        String strZzah = !this.zzy.zzaa() ? null : zzah();
        zzfu zzfuVar = this.zzy;
        Long lValueOf = Long.valueOf(zzfuVar.zzb().zzh.zza());
        if (lValueOf.longValue() == 0) {
            jMin = zzfuVar.zza;
        } else {
            jMin = Math.min(zzfuVar.zza, lValueOf.longValue());
        }
        long j2 = jMin;
        int iZzaf = zzaf();
        boolean zBooleanValue = zzs().zzg().booleanValue();
        Boolean boolZzf = zzs().zzf("google_analytics_ssaid_collection_enabled");
        boolean zBooleanValue2 = Boolean.valueOf(boolZzf == null || boolZzf.booleanValue()).booleanValue();
        zzfc zzfcVarZzr = zzr();
        zzfcVarZzr.zzc();
        return new zzn(strZzaa, strZzab, str2, jZzae, str3, 33025L, j, str, zZzaa, z, strZzah, 0L, j2, iZzaf, zBooleanValue, zBooleanValue2, zzfcVarZzr.zzf().getBoolean("deferred_analytics_collection", false), zzac(), zzs().zzf("google_analytics_default_allow_ad_personalization_signals") == null ? null : Boolean.valueOf(!r1.booleanValue()), this.zzg, this.zzh, (zznv.zzb() && zzs().zza(zzas.zzbi)) ? zzad() : null, (zzml.zzb() && zzs().zza(zzas.zzcg)) ? zzr().zzx().zza() : "");
    }

    private final String zzah() throws IllegalStateException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (zzpg.zzb() && zzs().zza(zzas.zzbk)) {
            zzq().zzw().zza("Disabled IID for tests.");
            return null;
        }
        try {
            Class<?> clsLoadClass = zzm().getClassLoader().loadClass("com.google.firebase.analytics.FirebaseAnalytics");
            if (clsLoadClass == null) {
                return null;
            }
            try {
                Object objInvoke = clsLoadClass.getDeclaredMethod("getInstance", Context.class).invoke(null, zzm());
                if (objInvoke == null) {
                    return null;
                }
                try {
                    return (String) clsLoadClass.getDeclaredMethod("getFirebaseInstanceId", new Class[0]).invoke(objInvoke, new Object[0]);
                } catch (Exception unused) {
                    zzq().zzj().zza("Failed to retrieve Firebase Instance Id");
                    return null;
                }
            } catch (Exception unused2) {
                zzq().zzi().zza("Failed to obtain Firebase Analytics instance");
                return null;
            }
        } catch (ClassNotFoundException unused3) {
        }
    }

    final String zzaa() {
        zzv();
        return this.zza;
    }

    final String zzab() {
        zzv();
        return this.zzj;
    }

    final String zzac() {
        zzv();
        return this.zzk;
    }

    final String zzad() {
        zzv();
        return this.zzl;
    }

    final int zzae() {
        zzv();
        return this.zzc;
    }

    final int zzaf() {
        zzv();
        return this.zzi;
    }

    final List<String> zzag() {
        return this.zzh;
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zza zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzhb zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzen zzf() {
        return super.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzir zzg() {
        return super.zzg();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzii zzh() {
        return super.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzem zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzjx zzj() {
        return super.zzj();
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
