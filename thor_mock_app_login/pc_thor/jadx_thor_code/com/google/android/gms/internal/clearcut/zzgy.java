package com.google.android.gms.internal.clearcut;

import java.io.IOException;

/* loaded from: classes2.dex */
public final class zzgy extends zzfu<zzgy> implements Cloneable {
    private String[] zzbiw = zzgb.zzsc;
    private String[] zzbix = zzgb.zzsc;
    private int[] zzbiy = zzgb.zzrx;
    private long[] zzbiz = zzgb.zzry;
    private long[] zzbja = zzgb.zzry;

    public zzgy() {
        this.zzrj = null;
        this.zzrs = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.clearcut.zzfu, com.google.android.gms.internal.clearcut.zzfz
    /* renamed from: zzgb, reason: merged with bridge method [inline-methods] */
    public final zzgy clone() {
        try {
            zzgy zzgyVar = (zzgy) super.clone();
            String[] strArr = this.zzbiw;
            if (strArr != null && strArr.length > 0) {
                zzgyVar.zzbiw = (String[]) strArr.clone();
            }
            String[] strArr2 = this.zzbix;
            if (strArr2 != null && strArr2.length > 0) {
                zzgyVar.zzbix = (String[]) strArr2.clone();
            }
            int[] iArr = this.zzbiy;
            if (iArr != null && iArr.length > 0) {
                zzgyVar.zzbiy = (int[]) iArr.clone();
            }
            long[] jArr = this.zzbiz;
            if (jArr != null && jArr.length > 0) {
                zzgyVar.zzbiz = (long[]) jArr.clone();
            }
            long[] jArr2 = this.zzbja;
            if (jArr2 != null && jArr2.length > 0) {
                zzgyVar.zzbja = (long[]) jArr2.clone();
            }
            return zzgyVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgy)) {
            return false;
        }
        zzgy zzgyVar = (zzgy) obj;
        if (zzfy.equals(this.zzbiw, zzgyVar.zzbiw) && zzfy.equals(this.zzbix, zzgyVar.zzbix) && zzfy.equals(this.zzbiy, zzgyVar.zzbiy) && zzfy.equals(this.zzbiz, zzgyVar.zzbiz) && zzfy.equals(this.zzbja, zzgyVar.zzbja)) {
            return (this.zzrj == null || this.zzrj.isEmpty()) ? zzgyVar.zzrj == null || zzgyVar.zzrj.isEmpty() : this.zzrj.equals(zzgyVar.zzrj);
        }
        return false;
    }

    public final int hashCode() {
        return ((((((((((((getClass().getName().hashCode() + 527) * 31) + zzfy.hashCode(this.zzbiw)) * 31) + zzfy.hashCode(this.zzbix)) * 31) + zzfy.hashCode(this.zzbiy)) * 31) + zzfy.hashCode(this.zzbiz)) * 31) + zzfy.hashCode(this.zzbja)) * 31) + ((this.zzrj == null || this.zzrj.isEmpty()) ? 0 : this.zzrj.hashCode());
    }

