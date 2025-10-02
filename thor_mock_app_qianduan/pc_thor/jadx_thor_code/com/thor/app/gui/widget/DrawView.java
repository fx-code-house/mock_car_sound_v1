package com.thor.app.gui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.carsystems.thor.app.R;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: DrawView.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010!\u001a\u00020\"J\u0010\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020%H\u0014J\u0018\u0010&\u001a\u00020\"2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020(H\u0014R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\"\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\n\"\u0004\b\u0018\u0010\fR\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\n\"\u0004\b\u001d\u0010\fR\u001a\u0010\u001e\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\n\"\u0004\b \u0010\f¨\u0006*"}, d2 = {"Lcom/thor/app/gui/widget/DrawView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "bottom", "", "getBottom", "()F", "setBottom", "(F)V", "d", "Landroid/graphics/drawable/Drawable;", "kotlin.jvm.PlatformType", "getD", "()Landroid/graphics/drawable/Drawable;", "setD", "(Landroid/graphics/drawable/Drawable;)V", "eraser", "Landroid/graphics/Paint;", TtmlNode.LEFT, "getLeft", "setLeft", "mRectF", "Landroid/graphics/RectF;", TtmlNode.RIGHT, "getRight", "setRight", "top", "getTop", "setTop", "makehole", "", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onMeasure", "widthMeasureSpec", "", "heightMeasureSpec", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DrawView extends View {
    private float bottom;
    private Drawable d;
    private Paint eraser;
    private float left;
    private RectF mRectF;
    private float right;
    private float top;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        this.eraser = new Paint();
        this.d = getResources().getDrawable(R.drawable.qrcodeframe, null);
        this.mRectF = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        this.eraser.setColor(ContextCompat.getColor(context, R.color.colorBlackTransparent00));
    }

    @Override // android.view.View
    public final float getLeft() {
        return this.left;
    }

    public final void setLeft(float f) {
        this.left = f;
    }

    @Override // android.view.View
    public final float getTop() {
        return this.top;
    }

    public final void setTop(float f) {
        this.top = f;
    }

    @Override // android.view.View
    public final float getRight() {
        return this.right;
    }

    public final void setRight(float f) {
        this.right = f;
    }

    @Override // android.view.View
    public final float getBottom() {
        return this.bottom;
    }

    public final void setBottom(float f) {
        this.bottom = f;
    }

    public final Drawable getD() {
        return this.d;
    }

    public final void setD(Drawable drawable) {
        this.d = drawable;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.colorBlackTransparent60));
        Timber.INSTANCE.i("Draw view height = %s", Integer.valueOf(getHeight()));
        Timber.INSTANCE.i("Draw view width = %s", Integer.valueOf(getWidth()));
        Timber.INSTANCE.i("Draw view left = %s", Float.valueOf(this.left));
        Timber.INSTANCE.i("Draw view top = %s", Float.valueOf(this.top));
        Timber.INSTANCE.i("Draw view right = %s", Float.valueOf(this.right));
        Timber.INSTANCE.i("Draw view bottom = %s", Float.valueOf(this.bottom));
        canvas.drawRect(this.mRectF, this.eraser);
        this.d.setBounds((int) this.left, (int) this.top, (int) this.right, (int) this.bottom);
        this.d.draw(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(size, size2);
        float f = size;
        float f2 = 0.15f * f;
        this.left = f2;
        this.top = 0.0f;
        float f3 = f * 0.85f;
        this.right = f3;
        float f4 = size2;
        this.bottom = f4;
        this.mRectF.set(f2, 0.0f, f3, f4);
    }

    public final void makehole() {
        this.eraser.setAntiAlias(true);
        this.eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        requestLayout();
    }
}
