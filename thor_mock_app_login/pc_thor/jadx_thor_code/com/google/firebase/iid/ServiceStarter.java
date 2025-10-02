package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.cloudmessaging.CloudMessagingReceiver;
import com.google.android.gms.wallet.WalletConstants;
import java.util.ArrayDeque;
import java.util.Queue;

/* compiled from: com.google.firebase:firebase-iid@@21.0.0 */
/* loaded from: classes2.dex */
public class ServiceStarter {
    public static final String ACTION_MESSAGING_EVENT = "com.google.firebase.MESSAGING_EVENT";
    public static final int ERROR_UNKNOWN = 500;
    public static final int SUCCESS = -1;
    private static ServiceStarter instance;
    private String firebaseMessagingServiceClassName = null;
    private Boolean hasWakeLockPermission = null;
    private Boolean hasAccessNetworkStatePermission = null;
    private final Queue<Intent> messagingEvents = new ArrayDeque();

    public static synchronized ServiceStarter getInstance() {
        if (instance == null) {
            instance = new ServiceStarter();
        }
        return instance;
    }

    private ServiceStarter() {
    }

    public static PendingIntent createMessagingPendingIntent(Context context, int i, Intent intent, int i2) {
        return PendingIntent.getBroadcast(context, i, wrapServiceIntent(context, ACTION_MESSAGING_EVENT, intent), i2);
    }

    public static void startMessagingServiceViaReceiver(Context context, Intent intent) {
        context.sendBroadcast(wrapServiceIntent(context, ACTION_MESSAGING_EVENT, intent));
    }

    private static Intent wrapServiceIntent(Context context, String str, Intent intent) {
        Intent intent2 = new Intent(context, (Class<?>) FirebaseInstanceIdReceiver.class);
        intent2.setAction(str);
        intent2.putExtra(CloudMessagingReceiver.IntentKeys.WRAPPED_INTENT, intent);
        return intent2;
    }

    public static Intent unwrapServiceIntent(Intent intent) {
        Parcelable parcelableExtra = intent.getParcelableExtra(CloudMessagingReceiver.IntentKeys.WRAPPED_INTENT);
        if (parcelableExtra instanceof Intent) {
            return (Intent) parcelableExtra;
        }
        return null;
    }

    public Intent getMessagingEvent() {
        return this.messagingEvents.poll();
    }

    public int startMessagingService(Context context, Intent intent) {
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Starting service");
        }
        this.messagingEvents.offer(intent);
        Intent intent2 = new Intent(ACTION_MESSAGING_EVENT);
        intent2.setPackage(context.getPackageName());
        return doStartService(context, intent2);
    }

    private int doStartService(Context context, Intent intent) {
        ComponentName componentNameStartService;
        String strResolveServiceClassName = resolveServiceClassName(context, intent);
        if (strResolveServiceClassName != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String strValueOf = String.valueOf(strResolveServiceClassName);
                Log.d("FirebaseInstanceId", strValueOf.length() != 0 ? "Restricting intent to a specific service: ".concat(strValueOf) : new String("Restricting intent to a specific service: "));
            }
            intent.setClassName(context.getPackageName(), strResolveServiceClassName);
        }
        try {
            if (hasWakeLockPermission(context)) {
                componentNameStartService = WakeLockHolder.startWakefulService(context, intent);
            } else {
                componentNameStartService = context.startService(intent);
                Log.d("FirebaseInstanceId", "Missing wake lock permission, service start may be delayed");
            }
            if (componentNameStartService != null) {
                return -1;
            }
            Log.e("FirebaseInstanceId", "Error while delivering the message: ServiceIntent not found.");
            return WalletConstants.ERROR_CODE_INVALID_PARAMETERS;
        } catch (IllegalStateException e) {
            String strValueOf2 = String.valueOf(e);
            Log.e("FirebaseInstanceId", new StringBuilder(String.valueOf(strValueOf2).length() + 45).append("Failed to start service while in background: ").append(strValueOf2).toString());
            return WalletConstants.ERROR_CODE_SERVICE_UNAVAILABLE;
        } catch (SecurityException e2) {
            Log.e("FirebaseInstanceId", "Error while delivering the message to the serviceIntent", e2);
            return 401;
        }
    }

    private synchronized String resolveServiceClassName(Context context, Intent intent) {
        String str = this.firebaseMessagingServiceClassName;
        if (str != null) {
            return str;
        }
        ResolveInfo resolveInfoResolveService = context.getPackageManager().resolveService(intent, 0);
        if (resolveInfoResolveService != null && resolveInfoResolveService.serviceInfo != null) {
            ServiceInfo serviceInfo = resolveInfoResolveService.serviceInfo;
            if (context.getPackageName().equals(serviceInfo.packageName) && serviceInfo.name != null) {
                if (serviceInfo.name.startsWith(".")) {
                    String strValueOf = String.valueOf(context.getPackageName());
                    String strValueOf2 = String.valueOf(serviceInfo.name);
                    this.firebaseMessagingServiceClassName = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
                } else {
                    this.firebaseMessagingServiceClassName = serviceInfo.name;
                }
                return this.firebaseMessagingServiceClassName;
            }
            String str2 = serviceInfo.packageName;
            String str3 = serviceInfo.name;
            Log.e("FirebaseInstanceId", new StringBuilder(String.valueOf(str2).length() + 94 + String.valueOf(str3).length()).append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ").append(str2).append("/").append(str3).toString());
            return null;
        }
        Log.e("FirebaseInstanceId", "Failed to resolve target intent service, skipping classname enforcement");
        return null;
    }

    boolean hasWakeLockPermission(Context context) {
        if (this.hasWakeLockPermission == null) {
            this.hasWakeLockPermission = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0);
        }
        if (!this.hasWakeLockPermission.booleanValue() && Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Missing Permission: android.permission.WAKE_LOCK this should normally be included by the manifest merger, but may needed to be manually added to your manifest");
        }
        return this.hasWakeLockPermission.booleanValue();
    }

    boolean hasAccessNetworkStatePermission(Context context) {
        if (this.hasAccessNetworkStatePermission == null) {
            this.hasAccessNetworkStatePermission = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0);
        }
        if (!this.hasWakeLockPermission.booleanValue() && Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Missing Permission: android.permission.ACCESS_NETWORK_STATE this should normally be included by the manifest merger, but may needed to be manually added to your manifest");
        }
        return this.hasAccessNetworkStatePermission.booleanValue();
    }

    public static void setForTesting(ServiceStarter serviceStarter) {
        instance = serviceStarter;
    }
}
