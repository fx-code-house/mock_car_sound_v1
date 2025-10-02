package com.google.android.exoplayer2.ui;

import android.text.Html;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;
import com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan;
import com.google.android.exoplayer2.text.span.RubySpan;
import com.google.android.exoplayer2.text.span.TextEmphasisSpan;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.exoplayer2.ui.SpannedToHtmlConverter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
final class SpannedToHtmlConverter {
    private static final Pattern NEWLINE_PATTERN = Pattern.compile("(&#13;)?&#10;");

    private static String getTextEmphasisPosition(int position) {
        return position != 2 ? "over right" : "under left";
    }

    private SpannedToHtmlConverter() {
    }

    public static HtmlAndCss convert(CharSequence text, float displayDensity) {
        if (text == null) {
            return new HtmlAndCss("", ImmutableMap.of());
        }
        if (!(text instanceof Spanned)) {
            return new HtmlAndCss(escapeHtml(text), ImmutableMap.of());
        }
        Spanned spanned = (Spanned) text;
        HashSet hashSet = new HashSet();
        int i = 0;
        for (BackgroundColorSpan backgroundColorSpan : (BackgroundColorSpan[]) spanned.getSpans(0, spanned.length(), BackgroundColorSpan.class)) {
            hashSet.add(Integer.valueOf(backgroundColorSpan.getBackgroundColor()));
        }
        HashMap map = new HashMap();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            map.put(HtmlUtils.cssAllClassDescendantsSelector(new StringBuilder(14).append("bg_").append(iIntValue).toString()), Util.formatInvariant("background-color:%s;", HtmlUtils.toCssRgba(iIntValue)));
        }
        SparseArray<Transition> sparseArrayFindSpanTransitions = findSpanTransitions(spanned, displayDensity);
        StringBuilder sb = new StringBuilder(spanned.length());
        int i2 = 0;
        while (i < sparseArrayFindSpanTransitions.size()) {
            int iKeyAt = sparseArrayFindSpanTransitions.keyAt(i);
            sb.append(escapeHtml(spanned.subSequence(i2, iKeyAt)));
            Transition transition = sparseArrayFindSpanTransitions.get(iKeyAt);
            Collections.sort(transition.spansRemoved, SpanInfo.FOR_CLOSING_TAGS);
            Iterator it2 = transition.spansRemoved.iterator();
            while (it2.hasNext()) {
                sb.append(((SpanInfo) it2.next()).closingTag);
            }
            Collections.sort(transition.spansAdded, SpanInfo.FOR_OPENING_TAGS);
            Iterator it3 = transition.spansAdded.iterator();
            while (it3.hasNext()) {
                sb.append(((SpanInfo) it3.next()).openingTag);
            }
            i++;
            i2 = iKeyAt;
        }
        sb.append(escapeHtml(spanned.subSequence(i2, spanned.length())));
        return new HtmlAndCss(sb.toString(), map);
    }

    private static SparseArray<Transition> findSpanTransitions(Spanned spanned, float displayDensity) {
        SparseArray<Transition> sparseArray = new SparseArray<>();
        for (Object obj : spanned.getSpans(0, spanned.length(), Object.class)) {
            String openingTag = getOpeningTag(obj, displayDensity);
            String closingTag = getClosingTag(obj);
            int spanStart = spanned.getSpanStart(obj);
            int spanEnd = spanned.getSpanEnd(obj);
            if (openingTag != null) {
                Assertions.checkNotNull(closingTag);
                SpanInfo spanInfo = new SpanInfo(spanStart, spanEnd, openingTag, closingTag);
                getOrCreate(sparseArray, spanStart).spansAdded.add(spanInfo);
                getOrCreate(sparseArray, spanEnd).spansRemoved.add(spanInfo);
            }
        }
        return sparseArray;
    }

    private static String getOpeningTag(Object span, float displayDensity) {
        float size;
        if (span instanceof StrikethroughSpan) {
            return "<span style='text-decoration:line-through;'>";
        }
        if (span instanceof ForegroundColorSpan) {
            return Util.formatInvariant("<span style='color:%s;'>", HtmlUtils.toCssRgba(((ForegroundColorSpan) span).getForegroundColor()));
        }
        if (span instanceof BackgroundColorSpan) {
            return Util.formatInvariant("<span class='bg_%s'>", Integer.valueOf(((BackgroundColorSpan) span).getBackgroundColor()));
        }
        if (span instanceof HorizontalTextInVerticalContextSpan) {
            return "<span style='text-combine-upright:all;'>";
        }
        if (span instanceof AbsoluteSizeSpan) {
            AbsoluteSizeSpan absoluteSizeSpan = (AbsoluteSizeSpan) span;
            if (absoluteSizeSpan.getDip()) {
                size = absoluteSizeSpan.getSize();
            } else {
                size = absoluteSizeSpan.getSize() / displayDensity;
            }
            return Util.formatInvariant("<span style='font-size:%.2fpx;'>", Float.valueOf(size));
        }
        if (span instanceof RelativeSizeSpan) {
            return Util.formatInvariant("<span style='font-size:%.2f%%;'>", Float.valueOf(((RelativeSizeSpan) span).getSizeChange() * 100.0f));
        }
        if (span instanceof TypefaceSpan) {
            String family = ((TypefaceSpan) span).getFamily();
            if (family != null) {
                return Util.formatInvariant("<span style='font-family:\"%s\";'>", family);
            }
            return null;
        }
        if (span instanceof StyleSpan) {
            int style = ((StyleSpan) span).getStyle();
            if (style == 1) {
                return "<b>";
            }
            if (style == 2) {
                return "<i>";
            }
            if (style != 3) {
                return null;
            }
            return "<b><i>";
        }
        if (span instanceof RubySpan) {
            int i = ((RubySpan) span).position;
            if (i == -1) {
                return "<ruby style='ruby-position:unset;'>";
            }
            if (i == 1) {
                return "<ruby style='ruby-position:over;'>";
            }
            if (i != 2) {
                return null;
            }
            return "<ruby style='ruby-position:under;'>";
        }
        if (span instanceof UnderlineSpan) {
            return "<u>";
        }
        if (!(span instanceof TextEmphasisSpan)) {
            return null;
        }
        TextEmphasisSpan textEmphasisSpan = (TextEmphasisSpan) span;
        return Util.formatInvariant("<span style='-webkit-text-emphasis-style:%1$s;text-emphasis-style:%1$s;-webkit-text-emphasis-position:%2$s;text-emphasis-position:%2$s;display:inline-block;'>", getTextEmphasisStyle(textEmphasisSpan.markShape, textEmphasisSpan.markFill), getTextEmphasisPosition(textEmphasisSpan.position));
    }

    private static String getClosingTag(Object span) {
        if ((span instanceof StrikethroughSpan) || (span instanceof ForegroundColorSpan) || (span instanceof BackgroundColorSpan) || (span instanceof HorizontalTextInVerticalContextSpan) || (span instanceof AbsoluteSizeSpan) || (span instanceof RelativeSizeSpan) || (span instanceof TextEmphasisSpan)) {
            return "</span>";
        }
        if (span instanceof TypefaceSpan) {
            if (((TypefaceSpan) span).getFamily() != null) {
                return "</span>";
            }
            return null;
        }
        if (span instanceof StyleSpan) {
            int style = ((StyleSpan) span).getStyle();
            if (style == 1) {
                return "</b>";
            }
            if (style == 2) {
                return "</i>";
            }
            if (style == 3) {
                return "</i></b>";
            }
        } else {
            if (span instanceof RubySpan) {
                String strEscapeHtml = escapeHtml(((RubySpan) span).rubyText);
                return new StringBuilder(String.valueOf(strEscapeHtml).length() + 16).append("<rt>").append(strEscapeHtml).append("</rt></ruby>").toString();
            }
            if (span instanceof UnderlineSpan) {
                return "</u>";
            }
        }
        return null;
    }

    private static String getTextEmphasisStyle(int shape, int fill) {
        StringBuilder sb = new StringBuilder();
        if (fill == 1) {
            sb.append("filled ");
        } else if (fill == 2) {
            sb.append("open ");
        }
        if (shape == 0) {
            sb.append("none");
        } else if (shape == 1) {
            sb.append(TtmlNode.TEXT_EMPHASIS_MARK_CIRCLE);
        } else if (shape == 2) {
            sb.append(TtmlNode.TEXT_EMPHASIS_MARK_DOT);
        } else if (shape == 3) {
            sb.append(TtmlNode.TEXT_EMPHASIS_MARK_SESAME);
        } else {
            sb.append("unset");
        }
        return sb.toString();
    }

    private static Transition getOrCreate(SparseArray<Transition> transitions, int key) {
        Transition transition = transitions.get(key);
        if (transition != null) {
            return transition;
        }
        Transition transition2 = new Transition();
        transitions.put(key, transition2);
        return transition2;
    }

    private static String escapeHtml(CharSequence text) {
        return NEWLINE_PATTERN.matcher(Html.escapeHtml(text)).replaceAll("<br>");
    }

    public static class HtmlAndCss {
        public final Map<String, String> cssRuleSets;
        public final String html;

        private HtmlAndCss(String html, Map<String, String> cssRuleSets) {
            this.html = html;
            this.cssRuleSets = cssRuleSets;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SpanInfo {
        public final String closingTag;
        public final int end;
        public final String openingTag;
        public final int start;
        private static final Comparator<SpanInfo> FOR_OPENING_TAGS = new Comparator() { // from class: com.google.android.exoplayer2.ui.SpannedToHtmlConverter$SpanInfo$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return SpannedToHtmlConverter.SpanInfo.lambda$static$0((SpannedToHtmlConverter.SpanInfo) obj, (SpannedToHtmlConverter.SpanInfo) obj2);
            }
        };
        private static final Comparator<SpanInfo> FOR_CLOSING_TAGS = new Comparator() { // from class: com.google.android.exoplayer2.ui.SpannedToHtmlConverter$SpanInfo$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return SpannedToHtmlConverter.SpanInfo.lambda$static$1((SpannedToHtmlConverter.SpanInfo) obj, (SpannedToHtmlConverter.SpanInfo) obj2);
            }
        };

        static /* synthetic */ int lambda$static$0(SpanInfo spanInfo, SpanInfo spanInfo2) {
            int iCompare = Integer.compare(spanInfo2.end, spanInfo.end);
            if (iCompare != 0) {
                return iCompare;
            }
            int iCompareTo = spanInfo.openingTag.compareTo(spanInfo2.openingTag);
            return iCompareTo != 0 ? iCompareTo : spanInfo.closingTag.compareTo(spanInfo2.closingTag);
        }

        static /* synthetic */ int lambda$static$1(SpanInfo spanInfo, SpanInfo spanInfo2) {
            int iCompare = Integer.compare(spanInfo2.start, spanInfo.start);
            if (iCompare != 0) {
                return iCompare;
            }
            int iCompareTo = spanInfo2.openingTag.compareTo(spanInfo.openingTag);
            return iCompareTo != 0 ? iCompareTo : spanInfo2.closingTag.compareTo(spanInfo.closingTag);
        }

        private SpanInfo(int start, int end, String openingTag, String closingTag) {
            this.start = start;
            this.end = end;
            this.openingTag = openingTag;
            this.closingTag = closingTag;
        }
    }

    private static final class Transition {
        private final List<SpanInfo> spansAdded = new ArrayList();
        private final List<SpanInfo> spansRemoved = new ArrayList();
    }
}
