package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzca;
import com.google.android.gms.internal.measurement.zzlo;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzfo extends zzki implements zzad {
    private static int zzb = 65535;
    private static int zzc = 2;
    private final Map<String, Map<String, String>> zzd;
    private final Map<String, Map<String, Boolean>> zze;
    private final Map<String, Map<String, Boolean>> zzf;
    private final Map<String, zzca.zzb> zzg;
    private final Map<String, Map<String, Integer>> zzh;
    private final Map<String, String> zzi;

    zzfo(zzkl zzklVar) {
        super(zzklVar);
        this.zzd = new ArrayMap();
        this.zze = new ArrayMap();
        this.zzf = new ArrayMap();
        this.zzg = new ArrayMap();
        this.zzi = new ArrayMap();
        this.zzh = new ArrayMap();
    }

    @Override // com.google.android.gms.measurement.internal.zzki
    protected final boolean zzd() {
        return false;
    }

    private final void zzi(String str) throws Throwable {
        zzaj();
        zzc();
        Preconditions.checkNotEmpty(str);
        if (this.zzg.get(str) == null) {
            byte[] bArrZzd = zzi().zzd(str);
            if (bArrZzd == null) {
                this.zzd.put(str, null);
                this.zze.put(str, null);
                this.zzf.put(str, null);
                this.zzg.put(str, null);
                this.zzi.put(str, null);
                this.zzh.put(str, null);
                return;
            }
            zzca.zzb.zza zzaVarZzbo = zza(str, bArrZzd).zzbo();
            zza(str, zzaVarZzbo);
            this.zzd.put(str, zza((zzca.zzb) ((com.google.android.gms.internal.measurement.zzhy) zzaVarZzbo.zzy())));
            this.zzg.put(str, (zzca.zzb) ((com.google.android.gms.internal.measurement.zzhy) zzaVarZzbo.zzy()));
            this.zzi.put(str, null);
        }
    }

    protected final zzca.zzb zza(String str) {
        zzaj();
        zzc();
        Preconditions.checkNotEmpty(str);
        zzi(str);
        return this.zzg.get(str);
    }

    protected final String zzb(String str) {
        zzc();
        return this.zzi.get(str);
    }

    protected final void zzc(String str) {
        zzc();
        this.zzi.put(str, null);
    }

    final void zzd(String str) {
        zzc();
        this.zzg.remove(str);
    }

    final boolean zze(String str) {
        zzc();
        zzca.zzb zzbVarZza = zza(str);
        if (zzbVarZza == null) {
            return false;
        }
        return zzbVarZza.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzad
    public final String zza(String str, String str2) throws Throwable {
        zzc();
        zzi(str);
        Map<String, String> map = this.zzd.get(str);
        if (map != null) {
            return map.get(str2);
        }
        return null;
    }

    private static Map<String, String> zza(zzca.zzb zzbVar) {
        ArrayMap arrayMap = new ArrayMap();
        if (zzbVar != null) {
            for (zzca.zzc zzcVar : zzbVar.zze()) {
                arrayMap.put(zzcVar.zza(), zzcVar.zzb());
            }
        }
        return arrayMap;
    }

    private final void zza(String str, zzca.zzb.zza zzaVar) throws IllegalStateException {
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        ArrayMap arrayMap3 = new ArrayMap();
        if (zzaVar != null) {
            for (int i = 0; i < zzaVar.zza(); i++) {
                zzca.zza.C0026zza c0026zzaZzbo = zzaVar.zza(i).zzbo();
                if (TextUtils.isEmpty(c0026zzaZzbo.zza())) {
                    zzq().zzh().zza("EventConfig contained null event name");
                } else {
                    String strZza = c0026zzaZzbo.zza();
                    String strZzb = zzgv.zzb(c0026zzaZzbo.zza());
                    if (!TextUtils.isEmpty(strZzb)) {
                        c0026zzaZzbo = c0026zzaZzbo.zza(strZzb);
                        zzaVar.zza(i, c0026zzaZzbo);
                    }
                    if (zzlo.zzb() && zzs().zza(zzas.zzcm)) {
                        arrayMap.put(strZza, Boolean.valueOf(c0026zzaZzbo.zzb()));
                    } else {
                        arrayMap.put(c0026zzaZzbo.zza(), Boolean.valueOf(c0026zzaZzbo.zzb()));
                    }
                    arrayMap2.put(c0026zzaZzbo.zza(), Boolean.valueOf(c0026zzaZzbo.zzc()));
                    if (c0026zzaZzbo.zzd()) {
                        if (c0026zzaZzbo.zze() < zzc || c0026zzaZzbo.zze() > zzb) {
                            zzq().zzh().zza("Invalid sampling rate. Event name, sample rate", c0026zzaZzbo.zza(), Integer.valueOf(c0026zzaZzbo.zze()));
                        } else {
                            arrayMap3.put(c0026zzaZzbo.zza(), Integer.valueOf(c0026zzaZzbo.zze()));
                        }
                    }
                }
            }
        }
        this.zze.put(str, arrayMap);
        this.zzf.put(str, arrayMap2);
        this.zzh.put(str, arrayMap3);
    }

    protected final boolean zza(String str, byte[] bArr, String str2) throws IllegalStateException {
        zzaj();
        zzc();
        Preconditions.checkNotEmpty(str);
        zzca.zzb.zza zzaVarZzbo = zza(str, bArr).zzbo();
        if (zzaVarZzbo == null) {
            return false;
        }
        zza(str, zzaVarZzbo);
        this.zzg.put(str, (zzca.zzb) ((com.google.android.gms.internal.measurement.zzhy) zzaVarZzbo.zzy()));
        this.zzi.put(str, str2);
        this.zzd.put(str, zza((zzca.zzb) ((com.google.android.gms.internal.measurement.zzhy) zzaVarZzbo.zzy())));
        zzi().zza(str, new ArrayList(zzaVarZzbo.zzb()));
        try {
            zzaVarZzbo.zzc();
            bArr = ((zzca.zzb) ((com.google.android.gms.internal.measurement.zzhy) zzaVarZzbo.zzy())).zzbk();
        } catch (RuntimeException e) {
            zzq().zzh().zza("Unable to serialize reduced-size config. Storing full config instead. appId", zzeq.zza(str), e);
        }
        zzaf zzafVarZzi = zzi();
        Preconditions.checkNotEmpty(str);
        zzafVarZzi.zzc();
        zzafVarZzi.zzaj();
        new ContentValues().put("remote_config", bArr);
        try {
            if (zzafVarZzi.c_().update("apps", r2, "app_id = ?", new String[]{str}) == 0) {
                zzafVarZzi.zzq().zze().zza("Failed to update remote config (got 0). appId", zzeq.zza(str));
            }
        } catch (SQLiteException e2) {
            zzafVarZzi.zzq().zze().zza("Error storing remote config. appId", zzeq.zza(str), e2);
        }
        this.zzg.put(str, (zzca.zzb) ((com.google.android.gms.internal.measurement.zzhy) zzaVarZzbo.zzy()));
        return true;
    }

    final boolean zzb(String str, String str2) throws Throwable {
        Boolean bool;
        zzc();
        zzi(str);
        if (zzg(str) && zzkv.zzd(str2)) {
            return true;
        }
        if (zzh(str) && zzkv.zza(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zze.get(str);
        if (map == null || (bool = map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    final boolean zzc(String str, String str2) throws Throwable {
        Boolean bool;
        zzc();
        zzi(str);
        if (FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(str2) || FirebaseAnalytics.Event.PURCHASE.equals(str2) || FirebaseAnalytics.Event.REFUND.equals(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zzf.get(str);
        if (map == null || (bool = map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    final int zzd(String str, String str2) throws Throwable {
        Integer num;
        zzc();
        zzi(str);
        Map<String, Integer> map = this.zzh.get(str);
        if (map == null || (num = map.get(str2)) == null) {
            return 1;
        }
        return num.intValue();
    }

    final long zzf(String str) throws Throwable {
        String strZza = zza(str, "measurement.account.time_zone_offset_minutes");
        if (TextUtils.isEmpty(strZza)) {
            return 0L;
        }
        try {
            return Long.parseLong(strZza);
        } catch (NumberFormatException e) {
            zzq().zzh().zza("Unable to parse timezone offset. appId", zzeq.zza(str), e);
            return 0L;
        }
    }

    private final zzca.zzb zza(String str, byte[] bArr) throws IllegalStateException {
        if (bArr == null) {
            return zzca.zzb.zzj();
        }
        try {
            zzca.zzb zzbVar = (zzca.zzb) ((com.google.android.gms.internal.measurement.zzhy) ((zzca.zzb.zza) zzkr.zza(zzca.zzb.zzi(), bArr)).zzy());
            zzq().zzw().zza("Parsed config. version, gmp_app_id", zzbVar.zza() ? Long.valueOf(zzbVar.zzb()) : null, zzbVar.zzc() ? zzbVar.zzd() : null);
            return zzbVar;
        } catch (com.google.android.gms.internal.measurement.zzij e) {
            zzq().zzh().zza("Unable to merge remote config. appId", zzeq.zza(str), e);
            return zzca.zzb.zzj();
        } catch (RuntimeException e2) {
            zzq().zzh().zza("Unable to merge remote config. appId", zzeq.zza(str), e2);
            return zzca.zzb.zzj();
        }
    }

    final boolean zzg(String str) {
        return IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(zza(str, "measurement.upload.blacklist_internal"));
    }

    final boolean zzh(String str) {
        return IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(zza(str, "measurement.upload.blacklist_public"));
    }

    @Override // com.google.android.gms.measurement.internal.zzkj
    public final /* bridge */ /* synthetic */ zzjr zzf() {
        return super.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzkj
    public final /* bridge */ /* synthetic */ zzkr f_() {
        return super.f_();
    }

    @Override // com.google.android.gms.measurement.internal.zzkj
    public final /* bridge */ /* synthetic */ zzr zzh() {
        return super.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzkj
    public final /* bridge */ /* synthetic */ zzaf zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzkj
    public final /* bridge */ /* synthetic */ zzfo zzj() {
        return super.zzj();
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
