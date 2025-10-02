package com.google.android.gms.internal.p000authapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.auth.api.identity.CredentialSavingClient;
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenRequest;
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenResult;
import com.google.android.gms.auth.api.identity.SavePasswordRequest;
import com.google.android.gms.auth.api.identity.SavePasswordResult;
import com.google.android.gms.auth.api.identity.zbh;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-auth@@20.7.0 */
/* loaded from: classes2.dex */
public final class zbaw extends GoogleApi implements CredentialSavingClient {
    private static final Api.ClientKey zba;
    private static final Api.AbstractClientBuilder zbb;
    private static final Api zbc;
    private final String zbd;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zba = clientKey;
        zbat zbatVar = new zbat();
        zbb = zbatVar;
        zbc = new Api("Auth.Api.Identity.CredentialSaving.API", zbatVar, clientKey);
    }

    public zbaw(Activity activity, zbh zbhVar) {
        super(activity, (Api<zbh>) zbc, zbhVar, GoogleApi.Settings.DEFAULT_SETTINGS);
        this.zbd = zbbj.zba();
    }

    @Override // com.google.android.gms.auth.api.identity.CredentialSavingClient
    public final Status getStatusFromIntent(Intent intent) {
        if (intent == null) {
            return Status.RESULT_INTERNAL_ERROR;
        }
        Status status = (Status) SafeParcelableSerializer.deserializeFromIntentExtra(intent, NotificationCompat.CATEGORY_STATUS, Status.CREATOR);
        return status == null ? Status.RESULT_INTERNAL_ERROR : status;
    }

    @Override // com.google.android.gms.auth.api.identity.CredentialSavingClient
    public final Task<SaveAccountLinkingTokenResult> saveAccountLinkingToken(SaveAccountLinkingTokenRequest saveAccountLinkingTokenRequest) {
        Preconditions.checkNotNull(saveAccountLinkingTokenRequest);
        SaveAccountLinkingTokenRequest.Builder builderZba = SaveAccountLinkingTokenRequest.zba(saveAccountLinkingTokenRequest);
        builderZba.zba(this.zbd);
        final SaveAccountLinkingTokenRequest saveAccountLinkingTokenRequestBuild = builderZba.build();
        return doRead(TaskApiCall.builder().setFeatures(zbbi.zbg).run(new RemoteCall() { // from class: com.google.android.gms.internal.auth-api.zbar
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                zbaw zbawVar = this.zba;
                SaveAccountLinkingTokenRequest saveAccountLinkingTokenRequest2 = saveAccountLinkingTokenRequestBuild;
                ((zbad) ((zbx) obj).getService()).zbc(new zbau(zbawVar, (TaskCompletionSource) obj2), (SaveAccountLinkingTokenRequest) Preconditions.checkNotNull(saveAccountLinkingTokenRequest2));
            }
        }).setAutoResolveMissingFeatures(false).setMethodKey(1535).build());
    }

    @Override // com.google.android.gms.auth.api.identity.CredentialSavingClient
    public final Task<SavePasswordResult> savePassword(SavePasswordRequest savePasswordRequest) {
        Preconditions.checkNotNull(savePasswordRequest);
        SavePasswordRequest.Builder builderZba = SavePasswordRequest.zba(savePasswordRequest);
        builderZba.zba(this.zbd);
        final SavePasswordRequest savePasswordRequestBuild = builderZba.build();
        return doRead(TaskApiCall.builder().setFeatures(zbbi.zbe).run(new RemoteCall() { // from class: com.google.android.gms.internal.auth-api.zbas
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                zbaw zbawVar = this.zba;
                SavePasswordRequest savePasswordRequest2 = savePasswordRequestBuild;
                ((zbad) ((zbx) obj).getService()).zbd(new zbav(zbawVar, (TaskCompletionSource) obj2), (SavePasswordRequest) Preconditions.checkNotNull(savePasswordRequest2));
            }
        }).setAutoResolveMissingFeatures(false).setMethodKey(1536).build());
    }

    public zbaw(Context context, zbh zbhVar) {
        super(context, (Api<zbh>) zbc, zbhVar, GoogleApi.Settings.DEFAULT_SETTINGS);
        this.zbd = zbbj.zba();
    }
}
