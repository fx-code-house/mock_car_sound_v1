package com.thor.app.services;

import android.text.TextUtils;
import androidx.appcompat.app.AlertDialog;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.PushNotificationManager;
import com.thor.app.managers.UsersManager;
import com.thor.networkmodule.model.responses.BaseResponse;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.Map;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: FmcService.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\fH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\rH\u0016J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\rH\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0016"}, d2 = {"Lcom/thor/app/services/FmcService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "pushNotificationManager", "Lcom/thor/app/managers/PushNotificationManager;", "getPushNotificationManager", "()Lcom/thor/app/managers/PushNotificationManager;", "setPushNotificationManager", "(Lcom/thor/app/managers/PushNotificationManager;)V", "catchResultData", "Lcom/thor/app/services/MessageData;", "data", "", "", "onMessageReceived", "", "remoteMessage", "Lcom/google/firebase/messaging/RemoteMessage;", "onNewToken", "token", "onTokenUpdate", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class FmcService extends Hilt_FmcService {
    public static final String DATA = "data";

    @Inject
    public PushNotificationManager pushNotificationManager;

    public final PushNotificationManager getPushNotificationManager() {
        PushNotificationManager pushNotificationManager = this.pushNotificationManager;
        if (pushNotificationManager != null) {
            return pushNotificationManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("pushNotificationManager");
        return null;
    }

    public final void setPushNotificationManager(PushNotificationManager pushNotificationManager) {
        Intrinsics.checkNotNullParameter(pushNotificationManager, "<set-?>");
        this.pushNotificationManager = pushNotificationManager;
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intrinsics.checkNotNullParameter(remoteMessage, "remoteMessage");
        Timber.INSTANCE.i("message receiver:" + remoteMessage.getData(), new Object[0]);
        Map<String, String> data = remoteMessage.getData();
        Intrinsics.checkNotNullExpressionValue(data, "remoteMessage.data");
        catchResultData(data);
    }

    private final MessageData catchResultData(Map<String, String> data) {
        MessageData messageData = (MessageData) new Gson().fromJson(new Gson().toJson(data), MessageData.class);
        return messageData == null ? new MessageData(null, null, null, null, 15, null) : messageData;
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onNewToken(String token) {
        Intrinsics.checkNotNullParameter(token, "token");
        super.onNewToken(token);
        onTokenUpdate(token);
    }

    private final void onTokenUpdate(String token) {
        Observable<BaseResponse> observableUpdateNotificationToken;
        Timber.INSTANCE.i("onTokenUpdate: %s", token);
        if (TextUtils.isEmpty(token) || (observableUpdateNotificationToken = UsersManager.INSTANCE.from(this).updateNotificationToken()) == null) {
            return;
        }
        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.services.FmcService.onTokenUpdate.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                invoke2(baseResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BaseResponse baseResponse) {
                AlertDialog alertDialogCreateErrorAlertDialog$default;
                if (((baseResponse == null || baseResponse.isSuccessful()) ? false : true) && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, FmcService.this.getApplicationContext(), baseResponse.getError(), null, 4, null)) != null) {
                    alertDialogCreateErrorAlertDialog$default.show();
                }
                Timber.INSTANCE.i("Response: %s", baseResponse);
            }
        };
        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.services.FmcService$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FmcService.onTokenUpdate$lambda$1(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.services.FmcService.onTokenUpdate.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                AlertDialog alertDialogCreateErrorAlertDialog$default;
                if (Intrinsics.areEqual(th != null ? th.getMessage() : null, "HTTP 400")) {
                    AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, FmcService.this, th.getMessage(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialog$default2 != null) {
                        alertDialogCreateErrorAlertDialog$default2.show();
                    }
                } else {
                    if (Intrinsics.areEqual(th != null ? th.getMessage() : null, "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, FmcService.this, th.getMessage(), null, 4, null)) != null) {
                        alertDialogCreateErrorAlertDialog$default.show();
                    }
                }
                Timber.INSTANCE.e(th);
            }
        };
        observableUpdateNotificationToken.subscribe(consumer, new Consumer() { // from class: com.thor.app.services.FmcService$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FmcService.onTokenUpdate$lambda$2(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onTokenUpdate$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onTokenUpdate$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }
}
