package com.thor.app.gui.activities.demo;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivitySoundPackageDescriptionBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.Constants;
import com.thor.app.gui.activities.shop.main.PackageGalleryPagerAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.businessmodule.viewmodel.shop.SoundPackageDescriptionViewModel;
import com.thor.networkmodule.model.responses.soundpackage.ImageUrl;
import com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import com.thor.networkmodule.network.OnLoadDataListener;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: DemoSoundPackageDescriptionActivity.kt */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0018H\u0002J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u0005H\u0016J\u0012\u0010\u001f\u001a\u00020\u00182\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0012\u0010\"\u001a\u00020\u00182\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J\b\u0010%\u001a\u00020\u0018H\u0016J\u0010\u0010&\u001a\u00020\u00182\u0006\u0010'\u001a\u00020(H\u0007J\u0010\u0010&\u001a\u00020\u00182\u0006\u0010'\u001a\u00020)H\u0007J\b\u0010*\u001a\u00020\u0018H\u0014J\b\u0010+\u001a\u00020\u0018H\u0002J\b\u0010,\u001a\u00020\u0018H\u0002J\b\u0010-\u001a\u00020\u0018H\u0002J\u001a\u0010.\u001a\u00020/2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010'\u001a\u000200H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014¨\u00061"}, d2 = {"Lcom/thor/app/gui/activities/demo/DemoSoundPackageDescriptionActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/view/View$OnTouchListener;", "Landroid/view/View$OnClickListener;", "Lcom/thor/networkmodule/network/OnLoadDataListener;", "Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageDescriptionResponse;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivitySoundPackageDescriptionBinding;", "fileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "mGestureDetector", "Landroid/view/GestureDetector;", "getMGestureDetector", "()Landroid/view/GestureDetector;", "setMGestureDetector", "(Landroid/view/GestureDetector;)V", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "fillGallery", "", "fillVideoView", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "value", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onLoadData", "onMessageEvent", "event", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "onPause", "onPlaySound", "onPlayVideo", "onStopPlayingVideo", "onTouch", "", "Landroid/view/MotionEvent;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoSoundPackageDescriptionActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, OnLoadDataListener<SoundPackageDescriptionResponse> {
    private ActivitySoundPackageDescriptionBinding binding;
    public GestureDetector mGestureDetector;
    private final FileLogger fileLogger = new FileLogger(this, null, 2, 0 == true ? 1 : 0);

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$usersManager$2
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

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws SecurityException {
        super.onCreate(savedInstanceState);
        DemoSoundPackageDescriptionActivity demoSoundPackageDescriptionActivity = this;
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(demoSoundPackageDescriptionActivity);
        EventBus.getDefault().register(this);
        ViewDataBinding contentView = DataBindingUtil.setContentView(demoSoundPackageDescriptionActivity, R.layout.activity_sound_package_description);
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
        activitySoundPackageDescriptionBinding4.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DemoSoundPackageDescriptionActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        ShopSoundPackage shopSoundPackage = (ShopSoundPackage) getIntent().getParcelableExtra(ShopSoundPackage.INSTANCE.getBUNDLE_NAME());
        if (shopSoundPackage != null) {
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding5 = this.binding;
            if (activitySoundPackageDescriptionBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding5 = null;
            }
            activitySoundPackageDescriptionBinding5.setSoundPackage(shopSoundPackage);
            onLoadData();
        }
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding6 = this.binding;
        if (activitySoundPackageDescriptionBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding6 = null;
        }
        DemoSoundPackageDescriptionActivity demoSoundPackageDescriptionActivity2 = this;
        activitySoundPackageDescriptionBinding6.imageViewPlayVideo.setOnClickListener(demoSoundPackageDescriptionActivity2);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding7 = this.binding;
        if (activitySoundPackageDescriptionBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding7 = null;
        }
        activitySoundPackageDescriptionBinding7.videoView.setOnTouchListener(this);
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding8 = this.binding;
        if (activitySoundPackageDescriptionBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding8;
        }
        activitySoundPackageDescriptionBinding2.viewPlaySound.setOnClickListener(demoSoundPackageDescriptionActivity2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(DemoSoundPackageDescriptionActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
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
        } else if (numValueOf != null && numValueOf.intValue() == R.id.image_view_play_video) {
            onPlayVideo();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        ObservableBoolean isPlaying;
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
                activitySoundPackageDescriptionBinding3 = null;
            }
            activitySoundPackageDescriptionBinding3.videoView.stopPlayback();
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding4 = this.binding;
            if (activitySoundPackageDescriptionBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding4;
            }
            SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding2.getModel();
            if (model == null || (isPlaying = model.getIsPlaying()) == null) {
                return;
            }
            isPlaying.set(false);
        }
    }

    /* compiled from: DemoSoundPackageDescriptionActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$onLoadData$1, reason: invalid class name */
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<SoundPackageDescriptionResponse, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj, DemoSoundPackageDescriptionActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageDescriptionResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SoundPackageDescriptionResponse soundPackageDescriptionResponse) throws Resources.NotFoundException {
            invoke2(soundPackageDescriptionResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SoundPackageDescriptionResponse p0) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((DemoSoundPackageDescriptionActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: DemoSoundPackageDescriptionActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$onLoadData$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, DemoSoundPackageDescriptionActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((DemoSoundPackageDescriptionActivity) this.receiver).handleError(p0);
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
        Observable<SoundPackageDescriptionResponse> observableFetchDemoSoundPackageDescription = usersManager.fetchDemoSoundPackageDescription(numValueOf.intValue());
        if (observableFetchDemoSoundPackageDescription != null) {
            final AnonymousClass1 anonymousClass1 = new AnonymousClass1(this);
            Consumer<? super SoundPackageDescriptionResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DemoSoundPackageDescriptionActivity.onLoadData$lambda$2(anonymousClass1, obj);
                }
            };
            final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
            observableFetchDemoSoundPackageDescription.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DemoSoundPackageDescriptionActivity.onLoadData$lambda$3(anonymousClass2, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void handleResponse(SoundPackageDescriptionResponse value) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(value, "value");
        Timber.INSTANCE.i("handleResponse: %s", value);
        if (value.isSuccessful()) {
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding = this.binding;
            if (activitySoundPackageDescriptionBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activitySoundPackageDescriptionBinding = null;
            }
            activitySoundPackageDescriptionBinding.setPackageInfo(value);
            fillGallery();
            fillVideoView();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(this, value.getError(), Integer.valueOf(value.getCode()));
        if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
        }
    }

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog;
        Intrinsics.checkNotNullParameter(error, "error");
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0);
            if (alertDialogCreateErrorAlertDialog2 != null) {
                alertDialogCreateErrorAlertDialog2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0)) != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        Timber.INSTANCE.e(error);
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
        activitySoundPackageDescriptionBinding2.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$$ExternalSyntheticLambda3
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
            final DemoSoundPackageDescriptionActivity$fillGallery$1$1$1 demoSoundPackageDescriptionActivity$fillGallery$1$1$1 = new Function1<ImageUrl, String>() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$fillGallery$1$1$1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(ImageUrl it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getUrl();
                }
            };
            Observable map = observableFromIterable.map(new Function() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return DemoSoundPackageDescriptionActivity.fillGallery$lambda$9$lambda$8$lambda$5(demoSoundPackageDescriptionActivity$fillGallery$1$1$1, obj);
                }
            });
            final Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$fillGallery$1$1$2
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
            Consumer consumer = new Consumer() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DemoSoundPackageDescriptionActivity.fillGallery$lambda$9$lambda$8$lambda$6(function1, obj);
                }
            };
            final DemoSoundPackageDescriptionActivity$fillGallery$1$1$3 demoSoundPackageDescriptionActivity$fillGallery$1$1$3 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$fillGallery$1$1$3
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
            map.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.demo.DemoSoundPackageDescriptionActivity$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DemoSoundPackageDescriptionActivity.fillGallery$lambda$9$lambda$8$lambda$7(demoSoundPackageDescriptionActivity$fillGallery$1$1$3, obj);
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
    public static final String fillGallery$lambda$9$lambda$8$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (String) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillGallery$lambda$9$lambda$8$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillGallery$lambda$9$lambda$8$lambda$7(Function1 tmp0, Object obj) {
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
            activitySoundPackageDescriptionBinding3.viewTachometer.onStop();
            ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding4 = this.binding;
            if (activitySoundPackageDescriptionBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding4;
            }
            SoundPackageDescriptionViewModel model = activitySoundPackageDescriptionBinding2.getModel();
            if (model == null || (isPlaying2 = model.getIsPlaying()) == null) {
                return;
            }
            isPlaying2.set(false);
            return;
        }
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding5 = this.binding;
        if (activitySoundPackageDescriptionBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySoundPackageDescriptionBinding5 = null;
        }
        activitySoundPackageDescriptionBinding5.viewTachometer.onPlay();
        ActivitySoundPackageDescriptionBinding activitySoundPackageDescriptionBinding6 = this.binding;
        if (activitySoundPackageDescriptionBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySoundPackageDescriptionBinding2 = activitySoundPackageDescriptionBinding6;
        }
        SoundPackageDescriptionViewModel model2 = activitySoundPackageDescriptionBinding2.getModel();
        if (model2 == null || (isPlaying = model2.getIsPlaying()) == null) {
            return;
        }
        isPlaying.set(true);
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
}
