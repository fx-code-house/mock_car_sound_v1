package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzcg;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* loaded from: classes2.dex */
final class zzds<T> implements zzef<T> {
    private static final Unsafe zzmh = zzfd.zzef();
    private final int[] zzmi;
    private final Object[] zzmj;
    private final int zzmk;
    private final int zzml;
    private final int zzmm;
    private final zzdo zzmn;
    private final boolean zzmo;
    private final boolean zzmp;
    private final boolean zzmq;
    private final boolean zzmr;
    private final int[] zzms;
    private final int[] zzmt;
    private final int[] zzmu;
    private final zzdw zzmv;
    private final zzcy zzmw;
    private final zzex<?, ?> zzmx;
    private final zzbu<?> zzmy;
    private final zzdj zzmz;

    private zzds(int[] iArr, Object[] objArr, int i, int i2, int i3, zzdo zzdoVar, boolean z, boolean z2, int[] iArr2, int[] iArr3, int[] iArr4, zzdw zzdwVar, zzcy zzcyVar, zzex<?, ?> zzexVar, zzbu<?> zzbuVar, zzdj zzdjVar) {
        this.zzmi = iArr;
        this.zzmj = objArr;
        this.zzmk = i;
        this.zzml = i2;
        this.zzmm = i3;
        this.zzmp = zzdoVar instanceof zzcg;
        this.zzmq = z;
        this.zzmo = zzbuVar != null && zzbuVar.zze(zzdoVar);
        this.zzmr = false;
        this.zzms = iArr2;
        this.zzmt = iArr3;
        this.zzmu = iArr4;
        this.zzmv = zzdwVar;
        this.zzmw = zzcyVar;
        this.zzmx = zzexVar;
        this.zzmy = zzbuVar;
        this.zzmn = zzdoVar;
        this.zzmz = zzdjVar;
    }

    private static int zza(int i, byte[] bArr, int i2, int i3, Object obj, zzay zzayVar) throws IOException {
        return zzax.zza(i, bArr, i2, i3, zzn(obj), zzayVar);
    }

    private static int zza(zzef<?> zzefVar, int i, byte[] bArr, int i2, int i3, zzcn<?> zzcnVar, zzay zzayVar) throws IOException {
        int iZza = zza((zzef) zzefVar, bArr, i2, i3, zzayVar);
        while (true) {
            zzcnVar.add(zzayVar.zzff);
            if (iZza >= i3) {
                break;
            }
            int iZza2 = zzax.zza(bArr, iZza, zzayVar);
            if (i != zzayVar.zzfd) {
                break;
            }
            iZza = zza((zzef) zzefVar, bArr, iZza2, i3, zzayVar);
        }
        return iZza;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int zza(zzef zzefVar, byte[] bArr, int i, int i2, int i3, zzay zzayVar) throws IOException {
        zzds zzdsVar = (zzds) zzefVar;
        Object objNewInstance = zzdsVar.newInstance();
        int iZza = zzdsVar.zza((zzds) objNewInstance, bArr, i, i2, i3, zzayVar);
        zzdsVar.zzc(objNewInstance);
        zzayVar.zzff = objNewInstance;
        return iZza;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int zza(zzef zzefVar, byte[] bArr, int i, int i2, zzay zzayVar) throws IOException {
        int iZza = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZza = zzax.zza(i3, bArr, iZza, zzayVar);
            i3 = zzayVar.zzfd;
        }
        int i4 = iZza;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzco.zzbl();
        }
        Object objNewInstance = zzefVar.newInstance();
        int i5 = i3 + i4;
        zzefVar.zza(objNewInstance, bArr, i4, i5, zzayVar);
        zzefVar.zzc(objNewInstance);
        zzayVar.zzff = objNewInstance;
        return i5;
    }

