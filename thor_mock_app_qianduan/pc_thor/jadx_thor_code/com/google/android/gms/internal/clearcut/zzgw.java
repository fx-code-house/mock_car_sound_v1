package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzcg;
import java.util.List;

/* loaded from: classes2.dex */
public final class zzgw {

    public static final class zza extends zzcg<zza, C0018zza> implements zzdq {
        private static volatile zzdz<zza> zzbg;
        private static final zza zzbir;
        private zzcn<zzb> zzbiq = zzbb();

        /* renamed from: com.google.android.gms.internal.clearcut.zzgw$zza$zza, reason: collision with other inner class name */
        public static final class C0018zza extends zzcg.zza<zza, C0018zza> implements zzdq {
            private C0018zza() {
                super(zza.zzbir);
            }

            /* synthetic */ C0018zza(zzgx zzgxVar) {
                this();
            }
        }

        public static final class zzb extends zzcg<zzb, C0019zza> implements zzdq {
            private static volatile zzdz<zzb> zzbg;
            private static final zzb zzbiv;
            private int zzbb;
            private String zzbis = "";
            private long zzbit;
            private long zzbiu;
            private int zzya;

            /* renamed from: com.google.android.gms.internal.clearcut.zzgw$zza$zzb$zza, reason: collision with other inner class name */
            public static final class C0019zza extends zzcg.zza<zzb, C0019zza> implements zzdq {
                private C0019zza() {
                    super(zzb.zzbiv);
                }

                /* synthetic */ C0019zza(zzgx zzgxVar) {
                    this();
                }

                public final C0019zza zzn(String str) {
                    zzbf();
                    ((zzb) this.zzjt).zzm(str);
                    return this;
                }

                public final C0019zza zzr(long j) {
                    zzbf();
                    ((zzb) this.zzjt).zzp(j);
                    return this;
                }

                public final C0019zza zzs(long j) {
                    zzbf();
                    ((zzb) this.zzjt).zzq(j);
                    return this;
                }
            }

            static {
                zzb zzbVar = new zzb();
                zzbiv = zzbVar;
                zzcg.zza((Class<zzb>) zzb.class, zzbVar);
            }

            private zzb() {
            }

            public static C0019zza zzfz() {
                return (C0019zza) ((zzcg.zza) zzbiv.zza(zzcg.zzg.zzkh, (Object) null, (Object) null));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zzm(String str) {
                str.getClass();
                this.zzbb |= 2;
                this.zzbis = str;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zzp(long j) {
                this.zzbb |= 4;
                this.zzbit = j;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zzq(long j) {
                this.zzbb |= 8;
                this.zzbiu = j;
            }

            public final int getEventCode() {
                return this.zzya;
            }

            /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.clearcut.zzcg$zzb, com.google.android.gms.internal.clearcut.zzdz<com.google.android.gms.internal.clearcut.zzgw$zza$zzb>] */
            @Override // com.google.android.gms.internal.clearcut.zzcg
            protected final Object zza(int i, Object obj, Object obj2) {
                zzdz<zzb> zzdzVar;
                zzgx zzgxVar = null;
                switch (zzgx.zzba[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new C0019zza(zzgxVar);
                    case 3:
                        return zza(zzbiv, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0005\u0000\u0000\u0000\u0001\u0004\u0000\u0002\b\u0001\u0003\u0002\u0002\u0004\u0002\u0003", new Object[]{"zzbb", "zzya", "zzbis", "zzbit", "zzbiu"});
                    case 4:
                        return zzbiv;
                    case 5:
                        zzdz<zzb> zzdzVar2 = zzbg;
                        zzdz<zzb> zzdzVar3 = zzdzVar2;
                        if (zzdzVar2 == null) {
                            synchronized (zzb.class) {
                                zzdz<zzb> zzdzVar4 = zzbg;
                                zzdzVar = zzdzVar4;
                                if (zzdzVar4 == null) {
                                    ?? zzbVar = new zzcg.zzb(zzbiv);
                                    zzbg = zzbVar;
                                    zzdzVar = zzbVar;
                                }
                            }
                            zzdzVar3 = zzdzVar;
                        }
                        return zzdzVar3;
                    case 6:
                        return (byte) 1;
                    case 7:
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            public final boolean zzfv() {
                return (this.zzbb & 1) == 1;
            }

            public final String zzfw() {
                return this.zzbis;
            }

            public final long zzfx() {
                return this.zzbit;
            }

            public final long zzfy() {
                return this.zzbiu;
            }
        }

        static {
            zza zzaVar = new zza();
            zzbir = zzaVar;
            zzcg.zza((Class<zza>) zza.class, zzaVar);
        }

        private zza() {
        }

        public static zza zzft() {
            return zzbir;
        }

        public static zza zzi(byte[] bArr) throws zzco {
            return (zza) zzcg.zzb(zzbir, bArr);
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.clearcut.zzcg$zzb, com.google.android.gms.internal.clearcut.zzdz<com.google.android.gms.internal.clearcut.zzgw$zza>] */
        @Override // com.google.android.gms.internal.clearcut.zzcg
        protected final Object zza(int i, Object obj, Object obj2) {
            zzdz<zza> zzdzVar;
            zzgx zzgxVar = null;
            switch (zzgx.zzba[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0018zza(zzgxVar);
                case 3:
                    return zza(zzbir, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0002\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzbiq", zzb.class});
                case 4:
                    return zzbir;
                case 5:
                    zzdz<zza> zzdzVar2 = zzbg;
                    zzdz<zza> zzdzVar3 = zzdzVar2;
                    if (zzdzVar2 == null) {
                        synchronized (zza.class) {
                            zzdz<zza> zzdzVar4 = zzbg;
                            zzdzVar = zzdzVar4;
                            if (zzdzVar4 == null) {
                                ?? zzbVar = new zzcg.zzb(zzbir);
                                zzbg = zzbVar;
                                zzdzVar = zzbVar;
                            }
                        }
                        zzdzVar3 = zzdzVar;
                    }
                    return zzdzVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final List<zzb> zzfs() {
            return this.zzbiq;
        }
    }
}
