package com.thor.app.gui.activities.testers;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityFirmwareListBinding;
import com.google.firebase.messaging.Constants;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.gui.activities.main.MainActivity;
import com.thor.app.gui.activities.testers.FirmwareListViewModel;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.fragments.firmwares.FirmwareListFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.businessmodule.bus.events.ResponseErrorCodeEvent;
import com.thor.networkmodule.model.responses.BaseResponse;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt;
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
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: FirmwareListActivity.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001dH\u0007J\u0012\u0010\u001e\u001a\u00020\u00162\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\"H\u0007J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020#H\u0007J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020$H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\n\u001a\u0004\b\u0012\u0010\u0013¨\u0006%"}, d2 = {"Lcom/thor/app/gui/activities/testers/FirmwareListActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityFirmwareListBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "viewModel", "Lcom/thor/app/gui/activities/testers/FirmwareListViewModel;", "getViewModel", "()Lcom/thor/app/gui/activities/testers/FirmwareListViewModel;", "viewModel$delegate", "handleError", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleEvent", "event", "Lcom/thor/app/gui/activities/testers/FirmwareListViewModel$Companion$ApiStatusError;", "omMessageEvent", "Lcom/thor/businessmodule/bus/events/ResponseErrorCodeEvent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onMessageEvent", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class FirmwareListActivity extends Hilt_FirmwareListActivity {
    private ActivityFirmwareListBinding binding;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0);
        }
    });

    public FirmwareListActivity() {
        final FirmwareListActivity firmwareListActivity = this;
        final Function0 function0 = null;
        this.viewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(FirmwareListViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = firmwareListActivity.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "viewModelStore");
                return viewModelStore;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = firmwareListActivity.getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity$special$$inlined$viewModels$default$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function02 = function0;
                if (function02 != null && (creationExtras = (CreationExtras) function02.invoke()) != null) {
                    return creationExtras;
                }
                CreationExtras defaultViewModelCreationExtras = firmwareListActivity.getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "this.defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        });
    }

    private final FirmwareListViewModel getViewModel() {
        return (FirmwareListViewModel) this.viewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    @Override // com.thor.app.gui.activities.testers.Hilt_FirmwareListActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws SecurityException {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_firmware_list);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.l…t.activity_firmware_list)");
        ActivityFirmwareListBinding activityFirmwareListBinding = (ActivityFirmwareListBinding) contentView;
        this.binding = activityFirmwareListBinding;
        if (activityFirmwareListBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityFirmwareListBinding = null;
        }
        activityFirmwareListBinding.toolbarWidget.enableBluetoothIndicator(true);
        ActivityFirmwareListBinding activityFirmwareListBinding2 = this.binding;
        if (activityFirmwareListBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityFirmwareListBinding2 = null;
        }
        activityFirmwareListBinding2.toolbarWidget.setHomeButtonVisibility(true);
        ActivityFirmwareListBinding activityFirmwareListBinding3 = this.binding;
        if (activityFirmwareListBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityFirmwareListBinding3 = null;
        }
        activityFirmwareListBinding3.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FirmwareListActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        ActivityFirmwareListBinding activityFirmwareListBinding4 = this.binding;
        if (activityFirmwareListBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityFirmwareListBinding4 = null;
        }
        activityFirmwareListBinding4.toolbarWidget.setTitle("Firmware list");
        FirmwareListViewModel viewModel = getViewModel();
        Bundle extras = getIntent().getExtras();
        String str = (String) (extras != null ? extras.getSerializable(MainActivity.FIRMWARE_PASSWORD) : null);
        if (str == null) {
            str = "";
        }
        viewModel.setPassword(str);
        getViewModel().getIsBluetoothConnected().set(getBleManager().isBleEnabledAndDeviceConnected());
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
        getViewModel().getApiStatusError().observe(this, new Observer() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FirmwareListActivity.onCreate$lambda$1(anonymousClass2, obj);
            }
        });
        getSupportFragmentManager().beginTransaction().disallowAddToBackStack().setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim).replace(R.id.fragment_container, FirmwareListFragment.INSTANCE.newInstance(getIntent().getAction())).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(FirmwareListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* compiled from: FirmwareListActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.testers.FirmwareListActivity$onCreate$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<FirmwareListViewModel.Companion.ApiStatusError, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, FirmwareListActivity.class, "handleEvent", "handleEvent(Lcom/thor/app/gui/activities/testers/FirmwareListViewModel$Companion$ApiStatusError;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(FirmwareListViewModel.Companion.ApiStatusError apiStatusError) {
            invoke2(apiStatusError);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(FirmwareListViewModel.Companion.ApiStatusError p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((FirmwareListActivity) this.receiver).handleEvent(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleEvent(FirmwareListViewModel.Companion.ApiStatusError event) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        if (!Intrinsics.areEqual((Object) event.isError(), (Object) true) || (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, event.getMessage(), null, 4, null)) == null) {
            return;
        }
        alertDialogCreateErrorAlertDialog$default.show();
    }

    private final void handleError(Throwable error) {
        Timber.INSTANCE.e(error);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void omMessageEvent(ResponseErrorCodeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        AlertDialog alertDialogCreateDialogBordError = DialogManager.INSTANCE.createDialogBordError(this, BleHelper.takeShort(event.getByteArray()));
        if (alertDialogCreateDialogBordError != null) {
            alertDialogCreateDialogBordError.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BleDataRequestLogEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG", "<= D " + event.getDataDeCrypto() + " <= E " + event.getDataCrypto());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BleDataResponseLogEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG", "=> D " + event.getDataDeCrypto() + " => E " + event.getDataCrypto());
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        FirmwareListActivity firmwareListActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(firmwareListActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, firmwareListActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new AnonymousClass1(uriForFile, event, null), 2, null);
    }

    /* compiled from: FirmwareListActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.testers.FirmwareListActivity$onMessageEvent$1", f = "FirmwareListActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.testers.FirmwareListActivity$onMessageEvent$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FirmwareListActivity.this.new AnonymousClass1(this.$path, this.$event, continuation);
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
                Observable<BaseResponse> observableSendErrorLogToApi = FirmwareListActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final C00691 c00691 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity.onMessageEvent.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity$onMessageEvent$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00691.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final AnonymousClass2 anonymousClass2 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity.onMessageEvent.1.2
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
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.testers.FirmwareListActivity$onMessageEvent$1$$ExternalSyntheticLambda1
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
}
