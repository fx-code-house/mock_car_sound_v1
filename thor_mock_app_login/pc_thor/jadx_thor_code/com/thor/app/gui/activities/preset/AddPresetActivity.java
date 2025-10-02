package com.thor.app.gui.activities.preset;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityAddPresetBinding;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.bus.events.mainpreset.DoneWritePreset;
import com.thor.app.bus.events.mainpreset.FailAddPreset;
import com.thor.app.bus.events.mainpreset.StartWritePreset;
import com.thor.app.bus.events.mainpreset.WriteInstalledPresetsErrorEvent;
import com.thor.app.bus.events.mainpreset.WriteInstalledPresetsSuccessfulEvent;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.networkmodule.model.ModeType;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
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
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: AddPresetActivity.kt */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001cH\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001dH\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001eH\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001fH\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020 H\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020!H\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\"H\u0007J\b\u0010#\u001a\u00020\u0015H\u0014J\b\u0010$\u001a\u00020\u0015H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\n\u001a\u0004\b\u0011\u0010\u0012¨\u0006%"}, d2 = {"Lcom/thor/app/gui/activities/preset/AddPresetActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityAddPresetBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "fileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "mSoundPackageId", "", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "handleSoundPackage", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onMessageEvent", "event", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/app/bus/events/mainpreset/DoneWritePreset;", "Lcom/thor/app/bus/events/mainpreset/FailAddPreset;", "Lcom/thor/app/bus/events/mainpreset/StartWritePreset;", "Lcom/thor/app/bus/events/mainpreset/WriteInstalledPresetsErrorEvent;", "Lcom/thor/app/bus/events/mainpreset/WriteInstalledPresetsSuccessfulEvent;", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "onStart", "onStop", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class AddPresetActivity extends AppCompatActivity {
    private ActivityAddPresetBinding binding;
    private int mSoundPackageId;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity$usersManager$2
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
    private final Lazy bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0);
        }
    });
    private final FileLogger fileLogger = new FileLogger(this, null, 2, null);

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddPresetActivity addPresetActivity = this;
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(addPresetActivity);
        ViewDataBinding contentView = DataBindingUtil.setContentView(addPresetActivity, R.layout.activity_add_preset);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.layout.activity_add_preset)");
        ActivityAddPresetBinding activityAddPresetBinding = (ActivityAddPresetBinding) contentView;
        this.binding = activityAddPresetBinding;
        ActivityAddPresetBinding activityAddPresetBinding2 = null;
        if (activityAddPresetBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityAddPresetBinding = null;
        }
        activityAddPresetBinding.toolbarWidget.setHomeButtonVisibility(true);
        ActivityAddPresetBinding activityAddPresetBinding3 = this.binding;
        if (activityAddPresetBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityAddPresetBinding3 = null;
        }
        activityAddPresetBinding3.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AddPresetActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        ActivityAddPresetBinding activityAddPresetBinding4 = this.binding;
        if (activityAddPresetBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityAddPresetBinding2 = activityAddPresetBinding4;
        }
        activityAddPresetBinding2.toolbarWidget.enableBluetoothIndicator(true);
        handleSoundPackage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(AddPresetActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() throws SecurityException {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private final void handleSoundPackage() {
        ShopSoundPackage shopSoundPackage = (ShopSoundPackage) getIntent().getParcelableExtra(ShopSoundPackage.INSTANCE.getBUNDLE_NAME());
        ActivityAddPresetBinding activityAddPresetBinding = null;
        if ((shopSoundPackage != null ? shopSoundPackage.getModeImages() : null) != null) {
            ArrayList<ModeType> modeImages = shopSoundPackage.getModeImages();
            Integer numValueOf = modeImages != null ? Integer.valueOf(modeImages.size()) : null;
            Intrinsics.checkNotNull(numValueOf);
            if (numValueOf.intValue() >= 3) {
                this.mSoundPackageId = shopSoundPackage.getId();
                Timber.INSTANCE.i("SoundPackage: %s", shopSoundPackage);
                ActivityAddPresetBinding activityAddPresetBinding2 = this.binding;
                if (activityAddPresetBinding2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activityAddPresetBinding2 = null;
                }
                activityAddPresetBinding2.viewCity.setSoundPackage(shopSoundPackage, 1);
                ActivityAddPresetBinding activityAddPresetBinding3 = this.binding;
                if (activityAddPresetBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activityAddPresetBinding3 = null;
                }
                activityAddPresetBinding3.viewSport.setSoundPackage(shopSoundPackage, 2);
                ActivityAddPresetBinding activityAddPresetBinding4 = this.binding;
                if (activityAddPresetBinding4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activityAddPresetBinding = activityAddPresetBinding4;
                }
                activityAddPresetBinding.viewOwn.setSoundPackage(shopSoundPackage, 3);
                return;
            }
        }
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(WriteInstalledPresetsSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Toast.makeText(this, R.string.message_write_installed_presets_successful, 1).show();
        Observable<BaseResponse> observableObserveOn = getUsersManager().sendStatisticsAboutAddSoundPackage(this.mSoundPackageId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity.onMessageEvent.1
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
                AlertDialog alertDialogCreateErrorAlertDialog;
                if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(AddPresetActivity.this, baseResponse.getError(), baseResponse.getCode())) != null) {
                    alertDialogCreateErrorAlertDialog.show();
                }
                Timber.INSTANCE.i(baseResponse.toString(), new Object[0]);
            }
        };
        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AddPresetActivity.onMessageEvent$lambda$1(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity.onMessageEvent.2
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
                if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                    AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, AddPresetActivity.this, th.getMessage(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialog$default2 != null) {
                        alertDialogCreateErrorAlertDialog$default2.show();
                    }
                } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, AddPresetActivity.this, th.getMessage(), null, 4, null)) != null) {
                    alertDialogCreateErrorAlertDialog$default.show();
                }
                Timber.INSTANCE.e(th);
            }
        };
        observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AddPresetActivity.onMessageEvent$lambda$2(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(WriteInstalledPresetsErrorEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Toast.makeText(this, R.string.message_write_installed_presets_error, 1).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(FailAddPreset event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        ActivityAddPresetBinding activityAddPresetBinding = this.binding;
        if (activityAddPresetBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityAddPresetBinding = null;
        }
        activityAddPresetBinding.progressBar.setVisibility(8);
        Toast.makeText(this, R.string.message_write_installed_presets_error, 1).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(DoneWritePreset event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ActivityAddPresetBinding activityAddPresetBinding = this.binding;
        if (activityAddPresetBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityAddPresetBinding = null;
        }
        activityAddPresetBinding.progressBar.setVisibility(8);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(StartWritePreset event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ActivityAddPresetBinding activityAddPresetBinding = this.binding;
        if (activityAddPresetBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityAddPresetBinding = null;
        }
        activityAddPresetBinding.progressBar.setVisibility(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BleDataRequestLogEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG", "<= D " + event.getDataDeCrypto() + " <= E " + event.getDataCrypto());
        this.fileLogger.i("<= D " + event.getDataDeCrypto(), new Object[0]);
        this.fileLogger.i("<= E " + event.getDataCrypto(), new Object[0]);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BleDataResponseLogEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG", "=> D " + event.getDataDeCrypto() + " => E " + event.getDataCrypto());
        this.fileLogger.i("=> D " + event.getDataDeCrypto(), new Object[0]);
        this.fileLogger.i("=> E " + event.getDataCrypto(), new Object[0]);
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        AddPresetActivity addPresetActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(addPresetActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, addPresetActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new AnonymousClass3(uriForFile, event, null), 2, null);
    }

    /* compiled from: AddPresetActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.preset.AddPresetActivity$onMessageEvent$3", f = "AddPresetActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.preset.AddPresetActivity$onMessageEvent$3, reason: invalid class name */
    static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AddPresetActivity.this.new AnonymousClass3(this.$path, this.$event, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = AddPresetActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final AnonymousClass1 anonymousClass1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity.onMessageEvent.3.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity$onMessageEvent$3$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            anonymousClass1.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final AnonymousClass2 anonymousClass2 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity.onMessageEvent.3.2
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
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.preset.AddPresetActivity$onMessageEvent$3$$ExternalSyntheticLambda1
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
