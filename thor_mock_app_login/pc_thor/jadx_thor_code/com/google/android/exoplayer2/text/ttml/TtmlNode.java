package com.google.android.exoplayer2.text.ttml;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableStringBuilder;
import android.util.Base64;
import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/* loaded from: classes.dex */
final class TtmlNode {
    public static final String ANNOTATION_POSITION_AFTER = "after";
    public static final String ANNOTATION_POSITION_BEFORE = "before";
    public static final String ANNOTATION_POSITION_OUTSIDE = "outside";
    public static final String ANONYMOUS_REGION_ID = "";
    public static final String ATTR_EBUTTS_MULTI_ROW_ALIGN = "multiRowAlign";
    public static final String ATTR_ID = "id";
    public static final String ATTR_TTS_BACKGROUND_COLOR = "backgroundColor";
    public static final String ATTR_TTS_COLOR = "color";
    public static final String ATTR_TTS_DISPLAY_ALIGN = "displayAlign";
    public static final String ATTR_TTS_EXTENT = "extent";
    public static final String ATTR_TTS_FONT_FAMILY = "fontFamily";
    public static final String ATTR_TTS_FONT_SIZE = "fontSize";
    public static final String ATTR_TTS_FONT_STYLE = "fontStyle";
    public static final String ATTR_TTS_FONT_WEIGHT = "fontWeight";
    public static final String ATTR_TTS_ORIGIN = "origin";
    public static final String ATTR_TTS_RUBY = "ruby";
    public static final String ATTR_TTS_RUBY_POSITION = "rubyPosition";
    public static final String ATTR_TTS_SHEAR = "shear";
    public static final String ATTR_TTS_TEXT_ALIGN = "textAlign";
    public static final String ATTR_TTS_TEXT_COMBINE = "textCombine";
    public static final String ATTR_TTS_TEXT_DECORATION = "textDecoration";
    public static final String ATTR_TTS_TEXT_EMPHASIS = "textEmphasis";
    public static final String ATTR_TTS_WRITING_MODE = "writingMode";
    public static final String BOLD = "bold";
    public static final String CENTER = "center";
    public static final String COMBINE_ALL = "all";
    public static final String COMBINE_NONE = "none";
    public static final String END = "end";
    public static final String ITALIC = "italic";
    public static final String LEFT = "left";
    public static final String LINETHROUGH = "linethrough";
    public static final String NO_LINETHROUGH = "nolinethrough";
    public static final String NO_UNDERLINE = "nounderline";
    public static final String RIGHT = "right";
    public static final String RUBY_BASE = "base";
    public static final String RUBY_BASE_CONTAINER = "baseContainer";
    public static final String RUBY_CONTAINER = "container";
    public static final String RUBY_DELIMITER = "delimiter";
    public static final String RUBY_TEXT = "text";
    public static final String RUBY_TEXT_CONTAINER = "textContainer";
    public static final String START = "start";
    public static final String TAG_BODY = "body";
    public static final String TAG_BR = "br";
    public static final String TAG_DATA = "data";
    public static final String TAG_DIV = "div";
    public static final String TAG_HEAD = "head";
    public static final String TAG_IMAGE = "image";
    public static final String TAG_INFORMATION = "information";
    public static final String TAG_LAYOUT = "layout";
    public static final String TAG_METADATA = "metadata";
    public static final String TAG_P = "p";
    public static final String TAG_REGION = "region";
    public static final String TAG_SPAN = "span";
    public static final String TAG_STYLE = "style";
    public static final String TAG_STYLING = "styling";
    public static final String TAG_TT = "tt";
    public static final String TEXT_EMPHASIS_AUTO = "auto";
    public static final String TEXT_EMPHASIS_MARK_CIRCLE = "circle";
    public static final String TEXT_EMPHASIS_MARK_DOT = "dot";
    public static final String TEXT_EMPHASIS_MARK_FILLED = "filled";
    public static final String TEXT_EMPHASIS_MARK_OPEN = "open";
    public static final String TEXT_EMPHASIS_MARK_SESAME = "sesame";
    public static final String TEXT_EMPHASIS_NONE = "none";
    public static final String UNDERLINE = "underline";
    public static final String VERTICAL = "tb";
    public static final String VERTICAL_LR = "tblr";
    public static final String VERTICAL_RL = "tbrl";
    private List<TtmlNode> children;
    public final long endTimeUs;
    public final String imageId;
    public final boolean isTextNode;
    private final HashMap<String, Integer> nodeEndsByRegion;
    private final HashMap<String, Integer> nodeStartsByRegion;
    public final TtmlNode parent;
    public final String regionId;
    public final long startTimeUs;
    public final TtmlStyle style;
    private final String[] styleIds;
    public final String tag;
    public final String text;

