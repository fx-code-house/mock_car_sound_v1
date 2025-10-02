package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzif implements Runnable {
    private final URL zza;
    private final byte[] zzb;
    private final zzic zzc;
    private final String zzd;
    private final Map<String, String> zze;
    private final /* synthetic */ zzid zzf;

    public zzif(zzid zzidVar, String str, URL url, byte[] bArr, Map<String, String> map, zzic zzicVar) {
        this.zzf = zzidVar;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(zzicVar);
        this.zza = url;
        this.zzb = null;
        this.zzc = zzicVar;
        this.zzd = str;
        this.zze = null;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        HttpURLConnection httpURLConnectionZza;
        Map<String, List<String>> headerFields;
        this.zzf.zzb();
        int responseCode = 0;
        try {
            httpURLConnectionZza = this.zzf.zza(this.zza);
            try {
                responseCode = httpURLConnectionZza.getResponseCode();
                headerFields = httpURLConnectionZza.getHeaderFields();
                try {
                    zzid zzidVar = this.zzf;
                    byte[] bArrZza = zzid.zza(httpURLConnectionZza);
                    if (httpURLConnectionZza != null) {
                        httpURLConnectionZza.disconnect();
                    }
                    zzb(responseCode, null, bArrZza, headerFields);
                } catch (IOException e) {
                    e = e;
                    if (httpURLConnectionZza != null) {
                        httpURLConnectionZza.disconnect();
                    }
                    zzb(responseCode, e, null, headerFields);
                } catch (Throwable th) {
                    th = th;
                    if (httpURLConnectionZza != null) {
                        httpURLConnectionZza.disconnect();
                    }
                    zzb(responseCode, null, null, headerFields);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                headerFields = null;
            } catch (Throwable th2) {
                th = th2;
                headerFields = null;
            }
        } catch (IOException e3) {
            e = e3;
            httpURLConnectionZza = null;
            headerFields = null;
        } catch (Throwable th3) {
            th = th3;
            httpURLConnectionZza = null;
            headerFields = null;
        }
    }

    private final void zzb(final int i, final Exception exc, final byte[] bArr, final Map<String, List<String>> map) throws IllegalStateException {
        this.zzf.zzp().zza(new Runnable(this, i, exc, bArr, map) { // from class: com.google.android.gms.measurement.internal.zzie
            private final zzif zza;
            private final int zzb;
            private final Exception zzc;
            private final byte[] zzd;
            private final Map zze;

            {
                this.zza = this;
                this.zzb = i;
                this.zzc = exc;
                this.zzd = bArr;
                this.zze = map;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zza(this.zzb, this.zzc, this.zzd, this.zze);
            }
        });
    }

    final /* synthetic */ void zza(int i, Exception exc, byte[] bArr, Map map) {
        this.zzc.zza(this.zzd, i, exc, bArr, map);
    }
}
