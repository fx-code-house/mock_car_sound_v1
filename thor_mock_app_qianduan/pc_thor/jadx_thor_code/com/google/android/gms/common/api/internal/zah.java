package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes2.dex */
public final class zah extends zad<Boolean> {
    public final ListenerHolder.ListenerKey<?> zab;

    public zah(ListenerHolder.ListenerKey<?> listenerKey, TaskCompletionSource<Boolean> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zab = listenerKey;
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final boolean zaa(zabq<?> zabqVar) {
        zaci zaciVar = zabqVar.zah().get(this.zab);
        return zaciVar != null && zaciVar.zaa.zab();
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final Feature[] zab(zabq<?> zabqVar) {
        zaci zaciVar = zabqVar.zah().get(this.zab);
        if (zaciVar == null) {
            return null;
        }
        return zaciVar.zaa.getRequiredFeatures();
    }

    @Override // com.google.android.gms.common.api.internal.zad
    public final void zac(zabq<?> zabqVar) throws RemoteException {
        zaci zaciVarRemove = zabqVar.zah().remove(this.zab);
        if (zaciVarRemove == null) {
            this.zaa.trySetResult(false);
        } else {
            zaciVarRemove.zab.unregisterListener(zabqVar.zaf(), this.zaa);
            zaciVarRemove.zaa.clearListener();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zad, com.google.android.gms.common.api.internal.zai
    public final /* bridge */ /* synthetic */ void zag(zaad zaadVar, boolean z) {
    }
}
