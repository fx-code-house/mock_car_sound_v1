package com.google.android.exoplayer2.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes.dex */
public class DefaultTimeBar extends View implements TimeBar {
    private static final String ACCESSIBILITY_CLASS_NAME = "android.widget.SeekBar";
    public static final int BAR_GRAVITY_BOTTOM = 1;
    public static final int BAR_GRAVITY_CENTER = 0;
    public static final int DEFAULT_AD_MARKER_COLOR = -1291845888;
    public static final int DEFAULT_AD_MARKER_WIDTH_DP = 4;
    public static final int DEFAULT_BAR_HEIGHT_DP = 4;
    public static final int DEFAULT_BUFFERED_COLOR = -855638017;
    private static final int DEFAULT_INCREMENT_COUNT = 20;
    public static final int DEFAULT_PLAYED_AD_MARKER_COLOR = 872414976;
    public static final int DEFAULT_PLAYED_COLOR = -1;
    public static final int DEFAULT_SCRUBBER_COLOR = -1;
    public static final int DEFAULT_SCRUBBER_DISABLED_SIZE_DP = 0;
    public static final int DEFAULT_SCRUBBER_DRAGGED_SIZE_DP = 16;
    public static final int DEFAULT_SCRUBBER_ENABLED_SIZE_DP = 12;
    public static final int DEFAULT_TOUCH_TARGET_HEIGHT_DP = 26;
    public static final int DEFAULT_UNPLAYED_COLOR = 872415231;
    private static final int FINE_SCRUB_RATIO = 3;
    private static final int FINE_SCRUB_Y_THRESHOLD_DP = -50;
    private static final float HIDDEN_SCRUBBER_SCALE = 0.0f;
    private static final float SHOWN_SCRUBBER_SCALE = 1.0f;
    private static final long STOP_SCRUBBING_TIMEOUT_MS = 1000;
    private int adGroupCount;
    private long[] adGroupTimesMs;
    private final Paint adMarkerPaint;
    private final int adMarkerWidth;
    private final int barGravity;
    private final int barHeight;
    private final Rect bufferedBar;
    private final Paint bufferedPaint;
    private long bufferedPosition;
    private final float density;
    private long duration;
    private final int fineScrubYThreshold;
    private final StringBuilder formatBuilder;
    private final Formatter formatter;
    private int keyCountIncrement;
    private long keyTimeIncrement;
    private int lastCoarseScrubXPosition;
    private Rect lastExclusionRectangle;
    private final CopyOnWriteArraySet<TimeBar.OnScrubListener> listeners;
    private boolean[] playedAdGroups;
    private final Paint playedAdMarkerPaint;
    private final Paint playedPaint;
    private long position;
    private final Rect progressBar;
    private long scrubPosition;
    private final Rect scrubberBar;
    private final int scrubberDisabledSize;
    private final int scrubberDraggedSize;
    private final Drawable scrubberDrawable;
    private final int scrubberEnabledSize;
    private final int scrubberPadding;
    private boolean scrubberPaddingDisabled;
    private final Paint scrubberPaint;
    private float scrubberScale;
    private ValueAnimator scrubberScalingAnimator;
    private boolean scrubbing;
    private final Rect seekBounds;
    private final Runnable stopScrubbingRunnable;
    private final Point touchPosition;
    private final int touchTargetHeight;
    private final Paint unplayedPaint;

    private static int dpToPx(float density, int dps) {
        return (int) ((dps * density) + 0.5f);
    }

    private static int pxToDp(float density, int px) {
        return (int) (px / density);
    }

    public DefaultTimeBar(Context context) {
        this(context, null);
    }

