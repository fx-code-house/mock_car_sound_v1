package com.google.android.gms.internal.vision;

import java.io.IOException;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzhj implements zzkd {
    private int tag;
    private int zzts;
    private final zzhe zzul;
    private int zzum = 0;

    public static zzhj zza(zzhe zzheVar) {
        return zzheVar.zzue != null ? zzheVar.zzue : new zzhj(zzheVar);
    }

    private zzhj(zzhe zzheVar) {
        zzhe zzheVar2 = (zzhe) zzie.zza(zzheVar, "input");
        this.zzul = zzheVar2;
        zzheVar2.zzue = this;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzeo() throws IOException {
        int i = this.zzum;
        if (i != 0) {
            this.tag = i;
            this.zzum = 0;
        } else {
            this.tag = this.zzul.zzfr();
        }
        int i2 = this.tag;
        if (i2 == 0 || i2 == this.zzts) {
            return Integer.MAX_VALUE;
        }
        return i2 >>> 3;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int getTag() {
        return this.tag;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final boolean zzep() throws IOException {
        int i;
        if (this.zzul.zzen() || (i = this.tag) == this.zzts) {
            return false;
        }
        return this.zzul.zzay(i);
    }

    private final void zzaq(int i) throws IOException {
        if ((this.tag & 7) != i) {
            throw zzin.zzhm();
        }
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final double readDouble() throws IOException {
        zzaq(1);
        return this.zzul.readDouble();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final float readFloat() throws IOException {
        zzaq(5);
        return this.zzul.readFloat();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final long zzeq() throws IOException {
        zzaq(0);
        return this.zzul.zzeq();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final long zzer() throws IOException {
        zzaq(0);
        return this.zzul.zzer();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzes() throws IOException {
        zzaq(0);
        return this.zzul.zzes();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final long zzet() throws IOException {
        zzaq(1);
        return this.zzul.zzet();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzeu() throws IOException {
        zzaq(5);
        return this.zzul.zzeu();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final boolean zzev() throws IOException {
        zzaq(0);
        return this.zzul.zzev();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final String readString() throws IOException {
        zzaq(2);
        return this.zzul.readString();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final String zzew() throws IOException {
        zzaq(2);
        return this.zzul.zzew();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final <T> T zza(Class<T> cls, zzho zzhoVar) throws IOException {
        zzaq(2);
        return (T) zzb(zzjy.zzij().zzf(cls), zzhoVar);
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final <T> T zza(zzkc<T> zzkcVar, zzho zzhoVar) throws IOException {
        zzaq(2);
        return (T) zzb(zzkcVar, zzhoVar);
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final <T> T zzb(Class<T> cls, zzho zzhoVar) throws IOException {
        zzaq(3);
        return (T) zzd(zzjy.zzij().zzf(cls), zzhoVar);
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final <T> T zzc(zzkc<T> zzkcVar, zzho zzhoVar) throws IOException {
        zzaq(3);
        return (T) zzd(zzkcVar, zzhoVar);
    }

    private final <T> T zzb(zzkc<T> zzkcVar, zzho zzhoVar) throws IOException {
        int iZzey = this.zzul.zzey();
        if (this.zzul.zzub >= this.zzul.zzuc) {
            throw new zzin("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
        }
        int iZzaz = this.zzul.zzaz(iZzey);
        T tNewInstance = zzkcVar.newInstance();
        this.zzul.zzub++;
        zzkcVar.zza(tNewInstance, this, zzhoVar);
        zzkcVar.zzj(tNewInstance);
        this.zzul.zzax(0);
        zzhe zzheVar = this.zzul;
        zzheVar.zzub--;
        this.zzul.zzba(iZzaz);
        return tNewInstance;
    }

    private final <T> T zzd(zzkc<T> zzkcVar, zzho zzhoVar) throws IOException {
        int i = this.zzts;
        this.zzts = ((this.tag >>> 3) << 3) | 4;
        try {
            T tNewInstance = zzkcVar.newInstance();
            zzkcVar.zza(tNewInstance, this, zzhoVar);
            zzkcVar.zzj(tNewInstance);
            if (this.tag == this.zzts) {
                return tNewInstance;
            }
            throw zzin.zzhn();
        } finally {
            this.zzts = i;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final zzgs zzex() throws IOException {
        zzaq(2);
        return this.zzul.zzex();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzey() throws IOException {
        zzaq(0);
        return this.zzul.zzey();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzez() throws IOException {
        zzaq(0);
        return this.zzul.zzez();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzfa() throws IOException {
        zzaq(5);
        return this.zzul.zzfa();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final long zzfb() throws IOException {
        zzaq(1);
        return this.zzul.zzfb();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzfc() throws IOException {
        zzaq(0);
        return this.zzul.zzfc();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final long zzfd() throws IOException {
        zzaq(0);
        return this.zzul.zzfd();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zza(List<Double> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzhm) {
            zzhm zzhmVar = (zzhm) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzhmVar.zzc(this.zzul.readDouble());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzey = this.zzul.zzey();
                zzar(iZzey);
                int iZzft = this.zzul.zzft() + iZzey;
                do {
                    zzhmVar.zzc(this.zzul.readDouble());
                } while (this.zzul.zzft() < iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                list.add(Double.valueOf(this.zzul.readDouble()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzey2 = this.zzul.zzey();
            zzar(iZzey2);
            int iZzft2 = this.zzul.zzft() + iZzey2;
            do {
                list.add(Double.valueOf(this.zzul.readDouble()));
            } while (this.zzul.zzft() < iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzb(List<Float> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzhz) {
            zzhz zzhzVar = (zzhz) list;
            int i = this.tag & 7;
            if (i == 2) {
                int iZzey = this.zzul.zzey();
                zzas(iZzey);
                int iZzft = this.zzul.zzft() + iZzey;
                do {
                    zzhzVar.zzu(this.zzul.readFloat());
                } while (this.zzul.zzft() < iZzft);
                return;
            }
            if (i == 5) {
                do {
                    zzhzVar.zzu(this.zzul.readFloat());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 2) {
            int iZzey2 = this.zzul.zzey();
            zzas(iZzey2);
            int iZzft2 = this.zzul.zzft() + iZzey2;
            do {
                list.add(Float.valueOf(this.zzul.readFloat()));
            } while (this.zzul.zzft() < iZzft2);
            return;
        }
        if (i2 == 5) {
            do {
                list.add(Float.valueOf(this.zzul.readFloat()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzc(List<Long> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzjbVar.zzac(this.zzul.zzeq());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzft = this.zzul.zzft() + this.zzul.zzey();
                do {
                    zzjbVar.zzac(this.zzul.zzeq());
                } while (this.zzul.zzft() < iZzft);
                zzat(iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Long.valueOf(this.zzul.zzeq()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzft2 = this.zzul.zzft() + this.zzul.zzey();
            do {
                list.add(Long.valueOf(this.zzul.zzeq()));
            } while (this.zzul.zzft() < iZzft2);
            zzat(iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzd(List<Long> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzjbVar.zzac(this.zzul.zzer());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzft = this.zzul.zzft() + this.zzul.zzey();
                do {
                    zzjbVar.zzac(this.zzul.zzer());
                } while (this.zzul.zzft() < iZzft);
                zzat(iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Long.valueOf(this.zzul.zzer()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzft2 = this.zzul.zzft() + this.zzul.zzey();
            do {
                list.add(Long.valueOf(this.zzul.zzer()));
            } while (this.zzul.zzft() < iZzft2);
            zzat(iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zze(List<Integer> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzifVar.zzbs(this.zzul.zzes());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzft = this.zzul.zzft() + this.zzul.zzey();
                do {
                    zzifVar.zzbs(this.zzul.zzes());
                } while (this.zzul.zzft() < iZzft);
                zzat(iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzul.zzes()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzft2 = this.zzul.zzft() + this.zzul.zzey();
            do {
                list.add(Integer.valueOf(this.zzul.zzes()));
            } while (this.zzul.zzft() < iZzft2);
            zzat(iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzf(List<Long> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzjbVar.zzac(this.zzul.zzet());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzey = this.zzul.zzey();
                zzar(iZzey);
                int iZzft = this.zzul.zzft() + iZzey;
                do {
                    zzjbVar.zzac(this.zzul.zzet());
                } while (this.zzul.zzft() < iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                list.add(Long.valueOf(this.zzul.zzet()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzey2 = this.zzul.zzey();
            zzar(iZzey2);
            int iZzft2 = this.zzul.zzft() + iZzey2;
            do {
                list.add(Long.valueOf(this.zzul.zzet()));
            } while (this.zzul.zzft() < iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzg(List<Integer> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i = this.tag & 7;
            if (i == 2) {
                int iZzey = this.zzul.zzey();
                zzas(iZzey);
                int iZzft = this.zzul.zzft() + iZzey;
                do {
                    zzifVar.zzbs(this.zzul.zzeu());
                } while (this.zzul.zzft() < iZzft);
                return;
            }
            if (i == 5) {
                do {
                    zzifVar.zzbs(this.zzul.zzeu());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 2) {
            int iZzey2 = this.zzul.zzey();
            zzas(iZzey2);
            int iZzft2 = this.zzul.zzft() + iZzey2;
            do {
                list.add(Integer.valueOf(this.zzul.zzeu()));
            } while (this.zzul.zzft() < iZzft2);
            return;
        }
        if (i2 == 5) {
            do {
                list.add(Integer.valueOf(this.zzul.zzeu()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzh(List<Boolean> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzgq) {
            zzgq zzgqVar = (zzgq) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzgqVar.addBoolean(this.zzul.zzev());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzft = this.zzul.zzft() + this.zzul.zzey();
                do {
                    zzgqVar.addBoolean(this.zzul.zzev());
                } while (this.zzul.zzft() < iZzft);
                zzat(iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Boolean.valueOf(this.zzul.zzev()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzft2 = this.zzul.zzft() + this.zzul.zzey();
            do {
                list.add(Boolean.valueOf(this.zzul.zzev()));
            } while (this.zzul.zzft() < iZzft2);
            zzat(iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void readStringList(List<String> list) throws IOException {
        zza(list, false);
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzi(List<String> list) throws IOException {
        zza(list, true);
    }

    private final void zza(List<String> list, boolean z) throws IOException {
        int iZzfr;
        int iZzfr2;
        if ((this.tag & 7) != 2) {
            throw zzin.zzhm();
        }
        if ((list instanceof zziu) && !z) {
            zziu zziuVar = (zziu) list;
            do {
                zziuVar.zzc(zzex());
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr2 = this.zzul.zzfr();
                }
            } while (iZzfr2 == this.tag);
            this.zzum = iZzfr2;
            return;
        }
        do {
            list.add(z ? zzew() : readString());
            if (this.zzul.zzen()) {
                return;
            } else {
                iZzfr = this.zzul.zzfr();
            }
        } while (iZzfr == this.tag);
        this.zzum = iZzfr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.vision.zzkd
    public final <T> void zza(List<T> list, zzkc<T> zzkcVar, zzho zzhoVar) throws IOException {
        int iZzfr;
        int i = this.tag;
        if ((i & 7) != 2) {
            throw zzin.zzhm();
        }
        do {
            list.add(zzb(zzkcVar, zzhoVar));
            if (this.zzul.zzen() || this.zzum != 0) {
                return;
            } else {
                iZzfr = this.zzul.zzfr();
            }
        } while (iZzfr == i);
        this.zzum = iZzfr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.vision.zzkd
    public final <T> void zzb(List<T> list, zzkc<T> zzkcVar, zzho zzhoVar) throws IOException {
        int iZzfr;
        int i = this.tag;
        if ((i & 7) != 3) {
            throw zzin.zzhm();
        }
        do {
            list.add(zzd(zzkcVar, zzhoVar));
            if (this.zzul.zzen() || this.zzum != 0) {
                return;
            } else {
                iZzfr = this.zzul.zzfr();
            }
        } while (iZzfr == i);
        this.zzum = iZzfr;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzj(List<zzgs> list) throws IOException {
        int iZzfr;
        if ((this.tag & 7) != 2) {
            throw zzin.zzhm();
        }
        do {
            list.add(zzex());
            if (this.zzul.zzen()) {
                return;
            } else {
                iZzfr = this.zzul.zzfr();
            }
        } while (iZzfr == this.tag);
        this.zzum = iZzfr;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzk(List<Integer> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzifVar.zzbs(this.zzul.zzey());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzft = this.zzul.zzft() + this.zzul.zzey();
                do {
                    zzifVar.zzbs(this.zzul.zzey());
                } while (this.zzul.zzft() < iZzft);
                zzat(iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzul.zzey()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzft2 = this.zzul.zzft() + this.zzul.zzey();
            do {
                list.add(Integer.valueOf(this.zzul.zzey()));
            } while (this.zzul.zzft() < iZzft2);
            zzat(iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzl(List<Integer> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzifVar.zzbs(this.zzul.zzez());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzft = this.zzul.zzft() + this.zzul.zzey();
                do {
                    zzifVar.zzbs(this.zzul.zzez());
                } while (this.zzul.zzft() < iZzft);
                zzat(iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzul.zzez()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzft2 = this.zzul.zzft() + this.zzul.zzey();
            do {
                list.add(Integer.valueOf(this.zzul.zzez()));
            } while (this.zzul.zzft() < iZzft2);
            zzat(iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzm(List<Integer> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i = this.tag & 7;
            if (i == 2) {
                int iZzey = this.zzul.zzey();
                zzas(iZzey);
                int iZzft = this.zzul.zzft() + iZzey;
                do {
                    zzifVar.zzbs(this.zzul.zzfa());
                } while (this.zzul.zzft() < iZzft);
                return;
            }
            if (i == 5) {
                do {
                    zzifVar.zzbs(this.zzul.zzfa());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 2) {
            int iZzey2 = this.zzul.zzey();
            zzas(iZzey2);
            int iZzft2 = this.zzul.zzft() + iZzey2;
            do {
                list.add(Integer.valueOf(this.zzul.zzfa()));
            } while (this.zzul.zzft() < iZzft2);
            return;
        }
        if (i2 == 5) {
            do {
                list.add(Integer.valueOf(this.zzul.zzfa()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzn(List<Long> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzjbVar.zzac(this.zzul.zzfb());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzey = this.zzul.zzey();
                zzar(iZzey);
                int iZzft = this.zzul.zzft() + iZzey;
                do {
                    zzjbVar.zzac(this.zzul.zzfb());
                } while (this.zzul.zzft() < iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                list.add(Long.valueOf(this.zzul.zzfb()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzey2 = this.zzul.zzey();
            zzar(iZzey2);
            int iZzft2 = this.zzul.zzft() + iZzey2;
            do {
                list.add(Long.valueOf(this.zzul.zzfb()));
            } while (this.zzul.zzft() < iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzo(List<Integer> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzifVar.zzbs(this.zzul.zzfc());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzft = this.zzul.zzft() + this.zzul.zzey();
                do {
                    zzifVar.zzbs(this.zzul.zzfc());
                } while (this.zzul.zzft() < iZzft);
                zzat(iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzul.zzfc()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzft2 = this.zzul.zzft() + this.zzul.zzey();
            do {
                list.add(Integer.valueOf(this.zzul.zzfc()));
            } while (this.zzul.zzft() < iZzft2);
            zzat(iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzp(List<Long> list) throws IOException {
        int iZzfr;
        int iZzfr2;
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzjbVar.zzac(this.zzul.zzfd());
                    if (this.zzul.zzen()) {
                        return;
                    } else {
                        iZzfr2 = this.zzul.zzfr();
                    }
                } while (iZzfr2 == this.tag);
                this.zzum = iZzfr2;
                return;
            }
            if (i == 2) {
                int iZzft = this.zzul.zzft() + this.zzul.zzey();
                do {
                    zzjbVar.zzac(this.zzul.zzfd());
                } while (this.zzul.zzft() < iZzft);
                zzat(iZzft);
                return;
            }
            throw zzin.zzhm();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Long.valueOf(this.zzul.zzfd()));
                if (this.zzul.zzen()) {
                    return;
                } else {
                    iZzfr = this.zzul.zzfr();
                }
            } while (iZzfr == this.tag);
            this.zzum = iZzfr;
            return;
        }
        if (i2 == 2) {
            int iZzft2 = this.zzul.zzft() + this.zzul.zzey();
            do {
                list.add(Long.valueOf(this.zzul.zzfd()));
            } while (this.zzul.zzft() < iZzft2);
            zzat(iZzft2);
            return;
        }
        throw zzin.zzhm();
    }

    private static void zzar(int i) throws IOException {
        if ((i & 7) != 0) {
            throw zzin.zzhn();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x005b, code lost:
    
        r8.put(r2, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0063, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.vision.zzkd
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final <K, V> void zza(java.util.Map<K, V> r8, com.google.android.gms.internal.vision.zzje<K, V> r9, com.google.android.gms.internal.vision.zzho r10) throws java.io.IOException {
        /*
            r7 = this;
            r0 = 2
            r7.zzaq(r0)
            com.google.android.gms.internal.vision.zzhe r1 = r7.zzul
            int r1 = r1.zzey()
            com.google.android.gms.internal.vision.zzhe r2 = r7.zzul
            int r1 = r2.zzaz(r1)
            K r2 = r9.zzaaj
            V r3 = r9.zzgk
        L14:
            int r4 = r7.zzeo()     // Catch: java.lang.Throwable -> L64
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 == r5) goto L5b
            com.google.android.gms.internal.vision.zzhe r5 = r7.zzul     // Catch: java.lang.Throwable -> L64
            boolean r5 = r5.zzen()     // Catch: java.lang.Throwable -> L64
            if (r5 != 0) goto L5b
            r5 = 1
            java.lang.String r6 = "Unable to parse map entry."
            if (r4 == r5) goto L46
            if (r4 == r0) goto L39
            boolean r4 = r7.zzep()     // Catch: com.google.android.gms.internal.vision.zzim -> L4e java.lang.Throwable -> L64
            if (r4 == 0) goto L33
            goto L14
        L33:
            com.google.android.gms.internal.vision.zzin r4 = new com.google.android.gms.internal.vision.zzin     // Catch: com.google.android.gms.internal.vision.zzim -> L4e java.lang.Throwable -> L64
            r4.<init>(r6)     // Catch: com.google.android.gms.internal.vision.zzim -> L4e java.lang.Throwable -> L64
            throw r4     // Catch: com.google.android.gms.internal.vision.zzim -> L4e java.lang.Throwable -> L64
        L39:
            com.google.android.gms.internal.vision.zzll r4 = r9.zzaak     // Catch: com.google.android.gms.internal.vision.zzim -> L4e java.lang.Throwable -> L64
            V r5 = r9.zzgk     // Catch: com.google.android.gms.internal.vision.zzim -> L4e java.lang.Throwable -> L64
            java.lang.Class r5 = r5.getClass()     // Catch: com.google.android.gms.internal.vision.zzim -> L4e java.lang.Throwable -> L64
            java.lang.Object r3 = r7.zza(r4, r5, r10)     // Catch: com.google.android.gms.internal.vision.zzim -> L4e java.lang.Throwable -> L64
            goto L14
        L46:
            com.google.android.gms.internal.vision.zzll r4 = r9.zzaai     // Catch: com.google.android.gms.internal.vision.zzim -> L4e java.lang.Throwable -> L64
            r5 = 0
            java.lang.Object r2 = r7.zza(r4, r5, r5)     // Catch: com.google.android.gms.internal.vision.zzim -> L4e java.lang.Throwable -> L64
            goto L14
        L4e:
            boolean r4 = r7.zzep()     // Catch: java.lang.Throwable -> L64
            if (r4 == 0) goto L55
            goto L14
        L55:
            com.google.android.gms.internal.vision.zzin r8 = new com.google.android.gms.internal.vision.zzin     // Catch: java.lang.Throwable -> L64
            r8.<init>(r6)     // Catch: java.lang.Throwable -> L64
            throw r8     // Catch: java.lang.Throwable -> L64
        L5b:
            r8.put(r2, r3)     // Catch: java.lang.Throwable -> L64
            com.google.android.gms.internal.vision.zzhe r8 = r7.zzul
            r8.zzba(r1)
            return
        L64:
            r8 = move-exception
            com.google.android.gms.internal.vision.zzhe r9 = r7.zzul
            r9.zzba(r1)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzhj.zza(java.util.Map, com.google.android.gms.internal.vision.zzje, com.google.android.gms.internal.vision.zzho):void");
    }

    private final Object zza(zzll zzllVar, Class<?> cls, zzho zzhoVar) throws IOException {
        switch (zzhi.zztn[zzllVar.ordinal()]) {
            case 1:
                return Boolean.valueOf(zzev());
            case 2:
                return zzex();
            case 3:
                return Double.valueOf(readDouble());
            case 4:
                return Integer.valueOf(zzez());
            case 5:
                return Integer.valueOf(zzeu());
            case 6:
                return Long.valueOf(zzet());
            case 7:
                return Float.valueOf(readFloat());
            case 8:
                return Integer.valueOf(zzes());
            case 9:
                return Long.valueOf(zzer());
            case 10:
                return zza(cls, zzhoVar);
            case 11:
                return Integer.valueOf(zzfa());
            case 12:
                return Long.valueOf(zzfb());
            case 13:
                return Integer.valueOf(zzfc());
            case 14:
                return Long.valueOf(zzfd());
            case 15:
                return zzew();
            case 16:
                return Integer.valueOf(zzey());
            case 17:
                return Long.valueOf(zzeq());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static void zzas(int i) throws IOException {
        if ((i & 3) != 0) {
            throw zzin.zzhn();
        }
    }

    private final void zzat(int i) throws IOException {
        if (this.zzul.zzft() != i) {
            throw zzin.zzhh();
        }
    }
}
