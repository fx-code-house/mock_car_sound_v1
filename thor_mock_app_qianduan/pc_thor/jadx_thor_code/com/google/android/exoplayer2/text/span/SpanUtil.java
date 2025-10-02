package com.google.android.exoplayer2.text.span;

import android.text.Spannable;

/* loaded from: classes.dex */
public final class SpanUtil {
    public static void addOrReplaceSpan(Spannable spannable, Object span, int start, int end, int spanFlags) {
        for (Object obj : spannable.getSpans(start, end, span.getClass())) {
            if (spannable.getSpanStart(obj) == start && spannable.getSpanEnd(obj) == end && spannable.getSpanFlags(obj) == spanFlags) {
                spannable.removeSpan(obj);
            }
        }
        spannable.setSpan(span, start, end, spanFlags);
    }

    private SpanUtil() {
    }
}
