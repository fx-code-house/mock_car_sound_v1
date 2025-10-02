package com.google.android.gms.internal.vision;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzke {
    private static final Class<?> zzabn = zziq();
    private static final zzku<?, ?> zzabo = zzn(false);
    private static final zzku<?, ?> zzabp = zzn(true);
    private static final zzku<?, ?> zzabq = new zzkw();

    public static void zzg(Class<?> cls) {
        Class<?> cls2;
        if (!zzid.class.isAssignableFrom(cls) && (cls2 = zzabn) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Double> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzg(i, list, z);
    }

    public static void zzb(int i, List<Float> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzf(i, list, z);
    }

    public static void zzc(int i, List<Long> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzc(i, list, z);
    }

    public static void zzd(int i, List<Long> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzd(i, list, z);
    }

    public static void zze(int i, List<Long> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzn(i, list, z);
    }

    public static void zzf(int i, List<Long> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zze(i, list, z);
    }

    public static void zzg(int i, List<Long> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzl(i, list, z);
    }

    public static void zzh(int i, List<Integer> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zza(i, list, z);
    }

    public static void zzi(int i, List<Integer> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzj(i, list, z);
    }

    public static void zzj(int i, List<Integer> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzm(i, list, z);
    }

    public static void zzk(int i, List<Integer> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzb(i, list, z);
    }

    public static void zzl(int i, List<Integer> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzk(i, list, z);
    }

    public static void zzm(int i, List<Integer> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzh(i, list, z);
    }

    public static void zzn(int i, List<Boolean> list, zzlr zzlrVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzi(i, list, z);
    }

    public static void zza(int i, List<String> list, zzlr zzlrVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zza(i, list);
    }

    public static void zzb(int i, List<zzgs> list, zzlr zzlrVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzb(i, list);
    }

    public static void zza(int i, List<?> list, zzlr zzlrVar, zzkc zzkcVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zza(i, list, zzkcVar);
    }

    public static void zzb(int i, List<?> list, zzlr zzlrVar, zzkc zzkcVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzlrVar.zzb(i, list, zzkcVar);
    }

    static int zzq(List<Long> list) {
        int iZzv;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            iZzv = 0;
            while (i < size) {
                iZzv += zzhl.zzv(zzjbVar.getLong(i));
                i++;
            }
        } else {
            iZzv = 0;
            while (i < size) {
                iZzv += zzhl.zzv(list.get(i).longValue());
                i++;
            }
        }
        return iZzv;
    }

    static int zzo(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzq(list) + (list.size() * zzhl.zzbh(i));
    }

    static int zzr(List<Long> list) {
        int iZzw;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            iZzw = 0;
            while (i < size) {
                iZzw += zzhl.zzw(zzjbVar.getLong(i));
                i++;
            }
        } else {
            iZzw = 0;
            while (i < size) {
                iZzw += zzhl.zzw(list.get(i).longValue());
                i++;
            }
        }
        return iZzw;
    }

    static int zzp(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzr(list) + (size * zzhl.zzbh(i));
    }

    static int zzs(List<Long> list) {
        int iZzx;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            iZzx = 0;
            while (i < size) {
                iZzx += zzhl.zzx(zzjbVar.getLong(i));
                i++;
            }
        } else {
            iZzx = 0;
            while (i < size) {
                iZzx += zzhl.zzx(list.get(i).longValue());
                i++;
            }
        }
        return iZzx;
    }

    static int zzq(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzs(list) + (size * zzhl.zzbh(i));
    }

    static int zzt(List<Integer> list) {
        int iZzbn;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            iZzbn = 0;
            while (i < size) {
                iZzbn += zzhl.zzbn(zzifVar.getInt(i));
                i++;
            }
        } else {
            iZzbn = 0;
            while (i < size) {
                iZzbn += zzhl.zzbn(list.get(i).intValue());
                i++;
            }
        }
        return iZzbn;
    }

    static int zzr(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzt(list) + (size * zzhl.zzbh(i));
    }

    static int zzu(List<Integer> list) {
        int iZzbi;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            iZzbi = 0;
            while (i < size) {
                iZzbi += zzhl.zzbi(zzifVar.getInt(i));
                i++;
            }
        } else {
            iZzbi = 0;
            while (i < size) {
                iZzbi += zzhl.zzbi(list.get(i).intValue());
                i++;
            }
        }
        return iZzbi;
    }

    static int zzs(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzu(list) + (size * zzhl.zzbh(i));
    }

    static int zzv(List<Integer> list) {
        int iZzbj;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            iZzbj = 0;
            while (i < size) {
                iZzbj += zzhl.zzbj(zzifVar.getInt(i));
                i++;
            }
        } else {
            iZzbj = 0;
            while (i < size) {
                iZzbj += zzhl.zzbj(list.get(i).intValue());
                i++;
            }
        }
        return iZzbj;
    }

    static int zzt(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzv(list) + (size * zzhl.zzbh(i));
    }

    static int zzw(List<Integer> list) {
        int iZzbk;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            iZzbk = 0;
            while (i < size) {
                iZzbk += zzhl.zzbk(zzifVar.getInt(i));
                i++;
            }
        } else {
            iZzbk = 0;
            while (i < size) {
                iZzbk += zzhl.zzbk(list.get(i).intValue());
                i++;
            }
        }
        return iZzbk;
    }

    static int zzu(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzw(list) + (size * zzhl.zzbh(i));
    }

    static int zzx(List<?> list) {
        return list.size() << 2;
    }

    static int zzv(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzhl.zzq(i, 0);
    }

    static int zzy(List<?> list) {
        return list.size() << 3;
    }

    static int zzw(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzhl.zzg(i, 0L);
    }

    static int zzz(List<?> list) {
        return list.size();
    }

    static int zzx(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzhl.zzb(i, true);
    }

    static int zzc(int i, List<?> list) {
        int iZzx;
        int iZzx2;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int iZzbh = zzhl.zzbh(i) * size;
        if (list instanceof zziu) {
            zziu zziuVar = (zziu) list;
            while (i2 < size) {
                Object objZzbt = zziuVar.zzbt(i2);
                if (objZzbt instanceof zzgs) {
                    iZzx2 = zzhl.zzb((zzgs) objZzbt);
                } else {
                    iZzx2 = zzhl.zzx((String) objZzbt);
                }
                iZzbh += iZzx2;
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzgs) {
                    iZzx = zzhl.zzb((zzgs) obj);
                } else {
                    iZzx = zzhl.zzx((String) obj);
                }
                iZzbh += iZzx;
                i2++;
            }
        }
        return iZzbh;
    }

    static int zzc(int i, Object obj, zzkc zzkcVar) {
        if (obj instanceof zzis) {
            return zzhl.zza(i, (zzis) obj);
        }
        return zzhl.zzb(i, (zzjn) obj, zzkcVar);
    }

    static int zzc(int i, List<?> list, zzkc zzkcVar) {
        int iZza;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzbh = zzhl.zzbh(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzis) {
                iZza = zzhl.zza((zzis) obj);
            } else {
                iZza = zzhl.zza((zzjn) obj, zzkcVar);
            }
            iZzbh += iZza;
        }
        return iZzbh;
    }

    static int zzd(int i, List<zzgs> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzbh = size * zzhl.zzbh(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            iZzbh += zzhl.zzb(list.get(i2));
        }
        return iZzbh;
    }

    static int zzd(int i, List<zzjn> list, zzkc zzkcVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzc = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzc += zzhl.zzc(i, list.get(i2), zzkcVar);
        }
        return iZzc;
    }

    public static zzku<?, ?> zzin() {
        return zzabo;
    }

    public static zzku<?, ?> zzio() {
        return zzabp;
    }

    public static zzku<?, ?> zzip() {
        return zzabq;
    }

    private static zzku<?, ?> zzn(boolean z) {
        try {
            Class<?> clsZzir = zzir();
            if (clsZzir == null) {
                return null;
            }
            return (zzku) clsZzir.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zziq() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzir() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean zze(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static <T> void zza(zzjg zzjgVar, T t, T t2, long j) {
        zzla.zza(t, j, zzjgVar.zzc(zzla.zzp(t, j), zzla.zzp(t2, j)));
    }

    static <T, FT extends zzhv<FT>> void zza(zzhq<FT> zzhqVar, T t, T t2) {
        zzht<T> zzhtVarZzh = zzhqVar.zzh(t2);
        if (zzhtVarZzh.zzux.isEmpty()) {
            return;
        }
        zzhqVar.zzi(t).zza(zzhtVarZzh);
    }

    static <T, UT, UB> void zza(zzku<UT, UB> zzkuVar, T t, T t2) {
        zzkuVar.zzf(t, zzkuVar.zzh(zzkuVar.zzy(t), zzkuVar.zzy(t2)));
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzij zzijVar, UB ub, zzku<UT, UB> zzkuVar) {
        if (zzijVar == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int iIntValue = list.get(i3).intValue();
                if (zzijVar.zzg(iIntValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(iIntValue));
                    }
                    i2++;
                } else {
                    ub = (UB) zza(i, iIntValue, ub, zzkuVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int iIntValue2 = it.next().intValue();
                if (!zzijVar.zzg(iIntValue2)) {
                    ub = (UB) zza(i, iIntValue2, ub, zzkuVar);
                    it.remove();
                }
            }
        }
        return ub;
    }

    static <UT, UB> UB zza(int i, int i2, UB ub, zzku<UT, UB> zzkuVar) {
        if (ub == null) {
            ub = zzkuVar.zzja();
        }
        zzkuVar.zza((zzku<UT, UB>) ub, i, i2);
        return ub;
    }
}
