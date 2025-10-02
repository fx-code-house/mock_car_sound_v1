package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzex<T> {
    private final Map<T, zzia<T>> zza = new HashMap();

    zzex() {
    }

    public final void zza(IBinder iBinder) {
        zzeu zzeuVar;
        synchronized (this.zza) {
            if (iBinder == null) {
                zzeuVar = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableService");
                zzeuVar = iInterfaceQueryLocalInterface instanceof zzeu ? (zzeu) iInterfaceQueryLocalInterface : new zzeu(iBinder);
            }
            zzhl zzhlVar = new zzhl();
            for (Map.Entry<T, zzia<T>> entry : this.zza.entrySet()) {
                zzia<T> value = entry.getValue();
                try {
                    zzeuVar.zzs(zzhlVar, new zzd(value));
                    if (Log.isLoggable("WearableClient", 3)) {
                        String strValueOf = String.valueOf(entry.getKey());
                        String strValueOf2 = String.valueOf(value);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 27 + String.valueOf(strValueOf2).length());
                        sb.append("onPostInitHandler: added: ");
                        sb.append(strValueOf);
                        sb.append("/");
                        sb.append(strValueOf2);
                        Log.d("WearableClient", sb.toString());
                    }
                } catch (RemoteException unused) {
                    String strValueOf3 = String.valueOf(entry.getKey());
                    String strValueOf4 = String.valueOf(value);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf3).length() + 32 + String.valueOf(strValueOf4).length());
                    sb2.append("onPostInitHandler: Didn't add: ");
                    sb2.append(strValueOf3);
                    sb2.append("/");
                    sb2.append(strValueOf4);
                    Log.w("WearableClient", sb2.toString());
                }
            }
        }
    }

    public final void zzb(zzhv zzhvVar, BaseImplementation.ResultHolder<Status> resultHolder, T t, zzia<T> zziaVar) throws RemoteException {
        synchronized (this.zza) {
            if (this.zza.get(t) != null) {
                if (Log.isLoggable("WearableClient", 2)) {
                    String strValueOf = String.valueOf(t);
                    StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 20);
                    sb.append("duplicate listener: ");
                    sb.append(strValueOf);
                    Log.v("WearableClient", sb.toString());
                }
                resultHolder.setResult(new Status(4001));
                return;
            }
            if (Log.isLoggable("WearableClient", 2)) {
                String strValueOf2 = String.valueOf(t);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 14);
                sb2.append("new listener: ");
                sb2.append(strValueOf2);
                Log.v("WearableClient", sb2.toString());
            }
            this.zza.put(t, zziaVar);
            try {
                ((zzeu) zzhvVar.getService()).zzs(new zzev(this.zza, t, resultHolder), new zzd(zziaVar));
            } catch (RemoteException e) {
                if (Log.isLoggable("WearableClient", 3)) {
                    String strValueOf3 = String.valueOf(t);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf3).length() + 39);
                    sb3.append("addListener failed, removing listener: ");
                    sb3.append(strValueOf3);
                    Log.d("WearableClient", sb3.toString());
                }
                this.zza.remove(t);
                throw e;
            }
        }
    }

    public final void zzc(zzhv zzhvVar, BaseImplementation.ResultHolder<Status> resultHolder, T t) throws RemoteException {
        synchronized (this.zza) {
            zzia<T> zziaVarRemove = this.zza.remove(t);
            if (zziaVarRemove == null) {
                if (Log.isLoggable("WearableClient", 2)) {
                    String strValueOf = String.valueOf(t);
                    StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 25);
                    sb.append("remove Listener unknown: ");
                    sb.append(strValueOf);
                    Log.v("WearableClient", sb.toString());
                }
                resultHolder.setResult(new Status(4002));
                return;
            }
            zziaVarRemove.zzq();
            if (Log.isLoggable("WearableClient", 2)) {
                String strValueOf2 = String.valueOf(t);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 24);
                sb2.append("service.removeListener: ");
                sb2.append(strValueOf2);
                Log.v("WearableClient", sb2.toString());
            }
            ((zzeu) zzhvVar.getService()).zzt(new zzew(this.zza, t, resultHolder), new zzgg(zziaVarRemove));
        }
    }
}
