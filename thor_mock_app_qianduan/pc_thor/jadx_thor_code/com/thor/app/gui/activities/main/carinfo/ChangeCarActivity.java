package com.thor.app.gui.activities.main.carinfo;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityChangeCarBinding;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.Constants;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.bus.events.bluetooth.UpdateCanConfigurationsErrorEvent;
import com.thor.app.bus.events.bluetooth.UpdateCanConfigurationsSuccessfulEvent;
import com.thor.app.gui.dialog.CanFileManager;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.dialogs.ProgressDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.CarManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.businessmodule.viewmodel.ChangeCarActivityViewModel;
import com.thor.networkmodule.model.ChangeCarInfo;
import com.thor.networkmodule.model.car.CarGeneration;
import com.thor.networkmodule.model.car.CarMark;
import com.thor.networkmodule.model.car.CarModel;
import com.thor.networkmodule.model.car.CarSeries;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.car.CarGenerationsResponse;
import com.thor.networkmodule.model.responses.car.CarMarksResponse;
import com.thor.networkmodule.model.responses.car.CarModelsResponse;
import com.thor.networkmodule.model.responses.car.CarSeriesResponse;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: ChangeCarActivity.kt */
@Metadata(d1 = {"\u0000â\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 t2\u00020\u00012\u00020\u0002:\u0001tB\u0005¢\u0006\u0002\u0010\u0003J\b\u00101\u001a\u000202H\u0002J\u0010\u00103\u001a\u0002022\u0006\u00104\u001a\u000205H\u0002J\b\u00106\u001a\u000202H\u0002J\b\u00107\u001a\u00020\u0019H\u0002J\u0010\u00108\u001a\u00020&2\u0006\u00109\u001a\u000205H\u0002J\u0010\u0010:\u001a\u00020&2\u0006\u00109\u001a\u000205H\u0002J\u0010\u0010;\u001a\u00020&2\u0006\u00109\u001a\u000205H\u0002J\u0010\u0010<\u001a\u00020&2\u0006\u00109\u001a\u000205H\u0002J\u0015\u0010=\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001050>H\u0002¢\u0006\u0002\u0010?J\u0015\u0010@\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001050>H\u0002¢\u0006\u0002\u0010?J\u0015\u0010A\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001050>H\u0002¢\u0006\u0002\u0010?J\u0015\u0010B\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001050>H\u0002¢\u0006\u0002\u0010?J\u0018\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020+H\u0002J\u0010\u0010H\u001a\u00020D2\u0006\u0010I\u001a\u00020JH\u0002J\u0010\u0010K\u001a\u00020D2\u0006\u0010L\u001a\u00020MH\u0002J\u0010\u0010K\u001a\u00020D2\u0006\u0010L\u001a\u00020NH\u0002J\u0010\u0010K\u001a\u00020D2\u0006\u0010L\u001a\u00020OH\u0002J\u0010\u0010K\u001a\u00020D2\u0006\u0010L\u001a\u00020PH\u0002J\u0010\u0010Q\u001a\u00020D2\u0006\u0010L\u001a\u00020MH\u0002J\u0010\u0010Q\u001a\u00020D2\u0006\u0010L\u001a\u00020NH\u0002J\u0010\u0010Q\u001a\u00020D2\u0006\u0010L\u001a\u00020OH\u0002J\u0010\u0010Q\u001a\u00020D2\u0006\u0010L\u001a\u00020PH\u0002J\b\u0010R\u001a\u00020DH\u0002J\b\u0010S\u001a\u00020DH\u0002J\b\u0010T\u001a\u00020DH\u0002J\b\u0010U\u001a\u00020DH\u0002J\u0012\u0010V\u001a\u00020D2\b\u0010W\u001a\u0004\u0018\u00010XH\u0016J\u0012\u0010Y\u001a\u00020D2\b\u0010Z\u001a\u0004\u0018\u00010[H\u0014J\u0010\u0010\\\u001a\u00020D2\u0006\u0010]\u001a\u00020&H\u0002J\b\u0010^\u001a\u00020DH\u0002J\u0010\u0010_\u001a\u00020D2\u0006\u0010`\u001a\u00020&H\u0002J\u0010\u0010a\u001a\u00020D2\u0006\u0010b\u001a\u00020&H\u0002J\u0010\u0010c\u001a\u00020D2\u0006\u0010]\u001a\u00020&H\u0002J\u0010\u0010d\u001a\u00020D2\u0006\u0010`\u001a\u00020&H\u0002J\u0010\u0010e\u001a\u00020D2\u0006\u0010b\u001a\u00020&H\u0002J\u0010\u0010f\u001a\u00020D2\u0006\u0010g\u001a\u00020hH\u0007J\u0010\u0010f\u001a\u00020D2\u0006\u0010g\u001a\u00020iH\u0007J\u0010\u0010f\u001a\u00020D2\u0006\u0010g\u001a\u00020jH\u0007J\u0010\u0010f\u001a\u00020D2\u0006\u0010g\u001a\u00020kH\u0007J\u0010\u0010f\u001a\u00020D2\u0006\u0010g\u001a\u00020lH\u0007J\b\u0010m\u001a\u00020DH\u0014J\b\u0010n\u001a\u00020DH\u0014J\b\u0010o\u001a\u00020DH\u0002J\b\u0010p\u001a\u00020DH\u0002J\b\u0010q\u001a\u00020DH\u0002J\b\u0010r\u001a\u00020DH\u0002J\u0010\u0010s\u001a\u00020D2\u0006\u0010G\u001a\u00020+H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\r8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u000b\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020 \u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010(\u001a\n\u0012\u0004\u0012\u00020)\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010,\u001a\u00020-8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\u000b\u001a\u0004\b.\u0010/¨\u0006u"}, d2 = {"Lcom/thor/app/gui/activities/main/carinfo/ChangeCarActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/view/View$OnClickListener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityChangeCarBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "canFileManager", "Lcom/thor/app/gui/dialog/CanFileManager;", "getCanFileManager", "()Lcom/thor/app/gui/dialog/CanFileManager;", "canFileManager$delegate", "carManager", "Lcom/thor/app/managers/CarManager;", "getCarManager", "()Lcom/thor/app/managers/CarManager;", "carManager$delegate", "fileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "mApiResponseProgressDialog", "Landroid/app/Dialog;", "mGenerations", "", "Lcom/thor/networkmodule/model/car/CarGeneration;", "mMarks", "Lcom/thor/networkmodule/model/car/CarMark;", "mModels", "Lcom/thor/networkmodule/model/car/CarModel;", "mProgressDialog", "Lcom/thor/app/gui/dialog/dialogs/ProgressDialogFragment;", "mSelectorDownAnimator", "Landroid/animation/ValueAnimator;", "mSelectorStep", "", "mSelectorUpAnimator", "mSeries", "Lcom/thor/networkmodule/model/car/CarSeries;", "receivedCarInfo", "Lcom/thor/networkmodule/model/ChangeCarInfo;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "createCanConfigurationSuccessfulAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "createErrorMessageAlertDialog", "message", "", "createNoCanFileOnServerAlertDialog", "createProgressLoading", "fetchCarGenerationId", AppMeasurementSdk.ConditionalUserProperty.NAME, "fetchCarMarkId", "fetchCarModelId", "fetchCarSeriesId", "getGenerationNames", "", "()[Ljava/lang/String;", "getMarkNames", "getModelNames", "getSeriesNames", "handleChangeCarResponse", "", "response", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "changeCarInfo", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "value", "Lcom/thor/networkmodule/model/responses/car/CarGenerationsResponse;", "Lcom/thor/networkmodule/model/responses/car/CarMarksResponse;", "Lcom/thor/networkmodule/model/responses/car/CarModelsResponse;", "Lcom/thor/networkmodule/model/responses/car/CarSeriesResponse;", "handleSavedResponse", "initAnimators", "loadInfoForSavedCar", "onApplySelector", "onChangeCar", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onLoadCarGenerations", "carModelId", "onLoadCarMarks", "onLoadCarModels", "carMarkId", "onLoadCarSeries", "carGenerationId", "onLoadSavedCarGenerations", "onLoadSavedCarModels", "onLoadSavedCarSeries", "onMessageEvent", "event", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/app/bus/events/bluetooth/UpdateCanConfigurationsErrorEvent;", "Lcom/thor/app/bus/events/bluetooth/UpdateCanConfigurationsSuccessfulEvent;", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "onPause", "onResume", "onSelectCarGeneration", "onSelectCarMark", "onSelectCarModel", "onSelectCarSeries", "setChangesInProfile", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ChangeCarActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int SELECTOR_STEP_GENERATION = 2;
    public static final int SELECTOR_STEP_MARK = 0;
    public static final int SELECTOR_STEP_MODEL = 1;
    public static final int SELECTOR_STEP_SERIES = 3;
    private ActivityChangeCarBinding binding;
    private Dialog mApiResponseProgressDialog;
    private List<CarGeneration> mGenerations;
    private List<CarMark> mMarks;
    private List<CarModel> mModels;
    private ProgressDialogFragment mProgressDialog;
    private ValueAnimator mSelectorDownAnimator;
    private int mSelectorStep;
    private ValueAnimator mSelectorUpAnimator;
    private List<CarSeries> mSeries;
    private ChangeCarInfo receivedCarInfo;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0);
        }
    });
    private final FileLogger fileLogger = FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, this, null, 2, null);

    /* renamed from: carManager$delegate, reason: from kotlin metadata */
    private final Lazy carManager = LazyKt.lazy(new Function0<CarManager>() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$carManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final CarManager invoke() {
            return CarManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: canFileManager$delegate, reason: from kotlin metadata */
    private final Lazy canFileManager = LazyKt.lazy(new Function0<CanFileManager>() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$canFileManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final CanFileManager invoke() {
            return CanFileManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    public final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final CarManager getCarManager() {
        return (CarManager) this.carManager.getValue();
    }

    protected final CanFileManager getCanFileManager() {
        return (CanFileManager) this.canFileManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        ObservableBoolean enableCarSeries;
        ObservableBoolean enableCarGeneration;
        ObservableBoolean enableChangeCar;
        ObservableBoolean enableCarModel;
        String carMarkName;
        super.onCreate(savedInstanceState);
        ChangeCarActivity changeCarActivity = this;
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(changeCarActivity);
        ViewDataBinding contentView = DataBindingUtil.setContentView(changeCarActivity, R.layout.activity_change_car);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.layout.activity_change_car)");
        this.binding = (ActivityChangeCarBinding) contentView;
        this.receivedCarInfo = (ChangeCarInfo) getIntent().getParcelableExtra(ChangeCarInfo.BUNDLE_NAME);
        ChangeCarActivityViewModel changeCarActivityViewModel = new ChangeCarActivityViewModel();
        ObservableField<String> carModel = changeCarActivityViewModel.getCarModel();
        ChangeCarInfo changeCarInfo = this.receivedCarInfo;
        ActivityChangeCarBinding activityChangeCarBinding = null;
        carModel.set(changeCarInfo != null ? changeCarInfo.getCarModelName() : null);
        ObservableField<String> carMark = changeCarActivityViewModel.getCarMark();
        ChangeCarInfo changeCarInfo2 = this.receivedCarInfo;
        carMark.set(changeCarInfo2 != null ? changeCarInfo2.getCarMarkName() : null);
        ObservableField<String> carGeneration = changeCarActivityViewModel.getCarGeneration();
        ChangeCarInfo changeCarInfo3 = this.receivedCarInfo;
        carGeneration.set(changeCarInfo3 != null ? changeCarInfo3.getCarGenerationName() : null);
        ObservableField<String> carSeries = changeCarActivityViewModel.getCarSeries();
        ChangeCarInfo changeCarInfo4 = this.receivedCarInfo;
        carSeries.set(changeCarInfo4 != null ? changeCarInfo4.getCarSeriesName() : null);
        ActivityChangeCarBinding activityChangeCarBinding2 = this.binding;
        if (activityChangeCarBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding2 = null;
        }
        activityChangeCarBinding2.setModel(changeCarActivityViewModel);
        ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
        if (activityChangeCarBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding3 = null;
        }
        activityChangeCarBinding3.toolbarWidget.setHomeButtonVisibility(true);
        ActivityChangeCarBinding activityChangeCarBinding4 = this.binding;
        if (activityChangeCarBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding4 = null;
        }
        activityChangeCarBinding4.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChangeCarActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        this.mApiResponseProgressDialog = createProgressLoading();
        ActivityChangeCarBinding activityChangeCarBinding5 = this.binding;
        if (activityChangeCarBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding5 = null;
        }
        activityChangeCarBinding5.setChangeCarInfo((ChangeCarInfo) getIntent().getParcelableExtra(ChangeCarInfo.BUNDLE_NAME));
        ChangeCarInfo changeCarInfo5 = this.receivedCarInfo;
        boolean z = false;
        if (changeCarInfo5 != null && (carMarkName = changeCarInfo5.getCarMarkName()) != null) {
            if (carMarkName.length() > 0) {
                z = true;
            }
        }
        if (z) {
            loadInfoForSavedCar();
            Dialog dialog = this.mApiResponseProgressDialog;
            if (dialog == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mApiResponseProgressDialog");
                dialog = null;
            }
            dialog.show();
            ActivityChangeCarBinding activityChangeCarBinding6 = this.binding;
            if (activityChangeCarBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding6 = null;
            }
            ChangeCarActivityViewModel model = activityChangeCarBinding6.getModel();
            if (model != null && (enableCarModel = model.getEnableCarModel()) != null) {
                enableCarModel.set(true);
            }
            ActivityChangeCarBinding activityChangeCarBinding7 = this.binding;
            if (activityChangeCarBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding7 = null;
            }
            ChangeCarActivityViewModel model2 = activityChangeCarBinding7.getModel();
            if (model2 != null && (enableChangeCar = model2.getEnableChangeCar()) != null) {
                enableChangeCar.set(true);
            }
            ActivityChangeCarBinding activityChangeCarBinding8 = this.binding;
            if (activityChangeCarBinding8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding8 = null;
            }
            ChangeCarActivityViewModel model3 = activityChangeCarBinding8.getModel();
            if (model3 != null && (enableCarGeneration = model3.getEnableCarGeneration()) != null) {
                enableCarGeneration.set(true);
            }
            ActivityChangeCarBinding activityChangeCarBinding9 = this.binding;
            if (activityChangeCarBinding9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding9 = null;
            }
            ChangeCarActivityViewModel model4 = activityChangeCarBinding9.getModel();
            if (model4 != null && (enableCarSeries = model4.getEnableCarSeries()) != null) {
                enableCarSeries.set(true);
            }
        }
        ActivityChangeCarBinding activityChangeCarBinding10 = this.binding;
        if (activityChangeCarBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding10 = null;
        }
        ChangeCarActivity changeCarActivity2 = this;
        activityChangeCarBinding10.textViewCarMark.setOnClickListener(changeCarActivity2);
        ActivityChangeCarBinding activityChangeCarBinding11 = this.binding;
        if (activityChangeCarBinding11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding11 = null;
        }
        activityChangeCarBinding11.textViewCarModel.setOnClickListener(changeCarActivity2);
        ActivityChangeCarBinding activityChangeCarBinding12 = this.binding;
        if (activityChangeCarBinding12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding12 = null;
        }
        activityChangeCarBinding12.textViewCarGeneration.setOnClickListener(changeCarActivity2);
        ActivityChangeCarBinding activityChangeCarBinding13 = this.binding;
        if (activityChangeCarBinding13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding13 = null;
        }
        activityChangeCarBinding13.textViewCarSeries.setOnClickListener(changeCarActivity2);
        ActivityChangeCarBinding activityChangeCarBinding14 = this.binding;
        if (activityChangeCarBinding14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding14 = null;
        }
        activityChangeCarBinding14.buttonChange.setOnClickListener(changeCarActivity2);
        ActivityChangeCarBinding activityChangeCarBinding15 = this.binding;
        if (activityChangeCarBinding15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding = activityChangeCarBinding15;
        }
        activityChangeCarBinding.textViewApplySelector.setOnClickListener(changeCarActivity2);
        ProgressDialogFragment progressDialogFragmentNewInstance = ProgressDialogFragment.newInstance();
        Intrinsics.checkNotNullExpressionValue(progressDialogFragmentNewInstance, "newInstance()");
        this.mProgressDialog = progressDialogFragmentNewInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(ChangeCarActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() throws SecurityException {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        ObservableField<String> carGeneration;
        ObservableField<String> carModel;
        ObservableField<String> carMark;
        String str = null;
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_car_mark) {
            onLoadCarMarks();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_car_model) {
            ActivityChangeCarBinding activityChangeCarBinding = this.binding;
            if (activityChangeCarBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding = null;
            }
            ChangeCarActivityViewModel model = activityChangeCarBinding.getModel();
            if (model != null && (carMark = model.getCarMark()) != null) {
                str = carMark.get();
            }
            Intrinsics.checkNotNull(str);
            onLoadCarModels(fetchCarMarkId(str));
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_car_generation) {
            ActivityChangeCarBinding activityChangeCarBinding2 = this.binding;
            if (activityChangeCarBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding2 = null;
            }
            ChangeCarActivityViewModel model2 = activityChangeCarBinding2.getModel();
            if (model2 != null && (carModel = model2.getCarModel()) != null) {
                str = carModel.get();
            }
            Intrinsics.checkNotNull(str);
            onLoadCarGenerations(fetchCarModelId(str));
            return;
        }
        if (numValueOf == null || numValueOf.intValue() != R.id.text_view_car_series) {
            if (numValueOf != null && numValueOf.intValue() == R.id.text_view_apply_selector) {
                onApplySelector();
                return;
            } else {
                if (numValueOf != null && numValueOf.intValue() == R.id.button_change) {
                    onChangeCar();
                    return;
                }
                return;
            }
        }
        ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
        if (activityChangeCarBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding3 = null;
        }
        ChangeCarActivityViewModel model3 = activityChangeCarBinding3.getModel();
        if (model3 != null && (carGeneration = model3.getCarGeneration()) != null) {
            str = carGeneration.get();
        }
        Intrinsics.checkNotNull(str);
        onLoadCarSeries(fetchCarGenerationId(str));
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$loadInfoForSavedCar$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01791 extends FunctionReferenceImpl implements Function1<CarMarksResponse, Unit> {
        C01791(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleSavedResponse", "handleSavedResponse(Lcom/thor/networkmodule/model/responses/car/CarMarksResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarMarksResponse carMarksResponse) {
            invoke2(carMarksResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarMarksResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleSavedResponse(p0);
        }
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$loadInfoForSavedCar$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleError(p0);
        }
    }

    private final void loadInfoForSavedCar() {
        Observable<CarMarksResponse> observableFetchMarks = CarManager.INSTANCE.from(this).fetchMarks();
        final C01791 c01791 = new C01791(this);
        Consumer<? super CarMarksResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.loadInfoForSavedCar$lambda$1(c01791, obj);
            }
        };
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
        observableFetchMarks.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.loadInfoForSavedCar$lambda$2(anonymousClass2, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadInfoForSavedCar$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadInfoForSavedCar$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadSavedCarModels$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01921 extends FunctionReferenceImpl implements Function1<CarModelsResponse, Unit> {
        C01921(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleSavedResponse", "handleSavedResponse(Lcom/thor/networkmodule/model/responses/car/CarModelsResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarModelsResponse carModelsResponse) {
            invoke2(carModelsResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarModelsResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleSavedResponse(p0);
        }
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadSavedCarModels$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01932 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01932(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadSavedCarModels(int carMarkId) {
        Observable<CarModelsResponse> observableFetchModels = CarManager.INSTANCE.from(this).fetchModels(carMarkId);
        final C01921 c01921 = new C01921(this);
        Consumer<? super CarModelsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadSavedCarModels$lambda$3(c01921, obj);
            }
        };
        final C01932 c01932 = new C01932(this);
        observableFetchModels.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadSavedCarModels$lambda$4(c01932, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadSavedCarModels$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadSavedCarModels$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadSavedCarGenerations$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01901 extends FunctionReferenceImpl implements Function1<CarGenerationsResponse, Unit> {
        C01901(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleSavedResponse", "handleSavedResponse(Lcom/thor/networkmodule/model/responses/car/CarGenerationsResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarGenerationsResponse carGenerationsResponse) {
            invoke2(carGenerationsResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarGenerationsResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleSavedResponse(p0);
        }
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadSavedCarGenerations$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01912 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01912(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadSavedCarGenerations(int carModelId) {
        Observable<CarGenerationsResponse> observableFetchGenerations = CarManager.INSTANCE.from(this).fetchGenerations(carModelId);
        final C01901 c01901 = new C01901(this);
        Consumer<? super CarGenerationsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadSavedCarGenerations$lambda$5(c01901, obj);
            }
        };
        final C01912 c01912 = new C01912(this);
        observableFetchGenerations.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadSavedCarGenerations$lambda$6(c01912, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadSavedCarGenerations$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadSavedCarGenerations$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadSavedCarSeries$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01941 extends FunctionReferenceImpl implements Function1<CarSeriesResponse, Unit> {
        C01941(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleSavedResponse", "handleSavedResponse(Lcom/thor/networkmodule/model/responses/car/CarSeriesResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarSeriesResponse carSeriesResponse) {
            invoke2(carSeriesResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarSeriesResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleSavedResponse(p0);
        }
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadSavedCarSeries$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01952 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01952(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadSavedCarSeries(int carGenerationId) {
        Observable<CarSeriesResponse> observableFetchSeries = CarManager.INSTANCE.from(this).fetchSeries(carGenerationId);
        final C01941 c01941 = new C01941(this);
        Consumer<? super CarSeriesResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadSavedCarSeries$lambda$7(c01941, obj);
            }
        };
        final C01952 c01952 = new C01952(this);
        observableFetchSeries.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadSavedCarSeries$lambda$8(c01952, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadSavedCarSeries$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadSavedCarSeries$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadCarMarks$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01841 extends FunctionReferenceImpl implements Function1<CarMarksResponse, Unit> {
        C01841(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/car/CarMarksResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarMarksResponse carMarksResponse) {
            invoke2(carMarksResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarMarksResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadCarMarks$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01852 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01852(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadCarMarks() {
        Observable<CarMarksResponse> observableFetchMarks = CarManager.INSTANCE.from(this).fetchMarks();
        final C01841 c01841 = new C01841(this);
        Consumer<? super CarMarksResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadCarMarks$lambda$9(c01841, obj);
            }
        };
        final C01852 c01852 = new C01852(this);
        observableFetchMarks.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadCarMarks$lambda$10(c01852, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarMarks$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarMarks$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadCarModels$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01861 extends FunctionReferenceImpl implements Function1<CarModelsResponse, Unit> {
        C01861(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/car/CarModelsResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarModelsResponse carModelsResponse) {
            invoke2(carModelsResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarModelsResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadCarModels$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01872 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01872(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadCarModels(int carMarkId) {
        Observable<CarModelsResponse> observableFetchModels = CarManager.INSTANCE.from(this).fetchModels(carMarkId);
        final C01861 c01861 = new C01861(this);
        Consumer<? super CarModelsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadCarModels$lambda$11(c01861, obj);
            }
        };
        final C01872 c01872 = new C01872(this);
        observableFetchModels.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda21
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadCarModels$lambda$12(c01872, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarModels$lambda$11(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarModels$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadCarGenerations$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01821 extends FunctionReferenceImpl implements Function1<CarGenerationsResponse, Unit> {
        C01821(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/car/CarGenerationsResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarGenerationsResponse carGenerationsResponse) {
            invoke2(carGenerationsResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarGenerationsResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadCarGenerations$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01832 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01832(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadCarGenerations(int carModelId) {
        Observable<CarGenerationsResponse> observableFetchGenerations = CarManager.INSTANCE.from(this).fetchGenerations(carModelId);
        final C01821 c01821 = new C01821(this);
        Consumer<? super CarGenerationsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadCarGenerations$lambda$13(c01821, obj);
            }
        };
        final C01832 c01832 = new C01832(this);
        observableFetchGenerations.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadCarGenerations$lambda$14(c01832, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarGenerations$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarGenerations$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadCarSeries$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01881 extends FunctionReferenceImpl implements Function1<CarSeriesResponse, Unit> {
        C01881(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/car/CarSeriesResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarSeriesResponse carSeriesResponse) {
            invoke2(carSeriesResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarSeriesResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onLoadCarSeries$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01892 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01892(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadCarSeries(int carGenerationId) {
        Observable<CarSeriesResponse> observableFetchSeries = CarManager.INSTANCE.from(this).fetchSeries(carGenerationId);
        final C01881 c01881 = new C01881(this);
        Consumer<? super CarSeriesResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadCarSeries$lambda$15(c01881, obj);
            }
        };
        final C01892 c01892 = new C01892(this);
        observableFetchSeries.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChangeCarActivity.onLoadCarSeries$lambda$16(c01892, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarSeries$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarSeries$lambda$16(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(CarMarksResponse value) {
        boolean z = true;
        Timber.INSTANCE.i("handleResponse: %s", value);
        if (value.isSuccessful()) {
            List<CarMark> marks = value.getMarks();
            if (marks != null && !marks.isEmpty()) {
                z = false;
            }
            if (z) {
                return;
            }
            this.mMarks = value.getMarks();
            onSelectCarMark();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, value.getError(), value.getCode());
        if (alertDialogCreateErrorAlertDialog != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        Snackbar.make(activityChangeCarBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(CarModelsResponse value) {
        boolean z = true;
        Timber.INSTANCE.i("handleResponse: %s", value);
        if (value.isSuccessful()) {
            List<CarModel> models = value.getModels();
            if (models != null && !models.isEmpty()) {
                z = false;
            }
            if (z) {
                return;
            }
            this.mModels = value.getModels();
            onSelectCarModel();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, value.getError(), value.getCode());
        if (alertDialogCreateErrorAlertDialog != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        Snackbar.make(activityChangeCarBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(CarGenerationsResponse value) {
        ObservableBoolean enableCarGeneration;
        Timber.INSTANCE.i("handleResponse: %s", value);
        ActivityChangeCarBinding activityChangeCarBinding = null;
        if (value.isSuccessful()) {
            List<CarGeneration> generations = value.getGenerations();
            if (generations == null || generations.isEmpty()) {
                return;
            }
            this.mGenerations = value.getGenerations();
            ActivityChangeCarBinding activityChangeCarBinding2 = this.binding;
            if (activityChangeCarBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityChangeCarBinding = activityChangeCarBinding2;
            }
            ChangeCarActivityViewModel model = activityChangeCarBinding.getModel();
            if (model != null && (enableCarGeneration = model.getEnableCarGeneration()) != null) {
                enableCarGeneration.set(true);
            }
            onSelectCarGeneration();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, value.getError(), value.getCode());
        if (alertDialogCreateErrorAlertDialog != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
        if (activityChangeCarBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding = activityChangeCarBinding3;
        }
        Snackbar.make(activityChangeCarBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(CarSeriesResponse value) {
        ObservableBoolean enableCarSeries;
        Timber.INSTANCE.i("handleResponse: %s", value);
        ActivityChangeCarBinding activityChangeCarBinding = null;
        if (value.isSuccessful()) {
            List<CarSeries> series = value.getSeries();
            if (series == null || series.isEmpty()) {
                return;
            }
            this.mSeries = value.getSeries();
            ActivityChangeCarBinding activityChangeCarBinding2 = this.binding;
            if (activityChangeCarBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityChangeCarBinding = activityChangeCarBinding2;
            }
            ChangeCarActivityViewModel model = activityChangeCarBinding.getModel();
            if (model != null && (enableCarSeries = model.getEnableCarSeries()) != null) {
                enableCarSeries.set(true);
            }
            onSelectCarSeries();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, value.getError(), value.getCode());
        if (alertDialogCreateErrorAlertDialog != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
        if (activityChangeCarBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding = activityChangeCarBinding3;
        }
        Snackbar.make(activityChangeCarBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption;
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption2 = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(this, error.getMessage(), 0);
            if (alertDialogCreateErrorAlertDialogWithSendLogOption2 != null) {
                alertDialogCreateErrorAlertDialogWithSendLogOption2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(this, error.getMessage(), 0)) != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
        }
        ProgressDialogFragment progressDialogFragment = this.mProgressDialog;
        ActivityChangeCarBinding activityChangeCarBinding = null;
        if (progressDialogFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
            progressDialogFragment = null;
        }
        if (progressDialogFragment.isResumed()) {
            ProgressDialogFragment progressDialogFragment2 = this.mProgressDialog;
            if (progressDialogFragment2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
                progressDialogFragment2 = null;
            }
            progressDialogFragment2.dismiss();
        }
        Dialog dialog = this.mApiResponseProgressDialog;
        if (dialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mApiResponseProgressDialog");
            dialog = null;
        }
        dialog.dismiss();
        Timber.INSTANCE.e(error);
        ActivityChangeCarBinding activityChangeCarBinding2 = this.binding;
        if (activityChangeCarBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding = activityChangeCarBinding2;
        }
        Snackbar.make(activityChangeCarBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
    }

    private final void onSelectCarMark() {
        ObservableBoolean isSelector;
        this.mSelectorStep = 0;
        List<CarMark> list = this.mMarks;
        if (list == null || list.isEmpty()) {
            return;
        }
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        ActivityChangeCarBinding activityChangeCarBinding2 = null;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        activityChangeCarBinding.viewPickerSelector.setMinValue(0);
        ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
        if (activityChangeCarBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding3 = null;
        }
        activityChangeCarBinding3.viewPickerSelector.setMaxValue(0);
        ActivityChangeCarBinding activityChangeCarBinding4 = this.binding;
        if (activityChangeCarBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding4 = null;
        }
        activityChangeCarBinding4.viewPickerSelector.setDisplayedValues(null);
        ActivityChangeCarBinding activityChangeCarBinding5 = this.binding;
        if (activityChangeCarBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding5 = null;
        }
        activityChangeCarBinding5.viewPickerSelector.setDisplayedValues(getMarkNames());
        ActivityChangeCarBinding activityChangeCarBinding6 = this.binding;
        if (activityChangeCarBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding6 = null;
        }
        activityChangeCarBinding6.viewPickerSelector.setMinValue(0);
        ActivityChangeCarBinding activityChangeCarBinding7 = this.binding;
        if (activityChangeCarBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding7 = null;
        }
        NumberPicker numberPicker = activityChangeCarBinding7.viewPickerSelector;
        List<CarMark> list2 = this.mMarks;
        Integer numValueOf = list2 != null ? Integer.valueOf(list2.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        numberPicker.setMaxValue(numValueOf.intValue() - 1);
        ActivityChangeCarBinding activityChangeCarBinding8 = this.binding;
        if (activityChangeCarBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding8 = null;
        }
        activityChangeCarBinding8.viewPickerSelector.setValue(0);
        initAnimators();
        ValueAnimator valueAnimator = this.mSelectorUpAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ActivityChangeCarBinding activityChangeCarBinding9 = this.binding;
        if (activityChangeCarBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding2 = activityChangeCarBinding9;
        }
        ChangeCarActivityViewModel model = activityChangeCarBinding2.getModel();
        if (model == null || (isSelector = model.getIsSelector()) == null) {
            return;
        }
        isSelector.set(true);
    }

    private final void onSelectCarModel() {
        ObservableBoolean isSelector;
        this.mSelectorStep = 1;
        List<CarModel> list = this.mModels;
        if (list == null || list.isEmpty()) {
            return;
        }
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        ActivityChangeCarBinding activityChangeCarBinding2 = null;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        activityChangeCarBinding.viewPickerSelector.setMinValue(0);
        ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
        if (activityChangeCarBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding3 = null;
        }
        activityChangeCarBinding3.viewPickerSelector.setMaxValue(0);
        ActivityChangeCarBinding activityChangeCarBinding4 = this.binding;
        if (activityChangeCarBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding4 = null;
        }
        activityChangeCarBinding4.viewPickerSelector.setDisplayedValues(null);
        ActivityChangeCarBinding activityChangeCarBinding5 = this.binding;
        if (activityChangeCarBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding5 = null;
        }
        activityChangeCarBinding5.viewPickerSelector.setDisplayedValues(getModelNames());
        ActivityChangeCarBinding activityChangeCarBinding6 = this.binding;
        if (activityChangeCarBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding6 = null;
        }
        activityChangeCarBinding6.viewPickerSelector.setMinValue(0);
        ActivityChangeCarBinding activityChangeCarBinding7 = this.binding;
        if (activityChangeCarBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding7 = null;
        }
        NumberPicker numberPicker = activityChangeCarBinding7.viewPickerSelector;
        List<CarModel> list2 = this.mModels;
        Integer numValueOf = list2 != null ? Integer.valueOf(list2.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        numberPicker.setMaxValue(numValueOf.intValue() - 1);
        ActivityChangeCarBinding activityChangeCarBinding8 = this.binding;
        if (activityChangeCarBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding8 = null;
        }
        activityChangeCarBinding8.viewPickerSelector.setValue(0);
        initAnimators();
        ValueAnimator valueAnimator = this.mSelectorUpAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ActivityChangeCarBinding activityChangeCarBinding9 = this.binding;
        if (activityChangeCarBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding2 = activityChangeCarBinding9;
        }
        ChangeCarActivityViewModel model = activityChangeCarBinding2.getModel();
        if (model == null || (isSelector = model.getIsSelector()) == null) {
            return;
        }
        isSelector.set(true);
    }

    private final void onSelectCarGeneration() {
        ObservableBoolean isSelector;
        this.mSelectorStep = 2;
        List<CarGeneration> list = this.mGenerations;
        if (list == null || list.isEmpty()) {
            return;
        }
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        ActivityChangeCarBinding activityChangeCarBinding2 = null;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        activityChangeCarBinding.viewPickerSelector.setMinValue(0);
        ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
        if (activityChangeCarBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding3 = null;
        }
        activityChangeCarBinding3.viewPickerSelector.setMaxValue(0);
        ActivityChangeCarBinding activityChangeCarBinding4 = this.binding;
        if (activityChangeCarBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding4 = null;
        }
        activityChangeCarBinding4.viewPickerSelector.setDisplayedValues(null);
        ActivityChangeCarBinding activityChangeCarBinding5 = this.binding;
        if (activityChangeCarBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding5 = null;
        }
        activityChangeCarBinding5.viewPickerSelector.setDisplayedValues(getGenerationNames());
        ActivityChangeCarBinding activityChangeCarBinding6 = this.binding;
        if (activityChangeCarBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding6 = null;
        }
        activityChangeCarBinding6.viewPickerSelector.setMinValue(0);
        ActivityChangeCarBinding activityChangeCarBinding7 = this.binding;
        if (activityChangeCarBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding7 = null;
        }
        NumberPicker numberPicker = activityChangeCarBinding7.viewPickerSelector;
        List<CarGeneration> list2 = this.mGenerations;
        Integer numValueOf = list2 != null ? Integer.valueOf(list2.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        numberPicker.setMaxValue(numValueOf.intValue() - 1);
        ActivityChangeCarBinding activityChangeCarBinding8 = this.binding;
        if (activityChangeCarBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding8 = null;
        }
        activityChangeCarBinding8.viewPickerSelector.setValue(0);
        initAnimators();
        ValueAnimator valueAnimator = this.mSelectorUpAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ActivityChangeCarBinding activityChangeCarBinding9 = this.binding;
        if (activityChangeCarBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding2 = activityChangeCarBinding9;
        }
        ChangeCarActivityViewModel model = activityChangeCarBinding2.getModel();
        if (model == null || (isSelector = model.getIsSelector()) == null) {
            return;
        }
        isSelector.set(true);
    }

    private final void onSelectCarSeries() {
        ObservableBoolean isSelector;
        this.mSelectorStep = 3;
        List<CarSeries> list = this.mSeries;
        if (list == null || list.isEmpty()) {
            return;
        }
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        ActivityChangeCarBinding activityChangeCarBinding2 = null;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        activityChangeCarBinding.viewPickerSelector.setMinValue(0);
        ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
        if (activityChangeCarBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding3 = null;
        }
        activityChangeCarBinding3.viewPickerSelector.setMaxValue(0);
        ActivityChangeCarBinding activityChangeCarBinding4 = this.binding;
        if (activityChangeCarBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding4 = null;
        }
        activityChangeCarBinding4.viewPickerSelector.setDisplayedValues(null);
        ActivityChangeCarBinding activityChangeCarBinding5 = this.binding;
        if (activityChangeCarBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding5 = null;
        }
        activityChangeCarBinding5.viewPickerSelector.setDisplayedValues(getSeriesNames());
        ActivityChangeCarBinding activityChangeCarBinding6 = this.binding;
        if (activityChangeCarBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding6 = null;
        }
        activityChangeCarBinding6.viewPickerSelector.setMinValue(0);
        ActivityChangeCarBinding activityChangeCarBinding7 = this.binding;
        if (activityChangeCarBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding7 = null;
        }
        NumberPicker numberPicker = activityChangeCarBinding7.viewPickerSelector;
        List<CarSeries> list2 = this.mSeries;
        Integer numValueOf = list2 != null ? Integer.valueOf(list2.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        numberPicker.setMaxValue(numValueOf.intValue() - 1);
        ActivityChangeCarBinding activityChangeCarBinding8 = this.binding;
        if (activityChangeCarBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding8 = null;
        }
        activityChangeCarBinding8.viewPickerSelector.setValue(0);
        initAnimators();
        ValueAnimator valueAnimator = this.mSelectorUpAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ActivityChangeCarBinding activityChangeCarBinding9 = this.binding;
        if (activityChangeCarBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding2 = activityChangeCarBinding9;
        }
        ChangeCarActivityViewModel model = activityChangeCarBinding2.getModel();
        if (model == null || (isSelector = model.getIsSelector()) == null) {
            return;
        }
        isSelector.set(true);
    }

    private final void onChangeCar() {
        ObservableField<String> carSeries;
        ObservableField<String> carSeries2;
        ObservableField<String> carGeneration;
        ObservableField<String> carGeneration2;
        ObservableField<String> carModel;
        ObservableField<String> carModel2;
        ObservableField<String> carMark;
        ObservableField<String> carMark2;
        final ChangeCarInfo changeCarInfo = new ChangeCarInfo(null, 0, 0, null, 0, null, 0, null, 0, null, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
        changeCarInfo.setUserId(Settings.getUserId());
        changeCarInfo.setToken(Settings.getAccessToken());
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        String str = null;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        ChangeCarActivityViewModel model = activityChangeCarBinding.getModel();
        String str2 = (model == null || (carMark2 = model.getCarMark()) == null) ? null : carMark2.get();
        Intrinsics.checkNotNull(str2);
        changeCarInfo.setCarMarkId(fetchCarMarkId(str2));
        ActivityChangeCarBinding activityChangeCarBinding2 = this.binding;
        if (activityChangeCarBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding2 = null;
        }
        ChangeCarActivityViewModel model2 = activityChangeCarBinding2.getModel();
        changeCarInfo.setCarMarkName((model2 == null || (carMark = model2.getCarMark()) == null) ? null : carMark.get());
        ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
        if (activityChangeCarBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding3 = null;
        }
        ChangeCarActivityViewModel model3 = activityChangeCarBinding3.getModel();
        String str3 = (model3 == null || (carModel2 = model3.getCarModel()) == null) ? null : carModel2.get();
        Intrinsics.checkNotNull(str3);
        changeCarInfo.setCarModelId(fetchCarModelId(str3));
        ActivityChangeCarBinding activityChangeCarBinding4 = this.binding;
        if (activityChangeCarBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding4 = null;
        }
        ChangeCarActivityViewModel model4 = activityChangeCarBinding4.getModel();
        changeCarInfo.setCarModelName((model4 == null || (carModel = model4.getCarModel()) == null) ? null : carModel.get());
        ActivityChangeCarBinding activityChangeCarBinding5 = this.binding;
        if (activityChangeCarBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding5 = null;
        }
        ChangeCarActivityViewModel model5 = activityChangeCarBinding5.getModel();
        String str4 = (model5 == null || (carGeneration2 = model5.getCarGeneration()) == null) ? null : carGeneration2.get();
        Intrinsics.checkNotNull(str4);
        changeCarInfo.setCarGenerationId(fetchCarGenerationId(str4));
        ActivityChangeCarBinding activityChangeCarBinding6 = this.binding;
        if (activityChangeCarBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding6 = null;
        }
        ChangeCarActivityViewModel model6 = activityChangeCarBinding6.getModel();
        changeCarInfo.setCarGenerationName((model6 == null || (carGeneration = model6.getCarGeneration()) == null) ? null : carGeneration.get());
        ActivityChangeCarBinding activityChangeCarBinding7 = this.binding;
        if (activityChangeCarBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding7 = null;
        }
        ChangeCarActivityViewModel model7 = activityChangeCarBinding7.getModel();
        String str5 = (model7 == null || (carSeries2 = model7.getCarSeries()) == null) ? null : carSeries2.get();
        Intrinsics.checkNotNull(str5);
        changeCarInfo.setCarSeriesId(fetchCarSeriesId(str5));
        ActivityChangeCarBinding activityChangeCarBinding8 = this.binding;
        if (activityChangeCarBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding8 = null;
        }
        ChangeCarActivityViewModel model8 = activityChangeCarBinding8.getModel();
        if (model8 != null && (carSeries = model8.getCarSeries()) != null) {
            str = carSeries.get();
        }
        changeCarInfo.setCarSeriesName(str);
        Observable<BaseResponse> observableChangeUserCar = getUsersManager().changeUserCar(changeCarInfo);
        if (observableChangeUserCar != null) {
            final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity.onChangeCar.1
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
                    ProgressDialogFragment progressDialogFragment = ChangeCarActivity.this.mProgressDialog;
                    ProgressDialogFragment progressDialogFragment2 = null;
                    if (progressDialogFragment == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
                        progressDialogFragment = null;
                    }
                    if (progressDialogFragment.isAdded()) {
                        return;
                    }
                    ProgressDialogFragment progressDialogFragment3 = ChangeCarActivity.this.mProgressDialog;
                    if (progressDialogFragment3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
                    } else {
                        progressDialogFragment2 = progressDialogFragment3;
                    }
                    progressDialogFragment2.show(ChangeCarActivity.this.getSupportFragmentManager(), ProgressDialogFragment.TAG);
                }
            };
            Observable<BaseResponse> observableDoOnSubscribe = observableChangeUserCar.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChangeCarActivity.onChangeCar$lambda$17(function1, obj);
                }
            });
            if (observableDoOnSubscribe != null) {
                final Function1<BaseResponse, Unit> function12 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity.onChangeCar.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                        invoke2(baseResponse);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(BaseResponse it) {
                        ChangeCarActivity changeCarActivity = ChangeCarActivity.this;
                        Intrinsics.checkNotNullExpressionValue(it, "it");
                        changeCarActivity.handleChangeCarResponse(it, changeCarInfo);
                    }
                };
                Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ChangeCarActivity.onChangeCar$lambda$18(function12, obj);
                    }
                };
                final AnonymousClass3 anonymousClass3 = new AnonymousClass3(this);
                observableDoOnSubscribe.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda3
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ChangeCarActivity.onChangeCar$lambda$19(anonymousClass3, obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onChangeCar$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onChangeCar$3, reason: invalid class name */
    /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass3(Object obj) {
            super(1, obj, ChangeCarActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ChangeCarActivity) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onChangeCar$lambda$18(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onChangeCar$lambda$19(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void onApplySelector() {
        CarMark carMark;
        ObservableBoolean enableCarSeries;
        ObservableBoolean enableCarGeneration;
        ObservableBoolean enableCarModel;
        ObservableField<String> carSeries;
        ObservableField<String> carGeneration;
        ObservableField<String> carModel;
        ObservableField<String> carMark2;
        ObservableBoolean enableChangeCar;
        ObservableBoolean isSelector;
        CarModel carModel2;
        ObservableBoolean enableCarSeries2;
        ObservableBoolean enableCarGeneration2;
        ObservableField<String> carSeries2;
        ObservableField<String> carGeneration2;
        ObservableField<String> carModel3;
        ObservableBoolean enableChangeCar2;
        CarGeneration carGeneration3;
        ObservableBoolean enableCarSeries3;
        ObservableField<String> carSeries3;
        ObservableField<String> carGeneration4;
        ObservableBoolean enableChangeCar3;
        CarSeries carSeries4;
        ObservableField<String> carSeries5;
        ObservableBoolean enableChangeCar4;
        int i = this.mSelectorStep;
        ActivityChangeCarBinding activityChangeCarBinding = null;
        if (i == 0) {
            ActivityChangeCarBinding activityChangeCarBinding2 = this.binding;
            if (activityChangeCarBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding2 = null;
            }
            ChangeCarActivityViewModel model = activityChangeCarBinding2.getModel();
            if (model != null && (enableChangeCar = model.getEnableChangeCar()) != null) {
                enableChangeCar.set(false);
            }
            List<CarMark> list = this.mMarks;
            if (list != null) {
                ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
                if (activityChangeCarBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activityChangeCarBinding3 = null;
                }
                carMark = list.get(activityChangeCarBinding3.viewPickerSelector.getValue());
            } else {
                carMark = null;
            }
            ActivityChangeCarBinding activityChangeCarBinding4 = this.binding;
            if (activityChangeCarBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding4 = null;
            }
            ChangeCarActivityViewModel model2 = activityChangeCarBinding4.getModel();
            if (model2 != null && (carMark2 = model2.getCarMark()) != null) {
                carMark2.set(carMark != null ? carMark.getName() : null);
            }
            ActivityChangeCarBinding activityChangeCarBinding5 = this.binding;
            if (activityChangeCarBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding5 = null;
            }
            activityChangeCarBinding5.textViewCarMark.setSelected(true);
            ActivityChangeCarBinding activityChangeCarBinding6 = this.binding;
            if (activityChangeCarBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding6 = null;
            }
            ChangeCarActivityViewModel model3 = activityChangeCarBinding6.getModel();
            if (model3 != null && (carModel = model3.getCarModel()) != null) {
                carModel.set("");
            }
            ActivityChangeCarBinding activityChangeCarBinding7 = this.binding;
            if (activityChangeCarBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding7 = null;
            }
            activityChangeCarBinding7.textViewCarModel.setSelected(false);
            ActivityChangeCarBinding activityChangeCarBinding8 = this.binding;
            if (activityChangeCarBinding8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding8 = null;
            }
            ChangeCarActivityViewModel model4 = activityChangeCarBinding8.getModel();
            if (model4 != null && (carGeneration = model4.getCarGeneration()) != null) {
                carGeneration.set("");
            }
            ActivityChangeCarBinding activityChangeCarBinding9 = this.binding;
            if (activityChangeCarBinding9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding9 = null;
            }
            activityChangeCarBinding9.textViewCarGeneration.setSelected(false);
            ActivityChangeCarBinding activityChangeCarBinding10 = this.binding;
            if (activityChangeCarBinding10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding10 = null;
            }
            ChangeCarActivityViewModel model5 = activityChangeCarBinding10.getModel();
            if (model5 != null && (carSeries = model5.getCarSeries()) != null) {
                carSeries.set("");
            }
            ActivityChangeCarBinding activityChangeCarBinding11 = this.binding;
            if (activityChangeCarBinding11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding11 = null;
            }
            activityChangeCarBinding11.textViewCarSeries.setSelected(false);
            ActivityChangeCarBinding activityChangeCarBinding12 = this.binding;
            if (activityChangeCarBinding12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding12 = null;
            }
            ChangeCarActivityViewModel model6 = activityChangeCarBinding12.getModel();
            if (model6 != null && (enableCarModel = model6.getEnableCarModel()) != null) {
                enableCarModel.set(true);
            }
            ActivityChangeCarBinding activityChangeCarBinding13 = this.binding;
            if (activityChangeCarBinding13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding13 = null;
            }
            ChangeCarActivityViewModel model7 = activityChangeCarBinding13.getModel();
            if (model7 != null && (enableCarGeneration = model7.getEnableCarGeneration()) != null) {
                enableCarGeneration.set(false);
            }
            ActivityChangeCarBinding activityChangeCarBinding14 = this.binding;
            if (activityChangeCarBinding14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding14 = null;
            }
            ChangeCarActivityViewModel model8 = activityChangeCarBinding14.getModel();
            if (model8 != null && (enableCarSeries = model8.getEnableCarSeries()) != null) {
                enableCarSeries.set(false);
            }
        } else if (i == 1) {
            ActivityChangeCarBinding activityChangeCarBinding15 = this.binding;
            if (activityChangeCarBinding15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding15 = null;
            }
            ChangeCarActivityViewModel model9 = activityChangeCarBinding15.getModel();
            if (model9 != null && (enableChangeCar2 = model9.getEnableChangeCar()) != null) {
                enableChangeCar2.set(false);
            }
            List<CarModel> list2 = this.mModels;
            if (list2 != null) {
                ActivityChangeCarBinding activityChangeCarBinding16 = this.binding;
                if (activityChangeCarBinding16 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activityChangeCarBinding16 = null;
                }
                carModel2 = list2.get(activityChangeCarBinding16.viewPickerSelector.getValue());
            } else {
                carModel2 = null;
            }
            ActivityChangeCarBinding activityChangeCarBinding17 = this.binding;
            if (activityChangeCarBinding17 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding17 = null;
            }
            ChangeCarActivityViewModel model10 = activityChangeCarBinding17.getModel();
            if (model10 != null && (carModel3 = model10.getCarModel()) != null) {
                carModel3.set(carModel2 != null ? carModel2.getName() : null);
            }
            ActivityChangeCarBinding activityChangeCarBinding18 = this.binding;
            if (activityChangeCarBinding18 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding18 = null;
            }
            activityChangeCarBinding18.textViewCarModel.setSelected(true);
            ActivityChangeCarBinding activityChangeCarBinding19 = this.binding;
            if (activityChangeCarBinding19 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding19 = null;
            }
            ChangeCarActivityViewModel model11 = activityChangeCarBinding19.getModel();
            if (model11 != null && (carGeneration2 = model11.getCarGeneration()) != null) {
                carGeneration2.set("");
            }
            ActivityChangeCarBinding activityChangeCarBinding20 = this.binding;
            if (activityChangeCarBinding20 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding20 = null;
            }
            activityChangeCarBinding20.textViewCarGeneration.setSelected(false);
            ActivityChangeCarBinding activityChangeCarBinding21 = this.binding;
            if (activityChangeCarBinding21 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding21 = null;
            }
            ChangeCarActivityViewModel model12 = activityChangeCarBinding21.getModel();
            if (model12 != null && (carSeries2 = model12.getCarSeries()) != null) {
                carSeries2.set("");
            }
            ActivityChangeCarBinding activityChangeCarBinding22 = this.binding;
            if (activityChangeCarBinding22 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding22 = null;
            }
            activityChangeCarBinding22.textViewCarSeries.setSelected(false);
            ActivityChangeCarBinding activityChangeCarBinding23 = this.binding;
            if (activityChangeCarBinding23 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding23 = null;
            }
            ChangeCarActivityViewModel model13 = activityChangeCarBinding23.getModel();
            if (model13 != null && (enableCarGeneration2 = model13.getEnableCarGeneration()) != null) {
                enableCarGeneration2.set(true);
            }
            ActivityChangeCarBinding activityChangeCarBinding24 = this.binding;
            if (activityChangeCarBinding24 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding24 = null;
            }
            ChangeCarActivityViewModel model14 = activityChangeCarBinding24.getModel();
            if (model14 != null && (enableCarSeries2 = model14.getEnableCarSeries()) != null) {
                enableCarSeries2.set(false);
            }
        } else if (i == 2) {
            ActivityChangeCarBinding activityChangeCarBinding25 = this.binding;
            if (activityChangeCarBinding25 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding25 = null;
            }
            ChangeCarActivityViewModel model15 = activityChangeCarBinding25.getModel();
            if (model15 != null && (enableChangeCar3 = model15.getEnableChangeCar()) != null) {
                enableChangeCar3.set(false);
            }
            List<CarGeneration> list3 = this.mGenerations;
            if (list3 != null) {
                ActivityChangeCarBinding activityChangeCarBinding26 = this.binding;
                if (activityChangeCarBinding26 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activityChangeCarBinding26 = null;
                }
                carGeneration3 = list3.get(activityChangeCarBinding26.viewPickerSelector.getValue());
            } else {
                carGeneration3 = null;
            }
            ActivityChangeCarBinding activityChangeCarBinding27 = this.binding;
            if (activityChangeCarBinding27 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding27 = null;
            }
            ChangeCarActivityViewModel model16 = activityChangeCarBinding27.getModel();
            if (model16 != null && (carGeneration4 = model16.getCarGeneration()) != null) {
                carGeneration4.set(carGeneration3 != null ? carGeneration3.getName() : null);
            }
            ActivityChangeCarBinding activityChangeCarBinding28 = this.binding;
            if (activityChangeCarBinding28 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding28 = null;
            }
            activityChangeCarBinding28.textViewCarGeneration.setSelected(true);
            ActivityChangeCarBinding activityChangeCarBinding29 = this.binding;
            if (activityChangeCarBinding29 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding29 = null;
            }
            ChangeCarActivityViewModel model17 = activityChangeCarBinding29.getModel();
            if (model17 != null && (carSeries3 = model17.getCarSeries()) != null) {
                carSeries3.set("");
            }
            ActivityChangeCarBinding activityChangeCarBinding30 = this.binding;
            if (activityChangeCarBinding30 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding30 = null;
            }
            activityChangeCarBinding30.textViewCarSeries.setSelected(false);
            ActivityChangeCarBinding activityChangeCarBinding31 = this.binding;
            if (activityChangeCarBinding31 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding31 = null;
            }
            ChangeCarActivityViewModel model18 = activityChangeCarBinding31.getModel();
            if (model18 != null && (enableCarSeries3 = model18.getEnableCarSeries()) != null) {
                enableCarSeries3.set(true);
            }
        } else if (i == 3) {
            ActivityChangeCarBinding activityChangeCarBinding32 = this.binding;
            if (activityChangeCarBinding32 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding32 = null;
            }
            ChangeCarActivityViewModel model19 = activityChangeCarBinding32.getModel();
            if (model19 != null && (enableChangeCar4 = model19.getEnableChangeCar()) != null) {
                enableChangeCar4.set(true);
            }
            List<CarSeries> list4 = this.mSeries;
            if (list4 != null) {
                ActivityChangeCarBinding activityChangeCarBinding33 = this.binding;
                if (activityChangeCarBinding33 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activityChangeCarBinding33 = null;
                }
                carSeries4 = list4.get(activityChangeCarBinding33.viewPickerSelector.getValue());
            } else {
                carSeries4 = null;
            }
            ActivityChangeCarBinding activityChangeCarBinding34 = this.binding;
            if (activityChangeCarBinding34 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding34 = null;
            }
            ChangeCarActivityViewModel model20 = activityChangeCarBinding34.getModel();
            if (model20 != null && (carSeries5 = model20.getCarSeries()) != null) {
                carSeries5.set(carSeries4 != null ? carSeries4.getName() : null);
            }
            ActivityChangeCarBinding activityChangeCarBinding35 = this.binding;
            if (activityChangeCarBinding35 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityChangeCarBinding35 = null;
            }
            activityChangeCarBinding35.textViewCarSeries.setSelected(true);
        }
        ValueAnimator valueAnimator = this.mSelectorDownAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ActivityChangeCarBinding activityChangeCarBinding36 = this.binding;
        if (activityChangeCarBinding36 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding = activityChangeCarBinding36;
        }
        ChangeCarActivityViewModel model21 = activityChangeCarBinding.getModel();
        if (model21 == null || (isSelector = model21.getIsSelector()) == null) {
            return;
        }
        isSelector.set(false);
    }

    private final String[] getMarkNames() {
        List<CarMark> list = this.mMarks;
        Integer numValueOf = list != null ? Integer.valueOf(list.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        String[] strArr = new String[numValueOf.intValue()];
        List<CarMark> list2 = this.mMarks;
        if (list2 != null) {
            int i = 0;
            for (Object obj : list2) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                strArr[i] = ((CarMark) obj).getName();
                i = i2;
            }
        }
        return strArr;
    }

    private final String[] getModelNames() {
        List<CarModel> list = this.mModels;
        Integer numValueOf = list != null ? Integer.valueOf(list.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        String[] strArr = new String[numValueOf.intValue()];
        List<CarModel> list2 = this.mModels;
        if (list2 != null) {
            int i = 0;
            for (Object obj : list2) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                strArr[i] = ((CarModel) obj).getName();
                i = i2;
            }
        }
        return strArr;
    }

    private final String[] getGenerationNames() {
        List<CarGeneration> list = this.mGenerations;
        Integer numValueOf = list != null ? Integer.valueOf(list.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        String[] strArr = new String[numValueOf.intValue()];
        List<CarGeneration> list2 = this.mGenerations;
        if (list2 != null) {
            int i = 0;
            for (Object obj : list2) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                CarGeneration carGeneration = (CarGeneration) obj;
                strArr[i] = carGeneration.getName() + StringUtils.SPACE + carGeneration.getYearsInfo();
                i = i2;
            }
        }
        return strArr;
    }

    private final String[] getSeriesNames() {
        List<CarSeries> list = this.mSeries;
        Integer numValueOf = list != null ? Integer.valueOf(list.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        String[] strArr = new String[numValueOf.intValue()];
        List<CarSeries> list2 = this.mSeries;
        if (list2 != null) {
            int i = 0;
            for (Object obj : list2) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                strArr[i] = ((CarSeries) obj).getName();
                i = i2;
            }
        }
        return strArr;
    }

    private final void initAnimators() {
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        ActivityChangeCarBinding activityChangeCarBinding2 = null;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        int height = activityChangeCarBinding.layoutMain.getHeight();
        ActivityChangeCarBinding activityChangeCarBinding3 = this.binding;
        if (activityChangeCarBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding3 = null;
        }
        int height2 = height - activityChangeCarBinding3.layoutSelector.getHeight();
        ActivityChangeCarBinding activityChangeCarBinding4 = this.binding;
        if (activityChangeCarBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding2 = activityChangeCarBinding4;
        }
        int top = activityChangeCarBinding2.layoutSelector.getTop();
        this.mSelectorUpAnimator = ValueAnimator.ofInt(top, height2);
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda22
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ChangeCarActivity.initAnimators$lambda$24(this.f$0, valueAnimator);
            }
        };
        ValueAnimator valueAnimator = this.mSelectorUpAnimator;
        if (valueAnimator != null) {
            valueAnimator.addUpdateListener(animatorUpdateListener);
        }
        this.mSelectorDownAnimator = ValueAnimator.ofInt(height2, top);
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener2 = new ValueAnimator.AnimatorUpdateListener() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda23
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ChangeCarActivity.initAnimators$lambda$25(this.f$0, valueAnimator2);
            }
        };
        ValueAnimator valueAnimator2 = this.mSelectorDownAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.addUpdateListener(animatorUpdateListener2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initAnimators$lambda$24(ChangeCarActivity this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue = ((Integer) animatedValue).intValue();
        ActivityChangeCarBinding activityChangeCarBinding = this$0.binding;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        activityChangeCarBinding.layoutSelector.setY(iIntValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initAnimators$lambda$25(ChangeCarActivity this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue = ((Integer) animatedValue).intValue();
        ActivityChangeCarBinding activityChangeCarBinding = this$0.binding;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        activityChangeCarBinding.layoutSelector.setY(iIntValue);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final int fetchCarMarkId(String name) {
        List<CarMark> list = this.mMarks;
        CarMark carMark = null;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (StringsKt.equals$default(((CarMark) next).getName(), name, false, 2, null)) {
                    carMark = next;
                    break;
                }
            }
            carMark = carMark;
        }
        if (carMark == null) {
            return 0;
        }
        return carMark.getId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final int fetchCarModelId(String name) {
        List<CarModel> list = this.mModels;
        CarModel carModel = null;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (StringsKt.equals$default(((CarModel) next).getName(), name, false, 2, null)) {
                    carModel = next;
                    break;
                }
            }
            carModel = carModel;
        }
        if (carModel == null) {
            return 0;
        }
        return carModel.getId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final int fetchCarGenerationId(String name) {
        List<CarGeneration> list = this.mGenerations;
        CarGeneration carGeneration = null;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (StringsKt.equals$default(((CarGeneration) next).getName(), name, false, 2, null)) {
                    carGeneration = next;
                    break;
                }
            }
            carGeneration = carGeneration;
        }
        if (carGeneration == null) {
            return 0;
        }
        return carGeneration.getId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final int fetchCarSeriesId(String name) {
        List<CarSeries> list = this.mSeries;
        CarSeries carSeries = null;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (StringsKt.equals$default(((CarSeries) next).getName(), name, false, 2, null)) {
                    carSeries = next;
                    break;
                }
            }
            carSeries = carSeries;
        }
        if (carSeries == null) {
            return 0;
        }
        return carSeries.getId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleChangeCarResponse(BaseResponse response, ChangeCarInfo changeCarInfo) {
        Timber.INSTANCE.i("handleChangeCarResponse: %s", response);
        if (response.isSuccessful()) {
            setChangesInProfile(changeCarInfo);
            getBleManager().executeActivatePresetCommand((short) 0, new InstalledPreset((short) 0, (short) 0, (short) 0, 4, null), new Function0<Unit>() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity.handleChangeCarResponse.1
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
                    ChangeCarActivity.this.getCanFileManager().startUpdate();
                }
            });
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(this, response.getError(), Integer.valueOf(response.getCode()));
        if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
        }
        ProgressDialogFragment progressDialogFragment = this.mProgressDialog;
        ActivityChangeCarBinding activityChangeCarBinding = null;
        if (progressDialogFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
            progressDialogFragment = null;
        }
        if (progressDialogFragment.isResumed()) {
            ProgressDialogFragment progressDialogFragment2 = this.mProgressDialog;
            if (progressDialogFragment2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
                progressDialogFragment2 = null;
            }
            progressDialogFragment2.dismiss();
        }
        UsersManager usersManager = getUsersManager();
        ActivityChangeCarBinding activityChangeCarBinding2 = this.binding;
        if (activityChangeCarBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityChangeCarBinding = activityChangeCarBinding2;
        }
        Snackbar snackbarMakeSnackBarError = usersManager.makeSnackBarError(activityChangeCarBinding.layoutMain, response.getError());
        if (snackbarMakeSnackBarError != null) {
            snackbarMakeSnackBarError.show();
        }
    }

    private final void setChangesInProfile(ChangeCarInfo changeCarInfo) {
        SignInResponse profile = Settings.INSTANCE.getProfile();
        if (profile != null) {
            profile.setCarMarkId(changeCarInfo.getCarMarkId());
            profile.setCarMarkName(changeCarInfo.getCarMarkName());
            profile.setCarModelId(changeCarInfo.getCarModelId());
            profile.setCarModelName(changeCarInfo.getCarModelName());
            profile.setCarGenerationId(changeCarInfo.getCarGenerationId());
            profile.setCarGenerationName(changeCarInfo.getCarGenerationName());
            profile.setCarSerieId(changeCarInfo.getCarSeriesId());
            profile.setCarSerieName(changeCarInfo.getCarSeriesName());
            Settings.saveProfile(profile);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateCanConfigurationsSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        ProgressDialogFragment progressDialogFragment = this.mProgressDialog;
        ProgressDialogFragment progressDialogFragment2 = null;
        if (progressDialogFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
            progressDialogFragment = null;
        }
        if (progressDialogFragment.isResumed()) {
            ProgressDialogFragment progressDialogFragment3 = this.mProgressDialog;
            if (progressDialogFragment3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
            } else {
                progressDialogFragment2 = progressDialogFragment3;
            }
            progressDialogFragment2.dismiss();
        }
        createCanConfigurationSuccessfulAlertDialog().show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateCanConfigurationsErrorEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        ProgressDialogFragment progressDialogFragment = this.mProgressDialog;
        ProgressDialogFragment progressDialogFragment2 = null;
        if (progressDialogFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
            progressDialogFragment = null;
        }
        if (progressDialogFragment.isResumed()) {
            ProgressDialogFragment progressDialogFragment3 = this.mProgressDialog;
            if (progressDialogFragment3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
            } else {
                progressDialogFragment2 = progressDialogFragment3;
            }
            progressDialogFragment2.dismiss();
        }
        if (event.isNetworkError()) {
            createNoCanFileOnServerAlertDialog().show();
        } else if (event.isUploadingOnSchemaError()) {
            String string = getString(R.string.text_error_write_file);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.text_error_write_file)");
            createErrorMessageAlertDialog(string).show();
        }
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

    private final AlertDialog createErrorMessageAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
        builder.setMessage(message).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                ChangeCarActivity.createErrorMessageAlertDialog$lambda$31(this.f$0, dialogInterface);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createErrorMessageAlertDialog$lambda$31(ChangeCarActivity this$0, DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final AlertDialog createNoCanFileOnServerAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
        SignInResponse profile = Settings.INSTANCE.getProfile();
        String str = getString(R.string.text_can_file_not_found) + StringUtils.LF;
        Object[] objArr = new Object[4];
        objArr[0] = profile != null ? profile.getCarMarkName() : null;
        objArr[1] = profile != null ? profile.getCarModelName() : null;
        objArr[2] = profile != null ? profile.getCarGenerationName() : null;
        objArr[3] = profile != null ? profile.getCarSerieName() : null;
        String string = getString(R.string.text_your_car_info, objArr);
        Intrinsics.checkNotNullExpressionValue(string, "getString(\n            R…e?.carSerieName\n        )");
        builder.setMessage(str + string).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                ChangeCarActivity.createNoCanFileOnServerAlertDialog$lambda$32(this.f$0, dialogInterface);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createNoCanFileOnServerAlertDialog$lambda$32(ChangeCarActivity this$0, DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final AlertDialog createCanConfigurationSuccessfulAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        builder.setTitle(R.string.title_done).setMessage(R.string.title_can_file_configured).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$$ExternalSyntheticLambda24
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                ChangeCarActivity.createCanConfigurationSuccessfulAlertDialog$lambda$33(this.f$0, dialogInterface);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createCanConfigurationSuccessfulAlertDialog$lambda$33(ChangeCarActivity this$0, DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleSavedResponse(CarMarksResponse value) {
        String carMarkName;
        boolean z = true;
        Timber.INSTANCE.i("handleResponse: %s", value);
        if (value.isSuccessful()) {
            List<CarMark> marks = value.getMarks();
            if (marks != null && !marks.isEmpty()) {
                z = false;
            }
            if (z) {
                return;
            }
            this.mMarks = value.getMarks();
            ChangeCarInfo changeCarInfo = this.receivedCarInfo;
            if (changeCarInfo == null || (carMarkName = changeCarInfo.getCarMarkName()) == null) {
                return;
            }
            onLoadSavedCarModels(fetchCarMarkId(carMarkName));
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, value.getError(), value.getCode());
        if (alertDialogCreateErrorAlertDialog != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        Dialog dialog = null;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        Snackbar.make(activityChangeCarBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
        Dialog dialog2 = this.mApiResponseProgressDialog;
        if (dialog2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mApiResponseProgressDialog");
        } else {
            dialog = dialog2;
        }
        dialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleSavedResponse(CarModelsResponse value) {
        String carModelName;
        boolean z = true;
        Timber.INSTANCE.i("handleResponse: %s", value);
        if (value.isSuccessful()) {
            List<CarModel> models = value.getModels();
            if (models != null && !models.isEmpty()) {
                z = false;
            }
            if (z) {
                return;
            }
            this.mModels = value.getModels();
            ChangeCarInfo changeCarInfo = this.receivedCarInfo;
            if (changeCarInfo == null || (carModelName = changeCarInfo.getCarModelName()) == null) {
                return;
            }
            onLoadSavedCarGenerations(fetchCarModelId(carModelName));
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, value.getError(), value.getCode());
        if (alertDialogCreateErrorAlertDialog != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        Dialog dialog = null;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        Snackbar.make(activityChangeCarBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
        Dialog dialog2 = this.mApiResponseProgressDialog;
        if (dialog2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mApiResponseProgressDialog");
        } else {
            dialog = dialog2;
        }
        dialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleSavedResponse(CarGenerationsResponse value) {
        String carGenerationName;
        boolean z = true;
        Timber.INSTANCE.i("handleResponse: %s", value);
        if (value.isSuccessful()) {
            List<CarGeneration> generations = value.getGenerations();
            if (generations != null && !generations.isEmpty()) {
                z = false;
            }
            if (z) {
                return;
            }
            this.mGenerations = value.getGenerations();
            ChangeCarInfo changeCarInfo = this.receivedCarInfo;
            if (changeCarInfo == null || (carGenerationName = changeCarInfo.getCarGenerationName()) == null) {
                return;
            }
            onLoadSavedCarSeries(fetchCarGenerationId(carGenerationName));
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, value.getError(), value.getCode());
        if (alertDialogCreateErrorAlertDialog != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        Dialog dialog = null;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        Snackbar.make(activityChangeCarBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
        Dialog dialog2 = this.mApiResponseProgressDialog;
        if (dialog2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mApiResponseProgressDialog");
        } else {
            dialog = dialog2;
        }
        dialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleSavedResponse(CarSeriesResponse value) {
        boolean z = true;
        Timber.INSTANCE.i("handleResponse: %s", value);
        Dialog dialog = null;
        if (value.isSuccessful()) {
            List<CarSeries> series = value.getSeries();
            if (series != null && !series.isEmpty()) {
                z = false;
            }
            if (!z) {
                this.mSeries = value.getSeries();
            }
            Dialog dialog2 = this.mApiResponseProgressDialog;
            if (dialog2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mApiResponseProgressDialog");
            } else {
                dialog = dialog2;
            }
            dialog.dismiss();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, value.getError(), value.getCode());
        if (alertDialogCreateErrorAlertDialog != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        ActivityChangeCarBinding activityChangeCarBinding = this.binding;
        if (activityChangeCarBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityChangeCarBinding = null;
        }
        Snackbar.make(activityChangeCarBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
        Dialog dialog3 = this.mApiResponseProgressDialog;
        if (dialog3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mApiResponseProgressDialog");
        } else {
            dialog = dialog3;
        }
        dialog.dismiss();
    }

    private final Dialog createProgressLoading() {
        Dialog dialog = new Dialog(this, 2131886084);
        dialog.setContentView(R.layout.dialog_progres_delete);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        Window window2 = dialog.getWindow();
        ProgressBar progressBar = window2 != null ? (ProgressBar) window2.findViewById(R.id.progressBar) : null;
        int carFuelType = Settings.INSTANCE.getCarFuelType();
        if (carFuelType != 1) {
            if (carFuelType == 2 && progressBar != null) {
                progressBar.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_progressbar, null));
            }
        } else if (progressBar != null) {
            progressBar.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_progressbar_blue, null));
        }
        return dialog;
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ChangeCarActivity changeCarActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(changeCarActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, changeCarActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new C01961(uriForFile, event, null), 2, null);
    }

    /* compiled from: ChangeCarActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onMessageEvent$1", f = "ChangeCarActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onMessageEvent$1, reason: invalid class name and case insensitive filesystem */
    static final class C01961 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01961(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super C01961> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ChangeCarActivity.this.new C01961(this.$path, this.$event, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01961) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = ChangeCarActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final C00611 c00611 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity.onMessageEvent.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onMessageEvent$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00611.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final AnonymousClass2 anonymousClass2 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity.onMessageEvent.1.2
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
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.main.carinfo.ChangeCarActivity$onMessageEvent$1$$ExternalSyntheticLambda1
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
