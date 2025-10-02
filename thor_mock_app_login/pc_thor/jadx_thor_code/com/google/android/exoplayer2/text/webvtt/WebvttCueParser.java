package com.google.android.exoplayer2.text.webvtt;

import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan;
import com.google.android.exoplayer2.text.span.RubySpan;
import com.google.android.exoplayer2.text.span.SpanUtil;
import com.google.android.exoplayer2.text.webvtt.WebvttCueParser;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class WebvttCueParser {
    private static final char CHAR_AMPERSAND = '&';
    private static final char CHAR_GREATER_THAN = '>';
    private static final char CHAR_LESS_THAN = '<';
    private static final char CHAR_SEMI_COLON = ';';
    private static final char CHAR_SLASH = '/';
    private static final char CHAR_SPACE = ' ';
    public static final Pattern CUE_HEADER_PATTERN = Pattern.compile("^(\\S+)\\s+-->\\s+(\\S+)(.*)?$");
    private static final Pattern CUE_SETTING_PATTERN = Pattern.compile("(\\S+?):(\\S+)");
    private static final Map<String, Integer> DEFAULT_BACKGROUND_COLORS;
    static final float DEFAULT_POSITION = 0.5f;
    private static final Map<String, Integer> DEFAULT_TEXT_COLORS;
    private static final String ENTITY_AMPERSAND = "amp";
    private static final String ENTITY_GREATER_THAN = "gt";
    private static final String ENTITY_LESS_THAN = "lt";
    private static final String ENTITY_NON_BREAK_SPACE = "nbsp";
    private static final int STYLE_BOLD = 1;
    private static final int STYLE_ITALIC = 2;
    private static final String TAG = "WebvttCueParser";
    private static final String TAG_BOLD = "b";
    private static final String TAG_CLASS = "c";
    private static final String TAG_ITALIC = "i";
    private static final String TAG_LANG = "lang";
    private static final String TAG_RUBY = "ruby";
    private static final String TAG_RUBY_TEXT = "rt";
    private static final String TAG_UNDERLINE = "u";
    private static final String TAG_VOICE = "v";
    private static final int TEXT_ALIGNMENT_CENTER = 2;
    private static final int TEXT_ALIGNMENT_END = 3;
    private static final int TEXT_ALIGNMENT_LEFT = 4;
    private static final int TEXT_ALIGNMENT_RIGHT = 5;
    private static final int TEXT_ALIGNMENT_START = 1;

    static {
        HashMap map = new HashMap();
        map.put("white", Integer.valueOf(Color.rgb(255, 255, 255)));
        map.put("lime", Integer.valueOf(Color.rgb(0, 255, 0)));
        map.put("cyan", Integer.valueOf(Color.rgb(0, 255, 255)));
        map.put("red", Integer.valueOf(Color.rgb(255, 0, 0)));
        map.put("yellow", Integer.valueOf(Color.rgb(255, 255, 0)));
        map.put("magenta", Integer.valueOf(Color.rgb(255, 0, 255)));
        map.put("blue", Integer.valueOf(Color.rgb(0, 0, 255)));
        map.put("black", Integer.valueOf(Color.rgb(0, 0, 0)));
        DEFAULT_TEXT_COLORS = Collections.unmodifiableMap(map);
        HashMap map2 = new HashMap();
        map2.put("bg_white", Integer.valueOf(Color.rgb(255, 255, 255)));
        map2.put("bg_lime", Integer.valueOf(Color.rgb(0, 255, 0)));
        map2.put("bg_cyan", Integer.valueOf(Color.rgb(0, 255, 255)));
        map2.put("bg_red", Integer.valueOf(Color.rgb(255, 0, 0)));
        map2.put("bg_yellow", Integer.valueOf(Color.rgb(255, 255, 0)));
        map2.put("bg_magenta", Integer.valueOf(Color.rgb(255, 0, 255)));
        map2.put("bg_blue", Integer.valueOf(Color.rgb(0, 0, 255)));
        map2.put("bg_black", Integer.valueOf(Color.rgb(0, 0, 0)));
        DEFAULT_BACKGROUND_COLORS = Collections.unmodifiableMap(map2);
    }

    public static WebvttCueInfo parseCue(ParsableByteArray webvttData, List<WebvttCssStyle> styles) {
        String line = webvttData.readLine();
        if (line == null) {
            return null;
        }
        Pattern pattern = CUE_HEADER_PATTERN;
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            return parseCue(null, matcher, webvttData, styles);
        }
        String line2 = webvttData.readLine();
        if (line2 == null) {
            return null;
        }
        Matcher matcher2 = pattern.matcher(line2);
        if (matcher2.matches()) {
            return parseCue(line.trim(), matcher2, webvttData, styles);
        }
        return null;
    }

    static Cue.Builder parseCueSettingsList(String cueSettingsList) {
        WebvttCueInfoBuilder webvttCueInfoBuilder = new WebvttCueInfoBuilder();
        parseCueSettingsList(cueSettingsList, webvttCueInfoBuilder);
        return webvttCueInfoBuilder.toCueBuilder();
    }

    static Cue newCueForText(CharSequence text) {
        WebvttCueInfoBuilder webvttCueInfoBuilder = new WebvttCueInfoBuilder();
        webvttCueInfoBuilder.text = text;
        return webvttCueInfoBuilder.toCueBuilder().build();
    }

    static SpannedString parseCueText(String id, String markup, List<WebvttCssStyle> styles) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        ArrayDeque arrayDeque = new ArrayDeque();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < markup.length()) {
            char cCharAt = markup.charAt(i);
            if (cCharAt == '&') {
                i++;
                int iIndexOf = markup.indexOf(59, i);
                int iIndexOf2 = markup.indexOf(32, i);
                if (iIndexOf == -1) {
                    iIndexOf = iIndexOf2;
                } else if (iIndexOf2 != -1) {
                    iIndexOf = Math.min(iIndexOf, iIndexOf2);
                }
                if (iIndexOf != -1) {
                    applyEntity(markup.substring(i, iIndexOf), spannableStringBuilder);
                    if (iIndexOf == iIndexOf2) {
                        spannableStringBuilder.append((CharSequence) StringUtils.SPACE);
                    }
                    i = iIndexOf + 1;
                } else {
                    spannableStringBuilder.append(cCharAt);
                }
            } else if (cCharAt == '<') {
                int iFindEndOfTag = i + 1;
                if (iFindEndOfTag < markup.length()) {
                    boolean z = markup.charAt(iFindEndOfTag) == '/';
                    iFindEndOfTag = findEndOfTag(markup, iFindEndOfTag);
                    int i2 = iFindEndOfTag - 2;
                    boolean z2 = markup.charAt(i2) == '/';
                    int i3 = i + (z ? 2 : 1);
                    if (!z2) {
                        i2 = iFindEndOfTag - 1;
                    }
                    String strSubstring = markup.substring(i3, i2);
                    if (!strSubstring.trim().isEmpty()) {
                        String tagName = getTagName(strSubstring);
                        if (isSupportedTag(tagName)) {
                            if (z) {
                                while (!arrayDeque.isEmpty()) {
                                    StartTag startTag = (StartTag) arrayDeque.pop();
                                    applySpansForTag(id, startTag, arrayList, spannableStringBuilder, styles);
                                    if (!arrayDeque.isEmpty()) {
                                        arrayList.add(new Element(startTag, spannableStringBuilder.length()));
                                    } else {
                                        arrayList.clear();
                                    }
                                    if (startTag.name.equals(tagName)) {
                                        break;
                                    }
                                }
                            } else if (!z2) {
                                arrayDeque.push(StartTag.buildStartTag(strSubstring, spannableStringBuilder.length()));
                            }
                        }
                    }
                }
                i = iFindEndOfTag;
            } else {
                spannableStringBuilder.append(cCharAt);
                i++;
            }
        }
        while (!arrayDeque.isEmpty()) {
            applySpansForTag(id, (StartTag) arrayDeque.pop(), arrayList, spannableStringBuilder, styles);
        }
        applySpansForTag(id, StartTag.buildWholeCueVirtualTag(), Collections.emptyList(), spannableStringBuilder, styles);
        return SpannedString.valueOf(spannableStringBuilder);
    }

    private static WebvttCueInfo parseCue(String id, Matcher cueHeaderMatcher, ParsableByteArray webvttData, List<WebvttCssStyle> styles) {
        WebvttCueInfoBuilder webvttCueInfoBuilder = new WebvttCueInfoBuilder();
        try {
            webvttCueInfoBuilder.startTimeUs = WebvttParserUtil.parseTimestampUs((String) Assertions.checkNotNull(cueHeaderMatcher.group(1)));
            webvttCueInfoBuilder.endTimeUs = WebvttParserUtil.parseTimestampUs((String) Assertions.checkNotNull(cueHeaderMatcher.group(2)));
            parseCueSettingsList((String) Assertions.checkNotNull(cueHeaderMatcher.group(3)), webvttCueInfoBuilder);
            StringBuilder sb = new StringBuilder();
            String line = webvttData.readLine();
            while (!TextUtils.isEmpty(line)) {
                if (sb.length() > 0) {
                    sb.append(StringUtils.LF);
                }
                sb.append(line.trim());
                line = webvttData.readLine();
            }
            webvttCueInfoBuilder.text = parseCueText(id, sb.toString(), styles);
            return webvttCueInfoBuilder.build();
        } catch (NumberFormatException unused) {
            String strValueOf = String.valueOf(cueHeaderMatcher.group());
            Log.w(TAG, strValueOf.length() != 0 ? "Skipping cue with bad header: ".concat(strValueOf) : new String("Skipping cue with bad header: "));
            return null;
        }
    }

    private static void parseCueSettingsList(String cueSettingsList, WebvttCueInfoBuilder builder) {
        Matcher matcher = CUE_SETTING_PATTERN.matcher(cueSettingsList);
        while (matcher.find()) {
            String str = (String) Assertions.checkNotNull(matcher.group(1));
            String str2 = (String) Assertions.checkNotNull(matcher.group(2));
            try {
                if ("line".equals(str)) {
                    parseLineAttribute(str2, builder);
                } else if ("align".equals(str)) {
                    builder.textAlignment = parseTextAlignment(str2);
                } else if ("position".equals(str)) {
                    parsePositionAttribute(str2, builder);
                } else if ("size".equals(str)) {
                    builder.size = WebvttParserUtil.parsePercentage(str2);
                } else if ("vertical".equals(str)) {
                    builder.verticalType = parseVerticalAttribute(str2);
                } else {
                    Log.w(TAG, new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(str2).length()).append("Unknown cue setting ").append(str).append(":").append(str2).toString());
                }
            } catch (NumberFormatException unused) {
                String strValueOf = String.valueOf(matcher.group());
                Log.w(TAG, strValueOf.length() != 0 ? "Skipping bad cue setting: ".concat(strValueOf) : new String("Skipping bad cue setting: "));
            }
        }
    }

    private static void parseLineAttribute(String s, WebvttCueInfoBuilder builder) {
        int iIndexOf = s.indexOf(44);
        if (iIndexOf != -1) {
            builder.lineAnchor = parseLineAnchor(s.substring(iIndexOf + 1));
            s = s.substring(0, iIndexOf);
        }
        if (s.endsWith("%")) {
            builder.line = WebvttParserUtil.parsePercentage(s);
            builder.lineType = 0;
        } else {
            builder.line = Integer.parseInt(s);
            builder.lineType = 1;
        }
    }

    private static int parseLineAnchor(String s) {
        s.hashCode();
        switch (s) {
            case "center":
            case "middle":
                return 1;
            case "end":
                return 2;
            case "start":
                return 0;
            default:
                String strValueOf = String.valueOf(s);
                Log.w(TAG, strValueOf.length() != 0 ? "Invalid anchor value: ".concat(strValueOf) : new String("Invalid anchor value: "));
                return Integer.MIN_VALUE;
        }
    }

    private static void parsePositionAttribute(String s, WebvttCueInfoBuilder builder) {
        int iIndexOf = s.indexOf(44);
        if (iIndexOf != -1) {
            builder.positionAnchor = parsePositionAnchor(s.substring(iIndexOf + 1));
            s = s.substring(0, iIndexOf);
        }
        builder.position = WebvttParserUtil.parsePercentage(s);
    }

    private static int parsePositionAnchor(String s) {
        s.hashCode();
        switch (s) {
            case "line-left":
            case "start":
                return 0;
            case "center":
            case "middle":
                return 1;
            case "line-right":
            case "end":
                return 2;
            default:
                String strValueOf = String.valueOf(s);
                Log.w(TAG, strValueOf.length() != 0 ? "Invalid anchor value: ".concat(strValueOf) : new String("Invalid anchor value: "));
                return Integer.MIN_VALUE;
        }
    }

    private static int parseVerticalAttribute(String s) {
        s.hashCode();
        if (s.equals("lr")) {
            return 2;
        }
        if (s.equals("rl")) {
            return 1;
        }
        String strValueOf = String.valueOf(s);
        Log.w(TAG, strValueOf.length() != 0 ? "Invalid 'vertical' value: ".concat(strValueOf) : new String("Invalid 'vertical' value: "));
        return Integer.MIN_VALUE;
    }

    private static int parseTextAlignment(String s) {
        s.hashCode();
        switch (s) {
            case "center":
            case "middle":
                return 2;
            case "end":
                return 3;
            case "left":
                return 4;
            case "right":
                return 5;
            case "start":
                return 1;
            default:
                String strValueOf = String.valueOf(s);
                Log.w(TAG, strValueOf.length() != 0 ? "Invalid alignment value: ".concat(strValueOf) : new String("Invalid alignment value: "));
                return 2;
        }
    }

    private static int findEndOfTag(String markup, int startPos) {
        int iIndexOf = markup.indexOf(62, startPos);
        return iIndexOf == -1 ? markup.length() : iIndexOf + 1;
    }

    private static void applyEntity(String entity, SpannableStringBuilder spannedText) {
        entity.hashCode();
        switch (entity) {
            case "gt":
                spannedText.append('>');
                break;
            case "lt":
                spannedText.append('<');
                break;
            case "amp":
                spannedText.append('&');
                break;
            case "nbsp":
                spannedText.append(CHAR_SPACE);
                break;
            default:
                Log.w(TAG, new StringBuilder(String.valueOf(entity).length() + 33).append("ignoring unsupported entity: '&").append(entity).append(";'").toString());
                break;
        }
    }

    private static boolean isSupportedTag(String tagName) {
        tagName.hashCode();
        switch (tagName) {
            case "b":
            case "c":
            case "i":
            case "u":
            case "v":
            case "rt":
            case "lang":
            case "ruby":
                return true;
            default:
                return false;
        }
    }

    private static void applySpansForTag(String cueId, StartTag startTag, List<Element> nestedElements, SpannableStringBuilder text, List<WebvttCssStyle> styles) {
        int i;
        int length;
        i = startTag.position;
        length = text.length();
        String str = startTag.name;
        str.hashCode();
        switch (str) {
            case "":
            case "v":
            case "lang":
                break;
            case "b":
                text.setSpan(new StyleSpan(1), i, length, 33);
                break;
            case "c":
                applyDefaultColors(text, startTag.classes, i, length);
                break;
            case "i":
                text.setSpan(new StyleSpan(2), i, length, 33);
                break;
            case "u":
                text.setSpan(new UnderlineSpan(), i, length, 33);
                break;
            case "ruby":
                applyRubySpans(text, cueId, startTag, nestedElements, styles);
                break;
            default:
                return;
        }
        List<StyleMatch> applicableStyles = getApplicableStyles(styles, cueId, startTag);
        for (int i2 = 0; i2 < applicableStyles.size(); i2++) {
            applyStyleToText(text, applicableStyles.get(i2).style, i, length);
        }
    }

    private static void applyRubySpans(SpannableStringBuilder text, String cueId, StartTag startTag, List<Element> nestedElements, List<WebvttCssStyle> styles) {
        int rubyPosition = getRubyPosition(styles, cueId, startTag);
        ArrayList arrayList = new ArrayList(nestedElements.size());
        arrayList.addAll(nestedElements);
        Collections.sort(arrayList, Element.BY_START_POSITION_ASC);
        int i = startTag.position;
        int length = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (TAG_RUBY_TEXT.equals(((Element) arrayList.get(i2)).startTag.name)) {
                Element element = (Element) arrayList.get(i2);
                int iFirstKnownRubyPosition = firstKnownRubyPosition(getRubyPosition(styles, cueId, element.startTag), rubyPosition, 1);
                int i3 = element.startTag.position - length;
                int i4 = element.endPosition - length;
                CharSequence charSequenceSubSequence = text.subSequence(i3, i4);
                text.delete(i3, i4);
                text.setSpan(new RubySpan(charSequenceSubSequence.toString(), iFirstKnownRubyPosition), i, i3, 33);
                length += charSequenceSubSequence.length();
                i = i3;
            }
        }
    }

    private static int getRubyPosition(List<WebvttCssStyle> styles, String cueId, StartTag startTag) {
        List<StyleMatch> applicableStyles = getApplicableStyles(styles, cueId, startTag);
        for (int i = 0; i < applicableStyles.size(); i++) {
            WebvttCssStyle webvttCssStyle = applicableStyles.get(i).style;
            if (webvttCssStyle.getRubyPosition() != -1) {
                return webvttCssStyle.getRubyPosition();
            }
        }
        return -1;
    }

    private static int firstKnownRubyPosition(int position1, int position2, int position3) {
        if (position1 != -1) {
            return position1;
        }
        if (position2 != -1) {
            return position2;
        }
        if (position3 != -1) {
            return position3;
        }
        throw new IllegalArgumentException();
    }

    private static void applyDefaultColors(SpannableStringBuilder text, Set<String> classes, int start, int end) {
        for (String str : classes) {
            Map<String, Integer> map = DEFAULT_TEXT_COLORS;
            if (map.containsKey(str)) {
                text.setSpan(new ForegroundColorSpan(map.get(str).intValue()), start, end, 33);
            } else {
                Map<String, Integer> map2 = DEFAULT_BACKGROUND_COLORS;
                if (map2.containsKey(str)) {
                    text.setSpan(new BackgroundColorSpan(map2.get(str).intValue()), start, end, 33);
                }
            }
        }
    }

    private static void applyStyleToText(SpannableStringBuilder spannedText, WebvttCssStyle style, int start, int end) {
        if (style == null) {
            return;
        }
        if (style.getStyle() != -1) {
            SpanUtil.addOrReplaceSpan(spannedText, new StyleSpan(style.getStyle()), start, end, 33);
        }
        if (style.isLinethrough()) {
            spannedText.setSpan(new StrikethroughSpan(), start, end, 33);
        }
        if (style.isUnderline()) {
            spannedText.setSpan(new UnderlineSpan(), start, end, 33);
        }
        if (style.hasFontColor()) {
            SpanUtil.addOrReplaceSpan(spannedText, new ForegroundColorSpan(style.getFontColor()), start, end, 33);
        }
        if (style.hasBackgroundColor()) {
            SpanUtil.addOrReplaceSpan(spannedText, new BackgroundColorSpan(style.getBackgroundColor()), start, end, 33);
        }
        if (style.getFontFamily() != null) {
            SpanUtil.addOrReplaceSpan(spannedText, new TypefaceSpan(style.getFontFamily()), start, end, 33);
        }
        int fontSizeUnit = style.getFontSizeUnit();
        if (fontSizeUnit == 1) {
            SpanUtil.addOrReplaceSpan(spannedText, new AbsoluteSizeSpan((int) style.getFontSize(), true), start, end, 33);
        } else if (fontSizeUnit == 2) {
            SpanUtil.addOrReplaceSpan(spannedText, new RelativeSizeSpan(style.getFontSize()), start, end, 33);
        } else if (fontSizeUnit == 3) {
            SpanUtil.addOrReplaceSpan(spannedText, new RelativeSizeSpan(style.getFontSize() / 100.0f), start, end, 33);
        }
        if (style.getCombineUpright()) {
            spannedText.setSpan(new HorizontalTextInVerticalContextSpan(), start, end, 33);
        }
    }

    private static String getTagName(String tagExpression) {
        String strTrim = tagExpression.trim();
        Assertions.checkArgument(!strTrim.isEmpty());
        return Util.splitAtFirst(strTrim, "[ \\.]")[0];
    }

    private static List<StyleMatch> getApplicableStyles(List<WebvttCssStyle> declaredStyles, String id, StartTag tag) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < declaredStyles.size(); i++) {
            WebvttCssStyle webvttCssStyle = declaredStyles.get(i);
            int specificityScore = webvttCssStyle.getSpecificityScore(id, tag.name, tag.classes, tag.voice);
            if (specificityScore > 0) {
                arrayList.add(new StyleMatch(specificityScore, webvttCssStyle));
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static final class WebvttCueInfoBuilder {
        public CharSequence text;
        public long startTimeUs = 0;
        public long endTimeUs = 0;
        public int textAlignment = 2;
        public float line = -3.4028235E38f;
        public int lineType = 1;
        public int lineAnchor = 0;
        public float position = -3.4028235E38f;
        public int positionAnchor = Integer.MIN_VALUE;
        public float size = 1.0f;
        public int verticalType = Integer.MIN_VALUE;

        private static float computeLine(float line, int lineType) {
            if (line == -3.4028235E38f || lineType != 0 || (line >= 0.0f && line <= 1.0f)) {
                return line != -3.4028235E38f ? line : lineType == 0 ? 1.0f : -3.4028235E38f;
            }
            return 1.0f;
        }

        private static float derivePosition(int textAlignment) {
            if (textAlignment != 4) {
                return textAlignment != 5 ? 0.5f : 1.0f;
            }
            return 0.0f;
        }

        private static int derivePositionAnchor(int textAlignment) {
            if (textAlignment == 1) {
                return 0;
            }
            if (textAlignment == 3) {
                return 2;
            }
            if (textAlignment != 4) {
                return textAlignment != 5 ? 1 : 2;
            }
            return 0;
        }

        public WebvttCueInfo build() {
            return new WebvttCueInfo(toCueBuilder().build(), this.startTimeUs, this.endTimeUs);
        }

        public Cue.Builder toCueBuilder() {
            float fDerivePosition = this.position;
            if (fDerivePosition == -3.4028235E38f) {
                fDerivePosition = derivePosition(this.textAlignment);
            }
            int iDerivePositionAnchor = this.positionAnchor;
            if (iDerivePositionAnchor == Integer.MIN_VALUE) {
                iDerivePositionAnchor = derivePositionAnchor(this.textAlignment);
            }
            Cue.Builder verticalType = new Cue.Builder().setTextAlignment(convertTextAlignment(this.textAlignment)).setLine(computeLine(this.line, this.lineType), this.lineType).setLineAnchor(this.lineAnchor).setPosition(fDerivePosition).setPositionAnchor(iDerivePositionAnchor).setSize(Math.min(this.size, deriveMaxSize(iDerivePositionAnchor, fDerivePosition))).setVerticalType(this.verticalType);
            CharSequence charSequence = this.text;
            if (charSequence != null) {
                verticalType.setText(charSequence);
            }
            return verticalType;
        }

        private static Layout.Alignment convertTextAlignment(int textAlignment) {
            if (textAlignment != 1) {
                if (textAlignment == 2) {
                    return Layout.Alignment.ALIGN_CENTER;
                }
                if (textAlignment != 3) {
                    if (textAlignment != 4) {
                        if (textAlignment != 5) {
                            Log.w(WebvttCueParser.TAG, new StringBuilder(34).append("Unknown textAlignment: ").append(textAlignment).toString());
                            return null;
                        }
                    }
                }
                return Layout.Alignment.ALIGN_OPPOSITE;
            }
            return Layout.Alignment.ALIGN_NORMAL;
        }

        private static float deriveMaxSize(int positionAnchor, float position) {
            if (positionAnchor == 0) {
                return 1.0f - position;
            }
            if (positionAnchor == 1) {
                return position <= 0.5f ? position * 2.0f : (1.0f - position) * 2.0f;
            }
            if (positionAnchor == 2) {
                return position;
            }
            throw new IllegalStateException(String.valueOf(positionAnchor));
        }
    }

    private static final class StyleMatch implements Comparable<StyleMatch> {
        public final int score;
        public final WebvttCssStyle style;

        public StyleMatch(int score, WebvttCssStyle style) {
            this.score = score;
            this.style = style;
        }

        @Override // java.lang.Comparable
        public int compareTo(StyleMatch another) {
            return Integer.compare(this.score, another.score);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class StartTag {
        public final Set<String> classes;
        public final String name;
        public final int position;
        public final String voice;

        private StartTag(String name, int position, String voice, Set<String> classes) {
            this.position = position;
            this.name = name;
            this.voice = voice;
            this.classes = classes;
        }

        public static StartTag buildStartTag(String fullTagExpression, int position) {
            String str;
            String strTrim = fullTagExpression.trim();
            Assertions.checkArgument(!strTrim.isEmpty());
            int iIndexOf = strTrim.indexOf(StringUtils.SPACE);
            if (iIndexOf == -1) {
                str = "";
            } else {
                String strTrim2 = strTrim.substring(iIndexOf).trim();
                strTrim = strTrim.substring(0, iIndexOf);
                str = strTrim2;
            }
            String[] strArrSplit = Util.split(strTrim, "\\.");
            String str2 = strArrSplit[0];
            HashSet hashSet = new HashSet();
            for (int i = 1; i < strArrSplit.length; i++) {
                hashSet.add(strArrSplit[i]);
            }
            return new StartTag(str2, position, str, hashSet);
        }

        public static StartTag buildWholeCueVirtualTag() {
            return new StartTag("", 0, "", Collections.emptySet());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Element {
        private static final Comparator<Element> BY_START_POSITION_ASC = new Comparator() { // from class: com.google.android.exoplayer2.text.webvtt.WebvttCueParser$Element$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Integer.compare(((WebvttCueParser.Element) obj).startTag.position, ((WebvttCueParser.Element) obj2).startTag.position);
            }
        };
        private final int endPosition;
        private final StartTag startTag;

        private Element(StartTag startTag, int endPosition) {
            this.startTag = startTag;
            this.endPosition = endPosition;
        }
    }
}
