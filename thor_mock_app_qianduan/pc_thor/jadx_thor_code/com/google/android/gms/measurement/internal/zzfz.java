package com.google.android.gms.measurement.internal;

import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.internal.measurement.zzml;
import com.google.android.gms.internal.measurement.zznw;
import com.google.firebase.messaging.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzfz extends zzel {
    private final zzkl zza;
    private Boolean zzb;
    private String zzc;

    public zzfz(zzkl zzklVar) {
        this(zzklVar, null);
    }

    private zzfz(zzkl zzklVar, String str) {
        Preconditions.checkNotNull(zzklVar);
        this.zza = zzklVar;
        this.zzc = null;
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zzb(zzn zznVar) throws IllegalStateException {
        zzb(zznVar, false);
        zza(new zzgb(this, zznVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zze(zzn zznVar) throws IllegalStateException {
        if (zzml.zzb() && this.zza.zzb().zza(zzas.zzci)) {
            Preconditions.checkNotEmpty(zznVar.zza);
            Preconditions.checkNotNull(zznVar.zzw);
            zzgj zzgjVar = new zzgj(this, zznVar);
            Preconditions.checkNotNull(zzgjVar);
            if (this.zza.zzp().zzf()) {
                zzgjVar.run();
            } else {
                this.zza.zzp().zzb(zzgjVar);
            }
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzaq zzaqVar, zzn zznVar) throws IllegalStateException {
        Preconditions.checkNotNull(zzaqVar);
        zzb(zznVar, false);
        zza(new zzgi(this, zzaqVar, zznVar));
    }

    final zzaq zzb(zzaq zzaqVar, zzn zznVar) throws IllegalStateException {
        boolean z = false;
        if (Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(zzaqVar.zza) && zzaqVar.zzb != null && zzaqVar.zzb.zza() != 0) {
            String strZzd = zzaqVar.zzb.zzd("_cis");
            if ("referrer broadcast".equals(strZzd) || "referrer API".equals(strZzd)) {
                z = true;
            }
        }
        if (!z) {
            return zzaqVar;
        }
        this.zza.zzq().zzu().zza("Event has been filtered ", zzaqVar.toString());
        return new zzaq("_cmpx", zzaqVar.zzb, zzaqVar.zzc, zzaqVar.zzd);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzaq zzaqVar, String str, String str2) throws IllegalStateException {
        Preconditions.checkNotNull(zzaqVar);
        Preconditions.checkNotEmpty(str);
        zza(str, true);
        zza(new zzgl(this, zzaqVar, str));
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final byte[] zza(zzaq zzaqVar, String str) throws IllegalStateException {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzaqVar);
        zza(str, true);
        this.zza.zzq().zzv().zza("Log and bundle. event", this.zza.zzj().zza(zzaqVar.zza));
        long jNanoTime = this.zza.zzl().nanoTime() / 1000000;
        try {
            byte[] bArr = (byte[]) this.zza.zzp().zzb(new zzgk(this, zzaqVar, str)).get();
            if (bArr == null) {
                this.zza.zzq().zze().zza("Log and bundle returned null. appId", zzeq.zza(str));
                bArr = new byte[0];
            }
            this.zza.zzq().zzv().zza("Log and bundle processed. event, size, time_ms", this.zza.zzj().zza(zzaqVar.zza), Integer.valueOf(bArr.length), Long.valueOf((this.zza.zzl().nanoTime() / 1000000) - jNanoTime));
            return bArr;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzq().zze().zza("Failed to log and bundle. appId, event, error", zzeq.zza(str), this.zza.zzj().zza(zzaqVar.zza), e);
            return null;
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzku zzkuVar, zzn zznVar) throws IllegalStateException {
        Preconditions.checkNotNull(zzkuVar);
        zzb(zznVar, false);
        zza(new zzgn(this, zzkuVar, zznVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final List<zzku> zza(zzn zznVar, boolean z) throws IllegalStateException {
        zzb(zznVar, false);
        try {
            List<zzkw> list = (List) this.zza.zzp().zza(new zzgm(this, zznVar)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzkw zzkwVar : list) {
                if (z || !zzkv.zzd(zzkwVar.zzc)) {
                    arrayList.add(new zzku(zzkwVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzq().zze().zza("Failed to get user properties. appId", zzeq.zza(zznVar.zza), e);
            return null;
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzn zznVar) throws IllegalStateException {
        zzb(zznVar, false);
        zza(new zzgp(this, zznVar));
    }

    private final void zzb(zzn zznVar, boolean z) throws IllegalStateException {
        Preconditions.checkNotNull(zznVar);
        zza(zznVar.zza, false);
        this.zza.zzk().zza(zznVar.zzb, zznVar.zzr, zznVar.zzv);
    }

    private final void zza(String str, boolean z) throws IllegalStateException {
        if (TextUtils.isEmpty(str)) {
            this.zza.zzq().zze().zza("Measurement Service called without app package");
            throw new SecurityException("Measurement Service called without app package");
        }
        if (z) {
            try {
                if (this.zzb == null) {
                    this.zzb = Boolean.valueOf("com.google.android.gms".equals(this.zzc) || UidVerifier.isGooglePlayServicesUid(this.zza.zzm(), Binder.getCallingUid()) || GoogleSignatureVerifier.getInstance(this.zza.zzm()).isUidGoogleSigned(Binder.getCallingUid()));
                }
                if (this.zzb.booleanValue()) {
                    return;
                }
            } catch (SecurityException e) {
                this.zza.zzq().zze().zza("Measurement Service called with invalid calling package. appId", zzeq.zza(str));
                throw e;
            }
        }
        if (this.zzc == null && GooglePlayServicesUtilLight.uidHasPackageName(this.zza.zzm(), Binder.getCallingUid(), str)) {
            this.zzc = str;
        }
        if (str.equals(this.zzc)) {
        } else {
            throw new SecurityException(String.format("Unknown calling package name '%s'.", str));
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(long j, String str, String str2, String str3) throws IllegalStateException {
        zza(new zzgo(this, str2, str3, str, j));
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(final Bundle bundle, final zzn zznVar) throws IllegalStateException {
        if (zznw.zzb() && this.zza.zzb().zza(zzas.zzbz)) {
            zzb(zznVar, false);
            zza(new Runnable(this, zznVar, bundle) { // from class: com.google.android.gms.measurement.internal.zzfy
                private final zzfz zza;
                private final zzn zzb;
                private final Bundle zzc;

                {
                    this.zza = this;
                    this.zzb = zznVar;
                    this.zzc = bundle;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    this.zza.zza(this.zzb, this.zzc);
                }
            });
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final String zzc(zzn zznVar) throws IllegalStateException {
        zzb(zznVar, false);
        return this.zza.zzd(zznVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzz zzzVar, zzn zznVar) throws IllegalStateException {
        Preconditions.checkNotNull(zzzVar);
        Preconditions.checkNotNull(zzzVar.zzc);
        zzb(zznVar, false);
        zzz zzzVar2 = new zzz(zzzVar);
        zzzVar2.zza = zznVar.zza;
        zza(new zzga(this, zzzVar2, zznVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzz zzzVar) throws IllegalStateException {
        Preconditions.checkNotNull(zzzVar);
        Preconditions.checkNotNull(zzzVar.zzc);
        zza(zzzVar.zza, true);
        zza(new zzgd(this, new zzz(zzzVar)));
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final List<zzku> zza(String str, String str2, boolean z, zzn zznVar) throws IllegalStateException {
        zzb(zznVar, false);
        try {
            List<zzkw> list = (List) this.zza.zzp().zza(new zzgc(this, zznVar, str, str2)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzkw zzkwVar : list) {
                if (z || !zzkv.zzd(zzkwVar.zzc)) {
                    arrayList.add(new zzku(zzkwVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzq().zze().zza("Failed to query user properties. appId", zzeq.zza(zznVar.zza), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final List<zzku> zza(String str, String str2, String str3, boolean z) throws IllegalStateException {
        zza(str, true);
        try {
            List<zzkw> list = (List) this.zza.zzp().zza(new zzgf(this, str, str2, str3)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzkw zzkwVar : list) {
                if (z || !zzkv.zzd(zzkwVar.zzc)) {
                    arrayList.add(new zzku(zzkwVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzq().zze().zza("Failed to get user properties as. appId", zzeq.zza(str), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final List<zzz> zza(String str, String str2, zzn zznVar) throws IllegalStateException {
        zzb(zznVar, false);
        try {
            return (List) this.zza.zzp().zza(new zzge(this, zznVar, str, str2)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzq().zze().zza("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final List<zzz> zza(String str, String str2, String str3) throws IllegalStateException {
        zza(str, true);
        try {
            return (List) this.zza.zzp().zza(new zzgh(this, str, str2, str3)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzq().zze().zza("Failed to get conditional user properties as", e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zzd(zzn zznVar) throws IllegalStateException {
        zza(zznVar.zza, false);
        zza(new zzgg(this, zznVar));
    }

    private final void zza(Runnable runnable) throws IllegalStateException {
        Preconditions.checkNotNull(runnable);
        if (this.zza.zzp().zzf()) {
            runnable.run();
        } else {
            this.zza.zzp().zza(runnable);
        }
    }

    final /* synthetic */ void zza(zzn zznVar, Bundle bundle) {
        this.zza.zze().zza(zznVar.zza, bundle);
    }
}
