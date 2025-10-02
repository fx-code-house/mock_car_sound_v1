package com.thor.app.utils.logs;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import timber.log.Timber;

/* loaded from: classes3.dex */
public class CrashReportingTree extends Timber.Tree {
    @Override // timber.log.Timber.Tree
    protected void log(final int priority, String tag, String message, final Throwable t) {
        if (priority != 6) {
            return;
        }
        reportError(message, t);
    }

    private void reportError(String message, Throwable t) {
        FirebaseCrashlytics.getInstance().log(message);
        if (t != null) {
            FirebaseCrashlytics.getInstance().recordException(t);
        }
    }
}
