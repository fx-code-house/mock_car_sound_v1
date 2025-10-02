package com.thor.app.gui.views.tachometer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.AttributeSet;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ViewTachometerBinding;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.thor.app.settings.Settings;
import com.thor.app.utils.track.TachometerTrackFileDataParser;
import com.thor.businessmodule.model.DemoSoundPackage;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TachometerView.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001)B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\b\u0010\u0019\u001a\u00020\u0016H\u0002J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u0016H\u0002J\u0012\u0010\u001e\u001a\u00020\u00162\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\b\u0010\u001f\u001a\u00020\u0016H\u0014J\u000e\u0010 \u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010!\u001a\u00020\u0016J\u0010\u0010\"\u001a\u00020\u00162\b\u0010#\u001a\u0004\u0018\u00010\u0012J\u0010\u0010$\u001a\u00020\u00162\b\u0010%\u001a\u0004\u0018\u00010\u001cJ\u0010\u0010&\u001a\u00020\u00162\b\u0010'\u001a\u0004\u0018\u00010(R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082D¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/thor/app/gui/views/tachometer/TachometerView;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewTachometerBinding;", "mIndicatorAnimatorSet", "Landroid/animation/AnimatorSet;", "mIndicatorBackgroundAnimatorSet", "mMainAnimatorSet", "mOnAnimationListener", "Lcom/thor/app/gui/views/tachometer/TachometerView$OnAnimationListener;", "mRotationRatio", "", "applyAvasTheming", "", "applyEcoTheming", "applyMainTheming", "clearIndicatorAnimations", "handleAssetsFile", "fileName", "", "handleGlobalTheming", "initView", "onDetachedFromWindow", "onRunFromAssetsFile", "onStop", "setOnAnimationListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setPresetName", AppMeasurementSdk.ConditionalUserProperty.NAME, "updateDemoUiMode", "category", "Lcom/thor/businessmodule/model/DemoSoundPackage$FuelCategory;", "OnAnimationListener", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class TachometerView extends FrameLayout {
    private ViewTachometerBinding binding;
    private AnimatorSet mIndicatorAnimatorSet;
    private AnimatorSet mIndicatorBackgroundAnimatorSet;
    private AnimatorSet mMainAnimatorSet;
    private OnAnimationListener mOnAnimationListener;
    private final float mRotationRatio;

    /* compiled from: TachometerView.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/thor/app/gui/views/tachometer/TachometerView$OnAnimationListener;", "", "onStartAnimation", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnAnimationListener {
        void onStartAnimation();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TachometerView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TachometerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TachometerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mRotationRatio = 0.0225f;
        this.mMainAnimatorSet = new AnimatorSet();
        this.mIndicatorAnimatorSet = new AnimatorSet();
        this.mIndicatorBackgroundAnimatorSet = new AnimatorSet();
        initView(attributeSet);
    }

    private final void initView(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewTachometerBinding viewTachometerBindingInflate = ViewTachometerBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewTachometerBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewTachometerBindingInflate;
        if (isInEditMode()) {
            return;
        }
        handleGlobalTheming();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AnimatorSet animatorSet = this.mMainAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.mMainAnimatorSet = null;
        this.mIndicatorAnimatorSet = null;
        this.mIndicatorBackgroundAnimatorSet = null;
    }

    public final void onRunFromAssetsFile(String fileName) throws IOException {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        clearIndicatorAnimations();
        handleAssetsFile(fileName);
    }

    public final void onStop() {
        clearIndicatorAnimations();
    }

    public final void setOnAnimationListener(OnAnimationListener listener) {
        this.mOnAnimationListener = listener;
    }

    public final void updateDemoUiMode(DemoSoundPackage.FuelCategory category) {
        if (category == DemoSoundPackage.FuelCategory.EV) {
            applyEcoTheming();
        } else {
            applyMainTheming();
        }
    }

    private final void clearIndicatorAnimations() {
        AnimatorSet animatorSet = this.mMainAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        ViewTachometerBinding viewTachometerBinding = this.binding;
        ViewTachometerBinding viewTachometerBinding2 = null;
        if (viewTachometerBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding = null;
        }
        viewTachometerBinding.viewIndicator.setRotation(0.0f);
        ViewTachometerBinding viewTachometerBinding3 = this.binding;
        if (viewTachometerBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewTachometerBinding2 = viewTachometerBinding3;
        }
        viewTachometerBinding2.viewIndicatorBackground.setRotation(0.0f);
    }

    private final void handleAssetsFile(String fileName) throws IOException {
        List<List<String>> list;
        ViewTachometerBinding viewTachometerBinding = this.binding;
        if (viewTachometerBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding = null;
        }
        viewTachometerBinding.viewIndicator.setPivotY(0.0f);
        ViewTachometerBinding viewTachometerBinding2 = this.binding;
        if (viewTachometerBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding2 = null;
        }
        View view = viewTachometerBinding2.viewIndicatorBackground;
        ViewTachometerBinding viewTachometerBinding3 = this.binding;
        if (viewTachometerBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding3 = null;
        }
        view.setPivotX(viewTachometerBinding3.viewIndicatorBackground.getWidth() / 2.0f);
        ViewTachometerBinding viewTachometerBinding4 = this.binding;
        if (viewTachometerBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding4 = null;
        }
        View view2 = viewTachometerBinding4.viewIndicatorBackground;
        ViewTachometerBinding viewTachometerBinding5 = this.binding;
        if (viewTachometerBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding5 = null;
        }
        view2.setPivotY(viewTachometerBinding5.viewIndicatorBackground.getHeight() / 2.0f);
        AssetFileDescriptor assetFileDescriptorOpenFd = getContext().getAssets().openFd(fileName);
        Intrinsics.checkNotNullExpressionValue(assetFileDescriptorOpenFd, "context.assets.openFd(fileName)");
        FileInputStream fileInputStreamCreateInputStream = assetFileDescriptorOpenFd.createInputStream();
        Intrinsics.checkNotNullExpressionValue(fileInputStreamCreateInputStream, "assetFileDescriptor.createInputStream()");
        List<List<String>> listFetchData = new TachometerTrackFileDataParser(fileInputStreamCreateInputStream).fetchData();
        ArrayList arrayList = new ArrayList();
        List<List<String>> list2 = listFetchData;
        int i = 0;
        for (Object obj : list2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            List list3 = (List) obj;
            if (i != 0) {
                List<String> list4 = listFetchData.get(i - 1);
                float f = Float.parseFloat(list4.get(0)) * this.mRotationRatio;
                float f2 = Float.parseFloat((String) list3.get(0)) * this.mRotationRatio;
                long j = Long.parseLong((String) list3.get(1)) - Long.parseLong(list4.get(1));
                ViewTachometerBinding viewTachometerBinding6 = this.binding;
                if (viewTachometerBinding6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    viewTachometerBinding6 = null;
                }
                ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(viewTachometerBinding6.viewIndicator, (Property<View, Float>) View.ROTATION, f2);
                rotateAnimator.setInterpolator(f < f2 ? new AccelerateInterpolator() : new DecelerateInterpolator());
                rotateAnimator.setDuration(j);
                Intrinsics.checkNotNullExpressionValue(rotateAnimator, "rotateAnimator");
                arrayList.add(rotateAnimator);
            }
            i = i2;
        }
        this.mIndicatorBackgroundAnimatorSet = new AnimatorSet();
        ArrayList arrayList2 = new ArrayList();
        int i3 = 0;
        for (Object obj2 : list2) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            List list5 = (List) obj2;
            if (i3 != 0) {
                List<String> list6 = listFetchData.get(i3 - 1);
                float f3 = Float.parseFloat(list6.get(0)) * this.mRotationRatio;
                float f4 = Float.parseFloat((String) list5.get(0)) * this.mRotationRatio;
                long j2 = Long.parseLong((String) list5.get(1)) - Long.parseLong(list6.get(1));
                ViewTachometerBinding viewTachometerBinding7 = this.binding;
                if (viewTachometerBinding7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    viewTachometerBinding7 = null;
                }
                list = listFetchData;
                ObjectAnimator rotateAnimator2 = ObjectAnimator.ofFloat(viewTachometerBinding7.viewIndicatorBackground, (Property<View, Float>) View.ROTATION, f4);
                rotateAnimator2.setInterpolator(f3 < f4 ? new AccelerateInterpolator() : new DecelerateInterpolator());
                rotateAnimator2.setDuration(j2);
                Intrinsics.checkNotNullExpressionValue(rotateAnimator2, "rotateAnimator");
                arrayList2.add(rotateAnimator2);
            } else {
                list = listFetchData;
            }
            listFetchData = list;
            i3 = i4;
        }
        AnimatorSet animatorSet = this.mIndicatorAnimatorSet;
        if (animatorSet != null) {
            animatorSet.playSequentially(arrayList);
        }
        AnimatorSet animatorSet2 = this.mIndicatorBackgroundAnimatorSet;
        if (animatorSet2 != null) {
            animatorSet2.playSequentially(arrayList2);
        }
        AnimatorSet animatorSet3 = this.mMainAnimatorSet;
        if (animatorSet3 != null) {
            animatorSet3.playTogether(this.mIndicatorAnimatorSet, this.mIndicatorBackgroundAnimatorSet);
        }
        OnAnimationListener onAnimationListener = this.mOnAnimationListener;
        if (onAnimationListener != null) {
            onAnimationListener.onStartAnimation();
        }
        postDelayed(new Runnable() { // from class: com.thor.app.gui.views.tachometer.TachometerView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TachometerView.handleAssetsFile$lambda$4(this.f$0);
            }
        }, 50L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAssetsFile$lambda$4(TachometerView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AnimatorSet animatorSet = this$0.mMainAnimatorSet;
        if (animatorSet != null) {
            animatorSet.start();
        }
    }

    private final void handleGlobalTheming() {
        int carFuelType = Settings.INSTANCE.getCarFuelType();
        if (carFuelType == 1) {
            applyEcoTheming();
            return;
        }
        if (carFuelType == 2) {
            applyMainTheming();
        } else if (carFuelType == 3) {
            applyAvasTheming();
        } else {
            applyMainTheming();
        }
    }

    private final void applyMainTheming() {
        ViewTachometerBinding viewTachometerBinding = this.binding;
        ViewTachometerBinding viewTachometerBinding2 = null;
        if (viewTachometerBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding = null;
        }
        viewTachometerBinding.viewIndicator.setBackgroundResource(2131231189);
        ViewTachometerBinding viewTachometerBinding3 = this.binding;
        if (viewTachometerBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding3 = null;
        }
        viewTachometerBinding3.viewScale.setBackgroundResource(2131231193);
        ViewTachometerBinding viewTachometerBinding4 = this.binding;
        if (viewTachometerBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewTachometerBinding2 = viewTachometerBinding4;
        }
        viewTachometerBinding2.viewIndicatorBackground.setBackgroundResource(2131231190);
    }

    private final void applyEcoTheming() {
        ViewTachometerBinding viewTachometerBinding = this.binding;
        ViewTachometerBinding viewTachometerBinding2 = null;
        if (viewTachometerBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding = null;
        }
        viewTachometerBinding.viewIndicator.setBackgroundResource(R.drawable.ic_tachometer_eco_indicator);
        ViewTachometerBinding viewTachometerBinding3 = this.binding;
        if (viewTachometerBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding3 = null;
        }
        viewTachometerBinding3.viewScale.setBackgroundResource(2131231186);
        ViewTachometerBinding viewTachometerBinding4 = this.binding;
        if (viewTachometerBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewTachometerBinding2 = viewTachometerBinding4;
        }
        viewTachometerBinding2.viewIndicatorBackground.setBackgroundResource(2131231191);
    }

    private final void applyAvasTheming() {
        ViewTachometerBinding viewTachometerBinding = this.binding;
        ViewTachometerBinding viewTachometerBinding2 = null;
        if (viewTachometerBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding = null;
        }
        viewTachometerBinding.viewIndicator.setBackgroundResource(R.drawable.ic_tachometer_green_indicator);
        ViewTachometerBinding viewTachometerBinding3 = this.binding;
        if (viewTachometerBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding3 = null;
        }
        viewTachometerBinding3.viewScale.setBackgroundResource(R.drawable.ic_tachometer_green_scale);
        ViewTachometerBinding viewTachometerBinding4 = this.binding;
        if (viewTachometerBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewTachometerBinding2 = viewTachometerBinding4;
        }
        viewTachometerBinding2.viewIndicatorBackground.setBackgroundResource(R.drawable.ic_tachometer_indicator_green_background);
    }

    public final void setPresetName(String name) {
        ViewTachometerBinding viewTachometerBinding = this.binding;
        if (viewTachometerBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerBinding = null;
        }
        viewTachometerBinding.textPresetName.setText(name);
    }
}
