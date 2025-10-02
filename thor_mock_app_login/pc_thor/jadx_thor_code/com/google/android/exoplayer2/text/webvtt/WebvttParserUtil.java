package com.google.android.exoplayer2.text.webvtt;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class WebvttParserUtil {
    private static final Pattern COMMENT = Pattern.compile("^NOTE([ \t].*)?$");
    private static final String WEBVTT_HEADER = "WEBVTT";

    private WebvttParserUtil() {
    }

    public static void validateWebvttHeaderLine(ParsableByteArray input) throws ParserException {
        int position = input.getPosition();
        if (isWebvttHeaderLine(input)) {
            return;
        }
        input.setPosition(position);
        String strValueOf = String.valueOf(input.readLine());
        throw ParserException.createForMalformedContainer(strValueOf.length() != 0 ? "Expected WEBVTT. Got ".concat(strValueOf) : new String("Expected WEBVTT. Got "), null);
    }

    public static boolean isWebvttHeaderLine(ParsableByteArray input) {
        String line = input.readLine();
        return line != null && line.startsWith(WEBVTT_HEADER);
    }

    public static long parseTimestampUs(String timestamp) throws NumberFormatException {
        String[] strArrSplitAtFirst = Util.splitAtFirst(timestamp, "\\.");
        long j = 0;
        for (String str : Util.split(strArrSplitAtFirst[0], ":")) {
            j = (j * 60) + Long.parseLong(str);
        }
        long j2 = j * 1000;
        if (strArrSplitAtFirst.length == 2) {
            j2 += Long.parseLong(strArrSplitAtFirst[1]);
        }
        return j2 * 1000;
    }

    public static float parsePercentage(String s) throws NumberFormatException {
        if (!s.endsWith("%")) {
            throw new NumberFormatException("Percentages must end with %");
        }
        return Float.parseFloat(s.substring(0, s.length() - 1)) / 100.0f;
    }

    public static Matcher findNextCueHeader(ParsableByteArray input) {
        String line;
        while (true) {
            String line2 = input.readLine();
            if (line2 == null) {
                return null;
            }
            if (COMMENT.matcher(line2).matches()) {
                do {
                    line = input.readLine();
                    if (line != null) {
                    }
                } while (!line.isEmpty());
            } else {
                Matcher matcher = WebvttCueParser.CUE_HEADER_PATTERN.matcher(line2);
                if (matcher.matches()) {
                    return matcher;
                }
            }
        }
    }
}
