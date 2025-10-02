package com.google.android.exoplayer2.util;

import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.video.VideoSize;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class EventLogger implements AnalyticsListener {
    private static final String DEFAULT_TAG = "EventLogger";
    private static final int MAX_TIMELINE_ITEM_LINES = 3;
    private static final NumberFormat TIME_FORMAT;
    private final Timeline.Period period;
    private final long startTimeMs;
    private final String tag;
    private final MappingTrackSelector trackSelector;
    private final Timeline.Window window;

    private static String getDiscontinuityReasonString(int reason) {
        return reason != 0 ? reason != 1 ? reason != 2 ? reason != 3 ? reason != 4 ? reason != 5 ? "?" : "INTERNAL" : "REMOVE" : "SKIP" : "SEEK_ADJUSTMENT" : "SEEK" : "AUTO_TRANSITION";
    }

    private static String getMediaItemTransitionReasonString(int reason) {
        return reason != 0 ? reason != 1 ? reason != 2 ? reason != 3 ? "?" : "PLAYLIST_CHANGED" : "SEEK" : "AUTO" : "REPEAT";
    }

    private static String getPlayWhenReadyChangeReasonString(int reason) {
        return reason != 1 ? reason != 2 ? reason != 3 ? reason != 4 ? reason != 5 ? "?" : "END_OF_MEDIA_ITEM" : "REMOTE" : "AUDIO_BECOMING_NOISY" : "AUDIO_FOCUS_LOSS" : "USER_REQUEST";
    }

    private static String getPlaybackSuppressionReasonString(int playbackSuppressionReason) {
        return playbackSuppressionReason != 0 ? playbackSuppressionReason != 1 ? "?" : "TRANSIENT_AUDIO_FOCUS_LOSS" : "NONE";
    }

    private static String getRepeatModeString(int repeatMode) {
        return repeatMode != 0 ? repeatMode != 1 ? repeatMode != 2 ? "?" : "ALL" : "ONE" : "OFF";
    }

    private static String getStateString(int state) {
        return state != 1 ? state != 2 ? state != 3 ? state != 4 ? "?" : "ENDED" : "READY" : "BUFFERING" : "IDLE";
    }

    private static String getTimelineChangeReasonString(int reason) {
        return reason != 0 ? reason != 1 ? "?" : "SOURCE_UPDATE" : "PLAYLIST_CHANGED";
    }

    private static String getTrackStatusString(boolean enabled) {
        return enabled ? "[X]" : "[ ]";
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadCanceled(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadCompleted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadStarted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    static {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        TIME_FORMAT = numberFormat;
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
    }

    public EventLogger(MappingTrackSelector trackSelector) {
        this(trackSelector, DEFAULT_TAG);
    }

    public EventLogger(MappingTrackSelector trackSelector, String tag) {
        this.trackSelector = trackSelector;
        this.tag = tag;
        this.window = new Timeline.Window();
        this.period = new Timeline.Period();
        this.startTimeMs = android.os.SystemClock.elapsedRealtime();
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onIsLoadingChanged(AnalyticsListener.EventTime eventTime, boolean isLoading) {
        logd(eventTime, "loading", Boolean.toString(isLoading));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlaybackStateChanged(AnalyticsListener.EventTime eventTime, int state) {
        logd(eventTime, "state", getStateString(state));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlayWhenReadyChanged(AnalyticsListener.EventTime eventTime, boolean playWhenReady, int reason) {
        String playWhenReadyChangeReasonString = getPlayWhenReadyChangeReasonString(reason);
        logd(eventTime, "playWhenReady", new StringBuilder(String.valueOf(playWhenReadyChangeReasonString).length() + 7).append(playWhenReady).append(", ").append(playWhenReadyChangeReasonString).toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlaybackSuppressionReasonChanged(AnalyticsListener.EventTime eventTime, int playbackSuppressionReason) {
        logd(eventTime, "playbackSuppressionReason", getPlaybackSuppressionReasonString(playbackSuppressionReason));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onIsPlayingChanged(AnalyticsListener.EventTime eventTime, boolean isPlaying) {
        logd(eventTime, "isPlaying", Boolean.toString(isPlaying));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onRepeatModeChanged(AnalyticsListener.EventTime eventTime, int repeatMode) {
        logd(eventTime, "repeatMode", getRepeatModeString(repeatMode));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onShuffleModeChanged(AnalyticsListener.EventTime eventTime, boolean shuffleModeEnabled) {
        logd(eventTime, "shuffleModeEnabled", Boolean.toString(shuffleModeEnabled));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
        StringBuilder sb = new StringBuilder("reason=");
        sb.append(getDiscontinuityReasonString(reason)).append(", PositionInfo:old [window=").append(oldPosition.windowIndex).append(", period=").append(oldPosition.periodIndex).append(", pos=").append(oldPosition.positionMs);
        if (oldPosition.adGroupIndex != -1) {
            sb.append(", contentPos=").append(oldPosition.contentPositionMs).append(", adGroup=").append(oldPosition.adGroupIndex).append(", ad=").append(oldPosition.adIndexInAdGroup);
        }
        sb.append("], PositionInfo:new [window=").append(newPosition.windowIndex).append(", period=").append(newPosition.periodIndex).append(", pos=").append(newPosition.positionMs);
        if (newPosition.adGroupIndex != -1) {
            sb.append(", contentPos=").append(newPosition.contentPositionMs).append(", adGroup=").append(newPosition.adGroupIndex).append(", ad=").append(newPosition.adIndexInAdGroup);
        }
        sb.append("]");
        logd(eventTime, "positionDiscontinuity", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlaybackParametersChanged(AnalyticsListener.EventTime eventTime, PlaybackParameters playbackParameters) {
        logd(eventTime, "playbackParameters", playbackParameters.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onTimelineChanged(AnalyticsListener.EventTime eventTime, int reason) {
        int periodCount = eventTime.timeline.getPeriodCount();
        int windowCount = eventTime.timeline.getWindowCount();
        String eventTimeString = getEventTimeString(eventTime);
        String timelineChangeReasonString = getTimelineChangeReasonString(reason);
        logd(new StringBuilder(String.valueOf(eventTimeString).length() + 69 + String.valueOf(timelineChangeReasonString).length()).append("timeline [").append(eventTimeString).append(", periodCount=").append(periodCount).append(", windowCount=").append(windowCount).append(", reason=").append(timelineChangeReasonString).toString());
        for (int i = 0; i < Math.min(periodCount, 3); i++) {
            eventTime.timeline.getPeriod(i, this.period);
            String timeString = getTimeString(this.period.getDurationMs());
            logd(new StringBuilder(String.valueOf(timeString).length() + 11).append("  period [").append(timeString).append("]").toString());
        }
        if (periodCount > 3) {
            logd("  ...");
        }
        for (int i2 = 0; i2 < Math.min(windowCount, 3); i2++) {
            eventTime.timeline.getWindow(i2, this.window);
            String timeString2 = getTimeString(this.window.getDurationMs());
            logd(new StringBuilder(String.valueOf(timeString2).length() + 42).append("  window [").append(timeString2).append(", seekable=").append(this.window.isSeekable).append(", dynamic=").append(this.window.isDynamic).append("]").toString());
        }
        if (windowCount > 3) {
            logd("  ...");
        }
        logd("]");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onMediaItemTransition(AnalyticsListener.EventTime eventTime, MediaItem mediaItem, int reason) {
        String eventTimeString = getEventTimeString(eventTime);
        String mediaItemTransitionReasonString = getMediaItemTransitionReasonString(reason);
        logd(new StringBuilder(String.valueOf(eventTimeString).length() + 21 + String.valueOf(mediaItemTransitionReasonString).length()).append("mediaItem [").append(eventTimeString).append(", reason=").append(mediaItemTransitionReasonString).append("]").toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlayerError(AnalyticsListener.EventTime eventTime, PlaybackException error) {
        loge(eventTime, "playerFailed", error);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onTracksChanged(AnalyticsListener.EventTime eventTime, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        MappingTrackSelector mappingTrackSelector = this.trackSelector;
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = mappingTrackSelector != null ? mappingTrackSelector.getCurrentMappedTrackInfo() : null;
        if (currentMappedTrackInfo == null) {
            logd(eventTime, "tracks", HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        String strValueOf = String.valueOf(getEventTimeString(eventTime));
        logd(strValueOf.length() != 0 ? "tracks [".concat(strValueOf) : new String("tracks ["));
        int rendererCount = currentMappedTrackInfo.getRendererCount();
        int i = 0;
        while (true) {
            String str = "    Group:";
            String str2 = " [";
            if (i >= rendererCount) {
                break;
            }
            TrackGroupArray trackGroups2 = currentMappedTrackInfo.getTrackGroups(i);
            TrackSelection trackSelection = trackSelections.get(i);
            int i2 = rendererCount;
            if (trackGroups2.length == 0) {
                String rendererName = currentMappedTrackInfo.getRendererName(i);
                logd(new StringBuilder(String.valueOf(rendererName).length() + 5).append("  ").append(rendererName).append(" []").toString());
            } else {
                String rendererName2 = currentMappedTrackInfo.getRendererName(i);
                logd(new StringBuilder(String.valueOf(rendererName2).length() + 4).append("  ").append(rendererName2).append(" [").toString());
                int i3 = 0;
                while (i3 < trackGroups2.length) {
                    TrackGroup trackGroup = trackGroups2.get(i3);
                    TrackGroupArray trackGroupArray = trackGroups2;
                    String adaptiveSupportString = getAdaptiveSupportString(trackGroup.length, currentMappedTrackInfo.getAdaptiveSupport(i, i3, false));
                    logd(new StringBuilder(String.valueOf(adaptiveSupportString).length() + 44).append(str).append(i3).append(", adaptive_supported=").append(adaptiveSupportString).append(str2).toString());
                    int i4 = 0;
                    while (i4 < trackGroup.length) {
                        String trackStatusString = getTrackStatusString(trackSelection, trackGroup, i4);
                        String formatSupportString = C.getFormatSupportString(currentMappedTrackInfo.getTrackSupport(i, i3, i4));
                        TrackGroup trackGroup2 = trackGroup;
                        String logString = Format.toLogString(trackGroup.getFormat(i4));
                        logd(new StringBuilder(String.valueOf(trackStatusString).length() + 38 + String.valueOf(logString).length() + String.valueOf(formatSupportString).length()).append("      ").append(trackStatusString).append(" Track:").append(i4).append(", ").append(logString).append(", supported=").append(formatSupportString).toString());
                        i4++;
                        str = str;
                        trackGroup = trackGroup2;
                        str2 = str2;
                    }
                    logd("    ]");
                    i3++;
                    trackGroups2 = trackGroupArray;
                }
                if (trackSelection != null) {
                    int i5 = 0;
                    while (true) {
                        if (i5 >= trackSelection.length()) {
                            break;
                        }
                        Metadata metadata = trackSelection.getFormat(i5).metadata;
                        if (metadata != null) {
                            logd("    Metadata [");
                            printMetadata(metadata, "      ");
                            logd("    ]");
                            break;
                        }
                        i5++;
                    }
                }
                logd("  ]");
            }
            i++;
            rendererCount = i2;
        }
        String str3 = "    Group:";
        String str4 = " [";
        TrackGroupArray unmappedTrackGroups = currentMappedTrackInfo.getUnmappedTrackGroups();
        if (unmappedTrackGroups.length > 0) {
            logd("  Unmapped [");
            int i6 = 0;
            while (i6 < unmappedTrackGroups.length) {
                String str5 = str3;
                String str6 = str4;
                logd(new StringBuilder(23).append(str5).append(i6).append(str6).toString());
                int i7 = 0;
                for (TrackGroup trackGroup3 = unmappedTrackGroups.get(i6); i7 < trackGroup3.length; trackGroup3 = trackGroup3) {
                    String trackStatusString2 = getTrackStatusString(false);
                    String formatSupportString2 = C.getFormatSupportString(0);
                    String logString2 = Format.toLogString(trackGroup3.getFormat(i7));
                    logd(new StringBuilder(String.valueOf(trackStatusString2).length() + 38 + String.valueOf(logString2).length() + String.valueOf(formatSupportString2).length()).append("      ").append(trackStatusString2).append(" Track:").append(i7).append(", ").append(logString2).append(", supported=").append(formatSupportString2).toString());
                    i7++;
                    unmappedTrackGroups = unmappedTrackGroups;
                }
                logd("    ]");
                i6++;
                str3 = str5;
                str4 = str6;
            }
            logd("  ]");
        }
        logd("]");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onMetadata(AnalyticsListener.EventTime eventTime, Metadata metadata) {
        String strValueOf = String.valueOf(getEventTimeString(eventTime));
        logd(strValueOf.length() != 0 ? "metadata [".concat(strValueOf) : new String("metadata ["));
        printMetadata(metadata, "  ");
        logd("]");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        logd(eventTime, "audioEnabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String decoderName, long initializationDurationMs) {
        logd(eventTime, "audioDecoderInitialized", decoderName);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
        logd(eventTime, "audioInputFormat", Format.toLogString(format));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioUnderrun(AnalyticsListener.EventTime eventTime, int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        loge(eventTime, "audioTrackUnderrun", new StringBuilder(55).append(bufferSize).append(", ").append(bufferSizeMs).append(", ").append(elapsedSinceLastFeedMs).toString(), null);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioDecoderReleased(AnalyticsListener.EventTime eventTime, String decoderName) {
        logd(eventTime, "audioDecoderReleased", decoderName);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        logd(eventTime, "audioDisabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioSessionIdChanged(AnalyticsListener.EventTime eventTime, int audioSessionId) {
        logd(eventTime, "audioSessionId", Integer.toString(audioSessionId));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioAttributesChanged(AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
        int i = audioAttributes.contentType;
        int i2 = audioAttributes.flags;
        int i3 = audioAttributes.usage;
        logd(eventTime, "audioAttributes", new StringBuilder(47).append(i).append(",").append(i2).append(",").append(i3).append(",").append(audioAttributes.allowedCapturePolicy).toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onSkipSilenceEnabledChanged(AnalyticsListener.EventTime eventTime, boolean skipSilenceEnabled) {
        logd(eventTime, "skipSilenceEnabled", Boolean.toString(skipSilenceEnabled));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVolumeChanged(AnalyticsListener.EventTime eventTime, float volume) {
        logd(eventTime, "volume", Float.toString(volume));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        logd(eventTime, "videoEnabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String decoderName, long initializationDurationMs) {
        logd(eventTime, "videoDecoderInitialized", decoderName);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
        logd(eventTime, "videoInputFormat", Format.toLogString(format));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int droppedFrames, long elapsedMs) {
        logd(eventTime, "droppedFrames", Integer.toString(droppedFrames));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoDecoderReleased(AnalyticsListener.EventTime eventTime, String decoderName) {
        logd(eventTime, "videoDecoderReleased", decoderName);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        logd(eventTime, "videoDisabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime, Object output, long renderTimeMs) {
        logd(eventTime, "renderedFirstFrame", String.valueOf(output));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, VideoSize videoSize) {
        int i = videoSize.width;
        logd(eventTime, "videoSize", new StringBuilder(24).append(i).append(", ").append(videoSize.height).toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadError(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
        printInternalError(eventTime, "loadError", error);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onSurfaceSizeChanged(AnalyticsListener.EventTime eventTime, int width, int height) {
        logd(eventTime, "surfaceSize", new StringBuilder(24).append(width).append(", ").append(height).toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onUpstreamDiscarded(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        logd(eventTime, "upstreamDiscarded", Format.toLogString(mediaLoadData.trackFormat));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        logd(eventTime, "downstreamFormat", Format.toLogString(mediaLoadData.trackFormat));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime, int state) {
        logd(eventTime, "drmSessionAcquired", new StringBuilder(17).append("state=").append(state).toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception error) {
        printInternalError(eventTime, "drmSessionManagerError", error);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmKeysRestored(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmKeysRestored");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmKeysRemoved(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmKeysRemoved");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmKeysLoaded(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmKeysLoaded");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionReleased(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmSessionReleased");
    }

    protected void logd(String msg) {
        Log.d(this.tag, msg);
    }

    protected void loge(String msg) {
        Log.e(this.tag, msg);
    }

    private void logd(AnalyticsListener.EventTime eventTime, String eventName) {
        logd(getEventString(eventTime, eventName, null, null));
    }

    private void logd(AnalyticsListener.EventTime eventTime, String eventName, String eventDescription) {
        logd(getEventString(eventTime, eventName, eventDescription, null));
    }

    private void loge(AnalyticsListener.EventTime eventTime, String eventName, Throwable throwable) {
        loge(getEventString(eventTime, eventName, null, throwable));
    }

    private void loge(AnalyticsListener.EventTime eventTime, String eventName, String eventDescription, Throwable throwable) {
        loge(getEventString(eventTime, eventName, eventDescription, throwable));
    }

    private void printInternalError(AnalyticsListener.EventTime eventTime, String type, Exception e) {
        loge(eventTime, "internalError", type, e);
    }

    private void printMetadata(Metadata metadata, String prefix) {
        for (int i = 0; i < metadata.length(); i++) {
            String strValueOf = String.valueOf(metadata.get(i));
            logd(new StringBuilder(String.valueOf(prefix).length() + String.valueOf(strValueOf).length()).append(prefix).append(strValueOf).toString());
        }
    }

    private String getEventString(AnalyticsListener.EventTime eventTime, String eventName, String eventDescription, Throwable throwable) {
        String eventTimeString = getEventTimeString(eventTime);
        String string = new StringBuilder(String.valueOf(eventName).length() + 2 + String.valueOf(eventTimeString).length()).append(eventName).append(" [").append(eventTimeString).toString();
        if (throwable instanceof PlaybackException) {
            String strValueOf = String.valueOf(string);
            String errorCodeName = ((PlaybackException) throwable).getErrorCodeName();
            string = new StringBuilder(String.valueOf(strValueOf).length() + 12 + String.valueOf(errorCodeName).length()).append(strValueOf).append(", errorCode=").append(errorCodeName).toString();
        }
        if (eventDescription != null) {
            String strValueOf2 = String.valueOf(string);
            string = new StringBuilder(String.valueOf(strValueOf2).length() + 2 + String.valueOf(eventDescription).length()).append(strValueOf2).append(", ").append(eventDescription).toString();
        }
        String throwableString = Log.getThrowableString(throwable);
        if (!TextUtils.isEmpty(throwableString)) {
            String strValueOf3 = String.valueOf(string);
            String strReplace = throwableString.replace(StringUtils.LF, "\n  ");
            string = new StringBuilder(String.valueOf(strValueOf3).length() + 4 + String.valueOf(strReplace).length()).append(strValueOf3).append("\n  ").append(strReplace).append('\n').toString();
        }
        return String.valueOf(string).concat("]");
    }

    private String getEventTimeString(AnalyticsListener.EventTime eventTime) {
        String string = new StringBuilder(18).append("window=").append(eventTime.windowIndex).toString();
        if (eventTime.mediaPeriodId != null) {
            String strValueOf = String.valueOf(string);
            string = new StringBuilder(String.valueOf(strValueOf).length() + 20).append(strValueOf).append(", period=").append(eventTime.timeline.getIndexOfPeriod(eventTime.mediaPeriodId.periodUid)).toString();
            if (eventTime.mediaPeriodId.isAd()) {
                String strValueOf2 = String.valueOf(string);
                String strValueOf3 = String.valueOf(new StringBuilder(String.valueOf(strValueOf2).length() + 21).append(strValueOf2).append(", adGroup=").append(eventTime.mediaPeriodId.adGroupIndex).toString());
                string = new StringBuilder(String.valueOf(strValueOf3).length() + 16).append(strValueOf3).append(", ad=").append(eventTime.mediaPeriodId.adIndexInAdGroup).toString();
            }
        }
        String timeString = getTimeString(eventTime.realtimeMs - this.startTimeMs);
        String timeString2 = getTimeString(eventTime.eventPlaybackPositionMs);
        return new StringBuilder(String.valueOf(timeString).length() + 23 + String.valueOf(timeString2).length() + String.valueOf(string).length()).append("eventTime=").append(timeString).append(", mediaPos=").append(timeString2).append(", ").append(string).toString();
    }

    private static String getTimeString(long timeMs) {
        return timeMs == C.TIME_UNSET ? "?" : TIME_FORMAT.format(timeMs / 1000.0f);
    }

    private static String getAdaptiveSupportString(int trackCount, int adaptiveSupport) {
        if (trackCount < 2) {
            return "N/A";
        }
        if (adaptiveSupport == 0) {
            return "NO";
        }
        if (adaptiveSupport == 8) {
            return "YES_NOT_SEAMLESS";
        }
        if (adaptiveSupport == 16) {
            return "YES";
        }
        throw new IllegalStateException();
    }

    private static String getTrackStatusString(TrackSelection selection, TrackGroup group, int trackIndex) {
        return getTrackStatusString((selection == null || selection.getTrackGroup() != group || selection.indexOf(trackIndex) == -1) ? false : true);
    }
}
