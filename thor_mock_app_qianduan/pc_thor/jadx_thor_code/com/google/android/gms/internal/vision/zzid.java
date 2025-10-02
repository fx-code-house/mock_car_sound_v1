package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzid;
import com.google.android.gms.internal.vision.zzid.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class zzid<MessageType extends zzid<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzge<MessageType, BuilderType> {
    private static Map<Object, zzid<?, ?>> zzyb = new ConcurrentHashMap();
    protected zzkx zzxz = zzkx.zzjb();
    private int zzya = -1;

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static class zzc<T extends zzid<T, ?>> extends zzgj<T> {
        private final T zzxw;

        public zzc(T t) {
            this.zzxw = t;
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public enum zzf {
        public static final int zzyh = 1;
        public static final int zzyi = 2;
        public static final int zzyj = 3;
        public static final int zzyk = 4;
        public static final int zzyl = 5;
        public static final int zzym = 6;
        public static final int zzyn = 7;
        public static final int zzyp = 1;
        public static final int zzyq = 2;
        public static final int zzys = 1;
        public static final int zzyt = 2;
        private static final /* synthetic */ int[] zzyo = {1, 2, 3, 4, 5, 6, 7};
        private static final /* synthetic */ int[] zzyr = {1, 2};
        private static final /* synthetic */ int[] zzyu = {1, 2};

        public static int[] zzhf() {
            return (int[]) zzyo.clone();
        }
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static abstract class zzb<MessageType extends zze<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zza<MessageType, BuilderType> implements zzjp {
        protected zzb(MessageType messagetype) {
            super(messagetype);
        }

        @Override // com.google.android.gms.internal.vision.zzid.zza
        protected void zzgs() {
            super.zzgs();
            ((zze) this.zzxx).zzyg = (zzht) ((zze) this.zzxx).zzyg.clone();
        }

        @Override // com.google.android.gms.internal.vision.zzid.zza
        /* renamed from: zzgt */
        public /* synthetic */ zzid zzgv() {
            return (zze) zzgv();
        }

        @Override // com.google.android.gms.internal.vision.zzid.zza, com.google.android.gms.internal.vision.zzjm
        public /* synthetic */ zzjn zzgv() {
            if (this.zzxy) {
                return (zze) this.zzxx;
            }
            ((zze) this.zzxx).zzyg.zzej();
            return (zze) super.zzgv();
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static abstract class zze<MessageType extends zze<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzid<MessageType, BuilderType> implements zzjp {
        protected zzht<zzd> zzyg = zzht.zzgh();

        final zzht<zzd> zzhe() {
            if (this.zzyg.isImmutable()) {
                this.zzyg = (zzht) this.zzyg.clone();
            }
            return this.zzyg;
        }

        /* JADX WARN: Type inference failed for: r1v8, types: [Type, java.util.ArrayList, java.util.List] */
        public final <Type> Type zzc(zzhp<MessageType, Type> zzhpVar) {
            zzg zzgVarZza = zzid.zza(zzhpVar);
            if (zzgVarZza.zzyv != ((zzid) zzgx())) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
            Type type = (Type) this.zzyg.zza((zzht<zzd>) zzgVarZza.zzyx);
            if (type == null) {
                return zzgVarZza.zzgk;
            }
            if (zzgVarZza.zzyx.zzye) {
                if (zzgVarZza.zzyx.zzyd.zzjk() != zzlo.ENUM) {
                    return type;
                }
                ?? r1 = (Type) new ArrayList();
                Iterator it = ((List) type).iterator();
                while (it.hasNext()) {
                    r1.add(zzgVarZza.zzl(it.next()));
                }
                return r1;
            }
            return (Type) zzgVarZza.zzl(type);
        }
    }

    public String toString() {
        return zzjo.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zzte != 0) {
            return this.zzte;
        }
        this.zzte = zzjy.zzij().zzx(this).hashCode(this);
        return this.zzte;
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static abstract class zza<MessageType extends zzid<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzgh<MessageType, BuilderType> {
        private final MessageType zzxw;
        protected MessageType zzxx;
        protected boolean zzxy = false;

        protected zza(MessageType messagetype) {
            this.zzxw = messagetype;
            this.zzxx = (MessageType) messagetype.zza(zzf.zzyk, null, null);
        }

        protected void zzgs() {
            MessageType messagetype = (MessageType) this.zzxx.zza(zzf.zzyk, null, null);
            zza(messagetype, this.zzxx);
            this.zzxx = messagetype;
        }

        @Override // com.google.android.gms.internal.vision.zzjp
        public final boolean isInitialized() {
            return zzid.zza(this.zzxx, false);
        }

        @Override // com.google.android.gms.internal.vision.zzjm
        /* renamed from: zzgt, reason: merged with bridge method [inline-methods] */
        public MessageType zzgv() {
            if (this.zzxy) {
                return this.zzxx;
            }
            MessageType messagetype = this.zzxx;
            zzjy.zzij().zzx(messagetype).zzj(messagetype);
            this.zzxy = true;
            return this.zzxx;
        }

        @Override // com.google.android.gms.internal.vision.zzjm
        /* renamed from: zzgu, reason: merged with bridge method [inline-methods] */
        public final MessageType zzgw() {
            MessageType messagetype = (MessageType) zzgv();
            if (messagetype.isInitialized()) {
                return messagetype;
            }
            throw new zzkv(messagetype);
        }

        @Override // com.google.android.gms.internal.vision.zzgh
        public final BuilderType zza(MessageType messagetype) {
            if (this.zzxy) {
                zzgs();
                this.zzxy = false;
            }
            zza(this.zzxx, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzjy.zzij().zzx(messagetype).zzd(messagetype, messagetype2);
        }

        private final BuilderType zzb(byte[] bArr, int i, int i2, zzho zzhoVar) throws zzin {
            if (this.zzxy) {
                zzgs();
                this.zzxy = false;
            }
            try {
                zzjy.zzij().zzx(this.zzxx).zza(this.zzxx, bArr, 0, i2, new zzgm(zzhoVar));
                return this;
            } catch (zzin e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e2);
            } catch (IndexOutOfBoundsException unused) {
                throw zzin.zzhh();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Override // com.google.android.gms.internal.vision.zzgh
        /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
        public final BuilderType zza(zzhe zzheVar, zzho zzhoVar) throws IOException {
            if (this.zzxy) {
                zzgs();
                this.zzxy = false;
            }
            try {
                zzjy.zzij().zzx(this.zzxx).zza(this.zzxx, zzhj.zza(zzheVar), zzhoVar);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }

        @Override // com.google.android.gms.internal.vision.zzgh
        public final /* synthetic */ zzgh zza(byte[] bArr, int i, int i2, zzho zzhoVar) throws zzin {
            return zzb(bArr, 0, i2, zzhoVar);
        }

        @Override // com.google.android.gms.internal.vision.zzgh
        /* renamed from: zzeh */
        public final /* synthetic */ zzgh clone() {
            return (zza) clone();
        }

        @Override // com.google.android.gms.internal.vision.zzjp
        public final /* synthetic */ zzjn zzgx() {
            return this.zzxw;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.internal.vision.zzgh
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zzaVar = (zza) this.zzxw.zza(zzf.zzyl, null, null);
            zzaVar.zza((zza) zzgv());
            return zzaVar;
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static final class zzd implements zzhv<zzd> {
        final zzll zzyd;
        final zzig<?> zzyc = null;
        final int number = 202056002;
        final boolean zzye = true;
        final boolean zzyf = false;

        zzd(zzig<?> zzigVar, int i, zzll zzllVar, boolean z, boolean z2) {
            this.zzyd = zzllVar;
        }

        @Override // com.google.android.gms.internal.vision.zzhv
        public final boolean zzgp() {
            return false;
        }

        @Override // com.google.android.gms.internal.vision.zzhv
        public final int zzak() {
            return this.number;
        }

        @Override // com.google.android.gms.internal.vision.zzhv
        public final zzll zzgm() {
            return this.zzyd;
        }

        @Override // com.google.android.gms.internal.vision.zzhv
        public final zzlo zzgn() {
            return this.zzyd.zzjk();
        }

        @Override // com.google.android.gms.internal.vision.zzhv
        public final boolean zzgo() {
            return this.zzye;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.internal.vision.zzhv
        public final zzjm zza(zzjm zzjmVar, zzjn zzjnVar) {
            return ((zza) zzjmVar).zza((zza) zzjnVar);
        }

        @Override // com.google.android.gms.internal.vision.zzhv
        public final zzjs zza(zzjs zzjsVar, zzjs zzjsVar2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.lang.Comparable
        public final /* synthetic */ int compareTo(Object obj) {
            return this.number - ((zzd) obj).number;
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static class zzg<ContainingType extends zzjn, Type> extends zzhp<ContainingType, Type> {
        final Type zzgk;
        final ContainingType zzyv;
        final zzjn zzyw;
        final zzd zzyx;

        zzg(ContainingType containingtype, Type type, zzjn zzjnVar, zzd zzdVar, Class cls) {
            if (containingtype == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            }
            if (zzdVar.zzyd == zzll.zzads && zzjnVar == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            }
            this.zzyv = containingtype;
            this.zzgk = type;
            this.zzyw = zzjnVar;
            this.zzyx = zzdVar;
        }

        final Object zzl(Object obj) {
            if (this.zzyx.zzyd.zzjk() != zzlo.ENUM) {
                return obj;
            }
            zzig zzigVar = null;
            zzigVar.zzh(((Integer) obj).intValue());
            throw null;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzjy.zzij().zzx(this).equals(this, (zzid) obj);
        }
        return false;
    }

    protected final <MessageType extends zzid<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> BuilderType zzgy() {
        return (BuilderType) zza(zzf.zzyl, (Object) null, (Object) null);
    }

    @Override // com.google.android.gms.internal.vision.zzjp
    public final boolean isInitialized() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    @Override // com.google.android.gms.internal.vision.zzge
    final int zzef() {
        return this.zzya;
    }

    @Override // com.google.android.gms.internal.vision.zzge
    final void zzak(int i) {
        this.zzya = i;
    }

    @Override // com.google.android.gms.internal.vision.zzjn
    public final void zzb(zzhl zzhlVar) throws IOException {
        zzjy.zzij().zzx(this).zza(this, zzhn.zza(zzhlVar));
    }

    @Override // com.google.android.gms.internal.vision.zzjn
    public final int zzgz() {
        if (this.zzya == -1) {
            this.zzya = zzjy.zzij().zzx(this).zzu(this);
        }
        return this.zzya;
    }

    static <T extends zzid<?, ?>> T zzd(Class<T> cls) throws ClassNotFoundException {
        zzid<?, ?> zzidVar = zzyb.get(cls);
        if (zzidVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzidVar = zzyb.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzidVar == null) {
            zzidVar = (T) ((zzid) zzla.zzh(cls)).zza(zzf.zzym, (Object) null, (Object) null);
            if (zzidVar == null) {
                throw new IllegalStateException();
            }
            zzyb.put(cls, zzidVar);
        }
        return (T) zzidVar;
    }

    protected static <T extends zzid<?, ?>> void zza(Class<T> cls, T t) {
        zzyb.put(cls, t);
    }

    protected static Object zza(zzjn zzjnVar, String str, Object[] objArr) {
        return new zzka(zzjnVar, str, objArr);
    }

    public static <ContainingType extends zzjn, Type> zzg<ContainingType, Type> zza(ContainingType containingtype, zzjn zzjnVar, zzig<?> zzigVar, int i, zzll zzllVar, boolean z, Class cls) {
        return new zzg<>(containingtype, Collections.emptyList(), zzjnVar, new zzd(null, 202056002, zzllVar, true, false), cls);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <MessageType extends zze<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>, T> zzg<MessageType, T> zza(zzhp<MessageType, T> zzhpVar) {
        return (zzg) zzhpVar;
    }

    protected static final <T extends zzid<T, ?>> boolean zza(T t, boolean z) {
        byte bByteValue = ((Byte) t.zza(zzf.zzyh, null, null)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zZzw = zzjy.zzij().zzx(t).zzw(t);
        if (z) {
            t.zza(zzf.zzyi, zZzw ? t : null, null);
        }
        return zZzw;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.vision.zzif, com.google.android.gms.internal.vision.zzii] */
    protected static zzii zzha() {
        return zzif.zzhg();
    }

    protected static <E> zzik<E> zzhb() {
        return zzkb.zzim();
    }

    protected static <E> zzik<E> zza(zzik<E> zzikVar) {
        int size = zzikVar.size();
        return zzikVar.zzan(size == 0 ? 10 : size << 1);
    }

    private static <T extends zzid<T, ?>> T zza(T t, byte[] bArr, int i, int i2, zzho zzhoVar) throws zzin {
        T t2 = (T) t.zza(zzf.zzyk, null, null);
        try {
            zzkc zzkcVarZzx = zzjy.zzij().zzx(t2);
            zzkcVarZzx.zza(t2, bArr, 0, i2, new zzgm(zzhoVar));
            zzkcVarZzx.zzj(t2);
            if (t2.zzte == 0) {
                return t2;
            }
            throw new RuntimeException();
        } catch (IOException e) {
            if (e.getCause() instanceof zzin) {
                throw ((zzin) e.getCause());
            }
            throw new zzin(e.getMessage()).zzg(t2);
        } catch (IndexOutOfBoundsException unused) {
            throw zzin.zzhh().zzg(t2);
        }
    }

    private static <T extends zzid<T, ?>> T zzb(T t) throws zzin {
        if (t == null || t.isInitialized()) {
            return t;
        }
        throw new zzin(new zzkv(t).getMessage()).zzg(t);
    }

    protected static <T extends zzid<T, ?>> T zza(T t, byte[] bArr) throws zzin {
        return (T) zzb(zza(t, bArr, 0, bArr.length, zzho.zzgf()));
    }

    protected static <T extends zzid<T, ?>> T zza(T t, byte[] bArr, zzho zzhoVar) throws zzin {
        return (T) zzb(zza(t, bArr, 0, bArr.length, zzhoVar));
    }

    @Override // com.google.android.gms.internal.vision.zzjn
    public final /* synthetic */ zzjm zzhc() {
        zza zzaVar = (zza) zza(zzf.zzyl, (Object) null, (Object) null);
        zzaVar.zza((zza) this);
        return zzaVar;
    }

    @Override // com.google.android.gms.internal.vision.zzjn
    public final /* synthetic */ zzjm zzhd() {
        return (zza) zza(zzf.zzyl, (Object) null, (Object) null);
    }

    @Override // com.google.android.gms.internal.vision.zzjp
    public final /* synthetic */ zzjn zzgx() {
        return (zzid) zza(zzf.zzym, (Object) null, (Object) null);
    }
}
