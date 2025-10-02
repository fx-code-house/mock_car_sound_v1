package com.google.android.exoplayer2.ui;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.span.LanguageFeatureSpan;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Predicate;

/* loaded from: classes.dex */
final class SubtitleViewUtils {
    public static float resolveTextSize(int textSizeType, float textSize, int rawViewHeight, int viewHeightMinusPadding) {
        float f;
        if (textSize == -3.4028235E38f) {
            return -3.4028235E38f;
        }
        if (textSizeType == 0) {
            f = viewHeightMinusPadding;
        } else {
            if (textSizeType != 1) {
                if (textSizeType != 2) {
                    return -3.4028235E38f;
                }
                return textSize;
            }
            f = rawViewHeight;
        }
        return textSize * f;
    }

    public static void removeAllEmbeddedStyling(Cue.Builder cue) {
        cue.clearWindowColor();
        if (cue.getText() instanceof Spanned) {
            if (!(cue.getText() instanceof Spannable)) {
                cue.setText(SpannableString.valueOf(cue.getText()));
            }
            removeSpansIf((Spannable) Assertions.checkNotNull(cue.getText()), new Predicate() { // from class: com.google.android.exoplayer2.ui.SubtitleViewUtils$$ExternalSyntheticLambda1
                @Override // com.google.common.base.Predicate
                public final boolean apply(Object obj) {
                    return SubtitleViewUtils.lambda$removeAllEmbeddedStyling$0(obj);
                }
            });
        }
        removeEmbeddedFontSizes(cue);
    }

    static /* synthetic */ boolean lambda$removeAllEmbeddedStyling$0(Object obj) {
        return !(obj instanceof LanguageFeatureSpan);
    }

    public static void removeEmbeddedFontSizes(Cue.Builder cue) {
        cue.setTextSize(-3.4028235E38f, Integer.MIN_VALUE);
        if (cue.getText() instanceof Spanned) {
            if (!(cue.getText() instanceof Spannable)) {
                cue.setText(SpannableString.valueOf(cue.getText()));
            }
            removeSpansIf((Spannable) Assertions.checkNotNull(cue.getText()), new Predicate() { // from class: com.google.android.exoplayer2.ui.SubtitleViewUtils$$ExternalSyntheticLambda0
                @Override // com.google.common.base.Predicate
                public final boolean apply(Object obj) {
                    return SubtitleViewUtils.lambda$removeEmbeddedFontSizes$1(obj);
                }
            });
        }
    }

    static /* synthetic */ boolean lambda$removeEmbeddedFontSizes$1(Object obj) {
        return (obj instanceof AbsoluteSizeSpan) || (obj instanceof RelativeSizeSpan);
    }

    private static void removeSpansIf(Spannable spannable, Predicate<Object> removeFilter) {
        for (Object obj : spannable.getSpans(0, spannable.length(), Object.class)) {
            if (removeFilter.apply(obj)) {
                spannable.removeSpan(obj);
            }
        }
    }

    private SubtitleViewUtils() {
    }
}