    public static TtmlNode buildTextNode(String text) {
        return new TtmlNode(null, TtmlRenderUtil.applyTextElementSpacePolicy(text), C.TIME_UNSET, C.TIME_UNSET, null, null, "", null, null);
    }

    public static TtmlNode buildNode(String tag, long startTimeUs, long endTimeUs, TtmlStyle style, String[] styleIds, String regionId, String imageId, TtmlNode parent) {
        return new TtmlNode(tag, null, startTimeUs, endTimeUs, style, styleIds, regionId, imageId, parent);
    }

    private TtmlNode(String tag, String text, long startTimeUs, long endTimeUs, TtmlStyle style, String[] styleIds, String regionId, String imageId, TtmlNode parent) {
        this.tag = tag;
        this.text = text;
        this.imageId = imageId;
        this.style = style;
        this.styleIds = styleIds;
        this.isTextNode = text != null;
        this.startTimeUs = startTimeUs;
        this.endTimeUs = endTimeUs;
        this.regionId = (String) Assertions.checkNotNull(regionId);
        this.parent = parent;
        this.nodeStartsByRegion = new HashMap<>();
        this.nodeEndsByRegion = new HashMap<>();
    }

    public boolean isActive(long timeUs) {
        long j = this.startTimeUs;
        return (j == C.TIME_UNSET && this.endTimeUs == C.TIME_UNSET) || (j <= timeUs && this.endTimeUs == C.TIME_UNSET) || ((j == C.TIME_UNSET && timeUs < this.endTimeUs) || (j <= timeUs && timeUs < this.endTimeUs));
    }

    public void addChild(TtmlNode child) {
        if (this.children == null) {
            this.children = new ArrayList();
        }
        this.children.add(child);
    }

    public TtmlNode getChild(int index) {
        List<TtmlNode> list = this.children;
        if (list == null) {
            throw new IndexOutOfBoundsException();
        }
        return list.get(index);
    }

