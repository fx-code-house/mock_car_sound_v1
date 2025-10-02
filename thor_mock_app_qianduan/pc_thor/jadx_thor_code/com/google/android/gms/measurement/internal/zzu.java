package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzbv;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
abstract class zzu {
    String zza;
    int zzb;
    Boolean zzc;
    Boolean zzd;
    Long zze;
    Long zzf;

    zzu(String str, int i) {
        this.zza = str;
        this.zzb = i;
    }

    abstract int zza();

    abstract boolean zzb();

    abstract boolean zzc();

    static Boolean zza(Boolean bool, boolean z) {
        if (bool == null) {
            return null;
        }
        return Boolean.valueOf(bool.booleanValue() != z);
    }

    static Boolean zza(String str, zzbv.zzf zzfVar, zzeq zzeqVar) {
        String strZzd;
        List<String> list;
        Preconditions.checkNotNull(zzfVar);
        if (str == null || !zzfVar.zza() || zzfVar.zzb() == zzbv.zzf.zzb.UNKNOWN_MATCH_TYPE) {
            return null;
        }
        if (zzfVar.zzb() == zzbv.zzf.zzb.IN_LIST) {
            if (zzfVar.zzh() == 0) {
                return null;
            }
        } else if (!zzfVar.zzc()) {
            return null;
        }
        zzbv.zzf.zzb zzbVarZzb = zzfVar.zzb();
        boolean zZzf = zzfVar.zzf();
        if (zZzf || zzbVarZzb == zzbv.zzf.zzb.REGEXP || zzbVarZzb == zzbv.zzf.zzb.IN_LIST) {
            strZzd = zzfVar.zzd();
        } else {
            strZzd = zzfVar.zzd().toUpperCase(Locale.ENGLISH);
        }
        String str2 = strZzd;
        if (zzfVar.zzh() == 0) {
            list = null;
        } else {
            List<String> listZzg = zzfVar.zzg();
            if (!zZzf) {
                ArrayList arrayList = new ArrayList(listZzg.size());
                Iterator<String> it = listZzg.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().toUpperCase(Locale.ENGLISH));
                }
                listZzg = Collections.unmodifiableList(arrayList);
            }
            list = listZzg;
        }
        return zza(str, zzbVarZzb, zZzf, str2, list, zzbVarZzb == zzbv.zzf.zzb.REGEXP ? str2 : null, zzeqVar);
    }

    private static Boolean zza(String str, zzbv.zzf.zzb zzbVar, boolean z, String str2, List<String> list, String str3, zzeq zzeqVar) throws IllegalStateException {
        if (str == null) {
            return null;
        }
        if (zzbVar == zzbv.zzf.zzb.IN_LIST) {
            if (list == null || list.size() == 0) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!z && zzbVar != zzbv.zzf.zzb.REGEXP) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (zzq.zza[zzbVar.ordinal()]) {
            case 1:
                try {
                    break;
                } catch (PatternSyntaxException unused) {
                    if (zzeqVar != null) {
                        zzeqVar.zzh().zza("Invalid regular expression in REGEXP audience filter. expression", str3);
                    }
                    return null;
                }
        }
        return null;
    }

    static Boolean zza(long j, zzbv.zzd zzdVar) {
        try {
            return zza(new BigDecimal(j), zzdVar, 0.0d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    static Boolean zza(double d, zzbv.zzd zzdVar) {
        try {
            return zza(new BigDecimal(d), zzdVar, Math.ulp(d));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    static Boolean zza(String str, zzbv.zzd zzdVar) {
        if (!zzkr.zza(str)) {
            return null;
        }
        try {
            return zza(new BigDecimal(str), zzdVar, 0.0d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static Boolean zza(BigDecimal bigDecimal, zzbv.zzd zzdVar, double d) {
        BigDecimal bigDecimal2;
        BigDecimal bigDecimal3;
        BigDecimal bigDecimal4;
        Preconditions.checkNotNull(zzdVar);
        if (zzdVar.zza() && zzdVar.zzb() != zzbv.zzd.zza.UNKNOWN_COMPARISON_TYPE) {
            if (zzdVar.zzb() == zzbv.zzd.zza.BETWEEN) {
                if (!zzdVar.zzg() || !zzdVar.zzi()) {
                    return null;
                }
            } else if (!zzdVar.zze()) {
                return null;
            }
            zzbv.zzd.zza zzaVarZzb = zzdVar.zzb();
            if (zzdVar.zzb() == zzbv.zzd.zza.BETWEEN) {
                if (zzkr.zza(zzdVar.zzh()) && zzkr.zza(zzdVar.zzj())) {
                    try {
                        BigDecimal bigDecimal5 = new BigDecimal(zzdVar.zzh());
                        bigDecimal4 = new BigDecimal(zzdVar.zzj());
                        bigDecimal3 = bigDecimal5;
                        bigDecimal2 = null;
                    } catch (NumberFormatException unused) {
                    }
                }
                return null;
            }
            if (!zzkr.zza(zzdVar.zzf())) {
                return null;
            }
            try {
                bigDecimal2 = new BigDecimal(zzdVar.zzf());
                bigDecimal3 = null;
                bigDecimal4 = null;
            } catch (NumberFormatException unused2) {
            }
            if (zzaVarZzb == zzbv.zzd.zza.BETWEEN) {
                if (bigDecimal3 == null) {
                    return null;
                }
            } else if (bigDecimal2 != null) {
            }
            int i = zzq.zzb[zzaVarZzb.ordinal()];
            if (i == 1) {
                return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == -1);
            }
            if (i == 2) {
                return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == 1);
            }
            if (i == 3) {
                if (d == 0.0d) {
                    return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == 0);
                }
                if (bigDecimal.compareTo(bigDecimal2.subtract(new BigDecimal(d).multiply(new BigDecimal(2)))) == 1 && bigDecimal.compareTo(bigDecimal2.add(new BigDecimal(d).multiply(new BigDecimal(2)))) == -1) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
            if (i == 4) {
                if (bigDecimal.compareTo(bigDecimal3) != -1 && bigDecimal.compareTo(bigDecimal4) != 1) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }
        return null;
    }
}
