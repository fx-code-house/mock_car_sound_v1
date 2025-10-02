package com.google.firebase.messaging;

import android.content.Context;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.GmsRpc;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.iid.Metadata;
import com.google.firebase.inject.Provider;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
/* loaded from: classes2.dex */
class TopicsSubscriber {
    private static final long MAX_DELAY_SEC = TimeUnit.HOURS.toSeconds(8);
    private final Context context;
    private final FirebaseInstanceId iid;
    private final Metadata metadata;
    private final GmsRpc rpc;
    private final TopicsStore store;
    private final ScheduledExecutorService syncExecutor;
    private final Map<String, ArrayDeque<TaskCompletionSource<Void>>> pendingOperations = new ArrayMap();
    private boolean syncScheduledOrRunning = false;

    static Task<TopicsSubscriber> createInstance(FirebaseApp firebaseApp, FirebaseInstanceId firebaseInstanceId, Metadata metadata, Provider<UserAgentPublisher> provider, Provider<HeartBeatInfo> provider2, FirebaseInstallationsApi firebaseInstallationsApi, Context context, ScheduledExecutorService scheduledExecutorService) {
        return createInstance(firebaseInstanceId, metadata, new GmsRpc(firebaseApp, metadata, provider, provider2, firebaseInstallationsApi), context, scheduledExecutorService);
    }

    static Task<TopicsSubscriber> createInstance(final FirebaseInstanceId firebaseInstanceId, final Metadata metadata, final GmsRpc gmsRpc, final Context context, final ScheduledExecutorService scheduledExecutorService) {
        return Tasks.call(scheduledExecutorService, new Callable(context, scheduledExecutorService, firebaseInstanceId, metadata, gmsRpc) { // from class: com.google.firebase.messaging.TopicsSubscriber$$Lambda$0
            private final Context arg$1;
            private final ScheduledExecutorService arg$2;
            private final FirebaseInstanceId arg$3;
            private final Metadata arg$4;
            private final GmsRpc arg$5;

            {
                this.arg$1 = context;
                this.arg$2 = scheduledExecutorService;
                this.arg$3 = firebaseInstanceId;
                this.arg$4 = metadata;
                this.arg$5 = gmsRpc;
            }

            @Override // java.util.concurrent.Callable
            public final Object call() {
                return TopicsSubscriber.lambda$createInstance$0$TopicsSubscriber(this.arg$1, this.arg$2, this.arg$3, this.arg$4, this.arg$5);
            }
        });
    }

