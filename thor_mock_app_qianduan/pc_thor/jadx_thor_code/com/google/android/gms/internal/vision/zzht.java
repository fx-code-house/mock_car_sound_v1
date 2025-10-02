package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzhv;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzht<T extends zzhv<T>> {
    private static final zzht zzva = new zzht(true);
    final zzkh<T, Object> zzux;
    private boolean zzuy;
    private boolean zzuz;

    private zzht() {
        this.zzux = zzkh.zzcb(16);
    }

    private zzht(boolean z) {
        this(zzkh.zzcb(0));
        zzej();
    }

    private zzht(zzkh<T, Object> zzkhVar) {
        this.zzux = zzkhVar;
        zzej();
    }

    public static <T extends zzhv<T>> zzht<T> zzgh() {
        return zzva;
    }

    public final void zzej() {
        if (this.zzuy) {
            return;
        }
        this.zzux.zzej();
        this.zzuy = true;
    }

    public final boolean isImmutable() {
        return this.zzuy;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzht) {
            return this.zzux.equals(((zzht) obj).zzux);
        }
        return false;
    }

    public final int hashCode() {
        return this.zzux.hashCode();
    }

    public final Iterator<Map.Entry<T, Object>> iterator() {
        if (this.zzuz) {
            return new zzit(this.zzux.entrySet().iterator());
        }
        return this.zzux.entrySet().iterator();
    }

    final Iterator<Map.Entry<T, Object>> descendingIterator() {
        if (this.zzuz) {
            return new zzit(this.zzux.zziu().iterator());
        }
        return this.zzux.zziu().iterator();
    }

    public final Object zza(T t) {
        Object obj = this.zzux.get(t);
        if (!(obj instanceof zzio)) {
            return obj;
        }
        return zzio.zzhp();
    }

    public final void zza(T t, Object obj) {
        if (t.zzgo()) {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                zza(t.zzgm(), obj2);
            }
            obj = arrayList;
        } else {
            zza(t.zzgm(), obj);
        }
        if (obj instanceof zzio) {
            this.zzuz = true;
        }
        this.zzux.zza((zzkh<T, Object>) t, (T) obj);
    }

    public final void zzb(T t, Object obj) {
        List arrayList;
        if (!t.zzgo()) {
            throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
        }
        zza(t.zzgm(), obj);
        Object objZza = zza((zzht<T>) t);
        if (objZza == null) {
            arrayList = new ArrayList();
            this.zzux.zza((zzkh<T, Object>) t, (T) arrayList);
        } else {
            arrayList = (List) objZza;
        }
        arrayList.add(obj);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void zza(com.google.android.gms.internal.vision.zzll r2, java.lang.Object r3) {
        /*
            com.google.android.gms.internal.vision.zzie.checkNotNull(r3)
            int[] r0 = com.google.android.gms.internal.vision.zzhw.zzvd
            com.google.android.gms.internal.vision.zzlo r2 = r2.zzjk()
            int r2 = r2.ordinal()
            r2 = r0[r2]
            r0 = 1
            r1 = 0
            switch(r2) {
                case 1: goto L40;
                case 2: goto L3d;
                case 3: goto L3a;
                case 4: goto L37;
                case 5: goto L34;
                case 6: goto L31;
                case 7: goto L28;
                case 8: goto L1f;
                case 9: goto L16;
                default: goto L14;
            }
        L14:
            r0 = r1
            goto L42
        L16:
            boolean r2 = r3 instanceof com.google.android.gms.internal.vision.zzjn
            if (r2 != 0) goto L42
            boolean r2 = r3 instanceof com.google.android.gms.internal.vision.zzio
            if (r2 == 0) goto L14
            goto L42
        L1f:
            boolean r2 = r3 instanceof java.lang.Integer
            if (r2 != 0) goto L42
            boolean r2 = r3 instanceof com.google.android.gms.internal.vision.zzih
            if (r2 == 0) goto L14
            goto L42
        L28:
            boolean r2 = r3 instanceof com.google.android.gms.internal.vision.zzgs
            if (r2 != 0) goto L42
            boolean r2 = r3 instanceof byte[]
            if (r2 == 0) goto L14
            goto L42
        L31:
            boolean r0 = r3 instanceof java.lang.String
            goto L42
        L34:
            boolean r0 = r3 instanceof java.lang.Boolean
            goto L42
        L37:
            boolean r0 = r3 instanceof java.lang.Double
            goto L42
        L3a:
            boolean r0 = r3 instanceof java.lang.Float
            goto L42
        L3d:
            boolean r0 = r3 instanceof java.lang.Long
            goto L42
        L40:
            boolean r0 = r3 instanceof java.lang.Integer
        L42:
            if (r0 == 0) goto L45
            return
        L45:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Wrong object type used with protocol message reflection."
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzht.zza(com.google.android.gms.internal.vision.zzll, java.lang.Object):void");
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzux.zzis(); i++) {
            if (!zzb(this.zzux.zzcc(i))) {
                return false;
            }
        }
        Iterator it = this.zzux.zzit().iterator();
        while (it.hasNext()) {
            if (!zzb((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    private static <T extends zzhv<T>> boolean zzb(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        if (key.zzgn() == zzlo.MESSAGE) {
            if (key.zzgo()) {
                Iterator it = ((List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    if (!((zzjn) it.next()).isInitialized()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzjn) {
                    if (!((zzjn) value).isInitialized()) {
                        return false;
                    }
                } else {
                    if (value instanceof zzio) {
                        return true;
                    }
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzht<T> zzhtVar) {
        for (int i = 0; i < zzhtVar.zzux.zzis(); i++) {
            zzc(zzhtVar.zzux.zzcc(i));
        }
        Iterator it = zzhtVar.zzux.zzit().iterator();
        while (it.hasNext()) {
            zzc((Map.Entry) it.next());
        }
    }

    private static Object zzk(Object obj) {
        if (obj instanceof zzjs) {
            return ((zzjs) obj).clone();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private final void zzc(Map.Entry<T, Object> entry) {
        zzjn zzjnVarZzgw;
        T key = entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzio) {
            value = zzio.zzhp();
        }
        if (key.zzgo()) {
            Object objZza = zza((zzht<T>) key);
            if (objZza == null) {
                objZza = new ArrayList();
            }
            Iterator it = ((List) value).iterator();
            while (it.hasNext()) {
                ((List) objZza).add(zzk(it.next()));
            }
            this.zzux.zza((zzkh<T, Object>) key, (T) objZza);
            return;
        }
        if (key.zzgn() == zzlo.MESSAGE) {
            Object objZza2 = zza((zzht<T>) key);
            if (objZza2 == null) {
                this.zzux.zza((zzkh<T, Object>) key, (T) zzk(value));
                return;
            }
            if (objZza2 instanceof zzjs) {
                zzjnVarZzgw = key.zza((zzjs) objZza2, (zzjs) value);
            } else {
                zzjnVarZzgw = key.zza(((zzjn) objZza2).zzhc(), (zzjn) value).zzgw();
            }
            this.zzux.zza((zzkh<T, Object>) key, (T) zzjnVarZzgw);
            return;
        }
        this.zzux.zza((zzkh<T, Object>) key, (T) zzk(value));
    }

    static void zza(zzhl zzhlVar, zzll zzllVar, int i, Object obj) throws IOException {
        if (zzllVar == zzll.zzadr) {
            zzjn zzjnVar = (zzjn) obj;
            zzie.zzf(zzjnVar);
            zzhlVar.writeTag(i, 3);
            zzjnVar.zzb(zzhlVar);
            zzhlVar.writeTag(i, 4);
        }
        zzhlVar.writeTag(i, zzllVar.zzjl());
        switch (zzhw.zztn[zzllVar.ordinal()]) {
            case 1:
                zzhlVar.zza(((Double) obj).doubleValue());
                break;
            case 2:
                zzhlVar.zzs(((Float) obj).floatValue());
                break;
            case 3:
                zzhlVar.zzs(((Long) obj).longValue());
                break;
            case 4:
                zzhlVar.zzs(((Long) obj).longValue());
                break;
            case 5:
                zzhlVar.zzbd(((Integer) obj).intValue());
                break;
            case 6:
                zzhlVar.zzu(((Long) obj).longValue());
                break;
            case 7:
                zzhlVar.zzbg(((Integer) obj).intValue());
                break;
            case 8:
                zzhlVar.zzk(((Boolean) obj).booleanValue());
                break;
            case 9:
                ((zzjn) obj).zzb(zzhlVar);
                break;
            case 10:
                zzhlVar.zzb((zzjn) obj);
                break;
            case 11:
                if (obj instanceof zzgs) {
                    zzhlVar.zza((zzgs) obj);
                    break;
                } else {
                    zzhlVar.zzw((String) obj);
                    break;
                }
            case 12:
                if (obj instanceof zzgs) {
                    zzhlVar.zza((zzgs) obj);
                    break;
                } else {
                    byte[] bArr = (byte[]) obj;
                    zzhlVar.zze(bArr, 0, bArr.length);
                    break;
                }
            case 13:
                zzhlVar.zzbe(((Integer) obj).intValue());
                break;
            case 14:
                zzhlVar.zzbg(((Integer) obj).intValue());
                break;
            case 15:
                zzhlVar.zzu(((Long) obj).longValue());
                break;
            case 16:
                zzhlVar.zzbf(((Integer) obj).intValue());
                break;
            case 17:
                zzhlVar.zzt(((Long) obj).longValue());
                break;
            case 18:
                if (obj instanceof zzih) {
                    zzhlVar.zzbd(((zzih) obj).zzak());
                    break;
                } else {
                    zzhlVar.zzbd(((Integer) obj).intValue());
                    break;
                }
        }
    }

    public final int zzgi() {
        int iZzd = 0;
        for (int i = 0; i < this.zzux.zzis(); i++) {
            iZzd += zzd(this.zzux.zzcc(i));
        }
        Iterator it = this.zzux.zzit().iterator();
        while (it.hasNext()) {
            iZzd += zzd((Map.Entry) it.next());
        }
        return iZzd;
    }

    private static int zzd(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        Object value = entry.getValue();
        if (key.zzgn() == zzlo.MESSAGE && !key.zzgo() && !key.zzgp()) {
            if (value instanceof zzio) {
                return zzhl.zzb(entry.getKey().zzak(), (zzio) value);
            }
            return zzhl.zzb(entry.getKey().zzak(), (zzjn) value);
        }
        return zzc(key, value);
    }

    static int zza(zzll zzllVar, int i, Object obj) {
        int iZzbh = zzhl.zzbh(i);
        if (zzllVar == zzll.zzadr) {
            zzie.zzf((zzjn) obj);
            iZzbh <<= 1;
        }
        return iZzbh + zzb(zzllVar, obj);
    }

    private static int zzb(zzll zzllVar, Object obj) {
        switch (zzhw.zztn[zzllVar.ordinal()]) {
            case 1:
                return zzhl.zzb(((Double) obj).doubleValue());
            case 2:
                return zzhl.zzt(((Float) obj).floatValue());
            case 3:
                return zzhl.zzv(((Long) obj).longValue());
            case 4:
                return zzhl.zzw(((Long) obj).longValue());
            case 5:
                return zzhl.zzbi(((Integer) obj).intValue());
            case 6:
                return zzhl.zzy(((Long) obj).longValue());
            case 7:
                return zzhl.zzbl(((Integer) obj).intValue());
            case 8:
                return zzhl.zzl(((Boolean) obj).booleanValue());
            case 9:
                return zzhl.zzd((zzjn) obj);
            case 10:
                if (obj instanceof zzio) {
                    return zzhl.zza((zzio) obj);
                }
                return zzhl.zzc((zzjn) obj);
            case 11:
                if (obj instanceof zzgs) {
                    return zzhl.zzb((zzgs) obj);
                }
                return zzhl.zzx((String) obj);
            case 12:
                if (obj instanceof zzgs) {
                    return zzhl.zzb((zzgs) obj);
                }
                return zzhl.zzf((byte[]) obj);
            case 13:
                return zzhl.zzbj(((Integer) obj).intValue());
            case 14:
                return zzhl.zzbm(((Integer) obj).intValue());
            case 15:
                return zzhl.zzz(((Long) obj).longValue());
            case 16:
                return zzhl.zzbk(((Integer) obj).intValue());
            case 17:
                return zzhl.zzx(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzih) {
                    return zzhl.zzbn(((zzih) obj).zzak());
                }
                return zzhl.zzbn(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zzc(zzhv<?> zzhvVar, Object obj) {
        zzll zzllVarZzgm = zzhvVar.zzgm();
        int iZzak = zzhvVar.zzak();
        if (zzhvVar.zzgo()) {
            int iZza = 0;
            if (zzhvVar.zzgp()) {
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    iZza += zzb(zzllVarZzgm, it.next());
                }
                return zzhl.zzbh(iZzak) + iZza + zzhl.zzbp(iZza);
            }
            Iterator it2 = ((List) obj).iterator();
            while (it2.hasNext()) {
                iZza += zza(zzllVarZzgm, iZzak, it2.next());
            }
            return iZza;
        }
        return zza(zzllVarZzgm, iZzak, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzht zzhtVar = new zzht();
        for (int i = 0; i < this.zzux.zzis(); i++) {
            Map.Entry<K, Object> entryZzcc = this.zzux.zzcc(i);
            zzhtVar.zza((zzht) entryZzcc.getKey(), entryZzcc.getValue());
        }
        Iterator it = this.zzux.zzit().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzhtVar.zza((zzht) entry.getKey(), entry.getValue());
        }
        zzhtVar.zzuz = this.zzuz;
        return zzhtVar;
    }
}
