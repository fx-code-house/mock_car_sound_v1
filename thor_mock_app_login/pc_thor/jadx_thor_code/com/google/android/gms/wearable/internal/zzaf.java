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
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzaf extends CapabilityClient {
    public static final /* synthetic */ int zza = 0;
    private final CapabilityApi zzb;

    public zzaf(Activity activity, GoogleApi.Settings settings) {
        super(activity, settings);
        this.zzb = new zzz();
    }

    private final Task<Void> zza(final ListenerHolder<CapabilityClient.OnCapabilityChangedListener> listenerHolder, final CapabilityClient.OnCapabilityChangedListener onCapabilityChangedListener, final IntentFilter[] intentFilterArr) {
        return doRegisterEventListener(RegistrationMethods.builder().withHolder(listenerHolder).register(new RemoteCall(onCapabilityChangedListener, listenerHolder, intentFilterArr) { // from class: com.google.android.gms.wearable.internal.zzac
            private final CapabilityClient.OnCapabilityChangedListener zza;
            private final ListenerHolder zzb;
            private final IntentFilter[] zzc;

            {
                this.zza = onCapabilityChangedListener;
                this.zzb = listenerHolder;
                this.zzc = intentFilterArr;
            }

            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                CapabilityClient.OnCapabilityChangedListener onCapabilityChangedListener2 = this.zza;
                ListenerHolder<? extends CapabilityApi.CapabilityListener> listenerHolder2 = this.zzb;
                IntentFilter[] intentFilterArr2 = this.zzc;
                int i = zzaf.zza;
                ((zzhv) obj).zzv(new zzgt((TaskCompletionSource) obj2), onCapabilityChangedListener2, listenerHolder2, intentFilterArr2);
            }
        }).unregister(new RemoteCall(onCapabilityChangedListener) { // from class: com.google.android.gms.wearable.internal.zzad
            private final CapabilityClient.OnCapabilityChangedListener zza;

            {
                this.zza = onCapabilityChangedListener;
            }

            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                CapabilityClient.OnCapabilityChangedListener onCapabilityChangedListener2 = this.zza;
                int i = zzaf.zza;
                ((zzhv) obj).zzz(new zzgs((TaskCompletionSource) obj2), onCapabilityChangedListener2);
            }
        }).setMethodKey(24013).build());
    }

    @Override // com.google.android.gms.wearable.CapabilityClient
    public final Task<Void> addListener(CapabilityClient.OnCapabilityChangedListener onCapabilityChangedListener, Uri uri, int i) {
        boolean z;
        Asserts.checkNotNull(onCapabilityChangedListener, "listener must not be null");
        Asserts.checkNotNull(uri, "uri must not be null");
        if (i == 0) {
            z = true;
        } else if (i == 1) {
            i = 1;
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "invalid filter type");
        return zza(ListenerHolders.createListenerHolder(onCapabilityChangedListener, getLooper(), "CapabilityListener"), onCapabilityChangedListener, new IntentFilter[]{zzgv.zzb("com.google.android.gms.wearable.CAPABILITY_CHANGED", uri, i)});
    }

    @Override // com.google.android.gms.wearable.CapabilityClient
    public final Task<Void> addLocalCapability(String str) {
        Asserts.checkNotNull(str, "capability must not be null");
        CapabilityApi capabilityApi = this.zzb;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        return PendingResultUtil.toVoidTask(googleApiClientAsGoogleApiClient.enqueue(new zzq((zzz) capabilityApi, googleApiClientAsGoogleApiClient, str)));
    }

    @Override // com.google.android.gms.wearable.CapabilityClient
    public final Task<Map<String, CapabilityInfo>> getAllCapabilities(int i) {
        CapabilityApi capabilityApi = this.zzb;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        boolean z = true;
        if (i != 0) {
            if (i == 1) {
                i = 1;
            } else {
                z = false;
            }
        }
        Preconditions.checkArgument(z);
        return PendingResultUtil.toTask(googleApiClientAsGoogleApiClient.enqueue(new zzp((zzz) capabilityApi, googleApiClientAsGoogleApiClient, i)), zzab.zza);
    }

    @Override // com.google.android.gms.wearable.CapabilityClient
    public final Task<CapabilityInfo> getCapability(String str, int i) {
        Asserts.checkNotNull(str, "capability must not be null");
        CapabilityApi capabilityApi = this.zzb;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        boolean z = true;
        if (i != 0) {
            if (i == 1) {
                i = 1;
            } else {
                z = false;
            }
        }
        Preconditions.checkArgument(z);
        return PendingResultUtil.toTask(googleApiClientAsGoogleApiClient.enqueue(new zzo((zzz) capabilityApi, googleApiClientAsGoogleApiClient, str, i)), zzaa.zza);
    }

    @Override // com.google.android.gms.wearable.CapabilityClient
    public final Task<Boolean> removeListener(CapabilityClient.OnCapabilityChangedListener onCapabilityChangedListener) {
        Asserts.checkNotNull(onCapabilityChangedListener, "listener must not be null");
        return doUnregisterEventListener((ListenerHolder.ListenerKey) Preconditions.checkNotNull(ListenerHolders.createListenerHolder(onCapabilityChangedListener, getLooper(), "CapabilityListener").getListenerKey(), "Key must not be null"), 24003);
    }

    @Override // com.google.android.gms.wearable.CapabilityClient
    public final Task<Void> removeLocalCapability(String str) {
        Asserts.checkNotNull(str, "capability must not be null");
        CapabilityApi capabilityApi = this.zzb;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        return PendingResultUtil.toVoidTask(googleApiClientAsGoogleApiClient.enqueue(new zzr((zzz) capabilityApi, googleApiClientAsGoogleApiClient, str)));
    }

    public zzaf(Context context, GoogleApi.Settings settings) {
        super(context, settings);
        this.zzb = new zzz();
    }

    @Override // com.google.android.gms.wearable.CapabilityClient
    public final Task<Boolean> removeListener(CapabilityClient.OnCapabilityChangedListener onCapabilityChangedListener, String str) {
        Asserts.checkNotNull(onCapabilityChangedListener, "listener must not be null");
        Asserts.checkNotNull(str, "capability must not be null");
        if (!str.startsWith("/")) {
            String strValueOf = String.valueOf(str);
            str = strValueOf.length() != 0 ? "/".concat(strValueOf) : new String("/");
        }
        Looper looper = getLooper();
        String strValueOf2 = String.valueOf(str);
        return doUnregisterEventListener((ListenerHolder.ListenerKey) Preconditions.checkNotNull(ListenerHolders.createListenerHolder(onCapabilityChangedListener, looper, strValueOf2.length() != 0 ? "CapabilityListener:".concat(strValueOf2) : new String("CapabilityListener:")).getListenerKey(), "Key must not be null"), 24003);
    }

    @Override // com.google.android.gms.wearable.CapabilityClient
    public final Task<Void> addListener(CapabilityClient.OnCapabilityChangedListener onCapabilityChangedListener, String str) {
        Asserts.checkNotNull(onCapabilityChangedListener, "listener must not be null");
        Asserts.checkNotNull(str, "capability must not be null");
        IntentFilter intentFilterZza = zzgv.zza("com.google.android.gms.wearable.CAPABILITY_CHANGED");
        if (!str.startsWith("/")) {
            String strValueOf = String.valueOf(str);
            str = strValueOf.length() != 0 ? "/".concat(strValueOf) : new String("/");
        }
        intentFilterZza.addDataPath(str, 0);
        IntentFilter[] intentFilterArr = {intentFilterZza};
        Looper looper = getLooper();
        String strValueOf2 = String.valueOf(str);
        return zza(ListenerHolders.createListenerHolder(onCapabilityChangedListener, looper, strValueOf2.length() != 0 ? "CapabilityListener:".concat(strValueOf2) : new String("CapabilityListener:")), new zzae(onCapabilityChangedListener, str), intentFilterArr);
    }
}
