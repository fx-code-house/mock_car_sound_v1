package com.google.firebase.messaging;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
/* loaded from: classes2.dex */
final class TopicsStore {
    private static WeakReference<TopicsStore> topicsStoreWeakReference;
    private final SharedPreferences sharedPreferences;
    private final Executor syncExecutor;
    private SharedPreferencesQueue topicOperationsQueue;

    private TopicsStore(SharedPreferences sharedPreferences, Executor executor) {
        this.syncExecutor = executor;
        this.sharedPreferences = sharedPreferences;
    }

    private final synchronized void initStore() {
        this.topicOperationsQueue = SharedPreferencesQueue.createInstance(this.sharedPreferences, "topic_operation_queue", ",", this.syncExecutor);
    }

    public static synchronized TopicsStore getInstance(Context context, Executor executor) {
        TopicsStore topicsStore;
        WeakReference<TopicsStore> weakReference = topicsStoreWeakReference;
        topicsStore = weakReference != null ? weakReference.get() : null;
        if (topicsStore == null) {
            topicsStore = new TopicsStore(context.getSharedPreferences("com.google.android.gms.appid", 0), executor);
            topicsStore.initStore();
            topicsStoreWeakReference = new WeakReference<>(topicsStore);
        }
        return topicsStore;
    }

    final synchronized TopicOperation getNextTopicOperation() {
        return TopicOperation.from(this.topicOperationsQueue.peek());
    }

    final synchronized boolean addTopicOperation(TopicOperation topicOperation) {
        return this.topicOperationsQueue.add(topicOperation.serialize());
    }

    final synchronized boolean removeTopicOperation(TopicOperation topicOperation) {
        return this.topicOperationsQueue.remove(topicOperation.serialize());
    }
}
