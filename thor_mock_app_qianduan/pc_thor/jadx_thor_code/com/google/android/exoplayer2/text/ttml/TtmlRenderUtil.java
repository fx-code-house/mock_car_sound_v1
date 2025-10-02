package com.google.android.exoplayer2.text.ttml;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan;
import com.google.android.exoplayer2.text.span.RubySpan;
import com.google.android.exoplayer2.text.span.SpanUtil;
import com.google.android.exoplayer2.text.span.TextEmphasisSpan;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
final class TtmlRenderUtil {
    private static final String TAG = "TtmlRenderUtil";

    public static TtmlStyle resolveStyle(TtmlStyle style, String[] styleIds, Map<String, TtmlStyle> globalStyles) {
        int i = 0;
        if (style == null) {
            if (styleIds == null) {
                return null;
            }
            if (styleIds.length == 1) {
                return globalStyles.get(styleIds[0]);
            }
            if (styleIds.length > 1) {
                TtmlStyle ttmlStyle = new TtmlStyle();
                int length = styleIds.length;
                while (i < length) {
                    ttmlStyle.chain(globalStyles.get(styleIds[i]));
                    i++;
                }
                return ttmlStyle;
            }
        } else {
            if (styleIds != null && styleIds.length == 1) {
                return style.chain(globalStyles.get(styleIds[0]));
            }
            if (styleIds != null && styleIds.length > 1) {
                int length2 = styleIds.length;
                while (i < length2) {
                    style.chain(globalStyles.get(styleIds[i]));
                    i++;
                }
            }
        }
        return style;
    }

    public static void applyStylesToSpan(Spannable builder, int start, int end, TtmlStyle style, TtmlNode parent, Map<String, TtmlStyle> globalStyles, int verticalType) {
        TtmlNode ttmlNodeFindRubyTextNode;
        TtmlStyle ttmlStyleResolveStyle;
        int i;
        int i2;
        if (style.getStyle() != -1) {
            builder.setSpan(new StyleSpan(style.getStyle()), start, end, 33);
        }
        if (style.isLinethrough()) {
            builder.setSpan(new StrikethroughSpan(), start, end, 33);
        }
        if (style.isUnderline()) {
            builder.setSpan(new UnderlineSpan(), start, end, 33);
        }
        if (style.hasFontColor()) {
            SpanUtil.addOrReplaceSpan(builder, new ForegroundColorSpan(style.getFontColor()), start, end, 33);
        }
        if (style.hasBackgroundColor()) {
            SpanUtil.addOrReplaceSpan(builder, new BackgroundColorSpan(style.getBackgroundColor()), start, end, 33);
        }
        if (style.getFontFamily() != null) {
            SpanUtil.addOrReplaceSpan(builder, new TypefaceSpan(style.getFontFamily()), start, end, 33);
        }
        if (style.getTextEmphasis() != null) {
            TextEmphasis textEmphasis = (TextEmphasis) Assertions.checkNotNull(style.getTextEmphasis());
            if (textEmphasis.markShape == -1) {
                i = (verticalType == 2 || verticalType == 1) ? 3 : 1;
                i2 = 1;
            } else {
                i = textEmphasis.markShape;
                i2 = textEmphasis.markFill;
            }
            SpanUtil.addOrReplaceSpan(builder, new TextEmphasisSpan(i, i2, textEmphasis.position == -2 ? 1 : textEmphasis.position), start, end, 33);
        }
        int rubyType = style.getRubyType();
        if (rubyType == 2) {
            TtmlNode ttmlNodeFindRubyContainerNode = findRubyContainerNode(parent, globalStyles);
            if (ttmlNodeFindRubyContainerNode != null && (ttmlNodeFindRubyTextNode = findRubyTextNode(ttmlNodeFindRubyContainerNode, globalStyles)) != null) {
                if (ttmlNodeFindRubyTextNode.getChildCount() == 1 && ttmlNodeFindRubyTextNode.getChild(0).text != null) {
                    String str = (String) Util.castNonNull(ttmlNodeFindRubyTextNode.getChild(0).text);
                    TtmlStyle ttmlStyleResolveStyle2 = resolveStyle(ttmlNodeFindRubyTextNode.style, ttmlNodeFindRubyTextNode.getStyleIds(), globalStyles);
                    int rubyPosition = ttmlStyleResolveStyle2 != null ? ttmlStyleResolveStyle2.getRubyPosition() : -1;
                    if (rubyPosition == -1 && (ttmlStyleResolveStyle = resolveStyle(ttmlNodeFindRubyContainerNode.style, ttmlNodeFindRubyContainerNode.getStyleIds(), globalStyles)) != null) {
                        rubyPosition = ttmlStyleResolveStyle.getRubyPosition();
                    }
                    builder.setSpan(new RubySpan(str, rubyPosition), start, end, 33);
                } else {
                    Log.i(TAG, "Skipping rubyText node without exactly one text child.");
                }
            }
        } else if (rubyType == 3 || rubyType == 4) {
            builder.setSpan(new DeleteTextSpan(), start, end, 33);
        }
        if (style.getTextCombine()) {
            SpanUtil.addOrReplaceSpan(builder, new HorizontalTextInVerticalContextSpan(), start, end, 33);
        }
        int fontSizeUnit = style.getFontSizeUnit();
        if (fontSizeUnit == 1) {
            SpanUtil.addOrReplaceSpan(builder, new AbsoluteSizeSpan((int) style.getFontSize(), true), start, end, 33);
        } else if (fontSizeUnit == 2) {
            SpanUtil.addOrReplaceSpan(builder, new RelativeSizeSpan(style.getFontSize()), start, end, 33);
        } else {
            if (fontSizeUnit != 3) {
                return;
            }
            SpanUtil.addOrReplaceSpan(builder, new RelativeSizeSpan(style.getFontSize() / 100.0f), start, end, 33);
        }
    }

