package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes2.dex */
public final class IndoorLevel {
    private final com.google.android.gms.internal.maps.zzq zzdg;

    public IndoorLevel(com.google.android.gms.internal.maps.zzq zzqVar) {
        this.zzdg = (com.google.android.gms.internal.maps.zzq) Preconditions.checkNotNull(zzqVar);
    }

    public final String getName() {
        try {
            return this.zzdg.getName();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final String getShortName() {
        try {
            return this.zzdg.getShortName();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void activate() {
        try {
            this.zzdg.activate();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof IndoorLevel)) {
            return false;
        }
        try {
            return this.zzdg.zzb(((IndoorLevel) obj).zzdg);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int hashCode() {
        try {
            return this.zzdg.zzj();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