    private TopicsSubscriber(FirebaseInstanceId firebaseInstanceId, Metadata metadata, TopicsStore topicsStore, GmsRpc gmsRpc, Context context, ScheduledExecutorService scheduledExecutorService) {
        this.iid = firebaseInstanceId;
        this.metadata = metadata;
        this.store = topicsStore;
        this.rpc = gmsRpc;
        this.context = context;
        this.syncExecutor = scheduledExecutorService;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Task<Void> subscribeToTopic(String str) {
        Task<Void> taskScheduleTopicOperation = scheduleTopicOperation(TopicOperation.subscribe(str));
        startTopicsSyncIfNecessary();
        return taskScheduleTopicOperation;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Task<Void> unsubscribeFromTopic(String str) {
        Task<Void> taskScheduleTopicOperation = scheduleTopicOperation(TopicOperation.unsubscribe(str));
        startTopicsSyncIfNecessary();
        return taskScheduleTopicOperation;
    }

    Task<Void> scheduleTopicOperation(TopicOperation topicOperation) {
        this.store.addTopicOperation(topicOperation);
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();
        addToPendingOperations(topicOperation, taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    private void addToPendingOperations(TopicOperation topicOperation, TaskCompletionSource<Void> taskCompletionSource) {
        ArrayDeque<TaskCompletionSource<Void>> arrayDeque;
        synchronized (this.pendingOperations) {
            String strSerialize = topicOperation.serialize();
            if (this.pendingOperations.containsKey(strSerialize)) {
                arrayDeque = this.pendingOperations.get(strSerialize);
            } else {
                ArrayDeque<TaskCompletionSource<Void>> arrayDeque2 = new ArrayDeque<>();
                this.pendingOperations.put(strSerialize, arrayDeque2);
                arrayDeque = arrayDeque2;
            }
            arrayDeque.add(taskCompletionSource);
        }
    }

    boolean hasPendingOperation() {
        return this.store.getNextTopicOperation() != null;
    }

    void startTopicsSyncIfNecessary() {
        if (hasPendingOperation()) {
            startSync();
        }
    }

    private void startSync() {
        if (isSyncScheduledOrRunning()) {
            return;
        }
        syncWithDelaySecondsInternal(0L);
    }

    void syncWithDelaySecondsInternal(long j) {
        scheduleSyncTaskWithDelaySeconds(new TopicsSyncTask(this, this.context, this.metadata, Math.min(Math.max(30L, j << 1), MAX_DELAY_SEC)), j);
        setSyncScheduledOrRunning(true);
    }

    void scheduleSyncTaskWithDelaySeconds(Runnable runnable, long j) {
        this.syncExecutor.schedule(runnable, j, TimeUnit.SECONDS);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x000d, code lost:
    
        if (isDebugLogEnabled() == false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x000f, code lost:
    
        android.util.Log.d(com.google.firebase.messaging.Constants.TAG, "topic sync succeeded");
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0017, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean syncTopics() throws java.io.IOException {
        /*
            r2 = this;
        L0:
            monitor-enter(r2)
            com.google.firebase.messaging.TopicsStore r0 = r2.store     // Catch: java.lang.Throwable -> L2b
            com.google.firebase.messaging.TopicOperation r0 = r0.getNextTopicOperation()     // Catch: java.lang.Throwable -> L2b
            if (r0 != 0) goto L19
            boolean r0 = isDebugLogEnabled()     // Catch: java.lang.Throwable -> L2b
            if (r0 == 0) goto L16
            java.lang.String r0 = "FirebaseMessaging"
            java.lang.String r1 = "topic sync succeeded"
            android.util.Log.d(r0, r1)     // Catch: java.lang.Throwable -> L2b
        L16:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2b
            r0 = 1
            return r0
        L19:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2b
            boolean r1 = r2.performTopicOperation(r0)
            if (r1 != 0) goto L22
            r0 = 0
            return r0
        L22:
            com.google.firebase.messaging.TopicsStore r1 = r2.store
            r1.removeTopicOperation(r0)
            r2.markCompletePendingOperation(r0)
            goto L0
        L2b:
            r0 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2b
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.TopicsSubscriber.syncTopics():boolean");
    }

    private void markCompletePendingOperation(TopicOperation topicOperation) {
        synchronized (this.pendingOperations) {
            String strSerialize = topicOperation.serialize();
            if (this.pendingOperations.containsKey(strSerialize)) {
                ArrayDeque<TaskCompletionSource<Void>> arrayDeque = this.pendingOperations.get(strSerialize);
                TaskCompletionSource<Void> taskCompletionSourcePoll = arrayDeque.poll();
                if (taskCompletionSourcePoll != null) {
                    taskCompletionSourcePoll.setResult(null);
                }
                if (arrayDeque.isEmpty()) {
                    this.pendingOperations.remove(strSerialize);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean performTopicOperation(com.google.firebase.messaging.TopicOperation r7) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 287
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.TopicsSubscriber.performTopicOperation(com.google.firebase.messaging.TopicOperation):boolean");
    }

    private void blockingSubscribeToTopic(String str) throws IOException {
        InstanceIdResult instanceIdResult = (InstanceIdResult) awaitTask(this.iid.getInstanceId());
        awaitTask(this.rpc.subscribeToTopic(instanceIdResult.getId(), instanceIdResult.getToken(), str));
    }

    private void blockingUnsubscribeFromTopic(String str) throws IOException {
        InstanceIdResult instanceIdResult = (InstanceIdResult) awaitTask(this.iid.getInstanceId());
        awaitTask(this.rpc.unsubscribeFromTopic(instanceIdResult.getId(), instanceIdResult.getToken(), str));
    }

    private static <T> T awaitTask(Task<T> task) throws IOException {
        try {
            return (T) Tasks.await(task, 30L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e = e;
            throw new IOException(GmsRpc.ERROR_SERVICE_NOT_AVAILABLE, e);
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            }
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            throw new IOException(e2);
        } catch (TimeoutException e3) {
            e = e3;
            throw new IOException(GmsRpc.ERROR_SERVICE_NOT_AVAILABLE, e);
        }
    }

    synchronized boolean isSyncScheduledOrRunning() {
        return this.syncScheduledOrRunning;
    }

    synchronized void setSyncScheduledOrRunning(boolean z) {
        this.syncScheduledOrRunning = z;
    }

    static boolean isDebugLogEnabled() {
        return Log.isLoggable(Constants.TAG, 3);
    }

    static final /* synthetic */ TopicsSubscriber lambda$createInstance$0$TopicsSubscriber(Context context, ScheduledExecutorService scheduledExecutorService, FirebaseInstanceId firebaseInstanceId, Metadata metadata, GmsRpc gmsRpc) throws Exception {
        return new TopicsSubscriber(firebaseInstanceId, metadata, TopicsStore.getInstance(context, scheduledExecutorService), gmsRpc, context, scheduledExecutorService);
    }
}