    public int getChildCount() {
        List<TtmlNode> list = this.children;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public long[] getEventTimesUs() {
        TreeSet<Long> treeSet = new TreeSet<>();
        int i = 0;
        getEventTimes(treeSet, false);
        long[] jArr = new long[treeSet.size()];
        Iterator<Long> it = treeSet.iterator();
        while (it.hasNext()) {
            jArr[i] = it.next().longValue();
            i++;
        }
        return jArr;
    }

    private void getEventTimes(TreeSet<Long> out, boolean descendsPNode) {
        boolean zEquals = TAG_P.equals(this.tag);
        boolean zEquals2 = TAG_DIV.equals(this.tag);
        if (descendsPNode || zEquals || (zEquals2 && this.imageId != null)) {
            long j = this.startTimeUs;
            if (j != C.TIME_UNSET) {
                out.add(Long.valueOf(j));
            }
            long j2 = this.endTimeUs;
            if (j2 != C.TIME_UNSET) {
                out.add(Long.valueOf(j2));
            }
        }
        if (this.children == null) {
            return;
        }
        for (int i = 0; i < this.children.size(); i++) {
            this.children.get(i).getEventTimes(out, descendsPNode || zEquals);
        }
    }

    public String[] getStyleIds() {
        return this.styleIds;
    }

    public List<Cue> getCues(long timeUs, Map<String, TtmlStyle> globalStyles, Map<String, TtmlRegion> regionMap, Map<String, String> imageMap) {
        List<Pair<String, String>> arrayList = new ArrayList<>();
        traverseForImage(timeUs, this.regionId, arrayList);
        TreeMap treeMap = new TreeMap();
        traverseForText(timeUs, false, this.regionId, treeMap);
        traverseForStyle(timeUs, globalStyles, regionMap, this.regionId, treeMap);
        ArrayList arrayList2 = new ArrayList();
        for (Pair<String, String> pair : arrayList) {
            String str = imageMap.get(pair.second);
            if (str != null) {
                byte[] bArrDecode = Base64.decode(str, 0);
                Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
                TtmlRegion ttmlRegion = (TtmlRegion) Assertions.checkNotNull(regionMap.get(pair.first));
                arrayList2.add(new Cue.Builder().setBitmap(bitmapDecodeByteArray).setPosition(ttmlRegion.position).setPositionAnchor(0).setLine(ttmlRegion.line, 0).setLineAnchor(ttmlRegion.lineAnchor).setSize(ttmlRegion.width).setBitmapHeight(ttmlRegion.height).setVerticalType(ttmlRegion.verticalType).build());
            }
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            TtmlRegion ttmlRegion2 = (TtmlRegion) Assertions.checkNotNull(regionMap.get(entry.getKey()));
            Cue.Builder builder = (Cue.Builder) entry.getValue();
            cleanUpText((SpannableStringBuilder) Assertions.checkNotNull(builder.getText()));
            builder.setLine(ttmlRegion2.line, ttmlRegion2.lineType);
            builder.setLineAnchor(ttmlRegion2.lineAnchor);
            builder.setPosition(ttmlRegion2.position);
            builder.setSize(ttmlRegion2.width);
            builder.setTextSize(ttmlRegion2.textSize, ttmlRegion2.textSizeType);
            builder.setVerticalType(ttmlRegion2.verticalType);
            arrayList2.add(builder.build());
        }
        return arrayList2;
    }

    private void traverseForImage(long timeUs, String inheritedRegion, List<Pair<String, String>> regionImageList) {
        if (!"".equals(this.regionId)) {
            inheritedRegion = this.regionId;
        }
        if (isActive(timeUs) && TAG_DIV.equals(this.tag) && this.imageId != null) {
            regionImageList.add(new Pair<>(inheritedRegion, this.imageId));
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            getChild(i).traverseForImage(timeUs, inheritedRegion, regionImageList);
        }
    }

    private void traverseForText(long timeUs, boolean descendsPNode, String inheritedRegion, Map<String, Cue.Builder> regionOutputs) {
        this.nodeStartsByRegion.clear();
        this.nodeEndsByRegion.clear();
        if (TAG_METADATA.equals(this.tag)) {
            return;
        }
        if (!"".equals(this.regionId)) {
            inheritedRegion = this.regionId;
        }
        if (this.isTextNode && descendsPNode) {
            getRegionOutputText(inheritedRegion, regionOutputs).append((CharSequence) Assertions.checkNotNull(this.text));
            return;
        }
        if (TAG_BR.equals(this.tag) && descendsPNode) {
            getRegionOutputText(inheritedRegion, regionOutputs).append('\n');
            return;
        }
        if (isActive(timeUs)) {
            for (Map.Entry<String, Cue.Builder> entry : regionOutputs.entrySet()) {
                this.nodeStartsByRegion.put(entry.getKey(), Integer.valueOf(((CharSequence) Assertions.checkNotNull(entry.getValue().getText())).length()));
            }
            boolean zEquals = TAG_P.equals(this.tag);
            for (int i = 0; i < getChildCount(); i++) {
                getChild(i).traverseForText(timeUs, descendsPNode || zEquals, inheritedRegion, regionOutputs);
            }
            if (zEquals) {
                TtmlRenderUtil.endParagraph(getRegionOutputText(inheritedRegion, regionOutputs));
            }
            for (Map.Entry<String, Cue.Builder> entry2 : regionOutputs.entrySet()) {
                this.nodeEndsByRegion.put(entry2.getKey(), Integer.valueOf(((CharSequence) Assertions.checkNotNull(entry2.getValue().getText())).length()));
            }
        }
    }

    private static SpannableStringBuilder getRegionOutputText(String resolvedRegionId, Map<String, Cue.Builder> regionOutputs) {
        if (!regionOutputs.containsKey(resolvedRegionId)) {
            Cue.Builder builder = new Cue.Builder();
            builder.setText(new SpannableStringBuilder());
            regionOutputs.put(resolvedRegionId, builder);
        }
        return (SpannableStringBuilder) Assertions.checkNotNull(regionOutputs.get(resolvedRegionId).getText());
    }

    private void traverseForStyle(long timeUs, Map<String, TtmlStyle> globalStyles, Map<String, TtmlRegion> regionMaps, String inheritedRegion, Map<String, Cue.Builder> regionOutputs) {
        int i;
        if (isActive(timeUs)) {
            String str = "".equals(this.regionId) ? inheritedRegion : this.regionId;
            Iterator<Map.Entry<String, Integer>> it = this.nodeEndsByRegion.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<String, Integer> next = it.next();
                String key = next.getKey();
                int iIntValue = this.nodeStartsByRegion.containsKey(key) ? this.nodeStartsByRegion.get(key).intValue() : 0;
                int iIntValue2 = next.getValue().intValue();
                if (iIntValue != iIntValue2) {
                    applyStyleToOutput(globalStyles, (Cue.Builder) Assertions.checkNotNull(regionOutputs.get(key)), iIntValue, iIntValue2, ((TtmlRegion) Assertions.checkNotNull(regionMaps.get(str))).verticalType);
                }
            }
            while (i < getChildCount()) {
                getChild(i).traverseForStyle(timeUs, globalStyles, regionMaps, str, regionOutputs);
                i++;
            }
        }
    }

