package com.google.android.gms.measurement.internal;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.util.Clock;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzak extends zzgq {
    private long zza;
    private String zzb;
    private Boolean zzc;
    private AccountManager zzd;
    private Boolean zze;
    private long zzf;

    zzak(zzfu zzfuVar) {
        super(zzfuVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    protected final boolean zzd() {
        Calendar calendar = Calendar.getInstance();
        this.zza = TimeUnit.MINUTES.convert(calendar.get(15) + calendar.get(16), TimeUnit.MILLISECONDS);
        Locale locale = Locale.getDefault();
        String lowerCase = locale.getLanguage().toLowerCase(Locale.ENGLISH);
        String lowerCase2 = locale.getCountry().toLowerCase(Locale.ENGLISH);
        this.zzb = new StringBuilder(String.valueOf(lowerCase).length() + 1 + String.valueOf(lowerCase2).length()).append(lowerCase).append("-").append(lowerCase2).toString();
        return false;
    }

    public final long zze() {
        zzab();
        return this.zza;
    }

    public final String zzf() {
        zzab();
        return this.zzb;
    }

    public final boolean zza(Context context) throws PackageManager.NameNotFoundException {
        if (this.zzc == null) {
            this.zzc = false;
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    packageManager.getPackageInfo("com.google.android.gms", 128);
                    this.zzc = true;
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return this.zzc.booleanValue();
    }

    final long zzg() {
        zzc();
        return this.zzf;
    }

    final void zzh() {
        zzc();
        this.zze = null;
        this.zzf = 0L;
    }

    final boolean zzi() throws IllegalStateException, OperationCanceledException, IOException, AuthenticatorException {
        Account[] result;
        zzc();
        long jCurrentTimeMillis = zzl().currentTimeMillis();
        if (jCurrentTimeMillis - this.zzf > 86400000) {
            this.zze = null;
        }
        Boolean bool = this.zze;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (ContextCompat.checkSelfPermission(zzm(), "android.permission.GET_ACCOUNTS") != 0) {
            zzq().zzi().zza("Permission error checking for dasher/unicorn accounts");
            this.zzf = jCurrentTimeMillis;
            this.zze = false;
            return false;
        }
        if (this.zzd == null) {
            this.zzd = AccountManager.get(zzm());
        }
        try {
            result = this.zzd.getAccountsByTypeAndFeatures("com.google", new String[]{"service_HOSTED"}, null, null).getResult();
        } catch (AuthenticatorException | OperationCanceledException | IOException e) {
            zzq().zzf().zza("Exception checking account types", e);
        }
        if (result != null && result.length > 0) {
            this.zze = true;
            this.zzf = jCurrentTimeMillis;
            return true;
        }
        Account[] result2 = this.zzd.getAccountsByTypeAndFeatures("com.google", new String[]{"service_uca"}, null, null).getResult();
        if (result2 != null && result2.length > 0) {
            this.zze = true;
            this.zzf = jCurrentTimeMillis;
            return true;
        }
        this.zzf = jCurrentTimeMillis;
        this.zze = false;
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzak zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ Clock zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ Context zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzeo zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzkv zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzfr zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzeq zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzfc zzr() {
        return super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzab zzs() {
        return super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzw zzt() {
        return super.zzt();
    }
}
