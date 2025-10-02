package com.thor.app.gui.activities.shop.main;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import com.carsystems.thor.app.databinding.ActivitySoundPackageDescriptionBinding;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.Constants;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageSuccessfulEvent;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment;
import com.thor.app.gui.views.tachometer.TachometerWithEqualizeView;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.InstalledSoundPackageEntity;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.ViewKt;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.businessmodule.billing.BillingManager;
import com.thor.businessmodule.settings.Variables;
import com.thor.businessmodule.viewmodel.shop.SoundPackageDescriptionViewModel;
import com.thor.networkmodule.model.ModeType;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.soundpackage.ImageUrl;
import com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse;
import com.thor.networkmodule.model.responses.soundpackage.SoundPackageFile;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import com.thor.networkmodule.network.OnLoadDataListener;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
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
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: SoundPackageDescriptionActivity.kt */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0015\u001a\u00020\u0016H\u0003J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u0005H\u0016J\b\u0010\u001e\u001a\u00020\u0016H\u0003J\u0012\u0010\u001f\u001a\u00020\u00162\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0012\u0010\"\u001a\u00020\u00162\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J\b\u0010%\u001a\u00020\u0016H\u0002J\b\u0010&\u001a\u00020\u0016H\u0016J\u0010\u0010'\u001a\u00020\u00162\u0006\u0010(\u001a\u00020)H\u0007J\u0010\u0010'\u001a\u00020\u00162\u0006\u0010(\u001a\u00020*H\u0007J\b\u0010+\u001a\u00020\u0016H\u0014J\b\u0010,\u001a\u00020\u0016H\u0002J\b\u0010-\u001a\u00020\u0016H\u0002J\b\u0010.\u001a\u00020\u0016H\u0014J\b\u0010/\u001a\u00020\u0016H\u0014J\b\u00100\u001a\u00020\u0016H\u0002J\u001a\u00101\u001a\u0002022\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010(\u001a\u000203H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012¨\u00064"}, d2 = {"Lcom/thor/app/gui/activities/shop/main/SoundPackageDescriptionActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/view/View$OnTouchListener;", "Landroid/view/View$OnClickListener;", "Lcom/thor/networkmodule/network/OnLoadDataListener;", "Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageDescriptionResponse;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivitySoundPackageDescriptionBinding;", "mGestureDetector", "Landroid/view/GestureDetector;", "getMGestureDetector", "()Landroid/view/GestureDetector;", "setMGestureDetector", "(Landroid/view/GestureDetector;)V", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "confirmPayment", "", "fillGallery", "fillVideoView", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "value", "onBuyPackage", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDownloadPackage", "onLoadData", "onMessageEvent", "event", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/app/bus/events/shop/main/UploadSoundPackageSuccessfulEvent;", "onPause", "onPlaySound", "onPlayVideo", "onStart", "onStop", "onStopPlayingVideo", "onTouch", "", "Landroid/view/MotionEvent;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SoundPackageDescriptionActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, OnLoadDataListener<SoundPackageDescriptionResponse> {
    private ActivitySoundPackageDescriptionBinding binding;
    public GestureDetector mGestureDetector;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    public final GestureDetector getMGestureDetector() {
        GestureDetector gestureDetector = this.mGestureDetector;
        if (gestureDetector != null) {
            return gestureDetector;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mGestureDetector");
        return null;
    }

    public final void setMGestureDetector(GestureDetector gestureDetector) {
        Intrinsics.checkNotNullParameter(gestureDetector, "<set-?>");
        this.mGestureDetector = gestureDetector;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        Observable<BaseResponse> observableSubscribeOn;
        Observable<BaseResponse> observableObserveOn;
        ObservableBoolean isGooglePlayBilling;
        super.onCreate(savedInstanceState);
        try {
            ThemingUtil.INSTANCE.onActivityCreateSetTheme(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_sound_package_description);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.l…ound_package_description)");
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = (ActivitySoundPackageDescriptionBinding) contentView;
        this.binding = activitySoundPackageDescriptionBinding;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = null;
        if (activitySoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding = null;
        }
        activitySoundPackageDescriptionBinding.setModel(new SoundPackageDescriptionViewModel());
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = this.binding;
        if (activitySoundPackageDescriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding3 = null;
        }
        activitySoundPackageDescriptionBinding3.toolbarWidget.setHomeButtonVisibility(true);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding4 = this.binding;
        if (activitySoundPackageDescriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding4 = null;
        }
        activitySoundPackageDescriptionBinding4.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SoundPackageDescriptionActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding5 = this.binding;
        if (activitySoundPackageDescriptionBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding5 = null;
        }
        activitySoundPackageDescriptionBinding5.toolbarWidget.enableBluetoothIndicator(true);
        ShopSoundPackage shopSoundPackage = (ShopSoundPackage) getIntent().getParcelableExtra(ShopSoundPackage.INSTANCE.getBUNDLE_NAME());
        if (shopSoundPackage != null) {
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding6 = this.binding;
            if (activitySoundPackageDescriptionBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding6 = null;
            }
            activitySoundPackageDescriptionBinding6.setSoundPackage(shopSoundPackage);
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding7 = this.binding;
            if (activitySoundPackageDescriptionBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding7 = null;
            }
            SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding7.getModel();
            if (model != null && (isGooglePlayBilling = model.getIsGooglePlayBilling()) != null) {
                isGooglePlayBilling.set((shopSoundPackage.isPurchased() || Settings.INSTANCE.isSubscriptionActive()) ? false : true);
            }
            onLoadData();
            UsersManager usersManagerFrom = UsersManager.INSTANCE.from(this);
            if (usersManagerFrom != null) {
                ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding8 = this.binding;
                if (activitySoundPackageDescriptionBinding8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activitySoundPackageDescriptionBinding8 = null;
                }
                ShopSoundPackage soundPackage = activitySoundPackageDescriptionBinding8.getSoundPackage();
                Integer numValueOf = soundPackage != null ? Integer.valueOf(soundPackage.getId()) : null;
                Intrinsics.checkNotNull(numValueOf);
                Observable<BaseResponse> observableSendStatisticsAboutWatchInfoSoundPackage = usersManagerFrom.sendStatisticsAboutWatchInfoSoundPackage(numValueOf.intValue());
                if (observableSendStatisticsAboutWatchInfoSoundPackage != null && (observableSubscribeOn = observableSendStatisticsAboutWatchInfoSoundPackage.subscribeOn(Schedulers.io())) != null && (observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread())) != null) {
                    final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$onCreate$2$1
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
                    Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda17
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            SoundPackageDescriptionActivity.onCreate$lambda$3$lambda$1(function1, obj);
                        }
                    };
                    final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$onCreate$2$2
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
                    observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda1
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            SoundPackageDescriptionActivity.onCreate$lambda$3$lambda$2(function12, obj);
                        }
                    });
                }
            }
        }
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding9 = this.binding;
        if (activitySoundPackageDescriptionBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding9 = null;
        }
        SoundPackageDescriptionActivity soundPackageDescriptionActivity = this;
        activitySoundPackageDescriptionBinding9.imageViewPlayVideo.setOnClickListener(soundPackageDescriptionActivity);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding10 = this.binding;
        if (activitySoundPackageDescriptionBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding10 = null;
        }
        activitySoundPackageDescriptionBinding10.videoView.setOnTouchListener(this);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding11 = this.binding;
        if (activitySoundPackageDescriptionBinding11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding11 = null;
        }
        activitySoundPackageDescriptionBinding11.viewPlaySound.setOnClickListener(soundPackageDescriptionActivity);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding12 = this.binding;
        if (activitySoundPackageDescriptionBinding12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding12 = null;
        }
        activitySoundPackageDescriptionBinding12.buttonBuy.setOnClickListener(soundPackageDescriptionActivity);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding13 = this.binding;
        if (activitySoundPackageDescriptionBinding13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding13 = null;
        }
        Button button = activitySoundPackageDescriptionBinding13.buttonDownloadPackage;
        Intrinsics.checkNotNullExpressionValue(button, "binding.buttonDownloadPackage");
        ViewKt.setOnClickListenerWithDebounce$default(button, 0L, new Function0<Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity.onCreate.3
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
                SoundPackageDescriptionActivity.this.onDownloadPackage();
            }
        }, 1, null);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding14 = this.binding;
        if (activitySoundPackageDescriptionBinding14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding14;
        }
        activitySoundPackageDescriptionBinding2.buttonDownloadPackage.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return SoundPackageDescriptionActivity.onCreate$lambda$6(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(SoundPackageDescriptionActivity this$0, View view) {
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
    public static final boolean onCreate$lambda$6(SoundPackageDescriptionActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SoundPackageDescriptionActivity soundPackageDescriptionActivity = this$0;
        AlertDialog.Builder builder = new AlertDialog.Builder(soundPackageDescriptionActivity, 2131886083);
        final EditText editText = new EditText(soundPackageDescriptionActivity);
        editText.setTextColor(ContextCompat.getColor(soundPackageDescriptionActivity, R.color.colorBlack));
        editText.setInputType(2);
        editText.setText(String.valueOf(Variables.INSTANCE.getBLOCK_SIZE()));
        builder.setMessage("Enter value:");
        builder.setTitle("Block size");
        builder.setView(editText);
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SoundPackageDescriptionActivity.onCreate$lambda$6$lambda$4(editText, dialogInterface, i);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda10
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

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.video_view) {
            onStopPlayingVideo();
            return true;
        }
        if (numValueOf == null || numValueOf.intValue() != R.id.view_flipper) {
            return true;
        }
        getMGestureDetector().onTouchEvent(event);
        return true;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.view_play_sound) {
            onPlaySound();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.image_view_play_video) {
            onPlayVideo();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.button_buy) {
            onBuyPackage();
        } else if (numValueOf != null && numValueOf.intValue() == R.id.button_download_package) {
            onDownloadPackage();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = null;
        if (activitySoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding = null;
        }
        if (activitySoundPackageDescriptionBinding.videoView.isPlaying()) {
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = this.binding;
            if (activitySoundPackageDescriptionBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding3;
            }
            activitySoundPackageDescriptionBinding2.videoView.stopPlayback();
        }
    }

    /* compiled from: SoundPackageDescriptionActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$onLoadData$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02301 extends FunctionReferenceImpl implements Function1<SoundPackageDescriptionResponse, Unit> {
        C02301(Object obj) {
            super(1, obj, SoundPackageDescriptionActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageDescriptionResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SoundPackageDescriptionResponse soundPackageDescriptionResponse) throws Resources.NotFoundException {
            invoke2(soundPackageDescriptionResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SoundPackageDescriptionResponse p0) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SoundPackageDescriptionActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SoundPackageDescriptionActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$onLoadData$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02312 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C02312(Object obj) {
            super(1, obj, SoundPackageDescriptionActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SoundPackageDescriptionActivity) this.receiver).handleError(p0);
        }
    }

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void onLoadData() {
        UsersManager usersManager = getUsersManager();
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
        if (activitySoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding = null;
        }
        ShopSoundPackage soundPackage = activitySoundPackageDescriptionBinding.getSoundPackage();
        Integer numValueOf = soundPackage != null ? Integer.valueOf(soundPackage.getId()) : null;
        Intrinsics.checkNotNull(numValueOf);
        Observable<SoundPackageDescriptionResponse> observableFetchSoundPackageDescription = usersManager.fetchSoundPackageDescription(numValueOf.intValue());
        if (observableFetchSoundPackageDescription != null) {
            final C02301 c02301 = new C02301(this);
            Consumer<? super SoundPackageDescriptionResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SoundPackageDescriptionActivity.onLoadData$lambda$7(c02301, obj);
                }
            };
            final C02312 c02312 = new C02312(this);
            observableFetchSoundPackageDescription.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SoundPackageDescriptionActivity.onLoadData$lambda$8(c02312, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void handleResponse(SoundPackageDescriptionResponse value) throws Resources.NotFoundException {
        ObservableBoolean isDownloadPackage;
        Intrinsics.checkNotNullParameter(value, "value");
        Timber.INSTANCE.i("handleResponse: %s", value);
        if (value.isSuccessful()) {
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = null;
            if (activitySoundPackageDescriptionBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding = null;
            }
            activitySoundPackageDescriptionBinding.setPackageInfo(value);
            fillGallery();
            fillVideoView();
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = this.binding;
            if (activitySoundPackageDescriptionBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding3 = null;
            }
            ShopSoundPackage soundPackage = activitySoundPackageDescriptionBinding3.getSoundPackage();
            Boolean boolValueOf = soundPackage != null ? Boolean.valueOf(soundPackage.isPurchased()) : null;
            Intrinsics.checkNotNull(boolValueOf);
            if (boolValueOf.booleanValue() || Settings.INSTANCE.isSubscriptionActive()) {
                ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding4 = this.binding;
                if (activitySoundPackageDescriptionBinding4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding4;
                }
                SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding2.getModel();
                if (model == null || (isDownloadPackage = model.getIsDownloadPackage()) == null) {
                    return;
                }
                isDownloadPackage.set(true);
                return;
            }
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, this, value.getError(), null, 4, null);
        if (alertDialogCreateErrorAlertDialogWithSendLogOption$default != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption$default.show();
        }
    }

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        Intrinsics.checkNotNullParameter(error, "error");
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null);
            if (alertDialogCreateErrorAlertDialog$default2 != null) {
                alertDialogCreateErrorAlertDialog$default2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null)) != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        Timber.INSTANCE.e(error);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onDownloadPackage() {
        List<SoundPackageFile> files;
        Timber.INSTANCE.i("onUpdateSoftware", new Object[0]);
        SoundPackageDescriptionActivity soundPackageDescriptionActivity = this;
        if (BleManager.INSTANCE.from(soundPackageDescriptionActivity).getMStateConnected()) {
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
            if (activitySoundPackageDescriptionBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding = null;
            }
            if (activitySoundPackageDescriptionBinding.getSoundPackage() != null) {
                ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = this.binding;
                if (activitySoundPackageDescriptionBinding2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activitySoundPackageDescriptionBinding2 = null;
                }
                SoundPackageDescriptionResponse packageInfo = activitySoundPackageDescriptionBinding2.getPackageInfo();
                if ((packageInfo != null ? packageInfo.getFiles() : null) != null) {
                    ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = this.binding;
                    if (activitySoundPackageDescriptionBinding3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        activitySoundPackageDescriptionBinding3 = null;
                    }
                    SoundPackageDescriptionResponse packageInfo2 = activitySoundPackageDescriptionBinding3.getPackageInfo();
                    Integer numValueOf = (packageInfo2 == null || (files = packageInfo2.getFiles()) == null) ? null : Integer.valueOf(files.size());
                    Intrinsics.checkNotNull(numValueOf);
                    if (numValueOf.intValue() >= 3) {
                        DownloadSoundPackageDialogFragment.Companion companion = DownloadSoundPackageDialogFragment.INSTANCE;
                        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding4 = this.binding;
                        if (activitySoundPackageDescriptionBinding4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            activitySoundPackageDescriptionBinding4 = null;
                        }
                        ShopSoundPackage soundPackage = activitySoundPackageDescriptionBinding4.getSoundPackage();
                        Intrinsics.checkNotNull(soundPackage);
                        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding5 = this.binding;
                        if (activitySoundPackageDescriptionBinding5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            activitySoundPackageDescriptionBinding5 = null;
                        }
                        SoundPackageDescriptionResponse packageInfo3 = activitySoundPackageDescriptionBinding5.getPackageInfo();
                        List<SoundPackageFile> files2 = packageInfo3 != null ? packageInfo3.getFiles() : null;
                        Intrinsics.checkNotNull(files2);
                        DownloadSoundPackageDialogFragment downloadSoundPackageDialogFragmentNewInstance = companion.newInstance(soundPackage, files2);
                        if (downloadSoundPackageDialogFragmentNewInstance.isAdded()) {
                            return;
                        }
                        downloadSoundPackageDialogFragmentNewInstance.show(getSupportFragmentManager(), "DownloadSoundPackageDialogFragment");
                        return;
                    }
                }
            }
            Timber.INSTANCE.e("Package info not correct", new Object[0]);
            return;
        }
        DialogManager.INSTANCE.createNoConnectionToBoardAlertDialog(soundPackageDescriptionActivity).show();
    }

    private final void onPlayVideo() {
        ObservableBoolean isPlayingVideo;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = null;
        if (activitySoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding = null;
        }
        SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding.getModel();
        if (model != null && (isPlayingVideo = model.getIsPlayingVideo()) != null) {
            isPlayingVideo.set(true);
        }
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = this.binding;
        if (activitySoundPackageDescriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding3;
        }
        activitySoundPackageDescriptionBinding2.videoView.start();
    }

    private final void onStopPlayingVideo() {
        ObservableBoolean isPlayingVideo;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = null;
        if (activitySoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding = null;
        }
        SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding.getModel();
        if (model != null && (isPlayingVideo = model.getIsPlayingVideo()) != null) {
            isPlayingVideo.set(false);
        }
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = this.binding;
        if (activitySoundPackageDescriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding3;
        }
        activitySoundPackageDescriptionBinding2.videoView.pause();
    }

    private final void fillVideoView() {
        ObservableBoolean isVideo;
        ObservableBoolean isVideo2;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = null;
        if (activitySoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding = null;
        }
        SoundPackageDescriptionResponse packageInfo = activitySoundPackageDescriptionBinding.getPackageInfo();
        String videoUrl = packageInfo != null ? packageInfo.getVideoUrl() : null;
        if (TextUtils.isEmpty(videoUrl)) {
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = this.binding;
            if (activitySoundPackageDescriptionBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding3;
            }
            SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding2.getModel();
            if (model == null || (isVideo2 = model.getIsVideo()) == null) {
                return;
            }
            isVideo2.set(false);
            return;
        }
        Uri uri = Uri.parse(videoUrl);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding4 = this.binding;
        if (activitySoundPackageDescriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding4 = null;
        }
        SoundPackageDescriptionViewModel model2 = activitySoundPackageDescriptionBinding4.getModel();
        if (model2 != null && (isVideo = model2.getIsVideo()) != null) {
            isVideo.set(true);
        }
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding5 = this.binding;
        if (activitySoundPackageDescriptionBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding5 = null;
        }
        activitySoundPackageDescriptionBinding5.imageViewPlayVideo.setOnClickListener(this);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding6 = this.binding;
        if (activitySoundPackageDescriptionBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding6 = null;
        }
        activitySoundPackageDescriptionBinding6.videoView.setOnTouchListener(this);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding7 = this.binding;
        if (activitySoundPackageDescriptionBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding7 = null;
        }
        activitySoundPackageDescriptionBinding7.videoView.setVideoURI(uri);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding8 = this.binding;
        if (activitySoundPackageDescriptionBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding8;
        }
        activitySoundPackageDescriptionBinding2.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda3
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(false);
            }
        });
    }

    private final void fillGallery() throws Resources.NotFoundException {
        ObservableBoolean isGallery;
        ObservableBoolean isGallery2;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = null;
        if (activitySoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding = null;
        }
        SoundPackageDescriptionResponse packageInfo = activitySoundPackageDescriptionBinding.getPackageInfo();
        if (packageInfo != null) {
            List<ImageUrl> images = packageInfo.getImages();
            if (images == null || images.isEmpty()) {
                ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = this.binding;
                if (activitySoundPackageDescriptionBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding3;
                }
                SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding2.getModel();
                if (model == null || (isGallery = model.getIsGallery()) == null) {
                    return;
                }
                isGallery.set(false);
                return;
            }
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding4 = this.binding;
            if (activitySoundPackageDescriptionBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding4 = null;
            }
            SoundPackageDescriptionViewModel model2 = activitySoundPackageDescriptionBinding4.getModel();
            if (model2 != null && (isGallery2 = model2.getIsGallery()) != null) {
                isGallery2.set(true);
            }
            final PackageGalleryPagerAdapter packageGalleryPagerAdapter = new PackageGalleryPagerAdapter();
            Observable observableFromIterable = Observable.fromIterable(images);
            final SoundPackageDescriptionActivity$fillGallery$1$1$1 soundPackageDescriptionActivity$fillGallery$1$1$1 = new Function1<ImageUrl, String>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$fillGallery$1$1$1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(ImageUrl it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getUrl();
                }
            };
            Observable map = observableFromIterable.map(new Function() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda11
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return SoundPackageDescriptionActivity.fillGallery$lambda$14$lambda$13$lambda$10(soundPackageDescriptionActivity$fillGallery$1$1$1, obj);
                }
            });
            final Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$fillGallery$1$1$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String str) {
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    PackageGalleryPagerAdapter packageGalleryPagerAdapter2 = packageGalleryPagerAdapter;
                    Intrinsics.checkNotNull(str);
                    packageGalleryPagerAdapter2.add(str);
                }
            };
            Consumer consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda12
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SoundPackageDescriptionActivity.fillGallery$lambda$14$lambda$13$lambda$11(function1, obj);
                }
            };
            final SoundPackageDescriptionActivity$fillGallery$1$1$3 soundPackageDescriptionActivity$fillGallery$1$1$3 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$fillGallery$1$1$3
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
            map.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda13
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SoundPackageDescriptionActivity.fillGallery$lambda$14$lambda$13$lambda$12(soundPackageDescriptionActivity$fillGallery$1$1$3, obj);
                }
            });
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding5 = this.binding;
            if (activitySoundPackageDescriptionBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding5 = null;
            }
            activitySoundPackageDescriptionBinding5.viewPager.setAdapter(packageGalleryPagerAdapter);
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding6 = this.binding;
            if (activitySoundPackageDescriptionBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding6 = null;
            }
            TabLayout tabLayout = activitySoundPackageDescriptionBinding6.tabLayout;
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding7 = this.binding;
            if (activitySoundPackageDescriptionBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding7;
            }
            tabLayout.setupWithViewPager(activitySoundPackageDescriptionBinding2.viewPager);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String fillGallery$lambda$14$lambda$13$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (String) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillGallery$lambda$14$lambda$13$lambda$11(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillGallery$lambda$14$lambda$13$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void onPlaySound() {
        ObservableBoolean isPlaying;
        ObservableBoolean isPlaying2;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = null;
        if (activitySoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding = null;
        }
        if (activitySoundPackageDescriptionBinding.viewTachometer.getMPlaying()) {
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = this.binding;
            if (activitySoundPackageDescriptionBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding3 = null;
            }
            SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding3.getModel();
            if (model != null && (isPlaying2 = model.getIsPlaying()) != null) {
                isPlaying2.set(false);
            }
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding4 = this.binding;
            if (activitySoundPackageDescriptionBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding4;
            }
            activitySoundPackageDescriptionBinding2.viewTachometer.onStop();
            return;
        }
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding5 = this.binding;
        if (activitySoundPackageDescriptionBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding5 = null;
        }
        SoundPackageDescriptionViewModel model2 = activitySoundPackageDescriptionBinding5.getModel();
        if (model2 != null && (isPlaying = model2.getIsPlaying()) != null) {
            isPlaying.set(true);
        }
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding6 = this.binding;
        if (activitySoundPackageDescriptionBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding6 = null;
        }
        TachometerWithEqualizeView tachometerWithEqualizeView = activitySoundPackageDescriptionBinding6.viewTachometer;
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding7 = this.binding;
        if (activitySoundPackageDescriptionBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding7 = null;
        }
        SoundPackageDescriptionResponse packageInfo = activitySoundPackageDescriptionBinding7.getPackageInfo();
        tachometerWithEqualizeView.onPlayByUrl(packageInfo != null ? packageInfo.getSampleUrl() : null);
    }

    private final void onBuyPackage() {
        String googlePlayId;
        Timber.INSTANCE.i("onBuyPackage", new Object[0]);
        BillingManager billingManager = new BillingManager(this);
        String[] strArr = new String[1];
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
        if (activitySoundPackageDescriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding = null;
        }
        ShopSoundPackage soundPackage = activitySoundPackageDescriptionBinding.getSoundPackage();
        if (soundPackage == null || (googlePlayId = soundPackage.getGooglePlayId()) == null) {
            googlePlayId = "";
        }
        strArr[0] = googlePlayId;
        Single<List<SkuDetails>> skuDetailsList = billingManager.getSkuDetailsList(CollectionsKt.arrayListOf(strArr), "inapp");
        final Function2<List<? extends SkuDetails>, Throwable, Unit> function2 = new Function2<List<? extends SkuDetails>, Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity.onBuyPackage.1
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
                    final SoundPackageDescriptionActivity soundPackageDescriptionActivity = SoundPackageDescriptionActivity.this;
                    new BillingManager(soundPackageDescriptionActivity).purchase(list.get(0), new BillingManager.PurchaseBillingListener() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$onBuyPackage$1$1$1
                        @Override // com.thor.businessmodule.billing.BillingManager.PurchaseBillingListener
                        public void handleCancelled() {
                        }

                        @Override // com.thor.businessmodule.billing.BillingManager.PurchaseBillingListener
                        public void handleError(Integer errorCode) {
                        }

                        @Override // com.thor.businessmodule.billing.BillingManager.PurchaseBillingListener
                        public void handlePurchase(Purchase purchase) {
                            Intrinsics.checkNotNullParameter(purchase, "purchase");
                            soundPackageDescriptionActivity.confirmPayment();
                        }
                    }).subscribe();
                }
            }
        };
        skuDetailsList.subscribe(new BiConsumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SoundPackageDescriptionActivity.onBuyPackage$lambda$15(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBuyPackage$lambda$15(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void confirmPayment() {
        UsersManager usersManagerFrom = UsersManager.INSTANCE.from(this);
        if (usersManagerFrom != null) {
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
            if (activitySoundPackageDescriptionBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding = null;
            }
            Observable<BaseResponse> observableSendGooglePaymentInfo = usersManagerFrom.sendGooglePaymentInfo(activitySoundPackageDescriptionBinding.getSoundPackage());
            if (observableSendGooglePaymentInfo != null) {
                final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity.confirmPayment.1
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
                        FileLogger.INSTANCE.newInstance(SoundPackageDescriptionActivity.this, "sendGooglePaymentInfo").d(baseResponse.toString(), new Object[0]);
                        Timber.INSTANCE.i("Payment response: %s", baseResponse);
                        if (baseResponse.isSuccessful()) {
                            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = SoundPackageDescriptionActivity.this.binding;
                            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = null;
                            if (activitySoundPackageDescriptionBinding2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                activitySoundPackageDescriptionBinding2 = null;
                            }
                            ShopSoundPackage soundPackage = activitySoundPackageDescriptionBinding2.getSoundPackage();
                            Intrinsics.checkNotNull(soundPackage);
                            soundPackage.setPkgStatus(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
                            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding4 = SoundPackageDescriptionActivity.this.binding;
                            if (activitySoundPackageDescriptionBinding4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                activitySoundPackageDescriptionBinding4 = null;
                            }
                            SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding4.getModel();
                            if (model != null && (isGooglePlayBilling = model.getIsGooglePlayBilling()) != null) {
                                ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding5 = SoundPackageDescriptionActivity.this.binding;
                                if (activitySoundPackageDescriptionBinding5 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                    activitySoundPackageDescriptionBinding5 = null;
                                }
                                ShopSoundPackage soundPackage2 = activitySoundPackageDescriptionBinding5.getSoundPackage();
                                Boolean boolValueOf = soundPackage2 != null ? Boolean.valueOf(soundPackage2.isPurchased()) : null;
                                Intrinsics.checkNotNull(boolValueOf);
                                if (!boolValueOf.booleanValue() && !Settings.INSTANCE.isSubscriptionActive()) {
                                    z = true;
                                }
                                isGooglePlayBilling.set(z);
                            }
                            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding6 = SoundPackageDescriptionActivity.this.binding;
                            if (activitySoundPackageDescriptionBinding6 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                            } else {
                                activitySoundPackageDescriptionBinding3 = activitySoundPackageDescriptionBinding6;
                            }
                            SoundPackageDescriptionViewModel model2 = activitySoundPackageDescriptionBinding3.getModel();
                            if (model2 == null || (isDownloadPackage = model2.getIsDownloadPackage()) == null) {
                                return;
                            }
                            isDownloadPackage.set(true);
                            return;
                        }
                        Toast.makeText(SoundPackageDescriptionActivity.this, R.string.warning_unknown_error, 1).show();
                    }
                };
                Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda6
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        SoundPackageDescriptionActivity.confirmPayment$lambda$16(function1, obj);
                    }
                };
                final AnonymousClass2 anonymousClass2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity.confirmPayment.2
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
                observableSendGooglePaymentInfo.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda7
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        SoundPackageDescriptionActivity.confirmPayment$lambda$17(anonymousClass2, obj);
                    }
                });
            }
        }
        Single<List<Purchase>> singleQueryPurchases = new BillingManager(this).queryPurchases();
        final Function2<List<? extends Purchase>, Throwable, Unit> function2 = new Function2<List<? extends Purchase>, Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity.confirmPayment.3
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
                    SoundPackageDescriptionActivity soundPackageDescriptionActivity = SoundPackageDescriptionActivity.this;
                    for (Purchase purchase : list) {
                        boolean z = false;
                        Timber.INSTANCE.i("Purchase: %s", list.toString());
                        ArrayList<String> skus = purchase.getSkus();
                        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = soundPackageDescriptionActivity.binding;
                        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding3 = null;
                        if (activitySoundPackageDescriptionBinding2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            activitySoundPackageDescriptionBinding2 = null;
                        }
                        ShopSoundPackage soundPackage = activitySoundPackageDescriptionBinding2.getSoundPackage();
                        Intrinsics.checkNotNull(soundPackage);
                        if (skus.contains(soundPackage.getGooglePlayId())) {
                            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding4 = soundPackageDescriptionActivity.binding;
                            if (activitySoundPackageDescriptionBinding4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                activitySoundPackageDescriptionBinding4 = null;
                            }
                            ShopSoundPackage soundPackage2 = activitySoundPackageDescriptionBinding4.getSoundPackage();
                            Intrinsics.checkNotNull(soundPackage2);
                            soundPackage2.setPkgStatus(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
                            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding5 = soundPackageDescriptionActivity.binding;
                            if (activitySoundPackageDescriptionBinding5 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                activitySoundPackageDescriptionBinding5 = null;
                            }
                            SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding5.getModel();
                            if (model != null && (isGooglePlayBilling = model.getIsGooglePlayBilling()) != null) {
                                ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding6 = soundPackageDescriptionActivity.binding;
                                if (activitySoundPackageDescriptionBinding6 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                    activitySoundPackageDescriptionBinding6 = null;
                                }
                                ShopSoundPackage soundPackage3 = activitySoundPackageDescriptionBinding6.getSoundPackage();
                                Boolean boolValueOf = soundPackage3 != null ? Boolean.valueOf(soundPackage3.isPurchased()) : null;
                                Intrinsics.checkNotNull(boolValueOf);
                                if (!boolValueOf.booleanValue() && !Settings.INSTANCE.isSubscriptionActive()) {
                                    z = true;
                                }
                                isGooglePlayBilling.set(z);
                            }
                            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding7 = soundPackageDescriptionActivity.binding;
                            if (activitySoundPackageDescriptionBinding7 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                            } else {
                                activitySoundPackageDescriptionBinding3 = activitySoundPackageDescriptionBinding7;
                            }
                            SoundPackageDescriptionViewModel model2 = activitySoundPackageDescriptionBinding3.getModel();
                            if (model2 != null && (isDownloadPackage = model2.getIsDownloadPackage()) != null) {
                                isDownloadPackage.set(true);
                            }
                        }
                    }
                }
            }
        };
        singleQueryPurchases.subscribe(new BiConsumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SoundPackageDescriptionActivity.confirmPayment$lambda$18(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmPayment$lambda$16(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmPayment$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmPayment$lambda$18(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UploadSoundPackageSuccessfulEvent event) {
        ObservableBoolean isDownloadPackage;
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        ShopSoundPackage soundPackage = event.getSoundPackage();
        int id = soundPackage.getId();
        int pkgVer = soundPackage.getPkgVer();
        ArrayList<ModeType> modeImages = soundPackage.getModeImages();
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = null;
        Intrinsics.checkNotNull(modeImages != null ? Integer.valueOf(modeImages.size()) : null);
        Completable completableInsert = ThorDatabase.INSTANCE.from(this).installedSoundPackageDao().insert(new InstalledSoundPackageEntity(id, pkgVer, r12.intValue() - 1, false, 8, null));
        Action action = new Action() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Action
            public final void run() {
                SoundPackageDescriptionActivity.onMessageEvent$lambda$19();
            }
        };
        final C02322 c02322 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity.onMessageEvent.2
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
        completableInsert.subscribe(action, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SoundPackageDescriptionActivity.onMessageEvent$lambda$20(c02322, obj);
            }
        });
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding2 = this.binding;
        if (activitySoundPackageDescriptionBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySoundPackageDescriptionBinding = activitySoundPackageDescriptionBinding2;
        }
        SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding.getModel();
        if (model != null && (isDownloadPackage = model.getIsDownloadPackage()) != null) {
            isDownloadPackage.set(false);
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$19() {
        Timber.INSTANCE.i("Success", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$20(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        SoundPackageDescriptionActivity soundPackageDescriptionActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(soundPackageDescriptionActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, soundPackageDescriptionActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new C02333(uriForFile, event, null), 2, null);
    }

    /* compiled from: SoundPackageDescriptionActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$onMessageEvent$3", f = "SoundPackageDescriptionActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$onMessageEvent$3, reason: invalid class name and case insensitive filesystem */
    static final class C02333 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02333(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super C02333> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SoundPackageDescriptionActivity.this.new C02333(this.$path, this.$event, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02333) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = SoundPackageDescriptionActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final AnonymousClass1 anonymousClass1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity.onMessageEvent.3.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$onMessageEvent$3$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            anonymousClass1.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final AnonymousClass2 anonymousClass2 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity.onMessageEvent.3.2
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
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SoundPackageDescriptionActivity$onMessageEvent$3$$ExternalSyntheticLambda1
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
