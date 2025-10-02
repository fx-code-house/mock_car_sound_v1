package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final /* synthetic */ class zzdw {
    static final /* synthetic */ int[] zzka;
    static final /* synthetic */ int[] zzkb;

    static {
        int[] iArr = new int[zzej.values().length];
        zzkb = iArr;
        try {
            iArr[zzej.BYTE_STRING.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            zzkb[zzej.MESSAGE.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            zzkb[zzej.STRING.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        int[] iArr2 = new int[zzdv.values().length];
        zzka = iArr2;
        try {
            iArr2[zzdv.MAP.ordinal()] = 1;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            zzka[zzdv.VECTOR.ordinal()] = 2;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            zzka[zzdv.SCALAR.ordinal()] = 3;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
