package com.google.android.gms.measurement.internal;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.firebase.messaging.Constants;
import org.apache.commons.lang3.time.DateUtils;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzo {
    private final zzfu zza;

    public zzo(zzfu zzfuVar) {
        this.zza = zzfuVar;
    }

    final void zza() throws IllegalStateException {
        this.zza.zzp().zzc();
        if (zzd()) {
            if (zzc()) {
                this.zza.zzb().zzv.zza(null);
                Bundle bundle = new Bundle();
                bundle.putString("source", "(not set)");
                bundle.putString("medium", "(not set)");
                bundle.putString("_cis", "intent");
                bundle.putLong("_cc", 1L);
                this.zza.zzg().zza("auto", "_cmpx", bundle);
            } else {
                String strZza = this.zza.zzb().zzv.zza();
                if (TextUtils.isEmpty(strZza)) {
                    this.zza.zzq().zzf().zza("Cache still valid but referrer not found");
                } else {
                    long jZza = ((this.zza.zzb().zzw.zza() / DateUtils.MILLIS_PER_HOUR) - 1) * DateUtils.MILLIS_PER_HOUR;
                    Uri uri = Uri.parse(strZza);
                    Bundle bundle2 = new Bundle();
                    Pair pair = new Pair(uri.getPath(), bundle2);
                    for (String str : uri.getQueryParameterNames()) {
                        bundle2.putString(str, uri.getQueryParameter(str));
                    }
                    ((Bundle) pair.second).putLong("_cc", jZza);
                    this.zza.zzg().zza((String) pair.first, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, (Bundle) pair.second);
                }
                this.zza.zzb().zzv.zza(null);
            }
            this.zza.zzb().zzw.zza(0L);
        }
    }

    final void zza(String str, Bundle bundle) {
        String string;
        this.zza.zzp().zzc();
        if (this.zza.zzaa()) {
            return;
        }
        if (bundle == null || bundle.isEmpty()) {
            string = null;
        } else {
            if (str == null || str.isEmpty()) {
                str = "auto";
            }
            Uri.Builder builder = new Uri.Builder();
            builder.path(str);
            for (String str2 : bundle.keySet()) {
                builder.appendQueryParameter(str2, bundle.getString(str2));
            }
            string = builder.build().toString();
        }
        if (TextUtils.isEmpty(string)) {
            return;
        }
        this.zza.zzb().zzv.zza(string);
        this.zza.zzb().zzw.zza(this.zza.zzl().currentTimeMillis());
    }

    final void zzb() {
        if (zzd() && zzc()) {
            this.zza.zzb().zzv.zza(null);
        }
    }

    private final boolean zzc() {
        return zzd() && this.zza.zzl().currentTimeMillis() - this.zza.zzb().zzw.zza() > this.zza.zza().zza((String) null, zzas.zzcd);
    }

    private final boolean zzd() {
        return this.zza.zzb().zzw.zza() > 0;
    }
}
