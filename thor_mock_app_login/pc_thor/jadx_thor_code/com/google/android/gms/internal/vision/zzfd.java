package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzfd {
    private static final zzfg zznw;
    private static final int zznx;

    public static void zza(Throwable th, Throwable th2) {
        zznw.zza(th, th2);
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static final class zza extends zzfg {
        zza() {
        }

        @Override // com.google.android.gms.internal.vision.zzfg
        public final void zza(Throwable th, Throwable th2) {
        }

        @Override // com.google.android.gms.internal.vision.zzfg
        public final void zza(Throwable th) {
            th.printStackTrace();
        }
    }

    public static void zza(Throwable th) {
        zznw.zza(th);
    }

    private static Integer zzdc() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0015 A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:5:0x0007, B:7:0x000f, B:8:0x0015, B:10:0x001e, B:11:0x0024), top: B:25:0x0007 }] */
    static {
        /*
            r0 = 1
            java.lang.Integer r1 = zzdc()     // Catch: java.lang.Throwable -> L2c
            if (r1 == 0) goto L15
            int r2 = r1.intValue()     // Catch: java.lang.Throwable -> L2a
            r3 = 19
            if (r2 < r3) goto L15
            com.google.android.gms.internal.vision.zzfj r2 = new com.google.android.gms.internal.vision.zzfj     // Catch: java.lang.Throwable -> L2a
            r2.<init>()     // Catch: java.lang.Throwable -> L2a
            goto L66
        L15:
            java.lang.String r2 = "com.google.devtools.build.android.desugar.runtime.twr_disable_mimic"
            boolean r2 = java.lang.Boolean.getBoolean(r2)     // Catch: java.lang.Throwable -> L2a
            r2 = r2 ^ r0
            if (r2 == 0) goto L24
            com.google.android.gms.internal.vision.zzfh r2 = new com.google.android.gms.internal.vision.zzfh     // Catch: java.lang.Throwable -> L2a
            r2.<init>()     // Catch: java.lang.Throwable -> L2a
            goto L66
        L24:
            com.google.android.gms.internal.vision.zzfd$zza r2 = new com.google.android.gms.internal.vision.zzfd$zza     // Catch: java.lang.Throwable -> L2a
            r2.<init>()     // Catch: java.lang.Throwable -> L2a
            goto L66
        L2a:
            r2 = move-exception
            goto L2e
        L2c:
            r2 = move-exception
            r1 = 0
        L2e:
            java.io.PrintStream r3 = java.lang.System.err
            java.lang.Class<com.google.android.gms.internal.vision.zzfd$zza> r4 = com.google.android.gms.internal.vision.zzfd.zza.class
            java.lang.String r4 = r4.getName()
            java.lang.String r5 = java.lang.String.valueOf(r4)
            int r5 = r5.length()
            int r5 = r5 + 133
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r5 = "An error has occurred when initializing the try-with-resources desuguring strategy. The default strategy "
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.String r5 = "will be used. The error is: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.println(r4)
            java.io.PrintStream r3 = java.lang.System.err
            r2.printStackTrace(r3)
            com.google.android.gms.internal.vision.zzfd$zza r2 = new com.google.android.gms.internal.vision.zzfd$zza
            r2.<init>()
        L66:
            com.google.android.gms.internal.vision.zzfd.zznw = r2
            if (r1 != 0) goto L6b
            goto L6f
        L6b:
            int r0 = r1.intValue()
        L6f:
            com.google.android.gms.internal.vision.zzfd.zznx = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzfd.<clinit>():void");
    }
}
