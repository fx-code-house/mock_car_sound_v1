package com.google.android.exoplayer2.text.ttml;

import android.text.Layout;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.common.base.Ascii;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes.dex */
public final class TtmlDecoder extends SimpleSubtitleDecoder {
    private static final String ATTR_BEGIN = "begin";
    private static final String ATTR_DURATION = "dur";
    private static final String ATTR_END = "end";
    private static final String ATTR_IMAGE = "backgroundImage";
    private static final String ATTR_REGION = "region";
    private static final String ATTR_STYLE = "style";
    private static final int DEFAULT_FRAME_RATE = 30;
    private static final String TAG = "TtmlDecoder";
    private static final String TTP = "http://www.w3.org/ns/ttml#parameter";
    private final XmlPullParserFactory xmlParserFactory;
    private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    private static final Pattern FONT_SIZE = Pattern.compile("^(([0-9]*.)?[0-9]+)(px|em|%)$");
    static final Pattern SIGNED_PERCENTAGE = Pattern.compile("^([-+]?\\d+\\.?\\d*?)%$");
    static final Pattern PERCENTAGE_COORDINATES = Pattern.compile("^(\\d+\\.?\\d*?)% (\\d+\\.?\\d*?)%$");
    private static final Pattern PIXEL_COORDINATES = Pattern.compile("^(\\d+\\.?\\d*?)px (\\d+\\.?\\d*?)px$");
    private static final Pattern CELL_RESOLUTION = Pattern.compile("^(\\d+) (\\d+)$");
    private static final FrameAndTickRate DEFAULT_FRAME_AND_TICK_RATE = new FrameAndTickRate(30.0f, 1, 1);
    private static final CellResolution DEFAULT_CELL_RESOLUTION = new CellResolution(32, 15);

    public TtmlDecoder() throws XmlPullParserException {
        super(TAG);
        try {
            XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
            this.xmlParserFactory = xmlPullParserFactoryNewInstance;
            xmlPullParserFactoryNewInstance.setNamespaceAware(true);
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bytes, int length, boolean reset) throws XmlPullParserException, SubtitleDecoderException, NumberFormatException, IOException {
        FrameAndTickRate frameAndTickRate;
        try {
            XmlPullParser xmlPullParserNewPullParser = this.xmlParserFactory.newPullParser();
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            HashMap map3 = new HashMap();
            map2.put("", new TtmlRegion(""));
            TtsExtent ttsExtent = null;
            xmlPullParserNewPullParser.setInput(new ByteArrayInputStream(bytes, 0, length), null);
            ArrayDeque arrayDeque = new ArrayDeque();
            FrameAndTickRate frameAndTickRates = DEFAULT_FRAME_AND_TICK_RATE;
            CellResolution cellResolution = DEFAULT_CELL_RESOLUTION;
            int i = 0;
            TtmlSubtitle ttmlSubtitle = null;
            for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.getEventType()) {
                TtmlNode ttmlNode = (TtmlNode) arrayDeque.peek();
                if (i == 0) {
                    String name = xmlPullParserNewPullParser.getName();
                    if (eventType == 2) {
                        if (TtmlNode.TAG_TT.equals(name)) {
                            frameAndTickRates = parseFrameAndTickRates(xmlPullParserNewPullParser);
                            cellResolution = parseCellResolution(xmlPullParserNewPullParser, DEFAULT_CELL_RESOLUTION);
                            ttsExtent = parseTtsExtent(xmlPullParserNewPullParser);
                        }
                        TtsExtent ttsExtent2 = ttsExtent;
                        FrameAndTickRate frameAndTickRate2 = frameAndTickRates;
                        CellResolution cellResolution2 = cellResolution;
                        if (!isSupportedTag(name)) {
                            String strValueOf = String.valueOf(xmlPullParserNewPullParser.getName());
                            Log.i(TAG, strValueOf.length() != 0 ? "Ignoring unsupported tag: ".concat(strValueOf) : new String("Ignoring unsupported tag: "));
                            i++;
                            frameAndTickRates = frameAndTickRate2;
                        } else {
                            if (TtmlNode.TAG_HEAD.equals(name)) {
                                frameAndTickRate = frameAndTickRate2;
                                parseHeader(xmlPullParserNewPullParser, map, cellResolution2, ttsExtent2, map2, map3);
                            } else {
                                frameAndTickRate = frameAndTickRate2;
                                try {
                                    TtmlNode node = parseNode(xmlPullParserNewPullParser, ttmlNode, map2, frameAndTickRate);
                                    arrayDeque.push(node);
                                    if (ttmlNode != null) {
                                        ttmlNode.addChild(node);
                                    }
                                } catch (SubtitleDecoderException e) {
                                    Log.w(TAG, "Suppressing parser error", e);
                                    i++;
                                }
                            }
                            frameAndTickRates = frameAndTickRate;
                        }
                        ttsExtent = ttsExtent2;
                        cellResolution = cellResolution2;
                    } else if (eventType == 4) {
                        ((TtmlNode) Assertions.checkNotNull(ttmlNode)).addChild(TtmlNode.buildTextNode(xmlPullParserNewPullParser.getText()));
                    } else if (eventType == 3) {
                        if (xmlPullParserNewPullParser.getName().equals(TtmlNode.TAG_TT)) {
                            ttmlSubtitle = new TtmlSubtitle((TtmlNode) Assertions.checkNotNull((TtmlNode) arrayDeque.peek()), map, map2, map3);
                        }
                        arrayDeque.pop();
                    }
                } else if (eventType == 2) {
                    i++;
                } else if (eventType == 3) {
                    i--;
                }
                xmlPullParserNewPullParser.next();
            }
            if (ttmlSubtitle != null) {
                return ttmlSubtitle;
            }
            throw new SubtitleDecoderException("No TTML subtitles found");
        } catch (IOException e2) {
            throw new IllegalStateException("Unexpected error when reading input.", e2);
        } catch (XmlPullParserException e3) {
            throw new SubtitleDecoderException("Unable to decode source", e3);
        }
    }

