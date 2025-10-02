package com.google.android.gms.internal.icing;

import android.content.Context;
import android.database.ContentObserver;
import android.util.Log;
import androidx.core.content.PermissionChecker;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzbh implements zzbg {
    private static zzbh zzcr;

    @Nullable
    private final ContentObserver zzcl;

    @Nullable
    private final Context zzcs;

    static zzbh zzc(Context context) {
        zzbh zzbhVar;
        synchronized (zzbh.class) {
            if (zzcr == null) {
                zzcr = PermissionChecker.checkSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0 ? new zzbh(context) : new zzbh();
            }
            zzbhVar = zzcr;
        }
        return zzbhVar;
    }

    private zzbh(Context context) {
        this.zzcs = context;
        zzbj zzbjVar = new zzbj(this, null);
        this.zzcl = zzbjVar;
        context.getContentResolver().registerContentObserver(zzax.CONTENT_URI, true, zzbjVar);
    }

    private zzbh() {
        this.zzcs = null;
        this.zzcl = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.icing.zzbg
    /* renamed from: zzj, reason: merged with bridge method [inline-methods] */
    public final String zzi(final String str) {
        if (this.zzcs == null) {
            return null;
        }
        try {
            return (String) zzbf.zza(new zzbi(this, str) { // from class: com.google.android.gms.internal.icing.zzbk
                private final zzbh zzct;
                private final String zzcu;

                {
                    this.zzct = this;
                    this.zzcu = str;
                }

                @Override // com.google.android.gms.internal.icing.zzbi
                public final Object zzl() {
                    return this.zzct.zzk(this.zzcu);
                }
            });
        } catch (IllegalStateException | SecurityException e) {
            String strValueOf = String.valueOf(str);
            Log.e("GservicesLoader", strValueOf.length() != 0 ? "Unable to read GServices for: ".concat(strValueOf) : new String("Unable to read GServices for: "), e);
            return null;
        }
    }

    static synchronized void zzs() {
        Context context;
        zzbh zzbhVar = zzcr;
        if (zzbhVar != null && (context = zzbhVar.zzcs) != null && zzbhVar.zzcl != null) {
            context.getContentResolver().unregisterContentObserver(zzcr.zzcl);
        }
        zzcr = null;
    }

    final /* synthetic */ String zzk(String str) {
        return zzax.zza(this.zzcs.getContentResolver(), str, (String) null);
    }
}