    private static <UT, UB> int zza(zzex<UT, UB> zzexVar, T t) {
        return zzexVar.zzm(zzexVar.zzq(t));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzay zzayVar) throws IOException {
        Object objValueOf;
        Object objValueOf2;
        int iZzb;
        long jZza;
        int iZzm;
        Object objValueOf3;
        int i9;
        Unsafe unsafe = zzmh;
        long j2 = this.zzmi[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    objValueOf = Double.valueOf(zzax.zze(bArr, i));
                    unsafe.putObject(t, j, objValueOf);
                    iZzb = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 52:
                if (i5 == 5) {
                    objValueOf2 = Float.valueOf(zzax.zzf(bArr, i));
                    unsafe.putObject(t, j, objValueOf2);
                    iZzb = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 53:
            case 54:
                if (i5 == 0) {
                    iZzb = zzax.zzb(bArr, i, zzayVar);
                    jZza = zzayVar.zzfe;
                    objValueOf3 = Long.valueOf(jZza);
                    unsafe.putObject(t, j, objValueOf3);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 55:
            case 62:
                if (i5 == 0) {
                    iZzb = zzax.zza(bArr, i, zzayVar);
                    iZzm = zzayVar.zzfd;
                    objValueOf3 = Integer.valueOf(iZzm);
                    unsafe.putObject(t, j, objValueOf3);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 56:
            case 65:
                if (i5 == 1) {
                    objValueOf = Long.valueOf(zzax.zzd(bArr, i));
                    unsafe.putObject(t, j, objValueOf);
                    iZzb = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 57:
            case 64:
                if (i5 == 5) {
                    objValueOf2 = Integer.valueOf(zzax.zzc(bArr, i));
                    unsafe.putObject(t, j, objValueOf2);
                    iZzb = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 58:
                if (i5 == 0) {
                    iZzb = zzax.zzb(bArr, i, zzayVar);
                    objValueOf3 = Boolean.valueOf(zzayVar.zzfe != 0);
                    unsafe.putObject(t, j, objValueOf3);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 59:
                if (i5 == 2) {
                    iZzb = zzax.zza(bArr, i, zzayVar);
                    i9 = zzayVar.zzfd;
                    if (i9 == 0) {
                        objValueOf3 = "";
                        unsafe.putObject(t, j, objValueOf3);
                        unsafe.putInt(t, j2, i4);
                        return iZzb;
                    }
                    if ((i6 & 536870912) != 0 && !zzff.zze(bArr, iZzb, iZzb + i9)) {
                        throw zzco.zzbp();
                    }
                    unsafe.putObject(t, j, new String(bArr, iZzb, i9, zzci.UTF_8));
                    iZzb += i9;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 60:
                if (i5 == 2) {
                    iZzb = zza(zzad(i8), bArr, i, i2, zzayVar);
                    Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    objValueOf3 = zzayVar.zzff;
                    if (object != null) {
                        objValueOf3 = zzci.zza(object, objValueOf3);
                    }
                    unsafe.putObject(t, j, objValueOf3);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 61:
                if (i5 == 2) {
                    iZzb = zzax.zza(bArr, i, zzayVar);
                    i9 = zzayVar.zzfd;
                    if (i9 == 0) {
                        objValueOf3 = zzbb.zzfi;
                        unsafe.putObject(t, j, objValueOf3);
                        unsafe.putInt(t, j2, i4);
                        return iZzb;
                    }
                    unsafe.putObject(t, j, zzbb.zzb(bArr, iZzb, i9));
                    iZzb += i9;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 63:
                if (i5 == 0) {
                    int iZza = zzax.zza(bArr, i, zzayVar);
                    int i10 = zzayVar.zzfd;
                    zzck<?> zzckVarZzaf = zzaf(i8);
                    if (zzckVarZzaf != null && zzckVarZzaf.zzb(i10) == null) {
                        zzn(t).zzb(i3, Long.valueOf(i10));
                        return iZza;
                    }
                    unsafe.putObject(t, j, Integer.valueOf(i10));
                    iZzb = iZza;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 66:
                if (i5 == 0) {
                    iZzb = zzax.zza(bArr, i, zzayVar);
                    iZzm = zzbk.zzm(zzayVar.zzfd);
                    objValueOf3 = Integer.valueOf(iZzm);
                    unsafe.putObject(t, j, objValueOf3);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 67:
                if (i5 == 0) {
                    iZzb = zzax.zzb(bArr, i, zzayVar);
                    jZza = zzbk.zza(zzayVar.zzfe);
                    objValueOf3 = Long.valueOf(jZza);
                    unsafe.putObject(t, j, objValueOf3);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 68:
                if (i5 == 3) {
                    iZzb = zza(zzad(i8), bArr, i, i2, (i3 & (-8)) | 4, zzayVar);
                    Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    objValueOf3 = zzayVar.zzff;
                    if (object2 != null) {
                        objValueOf3 = zzci.zza(object2, objValueOf3);
                    }
                    unsafe.putObject(t, j, objValueOf3);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            default:
                return i;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0233, code lost:
    
        if (r29.zzfe != 0) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0235, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0237, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0238, code lost:
    
        r12.addBoolean(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x023b, code lost:
    
        if (r4 >= r19) goto L245;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x023d, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzax.zza(r17, r4, r29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0243, code lost:
    
        if (r20 != r29.zzfd) goto L246;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0245, code lost:
    
        r4 = com.google.android.gms.internal.clearcut.zzax.zzb(r17, r6, r29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x024d, code lost:
    
        if (r29.zzfe == 0) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x0147, code lost:
    
        r12.add(com.google.android.gms.internal.clearcut.zzbb.zzb(r17, r1, r4));
        r1 = r1 + r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0238, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:?, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:281:?, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0137, code lost:
    
        if (r4 == 0) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0139, code lost:
    
        r12.add(com.google.android.gms.internal.clearcut.zzbb.zzfi);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x013f, code lost:
    
        r12.add(com.google.android.gms.internal.clearcut.zzbb.zzb(r17, r1, r4));
        r1 = r1 + r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0147, code lost:
    
        if (r1 >= r19) goto L229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0149, code lost:
    
        r4 = com.google.android.gms.internal.clearcut.zzax.zza(r17, r1, r29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x014f, code lost:
    
        if (r20 != r29.zzfd) goto L230;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0151, code lost:
    
        r1 = com.google.android.gms.internal.clearcut.zzax.zza(r17, r4, r29);
        r4 = r29.zzfd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0157, code lost:
    
        if (r4 != 0) goto L65;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:83:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01d4  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:100:0x01e2 -> B:91:0x01bb). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:132:0x024d -> B:125:0x0235). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:70:0x0157 -> B:64:0x0139). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:86:0x01a8 -> B:79:0x0189). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zza(T r16, byte[] r17, int r18, int r19, int r20, int r21, int r22, int r23, long r24, int r26, long r27, com.google.android.gms.internal.clearcut.zzay r29) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 994
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, byte[], int, int, int, int, int, int, long, int, long, com.google.android.gms.internal.clearcut.zzay):int");
    }

    private final <K, V> int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, long j, zzay zzayVar) throws IOException {
        Unsafe unsafe = zzmh;
        Object objZzae = zzae(i3);
        Object object = unsafe.getObject(t, j);
        if (this.zzmz.zzi(object)) {
            Object objZzk = this.zzmz.zzk(objZzae);
            this.zzmz.zzb(objZzk, object);
            unsafe.putObject(t, j, objZzk);
            object = objZzk;
        }
        zzdh<?, ?> zzdhVarZzl = this.zzmz.zzl(objZzae);
        Map<?, ?> mapZzg = this.zzmz.zzg(object);
        int iZza = zzax.zza(bArr, i, zzayVar);
        int i5 = zzayVar.zzfd;
        if (i5 < 0 || i5 > i2 - iZza) {
            throw zzco.zzbl();
        }
        int i6 = i5 + iZza;
        K k = zzdhVarZzl.zzmc;
        V v = zzdhVarZzl.zzdu;
        while (iZza < i6) {
            int iZza2 = iZza + 1;
            int i7 = bArr[iZza];
            if (i7 < 0) {
                iZza2 = zzax.zza(i7, bArr, iZza2, zzayVar);
                i7 = zzayVar.zzfd;
            }
            int i8 = iZza2;
            int i9 = i7 >>> 3;
            int i10 = i7 & 7;
            if (i9 != 1) {
                if (i9 == 2 && i10 == zzdhVarZzl.zzmd.zzel()) {
                    iZza = zza(bArr, i8, i2, zzdhVarZzl.zzmd, zzdhVarZzl.zzdu.getClass(), zzayVar);
                    v = zzayVar.zzff;
                } else {
                    iZza = zzax.zza(i7, bArr, i8, i2, zzayVar);
                }
            } else if (i10 == zzdhVarZzl.zzmb.zzel()) {
                iZza = zza(bArr, i8, i2, zzdhVarZzl.zzmb, (Class<?>) null, zzayVar);
                k = (K) zzayVar.zzff;
            } else {
                iZza = zzax.zza(i7, bArr, i8, i2, zzayVar);
            }
        }
        if (iZza != i6) {
            throw zzco.zzbo();
        }
        mapZzg.put(k, v);
        return i6;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:19:0x0069. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0372 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zza(T r27, byte[] r28, int r29, int r30, int r31, com.google.android.gms.internal.clearcut.zzay r32) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1072
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.clearcut.zzay):int");
    }

    private static int zza(byte[] bArr, int i, int i2, zzfl zzflVar, Class<?> cls, zzay zzayVar) throws IOException {
        int iZzb;
        Object objValueOf;
        Object objValueOf2;
        Object objValueOf3;
        int iZzm;
        long jZza;
        switch (zzdt.zzgq[zzflVar.ordinal()]) {
            case 1:
                iZzb = zzax.zzb(bArr, i, zzayVar);
                objValueOf = Boolean.valueOf(zzayVar.zzfe != 0);
                zzayVar.zzff = objValueOf;
                return iZzb;
            case 2:
                return zzax.zze(bArr, i, zzayVar);
            case 3:
                objValueOf2 = Double.valueOf(zzax.zze(bArr, i));
                zzayVar.zzff = objValueOf2;
                return i + 8;
            case 4:
            case 5:
                objValueOf3 = Integer.valueOf(zzax.zzc(bArr, i));
                zzayVar.zzff = objValueOf3;
                return i + 4;
            case 6:
            case 7:
                objValueOf2 = Long.valueOf(zzax.zzd(bArr, i));
                zzayVar.zzff = objValueOf2;
                return i + 8;
            case 8:
                objValueOf3 = Float.valueOf(zzax.zzf(bArr, i));
                zzayVar.zzff = objValueOf3;
                return i + 4;
            case 9:
            case 10:
            case 11:
                iZzb = zzax.zza(bArr, i, zzayVar);
                iZzm = zzayVar.zzfd;
                objValueOf = Integer.valueOf(iZzm);
                zzayVar.zzff = objValueOf;
                return iZzb;
            case 12:
            case 13:
                iZzb = zzax.zzb(bArr, i, zzayVar);
                jZza = zzayVar.zzfe;
                objValueOf = Long.valueOf(jZza);
                zzayVar.zzff = objValueOf;
                return iZzb;
            case 14:
                return zza((zzef) zzea.zzcm().zze(cls), bArr, i, i2, zzayVar);
            case 15:
                iZzb = zzax.zza(bArr, i, zzayVar);
                iZzm = zzbk.zzm(zzayVar.zzfd);
                objValueOf = Integer.valueOf(iZzm);
                zzayVar.zzff = objValueOf;
                return iZzb;
            case 16:
                iZzb = zzax.zzb(bArr, i, zzayVar);
                jZza = zzbk.zza(zzayVar.zzfe);
                objValueOf = Long.valueOf(jZza);
                zzayVar.zzff = objValueOf;
                return iZzb;
            case 17:
                return zzax.zzd(bArr, i, zzayVar);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    static <T> zzds<T> zza(Class<T> cls, zzdm zzdmVar, zzdw zzdwVar, zzcy zzcyVar, zzex<?, ?> zzexVar, zzbu<?> zzbuVar, zzdj zzdjVar) {
        int iZzcu;
        int i;
        int i2;
        int iZza;
        int iZza2;
        int iZzdg;
        if (!(zzdmVar instanceof zzec)) {
            ((zzes) zzdmVar).zzcf();
            throw new NoSuchMethodError();
        }
        zzec zzecVar = (zzec) zzdmVar;
        boolean z = zzecVar.zzcf() == zzcg.zzg.zzkm;
        if (zzecVar.getFieldCount() == 0) {
            iZzcu = 0;
            i = 0;
            i2 = 0;
        } else {
            int iZzcp = zzecVar.zzcp();
            int iZzcq = zzecVar.zzcq();
            iZzcu = zzecVar.zzcu();
            i = iZzcp;
            i2 = iZzcq;
        }
        int[] iArr = new int[iZzcu << 2];
        Object[] objArr = new Object[iZzcu << 1];
        int[] iArr2 = zzecVar.zzcr() > 0 ? new int[zzecVar.zzcr()] : null;
        int[] iArr3 = zzecVar.zzcs() > 0 ? new int[zzecVar.zzcs()] : null;
        zzed zzedVarZzco = zzecVar.zzco();
        if (zzedVarZzco.next()) {
            int iZzcx = zzedVarZzco.zzcx();
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (true) {
                if (iZzcx >= zzecVar.zzcv() || i3 >= ((iZzcx - i) << 2)) {
                    if (zzedVarZzco.zzda()) {
                        iZza = (int) zzfd.zza(zzedVarZzco.zzdb());
                        iZza2 = (int) zzfd.zza(zzedVarZzco.zzdc());
                        iZzdg = 0;
                    } else {
                        iZza = (int) zzfd.zza(zzedVarZzco.zzdd());
                        if (zzedVarZzco.zzde()) {
                            iZza2 = (int) zzfd.zza(zzedVarZzco.zzdf());
                            iZzdg = zzedVarZzco.zzdg();
                        } else {
                            iZza2 = 0;
                            iZzdg = 0;
                        }
                    }
                    iArr[i3] = zzedVarZzco.zzcx();
                    int i6 = i3 + 1;
                    iArr[i6] = (zzedVarZzco.zzdi() ? 536870912 : 0) | (zzedVarZzco.zzdh() ? 268435456 : 0) | (zzedVarZzco.zzcy() << 20) | iZza;
                    iArr[i3 + 2] = iZza2 | (iZzdg << 20);
                    if (zzedVarZzco.zzdl() != null) {
                        int i7 = (i3 / 4) << 1;
                        objArr[i7] = zzedVarZzco.zzdl();
                        if (zzedVarZzco.zzdj() != null) {
                            objArr[i7 + 1] = zzedVarZzco.zzdj();
                        } else if (zzedVarZzco.zzdk() != null) {
                            objArr[i7 + 1] = zzedVarZzco.zzdk();
                        }
                    } else if (zzedVarZzco.zzdj() != null) {
                        objArr[((i3 / 4) << 1) + 1] = zzedVarZzco.zzdj();
                    } else if (zzedVarZzco.zzdk() != null) {
                        objArr[((i3 / 4) << 1) + 1] = zzedVarZzco.zzdk();
                    }
                    int iZzcy = zzedVarZzco.zzcy();
                    if (iZzcy == zzcb.MAP.ordinal()) {
                        iArr2[i4] = i3;
                        i4++;
                    } else if (iZzcy >= 18 && iZzcy <= 49) {
                        iArr3[i5] = iArr[i6] & 1048575;
                        i5++;
                    }
                    if (!zzedVarZzco.next()) {
                        break;
                    }
                    iZzcx = zzedVarZzco.zzcx();
                } else {
                    for (int i8 = 0; i8 < 4; i8++) {
                        iArr[i3 + i8] = -1;
                    }
                }
                i3 += 4;
            }
        }
        return new zzds<>(iArr, objArr, i, i2, zzecVar.zzcv(), zzecVar.zzch(), z, false, zzecVar.zzct(), iArr2, iArr3, zzdwVar, zzcyVar, zzexVar, zzbuVar, zzdjVar);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzck<?> zzckVar, UB ub, zzex<UT, UB> zzexVar) {
        zzdh<?, ?> zzdhVarZzl = this.zzmz.zzl(zzae(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (zzckVar.zzb(((Integer) next.getValue()).intValue()) == null) {
                if (ub == null) {
                    ub = zzexVar.zzdz();
                }
                zzbg zzbgVarZzk = zzbb.zzk(zzdg.zza(zzdhVarZzl, next.getKey(), next.getValue()));
                try {
                    zzdg.zza(zzbgVarZzk.zzae(), zzdhVarZzl, next.getKey(), next.getValue());
                    zzexVar.zza((zzex<UT, UB>) ub, i2, zzbgVarZzk.zzad());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    private static void zza(int i, Object obj, zzfr zzfrVar) throws IOException {
        if (obj instanceof String) {
            zzfrVar.zza(i, (String) obj);
        } else {
            zzfrVar.zza(i, (zzbb) obj);
        }
    }

    private static <UT, UB> void zza(zzex<UT, UB> zzexVar, T t, zzfr zzfrVar) throws IOException {
        zzexVar.zza(zzexVar.zzq(t), zzfrVar);
    }

    private final <K, V> void zza(zzfr zzfrVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzfrVar.zza(i, this.zzmz.zzl(zzae(i2)), this.zzmz.zzh(obj));
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzag = zzag(i) & 1048575;
        if (zza((zzds<T>) t2, i)) {
            Object objZzo = zzfd.zzo(t, jZzag);
            Object objZzo2 = zzfd.zzo(t2, jZzag);
            if (objZzo != null && objZzo2 != null) {
                zzfd.zza(t, jZzag, zzci.zza(objZzo, objZzo2));
                zzb((zzds<T>) t, i);
            } else if (objZzo2 != null) {
                zzfd.zza(t, jZzag, objZzo2);
                zzb((zzds<T>) t, i);
            }
        }
    }

    private final boolean zza(T t, int i) {
        if (!this.zzmq) {
            int iZzah = zzah(i);
            return (zzfd.zzj(t, (long) (iZzah & 1048575)) & (1 << (iZzah >>> 20))) != 0;
        }
        int iZzag = zzag(i);
        long j = iZzag & 1048575;
        switch ((iZzag & 267386880) >>> 20) {
            case 0:
                return zzfd.zzn(t, j) != 0.0d;
            case 1:
                return zzfd.zzm(t, j) != 0.0f;
            case 2:
                return zzfd.zzk(t, j) != 0;
            case 3:
                return zzfd.zzk(t, j) != 0;
            case 4:
                return zzfd.zzj(t, j) != 0;
            case 5:
                return zzfd.zzk(t, j) != 0;
            case 6:
                return zzfd.zzj(t, j) != 0;
            case 7:
                return zzfd.zzl(t, j);
            case 8:
                Object objZzo = zzfd.zzo(t, j);
                if (objZzo instanceof String) {
                    return !((String) objZzo).isEmpty();
                }
                if (objZzo instanceof zzbb) {
                    return !zzbb.zzfi.equals(objZzo);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzfd.zzo(t, j) != null;
            case 10:
                return !zzbb.zzfi.equals(zzfd.zzo(t, j));
            case 11:
                return zzfd.zzj(t, j) != 0;
            case 12:
                return zzfd.zzj(t, j) != 0;
            case 13:
                return zzfd.zzj(t, j) != 0;
            case 14:
                return zzfd.zzk(t, j) != 0;
            case 15:
                return zzfd.zzj(t, j) != 0;
            case 16:
                return zzfd.zzk(t, j) != 0;
            case 17:
                return zzfd.zzo(t, j) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzfd.zzj(t, (long) (zzah(i2) & 1048575)) == i;
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        return this.zzmq ? zza((zzds<T>) t, i) : (i2 & i3) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzef zzefVar) {
        return zzefVar.zzo(zzfd.zzo(obj, i & 1048575));
    }

    private final zzef zzad(int i) {
        int i2 = (i / 4) << 1;
        zzef zzefVar = (zzef) this.zzmj[i2];
        if (zzefVar != null) {
            return zzefVar;
        }
        zzef<T> zzefVarZze = zzea.zzcm().zze((Class) this.zzmj[i2 + 1]);
        this.zzmj[i2] = zzefVarZze;
        return zzefVarZze;
    }

    private final Object zzae(int i) {
        return this.zzmj[(i / 4) << 1];
    }

    private final zzck<?> zzaf(int i) {
        return (zzck) this.zzmj[((i / 4) << 1) + 1];
    }

    private final int zzag(int i) {
        return this.zzmi[i + 1];
    }

    private final int zzah(int i) {
        return this.zzmi[i + 2];
    }

    private final int zzai(int i) {
        int i2 = this.zzmk;
        if (i >= i2) {
            int i3 = this.zzmm;
            if (i < i3) {
                int i4 = (i - i2) << 2;
                if (this.zzmi[i4] == i) {
                    return i4;
                }
                return -1;
            }
            if (i <= this.zzml) {
                int i5 = i3 - i2;
                int length = (this.zzmi.length / 4) - 1;
                while (i5 <= length) {
                    int i6 = (length + i5) >>> 1;
                    int i7 = i6 << 2;
                    int i8 = this.zzmi[i7];
                    if (i == i8) {
                        return i7;
                    }
                    if (i < i8) {
                        length = i6 - 1;
                    } else {
                        i5 = i6 + 1;
                    }
                }
            }
        }
        return -1;
    }

    private final void zzb(T t, int i) {
        if (this.zzmq) {
            return;
        }
        int iZzah = zzah(i);
        long j = iZzah & 1048575;
        zzfd.zza((Object) t, j, zzfd.zzj(t, j) | (1 << (iZzah >>> 20)));
    }

    private final void zzb(T t, int i, int i2) {
        zzfd.zza((Object) t, zzah(i2) & 1048575, i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void zzb(T r18, com.google.android.gms.internal.clearcut.zzfr r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1332
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzb(java.lang.Object, com.google.android.gms.internal.clearcut.zzfr):void");
    }

    private final void zzb(T t, T t2, int i) {
        int iZzag = zzag(i);
        int i2 = this.zzmi[i];
        long j = iZzag & 1048575;
        if (zza((zzds<T>) t2, i2, i)) {
            Object objZzo = zzfd.zzo(t, j);
            Object objZzo2 = zzfd.zzo(t2, j);
            if (objZzo != null && objZzo2 != null) {
                zzfd.zza(t, j, zzci.zza(objZzo, objZzo2));
                zzb((zzds<T>) t, i2, i);
            } else if (objZzo2 != null) {
                zzfd.zza(t, j, objZzo2);
                zzb((zzds<T>) t, i2, i);
            }
        }
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((zzds<T>) t, i) == zza((zzds<T>) t2, i);
    }

    private static <E> List<E> zzd(Object obj, long j) {
        return (List) zzfd.zzo(obj, j);
    }

    private static <T> double zze(T t, long j) {
        return ((Double) zzfd.zzo(t, j)).doubleValue();
    }

    private static <T> float zzf(T t, long j) {
        return ((Float) zzfd.zzo(t, j)).floatValue();
    }

    private static <T> int zzg(T t, long j) {
        return ((Integer) zzfd.zzo(t, j)).intValue();
    }

    private static <T> long zzh(T t, long j) {
        return ((Long) zzfd.zzo(t, j)).longValue();
    }

    private static <T> boolean zzi(T t, long j) {
        return ((Boolean) zzfd.zzo(t, j)).booleanValue();
    }

    private static zzey zzn(Object obj) {
        zzcg zzcgVar = (zzcg) obj;
        zzey zzeyVar = zzcgVar.zzjp;
        if (zzeyVar != zzey.zzea()) {
            return zzeyVar;
        }
        zzey zzeyVarZzeb = zzey.zzeb();
        zzcgVar.zzjp = zzeyVarZzeb;
        return zzeyVarZzeb;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01a2  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(T r10, T r11) {
        /*
            Method dump skipped, instructions count: 610
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.equals(java.lang.Object, java.lang.Object):boolean");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00e2 A[PHI: r3
      0x00e2: PHI (r3v13 java.lang.Object) = (r3v11 java.lang.Object), (r3v14 java.lang.Object) binds: [B:67:0x00e0, B:62:0x00ce] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int hashCode(T r9) {
        /*
            Method dump skipped, instructions count: 476
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.hashCode(java.lang.Object):int");
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final T newInstance() {
        return (T) this.zzmv.newInstance(this.zzmn);
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0400  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0413  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0428  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x04ee  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0842  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x08bd  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x08d0  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x08e5  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0030  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r14, com.google.android.gms.internal.clearcut.zzfr r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2736
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, com.google.android.gms.internal.clearcut.zzfr):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x0164, code lost:
    
        if (r0 == r15) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0188, code lost:
    
        if (r0 == r15) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01a1, code lost:
    
        if (r0 == r15) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01a3, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v25, types: [int] */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r23, byte[] r24, int r25, int r26, com.google.android.gms.internal.clearcut.zzay r27) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 518
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.clearcut.zzay):void");
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(T t) {
        int[] iArr = this.zzmt;
        if (iArr != null) {
            for (int i : iArr) {
                long jZzag = zzag(i) & 1048575;
                Object objZzo = zzfd.zzo(t, jZzag);
                if (objZzo != null) {
                    zzfd.zza(t, jZzag, this.zzmz.zzj(objZzo));
                }
            }
        }
        int[] iArr2 = this.zzmu;
        if (iArr2 != null) {
            for (int i2 : iArr2) {
                this.zzmw.zza(t, i2);
            }
        }
        this.zzmx.zzc(t);
        if (this.zzmo) {
            this.zzmy.zzc(t);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0032  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zzc(T r7, T r8) {
        /*
            Method dump skipped, instructions count: 404
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzc(java.lang.Object, java.lang.Object):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x0414, code lost:
    
        if (zza((com.google.android.gms.internal.clearcut.zzds<T>) r20, r15, r5) != false) goto L396;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x0434, code lost:
    
        if (zza((com.google.android.gms.internal.clearcut.zzds<T>) r20, r15, r5) != false) goto L407;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x043c, code lost:
    
        if (zza((com.google.android.gms.internal.clearcut.zzds<T>) r20, r15, r5) != false) goto L410;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x045c, code lost:
    
        if (zza((com.google.android.gms.internal.clearcut.zzds<T>) r20, r15, r5) != false) goto L422;
     */
    /* JADX WARN: Code restructure failed: missing block: B:272:0x0464, code lost:
    
        if (zza((com.google.android.gms.internal.clearcut.zzds<T>) r20, r15, r5) != false) goto L426;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x047c, code lost:
    
        if (zza((com.google.android.gms.internal.clearcut.zzds<T>) r20, r15, r5) != false) goto L435;
     */
    /* JADX WARN: Code restructure failed: missing block: B:395:0x06c4, code lost:
    
        if ((r12 & r18) != 0) goto L396;
     */
    /* JADX WARN: Code restructure failed: missing block: B:396:0x06c6, code lost:
    
        r4 = com.google.android.gms.internal.clearcut.zzbn.zzc(r15, (com.google.android.gms.internal.clearcut.zzdo) r2.getObject(r20, r10), zzad(r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:406:0x06f1, code lost:
    
        if ((r12 & r18) != 0) goto L407;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x06f3, code lost:
    
        r4 = com.google.android.gms.internal.clearcut.zzbn.zzh(r15, 0L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x06fc, code lost:
    
        if ((r12 & r18) != 0) goto L410;
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x06fe, code lost:
    
        r9 = com.google.android.gms.internal.clearcut.zzbn.zzk(r15, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x0721, code lost:
    
        if ((r12 & r18) != 0) goto L422;
     */
    /* JADX WARN: Code restructure failed: missing block: B:422:0x0723, code lost:
    
        r4 = r2.getObject(r20, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:425:0x0730, code lost:
    
        if ((r12 & r18) != 0) goto L426;
     */
    /* JADX WARN: Code restructure failed: missing block: B:426:0x0732, code lost:
    
        r4 = com.google.android.gms.internal.clearcut.zzeh.zzc(r15, r2.getObject(r20, r10), zzad(r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:434:0x0757, code lost:
    
        if ((r12 & r18) != 0) goto L435;
     */
    /* JADX WARN: Code restructure failed: missing block: B:435:0x0759, code lost:
    
        r4 = com.google.android.gms.internal.clearcut.zzbn.zzc(r15, true);
     */
    /* JADX WARN: Removed duplicated region for block: B:142:0x020d A[PHI: r5
      0x020d: PHI (r5v71 int) = 
      (r5v34 int)
      (r5v37 int)
      (r5v40 int)
      (r5v43 int)
      (r5v46 int)
      (r5v49 int)
      (r5v52 int)
      (r5v55 int)
      (r5v58 int)
      (r5v61 int)
      (r5v64 int)
      (r5v67 int)
      (r5v70 int)
      (r5v75 int)
     binds: [B:141:0x020b, B:136:0x01fa, B:131:0x01e9, B:126:0x01d8, B:121:0x01c7, B:116:0x01b6, B:111:0x01a5, B:106:0x0193, B:101:0x0181, B:96:0x016f, B:91:0x015d, B:86:0x014b, B:81:0x0139, B:76:0x0127] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x030a A[PHI: r5
      0x030a: PHI (r5v94 java.lang.Object) = (r5v12 java.lang.Object), (r5v92 java.lang.Object), (r5v96 java.lang.Object) binds: [B:193:0x0331, B:45:0x00ab, B:185:0x0306] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0334 A[PHI: r5
      0x0334: PHI (r5v90 java.lang.Object) = (r5v12 java.lang.Object), (r5v92 java.lang.Object) binds: [B:193:0x0331, B:45:0x00ab] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:374:0x05fa A[PHI: r4
      0x05fa: PHI (r4v115 int) = 
      (r4v78 int)
      (r4v81 int)
      (r4v84 int)
      (r4v87 int)
      (r4v90 int)
      (r4v93 int)
      (r4v96 int)
      (r4v99 int)
      (r4v102 int)
      (r4v105 int)
      (r4v108 int)
      (r4v111 int)
      (r4v114 int)
      (r4v119 int)
     binds: [B:373:0x05f8, B:368:0x05e7, B:363:0x05d6, B:358:0x05c5, B:353:0x05b4, B:348:0x05a3, B:343:0x0592, B:338:0x0580, B:333:0x056e, B:328:0x055c, B:323:0x054a, B:318:0x0538, B:313:0x0526, B:308:0x0514] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:392:0x06ba A[PHI: r6
      0x06ba: PHI (r6v4 int) = 
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v14 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v15 int)
      (r6v1 int)
     binds: [B:245:0x040b, B:437:0x0761, B:434:0x0757, B:428:0x0742, B:425:0x0730, B:421:0x0721, B:417:0x0714, B:413:0x0707, B:409:0x06fc, B:406:0x06f1, B:402:0x06e4, B:398:0x06d7, B:395:0x06c4, B:371:0x05f4, B:366:0x05e3, B:361:0x05d2, B:356:0x05c1, B:351:0x05b0, B:346:0x059f, B:341:0x058e, B:336:0x057c, B:331:0x056a, B:326:0x0558, B:321:0x0546, B:316:0x0534, B:311:0x0522, B:306:0x0510, B:301:0x04dc, B:298:0x04cf, B:295:0x04bf, B:292:0x04af, B:289:0x049f, B:286:0x0491, B:283:0x0484, B:280:0x047c, B:275:0x046c, B:272:0x0464, B:269:0x045c, B:266:0x0450, B:263:0x0444, B:411:0x0703, B:260:0x043c, B:257:0x0434, B:254:0x0428, B:251:0x041c, B:391:0x06b9, B:248:0x0414] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:423:0x0727 A[PHI: r4
      0x0727: PHI (r4v150 java.lang.Object) = (r4v16 java.lang.Object), (r4v146 java.lang.Object), (r4v153 java.lang.Object) binds: [B:430:0x074a, B:277:0x0474, B:422:0x0723] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:432:0x074d A[PHI: r4
      0x074d: PHI (r4v142 java.lang.Object) = (r4v16 java.lang.Object), (r4v146 java.lang.Object) binds: [B:430:0x074a, B:277:0x0474] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int zzm(T r20) {
        /*
            Method dump skipped, instructions count: 2306
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzm(java.lang.Object):int");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00c9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0107 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0119 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0119 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22, types: [com.google.android.gms.internal.clearcut.zzef] */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v8, types: [com.google.android.gms.internal.clearcut.zzef] */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzo(T r17) {
        /*
            Method dump skipped, instructions count: 308
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzo(java.lang.Object):boolean");
    }
}
