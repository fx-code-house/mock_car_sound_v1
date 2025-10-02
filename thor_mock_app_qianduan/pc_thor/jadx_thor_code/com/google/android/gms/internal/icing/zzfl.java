package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import sun.misc.Unsafe;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzfl<T> implements zzfu<T> {
    private static final int[] zzmr = new int[0];
    private static final Unsafe zzms = zzgs.zzdp();
    private final zzfh zzmn;
    private final zzgm<?, ?> zzmo;
    private final boolean zzmp;
    private final zzdn<?> zzmq;
    private final int[] zzmt;
    private final Object[] zzmu;
    private final int zzmv;
    private final int zzmw;
    private final boolean zzmx;
    private final boolean zzmy;
    private final boolean zzmz;
    private final int[] zzna;
    private final int zznb;
    private final int zznc;
    private final zzfm zznd;
    private final zzer zzne;
    private final zzfa zznf;

    private zzfl(int[] iArr, Object[] objArr, int i, int i2, zzfh zzfhVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzfm zzfmVar, zzer zzerVar, zzgm<?, ?> zzgmVar, zzdn<?> zzdnVar, zzfa zzfaVar) {
        this.zzmt = iArr;
        this.zzmu = objArr;
        this.zzmv = i;
        this.zzmw = i2;
        this.zzmx = zzfhVar instanceof zzdx;
        this.zzmy = z;
        this.zzmp = zzdnVar != null && zzdnVar.zze(zzfhVar);
        this.zzmz = false;
        this.zzna = iArr2;
        this.zznb = i3;
        this.zznc = i4;
        this.zznd = zzfmVar;
        this.zzne = zzerVar;
        this.zzmo = zzgmVar;
        this.zzmq = zzdnVar;
        this.zzmn = zzfhVar;
        this.zznf = zzfaVar;
    }

    static <T> zzfl<T> zza(Class<T> cls, zzff zzffVar, zzfm zzfmVar, zzer zzerVar, zzgm<?, ?> zzgmVar, zzdn<?> zzdnVar, zzfa zzfaVar) {
        int i;
        int iCharAt;
        int iCharAt2;
        int i2;
        int i3;
        int i4;
        int i5;
        int[] iArr;
        int i6;
        int i7;
        char cCharAt;
        int i8;
        char cCharAt2;
        int i9;
        char cCharAt3;
        int i10;
        char cCharAt4;
        int i11;
        char cCharAt5;
        int i12;
        char cCharAt6;
        int i13;
        char cCharAt7;
        int i14;
        char cCharAt8;
        int i15;
        int i16;
        boolean z;
        int i17;
        zzfv zzfvVar;
        int i18;
        int iObjectFieldOffset;
        int i19;
        int i20;
        Class<?> cls2;
        String str;
        int iObjectFieldOffset2;
        int i21;
        Field fieldZza;
        int i22;
        char cCharAt9;
        int i23;
        Field fieldZza2;
        Field fieldZza3;
        int i24;
        char cCharAt10;
        int i25;
        char cCharAt11;
        int i26;
        char cCharAt12;
        int i27;
        char cCharAt13;
        char cCharAt14;
        if (zzffVar instanceof zzfv) {
            zzfv zzfvVar2 = (zzfv) zzffVar;
            int i28 = 0;
            boolean z2 = zzfvVar2.zzco() == zzdx.zze.zzkv;
            String strZzcw = zzfvVar2.zzcw();
            int length = strZzcw.length();
            int iCharAt3 = strZzcw.charAt(0);
            if (iCharAt3 >= 55296) {
                int i29 = iCharAt3 & 8191;
                int i30 = 1;
                int i31 = 13;
                while (true) {
                    i = i30 + 1;
                    cCharAt14 = strZzcw.charAt(i30);
                    if (cCharAt14 < 55296) {
                        break;
                    }
                    i29 |= (cCharAt14 & 8191) << i31;
                    i31 += 13;
                    i30 = i;
                }
                iCharAt3 = i29 | (cCharAt14 << i31);
            } else {
                i = 1;
            }
            int i32 = i + 1;
            int iCharAt4 = strZzcw.charAt(i);
            if (iCharAt4 >= 55296) {
                int i33 = iCharAt4 & 8191;
                int i34 = 13;
                while (true) {
                    i27 = i32 + 1;
                    cCharAt13 = strZzcw.charAt(i32);
                    if (cCharAt13 < 55296) {
                        break;
                    }
                    i33 |= (cCharAt13 & 8191) << i34;
                    i34 += 13;
                    i32 = i27;
                }
                iCharAt4 = i33 | (cCharAt13 << i34);
                i32 = i27;
            }
            if (iCharAt4 == 0) {
                i6 = 0;
                iCharAt = 0;
                i4 = 0;
                iCharAt2 = 0;
                i5 = 0;
                iArr = zzmr;
                i3 = 0;
            } else {
                int i35 = i32 + 1;
                int iCharAt5 = strZzcw.charAt(i32);
                if (iCharAt5 >= 55296) {
                    int i36 = iCharAt5 & 8191;
                    int i37 = 13;
                    while (true) {
                        i14 = i35 + 1;
                        cCharAt8 = strZzcw.charAt(i35);
                        if (cCharAt8 < 55296) {
                            break;
                        }
                        i36 |= (cCharAt8 & 8191) << i37;
                        i37 += 13;
                        i35 = i14;
                    }
                    iCharAt5 = i36 | (cCharAt8 << i37);
                    i35 = i14;
                }
                int i38 = i35 + 1;
                int iCharAt6 = strZzcw.charAt(i35);
                if (iCharAt6 >= 55296) {
                    int i39 = iCharAt6 & 8191;
                    int i40 = 13;
                    while (true) {
                        i13 = i38 + 1;
                        cCharAt7 = strZzcw.charAt(i38);
                        if (cCharAt7 < 55296) {
                            break;
                        }
                        i39 |= (cCharAt7 & 8191) << i40;
                        i40 += 13;
                        i38 = i13;
                    }
                    iCharAt6 = i39 | (cCharAt7 << i40);
                    i38 = i13;
                }
                int i41 = i38 + 1;
                iCharAt = strZzcw.charAt(i38);
                if (iCharAt >= 55296) {
                    int i42 = iCharAt & 8191;
                    int i43 = 13;
                    while (true) {
                        i12 = i41 + 1;
                        cCharAt6 = strZzcw.charAt(i41);
                        if (cCharAt6 < 55296) {
                            break;
                        }
                        i42 |= (cCharAt6 & 8191) << i43;
                        i43 += 13;
                        i41 = i12;
                    }
                    iCharAt = i42 | (cCharAt6 << i43);
                    i41 = i12;
                }
                int i44 = i41 + 1;
                int iCharAt7 = strZzcw.charAt(i41);
                if (iCharAt7 >= 55296) {
                    int i45 = iCharAt7 & 8191;
                    int i46 = 13;
                    while (true) {
                        i11 = i44 + 1;
                        cCharAt5 = strZzcw.charAt(i44);
                        if (cCharAt5 < 55296) {
                            break;
                        }
                        i45 |= (cCharAt5 & 8191) << i46;
                        i46 += 13;
                        i44 = i11;
                    }
                    iCharAt7 = i45 | (cCharAt5 << i46);
                    i44 = i11;
                }
                int i47 = i44 + 1;
                iCharAt2 = strZzcw.charAt(i44);
                if (iCharAt2 >= 55296) {
                    int i48 = iCharAt2 & 8191;
                    int i49 = 13;
                    while (true) {
                        i10 = i47 + 1;
                        cCharAt4 = strZzcw.charAt(i47);
                        if (cCharAt4 < 55296) {
                            break;
                        }
                        i48 |= (cCharAt4 & 8191) << i49;
                        i49 += 13;
                        i47 = i10;
                    }
                    iCharAt2 = i48 | (cCharAt4 << i49);
                    i47 = i10;
                }
                int i50 = i47 + 1;
                int iCharAt8 = strZzcw.charAt(i47);
                if (iCharAt8 >= 55296) {
                    int i51 = iCharAt8 & 8191;
                    int i52 = 13;
                    while (true) {
                        i9 = i50 + 1;
                        cCharAt3 = strZzcw.charAt(i50);
                        if (cCharAt3 < 55296) {
                            break;
                        }
                        i51 |= (cCharAt3 & 8191) << i52;
                        i52 += 13;
                        i50 = i9;
                    }
                    iCharAt8 = i51 | (cCharAt3 << i52);
                    i50 = i9;
                }
                int i53 = i50 + 1;
                int iCharAt9 = strZzcw.charAt(i50);
                if (iCharAt9 >= 55296) {
                    int i54 = iCharAt9 & 8191;
                    int i55 = i53;
                    int i56 = 13;
                    while (true) {
                        i8 = i55 + 1;
                        cCharAt2 = strZzcw.charAt(i55);
                        if (cCharAt2 < 55296) {
                            break;
                        }
                        i54 |= (cCharAt2 & 8191) << i56;
                        i56 += 13;
                        i55 = i8;
                    }
                    iCharAt9 = i54 | (cCharAt2 << i56);
                    i2 = i8;
                } else {
                    i2 = i53;
                }
                int i57 = i2 + 1;
                int iCharAt10 = strZzcw.charAt(i2);
                if (iCharAt10 >= 55296) {
                    int i58 = iCharAt10 & 8191;
                    int i59 = i57;
                    int i60 = 13;
                    while (true) {
                        i7 = i59 + 1;
                        cCharAt = strZzcw.charAt(i59);
                        if (cCharAt < 55296) {
                            break;
                        }
                        i58 |= (cCharAt & 8191) << i60;
                        i60 += 13;
                        i59 = i7;
                    }
                    iCharAt10 = i58 | (cCharAt << i60);
                    i57 = i7;
                }
                int[] iArr2 = new int[iCharAt10 + iCharAt8 + iCharAt9];
                int i61 = (iCharAt5 << 1) + iCharAt6;
                i3 = iCharAt7;
                i4 = i61;
                i5 = iCharAt10;
                i28 = iCharAt5;
                i32 = i57;
                int i62 = iCharAt8;
                iArr = iArr2;
                i6 = i62;
            }
            Unsafe unsafe = zzms;
            Object[] objArrZzcx = zzfvVar2.zzcx();
            Class<?> cls3 = zzfvVar2.zzcq().getClass();
            int i63 = i32;
            int[] iArr3 = new int[iCharAt2 * 3];
            Object[] objArr = new Object[iCharAt2 << 1];
            int i64 = i5 + i6;
            int i65 = i5;
            int i66 = i63;
            int i67 = i64;
            int i68 = 0;
            int i69 = 0;
            while (i66 < length) {
                int i70 = i66 + 1;
                int iCharAt11 = strZzcw.charAt(i66);
                int i71 = length;
                if (iCharAt11 >= 55296) {
                    int i72 = iCharAt11 & 8191;
                    int i73 = i70;
                    int i74 = 13;
                    while (true) {
                        i26 = i73 + 1;
                        cCharAt12 = strZzcw.charAt(i73);
                        i15 = i5;
                        if (cCharAt12 < 55296) {
                            break;
                        }
                        i72 |= (cCharAt12 & 8191) << i74;
                        i74 += 13;
                        i73 = i26;
                        i5 = i15;
                    }
                    iCharAt11 = i72 | (cCharAt12 << i74);
                    i16 = i26;
                } else {
                    i15 = i5;
                    i16 = i70;
                }
                int i75 = i16 + 1;
                int iCharAt12 = strZzcw.charAt(i16);
                if (iCharAt12 >= 55296) {
                    int i76 = iCharAt12 & 8191;
                    int i77 = i75;
                    int i78 = 13;
                    while (true) {
                        i25 = i77 + 1;
                        cCharAt11 = strZzcw.charAt(i77);
                        z = z2;
                        if (cCharAt11 < 55296) {
                            break;
                        }
                        i76 |= (cCharAt11 & 8191) << i78;
                        i78 += 13;
                        i77 = i25;
                        z2 = z;
                    }
                    iCharAt12 = i76 | (cCharAt11 << i78);
                    i17 = i25;
                } else {
                    z = z2;
                    i17 = i75;
                }
                int i79 = iCharAt12 & 255;
                int i80 = i3;
                if ((iCharAt12 & 1024) != 0) {
                    iArr[i68] = i69;
                    i68++;
                }
                int i81 = iCharAt;
                if (i79 >= 51) {
                    int i82 = i17 + 1;
                    int iCharAt13 = strZzcw.charAt(i17);
                    char c = 55296;
                    if (iCharAt13 >= 55296) {
                        int i83 = iCharAt13 & 8191;
                        int i84 = 13;
                        while (true) {
                            i24 = i82 + 1;
                            cCharAt10 = strZzcw.charAt(i82);
                            if (cCharAt10 < c) {
                                break;
                            }
                            i83 |= (cCharAt10 & 8191) << i84;
                            i84 += 13;
                            i82 = i24;
                            c = 55296;
                        }
                        iCharAt13 = i83 | (cCharAt10 << i84);
                        i82 = i24;
                    }
                    int i85 = i79 - 51;
                    int i86 = i82;
                    if (i85 == 9 || i85 == 17) {
                        objArr[((i69 / 3) << 1) + 1] = objArrZzcx[i4];
                        i4++;
                    } else if (i85 == 12 && (iCharAt3 & 1) == 1) {
                        objArr[((i69 / 3) << 1) + 1] = objArrZzcx[i4];
                        i4++;
                    }
                    int i87 = iCharAt13 << 1;
                    Object obj = objArrZzcx[i87];
                    if (obj instanceof Field) {
                        fieldZza2 = (Field) obj;
                    } else {
                        fieldZza2 = zza(cls3, (String) obj);
                        objArrZzcx[i87] = fieldZza2;
                    }
                    zzfvVar = zzfvVar2;
                    String str2 = strZzcw;
                    int iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldZza2);
                    int i88 = i87 + 1;
                    Object obj2 = objArrZzcx[i88];
                    if (obj2 instanceof Field) {
                        fieldZza3 = (Field) obj2;
                    } else {
                        fieldZza3 = zza(cls3, (String) obj2);
                        objArrZzcx[i88] = fieldZza3;
                    }
                    cls2 = cls3;
                    i19 = i4;
                    i17 = i86;
                    str = str2;
                    i21 = 0;
                    iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza3);
                    iObjectFieldOffset = iObjectFieldOffset3;
                    i20 = i28;
                } else {
                    zzfvVar = zzfvVar2;
                    String str3 = strZzcw;
                    int i89 = i4 + 1;
                    Field fieldZza4 = zza(cls3, (String) objArrZzcx[i4]);
                    if (i79 == 9 || i79 == 17) {
                        i18 = 1;
                        objArr[((i69 / 3) << 1) + 1] = fieldZza4.getType();
                    } else {
                        if (i79 == 27 || i79 == 49) {
                            i18 = 1;
                            i23 = i89 + 1;
                            objArr[((i69 / 3) << 1) + 1] = objArrZzcx[i89];
                        } else if (i79 == 12 || i79 == 30 || i79 == 44) {
                            i18 = 1;
                            if ((iCharAt3 & 1) == 1) {
                                i23 = i89 + 1;
                                objArr[((i69 / 3) << 1) + 1] = objArrZzcx[i89];
                            }
                        } else if (i79 == 50) {
                            int i90 = i65 + 1;
                            iArr[i65] = i69;
                            int i91 = (i69 / 3) << 1;
                            int i92 = i89 + 1;
                            objArr[i91] = objArrZzcx[i89];
                            if ((iCharAt12 & 2048) != 0) {
                                i89 = i92 + 1;
                                objArr[i91 + 1] = objArrZzcx[i92];
                                i65 = i90;
                                i18 = 1;
                            } else {
                                i89 = i92;
                                i18 = 1;
                                i65 = i90;
                            }
                        } else {
                            i18 = 1;
                        }
                        i89 = i23;
                    }
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZza4);
                    if ((iCharAt3 & 1) != i18 || i79 > 17) {
                        i19 = i89;
                        i20 = i28;
                        cls2 = cls3;
                        str = str3;
                        iObjectFieldOffset2 = 0;
                        i21 = 0;
                        if (i79 >= 18 && i79 <= 49) {
                            iArr[i67] = iObjectFieldOffset;
                            i67++;
                        }
                    } else {
                        int i93 = i17 + 1;
                        str = str3;
                        int iCharAt14 = str.charAt(i17);
                        if (iCharAt14 >= 55296) {
                            int i94 = iCharAt14 & 8191;
                            int i95 = 13;
                            while (true) {
                                i22 = i93 + 1;
                                cCharAt9 = str.charAt(i93);
                                if (cCharAt9 < 55296) {
                                    break;
                                }
                                i94 |= (cCharAt9 & 8191) << i95;
                                i95 += 13;
                                i93 = i22;
                            }
                            iCharAt14 = i94 | (cCharAt9 << i95);
                            i93 = i22;
                        }
                        int i96 = (i28 << 1) + (iCharAt14 / 32);
                        Object obj3 = objArrZzcx[i96];
                        i19 = i89;
                        if (obj3 instanceof Field) {
                            fieldZza = (Field) obj3;
                        } else {
                            fieldZza = zza(cls3, (String) obj3);
                            objArrZzcx[i96] = fieldZza;
                        }
                        i20 = i28;
                        cls2 = cls3;
                        i21 = iCharAt14 % 32;
                        iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza);
                        i17 = i93;
                        if (i79 >= 18) {
                            iArr[i67] = iObjectFieldOffset;
                            i67++;
                        }
                    }
                }
                int i97 = i69 + 1;
                iArr3[i69] = iCharAt11;
                int i98 = i97 + 1;
                iArr3[i97] = iObjectFieldOffset | ((iCharAt12 & 256) != 0 ? 268435456 : 0) | ((iCharAt12 & 512) != 0 ? 536870912 : 0) | (i79 << 20);
                i69 = i98 + 1;
                iArr3[i98] = (i21 << 20) | iObjectFieldOffset2;
                i28 = i20;
                strZzcw = str;
                i66 = i17;
                cls3 = cls2;
                i3 = i80;
                length = i71;
                i5 = i15;
                z2 = z;
                iCharAt = i81;
                i4 = i19;
                zzfvVar2 = zzfvVar;
            }
            return new zzfl<>(iArr3, objArr, iCharAt, i3, zzfvVar2.zzcq(), z2, false, iArr, i5, i64, zzfmVar, zzerVar, zzgmVar, zzdnVar, zzfaVar);
        }
        ((zzgj) zzffVar).zzco();
        int i99 = zzdx.zze.zzkv;
        throw new NoSuchMethodError();
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            throw new RuntimeException(new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(string).length()).append("Field ").append(str).append(" for ").append(name).append(" not found. Known fields are ").append(string).toString());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01c1  */
    @Override // com.google.android.gms.internal.icing.zzfu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(T r10, T r11) {
        /*
            Method dump skipped, instructions count: 640
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfl.equals(java.lang.Object, java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final int hashCode(T t) {
        int i;
        int iZzk;
        int length = this.zzmt.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzag = zzag(i3);
            int i4 = this.zzmt[i3];
            long j = 1048575 & iZzag;
            int iHashCode = 37;
            switch ((iZzag & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    iZzk = zzeb.zzk(Double.doubleToLongBits(zzgs.zzn(t, j)));
                    i2 = i + iZzk;
                    break;
                case 1:
                    i = i2 * 53;
                    iZzk = Float.floatToIntBits(zzgs.zzm(t, j));
                    i2 = i + iZzk;
                    break;
                case 2:
                    i = i2 * 53;
                    iZzk = zzeb.zzk(zzgs.zzk(t, j));
                    i2 = i + iZzk;
                    break;
                case 3:
                    i = i2 * 53;
                    iZzk = zzeb.zzk(zzgs.zzk(t, j));
                    i2 = i + iZzk;
                    break;
                case 4:
                    i = i2 * 53;
                    iZzk = zzgs.zzj(t, j);
                    i2 = i + iZzk;
                    break;
                case 5:
                    i = i2 * 53;
                    iZzk = zzeb.zzk(zzgs.zzk(t, j));
                    i2 = i + iZzk;
                    break;
                case 6:
                    i = i2 * 53;
                    iZzk = zzgs.zzj(t, j);
                    i2 = i + iZzk;
                    break;
                case 7:
                    i = i2 * 53;
                    iZzk = zzeb.zzg(zzgs.zzl(t, j));
                    i2 = i + iZzk;
                    break;
                case 8:
                    i = i2 * 53;
                    iZzk = ((String) zzgs.zzo(t, j)).hashCode();
                    i2 = i + iZzk;
                    break;
                case 9:
                    Object objZzo = zzgs.zzo(t, j);
                    if (objZzo != null) {
                        iHashCode = objZzo.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZzk = zzgs.zzo(t, j).hashCode();
                    i2 = i + iZzk;
                    break;
                case 11:
                    i = i2 * 53;
                    iZzk = zzgs.zzj(t, j);
                    i2 = i + iZzk;
                    break;
                case 12:
                    i = i2 * 53;
                    iZzk = zzgs.zzj(t, j);
                    i2 = i + iZzk;
                    break;
                case 13:
                    i = i2 * 53;
                    iZzk = zzgs.zzj(t, j);
                    i2 = i + iZzk;
                    break;
                case 14:
                    i = i2 * 53;
                    iZzk = zzeb.zzk(zzgs.zzk(t, j));
                    i2 = i + iZzk;
                    break;
                case 15:
                    i = i2 * 53;
                    iZzk = zzgs.zzj(t, j);
                    i2 = i + iZzk;
                    break;
                case 16:
                    i = i2 * 53;
                    iZzk = zzeb.zzk(zzgs.zzk(t, j));
                    i2 = i + iZzk;
                    break;
                case 17:
                    Object objZzo2 = zzgs.zzo(t, j);
                    if (objZzo2 != null) {
                        iHashCode = objZzo2.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i2 * 53;
                    iZzk = zzgs.zzo(t, j).hashCode();
                    i2 = i + iZzk;
                    break;
                case 50:
                    i = i2 * 53;
                    iZzk = zzgs.zzo(t, j).hashCode();
                    i2 = i + iZzk;
                    break;
                case 51:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzeb.zzk(Double.doubleToLongBits(zze(t, j)));
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = Float.floatToIntBits(zzf(t, j));
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzeb.zzk(zzh(t, j));
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzeb.zzk(zzh(t, j));
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzg(t, j);
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzeb.zzk(zzh(t, j));
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzg(t, j);
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzeb.zzg(zzi(t, j));
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = ((String) zzgs.zzo(t, j)).hashCode();
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzgs.zzo(t, j).hashCode();
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzgs.zzo(t, j).hashCode();
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzg(t, j);
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzg(t, j);
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzg(t, j);
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzeb.zzk(zzh(t, j));
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzg(t, j);
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzeb.zzk(zzh(t, j));
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zza((zzfl<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzk = zzgs.zzo(t, j).hashCode();
                        i2 = i + iZzk;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzmo.zzp(t).hashCode();
        return this.zzmp ? (iHashCode2 * 53) + this.zzmq.zzd(t).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final void zzc(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzmt.length; i += 3) {
            int iZzag = zzag(i);
            long j = 1048575 & iZzag;
            int i2 = this.zzmt[i];
            switch ((iZzag & 267386880) >>> 20) {
                case 0:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza(t, j, zzgs.zzn(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzm(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzk(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzk(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzj(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzk(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzj(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza(t, j, zzgs.zzl(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza(t, j, zzgs.zzo(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza(t, j, zzgs.zzo(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzj(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzj(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzj(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzk(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzj(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zza((zzfl<T>) t2, i)) {
                        zzgs.zza((Object) t, j, zzgs.zzk(t2, j));
                        zzb((zzfl<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zza(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzne.zza(t, t2, j);
                    break;
                case 50:
                    zzfw.zza(this.zznf, t, t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zza((zzfl<T>) t2, i2, i)) {
                        zzgs.zza(t, j, zzgs.zzo(t2, j));
                        zzb((zzfl<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzb(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zza((zzfl<T>) t2, i2, i)) {
                        zzgs.zza(t, j, zzgs.zzo(t2, j));
                        zzb((zzfl<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        zzfw.zza(this.zzmo, t, t2);
        if (this.zzmp) {
            zzfw.zza(this.zzmq, t, t2);
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzag = zzag(i) & 1048575;
        if (zza((zzfl<T>) t2, i)) {
            Object objZzo = zzgs.zzo(t, jZzag);
            Object objZzo2 = zzgs.zzo(t2, jZzag);
            if (objZzo != null && objZzo2 != null) {
                zzgs.zza(t, jZzag, zzeb.zza(objZzo, objZzo2));
                zzb((zzfl<T>) t, i);
            } else if (objZzo2 != null) {
                zzgs.zza(t, jZzag, objZzo2);
                zzb((zzfl<T>) t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int iZzag = zzag(i);
        int i2 = this.zzmt[i];
        long j = iZzag & 1048575;
        if (zza((zzfl<T>) t2, i2, i)) {
            Object objZzo = zzgs.zzo(t, j);
            Object objZzo2 = zzgs.zzo(t2, j);
            if (objZzo != null && objZzo2 != null) {
                zzgs.zza(t, j, zzeb.zza(objZzo, objZzo2));
                zzb((zzfl<T>) t, i2, i);
            } else if (objZzo2 != null) {
                zzgs.zza(t, j, objZzo2);
                zzb((zzfl<T>) t, i2, i);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:421:0x090b A[PHI: r6
      0x090b: PHI (r6v4 int) = 
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
      (r6v16 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v17 int)
      (r6v1 int)
     binds: [B:256:0x0545, B:459:0x09b0, B:453:0x0994, B:450:0x0982, B:447:0x0973, B:444:0x0966, B:441:0x0959, B:437:0x094e, B:434:0x0943, B:431:0x0936, B:428:0x0929, B:425:0x0916, B:396:0x081f, B:390:0x0802, B:384:0x07e5, B:378:0x07c8, B:372:0x07aa, B:366:0x078c, B:360:0x076e, B:354:0x0750, B:348:0x0732, B:342:0x0714, B:336:0x06f6, B:330:0x06d8, B:324:0x06ba, B:318:0x069c, B:313:0x0668, B:310:0x065b, B:307:0x064b, B:304:0x063b, B:301:0x062b, B:298:0x061d, B:295:0x0610, B:292:0x0603, B:286:0x05e5, B:283:0x05d1, B:280:0x05bf, B:277:0x05af, B:274:0x059f, B:439:0x0955, B:271:0x0592, B:268:0x0584, B:265:0x0574, B:262:0x0564, B:420:0x090a, B:259:0x054e] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.icing.zzfu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int zzn(T r20) {
        /*
            Method dump skipped, instructions count: 2986
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfl.zzn(java.lang.Object):int");
    }

    private static <UT, UB> int zza(zzgm<UT, UB> zzgmVar, T t) {
        return zzgmVar.zzn(zzgmVar.zzp(t));
    }

    private static List<?> zzd(Object obj, long j) {
        return (List) zzgs.zzo(obj, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:178:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
    @Override // com.google.android.gms.internal.icing.zzfu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r14, com.google.android.gms.internal.icing.zzhg r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2916
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfl.zza(java.lang.Object, com.google.android.gms.internal.icing.zzhg):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void zzb(T r18, com.google.android.gms.internal.icing.zzhg r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1334
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfl.zzb(java.lang.Object, com.google.android.gms.internal.icing.zzhg):void");
    }

    private final <K, V> void zza(zzhg zzhgVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzhgVar.zza(i, this.zznf.zzk(zzaf(i2)), this.zznf.zzi(obj));
        }
    }

    private static <UT, UB> void zza(zzgm<UT, UB> zzgmVar, T t, zzhg zzhgVar) throws IOException {
        zzgmVar.zza(zzgmVar.zzp(t), zzhgVar);
    }

    private final zzfu zzae(int i) {
        int i2 = (i / 3) << 1;
        zzfu zzfuVar = (zzfu) this.zzmu[i2];
        if (zzfuVar != null) {
            return zzfuVar;
        }
        zzfu<T> zzfuVarZze = zzft.zzcv().zze((Class) this.zzmu[i2 + 1]);
        this.zzmu[i2] = zzfuVarZze;
        return zzfuVarZze;
    }

    private final Object zzaf(int i) {
        return this.zzmu[(i / 3) << 1];
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final void zzf(T t) {
        int i;
        int i2 = this.zznb;
        while (true) {
            i = this.zznc;
            if (i2 >= i) {
                break;
            }
            long jZzag = zzag(this.zzna[i2]) & 1048575;
            Object objZzo = zzgs.zzo(t, jZzag);
            if (objZzo != null) {
                zzgs.zza(t, jZzag, this.zznf.zzj(objZzo));
            }
            i2++;
        }
        int length = this.zzna.length;
        while (i < length) {
            this.zzne.zza(t, this.zzna[i]);
            i++;
        }
        this.zzmo.zzf(t);
        if (this.zzmp) {
            this.zzmq.zzf(t);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ca  */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.google.android.gms.internal.icing.zzfu] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12, types: [com.google.android.gms.internal.icing.zzfu] */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    @Override // com.google.android.gms.internal.icing.zzfu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzm(T r14) {
        /*
            Method dump skipped, instructions count: 285
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfl.zzm(java.lang.Object):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzfu zzfuVar) {
        return zzfuVar.zzm(zzgs.zzo(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzhg zzhgVar) throws IOException {
        if (obj instanceof String) {
            zzhgVar.zza(i, (String) obj);
        } else {
            zzhgVar.zza(i, (zzct) obj);
        }
    }

    private final int zzag(int i) {
        return this.zzmt[i + 1];
    }

    private final int zzah(int i) {
        return this.zzmt[i + 2];
    }

    private static <T> double zze(T t, long j) {
        return ((Double) zzgs.zzo(t, j)).doubleValue();
    }

    private static <T> float zzf(T t, long j) {
        return ((Float) zzgs.zzo(t, j)).floatValue();
    }

    private static <T> int zzg(T t, long j) {
        return ((Integer) zzgs.zzo(t, j)).intValue();
    }

    private static <T> long zzh(T t, long j) {
        return ((Long) zzgs.zzo(t, j)).longValue();
    }

    private static <T> boolean zzi(T t, long j) {
        return ((Boolean) zzgs.zzo(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((zzfl<T>) t, i) == zza((zzfl<T>) t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        if (this.zzmy) {
            return zza((zzfl<T>) t, i);
        }
        return (i2 & i3) != 0;
    }

    private final boolean zza(T t, int i) {
        if (this.zzmy) {
            int iZzag = zzag(i);
            long j = iZzag & 1048575;
            switch ((iZzag & 267386880) >>> 20) {
                case 0:
                    return zzgs.zzn(t, j) != 0.0d;
                case 1:
                    return zzgs.zzm(t, j) != 0.0f;
                case 2:
                    return zzgs.zzk(t, j) != 0;
                case 3:
                    return zzgs.zzk(t, j) != 0;
                case 4:
                    return zzgs.zzj(t, j) != 0;
                case 5:
                    return zzgs.zzk(t, j) != 0;
                case 6:
                    return zzgs.zzj(t, j) != 0;
                case 7:
                    return zzgs.zzl(t, j);
                case 8:
                    Object objZzo = zzgs.zzo(t, j);
                    if (objZzo instanceof String) {
                        return !((String) objZzo).isEmpty();
                    }
                    if (objZzo instanceof zzct) {
                        return !zzct.zzgi.equals(objZzo);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return zzgs.zzo(t, j) != null;
                case 10:
                    return !zzct.zzgi.equals(zzgs.zzo(t, j));
                case 11:
                    return zzgs.zzj(t, j) != 0;
                case 12:
                    return zzgs.zzj(t, j) != 0;
                case 13:
                    return zzgs.zzj(t, j) != 0;
                case 14:
                    return zzgs.zzk(t, j) != 0;
                case 15:
                    return zzgs.zzj(t, j) != 0;
                case 16:
                    return zzgs.zzk(t, j) != 0;
                case 17:
                    return zzgs.zzo(t, j) != null;
                default:
                    throw new IllegalArgumentException();
            }
        }
        int iZzah = zzah(i);
        return (zzgs.zzj(t, (long) (iZzah & 1048575)) & (1 << (iZzah >>> 20))) != 0;
    }

    private final void zzb(T t, int i) {
        if (this.zzmy) {
            return;
        }
        int iZzah = zzah(i);
        long j = iZzah & 1048575;
        zzgs.zza((Object) t, j, zzgs.zzj(t, j) | (1 << (iZzah >>> 20)));
    }

    private final boolean zza(T t, int i, int i2) {
        return zzgs.zzj(t, (long) (zzah(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzgs.zza((Object) t, zzah(i2) & 1048575, i);
    }
}
