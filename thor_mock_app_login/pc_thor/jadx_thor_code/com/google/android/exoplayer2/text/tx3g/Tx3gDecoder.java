package com.google.android.exoplayer2.text.tx3g;

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import java.util.List;

/* loaded from: classes.dex */
public final class Tx3gDecoder extends SimpleSubtitleDecoder {
    private static final char BOM_UTF16_BE = 65279;
    private static final char BOM_UTF16_LE = 65534;
    private static final int DEFAULT_COLOR = -1;
    private static final int DEFAULT_FONT_FACE = 0;
    private static final String DEFAULT_FONT_FAMILY = "sans-serif";
    private static final float DEFAULT_VERTICAL_PLACEMENT = 0.85f;
    private static final int FONT_FACE_BOLD = 1;
    private static final int FONT_FACE_ITALIC = 2;
    private static final int FONT_FACE_UNDERLINE = 4;
    private static final int SIZE_ATOM_HEADER = 8;
    private static final int SIZE_BOM_UTF16 = 2;
    private static final int SIZE_SHORT = 2;
    private static final int SIZE_STYLE_RECORD = 12;
    private static final int SPAN_PRIORITY_HIGH = 0;
    private static final int SPAN_PRIORITY_LOW = 16711680;
    private static final String TAG = "Tx3gDecoder";
    private static final String TX3G_SERIF = "Serif";
    private static final int TYPE_STYL = 1937013100;
    private static final int TYPE_TBOX = 1952608120;
    private final int calculatedVideoTrackHeight;
    private final boolean customVerticalPlacement;
    private final int defaultColorRgba;
    private final int defaultFontFace;
    private final String defaultFontFamily;
    private final float defaultVerticalPlacement;
    private final ParsableByteArray parsableByteArray;

