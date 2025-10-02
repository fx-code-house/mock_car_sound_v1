package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzhj {

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public static final class zza extends zzdx<zza, C0023zza> implements zzfj {
        private static volatile zzfr<zza> zzbc;
        private static final zza zzqr;
        private int zzbd;
        private boolean zzqn;
        private int zzqo;
        private String zzqp = "";
        private zzee<zzb> zzqq = zzbp();

        private zza() {
        }

        /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
        /* renamed from: com.google.android.gms.internal.icing.zzhj$zza$zza, reason: collision with other inner class name */
        public static final class C0023zza extends zzdx.zzb<zza, C0023zza> implements zzfj {
            private C0023zza() {
                super(zza.zzqr);
            }

            /* synthetic */ C0023zza(zzhi zzhiVar) {
                this();
            }
        }

        public final boolean zzdv() {
            return this.zzqn;
        }

        public final int getScore() {
            return this.zzqo;
        }

        public final String zzdw() {
            return this.zzqp;
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.icing.zzdx$zza, com.google.android.gms.internal.icing.zzfr<com.google.android.gms.internal.icing.zzhj$zza>] */
        @Override // com.google.android.gms.internal.icing.zzdx
        protected final Object zza(int i, Object obj, Object obj2) {
            zzfr<zza> zzfrVar;
            zzhi zzhiVar = null;
            switch (zzhi.zzaz[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0023zza(zzhiVar);
                case 3:
                    return zza(zzqr, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001\u0007\u0000\u0002\u0004\u0001\u0003\b\u0002\u0004\u001b", new Object[]{"zzbd", "zzqn", "zzqo", "zzqp", "zzqq", zzb.class});
                case 4:
                    return zzqr;
                case 5:
                    zzfr<zza> zzfrVar2 = zzbc;
                    zzfr<zza> zzfrVar3 = zzfrVar2;
                    if (zzfrVar2 == null) {
                        synchronized (zza.class) {
                            zzfr<zza> zzfrVar4 = zzbc;
                            zzfrVar = zzfrVar4;
                            if (zzfrVar4 == null) {
                                ?? zzaVar = new zzdx.zza(zzqr);
                                zzbc = zzaVar;
                                zzfrVar = zzaVar;
                            }
                        }
                        zzfrVar3 = zzfrVar;
                    }
                    return zzfrVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zza zzdx() {
            return zzqr;
        }

        static {
            zza zzaVar = new zza();
            zzqr = zzaVar;
            zzdx.zza((Class<zza>) zza.class, zzaVar);
        }
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public static final class zzb extends zzdx<zzb, zza> implements zzfj {
        private static volatile zzfr<zzb> zzbc;
        private static final zzb zzqz;
        private int zzbd;
        private String zzqs = "";
        private zzea zzqt = zzbo();
        private zzef zzqu = zzbm();
        private zzee<String> zzqv = zzdx.zzbp();
        private zzee<zzc> zzqw = zzbp();
        private zzct zzqx = zzct.zzgi;
        private zzed zzqy = zzbn();

        private zzb() {
        }

        /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
        public static final class zza extends zzdx.zzb<zzb, zza> implements zzfj {
            private zza() {
                super(zzb.zzqz);
            }

            /* synthetic */ zza(zzhi zzhiVar) {
                this();
            }
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.icing.zzdx$zza, com.google.android.gms.internal.icing.zzfr<com.google.android.gms.internal.icing.zzhj$zzb>] */
        @Override // com.google.android.gms.internal.icing.zzdx
        protected final Object zza(int i, Object obj, Object obj2) {
            zzfr<zzb> zzfrVar;
            zzhi zzhiVar = null;
            switch (zzhi.zzaz[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzhiVar);
                case 3:
                    return zza(zzqz, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0005\u0000\u0001\b\u0000\u0002\u0019\u0003\u0014\u0004\u001a\u0005\u001b\u0006\n\u0001\u0007\u0012", new Object[]{"zzbd", "zzqs", "zzqt", "zzqu", "zzqv", "zzqw", zzc.class, "zzqx", "zzqy"});
                case 4:
                    return zzqz;
                case 5:
                    zzfr<zzb> zzfrVar2 = zzbc;
                    zzfr<zzb> zzfrVar3 = zzfrVar2;
                    if (zzfrVar2 == null) {
                        synchronized (zzb.class) {
                            zzfr<zzb> zzfrVar4 = zzbc;
                            zzfrVar = zzfrVar4;
                            if (zzfrVar4 == null) {
                                ?? zzaVar = new zzdx.zza(zzqz);
                                zzbc = zzaVar;
                                zzfrVar = zzaVar;
                            }
                        }
                        zzfrVar3 = zzfrVar;
                    }
                    return zzfrVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzb zzbVar = new zzb();
            zzqz = zzbVar;
            zzdx.zza((Class<zzb>) zzb.class, zzbVar);
        }
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public static final class zzc extends zzdx<zzc, zza> implements zzfj {
        private static volatile zzfr<zzc> zzbc;
        private static final zzc zzrd;
        private int zzbd;
        private zza zzrc;
        private String zzra = "";
        private String zzrb = "";
        private zzee<zzb> zzqq = zzbp();

        private zzc() {
        }

        /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
        public static final class zza extends zzdx.zzb<zzc, zza> implements zzfj {
            private zza() {
                super(zzc.zzrd);
            }

            /* synthetic */ zza(zzhi zzhiVar) {
                this();
            }
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.icing.zzdx$zza, com.google.android.gms.internal.icing.zzfr<com.google.android.gms.internal.icing.zzhj$zzc>] */
        @Override // com.google.android.gms.internal.icing.zzdx
        protected final Object zza(int i, Object obj, Object obj2) {
            zzfr<zzc> zzfrVar;
            zzhi zzhiVar = null;
            switch (zzhi.zzaz[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza(zzhiVar);
                case 3:
                    return zza(zzrd, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001\b\u0000\u0002\b\u0001\u0003\u001b\u0004\t\u0002", new Object[]{"zzbd", "zzra", "zzrb", "zzqq", zzb.class, "zzrc"});
                case 4:
                    return zzrd;
                case 5:
                    zzfr<zzc> zzfrVar2 = zzbc;
                    zzfr<zzc> zzfrVar3 = zzfrVar2;
                    if (zzfrVar2 == null) {
                        synchronized (zzc.class) {
                            zzfr<zzc> zzfrVar4 = zzbc;
                            zzfrVar = zzfrVar4;
                            if (zzfrVar4 == null) {
                                ?? zzaVar = new zzdx.zza(zzrd);
                                zzbc = zzaVar;
                                zzfrVar = zzaVar;
                            }
                        }
                        zzfrVar3 = zzfrVar;
                    }
                    return zzfrVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzc zzcVar = new zzc();
            zzrd = zzcVar;
            zzdx.zza((Class<zzc>) zzc.class, zzcVar);
        }
    }
}