    private static FrameAndTickRate parseFrameAndTickRates(XmlPullParser xmlParser) throws SubtitleDecoderException, NumberFormatException {
        float f;
        String attributeValue = xmlParser.getAttributeValue(TTP, "frameRate");
        int i = attributeValue != null ? Integer.parseInt(attributeValue) : 30;
        String attributeValue2 = xmlParser.getAttributeValue(TTP, "frameRateMultiplier");
        if (attributeValue2 != null) {
            if (Util.split(attributeValue2, StringUtils.SPACE).length != 2) {
                throw new SubtitleDecoderException("frameRateMultiplier doesn't have 2 parts");
            }
            f = Integer.parseInt(r2[0]) / Integer.parseInt(r2[1]);
        } else {
            f = 1.0f;
        }
        FrameAndTickRate frameAndTickRate = DEFAULT_FRAME_AND_TICK_RATE;
        int i2 = frameAndTickRate.subFrameRate;
        String attributeValue3 = xmlParser.getAttributeValue(TTP, "subFrameRate");
        if (attributeValue3 != null) {
            i2 = Integer.parseInt(attributeValue3);
        }
        int i3 = frameAndTickRate.tickRate;
        String attributeValue4 = xmlParser.getAttributeValue(TTP, "tickRate");
        if (attributeValue4 != null) {
            i3 = Integer.parseInt(attributeValue4);
        }
        return new FrameAndTickRate(i * f, i2, i3);
    }

