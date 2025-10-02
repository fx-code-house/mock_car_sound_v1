package com.google.android.gms.internal.measurement;

import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.mapstruct.ap.internal.util.MessageConstants;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzfp<K, V> extends zzfc<K, V> {
    static final zzfc<Object, Object> zza = new zzfp(null, new Object[0], 0);
    private final transient Object zzb;
    private final transient Object[] zzc;
    private final transient int zzd;

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0056, code lost:
    
        r2[r6] = (byte) r3;
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0097, code lost:
    
        r2[r6] = (short) r3;
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00cc, code lost:
    
        r2[r7] = r3;
        r1 = r1 + 1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [int[]] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r8v0, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static <K, V> com.google.android.gms.internal.measurement.zzfp<K, V> zza(int r10, java.lang.Object[] r11) {
        /*
            Method dump skipped, instructions count: 231
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzfp.zza(int, java.lang.Object[]):com.google.android.gms.internal.measurement.zzfp");
    }

    private static IllegalArgumentException zza(Object obj, Object obj2, Object[] objArr, int i) {
        String strValueOf = String.valueOf(obj);
        String strValueOf2 = String.valueOf(obj2);
        String strValueOf3 = String.valueOf(objArr[i]);
        String strValueOf4 = String.valueOf(objArr[i ^ 1]);
        return new IllegalArgumentException(new StringBuilder(String.valueOf(strValueOf).length() + 39 + String.valueOf(strValueOf2).length() + String.valueOf(strValueOf3).length() + String.valueOf(strValueOf4).length()).append("Multiple entries with same key: ").append(strValueOf).append("=").append(strValueOf2).append(MessageConstants.AND).append(strValueOf3).append("=").append(strValueOf4).toString());
    }

    private zzfp(Object obj, Object[] objArr, int i) {
        this.zzb = obj;
        this.zzc = objArr;
        this.zzd = i;
    }

    @Override // java.util.Map
    public final int size() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.measurement.zzfc, java.util.Map
    @NullableDecl
    public final V get(@NullableDecl Object obj) {
        Object obj2 = this.zzb;
        Object[] objArr = this.zzc;
        int i = this.zzd;
        if (obj == null) {
            return null;
        }
        if (i == 1) {
            if (objArr[0].equals(obj)) {
                return (V) objArr[1];
            }
            return null;
        }
        if (obj2 == null) {
            return null;
        }
        if (obj2 instanceof byte[]) {
            byte[] bArr = (byte[]) obj2;
            int length = bArr.length - 1;
            int iZza = zzez.zza(obj.hashCode());
            while (true) {
                int i2 = iZza & length;
                int i3 = bArr[i2] & 255;
                if (i3 == 255) {
                    return null;
                }
                if (objArr[i3].equals(obj)) {
                    return (V) objArr[i3 ^ 1];
                }
                iZza = i2 + 1;
            }
        } else if (obj2 instanceof short[]) {
            short[] sArr = (short[]) obj2;
            int length2 = sArr.length - 1;
            int iZza2 = zzez.zza(obj.hashCode());
            while (true) {
                int i4 = iZza2 & length2;
                int i5 = sArr[i4] & 65535;
                if (i5 == 65535) {
                    return null;
                }
                if (objArr[i5].equals(obj)) {
                    return (V) objArr[i5 ^ 1];
                }
                iZza2 = i4 + 1;
            }
        } else {
            int[] iArr = (int[]) obj2;
            int length3 = iArr.length - 1;
            int iZza3 = zzez.zza(obj.hashCode());
            while (true) {
                int i6 = iZza3 & length3;
                int i7 = iArr[i6];
                if (i7 == -1) {
                    return null;
                }
                if (objArr[i7].equals(obj)) {
                    return (V) objArr[i7 ^ 1];
                }
                iZza3 = i6 + 1;
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzfc
    final zzfg<Map.Entry<K, V>> zza() {
        return new zzfo(this, this.zzc, 0, this.zzd);
    }

    @Override // com.google.android.gms.internal.measurement.zzfc
    final zzfg<K> zzb() {
        return new zzfq(this, new zzft(this.zzc, 0, this.zzd));
    }

    @Override // com.google.android.gms.internal.measurement.zzfc
    final zzey<V> zzc() {
        return new zzft(this.zzc, 1, this.zzd);
    }
}
