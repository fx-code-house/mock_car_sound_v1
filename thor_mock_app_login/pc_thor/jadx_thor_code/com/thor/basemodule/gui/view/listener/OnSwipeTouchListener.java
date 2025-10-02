package com.thor.basemodule.gui.view.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import timber.log.Timber;

/* loaded from: classes3.dex */
public abstract class OnSwipeTouchListener implements View.OnTouchListener {
    private final GestureDetector mGestureDetector;
    private final float SWIPE_TRANSLATION = 15.0f;
    private float mLastPosition = 0.0f;
    private float mOffsetX = 0.0f;
    private float mDiff = 0.0f;
    private boolean mMoving = false;

    public abstract void onSingleTap();

    public abstract void onSwipeBottom();

    public abstract void onSwipeLeft();

    public abstract void onSwipeRight();

    public abstract void onSwipeTop();

    public OnSwipeTouchListener(Context context) {
        this.mGestureDetector = new GestureDetector(context, new GestureListener());
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003a  */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouch(android.view.View r4, android.view.MotionEvent r5) {
        /*
            r3 = this;
            r4 = 1
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r1 = 0
            r0[r1] = r5
            java.lang.String r2 = "onTouch: %s"
            timber.log.Timber.i(r2, r0)
            int r0 = r5.getAction()
            r2 = 0
            if (r0 == 0) goto L40
            if (r0 == r4) goto L3a
            r4 = 2
            if (r0 == r4) goto L1b
            r4 = 3
            if (r0 == r4) goto L3a
            goto L46
        L1b:
            boolean r4 = r3.mMoving
            if (r4 == 0) goto L46
            float r4 = r3.mLastPosition
            int r0 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r0 == 0) goto L2c
            float r0 = r5.getX()
            float r4 = r4 - r0
            r3.mDiff = r4
        L2c:
            float r4 = r5.getX()
            r3.mLastPosition = r4
            float r4 = r3.mOffsetX
            float r0 = r3.mDiff
            float r4 = r4 + r0
            r3.mOffsetX = r4
            goto L46
        L3a:
            r3.onCheckOffset()
            r3.mMoving = r1
            goto L46
        L40:
            r3.mMoving = r4
            r3.mLastPosition = r2
            r3.mOffsetX = r2
        L46:
            android.view.GestureDetector r4 = r3.mGestureDetector
            boolean r4 = r4.onTouchEvent(r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.basemodule.gui.view.listener.OnSwipeTouchListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    private void onCheckOffset() {
        Timber.i("onCheckOffset: %s", Float.valueOf(this.mOffsetX));
        float f = this.mOffsetX;
        if (f > 15.0f) {
            onSwipeLeft();
        } else if (f < -15.0f) {
            onSwipeRight();
        }
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 20;
        private static final int SWIPE_VELOCITY_THRESHOLD = 20;

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        private GestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            try {
                float y = motionEvent2.getY() - motionEvent.getY();
                float x = motionEvent2.getX() - motionEvent.getX();
                if (Math.abs(x) > Math.abs(y)) {
                    if (Math.abs(x) <= 20.0f || Math.abs(f) <= 20.0f) {
                        return false;
                    }
                } else {
                    if (Math.abs(y) <= 20.0f || Math.abs(f2) <= 20.0f) {
                        return false;
                    }
                    if (y > 0.0f) {
                        OnSwipeTouchListener.this.onSwipeBottom();
                    } else {
                        OnSwipeTouchListener.this.onSwipeTop();
                    }
                }
                return true;
            } catch (Exception e) {
                Timber.e(e);
                return false;
            }
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            OnSwipeTouchListener.this.onSingleTap();
            return true;
        }
    }
}
