package com.google.android.gms.internal.wearable;

import com.google.android.gms.internal.wearable.zzbk;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzbl<T extends zzbk<T>> {
    private static final zzbl zzd = new zzbl(true);
    final zzds<T, Object> zza = new zzdl(16);
    private boolean zzb;
    private boolean zzc;

    private zzbl() {
    }

    public static <T extends zzbk<T>> zzbl<T> zza() {
        throw null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final void zzd(T r4, java.lang.Object r5) {
        /*
            com.google.android.gms.internal.wearable.zzem r0 = r4.zzb()
            com.google.android.gms.internal.wearable.zzca.zza(r5)
            com.google.android.gms.internal.wearable.zzem r1 = com.google.android.gms.internal.wearable.zzem.DOUBLE
            com.google.android.gms.internal.wearable.zzen r1 = com.google.android.gms.internal.wearable.zzen.INT
            com.google.android.gms.internal.wearable.zzen r0 = r0.zza()
            int r0 = r0.ordinal()
            switch(r0) {
                case 0: goto L41;
                case 1: goto L3e;
                case 2: goto L3b;
                case 3: goto L38;
                case 4: goto L35;
                case 5: goto L32;
                case 6: goto L29;
                case 7: goto L20;
                case 8: goto L17;
                default: goto L16;
            }
        L16:
            goto L46
        L17:
            boolean r0 = r5 instanceof com.google.android.gms.internal.wearable.zzcx
            if (r0 != 0) goto L45
            boolean r0 = r5 instanceof com.google.android.gms.internal.wearable.zzce
            if (r0 == 0) goto L46
            goto L45
        L20:
            boolean r0 = r5 instanceof java.lang.Integer
            if (r0 != 0) goto L45
            boolean r0 = r5 instanceof com.google.android.gms.internal.wearable.zzbu
            if (r0 == 0) goto L46
            goto L45
        L29:
            boolean r0 = r5 instanceof com.google.android.gms.internal.wearable.zzau
            if (r0 != 0) goto L45
            boolean r0 = r5 instanceof byte[]
            if (r0 == 0) goto L46
            goto L45
        L32:
            boolean r0 = r5 instanceof java.lang.String
            goto L43
        L35:
            boolean r0 = r5 instanceof java.lang.Boolean
            goto L43
        L38:
            boolean r0 = r5 instanceof java.lang.Double
            goto L43
        L3b:
            boolean r0 = r5 instanceof java.lang.Float
            goto L43
        L3e:
            boolean r0 = r5 instanceof java.lang.Long
            goto L43
        L41:
            boolean r0 = r5 instanceof java.lang.Integer
        L43:
            if (r0 == 0) goto L46
        L45:
            return
        L46:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r1 = 3
            java.lang.Object[] r1 = new java.lang.Object[r1]
            int r2 = r4.zza()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3 = 0
            r1[r3] = r2
            com.google.android.gms.internal.wearable.zzem r4 = r4.zzb()
            com.google.android.gms.internal.wearable.zzen r4 = r4.zza()
            r2 = 1
            r1[r2] = r4
            java.lang.Class r4 = r5.getClass()
            java.lang.String r4 = r4.getName()
            r5 = 2
            r1[r5] = r4
            java.lang.String r4 = "Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n"
            java.lang.String r4 = java.lang.String.format(r4, r1)
            r0.<init>(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.wearable.zzbl.zzd(com.google.android.gms.internal.wearable.zzbk, java.lang.Object):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* bridge */ /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzbl zzblVar = new zzbl();
        for (int i = 0; i < this.zza.zzc(); i++) {
            Map.Entry<K, Object> entryZzd = this.zza.zzd(i);
            zzblVar.zzc((zzbk) entryZzd.getKey(), entryZzd.getValue());
        }
        Iterator it = this.zza.zze().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzblVar.zzc((zzbk) entry.getKey(), entry.getValue());
        }
        zzblVar.zzc = this.zzc;
        return zzblVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzbl) {
            return this.zza.equals(((zzbl) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final void zzb() {
        if (this.zzb) {
            return;
        }
        this.zza.zza();
        this.zzb = true;
    }

    public final void zzc(T t, Object obj) {
        if (!t.zzc()) {
            zzd(t, obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                zzd(t, arrayList.get(i));
            }
            obj = arrayList;
        }
        if (obj instanceof zzce) {
            this.zzc = true;
        }
        this.zza.put(t, obj);
    }

    private zzbl(boolean z) {
        zzb();
        zzb();
    }
}
