package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;
import com.google.android.gms.internal.icing.zzdx.zzb;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public abstract class zzdx<MessageType extends zzdx<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzcm<MessageType, BuilderType> {
    private static Map<Object, zzdx<?, ?>> zzke = new ConcurrentHashMap();
    protected zzgp zzkc = zzgp.zzdl();
    private int zzkd = -1;

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public static class zza<T extends zzdx<T, ?>> extends zzcq<T> {
        private final T zzkg;

        public zza(T t) {
            this.zzkg = t;
        }
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    static final class zzc implements zzdu<zzc> {
        @Override // com.google.android.gms.internal.icing.zzdu
        public final int zzbf() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.icing.zzdu
        public final zzha zzbg() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.icing.zzdu
        public final zzhh zzbh() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.icing.zzdu
        public final boolean zzbi() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.icing.zzdu
        public final boolean zzbj() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.icing.zzdu
        public final zzfg zza(zzfg zzfgVar, zzfh zzfhVar) {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.icing.zzdu
        public final zzfn zza(zzfn zzfnVar, zzfn zzfnVar2) {
            throw new NoSuchMethodError();
        }

        @Override // java.lang.Comparable
        public final /* synthetic */ int compareTo(Object obj) {
            throw new NoSuchMethodError();
        }
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public static abstract class zzd<MessageType extends zzd<MessageType, BuilderType>, BuilderType> extends zzdx<MessageType, BuilderType> implements zzfj {
        protected zzds<zzc> zzkj = zzds.zzbd();
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public enum zze {
        public static final int zzkm = 1;
        public static final int zzkn = 2;
        public static final int zzko = 3;
        public static final int zzkp = 4;
        public static final int zzkq = 5;
        public static final int zzkr = 6;
        public static final int zzks = 7;
        public static final int zzku = 1;
        public static final int zzkv = 2;
        public static final int zzkx = 1;
        public static final int zzky = 2;
        private static final /* synthetic */ int[] zzkt = {1, 2, 3, 4, 5, 6, 7};
        private static final /* synthetic */ int[] zzkw = {1, 2};
        private static final /* synthetic */ int[] zzkz = {1, 2};

        public static int[] zzby() {
            return (int[]) zzkt.clone();
        }
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    public String toString() {
        return zzfi.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zzga != 0) {
            return this.zzga;
        }
        this.zzga = zzft.zzcv().zzo(this).hashCode(this);
        return this.zzga;
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public static abstract class zzb<MessageType extends zzdx<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzco<MessageType, BuilderType> {
        private final MessageType zzkg;
        protected MessageType zzkh;
        protected boolean zzki = false;

        protected zzb(MessageType messagetype) {
            this.zzkg = messagetype;
            this.zzkh = (MessageType) messagetype.zza(zze.zzkp, null, null);
        }

        protected void zzbt() {
            MessageType messagetype = (MessageType) this.zzkh.zza(zze.zzkp, null, null);
            zza(messagetype, this.zzkh);
            this.zzkh = messagetype;
        }

        @Override // com.google.android.gms.internal.icing.zzfj
        public final boolean isInitialized() {
            return zzdx.zza(this.zzkh, false);
        }

        @Override // com.google.android.gms.internal.icing.zzfg
        /* renamed from: zzbu, reason: merged with bridge method [inline-methods] */
        public MessageType zzbw() {
            if (this.zzki) {
                return this.zzkh;
            }
            MessageType messagetype = this.zzkh;
            zzft.zzcv().zzo(messagetype).zzf(messagetype);
            this.zzki = true;
            return this.zzkh;
        }

        @Override // com.google.android.gms.internal.icing.zzfg
        /* renamed from: zzbv, reason: merged with bridge method [inline-methods] */
        public final MessageType zzbx() {
            MessageType messagetype = (MessageType) zzbw();
            if (messagetype.isInitialized()) {
                return messagetype;
            }
            throw new zzgn(messagetype);
        }

        @Override // com.google.android.gms.internal.icing.zzco
        public final BuilderType zza(MessageType messagetype) {
            if (this.zzki) {
                zzbt();
                this.zzki = false;
            }
            zza(this.zzkh, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzft.zzcv().zzo(messagetype).zzc(messagetype, messagetype2);
        }

        @Override // com.google.android.gms.internal.icing.zzco
        /* renamed from: zzag */
        public final /* synthetic */ zzco clone() {
            return (zzb) clone();
        }

        @Override // com.google.android.gms.internal.icing.zzfj
        public final /* synthetic */ zzfh zzbr() {
            return this.zzkg;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.internal.icing.zzco
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zzb zzbVar = (zzb) this.zzkg.zza(zze.zzkq, null, null);
            zzbVar.zza((zzb) zzbw());
            return zzbVar;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzft.zzcv().zzo(this).equals(this, (zzdx) obj);
        }
        return false;
    }

    protected final <MessageType extends zzdx<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> BuilderType zzbk() {
        return (BuilderType) zza(zze.zzkq, (Object) null, (Object) null);
    }

    @Override // com.google.android.gms.internal.icing.zzfj
    public final boolean isInitialized() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    @Override // com.google.android.gms.internal.icing.zzcm
    final int zzae() {
        return this.zzkd;
    }

    @Override // com.google.android.gms.internal.icing.zzcm
    final void zzg(int i) {
        this.zzkd = i;
    }

    @Override // com.google.android.gms.internal.icing.zzfh
    public final void zzb(zzdk zzdkVar) throws IOException {
        zzft.zzcv().zzo(this).zza(this, zzdm.zza(zzdkVar));
    }

    @Override // com.google.android.gms.internal.icing.zzfh
    public final int zzbl() {
        if (this.zzkd == -1) {
            this.zzkd = zzft.zzcv().zzo(this).zzn(this);
        }
        return this.zzkd;
    }

    static <T extends zzdx<?, ?>> T zza(Class<T> cls) throws ClassNotFoundException {
        zzdx<?, ?> zzdxVar = zzke.get(cls);
        if (zzdxVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzdxVar = zzke.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzdxVar == null) {
            zzdxVar = (T) ((zzdx) zzgs.zzg(cls)).zza(zze.zzkr, (Object) null, (Object) null);
            if (zzdxVar == null) {
                throw new IllegalStateException();
            }
            zzke.put(cls, zzdxVar);
        }
        return (T) zzdxVar;
    }

    protected static <T extends zzdx<?, ?>> void zza(Class<T> cls, T t) {
        zzke.put(cls, t);
    }

    protected static Object zza(zzfh zzfhVar, String str, Object[] objArr) {
        return new zzfv(zzfhVar, str, objArr);
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

    protected static final <T extends zzdx<T, ?>> boolean zza(T t, boolean z) {
        byte bByteValue = ((Byte) t.zza(zze.zzkm, null, null)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zZzm = zzft.zzcv().zzo(t).zzm(t);
        if (z) {
            t.zza(zze.zzkn, zZzm ? t : null, null);
        }
        return zZzm;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.icing.zzef, com.google.android.gms.internal.icing.zzev] */
    protected static zzef zzbm() {
        return zzev.zzci();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.icing.zzdl, com.google.android.gms.internal.icing.zzed] */
    protected static zzed zzbn() {
        return zzdl.zzax();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.icing.zzcr, com.google.android.gms.internal.icing.zzea] */
    protected static zzea zzbo() {
        return zzcr.zzak();
    }

    protected static <E> zzee<E> zzbp() {
        return zzfs.zzcu();
    }

    protected static <E> zzee<E> zza(zzee<E> zzeeVar) {
        int size = zzeeVar.size();
        return zzeeVar.zzj(size == 0 ? 10 : size << 1);
    }

    @Override // com.google.android.gms.internal.icing.zzfh
    public final /* synthetic */ zzfg zzbq() {
        zzb zzbVar = (zzb) zza(zze.zzkq, (Object) null, (Object) null);
        zzbVar.zza((zzb) this);
        return zzbVar;
    }

    @Override // com.google.android.gms.internal.icing.zzfj
    public final /* synthetic */ zzfh zzbr() {
        return (zzdx) zza(zze.zzkr, (Object) null, (Object) null);
    }
}