    @Override // com.google.android.gms.internal.clearcut.zzfu, com.google.android.gms.internal.clearcut.zzfz
    public final void zza(zzfs zzfsVar) throws IOException {
        String[] strArr = this.zzbiw;
        int i = 0;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.zzbiw;
                if (i2 >= strArr2.length) {
                    break;
                }
                String str = strArr2[i2];
                if (str != null) {
                    zzfsVar.zza(1, str);
                }
                i2++;
            }
        }
        String[] strArr3 = this.zzbix;
        if (strArr3 != null && strArr3.length > 0) {
            int i3 = 0;
            while (true) {
                String[] strArr4 = this.zzbix;
                if (i3 >= strArr4.length) {
                    break;
                }
                String str2 = strArr4[i3];
                if (str2 != null) {
                    zzfsVar.zza(2, str2);
                }
                i3++;
            }
        }
        int[] iArr = this.zzbiy;
        if (iArr != null && iArr.length > 0) {
            int i4 = 0;
            while (true) {
                int[] iArr2 = this.zzbiy;
                if (i4 >= iArr2.length) {
                    break;
                }
                zzfsVar.zzc(3, iArr2[i4]);
                i4++;
            }
        }
        long[] jArr = this.zzbiz;
        if (jArr != null && jArr.length > 0) {
            int i5 = 0;
            while (true) {
                long[] jArr2 = this.zzbiz;
                if (i5 >= jArr2.length) {
                    break;
                }
                zzfsVar.zzi(4, jArr2[i5]);
                i5++;
            }
        }
        long[] jArr3 = this.zzbja;
        if (jArr3 != null && jArr3.length > 0) {
            while (true) {
                long[] jArr4 = this.zzbja;
                if (i >= jArr4.length) {
                    break;
                }
                zzfsVar.zzi(5, jArr4[i]);
                i++;
            }
        }
        super.zza(zzfsVar);
    }

    @Override // com.google.android.gms.internal.clearcut.zzfu, com.google.android.gms.internal.clearcut.zzfz
    protected final int zzen() {
        long[] jArr;
        int[] iArr;
        int iZzen = super.zzen();
        String[] strArr = this.zzbiw;
        int i = 0;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            int iZzh = 0;
            int i3 = 0;
            while (true) {
                String[] strArr2 = this.zzbiw;
                if (i2 >= strArr2.length) {
                    break;
                }
                String str = strArr2[i2];
                if (str != null) {
                    i3++;
                    iZzh += zzfs.zzh(str);
                }
                i2++;
            }
            iZzen = iZzen + iZzh + (i3 * 1);
        }
        String[] strArr3 = this.zzbix;
        if (strArr3 != null && strArr3.length > 0) {
            int i4 = 0;
            int iZzh2 = 0;
            int i5 = 0;
            while (true) {
                String[] strArr4 = this.zzbix;
                if (i4 >= strArr4.length) {
                    break;
                }
                String str2 = strArr4[i4];
                if (str2 != null) {
                    i5++;
                    iZzh2 += zzfs.zzh(str2);
                }
                i4++;
            }
            iZzen = iZzen + iZzh2 + (i5 * 1);
        }
        int[] iArr2 = this.zzbiy;
        if (iArr2 != null && iArr2.length > 0) {
            int i6 = 0;
            int iZzs = 0;
            while (true) {
                iArr = this.zzbiy;
                if (i6 >= iArr.length) {
                    break;
                }
                iZzs += zzfs.zzs(iArr[i6]);
                i6++;
            }
            iZzen = iZzen + iZzs + (iArr.length * 1);
        }
        long[] jArr2 = this.zzbiz;
        if (jArr2 != null && jArr2.length > 0) {
            int i7 = 0;
            int iZzo = 0;
            while (true) {
                jArr = this.zzbiz;
                if (i7 >= jArr.length) {
                    break;
                }
                iZzo += zzfs.zzo(jArr[i7]);
                i7++;
            }
            iZzen = iZzen + iZzo + (jArr.length * 1);
        }
        long[] jArr3 = this.zzbja;
        if (jArr3 == null || jArr3.length <= 0) {
            return iZzen;
        }
        int iZzo2 = 0;
        while (true) {
            long[] jArr4 = this.zzbja;
            if (i >= jArr4.length) {
                return iZzen + iZzo2 + (jArr4.length * 1);
            }
            iZzo2 += zzfs.zzo(jArr4[i]);
            i++;
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzfu
    /* renamed from: zzeo */
    public final /* synthetic */ zzfu clone() throws CloneNotSupportedException {
        return (zzgy) clone();
    }

    @Override // com.google.android.gms.internal.clearcut.zzfu, com.google.android.gms.internal.clearcut.zzfz
    /* renamed from: zzep */
    public final /* synthetic */ zzfz clone() throws CloneNotSupportedException {
        return (zzgy) clone();
    }
}
