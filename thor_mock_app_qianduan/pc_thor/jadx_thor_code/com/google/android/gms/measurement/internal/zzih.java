package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzcd;
import com.google.android.gms.internal.measurement.zzml;
import com.google.android.gms.internal.measurement.zznv;
import com.google.android.gms.internal.measurement.zznw;
import com.google.firebase.crashlytics.internal.common.AbstractSpiCall;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzih extends zzki {
    public zzih(zzkl zzklVar) {
        super(zzklVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzki
    protected final boolean zzd() {
        return false;
    }

    public final byte[] zza(zzaq zzaqVar, String str) throws IllegalStateException {
        zzkw next;
        Bundle bundleZzb;
        zzf zzfVar;
        zzcd.zzg.zza zzaVar;
        zzcd.zzf.zza zzaVar2;
        Bundle bundle;
        byte[] bArr;
        long j;
        zzam zzamVarZza;
        zzc();
        this.zzy.zzad();
        Preconditions.checkNotNull(zzaqVar);
        Preconditions.checkNotEmpty(str);
        if (!zzs().zze(str, zzas.zzav)) {
            zzq().zzv().zza("Generating ScionPayload disabled. packageName", str);
            return new byte[0];
        }
        if (!"_iap".equals(zzaqVar.zza) && !"_iapx".equals(zzaqVar.zza)) {
            zzq().zzv().zza("Generating a payload for this event is not available. package_name, event_name", str, zzaqVar.zza);
            return null;
        }
        zzcd.zzf.zza zzaVarZzb = zzcd.zzf.zzb();
        zzi().zze();
        try {
            zzf zzfVarZzb = zzi().zzb(str);
            if (zzfVarZzb == null) {
                zzq().zzv().zza("Log and bundle not available. package_name", str);
                return new byte[0];
            }
            if (!zzfVarZzb.zzr()) {
                zzq().zzv().zza("Log and bundle disabled. package_name", str);
                return new byte[0];
            }
            zzcd.zzg.zza zzaVarZza = zzcd.zzg.zzbh().zza(1).zza(AbstractSpiCall.ANDROID_CLIENT_TYPE);
            if (!TextUtils.isEmpty(zzfVarZzb.zzc())) {
                zzaVarZza.zzf(zzfVarZzb.zzc());
            }
            if (!TextUtils.isEmpty(zzfVarZzb.zzn())) {
                zzaVarZza.zze(zzfVarZzb.zzn());
            }
            if (!TextUtils.isEmpty(zzfVarZzb.zzl())) {
                zzaVarZza.zzg(zzfVarZzb.zzl());
            }
            if (zzfVarZzb.zzm() != -2147483648L) {
                zzaVarZza.zzh((int) zzfVarZzb.zzm());
            }
            zzaVarZza.zzf(zzfVarZzb.zzo()).zzk(zzfVarZzb.zzq());
            if (zznv.zzb() && zzs().zze(zzfVarZzb.zzc(), zzas.zzbi)) {
                if (!TextUtils.isEmpty(zzfVarZzb.zze())) {
                    zzaVarZza.zzk(zzfVarZzb.zze());
                } else if (!TextUtils.isEmpty(zzfVarZzb.zzg())) {
                    zzaVarZza.zzp(zzfVarZzb.zzg());
                } else if (!TextUtils.isEmpty(zzfVarZzb.zzf())) {
                    zzaVarZza.zzo(zzfVarZzb.zzf());
                }
            } else if (!TextUtils.isEmpty(zzfVarZzb.zze())) {
                zzaVarZza.zzk(zzfVarZzb.zze());
            } else if (!TextUtils.isEmpty(zzfVarZzb.zzf())) {
                zzaVarZza.zzo(zzfVarZzb.zzf());
            }
            zzac zzacVarZza = this.zza.zza(str);
            zzaVarZza.zzh(zzfVarZzb.zzp());
            if (this.zzy.zzaa() && zzs().zzh(zzaVarZza.zzj())) {
                if (zzml.zzb() && zzs().zza(zzas.zzci)) {
                    if (zzacVarZza.zzc() && !TextUtils.isEmpty(null)) {
                        zzaVarZza.zzn(null);
                    }
                } else {
                    zzaVarZza.zzj();
                    if (!TextUtils.isEmpty(null)) {
                        zzaVarZza.zzn(null);
                    }
                }
            }
            if (zzml.zzb() && zzs().zza(zzas.zzci)) {
                zzaVarZza.zzq(zzacVarZza.zza());
            }
            if (!zzml.zzb() || !zzs().zza(zzas.zzci) || zzacVarZza.zzc()) {
                Pair<String, Boolean> pairZza = zzf().zza(zzfVarZzb.zzc(), zzacVarZza);
                if (zzfVarZzb.zzaf() && pairZza != null && !TextUtils.isEmpty((CharSequence) pairZza.first)) {
                    zzaVarZza.zzh(zza((String) pairZza.first, Long.toString(zzaqVar.zzd)));
                    if (pairZza.second != null) {
                        zzaVarZza.zza(((Boolean) pairZza.second).booleanValue());
                    }
                }
            }
            zzk().zzab();
            zzcd.zzg.zza zzaVarZzc = zzaVarZza.zzc(Build.MODEL);
            zzk().zzab();
            zzaVarZzc.zzb(Build.VERSION.RELEASE).zzf((int) zzk().zze()).zzd(zzk().zzf());
            if (!zzml.zzb() || !zzs().zza(zzas.zzci) || zzacVarZza.zze()) {
                zzaVarZza.zzi(zza(zzfVarZzb.zzd(), Long.toString(zzaqVar.zzd)));
            }
            if (!TextUtils.isEmpty(zzfVarZzb.zzi())) {
                zzaVarZza.zzl(zzfVarZzb.zzi());
            }
            String strZzc = zzfVarZzb.zzc();
            List<zzkw> listZza = zzi().zza(strZzc);
            Iterator<zzkw> it = listZza.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if ("_lte".equals(next.zzc)) {
                    break;
                }
            }
            if (next == null || next.zze == null) {
                zzkw zzkwVar = new zzkw(strZzc, "auto", "_lte", zzl().currentTimeMillis(), 0L);
                listZza.add(zzkwVar);
                zzi().zza(zzkwVar);
            }
            zzkr zzkrVarF_ = f_();
            zzkrVarF_.zzq().zzw().zza("Checking account type status for ad personalization signals");
            if (zzkrVarF_.zzk().zzi()) {
                String strZzc2 = zzfVarZzb.zzc();
                if (zzfVarZzb.zzaf() && zzkrVarF_.zzj().zze(strZzc2)) {
                    zzkrVarF_.zzq().zzv().zza("Turning off ad personalization due to account type");
                    Iterator<zzkw> it2 = listZza.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if ("_npa".equals(it2.next().zzc)) {
                            it2.remove();
                            break;
                        }
                    }
                    listZza.add(new zzkw(strZzc2, "auto", "_npa", zzkrVarF_.zzl().currentTimeMillis(), 1L));
                }
            }
            zzcd.zzk[] zzkVarArr = new zzcd.zzk[listZza.size()];
            for (int i = 0; i < listZza.size(); i++) {
                zzcd.zzk.zza zzaVarZza2 = zzcd.zzk.zzj().zza(listZza.get(i).zzc).zza(listZza.get(i).zzd);
                f_().zza(zzaVarZza2, listZza.get(i).zze);
                zzkVarArr[i] = (zzcd.zzk) ((com.google.android.gms.internal.measurement.zzhy) zzaVarZza2.zzy());
            }
            zzaVarZza.zzb(Arrays.asList(zzkVarArr));
            if (zznw.zzb() && zzs().zza(zzas.zzbz) && zzs().zza(zzas.zzca)) {
                zzeu zzeuVarZza = zzeu.zza(zzaqVar);
                zzo().zza(zzeuVarZza.zzb, zzi().zzi(str));
                zzo().zza(zzeuVarZza, zzs().zza(str));
                bundleZzb = zzeuVarZza.zzb;
            } else {
                bundleZzb = zzaqVar.zzb.zzb();
            }
            Bundle bundle2 = bundleZzb;
            bundle2.putLong("_c", 1L);
            zzq().zzv().zza("Marking in-app purchase as real-time");
            bundle2.putLong("_r", 1L);
            bundle2.putString("_o", zzaqVar.zzc);
            if (zzo().zze(zzaVarZza.zzj())) {
                zzo().zza(bundle2, "_dbg", (Object) 1L);
                zzo().zza(bundle2, "_r", (Object) 1L);
            }
            zzam zzamVarZza2 = zzi().zza(str, zzaqVar.zza);
            if (zzamVarZza2 == null) {
                zzfVar = zzfVarZzb;
                zzaVar = zzaVarZza;
                zzaVar2 = zzaVarZzb;
                bundle = bundle2;
                bArr = null;
                zzamVarZza = new zzam(str, zzaqVar.zza, 0L, 0L, zzaqVar.zzd, 0L, null, null, null, null);
                j = 0;
            } else {
                zzfVar = zzfVarZzb;
                zzaVar = zzaVarZza;
                zzaVar2 = zzaVarZzb;
                bundle = bundle2;
                bArr = null;
                j = zzamVarZza2.zzf;
                zzamVarZza = zzamVarZza2.zza(zzaqVar.zzd);
            }
            zzi().zza(zzamVarZza);
            zzan zzanVar = new zzan(this.zzy, zzaqVar.zzc, str, zzaqVar.zza, zzaqVar.zzd, j, bundle);
            zzcd.zzc.zza zzaVarZzb2 = zzcd.zzc.zzj().zza(zzanVar.zzc).zza(zzanVar.zzb).zzb(zzanVar.zzd);
            Iterator<String> it3 = zzanVar.zze.iterator();
            while (it3.hasNext()) {
                String next2 = it3.next();
                zzcd.zze.zza zzaVarZza3 = zzcd.zze.zzm().zza(next2);
                f_().zza(zzaVarZza3, zzanVar.zze.zza(next2));
                zzaVarZzb2.zza(zzaVarZza3);
            }
            zzcd.zzg.zza zzaVar3 = zzaVar;
            zzaVar3.zza(zzaVarZzb2).zza(zzcd.zzh.zza().zza(zzcd.zzd.zza().zza(zzamVarZza.zzc).zza(zzaqVar.zza)));
            zzaVar3.zzc(zzh().zza(zzfVar.zzc(), Collections.emptyList(), zzaVar3.zzd(), Long.valueOf(zzaVarZzb2.zzf()), Long.valueOf(zzaVarZzb2.zzf())));
            if (zzaVarZzb2.zze()) {
                zzaVar3.zzb(zzaVarZzb2.zzf()).zzc(zzaVarZzb2.zzf());
            }
            long jZzk = zzfVar.zzk();
            if (jZzk != 0) {
                zzaVar3.zze(jZzk);
            }
            long jZzj = zzfVar.zzj();
            if (jZzj != 0) {
                zzaVar3.zzd(jZzj);
            } else if (jZzk != 0) {
                zzaVar3.zzd(jZzk);
            }
            zzfVar.zzv();
            zzaVar3.zzg((int) zzfVar.zzs()).zzg(33025L).zza(zzl().currentTimeMillis()).zzb(Boolean.TRUE.booleanValue());
            zzcd.zzf.zza zzaVar4 = zzaVar2;
            zzaVar4.zza(zzaVar3);
            zzf zzfVar2 = zzfVar;
            zzfVar2.zza(zzaVar3.zzf());
            zzfVar2.zzb(zzaVar3.zzg());
            zzi().zza(zzfVar2);
            zzi().b_();
            try {
                return f_().zzc(((zzcd.zzf) ((com.google.android.gms.internal.measurement.zzhy) zzaVar4.zzy())).zzbk());
            } catch (IOException e) {
                zzq().zze().zza("Data loss. Failed to bundle and serialize. appId", zzeq.zza(str), e);
                return bArr;
            }
        } catch (SecurityException e2) {
            zzq().zzv().zza("Resettable device id encryption failed", e2.getMessage());
            return new byte[0];
        } catch (SecurityException e3) {
            zzq().zzv().zza("app instance id encryption failed", e3.getMessage());
            return new byte[0];
        } finally {
            zzi().zzg();
        }
    }

    private static String zza(String str, String str2) {
        throw new SecurityException("This implementation should not be used.");
    }
}