    private static CellResolution parseCellResolution(XmlPullParser xmlParser, CellResolution defaultValue) throws SubtitleDecoderException, NumberFormatException {
        String attributeValue = xmlParser.getAttributeValue(TTP, "cellResolution");
        if (attributeValue == null) {
            return defaultValue;
        }
        Matcher matcher = CELL_RESOLUTION.matcher(attributeValue);
        if (!matcher.matches()) {
            String strValueOf = String.valueOf(attributeValue);
            Log.w(TAG, strValueOf.length() != 0 ? "Ignoring malformed cell resolution: ".concat(strValueOf) : new String("Ignoring malformed cell resolution: "));
            return defaultValue;
        }
        try {
            int i = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)));
            int i2 = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2)));
            if (i == 0 || i2 == 0) {
                throw new SubtitleDecoderException(new StringBuilder(47).append("Invalid cell resolution ").append(i).append(StringUtils.SPACE).append(i2).toString());
            }
            return new CellResolution(i, i2);
        } catch (NumberFormatException unused) {
            String strValueOf2 = String.valueOf(attributeValue);
            Log.w(TAG, strValueOf2.length() != 0 ? "Ignoring malformed cell resolution: ".concat(strValueOf2) : new String("Ignoring malformed cell resolution: "));
            return defaultValue;
        }
    }

    private static TtsExtent parseTtsExtent(XmlPullParser xmlParser) {
        String attributeValue = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_TTS_EXTENT);
        if (attributeValue == null) {
            return null;
        }
        Matcher matcher = PIXEL_COORDINATES.matcher(attributeValue);
        if (!matcher.matches()) {
            String strValueOf = String.valueOf(attributeValue);
            Log.w(TAG, strValueOf.length() != 0 ? "Ignoring non-pixel tts extent: ".concat(strValueOf) : new String("Ignoring non-pixel tts extent: "));
            return null;
        }
        try {
            return new TtsExtent(Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1))), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2))));
        } catch (NumberFormatException unused) {
            String strValueOf2 = String.valueOf(attributeValue);
            Log.w(TAG, strValueOf2.length() != 0 ? "Ignoring malformed tts extent: ".concat(strValueOf2) : new String("Ignoring malformed tts extent: "));
            return null;
        }
    }

    private static Map<String, TtmlStyle> parseHeader(XmlPullParser xmlParser, Map<String, TtmlStyle> globalStyles, CellResolution cellResolution, TtsExtent ttsExtent, Map<String, TtmlRegion> globalRegions, Map<String, String> imageMap) throws XmlPullParserException, IOException, NumberFormatException {
        do {
            xmlParser.next();
            if (XmlPullParserUtil.isStartTag(xmlParser, "style")) {
                String attributeValue = XmlPullParserUtil.getAttributeValue(xmlParser, "style");
                TtmlStyle styleAttributes = parseStyleAttributes(xmlParser, new TtmlStyle());
                if (attributeValue != null) {
                    for (String str : parseStyleIds(attributeValue)) {
                        styleAttributes.chain(globalStyles.get(str));
                    }
                }
                String id = styleAttributes.getId();
                if (id != null) {
                    globalStyles.put(id, styleAttributes);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlParser, "region")) {
                TtmlRegion regionAttributes = parseRegionAttributes(xmlParser, cellResolution, ttsExtent);
                if (regionAttributes != null) {
                    globalRegions.put(regionAttributes.id, regionAttributes);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlParser, TtmlNode.TAG_METADATA)) {
                parseMetadata(xmlParser, imageMap);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlParser, TtmlNode.TAG_HEAD));
        return globalStyles;
    }

    private static void parseMetadata(XmlPullParser xmlParser, Map<String, String> imageMap) throws XmlPullParserException, IOException {
        String attributeValue;
        do {
            xmlParser.next();
            if (XmlPullParserUtil.isStartTag(xmlParser, "image") && (attributeValue = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_ID)) != null) {
                imageMap.put(attributeValue, xmlParser.nextText());
            }
        } while (!XmlPullParserUtil.isEndTag(xmlParser, TtmlNode.TAG_METADATA));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:60:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.google.android.exoplayer2.text.ttml.TtmlRegion parseRegionAttributes(org.xmlpull.v1.XmlPullParser r17, com.google.android.exoplayer2.text.ttml.TtmlDecoder.CellResolution r18, com.google.android.exoplayer2.text.ttml.TtmlDecoder.TtsExtent r19) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 612
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseRegionAttributes(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlDecoder$CellResolution, com.google.android.exoplayer2.text.ttml.TtmlDecoder$TtsExtent):com.google.android.exoplayer2.text.ttml.TtmlRegion");
    }

    private static String[] parseStyleIds(String parentStyleIds) {
        String strTrim = parentStyleIds.trim();
        return strTrim.isEmpty() ? new String[0] : Util.split(strTrim, "\\s+");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.google.android.exoplayer2.text.ttml.TtmlStyle parseStyleAttributes(org.xmlpull.v1.XmlPullParser r12, com.google.android.exoplayer2.text.ttml.TtmlStyle r13) {
        /*
            Method dump skipped, instructions count: 954
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseStyleAttributes(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlStyle):com.google.android.exoplayer2.text.ttml.TtmlStyle");
    }

    private static TtmlStyle createIfNull(TtmlStyle style) {
        return style == null ? new TtmlStyle() : style;
    }

    private static Layout.Alignment parseAlignment(String alignment) {
        String lowerCase = Ascii.toLowerCase(alignment);
        lowerCase.hashCode();
        switch (lowerCase) {
            case "center":
                return Layout.Alignment.ALIGN_CENTER;
            case "end":
            case "right":
                return Layout.Alignment.ALIGN_OPPOSITE;
            case "left":
            case "start":
                return Layout.Alignment.ALIGN_NORMAL;
            default:
                return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.google.android.exoplayer2.text.ttml.TtmlNode parseNode(org.xmlpull.v1.XmlPullParser r20, com.google.android.exoplayer2.text.ttml.TtmlNode r21, java.util.Map<java.lang.String, com.google.android.exoplayer2.text.ttml.TtmlRegion> r22, com.google.android.exoplayer2.text.ttml.TtmlDecoder.FrameAndTickRate r23) throws com.google.android.exoplayer2.text.SubtitleDecoderException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseNode(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlNode, java.util.Map, com.google.android.exoplayer2.text.ttml.TtmlDecoder$FrameAndTickRate):com.google.android.exoplayer2.text.ttml.TtmlNode");
    }

    private static boolean isSupportedTag(String tag) {
        return tag.equals(TtmlNode.TAG_TT) || tag.equals(TtmlNode.TAG_HEAD) || tag.equals(TtmlNode.TAG_BODY) || tag.equals(TtmlNode.TAG_DIV) || tag.equals(TtmlNode.TAG_P) || tag.equals(TtmlNode.TAG_SPAN) || tag.equals(TtmlNode.TAG_BR) || tag.equals("style") || tag.equals(TtmlNode.TAG_STYLING) || tag.equals(TtmlNode.TAG_LAYOUT) || tag.equals("region") || tag.equals(TtmlNode.TAG_METADATA) || tag.equals("image") || tag.equals("data") || tag.equals(TtmlNode.TAG_INFORMATION);
    }

    private static void parseFontSize(String expression, TtmlStyle out) throws SubtitleDecoderException {
        Matcher matcher;
        String str;
        String[] strArrSplit = Util.split(expression, "\\s+");
        if (strArrSplit.length == 1) {
            matcher = FONT_SIZE.matcher(expression);
        } else if (strArrSplit.length == 2) {
            matcher = FONT_SIZE.matcher(strArrSplit[1]);
            Log.w(TAG, "Multiple values in fontSize attribute. Picking the second value for vertical font size and ignoring the first.");
        } else {
            throw new SubtitleDecoderException(new StringBuilder(52).append("Invalid number of entries for fontSize: ").append(strArrSplit.length).append(".").toString());
        }
        if (matcher.matches()) {
            str = (String) Assertions.checkNotNull(matcher.group(3));
            str.hashCode();
            switch (str) {
                case "%":
                    out.setFontSizeUnit(3);
                    break;
                case "em":
                    out.setFontSizeUnit(2);
                    break;
                case "px":
                    out.setFontSizeUnit(1);
                    break;
                default:
                    throw new SubtitleDecoderException(new StringBuilder(String.valueOf(str).length() + 30).append("Invalid unit for fontSize: '").append(str).append("'.").toString());
            }
            out.setFontSize(Float.parseFloat((String) Assertions.checkNotNull(matcher.group(1))));
            return;
        }
        throw new SubtitleDecoderException(new StringBuilder(String.valueOf(expression).length() + 36).append("Invalid expression for fontSize: '").append(expression).append("'.").toString());
    }

    private static float parseShear(String expression) {
        Matcher matcher = SIGNED_PERCENTAGE.matcher(expression);
        if (!matcher.matches()) {
            String strValueOf = String.valueOf(expression);
            Log.w(TAG, strValueOf.length() != 0 ? "Invalid value for shear: ".concat(strValueOf) : new String("Invalid value for shear: "));
            return Float.MAX_VALUE;
        }
        try {
            return Math.min(100.0f, Math.max(-100.0f, Float.parseFloat((String) Assertions.checkNotNull(matcher.group(1)))));
        } catch (NumberFormatException e) {
            String strValueOf2 = String.valueOf(expression);
            Log.w(TAG, strValueOf2.length() != 0 ? "Failed to parse shear: ".concat(strValueOf2) : new String("Failed to parse shear: "), e);
            return Float.MAX_VALUE;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static long parseTimeExpression(java.lang.String r13, com.google.android.exoplayer2.text.ttml.TtmlDecoder.FrameAndTickRate r14) throws com.google.android.exoplayer2.text.SubtitleDecoderException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 332
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseTimeExpression(java.lang.String, com.google.android.exoplayer2.text.ttml.TtmlDecoder$FrameAndTickRate):long");
    }

    private static final class FrameAndTickRate {
        final float effectiveFrameRate;
        final int subFrameRate;
        final int tickRate;

        FrameAndTickRate(float effectiveFrameRate, int subFrameRate, int tickRate) {
            this.effectiveFrameRate = effectiveFrameRate;
            this.subFrameRate = subFrameRate;
            this.tickRate = tickRate;
        }
    }

    private static final class CellResolution {
        final int columns;
        final int rows;

        CellResolution(int columns, int rows) {
            this.columns = columns;
            this.rows = rows;
        }
    }

    private static final class TtsExtent {
        final int height;
        final int width;

        TtsExtent(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
