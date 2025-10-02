package com.thor.app.gui.activities.testers;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.thor.app.managers.UsersManager;
import com.thor.networkmodule.model.firmware.FirmwareProfileList;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;
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
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import timber.log.Timber;

/* compiled from: FirmwareListViewModel.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000  2\u00020\u0001:\u0001 B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u001d\u001a\u00020\u001eJ\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/thor/app/gui/activities/testers/FirmwareListViewModel;", "Landroidx/lifecycle/ViewModel;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "(Lcom/thor/app/managers/UsersManager;)V", "_apiStatusError", "Landroidx/lifecycle/MutableLiveData;", "Lcom/thor/app/gui/activities/testers/FirmwareListViewModel$Companion$ApiStatusError;", "_firmwareList", "Lcom/thor/networkmodule/model/firmware/FirmwareProfileList;", "get_firmwareList", "()Landroidx/lifecycle/MutableLiveData;", "apiStatusError", "Landroidx/lifecycle/LiveData;", "getApiStatusError", "()Landroidx/lifecycle/LiveData;", "isBluetoothConnected", "Landroidx/databinding/ObservableBoolean;", "()Landroidx/databinding/ObservableBoolean;", "password", "", "getPassword", "()Ljava/lang/String;", "setPassword", "(Ljava/lang/String;)V", "rssiSignal", "Landroidx/databinding/ObservableInt;", "getRssiSignal", "()Landroidx/databinding/ObservableInt;", "fetchAllFirmwareList", "", "getFirmwareListLiveData", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class FirmwareListViewModel extends ViewModel {
    private final MutableLiveData<Companion.ApiStatusError> _apiStatusError;
    private final MutableLiveData<FirmwareProfileList> _firmwareList;
    private final LiveData<Companion.ApiStatusError> apiStatusError;
    private final ObservableBoolean isBluetoothConnected;
    private String password;
    private final ObservableInt rssiSignal;
    private final UsersManager usersManager;

    /* JADX WARN: Multi-variable type inference failed */
    @Inject
    public FirmwareListViewModel(UsersManager usersManager) {
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        this.usersManager = usersManager;
        this.password = "";
        this.isBluetoothConnected = new ObservableBoolean(false);
        this.rssiSignal = new ObservableInt(0);
        this._firmwareList = new MutableLiveData<>();
        MutableLiveData<Companion.ApiStatusError> mutableLiveData = new MutableLiveData<>(new Companion.ApiStatusError(false, null, 2, null == true ? 1 : 0));
        this._apiStatusError = mutableLiveData;
        this.apiStatusError = mutableLiveData;
    }

    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.password = str;
    }

    /* renamed from: isBluetoothConnected, reason: from getter */
    public final ObservableBoolean getIsBluetoothConnected() {
        return this.isBluetoothConnected;
    }

    public final ObservableInt getRssiSignal() {
        return this.rssiSignal;
    }

    public final MutableLiveData<FirmwareProfileList> get_firmwareList() {
        return this._firmwareList;
    }

    public final LiveData<Companion.ApiStatusError> getApiStatusError() {
        return this.apiStatusError;
    }

    public final LiveData<FirmwareProfileList> getFirmwareListLiveData() {
        return this._firmwareList;
    }

    /* compiled from: FirmwareListViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.testers.FirmwareListViewModel$fetchAllFirmwareList$1", f = "FirmwareListViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.testers.FirmwareListViewModel$fetchAllFirmwareList$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FirmwareListViewModel.this.new AnonymousClass1(continuation);
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
                Observable<FirmwareProfileList> observableFetchAllFirmwaresList = FirmwareListViewModel.this.usersManager.fetchAllFirmwaresList(FirmwareListViewModel.this.getPassword());
                if (observableFetchAllFirmwaresList != null) {
                    final C00701 c00701 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.testers.FirmwareListViewModel.fetchAllFirmwareList.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<FirmwareProfileList> observableDoOnSubscribe = observableFetchAllFirmwaresList.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.testers.FirmwareListViewModel$fetchAllFirmwareList$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00701.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final FirmwareListViewModel firmwareListViewModel = FirmwareListViewModel.this;
                        final Function1<FirmwareProfileList, Unit> function1 = new Function1<FirmwareProfileList, Unit>() { // from class: com.thor.app.gui.activities.testers.FirmwareListViewModel.fetchAllFirmwareList.1.2
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(FirmwareProfileList firmwareProfileList) {
                                invoke2(firmwareProfileList);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(FirmwareProfileList firmwareProfileList) {
                                firmwareListViewModel.get_firmwareList().postValue(firmwareProfileList);
                            }
                        };
                        Consumer<? super FirmwareProfileList> consumer = new Consumer() { // from class: com.thor.app.gui.activities.testers.FirmwareListViewModel$fetchAllFirmwareList$1$$ExternalSyntheticLambda1
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj2) {
                                function1.invoke(obj2);
                            }
                        };
                        final FirmwareListViewModel firmwareListViewModel2 = FirmwareListViewModel.this;
                        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.testers.FirmwareListViewModel.fetchAllFirmwareList.1.3
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
                                    firmwareListViewModel2._apiStatusError.postValue(new Companion.ApiStatusError(true, th.getMessage()));
                                }
                                Timber.INSTANCE.e(th);
                            }
                        };
                        observableDoOnSubscribe.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.testers.FirmwareListViewModel$fetchAllFirmwareList$1$$ExternalSyntheticLambda2
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

    public final void fetchAllFirmwareList() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new AnonymousClass1(null), 2, null);
    }
}
