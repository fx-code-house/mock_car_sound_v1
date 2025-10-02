package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.FrameLayout;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class SubtitleView extends FrameLayout implements TextOutput {
    public static final float DEFAULT_BOTTOM_PADDING_FRACTION = 0.08f;
    public static final float DEFAULT_TEXT_SIZE_FRACTION = 0.0533f;
    public static final int VIEW_TYPE_CANVAS = 1;
    public static final int VIEW_TYPE_WEB = 2;
    private boolean applyEmbeddedFontSizes;
    private boolean applyEmbeddedStyles;
    private float bottomPaddingFraction;
    private List<Cue> cues;
    private float defaultTextSize;
    private int defaultTextSizeType;
    private View innerSubtitleView;
    private Output output;
    private CaptionStyleCompat style;
    private int viewType;

    interface Output {
        void update(List<Cue> cues, CaptionStyleCompat style, float defaultTextSize, int defaultTextSizeType, float bottomPaddingFraction);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    public SubtitleView(Context context) {
        this(context, null);
    }

    public SubtitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cues = Collections.emptyList();
        this.style = CaptionStyleCompat.DEFAULT;
        this.defaultTextSizeType = 0;
        this.defaultTextSize = 0.0533f;
        this.bottomPaddingFraction = 0.08f;
        this.applyEmbeddedStyles = true;
        this.applyEmbeddedFontSizes = true;
        CanvasSubtitleOutput canvasSubtitleOutput = new CanvasSubtitleOutput(context);
        this.output = canvasSubtitleOutput;
        this.innerSubtitleView = canvasSubtitleOutput;
        addView(canvasSubtitleOutput);
        this.viewType = 1;
    }

    @Override // com.google.android.exoplayer2.text.TextOutput
    public void onCues(List<Cue> cues) {
        setCues(cues);
    }

    public void setCues(List<Cue> cues) {
        if (cues == null) {
            cues = Collections.emptyList();
        }
        this.cues = cues;
        updateOutput();
    }

    public void setViewType(int viewType) {
        if (this.viewType == viewType) {
            return;
        }
        if (viewType == 1) {
            setView(new CanvasSubtitleOutput(getContext()));
        } else if (viewType == 2) {
            setView(new WebViewSubtitleOutput(getContext()));
        } else {
            throw new IllegalArgumentException();
        }
        this.viewType = viewType;
    }

    private <T extends View & Output> void setView(T view) {
        removeView(this.innerSubtitleView);
        View view2 = this.innerSubtitleView;
        if (view2 instanceof WebViewSubtitleOutput) {
            ((WebViewSubtitleOutput) view2).destroy();
        }
        this.innerSubtitleView = view;
        this.output = view;
        addView(view);
    }

    public void setFixedTextSize(int unit, float size) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        setTextSize(2, TypedValue.applyDimension(unit, size, resources.getDisplayMetrics()));
    }

    public void setUserDefaultTextSize() {
        setFractionalTextSize(getUserCaptionFontScale() * 0.0533f);
    }

    public void setFractionalTextSize(float fractionOfHeight) {
        setFractionalTextSize(fractionOfHeight, false);
    }

    public void setFractionalTextSize(float f, boolean z) {
        setTextSize(z ? 1 : 0, f);
    }

    private void setTextSize(int textSizeType, float textSize) {
        this.defaultTextSizeType = textSizeType;
        this.defaultTextSize = textSize;
        updateOutput();
    }

    public void setApplyEmbeddedStyles(boolean applyEmbeddedStyles) {
        this.applyEmbeddedStyles = applyEmbeddedStyles;
        updateOutput();
    }

    public void setApplyEmbeddedFontSizes(boolean applyEmbeddedFontSizes) {
        this.applyEmbeddedFontSizes = applyEmbeddedFontSizes;
        updateOutput();
    }

    public void setUserDefaultStyle() {
        setStyle(getUserCaptionStyle());
    }

    public void setStyle(CaptionStyleCompat style) {
        this.style = style;
        updateOutput();
    }

    public void setBottomPaddingFraction(float bottomPaddingFraction) {
        this.bottomPaddingFraction = bottomPaddingFraction;
        updateOutput();
    }

    private float getUserCaptionFontScale() {
        CaptioningManager captioningManager;
        if (Util.SDK_INT < 19 || isInEditMode() || (captioningManager = (CaptioningManager) getContext().getSystemService("captioning")) == null || !captioningManager.isEnabled()) {
            return 1.0f;
        }
        return captioningManager.getFontScale();
    }

    private CaptionStyleCompat getUserCaptionStyle() {
        if (Util.SDK_INT < 19 || isInEditMode()) {
            return CaptionStyleCompat.DEFAULT;
        }
        CaptioningManager captioningManager = (CaptioningManager) getContext().getSystemService("captioning");
        if (captioningManager != null && captioningManager.isEnabled()) {
            return CaptionStyleCompat.createFromCaptionStyle(captioningManager.getUserStyle());
        }
        return CaptionStyleCompat.DEFAULT;
    }

    private void updateOutput() {
        this.output.update(getCuesWithStylingPreferencesApplied(), this.style, this.defaultTextSize, this.defaultTextSizeType, this.bottomPaddingFraction);
    }

    private List<Cue> getCuesWithStylingPreferencesApplied() {
        if (this.applyEmbeddedStyles && this.applyEmbeddedFontSizes) {
            return this.cues;
        }
        ArrayList arrayList = new ArrayList(this.cues.size());
        for (int i = 0; i < this.cues.size(); i++) {
            arrayList.add(removeEmbeddedStyling(this.cues.get(i)));
        }
        return arrayList;
    }

    private Cue removeEmbeddedStyling(Cue cue) {
        Cue.Builder builderBuildUpon = cue.buildUpon();
        if (!this.applyEmbeddedStyles) {
            SubtitleViewUtils.removeAllEmbeddedStyling(builderBuildUpon);
        } else if (!this.applyEmbeddedFontSizes) {
            SubtitleViewUtils.removeEmbeddedFontSizes(builderBuildUpon);
        }
        return builderBuildUpon.build();
    }
}
