package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzid;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzhs extends zzhq<zzid.zzd> {
    zzhs() {
    }

    @Override // com.google.android.gms.internal.vision.zzhq
    final boolean zze(zzjn zzjnVar) {
        return zzjnVar instanceof zzid.zze;
    }

    @Override // com.google.android.gms.internal.vision.zzhq
    final zzht<zzid.zzd> zzh(Object obj) {
        return ((zzid.zze) obj).zzyg;
    }

    @Override // com.google.android.gms.internal.vision.zzhq
    final zzht<zzid.zzd> zzi(Object obj) {
        return ((zzid.zze) obj).zzhe();
    }

    @Override // com.google.android.gms.internal.vision.zzhq
    final void zzj(Object obj) {
        zzh(obj).zzej();
    }

    @Override // com.google.android.gms.internal.vision.zzhq
    final <UT, UB> UB zza(zzkd zzkdVar, Object obj, zzho zzhoVar, zzht<zzid.zzd> zzhtVar, UB ub, zzku<UT, UB> zzkuVar) throws IOException {
        Object objZza;
        zzid.zzg zzgVar = (zzid.zzg) obj;
        int i = zzgVar.zzyx.number;
        boolean z = zzgVar.zzyx.zzye;
        zzig zzigVar = null;
        Object objZzb = null;
        if (zzgVar.zzyx.zzyd == zzll.zzadv) {
            zzigVar.zzh(zzkdVar.zzes());
            throw null;
        }
        switch (zzhr.zztn[zzgVar.zzyx.zzyd.ordinal()]) {
            case 1:
                objZzb = Double.valueOf(zzkdVar.readDouble());
                break;
            case 2:
                objZzb = Float.valueOf(zzkdVar.readFloat());
                break;
            case 3:
                objZzb = Long.valueOf(zzkdVar.zzer());
                break;
            case 4:
                objZzb = Long.valueOf(zzkdVar.zzeq());
                break;
            case 5:
                objZzb = Integer.valueOf(zzkdVar.zzes());
                break;
            case 6:
                objZzb = Long.valueOf(zzkdVar.zzet());
                break;
            case 7:
                objZzb = Integer.valueOf(zzkdVar.zzeu());
                break;
            case 8:
                objZzb = Boolean.valueOf(zzkdVar.zzev());
                break;
            case 9:
                objZzb = Integer.valueOf(zzkdVar.zzey());
                break;
            case 10:
                objZzb = Integer.valueOf(zzkdVar.zzfa());
                break;
            case 11:
                objZzb = Long.valueOf(zzkdVar.zzfb());
                break;
            case 12:
                objZzb = Integer.valueOf(zzkdVar.zzfc());
                break;
            case 13:
                objZzb = Long.valueOf(zzkdVar.zzfd());
                break;
            case 14:
                throw new IllegalStateException("Shouldn't reach here.");
            case 15:
                objZzb = zzkdVar.zzex();
                break;
            case 16:
                objZzb = zzkdVar.readString();
                break;
            case 17:
                objZzb = zzkdVar.zzb(zzgVar.zzyw.getClass(), zzhoVar);
                break;
            case 18:
                objZzb = zzkdVar.zza(zzgVar.zzyw.getClass(), zzhoVar);
                break;
        }
        if (zzgVar.zzyx.zzye) {
            zzhtVar.zzb((zzht<zzid.zzd>) zzgVar.zzyx, objZzb);
        } else {
            int i2 = zzhr.zztn[zzgVar.zzyx.zzyd.ordinal()];
            if ((i2 == 17 || i2 == 18) && (objZza = zzhtVar.zza((zzht<zzid.zzd>) zzgVar.zzyx)) != null) {
                objZzb = zzie.zzb(objZza, objZzb);
            }
            zzhtVar.zza((zzht<zzid.zzd>) zzgVar.zzyx, objZzb);
        }
        return ub;
    }

    @Override // com.google.android.gms.internal.vision.zzhq
    final int zza(Map.Entry<?, ?> entry) {
        return ((zzid.zzd) entry.getKey()).number;
    }

    @Override // com.google.android.gms.internal.vision.zzhq
    final void zza(zzlr zzlrVar, Map.Entry<?, ?> entry) throws IOException {
        zzid.zzd zzdVar = (zzid.zzd) entry.getKey();
        if (zzdVar.zzye) {
            switch (zzhr.zztn[zzdVar.zzyd.ordinal()]) {
                case 1:
                    zzke.zza(zzdVar.number, (List<Double>) entry.getValue(), zzlrVar, false);
                    break;
                case 2:
                    zzke.zzb(zzdVar.number, (List<Float>) entry.getValue(), zzlrVar, false);
                    break;
                case 3:
                    zzke.zzc(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 4:
                    zzke.zzd(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 5:
                    zzke.zzh(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 6:
                    zzke.zzf(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 7:
                    zzke.zzk(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 8:
                    zzke.zzn(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 9:
                    zzke.zzi(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 10:
                    zzke.zzl(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 11:
                    zzke.zzg(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 12:
                    zzke.zzj(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 13:
                    zzke.zze(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 14:
                    zzke.zzh(zzdVar.number, (List) entry.getValue(), zzlrVar, false);
                    break;
                case 15:
                    zzke.zzb(zzdVar.number, (List) entry.getValue(), zzlrVar);
                    break;
                case 16:
                    zzke.zza(zzdVar.number, (List<String>) entry.getValue(), zzlrVar);
                    break;
                case 17:
                    List list = (List) entry.getValue();
                    if (list != null && !list.isEmpty()) {
                        zzke.zzb(zzdVar.number, (List<?>) entry.getValue(), zzlrVar, zzjy.zzij().zzf(list.get(0).getClass()));
                        break;
                    }
                    break;
                case 18:
                    List list2 = (List) entry.getValue();
                    if (list2 != null && !list2.isEmpty()) {
                        zzke.zza(zzdVar.number, (List<?>) entry.getValue(), zzlrVar, zzjy.zzij().zzf(list2.get(0).getClass()));
                        break;
                    }
                    break;
            }
        }
        switch (zzhr.zztn[zzdVar.zzyd.ordinal()]) {
            case 1:
                zzlrVar.zza(zzdVar.number, ((Double) entry.getValue()).doubleValue());
                break;
            case 2:
                zzlrVar.zza(zzdVar.number, ((Float) entry.getValue()).floatValue());
                break;
            case 3:
                zzlrVar.zzi(zzdVar.number, ((Long) entry.getValue()).longValue());
                break;
            case 4:
                zzlrVar.zza(zzdVar.number, ((Long) entry.getValue()).longValue());
                break;
            case 5:
                zzlrVar.zzj(zzdVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 6:
                zzlrVar.zzc(zzdVar.number, ((Long) entry.getValue()).longValue());
                break;
            case 7:
                zzlrVar.zzm(zzdVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 8:
                zzlrVar.zza(zzdVar.number, ((Boolean) entry.getValue()).booleanValue());
                break;
            case 9:
                zzlrVar.zzk(zzdVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 10:
                zzlrVar.zzt(zzdVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 11:
                zzlrVar.zzj(zzdVar.number, ((Long) entry.getValue()).longValue());
                break;
            case 12:
                zzlrVar.zzl(zzdVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 13:
                zzlrVar.zzb(zzdVar.number, ((Long) entry.getValue()).longValue());
                break;
            case 14:
                zzlrVar.zzj(zzdVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 15:
                zzlrVar.zza(zzdVar.number, (zzgs) entry.getValue());
                break;
            case 16:
                zzlrVar.zza(zzdVar.number, (String) entry.getValue());
                break;
            case 17:
                zzlrVar.zzb(zzdVar.number, entry.getValue(), zzjy.zzij().zzf(entry.getValue().getClass()));
                break;
            case 18:
                zzlrVar.zza(zzdVar.number, entry.getValue(), zzjy.zzij().zzf(entry.getValue().getClass()));
                break;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzhq
    final Object zza(zzho zzhoVar, zzjn zzjnVar, int i) {
        return zzhoVar.zza(zzjnVar, i);
    }

    @Override // com.google.android.gms.internal.vision.zzhq
    final void zza(zzkd zzkdVar, Object obj, zzho zzhoVar, zzht<zzid.zzd> zzhtVar) throws IOException {
        zzid.zzg zzgVar = (zzid.zzg) obj;
        zzhtVar.zza((zzht<zzid.zzd>) zzgVar.zzyx, zzkdVar.zza(zzgVar.zzyw.getClass(), zzhoVar));
    }

    @Override // com.google.android.gms.internal.vision.zzhq
    final void zza(zzgs zzgsVar, Object obj, zzho zzhoVar, zzht<zzid.zzd> zzhtVar) throws IOException {
        byte[] bArr;
        zzid.zzg zzgVar = (zzid.zzg) obj;
        zzjn zzjnVarZzgv = zzgVar.zzyw.zzhd().zzgv();
        int size = zzgsVar.size();
        if (size == 0) {
            bArr = zzie.zzyy;
        } else {
            byte[] bArr2 = new byte[size];
            zzgsVar.zza(bArr2, 0, 0, size);
            bArr = bArr2;
        }
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        if (byteBufferWrap.hasArray()) {
            zzgr zzgrVar = new zzgr(byteBufferWrap, true);
            zzjy.zzij().zzx(zzjnVarZzgv).zza(zzjnVarZzgv, zzgrVar, zzhoVar);
            zzhtVar.zza((zzht<zzid.zzd>) zzgVar.zzyx, zzjnVarZzgv);
            if (zzgrVar.zzeo() != Integer.MAX_VALUE) {
                throw zzin.zzhl();
            }
            return;
        }
        throw new IllegalArgumentException("Direct buffers not yet supported");
    }
}
