package com.google.firebase.appindexing;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appindexing.internal.zzn;
import java.lang.ref.WeakReference;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public abstract class FirebaseUserActions {
    public static final String APP_INDEXING_API_TAG = "FirebaseUserActions";
    private static WeakReference<FirebaseUserActions> zzen;

    public abstract Task<Void> end(Action action);

    public abstract Task<Void> start(Action action);

    public static synchronized FirebaseUserActions getInstance() {
        FirebaseUserActions firebaseUserActions;
        WeakReference<FirebaseUserActions> weakReference = zzen;
        firebaseUserActions = weakReference == null ? null : weakReference.get();
        if (firebaseUserActions == null) {
            zzn zznVar = new zzn(FirebaseApp.getInstance().getApplicationContext());
            zzen = new WeakReference<>(zznVar);
            firebaseUserActions = zznVar;
        }
        return firebaseUserActions;
    }
}
