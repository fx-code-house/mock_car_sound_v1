package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzbv;
import com.google.android.gms.internal.measurement.zzcd;
import com.google.android.gms.internal.measurement.zzmx;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzx extends zzu {
    private zzbv.zze zzg;
    private final /* synthetic */ zzr zzh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzx(zzr zzrVar, String str, int i, zzbv.zze zzeVar) {
        super(str, i);
        this.zzh = zzrVar;
        this.zzg = zzeVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzu
    final boolean zzb() {
        return true;
    }

    @Override // com.google.android.gms.measurement.internal.zzu
    final boolean zzc() {
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzu
    final int zza() {
        return this.zzg.zzb();
    }

    /* JADX WARN: Multi-variable type inference failed */
    final boolean zza(Long l, Long l2, zzcd.zzk zzkVar, boolean z) {
        Object[] objArr = zzmx.zzb() && this.zzh.zzs().zzd(this.zza, zzas.zzaz);
        boolean zZze = this.zzg.zze();
        boolean zZzf = this.zzg.zzf();
        boolean zZzh = this.zzg.zzh();
        Object[] objArr2 = zZze || zZzf || zZzh;
        Boolean boolZza = null;
        boolZza = null;
        boolZza = null;
        boolZza = null;
        boolZza = null;
        if (z && objArr2 == false) {
            this.zzh.zzq().zzw().zza("Property filter already evaluated true and it is not associated with an enhanced audience. audience ID, filter ID", Integer.valueOf(this.zzb), this.zzg.zza() ? Integer.valueOf(this.zzg.zzb()) : null);
            return true;
        }
        zzbv.zzc zzcVarZzd = this.zzg.zzd();
        boolean zZzf2 = zzcVarZzd.zzf();
        if (zzkVar.zzf()) {
            if (!zzcVarZzd.zzc()) {
                this.zzh.zzq().zzh().zza("No number filter for long property. property", this.zzh.zzn().zzc(zzkVar.zzc()));
            } else {
                boolZza = zza(zza(zzkVar.zzg(), zzcVarZzd.zzd()), zZzf2);
            }
        } else if (zzkVar.zzh()) {
            if (!zzcVarZzd.zzc()) {
                this.zzh.zzq().zzh().zza("No number filter for double property. property", this.zzh.zzn().zzc(zzkVar.zzc()));
            } else {
                boolZza = zza(zza(zzkVar.zzi(), zzcVarZzd.zzd()), zZzf2);
            }
        } else if (zzkVar.zzd()) {
            if (!zzcVarZzd.zza()) {
                if (!zzcVarZzd.zzc()) {
                    this.zzh.zzq().zzh().zza("No string or number filter defined. property", this.zzh.zzn().zzc(zzkVar.zzc()));
                } else if (zzkr.zza(zzkVar.zze())) {
                    boolZza = zza(zza(zzkVar.zze(), zzcVarZzd.zzd()), zZzf2);
                } else {
                    this.zzh.zzq().zzh().zza("Invalid user property value for Numeric number filter. property, value", this.zzh.zzn().zzc(zzkVar.zzc()), zzkVar.zze());
                }
            } else {
                boolZza = zza(zza(zzkVar.zze(), zzcVarZzd.zzb(), this.zzh.zzq()), zZzf2);
            }
        } else {
            this.zzh.zzq().zzh().zza("User property has no value, property", this.zzh.zzn().zzc(zzkVar.zzc()));
        }
        this.zzh.zzq().zzw().zza("Property filter result", boolZza == null ? "null" : boolZza);
        if (boolZza == null) {
            return false;
        }
        this.zzc = true;
        if (zZzh && !boolZza.booleanValue()) {
            return true;
        }
        if (!z || this.zzg.zze()) {
            this.zzd = boolZza;
        }
        if (boolZza.booleanValue() && objArr2 != false && zzkVar.zza()) {
            long jZzb = zzkVar.zzb();
            if (l != null) {
                jZzb = l.longValue();
            }
            if (objArr != false && this.zzg.zze() && !this.zzg.zzf() && l2 != null) {
                jZzb = l2.longValue();
            }
            if (this.zzg.zzf()) {
                this.zzf = Long.valueOf(jZzb);
            } else {
                this.zze = Long.valueOf(jZzb);
            }
        }
        return true;
    }
}
