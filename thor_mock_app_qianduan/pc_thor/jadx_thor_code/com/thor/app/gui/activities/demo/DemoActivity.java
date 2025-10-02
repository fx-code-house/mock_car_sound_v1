package com.thor.app.gui.activities.demo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.BuildConfig;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityDemoBinding;
import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.thor.app.auto.common.MusicServiceConnection;
import com.thor.app.databinding.viewmodels.DemoActivityViewModel;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.dialogs.SimpleDialogFragment;
import com.thor.app.gui.fragments.demo.DemoMainFragment;
import com.thor.app.gui.fragments.demo.DemoSguFragment;
import com.thor.app.gui.widget.ShopModeSwitchView;
import com.thor.app.managers.DemoSoundPackageManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.di.InjectorUtils;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.businessmodule.model.DemoSoundPackage;
import com.thor.businessmodule.model.TypeDialog;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoActivity.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\rH\u0002J\b\u0010\u0011\u001a\u00020\rH\u0016J\u0012\u0010\u0012\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u0012\u0010\u0015\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\b\u0010\u0018\u001a\u00020\rH\u0014J\b\u0010\u0019\u001a\u00020\rH\u0002J\b\u0010\u001a\u001a\u00020\rH\u0002J\b\u0010\u001b\u001a\u00020\rH\u0002J\b\u0010\u001c\u001a\u00020\rH\u0002J\b\u0010\u001d\u001a\u00020\rH\u0002J\u0010\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006!"}, d2 = {"Lcom/thor/app/gui/activities/demo/DemoActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/view/View$OnClickListener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityDemoBinding;", "musicServiceConnection", "Lcom/thor/app/auto/common/MusicServiceConnection;", "getMusicServiceConnection", "()Lcom/thor/app/auto/common/MusicServiceConnection;", "musicServiceConnection$delegate", "Lkotlin/Lazy;", "doOnCheckAutoMode", "", "getBaseUrlWithLocale", "", "initViewPager", "onBackPressed", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onOpenDownloadTelegramLink", "onOpenSite", "onOpenSiteDialog", "onOpenTelegramDialog", "onOpenThorTechSupport", "updateDemoUiMode", "fuelCategory", "Lcom/thor/businessmodule/model/DemoSoundPackage$FuelCategory;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class DemoActivity extends Hilt_DemoActivity implements View.OnClickListener {
    private ActivityDemoBinding binding;

    /* renamed from: musicServiceConnection$delegate, reason: from kotlin metadata */
    private final Lazy musicServiceConnection = LazyKt.lazy(new Function0<MusicServiceConnection>() { // from class: com.thor.app.gui.activities.demo.DemoActivity$musicServiceConnection$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final MusicServiceConnection invoke() {
            return InjectorUtils.INSTANCE.provideMusicServiceConnection(this.this$0);
        }
    });

    private final MusicServiceConnection getMusicServiceConnection() {
        return (MusicServiceConnection) this.musicServiceConnection.getValue();
    }

    @Override // com.thor.app.gui.activities.demo.Hilt_DemoActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DemoActivity demoActivity = this;
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(demoActivity);
        ViewDataBinding contentView = DataBindingUtil.setContentView(demoActivity, R.layout.activity_demo);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.layout.activity_demo)");
        ActivityDemoBinding activityDemoBinding = (ActivityDemoBinding) contentView;
        this.binding = activityDemoBinding;
        ActivityDemoBinding activityDemoBinding2 = null;
        if (activityDemoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoBinding = null;
        }
        activityDemoBinding.setModel(new DemoActivityViewModel());
        ActivityDemoBinding activityDemoBinding3 = this.binding;
        if (activityDemoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoBinding3 = null;
        }
        activityDemoBinding3.toolbarWidget.setHomeButtonVisibility(true);
        ActivityDemoBinding activityDemoBinding4 = this.binding;
        if (activityDemoBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoBinding4 = null;
        }
        activityDemoBinding4.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.demo.DemoActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DemoActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        if (Settings.INSTANCE.isAccessSession()) {
            ActivityDemoBinding activityDemoBinding5 = this.binding;
            if (activityDemoBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityDemoBinding5 = null;
            }
            activityDemoBinding5.toolbarWidget.enableBluetoothIndicator(true);
        }
        ActivityDemoBinding activityDemoBinding6 = this.binding;
        if (activityDemoBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoBinding6 = null;
        }
        DemoActivity demoActivity2 = this;
        activityDemoBinding6.textViewOrderDevice.setOnClickListener(demoActivity2);
        ActivityDemoBinding activityDemoBinding7 = this.binding;
        if (activityDemoBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoBinding7 = null;
        }
        activityDemoBinding7.siteBtn.setOnClickListener(demoActivity2);
        ActivityDemoBinding activityDemoBinding8 = this.binding;
        if (activityDemoBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityDemoBinding2 = activityDemoBinding8;
        }
        activityDemoBinding2.telegramBtn.setOnClickListener(demoActivity2);
        initViewPager();
        doOnCheckAutoMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(DemoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onBackPressed();
    }

    private final void initViewPager() {
        ActivityDemoBinding activityDemoBinding = this.binding;
        ActivityDemoBinding activityDemoBinding2 = null;
        if (activityDemoBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoBinding = null;
        }
        activityDemoBinding.switchShopMode.setOnShopModeChangeListener(new ShopModeSwitchView.OnShopModeChangedListener() { // from class: com.thor.app.gui.activities.demo.DemoActivity.initViewPager.1

            /* compiled from: DemoActivity.kt */
            @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
            /* renamed from: com.thor.app.gui.activities.demo.DemoActivity$initViewPager$1$WhenMappings */
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

            @Override // com.thor.app.gui.widget.ShopModeSwitchView.OnShopModeChangedListener
            public void onModeChange(ShopModeSwitchView.ShopMode mode) {
                Intrinsics.checkNotNullParameter(mode, "mode");
                int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
                if (i == 1) {
                    DemoActivity.this.getSupportFragmentManager().beginTransaction().disallowAddToBackStack().setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim).replace(R.id.fragment_container, DemoMainFragment.INSTANCE.newInstance(DemoActivity.this.getIntent().getAction())).commit();
                } else {
                    if (i != 2) {
                        return;
                    }
                    DemoActivity.this.getSupportFragmentManager().beginTransaction().disallowAddToBackStack().setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim).replace(R.id.fragment_container, DemoSguFragment.Companion.newInstance$default(DemoSguFragment.INSTANCE, null, 1, null)).commit();
                }
            }
        });
        ActivityDemoBinding activityDemoBinding3 = this.binding;
        if (activityDemoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityDemoBinding2 = activityDemoBinding3;
        }
        activityDemoBinding2.switchShopMode.setCurrentMode(ShopModeSwitchView.ShopMode.CAR_SOUND);
    }

    @Override // com.thor.app.gui.activities.demo.Hilt_DemoActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        DemoSoundPackageManager.INSTANCE.release();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_order_device) {
            onOpenSite();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.site_btn) {
            onOpenSiteDialog();
        } else if (numValueOf != null && numValueOf.intValue() == R.id.telegram_btn) {
            onOpenTelegramDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onOpenSite() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(getBaseUrlWithLocale()));
        startActivity(intent);
    }

    private final String getBaseUrlWithLocale() {
        return Intrinsics.areEqual(Locale.getDefault().getDisplayLanguage(), "русский") ? BuildConfig.SITE_URL_RU : BuildConfig.SITE_URL;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onOpenThorTechSupport() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/ThorTechSupport"));
        intent.setPackage("org.telegram.messenger");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            onOpenDownloadTelegramLink();
        }
    }

    private final void onOpenDownloadTelegramLink() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=org.telegram.messenger")));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/ThorTechSupport")));
        }
    }

    private final void onOpenSiteDialog() {
        SimpleDialogFragment simpleDialogFragment = new SimpleDialogFragment(TypeDialog.GOTO_SITE);
        simpleDialogFragment.setOnConfirmListener(new OnConfirmDialogListener() { // from class: com.thor.app.gui.activities.demo.DemoActivity$onOpenSiteDialog$$inlined$doOnConfirm$1
            @Override // com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener
            public void onConfirm(String info) {
                this.this$0.onOpenSite();
            }
        });
        simpleDialogFragment.show(getSupportFragmentManager(), simpleDialogFragment.getClass().getSimpleName());
    }

    private final void onOpenTelegramDialog() {
        SimpleDialogFragment simpleDialogFragment = new SimpleDialogFragment(TypeDialog.GOTO_TELEGRAM);
        simpleDialogFragment.setOnConfirmListener(new OnConfirmDialogListener() { // from class: com.thor.app.gui.activities.demo.DemoActivity$onOpenTelegramDialog$$inlined$doOnConfirm$1
            @Override // com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener
            public void onConfirm(String info) {
                this.this$0.onOpenThorTechSupport();
            }
        });
        simpleDialogFragment.show(getSupportFragmentManager(), simpleDialogFragment.getClass().getSimpleName());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        DemoActivity demoActivity = this;
        if (ContextKt.isCarUIMode(demoActivity)) {
            DialogManager.INSTANCE.createCarModeDeviceSearchWarning(demoActivity, new Function0<Unit>() { // from class: com.thor.app.gui.activities.demo.DemoActivity.onBackPressed.1
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
                    DemoActivity.this.finish();
                }
            }).show();
        } else {
            super.onBackPressed();
        }
    }

    private final void doOnCheckAutoMode() {
        ActivityDemoBinding activityDemoBinding = null;
        if (ContextKt.isCarUIMode(this)) {
            ActivityDemoBinding activityDemoBinding2 = this.binding;
            if (activityDemoBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityDemoBinding = activityDemoBinding2;
            }
            activityDemoBinding.textViewOrderDevice.setVisibility(8);
            return;
        }
        ActivityDemoBinding activityDemoBinding3 = this.binding;
        if (activityDemoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityDemoBinding = activityDemoBinding3;
        }
        activityDemoBinding.textViewOrderDevice.setVisibility(0);
    }

    private final void updateDemoUiMode(DemoSoundPackage.FuelCategory fuelCategory) {
        ActivityDemoBinding activityDemoBinding = null;
        if (fuelCategory == DemoSoundPackage.FuelCategory.EV) {
            ActivityDemoBinding activityDemoBinding2 = this.binding;
            if (activityDemoBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityDemoBinding = activityDemoBinding2;
            }
            activityDemoBinding.textViewOrderDevice.setBackgroundResource(R.drawable.bg_selector_action_blue);
            return;
        }
        ActivityDemoBinding activityDemoBinding3 = this.binding;
        if (activityDemoBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityDemoBinding = activityDemoBinding3;
        }
        activityDemoBinding.textViewOrderDevice.setBackgroundResource(R.drawable.bg_selector_action_red);
    }
}
