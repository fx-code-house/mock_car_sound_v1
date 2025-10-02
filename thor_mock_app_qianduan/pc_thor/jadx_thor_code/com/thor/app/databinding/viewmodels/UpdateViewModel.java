package com.thor.app.databinding.viewmodels;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.carsystems.thor.app.BuildConfig;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.auto.media.AutoNotificationManagerKt;
import com.thor.app.gui.activities.splash.VersionState;
import com.thor.app.gui.activities.updatecheck.UpdateResult;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.dao.CurrentVersionDao;
import com.thor.app.room.entity.CurrentVersionsEntity;
import com.thor.app.settings.Settings;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.versions.CurrentAppVersion;
import com.thor.networkmodule.network.ApiServiceNew;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import retrofit2.Response;
import timber.log.Timber;

/* compiled from: UpdateViewModel.kt */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 22\u00020\u0001:\u00012B+\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0001\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\u0016\u0010#\u001a\u00020\u001e2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020%0$H\u0002J\u0006\u0010&\u001a\u00020\u001eJ\u0010\u0010'\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020)H\u0002J\u000e\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u0011J\u000e\u0010,\u001a\u00020\u001e2\u0006\u0010-\u001a\u00020.J\u000e\u0010/\u001a\u00020\u001e2\u0006\u00100\u001a\u00020\u0011J\u0006\u00101\u001a\u00020\u001eR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017¨\u00063"}, d2 = {"Lcom/thor/app/databinding/viewmodels/UpdateViewModel;", "Landroidx/lifecycle/ViewModel;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "currentVersion", "Lcom/thor/app/room/dao/CurrentVersionDao;", "apiNewService", "Lcom/thor/networkmodule/network/ApiServiceNew;", "context", "Landroid/content/Context;", "(Lcom/thor/app/managers/UsersManager;Lcom/thor/app/room/dao/CurrentVersionDao;Lcom/thor/networkmodule/network/ApiServiceNew;Landroid/content/Context;)V", "_apiErrorStatus", "Landroidx/lifecycle/MutableLiveData;", "Lcom/thor/app/databinding/viewmodels/UpdateViewModel$Companion$ApiErrorState;", "_isUpdateSuccessfully", "Lcom/thor/app/gui/activities/updatecheck/UpdateResult;", "_showUpdateDialog", "", "_versionState", "Lcom/thor/app/gui/activities/splash/VersionState;", "apiErrorState", "Landroidx/lifecycle/LiveData;", "getApiErrorState", "()Landroidx/lifecycle/LiveData;", "isUpdateSuccessfully", "showUpdateDialog", "getShowUpdateDialog", "versionState", "getVersionState", "clearUpdateDialog", "", "convertStringToInt", "", "inputString", "", "handleResponseFromDB", "", "Lcom/thor/app/room/entity/CurrentVersionsEntity;", "loadedAppVersionFromApi", "parseAppVersionApiResult", TtmlNode.TAG_BODY, "Lcom/thor/networkmodule/model/responses/versions/CurrentAppVersion;", "postUpdateResultEvent", "isSuccessful", "sendLogs", "uri", "Landroid/net/Uri;", "setUpdateResult", "result", "versionGet", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UpdateViewModel extends ViewModel {
    private final MutableLiveData<Companion.ApiErrorState> _apiErrorStatus;
    private final MutableLiveData<UpdateResult> _isUpdateSuccessfully;
    private final MutableLiveData<Boolean> _showUpdateDialog;
    private final MutableLiveData<VersionState> _versionState;
    private final LiveData<Companion.ApiErrorState> apiErrorState;
    private final ApiServiceNew apiNewService;
    private final Context context;
    private final CurrentVersionDao currentVersion;
    private final LiveData<UpdateResult> isUpdateSuccessfully;
    private final LiveData<Boolean> showUpdateDialog;
    private final UsersManager usersManager;
    private final LiveData<VersionState> versionState;

    /* JADX WARN: Multi-variable type inference failed */
    @Inject
    public UpdateViewModel(UsersManager usersManager, CurrentVersionDao currentVersion, @Named("new_api_service") ApiServiceNew apiNewService, @ApplicationContext Context context) {
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        Intrinsics.checkNotNullParameter(currentVersion, "currentVersion");
        Intrinsics.checkNotNullParameter(apiNewService, "apiNewService");
        Intrinsics.checkNotNullParameter(context, "context");
        this.usersManager = usersManager;
        this.currentVersion = currentVersion;
        this.apiNewService = apiNewService;
        this.context = context;
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        this._showUpdateDialog = mutableLiveData;
        this.showUpdateDialog = mutableLiveData;
        MutableLiveData<VersionState> mutableLiveData2 = new MutableLiveData<>();
        this._versionState = mutableLiveData2;
        this.versionState = mutableLiveData2;
        MutableLiveData<UpdateResult> mutableLiveData3 = new MutableLiveData<>();
        this._isUpdateSuccessfully = mutableLiveData3;
        this.isUpdateSuccessfully = mutableLiveData3;
        MutableLiveData<Companion.ApiErrorState> mutableLiveData4 = new MutableLiveData<>(new Companion.ApiErrorState(false, null, 2, null == true ? 1 : 0));
        this._apiErrorStatus = mutableLiveData4;
        this.apiErrorState = mutableLiveData4;
    }

    public final LiveData<Boolean> getShowUpdateDialog() {
        return this.showUpdateDialog;
    }

    public final LiveData<VersionState> getVersionState() {
        return this.versionState;
    }

    public final LiveData<UpdateResult> isUpdateSuccessfully() {
        return this.isUpdateSuccessfully;
    }

    public final LiveData<Companion.ApiErrorState> getApiErrorState() {
        return this.apiErrorState;
    }

    public final void setUpdateResult(boolean result) {
        if (result) {
            this._isUpdateSuccessfully.postValue(UpdateResult.Success.INSTANCE);
        } else {
            this._isUpdateSuccessfully.postValue(UpdateResult.Failure.INSTANCE);
        }
    }

    /* compiled from: UpdateViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.databinding.viewmodels.UpdateViewModel$versionGet$1", f = "UpdateViewModel.kt", i = {}, l = {61}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.databinding.viewmodels.UpdateViewModel$versionGet$1, reason: invalid class name and case insensitive filesystem */
    static final class C01421 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        C01421(Continuation<? super C01421> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UpdateViewModel.this.new C01421(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01421) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            UpdateViewModel updateViewModel;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                UpdateViewModel updateViewModel2 = UpdateViewModel.this;
                this.L$0 = updateViewModel2;
                this.label = 1;
                Object currentVersion = updateViewModel2.currentVersion.getCurrentVersion(this);
                if (currentVersion == coroutine_suspended) {
                    return coroutine_suspended;
                }
                updateViewModel = updateViewModel2;
                obj = currentVersion;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                updateViewModel = (UpdateViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            updateViewModel.handleResponseFromDB((List) obj);
            return Unit.INSTANCE;
        }
    }

    public final void versionGet() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C01421(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int convertStringToInt(String inputString) {
        return Integer.parseInt(StringsKt.replace$default(inputString, ".", "", false, 4, (Object) null));
    }

    public final void postUpdateResultEvent(boolean isSuccessful) {
        if (!isSuccessful) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C01401(null), 3, null);
        } else {
            this._versionState.postValue(VersionState.actualVersion.INSTANCE);
        }
    }

    /* compiled from: UpdateViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.databinding.viewmodels.UpdateViewModel$postUpdateResultEvent$1", f = "UpdateViewModel.kt", i = {}, l = {75}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.databinding.viewmodels.UpdateViewModel$postUpdateResultEvent$1, reason: invalid class name and case insensitive filesystem */
    static final class C01401 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C01401(Continuation<? super C01401> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UpdateViewModel.this.new C01401(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01401) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(1000L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            UpdateViewModel.this._versionState.postValue(VersionState.oldVersion.INSTANCE);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: UpdateViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.databinding.viewmodels.UpdateViewModel$handleResponseFromDB$1", f = "UpdateViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.databinding.viewmodels.UpdateViewModel$handleResponseFromDB$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<CurrentVersionsEntity> $currentVersion;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(List<CurrentVersionsEntity> list, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$currentVersion = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UpdateViewModel.this.new AnonymousClass1(this.$currentVersion, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object next;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Settings.getHardwareProfile();
            UpdateViewModel.this.convertStringToInt(BuildConfig.VERSION_NAME);
            Iterator<T> it = this.$currentVersion.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                String versionOfHardware = ((CurrentVersionsEntity) next).getVersionOfHardware();
                if (versionOfHardware != null && Short.parseShort(versionOfHardware) == 1538) {
                    break;
                }
            }
            CurrentVersionsEntity currentVersionsEntity = (CurrentVersionsEntity) next;
            if (currentVersionsEntity == null) {
                Log.i("UpdateViewModel", "devices == null");
                UpdateViewModel.this._versionState.postValue(VersionState.oldVersion.INSTANCE);
                return Unit.INSTANCE;
            }
            String versionOfFirmware = currentVersionsEntity.getVersionOfFirmware();
            if ((versionOfFirmware != null ? Integer.parseInt(versionOfFirmware) : 0) <= 521) {
                UpdateViewModel.this._versionState.postValue(VersionState.actualVersion.INSTANCE);
                return Unit.INSTANCE;
            }
            Log.i("UpdateViewModel", "devices.versionOfFirmware > versionFirmware");
            UpdateViewModel.this._versionState.postValue(VersionState.oldVersion.INSTANCE);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponseFromDB(List<CurrentVersionsEntity> currentVersion) {
        this._versionState.postValue(VersionState.None.INSTANCE);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(currentVersion, null), 3, null);
    }

    /* compiled from: UpdateViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.databinding.viewmodels.UpdateViewModel$sendLogs$1", f = "UpdateViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.databinding.viewmodels.UpdateViewModel$sendLogs$1, reason: invalid class name and case insensitive filesystem */
    static final class C01411 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01411(Uri uri, Continuation<? super C01411> continuation) {
            super(2, continuation);
            this.$uri = uri;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UpdateViewModel.this.new C01411(this.$uri, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01411) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendLogsToApi = UpdateViewModel.this.usersManager.sendLogsToApi(this.$uri, FileLogger.LOGS_FILE_NAME);
                if (observableSendLogsToApi != null) {
                    final C00551 c00551 = new Function1<Disposable, Unit>() { // from class: com.thor.app.databinding.viewmodels.UpdateViewModel.sendLogs.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendLogsToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.databinding.viewmodels.UpdateViewModel$sendLogs$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00551.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final UpdateViewModel updateViewModel = UpdateViewModel.this;
                        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.databinding.viewmodels.UpdateViewModel.sendLogs.1.2
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
                                updateViewModel.usersManager.deleteTempLogsFromCacheDirectory();
                                Timber.INSTANCE.i("Response: %s", baseResponse);
                                if (baseResponse.getStatus()) {
                                    return;
                                }
                                updateViewModel._apiErrorStatus.postValue(new Companion.ApiErrorState(true, baseResponse.getError()));
                                Timber.INSTANCE.e("Response error: %s", baseResponse.getError());
                            }
                        };
                        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.databinding.viewmodels.UpdateViewModel$sendLogs$1$$ExternalSyntheticLambda1
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj2) {
                                function1.invoke(obj2);
                            }
                        };
                        final UpdateViewModel updateViewModel2 = UpdateViewModel.this;
                        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.databinding.viewmodels.UpdateViewModel.sendLogs.1.3
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
                                    updateViewModel2._apiErrorStatus.postValue(new Companion.ApiErrorState(true, th.getMessage()));
                                }
                                updateViewModel2.usersManager.deleteTempLogsFromCacheDirectory();
                                Timber.INSTANCE.e(th);
                            }
                        };
                        observableDoOnSubscribe.subscribe(consumer, new Consumer() { // from class: com.thor.app.databinding.viewmodels.UpdateViewModel$sendLogs$1$$ExternalSyntheticLambda2
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
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C01411(uri, null), 2, null);
    }

    /* compiled from: UpdateViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.databinding.viewmodels.UpdateViewModel$loadedAppVersionFromApi$1", f = "UpdateViewModel.kt", i = {}, l = {AutoNotificationManagerKt.NOTIFICATION_LARGE_ICON_SIZE}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.databinding.viewmodels.UpdateViewModel$loadedAppVersionFromApi$1, reason: invalid class name and case insensitive filesystem */
    static final class C01391 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C01391(Continuation<? super C01391> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UpdateViewModel.this.new C01391(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Response response;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            boolean z = true;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.label = 1;
                    obj = UpdateViewModel.this.apiNewService.getAppVersionsFromApi(this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                response = (Response) obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!response.isSuccessful()) {
                return Unit.INSTANCE;
            }
            CurrentAppVersion currentAppVersion = (CurrentAppVersion) response.body();
            if (currentAppVersion == null || currentAppVersion.getStatus()) {
                z = false;
            }
            if (z) {
                return Unit.INSTANCE;
            }
            CurrentAppVersion currentAppVersion2 = (CurrentAppVersion) response.body();
            if (currentAppVersion2 != null) {
                UpdateViewModel.this.parseAppVersionApiResult(currentAppVersion2);
            }
            return Unit.INSTANCE;
        }
    }

    public final void loadedAppVersionFromApi() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C01391(null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void parseAppVersionApiResult(CurrentAppVersion body) {
        if (convertStringToInt(BuildConfig.VERSION_NAME) < convertStringToInt(body.getVersions().getCurrent().getAndroid())) {
            this._showUpdateDialog.postValue(true);
        }
    }

    public final void clearUpdateDialog() {
        this._showUpdateDialog.postValue(false);
    }
}
