package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.ChannelClient;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzav extends ChannelClient {
    public static final /* synthetic */ int zza = 0;
    private final zzam zzb;

    public zzav(Activity activity, GoogleApi.Settings settings) {
        super(activity, settings);
        this.zzb = new zzam();
    }

    private static zzbi zzc(ChannelClient.Channel channel) {
        Preconditions.checkNotNull(channel, "channel must not be null");
        return (zzbi) channel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzbi zzd(Channel channel) {
        Preconditions.checkNotNull(channel, "channel must not be null");
        return (zzbi) channel;
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<Void> close(ChannelClient.Channel channel) {
        zzbi zzbiVarZzc = zzc(channel);
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        return PendingResultUtil.toVoidTask(googleApiClientAsGoogleApiClient.enqueue(new zzaz(zzbiVarZzc, googleApiClientAsGoogleApiClient)));
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<InputStream> getInputStream(ChannelClient.Channel channel) {
        zzbi zzbiVarZzc = zzc(channel);
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        return PendingResultUtil.toTask(googleApiClientAsGoogleApiClient.enqueue(new zzbb(zzbiVarZzc, googleApiClientAsGoogleApiClient)), zzaq.zza);
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<OutputStream> getOutputStream(ChannelClient.Channel channel) {
        zzbi zzbiVarZzc = zzc(channel);
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        return PendingResultUtil.toTask(googleApiClientAsGoogleApiClient.enqueue(new zzbc(zzbiVarZzc, googleApiClientAsGoogleApiClient)), zzar.zza);
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<ChannelClient.Channel> openChannel(String str, String str2) {
        zzam zzamVar = this.zzb;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        Preconditions.checkNotNull(googleApiClientAsGoogleApiClient, "client is null");
        Preconditions.checkNotNull(str, "nodeId is null");
        Preconditions.checkNotNull(str2, "path is null");
        return PendingResultUtil.toTask(googleApiClientAsGoogleApiClient.enqueue(new zzai(zzamVar, googleApiClientAsGoogleApiClient, str, str2)), zzan.zza);
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<Void> receiveFile(ChannelClient.Channel channel, Uri uri, boolean z) {
        zzbi zzbiVarZzc = zzc(channel);
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        Preconditions.checkNotNull(googleApiClientAsGoogleApiClient, "client is null");
        Preconditions.checkNotNull(uri, "uri is null");
        return PendingResultUtil.toVoidTask(googleApiClientAsGoogleApiClient.enqueue(new zzbd(zzbiVarZzc, googleApiClientAsGoogleApiClient, uri, z)));
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<Void> registerChannelCallback(ChannelClient.Channel channel, ChannelClient.ChannelCallback channelCallback) {
        final String strZza = ((zzbi) channel).zza();
        Preconditions.checkNotNull(channelCallback, "listener is null");
        Looper looper = getLooper();
        String strValueOf = String.valueOf(strZza);
        ListenerHolder listenerHolderCreateListenerHolder = ListenerHolders.createListenerHolder(channelCallback, looper, strValueOf.length() != 0 ? "ChannelListener:".concat(strValueOf) : new String("ChannelListener:"));
        final IntentFilter[] intentFilterArr = {zzgv.zza("com.google.android.gms.wearable.CHANNEL_EVENT")};
        final zzau zzauVar = new zzau(channelCallback);
        final ListenerHolder listenerHolderCreateListenerHolder2 = ListenerHolders.createListenerHolder(zzauVar, getLooper(), "ChannelListener");
        return doRegisterEventListener(RegistrationMethods.builder().withHolder(listenerHolderCreateListenerHolder).register(new RemoteCall(zzauVar, listenerHolderCreateListenerHolder2, strZza, intentFilterArr) { // from class: com.google.android.gms.wearable.internal.zzas
            private final zzau zza;
            private final ListenerHolder zzb;
            private final String zzc;
            private final IntentFilter[] zzd;

            {
                this.zza = zzauVar;
                this.zzb = listenerHolderCreateListenerHolder2;
                this.zzc = strZza;
                this.zzd = intentFilterArr;
            }

            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                zzau zzauVar2 = this.zza;
                ListenerHolder<? extends ChannelApi.ChannelListener> listenerHolder = this.zzb;
                String str = this.zzc;
                IntentFilter[] intentFilterArr2 = this.zzd;
                zzhv zzhvVar = (zzhv) obj;
                int i = zzav.zza;
                zzhvVar.zzw(new zzgt((TaskCompletionSource) obj2), zzauVar2, listenerHolder, str, intentFilterArr2);
            }
        }).unregister(new RemoteCall(zzauVar, strZza) { // from class: com.google.android.gms.wearable.internal.zzat
            private final zzau zza;
            private final String zzb;

            {
                this.zza = zzauVar;
                this.zzb = strZza;
            }

            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                zzau zzauVar2 = this.zza;
                String str = this.zzb;
                int i = zzav.zza;
                ((zzhv) obj).zzA(new zzgs((TaskCompletionSource) obj2), zzauVar2, str);
            }
        }).setMethodKey(24014).build());
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<Void> sendFile(ChannelClient.Channel channel, Uri uri) {
        return PendingResultUtil.toVoidTask(zzc(channel).sendFile(asGoogleApiClient(), uri, 0L, -1L));
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<Boolean> unregisterChannelCallback(ChannelClient.Channel channel, ChannelClient.ChannelCallback channelCallback) {
        String strZza = zzc(channel).zza();
        Looper looper = getLooper();
        String strValueOf = String.valueOf(strZza);
        return doUnregisterEventListener((ListenerHolder.ListenerKey) Preconditions.checkNotNull(ListenerHolders.createListenerHolder(channelCallback, looper, strValueOf.length() != 0 ? "ChannelListener:".concat(strValueOf) : new String("ChannelListener:")).getListenerKey(), "Key must not be null"), 24004);
    }

    public zzav(Context context, GoogleApi.Settings settings) {
        super(context, settings);
        this.zzb = new zzam();
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<Void> sendFile(ChannelClient.Channel channel, Uri uri, long j, long j2) {
        return PendingResultUtil.toVoidTask(zzc(channel).sendFile(asGoogleApiClient(), uri, j, j2));
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<Void> close(ChannelClient.Channel channel, int i) {
        zzbi zzbiVarZzc = zzc(channel);
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        return PendingResultUtil.toVoidTask(googleApiClientAsGoogleApiClient.enqueue(new zzba(zzbiVarZzc, googleApiClientAsGoogleApiClient, i)));
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<Boolean> unregisterChannelCallback(ChannelClient.ChannelCallback channelCallback) {
        return doUnregisterEventListener((ListenerHolder.ListenerKey) Preconditions.checkNotNull(ListenerHolders.createListenerHolder(channelCallback, getLooper(), "ChannelListener").getListenerKey(), "Key must not be null"), 24004);
    }

    @Override // com.google.android.gms.wearable.ChannelClient
    public final Task<Void> registerChannelCallback(ChannelClient.ChannelCallback channelCallback) {
        Preconditions.checkNotNull(channelCallback, "listener is null");
        ListenerHolder listenerHolderCreateListenerHolder = ListenerHolders.createListenerHolder(channelCallback, getLooper(), "ChannelListener");
        final IntentFilter[] intentFilterArr = {zzgv.zza("com.google.android.gms.wearable.CHANNEL_EVENT")};
        final zzau zzauVar = new zzau(channelCallback);
        final ListenerHolder listenerHolderCreateListenerHolder2 = ListenerHolders.createListenerHolder(zzauVar, getLooper(), "ChannelListener");
        return doRegisterEventListener(RegistrationMethods.builder().withHolder(listenerHolderCreateListenerHolder).register(new RemoteCall(zzauVar, listenerHolderCreateListenerHolder2, intentFilterArr) { // from class: com.google.android.gms.wearable.internal.zzao
            private final zzau zza;
            private final ListenerHolder zzb;
            private final IntentFilter[] zzc;

            {
                this.zza = zzauVar;
                this.zzb = listenerHolderCreateListenerHolder2;
                this.zzc = intentFilterArr;
            }

            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                zzau zzauVar2 = this.zza;
                ListenerHolder<? extends ChannelApi.ChannelListener> listenerHolder = this.zzb;
                IntentFilter[] intentFilterArr2 = this.zzc;
                zzhv zzhvVar = (zzhv) obj;
                int i = zzav.zza;
                zzhvVar.zzw(new zzgt((TaskCompletionSource) obj2), zzauVar2, listenerHolder, null, intentFilterArr2);
            }
        }).unregister(new RemoteCall(zzauVar) { // from class: com.google.android.gms.wearable.internal.zzap
            private final zzau zza;

            {
                this.zza = zzauVar;
            }

            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                zzau zzauVar2 = this.zza;
                int i = zzav.zza;
                ((zzhv) obj).zzA(new zzgs((TaskCompletionSource) obj2), zzauVar2, null);
            }
        }).setMethodKey(24014).build());
    }
}
