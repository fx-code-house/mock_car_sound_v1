package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.google.android.datatransport.TransportFactory;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.FirebaseApp;
import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Subscriber;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.Metadata;
import com.google.firebase.inject.Provider;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
/* loaded from: classes2.dex */
public class FirebaseMessaging {

    @Deprecated
    public static final String INSTANCE_ID_SCOPE = "FCM";
    static TransportFactory transportFactory;
    private final AutoInit autoInit;
    private final Context context;
    private final Executor fileIoExecutor;
    private final FirebaseApp firebaseApp;
    private final FirebaseInstanceId iid;
    private final Task<TopicsSubscriber> topicsSubscriberTask;

    public static synchronized FirebaseMessaging getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    static synchronized FirebaseMessaging getInstance(FirebaseApp firebaseApp) {
        FirebaseMessaging firebaseMessaging;
        firebaseMessaging = (FirebaseMessaging) firebaseApp.get(FirebaseMessaging.class);
        Preconditions.checkNotNull(firebaseMessaging, "Firebase Messaging component is not present");
        return firebaseMessaging;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
    class AutoInit {
        private Boolean autoInitEnabled;
        private EventHandler<DataCollectionDefaultChange> dataCollectionDefaultChangeEventHandler;
        private boolean initialized;
        private final Subscriber subscriber;

        AutoInit(Subscriber subscriber) {
            this.subscriber = subscriber;
        }

        synchronized void initialize() {
            if (this.initialized) {
                return;
            }
            Boolean enabled = readEnabled();
            this.autoInitEnabled = enabled;
            if (enabled == null) {
                EventHandler<DataCollectionDefaultChange> eventHandler = new EventHandler(this) { // from class: com.google.firebase.messaging.FirebaseMessaging$AutoInit$$Lambda$0
                    private final FirebaseMessaging.AutoInit arg$1;

                    {
                        this.arg$1 = this;
                    }

                    @Override // com.google.firebase.events.EventHandler
                    public final void handle(Event event) {
                        this.arg$1.lambda$initialize$1$FirebaseMessaging$AutoInit(event);
                    }
                };
                this.dataCollectionDefaultChangeEventHandler = eventHandler;
                this.subscriber.subscribe(DataCollectionDefaultChange.class, eventHandler);
            }
            this.initialized = true;
        }

        synchronized boolean isEnabled() {
            initialize();
            Boolean bool = this.autoInitEnabled;
            if (bool != null) {
                return bool.booleanValue();
            }
            return FirebaseMessaging.this.firebaseApp.isDataCollectionDefaultEnabled();
        }

        synchronized void setEnabled(boolean z) {
            initialize();
            EventHandler<DataCollectionDefaultChange> eventHandler = this.dataCollectionDefaultChangeEventHandler;
            if (eventHandler != null) {
                this.subscriber.unsubscribe(DataCollectionDefaultChange.class, eventHandler);
                this.dataCollectionDefaultChangeEventHandler = null;
            }
            SharedPreferences.Editor editorEdit = FirebaseMessaging.this.firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.messaging", 0).edit();
            editorEdit.putBoolean("auto_init", z);
            editorEdit.apply();
            if (z) {
                FirebaseMessaging.this.fileIoExecutor.execute(new Runnable(this) { // from class: com.google.firebase.messaging.FirebaseMessaging$AutoInit$$Lambda$1
                    private final FirebaseMessaging.AutoInit arg$1;

                    {
                        this.arg$1 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        this.arg$1.lambda$setEnabled$2$FirebaseMessaging$AutoInit();
                    }
                });
            }
            this.autoInitEnabled = Boolean.valueOf(z);
        }

        private Boolean readEnabled() {
            ApplicationInfo applicationInfo;
            Context applicationContext = FirebaseMessaging.this.firebaseApp.getApplicationContext();
            SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("com.google.firebase.messaging", 0);
            if (sharedPreferences.contains("auto_init")) {
                return Boolean.valueOf(sharedPreferences.getBoolean("auto_init", false));
            }
            try {
                PackageManager packageManager = applicationContext.getPackageManager();
                if (packageManager == null || (applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128)) == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey("firebase_messaging_auto_init_enabled")) {
                    return null;
                }
                return Boolean.valueOf(applicationInfo.metaData.getBoolean("firebase_messaging_auto_init_enabled"));
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }

        final /* synthetic */ void lambda$setEnabled$2$FirebaseMessaging$AutoInit() {
            FirebaseMessaging.this.iid.getToken();
        }

        final /* synthetic */ void lambda$initialize$1$FirebaseMessaging$AutoInit(Event event) {
            if (isEnabled()) {
                FirebaseMessaging.this.fileIoExecutor.execute(new Runnable(this) { // from class: com.google.firebase.messaging.FirebaseMessaging$AutoInit$$Lambda$2
                    private final FirebaseMessaging.AutoInit arg$1;

                    {
                        this.arg$1 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        this.arg$1.lambda$initialize$0$FirebaseMessaging$AutoInit();
                    }
                });
            }
        }

        final /* synthetic */ void lambda$initialize$0$FirebaseMessaging$AutoInit() {
            FirebaseMessaging.this.iid.getToken();
        }
    }

    FirebaseMessaging(FirebaseApp firebaseApp, final FirebaseInstanceId firebaseInstanceId, Provider<UserAgentPublisher> provider, Provider<HeartBeatInfo> provider2, FirebaseInstallationsApi firebaseInstallationsApi, TransportFactory transportFactory2, Subscriber subscriber) throws ClassNotFoundException {
        try {
            Class.forName("com.google.firebase.iid.FirebaseInstanceIdReceiver");
            transportFactory = transportFactory2;
            this.firebaseApp = firebaseApp;
            this.iid = firebaseInstanceId;
            this.autoInit = new AutoInit(subscriber);
            Context applicationContext = firebaseApp.getApplicationContext();
            this.context = applicationContext;
            ScheduledExecutorService scheduledExecutorServiceNewInitExecutor = FcmExecutors.newInitExecutor();
            this.fileIoExecutor = scheduledExecutorServiceNewInitExecutor;
            scheduledExecutorServiceNewInitExecutor.execute(new Runnable(this, firebaseInstanceId) { // from class: com.google.firebase.messaging.FirebaseMessaging$$Lambda$0
                private final FirebaseMessaging arg$1;
                private final FirebaseInstanceId arg$2;

                {
                    this.arg$1 = this;
                    this.arg$2 = firebaseInstanceId;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    this.arg$1.lambda$new$0$FirebaseMessaging(this.arg$2);
                }
            });
            Task<TopicsSubscriber> taskCreateInstance = TopicsSubscriber.createInstance(firebaseApp, firebaseInstanceId, new Metadata(applicationContext), provider, provider2, firebaseInstallationsApi, applicationContext, FcmExecutors.newTopicsSyncExecutor());
            this.topicsSubscriberTask = taskCreateInstance;
            taskCreateInstance.addOnSuccessListener(FcmExecutors.newTopicsSyncTriggerExecutor(), new OnSuccessListener(this) { // from class: com.google.firebase.messaging.FirebaseMessaging$$Lambda$1
                private final FirebaseMessaging arg$1;

                {
                    this.arg$1 = this;
                }

                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    this.arg$1.lambda$new$1$FirebaseMessaging((TopicsSubscriber) obj);
                }
            });
        } catch (ClassNotFoundException unused) {
            throw new IllegalStateException("FirebaseMessaging and FirebaseInstanceId versions not compatible. Update to latest version of firebase-messaging.");
        }
    }

