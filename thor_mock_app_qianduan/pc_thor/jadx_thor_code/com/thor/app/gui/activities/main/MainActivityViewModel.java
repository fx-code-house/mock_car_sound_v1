package com.thor.app.gui.activities.main;

import android.net.Uri;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.messaging.Constants;
import com.thor.app.gui.activities.main.MainActivityViewModel;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.dao.CurrentVersionDao;
import com.thor.app.settings.Settings;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.networkmodule.model.notifications.NotificationInfo;
import com.thor.networkmodule.model.notifications.NotificationType;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.NotificationsResponse;
import com.thor.networkmodule.model.responses.PasswordValidationResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundConfig;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import timber.log.Timber;

/* compiled from: MainActivityViewModel.kt */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 J2\u00020\u0001:\u0001JB\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010>\u001a\u00020\u001dJ\u0012\u0010?\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t05J\b\u0010@\u001a\u00020\u001dH\u0002J\u0010\u0010A\u001a\u00020\u001d2\u0006\u0010B\u001a\u00020CH\u0002J\u000e\u0010D\u001a\u00020\u001d2\u0006\u0010E\u001a\u000209J\b\u0010F\u001a\u00020\u001dH\u0014J\u000e\u0010G\u001a\u00020\u001d2\u0006\u0010H\u001a\u00020IR\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0013\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0014\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\u0019\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0011R\"\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R7\u0010\"\u001a\u001f\u0012\u0013\u0012\u00110$¢\u0006\f\b%\u0012\b\b&\u0012\u0004\b\b('\u0012\u0004\u0012\u00020\u001d\u0018\u00010#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0017\u00101\u001a\b\u0012\u0004\u0012\u0002020-¢\u0006\b\n\u0000\u001a\u0004\b3\u00100R\u0017\u00104\u001a\b\u0012\u0004\u0012\u00020\f05¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u001a\u00108\u001a\u000209X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006K"}, d2 = {"Lcom/thor/app/gui/activities/main/MainActivityViewModel;", "Landroidx/lifecycle/ViewModel;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "currentVersion", "Lcom/thor/app/room/dao/CurrentVersionDao;", "(Lcom/thor/app/managers/UsersManager;Lcom/thor/app/room/dao/CurrentVersionDao;)V", "_notificationsList", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/thor/networkmodule/model/notifications/NotificationInfo;", "_uiEvent", "Lcom/thor/app/gui/activities/main/MainActivityViewModel$Companion$UiState;", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "isBleConnected", "Landroidx/databinding/ObservableBoolean;", "()Landroidx/databinding/ObservableBoolean;", "isBluetoothConnected", "isInstalledPresets", "isInstalledSgu", "isSguDriveSelect", "", "()Landroidx/lifecycle/MutableLiveData;", "isSguSoundConfig", "moreInfo", "getMoreInfo", "onFailurePasswordValidation", "Lkotlin/Function0;", "", "getOnFailurePasswordValidation", "()Lkotlin/jvm/functions/Function0;", "setOnFailurePasswordValidation", "(Lkotlin/jvm/functions/Function0;)V", "onSuccessPasswordValidation", "Lkotlin/Function1;", "Lcom/thor/networkmodule/model/responses/PasswordValidationResponse;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "result", "getOnSuccessPasswordValidation", "()Lkotlin/jvm/functions/Function1;", "setOnSuccessPasswordValidation", "(Lkotlin/jvm/functions/Function1;)V", "rssiLevel", "Landroidx/databinding/ObservableField;", "", "getRssiLevel", "()Landroidx/databinding/ObservableField;", "sguConfig", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundConfig;", "getSguConfig", "uiEvent", "Landroidx/lifecycle/LiveData;", "getUiEvent", "()Landroidx/lifecycle/LiveData;", "updateFirmwareText", "", "getUpdateFirmwareText", "()Ljava/lang/String;", "setUpdateFirmwareText", "(Ljava/lang/String;)V", "fetchAllNotifications", "getNotificationsListLiveData", "getTempDataToNotificationList", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "logsPasswordValidationResponse", "password", "onCleared", "sendLogs", "uri", "Landroid/net/Uri;", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<List<NotificationInfo>> _notificationsList;
    private final MutableLiveData<Companion.UiState> _uiEvent;
    private final CompositeDisposable compositeDisposable;
    private final CurrentVersionDao currentVersion;
    private final ObservableBoolean isBleConnected;
    private final ObservableBoolean isBluetoothConnected;
    private final ObservableBoolean isInstalledPresets;
    private final ObservableBoolean isInstalledSgu;
    private final MutableLiveData<Boolean> isSguDriveSelect;
    private final ObservableBoolean isSguSoundConfig;
    private final ObservableBoolean moreInfo;
    private Function0<Unit> onFailurePasswordValidation;
    private Function1<? super PasswordValidationResponse, Unit> onSuccessPasswordValidation;
    private final ObservableField<Integer> rssiLevel;
    private final ObservableField<SguSoundConfig> sguConfig;
    private final LiveData<Companion.UiState> uiEvent;
    private String updateFirmwareText;
    private final UsersManager usersManager;

    /* JADX WARN: Multi-variable type inference failed */
    @Inject
    public MainActivityViewModel(UsersManager usersManager, CurrentVersionDao currentVersion) {
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        Intrinsics.checkNotNullParameter(currentVersion, "currentVersion");
        this.usersManager = usersManager;
        this.currentVersion = currentVersion;
        this.isBluetoothConnected = new ObservableBoolean(false);
        this.moreInfo = new ObservableBoolean(true);
        this.isBleConnected = new ObservableBoolean(false);
        this.isInstalledPresets = new ObservableBoolean(false);
        this.isInstalledSgu = new ObservableBoolean(false);
        this.rssiLevel = new ObservableField<>(0);
        this.isSguSoundConfig = new ObservableBoolean(false);
        this.isSguDriveSelect = new MutableLiveData<>(false);
        this.sguConfig = new ObservableField<>(new SguSoundConfig((short) 0, (short) 0, (short) 0, 7, null));
        MutableLiveData<Companion.UiState> mutableLiveData = new MutableLiveData<>(new Companion.UiState(false, null, 2, null == true ? 1 : 0));
        this._uiEvent = mutableLiveData;
        this.uiEvent = mutableLiveData;
        this._notificationsList = new MutableLiveData<>();
        this.updateFirmwareText = "";
        this.compositeDisposable = new CompositeDisposable();
    }

    /* renamed from: isBluetoothConnected, reason: from getter */
    public final ObservableBoolean getIsBluetoothConnected() {
        return this.isBluetoothConnected;
    }

    public final ObservableBoolean getMoreInfo() {
        return this.moreInfo;
    }

    /* renamed from: isBleConnected, reason: from getter */
    public final ObservableBoolean getIsBleConnected() {
        return this.isBleConnected;
    }

    /* renamed from: isInstalledPresets, reason: from getter */
    public final ObservableBoolean getIsInstalledPresets() {
        return this.isInstalledPresets;
    }

    /* renamed from: isInstalledSgu, reason: from getter */
    public final ObservableBoolean getIsInstalledSgu() {
        return this.isInstalledSgu;
    }

    public final ObservableField<Integer> getRssiLevel() {
        return this.rssiLevel;
    }

    /* renamed from: isSguSoundConfig, reason: from getter */
    public final ObservableBoolean getIsSguSoundConfig() {
        return this.isSguSoundConfig;
    }

    public final MutableLiveData<Boolean> isSguDriveSelect() {
        return this.isSguDriveSelect;
    }

    public final ObservableField<SguSoundConfig> getSguConfig() {
        return this.sguConfig;
    }

    public final LiveData<Companion.UiState> getUiEvent() {
        return this.uiEvent;
    }

    public final String getUpdateFirmwareText() {
        return this.updateFirmwareText;
    }

    public final void setUpdateFirmwareText(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.updateFirmwareText = str;
    }

    public final Function1<PasswordValidationResponse, Unit> getOnSuccessPasswordValidation() {
        return this.onSuccessPasswordValidation;
    }

    public final void setOnSuccessPasswordValidation(Function1<? super PasswordValidationResponse, Unit> function1) {
        this.onSuccessPasswordValidation = function1;
    }

    public final Function0<Unit> getOnFailurePasswordValidation() {
        return this.onFailurePasswordValidation;
    }

    public final void setOnFailurePasswordValidation(Function0<Unit> function0) {
        this.onFailurePasswordValidation = function0;
    }

    /* compiled from: MainActivityViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.main.MainActivityViewModel$sendLogs$1", f = "MainActivityViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.main.MainActivityViewModel$sendLogs$1, reason: invalid class name and case insensitive filesystem */
    static final class C01781 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01781(Uri uri, Continuation<? super C01781> continuation) {
            super(2, continuation);
            this.$uri = uri;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainActivityViewModel.this.new C01781(this.$uri, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01781) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendLogsToApi = MainActivityViewModel.this.usersManager.sendLogsToApi(this.$uri, FileLogger.LOGS_FILE_NAME);
                if (observableSendLogsToApi != null) {
                    final C00601 c00601 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel.sendLogs.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendLogsToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel$sendLogs$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00601.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final MainActivityViewModel mainActivityViewModel = MainActivityViewModel.this;
                        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel.sendLogs.1.2
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
                                mainActivityViewModel.usersManager.deleteTempLogsFromCacheDirectory();
                                Timber.INSTANCE.i("Response: %s", baseResponse);
                                if (baseResponse.getStatus()) {
                                    return;
                                }
                                mainActivityViewModel._uiEvent.postValue(new Companion.UiState(true, baseResponse.getError()));
                                Timber.INSTANCE.e("Response error: %s", baseResponse.getError());
                            }
                        };
                        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel$sendLogs$1$$ExternalSyntheticLambda1
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj2) {
                                function1.invoke(obj2);
                            }
                        };
                        final MainActivityViewModel mainActivityViewModel2 = MainActivityViewModel.this;
                        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel.sendLogs.1.3
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
                                if (Intrinsics.areEqual(th.getMessage(), "HTTP 400") || Intrinsics.areEqual(th.getMessage(), "HTTP 500")) {
                                    mainActivityViewModel2._uiEvent.postValue(new Companion.UiState(true, th.getMessage()));
                                }
                                mainActivityViewModel2.usersManager.deleteTempLogsFromCacheDirectory();
                                Timber.INSTANCE.e(th);
                            }
                        };
                        observableDoOnSubscribe.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel$sendLogs$1$$ExternalSyntheticLambda2
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj2) {
                                function12.invoke(obj2);
                            }
                        });
                    }
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final void sendLogs(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C01781(uri, null), 2, null);
    }

    public final LiveData<List<NotificationInfo>> getNotificationsListLiveData() {
        return this._notificationsList;
    }

    public final void fetchAllNotifications() {
        getTempDataToNotificationList();
    }

    public final void logsPasswordValidationResponse(String password) {
        Intrinsics.checkNotNullParameter(password, "password");
        Observable<PasswordValidationResponse> observableObserveOn = this.usersManager.logsPasswordValidationResponse(password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        final Function1<PasswordValidationResponse, Unit> function1 = new Function1<PasswordValidationResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel$logsPasswordValidationResponse$result$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PasswordValidationResponse passwordValidationResponse) {
                invoke2(passwordValidationResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(PasswordValidationResponse it) {
                if (Intrinsics.areEqual((Object) it.getStatus(), (Object) false)) {
                    this.this$0._uiEvent.postValue(new MainActivityViewModel.Companion.UiState(true, it.getError_description()));
                }
                Function1<PasswordValidationResponse, Unit> onSuccessPasswordValidation = this.this$0.getOnSuccessPasswordValidation();
                if (onSuccessPasswordValidation != null) {
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    onSuccessPasswordValidation.invoke(it);
                }
            }
        };
        Consumer<? super PasswordValidationResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivityViewModel.logsPasswordValidationResponse$lambda$0(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel$logsPasswordValidationResponse$result$2
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
                if (Intrinsics.areEqual(th.getMessage(), "HTTP 400") || Intrinsics.areEqual(th.getMessage(), "HTTP 500")) {
                    this.this$0._uiEvent.postValue(new MainActivityViewModel.Companion.UiState(true, th.getMessage()));
                }
                Function0<Unit> onFailurePasswordValidation = this.this$0.getOnFailurePasswordValidation();
                if (onFailurePasswordValidation != null) {
                    onFailurePasswordValidation.invoke();
                }
            }
        };
        this.compositeDisposable.add(observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivityViewModel.logsPasswordValidationResponse$lambda$1(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logsPasswordValidationResponse$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logsPasswordValidationResponse$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.util.List] */
    private final void getTempDataToNotificationList() {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new ArrayList();
        if (Settings.INSTANCE.getIsNeedToUpdateFirmware()) {
            ((List) objectRef.element).add(new NotificationInfo(0, this.updateFirmwareText, "Доступна новая версия прошивки для вашего блока", "", "firmware", false, null, NotificationType.TYPE_FIRMWARE));
        }
        this._notificationsList.postValue(objectRef.element);
        Observable<NotificationsResponse> observableFetchUserNotifications = this.usersManager.fetchUserNotifications();
        if (observableFetchUserNotifications != null) {
            final Function1<NotificationsResponse, Unit> function1 = new Function1<NotificationsResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel.getTempDataToNotificationList.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(NotificationsResponse notificationsResponse) {
                    invoke2(notificationsResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(NotificationsResponse notificationsResponse) {
                    if (!notificationsResponse.isSuccessful()) {
                        this._uiEvent.postValue(new Companion.UiState(true, notificationsResponse.getError()));
                        return;
                    }
                    List<NotificationInfo> events = notificationsResponse.getEvents();
                    if (events != null) {
                        objectRef.element.addAll(events);
                    }
                    for (NotificationInfo notificationInfo : objectRef.element) {
                        notificationInfo.setNotificationType(NotificationType.INSTANCE.fromString(notificationInfo.getFormat()));
                    }
                    this._notificationsList.postValue(objectRef.element);
                }
            };
            Consumer<? super NotificationsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainActivityViewModel.getTempDataToNotificationList$lambda$2(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel.getTempDataToNotificationList.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    MainActivityViewModel mainActivityViewModel = MainActivityViewModel.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    mainActivityViewModel.handleError(it);
                }
            };
            Disposable disposableSubscribe = observableFetchUserNotifications.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivityViewModel$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainActivityViewModel.getTempDataToNotificationList$lambda$3(function12, obj);
                }
            });
            if (disposableSubscribe != null) {
                this.usersManager.addDisposable(disposableSubscribe);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getTempDataToNotificationList$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getTempDataToNotificationList$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400") || Intrinsics.areEqual(error.getMessage(), "HTTP 500")) {
            this._uiEvent.postValue(new Companion.UiState(true, error.getMessage()));
        }
        Timber.INSTANCE.e(error);
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        super.onCleared();
        this.compositeDisposable.dispose();
    }
}
