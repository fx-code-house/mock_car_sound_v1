package com.google.android.gms.internal.vision;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.vision.L;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class zzs<T> {
    private final String tag;
    private final String zzdm;
    private final String zzdn;
    private T zzdq;
    private final Context zzg;
    private final Object lock = new Object();
    private boolean zzdo = false;
    private boolean zzdp = false;

    public zzs(Context context, String str, String str2) {
        this.zzg = context;
        this.tag = str;
        String strValueOf = String.valueOf(str2);
        this.zzdm = strValueOf.length() != 0 ? "com.google.android.gms.vision.dynamite.".concat(strValueOf) : new String("com.google.android.gms.vision.dynamite.");
        this.zzdn = str2;
    }

    protected abstract T zza(DynamiteModule dynamiteModule, Context context) throws RemoteException, DynamiteModule.LoadingException;

    protected abstract void zzp() throws RemoteException;

    public final boolean isOperational() {
        return zzr() != null;
    }

    public final void zzq() {
        synchronized (this.lock) {
            if (this.zzdq == null) {
                return;
            }
            try {
                zzp();
            } catch (RemoteException e) {
                Log.e(this.tag, "Could not finalize native handle", e);
            }
        }
    }

    protected final T zzr() {
        DynamiteModule dynamiteModuleLoad;
        synchronized (this.lock) {
            T t = this.zzdq;
            if (t != null) {
                return t;
            }
            try {
                dynamiteModuleLoad = DynamiteModule.load(this.zzg, DynamiteModule.PREFER_HIGHEST_OR_REMOTE_VERSION, this.zzdm);
            } catch (DynamiteModule.LoadingException unused) {
                String str = String.format("%s.%s", "com.google.android.gms.vision", this.zzdn);
                L.d("Cannot load thick client module, fall back to load optional module %s", str);
                try {
                    dynamiteModuleLoad = DynamiteModule.load(this.zzg, DynamiteModule.PREFER_REMOTE, str);
                } catch (DynamiteModule.LoadingException e) {
                    L.e(e, "Error loading optional module %s", str);
                    if (!this.zzdo) {
                        L.d("Broadcasting download intent for dependency %s", this.zzdn);
                        String str2 = this.zzdn;
                        Intent intent = new Intent();
                        intent.setClassName("com.google.android.gms", "com.google.android.gms.vision.DependencyBroadcastReceiverProxy");
                        intent.putExtra("com.google.android.gms.vision.DEPENDENCIES", str2);
                        intent.setAction("com.google.android.gms.vision.DEPENDENCY");
                        this.zzg.sendBroadcast(intent);
                        this.zzdo = true;
                    }
                    dynamiteModuleLoad = null;
                }
            }
            if (dynamiteModuleLoad != null) {
                try {
                    this.zzdq = zza(dynamiteModuleLoad, this.zzg);
                } catch (RemoteException | DynamiteModule.LoadingException e2) {
                    Log.e(this.tag, "Error creating remote native handle", e2);
                }
            }
            boolean z = this.zzdp;
            if (!z && this.zzdq == null) {
                Log.w(this.tag, "Native handle not yet available. Reverting to no-op handle.");
                this.zzdp = true;
            } else if (z && this.zzdq != null) {
                Log.w(this.tag, "Native handle is now available.");
            }
            return this.zzdq;
        }
    }
}
