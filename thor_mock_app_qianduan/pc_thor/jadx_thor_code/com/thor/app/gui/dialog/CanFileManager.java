package com.thor.app.gui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import androidx.appcompat.app.AlertDialog;
import com.carsystems.thor.app.R;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.util.Hex;
import com.google.firebase.messaging.Constants;
import com.thor.app.ThorApplication;
import com.thor.app.bus.events.StartCheckEmergencySituationsEvent;
import com.thor.app.bus.events.bluetooth.UpdateCanConfigurationsErrorEvent;
import com.thor.app.bus.events.bluetooth.UpdateCanConfigurationsSuccessfulEvent;
import com.thor.app.bus.events.shop.main.DownloadSettingsFileSuccessEvent;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.businessmodule.bluetooth.model.other.CanInfo;
import com.thor.businessmodule.bluetooth.model.other.FileType;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.request.other.WriteCanConfigurationsFileRequest;
import com.thor.businessmodule.bluetooth.response.other.ReadCanInfoBleResponse;
import com.thor.networkmodule.model.CanFile;
import com.thor.networkmodule.model.responses.CanConfigurationsResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import retrofit2.Response;
import timber.log.Timber;

/* compiled from: CanFileManager.kt */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 (2\u00020\u0001:\u0001(B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0015H\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001fH\u0003J\u0010\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\"H\u0003J\u0010\u0010#\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020%H\u0007J\u0010\u0010#\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020&H\u0007J\u0006\u0010'\u001a\u00020\u001aR\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\n\u001a\u0004\b\u0011\u0010\u0012¨\u0006)"}, d2 = {"Lcom/thor/app/gui/dialog/CanFileManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "canInfo", "Lcom/thor/businessmodule/bluetooth/model/other/CanInfo;", "getContext", "()Landroid/content/Context;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "createErrorMessageAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "message", "", "createNoCanFileOnServerAlertDialog", "doUpdateCanConfigurations", "", "request", "Lcom/thor/businessmodule/bluetooth/request/other/WriteCanConfigurationsFileRequest;", "fetchCanConfigurationFileUrl", Scopes.PROFILE, "Lcom/thor/networkmodule/model/responses/SignInResponse;", "fetchCanConfigurationsFile", "canConfigurationsResponse", "Lcom/thor/networkmodule/model/responses/CanConfigurationsResponse;", "onMessageEvent", "event", "Lcom/thor/app/bus/events/bluetooth/UpdateCanConfigurationsErrorEvent;", "Lcom/thor/app/bus/events/bluetooth/UpdateCanConfigurationsSuccessfulEvent;", "startUpdate", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class CanFileManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager;
    private CanInfo canInfo;
    private final Context context;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager;

    @JvmStatic
    public static final CanFileManager from(Context context) {
        return INSTANCE.from(context);
    }

    public CanFileManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.dialog.CanFileManager$bleManager$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final BleManager invoke() {
                return BleManager.INSTANCE.from(this.this$0.getContext());
            }
        });
        this.usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.dialog.CanFileManager$usersManager$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final UsersManager invoke() {
                return UsersManager.INSTANCE.from(this.this$0.getContext());
            }
        });
    }

    public final Context getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    /* compiled from: CanFileManager.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lcom/thor/app/gui/dialog/CanFileManager$Companion;", "", "()V", Constants.MessagePayloadKeys.FROM, "Lcom/thor/app/gui/dialog/CanFileManager;", "context", "Landroid/content/Context;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CanFileManager from(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Context applicationContext = context.getApplicationContext();
            Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.thor.app.ThorApplication");
            return ((ThorApplication) applicationContext).getCanFileManager();
        }
    }

    public final void startUpdate() {
        Timber.INSTANCE.i("startUpdate", new Object[0]);
        final SignInResponse profile = Settings.INSTANCE.getProfile();
        Observable<ByteArrayOutputStream> observableExecuteReadCanInfoCommand = getBleManager().executeReadCanInfoCommand();
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.gui.dialog.CanFileManager$startUpdate$commandObservable$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                Timber.INSTANCE.i("readCanInfoCommand", new Object[0]);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(bytes));
                Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
                ReadCanInfoBleResponse readCanInfoBleResponse = new ReadCanInfoBleResponse(bytes);
                if (!readCanInfoBleResponse.getStatus()) {
                    BleManager bleManager = this.this$0.getBleManager();
                    final CanFileManager canFileManager = this.this$0;
                    bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.gui.dialog.CanFileManager$startUpdate$commandObservable$1.2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            canFileManager.startUpdate();
                        }
                    });
                    Timber.INSTANCE.e("Response is not correct: %s", readCanInfoBleResponse.getErrorCode());
                    return;
                }
                Timber.Companion companion = Timber.INSTANCE;
                Object[] objArr = new Object[1];
                byte[] bytes2 = readCanInfoBleResponse.getBytes();
                if (bytes2 == null) {
                    bytes2 = new byte[0];
                }
                objArr[0] = Hex.bytesToStringUppercase(bytes2);
                companion.i("Response is correct: %s", objArr);
                this.this$0.canInfo = readCanInfoBleResponse.getCanInfo();
                SignInResponse signInResponse = profile;
                if (signInResponse != null) {
                    this.this$0.fetchCanConfigurationFileUrl(signInResponse);
                }
            }
        };
        Disposable commandObservable = observableExecuteReadCanInfoCommand.subscribe(new Consumer() { // from class: com.thor.app.gui.dialog.CanFileManager$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CanFileManager.startUpdate$lambda$0(function1, obj);
            }
        });
        BleManager bleManager = getBleManager();
        Intrinsics.checkNotNullExpressionValue(commandObservable, "commandObservable");
        bleManager.addCommandDisposable(commandObservable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startUpdate$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fetchCanConfigurationFileUrl(SignInResponse profile) {
        Observable<CanConfigurationsResponse> observableFetchCanFileUrl = getUsersManager().fetchCanFileUrl(profile);
        if (observableFetchCanFileUrl != null) {
            final Function1<CanConfigurationsResponse, Unit> function1 = new Function1<CanConfigurationsResponse, Unit>() { // from class: com.thor.app.gui.dialog.CanFileManager.fetchCanConfigurationFileUrl.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(CanConfigurationsResponse canConfigurationsResponse) {
                    invoke2(canConfigurationsResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(CanConfigurationsResponse it) {
                    Timber.INSTANCE.i("CanConfigurationsResponse: %s", it);
                    if (it.isSuccessful()) {
                        CanFileManager canFileManager = CanFileManager.this;
                        Intrinsics.checkNotNullExpressionValue(it, "it");
                        canFileManager.fetchCanConfigurationsFile(it);
                        InstalledPresets installPresets = Settings.INSTANCE.getInstallPresets();
                        if (installPresets == null) {
                            installPresets = new InstalledPresets((short) 0, (short) 0, null, 6, null);
                        }
                        EventBus.getDefault().post(new DownloadSettingsFileSuccessEvent(true, installPresets));
                        return;
                    }
                    AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(CanFileManager.this.getContext(), it.getError(), it.getCode());
                    if (alertDialogCreateErrorAlertDialog != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.e("Response error: %s", it.getError());
                    InstalledPresets installPresets2 = Settings.INSTANCE.getInstallPresets();
                    if (installPresets2 == null) {
                        installPresets2 = new InstalledPresets((short) 0, (short) 0, null, 6, null);
                    }
                    EventBus.getDefault().post(new DownloadSettingsFileSuccessEvent(true, installPresets2));
                    EventBus.getDefault().post(new UpdateCanConfigurationsErrorEvent(true, false));
                }
            };
            Consumer<? super CanConfigurationsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.dialog.CanFileManager$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CanFileManager.fetchCanConfigurationFileUrl$lambda$1(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.dialog.CanFileManager.fetchCanConfigurationFileUrl.2
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
                    AlertDialog alertDialogCreateErrorAlertDialog;
                    if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                        AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(CanFileManager.this.getContext(), th.getMessage(), 0);
                        if (alertDialogCreateErrorAlertDialog2 != null) {
                            alertDialogCreateErrorAlertDialog2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(CanFileManager.this.getContext(), th.getMessage(), 0)) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.e(th);
                    EventBus.getDefault().post(new UpdateCanConfigurationsErrorEvent(true, false));
                }
            };
            observableFetchCanFileUrl.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.dialog.CanFileManager$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CanFileManager.fetchCanConfigurationFileUrl$lambda$2(function12, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchCanConfigurationFileUrl$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchCanConfigurationFileUrl$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fetchCanConfigurationsFile(final CanConfigurationsResponse canConfigurationsResponse) {
        Observable<Response<ResponseBody>> observableSubscribeOn;
        UsersManager usersManager = getUsersManager();
        CanFile canFile = canConfigurationsResponse.getCanFile();
        Observable<Response<ResponseBody>> observableFetchFile = usersManager.fetchFile(canFile != null ? canFile.getFileUrl() : null);
        if (observableFetchFile == null || (observableSubscribeOn = observableFetchFile.subscribeOn(Schedulers.io())) == null) {
            return;
        }
        final Function1<Response<ResponseBody>, Unit> function1 = new Function1<Response<ResponseBody>, Unit>() { // from class: com.thor.app.gui.dialog.CanFileManager.fetchCanConfigurationsFile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Response<ResponseBody> response) {
                invoke2(response);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Response<ResponseBody> response) {
                byte[] bArr;
                byte[] bArrBytes;
                Timber.INSTANCE.i("Response: %s", response);
                if (response.isSuccessful()) {
                    ResponseBody responseBodyBody = response.body();
                    if (responseBodyBody == null || (bArrBytes = responseBodyBody.bytes()) == null) {
                        bArr = null;
                    } else {
                        byte[] bArrCopyOf = Arrays.copyOf(bArrBytes, bArrBytes.length);
                        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
                        bArr = bArrCopyOf;
                    }
                    if (bArr != null) {
                        CanFileManager canFileManager = CanFileManager.this;
                        CanConfigurationsResponse canConfigurationsResponse2 = canConfigurationsResponse;
                        BleManager bleManager = canFileManager.getBleManager();
                        FileType fileType = FileType.CAN;
                        CanInfo canInfo = canFileManager.canInfo;
                        Short shValueOf = canInfo != null ? Short.valueOf(canInfo.getId()) : null;
                        Intrinsics.checkNotNull(shValueOf);
                        short sShortValue = shValueOf.shortValue();
                        CanFile canFile2 = canConfigurationsResponse2.getCanFile();
                        Integer numValueOf = canFile2 != null ? Integer.valueOf(canFile2.getVersionFile()) : null;
                        Intrinsics.checkNotNull(numValueOf);
                        bleManager.downloadStartGroup(fileType, 1, bArr, sShortValue, numValueOf.intValue());
                        return;
                    }
                    return;
                }
                Timber.INSTANCE.e("Response error: %s", response.message());
                EventBus.getDefault().post(new UpdateCanConfigurationsErrorEvent(true, false));
            }
        };
        Consumer<? super Response<ResponseBody>> consumer = new Consumer() { // from class: com.thor.app.gui.dialog.CanFileManager$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CanFileManager.fetchCanConfigurationsFile$lambda$3(function1, obj);
            }
        };
        final C02562 c02562 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.dialog.CanFileManager.fetchCanConfigurationsFile.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
                EventBus.getDefault().post(new UpdateCanConfigurationsErrorEvent(true, false));
            }
        };
        observableSubscribeOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.dialog.CanFileManager$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CanFileManager.fetchCanConfigurationsFile$lambda$4(c02562, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchCanConfigurationsFile$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchCanConfigurationsFile$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void doUpdateCanConfigurations(WriteCanConfigurationsFileRequest request) {
        Timber.INSTANCE.i("doUpdateCanConfigurations", new Object[0]);
        getBleManager().executeUpdateCanConfigurations(request);
    }

    private final AlertDialog createNoCanFileOnServerAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context, 2131886083);
        SignInResponse profile = Settings.INSTANCE.getProfile();
        String str = this.context.getString(R.string.text_can_file_not_found) + StringUtils.LF;
        Context context = this.context;
        Object[] objArr = new Object[4];
        objArr[0] = profile != null ? profile.getCarMarkName() : null;
        objArr[1] = profile != null ? profile.getCarModelName() : null;
        objArr[2] = profile != null ? profile.getCarGenerationName() : null;
        objArr[3] = profile != null ? profile.getCarSerieName() : null;
        String string = context.getString(R.string.text_your_car_info, objArr);
        Intrinsics.checkNotNullExpressionValue(string, "context.getString(\n     …arSerieName\n            )");
        builder.setMessage(str + string).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    private final AlertDialog createErrorMessageAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context, 2131886084);
        builder.setMessage(message).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateCanConfigurationsSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (event.isSuccessful()) {
            EventBus.getDefault().post(new StartCheckEmergencySituationsEvent(true));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateCanConfigurationsErrorEvent event) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (event.isNetworkError()) {
            createNoCanFileOnServerAlertDialog().show();
        } else if (event.isUploadingOnSchemaError()) {
            String string = this.context.getResources().getString(R.string.text_error_write_file);
            Intrinsics.checkNotNullExpressionValue(string, "context.resources.getStr…ng.text_error_write_file)");
            createErrorMessageAlertDialog(string).show();
        }
    }
}
