package com.thor.app.gui.activities.shop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityShopBinding;
import com.thor.app.bus.events.BadUserEvents;
import com.thor.app.bus.events.BluetoothDeviceConnectionChangedEvent;
import com.thor.app.bus.events.BluetoothDeviceRssiEvent;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.bus.events.bluetooth.sgu.CancelUploadSguEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageInterruptedEvent;
import com.thor.app.bus.events.shop.sgu.UploadSguSoundPackageSuccessfulEvent;
import com.thor.app.databinding.model.RunningAppOnPhoneStatus;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.fragments.shop.main.MainShopFragment;
import com.thor.app.gui.fragments.shop.sgu.SguShopFragment;
import com.thor.app.gui.widget.ShopModeSwitchView;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.ContextKt;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.app.utils.security.DeviceLockingUtilsKt;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.networkmodule.model.responses.BaseResponse;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.Serializable;
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
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: ShopActivity.kt */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\b\u0010\u0017\u001a\u00020\u0013H\u0002J\b\u0010\u0018\u001a\u00020\u0013H\u0002J\b\u0010\u0019\u001a\u00020\u0013H\u0002J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0012\u0010\u001d\u001a\u00020\u00132\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\b\u0010 \u001a\u00020\u0013H\u0003J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\"H\u0007J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020#H\u0007J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020$H\u0007J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020%H\u0007J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020&H\u0007J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020'H\u0007J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020(H\u0007J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020)H\u0007J\b\u0010*\u001a\u00020\u0013H\u0014J\u0012\u0010+\u001a\u00020\u00132\b\u0010,\u001a\u0004\u0018\u00010-H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010¨\u0006/"}, d2 = {"Lcom/thor/app/gui/activities/shop/ShopActivity;", "Lcom/thor/app/gui/activities/EmergencySituationBaseActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityShopBinding;", "fileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "viewModel", "Lcom/thor/app/gui/activities/shop/ShopViewModel;", "getViewModel", "()Lcom/thor/app/gui/activities/shop/ShopViewModel;", "viewModel$delegate", "handleShopMode", "", "mode", "Lcom/thor/app/gui/widget/ShopModeSwitchView$ShopMode;", "initSwipeContainer", "initToolbar", "initViewPager", "observeUiState", "omMessageEvent", "event", "Lcom/thor/app/bus/events/BadUserEvents;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onFullLogout", "onMessageEvent", "Lcom/thor/app/bus/events/BluetoothDeviceConnectionChangedEvent;", "Lcom/thor/app/bus/events/BluetoothDeviceRssiEvent;", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/app/bus/events/bluetooth/sgu/CancelUploadSguEvent;", "Lcom/thor/app/bus/events/shop/main/UploadSoundPackageInterruptedEvent;", "Lcom/thor/app/bus/events/shop/sgu/UploadSguSoundPackageSuccessfulEvent;", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "onResume", "showError", "message", "", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class ShopActivity extends Hilt_ShopActivity {
    private static final int MAIN_SHOP_PAGE_INDEX = 0;
    private static final int SGU_SHOP_PAGE_INDEX = 1;
    private static boolean isNeedSendInterruptedSoundEvent;
    private static int stableSignalCounter;
    private ActivityShopBinding binding;
    private final FileLogger fileLogger = new FileLogger(this, null, 2, null);

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.shop.ShopActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* compiled from: ShopActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ShopModeSwitchView.ShopMode.values().length];
            try {
                iArr[ShopModeSwitchView.ShopMode.CAR_SOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ShopModeSwitchView.ShopMode.BOOMBOX.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ShopActivity() {
        final ShopActivity shopActivity = this;
        final Function0 function0 = null;
        this.viewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(ShopViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.activities.shop.ShopActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = shopActivity.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "viewModelStore");
                return viewModelStore;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.activities.shop.ShopActivity$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = shopActivity.getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.activities.shop.ShopActivity$special$$inlined$viewModels$default$3
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
                CreationExtras defaultViewModelCreationExtras = shopActivity.getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "this.defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        });
    }

    private final ShopViewModel getViewModel() {
        return (ShopViewModel) this.viewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_shop);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.layout.activity_shop)");
        this.binding = (ActivityShopBinding) contentView;
        initToolbar();
        initInternetConnectionListener();
        initSwipeContainer();
        observeUiState();
        getViewModel().reAuth(false);
    }

    private final void initToolbar() {
        ActivityShopBinding activityShopBinding = this.binding;
        ActivityShopBinding activityShopBinding2 = null;
        if (activityShopBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopBinding = null;
        }
        activityShopBinding.toolbarWidget.setHomeButtonVisibility(true);
        ActivityShopBinding activityShopBinding3 = this.binding;
        if (activityShopBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopBinding3 = null;
        }
        activityShopBinding3.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.ShopActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShopActivity.initToolbar$lambda$0(this.f$0, view);
            }
        });
        ActivityShopBinding activityShopBinding4 = this.binding;
        if (activityShopBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityShopBinding2 = activityShopBinding4;
        }
        activityShopBinding2.toolbarWidget.enableBluetoothIndicator(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initToolbar$lambda$0(ShopActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void initSwipeContainer() {
        ActivityShopBinding activityShopBinding = this.binding;
        ActivityShopBinding activityShopBinding2 = null;
        if (activityShopBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopBinding = null;
        }
        activityShopBinding.swipeRefresh.setColorSchemeColors(ContextKt.fetchAttrValue(this, R.attr.colorAccent));
        ActivityShopBinding activityShopBinding3 = this.binding;
        if (activityShopBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopBinding3 = null;
        }
        activityShopBinding3.swipeRefresh.setProgressBackgroundColorSchemeResource(R.color.colorRefreshLayoutBackground);
        ActivityShopBinding activityShopBinding4 = this.binding;
        if (activityShopBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityShopBinding2 = activityShopBinding4;
        }
        activityShopBinding2.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.activities.shop.ShopActivity$$ExternalSyntheticLambda3
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                ShopActivity.initSwipeContainer$lambda$1(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initSwipeContainer$lambda$1(ShopActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ShopViewModel.reAuth$default(this$0.getViewModel(), false, 1, null);
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        DataLayerManager.INSTANCE.from(this).sendIsRunningAppOnPhone(RunningAppOnPhoneStatus.OFF);
    }

    private final void observeUiState() {
        final Function1<ShopUiState, Unit> function1 = new Function1<ShopUiState, Unit>() { // from class: com.thor.app.gui.activities.shop.ShopActivity.observeUiState.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ShopUiState shopUiState) {
                invoke2(shopUiState);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ShopUiState shopUiState) {
                if (shopUiState instanceof ShopError) {
                    ShopActivity.this.showError(((ShopError) shopUiState).getMessage());
                    return;
                }
                if (Intrinsics.areEqual(shopUiState, DeviceLocked.INSTANCE)) {
                    DeviceLockingUtilsKt.onDeviceLocked(ShopActivity.this);
                    return;
                }
                if (Intrinsics.areEqual(shopUiState, ReAuthSuccessful.INSTANCE)) {
                    ShopActivity.this.initViewPager();
                    return;
                }
                if (Intrinsics.areEqual(shopUiState, ShopLoading.INSTANCE)) {
                    ActivityShopBinding activityShopBinding = ShopActivity.this.binding;
                    if (activityShopBinding == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        activityShopBinding = null;
                    }
                    activityShopBinding.swipeRefresh.setRefreshing(true);
                }
            }
        };
        getViewModel().getUiState().observe(this, new Observer() { // from class: com.thor.app.gui.activities.shop.ShopActivity$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ShopActivity.observeUiState$lambda$2(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeUiState$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initViewPager() {
        ActivityShopBinding activityShopBinding = this.binding;
        ActivityShopBinding activityShopBinding2 = null;
        if (activityShopBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopBinding = null;
        }
        activityShopBinding.swipeRefresh.setRefreshing(false);
        ActivityShopBinding activityShopBinding3 = this.binding;
        if (activityShopBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopBinding3 = null;
        }
        activityShopBinding3.swipeRefresh.setEnabled(false);
        ActivityShopBinding activityShopBinding4 = this.binding;
        if (activityShopBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopBinding4 = null;
        }
        activityShopBinding4.switchShopMode.setOnShopModeChangeListener(new ShopModeSwitchView.OnShopModeChangedListener() { // from class: com.thor.app.gui.activities.shop.ShopActivity.initViewPager.1
            @Override // com.thor.app.gui.widget.ShopModeSwitchView.OnShopModeChangedListener
            public void onModeChange(ShopModeSwitchView.ShopMode mode) {
                Intrinsics.checkNotNullParameter(mode, "mode");
                ShopActivity.this.handleShopMode(mode);
            }
        });
        Bundle extras = getIntent().getExtras();
        Serializable serializable = extras != null ? extras.getSerializable(ShopModeSwitchView.SHOP_MODE_BUNDLE) : null;
        ShopModeSwitchView.ShopMode shopMode = serializable instanceof ShopModeSwitchView.ShopMode ? (ShopModeSwitchView.ShopMode) serializable : null;
        if (shopMode == null) {
            shopMode = ShopModeSwitchView.ShopMode.CAR_SOUND;
        }
        ActivityShopBinding activityShopBinding5 = this.binding;
        if (activityShopBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityShopBinding2 = activityShopBinding5;
        }
        activityShopBinding2.switchShopMode.setCurrentMode(shopMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleShopMode(ShopModeSwitchView.ShopMode mode) {
        int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
        if (i == 1) {
            getSupportFragmentManager().beginTransaction().disallowAddToBackStack().setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim).replace(R.id.fragment_container, MainShopFragment.INSTANCE.newInstance()).commit();
        } else {
            if (i != 2) {
                return;
            }
            getSupportFragmentManager().beginTransaction().disallowAddToBackStack().setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim).replace(R.id.fragment_container, SguShopFragment.INSTANCE.newInstance()).commit();
        }
    }

    @Subscribe
    public final void onMessageEvent(BluetoothDeviceConnectionChangedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (getBleManager().isBleEnabledAndDeviceConnected() && Settings.INSTANCE.isLoadSoundInterrupted() && Settings.INSTANCE.getSoundPackageParcel() != null) {
            isNeedSendInterruptedSoundEvent = true;
            getBleManager().readRemoteRssi();
        }
    }

    @Subscribe
    public final void onMessageEvent(UploadSoundPackageInterruptedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (getBleManager().isBleEnabledAndDeviceConnected() && Settings.INSTANCE.isLoadSoundInterrupted() && Settings.INSTANCE.getSoundPackageParcel() != null) {
            isNeedSendInterruptedSoundEvent = true;
            getBleManager().readRemoteRssi();
        }
    }

    @Subscribe
    public final void onMessageEvent(BluetoothDeviceRssiEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (isNeedSendInterruptedSoundEvent) {
            if (event.getRssi() >= -80) {
                stableSignalCounter++;
            } else {
                stableSignalCounter = 0;
            }
            if (event.getRssi() >= -80 && stableSignalCounter > 50) {
                isNeedSendInterruptedSoundEvent = false;
            } else {
                getBleManager().readRemoteRssi();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showError(String message) {
        ActivityShopBinding activityShopBinding = this.binding;
        if (activityShopBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopBinding = null;
        }
        activityShopBinding.swipeRefresh.setEnabled(true);
        ShopActivity shopActivity = this;
        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(shopActivity, message, 0);
        if (alertDialogCreateErrorAlertDialog != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        if (message != null) {
            Toast.makeText(shopActivity, message, 1).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void omMessageEvent(BadUserEvents event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        onFullLogout();
    }

    private final void onFullLogout() {
        Settings.saveIsCheckedEmrgencySituations(false);
        Observable<BaseResponse> observableRemoveNotification = getUsersManager().removeNotification();
        if (observableRemoveNotification != null) {
            final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.ShopActivity.onFullLogout.1
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
                    if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(ShopActivity.this, baseResponse.getError(), Integer.valueOf(baseResponse.getCode()))) != null) {
                        alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                    }
                    Timber.INSTANCE.i("Response: %s", baseResponse);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.ShopActivity$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ShopActivity.onFullLogout$lambda$4(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.ShopActivity.onFullLogout.2
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
                        AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(ShopActivity.this, th.getMessage(), 0);
                        if (alertDialogCreateErrorAlertDialog2 != null) {
                            alertDialogCreateErrorAlertDialog2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(ShopActivity.this, th.getMessage(), 0)) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.e(th);
                }
            };
            observableRemoveNotification.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.ShopActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ShopActivity.onFullLogout$lambda$5(function12, obj);
                }
            });
        }
        getBleManager().disconnect(true);
        Settings.removeAllProperties();
        ShopActivity shopActivity = this;
        Settings.clearCookies(shopActivity);
        Settings.saveUserGoogleToken("");
        Settings.saveUserGoogleUserId("");
        DataLayerManager.INSTANCE.from(shopActivity).sendIsAccessSession(false);
        ThorDatabase.INSTANCE.from(shopActivity).installedSoundPackageDao().deleteAllElements();
        ThorDatabase.INSTANCE.from(shopActivity).shopSoundPackageDao().deleteAllElements();
        Intent intent = new Intent(shopActivity, (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFullLogout$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFullLogout$lambda$5(Function1 tmp0, Object obj) {
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
        this.fileLogger.i("=> D " + event.getDataDeCrypto(), new Object[0]);
        this.fileLogger.i("=> E " + event.getDataCrypto(), new Object[0]);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UploadSguSoundPackageSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(CancelUploadSguEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Log.i("FIND", "10");
        BleManager.executeActivatePresetCommand$default(getBleManager(), getBleManager().getMActivatedPresetIndex(), getBleManager().getMActivatedPreset(), null, 4, null);
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ShopActivity shopActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(shopActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, shopActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new C02251(uriForFile, event, null), 2, null);
    }

    /* compiled from: ShopActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.shop.ShopActivity$onMessageEvent$1", f = "ShopActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.shop.ShopActivity$onMessageEvent$1, reason: invalid class name and case insensitive filesystem */
    static final class C02251 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02251(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super C02251> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ShopActivity.this.new C02251(this.$path, this.$event, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02251) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = ShopActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final C00671 c00671 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.shop.ShopActivity.onMessageEvent.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.shop.ShopActivity$onMessageEvent$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00671.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final AnonymousClass2 anonymousClass2 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.ShopActivity.onMessageEvent.1.2
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
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.shop.ShopActivity$onMessageEvent$1$$ExternalSyntheticLambda1
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
