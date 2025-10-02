package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.IOException;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzhs implements Callable<Boolean> {
    final /* synthetic */ ParcelFileDescriptor zza;
    final /* synthetic */ byte[] zzb;

    zzhs(zzhv zzhvVar, ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) {
        this.zza = parcelFileDescriptor;
        this.zzb = bArr;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Boolean call() throws Exception {
        if (Log.isLoggable("WearableClient", 3)) {
            String strValueOf = String.valueOf(this.zza);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 36);
            sb.append("processAssets: writing data to FD : ");
            sb.append(strValueOf);
            Log.d("WearableClient", sb.toString());
        }
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(this.zza);
        try {
            try {
                autoCloseOutputStream.write(this.zzb);
                autoCloseOutputStream.flush();
                if (Log.isLoggable("WearableClient", 3)) {
                    String strValueOf2 = String.valueOf(this.zza);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 27);
                    sb2.append("processAssets: wrote data: ");
                    sb2.append(strValueOf2);
                    Log.d("WearableClient", sb2.toString());
                }
                try {
                    if (Log.isLoggable("WearableClient", 3)) {
                        String strValueOf3 = String.valueOf(this.zza);
                        StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf3).length() + 24);
                        sb3.append("processAssets: closing: ");
                        sb3.append(strValueOf3);
                        Log.d("WearableClient", sb3.toString());
                    }
                    autoCloseOutputStream.close();
                    return true;
                } catch (IOException unused) {
                    return true;
                }
            } finally {
                try {
                    if (Log.isLoggable("WearableClient", 3)) {
                        String strValueOf4 = String.valueOf(this.zza);
                        StringBuilder sb4 = new StringBuilder(String.valueOf(strValueOf4).length() + 24);
                        sb4.append("processAssets: closing: ");
                        sb4.append(strValueOf4);
                        Log.d("WearableClient", sb4.toString());
                    }
                    autoCloseOutputStream.close();
                } catch (IOException unused2) {
                }
            }
        } catch (IOException unused3) {
            String strValueOf5 = String.valueOf(this.zza);
            StringBuilder sb5 = new StringBuilder(String.valueOf(strValueOf5).length() + 36);
            sb5.append("processAssets: writing data failed: ");
            sb5.append(strValueOf5);
            Log.w("WearableClient", sb5.toString());
            return false;
        }
    }
}
