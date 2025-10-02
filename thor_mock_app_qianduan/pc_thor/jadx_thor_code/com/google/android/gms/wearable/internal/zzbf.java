package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.wearable.ChannelApi;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzbf implements zzb<ChannelApi.ChannelListener> {
    final /* synthetic */ String zza;
    final /* synthetic */ IntentFilter[] zzb;

    zzbf(String str, IntentFilter[] intentFilterArr) {
        this.zza = str;
        this.zzb = intentFilterArr;
    }

    @Override // com.google.android.gms.wearable.internal.zzb
    public final /* bridge */ /* synthetic */ void zza(zzhv zzhvVar, BaseImplementation.ResultHolder resultHolder, ChannelApi.ChannelListener channelListener, ListenerHolder<ChannelApi.ChannelListener> listenerHolder) throws RemoteException {
        zzhvVar.zzw(resultHolder, channelListener, listenerHolder, this.zza, this.zzb);
    }
}
