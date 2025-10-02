package com.google.android.gms.fido.fido2;

import android.app.Activity;
import android.content.IntentSender;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
@Deprecated
/* loaded from: classes2.dex */
public interface Fido2PendingIntent {
    boolean hasPendingIntent();

    void launchPendingIntent(Activity activity, int i) throws IntentSender.SendIntentException;
}
