package com.google.android.exoplayer2.text.ssa;

import android.text.Layout;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.ssa.SsaStyle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class SsaDecoder extends SimpleSubtitleDecoder {
    private static final float DEFAULT_MARGIN = 0.05f;
    private static final String DIALOGUE_LINE_PREFIX = "Dialogue:";
    static final String FORMAT_LINE_PREFIX = "Format:";
    private static final Pattern SSA_TIMECODE_PATTERN = Pattern.compile("(?:(\\d+):)?(\\d+):(\\d+)[:.](\\d+)");
    static final String STYLE_LINE_PREFIX = "Style:";
    private static final String TAG = "SsaDecoder";
    private final SsaDialogueFormat dialogueFormatFromInitializationData;
    private final boolean haveInitializationData;
    private float screenHeight;
    private float screenWidth;
    private Map<String, SsaStyle> styles;

    private static float computeDefaultLineOrPosition(int anchor) {
        if (anchor == 0) {
            return DEFAULT_MARGIN;
        }
        if (anchor != 1) {
            return anchor != 2 ? -3.4028235E38f : 0.95f;
        }
        return 0.5f;
    }

    public SsaDecoder() {
        this(null);
    }

    public SsaDecoder(List<byte[]> initializationData) {
        super(TAG);
        this.screenWidth = -3.4028235E38f;
        this.screenHeight = -3.4028235E38f;
        if (initializationData != null && !initializationData.isEmpty()) {
            this.haveInitializationData = true;
            String strFromUtf8Bytes = Util.fromUtf8Bytes(initializationData.get(0));
            Assertions.checkArgument(strFromUtf8Bytes.startsWith(FORMAT_LINE_PREFIX));
            this.dialogueFormatFromInitializationData = (SsaDialogueFormat) Assertions.checkNotNull(SsaDialogueFormat.fromFormatLine(strFromUtf8Bytes));
            parseHeader(new ParsableByteArray(initializationData.get(1)));
            return;
        }
        this.haveInitializationData = false;
        this.dialogueFormatFromInitializationData = null;
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bytes, int length, boolean reset) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bytes, length);
        if (!this.haveInitializationData) {
            parseHeader(parsableByteArray);
        }
        parseEventBody(parsableByteArray, arrayList, arrayList2);
        return new SsaSubtitle(arrayList, arrayList2);
    }

    private void parseHeader(ParsableByteArray data) {
        while (true) {
            String line = data.readLine();
            if (line == null) {
                return;
            }
            if ("[Script Info]".equalsIgnoreCase(line)) {
                parseScriptInfo(data);
            } else if ("[V4+ Styles]".equalsIgnoreCase(line)) {
                this.styles = parseStyles(data);
            } else if ("[V4 Styles]".equalsIgnoreCase(line)) {
                Log.i(TAG, "[V4 Styles] are not supported");
            } else if ("[Events]".equalsIgnoreCase(line)) {
                return;
            }
        }
    }

    private void parseScriptInfo(ParsableByteArray data) {
        while (true) {
            String line = data.readLine();
            if (line == null) {
                return;
            }
            if (data.bytesLeft() != 0 && data.peekUnsignedByte() == 91) {
                return;
            }
            String[] strArrSplit = line.split(":");
            if (strArrSplit.length == 2) {
                String lowerCase = Ascii.toLowerCase(strArrSplit[0].trim());
                lowerCase.hashCode();
                if (lowerCase.equals("playresx")) {
                    this.screenWidth = Float.parseFloat(strArrSplit[1].trim());
                } else if (lowerCase.equals("playresy")) {
                    try {
                        this.screenHeight = Float.parseFloat(strArrSplit[1].trim());
                    } catch (NumberFormatException unused) {
                    }
                }
            }
        }
    }

    private static Map<String, SsaStyle> parseStyles(ParsableByteArray data) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        SsaStyle.Format formatFromFormatLine = null;
        while (true) {
            String line = data.readLine();
            if (line == null || (data.bytesLeft() != 0 && data.peekUnsignedByte() == 91)) {
                break;
            }
            if (line.startsWith(FORMAT_LINE_PREFIX)) {
                formatFromFormatLine = SsaStyle.Format.fromFormatLine(line);
            } else if (line.startsWith(STYLE_LINE_PREFIX)) {
                if (formatFromFormatLine == null) {
                    String strValueOf = String.valueOf(line);
                    Log.w(TAG, strValueOf.length() != 0 ? "Skipping 'Style:' line before 'Format:' line: ".concat(strValueOf) : new String("Skipping 'Style:' line before 'Format:' line: "));
                } else {
                    SsaStyle ssaStyleFromStyleLine = SsaStyle.fromStyleLine(line, formatFromFormatLine);
                    if (ssaStyleFromStyleLine != null) {
                        linkedHashMap.put(ssaStyleFromStyleLine.name, ssaStyleFromStyleLine);
                    }
                }
            }
        }
        return linkedHashMap;
    }

    private void parseEventBody(ParsableByteArray data, List<List<Cue>> cues, List<Long> cueTimesUs) {
        SsaDialogueFormat ssaDialogueFormatFromFormatLine = this.haveInitializationData ? this.dialogueFormatFromInitializationData : null;
        while (true) {
            String line = data.readLine();
            if (line == null) {
                return;
            }
            if (line.startsWith(FORMAT_LINE_PREFIX)) {
                ssaDialogueFormatFromFormatLine = SsaDialogueFormat.fromFormatLine(line);
            } else if (line.startsWith(DIALOGUE_LINE_PREFIX)) {
                if (ssaDialogueFormatFromFormatLine == null) {
                    String strValueOf = String.valueOf(line);
                    Log.w(TAG, strValueOf.length() != 0 ? "Skipping dialogue line before complete format: ".concat(strValueOf) : new String("Skipping dialogue line before complete format: "));
                } else {
                    parseDialogueLine(line, ssaDialogueFormatFromFormatLine, cues, cueTimesUs);
                }
            }
        }
    }

    private void parseDialogueLine(String dialogueLine, SsaDialogueFormat format, List<List<Cue>> cues, List<Long> cueTimesUs) {
        Assertions.checkArgument(dialogueLine.startsWith(DIALOGUE_LINE_PREFIX));
        String[] strArrSplit = dialogueLine.substring(9).split(",", format.length);
        if (strArrSplit.length != format.length) {
            String strValueOf = String.valueOf(dialogueLine);
            Log.w(TAG, strValueOf.length() != 0 ? "Skipping dialogue line with fewer columns than format: ".concat(strValueOf) : new String("Skipping dialogue line with fewer columns than format: "));
            return;
        }
        long timecodeUs = parseTimecodeUs(strArrSplit[format.startTimeIndex]);
        if (timecodeUs == C.TIME_UNSET) {
            String strValueOf2 = String.valueOf(dialogueLine);
            Log.w(TAG, strValueOf2.length() != 0 ? "Skipping invalid timing: ".concat(strValueOf2) : new String("Skipping invalid timing: "));
            return;
        }
        long timecodeUs2 = parseTimecodeUs(strArrSplit[format.endTimeIndex]);
        if (timecodeUs2 == C.TIME_UNSET) {
            String strValueOf3 = String.valueOf(dialogueLine);
            Log.w(TAG, strValueOf3.length() != 0 ? "Skipping invalid timing: ".concat(strValueOf3) : new String("Skipping invalid timing: "));
            return;
        }
        SsaStyle ssaStyle = (this.styles == null || format.styleIndex == -1) ? null : this.styles.get(strArrSplit[format.styleIndex].trim());
        String str = strArrSplit[format.textIndex];
        Cue cueCreateCue = createCue(SsaStyle.Overrides.stripStyleOverrides(str).replace("\\N", StringUtils.LF).replace("\\n", StringUtils.LF).replace("\\h", " "), ssaStyle, SsaStyle.Overrides.parseFromDialogue(str), this.screenWidth, this.screenHeight);
        int iAddCuePlacerholderByTime = addCuePlacerholderByTime(timecodeUs2, cueTimesUs, cues);
        for (int iAddCuePlacerholderByTime2 = addCuePlacerholderByTime(timecodeUs, cueTimesUs, cues); iAddCuePlacerholderByTime2 < iAddCuePlacerholderByTime; iAddCuePlacerholderByTime2++) {
            cues.get(iAddCuePlacerholderByTime2).add(cueCreateCue);
        }
    }

    private static long parseTimecodeUs(String timeString) {
        Matcher matcher = SSA_TIMECODE_PATTERN.matcher(timeString.trim());
        return !matcher.matches() ? C.TIME_UNSET : (Long.parseLong((String) Util.castNonNull(matcher.group(1))) * 60 * 60 * 1000000) + (Long.parseLong((String) Util.castNonNull(matcher.group(2))) * 60 * 1000000) + (Long.parseLong((String) Util.castNonNull(matcher.group(3))) * 1000000) + (Long.parseLong((String) Util.castNonNull(matcher.group(4))) * 10000);
    }

    private static Cue createCue(String text, SsaStyle style, SsaStyle.Overrides styleOverrides, float screenWidth, float screenHeight) {
        SpannableString spannableString = new SpannableString(text);
        Cue.Builder text2 = new Cue.Builder().setText(spannableString);
        if (style != null) {
            if (style.primaryColor != null) {
                spannableString.setSpan(new ForegroundColorSpan(style.primaryColor.intValue()), 0, spannableString.length(), 33);
            }
            if (style.fontSize != -3.4028235E38f && screenHeight != -3.4028235E38f) {
                text2.setTextSize(style.fontSize / screenHeight, 1);
            }
            if (style.bold && style.italic) {
                spannableString.setSpan(new StyleSpan(3), 0, spannableString.length(), 33);
            } else if (style.bold) {
                spannableString.setSpan(new StyleSpan(1), 0, spannableString.length(), 33);
            } else if (style.italic) {
                spannableString.setSpan(new StyleSpan(2), 0, spannableString.length(), 33);
            }
            if (style.underline) {
                spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 33);
            }
            if (style.strikeout) {
                spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), 33);
            }
        }
        int i = -1;
        if (styleOverrides.alignment != -1) {
            i = styleOverrides.alignment;
        } else if (style != null) {
            i = style.alignment;
        }
        text2.setTextAlignment(toTextAlignment(i)).setPositionAnchor(toPositionAnchor(i)).setLineAnchor(toLineAnchor(i));
        if (styleOverrides.position != null && screenHeight != -3.4028235E38f && screenWidth != -3.4028235E38f) {
            text2.setPosition(styleOverrides.position.x / screenWidth);
            text2.setLine(styleOverrides.position.y / screenHeight, 0);
        } else {
            text2.setPosition(computeDefaultLineOrPosition(text2.getPositionAnchor()));
            text2.setLine(computeDefaultLineOrPosition(text2.getLineAnchor()), 0);
        }
        return text2.build();
    }

    private static Layout.Alignment toTextAlignment(int alignment) {
        switch (alignment) {
            case -1:
                return null;
            case 0:
            default:
                Log.w(TAG, new StringBuilder(30).append("Unknown alignment: ").append(alignment).toString());
                return null;
            case 1:
            case 4:
            case 7:
                return Layout.Alignment.ALIGN_NORMAL;
            case 2:
            case 5:
            case 8:
                return Layout.Alignment.ALIGN_CENTER;
            case 3:
            case 6:
            case 9:
                return Layout.Alignment.ALIGN_OPPOSITE;
        }
    }

    private static int toLineAnchor(int alignment) {
        switch (alignment) {
            case -1:
                break;
            case 0:
            default:
                Log.w(TAG, new StringBuilder(30).append("Unknown alignment: ").append(alignment).toString());
                break;
            case 1:
            case 2:
            case 3:
                break;
            case 4:
            case 5:
            case 6:
                break;
            case 7:
            case 8:
            case 9:
                break;
        }
        return Integer.MIN_VALUE;
    }

    private static int toPositionAnchor(int alignment) {
        switch (alignment) {
            case -1:
                break;
            case 0:
            default:
                Log.w(TAG, new StringBuilder(30).append("Unknown alignment: ").append(alignment).toString());
                break;
            case 1:
            case 4:
            case 7:
                break;
            case 2:
            case 5:
            case 8:
                break;
            case 3:
            case 6:
            case 9:
                break;
        }
        return Integer.MIN_VALUE;
    }

    private static int addCuePlacerholderByTime(long timeUs, List<Long> sortedCueTimesUs, List<List<Cue>> cues) {
        int i;
        int size = sortedCueTimesUs.size() - 1;
        while (true) {
            if (size < 0) {
                i = 0;
                break;
            }
            if (sortedCueTimesUs.get(size).longValue() == timeUs) {
                return size;
            }
            if (sortedCueTimesUs.get(size).longValue() < timeUs) {
                i = size + 1;
                break;
            }
            size--;
        }
        sortedCueTimesUs.add(i, Long.valueOf(timeUs));
        cues.add(i, i == 0 ? new ArrayList() : new ArrayList(cues.get(i - 1)));
        return i;
    }
}
