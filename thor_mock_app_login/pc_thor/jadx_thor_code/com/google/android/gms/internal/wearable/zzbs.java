package com.google.android.gms.internal.wearable;

import com.google.android.gms.internal.wearable.zzbp;
import com.google.android.gms.internal.wearable.zzbs;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public abstract class zzbs<MessageType extends zzbs<MessageType, BuilderType>, BuilderType extends zzbp<MessageType, BuilderType>> extends zzaf<MessageType, BuilderType> {
    private static final Map<Object, zzbs<?, ?>> zzb = new ConcurrentHashMap();
    protected zzdx zzc = zzdx.zza();
    protected int zzd = -1;

    static <T extends zzbs> T zzQ(Class<T> cls) throws ClassNotFoundException {
        Map<Object, zzbs<?, ?>> map = zzb;
        zzbs<?, ?> zzbsVar = map.get(cls);
        if (zzbsVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzbsVar = map.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzbsVar == null) {
            zzbsVar = (zzbs) ((zzbs) zzeg.zzc(cls)).zzG(6, null, null);
            if (zzbsVar == null) {
                throw new IllegalStateException();
            }
            map.put(cls, zzbsVar);
        }
        return zzbsVar;
    }

    protected static <T extends zzbs> void zzR(Class<T> cls, T t) {
        zzb.put(cls, t);
    }

    protected static Object zzS(zzcx zzcxVar, String str, Object[] objArr) {
        return new zzdh(zzcxVar, str, objArr);
    }

    static Object zzT(Method method, Object obj, Object... objArr) {
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

    protected static zzby zzU() {
        return zzcm.zzd();
    }

    protected static zzbx zzV() {
        return zzbn.zzd();
    }

    protected static <E> zzbz<E> zzW() {
        return zzdg.zzd();
    }

    protected static <E> zzbz<E> zzX(zzbz<E> zzbzVar) {
        int size = zzbzVar.size();
        return zzbzVar.zze(size == 0 ? 10 : size + size);
    }

    static <T extends zzbs<T, ?>> T zzY(T t, byte[] bArr, int i, int i2, zzbg zzbgVar) throws zzcc {
        T t2 = (T) t.zzG(4, null, null);
        try {
            zzdi zzdiVarZzb = zzdf.zza().zzb(t2.getClass());
            zzdiVarZzb.zzh(t2, bArr, 0, i2, new zzai(zzbgVar));
            zzdiVarZzb.zzi(t2);
            if (t2.zza == 0) {
                return t2;
            }
            throw new RuntimeException();
        } catch (zzcc e) {
            e.zza(t2);
            throw e;
        } catch (IOException e2) {
            if (e2.getCause() instanceof zzcc) {
                throw ((zzcc) e2.getCause());
            }
            zzcc zzccVar = new zzcc(e2);
            zzccVar.zza(t2);
            throw zzccVar;
        } catch (IndexOutOfBoundsException unused) {
            zzcc zzccVarZzb = zzcc.zzb();
            zzccVarZzb.zza(t2);
            throw zzccVarZzb;
        }
    }

    protected static <T extends zzbs<T, ?>> T zzZ(T t, byte[] bArr) throws zzcc {
        T t2 = (T) zzY(t, bArr, 0, bArr.length, zzbg.zza());
        if (t2 == null || t2.zzN()) {
            return t2;
        }
        zzcc zzccVar = new zzcc(new zzdv(t2).getMessage());
        zzccVar.zza(t2);
        throw zzccVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzdf.zza().zzb(getClass()).zzb(this, (zzbs) obj);
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zza;
        if (i != 0) {
            return i;
        }
        int iZzc = zzdf.zza().zzb(getClass()).zzc(this);
        this.zza = iZzc;
        return iZzc;
    }

    public final String toString() {
        return zzcz.zza(this, super.toString());
    }

    protected abstract Object zzG(int i, Object obj, Object obj2);

    @Override // com.google.android.gms.internal.wearable.zzaf
    final int zzJ() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.wearable.zzaf
    final void zzK(int i) {
        this.zzd = i;
    }

    protected final <MessageType extends zzbs<MessageType, BuilderType>, BuilderType extends zzbp<MessageType, BuilderType>> BuilderType zzM() {
        return (BuilderType) zzG(5, null, null);
    }

    public final boolean zzN() {
        boolean zBooleanValue = Boolean.TRUE.booleanValue();
        byte bByteValue = ((Byte) zzG(1, null, null)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zZzj = zzdf.zza().zzb(getClass()).zzj(this);
        if (!zBooleanValue) {
            return zZzj;
        }
        zzG(2, true != zZzj ? null : this, null);
        return zZzj;
    }

    @Override // com.google.android.gms.internal.wearable.zzcx
    public final void zzO(zzbb zzbbVar) throws IOException {
        zzdf.zza().zzb(getClass()).zzm(this, zzbc.zza(zzbbVar));
    }

    @Override // com.google.android.gms.internal.wearable.zzcx
    public final int zzP() {
        int i = this.zzd;
        if (i != -1) {
            return i;
        }
        int iZze = zzdf.zza().zzb(getClass()).zze(this);
        this.zzd = iZze;
        return iZze;
    }

    @Override // com.google.android.gms.internal.wearable.zzcx
    public final /* bridge */ /* synthetic */ zzcw zzaa() {
        zzbp zzbpVar = (zzbp) zzG(5, null, null);
        zzbpVar.zzv(this);
        return zzbpVar;
    }

    @Override // com.google.android.gms.internal.wearable.zzcx
    public final /* bridge */ /* synthetic */ zzcw zzab() {
        return (zzbp) zzG(5, null, null);
    }

    @Override // com.google.android.gms.internal.wearable.zzcy
    public final /* bridge */ /* synthetic */ zzcx zzac() {
        return (zzbs) zzG(6, null, null);
    }
}
