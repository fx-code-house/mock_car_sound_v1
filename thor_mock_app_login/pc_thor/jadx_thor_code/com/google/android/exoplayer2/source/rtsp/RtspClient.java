package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.os.Handler;
import android.util.SparseArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.rtsp.RtspHeaders;
import com.google.android.exoplayer2.source.rtsp.RtspMediaPeriod;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.source.rtsp.RtspMessageUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.SocketFactory;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
final class RtspClient implements Closeable {
    private static final long DEFAULT_RTSP_KEEP_ALIVE_INTERVAL_MS = 30000;
    private boolean hasUpdatedTimelineAndTracks;
    private KeepAliveMonitor keepAliveMonitor;
    private final PlaybackEventListener playbackEventListener;
    private boolean receivedAuthorizationRequest;
    private final RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo;
    private RtspAuthenticationInfo rtspAuthenticationInfo;
    private String sessionId;
    private final SessionInfoListener sessionInfoListener;
    private final Uri uri;
    private final String userAgent;
    private final ArrayDeque<RtspMediaPeriod.RtpLoadInfo> pendingSetupRtpLoadInfos = new ArrayDeque<>();
    private final SparseArray<RtspRequest> pendingRequests = new SparseArray<>();
    private final MessageSender messageSender = new MessageSender();
    private long pendingSeekPositionUs = C.TIME_UNSET;
    private RtspMessageChannel messageChannel = new RtspMessageChannel(new MessageListener());

    public interface PlaybackEventListener {
        void onPlaybackError(RtspMediaSource.RtspPlaybackException error);

        void onPlaybackStarted(long startPositionUs, ImmutableList<RtspTrackTiming> trackTimingList);

        void onRtspSetupCompleted();
    }

    public interface SessionInfoListener {
        void onSessionTimelineRequestFailed(String message, Throwable cause);

        void onSessionTimelineUpdated(RtspSessionTiming timing, ImmutableList<RtspMediaTrack> tracks);
    }

    public RtspClient(SessionInfoListener sessionInfoListener, PlaybackEventListener playbackEventListener, String userAgent, Uri uri) {
        this.sessionInfoListener = sessionInfoListener;
        this.playbackEventListener = playbackEventListener;
        this.uri = RtspMessageUtil.removeUserInfo(uri);
        this.rtspAuthUserInfo = RtspMessageUtil.parseUserInfo(uri);
        this.userAgent = userAgent;
    }

    public void start() throws NumberFormatException, IOException {
        try {
            this.messageChannel.open(getSocket(this.uri));
            this.messageSender.sendOptionsRequest(this.uri, this.sessionId);
        } catch (IOException e) {
            Util.closeQuietly(this.messageChannel);
            throw e;
        }
    }

    public void setupSelectedTracks(List<RtspMediaPeriod.RtpLoadInfo> loadInfos) throws NumberFormatException {
        this.pendingSetupRtpLoadInfos.addAll(loadInfos);
        continueSetupRtspTrack();
    }

    public void startPlayback(long offsetMs) throws NumberFormatException {
        this.messageSender.sendPlayRequest(this.uri, offsetMs, (String) Assertions.checkNotNull(this.sessionId));
    }

