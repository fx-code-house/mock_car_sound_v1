package com.thor.app.gui.activities.login;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.NumberPicker;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivitySignUpCarInfoBinding;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.BooleanWearableDataLayer;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.Constants;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.dialogs.ProgressDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.CarManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.CarInfoPreference;
import com.thor.app.settings.Settings;
import com.thor.app.utils.data.DataLayerHelper;
import com.thor.app.utils.security.DeviceLockingUtilsKt;
import com.thor.businessmodule.viewmodel.login.SignUpCarInfoActivityViewModel;
import com.thor.networkmodule.model.SignUpInfo;
import com.thor.networkmodule.model.car.CarGeneration;
import com.thor.networkmodule.model.car.CarMark;
import com.thor.networkmodule.model.car.CarModel;
import com.thor.networkmodule.model.car.CarSeries;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.SignUpResponse;
import com.thor.networkmodule.model.responses.car.CarGenerationsResponse;
import com.thor.networkmodule.model.responses.car.CarMarksResponse;
import com.thor.networkmodule.model.responses.car.CarModelsResponse;
import com.thor.networkmodule.model.responses.car.CarSeriesResponse;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.StringUtils;
import timber.log.Timber;

/* compiled from: SignUpCarInfoActivity.kt */
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u0000 \\2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001\\B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010)\u001a\u00020 2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020 2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010-\u001a\u00020 2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010.\u001a\u00020 2\u0006\u0010*\u001a\u00020+H\u0002J\u0015\u0010/\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010+00H\u0002¢\u0006\u0002\u00101J\u0015\u00102\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010+00H\u0002¢\u0006\u0002\u00101J\u0015\u00103\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010+00H\u0002¢\u0006\u0002\u00101J\u0015\u00104\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010+00H\u0002¢\u0006\u0002\u00101J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u000208H\u0002J\u0010\u00109\u001a\u0002062\u0006\u0010:\u001a\u00020;H\u0002J\u0010\u00109\u001a\u0002062\u0006\u0010:\u001a\u00020<H\u0002J\u0010\u00109\u001a\u0002062\u0006\u0010:\u001a\u00020=H\u0002J\u0010\u00109\u001a\u0002062\u0006\u0010:\u001a\u00020>H\u0002J\u0010\u0010?\u001a\u0002062\u0006\u0010@\u001a\u00020AH\u0002J\b\u0010B\u001a\u000206H\u0002J\b\u0010C\u001a\u000206H\u0002J\u0012\u0010D\u001a\u0002062\b\u0010E\u001a\u0004\u0018\u00010FH\u0016J\u0012\u0010G\u001a\u0002062\b\u0010H\u001a\u0004\u0018\u00010IH\u0014J\u0010\u0010J\u001a\u0002062\u0006\u0010K\u001a\u00020LH\u0016J\b\u0010M\u001a\u000206H\u0014J\u0010\u0010N\u001a\u0002062\u0006\u0010O\u001a\u00020 H\u0002J\b\u0010P\u001a\u000206H\u0002J\u0010\u0010Q\u001a\u0002062\u0006\u0010R\u001a\u00020 H\u0002J\u0010\u0010S\u001a\u0002062\u0006\u0010T\u001a\u00020 H\u0002J\b\u0010U\u001a\u000206H\u0002J\b\u0010V\u001a\u000206H\u0002J\b\u0010W\u001a\u000206H\u0002J\b\u0010X\u001a\u000206H\u0002J\b\u0010Y\u001a\u000206H\u0002J\b\u0010Z\u001a\u000206H\u0014J\b\u0010[\u001a\u000206H\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\f\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\"\u001a\n\u0012\u0004\u0012\u00020#\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010$\u001a\u00020%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\f\u001a\u0004\b&\u0010'¨\u0006]"}, d2 = {"Lcom/thor/app/gui/activities/login/SignUpCarInfoActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/view/View$OnClickListener;", "Lcom/google/android/gms/wearable/DataClient$OnDataChangedListener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivitySignUpCarInfoBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "destroy", "", "mDataLayerHelper", "Lcom/thor/app/utils/data/DataLayerHelper;", "getMDataLayerHelper", "()Lcom/thor/app/utils/data/DataLayerHelper;", "mDataLayerHelper$delegate", "mGenerations", "", "Lcom/thor/networkmodule/model/car/CarGeneration;", "mMarks", "Lcom/thor/networkmodule/model/car/CarMark;", "mModels", "Lcom/thor/networkmodule/model/car/CarModel;", "mProgressDialog", "Lcom/thor/app/gui/dialog/dialogs/ProgressDialogFragment;", "mSelectorDownAnimator", "Landroid/animation/ValueAnimator;", "mSelectorStep", "", "mSelectorUpAnimator", "mSeries", "Lcom/thor/networkmodule/model/car/CarSeries;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "fetchCarGenerationId", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "fetchCarMarkId", "fetchCarModelId", "fetchCarSeriesId", "getGenerationNames", "", "()[Ljava/lang/String;", "getMarkNames", "getModelNames", "getSeriesNames", "handleError", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "value", "Lcom/thor/networkmodule/model/responses/car/CarGenerationsResponse;", "Lcom/thor/networkmodule/model/responses/car/CarMarksResponse;", "Lcom/thor/networkmodule/model/responses/car/CarModelsResponse;", "Lcom/thor/networkmodule/model/responses/car/CarSeriesResponse;", "handleSignUpResponse", "response", "Lcom/thor/networkmodule/model/responses/SignUpResponse;", "initAnimators", "onApplySelector", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDataChanged", "dataEvents", "Lcom/google/android/gms/wearable/DataEventBuffer;", "onDestroy", "onLoadCarGenerations", "carModelId", "onLoadCarMarks", "onLoadCarModels", "carMarkId", "onLoadCarSeries", "carGenerationId", "onSelectCarGeneration", "onSelectCarMark", "onSelectCarModel", "onSelectCarSeries", "onSignUp", "onStart", "onStop", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SignUpCarInfoActivity extends AppCompatActivity implements View.OnClickListener, DataClient.OnDataChangedListener {
    public static final int SELECTOR_STEP_GENERATION = 2;
    public static final int SELECTOR_STEP_MARK = 0;
    public static final int SELECTOR_STEP_MODEL = 1;
    public static final int SELECTOR_STEP_SERIES = 3;
    private ActivitySignUpCarInfoBinding binding;
    private List<CarGeneration> mGenerations;
    private List<CarMark> mMarks;
    private List<CarModel> mModels;
    private ProgressDialogFragment mProgressDialog;
    private ValueAnimator mSelectorDownAnimator;
    private int mSelectorStep;
    private ValueAnimator mSelectorUpAnimator;
    private List<CarSeries> mSeries;
    private boolean destroy = true;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: mDataLayerHelper$delegate, reason: from kotlin metadata */
    private final Lazy mDataLayerHelper = LazyKt.lazy(new Function0<DataLayerHelper>() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$mDataLayerHelper$2
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
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final DataLayerHelper getMDataLayerHelper() {
        Object value = this.mDataLayerHelper.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mDataLayerHelper>(...)");
        return (DataLayerHelper) value;
    }

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_car_info);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.l…ctivity_sign_up_car_info)");
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = (ActivitySignUpCarInfoBinding) contentView;
        this.binding = activitySignUpCarInfoBinding;
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = null;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        activitySignUpCarInfoBinding.setModel(new SignUpCarInfoActivityViewModel());
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
        if (activitySignUpCarInfoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding3 = null;
        }
        activitySignUpCarInfoBinding3.toolbarWidget.setHomeButtonVisibility(true);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding4 = this.binding;
        if (activitySignUpCarInfoBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding4 = null;
        }
        activitySignUpCarInfoBinding4.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SignUpCarInfoActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding5 = this.binding;
        if (activitySignUpCarInfoBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding5 = null;
        }
        activitySignUpCarInfoBinding5.setSignUpInfo((SignUpInfo) getIntent().getParcelableExtra(SignUpInfo.BUNDLE_NAME));
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding6 = this.binding;
        if (activitySignUpCarInfoBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding6 = null;
        }
        SignUpCarInfoActivity signUpCarInfoActivity = this;
        activitySignUpCarInfoBinding6.textViewCarMark.setOnClickListener(signUpCarInfoActivity);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding7 = this.binding;
        if (activitySignUpCarInfoBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding7 = null;
        }
        activitySignUpCarInfoBinding7.textViewCarModel.setOnClickListener(signUpCarInfoActivity);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding8 = this.binding;
        if (activitySignUpCarInfoBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding8 = null;
        }
        activitySignUpCarInfoBinding8.textViewCarGeneration.setOnClickListener(signUpCarInfoActivity);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding9 = this.binding;
        if (activitySignUpCarInfoBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding9 = null;
        }
        activitySignUpCarInfoBinding9.textViewCarSeries.setOnClickListener(signUpCarInfoActivity);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding10 = this.binding;
        if (activitySignUpCarInfoBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding10 = null;
        }
        activitySignUpCarInfoBinding10.buttonContinue.setOnClickListener(signUpCarInfoActivity);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding11 = this.binding;
        if (activitySignUpCarInfoBinding11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpCarInfoBinding2 = activitySignUpCarInfoBinding11;
        }
        activitySignUpCarInfoBinding2.textViewApplySelector.setOnClickListener(signUpCarInfoActivity);
        ProgressDialogFragment progressDialogFragmentNewInstance = ProgressDialogFragment.newInstance();
        Intrinsics.checkNotNullExpressionValue(progressDialogFragmentNewInstance, "newInstance()");
        this.mProgressDialog = progressDialogFragmentNewInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(SignUpCarInfoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        Wearable.getDataClient((Activity) this).addListener(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        if (this.destroy) {
            getBleManager().disconnect(true);
            Settings.removeAllProperties();
            SignUpCarInfoActivity signUpCarInfoActivity = this;
            Settings.clearCookies(signUpCarInfoActivity);
            DataLayerManager.INSTANCE.from(signUpCarInfoActivity).sendIsAccessSession(false);
        }
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        Wearable.getDataClient((Activity) this).removeListener(this);
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
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this.binding;
            if (activitySignUpCarInfoBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding = null;
            }
            SignUpCarInfoActivityViewModel model = activitySignUpCarInfoBinding.getModel();
            if (model != null && (carMark = model.getCarMark()) != null) {
                str = carMark.get();
            }
            Intrinsics.checkNotNull(str);
            onLoadCarModels(fetchCarMarkId(str));
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_car_generation) {
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = this.binding;
            if (activitySignUpCarInfoBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding2 = null;
            }
            SignUpCarInfoActivityViewModel model2 = activitySignUpCarInfoBinding2.getModel();
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
                if (numValueOf != null && numValueOf.intValue() == R.id.button_continue) {
                    onSignUp();
                    return;
                }
                return;
            }
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
        if (activitySignUpCarInfoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding3 = null;
        }
        SignUpCarInfoActivityViewModel model3 = activitySignUpCarInfoBinding3.getModel();
        if (model3 != null && (carGeneration = model3.getCarGeneration()) != null) {
            str = carGeneration.get();
        }
        Intrinsics.checkNotNull(str);
        onLoadCarSeries(fetchCarGenerationId(str));
    }

    @Override // com.google.android.gms.wearable.DataClient.OnDataChangedListener, com.google.android.gms.wearable.DataApi.DataListener
    public void onDataChanged(DataEventBuffer dataEvents) {
        Intrinsics.checkNotNullParameter(dataEvents, "dataEvents");
        Iterator<DataEvent> it = dataEvents.iterator();
        while (it.hasNext()) {
            DataEvent next = it.next();
            if (next.getType() == 1 && Intrinsics.areEqual(BooleanWearableDataLayer.CURRENT_ACTIVITY_PATH, next.getDataItem().getUri().getPath())) {
                Timber.INSTANCE.d("Data Changed for CURRENT_ACTIVITY_PATH", new Object[0]);
                getMDataLayerHelper().onStartSignUpWearableActivity();
            }
        }
    }

    /* compiled from: SignUpCarInfoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.login.SignUpCarInfoActivity$onLoadCarMarks$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01521 extends FunctionReferenceImpl implements Function1<CarMarksResponse, Unit> {
        C01521(Object obj) {
            super(1, obj, SignUpCarInfoActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/car/CarMarksResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarMarksResponse carMarksResponse) {
            invoke2(carMarksResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarMarksResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SignUpCarInfoActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SignUpCarInfoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.login.SignUpCarInfoActivity$onLoadCarMarks$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01532 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01532(Object obj) {
            super(1, obj, SignUpCarInfoActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SignUpCarInfoActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadCarMarks() {
        Observable<CarMarksResponse> observableFetchMarks = CarManager.INSTANCE.from(this).fetchMarks();
        final C01521 c01521 = new C01521(this);
        Consumer<? super CarMarksResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpCarInfoActivity.onLoadCarMarks$lambda$1(c01521, obj);
            }
        };
        final C01532 c01532 = new C01532(this);
        observableFetchMarks.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpCarInfoActivity.onLoadCarMarks$lambda$2(c01532, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarMarks$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarMarks$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: SignUpCarInfoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.login.SignUpCarInfoActivity$onLoadCarModels$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01541 extends FunctionReferenceImpl implements Function1<CarModelsResponse, Unit> {
        C01541(Object obj) {
            super(1, obj, SignUpCarInfoActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/car/CarModelsResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarModelsResponse carModelsResponse) {
            invoke2(carModelsResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarModelsResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SignUpCarInfoActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SignUpCarInfoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.login.SignUpCarInfoActivity$onLoadCarModels$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01552 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01552(Object obj) {
            super(1, obj, SignUpCarInfoActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SignUpCarInfoActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadCarModels(int carMarkId) {
        Observable<CarModelsResponse> observableFetchModels = CarManager.INSTANCE.from(this).fetchModels(carMarkId);
        final C01541 c01541 = new C01541(this);
        Consumer<? super CarModelsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpCarInfoActivity.onLoadCarModels$lambda$3(c01541, obj);
            }
        };
        final C01552 c01552 = new C01552(this);
        observableFetchModels.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpCarInfoActivity.onLoadCarModels$lambda$4(c01552, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarModels$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarModels$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: SignUpCarInfoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.login.SignUpCarInfoActivity$onLoadCarGenerations$1, reason: invalid class name */
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<CarGenerationsResponse, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj, SignUpCarInfoActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/car/CarGenerationsResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarGenerationsResponse carGenerationsResponse) {
            invoke2(carGenerationsResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarGenerationsResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SignUpCarInfoActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SignUpCarInfoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.login.SignUpCarInfoActivity$onLoadCarGenerations$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, SignUpCarInfoActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SignUpCarInfoActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadCarGenerations(int carModelId) {
        Observable<CarGenerationsResponse> observableFetchGenerations = CarManager.INSTANCE.from(this).fetchGenerations(carModelId);
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1(this);
        Consumer<? super CarGenerationsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpCarInfoActivity.onLoadCarGenerations$lambda$5(anonymousClass1, obj);
            }
        };
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
        observableFetchGenerations.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpCarInfoActivity.onLoadCarGenerations$lambda$6(anonymousClass2, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarGenerations$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarGenerations$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: SignUpCarInfoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.login.SignUpCarInfoActivity$onLoadCarSeries$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01561 extends FunctionReferenceImpl implements Function1<CarSeriesResponse, Unit> {
        C01561(Object obj) {
            super(1, obj, SignUpCarInfoActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/car/CarSeriesResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CarSeriesResponse carSeriesResponse) {
            invoke2(carSeriesResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CarSeriesResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SignUpCarInfoActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SignUpCarInfoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.login.SignUpCarInfoActivity$onLoadCarSeries$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01572 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01572(Object obj) {
            super(1, obj, SignUpCarInfoActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SignUpCarInfoActivity) this.receiver).handleError(p0);
        }
    }

    private final void onLoadCarSeries(int carGenerationId) {
        Observable<CarSeriesResponse> observableFetchSeries = CarManager.INSTANCE.from(this).fetchSeries(carGenerationId);
        final C01561 c01561 = new C01561(this);
        Consumer<? super CarSeriesResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpCarInfoActivity.onLoadCarSeries$lambda$7(c01561, obj);
            }
        };
        final C01572 c01572 = new C01572(this);
        observableFetchSeries.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpCarInfoActivity.onLoadCarSeries$lambda$8(c01572, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarSeries$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadCarSeries$lambda$8(Function1 tmp0, Object obj) {
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
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(this, value.getError(), Integer.valueOf(value.getCode()));
        if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this.binding;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        Snackbar.make(activitySignUpCarInfoBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
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
        AlertDialog alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, value.getError(), null, 4, null);
        if (alertDialogCreateErrorAlertDialog$default != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this.binding;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        Snackbar.make(activitySignUpCarInfoBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(CarGenerationsResponse value) {
        ObservableBoolean enableCarGeneration;
        Timber.INSTANCE.i("handleResponse: %s", value);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = null;
        if (value.isSuccessful()) {
            List<CarGeneration> generations = value.getGenerations();
            if (generations == null || generations.isEmpty()) {
                return;
            }
            this.mGenerations = value.getGenerations();
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = this.binding;
            if (activitySignUpCarInfoBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySignUpCarInfoBinding = activitySignUpCarInfoBinding2;
            }
            SignUpCarInfoActivityViewModel model = activitySignUpCarInfoBinding.getModel();
            if (model != null && (enableCarGeneration = model.getEnableCarGeneration()) != null) {
                enableCarGeneration.set(true);
            }
            onSelectCarGeneration();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, value.getError(), null, 4, null);
        if (alertDialogCreateErrorAlertDialog$default != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
        if (activitySignUpCarInfoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpCarInfoBinding = activitySignUpCarInfoBinding3;
        }
        Snackbar.make(activitySignUpCarInfoBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(CarSeriesResponse value) {
        ObservableBoolean enableCarSeries;
        Timber.INSTANCE.i("handleResponse: %s", value);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = null;
        if (value.isSuccessful()) {
            List<CarSeries> series = value.getSeries();
            if (series == null || series.isEmpty()) {
                return;
            }
            this.mSeries = value.getSeries();
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = this.binding;
            if (activitySignUpCarInfoBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySignUpCarInfoBinding = activitySignUpCarInfoBinding2;
            }
            SignUpCarInfoActivityViewModel model = activitySignUpCarInfoBinding.getModel();
            if (model != null && (enableCarSeries = model.getEnableCarSeries()) != null) {
                enableCarSeries.set(true);
            }
            onSelectCarSeries();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, value.getError(), null, 4, null);
        if (alertDialogCreateErrorAlertDialog$default != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
        if (activitySignUpCarInfoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpCarInfoBinding = activitySignUpCarInfoBinding3;
        }
        Snackbar.make(activitySignUpCarInfoBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null);
            if (alertDialogCreateErrorAlertDialog$default2 != null) {
                alertDialogCreateErrorAlertDialog$default2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null)) != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        Timber.INSTANCE.e(error);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this.binding;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        Snackbar.make(activitySignUpCarInfoBinding.layoutMain, R.string.warning_service_is_unavailable, 0).show();
    }

    private final void onSelectCarMark() {
        ObservableBoolean isSelector;
        this.mSelectorStep = 0;
        List<CarMark> list = this.mMarks;
        if (list == null || list.isEmpty()) {
            return;
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this.binding;
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = null;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        activitySignUpCarInfoBinding.viewPickerSelector.setMinValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
        if (activitySignUpCarInfoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding3 = null;
        }
        activitySignUpCarInfoBinding3.viewPickerSelector.setMaxValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding4 = this.binding;
        if (activitySignUpCarInfoBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding4 = null;
        }
        activitySignUpCarInfoBinding4.viewPickerSelector.setDisplayedValues(null);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding5 = this.binding;
        if (activitySignUpCarInfoBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding5 = null;
        }
        activitySignUpCarInfoBinding5.viewPickerSelector.setDisplayedValues(getMarkNames());
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding6 = this.binding;
        if (activitySignUpCarInfoBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding6 = null;
        }
        activitySignUpCarInfoBinding6.viewPickerSelector.setMinValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding7 = this.binding;
        if (activitySignUpCarInfoBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding7 = null;
        }
        NumberPicker numberPicker = activitySignUpCarInfoBinding7.viewPickerSelector;
        List<CarMark> list2 = this.mMarks;
        Integer numValueOf = list2 != null ? Integer.valueOf(list2.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        numberPicker.setMaxValue(numValueOf.intValue() - 1);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding8 = this.binding;
        if (activitySignUpCarInfoBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding8 = null;
        }
        activitySignUpCarInfoBinding8.viewPickerSelector.setValue(0);
        initAnimators();
        ValueAnimator valueAnimator = this.mSelectorUpAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding9 = this.binding;
        if (activitySignUpCarInfoBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpCarInfoBinding2 = activitySignUpCarInfoBinding9;
        }
        SignUpCarInfoActivityViewModel model = activitySignUpCarInfoBinding2.getModel();
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
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this.binding;
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = null;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        activitySignUpCarInfoBinding.viewPickerSelector.setMinValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
        if (activitySignUpCarInfoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding3 = null;
        }
        activitySignUpCarInfoBinding3.viewPickerSelector.setMaxValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding4 = this.binding;
        if (activitySignUpCarInfoBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding4 = null;
        }
        activitySignUpCarInfoBinding4.viewPickerSelector.setDisplayedValues(null);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding5 = this.binding;
        if (activitySignUpCarInfoBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding5 = null;
        }
        activitySignUpCarInfoBinding5.viewPickerSelector.setDisplayedValues(getModelNames());
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding6 = this.binding;
        if (activitySignUpCarInfoBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding6 = null;
        }
        activitySignUpCarInfoBinding6.viewPickerSelector.setMinValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding7 = this.binding;
        if (activitySignUpCarInfoBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding7 = null;
        }
        NumberPicker numberPicker = activitySignUpCarInfoBinding7.viewPickerSelector;
        List<CarModel> list2 = this.mModels;
        Integer numValueOf = list2 != null ? Integer.valueOf(list2.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        numberPicker.setMaxValue(numValueOf.intValue() - 1);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding8 = this.binding;
        if (activitySignUpCarInfoBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding8 = null;
        }
        activitySignUpCarInfoBinding8.viewPickerSelector.setValue(0);
        initAnimators();
        ValueAnimator valueAnimator = this.mSelectorUpAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding9 = this.binding;
        if (activitySignUpCarInfoBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpCarInfoBinding2 = activitySignUpCarInfoBinding9;
        }
        SignUpCarInfoActivityViewModel model = activitySignUpCarInfoBinding2.getModel();
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
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this.binding;
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = null;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        activitySignUpCarInfoBinding.viewPickerSelector.setMinValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
        if (activitySignUpCarInfoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding3 = null;
        }
        activitySignUpCarInfoBinding3.viewPickerSelector.setMaxValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding4 = this.binding;
        if (activitySignUpCarInfoBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding4 = null;
        }
        activitySignUpCarInfoBinding4.viewPickerSelector.setDisplayedValues(null);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding5 = this.binding;
        if (activitySignUpCarInfoBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding5 = null;
        }
        activitySignUpCarInfoBinding5.viewPickerSelector.setDisplayedValues(getGenerationNames());
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding6 = this.binding;
        if (activitySignUpCarInfoBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding6 = null;
        }
        activitySignUpCarInfoBinding6.viewPickerSelector.setMinValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding7 = this.binding;
        if (activitySignUpCarInfoBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding7 = null;
        }
        NumberPicker numberPicker = activitySignUpCarInfoBinding7.viewPickerSelector;
        List<CarGeneration> list2 = this.mGenerations;
        Integer numValueOf = list2 != null ? Integer.valueOf(list2.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        numberPicker.setMaxValue(numValueOf.intValue() - 1);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding8 = this.binding;
        if (activitySignUpCarInfoBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding8 = null;
        }
        activitySignUpCarInfoBinding8.viewPickerSelector.setValue(0);
        initAnimators();
        ValueAnimator valueAnimator = this.mSelectorUpAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding9 = this.binding;
        if (activitySignUpCarInfoBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpCarInfoBinding2 = activitySignUpCarInfoBinding9;
        }
        SignUpCarInfoActivityViewModel model = activitySignUpCarInfoBinding2.getModel();
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
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this.binding;
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = null;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        activitySignUpCarInfoBinding.viewPickerSelector.setMinValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
        if (activitySignUpCarInfoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding3 = null;
        }
        activitySignUpCarInfoBinding3.viewPickerSelector.setMaxValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding4 = this.binding;
        if (activitySignUpCarInfoBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding4 = null;
        }
        activitySignUpCarInfoBinding4.viewPickerSelector.setDisplayedValues(null);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding5 = this.binding;
        if (activitySignUpCarInfoBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding5 = null;
        }
        activitySignUpCarInfoBinding5.viewPickerSelector.setDisplayedValues(getSeriesNames());
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding6 = this.binding;
        if (activitySignUpCarInfoBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding6 = null;
        }
        activitySignUpCarInfoBinding6.viewPickerSelector.setMinValue(0);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding7 = this.binding;
        if (activitySignUpCarInfoBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding7 = null;
        }
        NumberPicker numberPicker = activitySignUpCarInfoBinding7.viewPickerSelector;
        List<CarSeries> list2 = this.mSeries;
        Integer numValueOf = list2 != null ? Integer.valueOf(list2.size()) : null;
        Intrinsics.checkNotNull(numValueOf);
        numberPicker.setMaxValue(numValueOf.intValue() - 1);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding8 = this.binding;
        if (activitySignUpCarInfoBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding8 = null;
        }
        activitySignUpCarInfoBinding8.viewPickerSelector.setValue(0);
        initAnimators();
        ValueAnimator valueAnimator = this.mSelectorUpAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding9 = this.binding;
        if (activitySignUpCarInfoBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpCarInfoBinding2 = activitySignUpCarInfoBinding9;
        }
        SignUpCarInfoActivityViewModel model = activitySignUpCarInfoBinding2.getModel();
        if (model == null || (isSelector = model.getIsSelector()) == null) {
            return;
        }
        isSelector.set(true);
    }

    private final void onSignUp() {
        ObservableField<String> carSeries;
        ObservableField<String> carGeneration;
        ObservableField<String> carModel;
        ObservableField<String> carMark;
        ObservableField<String> carGeneration2;
        ObservableField<String> carSeries2;
        ObservableField<String> carMark2;
        ObservableField<String> carModel2;
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this.binding;
        String str = null;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        SignUpInfo signUpInfo = activitySignUpCarInfoBinding.getSignUpInfo();
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = this.binding;
        if (activitySignUpCarInfoBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding2 = null;
        }
        SignUpCarInfoActivityViewModel model = activitySignUpCarInfoBinding2.getModel();
        String str2 = (model == null || (carModel2 = model.getCarModel()) == null) ? null : carModel2.get();
        Intrinsics.checkNotNull(str2);
        int iFetchCarModelId = fetchCarModelId(str2);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
        if (activitySignUpCarInfoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding3 = null;
        }
        SignUpCarInfoActivityViewModel model2 = activitySignUpCarInfoBinding3.getModel();
        String str3 = (model2 == null || (carMark2 = model2.getCarMark()) == null) ? null : carMark2.get();
        Intrinsics.checkNotNull(str3);
        int iFetchCarMarkId = fetchCarMarkId(str3);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding4 = this.binding;
        if (activitySignUpCarInfoBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding4 = null;
        }
        SignUpCarInfoActivityViewModel model3 = activitySignUpCarInfoBinding4.getModel();
        String str4 = (model3 == null || (carSeries2 = model3.getCarSeries()) == null) ? null : carSeries2.get();
        Intrinsics.checkNotNull(str4);
        int iFetchCarSeriesId = fetchCarSeriesId(str4);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding5 = this.binding;
        if (activitySignUpCarInfoBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding5 = null;
        }
        SignUpCarInfoActivityViewModel model4 = activitySignUpCarInfoBinding5.getModel();
        String str5 = (model4 == null || (carGeneration2 = model4.getCarGeneration()) == null) ? null : carGeneration2.get();
        Intrinsics.checkNotNull(str5);
        int iFetchCarGenerationId = fetchCarGenerationId(str5);
        if (signUpInfo != null) {
            signUpInfo.setCarMarkId(iFetchCarMarkId);
        }
        if (signUpInfo != null) {
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding6 = this.binding;
            if (activitySignUpCarInfoBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding6 = null;
            }
            SignUpCarInfoActivityViewModel model5 = activitySignUpCarInfoBinding6.getModel();
            signUpInfo.setCarMarkName((model5 == null || (carMark = model5.getCarMark()) == null) ? null : carMark.get());
        }
        if (signUpInfo != null) {
            signUpInfo.setCarModelId(iFetchCarModelId);
        }
        if (signUpInfo != null) {
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding7 = this.binding;
            if (activitySignUpCarInfoBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding7 = null;
            }
            SignUpCarInfoActivityViewModel model6 = activitySignUpCarInfoBinding7.getModel();
            signUpInfo.setCarModelName((model6 == null || (carModel = model6.getCarModel()) == null) ? null : carModel.get());
        }
        if (signUpInfo != null) {
            signUpInfo.setCarGenerationId(iFetchCarGenerationId);
        }
        if (signUpInfo != null) {
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding8 = this.binding;
            if (activitySignUpCarInfoBinding8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding8 = null;
            }
            SignUpCarInfoActivityViewModel model7 = activitySignUpCarInfoBinding8.getModel();
            signUpInfo.setCarGenerationName((model7 == null || (carGeneration = model7.getCarGeneration()) == null) ? null : carGeneration.get());
        }
        if (signUpInfo != null) {
            signUpInfo.setCarSeriesId(iFetchCarSeriesId);
        }
        if (signUpInfo != null) {
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding9 = this.binding;
            if (activitySignUpCarInfoBinding9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding9 = null;
            }
            SignUpCarInfoActivityViewModel model8 = activitySignUpCarInfoBinding9.getModel();
            if (model8 != null && (carSeries = model8.getCarSeries()) != null) {
                str = carSeries.get();
            }
            signUpInfo.setCarSeriesName(str);
        }
        Timber.INSTANCE.i("SignUpInfo: %s", signUpInfo);
        CarInfoPreference.INSTANCE.setCarModelID(iFetchCarModelId);
        CarInfoPreference.INSTANCE.setCarMarkId(iFetchCarMarkId);
        CarInfoPreference.INSTANCE.setCarSerieId(iFetchCarSeriesId);
        CarInfoPreference.INSTANCE.setCarGenerationId(iFetchCarGenerationId);
        new SignInResponse(0, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
        Observable<SignInResponse> observableConnectToolsDevice = getUsersManager().connectToolsDevice();
        final Function1<SignInResponse, Unit> function1 = new Function1<SignInResponse, Unit>() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity.onSignUp.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SignInResponse signInResponse) {
                invoke2(signInResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(SignInResponse signInResponse) {
                if (!signInResponse.isSuccessful()) {
                    if (signInResponse.getCode() == 888) {
                        DeviceLockingUtilsKt.onDeviceLocked(SignUpCarInfoActivity.this);
                        return;
                    }
                    AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, SignUpCarInfoActivity.this, signInResponse.getError(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialogWithSendLogOption$default != null) {
                        alertDialogCreateErrorAlertDialogWithSendLogOption$default.show();
                        return;
                    }
                    return;
                }
                Settings.saveProfile(signInResponse);
            }
        };
        Consumer<? super SignInResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpCarInfoActivity.onSignUp$lambda$9(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity.onSignUp.2
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
                AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default;
                if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                    AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default2 = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, SignUpCarInfoActivity.this, th.getMessage(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialogWithSendLogOption$default2 != null) {
                        alertDialogCreateErrorAlertDialogWithSendLogOption$default2.show();
                        return;
                    }
                    return;
                }
                if (!Intrinsics.areEqual(th.getMessage(), "HTTP 500") || (alertDialogCreateErrorAlertDialogWithSendLogOption$default = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, SignUpCarInfoActivity.this, th.getMessage(), null, 4, null)) == null) {
                    return;
                }
                alertDialogCreateErrorAlertDialogWithSendLogOption$default.show();
            }
        };
        observableConnectToolsDevice.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpCarInfoActivity.onSignUp$lambda$10(function12, obj);
            }
        });
        this.destroy = false;
        Intent intent = new Intent(this, (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSignUp$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSignUp$lambda$10(Function1 tmp0, Object obj) {
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
        ObservableBoolean enableSignUp;
        ObservableBoolean isSelector;
        CarModel carModel2;
        ObservableBoolean enableCarSeries2;
        ObservableBoolean enableCarGeneration2;
        ObservableField<String> carSeries2;
        ObservableField<String> carGeneration2;
        ObservableField<String> carModel3;
        ObservableBoolean enableSignUp2;
        CarGeneration carGeneration3;
        ObservableBoolean enableCarSeries3;
        ObservableField<String> carSeries3;
        ObservableField<String> carGeneration4;
        ObservableBoolean enableSignUp3;
        CarSeries carSeries4;
        ObservableField<String> carSeries5;
        ObservableBoolean enableSignUp4;
        int i = this.mSelectorStep;
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = null;
        if (i == 0) {
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = this.binding;
            if (activitySignUpCarInfoBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding2 = null;
            }
            SignUpCarInfoActivityViewModel model = activitySignUpCarInfoBinding2.getModel();
            if (model != null && (enableSignUp = model.getEnableSignUp()) != null) {
                enableSignUp.set(false);
            }
            List<CarMark> list = this.mMarks;
            if (list != null) {
                ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
                if (activitySignUpCarInfoBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activitySignUpCarInfoBinding3 = null;
                }
                carMark = list.get(activitySignUpCarInfoBinding3.viewPickerSelector.getValue());
            } else {
                carMark = null;
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding4 = this.binding;
            if (activitySignUpCarInfoBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding4 = null;
            }
            SignUpCarInfoActivityViewModel model2 = activitySignUpCarInfoBinding4.getModel();
            if (model2 != null && (carMark2 = model2.getCarMark()) != null) {
                carMark2.set(carMark != null ? carMark.getName() : null);
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding5 = this.binding;
            if (activitySignUpCarInfoBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding5 = null;
            }
            activitySignUpCarInfoBinding5.textViewCarMark.setSelected(true);
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding6 = this.binding;
            if (activitySignUpCarInfoBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding6 = null;
            }
            SignUpCarInfoActivityViewModel model3 = activitySignUpCarInfoBinding6.getModel();
            if (model3 != null && (carModel = model3.getCarModel()) != null) {
                carModel.set("");
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding7 = this.binding;
            if (activitySignUpCarInfoBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding7 = null;
            }
            activitySignUpCarInfoBinding7.textViewCarModel.setSelected(false);
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding8 = this.binding;
            if (activitySignUpCarInfoBinding8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding8 = null;
            }
            SignUpCarInfoActivityViewModel model4 = activitySignUpCarInfoBinding8.getModel();
            if (model4 != null && (carGeneration = model4.getCarGeneration()) != null) {
                carGeneration.set("");
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding9 = this.binding;
            if (activitySignUpCarInfoBinding9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding9 = null;
            }
            activitySignUpCarInfoBinding9.textViewCarGeneration.setSelected(false);
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding10 = this.binding;
            if (activitySignUpCarInfoBinding10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding10 = null;
            }
            SignUpCarInfoActivityViewModel model5 = activitySignUpCarInfoBinding10.getModel();
            if (model5 != null && (carSeries = model5.getCarSeries()) != null) {
                carSeries.set("");
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding11 = this.binding;
            if (activitySignUpCarInfoBinding11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding11 = null;
            }
            activitySignUpCarInfoBinding11.textViewCarSeries.setSelected(false);
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding12 = this.binding;
            if (activitySignUpCarInfoBinding12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding12 = null;
            }
            SignUpCarInfoActivityViewModel model6 = activitySignUpCarInfoBinding12.getModel();
            if (model6 != null && (enableCarModel = model6.getEnableCarModel()) != null) {
                enableCarModel.set(true);
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding13 = this.binding;
            if (activitySignUpCarInfoBinding13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding13 = null;
            }
            SignUpCarInfoActivityViewModel model7 = activitySignUpCarInfoBinding13.getModel();
            if (model7 != null && (enableCarGeneration = model7.getEnableCarGeneration()) != null) {
                enableCarGeneration.set(false);
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding14 = this.binding;
            if (activitySignUpCarInfoBinding14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding14 = null;
            }
            SignUpCarInfoActivityViewModel model8 = activitySignUpCarInfoBinding14.getModel();
            if (model8 != null && (enableCarSeries = model8.getEnableCarSeries()) != null) {
                enableCarSeries.set(false);
            }
        } else if (i == 1) {
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding15 = this.binding;
            if (activitySignUpCarInfoBinding15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding15 = null;
            }
            SignUpCarInfoActivityViewModel model9 = activitySignUpCarInfoBinding15.getModel();
            if (model9 != null && (enableSignUp2 = model9.getEnableSignUp()) != null) {
                enableSignUp2.set(false);
            }
            List<CarModel> list2 = this.mModels;
            if (list2 != null) {
                ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding16 = this.binding;
                if (activitySignUpCarInfoBinding16 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activitySignUpCarInfoBinding16 = null;
                }
                carModel2 = list2.get(activitySignUpCarInfoBinding16.viewPickerSelector.getValue());
            } else {
                carModel2 = null;
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding17 = this.binding;
            if (activitySignUpCarInfoBinding17 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding17 = null;
            }
            SignUpCarInfoActivityViewModel model10 = activitySignUpCarInfoBinding17.getModel();
            if (model10 != null && (carModel3 = model10.getCarModel()) != null) {
                carModel3.set(carModel2 != null ? carModel2.getName() : null);
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding18 = this.binding;
            if (activitySignUpCarInfoBinding18 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding18 = null;
            }
            activitySignUpCarInfoBinding18.textViewCarModel.setSelected(true);
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding19 = this.binding;
            if (activitySignUpCarInfoBinding19 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding19 = null;
            }
            SignUpCarInfoActivityViewModel model11 = activitySignUpCarInfoBinding19.getModel();
            if (model11 != null && (carGeneration2 = model11.getCarGeneration()) != null) {
                carGeneration2.set("");
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding20 = this.binding;
            if (activitySignUpCarInfoBinding20 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding20 = null;
            }
            activitySignUpCarInfoBinding20.textViewCarGeneration.setSelected(false);
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding21 = this.binding;
            if (activitySignUpCarInfoBinding21 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding21 = null;
            }
            SignUpCarInfoActivityViewModel model12 = activitySignUpCarInfoBinding21.getModel();
            if (model12 != null && (carSeries2 = model12.getCarSeries()) != null) {
                carSeries2.set("");
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding22 = this.binding;
            if (activitySignUpCarInfoBinding22 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding22 = null;
            }
            activitySignUpCarInfoBinding22.textViewCarSeries.setSelected(false);
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding23 = this.binding;
            if (activitySignUpCarInfoBinding23 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding23 = null;
            }
            SignUpCarInfoActivityViewModel model13 = activitySignUpCarInfoBinding23.getModel();
            if (model13 != null && (enableCarGeneration2 = model13.getEnableCarGeneration()) != null) {
                enableCarGeneration2.set(true);
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding24 = this.binding;
            if (activitySignUpCarInfoBinding24 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding24 = null;
            }
            SignUpCarInfoActivityViewModel model14 = activitySignUpCarInfoBinding24.getModel();
            if (model14 != null && (enableCarSeries2 = model14.getEnableCarSeries()) != null) {
                enableCarSeries2.set(false);
            }
        } else if (i == 2) {
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding25 = this.binding;
            if (activitySignUpCarInfoBinding25 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding25 = null;
            }
            SignUpCarInfoActivityViewModel model15 = activitySignUpCarInfoBinding25.getModel();
            if (model15 != null && (enableSignUp3 = model15.getEnableSignUp()) != null) {
                enableSignUp3.set(false);
            }
            List<CarGeneration> list3 = this.mGenerations;
            if (list3 != null) {
                ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding26 = this.binding;
                if (activitySignUpCarInfoBinding26 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activitySignUpCarInfoBinding26 = null;
                }
                carGeneration3 = list3.get(activitySignUpCarInfoBinding26.viewPickerSelector.getValue());
            } else {
                carGeneration3 = null;
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding27 = this.binding;
            if (activitySignUpCarInfoBinding27 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding27 = null;
            }
            SignUpCarInfoActivityViewModel model16 = activitySignUpCarInfoBinding27.getModel();
            if (model16 != null && (carGeneration4 = model16.getCarGeneration()) != null) {
                carGeneration4.set(carGeneration3 != null ? carGeneration3.getName() : null);
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding28 = this.binding;
            if (activitySignUpCarInfoBinding28 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding28 = null;
            }
            activitySignUpCarInfoBinding28.textViewCarGeneration.setSelected(true);
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding29 = this.binding;
            if (activitySignUpCarInfoBinding29 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding29 = null;
            }
            SignUpCarInfoActivityViewModel model17 = activitySignUpCarInfoBinding29.getModel();
            if (model17 != null && (carSeries3 = model17.getCarSeries()) != null) {
                carSeries3.set("");
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding30 = this.binding;
            if (activitySignUpCarInfoBinding30 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding30 = null;
            }
            activitySignUpCarInfoBinding30.textViewCarSeries.setSelected(false);
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding31 = this.binding;
            if (activitySignUpCarInfoBinding31 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding31 = null;
            }
            SignUpCarInfoActivityViewModel model18 = activitySignUpCarInfoBinding31.getModel();
            if (model18 != null && (enableCarSeries3 = model18.getEnableCarSeries()) != null) {
                enableCarSeries3.set(true);
            }
        } else if (i == 3) {
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding32 = this.binding;
            if (activitySignUpCarInfoBinding32 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding32 = null;
            }
            SignUpCarInfoActivityViewModel model19 = activitySignUpCarInfoBinding32.getModel();
            if (model19 != null && (enableSignUp4 = model19.getEnableSignUp()) != null) {
                enableSignUp4.set(true);
            }
            List<CarSeries> list4 = this.mSeries;
            if (list4 != null) {
                ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding33 = this.binding;
                if (activitySignUpCarInfoBinding33 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activitySignUpCarInfoBinding33 = null;
                }
                carSeries4 = list4.get(activitySignUpCarInfoBinding33.viewPickerSelector.getValue());
            } else {
                carSeries4 = null;
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding34 = this.binding;
            if (activitySignUpCarInfoBinding34 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding34 = null;
            }
            SignUpCarInfoActivityViewModel model20 = activitySignUpCarInfoBinding34.getModel();
            if (model20 != null && (carSeries5 = model20.getCarSeries()) != null) {
                carSeries5.set(carSeries4 != null ? carSeries4.getName() : null);
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding35 = this.binding;
            if (activitySignUpCarInfoBinding35 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySignUpCarInfoBinding35 = null;
            }
            activitySignUpCarInfoBinding35.textViewCarSeries.setSelected(true);
        }
        ValueAnimator valueAnimator = this.mSelectorDownAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding36 = this.binding;
        if (activitySignUpCarInfoBinding36 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpCarInfoBinding = activitySignUpCarInfoBinding36;
        }
        SignUpCarInfoActivityViewModel model21 = activitySignUpCarInfoBinding.getModel();
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
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this.binding;
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = null;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        int height = activitySignUpCarInfoBinding.layoutMain.getHeight();
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
        if (activitySignUpCarInfoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding3 = null;
        }
        int height2 = height - activitySignUpCarInfoBinding3.layoutSelector.getHeight();
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding4 = this.binding;
        if (activitySignUpCarInfoBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpCarInfoBinding2 = activitySignUpCarInfoBinding4;
        }
        int top = activitySignUpCarInfoBinding2.layoutSelector.getTop();
        this.mSelectorUpAnimator = ValueAnimator.ofInt(top, height2);
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda10
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SignUpCarInfoActivity.initAnimators$lambda$15(this.f$0, valueAnimator);
            }
        };
        ValueAnimator valueAnimator = this.mSelectorUpAnimator;
        if (valueAnimator != null) {
            valueAnimator.addUpdateListener(animatorUpdateListener);
        }
        this.mSelectorDownAnimator = ValueAnimator.ofInt(height2, top);
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener2 = new ValueAnimator.AnimatorUpdateListener() { // from class: com.thor.app.gui.activities.login.SignUpCarInfoActivity$$ExternalSyntheticLambda11
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SignUpCarInfoActivity.initAnimators$lambda$16(this.f$0, valueAnimator2);
            }
        };
        ValueAnimator valueAnimator2 = this.mSelectorDownAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.addUpdateListener(animatorUpdateListener2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initAnimators$lambda$15(SignUpCarInfoActivity this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue = ((Integer) animatedValue).intValue();
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this$0.binding;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        activitySignUpCarInfoBinding.layoutSelector.setY(iIntValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initAnimators$lambda$16(SignUpCarInfoActivity this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue = ((Integer) animatedValue).intValue();
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = this$0.binding;
        if (activitySignUpCarInfoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpCarInfoBinding = null;
        }
        activitySignUpCarInfoBinding.layoutSelector.setY(iIntValue);
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

    private final void handleSignUpResponse(SignUpResponse response) {
        Timber.INSTANCE.i("handleSignUpResponse: %s", response);
        ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding = null;
        if (response.isSuccessful()) {
            if (response.getUserId() == 0 || TextUtils.isEmpty(response.getToken())) {
                return;
            }
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding2 = this.binding;
            if (activitySignUpCarInfoBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySignUpCarInfoBinding = activitySignUpCarInfoBinding2;
            }
            SignUpInfo signUpInfo = activitySignUpCarInfoBinding.getSignUpInfo();
            if (signUpInfo != null) {
                SignInResponse signInResponse = new SignInResponse(0, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
                signInResponse.setUserId(response.getUserId());
                signInResponse.setDeviceSN(signUpInfo.getDeviceSN());
                signInResponse.setToken(response.getToken());
                signInResponse.setCarMarkId(signUpInfo.getCarMarkId());
                signInResponse.setCarMarkName(signUpInfo.getCarMarkName());
                signInResponse.setCarModelId(signUpInfo.getCarModelId());
                signInResponse.setCarModelName(signUpInfo.getCarModelName());
                signInResponse.setCarGenerationId(signUpInfo.getCarGenerationId());
                signInResponse.setCarGenerationName(signUpInfo.getCarGenerationName());
                signInResponse.setCarSerieId(signUpInfo.getCarSeriesId());
                signInResponse.setCarSerieName(signUpInfo.getCarSeriesName());
                Settings.INSTANCE.setSignUp(true);
                Settings.saveProfile(signInResponse);
                Settings.saveUserId(response.getUserId());
                String token = response.getToken();
                Intrinsics.checkNotNull(token);
                Settings.saveAccessToken(token);
                getMDataLayerHelper().onStartMainWearableActivity();
                Intent intent = new Intent(this, (Class<?>) SplashActivity.class);
                intent.setFlags(268468224);
                startActivity(intent);
                return;
            }
            return;
        }
        if (response.getCode() == 888) {
            DeviceLockingUtilsKt.onDeviceLocked(this);
            return;
        }
        UsersManager usersManagerFrom = UsersManager.INSTANCE.from(this);
        if (usersManagerFrom != null) {
            ActivitySignUpCarInfoBinding activitySignUpCarInfoBinding3 = this.binding;
            if (activitySignUpCarInfoBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySignUpCarInfoBinding = activitySignUpCarInfoBinding3;
            }
            Snackbar snackbarMakeSnackBarError = usersManagerFrom.makeSnackBarError(activitySignUpCarInfoBinding.layoutMain, response.getError());
            if (snackbarMakeSnackBarError != null) {
                snackbarMakeSnackBarError.show();
            }
        }
    }
}
