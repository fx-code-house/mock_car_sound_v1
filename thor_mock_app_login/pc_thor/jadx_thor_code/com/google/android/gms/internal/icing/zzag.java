package com.google.android.gms.internal.icing;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.internal.icing.zzhm;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzag {
    public static zzw zza(Action action, long j, String str, int i) {
        int i2;
        Bundle bundle = new Bundle();
        bundle.putAll(action.zze());
        Bundle bundle2 = bundle.getBundle("object");
        Uri uri = bundle2.containsKey(TtmlNode.ATTR_ID) ? Uri.parse(bundle2.getString(TtmlNode.ATTR_ID)) : null;
        String string = bundle2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
        String string2 = bundle2.getString(SessionDescription.ATTR_TYPE);
        Intent intentZza = zzaj.zza(str, Uri.parse(bundle2.getString(ImagesContract.URL)));
        zzg zzgVarZza = zzw.zza(intentZza, string, uri, string2, null);
        if (bundle.containsKey(".private:ssbContext")) {
            zzgVarZza.zza(zzk.zza(bundle.getByteArray(".private:ssbContext")));
            bundle.remove(".private:ssbContext");
        }
        if (bundle.containsKey(".private:accountName")) {
            zzgVarZza.zza(new Account(bundle.getString(".private:accountName"), "com.google"));
            bundle.remove(".private:accountName");
        }
        boolean z = false;
        if (bundle.containsKey(".private:isContextOnly") && bundle.getBoolean(".private:isContextOnly")) {
            bundle.remove(".private:isContextOnly");
            i2 = 4;
        } else {
            i2 = 0;
        }
        if (bundle.containsKey(".private:isDeviceOnly")) {
            z = bundle.getBoolean(".private:isDeviceOnly", false);
            bundle.remove(".private:isDeviceOnly");
        }
        zzgVarZza.zza(new zzk(zza(bundle).toByteArray(), new zzs(".private:action").zzb(true).zzd(".private:action").zzc("blob").zzc()));
        return new zzz().zza(zzw.zza(str, intentZza)).zza(j).zzb(i2).zza(zzgVarZza.zzb()).zzd(z).zzc(i).zzd();
    }

    private static zzhm.zzb zza(Bundle bundle) {
        zzhm.zzb.zza zzaVarZzee = zzhm.zzb.zzee();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof String) {
                zzaVarZzee.zzb((zzhm.zza) ((zzdx) zzhm.zza.zzec().zzu(str).zzb((zzhm.zzc) ((zzdx) zzhm.zzc.zzeg().zzx((String) obj).zzbx())).zzbx()));
            } else if (obj instanceof Bundle) {
                zzaVarZzee.zzb((zzhm.zza) ((zzdx) zzhm.zza.zzec().zzu(str).zzb((zzhm.zzc) ((zzdx) zzhm.zzc.zzeg().zzb(zza((Bundle) obj)).zzbx())).zzbx()));
            } else {
                int i = 0;
                if (obj instanceof String[]) {
                    String[] strArr = (String[]) obj;
                    int length = strArr.length;
                    while (i < length) {
                        String str2 = strArr[i];
                        if (str2 != null) {
                            zzaVarZzee.zzb((zzhm.zza) ((zzdx) zzhm.zza.zzec().zzu(str).zzb((zzhm.zzc) ((zzdx) zzhm.zzc.zzeg().zzx(str2).zzbx())).zzbx()));
                        }
                        i++;
                    }
                } else if (obj instanceof Bundle[]) {
                    Bundle[] bundleArr = (Bundle[]) obj;
                    int length2 = bundleArr.length;
                    while (i < length2) {
                        Bundle bundle2 = bundleArr[i];
                        if (bundle2 != null) {
                            zzaVarZzee.zzb((zzhm.zza) ((zzdx) zzhm.zza.zzec().zzu(str).zzb((zzhm.zzc) ((zzdx) zzhm.zzc.zzeg().zzb(zza(bundle2)).zzbx())).zzbx()));
                        }
                        i++;
                    }
                } else if (obj instanceof Boolean) {
                    zzaVarZzee.zzb((zzhm.zza) ((zzdx) zzhm.zza.zzec().zzu(str).zzb((zzhm.zzc) ((zzdx) zzhm.zzc.zzeg().zzj(((Boolean) obj).booleanValue()).zzbx())).zzbx()));
                } else {
                    String strValueOf = String.valueOf(obj);
                    Log.e("SearchIndex", new StringBuilder(String.valueOf(strValueOf).length() + 19).append("Unsupported value: ").append(strValueOf).toString());
                }
            }
        }
        if (bundle.containsKey(SessionDescription.ATTR_TYPE)) {
            zzaVarZzee.zzw(bundle.getString(SessionDescription.ATTR_TYPE));
        }
        return (zzhm.zzb) ((zzdx) zzaVarZzee.zzbx());
    }
}
