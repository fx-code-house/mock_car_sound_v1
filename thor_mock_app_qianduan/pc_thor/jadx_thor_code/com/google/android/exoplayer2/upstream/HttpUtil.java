package com.google.android.exoplayer2.upstream;

import android.text.TextUtils;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class HttpUtil {
    private static final String TAG = "HttpUtil";
    private static final Pattern CONTENT_RANGE_WITH_START_AND_END = Pattern.compile("bytes (\\d+)-(\\d+)/(?:\\d+|\\*)");
    private static final Pattern CONTENT_RANGE_WITH_SIZE = Pattern.compile("bytes (?:(?:\\d+-\\d+)|\\*)/(\\d+)");

    private HttpUtil() {
    }

    public static String buildRangeRequestHeader(long position, long length) {
        if (position == 0 && length == -1) {
            return null;
        }
        StringBuilder sb = new StringBuilder("bytes=");
        sb.append(position);
        sb.append("-");
        if (length != -1) {
            sb.append((position + length) - 1);
        }
        return sb.toString();
    }

    public static long getDocumentSize(String contentRangeHeader) {
        if (TextUtils.isEmpty(contentRangeHeader)) {
            return -1L;
        }
        Matcher matcher = CONTENT_RANGE_WITH_SIZE.matcher(contentRangeHeader);
        if (matcher.matches()) {
            return Long.parseLong((String) Assertions.checkNotNull(matcher.group(1)));
        }
        return -1L;
    }

    public static long getContentLength(String contentLengthHeader, String contentRangeHeader) throws NumberFormatException {
        long j;
        if (TextUtils.isEmpty(contentLengthHeader)) {
            j = -1;
        } else {
            try {
                j = Long.parseLong(contentLengthHeader);
            } catch (NumberFormatException unused) {
                Log.e(TAG, new StringBuilder(String.valueOf(contentLengthHeader).length() + 28).append("Unexpected Content-Length [").append(contentLengthHeader).append("]").toString());
            }
        }
        if (TextUtils.isEmpty(contentRangeHeader)) {
            return j;
        }
        Matcher matcher = CONTENT_RANGE_WITH_START_AND_END.matcher(contentRangeHeader);
        if (!matcher.matches()) {
            return j;
        }
        try {
            long j2 = (Long.parseLong((String) Assertions.checkNotNull(matcher.group(2))) - Long.parseLong((String) Assertions.checkNotNull(matcher.group(1)))) + 1;
            if (j < 0) {
                return j2;
            }
            if (j == j2) {
                return j;
            }
            Log.w(TAG, new StringBuilder(String.valueOf(contentLengthHeader).length() + 26 + String.valueOf(contentRangeHeader).length()).append("Inconsistent headers [").append(contentLengthHeader).append("] [").append(contentRangeHeader).append("]").toString());
            return Math.max(j, j2);
        } catch (NumberFormatException unused2) {
            Log.e(TAG, new StringBuilder(String.valueOf(contentRangeHeader).length() + 27).append("Unexpected Content-Range [").append(contentRangeHeader).append("]").toString());
            return j;
        }
    }
}
