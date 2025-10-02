package com.google.android.gms.internal.wearable;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzeg {
    static final long zza;
    static final boolean zzb;
    private static final Unsafe zzc;
    private static final Class<?> zzd;
    private static final boolean zze;
    private static final boolean zzf;
    private static final zzef zzg;
    private static final boolean zzh;
    private static final boolean zzi;

    /* JADX WARN: Removed duplicated region for block: B:11:0x003a  */
    static {
        /*
            Method dump skipped, instructions count: 350
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.wearable.zzeg.<clinit>():void");
    }

    private zzeg() {
    }

    private static int zzA(Class<?> cls) {
        if (zzi) {
            return zzg.zzj(cls);
        }
        return -1;
    }

    private static Field zzB() {
        int i = zzah.zza;
        Field fieldZzC = zzC(Buffer.class, "effectiveDirectAddress");
        if (fieldZzC != null) {
            return fieldZzC;
        }
        Field fieldZzC2 = zzC(Buffer.class, "address");
        if (fieldZzC2 == null || fieldZzC2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZzC2;
    }

    private static Field zzC(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzD(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        zzef zzefVar = zzg;
        int iZzk = zzefVar.zzk(obj, j2);
        int i = ((~((int) j)) & 3) << 3;
        zzefVar.zzl(obj, j2, ((255 & b) << i) | (iZzk & (~(255 << i))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzE(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        zzef zzefVar = zzg;
        int i = (((int) j) & 3) << 3;
        zzefVar.zzl(obj, j2, ((255 & b) << i) | (zzefVar.zzk(obj, j2) & (~(255 << i))));
    }

    static boolean zza() {
        return zzi;
    }

    static boolean zzb() {
        return zzh;
    }

    static <T> T zzc(Class<T> cls) {
        try {
            return (T) zzc.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    static int zzd(Object obj, long j) {
        return zzg.zzk(obj, j);
    }

    static void zze(Object obj, long j, int i) {
        zzg.zzl(obj, j, i);
    }

    static long zzf(Object obj, long j) {
        return zzg.zzm(obj, j);
    }

    static void zzg(Object obj, long j, long j2) {
        zzg.zzn(obj, j, j2);
    }

    static boolean zzh(Object obj, long j) {
        return zzg.zzb(obj, j);
    }

    static void zzi(Object obj, long j, boolean z) {
        zzg.zzc(obj, j, z);
    }

    static float zzj(Object obj, long j) {
        return zzg.zzd(obj, j);
    }

    static void zzk(Object obj, long j, float f) {
        zzg.zze(obj, j, f);
    }

    static double zzl(Object obj, long j) {
        return zzg.zzf(obj, j);
    }

    static void zzm(Object obj, long j, double d) {
        zzg.zzg(obj, j, d);
    }

    static Object zzn(Object obj, long j) {
        return zzg.zzo(obj, j);
    }

    static void zzo(Object obj, long j, Object obj2) {
        zzg.zzp(obj, j, obj2);
    }

    static void zzp(byte[] bArr, long j, byte b) {
        zzg.zza(bArr, zza + j, b);
    }

    static Unsafe zzq() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzec());
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean zzr(Class<?> cls) {
        int i = zzah.zza;
        try {
            Class<?> cls2 = zzd;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    static /* synthetic */ void zzs(Throwable th) {
        Logger logger = Logger.getLogger(zzeg.class.getName());
        Level level = Level.WARNING;
        String strValueOf = String.valueOf(th);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 71);
        sb.append("platform method missing - proto runtime falling back to safer methods: ");
        sb.append(strValueOf);
        logger.logp(level, "com.google.protobuf.UnsafeUtil", "logMissingMethod", sb.toString());
    }

    static /* synthetic */ boolean zzv(Object obj, long j) {
        return ((byte) ((zzg.zzk(obj, (-4) & j) >>> ((int) (((~j) & 3) << 3))) & 255)) != 0;
    }

    static /* synthetic */ boolean zzw(Object obj, long j) {
        return ((byte) ((zzg.zzk(obj, (-4) & j) >>> ((int) ((j & 3) << 3))) & 255)) != 0;
    }

    private static int zzz(Class<?> cls) {
        if (zzi) {
            return zzg.zzi(cls);
        }
        return -1;
    }
}
