package com.thor.app.gui.views.demo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.databinding.ObservableBoolean;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ViewDemoSoundPackageBinding;
import com.thor.app.auto.common.MusicServiceConnection;
import com.thor.app.gui.activities.demo.DemoPresetSoundSettingsActivity;
import com.thor.app.managers.DemoSoundPackageManager;
import com.thor.app.utils.di.InjectorUtils;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.businessmodule.model.DemoSoundPackage;
import com.thor.businessmodule.viewmodel.views.DemoSoundPackageViewModel;
import java.lang.ref.WeakReference;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoSoundPackageView.kt */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 92\u00020\u00012\u00020\u0002:\u00019B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\u001e\u001a\u00020\u001fH\u0002J\u0012\u0010 \u001a\u00020\u001f2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0006\u0010!\u001a\u00020\u001fJ\u0006\u0010\"\u001a\u00020\u001fJ\u0012\u0010#\u001a\u00020\u001f2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\b\u0010&\u001a\u00020\u001fH\u0002J\u0006\u0010'\u001a\u00020\u001fJ\u0006\u0010(\u001a\u00020\u001fJ\u0018\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020\nH\u0002J\u001e\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020\n2\u0006\u0010,\u001a\u00020\nJ\b\u0010-\u001a\u00020\u001fH\u0002J\b\u0010.\u001a\u00020\u001fH\u0002J\u0012\u0010/\u001a\u00020\u001f2\b\u00100\u001a\u0004\u0018\u000101H\u0002J\u000e\u00102\u001a\u00020\u001f2\u0006\u00103\u001a\u000204J\u000e\u00105\u001a\u00020\u001f2\u0006\u00106\u001a\u00020\nJ\b\u00107\u001a\u00020\u001fH\u0002J\u0010\u00108\u001a\u00020\u001f2\u0006\u00103\u001a\u000204H\u0002R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0017\u001a\u00020\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001d\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lcom/thor/app/gui/views/demo/DemoSoundPackageView;", "Landroidx/cardview/widget/CardView;", "Landroid/view/View$OnClickListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "animationDuration", "", "binding", "Lcom/carsystems/thor/app/databinding/ViewDemoSoundPackageBinding;", "mActivateAnimatorListener", "Landroid/animation/Animator$AnimatorListener;", "mAnimatorSet", "Landroid/animation/AnimatorSet;", "mDeactivateAnimatorListener", "mShiftOfPosition", "", "musicServiceConnection", "Lcom/thor/app/auto/common/MusicServiceConnection;", "getMusicServiceConnection", "()Lcom/thor/app/auto/common/MusicServiceConnection;", "musicServiceConnection$delegate", "Lkotlin/Lazy;", "scalingFactor", "initAnimation", "", "initView", "onActivate", "onActivateAnimated", "onClick", "v", "Landroid/view/View;", "onClickMainLayout", "onDeactivate", "onDeactivateAnimated", "onNestedScrollChanged", "scrollY", "oldScrollY", "height", "onOpenSettings", "sendMediaPauseRequest", "sendMediaPlayRequest", "track", "", "setDemoSoundPackage", "soundPackage", "Lcom/thor/businessmodule/model/DemoSoundPackage;", "setShiftOfPosition", "fullHeight", "stopAnimation", "updateUiMode", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoSoundPackageView extends CardView implements View.OnClickListener {
    private static final long DEFAULT_ANIMATION_DURATION = 250;
    private static final float SCALE_MAX = 1.3f;
    private static final float SCALE_MIN = 1.0f;
    private long animationDuration;
    private ViewDemoSoundPackageBinding binding;
    private Animator.AnimatorListener mActivateAnimatorListener;
    private AnimatorSet mAnimatorSet;
    private Animator.AnimatorListener mDeactivateAnimatorListener;
    private float mShiftOfPosition;

    /* renamed from: musicServiceConnection$delegate, reason: from kotlin metadata */
    private final Lazy musicServiceConnection;
    private float scalingFactor;

    private final MusicServiceConnection getMusicServiceConnection() {
        return (MusicServiceConnection) this.musicServiceConnection.getValue();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DemoSoundPackageView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DemoSoundPackageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DemoSoundPackageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mAnimatorSet = new AnimatorSet();
        this.animationDuration = DEFAULT_ANIMATION_DURATION;
        this.scalingFactor = SCALE_MAX;
        this.musicServiceConnection = LazyKt.lazy(new Function0<MusicServiceConnection>() { // from class: com.thor.app.gui.views.demo.DemoSoundPackageView$musicServiceConnection$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final MusicServiceConnection invoke() {
                InjectorUtils injectorUtils = InjectorUtils.INSTANCE;
                Context context2 = this.this$0.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "context");
                return injectorUtils.provideMusicServiceConnection(context2);
            }
        });
        initView(attributeSet);
    }

    private final void initView(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewDemoSoundPackageBinding viewDemoSoundPackageBindingInflate = ViewDemoSoundPackageBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewDemoSoundPackageBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewDemoSoundPackageBindingInflate;
        if (isInEditMode()) {
            return;
        }
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = this.binding;
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding2 = null;
        if (viewDemoSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding = null;
        }
        viewDemoSoundPackageBinding.setModel(new DemoSoundPackageViewModel());
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding3 = this.binding;
        if (viewDemoSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewDemoSoundPackageBinding2 = viewDemoSoundPackageBinding3;
        }
        viewDemoSoundPackageBinding2.mainLayout.setOnClickListener(this);
        initAnimation();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.main_layout) {
            onClickMainLayout();
        } else if (numValueOf != null && numValueOf.intValue() == R.id.image_view_settings) {
            onOpenSettings();
        }
    }

    public final void setDemoSoundPackage(DemoSoundPackage soundPackage) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = this.binding;
        if (viewDemoSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding = null;
        }
        viewDemoSoundPackageBinding.setSoundPackage(soundPackage);
    }

    private final void updateUiMode(DemoSoundPackage soundPackage) {
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = null;
        if (Intrinsics.areEqual(soundPackage.getCategory(), DemoSoundPackage.FuelCategory.EV.getValue())) {
            ViewDemoSoundPackageBinding viewDemoSoundPackageBinding2 = this.binding;
            if (viewDemoSoundPackageBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                viewDemoSoundPackageBinding = viewDemoSoundPackageBinding2;
            }
            viewDemoSoundPackageBinding.layoutSelectedGradient.setBackgroundResource(R.drawable.bg_shape_sound_card_gradient_blue_vertical);
            return;
        }
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding3 = this.binding;
        if (viewDemoSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewDemoSoundPackageBinding = viewDemoSoundPackageBinding3;
        }
        viewDemoSoundPackageBinding.layoutSelectedGradient.setBackgroundResource(R.drawable.bg_shape_sound_card_gradient_red_vertical);
    }

    private final void onNestedScrollChanged(int scrollY, int oldScrollY) {
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = this.binding;
        if (viewDemoSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding = null;
        }
        AppCompatImageView appCompatImageView = viewDemoSoundPackageBinding.imageViewCover;
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
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = this.binding;
        if (viewDemoSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding = null;
        }
        this.mShiftOfPosition = (viewDemoSoundPackageBinding.mainLayout.getHeight() / fullHeight) / 3.0f;
    }

    private final void onClickMainLayout() {
        DemoSoundPackageManager.INSTANCE.enableSoundPackage(this);
    }

    public final void onActivateAnimated() {
        ObservableBoolean isActivate;
        stopAnimation();
        callOnClick();
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = this.binding;
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding2 = null;
        if (viewDemoSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding = null;
        }
        DemoSoundPackageViewModel model = viewDemoSoundPackageBinding.getModel();
        if (model != null && (isActivate = model.getIsActivate()) != null) {
            isActivate.set(true);
        }
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding3 = this.binding;
        if (viewDemoSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding3 = null;
        }
        viewDemoSoundPackageBinding3.textViewSoundName.setAlpha(1.0f);
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding4 = this.binding;
        if (viewDemoSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding4 = null;
        }
        viewDemoSoundPackageBinding4.imageViewSettings.setAlpha(1.0f);
        this.mAnimatorSet.addListener(this.mActivateAnimatorListener);
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding5 = this.binding;
        if (viewDemoSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding5 = null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewDemoSoundPackageBinding5.imageViewCover, (Property<AppCompatImageView, Float>) View.SCALE_X, this.scalingFactor);
        objectAnimatorOfFloat.setDuration(this.animationDuration);
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding6 = this.binding;
        if (viewDemoSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewDemoSoundPackageBinding2 = viewDemoSoundPackageBinding6;
        }
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(viewDemoSoundPackageBinding2.imageViewCover, (Property<AppCompatImageView, Float>) View.SCALE_Y, this.scalingFactor);
        objectAnimatorOfFloat2.setDuration(this.animationDuration);
        this.mAnimatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        this.mAnimatorSet.start();
    }

    public final void onDeactivateAnimated() {
        ObservableBoolean isActivate;
        stopAnimation();
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = this.binding;
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding2 = null;
        if (viewDemoSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding = null;
        }
        DemoSoundPackageViewModel model = viewDemoSoundPackageBinding.getModel();
        if (model != null && (isActivate = model.getIsActivate()) != null) {
            isActivate.set(false);
        }
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding3 = this.binding;
        if (viewDemoSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding3 = null;
        }
        viewDemoSoundPackageBinding3.imageViewSettings.setOnClickListener(null);
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding4 = this.binding;
        if (viewDemoSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding4 = null;
        }
        viewDemoSoundPackageBinding4.textViewSoundName.setAlpha(0.5f);
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding5 = this.binding;
        if (viewDemoSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding5 = null;
        }
        viewDemoSoundPackageBinding5.imageViewSettings.setAlpha(0.5f);
        this.mAnimatorSet.addListener(this.mDeactivateAnimatorListener);
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding6 = this.binding;
        if (viewDemoSoundPackageBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding6 = null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewDemoSoundPackageBinding6.imageViewCover, (Property<AppCompatImageView, Float>) View.SCALE_X, 1.0f);
        objectAnimatorOfFloat.setDuration(this.animationDuration);
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding7 = this.binding;
        if (viewDemoSoundPackageBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewDemoSoundPackageBinding2 = viewDemoSoundPackageBinding7;
        }
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(viewDemoSoundPackageBinding2.imageViewCover, (Property<AppCompatImageView, Float>) View.SCALE_Y, 1.0f);
        objectAnimatorOfFloat2.setDuration(this.animationDuration);
        this.mAnimatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        this.mAnimatorSet.start();
    }

    private final void onOpenSettings() {
        Intent intent = new Intent(getContext(), (Class<?>) DemoPresetSoundSettingsActivity.class);
        String str = DemoSoundPackage.BUNDLE_NAME;
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = this.binding;
        if (viewDemoSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding = null;
        }
        intent.putExtra(str, viewDemoSoundPackageBinding.getSoundPackage());
        getContext().startActivity(intent);
        DemoSoundPackageManager.INSTANCE.onStopTachometer();
    }

    private final void initAnimation() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.animationDuration = ContextKt.isCarUIMode(context) ? 0L : DEFAULT_ANIMATION_DURATION;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        this.scalingFactor = ContextKt.isCarUIMode(context2) ? 1.0f : SCALE_MAX;
        this.mActivateAnimatorListener = new Animator.AnimatorListener() { // from class: com.thor.app.gui.views.demo.DemoSoundPackageView.initAnimation.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Context context3 = DemoSoundPackageView.this.getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "context");
                ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = null;
                if (ContextKt.isCarUIMode(context3)) {
                    DemoSoundPackageView demoSoundPackageView = DemoSoundPackageView.this;
                    ViewDemoSoundPackageBinding viewDemoSoundPackageBinding2 = demoSoundPackageView.binding;
                    if (viewDemoSoundPackageBinding2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        viewDemoSoundPackageBinding2 = null;
                    }
                    DemoSoundPackage soundPackage = viewDemoSoundPackageBinding2.getSoundPackage();
                    demoSoundPackageView.sendMediaPlayRequest(soundPackage != null ? soundPackage.getName() : null);
                } else {
                    DemoSoundPackageManager demoSoundPackageManager = DemoSoundPackageManager.INSTANCE;
                    ViewDemoSoundPackageBinding viewDemoSoundPackageBinding3 = DemoSoundPackageView.this.binding;
                    if (viewDemoSoundPackageBinding3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        viewDemoSoundPackageBinding3 = null;
                    }
                    DemoSoundPackage soundPackage2 = viewDemoSoundPackageBinding3.getSoundPackage();
                    String track = soundPackage2 != null ? soundPackage2.getTrack() : null;
                    Intrinsics.checkNotNull(track);
                    demoSoundPackageManager.onPlayTachometer(track);
                }
                ViewDemoSoundPackageBinding viewDemoSoundPackageBinding4 = DemoSoundPackageView.this.binding;
                if (viewDemoSoundPackageBinding4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    viewDemoSoundPackageBinding = viewDemoSoundPackageBinding4;
                }
                viewDemoSoundPackageBinding.imageViewSettings.setOnClickListener(DemoSoundPackageView.this);
            }
        };
        this.mDeactivateAnimatorListener = new Animator.AnimatorListener() { // from class: com.thor.app.gui.views.demo.DemoSoundPackageView.initAnimation.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                DemoSoundPackageManager.INSTANCE.onStopTachometer();
                Context context3 = DemoSoundPackageView.this.getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "context");
                if (ContextKt.isCarUIMode(context3)) {
                    DemoSoundPackageView.this.sendMediaPauseRequest();
                }
            }
        };
    }

    private final void stopAnimation() {
        this.mAnimatorSet.removeAllListeners();
        this.mAnimatorSet.cancel();
    }

    public final void onActivate() {
        ObservableBoolean isActivate;
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = this.binding;
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding2 = null;
        if (viewDemoSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding = null;
        }
        DemoSoundPackageViewModel model = viewDemoSoundPackageBinding.getModel();
        if (model != null && (isActivate = model.getIsActivate()) != null) {
            isActivate.set(true);
        }
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding3 = this.binding;
        if (viewDemoSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding3 = null;
        }
        viewDemoSoundPackageBinding3.textViewSoundName.setAlpha(1.0f);
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding4 = this.binding;
        if (viewDemoSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewDemoSoundPackageBinding2 = viewDemoSoundPackageBinding4;
        }
        viewDemoSoundPackageBinding2.imageViewSettings.setAlpha(1.0f);
    }

    public final void onDeactivate() {
        ObservableBoolean isActivate;
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding = this.binding;
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding2 = null;
        if (viewDemoSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding = null;
        }
        DemoSoundPackageViewModel model = viewDemoSoundPackageBinding.getModel();
        if (model != null && (isActivate = model.getIsActivate()) != null) {
            isActivate.set(false);
        }
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding3 = this.binding;
        if (viewDemoSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewDemoSoundPackageBinding3 = null;
        }
        viewDemoSoundPackageBinding3.textViewSoundName.setAlpha(0.5f);
        ViewDemoSoundPackageBinding viewDemoSoundPackageBinding4 = this.binding;
        if (viewDemoSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewDemoSoundPackageBinding2 = viewDemoSoundPackageBinding4;
        }
        viewDemoSoundPackageBinding2.imageViewSettings.setAlpha(0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendMediaPlayRequest(String track) {
        if (Intrinsics.areEqual((Object) getMusicServiceConnection().isConnected().getValue(), (Object) true)) {
            getMusicServiceConnection().getTransportControls().playFromSearch(track, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendMediaPauseRequest() {
        if (Intrinsics.areEqual((Object) getMusicServiceConnection().isConnected().getValue(), (Object) true)) {
            getMusicServiceConnection().getTransportControls().pause();
        }
    }
}
