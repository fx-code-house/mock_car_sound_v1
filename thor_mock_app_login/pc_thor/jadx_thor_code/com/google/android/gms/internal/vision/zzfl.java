package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzid;
import kotlin.text.Typography;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzfl {

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzb extends zzid<zzb, zza> implements zzjp {
        private static volatile zzjw<zzb> zzbk;
        private static final zzil<Integer, zzfz> zzoh = new zzfm();
        private static final zzb zzoi;
        private zzii zzog = zzha();

        private zzb() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzb, zza> implements zzjp {
            private zza() {
                super(zzb.zzoi);
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzb>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzb> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzoi, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001e", new Object[]{"zzog", zzfz.zzal()});
                case 4:
                    return zzoi;
                case 5:
                    zzjw<zzb> zzjwVar2 = zzbk;
                    zzjw<zzb> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzb.class) {
                            zzjw<zzb> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzoi);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.vision.zzfm, com.google.android.gms.internal.vision.zzil<java.lang.Integer, com.google.android.gms.internal.vision.zzfz>] */
        static {
            zzb zzbVar = new zzb();
            zzoi = zzbVar;
            zzid.zza((Class<zzb>) zzb.class, zzbVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzc extends zzid<zzc, zza> implements zzjp {
        private static volatile zzjw<zzc> zzbk;
        private static final zzc zzom;
        private int zzbm;
        private int zzoj;
        private int zzok;
        private String zzol = "";

        private zzc() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzc, zza> implements zzjp {
            private zza() {
                super(zzc.zzom);
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzc>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzc> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzom, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဈ\u0002", new Object[]{"zzbm", "zzoj", zzfz.zzal(), "zzok", zzgd.zzal(), "zzol"});
                case 4:
                    return zzom;
                case 5:
                    zzjw<zzc> zzjwVar2 = zzbk;
                    zzjw<zzc> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzc.class) {
                            zzjw<zzc> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzom);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
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
            zzom = zzcVar;
            zzid.zza((Class<zzc>) zzc.class, zzcVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zze extends zzid<zze, zzb> implements zzjp {
        private static volatile zzjw<zze> zzbk;
        private static final zze zzox;
        private int zzbm;
        private boolean zzoq;
        private int zzor;
        private long zzos;
        private long zzot;
        private long zzou;
        private boolean zzow;
        private String zzop = "";
        private String zzov = "";

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public enum zza implements zzih {
            REASON_UNKNOWN(0),
            REASON_MISSING(1),
            REASON_UPGRADE(2),
            REASON_INVALID(3);

            private static final zzig<zza> zzhq = new zzfo();
            private final int value;

            @Override // com.google.android.gms.internal.vision.zzih
            public final int zzak() {
                return this.value;
            }

            public static zza zzz(int i) {
                if (i == 0) {
                    return REASON_UNKNOWN;
                }
                if (i == 1) {
                    return REASON_MISSING;
                }
                if (i == 2) {
                    return REASON_UPGRADE;
                }
                if (i != 3) {
                    return null;
                }
                return REASON_INVALID;
            }

            public static zzij zzal() {
                return zzfn.zzht;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.value);
                return sb.append(" name=").append(name()).append(Typography.greater).toString();
            }

            zza(int i) {
                this.value = i;
            }
        }

        private zze() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zzb extends zzid.zza<zze, zzb> implements zzjp {
            private zzb() {
                super(zze.zzox);
            }

            /* synthetic */ zzb(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zze>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zze> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zzb(zzfkVar);
                case 3:
                    return zza(zzox, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဇ\u0001\u0003ဌ\u0002\u0004ဂ\u0003\u0005ဂ\u0004\u0006ဂ\u0005\u0007ဈ\u0006\bဇ\u0007", new Object[]{"zzbm", "zzop", "zzoq", "zzor", zza.zzal(), "zzos", "zzot", "zzou", "zzov", "zzow"});
                case 4:
                    return zzox;
                case 5:
                    zzjw<zze> zzjwVar2 = zzbk;
                    zzjw<zze> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zze.class) {
                            zzjw<zze> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzox);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zze zzeVar = new zze();
            zzox = zzeVar;
            zzid.zza((Class<zze>) zze.class, zzeVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzl extends zzid<zzl, zza> implements zzjp {
        private static volatile zzjw<zzl> zzbk;
        private static final zzl zzrk;
        private int zzbm;
        private long zzpi;
        private long zzpj;

        private zzl() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzl, zza> implements zzjp {
            private zza() {
                super(zzl.zzrk);
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzl>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzl> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzl();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzrk, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဂ\u0000\u0002ဂ\u0001", new Object[]{"zzbm", "zzpi", "zzpj"});
                case 4:
                    return zzrk;
                case 5:
                    zzjw<zzl> zzjwVar2 = zzbk;
                    zzjw<zzl> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzl.class) {
                            zzjw<zzl> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzrk);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzl zzlVar = new zzl();
            zzrk = zzlVar;
            zzid.zza((Class<zzl>) zzl.class, zzlVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zza extends zzid<zza, C0029zza> implements zzjp {
        private static volatile zzjw<zza> zzbk;
        private static final zza zzof;
        private int zzbm;
        private String zzod = "";
        private String zzoe = "";

        private zza() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        /* renamed from: com.google.android.gms.internal.vision.zzfl$zza$zza, reason: collision with other inner class name */
        public static final class C0029zza extends zzid.zza<zza, C0029zza> implements zzjp {
            private C0029zza() {
                super(zza.zzof);
            }

            public final C0029zza zzm(String str) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zza) this.zzxx).zzo(str);
                return this;
            }

            public final C0029zza zzn(String str) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zza) this.zzxx).zzp(str);
                return this;
            }

            /* synthetic */ C0029zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzo(String str) {
            str.getClass();
            this.zzbm |= 1;
            this.zzod = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzp(String str) {
            str.getClass();
            this.zzbm |= 2;
            this.zzoe = str;
        }

        public static C0029zza zzdd() {
            return zzof.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zza>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zza> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0029zza(zzfkVar);
                case 3:
                    return zza(zzof, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zzbm", "zzod", "zzoe"});
                case 4:
                    return zzof;
                case 5:
                    zzjw<zza> zzjwVar2 = zzbk;
                    zzjw<zza> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zza.class) {
                            zzjw<zza> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzof);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
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
            zzof = zzaVar;
            zzid.zza((Class<zza>) zza.class, zzaVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzd extends zzid<zzd, zza> implements zzjp {
        private static volatile zzjw<zzd> zzbk;
        private static final zzd zzoo;
        private zzik<zzm> zzon = zzhb();

        private zzd() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzd, zza> implements zzjp {
            private zza() {
                super(zzd.zzoo);
            }

            public final zza zzb(zzm zzmVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzd) this.zzxx).zza(zzmVar);
                return this;
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzm zzmVar) {
            zzmVar.getClass();
            zzik<zzm> zzikVar = this.zzon;
            if (!zzikVar.zzei()) {
                this.zzon = zzid.zza(zzikVar);
            }
            this.zzon.add(zzmVar);
        }

        public static zza zzdh() {
            return zzoo.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzd>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzd> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzoo, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzon", zzm.class});
                case 4:
                    return zzoo;
                case 5:
                    zzjw<zzd> zzjwVar2 = zzbk;
                    zzjw<zzd> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzd.class) {
                            zzjw<zzd> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzoo);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzd zzdVar = new zzd();
            zzoo = zzdVar;
            zzid.zza((Class<zzd>) zzd.class, zzdVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzf extends zzid<zzf, zza> implements zzjp {
        private static volatile zzjw<zzf> zzbk;
        private static final zzf zzpl;
        private int zzbm;
        private int zzpg;
        private long zzpi;
        private long zzpj;
        private String zzpd = "";
        private String zzpe = "";
        private zzik<String> zzpf = zzid.zzhb();
        private String zzph = "";
        private zzik<zzn> zzpk = zzhb();

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public enum zzb implements zzih {
            RESULT_UNKNOWN(0),
            RESULT_SUCCESS(1),
            RESULT_FAIL(2),
            RESULT_SKIPPED(3);

            private static final zzig<zzb> zzhq = new zzfp();
            private final int value;

            @Override // com.google.android.gms.internal.vision.zzih
            public final int zzak() {
                return this.value;
            }

            public static zzb zzaa(int i) {
                if (i == 0) {
                    return RESULT_UNKNOWN;
                }
                if (i == 1) {
                    return RESULT_SUCCESS;
                }
                if (i == 2) {
                    return RESULT_FAIL;
                }
                if (i != 3) {
                    return null;
                }
                return RESULT_SKIPPED;
            }

            public static zzij zzal() {
                return zzfq.zzht;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.value);
                return sb.append(" name=").append(name()).append(Typography.greater).toString();
            }

            zzb(int i) {
                this.value = i;
            }
        }

        private zzf() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzf, zza> implements zzjp {
            private zza() {
                super(zzf.zzpl);
            }

            public final zza zzq(String str) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzf) this.zzxx).setName(str);
                return this;
            }

            public final zza zzd(long j) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzf) this.zzxx).zzf(j);
                return this;
            }

            public final zza zze(long j) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzf) this.zzxx).zzg(j);
                return this;
            }

            public final zza zzc(Iterable<? extends zzn> iterable) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzf) this.zzxx).zzd(iterable);
                return this;
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setName(String str) {
            str.getClass();
            this.zzbm |= 1;
            this.zzpd = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzf(long j) {
            this.zzbm |= 16;
            this.zzpi = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzg(long j) {
            this.zzbm |= 32;
            this.zzpj = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(Iterable<? extends zzn> iterable) {
            zzik<zzn> zzikVar = this.zzpk;
            if (!zzikVar.zzei()) {
                this.zzpk = zzid.zza(zzikVar);
            }
            zzge.zza(iterable, this.zzpk);
        }

        public static zza zzdk() {
            return zzpl.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzf>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzf> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzpl, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0002\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003\u001a\u0004ဌ\u0002\u0005ဈ\u0003\u0006ဂ\u0004\u0007ဂ\u0005\b\u001b", new Object[]{"zzbm", "zzpd", "zzpe", "zzpf", "zzpg", zzb.zzal(), "zzph", "zzpi", "zzpj", "zzpk", zzn.class});
                case 4:
                    return zzpl;
                case 5:
                    zzjw<zzf> zzjwVar2 = zzbk;
                    zzjw<zzf> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzf.class) {
                            zzjw<zzf> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzpl);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzf zzfVar = new zzf();
            zzpl = zzfVar;
            zzid.zza((Class<zzf>) zzf.class, zzfVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzg extends zzid<zzg, zzb> implements zzjp {
        private static volatile zzjw<zzg> zzbk;
        private static final zzg zzpv;
        private int zzbm;
        private float zzkp;
        private boolean zzkt;
        private int zzpr;
        private int zzps;
        private int zzpt;
        private boolean zzpu;

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public enum zza implements zzih {
            CLASSIFICATION_UNKNOWN(0),
            CLASSIFICATION_NONE(1),
            CLASSIFICATION_ALL(2);

            private static final zzig<zza> zzhq = new zzfs();
            private final int value;

            @Override // com.google.android.gms.internal.vision.zzih
            public final int zzak() {
                return this.value;
            }

            public static zza zzab(int i) {
                if (i == 0) {
                    return CLASSIFICATION_UNKNOWN;
                }
                if (i == 1) {
                    return CLASSIFICATION_NONE;
                }
                if (i != 2) {
                    return null;
                }
                return CLASSIFICATION_ALL;
            }

            public static zzij zzal() {
                return zzfr.zzht;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.value);
                return sb.append(" name=").append(name()).append(Typography.greater).toString();
            }

            zza(int i) {
                this.value = i;
            }
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public enum zzc implements zzih {
            LANDMARK_UNKNOWN(0),
            LANDMARK_NONE(1),
            LANDMARK_ALL(2),
            LANDMARK_CONTOUR(3);

            private static final zzig<zzc> zzhq = new zzft();
            private final int value;

            @Override // com.google.android.gms.internal.vision.zzih
            public final int zzak() {
                return this.value;
            }

            public static zzc zzac(int i) {
                if (i == 0) {
                    return LANDMARK_UNKNOWN;
                }
                if (i == 1) {
                    return LANDMARK_NONE;
                }
                if (i == 2) {
                    return LANDMARK_ALL;
                }
                if (i != 3) {
                    return null;
                }
                return LANDMARK_CONTOUR;
            }

            public static zzij zzal() {
                return zzfu.zzht;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.value);
                return sb.append(" name=").append(name()).append(Typography.greater).toString();
            }

            zzc(int i) {
                this.value = i;
            }
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public enum zzd implements zzih {
            MODE_UNKNOWN(0),
            MODE_ACCURATE(1),
            MODE_FAST(2),
            MODE_SELFIE(3);

            private static final zzig<zzd> zzhq = new zzfw();
            private final int value;

            @Override // com.google.android.gms.internal.vision.zzih
            public final int zzak() {
                return this.value;
            }

            public static zzd zzad(int i) {
                if (i == 0) {
                    return MODE_UNKNOWN;
                }
                if (i == 1) {
                    return MODE_ACCURATE;
                }
                if (i == 2) {
                    return MODE_FAST;
                }
                if (i != 3) {
                    return null;
                }
                return MODE_SELFIE;
            }

            public static zzij zzal() {
                return zzfv.zzht;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.value);
                return sb.append(" name=").append(name()).append(Typography.greater).toString();
            }

            zzd(int i) {
                this.value = i;
            }
        }

        private zzg() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zzb extends zzid.zza<zzg, zzb> implements zzjp {
            private zzb() {
                super(zzg.zzpv);
            }

            public final zzb zzb(zzd zzdVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzg) this.zzxx).zza(zzdVar);
                return this;
            }

            public final zzb zzb(zzc zzcVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzg) this.zzxx).zza(zzcVar);
                return this;
            }

            public final zzb zzb(zza zzaVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzg) this.zzxx).zza(zzaVar);
                return this;
            }

            public final zzb zzh(boolean z) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzg) this.zzxx).zza(z);
                return this;
            }

            public final zzb zzi(boolean z) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzg) this.zzxx).zzg(z);
                return this;
            }

            public final zzb zzf(float f) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzg) this.zzxx).zzd(f);
                return this;
            }

            /* synthetic */ zzb(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzd zzdVar) {
            this.zzpr = zzdVar.zzak();
            this.zzbm |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzc zzcVar) {
            this.zzps = zzcVar.zzak();
            this.zzbm |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zza zzaVar) {
            this.zzpt = zzaVar.zzak();
            this.zzbm |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzbm |= 8;
            this.zzkt = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzg(boolean z) {
            this.zzbm |= 16;
            this.zzpu = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(float f) {
            this.zzbm |= 32;
            this.zzkp = f;
        }

        public static zzb zzdm() {
            return zzpv.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzg>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzg> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zzb(zzfkVar);
                case 3:
                    return zza(zzpv, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဌ\u0002\u0004ဇ\u0003\u0005ဇ\u0004\u0006ခ\u0005", new Object[]{"zzbm", "zzpr", zzd.zzal(), "zzps", zzc.zzal(), "zzpt", zza.zzal(), "zzkt", "zzpu", "zzkp"});
                case 4:
                    return zzpv;
                case 5:
                    zzjw<zzg> zzjwVar2 = zzbk;
                    zzjw<zzg> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzg.class) {
                            zzjw<zzg> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzpv);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzg zzgVar = new zzg();
            zzpv = zzgVar;
            zzid.zza((Class<zzg>) zzg.class, zzgVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzh extends zzid<zzh, zza> implements zzjp {
        private static volatile zzjw<zzh> zzbk;
        private static final zzh zzqq;
        private int zzbm;
        private float zzqk;
        private float zzql;
        private float zzqm;
        private float zzqn;
        private float zzqo;
        private float zzqp;

        private zzh() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzh, zza> implements zzjp {
            private zza() {
                super(zzh.zzqq);
            }

            public final zza zzg(float f) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzh) this.zzxx).zzm(f);
                return this;
            }

            public final zza zzh(float f) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzh) this.zzxx).zzn(f);
                return this;
            }

            public final zza zzi(float f) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzh) this.zzxx).zzo(f);
                return this;
            }

            public final zza zzj(float f) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzh) this.zzxx).zzp(f);
                return this;
            }

            public final zza zzk(float f) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzh) this.zzxx).zzq(f);
                return this;
            }

            public final zza zzl(float f) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzh) this.zzxx).zzr(f);
                return this;
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzm(float f) {
            this.zzbm |= 1;
            this.zzqk = f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzn(float f) {
            this.zzbm |= 2;
            this.zzql = f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzo(float f) {
            this.zzbm |= 4;
            this.zzqm = f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzp(float f) {
            this.zzbm |= 8;
            this.zzqn = f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzq(float f) {
            this.zzbm |= 16;
            this.zzqo = f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzr(float f) {
            this.zzbm |= 32;
            this.zzqp = f;
        }

        public static zza zzdo() {
            return zzqq.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzh>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzh> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzh();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzqq, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ခ\u0000\u0002ခ\u0001\u0003ခ\u0002\u0004ခ\u0003\u0005ခ\u0004\u0006ခ\u0005", new Object[]{"zzbm", "zzqk", "zzql", "zzqm", "zzqn", "zzqo", "zzqp"});
                case 4:
                    return zzqq;
                case 5:
                    zzjw<zzh> zzjwVar2 = zzbk;
                    zzjw<zzh> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzh.class) {
                            zzjw<zzh> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzqq);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzh zzhVar = new zzh();
            zzqq = zzhVar;
            zzid.zza((Class<zzh>) zzh.class, zzhVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzi extends zzid<zzi, zza> implements zzjp {
        private static volatile zzjw<zzi> zzbk;
        private static final zzi zzqu;
        private int zzbm;
        private zzj zzqr;
        private zzl zzqs;
        private zzik<zzf> zzqt = zzhb();

        private zzi() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzi, zza> implements zzjp {
            private zza() {
                super(zzi.zzqu);
            }

            public final zza zza(zzj zzjVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzi) this.zzxx).zzb(zzjVar);
                return this;
            }

            public final zza zza(zzf.zza zzaVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzi) this.zzxx).zza((zzf) ((zzid) zzaVar.zzgw()));
                return this;
            }

            public final zza zze(Iterable<? extends zzf> iterable) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzi) this.zzxx).zzf(iterable);
                return this;
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(zzj zzjVar) {
            zzjVar.getClass();
            this.zzqr = zzjVar;
            this.zzbm |= 1;
        }

        private final void zzdq() {
            zzik<zzf> zzikVar = this.zzqt;
            if (zzikVar.zzei()) {
                return;
            }
            this.zzqt = zzid.zza(zzikVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzf zzfVar) {
            zzfVar.getClass();
            zzdq();
            this.zzqt.add(zzfVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzf(Iterable<? extends zzf> iterable) {
            zzdq();
            zzge.zza(iterable, this.zzqt);
        }

        public static zza zzdr() {
            return zzqu.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzi>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzi> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzi();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzqu, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001b", new Object[]{"zzbm", "zzqr", "zzqs", "zzqt", zzf.class});
                case 4:
                    return zzqu;
                case 5:
                    zzjw<zzi> zzjwVar2 = zzbk;
                    zzjw<zzi> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzi.class) {
                            zzjw<zzi> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzqu);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzi zziVar = new zzi();
            zzqu = zziVar;
            zzid.zza((Class<zzi>) zzi.class, zziVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzj extends zzid<zzj, zza> implements zzjp {
        private static volatile zzjw<zzj> zzbk;
        private static final zzj zzqz;
        private int zzbm;
        private int zzoj;
        private long zzqv;
        private long zzqw;
        private long zzqx;
        private long zzqy;

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public enum zzb implements zzih {
            FORMAT_UNKNOWN(0),
            FORMAT_LUMINANCE(1),
            FORMAT_RGB8(2),
            FORMAT_MONOCHROME(3);

            private static final zzig<zzb> zzhq = new zzfx();
            private final int value;

            @Override // com.google.android.gms.internal.vision.zzih
            public final int zzak() {
                return this.value;
            }

            public static zzb zzae(int i) {
                if (i == 0) {
                    return FORMAT_UNKNOWN;
                }
                if (i == 1) {
                    return FORMAT_LUMINANCE;
                }
                if (i == 2) {
                    return FORMAT_RGB8;
                }
                if (i != 3) {
                    return null;
                }
                return FORMAT_MONOCHROME;
            }

            public static zzij zzal() {
                return zzfy.zzht;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.value);
                return sb.append(" name=").append(name()).append(Typography.greater).toString();
            }

            zzb(int i) {
                this.value = i;
            }
        }

        private zzj() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzj, zza> implements zzjp {
            private zza() {
                super(zzj.zzqz);
            }

            public final zza zzh(long j) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzj) this.zzxx).zzl(j);
                return this;
            }

            public final zza zzi(long j) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzj) this.zzxx).zzm(j);
                return this;
            }

            public final zza zzj(long j) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzj) this.zzxx).zzn(j);
                return this;
            }

            public final zza zzk(long j) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzj) this.zzxx).zzo(j);
                return this;
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzl(long j) {
            this.zzbm |= 2;
            this.zzqv = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzm(long j) {
            this.zzbm |= 4;
            this.zzqw = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzn(long j) {
            this.zzbm |= 8;
            this.zzqx = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzo(long j) {
            this.zzbm |= 16;
            this.zzqy = j;
        }

        public static zza zzdt() {
            return zzqz.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzj>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzj> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzj();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzqz, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဂ\u0001\u0003ဂ\u0002\u0004ဂ\u0004\u0005ဂ\u0003", new Object[]{"zzbm", "zzoj", zzb.zzal(), "zzqv", "zzqw", "zzqy", "zzqx"});
                case 4:
                    return zzqz;
                case 5:
                    zzjw<zzj> zzjwVar2 = zzbk;
                    zzjw<zzj> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzj.class) {
                            zzjw<zzj> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzqz);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzj zzjVar = new zzj();
            zzqz = zzjVar;
            zzid.zza((Class<zzj>) zzj.class, zzjVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzk extends zzid<zzk, zza> implements zzjp {
        private static volatile zzjw<zzk> zzbk;
        private static final zzk zzrj;
        private int zzbm;
        private long zzrf;
        private zza zzrg;
        private zzg zzrh;
        private zzb zzri;
        private String zzpd = "";
        private String zzov = "";

        private zzk() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzk, zza> implements zzjp {
            private zza() {
                super(zzk.zzrj);
            }

            public final zza zzs(String str) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzk) this.zzxx).setName(str);
                return this;
            }

            public final zza zzq(long j) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzk) this.zzxx).zzp(j);
                return this;
            }

            public final zza zzb(zza zzaVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzk) this.zzxx).zza(zzaVar);
                return this;
            }

            public final zza zzt(String str) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzk) this.zzxx).zzr(str);
                return this;
            }

            public final zza zza(zzg.zzb zzbVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzk) this.zzxx).zza((zzg) ((zzid) zzbVar.zzgw()));
                return this;
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setName(String str) {
            str.getClass();
            this.zzbm |= 1;
            this.zzpd = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzp(long j) {
            this.zzbm |= 2;
            this.zzrf = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zza zzaVar) {
            zzaVar.getClass();
            this.zzrg = zzaVar;
            this.zzbm |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzr(String str) {
            str.getClass();
            this.zzbm |= 8;
            this.zzov = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzg zzgVar) {
            zzgVar.getClass();
            this.zzrh = zzgVar;
            this.zzbm |= 16;
        }

        public static zza zzdv() {
            return zzrj.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzk>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzk> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzk();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzrj, "\u0001\u0006\u0000\u0001\u0001\u0011\u0006\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဂ\u0001\u0003ဉ\u0002\u0006ဈ\u0003\u0010ဉ\u0004\u0011ဉ\u0005", new Object[]{"zzbm", "zzpd", "zzrf", "zzrg", "zzov", "zzrh", "zzri"});
                case 4:
                    return zzrj;
                case 5:
                    zzjw<zzk> zzjwVar2 = zzbk;
                    zzjw<zzk> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzk.class) {
                            zzjw<zzk> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzrj);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzk zzkVar = new zzk();
            zzrj = zzkVar;
            zzid.zza((Class<zzk>) zzk.class, zzkVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzm extends zzid<zzm, zza> implements zzjp {
        private static volatile zzjw<zzm> zzbk;
        private static final zzm zzrn;
        private int zzbm;
        private int zzrl;
        private int zzrm;

        private zzm() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzm, zza> implements zzjp {
            private zza() {
                super(zzm.zzrn);
            }

            public final zza zzaf(int i) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzm) this.zzxx).setX(i);
                return this;
            }

            public final zza zzag(int i) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzm) this.zzxx).setY(i);
                return this;
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setX(int i) {
            this.zzbm |= 1;
            this.zzrl = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setY(int i) {
            this.zzbm |= 2;
            this.zzrm = i;
        }

        public static zza zzdy() {
            return zzrn.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzm>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzm> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzm();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzrn, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001", new Object[]{"zzbm", "zzrl", "zzrm"});
                case 4:
                    return zzrn;
                case 5:
                    zzjw<zzm> zzjwVar2 = zzbk;
                    zzjw<zzm> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzm.class) {
                            zzjw<zzm> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzrn);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzm zzmVar = new zzm();
            zzrn = zzmVar;
            zzid.zza((Class<zzm>) zzm.class, zzmVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzn extends zzid<zzn, zza> implements zzjp {
        private static volatile zzjw<zzn> zzbk;
        private static final zzn zzrs;
        private int zzbm;
        private zzd zzro;
        private int zzrp;
        private zzh zzrq;
        private zzc zzrr;

        private zzn() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzn, zza> implements zzjp {
            private zza() {
                super(zzn.zzrs);
            }

            public final zza zza(zzd.zza zzaVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzn) this.zzxx).zza((zzd) ((zzid) zzaVar.zzgw()));
                return this;
            }

            public final zza zzah(int i) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzn) this.zzxx).setId(i);
                return this;
            }

            public final zza zzb(zzh zzhVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzn) this.zzxx).zza(zzhVar);
                return this;
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzd zzdVar) {
            zzdVar.getClass();
            this.zzro = zzdVar;
            this.zzbm |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setId(int i) {
            this.zzbm |= 2;
            this.zzrp = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzh zzhVar) {
            zzhVar.getClass();
            this.zzrq = zzhVar;
            this.zzbm |= 4;
        }

        public static zza zzea() {
            return zzrs.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzn>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzn> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzn();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzrs, "\u0001\u0004\u0000\u0001\u0001\u0011\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002င\u0001\u0010ဉ\u0002\u0011ဉ\u0003", new Object[]{"zzbm", "zzro", "zzrp", "zzrq", "zzrr"});
                case 4:
                    return zzrs;
                case 5:
                    zzjw<zzn> zzjwVar2 = zzbk;
                    zzjw<zzn> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzn.class) {
                            zzjw<zzn> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzrs);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzn zznVar = new zzn();
            zzrs = zznVar;
            zzid.zza((Class<zzn>) zzn.class, zznVar);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static final class zzo extends zzid<zzo, zza> implements zzjp {
        private static volatile zzjw<zzo> zzbk;
        private static final zzo zzrx;
        private int zzbm;
        private zze zzrt;
        private zzk zzru;
        private zzi zzrv;
        private int zzrw;

        private zzo() {
        }

        /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
        public static final class zza extends zzid.zza<zzo, zza> implements zzjp {
            private zza() {
                super(zzo.zzrx);
            }

            public final zza zza(zzk.zza zzaVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzo) this.zzxx).zza((zzk) ((zzid) zzaVar.zzgw()));
                return this;
            }

            public final zza zzb(zzi zziVar) {
                if (this.zzxy) {
                    zzgs();
                    this.zzxy = false;
                }
                ((zzo) this.zzxx).zza(zziVar);
                return this;
            }

            /* synthetic */ zza(zzfk zzfkVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzk zzkVar) {
            zzkVar.getClass();
            this.zzru = zzkVar;
            this.zzbm |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzi zziVar) {
            zziVar.getClass();
            this.zzrv = zziVar;
            this.zzbm |= 4;
        }

        public static zza zzec() {
            return zzrx.zzgy();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzid$zzc, com.google.android.gms.internal.vision.zzjw<com.google.android.gms.internal.vision.zzfl$zzo>] */
        @Override // com.google.android.gms.internal.vision.zzid
        protected final Object zza(int i, Object obj, Object obj2) {
            zzjw<zzo> zzjwVar;
            zzfk zzfkVar = null;
            switch (zzfk.zzbl[i - 1]) {
                case 1:
                    return new zzo();
                case 2:
                    return new zza(zzfkVar);
                case 3:
                    return zza(zzrx, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004င\u0003", new Object[]{"zzbm", "zzrt", "zzru", "zzrv", "zzrw"});
                case 4:
                    return zzrx;
                case 5:
                    zzjw<zzo> zzjwVar2 = zzbk;
                    zzjw<zzo> zzjwVar3 = zzjwVar2;
                    if (zzjwVar2 == null) {
                        synchronized (zzo.class) {
                            zzjw<zzo> zzjwVar4 = zzbk;
                            zzjwVar = zzjwVar4;
                            if (zzjwVar4 == null) {
                                ?? zzcVar = new zzid.zzc(zzrx);
                                zzbk = zzcVar;
                                zzjwVar = zzcVar;
                            }
                        }
                        zzjwVar3 = zzjwVar;
                    }
                    return zzjwVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzo zzoVar = new zzo();
            zzrx = zzoVar;
            zzid.zza((Class<zzo>) zzo.class, zzoVar);
        }
    }
}
