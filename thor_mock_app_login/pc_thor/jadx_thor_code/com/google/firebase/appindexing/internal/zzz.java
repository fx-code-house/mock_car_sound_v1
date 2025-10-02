package com.google.firebase.appindexing.internal;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzz extends zzaa {
    private final Context zzcs;
    private final ContentResolver zzfu;

    public zzz(Context context) {
        this.zzcs = context;
        this.zzfu = context.getContentResolver();
    }

    @Override // com.google.firebase.appindexing.internal.zzaa
    public final void grantSlicePermission(String str, Uri uri) {
        ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = this.zzfu.acquireUnstableContentProviderClient(uri);
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("slice_uri", uri);
            bundle.putString("provider_pkg", this.zzcs.getPackageName());
            bundle.putString("pkg", str);
            contentProviderClientAcquireUnstableContentProviderClient.call("grant_perms", null, bundle);
        } catch (RemoteException e) {
            Log.e("ContentValues", "Unable to get slice descendants", e);
        } finally {
            contentProviderClientAcquireUnstableContentProviderClient.release();
        }
    }
}