    public boolean isAutoInitEnabled() {
        return this.autoInit.isEnabled();
    }

    public void setAutoInitEnabled(boolean z) {
        this.autoInit.setEnabled(z);
    }

    public boolean deliveryMetricsExportToBigQueryEnabled() {
        return MessagingAnalytics.deliveryMetricsExportToBigQueryEnabled();
    }

    public void setDeliveryMetricsExportToBigQuery(boolean z) {
        MessagingAnalytics.setDeliveryMetricsExportToBigQuery(z);
    }

    public Task<String> getToken() {
        return this.iid.getInstanceId().continueWith(FirebaseMessaging$$Lambda$2.$instance);
    }

    public Task<Void> deleteToken() {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        FcmExecutors.newNetworkIOExecutor().execute(new Runnable(this, taskCompletionSource) { // from class: com.google.firebase.messaging.FirebaseMessaging$$Lambda$3
            private final FirebaseMessaging arg$1;
            private final TaskCompletionSource arg$2;

            {
                this.arg$1 = this;
                this.arg$2 = taskCompletionSource;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.arg$1.lambda$deleteToken$3$FirebaseMessaging(this.arg$2);
            }
        });
        return taskCompletionSource.getTask();
    }

    public Task<Void> subscribeToTopic(final String str) {
        return this.topicsSubscriberTask.onSuccessTask(new SuccessContinuation(str) { // from class: com.google.firebase.messaging.FirebaseMessaging$$Lambda$4
            private final String arg$1;

            {
                this.arg$1 = str;
            }

            @Override // com.google.android.gms.tasks.SuccessContinuation
            public final Task then(Object obj) {
                return ((TopicsSubscriber) obj).subscribeToTopic(this.arg$1);
            }
        });
    }

    public Task<Void> unsubscribeFromTopic(final String str) {
        return this.topicsSubscriberTask.onSuccessTask(new SuccessContinuation(str) { // from class: com.google.firebase.messaging.FirebaseMessaging$$Lambda$5
            private final String arg$1;

            {
                this.arg$1 = str;
            }

            @Override // com.google.android.gms.tasks.SuccessContinuation
            public final Task then(Object obj) {
                return ((TopicsSubscriber) obj).unsubscribeFromTopic(this.arg$1);
            }
        });
    }

    public void send(RemoteMessage remoteMessage) {
        if (TextUtils.isEmpty(remoteMessage.getTo())) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        Intent intent = new Intent("com.google.android.gcm.intent.SEND");
        Intent intent2 = new Intent();
        intent2.setPackage("com.google.example.invalidpackage");
        intent.putExtra("app", PendingIntent.getBroadcast(this.context, 0, intent2, 0));
        intent.setPackage("com.google.android.gms");
        remoteMessage.populateSendMessageIntent(intent);
        this.context.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    public static TransportFactory getTransportFactory() {
        return transportFactory;
    }

    final /* synthetic */ void lambda$deleteToken$3$FirebaseMessaging(TaskCompletionSource taskCompletionSource) {
        try {
            this.iid.deleteToken(Metadata.getDefaultSenderId(this.firebaseApp), INSTANCE_ID_SCOPE);
            taskCompletionSource.setResult(null);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    final /* synthetic */ void lambda$new$1$FirebaseMessaging(TopicsSubscriber topicsSubscriber) {
        if (isAutoInitEnabled()) {
            topicsSubscriber.startTopicsSyncIfNecessary();
        }
    }

    final /* synthetic */ void lambda$new$0$FirebaseMessaging(FirebaseInstanceId firebaseInstanceId) {
        if (this.autoInit.isEnabled()) {
            firebaseInstanceId.getToken();
        }
    }
}
