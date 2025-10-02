package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzds<T extends zzdu<T>> {
    private static final zzds zzhn = new zzds(true);
    final zzfz<T, Object> zzhk;
    private boolean zzhl;
    private boolean zzhm;

    private zzds() {
        this.zzhk = zzfz.zzai(16);
    }

    private zzds(boolean z) {
        this(zzfz.zzai(0));
        zzai();
    }

    private zzds(zzfz<T, Object> zzfzVar) {
        this.zzhk = zzfzVar;
        zzai();
    }

    public static <T extends zzdu<T>> zzds<T> zzbd() {
        return zzhn;
    }

    public final void zzai() {
        if (this.zzhl) {
            return;
        }
        this.zzhk.zzai();
        this.zzhl = true;
    }

    public final boolean isImmutable() {
        return this.zzhl;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzds) {
            return this.zzhk.equals(((zzds) obj).zzhk);
        }
        return false;
    }

    public final int hashCode() {
        return this.zzhk.hashCode();
    }

    public final Iterator<Map.Entry<T, Object>> iterator() {
        if (this.zzhm) {
            return new zzen(this.zzhk.entrySet().iterator());
        }
        return this.zzhk.entrySet().iterator();
    }

    final Iterator<Map.Entry<T, Object>> descendingIterator() {
        if (this.zzhm) {
            return new zzen(this.zzhk.zzdf().iterator());
        }
        return this.zzhk.zzdf().iterator();
    }

    private final Object zza(T t) {
        Object obj = this.zzhk.get(t);
        if (!(obj instanceof zzei)) {
            return obj;
        }
        return zzei.zzca();
    }

    private final void zza(T t, Object obj) {
        if (t.zzbi()) {
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
                zza(t.zzbg(), obj2);
            }
            obj = arrayList;
        } else {
            zza(t.zzbg(), obj);
        }
        if (obj instanceof zzei) {
            this.zzhm = true;
        }
        this.zzhk.zza((zzfz<T, Object>) t, (T) obj);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void zza(com.google.android.gms.internal.icing.zzha r2, java.lang.Object r3) {
        /*
            com.google.android.gms.internal.icing.zzeb.checkNotNull(r3)
            int[] r0 = com.google.android.gms.internal.icing.zzdr.zzhi
            com.google.android.gms.internal.icing.zzhh r2 = r2.zzdt()
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
            boolean r2 = r3 instanceof com.google.android.gms.internal.icing.zzfh
            if (r2 != 0) goto L42
            boolean r2 = r3 instanceof com.google.android.gms.internal.icing.zzei
            if (r2 == 0) goto L14
            goto L42
        L1f:
            boolean r2 = r3 instanceof java.lang.Integer
            if (r2 != 0) goto L42
            boolean r2 = r3 instanceof com.google.android.gms.internal.icing.zzec
            if (r2 == 0) goto L14
            goto L42
        L28:
            boolean r2 = r3 instanceof com.google.android.gms.internal.icing.zzct
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzds.zza(com.google.android.gms.internal.icing.zzha, java.lang.Object):void");
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzhk.zzdd(); i++) {
            if (!zzb(this.zzhk.zzaj(i))) {
                return false;
            }
        }
        Iterator it = this.zzhk.zzde().iterator();
        while (it.hasNext()) {
            if (!zzb((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    private static <T extends zzdu<T>> boolean zzb(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        if (key.zzbh() == zzhh.MESSAGE) {
            if (key.zzbi()) {
                Iterator it = ((List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    if (!((zzfh) it.next()).isInitialized()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzfh) {
                    if (!((zzfh) value).isInitialized()) {
                        return false;
                    }
                } else {
                    if (value instanceof zzei) {
                        return true;
                    }
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzds<T> zzdsVar) {
        for (int i = 0; i < zzdsVar.zzhk.zzdd(); i++) {
            zzc(zzdsVar.zzhk.zzaj(i));
        }
        Iterator it = zzdsVar.zzhk.zzde().iterator();
        while (it.hasNext()) {
            zzc((Map.Entry) it.next());
        }
    }

    private static Object zzg(Object obj) {
        if (obj instanceof zzfn) {
            return ((zzfn) obj).clone();
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
        zzfh zzfhVarZzbx;
        T key = entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzei) {
            value = zzei.zzca();
        }
        if (key.zzbi()) {
            Object objZza = zza((zzds<T>) key);
            if (objZza == null) {
                objZza = new ArrayList();
            }
            Iterator it = ((List) value).iterator();
            while (it.hasNext()) {
                ((List) objZza).add(zzg(it.next()));
            }
            this.zzhk.zza((zzfz<T, Object>) key, (T) objZza);
            return;
        }
        if (key.zzbh() == zzhh.MESSAGE) {
            Object objZza2 = zza((zzds<T>) key);
            if (objZza2 == null) {
                this.zzhk.zza((zzfz<T, Object>) key, (T) zzg(value));
                return;
            }
            if (objZza2 instanceof zzfn) {
                zzfhVarZzbx = key.zza((zzfn) objZza2, (zzfn) value);
            } else {
                zzfhVarZzbx = key.zza(((zzfh) objZza2).zzbq(), (zzfh) value).zzbx();
            }
            this.zzhk.zza((zzfz<T, Object>) key, (T) zzfhVarZzbx);
            return;
        }
        this.zzhk.zza((zzfz<T, Object>) key, (T) zzg(value));
    }

    static void zza(zzdk zzdkVar, zzha zzhaVar, int i, Object obj) throws IOException {
        if (zzhaVar == zzha.zzpr) {
            zzfh zzfhVar = (zzfh) obj;
            zzeb.zzf(zzfhVar);
            zzdkVar.zzb(i, 3);
            zzfhVar.zzb(zzdkVar);
            zzdkVar.zzb(i, 4);
        }
        zzdkVar.zzb(i, zzhaVar.zzdu());
        switch (zzdr.zzhj[zzhaVar.ordinal()]) {
            case 1:
                zzdkVar.zza(((Double) obj).doubleValue());
                break;
            case 2:
                zzdkVar.zza(((Float) obj).floatValue());
                break;
            case 3:
                zzdkVar.zzb(((Long) obj).longValue());
                break;
            case 4:
                zzdkVar.zzb(((Long) obj).longValue());
                break;
            case 5:
                zzdkVar.zzo(((Integer) obj).intValue());
                break;
            case 6:
                zzdkVar.zzd(((Long) obj).longValue());
                break;
            case 7:
                zzdkVar.zzr(((Integer) obj).intValue());
                break;
            case 8:
                zzdkVar.zze(((Boolean) obj).booleanValue());
                break;
            case 9:
                ((zzfh) obj).zzb(zzdkVar);
                break;
            case 10:
                zzdkVar.zzb((zzfh) obj);
                break;
            case 11:
                if (obj instanceof zzct) {
                    zzdkVar.zza((zzct) obj);
                    break;
                } else {
                    zzdkVar.zzq((String) obj);
                    break;
                }
            case 12:
                if (obj instanceof zzct) {
                    zzdkVar.zza((zzct) obj);
                    break;
                } else {
                    byte[] bArr = (byte[]) obj;
                    zzdkVar.zzb(bArr, 0, bArr.length);
                    break;
                }
            case 13:
                zzdkVar.zzp(((Integer) obj).intValue());
                break;
            case 14:
                zzdkVar.zzr(((Integer) obj).intValue());
                break;
            case 15:
                zzdkVar.zzd(((Long) obj).longValue());
                break;
            case 16:
                zzdkVar.zzq(((Integer) obj).intValue());
                break;
            case 17:
                zzdkVar.zzc(((Long) obj).longValue());
                break;
            case 18:
                if (obj instanceof zzec) {
                    zzdkVar.zzo(((zzec) obj).zzbf());
                    break;
                } else {
                    zzdkVar.zzo(((Integer) obj).intValue());
                    break;
                }
        }
    }

    public final int zzbe() {
        int iZzd = 0;
        for (int i = 0; i < this.zzhk.zzdd(); i++) {
            iZzd += zzd(this.zzhk.zzaj(i));
        }
        Iterator it = this.zzhk.zzde().iterator();
        while (it.hasNext()) {
            iZzd += zzd((Map.Entry) it.next());
        }
        return iZzd;
    }

    private static int zzd(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        Object value = entry.getValue();
        if (key.zzbh() == zzhh.MESSAGE && !key.zzbi() && !key.zzbj()) {
            if (value instanceof zzei) {
                return zzdk.zzb(entry.getKey().zzbf(), (zzei) value);
            }
            return zzdk.zzb(entry.getKey().zzbf(), (zzfh) value);
        }
        return zzb((zzdu<?>) key, value);
    }

    static int zza(zzha zzhaVar, int i, Object obj) {
        int iZzs = zzdk.zzs(i);
        if (zzhaVar == zzha.zzpr) {
            zzeb.zzf((zzfh) obj);
            iZzs <<= 1;
        }
        return iZzs + zzb(zzhaVar, obj);
    }

    private static int zzb(zzha zzhaVar, Object obj) {
        switch (zzdr.zzhj[zzhaVar.ordinal()]) {
            case 1:
                return zzdk.zzb(((Double) obj).doubleValue());
            case 2:
                return zzdk.zzb(((Float) obj).floatValue());
            case 3:
                return zzdk.zze(((Long) obj).longValue());
            case 4:
                return zzdk.zzf(((Long) obj).longValue());
            case 5:
                return zzdk.zzt(((Integer) obj).intValue());
            case 6:
                return zzdk.zzh(((Long) obj).longValue());
            case 7:
                return zzdk.zzw(((Integer) obj).intValue());
            case 8:
                return zzdk.zzf(((Boolean) obj).booleanValue());
            case 9:
                return zzdk.zzd((zzfh) obj);
            case 10:
                if (obj instanceof zzei) {
                    return zzdk.zza((zzei) obj);
                }
                return zzdk.zzc((zzfh) obj);
            case 11:
                if (obj instanceof zzct) {
                    return zzdk.zzb((zzct) obj);
                }
                return zzdk.zzr((String) obj);
            case 12:
                if (obj instanceof zzct) {
                    return zzdk.zzb((zzct) obj);
                }
                return zzdk.zzc((byte[]) obj);
            case 13:
                return zzdk.zzu(((Integer) obj).intValue());
            case 14:
                return zzdk.zzx(((Integer) obj).intValue());
            case 15:
                return zzdk.zzi(((Long) obj).longValue());
            case 16:
                return zzdk.zzv(((Integer) obj).intValue());
            case 17:
                return zzdk.zzg(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzec) {
                    return zzdk.zzy(((zzec) obj).zzbf());
                }
                return zzdk.zzy(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zzb(zzdu<?> zzduVar, Object obj) {
        zzha zzhaVarZzbg = zzduVar.zzbg();
        int iZzbf = zzduVar.zzbf();
        if (zzduVar.zzbi()) {
            int iZza = 0;
            if (zzduVar.zzbj()) {
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    iZza += zzb(zzhaVarZzbg, it.next());
                }
                return zzdk.zzs(iZzbf) + iZza + zzdk.zzaa(iZza);
            }
            Iterator it2 = ((List) obj).iterator();
            while (it2.hasNext()) {
                iZza += zza(zzhaVarZzbg, iZzbf, it2.next());
            }
            return iZza;
        }
        return zza(zzhaVarZzbg, iZzbf, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzds zzdsVar = new zzds();
        for (int i = 0; i < this.zzhk.zzdd(); i++) {
            Map.Entry<K, Object> entryZzaj = this.zzhk.zzaj(i);
            zzdsVar.zza((zzds) entryZzaj.getKey(), entryZzaj.getValue());
        }
        Iterator it = this.zzhk.zzde().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzdsVar.zza((zzds) entry.getKey(), entry.getValue());
        }
        zzdsVar.zzhm = this.zzhm;
        return zzdsVar;
    }
}
