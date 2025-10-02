package com.google.android.gms.internal.play_billing;

import java.io.IOException;
import java.util.List;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzdr {
    public static final /* synthetic */ int zza = 0;
    private static final Class zzb;
    private static final zzeg zzc;
    private static final zzeg zzd;

    static {
        Class<?> cls;
        Class<?> cls2;
        zzeg zzegVar = null;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            cls = null;
        }
        zzb = cls;
        try {
            cls2 = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused2) {
            cls2 = null;
        }
        if (cls2 != null) {
            try {
                zzegVar = (zzeg) cls2.getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Throwable unused3) {
            }
        }
        zzc = zzegVar;
        zzd = new zzei();
    }

    static Object zzA(Object obj, int i, int i2, Object obj2, zzeg zzegVar) {
        if (obj2 == null) {
            obj2 = zzegVar.zzc(obj);
        }
        zzegVar.zzf(obj2, i, i2);
        return obj2;
    }

    static void zzB(zzeg zzegVar, Object obj, Object obj2) {
        zzegVar.zzh(obj, zzegVar.zze(zzegVar.zzd(obj), zzegVar.zzd(obj2)));
    }

    public static void zzC(Class cls) {
        Class cls2;
        if (!zzcb.class.isAssignableFrom(cls) && (cls2 = zzb) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzD(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzc(i, list, z);
    }

    public static void zzE(int i, List list, zzey zzeyVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zze(i, list);
    }

    public static void zzF(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzg(i, list, z);
    }

    public static void zzG(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzj(i, list, z);
    }

    public static void zzH(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzl(i, list, z);
    }

    public static void zzI(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzn(i, list, z);
    }

    public static void zzJ(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzp(i, list, z);
    }

    public static void zzK(int i, List list, zzey zzeyVar, zzdp zzdpVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((zzbj) zzeyVar).zzq(i, list.get(i2), zzdpVar);
        }
    }

    public static void zzL(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzs(i, list, z);
    }

    public static void zzM(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzu(i, list, z);
    }

    public static void zzN(int i, List list, zzey zzeyVar, zzdp zzdpVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((zzbj) zzeyVar).zzv(i, list.get(i2), zzdpVar);
        }
    }

    public static void zzO(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzx(i, list, z);
    }

    public static void zzP(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzz(i, list, z);
    }

    public static void zzQ(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzB(i, list, z);
    }

    public static void zzR(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzD(i, list, z);
    }

    public static void zzS(int i, List list, zzey zzeyVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzG(i, list);
    }

    public static void zzT(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzI(i, list, z);
    }

    public static void zzU(int i, List list, zzey zzeyVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzeyVar.zzK(i, list, z);
    }

    static boolean zzV(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static int zza(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzbi.zzx(i << 3) + 1);
    }

    static int zzb(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzx = size * zzbi.zzx(i << 3);
        for (int i2 = 0; i2 < list.size(); i2++) {
            int iZzd = ((zzba) list.get(i2)).zzd();
            iZzx += zzbi.zzx(iZzd) + iZzd;
        }
        return iZzx;
    }

    static int zzc(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzbi.zzx(i << 3));
    }

    static int zzd(List list) {
        int iZzu;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzcc) {
            zzcc zzccVar = (zzcc) list;
            iZzu = 0;
            while (i < size) {
                iZzu += zzbi.zzu(zzccVar.zze(i));
                i++;
            }
        } else {
            iZzu = 0;
            while (i < size) {
                iZzu += zzbi.zzu(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return iZzu;
    }

    static int zze(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzbi.zzx(i << 3) + 4);
    }

    static int zzf(List list) {
        return list.size() * 4;
    }

    static int zzg(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzbi.zzx(i << 3) + 8);
    }

    static int zzh(List list) {
        return list.size() * 8;
    }

    static int zzi(int i, List list, zzdp zzdpVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzt = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzt += zzbi.zzt(i, (zzdf) list.get(i2), zzdpVar);
        }
        return iZzt;
    }

    static int zzj(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzk(list) + (size * zzbi.zzx(i << 3));
    }

    static int zzk(List list) {
        int iZzu;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzcc) {
            zzcc zzccVar = (zzcc) list;
            iZzu = 0;
            while (i < size) {
                iZzu += zzbi.zzu(zzccVar.zze(i));
                i++;
            }
        } else {
            iZzu = 0;
            while (i < size) {
                iZzu += zzbi.zzu(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return iZzu;
    }

    static int zzl(int i, List list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzm(list) + (list.size() * zzbi.zzx(i << 3));
    }

    static int zzm(List list) {
        int iZzy;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzcu) {
            zzcu zzcuVar = (zzcu) list;
            iZzy = 0;
            while (i < size) {
                iZzy += zzbi.zzy(zzcuVar.zze(i));
                i++;
            }
        } else {
            iZzy = 0;
            while (i < size) {
                iZzy += zzbi.zzy(((Long) list.get(i)).longValue());
                i++;
            }
        }
        return iZzy;
    }

    static int zzn(int i, Object obj, zzdp zzdpVar) {
        if (!(obj instanceof zzcl)) {
            return zzbi.zzx(i << 3) + zzbi.zzv((zzdf) obj, zzdpVar);
        }
        int i2 = zzbi.zzb;
        int iZza = ((zzcl) obj).zza();
        return zzbi.zzx(i << 3) + zzbi.zzx(iZza) + iZza;
    }

    static int zzo(int i, List list, zzdp zzdpVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzx = zzbi.zzx(i << 3) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzcl) {
                int iZza = ((zzcl) obj).zza();
                iZzx += zzbi.zzx(iZza) + iZza;
            } else {
                iZzx += zzbi.zzv((zzdf) obj, zzdpVar);
            }
        }
        return iZzx;
    }

    static int zzp(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzq(list) + (size * zzbi.zzx(i << 3));
    }

    static int zzq(List list) {
        int iZzx;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzcc) {
            zzcc zzccVar = (zzcc) list;
            iZzx = 0;
            while (i < size) {
                int iZze = zzccVar.zze(i);
                iZzx += zzbi.zzx((iZze >> 31) ^ (iZze + iZze));
                i++;
            }
        } else {
            iZzx = 0;
            while (i < size) {
                int iIntValue = ((Integer) list.get(i)).intValue();
                iZzx += zzbi.zzx((iIntValue >> 31) ^ (iIntValue + iIntValue));
                i++;
            }
        }
        return iZzx;
    }

    static int zzr(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzs(list) + (size * zzbi.zzx(i << 3));
    }

    static int zzs(List list) {
        int iZzy;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzcu) {
            zzcu zzcuVar = (zzcu) list;
            iZzy = 0;
            while (i < size) {
                long jZze = zzcuVar.zze(i);
                iZzy += zzbi.zzy((jZze >> 63) ^ (jZze + jZze));
                i++;
            }
        } else {
            iZzy = 0;
            while (i < size) {
                long jLongValue = ((Long) list.get(i)).longValue();
                iZzy += zzbi.zzy((jLongValue >> 63) ^ (jLongValue + jLongValue));
                i++;
            }
        }
        return iZzy;
    }

    static int zzt(int i, List list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        boolean z = list instanceof zzcn;
        int iZzx = zzbi.zzx(i << 3) * size;
        if (z) {
            zzcn zzcnVar = (zzcn) list;
            while (i2 < size) {
                Object objZzf = zzcnVar.zzf(i2);
                if (objZzf instanceof zzba) {
                    int iZzd = ((zzba) objZzf).zzd();
                    iZzx += zzbi.zzx(iZzd) + iZzd;
                } else {
                    iZzx += zzbi.zzw((String) objZzf);
                }
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzba) {
                    int iZzd2 = ((zzba) obj).zzd();
                    iZzx += zzbi.zzx(iZzd2) + iZzd2;
                } else {
                    iZzx += zzbi.zzw((String) obj);
                }
                i2++;
            }
        }
        return iZzx;
    }

    static int zzu(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzv(list) + (size * zzbi.zzx(i << 3));
    }

    static int zzv(List list) {
        int iZzx;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzcc) {
            zzcc zzccVar = (zzcc) list;
            iZzx = 0;
            while (i < size) {
                iZzx += zzbi.zzx(zzccVar.zze(i));
                i++;
            }
        } else {
            iZzx = 0;
            while (i < size) {
                iZzx += zzbi.zzx(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return iZzx;
    }

    static int zzw(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzx(list) + (size * zzbi.zzx(i << 3));
    }

    static int zzx(List list) {
        int iZzy;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzcu) {
            zzcu zzcuVar = (zzcu) list;
            iZzy = 0;
            while (i < size) {
                iZzy += zzbi.zzy(zzcuVar.zze(i));
                i++;
            }
        } else {
            iZzy = 0;
            while (i < size) {
                iZzy += zzbi.zzy(((Long) list.get(i)).longValue());
                i++;
            }
        }
        return iZzy;
    }

    public static zzeg zzy() {
        return zzc;
    }

    public static zzeg zzz() {
        return zzd;
    }
}
