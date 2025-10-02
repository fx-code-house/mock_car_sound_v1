package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.rtsp.RtspHeaders;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
final class RtspMessageUtil {
    public static final long DEFAULT_RTSP_TIMEOUT_MS = 60000;
    private static final String RTSP_VERSION = "RTSP/1.0";
    private static final Pattern REQUEST_LINE_PATTERN = Pattern.compile("([A-Z_]+) (.*) RTSP/1\\.0");
    private static final Pattern STATUS_LINE_PATTERN = Pattern.compile("RTSP/1\\.0 (\\d+) (.+)");
    private static final Pattern CONTENT_LENGTH_HEADER_PATTERN = Pattern.compile("Content-Length:\\s?(\\d+)", 2);
    private static final Pattern SESSION_HEADER_PATTERN = Pattern.compile("([\\w$-_.+]+)(?:;\\s?timeout=(\\d+))?");
    private static final Pattern WWW_AUTHENTICATION_HEADER_DIGEST_PATTERN = Pattern.compile("Digest realm=\"([\\w\\s@.]+)\",\\s?(?:domain=\"(.+)\",\\s?)?nonce=\"(\\w+)\"(?:,\\s?opaque=\"(\\w+)\")?");
    private static final Pattern WWW_AUTHENTICATION_HEADER_BASIC_PATTERN = Pattern.compile("Basic realm=\"([\\w\\s@.]+)\"");
    private static final String LF = new String(new byte[]{10});
    private static final String CRLF = new String(new byte[]{Ascii.CR, 10});

    public static final class RtspSessionHeader {
        public final String sessionId;
        public final long timeoutMs;

        public RtspSessionHeader(String sessionId, long timeoutMs) {
            this.sessionId = sessionId;
            this.timeoutMs = timeoutMs;
        }
    }

    public static final class RtspAuthUserInfo {
        public final String password;
        public final String username;

        public RtspAuthUserInfo(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    public static ImmutableList<String> serializeRequest(RtspRequest request) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        builder.add((ImmutableList.Builder) Util.formatInvariant("%s %s %s", toMethodString(request.method), request.uri, RTSP_VERSION));
        ImmutableListMultimap<String, String> immutableListMultimapAsMultiMap = request.headers.asMultiMap();
        UnmodifiableIterator<String> it = immutableListMultimapAsMultiMap.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            ImmutableList<String> immutableList = immutableListMultimapAsMultiMap.get((ImmutableListMultimap<String, String>) next);
            for (int i = 0; i < immutableList.size(); i++) {
                builder.add((ImmutableList.Builder) Util.formatInvariant("%s: %s", next, immutableList.get(i)));
            }
        }
        builder.add((ImmutableList.Builder) "");
        builder.add((ImmutableList.Builder) request.messageBody);
        return builder.build();
    }

