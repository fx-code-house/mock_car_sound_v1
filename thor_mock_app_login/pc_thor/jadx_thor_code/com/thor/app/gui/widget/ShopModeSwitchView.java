package com.thor.app.gui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ViewShopModeSwitchBinding;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ShopModeSwitchView.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\u0000 \u001b2\u00020\u0001:\u0003\u001b\u001c\u001dB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u0011\u001a\u00020\u000eJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0012\u0010\u0015\u001a\u00020\u00132\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u000e\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u000eJ\u000e\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0010J\b\u0010\u0019\u001a\u00020\u0013H\u0002J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000eH\u0002R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/thor/app/gui/widget/ShopModeSwitchView;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewShopModeSwitchBinding;", "currentMode", "Lcom/thor/app/gui/widget/ShopModeSwitchView$ShopMode;", "shopModeChangedListener", "Lcom/thor/app/gui/widget/ShopModeSwitchView$OnShopModeChangedListener;", "getCurrentMode", "handleShopModeChange", "", "shopMode", "initialize", "setCurrentMode", "mode", "setOnShopModeChangeListener", "setupListeners", "updateUI", "Companion", "OnShopModeChangedListener", "ShopMode", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ShopModeSwitchView extends FrameLayout {
    public static final String SHOP_MODE_BUNDLE = "shop_mode_bundle";
    private ViewShopModeSwitchBinding binding;
    private ShopMode currentMode;
    private OnShopModeChangedListener shopModeChangedListener;

    /* compiled from: ShopModeSwitchView.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/thor/app/gui/widget/ShopModeSwitchView$OnShopModeChangedListener;", "", "onModeChange", "", "mode", "Lcom/thor/app/gui/widget/ShopModeSwitchView$ShopMode;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnShopModeChangedListener {
        void onModeChange(ShopMode mode);
    }

    /* compiled from: ShopModeSwitchView.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lcom/thor/app/gui/widget/ShopModeSwitchView$ShopMode;", "", "(Ljava/lang/String;I)V", "CAR_SOUND", "BOOMBOX", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum ShopMode {
        CAR_SOUND,
        BOOMBOX
    }

    /* compiled from: ShopModeSwitchView.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ShopMode.values().length];
            try {
                iArr[ShopMode.CAR_SOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ShopMode.BOOMBOX.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateUI$lambda$3(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateUI$lambda$6(View view) {
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ShopModeSwitchView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ShopModeSwitchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShopModeSwitchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.currentMode = ShopMode.CAR_SOUND;
        initialize(attributeSet);
    }

    private final void initialize(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        if (layoutInflater != null) {
            ViewShopModeSwitchBinding viewShopModeSwitchBindingInflate = ViewShopModeSwitchBinding.inflate(layoutInflater, this, true);
            Intrinsics.checkNotNullExpressionValue(viewShopModeSwitchBindingInflate, "inflate(layoutInflater, this, true)");
            this.binding = viewShopModeSwitchBindingInflate;
            setupListeners();
        }
    }

    private final void setupListeners() {
        ViewShopModeSwitchBinding viewShopModeSwitchBinding = this.binding;
        ViewShopModeSwitchBinding viewShopModeSwitchBinding2 = null;
        if (viewShopModeSwitchBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopModeSwitchBinding = null;
        }
        viewShopModeSwitchBinding.textCarSound.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.widget.ShopModeSwitchView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShopModeSwitchView.setupListeners$lambda$1(this.f$0, view);
            }
        });
        ViewShopModeSwitchBinding viewShopModeSwitchBinding3 = this.binding;
        if (viewShopModeSwitchBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewShopModeSwitchBinding2 = viewShopModeSwitchBinding3;
        }
        viewShopModeSwitchBinding2.textBoombox.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.widget.ShopModeSwitchView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShopModeSwitchView.setupListeners$lambda$2(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupListeners$lambda$1(ShopModeSwitchView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleShopModeChange(ShopMode.CAR_SOUND);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupListeners$lambda$2(ShopModeSwitchView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleShopModeChange(ShopMode.BOOMBOX);
    }

    private final void handleShopModeChange(ShopMode shopMode) {
        this.currentMode = shopMode;
        OnShopModeChangedListener onShopModeChangedListener = this.shopModeChangedListener;
        if (onShopModeChangedListener != null) {
            onShopModeChangedListener.onModeChange(shopMode);
        }
        updateUI(shopMode);
    }

    private final void updateUI(ShopMode shopMode) {
        int i = WhenMappings.$EnumSwitchMapping$0[shopMode.ordinal()];
        if (i == 1) {
            ViewShopModeSwitchBinding viewShopModeSwitchBinding = this.binding;
            if (viewShopModeSwitchBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewShopModeSwitchBinding = null;
            }
            viewShopModeSwitchBinding.textCarSound.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.widget.ShopModeSwitchView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ShopModeSwitchView.updateUI$lambda$3(view);
                }
            });
            ViewShopModeSwitchBinding viewShopModeSwitchBinding2 = this.binding;
            if (viewShopModeSwitchBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewShopModeSwitchBinding2 = null;
            }
            viewShopModeSwitchBinding2.textBoombox.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.widget.ShopModeSwitchView$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ShopModeSwitchView.updateUI$lambda$4(this.f$0, view);
                }
            });
            ViewShopModeSwitchBinding viewShopModeSwitchBinding3 = this.binding;
            if (viewShopModeSwitchBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewShopModeSwitchBinding3 = null;
            }
            viewShopModeSwitchBinding3.textCarSound.setTextAppearance(R.style.TextAppearance_ShopModeSwitch_Active);
            ViewShopModeSwitchBinding viewShopModeSwitchBinding4 = this.binding;
            if (viewShopModeSwitchBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewShopModeSwitchBinding4 = null;
            }
            viewShopModeSwitchBinding4.textCarSound.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_shop_mode_active_left));
            ViewShopModeSwitchBinding viewShopModeSwitchBinding5 = this.binding;
            if (viewShopModeSwitchBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewShopModeSwitchBinding5 = null;
            }
            viewShopModeSwitchBinding5.textBoombox.setTextAppearance(R.style.TextAppearance_ShopModeSwitch);
            ViewShopModeSwitchBinding viewShopModeSwitchBinding6 = this.binding;
            if (viewShopModeSwitchBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewShopModeSwitchBinding6 = null;
            }
            viewShopModeSwitchBinding6.textBoombox.setBackground(null);
            return;
        }
        if (i != 2) {
            return;
        }
        ViewShopModeSwitchBinding viewShopModeSwitchBinding7 = this.binding;
        if (viewShopModeSwitchBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopModeSwitchBinding7 = null;
        }
        viewShopModeSwitchBinding7.textCarSound.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.widget.ShopModeSwitchView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShopModeSwitchView.updateUI$lambda$5(this.f$0, view);
            }
        });
        ViewShopModeSwitchBinding viewShopModeSwitchBinding8 = this.binding;
        if (viewShopModeSwitchBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopModeSwitchBinding8 = null;
        }
        viewShopModeSwitchBinding8.textBoombox.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.widget.ShopModeSwitchView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShopModeSwitchView.updateUI$lambda$6(view);
            }
        });
        ViewShopModeSwitchBinding viewShopModeSwitchBinding9 = this.binding;
        if (viewShopModeSwitchBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopModeSwitchBinding9 = null;
        }
        viewShopModeSwitchBinding9.textBoombox.setTextAppearance(R.style.TextAppearance_ShopModeSwitch_Active);
        ViewShopModeSwitchBinding viewShopModeSwitchBinding10 = this.binding;
        if (viewShopModeSwitchBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopModeSwitchBinding10 = null;
        }
        viewShopModeSwitchBinding10.textBoombox.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_shop_mode_active_right));
        ViewShopModeSwitchBinding viewShopModeSwitchBinding11 = this.binding;
        if (viewShopModeSwitchBinding11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopModeSwitchBinding11 = null;
        }
        viewShopModeSwitchBinding11.textCarSound.setTextAppearance(R.style.TextAppearance_ShopModeSwitch);
        ViewShopModeSwitchBinding viewShopModeSwitchBinding12 = this.binding;
        if (viewShopModeSwitchBinding12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewShopModeSwitchBinding12 = null;
        }
        viewShopModeSwitchBinding12.textCarSound.setBackground(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateUI$lambda$4(ShopModeSwitchView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleShopModeChange(ShopMode.BOOMBOX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateUI$lambda$5(ShopModeSwitchView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleShopModeChange(ShopMode.CAR_SOUND);
    }

    public final void setOnShopModeChangeListener(OnShopModeChangedListener shopModeChangedListener) {
        Intrinsics.checkNotNullParameter(shopModeChangedListener, "shopModeChangedListener");
        this.shopModeChangedListener = shopModeChangedListener;
    }

    public final ShopMode getCurrentMode() {
        return this.currentMode;
    }

    public final void setCurrentMode(ShopMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        handleShopModeChange(mode);
    }
}
