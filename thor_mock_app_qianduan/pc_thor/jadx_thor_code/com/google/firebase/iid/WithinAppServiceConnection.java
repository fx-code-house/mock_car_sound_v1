package com.google.firebase.iid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.iid.WithinAppServiceConnection;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-iid@@21.0.0 */
/* loaded from: classes2.dex */
public class WithinAppServiceConnection implements ServiceConnection {
    private WithinAppServiceBinder binder;
    private boolean connectionInProgress;
    private final Intent connectionIntent;
    private final Context context;
    private final Queue<BindRequest> intentQueue;
    private final ScheduledExecutorService scheduledExecutorService;

    public WithinAppServiceConnection(Context context, String str) {
        this(context, str, new ScheduledThreadPoolExecutor(0, new NamedThreadFactory("Firebase-FirebaseInstanceIdServiceConnection")));
    }

    WithinAppServiceConnection(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
        this.intentQueue = new ArrayDeque();
        this.connectionInProgress = false;
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        this.connectionIntent = new Intent(str).setPackage(applicationContext.getPackageName());
        this.scheduledExecutorService = scheduledExecutorService;
    }

    /* compiled from: com.google.firebase:firebase-iid@@21.0.0 */
    static class BindRequest {
        final Intent intent;
        private final TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();

        BindRequest(Intent intent) {
            this.intent = intent;
        }

        void arrangeTimeout(ScheduledExecutorService scheduledExecutorService) {
            final ScheduledFuture<?> scheduledFutureSchedule = scheduledExecutorService.schedule(new Runnable(this) { // from class: com.google.firebase.iid.WithinAppServiceConnection$BindRequest$$Lambda$0
                private final WithinAppServiceConnection.BindRequest arg$1;

                {
                    this.arg$1 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    this.arg$1.lambda$arrangeTimeout$0$WithinAppServiceConnection$BindRequest();
                }
            }, 9000L, TimeUnit.MILLISECONDS);
            getTask().addOnCompleteListener(scheduledExecutorService, new OnCompleteListener(scheduledFutureSchedule) { // from class: com.google.firebase.iid.WithinAppServiceConnection$BindRequest$$Lambda$1
                private final ScheduledFuture arg$1;

                {
                    this.arg$1 = scheduledFutureSchedule;
                }

                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    this.arg$1.cancel(false);
                }
            });
        }

        Task<Void> getTask() {
            return this.taskCompletionSource.getTask();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void finish() {
            this.taskCompletionSource.trySetResult(null);
        }

        final /* synthetic */ void lambda$arrangeTimeout$0$WithinAppServiceConnection$BindRequest() {
            String action = this.intent.getAction();
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(action).length() + 61).append("Service took too long to process intent: ").append(action).append(" App may get closed.").toString());
            finish();
        }
    }

    public synchronized Task<Void> sendIntent(Intent intent) {
        BindRequest bindRequest;
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "new intent queued in the bind-strategy delivery");
        }
        bindRequest = new BindRequest(intent);
        bindRequest.arrangeTimeout(this.scheduledExecutorService);
        this.intentQueue.add(bindRequest);
        flushQueue();
        return bindRequest.getTask();
    }

    private synchronized void flushQueue() {
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "flush queue called");
        }
        while (!this.intentQueue.isEmpty()) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "found intent to be delivered");
            }
            WithinAppServiceBinder withinAppServiceBinder = this.binder;
            if (withinAppServiceBinder != null && withinAppServiceBinder.isBinderAlive()) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    Log.d("FirebaseInstanceId", "binder is alive, sending the intent.");
                }
                this.binder.send(this.intentQueue.poll());
            } else {
                startConnectionIfNeeded();
                return;
            }
        }
    }

    private void startConnectionIfNeeded() {
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", new StringBuilder(39).append("binder is dead. start connection? ").append(!this.connectionInProgress).toString());
        }
        if (this.connectionInProgress) {
            return;
        }
        this.connectionInProgress = true;
        try {
        } catch (SecurityException e) {
            Log.e("FirebaseInstanceId", "Exception while binding the service", e);
        }
        if (ConnectionTracker.getInstance().bindService(this.context, this.connectionIntent, this, 65)) {
            return;
        }
        Log.e("FirebaseInstanceId", "binding to the service failed");
        this.connectionInProgress = false;
        finishAllInQueue();
    }

    private void finishAllInQueue() {
        while (!this.intentQueue.isEmpty()) {
            this.intentQueue.poll().finish();
        }
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String strValueOf = String.valueOf(componentName);
            Log.d("FirebaseInstanceId", new StringBuilder(String.valueOf(strValueOf).length() + 20).append("onServiceConnected: ").append(strValueOf).toString());
        }
        this.connectionInProgress = false;
        if (!(iBinder instanceof WithinAppServiceBinder)) {
            String strValueOf2 = String.valueOf(iBinder);
            Log.e("FirebaseInstanceId", new StringBuilder(String.valueOf(strValueOf2).length() + 28).append("Invalid service connection: ").append(strValueOf2).toString());
            finishAllInQueue();
        } else {
            this.binder = (WithinAppServiceBinder) iBinder;
            flushQueue();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String strValueOf = String.valueOf(componentName);
            Log.d("FirebaseInstanceId", new StringBuilder(String.valueOf(strValueOf).length() + 23).append("onServiceDisconnected: ").append(strValueOf).toString());
        }
        flushQueue();
    }
}
