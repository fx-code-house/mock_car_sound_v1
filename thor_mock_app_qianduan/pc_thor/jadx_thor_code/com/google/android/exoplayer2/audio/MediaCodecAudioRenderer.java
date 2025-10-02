package com.google.android.exoplayer2.audio;

import android.content.Context;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.messaging.Constants;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class MediaCodecAudioRenderer extends MediaCodecRenderer implements MediaClock {
    private static final String TAG = "MediaCodecAudioRenderer";
    private static final String VIVO_BITS_PER_SAMPLE_KEY = "v-bits-per-sample";
    private boolean allowFirstBufferPositionDiscontinuity;
    private boolean allowPositionDiscontinuity;
    private final AudioSink audioSink;
    private boolean audioSinkNeedsReset;
    private int codecMaxInputSize;
    private boolean codecNeedsDiscardChannelsWorkaround;
    private final Context context;
    private long currentPositionUs;
    private Format decryptOnlyCodecFormat;
    private final AudioRendererEventListener.EventDispatcher eventDispatcher;
    private boolean experimentalKeepAudioTrackOnSeek;
    private Renderer.WakeupListener wakeupListener;

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.Renderer
    public MediaClock getMediaClock() {
        return this;
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, null, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, Handler eventHandler, AudioRendererEventListener eventListener) {
        this(context, mediaCodecSelector, eventHandler, eventListener, (AudioCapabilities) null, new AudioProcessor[0]);
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, Handler eventHandler, AudioRendererEventListener eventListener, AudioCapabilities audioCapabilities, AudioProcessor... audioProcessors) {
        this(context, mediaCodecSelector, eventHandler, eventListener, new DefaultAudioSink(audioCapabilities, audioProcessors));
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, Handler eventHandler, AudioRendererEventListener eventListener, AudioSink audioSink) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, false, eventHandler, eventListener, audioSink);
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, boolean enableDecoderFallback, Handler eventHandler, AudioRendererEventListener eventListener, AudioSink audioSink) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, enableDecoderFallback, eventHandler, eventListener, audioSink);
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecAdapter.Factory codecAdapterFactory, MediaCodecSelector mediaCodecSelector, boolean enableDecoderFallback, Handler eventHandler, AudioRendererEventListener eventListener, AudioSink audioSink) {
        super(1, codecAdapterFactory, mediaCodecSelector, enableDecoderFallback, 44100.0f);
        this.context = context.getApplicationContext();
        this.audioSink = audioSink;
        this.eventDispatcher = new AudioRendererEventListener.EventDispatcher(eventHandler, eventListener);
        audioSink.setListener(new AudioSinkListener());
    }

    public void experimentalSetEnableKeepAudioTrackOnSeek(boolean enableKeepAudioTrackOnSeek) {
        this.experimentalKeepAudioTrackOnSeek = enableKeepAudioTrackOnSeek;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException {
        if (!MimeTypes.isAudio(format.sampleMimeType)) {
            return RendererCapabilities.create(0);
        }
        int i = Util.SDK_INT >= 21 ? 32 : 0;
        boolean z = format.exoMediaCryptoType != null;
        boolean zSupportsFormatDrm = supportsFormatDrm(format);
        int i2 = 8;
        if (zSupportsFormatDrm && this.audioSink.supportsFormat(format) && (!z || MediaCodecUtil.getDecryptOnlyDecoderInfo() != null)) {
            return RendererCapabilities.create(4, 8, i);
        }
        if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType) && !this.audioSink.supportsFormat(format)) {
            return RendererCapabilities.create(1);
        }
        if (!this.audioSink.supportsFormat(Util.getPcmFormat(2, format.channelCount, format.sampleRate))) {
            return RendererCapabilities.create(1);
        }
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(mediaCodecSelector, format, false);
        if (decoderInfos.isEmpty()) {
            return RendererCapabilities.create(1);
        }
        if (!zSupportsFormatDrm) {
            return RendererCapabilities.create(2);
        }
        MediaCodecInfo mediaCodecInfo = decoderInfos.get(0);
        boolean zIsFormatSupported = mediaCodecInfo.isFormatSupported(format);
        if (zIsFormatSupported && mediaCodecInfo.isSeamlessAdaptationSupported(format)) {
            i2 = 16;
        }
        return RendererCapabilities.create(zIsFormatSupported ? 4 : 3, i2, i);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean requiresSecureDecoder) throws MediaCodecUtil.DecoderQueryException {
        MediaCodecInfo decryptOnlyDecoderInfo;
        String str = format.sampleMimeType;
        if (str == null) {
            return Collections.emptyList();
        }
        if (this.audioSink.supportsFormat(format) && (decryptOnlyDecoderInfo = MediaCodecUtil.getDecryptOnlyDecoderInfo()) != null) {
            return Collections.singletonList(decryptOnlyDecoderInfo);
        }
        List<MediaCodecInfo> decoderInfosSortedByFormatSupport = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(mediaCodecSelector.getDecoderInfos(str, requiresSecureDecoder, false), format);
        if (MimeTypes.AUDIO_E_AC3_JOC.equals(str)) {
            ArrayList arrayList = new ArrayList(decoderInfosSortedByFormatSupport);
            arrayList.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.AUDIO_E_AC3, requiresSecureDecoder, false));
            decoderInfosSortedByFormatSupport = arrayList;
        }
        return Collections.unmodifiableList(decoderInfosSortedByFormatSupport);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected boolean shouldUseBypass(Format format) {
        return this.audioSink.supportsFormat(format);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected MediaCodecAdapter.Configuration getMediaCodecConfiguration(MediaCodecInfo codecInfo, Format format, MediaCrypto crypto, float codecOperatingRate) {
        this.codecMaxInputSize = getCodecMaxInputSize(codecInfo, format, getStreamFormats());
        this.codecNeedsDiscardChannelsWorkaround = codecNeedsDiscardChannelsWorkaround(codecInfo.name);
        MediaFormat mediaFormat = getMediaFormat(format, codecInfo.codecMimeType, this.codecMaxInputSize, codecOperatingRate);
        this.decryptOnlyCodecFormat = MimeTypes.AUDIO_RAW.equals(codecInfo.mimeType) && !MimeTypes.AUDIO_RAW.equals(format.sampleMimeType) ? format : null;
        return new MediaCodecAdapter.Configuration(codecInfo, mediaFormat, format, null, crypto, 0);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected DecoderReuseEvaluation canReuseCodec(MediaCodecInfo codecInfo, Format oldFormat, Format newFormat) {
        DecoderReuseEvaluation decoderReuseEvaluationCanReuseCodec = codecInfo.canReuseCodec(oldFormat, newFormat);
        int i = decoderReuseEvaluationCanReuseCodec.discardReasons;
        if (getCodecMaxInputSize(codecInfo, newFormat) > this.codecMaxInputSize) {
            i |= 64;
        }
        int i2 = i;
        return new DecoderReuseEvaluation(codecInfo.name, oldFormat, newFormat, i2 != 0 ? 0 : decoderReuseEvaluationCanReuseCodec.result, i2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected float getCodecOperatingRateV23(float targetPlaybackSpeed, Format format, Format[] streamFormats) {
        int iMax = -1;
        for (Format format2 : streamFormats) {
            int i = format2.sampleRate;
            if (i != -1) {
                iMax = Math.max(iMax, i);
            }
        }
        if (iMax == -1) {
            return -1.0f;
        }
        return targetPlaybackSpeed * iMax;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onCodecInitialized(String name, long initializedTimestampMs, long initializationDurationMs) {
        this.eventDispatcher.decoderInitialized(name, initializedTimestampMs, initializationDurationMs);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onCodecReleased(String name) {
        this.eventDispatcher.decoderReleased(name);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onCodecError(Exception codecError) {
        Log.e(TAG, "Audio codec error", codecError);
        this.eventDispatcher.audioCodecError(codecError);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected DecoderReuseEvaluation onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        DecoderReuseEvaluation decoderReuseEvaluationOnInputFormatChanged = super.onInputFormatChanged(formatHolder);
        this.eventDispatcher.inputFormatChanged(formatHolder.format, decoderReuseEvaluationOnInputFormatChanged);
        return decoderReuseEvaluationOnInputFormatChanged;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onOutputFormatChanged(Format format, MediaFormat mediaFormat) throws ExoPlaybackException {
        int pcmEncoding;
        Format format2 = this.decryptOnlyCodecFormat;
        int[] iArr = null;
        if (format2 != null) {
            format = format2;
        } else if (getCodec() != null) {
            if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
                pcmEncoding = format.pcmEncoding;
            } else if (Util.SDK_INT >= 24 && mediaFormat.containsKey("pcm-encoding")) {
                pcmEncoding = mediaFormat.getInteger("pcm-encoding");
            } else if (mediaFormat.containsKey(VIVO_BITS_PER_SAMPLE_KEY)) {
                pcmEncoding = Util.getPcmEncoding(mediaFormat.getInteger(VIVO_BITS_PER_SAMPLE_KEY));
            } else {
                pcmEncoding = MimeTypes.AUDIO_RAW.equals(format.sampleMimeType) ? format.pcmEncoding : 2;
            }
            Format formatBuild = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_RAW).setPcmEncoding(pcmEncoding).setEncoderDelay(format.encoderDelay).setEncoderPadding(format.encoderPadding).setChannelCount(mediaFormat.getInteger("channel-count")).setSampleRate(mediaFormat.getInteger("sample-rate")).build();
            if (this.codecNeedsDiscardChannelsWorkaround && formatBuild.channelCount == 6 && format.channelCount < 6) {
                iArr = new int[format.channelCount];
                for (int i = 0; i < format.channelCount; i++) {
                    iArr[i] = i;
                }
            }
            format = formatBuild;
        }
        try {
            this.audioSink.configure(format, 0, iArr);
        } catch (AudioSink.ConfigurationException e) {
            throw createRendererException(e, e.format, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED);
        }
    }

    protected void onPositionDiscontinuity() {
        this.allowPositionDiscontinuity = true;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onEnabled(boolean joining, boolean mayRenderStartOfStream) throws ExoPlaybackException {
        super.onEnabled(joining, mayRenderStartOfStream);
        this.eventDispatcher.enabled(this.decoderCounters);
        if (getConfiguration().tunneling) {
            this.audioSink.enableTunnelingV21();
        } else {
            this.audioSink.disableTunneling();
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
        super.onPositionReset(positionUs, joining);
        if (this.experimentalKeepAudioTrackOnSeek) {
            this.audioSink.experimentalFlushWithoutAudioTrackRelease();
        } else {
            this.audioSink.flush();
        }
        this.currentPositionUs = positionUs;
        this.allowFirstBufferPositionDiscontinuity = true;
        this.allowPositionDiscontinuity = true;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onStarted() {
        super.onStarted();
        this.audioSink.play();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onStopped() {
        updateCurrentPosition();
        this.audioSink.pause();
        super.onStopped();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onDisabled() {
        this.audioSinkNeedsReset = true;
        try {
            this.audioSink.flush();
            try {
                super.onDisabled();
            } finally {
            }
        } catch (Throwable th) {
            try {
                super.onDisabled();
                throw th;
            } finally {
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onReset() {
        try {
            super.onReset();
        } finally {
            if (this.audioSinkNeedsReset) {
                this.audioSinkNeedsReset = false;
                this.audioSink.reset();
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return super.isEnded() && this.audioSink.isEnded();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return this.audioSink.hasPendingData() || super.isReady();
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public long getPositionUs() {
        if (getState() == 2) {
            updateCurrentPosition();
        }
        return this.currentPositionUs;
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.audioSink.setPlaybackParameters(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters getPlaybackParameters() {
        return this.audioSink.getPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onQueueInputBuffer(DecoderInputBuffer buffer) {
        if (!this.allowFirstBufferPositionDiscontinuity || buffer.isDecodeOnly()) {
            return;
        }
        if (Math.abs(buffer.timeUs - this.currentPositionUs) > 500000) {
            this.currentPositionUs = buffer.timeUs;
        }
        this.allowFirstBufferPositionDiscontinuity = false;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onProcessedStreamChange() {
        super.onProcessedStreamChange();
        this.audioSink.handleDiscontinuity();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected boolean processOutputBuffer(long positionUs, long elapsedRealtimeUs, MediaCodecAdapter codec, ByteBuffer buffer, int bufferIndex, int bufferFlags, int sampleCount, long bufferPresentationTimeUs, boolean isDecodeOnlyBuffer, boolean isLastBuffer, Format format) throws ExoPlaybackException {
        Assertions.checkNotNull(buffer);
        if (this.decryptOnlyCodecFormat != null && (bufferFlags & 2) != 0) {
            ((MediaCodecAdapter) Assertions.checkNotNull(codec)).releaseOutputBuffer(bufferIndex, false);
            return true;
        }
        if (isDecodeOnlyBuffer) {
            if (codec != null) {
                codec.releaseOutputBuffer(bufferIndex, false);
            }
            this.decoderCounters.skippedOutputBufferCount += sampleCount;
            this.audioSink.handleDiscontinuity();
            return true;
        }
        try {
            if (!this.audioSink.handleBuffer(buffer, bufferPresentationTimeUs, sampleCount)) {
                return false;
            }
            if (codec != null) {
                codec.releaseOutputBuffer(bufferIndex, false);
            }
            this.decoderCounters.renderedOutputBufferCount += sampleCount;
            return true;
        } catch (AudioSink.InitializationException e) {
            throw createRendererException(e, e.format, e.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED);
        } catch (AudioSink.WriteException e2) {
            throw createRendererException(e2, format, e2.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void renderToEndOfStream() throws ExoPlaybackException {
        try {
            this.audioSink.playToEndOfStream();
        } catch (AudioSink.WriteException e) {
            throw createRendererException(e, e.format, e.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int messageType, Object message) throws ExoPlaybackException {
        if (messageType == 2) {
            this.audioSink.setVolume(((Float) message).floatValue());
        }
        if (messageType == 3) {
            this.audioSink.setAudioAttributes((AudioAttributes) message);
            return;
        }
        if (messageType == 5) {
            this.audioSink.setAuxEffectInfo((AuxEffectInfo) message);
            return;
        }
        switch (messageType) {
            case 101:
                this.audioSink.setSkipSilenceEnabled(((Boolean) message).booleanValue());
                break;
            case 102:
                this.audioSink.setAudioSessionId(((Integer) message).intValue());
                break;
            case 103:
                this.wakeupListener = (Renderer.WakeupListener) message;
                break;
            default:
                super.handleMessage(messageType, message);
                break;
        }
    }

    protected int getCodecMaxInputSize(MediaCodecInfo codecInfo, Format format, Format[] streamFormats) {
        int codecMaxInputSize = getCodecMaxInputSize(codecInfo, format);
        if (streamFormats.length == 1) {
            return codecMaxInputSize;
        }
        for (Format format2 : streamFormats) {
            if (codecInfo.canReuseCodec(format, format2).result != 0) {
                codecMaxInputSize = Math.max(codecMaxInputSize, getCodecMaxInputSize(codecInfo, format2));
            }
        }
        return codecMaxInputSize;
    }

    private int getCodecMaxInputSize(MediaCodecInfo codecInfo, Format format) {
        if (!"OMX.google.raw.decoder".equals(codecInfo.name) || Util.SDK_INT >= 24 || (Util.SDK_INT == 23 && Util.isTv(this.context))) {
            return format.maxInputSize;
        }
        return -1;
    }

    protected MediaFormat getMediaFormat(Format format, String codecMimeType, int codecMaxInputSize, float codecOperatingRate) {
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", codecMimeType);
        mediaFormat.setInteger("channel-count", format.channelCount);
        mediaFormat.setInteger("sample-rate", format.sampleRate);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxInputSize);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger(Constants.FirelogAnalytics.PARAM_PRIORITY, 0);
            if (codecOperatingRate != -1.0f && !deviceDoesntSupportOperatingRate()) {
                mediaFormat.setFloat("operating-rate", codecOperatingRate);
            }
        }
        if (Util.SDK_INT <= 28 && MimeTypes.AUDIO_AC4.equals(format.sampleMimeType)) {
            mediaFormat.setInteger("ac4-is-sync", 1);
        }
        if (Util.SDK_INT >= 24 && this.audioSink.getFormatSupport(Util.getPcmFormat(4, format.channelCount, format.sampleRate)) == 2) {
            mediaFormat.setInteger("pcm-encoding", 4);
        }
        return mediaFormat;
    }

    private void updateCurrentPosition() {
        long currentPositionUs = this.audioSink.getCurrentPositionUs(isEnded());
        if (currentPositionUs != Long.MIN_VALUE) {
            if (!this.allowPositionDiscontinuity) {
                currentPositionUs = Math.max(this.currentPositionUs, currentPositionUs);
            }
            this.currentPositionUs = currentPositionUs;
            this.allowPositionDiscontinuity = false;
        }
    }

    private static boolean deviceDoesntSupportOperatingRate() {
        return Util.SDK_INT == 23 && ("ZTE B2017G".equals(Util.MODEL) || "AXON 7 mini".equals(Util.MODEL));
    }

    private static boolean codecNeedsDiscardChannelsWorkaround(String codecName) {
        return Util.SDK_INT < 24 && "OMX.SEC.aac.dec".equals(codecName) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("zeroflte") || Util.DEVICE.startsWith("herolte") || Util.DEVICE.startsWith("heroqlte"));
    }

    private final class AudioSinkListener implements AudioSink.Listener {
        private AudioSinkListener() {
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onPositionDiscontinuity() {
            MediaCodecAudioRenderer.this.onPositionDiscontinuity();
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onPositionAdvancing(long playoutStartSystemTimeMs) {
            MediaCodecAudioRenderer.this.eventDispatcher.positionAdvancing(playoutStartSystemTimeMs);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
            MediaCodecAudioRenderer.this.eventDispatcher.underrun(bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onSkipSilenceEnabledChanged(boolean skipSilenceEnabled) {
            MediaCodecAudioRenderer.this.eventDispatcher.skipSilenceEnabledChanged(skipSilenceEnabled);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onOffloadBufferEmptying() {
            if (MediaCodecAudioRenderer.this.wakeupListener != null) {
                MediaCodecAudioRenderer.this.wakeupListener.onWakeup();
            }
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onOffloadBufferFull(long bufferEmptyingDeadlineMs) {
            if (MediaCodecAudioRenderer.this.wakeupListener != null) {
                MediaCodecAudioRenderer.this.wakeupListener.onSleep(bufferEmptyingDeadlineMs);
            }
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onAudioSinkError(Exception audioSinkError) {
            Log.e(MediaCodecAudioRenderer.TAG, "Audio sink error", audioSinkError);
            MediaCodecAudioRenderer.this.eventDispatcher.audioSinkError(audioSinkError);
        }
    }
}
