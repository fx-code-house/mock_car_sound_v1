package com.google.android.gms.internal.vision;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzjt<T> implements zzkc<T> {
    private final zzjn zzaau;
    private final boolean zzaav;
    private final zzku<?, ?> zzabe;
    private final zzhq<?> zzabf;

    private zzjt(zzku<?, ?> zzkuVar, zzhq<?> zzhqVar, zzjn zzjnVar) {
        this.zzabe = zzkuVar;
        this.zzaav = zzhqVar.zze(zzjnVar);
        this.zzabf = zzhqVar;
        this.zzaau = zzjnVar;
    }

    static <T> zzjt<T> zza(zzku<?, ?> zzkuVar, zzhq<?> zzhqVar, zzjn zzjnVar) {
        return new zzjt<>(zzkuVar, zzhqVar, zzjnVar);
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final T newInstance() {
        return (T) this.zzaau.zzhd().zzgv();
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final boolean equals(T t, T t2) {
        if (!this.zzabe.zzy(t).equals(this.zzabe.zzy(t2))) {
            return false;
        }
        if (this.zzaav) {
            return this.zzabf.zzh(t).equals(this.zzabf.zzh(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final int hashCode(T t) {
        int iHashCode = this.zzabe.zzy(t).hashCode();
        return this.zzaav ? (iHashCode * 53) + this.zzabf.zzh(t).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final void zzd(T t, T t2) {
        zzke.zza(this.zzabe, t, t2);
        if (this.zzaav) {
            zzke.zza(this.zzabf, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final void zza(T t, zzlr zzlrVar) throws IOException {
        Iterator it = this.zzabf.zzh(t).iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzhv zzhvVar = (zzhv) entry.getKey();
            if (zzhvVar.zzgn() != zzlo.MESSAGE || zzhvVar.zzgo() || zzhvVar.zzgp()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zziq) {
                zzlrVar.zza(zzhvVar.zzak(), (Object) ((zziq) entry).zzhr().zzee());
            } else {
                zzlrVar.zza(zzhvVar.zzak(), entry.getValue());
            }
        }
        zzku<?, ?> zzkuVar = this.zzabe;
        zzkuVar.zzc(zzkuVar.zzy(t), zzlrVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00be A[EDGE_INSN: B:57:0x00be->B:33:0x00be BREAK  A[LOOP:1: B:18:0x0067->B:60:0x0067], SYNTHETIC] */
    @Override // com.google.android.gms.internal.vision.zzkc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r11, byte[] r12, int r13, int r14, com.google.android.gms.internal.vision.zzgm r15) throws java.io.IOException {
        /*
            r10 = this;
            r0 = r11
            com.google.android.gms.internal.vision.zzid r0 = (com.google.android.gms.internal.vision.zzid) r0
            com.google.android.gms.internal.vision.zzkx r1 = r0.zzxz
            com.google.android.gms.internal.vision.zzkx r2 = com.google.android.gms.internal.vision.zzkx.zzjb()
            if (r1 != r2) goto L11
            com.google.android.gms.internal.vision.zzkx r1 = com.google.android.gms.internal.vision.zzkx.zzjc()
            r0.zzxz = r1
        L11:
            com.google.android.gms.internal.vision.zzid$zze r11 = (com.google.android.gms.internal.vision.zzid.zze) r11
            com.google.android.gms.internal.vision.zzht r11 = r11.zzhe()
            r0 = 0
            r2 = r0
        L19:
            if (r13 >= r14) goto Lc9
            int r4 = com.google.android.gms.internal.vision.zzgk.zza(r12, r13, r15)
            int r13 = r15.zztk
            r3 = 11
            r5 = 2
            if (r13 == r3) goto L65
            r3 = r13 & 7
            if (r3 != r5) goto L60
            com.google.android.gms.internal.vision.zzhq<?> r2 = r10.zzabf
            com.google.android.gms.internal.vision.zzho r3 = r15.zzcu
            com.google.android.gms.internal.vision.zzjn r5 = r10.zzaau
            int r6 = r13 >>> 3
            java.lang.Object r2 = r2.zza(r3, r5, r6)
            r8 = r2
            com.google.android.gms.internal.vision.zzid$zzg r8 = (com.google.android.gms.internal.vision.zzid.zzg) r8
            if (r8 == 0) goto L55
            com.google.android.gms.internal.vision.zzjy r13 = com.google.android.gms.internal.vision.zzjy.zzij()
            com.google.android.gms.internal.vision.zzjn r2 = r8.zzyw
            java.lang.Class r2 = r2.getClass()
            com.google.android.gms.internal.vision.zzkc r13 = r13.zzf(r2)
            int r13 = com.google.android.gms.internal.vision.zzgk.zza(r13, r12, r4, r14, r15)
            com.google.android.gms.internal.vision.zzid$zzd r2 = r8.zzyx
            java.lang.Object r3 = r15.zztm
            r11.zza(r2, r3)
            goto L5e
        L55:
            r2 = r13
            r3 = r12
            r5 = r14
            r6 = r1
            r7 = r15
            int r13 = com.google.android.gms.internal.vision.zzgk.zza(r2, r3, r4, r5, r6, r7)
        L5e:
            r2 = r8
            goto L19
        L60:
            int r13 = com.google.android.gms.internal.vision.zzgk.zza(r13, r12, r4, r14, r15)
            goto L19
        L65:
            r13 = 0
            r3 = r0
        L67:
            if (r4 >= r14) goto Lbe
            int r4 = com.google.android.gms.internal.vision.zzgk.zza(r12, r4, r15)
            int r6 = r15.zztk
            int r7 = r6 >>> 3
            r8 = r6 & 7
            if (r7 == r5) goto La0
            r9 = 3
            if (r7 == r9) goto L79
            goto Lb5
        L79:
            if (r2 == 0) goto L95
            com.google.android.gms.internal.vision.zzjy r6 = com.google.android.gms.internal.vision.zzjy.zzij()
            com.google.android.gms.internal.vision.zzjn r7 = r2.zzyw
            java.lang.Class r7 = r7.getClass()
            com.google.android.gms.internal.vision.zzkc r6 = r6.zzf(r7)
            int r4 = com.google.android.gms.internal.vision.zzgk.zza(r6, r12, r4, r14, r15)
            com.google.android.gms.internal.vision.zzid$zzd r6 = r2.zzyx
            java.lang.Object r7 = r15.zztm
            r11.zza(r6, r7)
            goto L67
        L95:
            if (r8 != r5) goto Lb5
            int r4 = com.google.android.gms.internal.vision.zzgk.zze(r12, r4, r15)
            java.lang.Object r3 = r15.zztm
            com.google.android.gms.internal.vision.zzgs r3 = (com.google.android.gms.internal.vision.zzgs) r3
            goto L67
        La0:
            if (r8 != 0) goto Lb5
            int r4 = com.google.android.gms.internal.vision.zzgk.zza(r12, r4, r15)
            int r13 = r15.zztk
            com.google.android.gms.internal.vision.zzhq<?> r2 = r10.zzabf
            com.google.android.gms.internal.vision.zzho r6 = r15.zzcu
            com.google.android.gms.internal.vision.zzjn r7 = r10.zzaau
            java.lang.Object r2 = r2.zza(r6, r7, r13)
            com.google.android.gms.internal.vision.zzid$zzg r2 = (com.google.android.gms.internal.vision.zzid.zzg) r2
            goto L67
        Lb5:
            r7 = 12
            if (r6 == r7) goto Lbe
            int r4 = com.google.android.gms.internal.vision.zzgk.zza(r6, r12, r4, r14, r15)
            goto L67
        Lbe:
            if (r3 == 0) goto Lc6
            int r13 = r13 << 3
            r13 = r13 | r5
            r1.zzb(r13, r3)
        Lc6:
            r13 = r4
            goto L19
        Lc9:
            if (r13 != r14) goto Lcc
            return
        Lcc:
            com.google.android.gms.internal.vision.zzin r11 = com.google.android.gms.internal.vision.zzin.zzhn()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjt.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.vision.zzgm):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0086 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[LOOP:0: B:45:0x000c->B:53:?, LOOP_END, SYNTHETIC] */
    @Override // com.google.android.gms.internal.vision.zzkc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r11, com.google.android.gms.internal.vision.zzkd r12, com.google.android.gms.internal.vision.zzho r13) throws java.io.IOException {
        /*
            r10 = this;
            com.google.android.gms.internal.vision.zzku<?, ?> r0 = r10.zzabe
            com.google.android.gms.internal.vision.zzhq<?> r1 = r10.zzabf
            java.lang.Object r2 = r0.zzz(r11)
            com.google.android.gms.internal.vision.zzht r3 = r1.zzi(r11)
        Lc:
            int r4 = r12.zzeo()     // Catch: java.lang.Throwable -> L8f
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 != r5) goto L19
            r0.zzg(r11, r2)
            return
        L19:
            int r4 = r12.getTag()     // Catch: java.lang.Throwable -> L8f
            r6 = 11
            if (r4 == r6) goto L3e
            r5 = r4 & 7
            r6 = 2
            if (r5 != r6) goto L39
            com.google.android.gms.internal.vision.zzjn r5 = r10.zzaau     // Catch: java.lang.Throwable -> L8f
            int r4 = r4 >>> 3
            java.lang.Object r4 = r1.zza(r13, r5, r4)     // Catch: java.lang.Throwable -> L8f
            if (r4 == 0) goto L34
            r1.zza(r12, r4, r13, r3)     // Catch: java.lang.Throwable -> L8f
            goto L83
        L34:
            boolean r4 = r0.zza(r2, r12)     // Catch: java.lang.Throwable -> L8f
            goto L84
        L39:
            boolean r4 = r12.zzep()     // Catch: java.lang.Throwable -> L8f
            goto L84
        L3e:
            r4 = 0
            r6 = 0
            r7 = r6
            r6 = r4
        L42:
            int r8 = r12.zzeo()     // Catch: java.lang.Throwable -> L8f
            if (r8 == r5) goto L70
            int r8 = r12.getTag()     // Catch: java.lang.Throwable -> L8f
            r9 = 16
            if (r8 != r9) goto L5b
            int r7 = r12.zzey()     // Catch: java.lang.Throwable -> L8f
            com.google.android.gms.internal.vision.zzjn r4 = r10.zzaau     // Catch: java.lang.Throwable -> L8f
            java.lang.Object r4 = r1.zza(r13, r4, r7)     // Catch: java.lang.Throwable -> L8f
            goto L42
        L5b:
            r9 = 26
            if (r8 != r9) goto L6a
            if (r4 == 0) goto L65
            r1.zza(r12, r4, r13, r3)     // Catch: java.lang.Throwable -> L8f
            goto L42
        L65:
            com.google.android.gms.internal.vision.zzgs r6 = r12.zzex()     // Catch: java.lang.Throwable -> L8f
            goto L42
        L6a:
            boolean r8 = r12.zzep()     // Catch: java.lang.Throwable -> L8f
            if (r8 != 0) goto L42
        L70:
            int r5 = r12.getTag()     // Catch: java.lang.Throwable -> L8f
            r8 = 12
            if (r5 != r8) goto L8a
            if (r6 == 0) goto L83
            if (r4 == 0) goto L80
            r1.zza(r6, r4, r13, r3)     // Catch: java.lang.Throwable -> L8f
            goto L83
        L80:
            r0.zza(r2, r7, r6)     // Catch: java.lang.Throwable -> L8f
        L83:
            r4 = 1
        L84:
            if (r4 != 0) goto Lc
            r0.zzg(r11, r2)
            return
        L8a:
            com.google.android.gms.internal.vision.zzin r12 = com.google.android.gms.internal.vision.zzin.zzhl()     // Catch: java.lang.Throwable -> L8f
            throw r12     // Catch: java.lang.Throwable -> L8f
        L8f:
            r12 = move-exception
            r0.zzg(r11, r2)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjt.zza(java.lang.Object, com.google.android.gms.internal.vision.zzkd, com.google.android.gms.internal.vision.zzho):void");
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final void zzj(T t) {
        this.zzabe.zzj(t);
        this.zzabf.zzj(t);
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final boolean zzw(T t) {
        return this.zzabf.zzh(t).isInitialized();
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final int zzu(T t) {
        zzku<?, ?> zzkuVar = this.zzabe;
        int iZzaa = zzkuVar.zzaa(zzkuVar.zzy(t)) + 0;
        return this.zzaav ? iZzaa + this.zzabf.zzh(t).zzgi() : iZzaa;
    }
}
