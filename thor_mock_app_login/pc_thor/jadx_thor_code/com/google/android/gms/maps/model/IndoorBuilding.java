package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public final class IndoorBuilding {
    private final com.google.android.gms.internal.maps.zzn zzdd;
    private final zza zzde;

    static class zza {
        public static final zza zzdf = new zza();

        private zza() {
        }

        public static IndoorLevel zza(com.google.android.gms.internal.maps.zzq zzqVar) {
            return new IndoorLevel(zzqVar);
        }

        public static com.google.android.gms.internal.maps.zzq zza(IBinder iBinder) {
            return com.google.android.gms.internal.maps.zzr.zzf(iBinder);
        }
    }

    public IndoorBuilding(com.google.android.gms.internal.maps.zzn zznVar) {
        this(zznVar, zza.zzdf);
    }

    private IndoorBuilding(com.google.android.gms.internal.maps.zzn zznVar, zza zzaVar) {
        this.zzdd = (com.google.android.gms.internal.maps.zzn) Preconditions.checkNotNull(zznVar, "delegate");
        this.zzde = (zza) Preconditions.checkNotNull(zzaVar, "shim");
    }

    public final int getDefaultLevelIndex() {
        try {
            return this.zzdd.getDefaultLevelIndex();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int getActiveLevelIndex() {
        try {
            return this.zzdd.getActiveLevelIndex();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final List<IndoorLevel> getLevels() {
        try {
            List<IBinder> levels = this.zzdd.getLevels();
            ArrayList arrayList = new ArrayList(levels.size());
            Iterator<IBinder> it = levels.iterator();
            while (it.hasNext()) {
                arrayList.add(zza.zza(zza.zza(it.next())));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isUnderground() {
        try {
            return this.zzdd.isUnderground();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof IndoorBuilding)) {
            return false;
        }
        try {
            return this.zzdd.zzb(((IndoorBuilding) obj).zzdd);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int hashCode() {
        try {
            return this.zzdd.zzj();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
