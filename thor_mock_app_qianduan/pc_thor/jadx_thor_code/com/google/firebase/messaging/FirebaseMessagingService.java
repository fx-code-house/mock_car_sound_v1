package com.google.firebase.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.cloudmessaging.CloudMessagingReceiver;
import com.google.firebase.iid.ServiceStarter;
import com.google.firebase.messaging.Constants;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

/* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
/* loaded from: classes2.dex */
public class FirebaseMessagingService extends EnhancedIntentService {
    public static final String ACTION_DIRECT_BOOT_REMOTE_INTENT = "com.google.firebase.messaging.RECEIVE_DIRECT_BOOT";
    private static final Queue<String> recentlyReceivedMessageIds = new ArrayDeque(10);

    public void onDeletedMessages() {
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
    }

    public void onMessageSent(String str) {
    }

    public void onNewToken(String str) {
    }

    public void onSendError(String str, Exception exc) {
    }

    @Override // com.google.firebase.messaging.EnhancedIntentService
    protected Intent getStartCommandIntent(Intent intent) {
        return ServiceStarter.getInstance().getMessagingEvent();
    }

    @Override // com.google.firebase.messaging.EnhancedIntentService
    public boolean handleIntentOnMainThread(Intent intent) {
        if (!CloudMessagingReceiver.IntentActionKeys.NOTIFICATION_OPEN.equals(intent.getAction())) {
            return false;
        }
        handleNotificationOpen(intent);
        return true;
    }

    private void handleNotificationOpen(Intent intent) {
        if (MessagingAnalytics.shouldUploadScionMetrics(intent)) {
            MessagingAnalytics.logNotificationOpen(intent);
        }
    }

    @Override // com.google.firebase.messaging.EnhancedIntentService
    public void handleIntent(Intent intent) {
        String action = intent.getAction();
        if ("com.google.android.c2dm.intent.RECEIVE".equals(action) || ACTION_DIRECT_BOOT_REMOTE_INTENT.equals(action)) {
            handleMessageIntent(intent);
            return;
        }
        if (CloudMessagingReceiver.IntentActionKeys.NOTIFICATION_DISMISS.equals(action)) {
            if (MessagingAnalytics.shouldUploadScionMetrics(intent)) {
                MessagingAnalytics.logNotificationDismiss(intent);
            }
        } else if ("com.google.firebase.messaging.NEW_TOKEN".equals(action)) {
            onNewToken(intent.getStringExtra("token"));
        } else {
            String strValueOf = String.valueOf(intent.getAction());
            Log.d(Constants.TAG, strValueOf.length() != 0 ? "Unknown intent action: ".concat(strValueOf) : new String("Unknown intent action: "));
        }
    }

    private void handleMessageIntent(Intent intent) {
        if (alreadyReceivedMessage(intent.getStringExtra(Constants.MessagePayloadKeys.MSGID))) {
            return;
        }
        passMessageIntentToSdk(intent);
    }

    private void passMessageIntentToSdk(Intent intent) {
        String stringExtra;
        stringExtra = intent.getStringExtra(Constants.MessagePayloadKeys.MESSAGE_TYPE);
        if (stringExtra == null) {
            stringExtra = Constants.MessageTypes.MESSAGE;
        }
        stringExtra.hashCode();
        switch (stringExtra) {
            case "deleted_messages":
                onDeletedMessages();
                break;
            case "gcm":
                MessagingAnalytics.logNotificationReceived(intent);
                dispatchMessage(intent);
                break;
            case "send_error":
                onSendError(getMessageId(intent), new SendException(intent.getStringExtra(Constants.IPC_BUNDLE_KEY_SEND_ERROR)));
                break;
            case "send_event":
                onMessageSent(intent.getStringExtra(Constants.MessagePayloadKeys.MSGID));
                break;
            default:
                String strValueOf = String.valueOf(stringExtra);
                Log.w(Constants.TAG, strValueOf.length() != 0 ? "Received message with unknown type: ".concat(strValueOf) : new String("Received message with unknown type: "));
                break;
        }
    }

    private void dispatchMessage(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            extras = new Bundle();
        }
        extras.remove("androidx.content.wakelockid");
        if (NotificationParams.isNotification(extras)) {
            NotificationParams notificationParams = new NotificationParams(extras);
            ExecutorService executorServiceNewNetworkIOExecutor = FcmExecutors.newNetworkIOExecutor();
            try {
                if (new DisplayNotification(this, notificationParams, executorServiceNewNetworkIOExecutor).handleNotification()) {
                    return;
                }
                executorServiceNewNetworkIOExecutor.shutdown();
                if (MessagingAnalytics.shouldUploadScionMetrics(intent)) {
                    MessagingAnalytics.logNotificationForeground(intent);
                }
            } finally {
                executorServiceNewNetworkIOExecutor.shutdown();
            }
        }
        onMessageReceived(new RemoteMessage(extras));
    }

    private boolean alreadyReceivedMessage(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Queue<String> queue = recentlyReceivedMessageIds;
        if (queue.contains(str)) {
            if (!Log.isLoggable(Constants.TAG, 3)) {
                return true;
            }
            String strValueOf = String.valueOf(str);
            Log.d(Constants.TAG, strValueOf.length() != 0 ? "Received duplicate message: ".concat(strValueOf) : new String("Received duplicate message: "));
            return true;
        }
        if (queue.size() >= 10) {
            queue.remove();
        }
        queue.add(str);
        return false;
    }

    private String getMessageId(Intent intent) {
        String stringExtra = intent.getStringExtra(Constants.MessagePayloadKeys.MSGID);
        return stringExtra == null ? intent.getStringExtra(Constants.MessagePayloadKeys.MSGID_SERVER) : stringExtra;
    }
}
