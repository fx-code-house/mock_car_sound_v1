package com.thor.app.gui.activities;

import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Lifecycle;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: EmergencySituationBaseActivity.kt */
@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0007\u001a\u00020\u0003H\u0016¨\u0006\b"}, d2 = {"com/thor/app/gui/activities/EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1", "Landroid/net/ConnectivityManager$NetworkCallback;", "onAvailable", "", "network", "Landroid/net/Network;", "onLost", "onUnavailable", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1 extends ConnectivityManager.NetworkCallback {
    final /* synthetic */ Handler $dialogHandler;
    final /* synthetic */ AlertDialog $noInternetDialog;
    final /* synthetic */ EmergencySituationBaseActivity this$0;

    EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1(Handler handler, AlertDialog alertDialog, EmergencySituationBaseActivity emergencySituationBaseActivity) {
        this.$dialogHandler = handler;
        this.$noInternetDialog = alertDialog;
        this.this$0 = emergencySituationBaseActivity;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onAvailable(Network network) {
        Intrinsics.checkNotNullParameter(network, "network");
        Timber.INSTANCE.i("networkCallback: onAvailable %s", network.toString());
        Handler handler = this.$dialogHandler;
        final AlertDialog alertDialog = this.$noInternetDialog;
        final EmergencySituationBaseActivity emergencySituationBaseActivity = this.this$0;
        handler.post(new Runnable() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1.onAvailable$lambda$0(alertDialog, emergencySituationBaseActivity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAvailable$lambda$0(AlertDialog noInternetDialog, EmergencySituationBaseActivity this$0) {
        Intrinsics.checkNotNullParameter(noInternetDialog, "$noInternetDialog");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (noInternetDialog.isShowing() && this$0.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
            noInternetDialog.dismiss();
        }
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onLost(Network network) {
        Intrinsics.checkNotNullParameter(network, "network");
        Timber.INSTANCE.i("networkCallback: onLost %s", network.toString());
        Handler handler = this.$dialogHandler;
        final AlertDialog alertDialog = this.$noInternetDialog;
        final EmergencySituationBaseActivity emergencySituationBaseActivity = this.this$0;
        handler.post(new Runnable() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1.onLost$lambda$1(alertDialog, emergencySituationBaseActivity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLost$lambda$1(AlertDialog noInternetDialog, EmergencySituationBaseActivity this$0) {
        Intrinsics.checkNotNullParameter(noInternetDialog, "$noInternetDialog");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (noInternetDialog.isShowing() || !this$0.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
            return;
        }
        noInternetDialog.show();
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onUnavailable() {
        super.onUnavailable();
        Timber.INSTANCE.i("networkCallback: onUnavailable", new Object[0]);
        Handler handler = this.$dialogHandler;
        final AlertDialog alertDialog = this.$noInternetDialog;
        final EmergencySituationBaseActivity emergencySituationBaseActivity = this.this$0;
        handler.post(new Runnable() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1.onUnavailable$lambda$2(alertDialog, emergencySituationBaseActivity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onUnavailable$lambda$2(AlertDialog noInternetDialog, EmergencySituationBaseActivity this$0) {
        Intrinsics.checkNotNullParameter(noInternetDialog, "$noInternetDialog");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (noInternetDialog.isShowing() || !this$0.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
            return;
        }
        noInternetDialog.show();
    }
}