    public Tx3gDecoder(List<byte[]> initializationData) {
        super(TAG);
        this.parsableByteArray = new ParsableByteArray();
        if (initializationData.size() == 1 && (initializationData.get(0).length == 48 || initializationData.get(0).length == 53)) {
            byte[] bArr = initializationData.get(0);
            this.defaultFontFace = bArr[24];
            this.defaultColorRgba = ((bArr[26] & 255) << 24) | ((bArr[27] & 255) << 16) | ((bArr[28] & 255) << 8) | (bArr[29] & 255);
            this.defaultFontFamily = TX3G_SERIF.equals(Util.fromUtf8Bytes(bArr, 43, bArr.length - 43)) ? C.SERIF_NAME : "sans-serif";
            int i = bArr[25] * Ascii.DC4;
            this.calculatedVideoTrackHeight = i;
            boolean z = (bArr[0] & 32) != 0;
            this.customVerticalPlacement = z;
            if (z) {
                this.defaultVerticalPlacement = Util.constrainValue(((bArr[11] & 255) | ((bArr[10] & 255) << 8)) / i, 0.0f, 0.95f);
                return;
            } else {
                this.defaultVerticalPlacement = DEFAULT_VERTICAL_PLACEMENT;
                return;
            }
        }
        this.defaultFontFace = 0;
        this.defaultColorRgba = -1;
        this.defaultFontFamily = "sans-serif";
        this.customVerticalPlacement = false;
        this.defaultVerticalPlacement = DEFAULT_VERTICAL_PLACEMENT;
        this.calculatedVideoTrackHeight = -1;
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bytes, int length, boolean reset) throws SubtitleDecoderException {
        this.parsableByteArray.reset(bytes, length);
        String subtitleText = readSubtitleText(this.parsableByteArray);
        if (subtitleText.isEmpty()) {
            return Tx3gSubtitle.EMPTY;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(subtitleText);
        attachFontFace(spannableStringBuilder, this.defaultFontFace, 0, 0, spannableStringBuilder.length(), SPAN_PRIORITY_LOW);
        attachColor(spannableStringBuilder, this.defaultColorRgba, -1, 0, spannableStringBuilder.length(), SPAN_PRIORITY_LOW);
        attachFontFamily(spannableStringBuilder, this.defaultFontFamily, 0, spannableStringBuilder.length());
        float fConstrainValue = this.defaultVerticalPlacement;
        while (this.parsableByteArray.bytesLeft() >= 8) {
            int position = this.parsableByteArray.getPosition();
            int i = this.parsableByteArray.readInt();
            int i2 = this.parsableByteArray.readInt();
            if (i2 == TYPE_STYL) {
                assertTrue(this.parsableByteArray.bytesLeft() >= 2);
                int unsignedShort = this.parsableByteArray.readUnsignedShort();
                for (int i3 = 0; i3 < unsignedShort; i3++) {
                    applyStyleRecord(this.parsableByteArray, spannableStringBuilder);
                }
            } else if (i2 == TYPE_TBOX && this.customVerticalPlacement) {
                assertTrue(this.parsableByteArray.bytesLeft() >= 2);
                fConstrainValue = Util.constrainValue(this.parsableByteArray.readUnsignedShort() / this.calculatedVideoTrackHeight, 0.0f, 0.95f);
            }
            this.parsableByteArray.setPosition(position + i);
        }
        return new Tx3gSubtitle(new Cue.Builder().setText(spannableStringBuilder).setLine(fConstrainValue, 0).setLineAnchor(0).build());
    }

    private static String readSubtitleText(ParsableByteArray parsableByteArray) throws SubtitleDecoderException {
        char cPeekChar;
        assertTrue(parsableByteArray.bytesLeft() >= 2);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        if (unsignedShort == 0) {
            return "";
        }
        if (parsableByteArray.bytesLeft() >= 2 && ((cPeekChar = parsableByteArray.peekChar()) == 65279 || cPeekChar == 65534)) {
            return parsableByteArray.readString(unsignedShort, Charsets.UTF_16);
        }
        return parsableByteArray.readString(unsignedShort, Charsets.UTF_8);
    }

    private void applyStyleRecord(ParsableByteArray parsableByteArray, SpannableStringBuilder cueText) throws SubtitleDecoderException {
        assertTrue(parsableByteArray.bytesLeft() >= 12);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        int unsignedShort2 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(2);
        int unsignedByte = parsableByteArray.readUnsignedByte();
        parsableByteArray.skipBytes(1);
        int i = parsableByteArray.readInt();
        if (unsignedShort2 > cueText.length()) {
            Log.w(TAG, new StringBuilder(68).append("Truncating styl end (").append(unsignedShort2).append(") to cueText.length() (").append(cueText.length()).append(").").toString());
            unsignedShort2 = cueText.length();
        }
        if (unsignedShort >= unsignedShort2) {
            Log.w(TAG, new StringBuilder(60).append("Ignoring styl with start (").append(unsignedShort).append(") >= end (").append(unsignedShort2).append(").").toString());
            return;
        }
        int i2 = unsignedShort2;
        attachFontFace(cueText, unsignedByte, this.defaultFontFace, unsignedShort, i2, 0);
        attachColor(cueText, i, this.defaultColorRgba, unsignedShort, i2, 0);
    }

    private static void attachFontFace(SpannableStringBuilder cueText, int fontFace, int defaultFontFace, int start, int end, int spanPriority) {
        if (fontFace != defaultFontFace) {
            int i = spanPriority | 33;
            boolean z = (fontFace & 1) != 0;
            boolean z2 = (fontFace & 2) != 0;
            if (z) {
                if (z2) {
                    cueText.setSpan(new StyleSpan(3), start, end, i);
                } else {
                    cueText.setSpan(new StyleSpan(1), start, end, i);
                }
            } else if (z2) {
                cueText.setSpan(new StyleSpan(2), start, end, i);
            }
            boolean z3 = (fontFace & 4) != 0;
            if (z3) {
                cueText.setSpan(new UnderlineSpan(), start, end, i);
            }
            if (z3 || z || z2) {
                return;
            }
            cueText.setSpan(new StyleSpan(0), start, end, i);
        }
    }

    private static void attachColor(SpannableStringBuilder cueText, int colorRgba, int defaultColorRgba, int start, int end, int spanPriority) {
        if (colorRgba != defaultColorRgba) {
            cueText.setSpan(new ForegroundColorSpan((colorRgba >>> 8) | ((colorRgba & 255) << 24)), start, end, spanPriority | 33);
        }
    }

    private static void attachFontFamily(SpannableStringBuilder cueText, String fontFamily, int start, int end) {
        if (fontFamily != "sans-serif") {
            cueText.setSpan(new TypefaceSpan(fontFamily), start, end, 16711713);
        }
    }

    private static void assertTrue(boolean checkValue) throws SubtitleDecoderException {
        if (!checkValue) {
            throw new SubtitleDecoderException("Unexpected subtitle format.");
        }
    }
}
