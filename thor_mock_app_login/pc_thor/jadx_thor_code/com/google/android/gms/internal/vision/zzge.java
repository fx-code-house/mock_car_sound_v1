package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzge;
import com.google.android.gms.internal.vision.zzgh;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class zzge<MessageType extends zzge<MessageType, BuilderType>, BuilderType extends zzgh<MessageType, BuilderType>> implements zzjn {
    protected int zzte = 0;

    @Override // com.google.android.gms.internal.vision.zzjn
    public final zzgs zzee() {
        try {
            zzha zzhaVarZzaw = zzgs.zzaw(zzgz());
            zzb(zzhaVarZzaw.zzfq());
            return zzhaVarZzaw.zzfp();
        } catch (IOException e) {
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name).length() + 62 + "ByteString".length()).append("Serializing ").append(name).append(" to a ByteString threw an IOException (should never happen).").toString(), e);
        }
    }

    public final byte[] toByteArray() {
        try {
            byte[] bArr = new byte[zzgz()];
            zzhl zzhlVarZze = zzhl.zze(bArr);
            zzb(zzhlVarZze);
            zzhlVarZze.zzgb();
            return bArr;
        } catch (IOException e) {
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name).length() + 62 + "byte array".length()).append("Serializing ").append(name).append(" to a byte array threw an IOException (should never happen).").toString(), e);
        }
    }

    int zzef() {
        throw new UnsupportedOperationException();
    }

    void zzak(int i) {
        throw new UnsupportedOperationException();
    }

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzie.checkNotNull(iterable);
        if (iterable instanceof zziu) {
            List<?> listZzhs = ((zziu) iterable).zzhs();
            zziu zziuVar = (zziu) list;
            int size = list.size();
            for (Object obj : listZzhs) {
                if (obj == null) {
                    String string = new StringBuilder(37).append("Element at index ").append(zziuVar.size() - size).append(" is null.").toString();
                    for (int size2 = zziuVar.size() - 1; size2 >= size; size2--) {
                        zziuVar.remove(size2);
                    }
                    throw new NullPointerException(string);
                }
                if (obj instanceof zzgs) {
                    zziuVar.zzc((zzgs) obj);
                } else {
                    zziuVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zzjz) {
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
