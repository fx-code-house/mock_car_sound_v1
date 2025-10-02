package com.google.android.gms.internal.wearable;

import com.google.android.gms.internal.wearable.zzae;
import com.google.android.gms.internal.wearable.zzaf;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public abstract class zzaf<MessageType extends zzaf<MessageType, BuilderType>, BuilderType extends zzae<MessageType, BuilderType>> implements zzcx {
    protected int zza = 0;

    /* JADX WARN: Multi-variable type inference failed */
    protected static <T> void zzL(Iterable<T> iterable, List<? super T> list) {
        zzca.zza(iterable);
        if (iterable instanceof zzch) {
            List<?> listZzh = ((zzch) iterable).zzh();
            zzch zzchVar = (zzch) list;
            int size = list.size();
            for (Object obj : listZzh) {
                if (obj == null) {
                    int size2 = zzchVar.size();
                    StringBuilder sb = new StringBuilder(37);
                    sb.append("Element at index ");
                    sb.append(size2 - size);
                    sb.append(" is null.");
                    String string = sb.toString();
                    int size3 = zzchVar.size();
                    while (true) {
                        size3--;
                        if (size3 < size) {
                            throw new NullPointerException(string);
                        }
                        zzchVar.remove(size3);
                    }
                } else if (obj instanceof zzau) {
                    zzchVar.zzf((zzau) obj);
                } else {
                    zzchVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zzde) {
            list.addAll(iterable);
            return;
        }
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(list.size() + iterable.size());
        }
        int size4 = list.size();
        for (T t : iterable) {
            if (t == null) {
                int size5 = list.size();
                StringBuilder sb2 = new StringBuilder(37);
                sb2.append("Element at index ");
                sb2.append(size5 - size4);
                sb2.append(" is null.");
                String string2 = sb2.toString();
                int size6 = list.size();
                while (true) {
                    size6--;
                    if (size6 < size4) {
                        throw new NullPointerException(string2);
                    }
                    list.remove(size6);
                }
            } else {
                list.add(t);
            }
        }
    }

    @Override // com.google.android.gms.internal.wearable.zzcx
    public final zzau zzH() {
        try {
            int iZzP = zzP();
            zzau zzauVar = zzau.zzb;
            byte[] bArr = new byte[iZzP];
            zzbb zzbbVarZzt = zzbb.zzt(bArr);
            zzO(zzbbVarZzt);
            zzbbVarZzt.zzC();
            return new zzas(bArr);
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 72);
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ByteString threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    public final byte[] zzI() {
        try {
            byte[] bArr = new byte[zzP()];
            zzbb zzbbVarZzt = zzbb.zzt(bArr);
            zzO(zzbbVarZzt);
            zzbbVarZzt.zzC();
            return bArr;
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 72);
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a byte array threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    int zzJ() {
        throw null;
    }

    void zzK(int i) {
        throw null;
    }
}
