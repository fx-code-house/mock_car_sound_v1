package com.thor.app.gui.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import com.carsystems.thor.app.databinding.ViewRssiSignalBinding;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RssiSignalView.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\tJ\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\tH\u0002R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/thor/app/gui/views/RssiSignalView;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewRssiSignalBinding;", "init", "", "setBackgroundTintByLevel", "signalLevel", "setNewBackgroundColorToView", "view", "Landroid/view/View;", TtmlNode.ATTR_TTS_COLOR, "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class RssiSignalView extends LinearLayout {
    private ViewRssiSignalBinding binding;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RssiSignalView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RssiSignalView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RssiSignalView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        init(attributeSet);
    }

    private final void init(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        if (layoutInflater != null) {
            ViewRssiSignalBinding viewRssiSignalBindingInflate = ViewRssiSignalBinding.inflate(layoutInflater, this, true);
            Intrinsics.checkNotNullExpressionValue(viewRssiSignalBindingInflate, "inflate(it, this, true)");
            this.binding = viewRssiSignalBindingInflate;
        }
    }

    public final void setBackgroundTintByLevel(int signalLevel) {
        View[] viewArr = new View[4];
        ViewRssiSignalBinding viewRssiSignalBinding = this.binding;
        ViewRssiSignalBinding viewRssiSignalBinding2 = null;
        if (viewRssiSignalBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewRssiSignalBinding = null;
        }
        View view = viewRssiSignalBinding.signalFirst;
        Intrinsics.checkNotNullExpressionValue(view, "binding.signalFirst");
        int i = 0;
        viewArr[0] = view;
        ViewRssiSignalBinding viewRssiSignalBinding3 = this.binding;
        if (viewRssiSignalBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewRssiSignalBinding3 = null;
        }
        View view2 = viewRssiSignalBinding3.signalSecond;
        Intrinsics.checkNotNullExpressionValue(view2, "binding.signalSecond");
        viewArr[1] = view2;
        ViewRssiSignalBinding viewRssiSignalBinding4 = this.binding;
        if (viewRssiSignalBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewRssiSignalBinding4 = null;
        }
        View view3 = viewRssiSignalBinding4.signalThird;
        Intrinsics.checkNotNullExpressionValue(view3, "binding.signalThird");
        viewArr[2] = view3;
        ViewRssiSignalBinding viewRssiSignalBinding5 = this.binding;
        if (viewRssiSignalBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            viewRssiSignalBinding2 = viewRssiSignalBinding5;
        }
        View view4 = viewRssiSignalBinding2.signalFourth;
        Intrinsics.checkNotNullExpressionValue(view4, "binding.signalFourth");
        viewArr[3] = view4;
        for (View view5 : CollectionsKt.mutableListOf(viewArr)) {
            if (i < signalLevel) {
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "context");
                setNewBackgroundColorToView(view5, RssiSignalViewKt.getThemeAccentColor(context));
            } else {
                Context context2 = getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "context");
                setNewBackgroundColorToView(view5, RssiSignalViewKt.getThemeSecondaryColor(context2));
            }
            i++;
        }
    }

    private final void setNewBackgroundColorToView(View view, int color) {
        Drawable background = view.getBackground();
        Intrinsics.checkNotNullExpressionValue(background, "view.background");
        Drawable drawableWrap = DrawableCompat.wrap(background);
        Intrinsics.checkNotNullExpressionValue(drawableWrap, "wrap(viewDrawable)");
        DrawableCompat.setTint(drawableWrap, color);
        view.setBackground(drawableWrap);
    }
}
