package com.thor.app.gui.activities.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivitySplashBinding;
import com.carsystems.thor.datalayermodule.datalayer.SettingsFromService;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.SettingsWearableDataLayer;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.AssistActionBuilder;
import com.google.firebase.messaging.Constants;
import com.thor.app.databinding.model.RunningAppOnPhoneStatus;
import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity;
import com.thor.app.gui.activities.googleautha.GoogleAuthActivity;
import com.thor.app.gui.activities.main.MainActivity;
import com.thor.app.gui.activities.splash.SplashActivityViewModel;
import com.thor.app.gui.activities.splash.VersionState;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.services.ListenerServiceFromWear;
import com.thor.app.settings.CarInfoPreference;
import com.thor.app.settings.Settings;
import com.thor.app.utils.data.DataLayerHelper;
import com.thor.app.utils.security.DeviceLockingUtilsKt;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.businessmodule.bluetooth.model.other.settings.ApplicationDataModel;
import com.thor.networkmodule.model.responses.SignInResponse;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.Regex;
import org.mapstruct.ap.shaded.freemarker.core.Configurable;
import timber.log.Timber;

/* compiled from: SplashActivity.kt */
@Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020 2\b\b\u0001\u0010!\u001a\u00020\"H\u0002J\u001c\u0010#\u001a\u00020$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J&\u0010&\u001a\u00020$2\b\u0010'\u001a\u0004\u0018\u00010(2\b\u0010)\u001a\u0004\u0018\u00010*2\b\u0010+\u001a\u0004\u0018\u00010,H\u0002J\u0010\u0010-\u001a\u00020$2\u0006\u0010.\u001a\u00020/H\u0002J\u0010\u00100\u001a\u00020$2\u0006\u00101\u001a\u000202H\u0002J\b\u00103\u001a\u00020$H\u0002J\u0010\u00104\u001a\u00020$2\u0006\u00105\u001a\u000206H\u0002J\u001a\u00107\u001a\u00020$2\b\u0010+\u001a\u0004\u0018\u00010,2\u0006\u00108\u001a\u00020\u0012H\u0002J\b\u00109\u001a\u00020$H\u0002J\u0012\u0010:\u001a\u00020$2\b\u0010;\u001a\u0004\u0018\u00010<H\u0014J\u0010\u0010=\u001a\u00020$2\u0006\u0010>\u001a\u00020?H\u0016J\b\u0010@\u001a\u00020$H\u0014J\u001c\u0010A\u001a\u00020$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\b\u0010B\u001a\u00020$H\u0014J\b\u0010C\u001a\u00020$H\u0002J\b\u0010D\u001a\u00020$H\u0002J\b\u0010E\u001a\u00020$H\u0002J\b\u0010F\u001a\u00020$H\u0002J\u0016\u0010G\u001a\u00020$*\u00020,2\b\u0010)\u001a\u0004\u0018\u00010*H\u0002R-\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\f\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0018\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\f\u001a\u0004\b\u001b\u0010\u001c¨\u0006H"}, d2 = {"Lcom/thor/app/gui/activities/splash/SplashActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/google/android/gms/wearable/DataClient$OnDataChangedListener;", "()V", "actions", "", "Lkotlin/Pair;", "", "Lkotlin/text/Regex;", "getActions", "()[Lkotlin/Pair;", "actions$delegate", "Lkotlin/Lazy;", "binding", "Lcom/carsystems/thor/app/databinding/ActivitySplashBinding;", "disposable", "Lio/reactivex/disposables/CompositeDisposable;", "isEmergencyUpdate", "", "mViewModel", "Lcom/thor/app/gui/activities/splash/SplashActivityViewModel;", "getMViewModel", "()Lcom/thor/app/gui/activities/splash/SplashActivityViewModel;", "mViewModel$delegate", "reloadIntent", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "getStringByLocale", Configurable.LOCALE_KEY, "Ljava/util/Locale;", TtmlNode.ATTR_ID, "", "handleActionType", "", "action", "handleDeepLink", "data", "Landroid/net/Uri;", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleEvent", "event", "Lcom/thor/app/gui/activities/splash/SplashActivityViewModel$Companion$ApiErrorState;", "handleIntentAndDelay", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "notifyActionSuccess", "succeed", "observeErrors", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDataChanged", "dataEvents", "Lcom/google/android/gms/wearable/DataEventBuffer;", "onStart", "onStartWorker", "onStop", "reAuth", "startScanDevices", "syncDataWithWearable", "updateInfo", "handleIntent", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class SplashActivity extends Hilt_SplashActivity implements DataClient.OnDataChangedListener {
    private ActivitySplashBinding binding;
    private boolean isEmergencyUpdate;

    /* renamed from: mViewModel$delegate, reason: from kotlin metadata */
    private final Lazy mViewModel;
    private boolean reloadIntent;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.splash.SplashActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });
    private final CompositeDisposable disposable = new CompositeDisposable();

    /* renamed from: actions$delegate, reason: from kotlin metadata */
    private final Lazy actions = LazyKt.lazy(new Function0<Pair<? extends String, ? extends Regex>[]>() { // from class: com.thor.app.gui.activities.splash.SplashActivity$actions$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Pair<? extends String, ? extends Regex>[] invoke() {
            String string = this.this$0.getString(R.string.command_turn_off);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.command_turn_off)");
            String string2 = this.this$0.getString(R.string.command_turn_on);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.command_turn_on)");
            String string3 = this.this$0.getString(R.string.command_next_preset);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(R.string.command_next_preset)");
            String string4 = this.this$0.getString(R.string.command_previous_preset);
            Intrinsics.checkNotNullExpressionValue(string4, "getString(R.string.command_previous_preset)");
            return new Pair[]{new Pair<>(BleCommandsWorker.ACTION_TURN_OFF_PRESET, new Regex(string)), new Pair<>(BleCommandsWorker.ACTION_TURN_ON_PRESET, new Regex(string2)), new Pair<>(BleCommandsWorker.ACTION_NEXT_PRESET, new Regex(string3)), new Pair<>(BleCommandsWorker.ACTION_PREVIOUS_PRESET, new Regex(string4))};
        }
    });

    public SplashActivity() {
        final SplashActivity splashActivity = this;
        final Function0 function0 = null;
        this.mViewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(SplashActivityViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.activities.splash.SplashActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = splashActivity.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "viewModelStore");
                return viewModelStore;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.activities.splash.SplashActivity$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = splashActivity.getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.activities.splash.SplashActivity$special$$inlined$viewModels$default$3
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
                CreationExtras defaultViewModelCreationExtras = splashActivity.getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "this.defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        });
    }

    private final SplashActivityViewModel getMViewModel() {
        return (SplashActivityViewModel) this.mViewModel.getValue();
    }

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    private final Pair<String, Regex>[] getActions() {
        return (Pair[]) this.actions.getValue();
    }

    @Override // com.thor.app.gui.activities.splash.Hilt_SplashActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws Throwable {
        SplashActivity splashActivity = this;
        ThemingUtil.INSTANCE.onSplashActivityCreateSetTheme(splashActivity);
        super.onCreate(savedInstanceState);
        SplashActivity splashActivity2 = this;
        new ApplicationDataModel((Context) splashActivity2);
        ViewDataBinding contentView = DataBindingUtil.setContentView(splashActivity, R.layout.activity_splash);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.layout.activity_splash)");
        ActivitySplashBinding activitySplashBinding = (ActivitySplashBinding) contentView;
        this.binding = activitySplashBinding;
        if (activitySplashBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySplashBinding = null;
        }
        activitySplashBinding.setViewModel(getMViewModel());
        this.reloadIntent = getIntent().getBooleanExtra("reload", false);
        updateInfo();
        syncDataWithWearable();
        handleIntentAndDelay();
        getMViewModel().processVersionsFromApi();
        final C02401 c02401 = new C02401(this);
        getMViewModel().getApiErrorState().observe(this, new Observer() { // from class: com.thor.app.gui.activities.splash.SplashActivity$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SplashActivity.onCreate$lambda$0(c02401, obj);
            }
        });
        startForegroundService(new Intent(splashActivity2, (Class<?>) ListenerServiceFromWear.class));
    }

    /* compiled from: SplashActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.splash.SplashActivity$onCreate$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02401 extends FunctionReferenceImpl implements Function1<SplashActivityViewModel.Companion.ApiErrorState, Unit> {
        C02401(Object obj) {
            super(1, obj, SplashActivity.class, "handleEvent", "handleEvent(Lcom/thor/app/gui/activities/splash/SplashActivityViewModel$Companion$ApiErrorState;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SplashActivityViewModel.Companion.ApiErrorState apiErrorState) {
            invoke2(apiErrorState);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SplashActivityViewModel.Companion.ApiErrorState p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SplashActivity) this.receiver).handleEvent(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleEvent(SplashActivityViewModel.Companion.ApiErrorState event) {
        AlertDialog alertDialogCreateErrorAlertDialog;
        if (!Intrinsics.areEqual((Object) event.isError(), (Object) true) || (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, event.getMessage(), 0)) == null) {
            return;
        }
        alertDialogCreateErrorAlertDialog.show();
    }

    private final void observeErrors() {
        final Function1<VersionState, Unit> function1 = new Function1<VersionState, Unit>() { // from class: com.thor.app.gui.activities.splash.SplashActivity.observeErrors.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(VersionState versionState) {
                invoke2(versionState);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(VersionState versionState) {
                if (Intrinsics.areEqual(versionState, VersionState.oldVersion.INSTANCE)) {
                    Toast.makeText(SplashActivity.this, "Error 404", 1).show();
                } else {
                    Toast.makeText(SplashActivity.this, "Unknown error", 1).show();
                }
            }
        };
        getMViewModel().getVersionState().observe(this, new Observer() { // from class: com.thor.app.gui.activities.splash.SplashActivity$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SplashActivity.observeErrors$lambda$1(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeErrors$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void handleIntentAndDelay() throws Throwable {
        Settings.INSTANCE.saveDeviceLockState(false);
        Intent intent = getIntent();
        if (intent != null) {
            handleIntent(intent, this);
        }
        this.disposable.add(Completable.create(new CompletableOnSubscribe() { // from class: com.thor.app.gui.activities.splash.SplashActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.CompletableOnSubscribe
            public final void subscribe(CompletableEmitter completableEmitter) {
                SplashActivity.handleIntentAndDelay$lambda$2(completableEmitter);
            }
        }).delay(2L, TimeUnit.SECONDS).subscribe(new Action() { // from class: com.thor.app.gui.activities.splash.SplashActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                SplashActivity.handleIntentAndDelay$lambda$4(this.f$0);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleIntentAndDelay$lambda$2(CompletableEmitter it) {
        Intrinsics.checkNotNullParameter(it, "it");
        it.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleIntentAndDelay$lambda$4(SplashActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Settings settings = Settings.INSTANCE;
        if (Settings.getUserGoogleUserId().length() == 0) {
            this$0.startActivity(new Intent(this$0, (Class<?>) GoogleAuthActivity.class));
            this$0.finish();
        } else {
            if (settings.isAccessSession()) {
                this$0.reAuth();
                return;
            }
            if (settings.getDeviceMacAddress().length() == 0) {
                this$0.startScanDevices();
            } else {
                this$0.reAuth();
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        Wearable.getDataClient((Activity) this).addListener(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        Wearable.getDataClient((Activity) this).removeListener(this);
    }

    @Override // com.google.android.gms.wearable.DataClient.OnDataChangedListener, com.google.android.gms.wearable.DataApi.DataListener
    public void onDataChanged(DataEventBuffer dataEvents) {
        Intrinsics.checkNotNullParameter(dataEvents, "dataEvents");
        Iterator<DataEvent> it = dataEvents.iterator();
        while (it.hasNext()) {
            DataEvent next = it.next();
            if (next.getType() == 1 && Intrinsics.areEqual(next.getDataItem().getUri().getPath(), SettingsWearableDataLayer.SETTINGS_PATH)) {
                Timber.INSTANCE.i("Received data from SETTINGS_PATH", new Object[0]);
                SettingsFromService settingsFromServiceConvertFromDataItem = SettingsWearableDataLayer.INSTANCE.convertFromDataItem(next.getDataItem());
                Intrinsics.checkNotNullExpressionValue(settingsFromServiceConvertFromDataItem, "SettingsWearableDataLaye…mDataItem(event.dataItem)");
                SettingsFromService settingsFromService = settingsFromServiceConvertFromDataItem;
                Settings.saveUserId(settingsFromService.getResponse().getUserId());
                String token = settingsFromService.getResponse().getToken();
                Intrinsics.checkNotNull(token);
                Settings.saveAccessToken(token);
                Settings.saveProfile(settingsFromService.getResponse());
                Settings.INSTANCE.saveDeviceMacAddress(settingsFromService.getBluetoothDeviceMacAddress());
            }
        }
    }

    private final void updateInfo() {
        getMViewModel().updateSelectedServer(this);
        UsersManager.updateFirmwareProfile$default(getUsersManager(), null, 1, null);
    }

    private final void syncDataWithWearable() {
        SplashActivity splashActivity = this;
        DataLayerManager.INSTANCE.from(splashActivity).sendGetCurrentSettings();
        DataLayerManager.INSTANCE.from(splashActivity).sendIsRunningAppOnPhone(RunningAppOnPhoneStatus.MAIN);
    }

    private final void startScanDevices() {
        startActivity(new Intent(this, (Class<?>) BluetoothDevicesActivity.class));
    }

    private final void reAuth() {
        SignInResponse profile = Settings.INSTANCE.getProfile();
        Observable<SignInResponse> observableSignIn = getUsersManager().signIn(profile != null ? profile.getDeviceSN() : null, false);
        final C02411 c02411 = new C02411(this);
        Consumer<? super SignInResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.splash.SplashActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SplashActivity.reAuth$lambda$5(c02411, obj);
            }
        };
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
        observableSignIn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.splash.SplashActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SplashActivity.reAuth$lambda$6(anonymousClass2, obj);
            }
        });
    }

    /* compiled from: SplashActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.splash.SplashActivity$reAuth$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02411 extends FunctionReferenceImpl implements Function1<SignInResponse, Unit> {
        C02411(Object obj) {
            super(1, obj, SplashActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/SignInResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SignInResponse signInResponse) {
            invoke2(signInResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SignInResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SplashActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SplashActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.splash.SplashActivity$reAuth$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, SplashActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SplashActivity) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void reAuth$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void reAuth$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(SignInResponse response) {
        Timber.INSTANCE.i("handleResponse: %s", response);
        if (response.isSuccessful()) {
            if (response.getUserId() == 0 || TextUtils.isEmpty(response.getToken())) {
                Settings.removeDeviceMacAddress();
                startScanDevices();
                return;
            }
            Settings.saveUserId(response.getUserId());
            String token = response.getToken();
            Intrinsics.checkNotNull(token);
            Settings.saveAccessToken(token);
            CarInfoPreference.INSTANCE.setCarModelID(response.getCarModelId());
            CarInfoPreference.INSTANCE.setCarMarkId(response.getCarMarkId());
            CarInfoPreference.INSTANCE.setCarGenerationId(response.getCarGenerationId());
            CarInfoPreference.INSTANCE.setCarSerieId(response.getCarSerieId());
            Settings.saveProfile(response);
            SplashActivity splashActivity = this;
            DataLayerManager.INSTANCE.from(splashActivity).m558sendurrentAppSettings(response, Settings.INSTANCE.getDeviceMacAddress());
            UsersManager.updateFirmwareProfile$default(getUsersManager(), null, 1, null);
            getUsersManager().fetchSoundPackages();
            DataLayerHelper.newInstance(splashActivity).onStartMainWearableActivity();
            Intent intent = new Intent(splashActivity, (Class<?>) MainActivity.class);
            intent.putExtra("reload", this.reloadIntent);
            startActivity(intent);
            return;
        }
        if (response.getCode() == 888) {
            DeviceLockingUtilsKt.onDeviceLocked(this);
        } else if (Settings.INSTANCE.isAccessSession()) {
            startActivity(new Intent(this, (Class<?>) MainActivity.class));
        } else {
            Settings.removeDeviceMacAddress();
            startScanDevices();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog;
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0);
            if (alertDialogCreateErrorAlertDialog2 != null) {
                alertDialogCreateErrorAlertDialog2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0)) != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        Timber.INSTANCE.e(error);
        if (Settings.INSTANCE.isAccessSession()) {
            startActivity(new Intent(this, (Class<?>) MainActivity.class));
        } else {
            Settings.removeDeviceMacAddress();
            startScanDevices();
        }
    }

    private final void handleIntent(Intent intent, Context context) throws Throwable {
        String action = intent.getAction();
        if (action != null && action.hashCode() == -1173171990 && action.equals("android.intent.action.VIEW")) {
            handleDeepLink(intent.getData(), context, intent);
        }
    }

    private final void handleDeepLink(Uri data, Context context, Intent intent) throws Throwable {
        this.disposable.dispose();
        String path = data != null ? data.getPath() : null;
        boolean z = false;
        z = false;
        z = false;
        if (path != null && path.hashCode() == 1635016485 && path.equals("/action")) {
            String queryParameter = data.getQueryParameter("actionType");
            if (queryParameter == null) {
                queryParameter = "";
            }
            for (Pair<String, Regex> pair : getActions()) {
                Regex second = pair.getSecond();
                Locale locale = Locale.getDefault();
                Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
                String lowerCase = queryParameter.toLowerCase(locale);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                if (second.matches(lowerCase)) {
                    handleActionType(new Pair<>(pair.getFirst(), queryParameter));
                }
            }
            z = true;
        }
        notifyActionSuccess(intent, z);
        finish();
    }

    private final void handleActionType(Pair<String, String> action) throws Throwable {
        if (Settings.INSTANCE.isAccessSession()) {
            UsersManager.updateFirmwareProfile$default(getUsersManager(), null, 1, null);
            getUsersManager().fetchSoundPackages();
            onStartWorker(action);
            return;
        }
        Toast.makeText(this, "You must authorize first", 1).show();
    }

    private final void onStartWorker(Pair<String, String> action) throws Throwable {
        Constraints constraintsBuild = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        Intrinsics.checkNotNullExpressionValue(constraintsBuild, "Builder().setRequiredNet…rkType.CONNECTED).build()");
        Data dataBuild = new Data.Builder().putString(BleCommandsWorker.INPUT_DATA_COMMAND, action.getFirst()).putString(BleCommandsWorker.INPUT_DATA_USER_COMMAND, action.getSecond()).build();
        Intrinsics.checkNotNullExpressionValue(dataBuild, "Builder()\n            .p…ond)\n            .build()");
        OneTimeWorkRequest oneTimeWorkRequestBuild = new OneTimeWorkRequest.Builder(BleCommandsWorker.class).setConstraints(constraintsBuild).setInputData(dataBuild).build();
        Intrinsics.checkNotNullExpressionValue(oneTimeWorkRequestBuild, "Builder(BleCommandsWorke…utData(inputData).build()");
        WorkManager.getInstance().enqueue(oneTimeWorkRequestBuild);
    }

    private final void notifyActionSuccess(Intent intent, boolean succeed) {
        String stringExtra;
        if (intent == null || (stringExtra = intent.getStringExtra("actions.fulfillment.extra.ACTION_TOKEN")) == null) {
            return;
        }
        FirebaseUserActions.getInstance().end(new AssistActionBuilder().setActionToken(stringExtra).setActionStatus(succeed ? "http://schema.org/CompletedActionStatus" : "http://schema.org/FailedActionStatus").build());
    }

    private final String getStringByLocale(Locale locale, int id) throws Resources.NotFoundException {
        Resources resources = getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "resources");
        Configuration configuration = resources.getConfiguration();
        Intrinsics.checkNotNullExpressionValue(configuration, "res.configuration");
        Locale locale2 = configuration.locale;
        configuration.locale = locale;
        resources.updateConfiguration(configuration, null);
        String string = resources.getString(id);
        Intrinsics.checkNotNullExpressionValue(string, "res.getString(id)");
        configuration.locale = locale2;
        resources.updateConfiguration(configuration, null);
        return string;
    }
}
