package com.google.android.gms.internal.icing;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzgs {
    private static final Logger logger = Logger.getLogger(zzgs.class.getName());
    private static final Class<?> zzgg;
    private static final boolean zzgx;
    private static final Unsafe zzms;
    private static final boolean zzol;
    private static final boolean zzom;
    private static final zzd zzon;
    private static final boolean zzoo;
    private static final long zzop;
    private static final long zzoq;
    private static final long zzor;
    private static final long zzos;
    private static final long zzot;
    private static final long zzou;
    private static final long zzov;
    private static final long zzow;
    private static final long zzox;
    private static final long zzoy;
    private static final long zzoz;
    private static final long zzpa;
    private static final long zzpb;
    private static final long zzpc;
    private static final int zzpd;
    static final boolean zzpe;

    private zzgs() {
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final byte zzx(Object obj, long j) {
            if (zzgs.zzpe) {
                return zzgs.zzp(obj, j);
            }
            return zzgs.zzq(obj, j);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zze(Object obj, long j, byte b) {
            if (zzgs.zzpe) {
                zzgs.zza(obj, j, b);
            } else {
                zzgs.zzb(obj, j, b);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final boolean zzl(Object obj, long j) {
            if (zzgs.zzpe) {
                return zzgs.zzr(obj, j);
            }
            return zzgs.zzs(obj, j);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j, boolean z) {
            if (zzgs.zzpe) {
                zzgs.zzb(obj, j, z);
            } else {
                zzgs.zzc(obj, j, z);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final float zzm(Object obj, long j) {
            return Float.intBitsToFloat(zzj(obj, j));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j, float f) {
            zza(obj, j, Float.floatToIntBits(f));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final double zzn(Object obj, long j) {
            return Double.longBitsToDouble(zzk(obj, j));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final byte zzx(Object obj, long j) {
            return this.zzph.getByte(obj, j);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zze(Object obj, long j, byte b) {
            this.zzph.putByte(obj, j, b);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final boolean zzl(Object obj, long j) {
            return this.zzph.getBoolean(obj, j);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j, boolean z) {
            this.zzph.putBoolean(obj, j, z);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final float zzm(Object obj, long j) {
            return this.zzph.getFloat(obj, j);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j, float f) {
            this.zzph.putFloat(obj, j, f);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final double zzn(Object obj, long j) {
            return this.zzph.getDouble(obj, j);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j, double d) {
            this.zzph.putDouble(obj, j, d);
        }
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final byte zzx(Object obj, long j) {
            if (zzgs.zzpe) {
                return zzgs.zzp(obj, j);
            }
            return zzgs.zzq(obj, j);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zze(Object obj, long j, byte b) {
            if (zzgs.zzpe) {
                zzgs.zza(obj, j, b);
            } else {
                zzgs.zzb(obj, j, b);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final boolean zzl(Object obj, long j) {
            if (zzgs.zzpe) {
                return zzgs.zzr(obj, j);
            }
            return zzgs.zzs(obj, j);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j, boolean z) {
            if (zzgs.zzpe) {
                zzgs.zzb(obj, j, z);
            } else {
                zzgs.zzc(obj, j, z);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final float zzm(Object obj, long j) {
            return Float.intBitsToFloat(zzj(obj, j));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j, float f) {
            zza(obj, j, Float.floatToIntBits(f));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final double zzn(Object obj, long j) {
            return Double.longBitsToDouble(zzk(obj, j));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }
    }

    static boolean zzdn() {
        return zzgx;
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    static abstract class zzd {
        Unsafe zzph;

        zzd(Unsafe unsafe) {
            this.zzph = unsafe;
        }

        public abstract void zza(Object obj, long j, double d);

        public abstract void zza(Object obj, long j, float f);

        public abstract void zza(Object obj, long j, boolean z);

        public abstract void zze(Object obj, long j, byte b);

        public abstract boolean zzl(Object obj, long j);

        public abstract float zzm(Object obj, long j);

        public abstract double zzn(Object obj, long j);

        public abstract byte zzx(Object obj, long j);

        public final int zzj(Object obj, long j) {
            return this.zzph.getInt(obj, j);
        }

        public final void zza(Object obj, long j, int i) {
            this.zzph.putInt(obj, j, i);
        }

        public final long zzk(Object obj, long j) {
            return this.zzph.getLong(obj, j);
        }

        public final void zza(Object obj, long j, long j2) {
            this.zzph.putLong(obj, j, j2);
        }
    }

    static boolean zzdo() {
        return zzoo;
    }

    static <T> T zzg(Class<T> cls) {
        try {
            return (T) zzms.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static int zzh(Class<?> cls) {
        if (zzgx) {
            return zzon.zzph.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzi(Class<?> cls) {
        if (zzgx) {
            return zzon.zzph.arrayIndexScale(cls);
        }
        return -1;
    }

    static int zzj(Object obj, long j) {
        return zzon.zzj(obj, j);
    }

    static void zza(Object obj, long j, int i) {
        zzon.zza(obj, j, i);
    }

    static long zzk(Object obj, long j) {
        return zzon.zzk(obj, j);
    }

    static void zza(Object obj, long j, long j2) {
        zzon.zza(obj, j, j2);
    }

    static boolean zzl(Object obj, long j) {
        return zzon.zzl(obj, j);
    }

    static void zza(Object obj, long j, boolean z) {
        zzon.zza(obj, j, z);
    }

    static float zzm(Object obj, long j) {
        return zzon.zzm(obj, j);
    }

    static void zza(Object obj, long j, float f) {
        zzon.zza(obj, j, f);
    }

    static double zzn(Object obj, long j) {
        return zzon.zzn(obj, j);
    }

    static void zza(Object obj, long j, double d) {
        zzon.zza(obj, j, d);
    }

    static Object zzo(Object obj, long j) {
        return zzon.zzph.getObject(obj, j);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzon.zzph.putObject(obj, j, obj2);
    }

    static byte zza(byte[] bArr, long j) {
        return zzon.zzx(bArr, zzop + j);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzon.zze(bArr, zzop + j, b);
    }

    static Unsafe zzdp() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzgu());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzdq() {
        Unsafe unsafe = zzms;
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
            if (zzcs.zzal()) {
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

    private static boolean zzdr() {
        Unsafe unsafe = zzms;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            if (zzds() == null) {
                return false;
            }
            if (zzcs.zzal()) {
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

    private static boolean zzj(Class<?> cls) {
        if (!zzcs.zzal()) {
            return false;
        }
        try {
            Class<?> cls2 = zzgg;
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

    private static Field zzds() {
        Field fieldZzb;
        if (zzcs.zzal() && (fieldZzb = zzb(Buffer.class, "effectiveDirectAddress")) != null) {
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
    public static byte zzp(Object obj, long j) {
        return (byte) (zzj(obj, (-4) & j) >>> ((int) (((~j) & 3) << 3)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte zzq(Object obj, long j) {
        return (byte) (zzj(obj, (-4) & j) >>> ((int) ((j & 3) << 3)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zza(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int iZzj = zzj(obj, j2);
        int i = ((~((int) j)) & 3) << 3;
        zza(obj, j2, ((255 & b) << i) | (iZzj & (~(255 << i))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzb(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int i = (((int) j) & 3) << 3;
        zza(obj, j2, ((255 & b) << i) | (zzj(obj, j2) & (~(255 << i))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzr(Object obj, long j) {
        return zzp(obj, j) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzs(Object obj, long j) {
        return zzq(obj, j) != 0;
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
        Unsafe unsafeZzdp = zzdp();
        zzms = unsafeZzdp;
        zzgg = zzcs.zzam();
        boolean zZzj = zzj(Long.TYPE);
        zzol = zZzj;
        boolean zZzj2 = zzj(Integer.TYPE);
        zzom = zZzj2;
        zzd zzbVar = null;
        if (unsafeZzdp != null) {
            if (!zzcs.zzal()) {
                zzbVar = new zzb(unsafeZzdp);
            } else if (zZzj) {
                zzbVar = new zzc(unsafeZzdp);
            } else if (zZzj2) {
                zzbVar = new zza(unsafeZzdp);
            }
        }
        zzon = zzbVar;
        zzoo = zzdr();
        zzgx = zzdq();
        long jZzh = zzh(byte[].class);
        zzop = jZzh;
        zzoq = zzh(boolean[].class);
        zzor = zzi(boolean[].class);
        zzos = zzh(int[].class);
        zzot = zzi(int[].class);
        zzou = zzh(long[].class);
        zzov = zzi(long[].class);
        zzow = zzh(float[].class);
        zzox = zzi(float[].class);
        zzoy = zzh(double[].class);
        zzoz = zzi(double[].class);
        zzpa = zzh(Object[].class);
        zzpb = zzi(Object[].class);
        Field fieldZzds = zzds();
        zzpc = (fieldZzds == null || zzbVar == null) ? -1L : zzbVar.zzph.objectFieldOffset(fieldZzds);
        zzpd = (int) (jZzh & 7);
        zzpe = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }
}
