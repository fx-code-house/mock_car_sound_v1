package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.util.List;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzfw {
    private static final Class<?> zznn = zzdb();
    private static final zzgm<?, ?> zzno = zzh(false);
    private static final zzgm<?, ?> zznp = zzh(true);
    private static final zzgm<?, ?> zznq = new zzgo();

    public static void zzf(Class<?> cls) {
        Class<?> cls2;
        if (!zzdx.class.isAssignableFrom(cls) && (cls2 = zznn) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Double> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzg(i, list, z);
    }

    public static void zzb(int i, List<Float> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzf(i, list, z);
    }

    public static void zzc(int i, List<Long> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzc(i, list, z);
    }

    public static void zzd(int i, List<Long> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzd(i, list, z);
    }

    public static void zze(int i, List<Long> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzn(i, list, z);
    }

    public static void zzf(int i, List<Long> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zze(i, list, z);
    }

    public static void zzg(int i, List<Long> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzl(i, list, z);
    }

    public static void zzh(int i, List<Integer> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zza(i, list, z);
    }

    public static void zzi(int i, List<Integer> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzj(i, list, z);
    }

    public static void zzj(int i, List<Integer> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzm(i, list, z);
    }

    public static void zzk(int i, List<Integer> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzb(i, list, z);
    }

    public static void zzl(int i, List<Integer> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzk(i, list, z);
    }

    public static void zzm(int i, List<Integer> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzh(i, list, z);
    }

    public static void zzn(int i, List<Boolean> list, zzhg zzhgVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzi(i, list, z);
    }

    public static void zza(int i, List<String> list, zzhg zzhgVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zza(i, list);
    }

    public static void zzb(int i, List<zzct> list, zzhg zzhgVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzb(i, list);
    }

    public static void zza(int i, List<?> list, zzhg zzhgVar, zzfu zzfuVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zza(i, list, zzfuVar);
    }

    public static void zzb(int i, List<?> list, zzhg zzhgVar, zzfu zzfuVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzb(i, list, zzfuVar);
    }

    static int zza(List<Long> list) {
        int iZze;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzev) {
            zzev zzevVar = (zzev) list;
            iZze = 0;
            while (i < size) {
                iZze += zzdk.zze(zzevVar.getLong(i));
                i++;
            }
        } else {
            iZze = 0;
            while (i < size) {
                iZze += zzdk.zze(list.get(i).longValue());
                i++;
            }
        }
        return iZze;
    }

    static int zzo(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zza(list) + (list.size() * zzdk.zzs(i));
    }

    static int zzb(List<Long> list) {
        int iZzf;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzev) {
            zzev zzevVar = (zzev) list;
            iZzf = 0;
            while (i < size) {
                iZzf += zzdk.zzf(zzevVar.getLong(i));
                i++;
            }
        } else {
            iZzf = 0;
            while (i < size) {
                iZzf += zzdk.zzf(list.get(i).longValue());
                i++;
            }
        }
        return iZzf;
    }

    static int zzp(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzb(list) + (size * zzdk.zzs(i));
    }

    static int zzc(List<Long> list) {
        int iZzg;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzev) {
            zzev zzevVar = (zzev) list;
            iZzg = 0;
            while (i < size) {
                iZzg += zzdk.zzg(zzevVar.getLong(i));
                i++;
            }
        } else {
            iZzg = 0;
            while (i < size) {
                iZzg += zzdk.zzg(list.get(i).longValue());
                i++;
            }
        }
        return iZzg;
    }

    static int zzq(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzc(list) + (size * zzdk.zzs(i));
    }

    static int zzd(List<Integer> list) {
        int iZzy;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzdz) {
            zzdz zzdzVar = (zzdz) list;
            iZzy = 0;
            while (i < size) {
                iZzy += zzdk.zzy(zzdzVar.getInt(i));
                i++;
            }
        } else {
            iZzy = 0;
            while (i < size) {
                iZzy += zzdk.zzy(list.get(i).intValue());
                i++;
            }
        }
        return iZzy;
    }

    static int zzr(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzdk.zzs(i));
    }

    static int zze(List<Integer> list) {
        int iZzt;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzdz) {
            zzdz zzdzVar = (zzdz) list;
            iZzt = 0;
            while (i < size) {
                iZzt += zzdk.zzt(zzdzVar.getInt(i));
                i++;
            }
        } else {
            iZzt = 0;
            while (i < size) {
                iZzt += zzdk.zzt(list.get(i).intValue());
                i++;
            }
        }
        return iZzt;
    }

    static int zzs(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zze(list) + (size * zzdk.zzs(i));
    }

    static int zzf(List<Integer> list) {
        int iZzu;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzdz) {
            zzdz zzdzVar = (zzdz) list;
            iZzu = 0;
            while (i < size) {
                iZzu += zzdk.zzu(zzdzVar.getInt(i));
                i++;
            }
        } else {
            iZzu = 0;
            while (i < size) {
                iZzu += zzdk.zzu(list.get(i).intValue());
                i++;
            }
        }
        return iZzu;
    }

    static int zzt(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzf(list) + (size * zzdk.zzs(i));
    }

    static int zzg(List<Integer> list) {
        int iZzv;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzdz) {
            zzdz zzdzVar = (zzdz) list;
            iZzv = 0;
            while (i < size) {
                iZzv += zzdk.zzv(zzdzVar.getInt(i));
                i++;
            }
        } else {
            iZzv = 0;
            while (i < size) {
                iZzv += zzdk.zzv(list.get(i).intValue());
                i++;
            }
        }
        return iZzv;
    }

    static int zzu(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzg(list) + (size * zzdk.zzs(i));
    }

    static int zzh(List<?> list) {
        return list.size() << 2;
    }

    static int zzv(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzdk.zzj(i, 0);
    }

    static int zzi(List<?> list) {
        return list.size() << 3;
    }

    static int zzw(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzdk.zzg(i, 0L);
    }

    static int zzj(List<?> list) {
        return list.size();
    }

    static int zzx(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzdk.zzb(i, true);
    }

    static int zzc(int i, List<?> list) {
        int iZzr;
        int iZzr2;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int iZzs = zzdk.zzs(i) * size;
        if (list instanceof zzeo) {
            zzeo zzeoVar = (zzeo) list;
            while (i2 < size) {
                Object objZzad = zzeoVar.zzad(i2);
                if (objZzad instanceof zzct) {
                    iZzr2 = zzdk.zzb((zzct) objZzad);
                } else {
                    iZzr2 = zzdk.zzr((String) objZzad);
                }
                iZzs += iZzr2;
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzct) {
                    iZzr = zzdk.zzb((zzct) obj);
                } else {
                    iZzr = zzdk.zzr((String) obj);
                }
                iZzs += iZzr;
                i2++;
            }
        }
        return iZzs;
    }

    static int zzc(int i, Object obj, zzfu zzfuVar) {
        if (obj instanceof zzem) {
            return zzdk.zza(i, (zzem) obj);
        }
        return zzdk.zzb(i, (zzfh) obj, zzfuVar);
    }

    static int zzc(int i, List<?> list, zzfu zzfuVar) {
        int iZza;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzs = zzdk.zzs(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzem) {
                iZza = zzdk.zza((zzem) obj);
            } else {
                iZza = zzdk.zza((zzfh) obj, zzfuVar);
            }
            iZzs += iZza;
        }
        return iZzs;
    }

    static int zzd(int i, List<zzct> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzs = size * zzdk.zzs(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            iZzs += zzdk.zzb(list.get(i2));
        }
        return iZzs;
    }

    static int zzd(int i, List<zzfh> list, zzfu zzfuVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzc = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzc += zzdk.zzc(i, list.get(i2), zzfuVar);
        }
        return iZzc;
    }

    public static zzgm<?, ?> zzcy() {
        return zzno;
    }

    public static zzgm<?, ?> zzcz() {
        return zznp;
    }

    public static zzgm<?, ?> zzda() {
        return zznq;
    }

    private static zzgm<?, ?> zzh(boolean z) {
        try {
            Class<?> clsZzdc = zzdc();
            if (clsZzdc == null) {
                return null;
            }
            return (zzgm) clsZzdc.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzdb() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzdc() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean zzd(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static <T> void zza(zzfa zzfaVar, T t, T t2, long j) {
        zzgs.zza(t, j, zzfaVar.zzb(zzgs.zzo(t, j), zzgs.zzo(t2, j)));
    }

    static <T, FT extends zzdu<FT>> void zza(zzdn<FT> zzdnVar, T t, T t2) {
        zzds<T> zzdsVarZzd = zzdnVar.zzd(t2);
        if (zzdsVarZzd.zzhk.isEmpty()) {
            return;
        }
        zzdnVar.zze(t).zza(zzdsVarZzd);
    }

    static <T, UT, UB> void zza(zzgm<UT, UB> zzgmVar, T t, T t2) {
        zzgmVar.zze(t, zzgmVar.zzf(zzgmVar.zzp(t), zzgmVar.zzp(t2)));
    }
}
