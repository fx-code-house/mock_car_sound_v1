package com.google.android.gms.internal.vision;

import com.google.common.base.Ascii;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzgr extends zzgp {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private int tag;
    private final boolean zztq;
    private final int zztr;
    private int zzts;

    public zzgr(ByteBuffer byteBuffer, boolean z) {
        super(null);
        this.zztq = true;
        this.buffer = byteBuffer.array();
        int iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
        this.pos = iArrayOffset;
        this.zztr = iArrayOffset;
        this.limit = byteBuffer.arrayOffset() + byteBuffer.limit();
    }

    private final boolean zzen() {
        return this.pos == this.limit;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzeo() throws IOException {
        if (zzen()) {
            return Integer.MAX_VALUE;
        }
        int iZzfe = zzfe();
        this.tag = iZzfe;
        if (iZzfe == this.zzts) {
            return Integer.MAX_VALUE;
        }
        return iZzfe >>> 3;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int getTag() {
        return this.tag;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0076  */
    @Override // com.google.android.gms.internal.vision.zzkd
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzep() throws java.io.IOException {
        /*
            r7 = this;
            boolean r0 = r7.zzen()
            r1 = 0
            if (r0 != 0) goto L85
            int r0 = r7.tag
            int r2 = r7.zzts
            if (r0 != r2) goto Lf
            goto L85
        Lf:
            r3 = r0 & 7
            r4 = 1
            if (r3 == 0) goto L59
            if (r3 == r4) goto L53
            r1 = 2
            if (r3 == r1) goto L4b
            r1 = 4
            r5 = 3
            if (r3 == r5) goto L29
            r0 = 5
            if (r3 != r0) goto L24
            r7.zzao(r1)
            return r4
        L24:
            com.google.android.gms.internal.vision.zzim r0 = com.google.android.gms.internal.vision.zzin.zzhm()
            throw r0
        L29:
            int r0 = r0 >>> r5
            int r0 = r0 << r5
            r0 = r0 | r1
            r7.zzts = r0
        L2e:
            int r0 = r7.zzeo()
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r0 == r1) goto L3d
            boolean r0 = r7.zzep()
            if (r0 != 0) goto L2e
        L3d:
            int r0 = r7.tag
            int r1 = r7.zzts
            if (r0 != r1) goto L46
            r7.zzts = r2
            return r4
        L46:
            com.google.android.gms.internal.vision.zzin r0 = com.google.android.gms.internal.vision.zzin.zzhn()
            throw r0
        L4b:
            int r0 = r7.zzfe()
            r7.zzao(r0)
            return r4
        L53:
            r0 = 8
            r7.zzao(r0)
            return r4
        L59:
            int r0 = r7.limit
            int r2 = r7.pos
            int r0 = r0 - r2
            r3 = 10
            if (r0 < r3) goto L74
            byte[] r0 = r7.buffer
            r5 = r1
        L65:
            if (r5 >= r3) goto L74
            int r6 = r2 + 1
            r2 = r0[r2]
            if (r2 < 0) goto L70
            r7.pos = r6
            goto L7f
        L70:
            int r5 = r5 + 1
            r2 = r6
            goto L65
        L74:
            if (r1 >= r3) goto L80
            byte r0 = r7.readByte()
            if (r0 >= 0) goto L7f
            int r1 = r1 + 1
            goto L74
        L7f:
            return r4
        L80:
            com.google.android.gms.internal.vision.zzin r0 = com.google.android.gms.internal.vision.zzin.zzhj()
            throw r0
        L85:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzgr.zzep():boolean");
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final double readDouble() throws IOException {
        zzaq(1);
        return Double.longBitsToDouble(zzfi());
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final float readFloat() throws IOException {
        zzaq(5);
        return Float.intBitsToFloat(zzfh());
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final long zzeq() throws IOException {
        zzaq(0);
        return zzff();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final long zzer() throws IOException {
        zzaq(0);
        return zzff();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzes() throws IOException {
        zzaq(0);
        return zzfe();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final long zzet() throws IOException {
        zzaq(1);
        return zzfi();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzeu() throws IOException {
        zzaq(5);
        return zzfh();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final boolean zzev() throws IOException {
        zzaq(0);
        return zzfe() != 0;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final String readString() throws IOException {
        return zzj(false);
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final String zzew() throws IOException {
        return zzj(true);
    }

    private final String zzj(boolean z) throws IOException {
        zzaq(2);
        int iZzfe = zzfe();
        if (iZzfe == 0) {
            return "";
        }
        zzap(iZzfe);
        if (z) {
            byte[] bArr = this.buffer;
            int i = this.pos;
            if (!zzld.zzf(bArr, i, i + iZzfe)) {
                throw zzin.zzho();
            }
        }
        String str = new String(this.buffer, this.pos, iZzfe, zzie.UTF_8);
        this.pos += iZzfe;
        return str;
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

    private final <T> T zzb(zzkc<T> zzkcVar, zzho zzhoVar) throws IOException {
        int iZzfe = zzfe();
        zzap(iZzfe);
        int i = this.limit;
        int i2 = this.pos + iZzfe;
        this.limit = i2;
        try {
            T tNewInstance = zzkcVar.newInstance();
            zzkcVar.zza(tNewInstance, this, zzhoVar);
            zzkcVar.zzj(tNewInstance);
            if (this.pos == i2) {
                return tNewInstance;
            }
            throw zzin.zzhn();
        } finally {
            this.limit = i;
        }
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
        zzgs zzgsVarZza;
        zzaq(2);
        int iZzfe = zzfe();
        if (iZzfe == 0) {
            return zzgs.zztt;
        }
        zzap(iZzfe);
        if (this.zztq) {
            zzgsVarZza = zzgs.zzb(this.buffer, this.pos, iZzfe);
        } else {
            zzgsVarZza = zzgs.zza(this.buffer, this.pos, iZzfe);
        }
        this.pos += iZzfe;
        return zzgsVarZza;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzey() throws IOException {
        zzaq(0);
        return zzfe();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzez() throws IOException {
        zzaq(0);
        return zzfe();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzfa() throws IOException {
        zzaq(5);
        return zzfh();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final long zzfb() throws IOException {
        zzaq(1);
        return zzfi();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final int zzfc() throws IOException {
        zzaq(0);
        return zzhe.zzbb(zzfe());
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final long zzfd() throws IOException {
        zzaq(0);
        return zzhe.zzr(zzff());
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zza(List<Double> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzhm) {
            zzhm zzhmVar = (zzhm) list;
            int i3 = this.tag & 7;
            if (i3 == 1) {
                do {
                    zzhmVar.zzc(readDouble());
                    if (zzen()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (zzfe() == this.tag);
                this.pos = i2;
                return;
            }
            if (i3 == 2) {
                int iZzfe = zzfe();
                zzar(iZzfe);
                int i4 = this.pos + iZzfe;
                while (this.pos < i4) {
                    zzhmVar.zzc(Double.longBitsToDouble(zzfk()));
                }
                return;
            }
            throw zzin.zzhm();
        }
        int i5 = this.tag & 7;
        if (i5 == 1) {
            do {
                list.add(Double.valueOf(readDouble()));
                if (zzen()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i;
            return;
        }
        if (i5 == 2) {
            int iZzfe2 = zzfe();
            zzar(iZzfe2);
            int i6 = this.pos + iZzfe2;
            while (this.pos < i6) {
                list.add(Double.valueOf(Double.longBitsToDouble(zzfk())));
            }
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzb(List<Float> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzhz) {
            zzhz zzhzVar = (zzhz) list;
            int i3 = this.tag & 7;
            if (i3 == 2) {
                int iZzfe = zzfe();
                zzas(iZzfe);
                int i4 = this.pos + iZzfe;
                while (this.pos < i4) {
                    zzhzVar.zzu(Float.intBitsToFloat(zzfj()));
                }
                return;
            }
            if (i3 == 5) {
                do {
                    zzhzVar.zzu(readFloat());
                    if (zzen()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (zzfe() == this.tag);
                this.pos = i2;
                return;
            }
            throw zzin.zzhm();
        }
        int i5 = this.tag & 7;
        if (i5 == 2) {
            int iZzfe2 = zzfe();
            zzas(iZzfe2);
            int i6 = this.pos + iZzfe2;
            while (this.pos < i6) {
                list.add(Float.valueOf(Float.intBitsToFloat(zzfj())));
            }
            return;
        }
        if (i5 == 5) {
            do {
                list.add(Float.valueOf(readFloat()));
                if (zzen()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i;
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzc(List<Long> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            int i3 = this.tag & 7;
            if (i3 == 0) {
                do {
                    zzjbVar.zzac(zzeq());
                    if (zzen()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (zzfe() == this.tag);
                this.pos = i2;
                return;
            }
            if (i3 == 2) {
                int iZzfe = this.pos + zzfe();
                while (this.pos < iZzfe) {
                    zzjbVar.zzac(zzff());
                }
                zzat(iZzfe);
                return;
            }
            throw zzin.zzhm();
        }
        int i4 = this.tag & 7;
        if (i4 == 0) {
            do {
                list.add(Long.valueOf(zzeq()));
                if (zzen()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i;
            return;
        }
        if (i4 == 2) {
            int iZzfe2 = this.pos + zzfe();
            while (this.pos < iZzfe2) {
                list.add(Long.valueOf(zzff()));
            }
            zzat(iZzfe2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzd(List<Long> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            int i3 = this.tag & 7;
            if (i3 == 0) {
                do {
                    zzjbVar.zzac(zzer());
                    if (zzen()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (zzfe() == this.tag);
                this.pos = i2;
                return;
            }
            if (i3 == 2) {
                int iZzfe = this.pos + zzfe();
                while (this.pos < iZzfe) {
                    zzjbVar.zzac(zzff());
                }
                zzat(iZzfe);
                return;
            }
            throw zzin.zzhm();
        }
        int i4 = this.tag & 7;
        if (i4 == 0) {
            do {
                list.add(Long.valueOf(zzer()));
                if (zzen()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i;
            return;
        }
        if (i4 == 2) {
            int iZzfe2 = this.pos + zzfe();
            while (this.pos < iZzfe2) {
                list.add(Long.valueOf(zzff()));
            }
            zzat(iZzfe2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zze(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i3 = this.tag & 7;
            if (i3 == 0) {
                do {
                    zzifVar.zzbs(zzes());
                    if (zzen()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (zzfe() == this.tag);
                this.pos = i2;
                return;
            }
            if (i3 == 2) {
                int iZzfe = this.pos + zzfe();
                while (this.pos < iZzfe) {
                    zzifVar.zzbs(zzfe());
                }
                zzat(iZzfe);
                return;
            }
            throw zzin.zzhm();
        }
        int i4 = this.tag & 7;
        if (i4 == 0) {
            do {
                list.add(Integer.valueOf(zzes()));
                if (zzen()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i;
            return;
        }
        if (i4 == 2) {
            int iZzfe2 = this.pos + zzfe();
            while (this.pos < iZzfe2) {
                list.add(Integer.valueOf(zzfe()));
            }
            zzat(iZzfe2);
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzf(List<Long> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            int i3 = this.tag & 7;
            if (i3 == 1) {
                do {
                    zzjbVar.zzac(zzet());
                    if (zzen()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (zzfe() == this.tag);
                this.pos = i2;
                return;
            }
            if (i3 == 2) {
                int iZzfe = zzfe();
                zzar(iZzfe);
                int i4 = this.pos + iZzfe;
                while (this.pos < i4) {
                    zzjbVar.zzac(zzfk());
                }
                return;
            }
            throw zzin.zzhm();
        }
        int i5 = this.tag & 7;
        if (i5 == 1) {
            do {
                list.add(Long.valueOf(zzet()));
                if (zzen()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i;
            return;
        }
        if (i5 == 2) {
            int iZzfe2 = zzfe();
            zzar(iZzfe2);
            int i6 = this.pos + iZzfe2;
            while (this.pos < i6) {
                list.add(Long.valueOf(zzfk()));
            }
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzg(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i3 = this.tag & 7;
            if (i3 == 2) {
                int iZzfe = zzfe();
                zzas(iZzfe);
                int i4 = this.pos + iZzfe;
                while (this.pos < i4) {
                    zzifVar.zzbs(zzfj());
                }
                return;
            }
            if (i3 == 5) {
                do {
                    zzifVar.zzbs(zzeu());
                    if (zzen()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (zzfe() == this.tag);
                this.pos = i2;
                return;
            }
            throw zzin.zzhm();
        }
        int i5 = this.tag & 7;
        if (i5 == 2) {
            int iZzfe2 = zzfe();
            zzas(iZzfe2);
            int i6 = this.pos + iZzfe2;
            while (this.pos < i6) {
                list.add(Integer.valueOf(zzfj()));
            }
            return;
        }
        if (i5 == 5) {
            do {
                list.add(Integer.valueOf(zzeu()));
                if (zzen()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i;
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzh(List<Boolean> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgq) {
            zzgq zzgqVar = (zzgq) list;
            int i3 = this.tag & 7;
            if (i3 != 0) {
                if (i3 == 2) {
                    int iZzfe = this.pos + zzfe();
                    while (this.pos < iZzfe) {
                        zzgqVar.addBoolean(zzfe() != 0);
                    }
                    zzat(iZzfe);
                    return;
                }
                throw zzin.zzhm();
            }
            do {
                zzgqVar.addBoolean(zzev());
                if (zzen()) {
                    return;
                } else {
                    i2 = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i2;
            return;
        }
        int i4 = this.tag & 7;
        if (i4 != 0) {
            if (i4 == 2) {
                int iZzfe2 = this.pos + zzfe();
                while (this.pos < iZzfe2) {
                    list.add(Boolean.valueOf(zzfe() != 0));
                }
                zzat(iZzfe2);
                return;
            }
            throw zzin.zzhm();
        }
        do {
            list.add(Boolean.valueOf(zzev()));
            if (zzen()) {
                return;
            } else {
                i = this.pos;
            }
        } while (zzfe() == this.tag);
        this.pos = i;
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
        int i;
        int i2;
        if ((this.tag & 7) != 2) {
            throw zzin.zzhm();
        }
        if ((list instanceof zziu) && !z) {
            zziu zziuVar = (zziu) list;
            do {
                zziuVar.zzc(zzex());
                if (zzen()) {
                    return;
                } else {
                    i2 = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i2;
            return;
        }
        do {
            list.add(zzj(z));
            if (zzen()) {
                return;
            } else {
                i = this.pos;
            }
        } while (zzfe() == this.tag);
        this.pos = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.vision.zzkd
    public final <T> void zza(List<T> list, zzkc<T> zzkcVar, zzho zzhoVar) throws IOException {
        int i;
        int i2 = this.tag;
        if ((i2 & 7) != 2) {
            throw zzin.zzhm();
        }
        do {
            list.add(zzb(zzkcVar, zzhoVar));
            if (zzen()) {
                return;
            } else {
                i = this.pos;
            }
        } while (zzfe() == i2);
        this.pos = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.vision.zzkd
    public final <T> void zzb(List<T> list, zzkc<T> zzkcVar, zzho zzhoVar) throws IOException {
        int i;
        int i2 = this.tag;
        if ((i2 & 7) != 3) {
            throw zzin.zzhm();
        }
        do {
            list.add(zzd(zzkcVar, zzhoVar));
            if (zzen()) {
                return;
            } else {
                i = this.pos;
            }
        } while (zzfe() == i2);
        this.pos = i;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzj(List<zzgs> list) throws IOException {
        int i;
        if ((this.tag & 7) != 2) {
            throw zzin.zzhm();
        }
        do {
            list.add(zzex());
            if (zzen()) {
                return;
            } else {
                i = this.pos;
            }
        } while (zzfe() == this.tag);
        this.pos = i;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzk(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i3 = this.tag & 7;
            if (i3 != 0) {
                if (i3 == 2) {
                    int iZzfe = this.pos + zzfe();
                    while (this.pos < iZzfe) {
                        zzifVar.zzbs(zzfe());
                    }
                    return;
                }
                throw zzin.zzhm();
            }
            do {
                zzifVar.zzbs(zzey());
                if (zzen()) {
                    return;
                } else {
                    i2 = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i2;
            return;
        }
        int i4 = this.tag & 7;
        if (i4 != 0) {
            if (i4 == 2) {
                int iZzfe2 = this.pos + zzfe();
                while (this.pos < iZzfe2) {
                    list.add(Integer.valueOf(zzfe()));
                }
                return;
            }
            throw zzin.zzhm();
        }
        do {
            list.add(Integer.valueOf(zzey()));
            if (zzen()) {
                return;
            } else {
                i = this.pos;
            }
        } while (zzfe() == this.tag);
        this.pos = i;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzl(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i3 = this.tag & 7;
            if (i3 != 0) {
                if (i3 == 2) {
                    int iZzfe = this.pos + zzfe();
                    while (this.pos < iZzfe) {
                        zzifVar.zzbs(zzfe());
                    }
                    return;
                }
                throw zzin.zzhm();
            }
            do {
                zzifVar.zzbs(zzez());
                if (zzen()) {
                    return;
                } else {
                    i2 = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i2;
            return;
        }
        int i4 = this.tag & 7;
        if (i4 != 0) {
            if (i4 == 2) {
                int iZzfe2 = this.pos + zzfe();
                while (this.pos < iZzfe2) {
                    list.add(Integer.valueOf(zzfe()));
                }
                return;
            }
            throw zzin.zzhm();
        }
        do {
            list.add(Integer.valueOf(zzez()));
            if (zzen()) {
                return;
            } else {
                i = this.pos;
            }
        } while (zzfe() == this.tag);
        this.pos = i;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzm(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i3 = this.tag & 7;
            if (i3 == 2) {
                int iZzfe = zzfe();
                zzas(iZzfe);
                int i4 = this.pos + iZzfe;
                while (this.pos < i4) {
                    zzifVar.zzbs(zzfj());
                }
                return;
            }
            if (i3 == 5) {
                do {
                    zzifVar.zzbs(zzfa());
                    if (zzen()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (zzfe() == this.tag);
                this.pos = i2;
                return;
            }
            throw zzin.zzhm();
        }
        int i5 = this.tag & 7;
        if (i5 == 2) {
            int iZzfe2 = zzfe();
            zzas(iZzfe2);
            int i6 = this.pos + iZzfe2;
            while (this.pos < i6) {
                list.add(Integer.valueOf(zzfj()));
            }
            return;
        }
        if (i5 == 5) {
            do {
                list.add(Integer.valueOf(zzfa()));
                if (zzen()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i;
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzn(List<Long> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            int i3 = this.tag & 7;
            if (i3 == 1) {
                do {
                    zzjbVar.zzac(zzfb());
                    if (zzen()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (zzfe() == this.tag);
                this.pos = i2;
                return;
            }
            if (i3 == 2) {
                int iZzfe = zzfe();
                zzar(iZzfe);
                int i4 = this.pos + iZzfe;
                while (this.pos < i4) {
                    zzjbVar.zzac(zzfk());
                }
                return;
            }
            throw zzin.zzhm();
        }
        int i5 = this.tag & 7;
        if (i5 == 1) {
            do {
                list.add(Long.valueOf(zzfb()));
                if (zzen()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i;
            return;
        }
        if (i5 == 2) {
            int iZzfe2 = zzfe();
            zzar(iZzfe2);
            int i6 = this.pos + iZzfe2;
            while (this.pos < i6) {
                list.add(Long.valueOf(zzfk()));
            }
            return;
        }
        throw zzin.zzhm();
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzo(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzif) {
            zzif zzifVar = (zzif) list;
            int i3 = this.tag & 7;
            if (i3 != 0) {
                if (i3 == 2) {
                    int iZzfe = this.pos + zzfe();
                    while (this.pos < iZzfe) {
                        zzifVar.zzbs(zzhe.zzbb(zzfe()));
                    }
                    return;
                }
                throw zzin.zzhm();
            }
            do {
                zzifVar.zzbs(zzfc());
                if (zzen()) {
                    return;
                } else {
                    i2 = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i2;
            return;
        }
        int i4 = this.tag & 7;
        if (i4 != 0) {
            if (i4 == 2) {
                int iZzfe2 = this.pos + zzfe();
                while (this.pos < iZzfe2) {
                    list.add(Integer.valueOf(zzhe.zzbb(zzfe())));
                }
                return;
            }
            throw zzin.zzhm();
        }
        do {
            list.add(Integer.valueOf(zzfc()));
            if (zzen()) {
                return;
            } else {
                i = this.pos;
            }
        } while (zzfe() == this.tag);
        this.pos = i;
    }

    @Override // com.google.android.gms.internal.vision.zzkd
    public final void zzp(List<Long> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzjb) {
            zzjb zzjbVar = (zzjb) list;
            int i3 = this.tag & 7;
            if (i3 != 0) {
                if (i3 == 2) {
                    int iZzfe = this.pos + zzfe();
                    while (this.pos < iZzfe) {
                        zzjbVar.zzac(zzhe.zzr(zzff()));
                    }
                    return;
                }
                throw zzin.zzhm();
            }
            do {
                zzjbVar.zzac(zzfd());
                if (zzen()) {
                    return;
                } else {
                    i2 = this.pos;
                }
            } while (zzfe() == this.tag);
            this.pos = i2;
            return;
        }
        int i4 = this.tag & 7;
        if (i4 != 0) {
            if (i4 == 2) {
                int iZzfe2 = this.pos + zzfe();
                while (this.pos < iZzfe2) {
                    list.add(Long.valueOf(zzhe.zzr(zzff())));
                }
                return;
            }
            throw zzin.zzhm();
        }
        do {
            list.add(Long.valueOf(zzfd()));
            if (zzen()) {
                return;
            } else {
                i = this.pos;
            }
        } while (zzfe() == this.tag);
        this.pos = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.vision.zzkd
    public final <K, V> void zza(Map<K, V> map, zzje<K, V> zzjeVar, zzho zzhoVar) throws IOException {
        zzaq(2);
        int iZzfe = zzfe();
        zzap(iZzfe);
        int i = this.limit;
        this.limit = this.pos + iZzfe;
        try {
            Object objZza = zzjeVar.zzaaj;
            Object objZza2 = zzjeVar.zzgk;
            while (true) {
                int iZzeo = zzeo();
                if (iZzeo == Integer.MAX_VALUE) {
                    map.put(objZza, objZza2);
                    return;
                }
                if (iZzeo == 1) {
                    objZza = zza(zzjeVar.zzaai, (Class<?>) null, (zzho) null);
                } else if (iZzeo == 2) {
                    objZza2 = zza(zzjeVar.zzaak, zzjeVar.zzgk.getClass(), zzhoVar);
                } else {
                    try {
                        if (!zzep()) {
                            throw new zzin("Unable to parse map entry.");
                        }
                    } catch (zzim unused) {
                        if (!zzep()) {
                            throw new zzin("Unable to parse map entry.");
                        }
                    }
                }
            }
        } finally {
            this.limit = i;
        }
    }

    private final Object zza(zzll zzllVar, Class<?> cls, zzho zzhoVar) throws IOException {
        switch (zzgo.zztn[zzllVar.ordinal()]) {
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
                return zzj(true);
            case 16:
                return Integer.valueOf(zzey());
            case 17:
                return Long.valueOf(zzeq());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private final int zzfe() throws IOException {
        int i;
        int i2 = this.pos;
        int i3 = this.limit;
        if (i3 == i2) {
            throw zzin.zzhh();
        }
        byte[] bArr = this.buffer;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            this.pos = i4;
            return b;
        }
        if (i3 - i4 < 9) {
            return (int) zzfg();
        }
        int i5 = i4 + 1;
        int i6 = b ^ (bArr[i4] << 7);
        if (i6 < 0) {
            i = i6 ^ (-128);
        } else {
            int i7 = i5 + 1;
            int i8 = i6 ^ (bArr[i5] << Ascii.SO);
            if (i8 >= 0) {
                i = i8 ^ 16256;
            } else {
                i5 = i7 + 1;
                int i9 = i8 ^ (bArr[i7] << Ascii.NAK);
                if (i9 < 0) {
                    i = i9 ^ (-2080896);
                } else {
                    i7 = i5 + 1;
                    byte b2 = bArr[i5];
                    i = (i9 ^ (b2 << Ascii.FS)) ^ 266354560;
                    if (b2 < 0) {
                        i5 = i7 + 1;
                        if (bArr[i7] < 0) {
                            i7 = i5 + 1;
                            if (bArr[i5] < 0) {
                                i5 = i7 + 1;
                                if (bArr[i7] < 0) {
                                    i7 = i5 + 1;
                                    if (bArr[i5] < 0) {
                                        i5 = i7 + 1;
                                        if (bArr[i7] < 0) {
                                            throw zzin.zzhj();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            i5 = i7;
        }
        this.pos = i5;
        return i;
    }

    private final long zzff() throws IOException {
        long j;
        long j2;
        long j3;
        int i;
        int i2 = this.pos;
        int i3 = this.limit;
        if (i3 == i2) {
            throw zzin.zzhh();
        }
        byte[] bArr = this.buffer;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            this.pos = i4;
            return b;
        }
        if (i3 - i4 < 9) {
            return zzfg();
        }
        int i5 = i4 + 1;
        int i6 = b ^ (bArr[i4] << 7);
        if (i6 >= 0) {
            int i7 = i5 + 1;
            int i8 = i6 ^ (bArr[i5] << Ascii.SO);
            if (i8 >= 0) {
                i5 = i7;
                j = i8 ^ 16256;
            } else {
                i5 = i7 + 1;
                int i9 = i8 ^ (bArr[i7] << Ascii.NAK);
                if (i9 < 0) {
                    i = i9 ^ (-2080896);
                } else {
                    long j4 = i9;
                    int i10 = i5 + 1;
                    long j5 = j4 ^ (bArr[i5] << 28);
                    if (j5 >= 0) {
                        j3 = 266354560;
                    } else {
                        i5 = i10 + 1;
                        long j6 = j5 ^ (bArr[i10] << 35);
                        if (j6 < 0) {
                            j2 = -34093383808L;
                        } else {
                            i10 = i5 + 1;
                            j5 = j6 ^ (bArr[i5] << 42);
                            if (j5 >= 0) {
                                j3 = 4363953127296L;
                            } else {
                                i5 = i10 + 1;
                                j6 = j5 ^ (bArr[i10] << 49);
                                if (j6 < 0) {
                                    j2 = -558586000294016L;
                                } else {
                                    int i11 = i5 + 1;
                                    long j7 = (j6 ^ (bArr[i5] << 56)) ^ 71499008037633920L;
                                    if (j7 < 0) {
                                        i5 = i11 + 1;
                                        if (bArr[i11] < 0) {
                                            throw zzin.zzhj();
                                        }
                                    } else {
                                        i5 = i11;
                                    }
                                    j = j7;
                                }
                            }
                        }
                        j = j6 ^ j2;
                    }
                    j = j5 ^ j3;
                    i5 = i10;
                }
            }
            this.pos = i5;
            return j;
        }
        i = i6 ^ (-128);
        j = i;
        this.pos = i5;
        return j;
    }

    private final long zzfg() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            j |= (r3 & Byte.MAX_VALUE) << i;
            if ((readByte() & 128) == 0) {
                return j;
            }
        }
        throw zzin.zzhj();
    }

    private final byte readByte() throws IOException {
        int i = this.pos;
        if (i == this.limit) {
            throw zzin.zzhh();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 1;
        return bArr[i];
    }

    private final int zzfh() throws IOException {
        zzap(4);
        return zzfj();
    }

    private final long zzfi() throws IOException {
        zzap(8);
        return zzfk();
    }

    private final int zzfj() {
        int i = this.pos;
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private final long zzfk() {
        int i = this.pos;
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    private final void zzao(int i) throws IOException {
        zzap(i);
        this.pos += i;
    }

    private final void zzap(int i) throws IOException {
        if (i < 0 || i > this.limit - this.pos) {
            throw zzin.zzhh();
        }
    }

    private final void zzaq(int i) throws IOException {
        if ((this.tag & 7) != i) {
            throw zzin.zzhm();
        }
    }

    private final void zzar(int i) throws IOException {
        zzap(i);
        if ((i & 7) != 0) {
            throw zzin.zzhn();
        }
    }

    private final void zzas(int i) throws IOException {
        zzap(i);
        if ((i & 3) != 0) {
            throw zzin.zzhn();
        }
    }

    private final void zzat(int i) throws IOException {
        if (this.pos != i) {
            throw zzin.zzhh();
        }
    }
}
