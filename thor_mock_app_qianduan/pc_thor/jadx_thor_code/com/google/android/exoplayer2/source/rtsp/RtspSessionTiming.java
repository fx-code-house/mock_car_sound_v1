package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
final class RtspSessionTiming {
    private static final long LIVE_START_TIME = 0;
    private static final String START_TIMING_NTP_FORMAT = "npt=%.3f-";
    public final long startTimeMs;
    public final long stopTimeMs;
    public static final RtspSessionTiming DEFAULT = new RtspSessionTiming(0, C.TIME_UNSET);
    private static final Pattern NPT_RANGE_PATTERN = Pattern.compile("npt=([.\\d]+|now)\\s?-\\s?([.\\d]+)?");

    public static RtspSessionTiming parseTiming(String sdpRangeAttribute) throws ParserException {
        long j;
        Matcher matcher = NPT_RANGE_PATTERN.matcher(sdpRangeAttribute);
        Assertions.checkArgument(matcher.matches());
        String str = (String) Assertions.checkNotNull(matcher.group(1));
        long j2 = str.equals("now") ? 0L : (long) (Float.parseFloat(str) * 1000.0f);
        String strGroup = matcher.group(2);
        if (strGroup != null) {
            try {
                j = (long) (Float.parseFloat(strGroup) * 1000.0f);
                Assertions.checkArgument(j > j2);
            } catch (NumberFormatException e) {
                throw ParserException.createForMalformedManifest(strGroup, e);
            }
        } else {
            j = C.TIME_UNSET;
        }
        return new RtspSessionTiming(j2, j);
    }

    public static String getOffsetStartTimeTiming(long offsetStartTimeMs) {
        return Util.formatInvariant(START_TIMING_NTP_FORMAT, Double.valueOf(offsetStartTimeMs / 1000.0d));
    }

    private RtspSessionTiming(long startTimeMs, long stopTimeMs) {
        this.startTimeMs = startTimeMs;
        this.stopTimeMs = stopTimeMs;
    }

    public boolean isLive() {
        return this.stopTimeMs == C.TIME_UNSET;
    }

    public long getDurationMs() {
        return this.stopTimeMs - this.startTimeMs;
    }
}
