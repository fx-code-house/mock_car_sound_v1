package com.google.android.gms.internal.play_billing;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import javax.annotation.CheckForNull;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzaf extends zzx {
    static final zzx zza = new zzaf(null, new Object[0], 0);
    final transient Object[] zzb;

    @CheckForNull
    private final transient Object zzc;
    private final transient int zzd;

    private zzaf(@CheckForNull Object obj, Object[] objArr, int i) {
        this.zzc = obj;
        this.zzb = objArr;
        this.zzd = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r5v4, types: [int[]] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.lang.Object[]] */
    static zzaf zzf(int i, Object[] objArr, zzw zzwVar) {
        int iHighestOneBit;
        short[] sArr;
        Object[] objArr2;
        int i2 = i;
        Object[] objArrCopyOf = objArr;
        if (i2 == 0) {
            return (zzaf) zza;
        }
        Object obj = null;
        if (i2 == 1) {
            Object obj2 = objArrCopyOf[0];
            obj2.getClass();
            Object obj3 = objArrCopyOf[1];
            obj3.getClass();
            zzp.zza(obj2, obj3);
            return new zzaf(null, objArrCopyOf, 1);
        }
        zzm.zzb(i2, objArrCopyOf.length >> 1, FirebaseAnalytics.Param.INDEX);
        char c = 2;
        int iMax = Math.max(i2, 2);
        if (iMax < 751619276) {
            iHighestOneBit = Integer.highestOneBit(iMax - 1);
            do {
                iHighestOneBit += iHighestOneBit;
            } while (iHighestOneBit * 0.7d < iMax);
        } else {
            iHighestOneBit = 1073741824;
            if (iMax >= 1073741824) {
                throw new IllegalArgumentException("collection too large");
            }
        }
        if (i2 == 1) {
            Object obj4 = objArrCopyOf[0];
            obj4.getClass();
            Object obj5 = objArrCopyOf[1];
            obj5.getClass();
            zzp.zza(obj4, obj5);
        } else {
            int i3 = iHighestOneBit - 1;
            char c2 = 65535;
            if (iHighestOneBit <= 128) {
                byte[] bArr = new byte[iHighestOneBit];
                Arrays.fill(bArr, (byte) -1);
                int i4 = 0;
                for (int i5 = 0; i5 < i2; i5++) {
                    int i6 = i4 + i4;
                    int i7 = i5 + i5;
                    Object obj6 = objArrCopyOf[i7];
                    obj6.getClass();
                    Object obj7 = objArrCopyOf[i7 ^ 1];
                    obj7.getClass();
                    zzp.zza(obj6, obj7);
                    int iZza = zzq.zza(obj6.hashCode());
                    while (true) {
                        int i8 = iZza & i3;
                        int i9 = bArr[i8] & 255;
                        if (i9 == 255) {
                            bArr[i8] = (byte) i6;
                            if (i4 < i5) {
                                objArrCopyOf[i6] = obj6;
                                objArrCopyOf[i6 ^ 1] = obj7;
                            }
                            i4++;
                        } else {
                            if (obj6.equals(objArrCopyOf[i9])) {
                                int i10 = i9 ^ 1;
                                Object obj8 = objArrCopyOf[i10];
                                obj8.getClass();
                                zzv zzvVar = new zzv(obj6, obj7, obj8);
                                objArrCopyOf[i10] = obj7;
                                obj = zzvVar;
                                break;
                            }
                            iZza = i8 + 1;
                        }
                    }
                }
                if (i4 == i2) {
                    obj = bArr;
                    c = 2;
                } else {
                    obj = new Object[]{bArr, Integer.valueOf(i4), obj};
                    c = 2;
                }
            } else if (iHighestOneBit <= 32768) {
                sArr = new short[iHighestOneBit];
                Arrays.fill(sArr, (short) -1);
                int i11 = 0;
                for (int i12 = 0; i12 < i2; i12++) {
                    int i13 = i11 + i11;
                    int i14 = i12 + i12;
                    Object obj9 = objArrCopyOf[i14];
                    obj9.getClass();
                    Object obj10 = objArrCopyOf[i14 ^ 1];
                    obj10.getClass();
                    zzp.zza(obj9, obj10);
                    int iZza2 = zzq.zza(obj9.hashCode());
                    while (true) {
                        int i15 = iZza2 & i3;
                        char c3 = (char) sArr[i15];
                        if (c3 == 65535) {
                            sArr[i15] = (short) i13;
                            if (i11 < i12) {
                                objArrCopyOf[i13] = obj9;
                                objArrCopyOf[i13 ^ 1] = obj10;
                            }
                            i11++;
                        } else {
                            if (obj9.equals(objArrCopyOf[c3])) {
                                int i16 = c3 ^ 1;
                                Object obj11 = objArrCopyOf[i16];
                                obj11.getClass();
                                zzv zzvVar2 = new zzv(obj9, obj10, obj11);
                                objArrCopyOf[i16] = obj10;
                                obj = zzvVar2;
                                break;
                            }
                            iZza2 = i15 + 1;
                        }
                    }
                }
                if (i11 != i2) {
                    c = 2;
                    objArr2 = new Object[]{sArr, Integer.valueOf(i11), obj};
                    obj = objArr2;
                }
                obj = sArr;
                c = 2;
            } else {
                sArr = new int[iHighestOneBit];
                Arrays.fill((int[]) sArr, -1);
                int i17 = 0;
                int i18 = 0;
                while (i17 < i2) {
                    int i19 = i18 + i18;
                    int i20 = i17 + i17;
                    Object obj12 = objArrCopyOf[i20];
                    obj12.getClass();
                    Object obj13 = objArrCopyOf[i20 ^ 1];
                    obj13.getClass();
                    zzp.zza(obj12, obj13);
                    int iZza3 = zzq.zza(obj12.hashCode());
                    while (true) {
                        int i21 = iZza3 & i3;
                        ?? r15 = sArr[i21];
                        if (r15 == c2) {
                            sArr[i21] = i19;
                            if (i18 < i17) {
                                objArrCopyOf[i19] = obj12;
                                objArrCopyOf[i19 ^ 1] = obj13;
                            }
                            i18++;
                        } else {
                            if (obj12.equals(objArrCopyOf[r15])) {
                                int i22 = r15 ^ 1;
                                Object obj14 = objArrCopyOf[i22];
                                obj14.getClass();
                                zzv zzvVar3 = new zzv(obj12, obj13, obj14);
                                objArrCopyOf[i22] = obj13;
                                obj = zzvVar3;
                                break;
                            }
                            iZza3 = i21 + 1;
                            c2 = 65535;
                        }
                    }
                    i17++;
                    c2 = 65535;
                }
                if (i18 != i2) {
                    c = 2;
                    objArr2 = new Object[]{sArr, Integer.valueOf(i18), obj};
                    obj = objArr2;
                }
                obj = sArr;
                c = 2;
            }
        }
        boolean z = obj instanceof Object[];
        Object obj15 = obj;
        if (z) {
            Object[] objArr3 = (Object[]) obj;
            zzwVar.zzc = (zzv) objArr3[c];
            Object obj16 = objArr3[0];
            int iIntValue = ((Integer) objArr3[1]).intValue();
            objArrCopyOf = Arrays.copyOf(objArrCopyOf, iIntValue + iIntValue);
            obj15 = obj16;
            i2 = iIntValue;
        }
        return new zzaf(obj15, objArrCopyOf, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0009 A[EDGE_INSN: B:43:0x0009->B:4:0x0009 BREAK  A[LOOP:0: B:15:0x0038->B:21:0x004e], EDGE_INSN: B:45:0x0009->B:4:0x0009 BREAK  A[LOOP:1: B:25:0x0063->B:31:0x007a], EDGE_INSN: B:47:0x0009->B:4:0x0009 BREAK  A[LOOP:2: B:33:0x0089->B:42:0x00a0]] */
    @Override // com.google.android.gms.internal.play_billing.zzx, java.util.Map
    @javax.annotation.CheckForNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object get(@javax.annotation.CheckForNull java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = r9.zzc
            java.lang.Object[] r1 = r9.zzb
            int r2 = r9.zzd
            r3 = 0
            if (r10 != 0) goto Lc
        L9:
            r10 = r3
            goto L9c
        Lc:
            r4 = 1
            if (r2 != r4) goto L22
            r0 = 0
            r0 = r1[r0]
            r0.getClass()
            boolean r10 = r0.equals(r10)
            if (r10 == 0) goto L9
            r10 = r1[r4]
            r10.getClass()
            goto L9c
        L22:
            if (r0 != 0) goto L25
            goto L9
        L25:
            boolean r2 = r0 instanceof byte[]
            r5 = -1
            if (r2 == 0) goto L51
            r2 = r0
            byte[] r2 = (byte[]) r2
            int r0 = r2.length
            int r6 = r0 + (-1)
            int r0 = r10.hashCode()
            int r0 = com.google.android.gms.internal.play_billing.zzq.zza(r0)
        L38:
            r0 = r0 & r6
            r5 = r2[r0]
            r7 = 255(0xff, float:3.57E-43)
            r5 = r5 & r7
            if (r5 != r7) goto L41
            goto L9
        L41:
            r7 = r1[r5]
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L4e
            r10 = r5 ^ 1
            r10 = r1[r10]
            goto L9c
        L4e:
            int r0 = r0 + 1
            goto L38
        L51:
            boolean r2 = r0 instanceof short[]
            if (r2 == 0) goto L7d
            r2 = r0
            short[] r2 = (short[]) r2
            int r0 = r2.length
            int r6 = r0 + (-1)
            int r0 = r10.hashCode()
            int r0 = com.google.android.gms.internal.play_billing.zzq.zza(r0)
        L63:
            r0 = r0 & r6
            short r5 = r2[r0]
            char r5 = (char) r5
            r7 = 65535(0xffff, float:9.1834E-41)
            if (r5 != r7) goto L6d
            goto L9
        L6d:
            r7 = r1[r5]
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L7a
            r10 = r5 ^ 1
            r10 = r1[r10]
            goto L9c
        L7a:
            int r0 = r0 + 1
            goto L63
        L7d:
            int[] r0 = (int[]) r0
            int r2 = r0.length
            int r2 = r2 + r5
            int r6 = r10.hashCode()
            int r6 = com.google.android.gms.internal.play_billing.zzq.zza(r6)
        L89:
            r6 = r6 & r2
            r7 = r0[r6]
            if (r7 != r5) goto L90
            goto L9
        L90:
            r8 = r1[r7]
            boolean r8 = r10.equals(r8)
            if (r8 == 0) goto La0
            r10 = r7 ^ 1
            r10 = r1[r10]
        L9c:
            if (r10 != 0) goto L9f
            return r3
        L9f:
            return r10
        La0:
            int r6 = r6 + 1
            goto L89
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzaf.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map
    public final int size() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.play_billing.zzx
    final zzr zza() {
        return new zzae(this.zzb, 1, this.zzd);
    }

    @Override // com.google.android.gms.internal.play_billing.zzx
    final zzy zzc() {
        return new zzac(this, this.zzb, 0, this.zzd);
    }

    @Override // com.google.android.gms.internal.play_billing.zzx
    final zzy zzd() {
        return new zzad(this, new zzae(this.zzb, 0, this.zzd));
    }
}
