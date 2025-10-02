package com.thor.app.gui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ViewFlipper;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewFlipperIndicator.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/thor/app/gui/widget/ViewFlipperIndicator;", "Landroid/widget/ViewFlipper;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "margin", "", "paintBlack", "Landroid/graphics/Paint;", "paintCurrent", "paintDefault", "radius", "dispatchDraw", "", "canvas", "Landroid/graphics/Canvas;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ViewFlipperIndicator extends ViewFlipper {
    private final int margin;
    private final Paint paintBlack;
    private final Paint paintCurrent;
    private final Paint paintDefault;
    private final int radius;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewFlipperIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        Paint paint = new Paint();
        this.paintCurrent = paint;
        Paint paint2 = new Paint();
        this.paintDefault = paint2;
        Paint paint3 = new Paint();
        this.paintBlack = paint3;
        this.radius = 10;
        this.margin = 10;
        paint.setColor(SupportMenu.CATEGORY_MASK);
        paint2.setColor(-7829368);
        paint3.setColor(ViewCompat.MEASURED_STATE_MASK);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.dispatchDraw(canvas);
        float width = (getWidth() / 2) - ((((this.radius + this.margin) * 2) * getChildCount()) / 2);
        float height = getHeight() - 15;
        canvas.save();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == getDisplayedChild()) {
                canvas.drawCircle(width, height, this.radius, this.paintCurrent);
            } else {
                canvas.drawCircle(width, height, this.radius, this.paintDefault);
                canvas.drawCircle(width, height, this.radius - 3.0f, this.paintBlack);
            }
            width += (this.radius + this.margin) * 2;
        }
        canvas.restore();
    }
}
