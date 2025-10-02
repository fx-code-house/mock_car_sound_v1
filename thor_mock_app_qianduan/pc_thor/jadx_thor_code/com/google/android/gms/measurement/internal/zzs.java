package com.google.android.gms.measurement.internal;

import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.internal.measurement.zzcd;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzs {
    private zzcd.zzc zza;
    private Long zzb;
    private long zzc;
    private final /* synthetic */ zzr zzd;

    private zzs(zzr zzrVar) {
        this.zzd = zzrVar;
    }

    final zzcd.zzc zza(String str, zzcd.zzc zzcVar) {
        String strZzc = zzcVar.zzc();
        List<zzcd.zze> listZza = zzcVar.zza();
        this.zzd.f_();
        Long l = (Long) zzkr.zzb(zzcVar, "_eid");
        boolean z = l != null;
        if (z && strZzc.equals("_ep")) {
            this.zzd.f_();
            strZzc = (String) zzkr.zzb(zzcVar, "_en");
            if (TextUtils.isEmpty(strZzc)) {
                this.zzd.zzq().zzf().zza("Extra parameter without an event name. eventId", l);
                return null;
            }
            if (this.zza == null || this.zzb == null || l.longValue() != this.zzb.longValue()) {
                Pair<zzcd.zzc, Long> pairZza = this.zzd.zzi().zza(str, l);
                if (pairZza == null || pairZza.first == null) {
                    this.zzd.zzq().zzf().zza("Extra parameter without existing main event. eventName, eventId", strZzc, l);
                    return null;
                }
                this.zza = (zzcd.zzc) pairZza.first;
                this.zzc = ((Long) pairZza.second).longValue();
                this.zzd.f_();
                this.zzb = (Long) zzkr.zzb(this.zza, "_eid");
            }
            long j = this.zzc - 1;
            this.zzc = j;
            if (j <= 0) {
                zzaf zzafVarZzi = this.zzd.zzi();
                zzafVarZzi.zzc();
                zzafVarZzi.zzq().zzw().zza("Clearing complex main event info. appId", str);
                try {
                    zzafVarZzi.c_().execSQL("delete from main_event_params where app_id=?", new String[]{str});
                } catch (SQLiteException e) {
                    zzafVarZzi.zzq().zze().zza("Error clearing complex main event", e);
                }
            } else {
                this.zzd.zzi().zza(str, l, this.zzc, this.zza);
            }
            ArrayList arrayList = new ArrayList();
            for (zzcd.zze zzeVar : this.zza.zza()) {
                this.zzd.f_();
                if (zzkr.zza(zzcVar, zzeVar.zzb()) == null) {
                    arrayList.add(zzeVar);
                }
            }
            if (arrayList.isEmpty()) {
                this.zzd.zzq().zzf().zza("No unique parameters in main event. eventName", strZzc);
            } else {
                arrayList.addAll(listZza);
                listZza = arrayList;
            }
        } else if (z) {
            this.zzb = l;
            this.zza = zzcVar;
            this.zzd.f_();
            Object objZzb = zzkr.zzb(zzcVar, "_epc");
            long jLongValue = ((Long) (objZzb != null ? objZzb : 0L)).longValue();
            this.zzc = jLongValue;
            if (jLongValue <= 0) {
                this.zzd.zzq().zzf().zza("Complex event with zero extra param count. eventName", strZzc);
            } else {
                this.zzd.zzi().zza(str, l, this.zzc, zzcVar);
            }
        }
        return (zzcd.zzc) ((com.google.android.gms.internal.measurement.zzhy) zzcVar.zzbo().zza(strZzc).zzc().zza(listZza).zzy());
    }

    /* synthetic */ zzs(zzr zzrVar, zzq zzqVar) {
        this(zzrVar);
    }
}
