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
final class zzhu implements Runnable {
    final /* synthetic */ Uri zza;
    final /* synthetic */ BaseImplementation.ResultHolder zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ long zzd;
    final /* synthetic */ long zze;
    final /* synthetic */ zzhv zzf;

    zzhu(zzhv zzhvVar, Uri uri, BaseImplementation.ResultHolder resultHolder, String str, long j, long j2) {
        this.zzf = zzhvVar;
        this.zza = uri;
        this.zzb = resultHolder;
        this.zzc = str;
        this.zzd = j;
        this.zze = j2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public final void run() throws IOException {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "Executing sendFileToChannelTask");
        }
        if (!"file".equals(this.zza.getScheme())) {
            Log.w("WearableClient", "Channel.sendFile used with non-file URI");
            this.zzb.setFailedResult(new Status(10, "Channel.sendFile used with non-file URI"));
            return;
        }
        File file = new File(this.zza.getPath());
        try {
            ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 268435456);
            try {
                try {
                    ((zzeu) this.zzf.getService()).zzA(new zzho(this.zzb), this.zzc, parcelFileDescriptorOpen, this.zzd, this.zze);
                } finally {
                    try {
                        parcelFileDescriptorOpen.close();
                    } catch (IOException e) {
                        Log.w("WearableClient", "Failed to close sourceFd", e);
                    }
                }
            } catch (RemoteException e2) {
                Log.w("WearableClient", "Channel.sendFile failed.", e2);
                this.zzb.setFailedResult(new Status(8));
                try {
                    parcelFileDescriptorOpen.close();
                } catch (IOException e3) {
                    Log.w("WearableClient", "Failed to close sourceFd", e3);
                }
            }
        } catch (FileNotFoundException unused) {
            String strValueOf = String.valueOf(file);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 46);
            sb.append("File couldn't be opened for Channel.sendFile: ");
            sb.append(strValueOf);
            Log.w("WearableClient", sb.toString());
            this.zzb.setFailedResult(new Status(13));
        }
    }
}