    private static TtmlNode findRubyTextNode(TtmlNode rubyContainerNode, Map<String, TtmlStyle> globalStyles) {
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(rubyContainerNode);
        while (!arrayDeque.isEmpty()) {
            TtmlNode ttmlNode = (TtmlNode) arrayDeque.pop();
            TtmlStyle ttmlStyleResolveStyle = resolveStyle(ttmlNode.style, ttmlNode.getStyleIds(), globalStyles);
            if (ttmlStyleResolveStyle != null && ttmlStyleResolveStyle.getRubyType() == 3) {
                return ttmlNode;
            }
            for (int childCount = ttmlNode.getChildCount() - 1; childCount >= 0; childCount--) {
                arrayDeque.push(ttmlNode.getChild(childCount));
            }
        }
        return null;
    }

    private static TtmlNode findRubyContainerNode(TtmlNode node, Map<String, TtmlStyle> globalStyles) {
        while (node != null) {
            TtmlStyle ttmlStyleResolveStyle = resolveStyle(node.style, node.getStyleIds(), globalStyles);
            if (ttmlStyleResolveStyle != null && ttmlStyleResolveStyle.getRubyType() == 1) {
                return node;
            }
            node = node.parent;
        }
        return null;
    }

    static void endParagraph(SpannableStringBuilder builder) {
        int length = builder.length() - 1;
        while (length >= 0 && builder.charAt(length) == ' ') {
            length--;
        }
        if (length < 0 || builder.charAt(length) == '\n') {
            return;
        }
        builder.append('\n');
    }

    static String applyTextElementSpacePolicy(String in) {
        return in.replaceAll("\r\n", StringUtils.LF).replaceAll(" *\n *", StringUtils.LF).replaceAll(StringUtils.LF, StringUtils.SPACE).replaceAll("[ \t\\x0B\f\r]+", StringUtils.SPACE);
    }

    private TtmlRenderUtil() {
    }
}
