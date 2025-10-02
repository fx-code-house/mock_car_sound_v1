package com.thor.app.gui.views.sgu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import com.carsystems.thor.app.databinding.ViewSguSoundConfigBinding;
import com.thor.app.bus.events.sgu.ActivatedSguSoundEvent;
import com.thor.app.managers.BleManager;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import java.lang.ref.WeakReference;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: SguSoundConfigView.kt */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 (2\u00020\u0001:\u0001(B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u0015\u001a\u00020\fJ\u0006\u0010\u0016\u001a\u00020\u0017J\u0012\u0010\u0018\u001a\u00020\u00192\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0014J\b\u0010\u001b\u001a\u00020\u0019H\u0014J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0016\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\tJ\u001e\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\tJ\u000e\u0010#\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\tJ\u000e\u0010%\u001a\u00020\u00192\u0006\u0010&\u001a\u00020'R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/thor/app/gui/views/sgu/SguSoundConfigView;", "Landroidx/cardview/widget/CardView;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewSguSoundConfigBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "mShiftOfPosition", "", "getBinding", "getConfigView", "Landroidx/appcompat/widget/AppCompatImageView;", "initView", "", "onAttachedToWindow", "onDetachedFromWindow", "onMessageEvent", "event", "Lcom/thor/app/bus/events/sgu/ActivatedSguSoundEvent;", "onNestedScrollChanged", "scrollY", "oldScrollY", "height", "setShiftOfPosition", "fullHeight", "setSoundPackage", "soundPackage", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSoundConfigView extends CardView {
    public static final long ANIMATION_DURATION = 300;
    public static final float SCALE_DEFAULT = 1.0f;
    public static final float SCALE_MAX = 1.1f;
    private ViewSguSoundConfigBinding binding;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager;
    private float mShiftOfPosition;

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SguSoundConfigView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SguSoundConfigView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SguSoundConfigView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.views.sgu.SguSoundConfigView$bleManager$2
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
        ViewSguSoundConfigBinding viewSguSoundConfigBindingInflate = ViewSguSoundConfigBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewSguSoundConfigBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewSguSoundConfigBindingInflate;
        isInEditMode();
    }

    public final ViewSguSoundConfigBinding getBinding() {
        ViewSguSoundConfigBinding viewSguSoundConfigBinding = this.binding;
        if (viewSguSoundConfigBinding != null) {
            return viewSguSoundConfigBinding;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        return null;
    }

    public final void setSoundPackage(SguSound soundPackage) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        Timber.INSTANCE.i("setSoundPackage: %s", soundPackage);
        ViewSguSoundConfigBinding viewSguSoundConfigBinding = this.binding;
        if (viewSguSoundConfigBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguSoundConfigBinding = null;
        }
        viewSguSoundConfigBinding.setModel(soundPackage);
    }

    public final AppCompatImageView getConfigView() {
        ViewSguSoundConfigBinding viewSguSoundConfigBinding = this.binding;
        if (viewSguSoundConfigBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguSoundConfigBinding = null;
        }
        AppCompatImageView appCompatImageView = viewSguSoundConfigBinding.imageConfig;
        Intrinsics.checkNotNullExpressionValue(appCompatImageView, "binding.imageConfig");
        return appCompatImageView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(ActivatedSguSoundEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() throws SecurityException {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    public final void onNestedScrollChanged(int scrollY, int oldScrollY, int height) {
        setShiftOfPosition(height);
        onNestedScrollChanged(scrollY, oldScrollY);
    }

    public final void onNestedScrollChanged(int scrollY, int oldScrollY) {
        ViewSguSoundConfigBinding viewSguSoundConfigBinding = this.binding;
        if (viewSguSoundConfigBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguSoundConfigBinding = null;
        }
        ImageView imageView = viewSguSoundConfigBinding.imageViewCover;
        int i = scrollY - oldScrollY;
        float y = imageView.getY();
        float f = (i * this.mShiftOfPosition) + y;
        if (i > 0 && y < 0.0f) {
            imageView.setY(f);
        } else {
            if (i >= 0 || y <= (-(imageView.getHeight() - getHeight()))) {
                return;
            }
            imageView.setY(f);
        }
    }

    public final void setShiftOfPosition(int fullHeight) {
        ViewSguSoundConfigBinding viewSguSoundConfigBinding = this.binding;
        if (viewSguSoundConfigBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSguSoundConfigBinding = null;
        }
        this.mShiftOfPosition = (viewSguSoundConfigBinding.mainLayout.getHeight() / fullHeight) / 3.0f;
    }
}
