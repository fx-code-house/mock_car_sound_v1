package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;

/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes2.dex */
final class zaal implements BaseGmsClient.ConnectionProgressReportCallbacks {
    private final WeakReference<zaaw> zaa;
    private final Api<?> zab;
    private final boolean zac;

    public zaal(zaaw zaawVar, Api<?> api, boolean z) {
        this.zaa = new WeakReference<>(zaawVar);
        this.zab = api;
        this.zac = z;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
    public final void onReportServiceBinding(ConnectionResult connectionResult) {
        Lock lock;
        zaaw zaawVar = this.zaa.get();
        if (zaawVar == null) {
            return;
        }
        Preconditions.checkState(Looper.myLooper() == zaawVar.zaa.zag.getLooper(), "onReportServiceBinding must be called on the GoogleApiClient handler thread");
        zaawVar.zab.lock();
        try {
            if (zaawVar.zaG(0)) {
                if (!connectionResult.isSuccess()) {
                    zaawVar.zaE(connectionResult, this.zab, this.zac);
                }
                if (zaawVar.zaH()) {
                    zaawVar.zaF();
                }
                lock = zaawVar.zab;
            } else {
                lock = zaawVar.zab;
            }
            lock.unlock();
        } catch (Throwable th) {
            zaawVar.zab.unlock();
            throw th;
        }
    }
}
