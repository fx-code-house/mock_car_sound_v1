package com.thor.app.gui.activities.shop.sgu;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivitySguSoundPackageDescriptionBinding;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemDecorator;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemForFirstDecoration;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemForLastDecoration;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.bus.events.shop.sgu.UploadSguSoundPackageSuccessfulEvent;
import com.thor.app.gui.adapters.shop.sgu.SguSoundsPresentationAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.StringKt;
import com.thor.app.utils.extensions.ViewKt;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.basemodule.extensions.MeasureKt;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import com.thor.businessmodule.billing.BillingManager;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.businessmodule.settings.Variables;
import com.thor.businessmodule.viewmodel.shop.SoundPackageDescriptionViewModel;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
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
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: SguSetDetailsActivity.kt */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0015\u001a\u00020\u0016H\u0003J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\b\u0010\u0019\u001a\u00020\u0016H\u0003J\u0012\u0010\u001a\u001a\u00020\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\u0012\u0010\u001d\u001a\u00020\u00162\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\b\u0010 \u001a\u00020\u0016H\u0002J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#H\u0007J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020$H\u0007J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020%H\u0007J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020&H\u0007J\b\u0010'\u001a\u00020\u0016H\u0014J\b\u0010(\u001a\u00020\u0016H\u0014J\b\u0010)\u001a\u00020\u0016H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0012\u0010\u0013¨\u0006*"}, d2 = {"Lcom/thor/app/gui/activities/shop/sgu/SguSetDetailsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/view/View$OnClickListener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivitySguSoundPackageDescriptionBinding;", "fileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "mediaPlayer", "Landroid/media/MediaPlayer;", "soundsAdapter", "Lcom/thor/app/gui/adapters/shop/sgu/SguSoundsPresentationAdapter;", "getSoundsAdapter", "()Lcom/thor/app/gui/adapters/shop/sgu/SguSoundsPresentationAdapter;", "soundsAdapter$delegate", "Lkotlin/Lazy;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "confirmPayment", "", "fillGallery", "initAdapter", "onBuyPackage", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDownloadPackage", "onMessageEvent", "event", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/app/bus/events/shop/sgu/UploadSguSoundPackageSuccessfulEvent;", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "onResume", "onStart", "onStop", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSetDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySguSoundPackageDescriptionBinding binding;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: soundsAdapter$delegate, reason: from kotlin metadata */
    private final Lazy soundsAdapter = LazyKt.lazy(new Function0<SguSoundsPresentationAdapter>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$soundsAdapter$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final SguSoundsPresentationAdapter invoke() {
            return new SguSoundsPresentationAdapter();
        }
    });
    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private final FileLogger fileLogger = new FileLogger(this, null, 2, null);

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SguSoundsPresentationAdapter getSoundsAdapter() {
        return (SguSoundsPresentationAdapter) this.soundsAdapter.getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        ObservableBoolean isDownloadPackage;
        Observable<BaseResponse> observableSubscribeOn;
        Observable<BaseResponse> observableObserveOn;
        ObservableBoolean isGooglePlayBilling;
        super.onCreate(savedInstanceState);
        SguSetDetailsActivity sguSetDetailsActivity = this;
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(sguSetDetailsActivity);
        ViewDataBinding contentView = DataBindingUtil.setContentView(sguSetDetailsActivity, R.layout.activity_sgu_sound_package_description);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.l…ound_package_description)");
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding = (ActivitySguSoundPackageDescriptionBinding) contentView;
        this.binding = activitySguSoundPackageDescriptionBinding;
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding2 = null;
        if (activitySguSoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding = null;
        }
        activitySguSoundPackageDescriptionBinding.setModel(new SoundPackageDescriptionViewModel());
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding3 = this.binding;
        if (activitySguSoundPackageDescriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding3 = null;
        }
        activitySguSoundPackageDescriptionBinding3.toolbarWidget.setHomeButtonVisibility(true);
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding4 = this.binding;
        if (activitySguSoundPackageDescriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding4 = null;
        }
        activitySguSoundPackageDescriptionBinding4.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SguSetDetailsActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding5 = this.binding;
        if (activitySguSoundPackageDescriptionBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding5 = null;
        }
        activitySguSoundPackageDescriptionBinding5.toolbarWidget.enableBluetoothIndicator(true);
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding6 = this.binding;
        if (activitySguSoundPackageDescriptionBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding6 = null;
        }
        activitySguSoundPackageDescriptionBinding6.toolbarWidget.setTitle(R.string.title_package_description);
        SguSoundSet sguSoundSet = (SguSoundSet) getIntent().getParcelableExtra(SguSoundSet.BUNDLE_NAME);
        if (sguSoundSet != null) {
            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding7 = this.binding;
            if (activitySguSoundPackageDescriptionBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySguSoundPackageDescriptionBinding7 = null;
            }
            activitySguSoundPackageDescriptionBinding7.setSoundPackage(sguSoundSet);
            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding8 = this.binding;
            if (activitySguSoundPackageDescriptionBinding8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySguSoundPackageDescriptionBinding8 = null;
            }
            SoundPackageDescriptionViewModel model = activitySguSoundPackageDescriptionBinding8.getModel();
            if (model != null && (isGooglePlayBilling = model.getIsGooglePlayBilling()) != null) {
                isGooglePlayBilling.set((sguSoundSet.getSetStatus() || Settings.INSTANCE.isSubscriptionActive()) ? false : true);
            }
            fillGallery();
            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding9 = this.binding;
            if (activitySguSoundPackageDescriptionBinding9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySguSoundPackageDescriptionBinding9 = null;
            }
            SguSoundSet soundPackage = activitySguSoundPackageDescriptionBinding9.getSoundPackage();
            Boolean boolValueOf = soundPackage != null ? Boolean.valueOf(soundPackage.getSetStatus()) : null;
            Intrinsics.checkNotNull(boolValueOf);
            if (boolValueOf.booleanValue() || Settings.INSTANCE.isSubscriptionActive()) {
                ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding10 = this.binding;
                if (activitySguSoundPackageDescriptionBinding10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activitySguSoundPackageDescriptionBinding10 = null;
                }
                SoundPackageDescriptionViewModel model2 = activitySguSoundPackageDescriptionBinding10.getModel();
                if (model2 != null && (isDownloadPackage = model2.getIsDownloadPackage()) != null) {
                    isDownloadPackage.set(true);
                }
            }
            UsersManager usersManagerFrom = UsersManager.INSTANCE.from(this);
            if (usersManagerFrom != null) {
                ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding11 = this.binding;
                if (activitySguSoundPackageDescriptionBinding11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activitySguSoundPackageDescriptionBinding11 = null;
                }
                SguSoundSet soundPackage2 = activitySguSoundPackageDescriptionBinding11.getSoundPackage();
                Integer numValueOf = soundPackage2 != null ? Integer.valueOf(soundPackage2.getId()) : null;
                Intrinsics.checkNotNull(numValueOf);
                Observable<BaseResponse> observableSendStatisticsAboutWatchInfoSoundPackage = usersManagerFrom.sendStatisticsAboutWatchInfoSoundPackage(numValueOf.intValue());
                if (observableSendStatisticsAboutWatchInfoSoundPackage != null && (observableSubscribeOn = observableSendStatisticsAboutWatchInfoSoundPackage.subscribeOn(Schedulers.io())) != null && (observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread())) != null) {
                    final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$onCreate$2$1
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
                            if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this.this$0, baseResponse.getError(), baseResponse.getCode())) != null) {
                                alertDialogCreateErrorAlertDialog.show();
                            }
                            Timber.INSTANCE.i(baseResponse.toString(), new Object[0]);
                        }
                    };
                    Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$$ExternalSyntheticLambda4
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            SguSetDetailsActivity.onCreate$lambda$3$lambda$1(function1, obj);
                        }
                    };
                    final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$onCreate$2$2
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
                                }
                            } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this.this$0, th.getMessage(), null, 4, null)) != null) {
                                alertDialogCreateErrorAlertDialog$default.show();
                            }
                            Timber.INSTANCE.e(th);
                        }
                    };
                    observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$$ExternalSyntheticLambda5
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            SguSetDetailsActivity.onCreate$lambda$3$lambda$2(function12, obj);
                        }
                    });
                }
            }
        }
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding12 = this.binding;
        if (activitySguSoundPackageDescriptionBinding12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding12 = null;
        }
        activitySguSoundPackageDescriptionBinding12.buttonBuy.setOnClickListener(this);
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding13 = this.binding;
        if (activitySguSoundPackageDescriptionBinding13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding13 = null;
        }
        Button button = activitySguSoundPackageDescriptionBinding13.buttonDownloadPackage;
        Intrinsics.checkNotNullExpressionValue(button, "binding.buttonDownloadPackage");
        ViewKt.setOnClickListenerWithDebounce$default(button, 0L, new Function0<Unit>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity.onCreate.3
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
                SguSetDetailsActivity.this.onDownloadPackage();
            }
        }, 1, null);
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding14 = this.binding;
        if (activitySguSoundPackageDescriptionBinding14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySguSoundPackageDescriptionBinding2 = activitySguSoundPackageDescriptionBinding14;
        }
        activitySguSoundPackageDescriptionBinding2.buttonDownloadPackage.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return SguSetDetailsActivity.onCreate$lambda$6(this.f$0, view);
            }
        });
        initAdapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(SguSetDetailsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onCreate$lambda$6(SguSetDetailsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SguSetDetailsActivity sguSetDetailsActivity = this$0;
        AlertDialog.Builder builder = new AlertDialog.Builder(sguSetDetailsActivity, 2131886083);
        final EditText editText = new EditText(sguSetDetailsActivity);
        editText.setTextColor(ContextCompat.getColor(sguSetDetailsActivity, R.color.colorBlack));
        editText.setInputType(2);
        editText.setText(String.valueOf(Variables.INSTANCE.getBLOCK_SIZE()));
        builder.setMessage("Enter value:");
        builder.setTitle("Block size");
        builder.setView(editText);
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SguSetDetailsActivity.onCreate$lambda$6$lambda$4(editText, dialogInterface, i);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
        editText.requestFocus();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$6$lambda$4(EditText edittext, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(edittext, "$edittext");
        try {
            Variables.INSTANCE.setBLOCK_SIZE(Integer.parseInt(edittext.getText().toString()));
        } catch (Exception e) {
            Timber.INSTANCE.w(e);
        }
        dialogInterface.dismiss();
    }

    private final void initAdapter() {
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding = this.binding;
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding2 = null;
        if (activitySguSoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding = null;
        }
        activitySguSoundPackageDescriptionBinding.autoscrollRecycler.setAdapter(getSoundsAdapter());
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding3 = this.binding;
        if (activitySguSoundPackageDescriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding3 = null;
        }
        activitySguSoundPackageDescriptionBinding3.autoscrollRecycler.addItemDecoration(new SpaceItemDecorator(MeasureKt.getDp(4), 0, MeasureKt.getDp(4), 0, 10, null));
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding4 = this.binding;
        if (activitySguSoundPackageDescriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding4 = null;
        }
        activitySguSoundPackageDescriptionBinding4.autoscrollRecycler.addItemDecoration(new SpaceItemForFirstDecoration(MeasureKt.getDp(16), 0, 0, 0, 14, null));
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding5 = this.binding;
        if (activitySguSoundPackageDescriptionBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySguSoundPackageDescriptionBinding2 = activitySguSoundPackageDescriptionBinding5;
        }
        activitySguSoundPackageDescriptionBinding2.autoscrollRecycler.addItemDecoration(new SpaceItemForLastDecoration(0, 0, MeasureKt.getDp(16), 0, 11, null));
        getSoundsAdapter().setOnItemClickListener(new RecyclerCollectionAdapter.OnItemClickListener<SguSound>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$initAdapter$$inlined$doOnItemClick$1
            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(SguSound item, int position) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(SguSound item, View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(SguSound item) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                final SguSound sguSound = item;
                if (!sguSound.getSoundFiles().isEmpty()) {
                    try {
                        this.this$0.mediaPlayer.reset();
                        String sampleUrl = sguSound.getSampleUrl();
                        if (sampleUrl != null) {
                            this.this$0.mediaPlayer.setDataSource(StringKt.getFullFileUrl(sampleUrl));
                            this.this$0.mediaPlayer.prepareAsync();
                            MediaPlayer mediaPlayer = this.this$0.mediaPlayer;
                            final SguSetDetailsActivity sguSetDetailsActivity = this.this$0;
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$initAdapter$1$1$1
                                @Override // android.media.MediaPlayer.OnPreparedListener
                                public final void onPrepared(MediaPlayer mediaPlayer2) throws IllegalStateException {
                                    SguSoundsPresentationAdapter soundsAdapter = sguSetDetailsActivity.getSoundsAdapter();
                                    SguSound sound = sguSound;
                                    Intrinsics.checkNotNullExpressionValue(sound, "sound");
                                    soundsAdapter.onStartPlaying(sound);
                                    mediaPlayer2.start();
                                }
                            });
                            MediaPlayer mediaPlayer2 = this.this$0.mediaPlayer;
                            final SguSetDetailsActivity sguSetDetailsActivity2 = this.this$0;
                            mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$initAdapter$1$1$2
                                @Override // android.media.MediaPlayer.OnCompletionListener
                                public final void onCompletion(MediaPlayer mediaPlayer3) {
                                    SguSoundsPresentationAdapter soundsAdapter = sguSetDetailsActivity2.getSoundsAdapter();
                                    SguSound sound = sguSound;
                                    Intrinsics.checkNotNullExpressionValue(sound, "sound");
                                    soundsAdapter.onStopPlaying(sound);
                                }
                            });
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        Timber.INSTANCE.e(e);
                        return;
                    }
                }
                if (Settings.getSelectedServer() == 1092) {
                    Toast.makeText(this.this$0, "Sound has no files, please check database!", 1).show();
                }
            }
        });
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() throws SecurityException {
        Timber.INSTANCE.i("onStart", new Object[0]);
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        Timber.INSTANCE.i("onStop", new Object[0]);
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getSoundsAdapter().notifyDataSetChanged();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.button_buy) {
            onBuyPackage();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onDownloadPackage() {
        Timber.INSTANCE.i("onUpdateSoftware", new Object[0]);
        SguSetDetailsActivity sguSetDetailsActivity = this;
        if (BleManager.INSTANCE.from(sguSetDetailsActivity).getMStateConnected()) {
            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding = this.binding;
            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding2 = null;
            if (activitySguSoundPackageDescriptionBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySguSoundPackageDescriptionBinding = null;
            }
            if (activitySguSoundPackageDescriptionBinding.getSoundPackage() == null) {
                Timber.INSTANCE.e("Package info not correct", new Object[0]);
                return;
            }
            UploadSguSoundSetDialogFragment.Companion companion = UploadSguSoundSetDialogFragment.INSTANCE;
            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding3 = this.binding;
            if (activitySguSoundPackageDescriptionBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySguSoundPackageDescriptionBinding2 = activitySguSoundPackageDescriptionBinding3;
            }
            SguSoundSet soundPackage = activitySguSoundPackageDescriptionBinding2.getSoundPackage();
            Intrinsics.checkNotNull(soundPackage);
            UploadSguSoundSetDialogFragment uploadSguSoundSetDialogFragmentNewInstance = companion.newInstance(soundPackage);
            if (uploadSguSoundSetDialogFragmentNewInstance.isAdded()) {
                return;
            }
            uploadSguSoundSetDialogFragmentNewInstance.show(getSupportFragmentManager(), "UploadSguSoundSetDialogFragment");
            return;
        }
        DialogManager.INSTANCE.createNoConnectionToBoardAlertDialog(sguSetDetailsActivity).show();
    }

    private final void fillGallery() {
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding = this.binding;
        if (activitySguSoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding = null;
        }
        SguSoundSet soundPackage = activitySguSoundPackageDescriptionBinding.getSoundPackage();
        if (soundPackage != null) {
            getSoundsAdapter().clearAndAddAll(soundPackage.getFiles());
        }
    }

    private final void onBuyPackage() {
        String iapIdentifier;
        Timber.INSTANCE.i("onBuyPackage", new Object[0]);
        BillingManager billingManager = new BillingManager(this);
        String[] strArr = new String[1];
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding = this.binding;
        if (activitySguSoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding = null;
        }
        SguSoundSet soundPackage = activitySguSoundPackageDescriptionBinding.getSoundPackage();
        if (soundPackage == null || (iapIdentifier = soundPackage.getIapIdentifier()) == null) {
            iapIdentifier = "";
        }
        strArr[0] = iapIdentifier;
        Single<List<SkuDetails>> skuDetailsList = billingManager.getSkuDetailsList(CollectionsKt.arrayListOf(strArr), "inapp");
        final Function2<List<? extends SkuDetails>, Throwable, Unit> function2 = new Function2<List<? extends SkuDetails>, Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity.onBuyPackage.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends SkuDetails> list, Throwable th) {
                invoke2(list, th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<? extends SkuDetails> list, Throwable th) {
                if (list != null) {
                    final SguSetDetailsActivity sguSetDetailsActivity = SguSetDetailsActivity.this;
                    new BillingManager(sguSetDetailsActivity).purchase(list.get(0), new BillingManager.PurchaseBillingListener() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$onBuyPackage$1$1$1
                        @Override // com.thor.businessmodule.billing.BillingManager.PurchaseBillingListener
                        public void handleCancelled() {
                        }

                        @Override // com.thor.businessmodule.billing.BillingManager.PurchaseBillingListener
                        public void handleError(Integer errorCode) {
                        }

                        @Override // com.thor.businessmodule.billing.BillingManager.PurchaseBillingListener
                        public void handlePurchase(Purchase purchase) {
                            Intrinsics.checkNotNullParameter(purchase, "purchase");
                            sguSetDetailsActivity.confirmPayment();
                        }
                    }).subscribe();
                }
            }
        };
        skuDetailsList.subscribe(new BiConsumer() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SguSetDetailsActivity.onBuyPackage$lambda$10(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBuyPackage$lambda$10(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void confirmPayment() {
        UsersManager usersManagerFrom = UsersManager.INSTANCE.from(this);
        if (usersManagerFrom != null) {
            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding = this.binding;
            if (activitySguSoundPackageDescriptionBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySguSoundPackageDescriptionBinding = null;
            }
            Observable<BaseResponse> observableSendGooglePaymentInfo = usersManagerFrom.sendGooglePaymentInfo(activitySguSoundPackageDescriptionBinding.getSoundPackage());
            if (observableSendGooglePaymentInfo != null) {
                final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity.confirmPayment.1
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
                        ObservableBoolean isDownloadPackage;
                        ObservableBoolean isGooglePlayBilling;
                        boolean z = false;
                        FileLogger.INSTANCE.newInstance(SguSetDetailsActivity.this, "sendGooglePaymentInfo").d(baseResponse.toString(), new Object[0]);
                        Timber.INSTANCE.i("Payment response: %s", baseResponse);
                        if (baseResponse.isSuccessful()) {
                            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding2 = SguSetDetailsActivity.this.binding;
                            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding3 = null;
                            if (activitySguSoundPackageDescriptionBinding2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                activitySguSoundPackageDescriptionBinding2 = null;
                            }
                            SguSoundSet soundPackage = activitySguSoundPackageDescriptionBinding2.getSoundPackage();
                            Intrinsics.checkNotNull(soundPackage);
                            soundPackage.setSetStatus(true);
                            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding4 = SguSetDetailsActivity.this.binding;
                            if (activitySguSoundPackageDescriptionBinding4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                activitySguSoundPackageDescriptionBinding4 = null;
                            }
                            SoundPackageDescriptionViewModel model = activitySguSoundPackageDescriptionBinding4.getModel();
                            if (model != null && (isGooglePlayBilling = model.getIsGooglePlayBilling()) != null) {
                                ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding5 = SguSetDetailsActivity.this.binding;
                                if (activitySguSoundPackageDescriptionBinding5 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                    activitySguSoundPackageDescriptionBinding5 = null;
                                }
                                SguSoundSet soundPackage2 = activitySguSoundPackageDescriptionBinding5.getSoundPackage();
                                Boolean boolValueOf = soundPackage2 != null ? Boolean.valueOf(soundPackage2.getSetStatus()) : null;
                                Intrinsics.checkNotNull(boolValueOf);
                                if (!boolValueOf.booleanValue() && !Settings.INSTANCE.isSubscriptionActive()) {
                                    z = true;
                                }
                                isGooglePlayBilling.set(z);
                            }
                            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding6 = SguSetDetailsActivity.this.binding;
                            if (activitySguSoundPackageDescriptionBinding6 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                            } else {
                                activitySguSoundPackageDescriptionBinding3 = activitySguSoundPackageDescriptionBinding6;
                            }
                            SoundPackageDescriptionViewModel model2 = activitySguSoundPackageDescriptionBinding3.getModel();
                            if (model2 == null || (isDownloadPackage = model2.getIsDownloadPackage()) == null) {
                                return;
                            }
                            isDownloadPackage.set(true);
                        }
                    }
                };
                Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        SguSetDetailsActivity.confirmPayment$lambda$11(function1, obj);
                    }
                };
                final AnonymousClass2 anonymousClass2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity.confirmPayment.2
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Throwable th) {
                        Timber.INSTANCE.e(th);
                    }
                };
                observableSendGooglePaymentInfo.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        SguSetDetailsActivity.confirmPayment$lambda$12(anonymousClass2, obj);
                    }
                });
            }
        }
        Single<List<Purchase>> singleQueryPurchases = new BillingManager(this).queryPurchases();
        final Function2<List<? extends Purchase>, Throwable, Unit> function2 = new Function2<List<? extends Purchase>, Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity.confirmPayment.3
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends Purchase> list, Throwable th) {
                invoke2(list, th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<? extends Purchase> list, Throwable th) {
                ObservableBoolean isDownloadPackage;
                ObservableBoolean isGooglePlayBilling;
                if (list != null) {
                    SguSetDetailsActivity sguSetDetailsActivity = SguSetDetailsActivity.this;
                    for (Purchase purchase : list) {
                        boolean z = false;
                        Timber.INSTANCE.i("Purchase: %s", list.toString());
                        ArrayList<String> skus = purchase.getSkus();
                        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding2 = sguSetDetailsActivity.binding;
                        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding3 = null;
                        if (activitySguSoundPackageDescriptionBinding2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            activitySguSoundPackageDescriptionBinding2 = null;
                        }
                        SguSoundSet soundPackage = activitySguSoundPackageDescriptionBinding2.getSoundPackage();
                        Intrinsics.checkNotNull(soundPackage);
                        if (skus.contains(soundPackage.getIapIdentifier())) {
                            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding4 = sguSetDetailsActivity.binding;
                            if (activitySguSoundPackageDescriptionBinding4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                activitySguSoundPackageDescriptionBinding4 = null;
                            }
                            SguSoundSet soundPackage2 = activitySguSoundPackageDescriptionBinding4.getSoundPackage();
                            Intrinsics.checkNotNull(soundPackage2);
                            soundPackage2.setSetStatus(true);
                            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding5 = sguSetDetailsActivity.binding;
                            if (activitySguSoundPackageDescriptionBinding5 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                activitySguSoundPackageDescriptionBinding5 = null;
                            }
                            SoundPackageDescriptionViewModel model = activitySguSoundPackageDescriptionBinding5.getModel();
                            if (model != null && (isGooglePlayBilling = model.getIsGooglePlayBilling()) != null) {
                                ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding6 = sguSetDetailsActivity.binding;
                                if (activitySguSoundPackageDescriptionBinding6 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                    activitySguSoundPackageDescriptionBinding6 = null;
                                }
                                SguSoundSet soundPackage3 = activitySguSoundPackageDescriptionBinding6.getSoundPackage();
                                Boolean boolValueOf = soundPackage3 != null ? Boolean.valueOf(soundPackage3.getSetStatus()) : null;
                                Intrinsics.checkNotNull(boolValueOf);
                                if (!boolValueOf.booleanValue() && !Settings.INSTANCE.isSubscriptionActive()) {
                                    z = true;
                                }
                                isGooglePlayBilling.set(z);
                            }
                            ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding7 = sguSetDetailsActivity.binding;
                            if (activitySguSoundPackageDescriptionBinding7 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                            } else {
                                activitySguSoundPackageDescriptionBinding3 = activitySguSoundPackageDescriptionBinding7;
                            }
                            SoundPackageDescriptionViewModel model2 = activitySguSoundPackageDescriptionBinding3.getModel();
                            if (model2 != null && (isDownloadPackage = model2.getIsDownloadPackage()) != null) {
                                isDownloadPackage.set(true);
                            }
                        }
                    }
                }
            }
        };
        singleQueryPurchases.subscribe(new BiConsumer() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SguSetDetailsActivity.confirmPayment$lambda$13(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmPayment$lambda$11(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmPayment$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmPayment$lambda$13(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UploadSguSoundPackageSuccessfulEvent event) {
        ObservableBoolean isDownloadPackage;
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        event.getSoundSetDetails();
        ActivitySguSoundPackageDescriptionBinding activitySguSoundPackageDescriptionBinding = this.binding;
        if (activitySguSoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySguSoundPackageDescriptionBinding = null;
        }
        SoundPackageDescriptionViewModel model = activitySguSoundPackageDescriptionBinding.getModel();
        if (model != null && (isDownloadPackage = model.getIsDownloadPackage()) != null) {
            isDownloadPackage.set(false);
        }
        finish();
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
        SguSetDetailsActivity sguSetDetailsActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(sguSetDetailsActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, sguSetDetailsActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new C02391(uriForFile, event, null), 2, null);
    }

    /* compiled from: SguSetDetailsActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$onMessageEvent$1", f = "SguSetDetailsActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$onMessageEvent$1, reason: invalid class name and case insensitive filesystem */
    static final class C02391 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02391(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super C02391> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SguSetDetailsActivity.this.new C02391(this.$path, this.$event, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = SguSetDetailsActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final C00681 c00681 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity.onMessageEvent.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$onMessageEvent$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00681.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final AnonymousClass2 anonymousClass2 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity.onMessageEvent.1.2
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
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.shop.sgu.SguSetDetailsActivity$onMessageEvent$1$$ExternalSyntheticLambda1
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