    public void seekToUs(long positionUs) throws NumberFormatException {
        this.messageSender.sendPauseRequest(this.uri, (String) Assertions.checkNotNull(this.sessionId));
        this.pendingSeekPositionUs = positionUs;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws NumberFormatException, IOException {
        KeepAliveMonitor keepAliveMonitor = this.keepAliveMonitor;
        if (keepAliveMonitor != null) {
            keepAliveMonitor.close();
            this.keepAliveMonitor = null;
            this.messageSender.sendTeardownRequest(this.uri, (String) Assertions.checkNotNull(this.sessionId));
        }
        this.messageChannel.close();
    }

    public void retryWithRtpTcp() throws NumberFormatException {
        try {
            close();
            RtspMessageChannel rtspMessageChannel = new RtspMessageChannel(new MessageListener());
            this.messageChannel = rtspMessageChannel;
            rtspMessageChannel.open(getSocket(this.uri));
            this.sessionId = null;
            this.receivedAuthorizationRequest = false;
            this.rtspAuthenticationInfo = null;
        } catch (IOException e) {
            this.playbackEventListener.onPlaybackError(new RtspMediaSource.RtspPlaybackException(e));
        }
    }

    public void registerInterleavedDataChannel(int channel, RtspMessageChannel.InterleavedBinaryDataListener interleavedBinaryDataListener) {
        this.messageChannel.registerInterleavedBinaryDataListener(channel, interleavedBinaryDataListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void continueSetupRtspTrack() throws NumberFormatException {
        RtspMediaPeriod.RtpLoadInfo rtpLoadInfoPollFirst = this.pendingSetupRtpLoadInfos.pollFirst();
        if (rtpLoadInfoPollFirst == null) {
            this.playbackEventListener.onRtspSetupCompleted();
        } else {
            this.messageSender.sendSetupRequest(rtpLoadInfoPollFirst.getTrackUri(), rtpLoadInfoPollFirst.getTransport(), this.sessionId);
        }
    }

    private static Socket getSocket(Uri uri) throws IOException {
        Assertions.checkArgument(uri.getHost() != null);
        return SocketFactory.getDefault().createSocket((String) Assertions.checkNotNull(uri.getHost()), uri.getPort() > 0 ? uri.getPort() : RtspMessageChannel.DEFAULT_RTSP_PORT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchRtspError(Throwable error) {
        RtspMediaSource.RtspPlaybackException rtspPlaybackException;
        if (error instanceof RtspMediaSource.RtspPlaybackException) {
            rtspPlaybackException = (RtspMediaSource.RtspPlaybackException) error;
        } else {
            rtspPlaybackException = new RtspMediaSource.RtspPlaybackException(error);
        }
        if (this.hasUpdatedTimelineAndTracks) {
            this.playbackEventListener.onPlaybackError(rtspPlaybackException);
        } else {
            this.sessionInfoListener.onSessionTimelineRequestFailed(Strings.nullToEmpty(error.getMessage()), error);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean serverSupportsDescribe(List<Integer> serverSupportedMethods) {
        return serverSupportedMethods.isEmpty() || serverSupportedMethods.contains(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImmutableList<RtspMediaTrack> buildTrackList(SessionDescription sessionDescription, Uri uri) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (int i = 0; i < sessionDescription.mediaDescriptionList.size(); i++) {
            MediaDescription mediaDescription = sessionDescription.mediaDescriptionList.get(i);
            if (RtpPayloadFormat.isFormatSupported(mediaDescription)) {
                builder.add((ImmutableList.Builder) new RtspMediaTrack(mediaDescription, uri));
            }
        }
        return builder.build();
    }

    private final class MessageSender {
        private int cSeq;
        private RtspRequest lastRequest;

        private MessageSender() {
        }

        public void sendOptionsRequest(Uri uri, String sessionId) throws NumberFormatException {
            sendRequest(getRequestWithCommonHeaders(4, sessionId, ImmutableMap.of(), uri));
        }

        public void sendDescribeRequest(Uri uri, String sessionId) throws NumberFormatException {
            sendRequest(getRequestWithCommonHeaders(2, sessionId, ImmutableMap.of(), uri));
        }

        public void sendSetupRequest(Uri trackUri, String transport, String sessionId) throws NumberFormatException {
            sendRequest(getRequestWithCommonHeaders(10, sessionId, ImmutableMap.of(RtspHeaders.TRANSPORT, transport), trackUri));
        }

        public void sendPlayRequest(Uri uri, long offsetMs, String sessionId) throws NumberFormatException {
            sendRequest(getRequestWithCommonHeaders(6, sessionId, ImmutableMap.of("Range", RtspSessionTiming.getOffsetStartTimeTiming(offsetMs)), uri));
        }

        public void sendTeardownRequest(Uri uri, String sessionId) throws NumberFormatException {
            sendRequest(getRequestWithCommonHeaders(12, sessionId, ImmutableMap.of(), uri));
        }

        public void sendPauseRequest(Uri uri, String sessionId) throws NumberFormatException {
            sendRequest(getRequestWithCommonHeaders(5, sessionId, ImmutableMap.of(), uri));
        }

        public void retryLastRequest() throws NumberFormatException {
            Assertions.checkStateNotNull(this.lastRequest);
            ImmutableListMultimap<String, String> immutableListMultimapAsMultiMap = this.lastRequest.headers.asMultiMap();
            HashMap map = new HashMap();
            for (String str : immutableListMultimapAsMultiMap.keySet()) {
                if (!str.equals(RtspHeaders.CSEQ) && !str.equals("User-Agent") && !str.equals(RtspHeaders.SESSION) && !str.equals("Authorization")) {
                    map.put(str, (String) Iterables.getLast(immutableListMultimapAsMultiMap.get((ImmutableListMultimap<String, String>) str)));
                }
            }
            sendRequest(getRequestWithCommonHeaders(this.lastRequest.method, RtspClient.this.sessionId, map, this.lastRequest.uri));
        }

        private RtspRequest getRequestWithCommonHeaders(int method, String sessionId, Map<String, String> additionalHeaders, Uri uri) {
            RtspHeaders.Builder builder = new RtspHeaders.Builder();
            int i = this.cSeq;
            this.cSeq = i + 1;
            builder.add(RtspHeaders.CSEQ, String.valueOf(i));
            builder.add("User-Agent", RtspClient.this.userAgent);
            if (sessionId != null) {
                builder.add(RtspHeaders.SESSION, sessionId);
            }
            if (RtspClient.this.rtspAuthenticationInfo != null) {
                Assertions.checkStateNotNull(RtspClient.this.rtspAuthUserInfo);
                try {
                    builder.add("Authorization", RtspClient.this.rtspAuthenticationInfo.getAuthorizationHeaderValue(RtspClient.this.rtspAuthUserInfo, uri, method));
                } catch (ParserException e) {
                    RtspClient.this.dispatchRtspError(new RtspMediaSource.RtspPlaybackException(e));
                }
            }
            builder.addAll(additionalHeaders);
            return new RtspRequest(uri, method, builder.build(), "");
        }

        private void sendRequest(RtspRequest request) throws NumberFormatException {
            int i = Integer.parseInt((String) Assertions.checkNotNull(request.headers.get(RtspHeaders.CSEQ)));
            Assertions.checkState(RtspClient.this.pendingRequests.get(i) == null);
            RtspClient.this.pendingRequests.append(i, request);
            RtspClient.this.messageChannel.send(RtspMessageUtil.serializeRequest(request));
            this.lastRequest = request;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class MessageListener implements RtspMessageChannel.MessageListener {
        private final Handler messageHandler = Util.createHandlerForCurrentLooper();

        public MessageListener() {
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspMessageChannel.MessageListener
        public void onRtspMessageReceived(final List<String> message) {
            this.messageHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.RtspClient$MessageListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws ParserException, NumberFormatException {
                    this.f$0.m192xbc9c051a(message);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: handleRtspMessage, reason: merged with bridge method [inline-methods] */
        public void m192xbc9c051a(List<String> message) throws ParserException, NumberFormatException {
            RtspSessionTiming timing;
            ImmutableList<RtspTrackTiming> trackTiming;
            RtspResponse response = RtspMessageUtil.parseResponse(message);
            int i = Integer.parseInt((String) Assertions.checkNotNull(response.headers.get(RtspHeaders.CSEQ)));
            RtspRequest rtspRequest = (RtspRequest) RtspClient.this.pendingRequests.get(i);
            if (rtspRequest == null) {
                return;
            }
            RtspClient.this.pendingRequests.remove(i);
            int i2 = rtspRequest.method;
            try {
                int i3 = response.status;
                if (i3 != 200) {
                    if (i3 == 401 && RtspClient.this.rtspAuthUserInfo != null && !RtspClient.this.receivedAuthorizationRequest) {
                        String str = response.headers.get("WWW-Authenticate");
                        if (str == null) {
                            throw ParserException.createForMalformedManifest("Missing WWW-Authenticate header in a 401 response.", null);
                        }
                        RtspClient.this.rtspAuthenticationInfo = RtspMessageUtil.parseWwwAuthenticateHeader(str);
                        RtspClient.this.messageSender.retryLastRequest();
                        RtspClient.this.receivedAuthorizationRequest = true;
                        return;
                    }
                    RtspClient rtspClient = RtspClient.this;
                    String methodString = RtspMessageUtil.toMethodString(i2);
                    rtspClient.dispatchRtspError(new RtspMediaSource.RtspPlaybackException(new StringBuilder(String.valueOf(methodString).length() + 12).append(methodString).append(StringUtils.SPACE).append(response.status).toString()));
                    return;
                }
                switch (i2) {
                    case 1:
                    case 3:
                    case 7:
                    case 8:
                    case 9:
                    case 11:
                    case 12:
                        return;
                    case 2:
                        onDescribeResponseReceived(new RtspDescribeResponse(response.status, SessionDescriptionParser.parse(response.messageBody)));
                        return;
                    case 4:
                        onOptionsResponseReceived(new RtspOptionsResponse(response.status, RtspMessageUtil.parsePublicHeader(response.headers.get(RtspHeaders.PUBLIC))));
                        return;
                    case 5:
                        onPauseResponseReceived();
                        return;
                    case 6:
                        String str2 = response.headers.get("Range");
                        if (str2 == null) {
                            timing = RtspSessionTiming.DEFAULT;
                        } else {
                            timing = RtspSessionTiming.parseTiming(str2);
                        }
                        String str3 = response.headers.get(RtspHeaders.RTP_INFO);
                        if (str3 == null) {
                            trackTiming = ImmutableList.of();
                        } else {
                            trackTiming = RtspTrackTiming.parseTrackTiming(str3);
                        }
                        onPlayResponseReceived(new RtspPlayResponse(response.status, timing, trackTiming));
                        return;
                    case 10:
                        String str4 = response.headers.get(RtspHeaders.SESSION);
                        String str5 = response.headers.get(RtspHeaders.TRANSPORT);
                        if (str4 == null || str5 == null) {
                            throw ParserException.createForMalformedManifest("Missing mandatory session or transport header", null);
                        }
                        onSetupResponseReceived(new RtspSetupResponse(response.status, RtspMessageUtil.parseSessionHeader(str4), str5));
                        return;
                    default:
                        throw new IllegalStateException();
                }
            } catch (ParserException e) {
                RtspClient.this.dispatchRtspError(new RtspMediaSource.RtspPlaybackException(e));
            }
        }

        private void onOptionsResponseReceived(RtspOptionsResponse response) throws NumberFormatException {
            if (RtspClient.this.keepAliveMonitor != null) {
                return;
            }
            if (RtspClient.serverSupportsDescribe(response.supportedMethods)) {
                RtspClient.this.messageSender.sendDescribeRequest(RtspClient.this.uri, RtspClient.this.sessionId);
            } else {
                RtspClient.this.sessionInfoListener.onSessionTimelineRequestFailed("DESCRIBE not supported.", null);
            }
        }

        private void onDescribeResponseReceived(RtspDescribeResponse response) {
            RtspSessionTiming timing = RtspSessionTiming.DEFAULT;
            String str = response.sessionDescription.attributes.get(SessionDescription.ATTR_RANGE);
            if (str != null) {
                try {
                    timing = RtspSessionTiming.parseTiming(str);
                } catch (ParserException e) {
                    RtspClient.this.sessionInfoListener.onSessionTimelineRequestFailed("SDP format error.", e);
                    return;
                }
            }
            ImmutableList<RtspMediaTrack> immutableListBuildTrackList = RtspClient.buildTrackList(response.sessionDescription, RtspClient.this.uri);
            if (immutableListBuildTrackList.isEmpty()) {
                RtspClient.this.sessionInfoListener.onSessionTimelineRequestFailed("No playable track.", null);
            } else {
                RtspClient.this.sessionInfoListener.onSessionTimelineUpdated(timing, immutableListBuildTrackList);
                RtspClient.this.hasUpdatedTimelineAndTracks = true;
            }
        }

        private void onSetupResponseReceived(RtspSetupResponse response) throws NumberFormatException {
            RtspClient.this.sessionId = response.sessionHeader.sessionId;
            RtspClient.this.continueSetupRtspTrack();
        }

        private void onPlayResponseReceived(RtspPlayResponse response) {
            if (RtspClient.this.keepAliveMonitor == null) {
                RtspClient.this.keepAliveMonitor = RtspClient.this.new KeepAliveMonitor(30000L);
                RtspClient.this.keepAliveMonitor.start();
            }
            RtspClient.this.playbackEventListener.onPlaybackStarted(C.msToUs(response.sessionTiming.startTimeMs), response.trackTimingList);
            RtspClient.this.pendingSeekPositionUs = C.TIME_UNSET;
        }

        private void onPauseResponseReceived() throws NumberFormatException {
            if (RtspClient.this.pendingSeekPositionUs != C.TIME_UNSET) {
                RtspClient rtspClient = RtspClient.this;
                rtspClient.startPlayback(C.usToMs(rtspClient.pendingSeekPositionUs));
            }
        }
    }

    private final class KeepAliveMonitor implements Runnable, Closeable {
        private final long intervalMs;
        private boolean isStarted;
        private final Handler keepAliveHandler = Util.createHandlerForCurrentLooper();

        public KeepAliveMonitor(long intervalMs) {
            this.intervalMs = intervalMs;
        }

        public void start() {
            if (this.isStarted) {
                return;
            }
            this.isStarted = true;
            this.keepAliveHandler.postDelayed(this, this.intervalMs);
        }

        @Override // java.lang.Runnable
        public void run() throws NumberFormatException {
            RtspClient.this.messageSender.sendOptionsRequest(RtspClient.this.uri, RtspClient.this.sessionId);
            this.keepAliveHandler.postDelayed(this, this.intervalMs);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.isStarted = false;
            this.keepAliveHandler.removeCallbacks(this);
        }
    }
}
