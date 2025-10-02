package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzal {

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public static final class zza extends zzdx<zza, C0021zza> implements zzfj {
        private static final zza zzbb;
        private static volatile zzfr<zza> zzbc;
        private zzee<zzb> zzba = zzbp();

        private zza() {
        }

        /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
        public static final class zzb extends zzdx<zzb, C0022zza> implements zzfj {
            private static volatile zzfr<zzb> zzbc;
            private static final zzb zzbh;
            private int zzbd;
            private String zzbe = "";
            private String zzbf = "";
            private int zzbg;

            private zzb() {
            }

            /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
            /* renamed from: com.google.android.gms.internal.icing.zzal$zza$zzb$zza, reason: collision with other inner class name */
            public static final class C0022zza extends zzdx.zzb<zzb, C0022zza> implements zzfj {
                private C0022zza() {
                    super(zzb.zzbh);
                }

                public final C0022zza zze(String str) {
                    if (this.zzki) {
                        zzbt();
                        this.zzki = false;
                    }
                    ((zzb) this.zzkh).zzg(str);
                    return this;
                }

                public final C0022zza zzf(String str) {
                    if (this.zzki) {
                        zzbt();
                        this.zzki = false;
                    }
                    ((zzb) this.zzkh).zzh(str);
                    return this;
                }

                public final C0022zza zzd(int i) {
                    if (this.zzki) {
                        zzbt();
                        this.zzki = false;
                    }
                    ((zzb) this.zzkh).zze(i);
                    return this;
                }

                /* synthetic */ C0022zza(zzak zzakVar) {
                    this();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zzg(String str) {
                str.getClass();
                this.zzbd |= 1;
                this.zzbe = str;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zzh(String str) {
                str.getClass();
                this.zzbd |= 2;
                this.zzbf = str;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zze(int i) {
                this.zzbd |= 4;
                this.zzbg = i;
            }

            public static C0022zza zzh() {
                return zzbh.zzbk();
            }

            /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.icing.zzdx$zza, com.google.android.gms.internal.icing.zzfr<com.google.android.gms.internal.icing.zzal$zza$zzb>] */
            @Override // com.google.android.gms.internal.icing.zzdx
            protected final Object zza(int i, Object obj, Object obj2) {
                zzfr<zzb> zzfrVar;
                zzak zzakVar = null;
                switch (zzak.zzaz[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new C0022zza(zzakVar);
                    case 3:
                        return zza(zzbh, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\u0004\u0002", new Object[]{"zzbd", "zzbe", "zzbf", "zzbg"});
                    case 4:
                        return zzbh;
                    case 5:
                        zzfr<zzb> zzfrVar2 = zzbc;
                        zzfr<zzb> zzfrVar3 = zzfrVar2;
                        if (zzfrVar2 == null) {
                            synchronized (zzb.class) {
                                zzfr<zzb> zzfrVar4 = zzbc;
                                zzfrVar = zzfrVar4;
                                if (zzfrVar4 == null) {
                                    ?? zzaVar = new zzdx.zza(zzbh);
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
                zzbh = zzbVar;
                zzdx.zza((Class<zzb>) zzb.class, zzbVar);
            }
        }

        /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
        /* renamed from: com.google.android.gms.internal.icing.zzal$zza$zza, reason: collision with other inner class name */
        public static final class C0021zza extends zzdx.zzb<zza, C0021zza> implements zzfj {
            private C0021zza() {
                super(zza.zzbb);
            }

            public final C0021zza zza(Iterable<? extends zzb> iterable) {
                if (this.zzki) {
                    zzbt();
                    this.zzki = false;
                }
                ((zza) this.zzkh).zzb(iterable);
                return this;
            }

            /* synthetic */ C0021zza(zzak zzakVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(Iterable<? extends zzb> iterable) {
            if (!this.zzba.zzah()) {
                this.zzba = zzdx.zza(this.zzba);
            }
            zzcm.zza(iterable, this.zzba);
        }

        public static C0021zza zzf() {
            return zzbb.zzbk();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.icing.zzdx$zza, com.google.android.gms.internal.icing.zzfr<com.google.android.gms.internal.icing.zzal$zza>] */
        @Override // com.google.android.gms.internal.icing.zzdx
        protected final Object zza(int i, Object obj, Object obj2) {
            zzfr<zza> zzfrVar;
            zzak zzakVar = null;
            switch (zzak.zzaz[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0021zza(zzakVar);
                case 3:
                    return zza(zzbb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzba", zzb.class});
                case 4:
                    return zzbb;
                case 5:
                    zzfr<zza> zzfrVar2 = zzbc;
                    zzfr<zza> zzfrVar3 = zzfrVar2;
                    if (zzfrVar2 == null) {
                        synchronized (zza.class) {
                            zzfr<zza> zzfrVar4 = zzbc;
                            zzfrVar = zzfrVar4;
                            if (zzfrVar4 == null) {
                                ?? zzaVar = new zzdx.zza(zzbb);
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
            zza zzaVar = new zza();
            zzbb = zzaVar;
            zzdx.zza((Class<zza>) zza.class, zzaVar);
        }
    }
}
