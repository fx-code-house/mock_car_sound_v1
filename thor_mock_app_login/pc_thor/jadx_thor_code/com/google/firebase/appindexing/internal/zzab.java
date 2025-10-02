package com.google.firebase.appindexing.internal;

import android.app.slice.SliceManager;
import android.content.Context;
import android.net.Uri;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzab extends zzaa {
    private final SliceManager zzfx;

    public zzab(Context context) {
        this.zzfx = (SliceManager) context.getSystemService(SliceManager.class);
    }

    @Override // com.google.firebase.appindexing.internal.zzaa
    public final void grantSlicePermission(String str, Uri uri) {
        this.zzfx.grantSlicePermission(str, uri);
    }
}
