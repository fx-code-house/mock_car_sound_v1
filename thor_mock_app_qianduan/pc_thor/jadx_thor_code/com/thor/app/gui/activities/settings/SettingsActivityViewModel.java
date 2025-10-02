package com.thor.app.gui.activities.settings;

import android.net.Uri;
import android.util.Log;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.thor.app.gui.activities.settings.SettingsActivityViewModel;
import com.thor.app.managers.UsersManager;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.PasswordValidationResponse;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* compiled from: SettingsActivityViewModel.kt */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020(J\b\u0010)\u001a\u00020\u0013H\u0014J\u0016\u0010*\u001a\u00020\u00132\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020(R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0010R\"\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R7\u0010\u0018\u001a\u001f\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\u001b\u0012\b\b\u001c\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lcom/thor/app/gui/activities/settings/SettingsActivityViewModel;", "Landroidx/lifecycle/ViewModel;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "(Lcom/thor/app/managers/UsersManager;)V", "_apiStatusError", "Landroidx/lifecycle/MutableLiveData;", "Lcom/thor/app/gui/activities/settings/SettingsActivityViewModel$Companion$ApiStatusError;", "apiStatusError", "Landroidx/lifecycle/LiveData;", "getApiStatusError", "()Landroidx/lifecycle/LiveData;", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "isBluetoothConnected", "Landroidx/databinding/ObservableBoolean;", "()Landroidx/databinding/ObservableBoolean;", "onFailurePasswordValidation", "Lkotlin/Function0;", "", "getOnFailurePasswordValidation", "()Lkotlin/jvm/functions/Function0;", "setOnFailurePasswordValidation", "(Lkotlin/jvm/functions/Function0;)V", "onSuccessPasswordValidation", "Lkotlin/Function1;", "Lcom/thor/networkmodule/model/responses/PasswordValidationResponse;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "result", "getOnSuccessPasswordValidation", "()Lkotlin/jvm/functions/Function1;", "setOnSuccessPasswordValidation", "(Lkotlin/jvm/functions/Function1;)V", "rssiSignal", "Landroidx/databinding/ObservableInt;", "getRssiSignal", "()Landroidx/databinding/ObservableInt;", "changeServerPasswordValidationResponse", "password", "", "onCleared", "sendErrorLog", "uri", "Landroid/net/Uri;", "message", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SettingsActivityViewModel extends ViewModel {
    private final MutableLiveData<Companion.ApiStatusError> _apiStatusError;
    private final LiveData<Companion.ApiStatusError> apiStatusError;
    private final CompositeDisposable compositeDisposable;
    private final ObservableBoolean isBluetoothConnected;
    private Function0<Unit> onFailurePasswordValidation;
    private Function1<? super PasswordValidationResponse, Unit> onSuccessPasswordValidation;
    private final ObservableInt rssiSignal;
    private final UsersManager usersManager;

    /* JADX WARN: Multi-variable type inference failed */
    @Inject
    public SettingsActivityViewModel(UsersManager usersManager) {
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        this.usersManager = usersManager;
        this.isBluetoothConnected = new ObservableBoolean(false);
        this.rssiSignal = new ObservableInt(0);
        MutableLiveData<Companion.ApiStatusError> mutableLiveData = new MutableLiveData<>(new Companion.ApiStatusError(false, null, 2, null == true ? 1 : 0));
        this._apiStatusError = mutableLiveData;
        this.apiStatusError = mutableLiveData;
        this.compositeDisposable = new CompositeDisposable();
    }

    /* renamed from: isBluetoothConnected, reason: from getter */
    public final ObservableBoolean getIsBluetoothConnected() {
        return this.isBluetoothConnected;
    }

    public final ObservableInt getRssiSignal() {
        return this.rssiSignal;
    }

    public final LiveData<Companion.ApiStatusError> getApiStatusError() {
        return this.apiStatusError;
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

    public final void changeServerPasswordValidationResponse(String password) {
        Intrinsics.checkNotNullParameter(password, "password");
        Observable<PasswordValidationResponse> observableObserveOn = this.usersManager.logsPasswordValidationResponse(password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        final Function1<PasswordValidationResponse, Unit> function1 = new Function1<PasswordValidationResponse, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivityViewModel$changeServerPasswordValidationResponse$result$1
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
                    this.this$0._apiStatusError.postValue(new SettingsActivityViewModel.Companion.ApiStatusError(true, it.getError_description()));
                }
                Function1<PasswordValidationResponse, Unit> onSuccessPasswordValidation = this.this$0.getOnSuccessPasswordValidation();
                if (onSuccessPasswordValidation != null) {
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    onSuccessPasswordValidation.invoke(it);
                }
            }
        };
        Consumer<? super PasswordValidationResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivityViewModel$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivityViewModel.changeServerPasswordValidationResponse$lambda$0(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivityViewModel$changeServerPasswordValidationResponse$result$2
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
                    this.this$0._apiStatusError.postValue(new SettingsActivityViewModel.Companion.ApiStatusError(true, th.getMessage()));
                }
                Function0<Unit> onFailurePasswordValidation = this.this$0.getOnFailurePasswordValidation();
                if (onFailurePasswordValidation != null) {
                    onFailurePasswordValidation.invoke();
                }
            }
        };
        this.compositeDisposable.add(observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivityViewModel$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivityViewModel.changeServerPasswordValidationResponse$lambda$1(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void changeServerPasswordValidationResponse$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void changeServerPasswordValidationResponse$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: SettingsActivityViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.settings.SettingsActivityViewModel$sendErrorLog$1", f = "SettingsActivityViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.settings.SettingsActivityViewModel$sendErrorLog$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $message;
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Uri uri, String str, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$message = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SettingsActivityViewModel.this.new AnonymousClass1(this.$uri, this.$message, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = SettingsActivityViewModel.this.usersManager.sendErrorLogToApi(this.$uri, this.$message);
                if (observableSendErrorLogToApi != null) {
                    final C00661 c00661 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivityViewModel.sendErrorLog.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivityViewModel$sendErrorLog$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00661.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final AnonymousClass2 anonymousClass2 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivityViewModel.sendErrorLog.1.2
                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                                invoke2(baseResponse);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(BaseResponse baseResponse) {
                                Log.i("SEND_LOGS", "Response: " + baseResponse);
                            }
                        };
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivityViewModel$sendErrorLog$1$$ExternalSyntheticLambda1
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj2) {
                                anonymousClass2.invoke(obj2);
                            }
                        });
                    }
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final void sendErrorLog(Uri uri, String message) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(message, "message");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new AnonymousClass1(uri, message, null), 2, null);
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        super.onCleared();
        this.compositeDisposable.dispose();
    }
}
