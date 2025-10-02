package com.google.android.gms.internal.vision;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class zzbi<T> {

    @Nullable
    private static volatile zzbr zzgf = null;
    private static volatile boolean zzgg = false;
    private final String name;
    private final zzbo zzgj;
    private final T zzgk;
    private volatile int zzgm;
    private volatile T zzgn;
    private final boolean zzgo;
    private static final Object zzge = new Object();
    private static final AtomicReference<Collection<zzbi<?>>> zzgh = new AtomicReference<>();
    private static zzbs zzgi = new zzbs(zzbk.zzgq);
    private static final AtomicInteger zzgl = new AtomicInteger();

    static final /* synthetic */ boolean zzah() {
        return true;
    }

    @Deprecated
    public static void zzi(final Context context) {
        synchronized (zzge) {
            zzbr zzbrVar = zzgf;
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                context = applicationContext;
            }
            if (zzbrVar == null || zzbrVar.zzaa() != context) {
                zzau.zzy();
                zzbq.zzy();
                zzbd.zzae();
                zzgf = new zzav(context, zzdi.zza(new zzdf(context) { // from class: com.google.android.gms.internal.vision.zzbl
                    private final Context zzgr;

                    {
                        this.zzgr = context;
                    }

                    @Override // com.google.android.gms.internal.vision.zzdf
                    public final Object get() {
                        return zzbi.zzk(this.zzgr);
                    }
                }));
                zzgl.incrementAndGet();
            }
        }
    }

    abstract T zza(Object obj);

    public static void zzj(Context context) {
        if (zzgf != null) {
            return;
        }
        synchronized (zzge) {
            if (zzgf == null) {
                zzi(context);
            }
        }
    }

    static void zzaf() {
        zzgl.incrementAndGet();
    }

    private zzbi(zzbo zzboVar, String str, T t, boolean z) {
        this.zzgm = -1;
        if (zzboVar.zzgt == null && zzboVar.zzgu == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        if (zzboVar.zzgt != null && zzboVar.zzgu != null) {
            throw new IllegalArgumentException("Must pass one of SharedPreferences file name or ContentProvider URI");
        }
        this.zzgj = zzboVar;
        this.name = str;
        this.zzgk = t;
        this.zzgo = z;
    }

    private final String zze(String str) {
        if (str != null && str.isEmpty()) {
            return this.name;
        }
        String strValueOf = String.valueOf(str);
        String strValueOf2 = String.valueOf(this.name);
        return strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
    }

    public final String zzag() {
        return zze(this.zzgj.zzgw);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x004e A[Catch: all -> 0x0087, TryCatch #0 {, blocks: (B:8:0x001c, B:10:0x0020, B:14:0x0027, B:16:0x0032, B:29:0x0050, B:31:0x0060, B:33:0x007a, B:34:0x007d, B:35:0x0081, B:19:0x0039, B:28:0x004e, B:22:0x0040, B:25:0x0047, B:36:0x0085), top: B:43:0x001c }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0060 A[Catch: all -> 0x0087, TryCatch #0 {, blocks: (B:8:0x001c, B:10:0x0020, B:14:0x0027, B:16:0x0032, B:29:0x0050, B:31:0x0060, B:33:0x007a, B:34:0x007d, B:35:0x0081, B:19:0x0039, B:28:0x004e, B:22:0x0040, B:25:0x0047, B:36:0x0085), top: B:43:0x001c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final T get() {
        /*
            r6 = this;
            boolean r0 = r6.zzgo
            if (r0 != 0) goto L11
            com.google.android.gms.internal.vision.zzbs r0 = com.google.android.gms.internal.vision.zzbi.zzgi
            java.lang.String r1 = r6.name
            boolean r0 = r0.zzg(r1)
            java.lang.String r1 = "Attempt to access PhenotypeFlag not via codegen. All new PhenotypeFlags must be accessed through codegen APIs. If you believe you are seeing this error by mistake, you can add your flag to the exemption list located at //java/com/google/android/libraries/phenotype/client/lockdown/flags.textproto. Send the addition CL to ph-reviews@. See go/phenotype-android-codegen for information about generated code. See go/ph-lockdown for more information about this error."
            com.google.android.gms.internal.vision.zzde.checkState(r0, r1)
        L11:
            java.util.concurrent.atomic.AtomicInteger r0 = com.google.android.gms.internal.vision.zzbi.zzgl
            int r0 = r0.get()
            int r1 = r6.zzgm
            if (r1 >= r0) goto L8a
            monitor-enter(r6)
            int r1 = r6.zzgm     // Catch: java.lang.Throwable -> L87
            if (r1 >= r0) goto L85
            com.google.android.gms.internal.vision.zzbr r1 = com.google.android.gms.internal.vision.zzbi.zzgf     // Catch: java.lang.Throwable -> L87
            if (r1 == 0) goto L26
            r2 = 1
            goto L27
        L26:
            r2 = 0
        L27:
            java.lang.String r3 = "Must call PhenotypeFlag.init() first"
            com.google.android.gms.internal.vision.zzde.checkState(r2, r3)     // Catch: java.lang.Throwable -> L87
            com.google.android.gms.internal.vision.zzbo r2 = r6.zzgj     // Catch: java.lang.Throwable -> L87
            boolean r2 = r2.zzgy     // Catch: java.lang.Throwable -> L87
            if (r2 == 0) goto L40
            java.lang.Object r2 = r6.zzb(r1)     // Catch: java.lang.Throwable -> L87
            if (r2 == 0) goto L39
            goto L50
        L39:
            java.lang.Object r2 = r6.zza(r1)     // Catch: java.lang.Throwable -> L87
            if (r2 == 0) goto L4e
            goto L50
        L40:
            java.lang.Object r2 = r6.zza(r1)     // Catch: java.lang.Throwable -> L87
            if (r2 == 0) goto L47
            goto L50
        L47:
            java.lang.Object r2 = r6.zzb(r1)     // Catch: java.lang.Throwable -> L87
            if (r2 == 0) goto L4e
            goto L50
        L4e:
            T r2 = r6.zzgk     // Catch: java.lang.Throwable -> L87
        L50:
            com.google.android.gms.internal.vision.zzdf r1 = r1.zzab()     // Catch: java.lang.Throwable -> L87
            java.lang.Object r1 = r1.get()     // Catch: java.lang.Throwable -> L87
            com.google.android.gms.internal.vision.zzcy r1 = (com.google.android.gms.internal.vision.zzcy) r1     // Catch: java.lang.Throwable -> L87
            boolean r3 = r1.isPresent()     // Catch: java.lang.Throwable -> L87
            if (r3 == 0) goto L81
            java.lang.Object r1 = r1.get()     // Catch: java.lang.Throwable -> L87
            com.google.android.gms.internal.vision.zzbe r1 = (com.google.android.gms.internal.vision.zzbe) r1     // Catch: java.lang.Throwable -> L87
            com.google.android.gms.internal.vision.zzbo r2 = r6.zzgj     // Catch: java.lang.Throwable -> L87
            android.net.Uri r2 = r2.zzgu     // Catch: java.lang.Throwable -> L87
            com.google.android.gms.internal.vision.zzbo r3 = r6.zzgj     // Catch: java.lang.Throwable -> L87
            java.lang.String r3 = r3.zzgt     // Catch: java.lang.Throwable -> L87
            com.google.android.gms.internal.vision.zzbo r4 = r6.zzgj     // Catch: java.lang.Throwable -> L87
            java.lang.String r4 = r4.zzgw     // Catch: java.lang.Throwable -> L87
            java.lang.String r5 = r6.name     // Catch: java.lang.Throwable -> L87
            java.lang.String r1 = r1.zza(r2, r3, r4, r5)     // Catch: java.lang.Throwable -> L87
            if (r1 != 0) goto L7d
            T r2 = r6.zzgk     // Catch: java.lang.Throwable -> L87
            goto L81
        L7d:
            java.lang.Object r2 = r6.zza(r1)     // Catch: java.lang.Throwable -> L87
        L81:
            r6.zzgn = r2     // Catch: java.lang.Throwable -> L87
            r6.zzgm = r0     // Catch: java.lang.Throwable -> L87
        L85:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L87
            goto L8a
        L87:
            r0 = move-exception
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L87
            throw r0
        L8a:
            T r0 = r6.zzgn
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzbi.get():java.lang.Object");
    }

    @Nullable
    private final T zza(zzbr zzbrVar) {
        zzay zzayVarZzb;
        Object objZzb;
        boolean z = false;
        if (!this.zzgj.zzgz) {
            String str = (String) zzbd.zze(zzbrVar.zzaa()).zzb("gms:phenotype:phenotype_flag:debug_bypass_phenotype");
            if (str != null && zzaq.zzfb.matcher(str).matches()) {
                z = true;
            }
        }
        if (!z) {
            if (this.zzgj.zzgu != null) {
                if (!zzbg.zza(zzbrVar.zzaa(), this.zzgj.zzgu)) {
                    zzayVarZzb = null;
                } else if (this.zzgj.zzha) {
                    ContentResolver contentResolver = zzbrVar.zzaa().getContentResolver();
                    String lastPathSegment = this.zzgj.zzgu.getLastPathSegment();
                    String packageName = zzbrVar.zzaa().getPackageName();
                    zzayVarZzb = zzau.zza(contentResolver, zzbj.getContentProviderUri(new StringBuilder(String.valueOf(lastPathSegment).length() + 1 + String.valueOf(packageName).length()).append(lastPathSegment).append("#").append(packageName).toString()));
                } else {
                    zzayVarZzb = zzau.zza(zzbrVar.zzaa().getContentResolver(), this.zzgj.zzgu);
                }
            } else {
                zzayVarZzb = zzbq.zzb(zzbrVar.zzaa(), this.zzgj.zzgt);
            }
            if (zzayVarZzb != null && (objZzb = zzayVarZzb.zzb(zzag())) != null) {
                return zza(objZzb);
            }
        } else if (Log.isLoggable("PhenotypeFlag", 3)) {
            String strValueOf = String.valueOf(zzag());
            Log.d("PhenotypeFlag", strValueOf.length() != 0 ? "Bypass reading Phenotype values for flag: ".concat(strValueOf) : new String("Bypass reading Phenotype values for flag: "));
        }
        return null;
    }

    @Nullable
    private final T zzb(zzbr zzbrVar) {
        if (!this.zzgj.zzgx && (this.zzgj.zzhb == null || this.zzgj.zzhb.apply(zzbrVar.zzaa()).booleanValue())) {
            Object objZzb = zzbd.zze(zzbrVar.zzaa()).zzb(this.zzgj.zzgx ? null : zze(this.zzgj.zzgv));
            if (objZzb != null) {
                return zza(objZzb);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> zzbi<T> zza(zzbo zzboVar, String str, T t, zzbp<T> zzbpVar, boolean z) {
        return new zzbm(zzboVar, str, t, true, zzbpVar);
    }

    static final /* synthetic */ zzcy zzk(Context context) {
        new zzbh();
        return zzbh.zzg(context);
    }

    /* synthetic */ zzbi(zzbo zzboVar, String str, Object obj, boolean z, zzbn zzbnVar) {
        this(zzboVar, str, obj, z);
    }
}
