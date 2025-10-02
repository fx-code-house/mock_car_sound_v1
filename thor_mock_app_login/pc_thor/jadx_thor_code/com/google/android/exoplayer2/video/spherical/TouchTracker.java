package com.google.android.exoplayer2.video.spherical;

import android.content.Context;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.exoplayer2.video.spherical.OrientationListener;

/* loaded from: classes.dex */
final class TouchTracker extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener, OrientationListener.Listener {
    static final float MAX_PITCH_DEGREES = 45.0f;
    private final GestureDetector gestureDetector;
    private final Listener listener;
    private final float pxPerDegrees;
    private final PointF previousTouchPointPx = new PointF();
    private final PointF accumulatedTouchOffsetDegrees = new PointF();
    private volatile float roll = 3.1415927f;

    public interface Listener {
        void onScrollChange(PointF scrollOffsetDegrees);

        default boolean onSingleTapUp(MotionEvent event) {
            return false;
        }
    }

    public TouchTracker(Context context, Listener listener, float pxPerDegrees) {
        this.listener = listener;
        this.pxPerDegrees = pxPerDegrees;
        this.gestureDetector = new GestureDetector(context, this);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent e) {
        this.previousTouchPointPx.set(e.getX(), e.getY());
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float x = (e2.getX() - this.previousTouchPointPx.x) / this.pxPerDegrees;
        float y = (e2.getY() - this.previousTouchPointPx.y) / this.pxPerDegrees;
        this.previousTouchPointPx.set(e2.getX(), e2.getY());
        double d = this.roll;
        float fCos = (float) Math.cos(d);
        float fSin = (float) Math.sin(d);
        this.accumulatedTouchOffsetDegrees.x -= (fCos * x) - (fSin * y);
        this.accumulatedTouchOffsetDegrees.y += (fSin * x) + (fCos * y);
        PointF pointF = this.accumulatedTouchOffsetDegrees;
        pointF.y = Math.max(-45.0f, Math.min(MAX_PITCH_DEGREES, pointF.y));
        this.listener.onScrollChange(this.accumulatedTouchOffsetDegrees);
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent e) {
        return this.listener.onSingleTapUp(e);
    }

    @Override // com.google.android.exoplayer2.video.spherical.OrientationListener.Listener
    public void onOrientationChange(float[] deviceOrientationMatrix, float roll) {
        this.roll = -roll;
    }
}
