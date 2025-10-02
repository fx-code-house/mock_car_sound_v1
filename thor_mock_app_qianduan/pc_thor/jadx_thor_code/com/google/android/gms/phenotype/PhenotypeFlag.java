package com.google.android.gms.phenotype;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.UserManager;
import android.util.Log;
import androidx.core.content.PermissionChecker;
import javax.annotation.Nullable;

@Deprecated
/* loaded from: classes2.dex */
public abstract class PhenotypeFlag<T> {
    private static final Object zzak = new Object();
    private static Context zzal = null;
    private static boolean zzam = false;
    private static Boolean zzan;
    private final Factory zzao;
    final String zzap;
    private final String zzaq;
    private final T zzar;
    private T zzas;

    public static class Factory {
        private final String zzax;
        private final Uri zzay;
        private final String zzaz;
        private final String zzba;
        private final boolean zzbb;
        private final boolean zzbc;

        public Factory(Uri uri) {
            this(null, uri, "", "", false, false);
        }

        private Factory(String str, Uri uri, String str2, String str3, boolean z, boolean z2) {
            this.zzax = str;
            this.zzay = uri;
            this.zzaz = str2;
            this.zzba = str3;
            this.zzbb = z;
            this.zzbc = z2;
        }

        public PhenotypeFlag<String> createFlag(String str, String str2) {
            return PhenotypeFlag.zza(this, str, str2);
        }

        public Factory withGservicePrefix(String str) {
            boolean z = this.zzbb;
            if (z) {
                throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
            }
            return new Factory(this.zzax, this.zzay, str, this.zzba, z, this.zzbc);
        }

        public Factory withPhenotypePrefix(String str) {
            return new Factory(this.zzax, this.zzay, this.zzaz, str, this.zzbb, this.zzbc);
        }
    }

    interface zza<V> {
        V zzh();
    }

    private PhenotypeFlag(Factory factory, String str, T t) {
        this.zzas = null;
        if (factory.zzax == null && factory.zzay == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        if (factory.zzax != null && factory.zzay != null) {
            throw new IllegalArgumentException("Must pass one of SharedPreferences file name or ContentProvider URI");
        }
        this.zzao = factory;
        String strValueOf = String.valueOf(factory.zzaz);
        String strValueOf2 = String.valueOf(str);
        this.zzaq = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
        String strValueOf3 = String.valueOf(factory.zzba);
        String strValueOf4 = String.valueOf(str);
        this.zzap = strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3);
        this.zzar = t;
    }

    /* synthetic */ PhenotypeFlag(Factory factory, String str, Object obj, zzr zzrVar) {
        this(factory, str, obj);
    }

    public static void maybeInit(Context context) {
        Context applicationContext;
        com.google.android.gms.internal.phenotype.zzh.maybeInit(context);
        if (zzal == null) {
            com.google.android.gms.internal.phenotype.zzh.init(context);
            synchronized (zzak) {
                if (!context.isDeviceProtectedStorage() && (applicationContext = context.getApplicationContext()) != null) {
                    context = applicationContext;
                }
                if (zzal != context) {
                    zzan = null;
                }
                zzal = context;
            }
            zzam = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PhenotypeFlag<String> zza(Factory factory, String str, String str2) {
        return new zzs(factory, str, str2);
    }

    private static <V> V zza(zza<V> zzaVar) {
        try {
            return zzaVar.zzh();
        } catch (SecurityException unused) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return zzaVar.zzh();
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    static boolean zza(final String str, boolean z) {
        final boolean z2 = false;
        if (zzf()) {
            return ((Boolean) zza(new zza(str, z2) { // from class: com.google.android.gms.phenotype.zzq
                private final String zzav;
                private final boolean zzaw = false;

                {
                    this.zzav = str;
                }

                @Override // com.google.android.gms.phenotype.PhenotypeFlag.zza
                public final Object zzh() {
                    return Boolean.valueOf(com.google.android.gms.internal.phenotype.zzf.zza(PhenotypeFlag.zzal.getContentResolver(), this.zzav, this.zzaw));
                }
            })).booleanValue();
        }
        return false;
    }

    @Nullable
    private final T zzd() {
        if (zza("gms:phenotype:phenotype_flag:debug_bypass_phenotype", false)) {
            String strValueOf = String.valueOf(this.zzap);
            Log.w("PhenotypeFlag", strValueOf.length() != 0 ? "Bypass reading Phenotype values for flag: ".concat(strValueOf) : new String("Bypass reading Phenotype values for flag: "));
        } else if (this.zzao.zzay != null) {
            final com.google.android.gms.phenotype.zza zzaVarZza = com.google.android.gms.phenotype.zza.zza(zzal.getContentResolver(), this.zzao.zzay);
            String str = (String) zza(new zza(this, zzaVarZza) { // from class: com.google.android.gms.phenotype.zzo
                private final PhenotypeFlag zzat;
                private final zza zzau;

                {
                    this.zzat = this;
                    this.zzau = zzaVarZza;
                }

                @Override // com.google.android.gms.phenotype.PhenotypeFlag.zza
                public final Object zzh() {
                    return this.zzau.zza().get(this.zzat.zzap);
                }
            });
            if (str != null) {
                return zza(str);
            }
        } else {
            if (this.zzao.zzax == null || !(zzal.isDeviceProtectedStorage() || ((UserManager) zzal.getSystemService(UserManager.class)).isUserUnlocked())) {
                return null;
            }
            SharedPreferences sharedPreferences = zzal.getSharedPreferences(this.zzao.zzax, 0);
            if (sharedPreferences.contains(this.zzap)) {
                return zza(sharedPreferences);
            }
        }
        return null;
    }

    @Nullable
    private final T zze() {
        String str;
        if (this.zzao.zzbb || !zzf() || (str = (String) zza(new zza(this) { // from class: com.google.android.gms.phenotype.zzp
            private final PhenotypeFlag zzat;

            {
                this.zzat = this;
            }

            @Override // com.google.android.gms.phenotype.PhenotypeFlag.zza
            public final Object zzh() {
                return this.zzat.zzg();
            }
        })) == null) {
            return null;
        }
        return zza(str);
    }

    private static boolean zzf() {
        if (zzan == null) {
            Context context = zzal;
            if (context == null) {
                return false;
            }
            zzan = Boolean.valueOf(PermissionChecker.checkCallingOrSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0);
        }
        return zzan.booleanValue();
    }

    public T get() {
        if (zzal == null) {
            throw new IllegalStateException("Must call PhenotypeFlag.init() first");
        }
        if (this.zzao.zzbc) {
            T tZze = zze();
            if (tZze != null) {
                return tZze;
            }
            T tZzd = zzd();
            if (tZzd != null) {
                return tZzd;
            }
        } else {
            T tZzd2 = zzd();
            if (tZzd2 != null) {
                return tZzd2;
            }
            T tZze2 = zze();
            if (tZze2 != null) {
                return tZze2;
            }
        }
        return this.zzar;
    }

    public abstract T zza(SharedPreferences sharedPreferences);

    public abstract T zza(String str);

    final /* synthetic */ String zzg() {
        return com.google.android.gms.internal.phenotype.zzf.zza(zzal.getContentResolver(), this.zzaq, (String) null);
    }
}
