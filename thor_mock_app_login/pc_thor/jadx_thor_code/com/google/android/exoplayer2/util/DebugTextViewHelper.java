package com.google.android.exoplayer2.util;

import android.os.Looper;
import android.widget.TextView;
import androidx.core.os.EnvironmentCompat;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class DebugTextViewHelper implements Player.Listener, Runnable {
    private static final int REFRESH_INTERVAL_MS = 1000;
    private final SimpleExoPlayer player;
    private boolean started;
    private final TextView textView;

    public DebugTextViewHelper(SimpleExoPlayer player, TextView textView) {
        Assertions.checkArgument(player.getApplicationLooper() == Looper.getMainLooper());
        this.player = player;
        this.textView = textView;
    }

    public final void start() {
        if (this.started) {
            return;
        }
        this.started = true;
        this.player.addListener((Player.Listener) this);
        updateAndPost();
    }

    public final void stop() {
        if (this.started) {
            this.started = false;
            this.player.removeListener((Player.Listener) this);
            this.textView.removeCallbacks(this);
        }
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackStateChanged(int playbackState) {
        updateAndPost();
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
        updateAndPost();
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
        updateAndPost();
    }

    @Override // java.lang.Runnable
    public final void run() {
        updateAndPost();
    }

    protected final void updateAndPost() {
        this.textView.setText(getDebugString());
        this.textView.removeCallbacks(this);
        this.textView.postDelayed(this, 1000L);
    }

    protected String getDebugString() {
        String playerStateString = getPlayerStateString();
        String videoString = getVideoString();
        String audioString = getAudioString();
        return new StringBuilder(String.valueOf(playerStateString).length() + String.valueOf(videoString).length() + String.valueOf(audioString).length()).append(playerStateString).append(videoString).append(audioString).toString();
    }

    protected String getPlayerStateString() {
        int playbackState = this.player.getPlaybackState();
        return String.format("playWhenReady:%s playbackState:%s window:%s", Boolean.valueOf(this.player.getPlayWhenReady()), playbackState != 1 ? playbackState != 2 ? playbackState != 3 ? playbackState != 4 ? EnvironmentCompat.MEDIA_UNKNOWN : "ended" : "ready" : "buffering" : "idle", Integer.valueOf(this.player.getCurrentWindowIndex()));
    }

    protected String getVideoString() {
        Format videoFormat = this.player.getVideoFormat();
        DecoderCounters videoDecoderCounters = this.player.getVideoDecoderCounters();
        if (videoFormat == null || videoDecoderCounters == null) {
            return "";
        }
        String str = videoFormat.sampleMimeType;
        String str2 = videoFormat.id;
        int i = videoFormat.width;
        int i2 = videoFormat.height;
        String pixelAspectRatioString = getPixelAspectRatioString(videoFormat.pixelWidthHeightRatio);
        String decoderCountersBufferCountString = getDecoderCountersBufferCountString(videoDecoderCounters);
        String videoFrameProcessingOffsetAverageString = getVideoFrameProcessingOffsetAverageString(videoDecoderCounters.totalVideoFrameProcessingOffsetUs, videoDecoderCounters.videoFrameProcessingOffsetCount);
        return new StringBuilder(String.valueOf(str).length() + 39 + String.valueOf(str2).length() + String.valueOf(pixelAspectRatioString).length() + String.valueOf(decoderCountersBufferCountString).length() + String.valueOf(videoFrameProcessingOffsetAverageString).length()).append(StringUtils.LF).append(str).append("(id:").append(str2).append(" r:").append(i).append("x").append(i2).append(pixelAspectRatioString).append(decoderCountersBufferCountString).append(" vfpo: ").append(videoFrameProcessingOffsetAverageString).append(")").toString();
    }

    protected String getAudioString() {
        Format audioFormat = this.player.getAudioFormat();
        DecoderCounters audioDecoderCounters = this.player.getAudioDecoderCounters();
        if (audioFormat == null || audioDecoderCounters == null) {
            return "";
        }
        String str = audioFormat.sampleMimeType;
        String str2 = audioFormat.id;
        int i = audioFormat.sampleRate;
        int i2 = audioFormat.channelCount;
        String decoderCountersBufferCountString = getDecoderCountersBufferCountString(audioDecoderCounters);
        return new StringBuilder(String.valueOf(str).length() + 36 + String.valueOf(str2).length() + String.valueOf(decoderCountersBufferCountString).length()).append(StringUtils.LF).append(str).append("(id:").append(str2).append(" hz:").append(i).append(" ch:").append(i2).append(decoderCountersBufferCountString).append(")").toString();
    }

    private static String getDecoderCountersBufferCountString(DecoderCounters counters) {
        if (counters == null) {
            return "";
        }
        counters.ensureUpdated();
        int i = counters.skippedInputBufferCount;
        int i2 = counters.skippedOutputBufferCount;
        int i3 = counters.renderedOutputBufferCount;
        int i4 = counters.droppedBufferCount;
        int i5 = counters.maxConsecutiveDroppedBufferCount;
        return new StringBuilder(93).append(" sib:").append(i).append(" sb:").append(i2).append(" rb:").append(i3).append(" db:").append(i4).append(" mcdb:").append(i5).append(" dk:").append(counters.droppedToKeyframeCount).toString();
    }

    private static String getPixelAspectRatioString(float pixelAspectRatio) {
        if (pixelAspectRatio == -1.0f || pixelAspectRatio == 1.0f) {
            return "";
        }
        String strValueOf = String.valueOf(String.format(Locale.US, "%.02f", Float.valueOf(pixelAspectRatio)));
        return strValueOf.length() != 0 ? " par:".concat(strValueOf) : new String(" par:");
    }

    private static String getVideoFrameProcessingOffsetAverageString(long totalOffsetUs, int frameCount) {
        return frameCount == 0 ? "N/A" : String.valueOf((long) (totalOffsetUs / frameCount));
    }
}
