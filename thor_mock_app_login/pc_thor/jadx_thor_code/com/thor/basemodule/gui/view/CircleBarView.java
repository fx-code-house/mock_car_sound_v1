package com.thor.basemodule.gui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.thor.basemodule.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class CircleBarView extends View {
    public static final int FULL = 360;
    public static final int ONE_QUARTER = -90;
    public static final int START = 90;
    public static final int THREE_QUARTER = 270;
    public static final int TWO_QUARTER = 180;
    public static final int ZERO = 0;
    private float mAngle;
    private int mBackgroundColor;
    private Paint mBackgroundPaint;
    private int mMainColor;
    private Paint mMainPaint;
    private RectF mRect;
    private int mStrokeWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PartOfCircle {
    }

    public CircleBarView(Context context) {
        super(context);
        this.mAngle = 0.0f;
        this.mStrokeWidth = 10;
        initialize();
    }

    public CircleBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAngle = 0.0f;
        this.mStrokeWidth = 10;
        initialize();
        initializeAttrs(attributeSet);
    }

    public CircleBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAngle = 0.0f;
        this.mStrokeWidth = 10;
        initialize();
        initializeAttrs(attributeSet);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (size2 < size) {
            size = size2;
        }
        setMeasuredDimension(size, size);
        this.mRect = takeRectSquare(this.mStrokeWidth, size, size);
        this.mStrokeWidth = getResources().getDimensionPixelSize(R.dimen.width_dp_4);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(this.mRect, 0.0f, 360.0f, false, this.mBackgroundPaint);
        canvas.drawArc(this.mRect, 90.0f, this.mAngle, false, this.mMainPaint);
    }

    private void initialize() {
        Paint paint = new Paint();
        this.mMainPaint = paint;
        paint.setAntiAlias(true);
        this.mMainPaint.setStyle(Paint.Style.STROKE);
        this.mMainPaint.setStrokeWidth(this.mStrokeWidth);
        this.mBackgroundPaint = new Paint(this.mMainPaint);
    }

    private void initializeAttrs(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.CircleBar, 0, 0);
        try {
            this.mAngle = typedArrayObtainStyledAttributes.getFloat(R.styleable.CircleBar_angle, 0.0f);
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.CircleBar_mainColor, -7829368);
            this.mMainColor = color;
            this.mMainPaint.setColor(color);
            int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.CircleBar_backgroundColor, -16776961);
            this.mBackgroundColor = color2;
            this.mBackgroundPaint.setColor(color2);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public float getAngle() {
        return this.mAngle;
    }

    public void setAngle(float f) {
        this.mAngle = f;
        requestLayout();
    }

    public void setMainColor(int i) {
        this.mMainColor = i;
        this.mMainPaint.setColor(i);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        this.mBackgroundPaint.setColor(i);
    }

    private RectF takeRectSquare(int i, int i2, int i3) {
        float f = i;
        return new RectF(f, f, i2 - i, i3 - i);
    }

    public static class CircleBarAngleAnimation extends Animation {
        private final CircleBarView mCircleBarView;
        private final float mNewAngle;
        private final float mOldAngle;

        public CircleBarAngleAnimation(CircleBarView circleBarView, int i) {
            this.mOldAngle = circleBarView.getAngle();
            this.mNewAngle = i;
            this.mCircleBarView = circleBarView;
        }

        @Override // android.view.animation.Animation
        protected void applyTransformation(float f, Transformation transformation) {
            float f2 = this.mOldAngle;
            this.mCircleBarView.setAngle(f2 + ((this.mNewAngle - f2) * f));
            this.mCircleBarView.requestLayout();
        }
    }
}