    public static ImmutableList<String> serializeResponse(RtspResponse response) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        builder.add((ImmutableList.Builder) Util.formatInvariant("%s %s %s", RTSP_VERSION, Integer.valueOf(response.status), getRtspStatusReasonPhrase(response.status)));
        ImmutableListMultimap<String, String> immutableListMultimapAsMultiMap = response.headers.asMultiMap();
        UnmodifiableIterator<String> it = immutableListMultimapAsMultiMap.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            ImmutableList<String> immutableList = immutableListMultimapAsMultiMap.get((ImmutableListMultimap<String, String>) next);
            for (int i = 0; i < immutableList.size(); i++) {
                builder.add((ImmutableList.Builder) Util.formatInvariant("%s: %s", next, immutableList.get(i)));
            }
        }
        builder.add((ImmutableList.Builder) "");
        builder.add((ImmutableList.Builder) response.messageBody);
        return builder.build();
    }

    public static byte[] convertMessageToByteArray(List<String> message) {
        return Joiner.on(CRLF).join(message).getBytes(RtspMessageChannel.CHARSET);
    }

    public static Uri removeUserInfo(Uri uri) {
        if (uri.getUserInfo() == null) {
            return uri;
        }
        String str = (String) Assertions.checkNotNull(uri.getAuthority());
        Assertions.checkArgument(str.contains("@"));
        return uri.buildUpon().encodedAuthority(Util.split(str, "@")[1]).build();
    }

    public static RtspAuthUserInfo parseUserInfo(Uri uri) {
        String userInfo = uri.getUserInfo();
        if (userInfo == null || !userInfo.contains(":")) {
            return null;
        }
        String[] strArrSplitAtFirst = Util.splitAtFirst(userInfo, ":");
        return new RtspAuthUserInfo(strArrSplitAtFirst[0], strArrSplitAtFirst[1]);
    }

    public static byte[] getStringBytes(String s) {
        return s.getBytes(RtspMessageChannel.CHARSET);
    }

    public static String toMethodString(int method) {
        switch (method) {
            case 1:
                return "ANNOUNCE";
            case 2:
                return "DESCRIBE";
            case 3:
                return "GET_PARAMETER";
            case 4:
                return "OPTIONS";
            case 5:
                return "PAUSE";
            case 6:
                return "PLAY";
            case 7:
                return "PLAY_NOTIFY";
            case 8:
                return "RECORD";
            case 9:
                return "REDIRECT";
            case 10:
                return "SETUP";
            case 11:
                return "SET_PARAMETER";
            case 12:
                return "TEARDOWN";
            default:
                throw new IllegalStateException();
        }
    }

    private static int parseMethodString(String method) {
        method.hashCode();
        switch (method) {
            case "RECORD":
                return 8;
            case "TEARDOWN":
                return 12;
            case "GET_PARAMETER":
                return 3;
            case "OPTIONS":
                return 4;
            case "PLAY_NOTIFY":
                return 7;
            case "PLAY":
                return 6;
            case "REDIRECT":
                return 9;
            case "SET_PARAMETER":
                return 11;
            case "PAUSE":
                return 5;
            case "SETUP":
                return 10;
            case "ANNOUNCE":
                return 1;
            case "DESCRIBE":
                return 2;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static RtspResponse parseResponse(List<String> lines) throws NumberFormatException {
        Matcher matcher = STATUS_LINE_PATTERN.matcher(lines.get(0));
        Assertions.checkArgument(matcher.matches());
        int i = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)));
        int iIndexOf = lines.indexOf("");
        Assertions.checkArgument(iIndexOf > 0);
        return new RtspResponse(i, new RtspHeaders.Builder().addAll(lines.subList(1, iIndexOf)).build(), Joiner.on(CRLF).join(lines.subList(iIndexOf + 1, lines.size())));
    }

    public static RtspRequest parseRequest(List<String> lines) {
        Matcher matcher = REQUEST_LINE_PATTERN.matcher(lines.get(0));
        Assertions.checkArgument(matcher.matches());
        int methodString = parseMethodString((String) Assertions.checkNotNull(matcher.group(1)));
        Uri uri = Uri.parse((String) Assertions.checkNotNull(matcher.group(2)));
        int iIndexOf = lines.indexOf("");
        Assertions.checkArgument(iIndexOf > 0);
        return new RtspRequest(uri, methodString, new RtspHeaders.Builder().addAll(lines.subList(1, iIndexOf)).build(), Joiner.on(CRLF).join(lines.subList(iIndexOf + 1, lines.size())));
    }

    public static boolean isRtspStartLine(String line) {
        return REQUEST_LINE_PATTERN.matcher(line).matches() || STATUS_LINE_PATTERN.matcher(line).matches();
    }

    public static String[] splitRtspMessageBody(String body) {
        String str = CRLF;
        if (!body.contains(str)) {
            str = LF;
        }
        return Util.split(body, str);
    }

    public static long parseContentLengthHeader(String line) throws ParserException {
        try {
            Matcher matcher = CONTENT_LENGTH_HEADER_PATTERN.matcher(line);
            if (matcher.find()) {
                return Long.parseLong((String) Assertions.checkNotNull(matcher.group(1)));
            }
            return -1L;
        } catch (NumberFormatException e) {
            throw ParserException.createForMalformedManifest(line, e);
        }
    }

    public static ImmutableList<Integer> parsePublicHeader(String publicHeader) {
        if (publicHeader == null) {
            return ImmutableList.of();
        }
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (String str : Util.split(publicHeader, ",\\s?")) {
            builder.add((ImmutableList.Builder) Integer.valueOf(parseMethodString(str)));
        }
        return builder.build();
    }

    public static RtspSessionHeader parseSessionHeader(String headerValue) throws ParserException {
        long j;
        Matcher matcher = SESSION_HEADER_PATTERN.matcher(headerValue);
        if (!matcher.matches()) {
            throw ParserException.createForMalformedManifest(headerValue, null);
        }
        String str = (String) Assertions.checkNotNull(matcher.group(1));
        if (matcher.group(2) != null) {
            try {
                j = Integer.parseInt(r0) * 1000;
            } catch (NumberFormatException e) {
                throw ParserException.createForMalformedManifest(headerValue, e);
            }
        } else {
            j = 60000;
        }
        return new RtspSessionHeader(str, j);
    }

    public static RtspAuthenticationInfo parseWwwAuthenticateHeader(String headerValue) throws ParserException {
        Matcher matcher = WWW_AUTHENTICATION_HEADER_DIGEST_PATTERN.matcher(headerValue);
        if (matcher.find()) {
            return new RtspAuthenticationInfo(2, (String) Assertions.checkNotNull(matcher.group(1)), (String) Assertions.checkNotNull(matcher.group(3)), Strings.nullToEmpty(matcher.group(4)));
        }
        Matcher matcher2 = WWW_AUTHENTICATION_HEADER_BASIC_PATTERN.matcher(headerValue);
        if (matcher2.matches()) {
            return new RtspAuthenticationInfo(1, (String) Assertions.checkNotNull(matcher2.group(1)), "", "");
        }
        String strValueOf = String.valueOf(headerValue);
        throw ParserException.createForMalformedManifest(strValueOf.length() != 0 ? "Invalid WWW-Authenticate header ".concat(strValueOf) : new String("Invalid WWW-Authenticate header "), null);
    }

    private static String getRtspStatusReasonPhrase(int statusCode) {
        if (statusCode == 200) {
            return "OK";
        }
        if (statusCode == 461) {
            return "Unsupported Transport";
        }
        if (statusCode == 500) {
            return "Internal Server Error";
        }
        if (statusCode == 505) {
            return "RTSP Version Not Supported";
        }
        if (statusCode == 400) {
            return "Bad Request";
        }
        if (statusCode == 401) {
            return "Unauthorized";
        }
        if (statusCode == 404) {
            return "Not Found";
        }
        if (statusCode == 405) {
            return "Method Not Allowed";
        }
        switch (statusCode) {
            case 454:
                return "Session Not Found";
            case 455:
                return "Method Not Valid In This State";
            case 456:
                return "Header Field Not Valid";
            case 457:
                return "Invalid Range";
            default:
                throw new IllegalArgumentException();
        }
    }

    public static int parseInt(String intString) throws ParserException {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            throw ParserException.createForMalformedManifest(intString, e);
        }
    }

    private RtspMessageUtil() {
    }
}