    public DefaultTimeBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultTimeBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, attrs);
    }

    public DefaultTimeBar(Context context, AttributeSet attrs, int defStyleAttr, AttributeSet timebarAttrs) {
        this(context, attrs, defStyleAttr, timebarAttrs, 0);
    }

    public DefaultTimeBar(Context context, AttributeSet attrs, int defStyleAttr, AttributeSet timebarAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        this.seekBounds = new Rect();
        this.progressBar = new Rect();
        this.bufferedBar = new Rect();
        this.scrubberBar = new Rect();
        Paint paint = new Paint();
        this.playedPaint = paint;
        Paint paint2 = new Paint();
        this.bufferedPaint = paint2;
        Paint paint3 = new Paint();
        this.unplayedPaint = paint3;
        Paint paint4 = new Paint();
        this.adMarkerPaint = paint4;
        Paint paint5 = new Paint();
        this.playedAdMarkerPaint = paint5;
        Paint paint6 = new Paint();
        this.scrubberPaint = paint6;
        paint6.setAntiAlias(true);
        this.listeners = new CopyOnWriteArraySet<>();
        this.touchPosition = new Point();
        float f = context.getResources().getDisplayMetrics().density;
        this.density = f;
        this.fineScrubYThreshold = dpToPx(f, FINE_SCRUB_Y_THRESHOLD_DP);
        int iDpToPx = dpToPx(f, 4);
        int iDpToPx2 = dpToPx(f, 26);
        int iDpToPx3 = dpToPx(f, 4);
        int iDpToPx4 = dpToPx(f, 12);
        int iDpToPx5 = dpToPx(f, 0);
        int iDpToPx6 = dpToPx(f, 16);
        if (timebarAttrs != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(timebarAttrs, R.styleable.DefaultTimeBar, defStyleAttr, defStyleRes);
            try {
                Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.DefaultTimeBar_scrubber_drawable);
                this.scrubberDrawable = drawable;
                if (drawable != null) {
                    setDrawableLayoutDirection(drawable);
                    iDpToPx2 = Math.max(drawable.getMinimumHeight(), iDpToPx2);
                }
                this.barHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_bar_height, iDpToPx);
                this.touchTargetHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_touch_target_height, iDpToPx2);
                this.barGravity = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_bar_gravity, 0);
                this.adMarkerWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_ad_marker_width, iDpToPx3);
                this.scrubberEnabledSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_scrubber_enabled_size, iDpToPx4);
                this.scrubberDisabledSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_scrubber_disabled_size, iDpToPx5);
                this.scrubberDraggedSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_scrubber_dragged_size, iDpToPx6);
                int i = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_played_color, -1);
                int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_scrubber_color, -1);
                int i3 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_buffered_color, DEFAULT_BUFFERED_COLOR);
                int i4 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_unplayed_color, DEFAULT_UNPLAYED_COLOR);
                int i5 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_ad_marker_color, DEFAULT_AD_MARKER_COLOR);
                int i6 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_played_ad_marker_color, DEFAULT_PLAYED_AD_MARKER_COLOR);
                paint.setColor(i);
                paint6.setColor(i2);
                paint2.setColor(i3);
                paint3.setColor(i4);
                paint4.setColor(i5);
                paint5.setColor(i6);
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        } else {
            this.barHeight = iDpToPx;
            this.touchTargetHeight = iDpToPx2;
            this.barGravity = 0;
            this.adMarkerWidth = iDpToPx3;
            this.scrubberEnabledSize = iDpToPx4;
            this.scrubberDisabledSize = iDpToPx5;
            this.scrubberDraggedSize = iDpToPx6;
            paint.setColor(-1);
            paint6.setColor(-1);
            paint2.setColor(DEFAULT_BUFFERED_COLOR);
            paint3.setColor(DEFAULT_UNPLAYED_COLOR);
            paint4.setColor(DEFAULT_AD_MARKER_COLOR);
            paint5.setColor(DEFAULT_PLAYED_AD_MARKER_COLOR);
            this.scrubberDrawable = null;
        }
        StringBuilder sb = new StringBuilder();
        this.formatBuilder = sb;
        this.formatter = new Formatter(sb, Locale.getDefault());
        this.stopScrubbingRunnable = new Runnable() { // from class: com.google.android.exoplayer2.ui.DefaultTimeBar$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m198lambda$new$0$comgoogleandroidexoplayer2uiDefaultTimeBar();
            }
        };
        Drawable drawable2 = this.scrubberDrawable;
        if (drawable2 != null) {
            this.scrubberPadding = (drawable2.getMinimumWidth() + 1) / 2;
        } else {
            this.scrubberPadding = (Math.max(this.scrubberDisabledSize, Math.max(this.scrubberEnabledSize, this.scrubberDraggedSize)) + 1) / 2;
        }
        this.scrubberScale = 1.0f;
        ValueAnimator valueAnimator = new ValueAnimator();
        this.scrubberScalingAnimator = valueAnimator;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.DefaultTimeBar$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.m199lambda$new$1$comgoogleandroidexoplayer2uiDefaultTimeBar(valueAnimator2);
            }
        });
        this.duration = C.TIME_UNSET;
        this.keyTimeIncrement = C.TIME_UNSET;
        this.keyCountIncrement = 20;
        setFocusable(true);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    /* renamed from: lambda$new$0$com-google-android-exoplayer2-ui-DefaultTimeBar, reason: not valid java name */
    /* synthetic */ void m198lambda$new$0$comgoogleandroidexoplayer2uiDefaultTimeBar() {
        stopScrubbing(false);
    }

    /* renamed from: lambda$new$1$com-google-android-exoplayer2-ui-DefaultTimeBar, reason: not valid java name */
    /* synthetic */ void m199lambda$new$1$comgoogleandroidexoplayer2uiDefaultTimeBar(ValueAnimator valueAnimator) {
        this.scrubberScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate(this.seekBounds);
    }

    public void showScrubber() {
        if (this.scrubberScalingAnimator.isStarted()) {
            this.scrubberScalingAnimator.cancel();
        }
        this.scrubberPaddingDisabled = false;
        this.scrubberScale = 1.0f;
        invalidate(this.seekBounds);
    }

    public void showScrubber(long showAnimationDurationMs) {
        if (this.scrubberScalingAnimator.isStarted()) {
            this.scrubberScalingAnimator.cancel();
        }
        this.scrubberPaddingDisabled = false;
        this.scrubberScalingAnimator.setFloatValues(this.scrubberScale, 1.0f);
        this.scrubberScalingAnimator.setDuration(showAnimationDurationMs);
        this.scrubberScalingAnimator.start();
    }

    public void hideScrubber(boolean disableScrubberPadding) {
        if (this.scrubberScalingAnimator.isStarted()) {
            this.scrubberScalingAnimator.cancel();
        }
        this.scrubberPaddingDisabled = disableScrubberPadding;
        this.scrubberScale = 0.0f;
        invalidate(this.seekBounds);
    }

    public void hideScrubber(long hideAnimationDurationMs) {
        if (this.scrubberScalingAnimator.isStarted()) {
            this.scrubberScalingAnimator.cancel();
        }
        this.scrubberScalingAnimator.setFloatValues(this.scrubberScale, 0.0f);
        this.scrubberScalingAnimator.setDuration(hideAnimationDurationMs);
        this.scrubberScalingAnimator.start();
    }

    public void setPlayedColor(int playedColor) {
        this.playedPaint.setColor(playedColor);
        invalidate(this.seekBounds);
    }

    public void setScrubberColor(int scrubberColor) {
        this.scrubberPaint.setColor(scrubberColor);
        invalidate(this.seekBounds);
    }

    public void setBufferedColor(int bufferedColor) {
        this.bufferedPaint.setColor(bufferedColor);
        invalidate(this.seekBounds);
    }

    public void setUnplayedColor(int unplayedColor) {
        this.unplayedPaint.setColor(unplayedColor);
        invalidate(this.seekBounds);
    }

    public void setAdMarkerColor(int adMarkerColor) {
        this.adMarkerPaint.setColor(adMarkerColor);
        invalidate(this.seekBounds);
    }

    public void setPlayedAdMarkerColor(int playedAdMarkerColor) {
        this.playedAdMarkerPaint.setColor(playedAdMarkerColor);
        invalidate(this.seekBounds);
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void addListener(TimeBar.OnScrubListener listener) {
        Assertions.checkNotNull(listener);
        this.listeners.add(listener);
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void removeListener(TimeBar.OnScrubListener listener) {
        this.listeners.remove(listener);
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setKeyTimeIncrement(long time) {
        Assertions.checkArgument(time > 0);
        this.keyCountIncrement = -1;
        this.keyTimeIncrement = time;
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setKeyCountIncrement(int count) {
        Assertions.checkArgument(count > 0);
        this.keyCountIncrement = count;
        this.keyTimeIncrement = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setPosition(long position) {
        this.position = position;
        setContentDescription(getProgressText());
        update();
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setBufferedPosition(long bufferedPosition) {
        this.bufferedPosition = bufferedPosition;
        update();
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setDuration(long duration) {
        this.duration = duration;
        if (this.scrubbing && duration == C.TIME_UNSET) {
            stopScrubbing(true);
        }
        update();
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public long getPreferredUpdateDelay() {
        int iPxToDp = pxToDp(this.density, this.progressBar.width());
        if (iPxToDp != 0) {
            long j = this.duration;
            if (j != 0 && j != C.TIME_UNSET) {
                return j / iPxToDp;
            }
        }
        return Long.MAX_VALUE;
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setAdGroupTimesMs(long[] adGroupTimesMs, boolean[] playedAdGroups, int adGroupCount) {
        Assertions.checkArgument(adGroupCount == 0 || !(adGroupTimesMs == null || playedAdGroups == null));
        this.adGroupCount = adGroupCount;
        this.adGroupTimesMs = adGroupTimesMs;
        this.playedAdGroups = playedAdGroups;
        update();
    }

    @Override // android.view.View, com.google.android.exoplayer2.ui.TimeBar
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!this.scrubbing || enabled) {
            return;
        }
        stopScrubbing(true);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        canvas.save();
        drawTimeBar(canvas);
        drawPlayhead(canvas);
        canvas.restore();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004e  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            boolean r0 = r7.isEnabled()
            r1 = 0
            if (r0 == 0) goto L76
            long r2 = r7.duration
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 > 0) goto L10
            goto L76
        L10:
            android.graphics.Point r0 = r7.resolveRelativeTouchPosition(r8)
            int r2 = r0.x
            int r0 = r0.y
            int r3 = r8.getAction()
            r4 = 1
            if (r3 == 0) goto L5d
            r5 = 3
            if (r3 == r4) goto L4e
            r6 = 2
            if (r3 == r6) goto L28
            if (r3 == r5) goto L4e
            goto L76
        L28:
            boolean r8 = r7.scrubbing
            if (r8 == 0) goto L76
            int r8 = r7.fineScrubYThreshold
            if (r0 >= r8) goto L3a
            int r8 = r7.lastCoarseScrubXPosition
            int r2 = r2 - r8
            int r2 = r2 / r5
            int r8 = r8 + r2
            float r8 = (float) r8
            r7.positionScrubber(r8)
            goto L40
        L3a:
            r7.lastCoarseScrubXPosition = r2
            float r8 = (float) r2
            r7.positionScrubber(r8)
        L40:
            long r0 = r7.getScrubberPosition()
            r7.updateScrubbing(r0)
            r7.update()
            r7.invalidate()
            return r4
        L4e:
            boolean r0 = r7.scrubbing
            if (r0 == 0) goto L76
            int r8 = r8.getAction()
            if (r8 != r5) goto L59
            r1 = r4
        L59:
            r7.stopScrubbing(r1)
            return r4
        L5d:
            float r8 = (float) r2
            float r0 = (float) r0
            boolean r0 = r7.isInSeekBar(r8, r0)
            if (r0 == 0) goto L76
            r7.positionScrubber(r8)
            long r0 = r7.getScrubberPosition()
            r7.startScrubbing(r0)
            r7.update()
            r7.invalidate()
            return r4
        L76:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.DefaultTimeBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:11:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0027  */
    @Override // android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onKeyDown(int r5, android.view.KeyEvent r6) {
        /*
            r4 = this;
            boolean r0 = r4.isEnabled()
            if (r0 == 0) goto L30
            long r0 = r4.getPositionIncrement()
            r2 = 66
            r3 = 1
            if (r5 == r2) goto L27
            switch(r5) {
                case 21: goto L13;
                case 22: goto L14;
                case 23: goto L27;
                default: goto L12;
            }
        L12:
            goto L30
        L13:
            long r0 = -r0
        L14:
            boolean r0 = r4.scrubIncrementally(r0)
            if (r0 == 0) goto L30
            java.lang.Runnable r5 = r4.stopScrubbingRunnable
            r4.removeCallbacks(r5)
            java.lang.Runnable r5 = r4.stopScrubbingRunnable
            r0 = 1000(0x3e8, double:4.94E-321)
            r4.postDelayed(r5, r0)
            return r3
        L27:
            boolean r0 = r4.scrubbing
            if (r0 == 0) goto L30
            r5 = 0
            r4.stopScrubbing(r5)
            return r3
        L30:
            boolean r5 = super.onKeyDown(r5, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.DefaultTimeBar.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (!this.scrubbing || gainFocus) {
            return;
        }
        stopScrubbing(false);
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.scrubberDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        int size = View.MeasureSpec.getSize(heightMeasureSpec);
        if (mode == 0) {
            size = this.touchTargetHeight;
        } else if (mode != 1073741824) {
            size = Math.min(this.touchTargetHeight, size);
        }
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), size);
        updateDrawableState();
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int paddingBottom;
        int iMax;
        int i = right - left;
        int i2 = bottom - top;
        int paddingLeft = getPaddingLeft();
        int paddingRight = i - getPaddingRight();
        int i3 = this.scrubberPaddingDisabled ? 0 : this.scrubberPadding;
        if (this.barGravity == 1) {
            paddingBottom = (i2 - getPaddingBottom()) - this.touchTargetHeight;
            int paddingBottom2 = i2 - getPaddingBottom();
            int i4 = this.barHeight;
            iMax = (paddingBottom2 - i4) - Math.max(i3 - (i4 / 2), 0);
        } else {
            paddingBottom = (i2 - this.touchTargetHeight) / 2;
            iMax = (i2 - this.barHeight) / 2;
        }
        this.seekBounds.set(paddingLeft, paddingBottom, paddingRight, this.touchTargetHeight + paddingBottom);
        this.progressBar.set(this.seekBounds.left + i3, iMax, this.seekBounds.right - i3, this.barHeight + iMax);
        if (Util.SDK_INT >= 29) {
            setSystemGestureExclusionRectsV29(i, i2);
        }
        update();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        Drawable drawable = this.scrubberDrawable;
        if (drawable == null || !setDrawableLayoutDirection(drawable, layoutDirection)) {
            return;
        }
        invalidate();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        if (event.getEventType() == 4) {
            event.getText().add(getProgressText());
        }
        event.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ACCESSIBILITY_CLASS_NAME);
        info.setContentDescription(getProgressText());
        if (this.duration <= 0) {
            return;
        }
        if (Util.SDK_INT >= 21) {
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
        } else {
            info.addAction(4096);
            info.addAction(8192);
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int action, Bundle args) {
        if (super.performAccessibilityAction(action, args)) {
            return true;
        }
        if (this.duration <= 0) {
            return false;
        }
        if (action == 8192) {
            if (scrubIncrementally(-getPositionIncrement())) {
                stopScrubbing(false);
            }
        } else {
            if (action != 4096) {
                return false;
            }
            if (scrubIncrementally(getPositionIncrement())) {
                stopScrubbing(false);
            }
        }
        sendAccessibilityEvent(4);
        return true;
    }

    private void startScrubbing(long scrubPosition) {
        this.scrubPosition = scrubPosition;
        this.scrubbing = true;
        setPressed(true);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        Iterator<TimeBar.OnScrubListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onScrubStart(this, scrubPosition);
        }
    }

    private void updateScrubbing(long scrubPosition) {
        if (this.scrubPosition == scrubPosition) {
            return;
        }
        this.scrubPosition = scrubPosition;
        Iterator<TimeBar.OnScrubListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onScrubMove(this, scrubPosition);
        }
    }

    private void stopScrubbing(boolean canceled) {
        removeCallbacks(this.stopScrubbingRunnable);
        this.scrubbing = false;
        setPressed(false);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
        invalidate();
        Iterator<TimeBar.OnScrubListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onScrubStop(this, this.scrubPosition, canceled);
        }
    }

    private boolean scrubIncrementally(long positionChange) {
        long j = this.duration;
        if (j <= 0) {
            return false;
        }
        long j2 = this.scrubbing ? this.scrubPosition : this.position;
        long jConstrainValue = Util.constrainValue(j2 + positionChange, 0L, j);
        if (jConstrainValue == j2) {
            return false;
        }
        if (!this.scrubbing) {
            startScrubbing(jConstrainValue);
        } else {
            updateScrubbing(jConstrainValue);
        }
        update();
        return true;
    }

    private void update() {
        this.bufferedBar.set(this.progressBar);
        this.scrubberBar.set(this.progressBar);
        long j = this.scrubbing ? this.scrubPosition : this.position;
        if (this.duration > 0) {
            this.bufferedBar.right = Math.min(this.progressBar.left + ((int) ((this.progressBar.width() * this.bufferedPosition) / this.duration)), this.progressBar.right);
            this.scrubberBar.right = Math.min(this.progressBar.left + ((int) ((this.progressBar.width() * j) / this.duration)), this.progressBar.right);
        } else {
            this.bufferedBar.right = this.progressBar.left;
            this.scrubberBar.right = this.progressBar.left;
        }
        invalidate(this.seekBounds);
    }

    private void positionScrubber(float xPosition) {
        this.scrubberBar.right = Util.constrainValue((int) xPosition, this.progressBar.left, this.progressBar.right);
    }

    private Point resolveRelativeTouchPosition(MotionEvent motionEvent) {
        this.touchPosition.set((int) motionEvent.getX(), (int) motionEvent.getY());
        return this.touchPosition;
    }

    private long getScrubberPosition() {
        if (this.progressBar.width() <= 0 || this.duration == C.TIME_UNSET) {
            return 0L;
        }
        return (this.scrubberBar.width() * this.duration) / this.progressBar.width();
    }

    private boolean isInSeekBar(float x, float y) {
        return this.seekBounds.contains((int) x, (int) y);
    }

    private void drawTimeBar(Canvas canvas) {
        int iHeight = this.progressBar.height();
        int iCenterY = this.progressBar.centerY() - (iHeight / 2);
        int i = iHeight + iCenterY;
        if (this.duration <= 0) {
            canvas.drawRect(this.progressBar.left, iCenterY, this.progressBar.right, i, this.unplayedPaint);
            return;
        }
        int i2 = this.bufferedBar.left;
        int i3 = this.bufferedBar.right;
        int iMax = Math.max(Math.max(this.progressBar.left, i3), this.scrubberBar.right);
        if (iMax < this.progressBar.right) {
            canvas.drawRect(iMax, iCenterY, this.progressBar.right, i, this.unplayedPaint);
        }
        int iMax2 = Math.max(i2, this.scrubberBar.right);
        if (i3 > iMax2) {
            canvas.drawRect(iMax2, iCenterY, i3, i, this.bufferedPaint);
        }
        if (this.scrubberBar.width() > 0) {
            canvas.drawRect(this.scrubberBar.left, iCenterY, this.scrubberBar.right, i, this.playedPaint);
        }
        if (this.adGroupCount == 0) {
            return;
        }
        long[] jArr = (long[]) Assertions.checkNotNull(this.adGroupTimesMs);
        boolean[] zArr = (boolean[]) Assertions.checkNotNull(this.playedAdGroups);
        int i4 = this.adMarkerWidth / 2;
        for (int i5 = 0; i5 < this.adGroupCount; i5++) {
            canvas.drawRect(this.progressBar.left + Math.min(this.progressBar.width() - this.adMarkerWidth, Math.max(0, ((int) ((this.progressBar.width() * Util.constrainValue(jArr[i5], 0L, this.duration)) / this.duration)) - i4)), iCenterY, r9 + this.adMarkerWidth, i, zArr[i5] ? this.playedAdMarkerPaint : this.adMarkerPaint);
        }
    }

    private void drawPlayhead(Canvas canvas) {
        int i;
        if (this.duration <= 0) {
            return;
        }
        int iConstrainValue = Util.constrainValue(this.scrubberBar.right, this.scrubberBar.left, this.progressBar.right);
        int iCenterY = this.scrubberBar.centerY();
        if (this.scrubberDrawable == null) {
            if (this.scrubbing || isFocused()) {
                i = this.scrubberDraggedSize;
            } else {
                i = isEnabled() ? this.scrubberEnabledSize : this.scrubberDisabledSize;
            }
            canvas.drawCircle(iConstrainValue, iCenterY, (int) ((i * this.scrubberScale) / 2.0f), this.scrubberPaint);
            return;
        }
        int intrinsicWidth = ((int) (r2.getIntrinsicWidth() * this.scrubberScale)) / 2;
        int intrinsicHeight = ((int) (this.scrubberDrawable.getIntrinsicHeight() * this.scrubberScale)) / 2;
        this.scrubberDrawable.setBounds(iConstrainValue - intrinsicWidth, iCenterY - intrinsicHeight, iConstrainValue + intrinsicWidth, iCenterY + intrinsicHeight);
        this.scrubberDrawable.draw(canvas);
    }

    private void updateDrawableState() {
        Drawable drawable = this.scrubberDrawable;
        if (drawable != null && drawable.isStateful() && this.scrubberDrawable.setState(getDrawableState())) {
            invalidate();
        }
    }

    private void setSystemGestureExclusionRectsV29(int width, int height) {
        Rect rect = this.lastExclusionRectangle;
        if (rect != null && rect.width() == width && this.lastExclusionRectangle.height() == height) {
            return;
        }
        Rect rect2 = new Rect(0, 0, width, height);
        this.lastExclusionRectangle = rect2;
        setSystemGestureExclusionRects(Collections.singletonList(rect2));
    }

    private String getProgressText() {
        return Util.getStringForTime(this.formatBuilder, this.formatter, this.position);
    }

    private long getPositionIncrement() {
        long j = this.keyTimeIncrement;
        if (j != C.TIME_UNSET) {
            return j;
        }
        long j2 = this.duration;
        if (j2 == C.TIME_UNSET) {
            return 0L;
        }
        return j2 / this.keyCountIncrement;
    }

    private boolean setDrawableLayoutDirection(Drawable drawable) {
        return Util.SDK_INT >= 23 && setDrawableLayoutDirection(drawable, getLayoutDirection());
    }

    private static boolean setDrawableLayoutDirection(Drawable drawable, int layoutDirection) {
        return Util.SDK_INT >= 23 && drawable.setLayoutDirection(layoutDirection);
    }
}
