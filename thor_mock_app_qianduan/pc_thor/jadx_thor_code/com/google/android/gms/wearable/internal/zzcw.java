package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
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
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzcw extends DataClient {
    public static final /* synthetic */ int zza = 0;
    private final DataApi zzb;

    public zzcw(Activity activity, GoogleApi.Settings settings) {
        super(activity, settings);
        this.zzb = new zzcj();
    }

    private final Task<Void> zza(final DataClient.OnDataChangedListener onDataChangedListener, final IntentFilter[] intentFilterArr) {
        final ListenerHolder listenerHolderCreateListenerHolder = ListenerHolders.createListenerHolder(onDataChangedListener, getLooper(), "DataListener");
        return doRegisterEventListener(RegistrationMethods.builder().withHolder(listenerHolderCreateListenerHolder).register(new RemoteCall(onDataChangedListener, listenerHolderCreateListenerHolder, intentFilterArr) { // from class: com.google.android.gms.wearable.internal.zzcu
            private final DataClient.OnDataChangedListener zza;
            private final ListenerHolder zzb;
            private final IntentFilter[] zzc;

            {
                this.zza = onDataChangedListener;
                this.zzb = listenerHolderCreateListenerHolder;
                this.zzc = intentFilterArr;
            }

            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                DataClient.OnDataChangedListener onDataChangedListener2 = this.zza;
                ListenerHolder<? extends DataApi.DataListener> listenerHolder = this.zzb;
                IntentFilter[] intentFilterArr2 = this.zzc;
                int i = zzcw.zza;
                ((zzhv) obj).zzt(new zzgt((TaskCompletionSource) obj2), onDataChangedListener2, listenerHolder, intentFilterArr2);
            }
        }).unregister(new RemoteCall(onDataChangedListener) { // from class: com.google.android.gms.wearable.internal.zzcl
            private final DataClient.OnDataChangedListener zza;

            {
                this.zza = onDataChangedListener;
            }

            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                DataClient.OnDataChangedListener onDataChangedListener2 = this.zza;
                int i = zzcw.zza;
                ((zzhv) obj).zzx(new zzgs((TaskCompletionSource) obj2), onDataChangedListener2);
            }
        }).setMethodKey(24015).build());
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<Void> addListener(DataClient.OnDataChangedListener onDataChangedListener) {
        return zza(onDataChangedListener, new IntentFilter[]{zzgv.zza("com.google.android.gms.wearable.DATA_CHANGED")});
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<Integer> deleteDataItems(Uri uri) {
        return PendingResultUtil.toTask(((zzcj) this.zzb).deleteDataItems(asGoogleApiClient(), uri, 0), zzcq.zza);
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<DataItem> getDataItem(Uri uri) {
        DataApi dataApi = this.zzb;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        return PendingResultUtil.toTask(googleApiClientAsGoogleApiClient.enqueue(new zzby((zzcj) dataApi, googleApiClientAsGoogleApiClient, uri)), zzcm.zza);
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<DataItemBuffer> getDataItems() {
        DataApi dataApi = this.zzb;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        return PendingResultUtil.toTask(googleApiClientAsGoogleApiClient.enqueue(new zzbz((zzcj) dataApi, googleApiClientAsGoogleApiClient)), zzcn.zza);
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<DataClient.GetFdForAssetResponse> getFdForAsset(Asset asset) {
        DataApi dataApi = this.zzb;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        if (asset == null) {
            throw new IllegalArgumentException("asset is null");
        }
        if (asset.getDigest() == null) {
            throw new IllegalArgumentException("invalid asset");
        }
        if (asset.zza() == null) {
            return PendingResultUtil.toTask(googleApiClientAsGoogleApiClient.enqueue(new zzcc((zzcj) dataApi, googleApiClientAsGoogleApiClient, asset)), zzcs.zza);
        }
        throw new IllegalArgumentException("invalid asset");
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<DataItem> putDataItem(PutDataRequest putDataRequest) {
        DataApi dataApi = this.zzb;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        return PendingResultUtil.toTask(googleApiClientAsGoogleApiClient.enqueue(new zzbx((zzcj) dataApi, googleApiClientAsGoogleApiClient, putDataRequest)), zzck.zza);
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<Boolean> removeListener(DataClient.OnDataChangedListener onDataChangedListener) {
        return doUnregisterEventListener((ListenerHolder.ListenerKey) Preconditions.checkNotNull(ListenerHolders.createListenerHolder(onDataChangedListener, getLooper(), "DataListener").getListenerKey(), "Key must not be null"), 24005);
    }

    public zzcw(Context context, GoogleApi.Settings settings) {
        super(context, settings);
        this.zzb = new zzcj();
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<Void> addListener(DataClient.OnDataChangedListener onDataChangedListener, Uri uri, int i) {
        boolean z;
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
        return zza(onDataChangedListener, new IntentFilter[]{zzgv.zzb("com.google.android.gms.wearable.DATA_CHANGED", uri, i)});
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<Integer> deleteDataItems(Uri uri, int i) {
        return PendingResultUtil.toTask(this.zzb.deleteDataItems(asGoogleApiClient(), uri, i), zzcr.zza);
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<DataItemBuffer> getDataItems(Uri uri) {
        return PendingResultUtil.toTask(((zzcj) this.zzb).getDataItems(asGoogleApiClient(), uri, 0), zzco.zza);
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<DataItemBuffer> getDataItems(Uri uri, int i) {
        return PendingResultUtil.toTask(this.zzb.getDataItems(asGoogleApiClient(), uri, i), zzcp.zza);
    }

    @Override // com.google.android.gms.wearable.DataClient
    public final Task<DataClient.GetFdForAssetResponse> getFdForAsset(DataItemAsset dataItemAsset) {
        DataApi dataApi = this.zzb;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        return PendingResultUtil.toTask(googleApiClientAsGoogleApiClient.enqueue(new zzcd((zzcj) dataApi, googleApiClientAsGoogleApiClient, dataItemAsset)), zzct.zza);
    }
}
