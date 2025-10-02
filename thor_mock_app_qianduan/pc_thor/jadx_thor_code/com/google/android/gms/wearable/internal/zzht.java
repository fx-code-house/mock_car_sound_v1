package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzht implements Runnable {
    final /* synthetic */ Uri zza;
    final /* synthetic */ BaseImplementation.ResultHolder zzb;
    final /* synthetic */ boolean zzc;
    final /* synthetic */ String zzd;
    final /* synthetic */ zzhv zze;

    zzht(zzhv zzhvVar, Uri uri, BaseImplementation.ResultHolder resultHolder, boolean z, String str) {
        this.zze = zzhvVar;
        this.zza = uri;
        this.zzb = resultHolder;
        this.zzc = z;
        this.zzd = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v14, types: [android.os.ParcelFileDescriptor] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.File, java.lang.Object] */
    @Override // java.lang.Runnable
    public final void run() throws IOException {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "Executing receiveFileFromChannelTask");
        }
        if (!"file".equals(this.zza.getScheme())) {
            Log.w("WearableClient", "Channel.receiveFile used with non-file URI");
            this.zzb.setFailedResult(new Status(10, "Channel.receiveFile used with non-file URI"));
            return;
        }
        ParcelFileDescriptor file = new File(this.zza.getPath());
        try {
            try {
                file = ParcelFileDescriptor.open(file, (true != this.zzc ? 0 : 33554432) | 671088640);
                try {
                    ((zzeu) this.zze.getService()).zzz(new zzhr(this.zzb), this.zzd, file);
                } catch (RemoteException e) {
                    Log.w("WearableClient", "Channel.receiveFile failed.", e);
                    this.zzb.setFailedResult(new Status(8));
                    try {
                        file.close();
                    } catch (IOException e2) {
                        Log.w("WearableClient", "Failed to close targetFd", e2);
                    }
                }
            } catch (FileNotFoundException unused) {
                String strValueOf = String.valueOf((Object) file);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 49);
                sb.append("File couldn't be opened for Channel.receiveFile: ");
                sb.append(strValueOf);
                Log.w("WearableClient", sb.toString());
                this.zzb.setFailedResult(new Status(13));
            }
        } finally {
            try {
                file.close();
            } catch (IOException e3) {
                Log.w("WearableClient", "Failed to close targetFd", e3);
            }
        }
    }
}
