package com.google.android.gms.wearable;

import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.internal.zzag;
import com.google.android.gms.wearable.internal.zzax;
import com.google.android.gms.wearable.internal.zzeo;
import com.google.android.gms.wearable.internal.zzes;
import com.google.android.gms.wearable.internal.zzfj;
import com.google.android.gms.wearable.internal.zzfw;
import com.google.android.gms.wearable.internal.zzib;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzx extends zzes {
    final /* synthetic */ WearableListenerService zza;
    private volatile int zzb = -1;

    /* synthetic */ zzx(WearableListenerService wearableListenerService, zzi zziVar) {
        this.zza = wearableListenerService;
    }

    static final /* synthetic */ void zzm(zzeo zzeoVar, Task task) {
        if (task.isSuccessful()) {
            zzo(zzeoVar, true, (byte[]) task.getResult());
        } else {
            Log.e("WearableLS", "Failed to resolve future, sending null response", task.getException());
            zzo(zzeoVar, false, null);
        }
    }

    private final boolean zzn(Runnable runnable, String str, Object obj) {
        if (Log.isLoggable("WearableLS", 3)) {
            Log.d("WearableLS", String.format("%s: %s %s", str, this.zza.zza.toString(), obj));
        }
        int callingUid = Binder.getCallingUid();
        if (callingUid != this.zzb) {
            if ((!zzib.zza(this.zza).zzb("com.google.android.wearable.app.cn") || !UidVerifier.uidHasPackageName(this.zza, callingUid, "com.google.android.wearable.app.cn")) && !UidVerifier.isGooglePlayServicesUid(this.zza, callingUid)) {
                StringBuilder sb = new StringBuilder(57);
                sb.append("Caller is not GooglePlayServices; caller UID: ");
                sb.append(callingUid);
                Log.e("WearableLS", sb.toString());
                return false;
            }
            this.zzb = callingUid;
        }
        synchronized (this.zza.zzf) {
            if (this.zza.zzg) {
                return false;
            }
            this.zza.zzb.post(runnable);
            return true;
        }
    }

    private static final void zzo(zzeo zzeoVar, boolean z, byte[] bArr) {
        try {
            zzeoVar.zzd(z, bArr);
        } catch (RemoteException e) {
            Log.e("WearableLS", "Failed to send a response back", e);
        }
    }

    @Override // com.google.android.gms.wearable.internal.zzet
    public final void zzb(DataHolder dataHolder) {
        zzo zzoVar = new zzo(this, dataHolder);
        try {
            String strValueOf = String.valueOf(dataHolder);
            int count = dataHolder.getCount();
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 18);
            sb.append(strValueOf);
            sb.append(", rows=");
            sb.append(count);
            if (zzn(zzoVar, "onDataItemChanged", sb.toString())) {
            }
        } finally {
            dataHolder.close();
        }
    }

    @Override // com.google.android.gms.wearable.internal.zzet
    public final void zzc(zzfj zzfjVar) {
        zzn(new zzp(this, zzfjVar), "onMessageReceived", zzfjVar);
    }

    @Override // com.google.android.gms.wearable.internal.zzet
    public final void zzd(zzfw zzfwVar) {
        zzn(new zzq(this, zzfwVar), "onPeerConnected", zzfwVar);
    }

    @Override // com.google.android.gms.wearable.internal.zzet
    public final void zze(zzfw zzfwVar) {
        zzn(new zzr(this, zzfwVar), "onPeerDisconnected", zzfwVar);
    }

    @Override // com.google.android.gms.wearable.internal.zzet
    public final void zzf(List<zzfw> list) {
        zzn(new zzs(this, list), "onConnectedNodes", list);
    }

    @Override // com.google.android.gms.wearable.internal.zzet
    public final void zzg(zzag zzagVar) {
        zzn(new zzt(this, zzagVar), "onConnectedCapabilityChanged", zzagVar);
    }

    @Override // com.google.android.gms.wearable.internal.zzet
    public final void zzh(com.google.android.gms.wearable.internal.zzl zzlVar) {
        zzn(new zzu(this, zzlVar), "onNotificationReceived", zzlVar);
    }

    @Override // com.google.android.gms.wearable.internal.zzet
    public final void zzi(com.google.android.gms.wearable.internal.zzi zziVar) {
        zzn(new zzv(this, zziVar), "onEntityUpdate", zziVar);
    }

    @Override // com.google.android.gms.wearable.internal.zzet
    public final void zzj(zzax zzaxVar) {
        zzn(new zzw(this, zzaxVar), "onChannelEvent", zzaxVar);
    }

    @Override // com.google.android.gms.wearable.internal.zzet
    public final void zzk(final zzfj zzfjVar, final zzeo zzeoVar) {
        final byte[] bArr = null;
        zzn(new Runnable(this, zzfjVar, zzeoVar, bArr) { // from class: com.google.android.gms.wearable.zzm
            private final zzx zza;
            private final zzfj zzb;
            private final zzeo zzc;

            {
                this.zza = this;
                this.zzb = zzfjVar;
                this.zzc = zzeoVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzl(this.zzb, this.zzc);
            }
        }, "onRequestReceived", zzfjVar);
    }

    final /* synthetic */ void zzl(zzfj zzfjVar, final zzeo zzeoVar) {
        Task<byte[]> taskOnRequest = this.zza.onRequest(zzfjVar.getSourceNodeId(), zzfjVar.getPath(), zzfjVar.getData());
        final byte[] bArr = null;
        if (taskOnRequest == null) {
            zzo(zzeoVar, false, null);
        } else {
            taskOnRequest.addOnCompleteListener(new OnCompleteListener(this, zzeoVar, bArr) { // from class: com.google.android.gms.wearable.zzn
                private final zzx zza;
                private final zzeo zzb;

                {
                    this.zza = this;
                    this.zzb = zzeoVar;
                }

                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    zzx.zzm(this.zzb, task);
                }
            });
        }
    }
}
