package com.google.android.gms.internal.play_billing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzbs {
    private static final zzbs zzb = new zzbs(true);
    final zzec zza = new zzds(16);
    private boolean zzc;
    private boolean zzd;

    private zzbs() {
    }

    public static zzbs zza() {
        throw null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final void zzd(com.google.android.gms.internal.play_billing.zzbr r4, java.lang.Object r5) {
        /*
            com.google.android.gms.internal.play_billing.zzew r0 = r4.zzb()
            byte[] r1 = com.google.android.gms.internal.play_billing.zzcg.zzd
            r5.getClass()
            com.google.android.gms.internal.play_billing.zzew r1 = com.google.android.gms.internal.play_billing.zzew.DOUBLE
            com.google.android.gms.internal.play_billing.zzex r1 = com.google.android.gms.internal.play_billing.zzex.INT
            com.google.android.gms.internal.play_billing.zzex r0 = r0.zza()
            int r0 = r0.ordinal()
            switch(r0) {
                case 0: goto L43;
                case 1: goto L40;
                case 2: goto L3d;
                case 3: goto L3a;
                case 4: goto L37;
                case 5: goto L34;
                case 6: goto L2b;
                case 7: goto L22;
                case 8: goto L19;
                default: goto L18;
            }
        L18:
            goto L48
        L19:
            boolean r0 = r5 instanceof com.google.android.gms.internal.play_billing.zzdf
            if (r0 != 0) goto L47
            boolean r0 = r5 instanceof com.google.android.gms.internal.play_billing.zzck
            if (r0 == 0) goto L48
            goto L47
        L22:
            boolean r0 = r5 instanceof java.lang.Integer
            if (r0 != 0) goto L47
            boolean r0 = r5 instanceof com.google.android.gms.internal.play_billing.zzfp
            if (r0 == 0) goto L48
            goto L47
        L2b:
            boolean r0 = r5 instanceof com.google.android.gms.internal.play_billing.zzba
            if (r0 != 0) goto L47
            boolean r0 = r5 instanceof byte[]
            if (r0 == 0) goto L48
            goto L47
        L34:
            boolean r0 = r5 instanceof java.lang.String
            goto L45
        L37:
            boolean r0 = r5 instanceof java.lang.Boolean
            goto L45
        L3a:
            boolean r0 = r5 instanceof java.lang.Double
            goto L45
        L3d:
            boolean r0 = r5 instanceof java.lang.Float
            goto L45
        L40:
            boolean r0 = r5 instanceof java.lang.Long
            goto L45
        L43:
            boolean r0 = r5 instanceof java.lang.Integer
        L45:
            if (r0 == 0) goto L48
        L47:
            return
        L48:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r1 = 3
            java.lang.Object[] r1 = new java.lang.Object[r1]
            int r2 = r4.zza()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3 = 0
            r1[r3] = r2
            com.google.android.gms.internal.play_billing.zzew r4 = r4.zzb()
            com.google.android.gms.internal.play_billing.zzex r4 = r4.zza()
            r2 = 1
            r1[r2] = r4
            java.lang.Class r4 = r5.getClass()
            java.lang.String r4 = r4.getName()
            r5 = 2
            r1[r5] = r4
            java.lang.String r4 = "Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n"
            java.lang.String r4 = java.lang.String.format(r4, r1)
            r0.<init>(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzbs.zzd(com.google.android.gms.internal.play_billing.zzbr, java.lang.Object):void");
    }

    public final /* bridge */ /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzbs zzbsVar = new zzbs();
        for (int i = 0; i < this.zza.zzb(); i++) {
            Map.Entry entryZzg = this.zza.zzg(i);
            zzbsVar.zzc((zzbr) entryZzg.getKey(), entryZzg.getValue());
        }
        for (Map.Entry entry : this.zza.zzc()) {
            zzbsVar.zzc((zzbr) entry.getKey(), entry.getValue());
        }
        zzbsVar.zzd = this.zzd;
        return zzbsVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzbs) {
            return this.zza.equals(((zzbs) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final void zzb() {
        if (this.zzc) {
            return;
        }
        for (int i = 0; i < this.zza.zzb(); i++) {
            Map.Entry entryZzg = this.zza.zzg(i);
            if (entryZzg.getValue() instanceof zzcb) {
                ((zzcb) entryZzg.getValue()).zzn();
            }
        }
        this.zza.zza();
        this.zzc = true;
    }

    public final void zzc(zzbr zzbrVar, Object obj) {
        if (!zzbrVar.zzc()) {
            zzd(zzbrVar, obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                zzd(zzbrVar, arrayList.get(i));
            }
            obj = arrayList;
        }
        if (obj instanceof zzck) {
            this.zzd = true;
        }
        this.zza.put(zzbrVar, obj);
    }

    private zzbs(boolean z) {
        zzb();
        zzb();
    }
}
