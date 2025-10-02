package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzcm;
import com.google.android.gms.internal.icing.zzco;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public abstract class zzcm<MessageType extends zzcm<MessageType, BuilderType>, BuilderType extends zzco<MessageType, BuilderType>> implements zzfh {
    protected int zzga = 0;

    @Override // com.google.android.gms.internal.icing.zzfh
    public final zzct zzad() {
        try {
            zzdb zzdbVarZzm = zzct.zzm(zzbl());
            zzb(zzdbVarZzm.zzas());
            return zzdbVarZzm.zzar();
        } catch (IOException e) {
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name).length() + 62 + "ByteString".length()).append("Serializing ").append(name).append(" to a ByteString threw an IOException (should never happen).").toString(), e);
        }
    }

    public final byte[] toByteArray() {
        try {
            byte[] bArr = new byte[zzbl()];
            zzdk zzdkVarZzb = zzdk.zzb(bArr);
            zzb(zzdkVarZzb);
            zzdkVarZzb.zzav();
            return bArr;
        } catch (IOException e) {
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name).length() + 62 + "byte array".length()).append("Serializing ").append(name).append(" to a byte array threw an IOException (should never happen).").toString(), e);
        }
    }

    int zzae() {
        throw new UnsupportedOperationException();
    }

    void zzg(int i) {
        throw new UnsupportedOperationException();
    }

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzeb.checkNotNull(iterable);
        if (iterable instanceof zzeo) {
            List<?> listZzcd = ((zzeo) iterable).zzcd();
            zzeo zzeoVar = (zzeo) list;
            int size = list.size();
            for (Object obj : listZzcd) {
                if (obj == null) {
                    String string = new StringBuilder(37).append("Element at index ").append(zzeoVar.size() - size).append(" is null.").toString();
                    for (int size2 = zzeoVar.size() - 1; size2 >= size; size2--) {
                        zzeoVar.remove(size2);
                    }
                    throw new NullPointerException(string);
                }
                if (obj instanceof zzct) {
                    zzeoVar.zzc((zzct) obj);
                } else {
                    zzeoVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zzfq) {
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
