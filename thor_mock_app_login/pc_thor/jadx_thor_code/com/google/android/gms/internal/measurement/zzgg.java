package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzgg;
import com.google.android.gms.internal.measurement.zzgi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
public abstract class zzgg<MessageType extends zzgg<MessageType, BuilderType>, BuilderType extends zzgi<MessageType, BuilderType>> implements zzjj {
    protected int zza = 0;

    @Override // com.google.android.gms.internal.measurement.zzjj
    public final zzgp zzbj() {
        try {
            zzgx zzgxVarZzc = zzgp.zzc(zzbp());
            zza(zzgxVarZzc.zzb());
            return zzgxVarZzc.zza();
        } catch (IOException e) {
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name).length() + 62 + "ByteString".length()).append("Serializing ").append(name).append(" to a ByteString threw an IOException (should never happen).").toString(), e);
        }
    }

    public final byte[] zzbk() {
        try {
            byte[] bArr = new byte[zzbp()];
            zzhi zzhiVarZza = zzhi.zza(bArr);
            zza(zzhiVarZza);
            zzhiVarZza.zzb();
            return bArr;
        } catch (IOException e) {
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name).length() + 62 + "byte array".length()).append("Serializing ").append(name).append(" to a byte array threw an IOException (should never happen).").toString(), e);
        }
    }

    int zzbl() {
        throw new UnsupportedOperationException();
    }

    void zzc(int i) {
        throw new UnsupportedOperationException();
    }

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzia.zza(iterable);
        if (iterable instanceof zziq) {
            List<?> listZzb = ((zziq) iterable).zzb();
            zziq zziqVar = (zziq) list;
            int size = list.size();
            for (Object obj : listZzb) {
                if (obj == null) {
                    String string = new StringBuilder(37).append("Element at index ").append(zziqVar.size() - size).append(" is null.").toString();
                    for (int size2 = zziqVar.size() - 1; size2 >= size; size2--) {
                        zziqVar.remove(size2);
                    }
                    throw new NullPointerException(string);
                }
                if (obj instanceof zzgp) {
                    zziqVar.zza((zzgp) obj);
                } else {
                    zziqVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zzjv) {
            list.addAll((Collection) iterable);
            return;
        }
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
        }
        int size3 = list.size();
        for (T t : iterable) {
            if (t == null) {
                String string2 = new StringBuilder(37).append("Element at index ").append(list.size() - size3).append(" is null.").toString();
                for (int size4 = list.size() - 1; size4 >= size3; size4--) {
                    list.remove(size4);
                }
                throw new NullPointerException(string2);
            }
            list.add(t);
        }
    }
}