    private void applyStyleToOutput(Map<String, TtmlStyle> globalStyles, Cue.Builder regionOutput, int start, int end, int verticalType) {
        TtmlStyle ttmlStyleResolveStyle = TtmlRenderUtil.resolveStyle(this.style, this.styleIds, globalStyles);
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) regionOutput.getText();
        if (spannableStringBuilder == null) {
            spannableStringBuilder = new SpannableStringBuilder();
            regionOutput.setText(spannableStringBuilder);
        }
        SpannableStringBuilder spannableStringBuilder2 = spannableStringBuilder;
        if (ttmlStyleResolveStyle != null) {
            TtmlRenderUtil.applyStylesToSpan(spannableStringBuilder2, start, end, ttmlStyleResolveStyle, this.parent, globalStyles, verticalType);
            if (TAG_P.equals(this.tag)) {
                if (ttmlStyleResolveStyle.getShearPercentage() != Float.MAX_VALUE) {
                    regionOutput.setShearDegrees((ttmlStyleResolveStyle.getShearPercentage() * (-90.0f)) / 100.0f);
                }
                if (ttmlStyleResolveStyle.getTextAlign() != null) {
                    regionOutput.setTextAlignment(ttmlStyleResolveStyle.getTextAlign());
                }
                if (ttmlStyleResolveStyle.getMultiRowAlign() != null) {
                    regionOutput.setMultiRowAlignment(ttmlStyleResolveStyle.getMultiRowAlign());
                }
            }
        }
    }

    private static void cleanUpText(SpannableStringBuilder builder) {
        for (DeleteTextSpan deleteTextSpan : (DeleteTextSpan[]) builder.getSpans(0, builder.length(), DeleteTextSpan.class)) {
            builder.replace(builder.getSpanStart(deleteTextSpan), builder.getSpanEnd(deleteTextSpan), "");
        }
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == ' ') {
                int i2 = i + 1;
                int i3 = i2;
                while (i3 < builder.length() && builder.charAt(i3) == ' ') {
                    i3++;
                }
                int i4 = i3 - i2;
                if (i4 > 0) {
                    builder.delete(i, i4 + i);
                }
            }
        }
        if (builder.length() > 0 && builder.charAt(0) == ' ') {
            builder.delete(0, 1);
        }
        for (int i5 = 0; i5 < builder.length() - 1; i5++) {
            if (builder.charAt(i5) == '\n') {
                int i6 = i5 + 1;
                if (builder.charAt(i6) == ' ') {
                    builder.delete(i6, i5 + 2);
                }
            }
        }
        if (builder.length() > 0 && builder.charAt(builder.length() - 1) == ' ') {
            builder.delete(builder.length() - 1, builder.length());
        }
        for (int i7 = 0; i7 < builder.length() - 1; i7++) {
            if (builder.charAt(i7) == ' ') {
                int i8 = i7 + 1;
                if (builder.charAt(i8) == '\n') {
                    builder.delete(i7, i8);
                }
            }
        }
        if (builder.length() <= 0 || builder.charAt(builder.length() - 1) != '\n') {
            return;
        }
        builder.delete(builder.length() - 1, builder.length());
    }
}
