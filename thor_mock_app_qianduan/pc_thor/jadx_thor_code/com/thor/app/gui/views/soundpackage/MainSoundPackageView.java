package com.thor.app.gui.views.soundpackage;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.databinding.ObservableBoolean;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ViewMainSoundPackageBinding;
import com.thor.app.managers.BleManager;
import com.thor.businessmodule.bluetooth.model.other.DriveMode;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.businessmodule.viewmodel.views.MainPresetPackageViewModel;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: MainSoundPackageView.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 +2\u00020\u0001:\u0001+B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\u0018J\u0006\u0010\u001a\u001a\u00020\fJ\u0012\u0010\u001b\u001a\u00020\u00182\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0006\u0010\u001c\u001a\u00020\u0018J\u0006\u0010\u001d\u001a\u00020\u0018J\u0006\u0010\u001e\u001a\u00020\u0018J\u0006\u0010\u001f\u001a\u00020\u0018J\u0016\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\tJ\u001e\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\t2\u0006\u0010#\u001a\u00020\tJ\u0006\u0010$\u001a\u00020\u0018J\u0006\u0010%\u001a\u00020\u0018J\u000e\u0010&\u001a\u00020\u00182\u0006\u0010'\u001a\u00020(J\u000e\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\tR\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/thor/app/gui/views/soundpackage/MainSoundPackageView;", "Landroidx/cardview/widget/CardView;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewMainSoundPackageBinding;", "mBleManager", "Lcom/thor/app/managers/BleManager;", "getMBleManager", "()Lcom/thor/app/managers/BleManager;", "mBleManager$delegate", "Lkotlin/Lazy;", "mShiftOfPosition", "", "mSwiping", "", "cleanStates", "", "cleanSwipe", "getMainBinding", "initView", "onActivate", "onActivateWithoutAnimation", "onDeactivate", "onDeactivateWithoutAnimation", "onNestedScrollChanged", "scrollY", "oldScrollY", "height", "onSwipeToLeftAnimation", "onSwipeToRightAnimation", "setMainPresetPackage", "preset", "Lcom/thor/businessmodule/model/MainPresetPackage;", "setShiftOfPosition", "fullHeight", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MainSoundPackageView extends CardView {
    private static final long ANIMATION_DURATION = 240;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final float SCALE_MAX = 1.3f;
    private static final float SCALE_MIN = 1.0f;
    private ViewMainSoundPackageBinding binding;

    /* renamed from: mBleManager$delegate, reason: from kotlin metadata */
    private final Lazy mBleManager;
    private float mShiftOfPosition;
    private boolean mSwiping;

    protected final BleManager getMBleManager() {
        return (BleManager) this.mBleManager.getValue();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MainSoundPackageView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MainSoundPackageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MainSoundPackageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mBleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.gui.views.soundpackage.MainSoundPackageView$mBleManager$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final BleManager invoke() {
                return BleManager.INSTANCE.from(this.this$0.getContext());
            }
        });
        initView(attributeSet);
    }

    private final void initView(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewMainSoundPackageBinding viewMainSoundPackageBindingInflate = ViewMainSoundPackageBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewMainSoundPackageBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewMainSoundPackageBindingInflate;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = null;
        if (viewMainSoundPackageBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBindingInflate = null;
        }
        viewMainSoundPackageBindingInflate.setModel(new MainPresetPackageViewModel());
        if (isInEditMode()) {
            return;
        }
        ViewMainSoundPackageBinding viewMainSoundPackageBinding2 = this.binding;
        if (viewMainSoundPackageBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewMainSoundPackageBinding = viewMainSoundPackageBinding2;
        }
        viewMainSoundPackageBinding.imageViewSettings.setEnabled(false);
    }

    public final void setMainPresetPackage(MainPresetPackage preset) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(preset, "preset");
        cleanStates();
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        viewMainSoundPackageBinding.setPresetPackage(preset);
        if (preset.isActivated()) {
            onActivateWithoutAnimation();
        } else {
            onDeactivateWithoutAnimation();
        }
    }

    public final ViewMainSoundPackageBinding getMainBinding() {
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        if (viewMainSoundPackageBinding != null) {
            return viewMainSoundPackageBinding;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        return null;
    }

    public final void onNestedScrollChanged(int scrollY, int oldScrollY) {
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        AppCompatImageView appCompatImageView = viewMainSoundPackageBinding.imageViewCover;
        int i = scrollY - oldScrollY;
        float y = appCompatImageView.getY();
        float f = (i * this.mShiftOfPosition) + y;
        if (i > 0 && y < 0.0f) {
            appCompatImageView.setY(f);
        } else {
            if (i >= 0 || y <= (-(appCompatImageView.getHeight() - getHeight()))) {
                return;
            }
            appCompatImageView.setY(f);
        }
    }

    public final void onNestedScrollChanged(int scrollY, int oldScrollY, int height) {
        setShiftOfPosition(height);
        onNestedScrollChanged(scrollY, oldScrollY);
    }

    public final void setShiftOfPosition(int fullHeight) {
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        this.mShiftOfPosition = (viewMainSoundPackageBinding.mainLayout.getHeight() / fullHeight) / 3.0f;
    }

    public final void onActivate() {
        ObservableBoolean isActivate;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding2 = null;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        MainPresetPackageViewModel model = viewMainSoundPackageBinding.getModel();
        if (model != null && (isActivate = model.getIsActivate()) != null) {
            isActivate.set(true);
        }
        ViewMainSoundPackageBinding viewMainSoundPackageBinding3 = this.binding;
        if (viewMainSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding3 = null;
        }
        MainPresetPackage presetPackage = viewMainSoundPackageBinding3.getPresetPackage();
        if (presetPackage != null) {
            presetPackage.setActivated(true);
        }
        postOnAnimation(new Runnable() { // from class: com.thor.app.gui.views.soundpackage.MainSoundPackageView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MainSoundPackageView.onActivate$lambda$1(this.f$0);
            }
        });
        ViewMainSoundPackageBinding viewMainSoundPackageBinding4 = this.binding;
        if (viewMainSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding4 = null;
        }
        viewMainSoundPackageBinding4.viewCoverRedGradient.setBackgroundResource(R.drawable.bg_shape_sound_card_gradient_accent_vertical);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding5 = this.binding;
        if (viewMainSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding5 = null;
        }
        viewMainSoundPackageBinding5.textViewSoundName.setAlpha(1.0f);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding6 = this.binding;
        if (viewMainSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding6 = null;
        }
        viewMainSoundPackageBinding6.imageViewSettings.setAlpha(1.0f);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding7 = this.binding;
        if (viewMainSoundPackageBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewMainSoundPackageBinding2 = viewMainSoundPackageBinding7;
        }
        viewMainSoundPackageBinding2.imageViewSettings.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivate$lambda$1(MainSoundPackageView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this$0.binding;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        viewMainSoundPackageBinding.imageViewCover.animate().scaleX(SCALE_MAX).scaleY(SCALE_MAX).setDuration(ANIMATION_DURATION).start();
    }

    public final void onActivateWithoutAnimation() {
        ObservableBoolean isActivate;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding2 = null;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        MainPresetPackageViewModel model = viewMainSoundPackageBinding.getModel();
        if (model != null && (isActivate = model.getIsActivate()) != null) {
            isActivate.set(true);
        }
        ViewMainSoundPackageBinding viewMainSoundPackageBinding3 = this.binding;
        if (viewMainSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding3 = null;
        }
        viewMainSoundPackageBinding3.viewCoverRedGradient.setBackgroundResource(R.drawable.bg_shape_sound_card_gradient_accent_vertical);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding4 = this.binding;
        if (viewMainSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding4 = null;
        }
        viewMainSoundPackageBinding4.imageViewSettings.setEnabled(true);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding5 = this.binding;
        if (viewMainSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding5 = null;
        }
        viewMainSoundPackageBinding5.textViewSoundName.setAlpha(1.0f);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding6 = this.binding;
        if (viewMainSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding6 = null;
        }
        viewMainSoundPackageBinding6.imageViewSettings.setAlpha(1.0f);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding7 = this.binding;
        if (viewMainSoundPackageBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding7 = null;
        }
        viewMainSoundPackageBinding7.imageViewCover.setScaleX(SCALE_MAX);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding8 = this.binding;
        if (viewMainSoundPackageBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewMainSoundPackageBinding2 = viewMainSoundPackageBinding8;
        }
        viewMainSoundPackageBinding2.imageViewCover.setScaleY(SCALE_MAX);
    }

    public final void onDeactivate() {
        ObservableBoolean isActivate;
        Timber.INSTANCE.i("onDeactivate", new Object[0]);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding2 = null;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        MainPresetPackage presetPackage = viewMainSoundPackageBinding.getPresetPackage();
        if (presetPackage != null) {
            presetPackage.setActivated(false);
        }
        ViewMainSoundPackageBinding viewMainSoundPackageBinding3 = this.binding;
        if (viewMainSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding3 = null;
        }
        MainPresetPackageViewModel model = viewMainSoundPackageBinding3.getModel();
        if (model != null && (isActivate = model.getIsActivate()) != null) {
            isActivate.set(false);
        }
        postOnAnimation(new Runnable() { // from class: com.thor.app.gui.views.soundpackage.MainSoundPackageView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MainSoundPackageView.onDeactivate$lambda$2(this.f$0);
            }
        });
        ViewMainSoundPackageBinding viewMainSoundPackageBinding4 = this.binding;
        if (viewMainSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding4 = null;
        }
        viewMainSoundPackageBinding4.textViewSoundName.setAlpha(0.5f);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding5 = this.binding;
        if (viewMainSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding5 = null;
        }
        viewMainSoundPackageBinding5.imageViewSettings.setAlpha(0.5f);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding6 = this.binding;
        if (viewMainSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewMainSoundPackageBinding2 = viewMainSoundPackageBinding6;
        }
        viewMainSoundPackageBinding2.imageViewSettings.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onDeactivate$lambda$2(MainSoundPackageView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this$0.binding;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        viewMainSoundPackageBinding.imageViewCover.animate().scaleX(1.0f).scaleY(1.0f).setDuration(ANIMATION_DURATION).start();
    }

    public final void onDeactivateWithoutAnimation() {
        ObservableBoolean isActivate;
        ObservableBoolean isActivate2;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding2 = null;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        MainPresetPackageViewModel model = viewMainSoundPackageBinding.getModel();
        Boolean boolValueOf = (model == null || (isActivate2 = model.getIsActivate()) == null) ? null : Boolean.valueOf(isActivate2.get());
        Intrinsics.checkNotNull(boolValueOf);
        if (boolValueOf.booleanValue()) {
            ViewMainSoundPackageBinding viewMainSoundPackageBinding3 = this.binding;
            if (viewMainSoundPackageBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewMainSoundPackageBinding3 = null;
            }
            MainPresetPackageViewModel model2 = viewMainSoundPackageBinding3.getModel();
            if (model2 != null && (isActivate = model2.getIsActivate()) != null) {
                isActivate.set(false);
            }
            ViewMainSoundPackageBinding viewMainSoundPackageBinding4 = this.binding;
            if (viewMainSoundPackageBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewMainSoundPackageBinding4 = null;
            }
            viewMainSoundPackageBinding4.textViewSoundName.setAlpha(0.5f);
            ViewMainSoundPackageBinding viewMainSoundPackageBinding5 = this.binding;
            if (viewMainSoundPackageBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewMainSoundPackageBinding5 = null;
            }
            viewMainSoundPackageBinding5.imageViewSettings.setAlpha(0.5f);
            ViewMainSoundPackageBinding viewMainSoundPackageBinding6 = this.binding;
            if (viewMainSoundPackageBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewMainSoundPackageBinding6 = null;
            }
            viewMainSoundPackageBinding6.imageViewCover.setScaleX(1.0f);
            ViewMainSoundPackageBinding viewMainSoundPackageBinding7 = this.binding;
            if (viewMainSoundPackageBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewMainSoundPackageBinding7 = null;
            }
            viewMainSoundPackageBinding7.imageViewCover.setScaleY(1.0f);
            ViewMainSoundPackageBinding viewMainSoundPackageBinding8 = this.binding;
            if (viewMainSoundPackageBinding8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                viewMainSoundPackageBinding2 = viewMainSoundPackageBinding8;
            }
            viewMainSoundPackageBinding2.imageViewSettings.setEnabled(false);
        }
    }

    public final void cleanStates() throws Resources.NotFoundException {
        ObservableBoolean isActivate;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding2 = null;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        MainPresetPackageViewModel model = viewMainSoundPackageBinding.getModel();
        if (model != null && (isActivate = model.getIsActivate()) != null) {
            isActivate.set(false);
        }
        cleanSwipe();
        ViewMainSoundPackageBinding viewMainSoundPackageBinding3 = this.binding;
        if (viewMainSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding3 = null;
        }
        viewMainSoundPackageBinding3.textViewSoundName.setAlpha(0.5f);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding4 = this.binding;
        if (viewMainSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding4 = null;
        }
        viewMainSoundPackageBinding4.imageViewSettings.setAlpha(0.5f);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding5 = this.binding;
        if (viewMainSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding5 = null;
        }
        viewMainSoundPackageBinding5.imageViewCover.setScaleX(1.0f);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding6 = this.binding;
        if (viewMainSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding6 = null;
        }
        viewMainSoundPackageBinding6.imageViewCover.setScaleY(1.0f);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding7 = this.binding;
        if (viewMainSoundPackageBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewMainSoundPackageBinding2 = viewMainSoundPackageBinding7;
        }
        viewMainSoundPackageBinding2.imageViewSettings.setEnabled(false);
    }

    public final void cleanSwipe() throws Resources.NotFoundException {
        Timber.INSTANCE.i("cleanSwipe", new Object[0]);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        MainPresetPackageViewModel model = viewMainSoundPackageBinding.getModel();
        ObservableBoolean isDeleteEnabled = model != null ? model.getIsDeleteEnabled() : null;
        Intrinsics.checkNotNull(isDeleteEnabled);
        if (isDeleteEnabled.get()) {
            onSwipeToRightAnimation();
        }
    }

    public final void onSwipeToLeftAnimation() throws Resources.NotFoundException {
        ObservableBoolean isDeleteEnabled;
        Timber.INSTANCE.i("onSwipeToLeftAnimation", new Object[0]);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding2 = null;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        MainPresetPackageViewModel model = viewMainSoundPackageBinding.getModel();
        ObservableBoolean isDeleteEnabled2 = model != null ? model.getIsDeleteEnabled() : null;
        Intrinsics.checkNotNull(isDeleteEnabled2);
        if (isDeleteEnabled2.get() || this.mSwiping) {
            return;
        }
        this.mSwiping = true;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.margin_dp_8);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding3 = this.binding;
        if (viewMainSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding3 = null;
        }
        int width = viewMainSoundPackageBinding3.mainLayout.getWidth() + dimensionPixelSize;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding4 = this.binding;
        if (viewMainSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding4 = null;
        }
        viewMainSoundPackageBinding4.layoutDelete.setX(width);
        Timber.INSTANCE.i("New positionX: %s", Integer.valueOf(width));
        ViewMainSoundPackageBinding viewMainSoundPackageBinding5 = this.binding;
        if (viewMainSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding5 = null;
        }
        viewMainSoundPackageBinding5.layoutDelete.setVisibility(0);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.width_dp_120);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding6 = this.binding;
        if (viewMainSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding6 = null;
        }
        float x = viewMainSoundPackageBinding6.layoutCover.getX();
        ViewMainSoundPackageBinding viewMainSoundPackageBinding7 = this.binding;
        if (viewMainSoundPackageBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding7 = null;
        }
        float x2 = viewMainSoundPackageBinding7.layoutDelete.getX();
        Timber.INSTANCE.i("X: %s, x: %s", Float.valueOf(x), Float.valueOf(x2));
        ViewMainSoundPackageBinding viewMainSoundPackageBinding8 = this.binding;
        if (viewMainSoundPackageBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding8 = null;
        }
        float f = dimensionPixelOffset;
        viewMainSoundPackageBinding8.layoutDelete.animate().x(x2 - f).setDuration(ANIMATION_DURATION).start();
        ViewMainSoundPackageBinding viewMainSoundPackageBinding9 = this.binding;
        if (viewMainSoundPackageBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding9 = null;
        }
        viewMainSoundPackageBinding9.layoutCover.animate().x(x - f).setDuration(ANIMATION_DURATION).start();
        ViewMainSoundPackageBinding viewMainSoundPackageBinding10 = this.binding;
        if (viewMainSoundPackageBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewMainSoundPackageBinding2 = viewMainSoundPackageBinding10;
        }
        MainPresetPackageViewModel model2 = viewMainSoundPackageBinding2.getModel();
        if (model2 != null && (isDeleteEnabled = model2.getIsDeleteEnabled()) != null) {
            isDeleteEnabled.set(true);
        }
        postDelayed(new Runnable() { // from class: com.thor.app.gui.views.soundpackage.MainSoundPackageView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                MainSoundPackageView.onSwipeToLeftAnimation$lambda$3(this.f$0);
            }
        }, ANIMATION_DURATION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSwipeToLeftAnimation$lambda$3(MainSoundPackageView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mSwiping = false;
    }

    public final void onSwipeToRightAnimation() throws Resources.NotFoundException {
        ObservableBoolean isDeleteEnabled;
        Timber.INSTANCE.i("onSwipeToRightAnimation", new Object[0]);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.binding;
        ViewMainSoundPackageBinding viewMainSoundPackageBinding2 = null;
        if (viewMainSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding = null;
        }
        MainPresetPackageViewModel model = viewMainSoundPackageBinding.getModel();
        ObservableBoolean isDeleteEnabled2 = model != null ? model.getIsDeleteEnabled() : null;
        Intrinsics.checkNotNull(isDeleteEnabled2);
        if (!isDeleteEnabled2.get() || this.mSwiping) {
            return;
        }
        this.mSwiping = true;
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.width_dp_120);
        ViewMainSoundPackageBinding viewMainSoundPackageBinding3 = this.binding;
        if (viewMainSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding3 = null;
        }
        float x = viewMainSoundPackageBinding3.layoutCover.getX();
        ViewMainSoundPackageBinding viewMainSoundPackageBinding4 = this.binding;
        if (viewMainSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding4 = null;
        }
        float x2 = viewMainSoundPackageBinding4.layoutDelete.getX();
        ViewMainSoundPackageBinding viewMainSoundPackageBinding5 = this.binding;
        if (viewMainSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding5 = null;
        }
        float f = dimensionPixelOffset;
        viewMainSoundPackageBinding5.layoutDelete.animate().x(x2 + f).setDuration(ANIMATION_DURATION).start();
        ViewMainSoundPackageBinding viewMainSoundPackageBinding6 = this.binding;
        if (viewMainSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewMainSoundPackageBinding6 = null;
        }
        viewMainSoundPackageBinding6.layoutCover.animate().x(f + x).setDuration(ANIMATION_DURATION).start();
        Timber.INSTANCE.i("X: %s, x: %s", Float.valueOf(x), Float.valueOf(x2));
        ViewMainSoundPackageBinding viewMainSoundPackageBinding7 = this.binding;
        if (viewMainSoundPackageBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewMainSoundPackageBinding2 = viewMainSoundPackageBinding7;
        }
        MainPresetPackageViewModel model2 = viewMainSoundPackageBinding2.getModel();
        if (model2 != null && (isDeleteEnabled = model2.getIsDeleteEnabled()) != null) {
            isDeleteEnabled.set(false);
        }
        postDelayed(new Runnable() { // from class: com.thor.app.gui.views.soundpackage.MainSoundPackageView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MainSoundPackageView.onSwipeToRightAnimation$lambda$4(this.f$0);
            }
        }, ANIMATION_DURATION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSwipeToRightAnimation$lambda$4(MainSoundPackageView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mSwiping = false;
    }

    /* compiled from: MainSoundPackageView.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/thor/app/gui/views/soundpackage/MainSoundPackageView$Companion;", "", "()V", "ANIMATION_DURATION", "", "SCALE_MAX", "", "SCALE_MIN", "getTestList", "Ljava/util/ArrayList;", "Lcom/thor/businessmodule/bluetooth/model/other/DriveMode;", "Lkotlin/collections/ArrayList;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ArrayList<DriveMode> getTestList() {
            ArrayList<DriveMode> arrayList = new ArrayList<>();
            arrayList.add(new DriveMode((short) 1, "00"));
            arrayList.add(new DriveMode((short) 2, "01"));
            arrayList.add(new DriveMode((short) 4, "10"));
            return arrayList;
        }
    }
}
