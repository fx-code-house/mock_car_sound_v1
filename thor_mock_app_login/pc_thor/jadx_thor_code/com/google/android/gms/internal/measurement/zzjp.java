package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjp<T> implements zzkb<T> {
    private final zzjj zza;
    private final zzkt<?, ?> zzb;
    private final boolean zzc;
    private final zzhn<?> zzd;

    private zzjp(zzkt<?, ?> zzktVar, zzhn<?> zzhnVar, zzjj zzjjVar) {
        this.zzb = zzktVar;
        this.zzc = zzhnVar.zza(zzjjVar);
        this.zzd = zzhnVar;
        this.zza = zzjjVar;
    }

    static <T> zzjp<T> zza(zzkt<?, ?> zzktVar, zzhn<?> zzhnVar, zzjj zzjjVar) {
        return new zzjp<>(zzktVar, zzhnVar, zzjjVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final T zza() {
        return (T) this.zza.zzbu().zzx();
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final boolean zza(T t, T t2) {
        if (!this.zzb.zzb(t).equals(this.zzb.zzb(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zza(t).equals(this.zzd.zza(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final int zza(T t) {
        int iHashCode = this.zzb.zzb(t).hashCode();
        return this.zzc ? (iHashCode * 53) + this.zzd.zza(t).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final void zzb(T t, T t2) {
        zzkd.zza(this.zzb, t, t2);
        if (this.zzc) {
            zzkd.zza(this.zzd, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final void zza(T t, zzlm zzlmVar) throws IOException {
        Iterator itZzd = this.zzd.zza(t).zzd();
        while (itZzd.hasNext()) {
            Map.Entry entry = (Map.Entry) itZzd.next();
            zzht zzhtVar = (zzht) entry.getKey();
            if (zzhtVar.zzc() != zzln.MESSAGE || zzhtVar.zzd() || zzhtVar.zze()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zzim) {
                zzlmVar.zza(zzhtVar.zza(), (Object) ((zzim) entry).zza().zzc());
            } else {
                zzlmVar.zza(zzhtVar.zza(), entry.getValue());
            }
        }
        zzkt<?, ?> zzktVar = this.zzb;
        zzktVar.zzb((zzkt<?, ?>) zzktVar.zzb(t), zzlmVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0099 A[EDGE_INSN: B:56:0x0099->B:34:0x0099 BREAK  A[LOOP:1: B:18:0x0053->B:61:0x0053], SYNTHETIC] */
    @Override // com.google.android.gms.internal.measurement.zzkb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r10, byte[] r11, int r12, int r13, com.google.android.gms.internal.measurement.zzgo r14) throws java.io.IOException {
        /*
            r9 = this;
            r0 = r10
            com.google.android.gms.internal.measurement.zzhy r0 = (com.google.android.gms.internal.measurement.zzhy) r0
            com.google.android.gms.internal.measurement.zzks r1 = r0.zzb
            com.google.android.gms.internal.measurement.zzks r2 = com.google.android.gms.internal.measurement.zzks.zza()
            if (r1 != r2) goto L11
            com.google.android.gms.internal.measurement.zzks r1 = com.google.android.gms.internal.measurement.zzks.zzb()
            r0.zzb = r1
        L11:
            com.google.android.gms.internal.measurement.zzhy$zzd r10 = (com.google.android.gms.internal.measurement.zzhy.zzd) r10
            r10.zza()
            r10 = 0
            r0 = r10
        L18:
            if (r12 >= r13) goto La4
            int r4 = com.google.android.gms.internal.measurement.zzgl.zza(r11, r12, r14)
            int r2 = r14.zza
            r12 = 11
            r3 = 2
            if (r2 == r12) goto L51
            r12 = r2 & 7
            if (r12 != r3) goto L4c
            com.google.android.gms.internal.measurement.zzhn<?> r12 = r9.zzd
            com.google.android.gms.internal.measurement.zzhl r0 = r14.zzd
            com.google.android.gms.internal.measurement.zzjj r3 = r9.zza
            int r5 = r2 >>> 3
            java.lang.Object r12 = r12.zza(r0, r3, r5)
            r0 = r12
            com.google.android.gms.internal.measurement.zzhy$zzf r0 = (com.google.android.gms.internal.measurement.zzhy.zzf) r0
            if (r0 != 0) goto L43
            r3 = r11
            r5 = r13
            r6 = r1
            r7 = r14
            int r12 = com.google.android.gms.internal.measurement.zzgl.zza(r2, r3, r4, r5, r6, r7)
            goto L18
        L43:
            com.google.android.gms.internal.measurement.zzjx.zza()
            java.lang.NoSuchMethodError r10 = new java.lang.NoSuchMethodError
            r10.<init>()
            throw r10
        L4c:
            int r12 = com.google.android.gms.internal.measurement.zzgl.zza(r2, r11, r4, r13, r14)
            goto L18
        L51:
            r12 = 0
            r2 = r10
        L53:
            if (r4 >= r13) goto L99
            int r4 = com.google.android.gms.internal.measurement.zzgl.zza(r11, r4, r14)
            int r5 = r14.zza
            int r6 = r5 >>> 3
            r7 = r5 & 7
            if (r6 == r3) goto L7b
            r8 = 3
            if (r6 == r8) goto L65
            goto L90
        L65:
            if (r0 != 0) goto L72
            if (r7 != r3) goto L90
            int r4 = com.google.android.gms.internal.measurement.zzgl.zze(r11, r4, r14)
            java.lang.Object r2 = r14.zzc
            com.google.android.gms.internal.measurement.zzgp r2 = (com.google.android.gms.internal.measurement.zzgp) r2
            goto L53
        L72:
            com.google.android.gms.internal.measurement.zzjx.zza()
            java.lang.NoSuchMethodError r10 = new java.lang.NoSuchMethodError
            r10.<init>()
            throw r10
        L7b:
            if (r7 != 0) goto L90
            int r4 = com.google.android.gms.internal.measurement.zzgl.zza(r11, r4, r14)
            int r12 = r14.zza
            com.google.android.gms.internal.measurement.zzhn<?> r0 = r9.zzd
            com.google.android.gms.internal.measurement.zzhl r5 = r14.zzd
            com.google.android.gms.internal.measurement.zzjj r6 = r9.zza
            java.lang.Object r0 = r0.zza(r5, r6, r12)
            com.google.android.gms.internal.measurement.zzhy$zzf r0 = (com.google.android.gms.internal.measurement.zzhy.zzf) r0
            goto L53
        L90:
            r6 = 12
            if (r5 == r6) goto L99
            int r4 = com.google.android.gms.internal.measurement.zzgl.zza(r5, r11, r4, r13, r14)
            goto L53
        L99:
            if (r2 == 0) goto La1
            int r12 = r12 << 3
            r12 = r12 | r3
            r1.zza(r12, r2)
        La1:
            r12 = r4
            goto L18
        La4:
            if (r12 != r13) goto La7
            return
        La7:
            com.google.android.gms.internal.measurement.zzij r10 = com.google.android.gms.internal.measurement.zzij.zzg()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjp.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.measurement.zzgo):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0086 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[LOOP:0: B:45:0x000c->B:53:?, LOOP_END, SYNTHETIC] */
    @Override // com.google.android.gms.internal.measurement.zzkb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r11, com.google.android.gms.internal.measurement.zzjy r12, com.google.android.gms.internal.measurement.zzhl r13) throws java.io.IOException {
        /*
            r10 = this;
            com.google.android.gms.internal.measurement.zzkt<?, ?> r0 = r10.zzb
            com.google.android.gms.internal.measurement.zzhn<?> r1 = r10.zzd
            java.lang.Object r2 = r0.zzc(r11)
            com.google.android.gms.internal.measurement.zzhr r3 = r1.zzb(r11)
        Lc:
            int r4 = r12.zza()     // Catch: java.lang.Throwable -> L8f
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 != r5) goto L19
            r0.zzb(r11, r2)
            return
        L19:
            int r4 = r12.zzb()     // Catch: java.lang.Throwable -> L8f
            r6 = 11
            if (r4 == r6) goto L3e
            r5 = r4 & 7
            r6 = 2
            if (r5 != r6) goto L39
            com.google.android.gms.internal.measurement.zzjj r5 = r10.zza     // Catch: java.lang.Throwable -> L8f
            int r4 = r4 >>> 3
            java.lang.Object r4 = r1.zza(r13, r5, r4)     // Catch: java.lang.Throwable -> L8f
            if (r4 == 0) goto L34
            r1.zza(r12, r4, r13, r3)     // Catch: java.lang.Throwable -> L8f
            goto L83
        L34:
            boolean r4 = r0.zza(r2, r12)     // Catch: java.lang.Throwable -> L8f
            goto L84
        L39:
            boolean r4 = r12.zzc()     // Catch: java.lang.Throwable -> L8f
            goto L84
        L3e:
            r4 = 0
            r6 = 0
            r7 = r6
            r6 = r4
        L42:
            int r8 = r12.zza()     // Catch: java.lang.Throwable -> L8f
            if (r8 == r5) goto L70
            int r8 = r12.zzb()     // Catch: java.lang.Throwable -> L8f
            r9 = 16
            if (r8 != r9) goto L5b
            int r7 = r12.zzo()     // Catch: java.lang.Throwable -> L8f
            com.google.android.gms.internal.measurement.zzjj r4 = r10.zza     // Catch: java.lang.Throwable -> L8f
            java.lang.Object r4 = r1.zza(r13, r4, r7)     // Catch: java.lang.Throwable -> L8f
            goto L42
        L5b:
            r9 = 26
            if (r8 != r9) goto L6a
            if (r4 == 0) goto L65
            r1.zza(r12, r4, r13, r3)     // Catch: java.lang.Throwable -> L8f
            goto L42
        L65:
            com.google.android.gms.internal.measurement.zzgp r6 = r12.zzn()     // Catch: java.lang.Throwable -> L8f
            goto L42
        L6a:
            boolean r8 = r12.zzc()     // Catch: java.lang.Throwable -> L8f
            if (r8 != 0) goto L42
        L70:
            int r5 = r12.zzb()     // Catch: java.lang.Throwable -> L8f
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
            r0.zzb(r11, r2)
            return
        L8a:
            com.google.android.gms.internal.measurement.zzij r12 = com.google.android.gms.internal.measurement.zzij.zze()     // Catch: java.lang.Throwable -> L8f
            throw r12     // Catch: java.lang.Throwable -> L8f
        L8f:
            r12 = move-exception
            r0.zzb(r11, r2)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjp.zza(java.lang.Object, com.google.android.gms.internal.measurement.zzjy, com.google.android.gms.internal.measurement.zzhl):void");
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final void zzc(T t) {
        this.zzb.zzd(t);
        this.zzd.zzc(t);
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final boolean zzd(T t) {
        return this.zzd.zza(t).zzf();
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final int zzb(T t) {
        zzkt<?, ?> zzktVar = this.zzb;
        int iZze = zzktVar.zze(zzktVar.zzb(t)) + 0;
        return this.zzc ? iZze + this.zzd.zza(t).zzg() : iZze;
    }
}
