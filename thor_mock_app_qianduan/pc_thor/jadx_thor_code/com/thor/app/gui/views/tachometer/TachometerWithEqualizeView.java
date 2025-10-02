package com.thor.app.gui.views.tachometer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.carsystems.thor.app.databinding.ViewTachometerWithEqualizeBinding;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.thor.app.gui.views.tachometer.TachometerView;
import com.thor.businessmodule.model.DemoSoundPackage;
import com.thor.businessmodule.settings.Constants;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TachometerWithEqualizeView.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0006\u0010\u0017\u001a\u00020\u000fJ\u0006\u0010\u0018\u001a\u00020\u0016J\u000e\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0012J\u0010\u0010\u001a\u001a\u00020\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u0012J\u0006\u0010\u001c\u001a\u00020\u0016J\b\u0010\u001d\u001a\u00020\u0016H\u0016J\u0006\u0010\u001e\u001a\u00020\u0016J\u0010\u0010\u001f\u001a\u00020\u00162\b\u0010 \u001a\u0004\u0018\u00010\u0012J\u0010\u0010!\u001a\u00020\u00162\b\u0010\"\u001a\u0004\u0018\u00010#R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/thor/app/gui/views/tachometer/TachometerWithEqualizeView;", "Landroidx/constraintlayout/widget/ConstraintLayout;", "Lcom/thor/app/gui/views/tachometer/TachometerView$OnAnimationListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewTachometerWithEqualizeBinding;", "isFromShop", "", "mPlaying", "mTrackName", "", "mTrackRules", "mTrackUrl", "initView", "", "isPlaying", "onPlay", "trackName", "onPlayByUrl", ImagesContract.URL, "onPrepare", "onStartAnimation", "onStop", "setPresetName", AppMeasurementSdk.ConditionalUserProperty.NAME, "updateDemoUiMode", "category", "Lcom/thor/businessmodule/model/DemoSoundPackage$FuelCategory;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class TachometerWithEqualizeView extends ConstraintLayout implements TachometerView.OnAnimationListener {
    private ViewTachometerWithEqualizeBinding binding;
    private boolean isFromShop;
    private boolean mPlaying;
    private String mTrackName;
    private String mTrackRules;
    private String mTrackUrl;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TachometerWithEqualizeView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TachometerWithEqualizeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TachometerWithEqualizeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mTrackName = Constants.DEFAULT_DEMO_SOUND_ASSET;
        this.mTrackRules = Constants.DEFAULT_DEMO_ANIMATION_RULES_ASSET;
        this.mTrackUrl = "";
        initView(attributeSet);
    }

    private final void initView(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBindingInflate = ViewTachometerWithEqualizeBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewTachometerWithEqualizeBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewTachometerWithEqualizeBindingInflate;
        if (isInEditMode()) {
            return;
        }
        ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding = this.binding;
        if (viewTachometerWithEqualizeBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerWithEqualizeBinding = null;
        }
        viewTachometerWithEqualizeBinding.viewTachometer.setOnAnimationListener(this);
    }

    public final void onPrepare() {
        synchronized (this) {
            this.mPlaying = true;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void onPlay() {
        synchronized (this) {
            if (!this.mPlaying) {
                this.isFromShop = false;
                this.mPlaying = true;
                ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding = this.binding;
                if (viewTachometerWithEqualizeBinding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    viewTachometerWithEqualizeBinding = null;
                }
                viewTachometerWithEqualizeBinding.viewTachometer.onRunFromAssetsFile(this.mTrackRules);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void onPlay(String trackName) {
        Intrinsics.checkNotNullParameter(trackName, "trackName");
        synchronized (this) {
            if (!this.mPlaying) {
                this.isFromShop = false;
                this.mTrackName = trackName;
                this.mPlaying = true;
                ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding = this.binding;
                if (viewTachometerWithEqualizeBinding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    viewTachometerWithEqualizeBinding = null;
                }
                viewTachometerWithEqualizeBinding.viewTachometer.onRunFromAssetsFile(this.mTrackRules);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void onPlayByUrl(String url) {
        synchronized (this) {
            if (!this.mPlaying) {
                this.isFromShop = true;
                if (url != null) {
                    this.mTrackUrl = url;
                }
                this.mPlaying = true;
                ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding = this.binding;
                if (viewTachometerWithEqualizeBinding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    viewTachometerWithEqualizeBinding = null;
                }
                viewTachometerWithEqualizeBinding.viewTachometer.onRunFromAssetsFile(this.mTrackRules);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setPresetName(String name) {
        ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding = this.binding;
        if (viewTachometerWithEqualizeBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerWithEqualizeBinding = null;
        }
        viewTachometerWithEqualizeBinding.viewTachometer.setPresetName(name);
    }

    @Override // com.thor.app.gui.views.tachometer.TachometerView.OnAnimationListener
    public void onStartAnimation() throws IllegalStateException {
        ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding = null;
        if (this.isFromShop) {
            if (this.mTrackUrl.length() > 0) {
                ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding2 = this.binding;
                if (viewTachometerWithEqualizeBinding2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    viewTachometerWithEqualizeBinding = viewTachometerWithEqualizeBinding2;
                }
                viewTachometerWithEqualizeBinding.viewEqualize.playSoundFromUrl(this.mTrackUrl);
                return;
            }
        }
        ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding3 = this.binding;
        if (viewTachometerWithEqualizeBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewTachometerWithEqualizeBinding = viewTachometerWithEqualizeBinding3;
        }
        viewTachometerWithEqualizeBinding.viewEqualize.playSound(this.mTrackName);
    }

    /* JADX WARN: Failed to analyze thrown exceptions
    java.util.ConcurrentModificationException
    	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1096)
    	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1050)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:118)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:69)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:179)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:132)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:69)
     */
    public final void onStop() {
        synchronized (this) {
            if (this.mPlaying) {
                ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding = this.binding;
                ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding2 = null;
                if (viewTachometerWithEqualizeBinding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    viewTachometerWithEqualizeBinding = null;
                }
                viewTachometerWithEqualizeBinding.viewEqualize.stopSound();
                ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding3 = this.binding;
                if (viewTachometerWithEqualizeBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    viewTachometerWithEqualizeBinding2 = viewTachometerWithEqualizeBinding3;
                }
                viewTachometerWithEqualizeBinding2.viewTachometer.onStop();
                this.mPlaying = false;
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* renamed from: isPlaying, reason: from getter */
    public final boolean getMPlaying() {
        return this.mPlaying;
    }

    public final void updateDemoUiMode(DemoSoundPackage.FuelCategory category) {
        ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding = this.binding;
        ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding2 = null;
        if (viewTachometerWithEqualizeBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewTachometerWithEqualizeBinding = null;
        }
        viewTachometerWithEqualizeBinding.viewTachometer.updateDemoUiMode(category);
        ViewTachometerWithEqualizeBinding viewTachometerWithEqualizeBinding3 = this.binding;
        if (viewTachometerWithEqualizeBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewTachometerWithEqualizeBinding2 = viewTachometerWithEqualizeBinding3;
        }
        viewTachometerWithEqualizeBinding2.viewEqualize.updateDemoUiMode(category);
    }
}
