package com.google.android.gms.internal.vision;

import android.content.Context;
import android.database.ContentObserver;
import android.util.Log;
import androidx.core.content.PermissionChecker;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzbd implements zzay {
    private static zzbd zzgb;

    @Nullable
    private final ContentObserver zzfr;

    @Nullable
    private final Context zzg;

    static zzbd zze(Context context) {
        zzbd zzbdVar;
        synchronized (zzbd.class) {
            if (zzgb == null) {
                zzgb = PermissionChecker.checkSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0 ? new zzbd(context) : new zzbd();
            }
            zzbdVar = zzgb;
        }
        return zzbdVar;
    }

    private zzbd(Context context) {
        this.zzg = context;
        zzbf zzbfVar = new zzbf(this, null);
        this.zzfr = zzbfVar;
        context.getContentResolver().registerContentObserver(zzaq.CONTENT_URI, true, zzbfVar);
    }

    private zzbd() {
        this.zzg = null;
        this.zzfr = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.vision.zzay
    /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public final String zzb(final String str) {
        if (this.zzg == null) {
            return null;
        }
        try {
            return (String) zzbb.zza(new zzba(this, str) { // from class: com.google.android.gms.internal.vision.zzbc
                private final zzbd zzfz;
                private final String zzga;

                {
                    this.zzfz = this;
                    this.zzga = str;
                }

                @Override // com.google.android.gms.internal.vision.zzba
                public final Object zzac() {
                    return this.zzfz.zzd(this.zzga);
                }
            });
        } catch (IllegalStateException | SecurityException e) {
            String strValueOf = String.valueOf(str);
            Log.e("GservicesLoader", strValueOf.length() != 0 ? "Unable to read GServices for: ".concat(strValueOf) : new String("Unable to read GServices for: "), e);
            return null;
        }
    }

    static synchronized void zzae() {
        Context context;
        zzbd zzbdVar = zzgb;
        if (zzbdVar != null && (context = zzbdVar.zzg) != null && zzbdVar.zzfr != null) {
            context.getContentResolver().unregisterContentObserver(zzgb.zzfr);
        }
        zzgb = null;
    }

    final /* synthetic */ String zzd(String str) {
        return zzaq.zza(this.zzg.getContentResolver(), str, (String) null);
    }
}
