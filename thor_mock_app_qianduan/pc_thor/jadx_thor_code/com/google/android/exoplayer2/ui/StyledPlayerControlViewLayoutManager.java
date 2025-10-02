package com.google.android.exoplayer2.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
final class StyledPlayerControlViewLayoutManager {
    private static final long ANIMATION_INTERVAL_MS = 2000;
    private static final long DURATION_FOR_HIDING_ANIMATION_MS = 250;
    private static final long DURATION_FOR_SHOWING_ANIMATION_MS = 250;
    private static final int UX_STATE_ALL_VISIBLE = 0;
    private static final int UX_STATE_ANIMATING_HIDE = 3;
    private static final int UX_STATE_ANIMATING_SHOW = 4;
    private static final int UX_STATE_NONE_VISIBLE = 2;
    private static final int UX_STATE_ONLY_PROGRESS_VISIBLE = 1;
    private final ViewGroup basicControls;
    private final ViewGroup bottomBar;
    private final ViewGroup centerControls;
    private final View controlsBackground;
    private final ViewGroup extraControls;
    private final ViewGroup extraControlsScrollView;
    private final AnimatorSet hideAllBarsAnimator;
    private final AnimatorSet hideMainBarAnimator;
    private final AnimatorSet hideProgressBarAnimator;
    private boolean isMinimalMode;
    private final ViewGroup minimalControls;
    private boolean needToShowBars;
    private final ValueAnimator overflowHideAnimator;
    private final ValueAnimator overflowShowAnimator;
    private final View overflowShowButton;
    private final AnimatorSet showAllBarsAnimator;
    private final AnimatorSet showMainBarAnimator;
    private final StyledPlayerControlView styledPlayerControlView;
    private final View timeBar;
    private final ViewGroup timeView;
    private final Runnable showAllBarsRunnable = new Runnable() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda5
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.showAllBars();
        }
    };
    private final Runnable hideAllBarsRunnable = new Runnable() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda8
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.hideAllBars();
        }
    };
    private final Runnable hideProgressBarRunnable = new Runnable() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda9
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.hideProgressBar();
        }
    };
    private final Runnable hideMainBarRunnable = new Runnable() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda10
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.hideMainBar();
        }
    };
    private final Runnable hideControllerRunnable = new Runnable() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda11
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.hideController();
        }
    };
    private final View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda12
        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.f$0.onLayoutChange(view, i, i2, i3, i4, i5, i6, i7, i8);
        }
    };
    private boolean animationEnabled = true;
    private int uxState = 0;
    private final List<View> shownButtons = new ArrayList();

    public StyledPlayerControlViewLayoutManager(final StyledPlayerControlView styledPlayerControlView) throws Resources.NotFoundException {
        this.styledPlayerControlView = styledPlayerControlView;
        this.controlsBackground = styledPlayerControlView.findViewById(R.id.exo_controls_background);
        this.centerControls = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_center_controls);
        this.minimalControls = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_minimal_controls);
        ViewGroup viewGroup = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_bottom_bar);
        this.bottomBar = viewGroup;
        this.timeView = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_time);
        View viewFindViewById = styledPlayerControlView.findViewById(R.id.exo_progress);
        this.timeBar = viewFindViewById;
        this.basicControls = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_basic_controls);
        this.extraControls = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_extra_controls);
        this.extraControlsScrollView = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_extra_controls_scroll_view);
        View viewFindViewById2 = styledPlayerControlView.findViewById(R.id.exo_overflow_show);
        this.overflowShowButton = viewFindViewById2;
        View viewFindViewById3 = styledPlayerControlView.findViewById(R.id.exo_overflow_hide);
        if (viewFindViewById2 != null && viewFindViewById3 != null) {
            viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.onOverflowButtonClick(view);
                }
            });
            viewFindViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.onOverflowButtonClick(view);
                }
            });
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.m207x2808b27(valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                if (!(StyledPlayerControlViewLayoutManager.this.timeBar instanceof DefaultTimeBar) || StyledPlayerControlViewLayoutManager.this.isMinimalMode) {
                    return;
                }
                ((DefaultTimeBar) StyledPlayerControlViewLayoutManager.this.timeBar).hideScrubber(250L);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (StyledPlayerControlViewLayoutManager.this.controlsBackground != null) {
                    StyledPlayerControlViewLayoutManager.this.controlsBackground.setVisibility(4);
                }
                if (StyledPlayerControlViewLayoutManager.this.centerControls != null) {
                    StyledPlayerControlViewLayoutManager.this.centerControls.setVisibility(4);
                }
                if (StyledPlayerControlViewLayoutManager.this.minimalControls != null) {
                    StyledPlayerControlViewLayoutManager.this.minimalControls.setVisibility(4);
                }
            }
        });
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat2.setInterpolator(new LinearInterpolator());
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.m208xe5ac3e68(valueAnimator);
            }
        });
        valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                if (StyledPlayerControlViewLayoutManager.this.controlsBackground != null) {
                    StyledPlayerControlViewLayoutManager.this.controlsBackground.setVisibility(0);
                }
                if (StyledPlayerControlViewLayoutManager.this.centerControls != null) {
                    StyledPlayerControlViewLayoutManager.this.centerControls.setVisibility(0);
                }
                if (StyledPlayerControlViewLayoutManager.this.minimalControls != null) {
                    StyledPlayerControlViewLayoutManager.this.minimalControls.setVisibility(StyledPlayerControlViewLayoutManager.this.isMinimalMode ? 0 : 4);
                }
                if (!(StyledPlayerControlViewLayoutManager.this.timeBar instanceof DefaultTimeBar) || StyledPlayerControlViewLayoutManager.this.isMinimalMode) {
                    return;
                }
                ((DefaultTimeBar) StyledPlayerControlViewLayoutManager.this.timeBar).showScrubber(250L);
            }
        });
        Resources resources = styledPlayerControlView.getResources();
        float dimension = resources.getDimension(R.dimen.exo_styled_bottom_bar_height) - resources.getDimension(R.dimen.exo_styled_progress_bar_height);
        float dimension2 = resources.getDimension(R.dimen.exo_styled_bottom_bar_height);
        AnimatorSet animatorSet = new AnimatorSet();
        this.hideMainBarAnimator = animatorSet;
        animatorSet.setDuration(250L);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                StyledPlayerControlViewLayoutManager.this.setUxState(3);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                StyledPlayerControlViewLayoutManager.this.setUxState(1);
                if (StyledPlayerControlViewLayoutManager.this.needToShowBars) {
                    styledPlayerControlView.post(StyledPlayerControlViewLayoutManager.this.showAllBarsRunnable);
                    StyledPlayerControlViewLayoutManager.this.needToShowBars = false;
                }
            }
        });
        animatorSet.play(valueAnimatorOfFloat).with(ofTranslationY(0.0f, dimension, viewFindViewById)).with(ofTranslationY(0.0f, dimension, viewGroup));
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.hideProgressBarAnimator = animatorSet2;
        animatorSet2.setDuration(250L);
        animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                StyledPlayerControlViewLayoutManager.this.setUxState(3);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                StyledPlayerControlViewLayoutManager.this.setUxState(2);
                if (StyledPlayerControlViewLayoutManager.this.needToShowBars) {
                    styledPlayerControlView.post(StyledPlayerControlViewLayoutManager.this.showAllBarsRunnable);
                    StyledPlayerControlViewLayoutManager.this.needToShowBars = false;
                }
            }
        });
        animatorSet2.play(ofTranslationY(dimension, dimension2, viewFindViewById)).with(ofTranslationY(dimension, dimension2, viewGroup));
        AnimatorSet animatorSet3 = new AnimatorSet();
        this.hideAllBarsAnimator = animatorSet3;
        animatorSet3.setDuration(250L);
        animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                StyledPlayerControlViewLayoutManager.this.setUxState(3);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                StyledPlayerControlViewLayoutManager.this.setUxState(2);
                if (StyledPlayerControlViewLayoutManager.this.needToShowBars) {
                    styledPlayerControlView.post(StyledPlayerControlViewLayoutManager.this.showAllBarsRunnable);
                    StyledPlayerControlViewLayoutManager.this.needToShowBars = false;
                }
            }
        });
        animatorSet3.play(valueAnimatorOfFloat).with(ofTranslationY(0.0f, dimension2, viewFindViewById)).with(ofTranslationY(0.0f, dimension2, viewGroup));
        AnimatorSet animatorSet4 = new AnimatorSet();
        this.showMainBarAnimator = animatorSet4;
        animatorSet4.setDuration(250L);
        animatorSet4.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                StyledPlayerControlViewLayoutManager.this.setUxState(4);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                StyledPlayerControlViewLayoutManager.this.setUxState(0);
            }
        });
        animatorSet4.play(valueAnimatorOfFloat2).with(ofTranslationY(dimension, 0.0f, viewFindViewById)).with(ofTranslationY(dimension, 0.0f, viewGroup));
        AnimatorSet animatorSet5 = new AnimatorSet();
        this.showAllBarsAnimator = animatorSet5;
        animatorSet5.setDuration(250L);
        animatorSet5.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                StyledPlayerControlViewLayoutManager.this.setUxState(4);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                StyledPlayerControlViewLayoutManager.this.setUxState(0);
            }
        });
        animatorSet5.play(valueAnimatorOfFloat2).with(ofTranslationY(dimension2, 0.0f, viewFindViewById)).with(ofTranslationY(dimension2, 0.0f, viewGroup));
        ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.overflowShowAnimator = valueAnimatorOfFloat3;
        valueAnimatorOfFloat3.setDuration(250L);
        valueAnimatorOfFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.m209xc8d7f1a9(valueAnimator);
            }
        });
        valueAnimatorOfFloat3.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                if (StyledPlayerControlViewLayoutManager.this.extraControlsScrollView != null) {
                    StyledPlayerControlViewLayoutManager.this.extraControlsScrollView.setVisibility(0);
                    StyledPlayerControlViewLayoutManager.this.extraControlsScrollView.setTranslationX(StyledPlayerControlViewLayoutManager.this.extraControlsScrollView.getWidth());
                    StyledPlayerControlViewLayoutManager.this.extraControlsScrollView.scrollTo(StyledPlayerControlViewLayoutManager.this.extraControlsScrollView.getWidth(), 0);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (StyledPlayerControlViewLayoutManager.this.basicControls != null) {
                    StyledPlayerControlViewLayoutManager.this.basicControls.setVisibility(4);
                }
            }
        });
        ValueAnimator valueAnimatorOfFloat4 = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.overflowHideAnimator = valueAnimatorOfFloat4;
        valueAnimatorOfFloat4.setDuration(250L);
        valueAnimatorOfFloat4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.m210xac03a4ea(valueAnimator);
            }
        });
        valueAnimatorOfFloat4.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                if (StyledPlayerControlViewLayoutManager.this.basicControls != null) {
                    StyledPlayerControlViewLayoutManager.this.basicControls.setVisibility(0);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (StyledPlayerControlViewLayoutManager.this.extraControlsScrollView != null) {
                    StyledPlayerControlViewLayoutManager.this.extraControlsScrollView.setVisibility(4);
                }
            }
        });
    }

    /* renamed from: lambda$new$0$com-google-android-exoplayer2-ui-StyledPlayerControlViewLayoutManager, reason: not valid java name */
    /* synthetic */ void m207x2808b27(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        View view = this.controlsBackground;
        if (view != null) {
            view.setAlpha(fFloatValue);
        }
        ViewGroup viewGroup = this.centerControls;
        if (viewGroup != null) {
            viewGroup.setAlpha(fFloatValue);
        }
        ViewGroup viewGroup2 = this.minimalControls;
        if (viewGroup2 != null) {
            viewGroup2.setAlpha(fFloatValue);
        }
    }

    /* renamed from: lambda$new$1$com-google-android-exoplayer2-ui-StyledPlayerControlViewLayoutManager, reason: not valid java name */
    /* synthetic */ void m208xe5ac3e68(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        View view = this.controlsBackground;
        if (view != null) {
            view.setAlpha(fFloatValue);
        }
        ViewGroup viewGroup = this.centerControls;
        if (viewGroup != null) {
            viewGroup.setAlpha(fFloatValue);
        }
        ViewGroup viewGroup2 = this.minimalControls;
        if (viewGroup2 != null) {
            viewGroup2.setAlpha(fFloatValue);
        }
    }

    /* renamed from: lambda$new$2$com-google-android-exoplayer2-ui-StyledPlayerControlViewLayoutManager, reason: not valid java name */
    /* synthetic */ void m209xc8d7f1a9(ValueAnimator valueAnimator) {
        animateOverflow(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* renamed from: lambda$new$3$com-google-android-exoplayer2-ui-StyledPlayerControlViewLayoutManager, reason: not valid java name */
    /* synthetic */ void m210xac03a4ea(ValueAnimator valueAnimator) {
        animateOverflow(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public void show() {
        if (!this.styledPlayerControlView.isVisible()) {
            this.styledPlayerControlView.setVisibility(0);
            this.styledPlayerControlView.updateAll();
            this.styledPlayerControlView.requestPlayPauseFocus();
        }
        showAllBars();
    }

    public void hide() {
        int i = this.uxState;
        if (i == 3 || i == 2) {
            return;
        }
        removeHideCallbacks();
        if (!this.animationEnabled) {
            hideController();
        } else if (this.uxState == 1) {
            hideProgressBar();
        } else {
            hideAllBars();
        }
    }

    public void hideImmediately() {
        int i = this.uxState;
        if (i == 3 || i == 2) {
            return;
        }
        removeHideCallbacks();
        hideController();
    }

    public void setAnimationEnabled(boolean animationEnabled) {
        this.animationEnabled = animationEnabled;
    }

    public boolean isAnimationEnabled() {
        return this.animationEnabled;
    }

    public void resetHideCallbacks() {
        if (this.uxState == 3) {
            return;
        }
        removeHideCallbacks();
        int showTimeoutMs = this.styledPlayerControlView.getShowTimeoutMs();
        if (showTimeoutMs > 0) {
            if (!this.animationEnabled) {
                postDelayedRunnable(this.hideControllerRunnable, showTimeoutMs);
            } else if (this.uxState == 1) {
                postDelayedRunnable(this.hideProgressBarRunnable, 2000L);
            } else {
                postDelayedRunnable(this.hideMainBarRunnable, showTimeoutMs);
            }
        }
    }

    public void removeHideCallbacks() {
        this.styledPlayerControlView.removeCallbacks(this.hideControllerRunnable);
        this.styledPlayerControlView.removeCallbacks(this.hideAllBarsRunnable);
        this.styledPlayerControlView.removeCallbacks(this.hideMainBarRunnable);
        this.styledPlayerControlView.removeCallbacks(this.hideProgressBarRunnable);
    }

    public void onAttachedToWindow() {
        this.styledPlayerControlView.addOnLayoutChangeListener(this.onLayoutChangeListener);
    }

    public void onDetachedFromWindow() {
        this.styledPlayerControlView.removeOnLayoutChangeListener(this.onLayoutChangeListener);
    }

    public boolean isFullyVisible() {
        return this.uxState == 0 && this.styledPlayerControlView.isVisible();
    }

    public void setShowButton(View button, boolean showButton) {
        if (button == null) {
            return;
        }
        if (!showButton) {
            button.setVisibility(8);
            this.shownButtons.remove(button);
            return;
        }
        if (this.isMinimalMode && shouldHideInMinimalMode(button)) {
            button.setVisibility(4);
        } else {
            button.setVisibility(0);
        }
        this.shownButtons.add(button);
    }

    public boolean getShowButton(View button) {
        return button != null && this.shownButtons.contains(button);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUxState(int uxState) {
        int i = this.uxState;
        this.uxState = uxState;
        if (uxState == 2) {
            this.styledPlayerControlView.setVisibility(8);
        } else if (i == 2) {
            this.styledPlayerControlView.setVisibility(0);
        }
        if (i != uxState) {
            this.styledPlayerControlView.notifyOnVisibilityChange();
        }
    }

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View view = this.controlsBackground;
        if (view != null) {
            view.layout(0, 0, right - left, bottom - top);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        boolean zUseMinimalMode = useMinimalMode();
        if (this.isMinimalMode != zUseMinimalMode) {
            this.isMinimalMode = zUseMinimalMode;
            v.post(new Runnable() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    this.f$0.updateLayoutForSizeChange();
                }
            });
        }
        boolean z = right - left != oldRight - oldLeft;
        if (this.isMinimalMode || !z) {
            return;
        }
        v.post(new Runnable() { // from class: com.google.android.exoplayer2.ui.StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onLayoutWidthChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onOverflowButtonClick(View v) {
        resetHideCallbacks();
        if (v.getId() == R.id.exo_overflow_show) {
            this.overflowShowAnimator.start();
        } else if (v.getId() == R.id.exo_overflow_hide) {
            this.overflowHideAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAllBars() {
        if (!this.animationEnabled) {
            setUxState(0);
            resetHideCallbacks();
            return;
        }
        int i = this.uxState;
        if (i == 1) {
            this.showMainBarAnimator.start();
        } else if (i == 2) {
            this.showAllBarsAnimator.start();
        } else if (i == 3) {
            this.needToShowBars = true;
        } else if (i == 4) {
            return;
        }
        resetHideCallbacks();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideAllBars() {
        this.hideAllBarsAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideProgressBar() {
        this.hideProgressBarAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideMainBar() {
        this.hideMainBarAnimator.start();
        postDelayedRunnable(this.hideProgressBarRunnable, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideController() {
        setUxState(2);
    }

    private static ObjectAnimator ofTranslationY(float startValue, float endValue, View target) {
        return ObjectAnimator.ofFloat(target, "translationY", startValue, endValue);
    }

    private void postDelayedRunnable(Runnable runnable, long interval) {
        if (interval >= 0) {
            this.styledPlayerControlView.postDelayed(runnable, interval);
        }
    }

    private void animateOverflow(float animatedValue) {
        if (this.extraControlsScrollView != null) {
            this.extraControlsScrollView.setTranslationX((int) (r0.getWidth() * (1.0f - animatedValue)));
        }
        ViewGroup viewGroup = this.timeView;
        if (viewGroup != null) {
            viewGroup.setAlpha(1.0f - animatedValue);
        }
        ViewGroup viewGroup2 = this.basicControls;
        if (viewGroup2 != null) {
            viewGroup2.setAlpha(1.0f - animatedValue);
        }
    }

    private boolean useMinimalMode() {
        int width = (this.styledPlayerControlView.getWidth() - this.styledPlayerControlView.getPaddingLeft()) - this.styledPlayerControlView.getPaddingRight();
        int height = (this.styledPlayerControlView.getHeight() - this.styledPlayerControlView.getPaddingBottom()) - this.styledPlayerControlView.getPaddingTop();
        int widthWithMargins = getWidthWithMargins(this.centerControls);
        ViewGroup viewGroup = this.centerControls;
        int paddingLeft = widthWithMargins - (viewGroup != null ? viewGroup.getPaddingLeft() + this.centerControls.getPaddingRight() : 0);
        int heightWithMargins = getHeightWithMargins(this.centerControls);
        ViewGroup viewGroup2 = this.centerControls;
        return width <= Math.max(paddingLeft, getWidthWithMargins(this.timeView) + getWidthWithMargins(this.overflowShowButton)) || height <= (heightWithMargins - (viewGroup2 != null ? viewGroup2.getPaddingTop() + this.centerControls.getPaddingBottom() : 0)) + (getHeightWithMargins(this.bottomBar) * 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLayoutForSizeChange() throws Resources.NotFoundException {
        ViewGroup viewGroup = this.minimalControls;
        if (viewGroup != null) {
            viewGroup.setVisibility(this.isMinimalMode ? 0 : 4);
        }
        View view = this.timeBar;
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            int dimensionPixelSize = this.styledPlayerControlView.getResources().getDimensionPixelSize(R.dimen.exo_styled_progress_margin_bottom);
            if (this.isMinimalMode) {
                dimensionPixelSize = 0;
            }
            marginLayoutParams.bottomMargin = dimensionPixelSize;
            this.timeBar.setLayoutParams(marginLayoutParams);
            View view2 = this.timeBar;
            if (view2 instanceof DefaultTimeBar) {
                DefaultTimeBar defaultTimeBar = (DefaultTimeBar) view2;
                if (this.isMinimalMode) {
                    defaultTimeBar.hideScrubber(true);
                } else {
                    int i = this.uxState;
                    if (i == 1) {
                        defaultTimeBar.hideScrubber(false);
                    } else if (i != 3) {
                        defaultTimeBar.showScrubber();
                    }
                }
            }
        }
        for (View view3 : this.shownButtons) {
            view3.setVisibility((this.isMinimalMode && shouldHideInMinimalMode(view3)) ? 4 : 0);
        }
    }

    private boolean shouldHideInMinimalMode(View button) {
        int id = button.getId();
        return id == R.id.exo_bottom_bar || id == R.id.exo_prev || id == R.id.exo_next || id == R.id.exo_rew || id == R.id.exo_rew_with_amount || id == R.id.exo_ffwd || id == R.id.exo_ffwd_with_amount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLayoutWidthChanged() {
        int i;
        if (this.basicControls == null || this.extraControls == null) {
            return;
        }
        int width = (this.styledPlayerControlView.getWidth() - this.styledPlayerControlView.getPaddingLeft()) - this.styledPlayerControlView.getPaddingRight();
        while (true) {
            if (this.extraControls.getChildCount() <= 1) {
                break;
            }
            int childCount = this.extraControls.getChildCount() - 2;
            View childAt = this.extraControls.getChildAt(childCount);
            this.extraControls.removeViewAt(childCount);
            this.basicControls.addView(childAt, 0);
        }
        View view = this.overflowShowButton;
        if (view != null) {
            view.setVisibility(8);
        }
        int widthWithMargins = getWidthWithMargins(this.timeView);
        int childCount2 = this.basicControls.getChildCount() - 1;
        for (int i2 = 0; i2 < childCount2; i2++) {
            widthWithMargins += getWidthWithMargins(this.basicControls.getChildAt(i2));
        }
        if (widthWithMargins > width) {
            View view2 = this.overflowShowButton;
            if (view2 != null) {
                view2.setVisibility(0);
                widthWithMargins += getWidthWithMargins(this.overflowShowButton);
            }
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < childCount2; i3++) {
                View childAt2 = this.basicControls.getChildAt(i3);
                widthWithMargins -= getWidthWithMargins(childAt2);
                arrayList.add(childAt2);
                if (widthWithMargins <= width) {
                    break;
                }
            }
            if (arrayList.isEmpty()) {
                return;
            }
            this.basicControls.removeViews(0, arrayList.size());
            for (i = 0; i < arrayList.size(); i++) {
                this.extraControls.addView((View) arrayList.get(i), this.extraControls.getChildCount() - 1);
            }
            return;
        }
        ViewGroup viewGroup = this.extraControlsScrollView;
        if (viewGroup == null || viewGroup.getVisibility() != 0 || this.overflowHideAnimator.isStarted()) {
            return;
        }
        this.overflowShowAnimator.cancel();
        this.overflowHideAnimator.start();
    }

    private static int getWidthWithMargins(View v) {
        if (v == null) {
            return 0;
        }
        int width = v.getWidth();
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return width;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return width + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
    }

    private static int getHeightWithMargins(View v) {
        if (v == null) {
            return 0;
        }
        int height = v.getHeight();
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return height;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return height + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }
}
