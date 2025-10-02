package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzjl implements ServiceConnection, BaseGmsClient.BaseConnectionCallbacks, BaseGmsClient.BaseOnConnectionFailedListener {
    final /* synthetic */ zzir zza;
    private volatile boolean zzb;
    private volatile zzer zzc;

    protected zzjl(zzir zzirVar) {
        this.zza = zzirVar;
    }

    public final void zza(Intent intent) {
        this.zza.zzc();
        Context contextZzm = this.zza.zzm();
        ConnectionTracker connectionTracker = ConnectionTracker.getInstance();
        synchronized (this) {
            if (this.zzb) {
                this.zza.zzq().zzw().zza("Connection attempt already in progress");
                return;
            }
            this.zza.zzq().zzw().zza("Using local app measurement service");
            this.zzb = true;
            connectionTracker.bindService(contextZzm, intent, this.zza.zza, 129);
        }
    }

    public final void zza() {
        if (this.zzc != null && (this.zzc.isConnected() || this.zzc.isConnecting())) {
            this.zzc.disconnect();
        }
        this.zzc = null;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzei zzekVar;
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceConnected");
        synchronized (this) {
            if (iBinder == null) {
                this.zzb = false;
                this.zza.zzq().zze().zza("Service connected with null binder");
                return;
            }
            zzei zzeiVar = null;
            try {
                String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                if ("com.google.android.gms.measurement.internal.IMeasurementService".equals(interfaceDescriptor)) {
                    if (iBinder != null) {
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
                        if (iInterfaceQueryLocalInterface instanceof zzei) {
                            zzekVar = (zzei) iInterfaceQueryLocalInterface;
                        } else {
                            zzekVar = new zzek(iBinder);
                        }
                        zzeiVar = zzekVar;
                    }
                    this.zza.zzq().zzw().zza("Bound to IMeasurementService interface");
                } else {
                    this.zza.zzq().zze().zza("Got binder with a wrong descriptor", interfaceDescriptor);
                }
            } catch (RemoteException unused) {
                this.zza.zzq().zze().zza("Service connect failed to get IMeasurementService");
            }
            if (zzeiVar == null) {
                this.zzb = false;
                try {
                    ConnectionTracker.getInstance().unbindService(this.zza.zzm(), this.zza.zza);
                } catch (IllegalArgumentException unused2) {
                }
            } else {
                this.zza.zzp().zza(new zzjk(this, zzeiVar));
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) throws IllegalStateException {
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceDisconnected");
        this.zza.zzq().zzv().zza("Service disconnected");
        this.zza.zzp().zza(new zzjn(this, componentName));
    }

    public final void zzb() {
        this.zza.zzc();
        Context contextZzm = this.zza.zzm();
        synchronized (this) {
            if (this.zzb) {
                this.zza.zzq().zzw().zza("Connection attempt already in progress");
                return;
            }
            if (this.zzc != null && (this.zzc.isConnecting() || this.zzc.isConnected())) {
                this.zza.zzq().zzw().zza("Already awaiting connection attempt");
                return;
            }
            this.zzc = new zzer(contextZzm, Looper.getMainLooper(), this, this);
            this.zza.zzq().zzw().zza("Connecting to remote service");
            this.zzb = true;
            this.zzc.checkAvailabilityAndConnect();
        }
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnected(Bundle bundle) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                this.zza.zzp().zza(new zzjm(this, this.zzc.getService()));
            } catch (DeadObjectException | IllegalStateException unused) {
                this.zzc = null;
                this.zzb = false;
            }
        }
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnectionSuspended(int i) throws IllegalStateException {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionSuspended");
        this.zza.zzq().zzv().zza("Service connection suspended");
        this.zza.zzp().zza(new zzjp(this));
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener
    public final void onConnectionFailed(ConnectionResult connectionResult) throws IllegalStateException {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionFailed");
        zzeq zzeqVarZzc = this.zza.zzy.zzc();
        if (zzeqVarZzc != null) {
            zzeqVarZzc.zzh().zza("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzb = false;
            this.zzc = null;
        }
        this.zza.zzp().zza(new zzjo(this));
    }

    static /* synthetic */ boolean zza(zzjl zzjlVar, boolean z) {
        zzjlVar.zzb = false;
        return false;
    }
}
