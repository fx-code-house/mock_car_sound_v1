package com.thor.app.gui.activities.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivitySettingsBinding;
import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.gms.common.util.Hex;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.thor.app.bus.events.BadUserEvents;
import com.thor.app.bus.events.BluetoothDeviceRssiEvent;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.bus.events.shop.main.DownloadSettingsFileSuccessEvent;
import com.thor.app.databinding.model.DriveSelectStatus;
import com.thor.app.gui.activities.main.carinfo.ChangeCarActivity;
import com.thor.app.gui.activities.settings.SettingsActivity;
import com.thor.app.gui.activities.settings.SettingsActivityViewModel;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.dialogs.CarInfoDialogFragment;
import com.thor.app.gui.dialog.dialogs.ProgressDialogFragment;
import com.thor.app.gui.dialog.dialogs.TestersDialogFragment;
import com.thor.app.gui.dialog.dialogs.TipDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.CarManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.settings.Settings;
import com.thor.app.utils.data.DataLayerHelper;
import com.thor.app.utils.extensions.ImageViewKt;
import com.thor.app.utils.extensions.ViewKt;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.basemodule.extensions.NumberKt;
import com.thor.businessmodule.bluetooth.model.other.DeviceSettings;
import com.thor.businessmodule.bluetooth.model.other.DriveMode;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.bluetooth.model.other.settings.DeviceStatus;
import com.thor.businessmodule.bluetooth.response.other.ReadDriveModesBleResponse;
import com.thor.businessmodule.bluetooth.response.other.ReadSettingsBleResponse;
import com.thor.businessmodule.bluetooth.response.other.WriteSettingsBleResponse;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.businessmodule.model.TypeDialog;
import com.thor.businessmodule.settings.Constants;
import com.thor.networkmodule.model.CanFile;
import com.thor.networkmodule.model.ChangeCarInfo;
import com.thor.networkmodule.model.DriveSelect;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.CanConfigurationsResponse;
import com.thor.networkmodule.model.responses.DriveSelectResponse;
import com.thor.networkmodule.model.responses.PasswordValidationResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.utils.ConnectivityAndInternetAccess;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: SettingsActivity.kt */
@Metadata(d1 = {"\u0000È\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J\b\u0010*\u001a\u00020'H\u0002J\b\u0010+\u001a\u00020'H\u0002J\b\u0010,\u001a\u00020'H\u0002J\b\u0010-\u001a\u00020'H\u0002J\u0018\u0010.\u001a\u00020'2\u000e\u0010/\u001a\n\u0012\u0004\u0012\u000201\u0018\u000100H\u0002J\u0010\u00102\u001a\u00020'2\u0006\u00103\u001a\u000204H\u0002J\u0010\u00105\u001a\u00020'2\u0006\u00106\u001a\u000207H\u0002J\b\u00108\u001a\u00020'H\u0002J\b\u00109\u001a\u00020'H\u0002J\b\u0010:\u001a\u00020'H\u0002J\u0010\u0010;\u001a\u00020'2\u0006\u00103\u001a\u00020<H\u0007J\b\u0010=\u001a\u00020'H\u0002J\u0012\u0010>\u001a\u00020'2\b\u0010?\u001a\u0004\u0018\u00010@H\u0014J\u0010\u0010A\u001a\u00020'2\u0006\u0010B\u001a\u00020CH\u0002J\b\u0010D\u001a\u00020'H\u0003J\u0012\u0010E\u001a\u00020'2\b\u00103\u001a\u0004\u0018\u00010FH\u0007J\u0010\u0010E\u001a\u00020'2\u0006\u00103\u001a\u00020GH\u0007J\u0010\u0010E\u001a\u00020'2\u0006\u00103\u001a\u00020HH\u0007J\u0010\u0010E\u001a\u00020'2\u0006\u00103\u001a\u00020IH\u0007J\u0010\u0010E\u001a\u00020'2\u0006\u00103\u001a\u00020JH\u0007J\u0010\u0010K\u001a\u00020'2\u0006\u0010B\u001a\u00020CH\u0002J\u0010\u0010L\u001a\u00020'2\u0006\u0010B\u001a\u00020CH\u0002J\u0010\u0010M\u001a\u00020'2\u0006\u0010B\u001a\u00020CH\u0002J\b\u0010N\u001a\u00020'H\u0014J\b\u0010O\u001a\u00020'H\u0014J\u0010\u0010P\u001a\u00020'2\u0006\u0010B\u001a\u00020CH\u0002J\u0010\u0010Q\u001a\u00020'2\u0006\u0010R\u001a\u00020SH\u0002J\b\u0010T\u001a\u00020'H\u0002J\b\u0010U\u001a\u00020'H\u0002J\u0010\u0010V\u001a\u00020'2\u0006\u0010W\u001a\u00020XH\u0002J\u0018\u0010Y\u001a\u0012\u0012\u0004\u0012\u00020[0Zj\b\u0012\u0004\u0012\u00020[`\\H\u0002J\b\u0010]\u001a\u00020'H\u0002J\b\u0010^\u001a\u00020'H\u0002J\b\u0010_\u001a\u00020'H\u0002J\u001e\u0010`\u001a\u00020'2\n\b\u0002\u0010a\u001a\u0004\u0018\u00010b2\b\b\u0002\u0010c\u001a\u00020\u0011H\u0002J\b\u0010d\u001a\u00020'H\u0002J\b\u0010e\u001a\u00020'H\u0002J\b\u0010f\u001a\u00020'H\u0002J\b\u0010g\u001a\u00020'H\u0002J\b\u0010h\u001a\u00020'H\u0002J\b\u0010i\u001a\u00020'H\u0002J\b\u0010j\u001a\u00020'H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\n\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0017\u001a\u00020\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\n\u001a\u0004\b\u0019\u0010\u001aR\u001b\u0010\u001c\u001a\u00020\u001d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\n\u001a\u0004\b\u001e\u0010\u001fR\u001b\u0010!\u001a\u00020\"8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\n\u001a\u0004\b#\u0010$¨\u0006k"}, d2 = {"Lcom/thor/app/gui/activities/settings/SettingsActivity;", "Lcom/thor/app/gui/activities/EmergencySituationBaseActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivitySettingsBinding;", "database", "Lcom/thor/app/room/ThorDatabase;", "getDatabase", "()Lcom/thor/app/room/ThorDatabase;", "database$delegate", "Lkotlin/Lazy;", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "isCarInfoDialogShown", "", "mDataLayerHelper", "Lcom/thor/app/utils/data/DataLayerHelper;", "getMDataLayerHelper", "()Lcom/thor/app/utils/data/DataLayerHelper;", "mDataLayerHelper$delegate", "progressDialog", "Lcom/thor/app/gui/dialog/dialogs/ProgressDialogFragment;", "getProgressDialog", "()Lcom/thor/app/gui/dialog/dialogs/ProgressDialogFragment;", "progressDialog$delegate", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "viewModel", "Lcom/thor/app/gui/activities/settings/SettingsActivityViewModel;", "getViewModel", "()Lcom/thor/app/gui/activities/settings/SettingsActivityViewModel;", "viewModel$delegate", "changeServerPasswordValidation", "", "password", "", "checkHardware", "fetchDeviceParameters", "fetchDeviceSettings", "fetchServerCanInfo", "handleDriveSelect", "driveSelects", "", "Lcom/thor/networkmodule/model/DriveSelect;", "handleEvent", "event", "Lcom/thor/app/gui/activities/settings/SettingsActivityViewModel$Companion$ApiStatusError;", "handleReadSettingsResponse", "response", "Lcom/thor/businessmodule/bluetooth/response/other/ReadSettingsBleResponse;", "handleTipStates", "hideProgress", "logout", "omMessageEvent", "Lcom/thor/app/bus/events/BadUserEvents;", "onChangeCar", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDriveSelectClick", "view", "Landroid/view/View;", "onFullLogout", "onMessageEvent", "Lcom/thor/app/bus/events/BluetoothDeviceRssiEvent;", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/app/bus/events/shop/main/DownloadSettingsFileSuccessEvent;", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "onMyAutoClick", "onNativeButtonControlClick", "onNewDeviceClick", "onPause", "onResume", "onTwoSpeakerInstalledClick", "openCanFileInfoDialog", "model", "Lcom/thor/networkmodule/model/CanFile;", "openChangeCarScreen", "openContactSupportDialog", "openTipDialog", SessionDescription.ATTR_TYPE, "Lcom/thor/businessmodule/model/TypeDialog;", "prepareDeviceSettings", "Ljava/util/ArrayList;", "Lcom/thor/businessmodule/bluetooth/model/other/DeviceSettings$Setting;", "Lkotlin/collections/ArrayList;", "setupClickListeners", "setupRssiListener", "setupToolbar", "showCarInfoDialog", "carInfo", "Lcom/thor/networkmodule/model/responses/CanConfigurationsResponse;", "isNoCanFile", "showEnterPasswordDialog", "showLogoutDialog", "showNoInternetConnection", "showProgress", "showServerSelectDialog", "tryFetchDriveSelect", "writeSettings", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class SettingsActivity extends Hilt_SettingsActivity {
    private ActivitySettingsBinding binding;
    private boolean isCarInfoDialogShown;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* renamed from: mDataLayerHelper$delegate, reason: from kotlin metadata */
    private final Lazy mDataLayerHelper = LazyKt.lazy(new Function0<DataLayerHelper>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$mDataLayerHelper$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final DataLayerHelper invoke() {
            return DataLayerHelper.newInstance(this.this$0);
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: database$delegate, reason: from kotlin metadata */
    private final Lazy database = LazyKt.lazy(new Function0<ThorDatabase>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$database$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThorDatabase invoke() {
            return ThorDatabase.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: progressDialog$delegate, reason: from kotlin metadata */
    private final Lazy progressDialog = LazyKt.lazy(new Function0<ProgressDialogFragment>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$progressDialog$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ProgressDialogFragment invoke() {
            return ProgressDialogFragment.newInstance();
        }
    });

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler = LazyKt.lazy(new Function0<Handler>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$handler$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Handler invoke() {
            return new Handler(Looper.getMainLooper());
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupClickListeners$lambda$2(View view) {
        return true;
    }

    public SettingsActivity() {
        final SettingsActivity settingsActivity = this;
        final Function0 function0 = null;
        this.viewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(SettingsActivityViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = settingsActivity.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "viewModelStore");
                return viewModelStore;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = settingsActivity.getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$special$$inlined$viewModels$default$3
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
                CreationExtras defaultViewModelCreationExtras = settingsActivity.getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "this.defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DataLayerHelper getMDataLayerHelper() {
        Object value = this.mDataLayerHelper.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mDataLayerHelper>(...)");
        return (DataLayerHelper) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    private final ThorDatabase getDatabase() {
        return (ThorDatabase) this.database.getValue();
    }

    private final ProgressDialogFragment getProgressDialog() {
        Object value = this.progressDialog.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-progressDialog>(...)");
        return (ProgressDialogFragment) value;
    }

    private final SettingsActivityViewModel getViewModel() {
        return (SettingsActivityViewModel) this.viewModel.getValue();
    }

    private final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(this);
        ActivitySettingsBinding activitySettingsBindingInflate = ActivitySettingsBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activitySettingsBindingInflate, "inflate(layoutInflater)");
        this.binding = activitySettingsBindingInflate;
        if (activitySettingsBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBindingInflate = null;
        }
        setContentView(activitySettingsBindingInflate.getRoot());
        setupToolbar();
        setupClickListeners();
        setupRssiListener();
        handleTipStates();
        final C02121 c02121 = new C02121(this);
        getViewModel().getApiStatusError().observe(this, new Observer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SettingsActivity.onCreate$lambda$0(c02121, obj);
            }
        });
    }

    /* compiled from: SettingsActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.settings.SettingsActivity$onCreate$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02121 extends FunctionReferenceImpl implements Function1<SettingsActivityViewModel.Companion.ApiStatusError, Unit> {
        C02121(Object obj) {
            super(1, obj, SettingsActivity.class, "handleEvent", "handleEvent(Lcom/thor/app/gui/activities/settings/SettingsActivityViewModel$Companion$ApiStatusError;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SettingsActivityViewModel.Companion.ApiStatusError apiStatusError) {
            invoke2(apiStatusError);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SettingsActivityViewModel.Companion.ApiStatusError p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SettingsActivity) this.receiver).handleEvent(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleEvent(SettingsActivityViewModel.Companion.ApiStatusError event) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        if (!Intrinsics.areEqual((Object) event.isError(), (Object) true) || (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, event.getMessage(), null, 4, null)) == null) {
            return;
        }
        alertDialogCreateErrorAlertDialog$default.show();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        checkHardware();
        fetchDeviceSettings();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        writeSettings();
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    private final void handleTipStates() {
        ActivitySettingsBinding activitySettingsBinding = null;
        if (Settings.INSTANCE.getDriveSelectTipState()) {
            ActivitySettingsBinding activitySettingsBinding2 = this.binding;
            if (activitySettingsBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySettingsBinding2 = null;
            }
            AppCompatImageButton appCompatImageButton = activitySettingsBinding2.driveSelectTip;
            Intrinsics.checkNotNullExpressionValue(appCompatImageButton, "binding.driveSelectTip");
            ImageViewKt.setSvgColor(appCompatImageButton, R.color.colorText_dialog);
        }
        if (Settings.INSTANCE.getNativeControlTipState()) {
            ActivitySettingsBinding activitySettingsBinding3 = this.binding;
            if (activitySettingsBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySettingsBinding = activitySettingsBinding3;
            }
            AppCompatImageButton appCompatImageButton2 = activitySettingsBinding.nativeControlTip;
            Intrinsics.checkNotNullExpressionValue(appCompatImageButton2, "binding.nativeControlTip");
            ImageViewKt.setSvgColor(appCompatImageButton2, R.color.colorText_dialog);
        }
    }

    private final void setupToolbar() {
        ActivitySettingsBinding activitySettingsBinding = this.binding;
        ActivitySettingsBinding activitySettingsBinding2 = null;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        activitySettingsBinding.toolbarWidget.setHomeButtonVisibility(true);
        ActivitySettingsBinding activitySettingsBinding3 = this.binding;
        if (activitySettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding3 = null;
        }
        activitySettingsBinding3.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupToolbar$lambda$1(this.f$0, view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding4 = this.binding;
        if (activitySettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySettingsBinding2 = activitySettingsBinding4;
        }
        activitySettingsBinding2.toolbarWidget.enableBluetoothIndicator(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupToolbar$lambda$1(SettingsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void setupRssiListener() {
        ActivitySettingsBinding activitySettingsBinding = this.binding;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        activitySettingsBinding.setModel(getViewModel());
        getViewModel().getIsBluetoothConnected().set(getBleManager().isBleEnabledAndDeviceConnected());
        getViewModel().getRssiSignal().set(getBleManager().getRssiLevel());
    }

    private final void setupClickListeners() {
        ActivitySettingsBinding activitySettingsBinding = this.binding;
        ActivitySettingsBinding activitySettingsBinding2 = null;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        LinearLayout linearLayout = activitySettingsBinding.actionNewDevice;
        Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.actionNewDevice");
        ViewKt.setOnVeryLongClickListener(linearLayout, 10000L, new C02171(this), false);
        ActivitySettingsBinding activitySettingsBinding3 = this.binding;
        if (activitySettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding3 = null;
        }
        activitySettingsBinding3.actionNewDevice.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onNewDeviceClick(view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding4 = this.binding;
        if (activitySettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding4 = null;
        }
        activitySettingsBinding4.actionNewDevice.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return SettingsActivity.setupClickListeners$lambda$2(view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding5 = this.binding;
        if (activitySettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding5 = null;
        }
        activitySettingsBinding5.actionMyAuto.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onMyAutoClick(view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding6 = this.binding;
        if (activitySettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding6 = null;
        }
        activitySettingsBinding6.actionDriveSelect.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onDriveSelectClick(view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding7 = this.binding;
        if (activitySettingsBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding7 = null;
        }
        activitySettingsBinding7.actionNativeButtonControl.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onNativeButtonControlClick(view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding8 = this.binding;
        if (activitySettingsBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding8 = null;
        }
        activitySettingsBinding8.actionTwoSpeakerInstalled.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onTwoSpeakerInstalledClick(view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding9 = this.binding;
        if (activitySettingsBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding9 = null;
        }
        activitySettingsBinding9.driveSelectTip.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupClickListeners$lambda$3(this.f$0, view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding10 = this.binding;
        if (activitySettingsBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding10 = null;
        }
        activitySettingsBinding10.nativeControlTip.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupClickListeners$lambda$4(this.f$0, view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding11 = this.binding;
        if (activitySettingsBinding11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding11 = null;
        }
        activitySettingsBinding11.formatFlashTip.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupClickListeners$lambda$5(this.f$0, view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding12 = this.binding;
        if (activitySettingsBinding12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySettingsBinding2 = activitySettingsBinding12;
        }
        activitySettingsBinding2.actionFormatFlashButton.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupClickListeners$lambda$6(this.f$0, view);
            }
        });
    }

    /* compiled from: SettingsActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.settings.SettingsActivity$setupClickListeners$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02171 extends FunctionReferenceImpl implements Function0<Unit> {
        C02171(Object obj) {
            super(0, obj, SettingsActivity.class, "showEnterPasswordDialog", "showEnterPasswordDialog()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((SettingsActivity) this.receiver).showEnterPasswordDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupClickListeners$lambda$3(SettingsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.openTipDialog(TypeDialog.SHOW_DRIVE_SELECT_TIP);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupClickListeners$lambda$4(SettingsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.openTipDialog(TypeDialog.SHOW_NATIVE_CONTROL_TIP);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupClickListeners$lambda$5(SettingsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.openTipDialog(TypeDialog.FORMAT_FLASH_TIP);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupClickListeners$lambda$6(SettingsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.createFormatFlashAlertDialog(false).show();
    }

    private final void checkHardware() {
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        ActivitySettingsBinding activitySettingsBinding = null;
        Integer numValueOf = hardwareProfile != null ? Integer.valueOf(hardwareProfile.getVersionFirmware()) : null;
        if (numValueOf != null) {
            numValueOf.intValue();
            if (numValueOf.intValue() >= 391) {
                tryFetchDriveSelect();
                ActivitySettingsBinding activitySettingsBinding2 = this.binding;
                if (activitySettingsBinding2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activitySettingsBinding = activitySettingsBinding2;
                }
                activitySettingsBinding.actionNativeButtonControl.setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onNewDeviceClick(View view) {
        if (ConnectivityAndInternetAccess.INSTANCE.isConnected(this)) {
            showLogoutDialog();
        } else {
            showNoInternetConnection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onMyAutoClick(View view) {
        if (getBleManager().isBleEnabledAndDeviceConnected()) {
            ActivitySettingsBinding activitySettingsBinding = this.binding;
            if (activitySettingsBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySettingsBinding = null;
            }
            activitySettingsBinding.progressBar.setVisibility(0);
            getBleManager().executeInitCrypto(null);
            getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity.onMyAutoClick$lambda$8(this.f$0);
                }
            }, 3000L);
            return;
        }
        fetchServerCanInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMyAutoClick$lambda$8(SettingsActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivitySettingsBinding activitySettingsBinding = this$0.binding;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        activitySettingsBinding.progressBar.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onDriveSelectClick(View view) {
        ActivitySettingsBinding activitySettingsBinding = this.binding;
        ActivitySettingsBinding activitySettingsBinding2 = null;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        SwitchMaterial switchMaterial = activitySettingsBinding.driveSelectSwitch;
        ActivitySettingsBinding activitySettingsBinding3 = this.binding;
        if (activitySettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding3 = null;
        }
        switchMaterial.setChecked(!activitySettingsBinding3.driveSelectSwitch.isChecked());
        ActivitySettingsBinding activitySettingsBinding4 = this.binding;
        if (activitySettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySettingsBinding2 = activitySettingsBinding4;
        }
        if (activitySettingsBinding2.driveSelectSwitch.isChecked()) {
            DialogManager.INSTANCE.createDriveSelectInfoAlertDialog(this, new Function0<Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.onDriveSelectClick.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }
            }).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onNativeButtonControlClick(View view) {
        ActivitySettingsBinding activitySettingsBinding = this.binding;
        ActivitySettingsBinding activitySettingsBinding2 = null;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        SwitchMaterial switchMaterial = activitySettingsBinding.nativeControlSwitch;
        ActivitySettingsBinding activitySettingsBinding3 = this.binding;
        if (activitySettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySettingsBinding2 = activitySettingsBinding3;
        }
        switchMaterial.setChecked(!activitySettingsBinding2.nativeControlSwitch.isChecked());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onTwoSpeakerInstalledClick(View view) {
        ActivitySettingsBinding activitySettingsBinding = this.binding;
        ActivitySettingsBinding activitySettingsBinding2 = null;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        SwitchMaterial switchMaterial = activitySettingsBinding.twoSpeakerSwitch;
        ActivitySettingsBinding activitySettingsBinding3 = this.binding;
        if (activitySettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySettingsBinding2 = activitySettingsBinding3;
        }
        switchMaterial.setChecked(!activitySettingsBinding2.twoSpeakerSwitch.isChecked());
    }

    private final void openTipDialog(final TypeDialog type) {
        TipDialogFragment tipDialogFragment = new TipDialogFragment(type);
        tipDialogFragment.setOnConfirmListener(new OnConfirmDialogListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$openTipDialog$$inlined$doOnConfirm$1
            @Override // com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener
            public void onConfirm(String info) {
                ActivitySettingsBinding activitySettingsBinding = null;
                if (type == TypeDialog.SHOW_NATIVE_CONTROL_TIP) {
                    Settings.INSTANCE.saveNativeControlTipState(true);
                    ActivitySettingsBinding activitySettingsBinding2 = this.binding;
                    if (activitySettingsBinding2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        activitySettingsBinding2 = null;
                    }
                    AppCompatImageButton appCompatImageButton = activitySettingsBinding2.nativeControlTip;
                    Intrinsics.checkNotNullExpressionValue(appCompatImageButton, "binding.nativeControlTip");
                    ImageViewKt.setSvgColor(appCompatImageButton, R.color.colorText_dialog);
                }
                if (type == TypeDialog.SHOW_DRIVE_SELECT_TIP) {
                    Settings.INSTANCE.saveDriveSelectTipState(true);
                    ActivitySettingsBinding activitySettingsBinding3 = this.binding;
                    if (activitySettingsBinding3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        activitySettingsBinding = activitySettingsBinding3;
                    }
                    AppCompatImageButton appCompatImageButton2 = activitySettingsBinding.driveSelectTip;
                    Intrinsics.checkNotNullExpressionValue(appCompatImageButton2, "binding.driveSelectTip");
                    ImageViewKt.setSvgColor(appCompatImageButton2, R.color.colorText_dialog);
                }
            }
        });
        tipDialogFragment.show(getSupportFragmentManager(), tipDialogFragment.getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void logout() {
        Settings.saveIsCheckedEmrgencySituations(false);
        Observable<BaseResponse> observableRemoveNotification = getUsersManager().removeNotification();
        if (observableRemoveNotification != null) {
            final C02101 c02101 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.logout.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                    invoke2(baseResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(BaseResponse baseResponse) {
                    Timber.INSTANCE.i("Response: %s", baseResponse);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda26
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SettingsActivity.logout$lambda$10(c02101, obj);
                }
            };
            final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.logout.2
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
                        AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, SettingsActivity.this, th.getMessage(), null, 4, null);
                        if (alertDialogCreateErrorAlertDialog$default2 != null) {
                            alertDialogCreateErrorAlertDialog$default2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, SettingsActivity.this, th.getMessage(), null, 4, null)) != null) {
                        alertDialogCreateErrorAlertDialog$default.show();
                    }
                    Timber.INSTANCE.e(th);
                }
            };
            observableRemoveNotification.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda27
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SettingsActivity.logout$lambda$11(function1, obj);
                }
            });
        }
        getBleManager().clear();
        getBleManager().disconnect(true);
        Settings.removeAllProperties();
        SettingsActivity settingsActivity = this;
        Settings.clearCookies(settingsActivity);
        DataLayerManager.INSTANCE.from(settingsActivity).sendIsAccessSession(false);
        getDatabase().installedSoundPackageDao().deleteAllElements();
        getDatabase().shopSoundPackageDao().deleteAllElements();
        final Intent intent = new Intent(settingsActivity, (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.logout$lambda$13(this.f$0, intent);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logout$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logout$lambda$11(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logout$lambda$13(SettingsActivity this$0, Intent intent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(intent, "$intent");
        this$0.startActivity(intent);
    }

    private final void fetchDeviceParameters() {
        DeviceStatus deviceStatus = Settings.INSTANCE.getDeviceStatus();
        Settings.INSTANCE.saveDeviceHasCanFileData(deviceStatus != null ? deviceStatus.getEngineConnected() : false);
        fetchServerCanInfo();
    }

    private final void fetchServerCanInfo() {
        SignInResponse profile = Settings.INSTANCE.getProfile();
        if (profile != null) {
            Observable<SignInResponse> observableFetchCarInfo = getUsersManager().fetchCarInfo(profile.getDeviceSN());
            final SettingsActivity$fetchServerCanInfo$1$1 settingsActivity$fetchServerCanInfo$1$1 = new SettingsActivity$fetchServerCanInfo$1$1(this, profile);
            Consumer<? super SignInResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SettingsActivity.fetchServerCanInfo$lambda$16$lambda$14(settingsActivity$fetchServerCanInfo$1$1, obj);
                }
            };
            final SettingsActivity$fetchServerCanInfo$1$2 settingsActivity$fetchServerCanInfo$1$2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$fetchServerCanInfo$1$2
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable th) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }
            };
            observableFetchCarInfo.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SettingsActivity.fetchServerCanInfo$lambda$16$lambda$15(settingsActivity$fetchServerCanInfo$1$2, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchServerCanInfo$lambda$16$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchServerCanInfo$lambda$16$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onChangeCar() {
        Timber.INSTANCE.i("onChangeCar", new Object[0]);
        if (getBleManager().getMStateConnected() && ConnectivityAndInternetAccess.INSTANCE.isConnected(this)) {
            openChangeCarScreen();
        } else if (!getBleManager().getMStateConnected()) {
            DialogManager.INSTANCE.createNoConnectionToBoardAlertDialog(this).show();
        } else {
            showNoInternetConnection();
        }
    }

    private final void openChangeCarScreen() {
        ChangeCarInfo changeCarInfo = new ChangeCarInfo(null, 0, 0, null, 0, null, 0, null, 0, null, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
        SignInResponse profile = Settings.INSTANCE.getProfile();
        changeCarInfo.setToken(Settings.getAccessToken());
        changeCarInfo.setUserId(Settings.getUserId());
        changeCarInfo.setCarMarkName(profile != null ? profile.getCarMarkName() : null);
        changeCarInfo.setCarModelName(profile != null ? profile.getCarModelName() : null);
        changeCarInfo.setCarGenerationName(profile != null ? profile.getCarGenerationName() : null);
        changeCarInfo.setCarSeriesName(profile != null ? profile.getCarSerieName() : null);
        Intent intent = new Intent(this, (Class<?>) ChangeCarActivity.class);
        intent.putExtra(ChangeCarInfo.BUNDLE_NAME, changeCarInfo);
        startActivity(intent);
    }

    /* compiled from: SettingsActivity.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.settings.SettingsActivity$showLogoutDialog$1, reason: invalid class name and case insensitive filesystem */
    static final class C02181 extends Lambda implements Function0<Unit> {
        C02181() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            SettingsActivity.this.logout();
            Single<Boolean> singleIsConnectedToWear = SettingsActivity.this.getMDataLayerHelper().isConnectedToWear();
            final SettingsActivity settingsActivity = SettingsActivity.this;
            final Function1<Boolean, Unit> function1 = new Function1<Boolean, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.showLogoutDialog.1.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                    invoke2(bool);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Boolean bool) {
                    settingsActivity.getMDataLayerHelper().onStartBluetoothSearchWearableActivity();
                }
            };
            singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$showLogoutDialog$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SettingsActivity.C02181.invoke$lambda$0(function1, obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }
    }

    private final void showLogoutDialog() {
        DialogManager.INSTANCE.createLogOutAlertDialog(this, new C02181()).show();
    }

    private final void showNoInternetConnection() {
        ActivitySettingsBinding activitySettingsBinding = this.binding;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        Snackbar.make(activitySettingsBinding.getRoot(), R.string.warning_service_is_unavailable, 0).show();
    }

    static /* synthetic */ void showCarInfoDialog$default(SettingsActivity settingsActivity, CanConfigurationsResponse canConfigurationsResponse, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            canConfigurationsResponse = null;
        }
        if ((i & 2) != 0) {
            z = false;
        }
        settingsActivity.showCarInfoDialog(canConfigurationsResponse, z);
    }

    private final void showCarInfoDialog(final CanConfigurationsResponse carInfo, boolean isNoCanFile) {
        if (this.isCarInfoDialogShown) {
            return;
        }
        AlertDialog alertDialogCreateCarInfoAlertDialog = DialogManager.INSTANCE.createCarInfoAlertDialog(this, isNoCanFile, new Function0<Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$showCarInfoDialog$dialog$1
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
                this.this$0.onChangeCar();
            }
        }, new Function0<Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$showCarInfoDialog$dialog$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                CanConfigurationsResponse canConfigurationsResponse = carInfo;
                if ((canConfigurationsResponse != null ? canConfigurationsResponse.getCanFile() : null) == null) {
                    this.openContactSupportDialog();
                    return;
                }
                SettingsActivity settingsActivity = this;
                CanFile canFile = carInfo.getCanFile();
                Intrinsics.checkNotNull(canFile);
                settingsActivity.openCanFileInfoDialog(canFile);
            }
        });
        alertDialogCreateCarInfoAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SettingsActivity.showCarInfoDialog$lambda$17(this.f$0, dialogInterface);
            }
        });
        alertDialogCreateCarInfoAlertDialog.show();
        this.isCarInfoDialogShown = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showCarInfoDialog$lambda$17(SettingsActivity this$0, DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isCarInfoDialogShown = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openContactSupportDialog() {
        DialogManager.INSTANCE.createNoCarFileAlertDialog(this).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openCanFileInfoDialog(CanFile model) {
        CarInfoDialogFragment carInfoDialogFragment = new CarInfoDialogFragment(model);
        carInfoDialogFragment.show(getSupportFragmentManager(), carInfoDialogFragment.getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showEnterPasswordDialog() {
        TestersDialogFragment testersDialogFragment = new TestersDialogFragment();
        testersDialogFragment.setOnConfirmListener(new OnConfirmDialogListener() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$showEnterPasswordDialog$$inlined$doOnConfirm$1
            @Override // com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener
            public void onConfirm(String info) {
                this.this$0.changeServerPasswordValidation(info);
            }
        });
        testersDialogFragment.show(getSupportFragmentManager(), testersDialogFragment.getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void changeServerPasswordValidation(String password) {
        getViewModel().changeServerPasswordValidationResponse(String.valueOf(password));
        getViewModel().setOnSuccessPasswordValidation(new Function1<PasswordValidationResponse, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.changeServerPasswordValidation.1
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
                Intrinsics.checkNotNullParameter(it, "it");
                if (Intrinsics.areEqual((Object) it.getStatus(), (Object) true)) {
                    SettingsActivity.this.showServerSelectDialog();
                } else {
                    Toast.makeText(SettingsActivity.this, it.getError_description(), 0).show();
                }
            }
        });
        getViewModel().setOnFailurePasswordValidation(new Function0<Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.changeServerPasswordValidation.2
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
                SettingsActivity settingsActivity = SettingsActivity.this;
                Toast.makeText(settingsActivity, settingsActivity.getString(R.string.text_error_message), 0).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showServerSelectDialog() {
        DialogManager.INSTANCE.createServerSelectAlertDialog(this, new Function0<Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.showServerSelectDialog.1
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
                if (Settings.getSelectedServer() != 1092) {
                    Settings.INSTANCE.saveSelectedServer(Constants.SERVER_DEVELOPER);
                    SettingsActivity.this.logout();
                }
            }
        }, new Function0<Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.showServerSelectDialog.2
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
                if (Settings.getSelectedServer() == 1092) {
                    Settings.INSTANCE.saveSelectedServer(Constants.SERVER_RELEASE);
                    SettingsActivity.this.logout();
                }
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fetchDeviceSettings() {
        Observable<ByteArrayOutputStream> observableObserveOn = getBleManager().executeReadSettingsCommand().observeOn(AndroidSchedulers.mainThread());
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.fetchDeviceSettings.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                SettingsActivity.this.showProgress();
            }
        };
        Observable<ByteArrayOutputStream> observableDoOnTerminate = observableObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivity.fetchDeviceSettings$lambda$19(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda22
            @Override // io.reactivex.functions.Action
            public final void run() {
                SettingsActivity.fetchDeviceSettings$lambda$20(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.fetchDeviceSettings.3
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
                byte[] bytes = byteArrayOutputStream.toByteArray();
                Timber.INSTANCE.i("fetchSettings data: %s", Hex.bytesToStringUppercase(bytes));
                SettingsActivity settingsActivity = SettingsActivity.this;
                Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
                settingsActivity.handleReadSettingsResponse(new ReadSettingsBleResponse(bytes));
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivity.fetchDeviceSettings$lambda$21(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.fetchDeviceSettings.4
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
                BleManager bleManager = SettingsActivity.this.getBleManager();
                final SettingsActivity settingsActivity = SettingsActivity.this;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.fetchDeviceSettings.4.1
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
                        settingsActivity.fetchDeviceSettings();
                    }
                });
            }
        };
        Disposable it = observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda25
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivity.fetchDeviceSettings$lambda$22(function13, obj);
            }
        });
        BleManager bleManager = getBleManager();
        Intrinsics.checkNotNullExpressionValue(it, "it");
        bleManager.addCommandDisposable(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchDeviceSettings$lambda$19(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchDeviceSettings$lambda$20(SettingsActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideProgress();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchDeviceSettings$lambda$21(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchDeviceSettings$lambda$22(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleReadSettingsResponse(ReadSettingsBleResponse response) {
        DeviceSettings deviceSettings = response.getDeviceSettings();
        if (deviceSettings != null) {
            ActivitySettingsBinding activitySettingsBinding = this.binding;
            ActivitySettingsBinding activitySettingsBinding2 = null;
            if (activitySettingsBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySettingsBinding = null;
            }
            activitySettingsBinding.driveSelectSwitch.setChecked(deviceSettings.isDriveSelectActive());
            ActivitySettingsBinding activitySettingsBinding3 = this.binding;
            if (activitySettingsBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySettingsBinding3 = null;
            }
            activitySettingsBinding3.nativeControlSwitch.setChecked(deviceSettings.isNativeControlActive());
            ActivitySettingsBinding activitySettingsBinding4 = this.binding;
            if (activitySettingsBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySettingsBinding2 = activitySettingsBinding4;
            }
            activitySettingsBinding2.twoSpeakerSwitch.setChecked(deviceSettings.isTwoSpeakerInstalled());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeSettings() {
        Observable<ByteArrayOutputStream> observableExecuteWriteSettingsCommand = getBleManager().executeWriteSettingsCommand(prepareDeviceSettings());
        final C02211 c02211 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.writeSettings.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] bytes = byteArrayOutputStream.toByteArray();
                Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
                new WriteSettingsBleResponse(bytes);
                Timber.INSTANCE.i("writeSettings data: %s", Hex.bytesToStringUppercase(bytes));
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda21
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivity.writeSettings$lambda$25(c02211, obj);
            }
        };
        final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.writeSettings.2
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
                BleManager bleManager = SettingsActivity.this.getBleManager();
                final SettingsActivity settingsActivity = SettingsActivity.this;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.writeSettings.2.1
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
                        settingsActivity.writeSettings();
                    }
                });
            }
        };
        Disposable it = observableExecuteWriteSettingsCommand.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda23
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivity.writeSettings$lambda$26(function1, obj);
            }
        });
        BleManager bleManager = getBleManager();
        Intrinsics.checkNotNullExpressionValue(it, "it");
        bleManager.addCommandDisposable(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeSettings$lambda$25(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeSettings$lambda$26(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final ArrayList<DeviceSettings.Setting> prepareDeviceSettings() {
        ArrayList<DeviceSettings.Setting> arrayList = new ArrayList<>();
        ActivitySettingsBinding activitySettingsBinding = this.binding;
        ActivitySettingsBinding activitySettingsBinding2 = null;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        DeviceSettings.Setting setting = new DeviceSettings.Setting((short) 1, (short) NumberKt.toInt(activitySettingsBinding.driveSelectSwitch.isChecked()));
        ActivitySettingsBinding activitySettingsBinding3 = this.binding;
        if (activitySettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding3 = null;
        }
        DeviceSettings.Setting setting2 = new DeviceSettings.Setting((short) 2, (short) NumberKt.toInt(activitySettingsBinding3.nativeControlSwitch.isChecked()));
        ActivitySettingsBinding activitySettingsBinding4 = this.binding;
        if (activitySettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySettingsBinding2 = activitySettingsBinding4;
        }
        DeviceSettings.Setting setting3 = new DeviceSettings.Setting((short) 3, (short) (activitySettingsBinding2.twoSpeakerSwitch.isChecked() ? 2 : 1));
        arrayList.add(setting);
        arrayList.add(setting2);
        arrayList.add(setting3);
        return arrayList;
    }

    private final void tryFetchDriveSelect() {
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        Short shValueOf = hardwareProfile != null ? Short.valueOf(hardwareProfile.getVersionFirmware()) : null;
        if (!Settings.INSTANCE.isDeviceHasCanFileData() || shValueOf == null || shValueOf.shortValue() < 346) {
            return;
        }
        CarManager carManagerFrom = CarManager.INSTANCE.from(this);
        Observable<DriveSelectResponse> observableFetchDriveSelect = carManagerFrom.fetchDriveSelect();
        final Function1<DriveSelectResponse, Unit> function1 = new Function1<DriveSelectResponse, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$tryFetchDriveSelect$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DriveSelectResponse driveSelectResponse) {
                invoke2(driveSelectResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DriveSelectResponse driveSelectResponse) {
                if (driveSelectResponse.isSuccessful()) {
                    this.this$0.handleDriveSelect(driveSelectResponse.getItems());
                    return;
                }
                AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, this.this$0, driveSelectResponse.getError(), null, 4, null);
                if (alertDialogCreateErrorAlertDialogWithSendLogOption$default != null) {
                    alertDialogCreateErrorAlertDialogWithSendLogOption$default.show();
                }
            }
        };
        Consumer<? super DriveSelectResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda29
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivity.tryFetchDriveSelect$lambda$31$lambda$28(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$tryFetchDriveSelect$1$2
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
                    AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this.this$0, th.getMessage(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialog$default2 != null) {
                        alertDialogCreateErrorAlertDialog$default2.show();
                        return;
                    }
                    return;
                }
                if (!Intrinsics.areEqual(th.getMessage(), "HTTP 500") || (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this.this$0, th.getMessage(), null, 4, null)) == null) {
                    return;
                }
                alertDialogCreateErrorAlertDialog$default.show();
            }
        };
        carManagerFrom.addDisposable(observableFetchDriveSelect.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda30
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivity.tryFetchDriveSelect$lambda$31$lambda$29(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryFetchDriveSelect$lambda$31$lambda$28(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryFetchDriveSelect$lambda$31$lambda$29(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleDriveSelect(final List<DriveSelect> driveSelects) {
        BleManager bleManager = getBleManager();
        Observable<ByteArrayOutputStream> observableObserveOn = getBleManager().executeReadDriveModes().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        final C02081 c02081 = new C02081(driveSelects, this);
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivity.handleDriveSelect$lambda$32(c02081, obj);
            }
        };
        final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.handleDriveSelect.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                BleManager bleManager2 = SettingsActivity.this.getBleManager();
                final SettingsActivity settingsActivity = SettingsActivity.this;
                final List<DriveSelect> list = driveSelects;
                bleManager2.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.handleDriveSelect.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        settingsActivity.handleDriveSelect(list);
                    }
                });
            }
        };
        Disposable disposableSubscribe = observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SettingsActivity.handleDriveSelect$lambda$33(function1, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(disposableSubscribe, "private fun handleDriveS…       })\n        )\n    }");
        bleManager.addCommandDisposable(disposableSubscribe);
    }

    /* compiled from: SettingsActivity.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Ljava/io/ByteArrayOutputStream;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.settings.SettingsActivity$handleDriveSelect$1, reason: invalid class name and case insensitive filesystem */
    static final class C02081 extends Lambda implements Function1<ByteArrayOutputStream, Unit> {
        final /* synthetic */ List<DriveSelect> $driveSelects;
        final /* synthetic */ SettingsActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02081(List<DriveSelect> list, SettingsActivity settingsActivity) {
            super(1);
            this.$driveSelects = list;
            this.this$0 = settingsActivity;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
            invoke2(byteArrayOutputStream);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
            final ArrayList arrayList = new ArrayList();
            ArrayList<DriveMode> driveModes = new ReadDriveModesBleResponse(byteArrayOutputStream.toByteArray()).getDriveModes();
            List<DriveSelect> list = this.$driveSelects;
            if (list != null) {
                CollectionsKt.sortedWith(list, new Comparator() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$handleDriveSelect$1$invoke$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues(Integer.valueOf(((DriveSelect) t).getRank()), Integer.valueOf(((DriveSelect) t2).getRank()));
                    }
                });
            }
            if (driveModes != null) {
                List<DriveSelect> list2 = this.$driveSelects;
                Iterator<DriveMode> it = driveModes.iterator();
                while (it.hasNext()) {
                    DriveMode next = it.next();
                    if (list2 != null) {
                        for (DriveSelect driveSelect : list2) {
                            if (driveSelect.getId() == next.getModeId()) {
                                arrayList.add(new DriveSelectStatus(driveSelect.getId(), driveSelect.getName(), next.getModeValue().getValue(), driveSelect.getRank()));
                            }
                        }
                    }
                }
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.size() > 1) {
                CollectionsKt.sortWith(arrayList2, new Comparator() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$handleDriveSelect$1$invoke$$inlined$sortBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues(Integer.valueOf(((DriveSelectStatus) t).getRank()), Integer.valueOf(((DriveSelectStatus) t2).getRank()));
                    }
                });
            }
            final SettingsActivity settingsActivity = this.this$0;
            settingsActivity.runOnUiThread(new Runnable() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$handleDriveSelect$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity.C02081.invoke$lambda$4(arrayList, settingsActivity);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(ArrayList mergedList, SettingsActivity this$0) {
            Intrinsics.checkNotNullParameter(mergedList, "$mergedList");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ActivitySettingsBinding activitySettingsBinding = null;
            if (!mergedList.isEmpty()) {
                ActivitySettingsBinding activitySettingsBinding2 = this$0.binding;
                if (activitySettingsBinding2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activitySettingsBinding = activitySettingsBinding2;
                }
                activitySettingsBinding.actionDriveSelect.setVisibility(0);
                return;
            }
            ActivitySettingsBinding activitySettingsBinding3 = this$0.binding;
            if (activitySettingsBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySettingsBinding = activitySettingsBinding3;
            }
            activitySettingsBinding.actionDriveSelect.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDriveSelect$lambda$32(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDriveSelect$lambda$33(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showProgress() {
        if (getProgressDialog().isAdded()) {
            return;
        }
        getProgressDialog().show(getSupportFragmentManager(), ProgressDialogFragment.TAG);
    }

    @Subscribe
    public final void onMessageEvent(BluetoothDeviceRssiEvent event) {
        System.out.println((Object) "eventRssi");
        getViewModel().getRssiSignal().set(getBleManager().getRssiLevel());
    }

    private final void hideProgress() {
        if (getProgressDialog().isResumed()) {
            getProgressDialog().dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void omMessageEvent(BadUserEvents event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        onFullLogout();
    }

    private final void onFullLogout() {
        Log.i("TEST21212_T", "onFullLogout");
        Settings.saveIsCheckedEmrgencySituations(false);
        Observable<BaseResponse> observableRemoveNotification = getUsersManager().removeNotification();
        if (observableRemoveNotification != null) {
            final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.onFullLogout.1
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
                    AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption;
                    if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(SettingsActivity.this, baseResponse.getError(), Integer.valueOf(baseResponse.getCode()))) != null) {
                        alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                    }
                    Timber.INSTANCE.i("Response: %s", baseResponse);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SettingsActivity.onFullLogout$lambda$34(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.onFullLogout.2
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
                        AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(SettingsActivity.this, th.getMessage(), 0);
                        if (alertDialogCreateErrorAlertDialog2 != null) {
                            alertDialogCreateErrorAlertDialog2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(SettingsActivity.this, th.getMessage(), 0)) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.e(th);
                }
            };
            observableRemoveNotification.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SettingsActivity.onFullLogout$lambda$35(function12, obj);
                }
            });
        }
        getBleManager().disconnect(true);
        Settings.removeAllProperties();
        SettingsActivity settingsActivity = this;
        Settings.clearCookies(settingsActivity);
        Settings.saveUserGoogleToken("");
        Settings.saveUserGoogleUserId("");
        DataLayerManager.INSTANCE.from(settingsActivity).sendIsAccessSession(false);
        ThorDatabase.INSTANCE.from(settingsActivity).installedSoundPackageDao().deleteAllElements();
        ThorDatabase.INSTANCE.from(settingsActivity).shopSoundPackageDao().deleteAllElements();
        Intent intent = new Intent(settingsActivity, (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFullLogout$lambda$34(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFullLogout$lambda$35(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(DownloadSettingsFileSuccessEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG_CAN_FILE", " => DownloadSettingsFileSuccessEvent");
        ActivitySettingsBinding activitySettingsBinding = this.binding;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        activitySettingsBinding.progressBar.setVisibility(8);
        fetchDeviceParameters();
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        SettingsActivity settingsActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(settingsActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, settingsActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new C02161(uriForFile, event, null), 2, null);
    }

    /* compiled from: SettingsActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.settings.SettingsActivity$onMessageEvent$1", f = "SettingsActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.settings.SettingsActivity$onMessageEvent$1, reason: invalid class name and case insensitive filesystem */
    static final class C02161 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02161(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super C02161> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SettingsActivity.this.new C02161(this.$path, this.$event, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02161) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = SettingsActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final C00641 c00641 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.onMessageEvent.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$onMessageEvent$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00641.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final AnonymousClass2 anonymousClass2 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity.onMessageEvent.1.2
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
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$onMessageEvent$1$$ExternalSyntheticLambda1
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
