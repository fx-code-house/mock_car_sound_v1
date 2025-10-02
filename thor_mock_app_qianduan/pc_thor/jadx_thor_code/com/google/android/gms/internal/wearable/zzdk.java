package com.google.android.gms.internal.wearable;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzdk {
    private static final Class<?> zza;
    private static final zzdw<?, ?> zzb;
    private static final zzdw<?, ?> zzc;
    private static final zzdw<?, ?> zzd;

    static {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            cls = null;
        }
        zza = cls;
        zzb = zzab(false);
        zzc = zzab(true);
        zzd = new zzdy();
    }

    public static zzdw<?, ?> zzA() {
        return zzb;
    }

    public static zzdw<?, ?> zzB() {
        return zzc;
    }

    public static zzdw<?, ?> zzC() {
        return zzd;
    }

    static boolean zzD(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static <T, FT extends zzbk<FT>> void zzE(zzbh<FT> zzbhVar, T t, T t2) {
        zzbhVar.zzb(t2);
        throw null;
    }

    static <T, UT, UB> void zzF(zzdw<UT, UB> zzdwVar, T t, T t2) {
        zzdwVar.zzc(t, zzdwVar.zzf(zzdwVar.zzd(t), zzdwVar.zzd(t2)));
    }

    static <UT, UB> UB zzG(int i, List<Integer> list, zzbw zzbwVar, UB ub, zzdw<UT, UB> zzdwVar) {
        if (zzbwVar == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int iIntValue = list.get(i3).intValue();
                if (zzbwVar.zza(iIntValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(iIntValue));
                    }
                    i2++;
                } else {
                    ub = (UB) zzH(i, iIntValue, ub, zzdwVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
                return ub;
            }
        } else {
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int iIntValue2 = it.next().intValue();
                if (!zzbwVar.zza(iIntValue2)) {
                    ub = (UB) zzH(i, iIntValue2, ub, zzdwVar);
                    it.remove();
                }
            }
        }
        return ub;
    }

    static <UT, UB> UB zzH(int i, int i2, UB ub, zzdw<UT, UB> zzdwVar) {
        if (ub == null) {
            ub = zzdwVar.zzb();
        }
        zzdwVar.zza(ub, i, i2);
        return ub;
    }

    static <T> void zzI(zzcs zzcsVar, T t, T t2, long j) {
        zzeg.zzo(t, j, zzcs.zzb(zzeg.zzn(t, j), zzeg.zzn(t2, j)));
    }

    public static void zzJ(int i, List<Double> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzB(i, list, z);
    }

    public static void zzK(int i, List<Float> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzA(i, list, z);
    }

    public static void zzL(int i, List<Long> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzx(i, list, z);
    }

    public static void zzM(int i, List<Long> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzy(i, list, z);
    }

    public static void zzN(int i, List<Long> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzK(i, list, z);
    }

    public static void zzO(int i, List<Long> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzz(i, list, z);
    }

    public static void zzP(int i, List<Long> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzI(i, list, z);
    }

    public static void zzQ(int i, List<Integer> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzv(i, list, z);
    }

    public static void zzR(int i, List<Integer> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzG(i, list, z);
    }

    public static void zzS(int i, List<Integer> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzJ(i, list, z);
    }

    public static void zzT(int i, List<Integer> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzw(i, list, z);
    }

    public static void zzU(int i, List<Integer> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzH(i, list, z);
    }

    public static void zzV(int i, List<Integer> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzC(i, list, z);
    }

    public static void zzW(int i, List<Boolean> list, zzbc zzbcVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzD(i, list, z);
    }

    public static void zzX(int i, List<String> list, zzbc zzbcVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzE(i, list);
    }

    public static void zzY(int i, List<zzau> list, zzbc zzbcVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzbcVar.zzF(i, list);
    }

    public static void zzZ(int i, List<?> list, zzbc zzbcVar, zzdi zzdiVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzbcVar.zzr(i, list.get(i2), zzdiVar);
        }
    }

    public static void zza(Class<?> cls) {
        Class<?> cls2;
        if (!zzbs.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzaa(int i, List<?> list, zzbc zzbcVar, zzdi zzdiVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzbcVar.zzs(i, list.get(i2), zzdiVar);
        }
    }

    private static zzdw<?, ?> zzab(boolean z) {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            cls = null;
        }
        if (cls == null) {
            return null;
        }
        try {
            return (zzdw) cls.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused2) {
            return null;
        }
    }

    static int zzb(List<Long> list) {
        int iZzx;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzcm) {
            zzcm zzcmVar = (zzcm) list;
            iZzx = 0;
            while (i < size) {
                iZzx += zzbb.zzx(zzcmVar.zzf(i));
                i++;
            }
        } else {
            iZzx = 0;
            while (i < size) {
                iZzx += zzbb.zzx(list.get(i).longValue());
                i++;
            }
        }
        return iZzx;
    }

    static int zzc(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzb(list) + (list.size() * zzbb.zzu(i));
    }

    static int zzd(List<Long> list) {
        int iZzx;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzcm) {
            zzcm zzcmVar = (zzcm) list;
            iZzx = 0;
            while (i < size) {
                iZzx += zzbb.zzx(zzcmVar.zzf(i));
                i++;
            }
        } else {
            iZzx = 0;
            while (i < size) {
                iZzx += zzbb.zzx(list.get(i).longValue());
                i++;
            }
        }
        return iZzx;
    }

    static int zze(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzbb.zzu(i));
    }

    static int zzf(List<Long> list) {
        int iZzx;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzcm) {
            zzcm zzcmVar = (zzcm) list;
            iZzx = 0;
            while (i < size) {
                long jZzf = zzcmVar.zzf(i);
                iZzx += zzbb.zzx((jZzf >> 63) ^ (jZzf + jZzf));
                i++;
            }
        } else {
            iZzx = 0;
            while (i < size) {
                long jLongValue = list.get(i).longValue();
                iZzx += zzbb.zzx((jLongValue >> 63) ^ (jLongValue + jLongValue));
                i++;
            }
        }
        return iZzx;
    }

    static int zzg(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzf(list) + (size * zzbb.zzu(i));
    }

    static int zzh(List<Integer> list) {
        int iZzv;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzbt) {
            zzbt zzbtVar = (zzbt) list;
            iZzv = 0;
            while (i < size) {
                iZzv += zzbb.zzv(zzbtVar.zzd(i));
                i++;
            }
        } else {
            iZzv = 0;
            while (i < size) {
                iZzv += zzbb.zzv(list.get(i).intValue());
                i++;
            }
        }
        return iZzv;
    }

    static int zzi(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzh(list) + (size * zzbb.zzu(i));
    }

    static int zzj(List<Integer> list) {
        int iZzv;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzbt) {
            zzbt zzbtVar = (zzbt) list;
            iZzv = 0;
            while (i < size) {
                iZzv += zzbb.zzv(zzbtVar.zzd(i));
                i++;
            }
        } else {
            iZzv = 0;
            while (i < size) {
                iZzv += zzbb.zzv(list.get(i).intValue());
                i++;
            }
        }
        return iZzv;
    }

    static int zzk(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzj(list) + (size * zzbb.zzu(i));
    }

    static int zzl(List<Integer> list) {
        int iZzw;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzbt) {
            zzbt zzbtVar = (zzbt) list;
            iZzw = 0;
            while (i < size) {
                iZzw += zzbb.zzw(zzbtVar.zzd(i));
                i++;
            }
        } else {
            iZzw = 0;
            while (i < size) {
                iZzw += zzbb.zzw(list.get(i).intValue());
                i++;
            }
        }
        return iZzw;
    }

    static int zzm(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzl(list) + (size * zzbb.zzu(i));
    }

    static int zzn(List<Integer> list) {
        int iZzw;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzbt) {
            zzbt zzbtVar = (zzbt) list;
            iZzw = 0;
            while (i < size) {
                int iZzd = zzbtVar.zzd(i);
                iZzw += zzbb.zzw((iZzd >> 31) ^ (iZzd + iZzd));
                i++;
            }
        } else {
            iZzw = 0;
            while (i < size) {
                int iIntValue = list.get(i).intValue();
                iZzw += zzbb.zzw((iIntValue >> 31) ^ (iIntValue + iIntValue));
                i++;
            }
        }
        return iZzw;
    }

    static int zzo(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzn(list) + (size * zzbb.zzu(i));
    }

    static int zzp(List<?> list) {
        return list.size() * 4;
    }

    static int zzq(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzbb.zzw(i << 3) + 4);
    }

    static int zzr(List<?> list) {
        return list.size() * 8;
    }

    static int zzs(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzbb.zzw(i << 3) + 8);
    }

    static int zzt(List<?> list) {
        return list.size();
    }

    static int zzu(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzbb.zzw(i << 3) + 1);
    }

    static int zzv(int i, List<?> list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int iZzu = zzbb.zzu(i) * size;
        if (list instanceof zzch) {
            zzch zzchVar = (zzch) list;
            while (i2 < size) {
                Object objZzg = zzchVar.zzg(i2);
                iZzu += objZzg instanceof zzau ? zzbb.zzA((zzau) objZzg) : zzbb.zzy((String) objZzg);
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                iZzu += obj instanceof zzau ? zzbb.zzA((zzau) obj) : zzbb.zzy((String) obj);
                i2++;
            }
        }
        return iZzu;
    }

    static int zzw(int i, Object obj, zzdi zzdiVar) {
        if (!(obj instanceof zzcf)) {
            return zzbb.zzw(i << 3) + zzbb.zzB((zzcx) obj, zzdiVar);
        }
        int iZzw = zzbb.zzw(i << 3);
        int iZza = ((zzcf) obj).zza();
        return iZzw + zzbb.zzw(iZza) + iZza;
    }

    static int zzx(int i, List<?> list, zzdi zzdiVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzu = zzbb.zzu(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            iZzu += obj instanceof zzcf ? zzbb.zzz((zzcf) obj) : zzbb.zzB((zzcx) obj, zzdiVar);
        }
        return iZzu;
    }

    static int zzy(int i, List<zzau> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzu = size * zzbb.zzu(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            iZzu += zzbb.zzA(list.get(i2));
        }
        return iZzu;
    }

    static int zzz(int i, List<zzcx> list, zzdi zzdiVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzE = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzE += zzbb.zzE(i, list.get(i2), zzdiVar);
        }
        return iZzE;
    }
}
