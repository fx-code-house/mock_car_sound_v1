package com.google.android.gms.internal.vision;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzla {
    private static final Logger logger = Logger.getLogger(zzla.class.getName());
    private static final Unsafe zzaap;
    private static final boolean zzacl;
    private static final boolean zzacm;
    private static final zzd zzacn;
    private static final boolean zzaco;
    private static final long zzacp;
    private static final long zzacq;
    private static final long zzacr;
    private static final long zzacs;
    private static final long zzact;
    private static final long zzacu;
    private static final long zzacv;
    private static final long zzacw;
    private static final long zzacx;
    private static final long zzacy;
    private static final long zzacz;
    private static final long zzada;
    private static final long zzadb;
    private static final long zzadc;
    private static final int zzadd;
    static final boolean zzade;
    private static final Class<?> zzti;
    private static final boolean zzun;

    private zzla() {
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final byte zzy(Object obj, long j) {
            if (zzla.zzade) {
                return zzla.zzq(obj, j);
            }
            return zzla.zzr(obj, j);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zze(Object obj, long j, byte b) {
            if (zzla.zzade) {
                zzla.zza(obj, j, b);
            } else {
                zzla.zzb(obj, j, b);
            }
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final boolean zzm(Object obj, long j) {
            if (zzla.zzade) {
                return zzla.zzs(obj, j);
            }
            return zzla.zzt(obj, j);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zza(Object obj, long j, boolean z) {
            if (zzla.zzade) {
                zzla.zzb(obj, j, z);
            } else {
                zzla.zzc(obj, j, z);
            }
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final byte zzy(Object obj, long j) {
            return this.zzadh.getByte(obj, j);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zze(Object obj, long j, byte b) {
            this.zzadh.putByte(obj, j, b);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final boolean zzm(Object obj, long j) {
            return this.zzadh.getBoolean(obj, j);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zza(Object obj, long j, boolean z) {
            this.zzadh.putBoolean(obj, j, z);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final float zzn(Object obj, long j) {
            return this.zzadh.getFloat(obj, j);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zza(Object obj, long j, float f) {
            this.zzadh.putFloat(obj, j, f);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final double zzo(Object obj, long j) {
            return this.zzadh.getDouble(obj, j);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zza(Object obj, long j, double d) {
            this.zzadh.putDouble(obj, j, d);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final byte zzy(Object obj, long j) {
            if (zzla.zzade) {
                return zzla.zzq(obj, j);
            }
            return zzla.zzr(obj, j);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zze(Object obj, long j, byte b) {
            if (zzla.zzade) {
                zzla.zza(obj, j, b);
            } else {
                zzla.zzb(obj, j, b);
            }
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final boolean zzm(Object obj, long j) {
            if (zzla.zzade) {
                return zzla.zzs(obj, j);
            }
            return zzla.zzt(obj, j);
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zza(Object obj, long j, boolean z) {
            if (zzla.zzade) {
                zzla.zzb(obj, j, z);
            } else {
                zzla.zzc(obj, j, z);
            }
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        @Override // com.google.android.gms.internal.vision.zzla.zzd
        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }
    }

    static boolean zzje() {
        return zzun;
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static abstract class zzd {
        Unsafe zzadh;

        zzd(Unsafe unsafe) {
            this.zzadh = unsafe;
        }

        public abstract void zza(Object obj, long j, double d);

        public abstract void zza(Object obj, long j, float f);

        public abstract void zza(Object obj, long j, boolean z);

        public abstract void zze(Object obj, long j, byte b);

        public abstract boolean zzm(Object obj, long j);

        public abstract float zzn(Object obj, long j);

        public abstract double zzo(Object obj, long j);

        public abstract byte zzy(Object obj, long j);

        public final int zzk(Object obj, long j) {
            return this.zzadh.getInt(obj, j);
        }

        public final void zzb(Object obj, long j, int i) {
            this.zzadh.putInt(obj, j, i);
        }

        public final long zzl(Object obj, long j) {
            return this.zzadh.getLong(obj, j);
        }

        public final void zza(Object obj, long j, long j2) {
            this.zzadh.putLong(obj, j, j2);
        }
    }

    static boolean zzjf() {
        return zzaco;
    }

    static <T> T zzh(Class<T> cls) {
        try {
            return (T) zzaap.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static int zzi(Class<?> cls) {
        if (zzun) {
            return zzacn.zzadh.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzj(Class<?> cls) {
        if (zzun) {
            return zzacn.zzadh.arrayIndexScale(cls);
        }
        return -1;
    }

    static int zzk(Object obj, long j) {
        return zzacn.zzk(obj, j);
    }

    static void zzb(Object obj, long j, int i) {
        zzacn.zzb(obj, j, i);
    }

    static long zzl(Object obj, long j) {
        return zzacn.zzl(obj, j);
    }

    static void zza(Object obj, long j, long j2) {
        zzacn.zza(obj, j, j2);
    }

    static boolean zzm(Object obj, long j) {
        return zzacn.zzm(obj, j);
    }

    static void zza(Object obj, long j, boolean z) {
        zzacn.zza(obj, j, z);
    }

    static float zzn(Object obj, long j) {
        return zzacn.zzn(obj, j);
    }

    static void zza(Object obj, long j, float f) {
        zzacn.zza(obj, j, f);
    }

    static double zzo(Object obj, long j) {
        return zzacn.zzo(obj, j);
    }

    static void zza(Object obj, long j, double d) {
        zzacn.zza(obj, j, d);
    }

    static Object zzp(Object obj, long j) {
        return zzacn.zzadh.getObject(obj, j);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzacn.zzadh.putObject(obj, j, obj2);
    }

    static byte zza(byte[] bArr, long j) {
        return zzacn.zzy(bArr, zzacp + j);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzacn.zze(bArr, zzacp + j, b);
    }

    static Unsafe zzjg() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzlc());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzjh() {
        Unsafe unsafe = zzaap;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            cls.getMethod("getInt", Object.class, Long.TYPE);
            cls.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            cls.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            cls.getMethod("getObject", Object.class, Long.TYPE);
            cls.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (zzgl.zzel()) {
                return true;
            }
            cls.getMethod("getByte", Object.class, Long.TYPE);
            cls.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, Long.TYPE);
            cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, Long.TYPE);
            cls.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            cls.getMethod("getDouble", Object.class, Long.TYPE);
            cls.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String strValueOf = String.valueOf(th);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", new StringBuilder(String.valueOf(strValueOf).length() + 71).append("platform method missing - proto runtime falling back to safer methods: ").append(strValueOf).toString());
            return false;
        }
    }

    private static boolean zzji() {
        Unsafe unsafe = zzaap;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            if (zzjj() == null) {
                return false;
            }
            if (zzgl.zzel()) {
                return true;
            }
            cls.getMethod("getByte", Long.TYPE);
            cls.getMethod("putByte", Long.TYPE, Byte.TYPE);
            cls.getMethod("getInt", Long.TYPE);
            cls.getMethod("putInt", Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Long.TYPE);
            cls.getMethod("putLong", Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String strValueOf = String.valueOf(th);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", new StringBuilder(String.valueOf(strValueOf).length() + 71).append("platform method missing - proto runtime falling back to safer methods: ").append(strValueOf).toString());
            return false;
        }
    }

    private static boolean zzk(Class<?> cls) {
        if (!zzgl.zzel()) {
            return false;
        }
        try {
            Class<?> cls2 = zzti;
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

    private static Field zzjj() {
        Field fieldZzb;
        if (zzgl.zzel() && (fieldZzb = zzb(Buffer.class, "effectiveDirectAddress")) != null) {
            return fieldZzb;
        }
        Field fieldZzb2 = zzb(Buffer.class, "address");
        if (fieldZzb2 == null || fieldZzb2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZzb2;
    }

    private static Field zzb(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte zzq(Object obj, long j) {
        return (byte) (zzk(obj, (-4) & j) >>> ((int) (((~j) & 3) << 3)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte zzr(Object obj, long j) {
        return (byte) (zzk(obj, (-4) & j) >>> ((int) ((j & 3) << 3)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zza(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int iZzk = zzk(obj, j2);
        int i = ((~((int) j)) & 3) << 3;
        zzb(obj, j2, ((255 & b) << i) | (iZzk & (~(255 << i))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzb(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int i = (((int) j) & 3) << 3;
        zzb(obj, j2, ((255 & b) << i) | (zzk(obj, j2) & (~(255 << i))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzs(Object obj, long j) {
        return zzq(obj, j) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzt(Object obj, long j) {
        return zzr(obj, j) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzb(Object obj, long j, boolean z) {
        zza(obj, j, z ? (byte) 1 : (byte) 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzc(Object obj, long j, boolean z) {
        zzb(obj, j, z ? (byte) 1 : (byte) 0);
    }

    static {
        Unsafe unsafeZzjg = zzjg();
        zzaap = unsafeZzjg;
        zzti = zzgl.zzem();
        boolean zZzk = zzk(Long.TYPE);
        zzacl = zZzk;
        boolean zZzk2 = zzk(Integer.TYPE);
        zzacm = zZzk2;
        zzd zzbVar = null;
        if (unsafeZzjg != null) {
            if (!zzgl.zzel()) {
                zzbVar = new zzb(unsafeZzjg);
            } else if (zZzk) {
                zzbVar = new zzc(unsafeZzjg);
            } else if (zZzk2) {
                zzbVar = new zza(unsafeZzjg);
            }
        }
        zzacn = zzbVar;
        zzaco = zzji();
        zzun = zzjh();
        long jZzi = zzi(byte[].class);
        zzacp = jZzi;
        zzacq = zzi(boolean[].class);
        zzacr = zzj(boolean[].class);
        zzacs = zzi(int[].class);
        zzact = zzj(int[].class);
        zzacu = zzi(long[].class);
        zzacv = zzj(long[].class);
        zzacw = zzi(float[].class);
        zzacx = zzj(float[].class);
        zzacy = zzi(double[].class);
        zzacz = zzj(double[].class);
        zzada = zzi(Object[].class);
        zzadb = zzj(Object[].class);
        Field fieldZzjj = zzjj();
        zzadc = (fieldZzjj == null || zzbVar == null) ? -1L : zzbVar.zzadh.objectFieldOffset(fieldZzjj);
        zzadd = (int) (jZzi & 7);
        zzade = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }
}
