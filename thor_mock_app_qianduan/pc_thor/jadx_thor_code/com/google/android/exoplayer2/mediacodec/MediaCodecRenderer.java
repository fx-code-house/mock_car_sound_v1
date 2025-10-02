package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaCryptoException;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.SystemClock;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class MediaCodecRenderer extends BaseRenderer {
    private static final byte[] ADAPTATION_WORKAROUND_BUFFER = {0, 0, 1, 103, 66, -64, Ascii.VT, -38, 37, -112, 0, 0, 1, 104, -50, Ascii.SI, 19, 32, 0, 0, 1, 101, -120, -124, Ascii.CR, -50, 113, Ascii.CAN, -96, 0, 47, -65, Ascii.FS, 49, -61, 39, 93, 120};
    private static final int ADAPTATION_WORKAROUND_MODE_ALWAYS = 2;
    private static final int ADAPTATION_WORKAROUND_MODE_NEVER = 0;
    private static final int ADAPTATION_WORKAROUND_MODE_SAME_RESOLUTION = 1;
    private static final int ADAPTATION_WORKAROUND_SLICE_WIDTH_HEIGHT = 32;
    protected static final float CODEC_OPERATING_RATE_UNSET = -1.0f;
    private static final int DRAIN_ACTION_FLUSH = 1;
    private static final int DRAIN_ACTION_FLUSH_AND_UPDATE_DRM_SESSION = 2;
    private static final int DRAIN_ACTION_NONE = 0;
    private static final int DRAIN_ACTION_REINITIALIZE = 3;
    private static final int DRAIN_STATE_NONE = 0;
    private static final int DRAIN_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int DRAIN_STATE_WAIT_END_OF_STREAM = 2;
    private static final long MAX_CODEC_HOTSWAP_TIME_MS = 1000;
    private static final int MAX_PENDING_OUTPUT_STREAM_OFFSET_COUNT = 10;
    private static final int RECONFIGURATION_STATE_NONE = 0;
    private static final int RECONFIGURATION_STATE_QUEUE_PENDING = 2;
    private static final int RECONFIGURATION_STATE_WRITE_PENDING = 1;
    private static final String TAG = "MediaCodecRenderer";
    private final float assumedMinimumCodecOperatingRate;
    private ArrayDeque<MediaCodecInfo> availableCodecInfos;
    private final DecoderInputBuffer buffer;
    private final BatchBuffer bypassBatchBuffer;
    private boolean bypassDrainAndReinitialize;
    private boolean bypassEnabled;
    private final DecoderInputBuffer bypassSampleBuffer;
    private boolean bypassSampleBufferPending;
    private C2Mp3TimestampTracker c2Mp3TimestampTracker;
    private MediaCodecAdapter codec;
    private int codecAdaptationWorkaroundMode;
    private final MediaCodecAdapter.Factory codecAdapterFactory;
    private int codecDrainAction;
    private int codecDrainState;
    private DrmSession codecDrmSession;
    private boolean codecHasOutputMediaFormat;
    private long codecHotswapDeadlineMs;
    private MediaCodecInfo codecInfo;
    private Format codecInputFormat;
    private boolean codecNeedsAdaptationWorkaroundBuffer;
    private boolean codecNeedsDiscardToSpsWorkaround;
    private boolean codecNeedsEosBufferTimestampWorkaround;
    private boolean codecNeedsEosFlushWorkaround;
    private boolean codecNeedsEosOutputExceptionWorkaround;
    private boolean codecNeedsEosPropagation;
    private boolean codecNeedsFlushWorkaround;
    private boolean codecNeedsMonoChannelCountWorkaround;
    private boolean codecNeedsSosFlushWorkaround;
    private float codecOperatingRate;
    private MediaFormat codecOutputMediaFormat;
    private boolean codecOutputMediaFormatChanged;
    private boolean codecReceivedBuffers;
    private boolean codecReceivedEos;
    private int codecReconfigurationState;
    private boolean codecReconfigured;
    private float currentPlaybackSpeed;
    private final ArrayList<Long> decodeOnlyPresentationTimestamps;
    protected DecoderCounters decoderCounters;
    private boolean enableAsynchronousBufferQueueing;
    private final boolean enableDecoderFallback;
    private boolean enableSynchronizeCodecInteractionsWithQueueing;
    private boolean forceAsyncQueueingSynchronizationWorkaround;
    private final TimedValueQueue<Format> formatQueue;
    private Format inputFormat;
    private int inputIndex;
    private boolean inputStreamEnded;
    private boolean isDecodeOnlyOutputBuffer;
    private boolean isLastOutputBuffer;
    private long largestQueuedPresentationTimeUs;
    private long lastBufferInStreamPresentationTimeUs;
    private final MediaCodecSelector mediaCodecSelector;
    private MediaCrypto mediaCrypto;
    private boolean mediaCryptoRequiresSecureDecoder;
    private final DecoderInputBuffer noDataBuffer;
    private ByteBuffer outputBuffer;
    private final MediaCodec.BufferInfo outputBufferInfo;
    private Format outputFormat;
    private int outputIndex;
    private boolean outputStreamEnded;
    private long outputStreamOffsetUs;
    private long outputStreamStartPositionUs;
    private boolean pendingOutputEndOfStream;
    private int pendingOutputStreamOffsetCount;
    private final long[] pendingOutputStreamOffsetsUs;
    private final long[] pendingOutputStreamStartPositionsUs;
    private final long[] pendingOutputStreamSwitchTimesUs;
    private ExoPlaybackException pendingPlaybackException;
    private DecoderInitializationException preferredDecoderInitializationException;
    private long renderTimeLimitMs;
    private boolean shouldSkipAdaptationWorkaroundOutputBuffer;
    private DrmSession sourceDrmSession;
    private float targetPlaybackSpeed;
    private boolean waitingForFirstSampleInFormat;

    protected boolean getCodecNeedsEosPropagation() {
        return false;
    }

    protected float getCodecOperatingRateV23(float targetPlaybackSpeed, Format format, Format[] streamFormats) {
        return -1.0f;
    }

    protected abstract List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean requiresSecureDecoder) throws MediaCodecUtil.DecoderQueryException;

    protected abstract MediaCodecAdapter.Configuration getMediaCodecConfiguration(MediaCodecInfo codecInfo, Format format, MediaCrypto crypto, float codecOperatingRate);

    protected void handleInputBufferSupplementalData(DecoderInputBuffer buffer) throws ExoPlaybackException {
    }

    protected void onCodecError(Exception codecError) {
    }

    protected void onCodecInitialized(String name, long initializedTimestampMs, long initializationDurationMs) {
    }

    protected void onCodecReleased(String name) {
    }

    protected void onOutputFormatChanged(Format format, MediaFormat mediaFormat) throws ExoPlaybackException {
    }

    protected void onProcessedStreamChange() {
    }

    protected void onQueueInputBuffer(DecoderInputBuffer buffer) throws ExoPlaybackException {
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onStarted() {
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onStopped() {
    }

    protected abstract boolean processOutputBuffer(long positionUs, long elapsedRealtimeUs, MediaCodecAdapter codec, ByteBuffer buffer, int bufferIndex, int bufferFlags, int sampleCount, long bufferPresentationTimeUs, boolean isDecodeOnlyBuffer, boolean isLastBuffer, Format format) throws ExoPlaybackException;

    protected void renderToEndOfStream() throws ExoPlaybackException {
    }

    protected boolean shouldInitCodec(MediaCodecInfo codecInfo) {
        return true;
    }

    protected boolean shouldUseBypass(Format format) {
        return false;
    }

    protected abstract int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException;

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.RendererCapabilities
    public final int supportsMixedMimeTypeAdaptation() {
        return 8;
    }

    public static class DecoderInitializationException extends Exception {
        private static final int CUSTOM_ERROR_CODE_BASE = -50000;
        private static final int DECODER_QUERY_ERROR = -49998;
        private static final int NO_SUITABLE_DECODER_ERROR = -49999;
        public final MediaCodecInfo codecInfo;
        public final String diagnosticInfo;
        public final DecoderInitializationException fallbackDecoderInitializationException;
        public final String mimeType;
        public final boolean secureDecoderRequired;

        /* JADX WARN: Illegal instructions before constructor call */
        public DecoderInitializationException(Format format, Throwable cause, boolean secureDecoderRequired, int errorCode) {
            String strValueOf = String.valueOf(format);
            this(new StringBuilder(String.valueOf(strValueOf).length() + 36).append("Decoder init failed: [").append(errorCode).append("], ").append(strValueOf).toString(), cause, format.sampleMimeType, secureDecoderRequired, null, buildCustomDiagnosticInfo(errorCode), null);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public DecoderInitializationException(Format format, Throwable cause, boolean secureDecoderRequired, MediaCodecInfo mediaCodecInfo) {
            String str = mediaCodecInfo.name;
            String strValueOf = String.valueOf(format);
            this(new StringBuilder(String.valueOf(str).length() + 23 + String.valueOf(strValueOf).length()).append("Decoder init failed: ").append(str).append(", ").append(strValueOf).toString(), cause, format.sampleMimeType, secureDecoderRequired, mediaCodecInfo, Util.SDK_INT >= 21 ? getDiagnosticInfoV21(cause) : null, null);
        }

        private DecoderInitializationException(String message, Throwable cause, String mimeType, boolean secureDecoderRequired, MediaCodecInfo mediaCodecInfo, String diagnosticInfo, DecoderInitializationException fallbackDecoderInitializationException) {
            super(message, cause);
            this.mimeType = mimeType;
            this.secureDecoderRequired = secureDecoderRequired;
            this.codecInfo = mediaCodecInfo;
            this.diagnosticInfo = diagnosticInfo;
            this.fallbackDecoderInitializationException = fallbackDecoderInitializationException;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public DecoderInitializationException copyWithFallbackException(DecoderInitializationException fallbackException) {
            return new DecoderInitializationException(getMessage(), getCause(), this.mimeType, this.secureDecoderRequired, this.codecInfo, this.diagnosticInfo, fallbackException);
        }

        private static String getDiagnosticInfoV21(Throwable cause) {
            if (cause instanceof MediaCodec.CodecException) {
                return ((MediaCodec.CodecException) cause).getDiagnosticInfo();
            }
            return null;
        }

        private static String buildCustomDiagnosticInfo(int errorCode) {
            String str = errorCode < 0 ? "neg_" : "";
            return new StringBuilder(str.length() + 71).append("com.google.android.exoplayer2.mediacodec.MediaCodecRenderer_").append(str).append(Math.abs(errorCode)).toString();
        }
    }

    public MediaCodecRenderer(int trackType, MediaCodecAdapter.Factory codecAdapterFactory, MediaCodecSelector mediaCodecSelector, boolean enableDecoderFallback, float assumedMinimumCodecOperatingRate) {
        super(trackType);
        this.codecAdapterFactory = codecAdapterFactory;
        this.mediaCodecSelector = (MediaCodecSelector) Assertions.checkNotNull(mediaCodecSelector);
        this.enableDecoderFallback = enableDecoderFallback;
        this.assumedMinimumCodecOperatingRate = assumedMinimumCodecOperatingRate;
        this.noDataBuffer = DecoderInputBuffer.newNoDataInstance();
        this.buffer = new DecoderInputBuffer(0);
        this.bypassSampleBuffer = new DecoderInputBuffer(2);
        BatchBuffer batchBuffer = new BatchBuffer();
        this.bypassBatchBuffer = batchBuffer;
        this.formatQueue = new TimedValueQueue<>();
        this.decodeOnlyPresentationTimestamps = new ArrayList<>();
        this.outputBufferInfo = new MediaCodec.BufferInfo();
        this.currentPlaybackSpeed = 1.0f;
        this.targetPlaybackSpeed = 1.0f;
        this.renderTimeLimitMs = C.TIME_UNSET;
        this.pendingOutputStreamStartPositionsUs = new long[10];
        this.pendingOutputStreamOffsetsUs = new long[10];
        this.pendingOutputStreamSwitchTimesUs = new long[10];
        this.outputStreamStartPositionUs = C.TIME_UNSET;
        this.outputStreamOffsetUs = C.TIME_UNSET;
        batchBuffer.ensureSpaceForWrite(0);
        batchBuffer.data.order(ByteOrder.nativeOrder());
        this.codecOperatingRate = -1.0f;
        this.codecAdaptationWorkaroundMode = 0;
        this.codecReconfigurationState = 0;
        this.inputIndex = -1;
        this.outputIndex = -1;
        this.codecHotswapDeadlineMs = C.TIME_UNSET;
        this.largestQueuedPresentationTimeUs = C.TIME_UNSET;
        this.lastBufferInStreamPresentationTimeUs = C.TIME_UNSET;
        this.codecDrainState = 0;
        this.codecDrainAction = 0;
    }

    public void setRenderTimeLimitMs(long renderTimeLimitMs) {
        this.renderTimeLimitMs = renderTimeLimitMs;
    }

    public void experimentalSetAsynchronousBufferQueueingEnabled(boolean enabled) {
        this.enableAsynchronousBufferQueueing = enabled;
    }

    public void experimentalSetForceAsyncQueueingSynchronizationWorkaround(boolean enabled) {
        this.forceAsyncQueueingSynchronizationWorkaround = enabled;
    }

    public void experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(boolean enabled) {
        this.enableSynchronizeCodecInteractionsWithQueueing = enabled;
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public final int supportsFormat(Format format) throws ExoPlaybackException {
        try {
            return supportsFormat(this.mediaCodecSelector, format);
        } catch (MediaCodecUtil.DecoderQueryException e) {
            throw createRendererException(e, format, 4002);
        }
    }

    protected final void maybeInitCodecOrBypass() throws ExoPlaybackException {
        Format format;
        if (this.codec != null || this.bypassEnabled || (format = this.inputFormat) == null) {
            return;
        }
        if (this.sourceDrmSession == null && shouldUseBypass(format)) {
            initBypass(this.inputFormat);
            return;
        }
        setCodecDrmSession(this.sourceDrmSession);
        String str = this.inputFormat.sampleMimeType;
        DrmSession drmSession = this.codecDrmSession;
        if (drmSession != null) {
            if (this.mediaCrypto == null) {
                FrameworkMediaCrypto frameworkMediaCrypto = getFrameworkMediaCrypto(drmSession);
                if (frameworkMediaCrypto == null) {
                    if (this.codecDrmSession.getError() == null) {
                        return;
                    }
                } else {
                    try {
                        this.mediaCrypto = new MediaCrypto(frameworkMediaCrypto.uuid, frameworkMediaCrypto.sessionId);
                        this.mediaCryptoRequiresSecureDecoder = !frameworkMediaCrypto.forceAllowInsecureDecoderComponents && this.mediaCrypto.requiresSecureDecoderComponent(str);
                    } catch (MediaCryptoException e) {
                        throw createRendererException(e, this.inputFormat, PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR);
                    }
                }
            }
            if (FrameworkMediaCrypto.WORKAROUND_DEVICE_NEEDS_KEYS_TO_CONFIGURE_CODEC) {
                int state = this.codecDrmSession.getState();
                if (state == 1) {
                    DrmSession.DrmSessionException drmSessionException = (DrmSession.DrmSessionException) Assertions.checkNotNull(this.codecDrmSession.getError());
                    throw createRendererException(drmSessionException, this.inputFormat, drmSessionException.errorCode);
                }
                if (state != 4) {
                    return;
                }
            }
        }
        try {
            maybeInitCodecWithFallback(this.mediaCrypto, this.mediaCryptoRequiresSecureDecoder);
        } catch (DecoderInitializationException e2) {
            throw createRendererException(e2, this.inputFormat, 4001);
        }
    }

    protected final void setPendingPlaybackException(ExoPlaybackException exception) {
        this.pendingPlaybackException = exception;
    }

    protected final void updateOutputFormatForTime(long presentationTimeUs) throws ExoPlaybackException {
        boolean z;
        Format formatPollFloor = this.formatQueue.pollFloor(presentationTimeUs);
        if (formatPollFloor == null && this.codecOutputMediaFormatChanged) {
            formatPollFloor = this.formatQueue.pollFirst();
        }
        if (formatPollFloor != null) {
            this.outputFormat = formatPollFloor;
            z = true;
        } else {
            z = false;
        }
        if (z || (this.codecOutputMediaFormatChanged && this.outputFormat != null)) {
            onOutputFormatChanged(this.outputFormat, this.codecOutputMediaFormat);
            this.codecOutputMediaFormatChanged = false;
        }
    }

    protected final MediaCodecAdapter getCodec() {
        return this.codec;
    }

    protected final MediaFormat getCodecOutputMediaFormat() {
        return this.codecOutputMediaFormat;
    }

    protected final MediaCodecInfo getCodecInfo() {
        return this.codecInfo;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onEnabled(boolean joining, boolean mayRenderStartOfStream) throws ExoPlaybackException {
        this.decoderCounters = new DecoderCounters();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onStreamChanged(Format[] formats, long startPositionUs, long offsetUs) throws ExoPlaybackException {
        if (this.outputStreamOffsetUs == C.TIME_UNSET) {
            Assertions.checkState(this.outputStreamStartPositionUs == C.TIME_UNSET);
            this.outputStreamStartPositionUs = startPositionUs;
            this.outputStreamOffsetUs = offsetUs;
            return;
        }
        int i = this.pendingOutputStreamOffsetCount;
        long[] jArr = this.pendingOutputStreamOffsetsUs;
        if (i == jArr.length) {
            Log.w(TAG, new StringBuilder(65).append("Too many stream changes, so dropping offset: ").append(jArr[i - 1]).toString());
        } else {
            this.pendingOutputStreamOffsetCount = i + 1;
        }
        long[] jArr2 = this.pendingOutputStreamStartPositionsUs;
        int i2 = this.pendingOutputStreamOffsetCount;
        jArr2[i2 - 1] = startPositionUs;
        this.pendingOutputStreamOffsetsUs[i2 - 1] = offsetUs;
        this.pendingOutputStreamSwitchTimesUs[i2 - 1] = this.largestQueuedPresentationTimeUs;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        this.pendingOutputEndOfStream = false;
        if (this.bypassEnabled) {
            this.bypassBatchBuffer.clear();
            this.bypassSampleBuffer.clear();
            this.bypassSampleBufferPending = false;
        } else {
            flushOrReinitializeCodec();
        }
        if (this.formatQueue.size() > 0) {
            this.waitingForFirstSampleInFormat = true;
        }
        this.formatQueue.clear();
        int i = this.pendingOutputStreamOffsetCount;
        if (i != 0) {
            this.outputStreamOffsetUs = this.pendingOutputStreamOffsetsUs[i - 1];
            this.outputStreamStartPositionUs = this.pendingOutputStreamStartPositionsUs[i - 1];
            this.pendingOutputStreamOffsetCount = 0;
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void setPlaybackSpeed(float currentPlaybackSpeed, float targetPlaybackSpeed) throws ExoPlaybackException {
        this.currentPlaybackSpeed = currentPlaybackSpeed;
        this.targetPlaybackSpeed = targetPlaybackSpeed;
        updateCodecOperatingRate(this.codecInputFormat);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onDisabled() {
        this.inputFormat = null;
        this.outputStreamStartPositionUs = C.TIME_UNSET;
        this.outputStreamOffsetUs = C.TIME_UNSET;
        this.pendingOutputStreamOffsetCount = 0;
        flushOrReleaseCodec();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onReset() {
        try {
            disableBypass();
            releaseCodec();
        } finally {
            setSourceDrmSession(null);
        }
    }

    private void disableBypass() {
        this.bypassDrainAndReinitialize = false;
        this.bypassBatchBuffer.clear();
        this.bypassSampleBuffer.clear();
        this.bypassSampleBufferPending = false;
        this.bypassEnabled = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void releaseCodec() {
        try {
            MediaCodecAdapter mediaCodecAdapter = this.codec;
            if (mediaCodecAdapter != null) {
                mediaCodecAdapter.release();
                this.decoderCounters.decoderReleaseCount++;
                onCodecReleased(this.codecInfo.name);
            }
            this.codec = null;
            try {
                MediaCrypto mediaCrypto = this.mediaCrypto;
                if (mediaCrypto != null) {
                    mediaCrypto.release();
                }
            } finally {
            }
        } catch (Throwable th) {
            this.codec = null;
            try {
                MediaCrypto mediaCrypto2 = this.mediaCrypto;
                if (mediaCrypto2 != null) {
                    mediaCrypto2.release();
                }
                throw th;
            } finally {
            }
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException, MediaCryptoException {
        boolean z = false;
        if (this.pendingOutputEndOfStream) {
            this.pendingOutputEndOfStream = false;
            processEndOfStream();
        }
        ExoPlaybackException exoPlaybackException = this.pendingPlaybackException;
        if (exoPlaybackException != null) {
            this.pendingPlaybackException = null;
            throw exoPlaybackException;
        }
        try {
            if (this.outputStreamEnded) {
                renderToEndOfStream();
                return;
            }
            if (this.inputFormat != null || readSourceOmittingSampleData(2)) {
                maybeInitCodecOrBypass();
                if (this.bypassEnabled) {
                    TraceUtil.beginSection("bypassRender");
                    while (bypassRender(positionUs, elapsedRealtimeUs)) {
                    }
                    TraceUtil.endSection();
                } else if (this.codec != null) {
                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                    TraceUtil.beginSection("drainAndFeed");
                    while (drainOutputBuffer(positionUs, elapsedRealtimeUs) && shouldContinueRendering(jElapsedRealtime)) {
                    }
                    while (feedInputBuffer() && shouldContinueRendering(jElapsedRealtime)) {
                    }
                    TraceUtil.endSection();
                } else {
                    this.decoderCounters.skippedInputBufferCount += skipSource(positionUs);
                    readSourceOmittingSampleData(1);
                }
                this.decoderCounters.ensureUpdated();
            }
        } catch (IllegalStateException e) {
            if (isMediaCodecException(e)) {
                onCodecError(e);
                if (Util.SDK_INT >= 21 && isRecoverableMediaCodecExceptionV21(e)) {
                    z = true;
                }
                if (z) {
                    releaseCodec();
                }
                throw createRendererException(createDecoderException(e, getCodecInfo()), this.inputFormat, z, 4003);
            }
            throw e;
        }
    }

    protected final boolean flushOrReinitializeCodec() throws ExoPlaybackException {
        boolean zFlushOrReleaseCodec = flushOrReleaseCodec();
        if (zFlushOrReleaseCodec) {
            maybeInitCodecOrBypass();
        }
        return zFlushOrReleaseCodec;
    }

    protected boolean flushOrReleaseCodec() {
        if (this.codec == null) {
            return false;
        }
        if (this.codecDrainAction == 3 || this.codecNeedsFlushWorkaround || ((this.codecNeedsSosFlushWorkaround && !this.codecHasOutputMediaFormat) || (this.codecNeedsEosFlushWorkaround && this.codecReceivedEos))) {
            releaseCodec();
            return true;
        }
        flushCodec();
        return false;
    }

    private void flushCodec() {
        try {
            this.codec.flush();
        } finally {
            resetCodecStateForFlush();
        }
    }

    protected void resetCodecStateForFlush() {
        resetInputBuffer();
        resetOutputBuffer();
        this.codecHotswapDeadlineMs = C.TIME_UNSET;
        this.codecReceivedEos = false;
        this.codecReceivedBuffers = false;
        this.codecNeedsAdaptationWorkaroundBuffer = false;
        this.shouldSkipAdaptationWorkaroundOutputBuffer = false;
        this.isDecodeOnlyOutputBuffer = false;
        this.isLastOutputBuffer = false;
        this.decodeOnlyPresentationTimestamps.clear();
        this.largestQueuedPresentationTimeUs = C.TIME_UNSET;
        this.lastBufferInStreamPresentationTimeUs = C.TIME_UNSET;
        C2Mp3TimestampTracker c2Mp3TimestampTracker = this.c2Mp3TimestampTracker;
        if (c2Mp3TimestampTracker != null) {
            c2Mp3TimestampTracker.reset();
        }
        this.codecDrainState = 0;
        this.codecDrainAction = 0;
        this.codecReconfigurationState = this.codecReconfigured ? 1 : 0;
    }

    protected void resetCodecStateForRelease() {
        resetCodecStateForFlush();
        this.pendingPlaybackException = null;
        this.c2Mp3TimestampTracker = null;
        this.availableCodecInfos = null;
        this.codecInfo = null;
        this.codecInputFormat = null;
        this.codecOutputMediaFormat = null;
        this.codecOutputMediaFormatChanged = false;
        this.codecHasOutputMediaFormat = false;
        this.codecOperatingRate = -1.0f;
        this.codecAdaptationWorkaroundMode = 0;
        this.codecNeedsDiscardToSpsWorkaround = false;
        this.codecNeedsFlushWorkaround = false;
        this.codecNeedsSosFlushWorkaround = false;
        this.codecNeedsEosFlushWorkaround = false;
        this.codecNeedsEosOutputExceptionWorkaround = false;
        this.codecNeedsEosBufferTimestampWorkaround = false;
        this.codecNeedsMonoChannelCountWorkaround = false;
        this.codecNeedsEosPropagation = false;
        this.codecReconfigured = false;
        this.codecReconfigurationState = 0;
        this.mediaCryptoRequiresSecureDecoder = false;
    }

    protected MediaCodecDecoderException createDecoderException(Throwable cause, MediaCodecInfo codecInfo) {
        return new MediaCodecDecoderException(cause, codecInfo);
    }

    private boolean readSourceOmittingSampleData(int readFlags) throws ExoPlaybackException, MediaCryptoException {
        FormatHolder formatHolder = getFormatHolder();
        this.noDataBuffer.clear();
        int source = readSource(formatHolder, this.noDataBuffer, readFlags | 4);
        if (source == -5) {
            onInputFormatChanged(formatHolder);
            return true;
        }
        if (source != -4 || !this.noDataBuffer.isEndOfStream()) {
            return false;
        }
        this.inputStreamEnded = true;
        processEndOfStream();
        return false;
    }

    private void maybeInitCodecWithFallback(MediaCrypto crypto, boolean mediaCryptoRequiresSecureDecoder) throws DecoderInitializationException {
        if (this.availableCodecInfos == null) {
            try {
                List<MediaCodecInfo> availableCodecInfos = getAvailableCodecInfos(mediaCryptoRequiresSecureDecoder);
                ArrayDeque<MediaCodecInfo> arrayDeque = new ArrayDeque<>();
                this.availableCodecInfos = arrayDeque;
                if (this.enableDecoderFallback) {
                    arrayDeque.addAll(availableCodecInfos);
                } else if (!availableCodecInfos.isEmpty()) {
                    this.availableCodecInfos.add(availableCodecInfos.get(0));
                }
                this.preferredDecoderInitializationException = null;
            } catch (MediaCodecUtil.DecoderQueryException e) {
                throw new DecoderInitializationException(this.inputFormat, e, mediaCryptoRequiresSecureDecoder, -49998);
            }
        }
        if (this.availableCodecInfos.isEmpty()) {
            throw new DecoderInitializationException(this.inputFormat, (Throwable) null, mediaCryptoRequiresSecureDecoder, -49999);
        }
        while (this.codec == null) {
            MediaCodecInfo mediaCodecInfoPeekFirst = this.availableCodecInfos.peekFirst();
            if (!shouldInitCodec(mediaCodecInfoPeekFirst)) {
                return;
            }
            try {
                initCodec(mediaCodecInfoPeekFirst, crypto);
            } catch (Exception e2) {
                String strValueOf = String.valueOf(mediaCodecInfoPeekFirst);
                Log.w(TAG, new StringBuilder(String.valueOf(strValueOf).length() + 30).append("Failed to initialize decoder: ").append(strValueOf).toString(), e2);
                this.availableCodecInfos.removeFirst();
                DecoderInitializationException decoderInitializationException = new DecoderInitializationException(this.inputFormat, e2, mediaCryptoRequiresSecureDecoder, mediaCodecInfoPeekFirst);
                onCodecError(decoderInitializationException);
                if (this.preferredDecoderInitializationException != null) {
                    this.preferredDecoderInitializationException = this.preferredDecoderInitializationException.copyWithFallbackException(decoderInitializationException);
                } else {
                    this.preferredDecoderInitializationException = decoderInitializationException;
                }
                if (this.availableCodecInfos.isEmpty()) {
                    throw this.preferredDecoderInitializationException;
                }
            }
        }
        this.availableCodecInfos = null;
    }

    private List<MediaCodecInfo> getAvailableCodecInfos(boolean mediaCryptoRequiresSecureDecoder) throws MediaCodecUtil.DecoderQueryException {
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(this.mediaCodecSelector, this.inputFormat, mediaCryptoRequiresSecureDecoder);
        if (decoderInfos.isEmpty() && mediaCryptoRequiresSecureDecoder) {
            decoderInfos = getDecoderInfos(this.mediaCodecSelector, this.inputFormat, false);
            if (!decoderInfos.isEmpty()) {
                String str = this.inputFormat.sampleMimeType;
                String strValueOf = String.valueOf(decoderInfos);
                Log.w(TAG, new StringBuilder(String.valueOf(str).length() + 99 + String.valueOf(strValueOf).length()).append("Drm session requires secure decoder for ").append(str).append(", but no secure decoder available. Trying to proceed with ").append(strValueOf).append(".").toString());
            }
        }
        return decoderInfos;
    }

    private void initBypass(Format format) {
        disableBypass();
        String str = format.sampleMimeType;
        if (!MimeTypes.AUDIO_AAC.equals(str) && !MimeTypes.AUDIO_MPEG.equals(str) && !MimeTypes.AUDIO_OPUS.equals(str)) {
            this.bypassBatchBuffer.setMaxSampleCount(1);
        } else {
            this.bypassBatchBuffer.setMaxSampleCount(32);
        }
        this.bypassEnabled = true;
    }

    private void initCodec(MediaCodecInfo codecInfo, MediaCrypto crypto) throws Exception {
        MediaCodecAdapter mediaCodecAdapterCreateAdapter;
        String str = codecInfo.name;
        float codecOperatingRateV23 = Util.SDK_INT < 23 ? -1.0f : getCodecOperatingRateV23(this.targetPlaybackSpeed, this.inputFormat, getStreamFormats());
        float f = codecOperatingRateV23 > this.assumedMinimumCodecOperatingRate ? codecOperatingRateV23 : -1.0f;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        String strValueOf = String.valueOf(str);
        TraceUtil.beginSection(strValueOf.length() != 0 ? "createCodec:".concat(strValueOf) : new String("createCodec:"));
        MediaCodecAdapter.Configuration mediaCodecConfiguration = getMediaCodecConfiguration(codecInfo, this.inputFormat, crypto, f);
        if (this.enableAsynchronousBufferQueueing && Util.SDK_INT >= 23) {
            mediaCodecAdapterCreateAdapter = new AsynchronousMediaCodecAdapter.Factory(getTrackType(), this.forceAsyncQueueingSynchronizationWorkaround, this.enableSynchronizeCodecInteractionsWithQueueing).createAdapter(mediaCodecConfiguration);
        } else {
            mediaCodecAdapterCreateAdapter = this.codecAdapterFactory.createAdapter(mediaCodecConfiguration);
        }
        long jElapsedRealtime2 = SystemClock.elapsedRealtime();
        this.codec = mediaCodecAdapterCreateAdapter;
        this.codecInfo = codecInfo;
        this.codecOperatingRate = f;
        this.codecInputFormat = this.inputFormat;
        this.codecAdaptationWorkaroundMode = codecAdaptationWorkaroundMode(str);
        this.codecNeedsDiscardToSpsWorkaround = codecNeedsDiscardToSpsWorkaround(str, this.codecInputFormat);
        this.codecNeedsFlushWorkaround = codecNeedsFlushWorkaround(str);
        this.codecNeedsSosFlushWorkaround = codecNeedsSosFlushWorkaround(str);
        this.codecNeedsEosFlushWorkaround = codecNeedsEosFlushWorkaround(str);
        this.codecNeedsEosOutputExceptionWorkaround = codecNeedsEosOutputExceptionWorkaround(str);
        this.codecNeedsEosBufferTimestampWorkaround = codecNeedsEosBufferTimestampWorkaround(str);
        this.codecNeedsMonoChannelCountWorkaround = codecNeedsMonoChannelCountWorkaround(str, this.codecInputFormat);
        this.codecNeedsEosPropagation = codecNeedsEosPropagationWorkaround(codecInfo) || getCodecNeedsEosPropagation();
        if (mediaCodecAdapterCreateAdapter.needsReconfiguration()) {
            this.codecReconfigured = true;
            this.codecReconfigurationState = 1;
            this.codecNeedsAdaptationWorkaroundBuffer = this.codecAdaptationWorkaroundMode != 0;
        }
        if ("c2.android.mp3.decoder".equals(codecInfo.name)) {
            this.c2Mp3TimestampTracker = new C2Mp3TimestampTracker();
        }
        if (getState() == 2) {
            this.codecHotswapDeadlineMs = SystemClock.elapsedRealtime() + 1000;
        }
        this.decoderCounters.decoderInitCount++;
        onCodecInitialized(str, jElapsedRealtime2, jElapsedRealtime2 - jElapsedRealtime);
    }

    private boolean shouldContinueRendering(long renderStartTimeMs) {
        return this.renderTimeLimitMs == C.TIME_UNSET || SystemClock.elapsedRealtime() - renderStartTimeMs < this.renderTimeLimitMs;
    }

    private boolean hasOutputBuffer() {
        return this.outputIndex >= 0;
    }

    private void resetInputBuffer() {
        this.inputIndex = -1;
        this.buffer.data = null;
    }

    private void resetOutputBuffer() {
        this.outputIndex = -1;
        this.outputBuffer = null;
    }

    private void setSourceDrmSession(DrmSession session) {
        DrmSession.replaceSession(this.sourceDrmSession, session);
        this.sourceDrmSession = session;
    }

    private void setCodecDrmSession(DrmSession session) {
        DrmSession.replaceSession(this.codecDrmSession, session);
        this.codecDrmSession = session;
    }

    private boolean feedInputBuffer() throws ExoPlaybackException, MediaCryptoException {
        MediaCodecAdapter mediaCodecAdapter = this.codec;
        if (mediaCodecAdapter == null || this.codecDrainState == 2 || this.inputStreamEnded) {
            return false;
        }
        if (this.inputIndex < 0) {
            int iDequeueInputBufferIndex = mediaCodecAdapter.dequeueInputBufferIndex();
            this.inputIndex = iDequeueInputBufferIndex;
            if (iDequeueInputBufferIndex < 0) {
                return false;
            }
            this.buffer.data = this.codec.getInputBuffer(iDequeueInputBufferIndex);
            this.buffer.clear();
        }
        if (this.codecDrainState == 1) {
            if (!this.codecNeedsEosPropagation) {
                this.codecReceivedEos = true;
                this.codec.queueInputBuffer(this.inputIndex, 0, 0, 0L, 4);
                resetInputBuffer();
            }
            this.codecDrainState = 2;
            return false;
        }
        if (this.codecNeedsAdaptationWorkaroundBuffer) {
            this.codecNeedsAdaptationWorkaroundBuffer = false;
            ByteBuffer byteBuffer = this.buffer.data;
            byte[] bArr = ADAPTATION_WORKAROUND_BUFFER;
            byteBuffer.put(bArr);
            this.codec.queueInputBuffer(this.inputIndex, 0, bArr.length, 0L, 0);
            resetInputBuffer();
            this.codecReceivedBuffers = true;
            return true;
        }
        if (this.codecReconfigurationState == 1) {
            for (int i = 0; i < this.codecInputFormat.initializationData.size(); i++) {
                this.buffer.data.put(this.codecInputFormat.initializationData.get(i));
            }
            this.codecReconfigurationState = 2;
        }
        int iPosition = this.buffer.data.position();
        FormatHolder formatHolder = getFormatHolder();
        try {
            int source = readSource(formatHolder, this.buffer, 0);
            if (hasReadStreamToEnd()) {
                this.lastBufferInStreamPresentationTimeUs = this.largestQueuedPresentationTimeUs;
            }
            if (source == -3) {
                return false;
            }
            if (source == -5) {
                if (this.codecReconfigurationState == 2) {
                    this.buffer.clear();
                    this.codecReconfigurationState = 1;
                }
                onInputFormatChanged(formatHolder);
                return true;
            }
            if (this.buffer.isEndOfStream()) {
                if (this.codecReconfigurationState == 2) {
                    this.buffer.clear();
                    this.codecReconfigurationState = 1;
                }
                this.inputStreamEnded = true;
                if (!this.codecReceivedBuffers) {
                    processEndOfStream();
                    return false;
                }
                try {
                    if (!this.codecNeedsEosPropagation) {
                        this.codecReceivedEos = true;
                        this.codec.queueInputBuffer(this.inputIndex, 0, 0, 0L, 4);
                        resetInputBuffer();
                    }
                    return false;
                } catch (MediaCodec.CryptoException e) {
                    throw createRendererException(e, this.inputFormat, C.getErrorCodeForMediaDrmErrorCode(e.getErrorCode()));
                }
            }
            if (!this.codecReceivedBuffers && !this.buffer.isKeyFrame()) {
                this.buffer.clear();
                if (this.codecReconfigurationState == 2) {
                    this.codecReconfigurationState = 1;
                }
                return true;
            }
            boolean zIsEncrypted = this.buffer.isEncrypted();
            if (zIsEncrypted) {
                this.buffer.cryptoInfo.increaseClearDataFirstSubSampleBy(iPosition);
            }
            if (this.codecNeedsDiscardToSpsWorkaround && !zIsEncrypted) {
                NalUnitUtil.discardToSps(this.buffer.data);
                if (this.buffer.data.position() == 0) {
                    return true;
                }
                this.codecNeedsDiscardToSpsWorkaround = false;
            }
            long jUpdateAndGetPresentationTimeUs = this.buffer.timeUs;
            C2Mp3TimestampTracker c2Mp3TimestampTracker = this.c2Mp3TimestampTracker;
            if (c2Mp3TimestampTracker != null) {
                jUpdateAndGetPresentationTimeUs = c2Mp3TimestampTracker.updateAndGetPresentationTimeUs(this.inputFormat, this.buffer);
            }
            long j = jUpdateAndGetPresentationTimeUs;
            if (this.buffer.isDecodeOnly()) {
                this.decodeOnlyPresentationTimestamps.add(Long.valueOf(j));
            }
            if (this.waitingForFirstSampleInFormat) {
                this.formatQueue.add(j, this.inputFormat);
                this.waitingForFirstSampleInFormat = false;
            }
            if (this.c2Mp3TimestampTracker != null) {
                this.largestQueuedPresentationTimeUs = Math.max(this.largestQueuedPresentationTimeUs, this.buffer.timeUs);
            } else {
                this.largestQueuedPresentationTimeUs = Math.max(this.largestQueuedPresentationTimeUs, j);
            }
            this.buffer.flip();
            if (this.buffer.hasSupplementalData()) {
                handleInputBufferSupplementalData(this.buffer);
            }
            onQueueInputBuffer(this.buffer);
            try {
                if (zIsEncrypted) {
                    this.codec.queueSecureInputBuffer(this.inputIndex, 0, this.buffer.cryptoInfo, j, 0);
                } else {
                    this.codec.queueInputBuffer(this.inputIndex, 0, this.buffer.data.limit(), j, 0);
                }
                resetInputBuffer();
                this.codecReceivedBuffers = true;
                this.codecReconfigurationState = 0;
                this.decoderCounters.inputBufferCount++;
                return true;
            } catch (MediaCodec.CryptoException e2) {
                throw createRendererException(e2, this.inputFormat, C.getErrorCodeForMediaDrmErrorCode(e2.getErrorCode()));
            }
        } catch (DecoderInputBuffer.InsufficientCapacityException e3) {
            onCodecError(e3);
            readSourceOmittingSampleData(0);
            flushCodec();
            return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x00ce  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected com.google.android.exoplayer2.decoder.DecoderReuseEvaluation onInputFormatChanged(com.google.android.exoplayer2.FormatHolder r12) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            Method dump skipped, instructions count: 247
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.onInputFormatChanged(com.google.android.exoplayer2.FormatHolder):com.google.android.exoplayer2.decoder.DecoderReuseEvaluation");
    }

    protected void onProcessedOutputBuffer(long presentationTimeUs) {
        while (true) {
            int i = this.pendingOutputStreamOffsetCount;
            if (i == 0 || presentationTimeUs < this.pendingOutputStreamSwitchTimesUs[0]) {
                return;
            }
            long[] jArr = this.pendingOutputStreamStartPositionsUs;
            this.outputStreamStartPositionUs = jArr[0];
            this.outputStreamOffsetUs = this.pendingOutputStreamOffsetsUs[0];
            int i2 = i - 1;
            this.pendingOutputStreamOffsetCount = i2;
            System.arraycopy(jArr, 1, jArr, 0, i2);
            long[] jArr2 = this.pendingOutputStreamOffsetsUs;
            System.arraycopy(jArr2, 1, jArr2, 0, this.pendingOutputStreamOffsetCount);
            long[] jArr3 = this.pendingOutputStreamSwitchTimesUs;
            System.arraycopy(jArr3, 1, jArr3, 0, this.pendingOutputStreamOffsetCount);
            onProcessedStreamChange();
        }
    }

    protected DecoderReuseEvaluation canReuseCodec(MediaCodecInfo codecInfo, Format oldFormat, Format newFormat) {
        return new DecoderReuseEvaluation(codecInfo.name, oldFormat, newFormat, 0, 1);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return this.inputFormat != null && (isSourceReady() || hasOutputBuffer() || (this.codecHotswapDeadlineMs != C.TIME_UNSET && SystemClock.elapsedRealtime() < this.codecHotswapDeadlineMs));
    }

    protected float getPlaybackSpeed() {
        return this.currentPlaybackSpeed;
    }

    protected float getCodecOperatingRate() {
        return this.codecOperatingRate;
    }

    protected final boolean updateCodecOperatingRate() throws ExoPlaybackException {
        return updateCodecOperatingRate(this.codecInputFormat);
    }

    private boolean updateCodecOperatingRate(Format format) throws ExoPlaybackException {
        if (Util.SDK_INT >= 23 && this.codec != null && this.codecDrainAction != 3 && getState() != 0) {
            float codecOperatingRateV23 = getCodecOperatingRateV23(this.targetPlaybackSpeed, format, getStreamFormats());
            float f = this.codecOperatingRate;
            if (f == codecOperatingRateV23) {
                return true;
            }
            if (codecOperatingRateV23 == -1.0f) {
                drainAndReinitializeCodec();
                return false;
            }
            if (f == -1.0f && codecOperatingRateV23 <= this.assumedMinimumCodecOperatingRate) {
                return true;
            }
            Bundle bundle = new Bundle();
            bundle.putFloat("operating-rate", codecOperatingRateV23);
            this.codec.setParameters(bundle);
            this.codecOperatingRate = codecOperatingRateV23;
        }
        return true;
    }

    private boolean drainAndFlushCodec() {
        if (this.codecReceivedBuffers) {
            this.codecDrainState = 1;
            if (this.codecNeedsFlushWorkaround || this.codecNeedsEosFlushWorkaround) {
                this.codecDrainAction = 3;
                return false;
            }
            this.codecDrainAction = 1;
        }
        return true;
    }

    private boolean drainAndUpdateCodecDrmSessionV23() throws ExoPlaybackException, MediaCryptoException {
        if (this.codecReceivedBuffers) {
            this.codecDrainState = 1;
            if (this.codecNeedsFlushWorkaround || this.codecNeedsEosFlushWorkaround) {
                this.codecDrainAction = 3;
                return false;
            }
            this.codecDrainAction = 2;
        } else {
            updateDrmSessionV23();
        }
        return true;
    }

    private void drainAndReinitializeCodec() throws ExoPlaybackException {
        if (this.codecReceivedBuffers) {
            this.codecDrainState = 1;
            this.codecDrainAction = 3;
        } else {
            reinitializeCodec();
        }
    }

    private boolean drainOutputBuffer(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException, MediaCryptoException {
        boolean z;
        boolean zProcessOutputBuffer;
        int iDequeueOutputBufferIndex;
        if (!hasOutputBuffer()) {
            if (this.codecNeedsEosOutputExceptionWorkaround && this.codecReceivedEos) {
                try {
                    iDequeueOutputBufferIndex = this.codec.dequeueOutputBufferIndex(this.outputBufferInfo);
                } catch (IllegalStateException unused) {
                    processEndOfStream();
                    if (this.outputStreamEnded) {
                        releaseCodec();
                    }
                    return false;
                }
            } else {
                iDequeueOutputBufferIndex = this.codec.dequeueOutputBufferIndex(this.outputBufferInfo);
            }
            if (iDequeueOutputBufferIndex < 0) {
                if (iDequeueOutputBufferIndex == -2) {
                    processOutputMediaFormatChanged();
                    return true;
                }
                if (this.codecNeedsEosPropagation && (this.inputStreamEnded || this.codecDrainState == 2)) {
                    processEndOfStream();
                }
                return false;
            }
            if (this.shouldSkipAdaptationWorkaroundOutputBuffer) {
                this.shouldSkipAdaptationWorkaroundOutputBuffer = false;
                this.codec.releaseOutputBuffer(iDequeueOutputBufferIndex, false);
                return true;
            }
            if (this.outputBufferInfo.size == 0 && (this.outputBufferInfo.flags & 4) != 0) {
                processEndOfStream();
                return false;
            }
            this.outputIndex = iDequeueOutputBufferIndex;
            ByteBuffer outputBuffer = this.codec.getOutputBuffer(iDequeueOutputBufferIndex);
            this.outputBuffer = outputBuffer;
            if (outputBuffer != null) {
                outputBuffer.position(this.outputBufferInfo.offset);
                this.outputBuffer.limit(this.outputBufferInfo.offset + this.outputBufferInfo.size);
            }
            if (this.codecNeedsEosBufferTimestampWorkaround && this.outputBufferInfo.presentationTimeUs == 0 && (this.outputBufferInfo.flags & 4) != 0) {
                long j = this.largestQueuedPresentationTimeUs;
                if (j != C.TIME_UNSET) {
                    this.outputBufferInfo.presentationTimeUs = j;
                }
            }
            this.isDecodeOnlyOutputBuffer = isDecodeOnlyBuffer(this.outputBufferInfo.presentationTimeUs);
            this.isLastOutputBuffer = this.lastBufferInStreamPresentationTimeUs == this.outputBufferInfo.presentationTimeUs;
            updateOutputFormatForTime(this.outputBufferInfo.presentationTimeUs);
        }
        if (this.codecNeedsEosOutputExceptionWorkaround && this.codecReceivedEos) {
            try {
                z = false;
                try {
                    zProcessOutputBuffer = processOutputBuffer(positionUs, elapsedRealtimeUs, this.codec, this.outputBuffer, this.outputIndex, this.outputBufferInfo.flags, 1, this.outputBufferInfo.presentationTimeUs, this.isDecodeOnlyOutputBuffer, this.isLastOutputBuffer, this.outputFormat);
                } catch (IllegalStateException unused2) {
                    processEndOfStream();
                    if (this.outputStreamEnded) {
                        releaseCodec();
                    }
                    return z;
                }
            } catch (IllegalStateException unused3) {
                z = false;
            }
        } else {
            z = false;
            zProcessOutputBuffer = processOutputBuffer(positionUs, elapsedRealtimeUs, this.codec, this.outputBuffer, this.outputIndex, this.outputBufferInfo.flags, 1, this.outputBufferInfo.presentationTimeUs, this.isDecodeOnlyOutputBuffer, this.isLastOutputBuffer, this.outputFormat);
        }
        if (zProcessOutputBuffer) {
            onProcessedOutputBuffer(this.outputBufferInfo.presentationTimeUs);
            boolean z2 = (this.outputBufferInfo.flags & 4) != 0 ? true : z;
            resetOutputBuffer();
            if (!z2) {
                return true;
            }
            processEndOfStream();
        }
        return z;
    }

    private void processOutputMediaFormatChanged() {
        this.codecHasOutputMediaFormat = true;
        MediaFormat outputFormat = this.codec.getOutputFormat();
        if (this.codecAdaptationWorkaroundMode != 0 && outputFormat.getInteger("width") == 32 && outputFormat.getInteger("height") == 32) {
            this.shouldSkipAdaptationWorkaroundOutputBuffer = true;
            return;
        }
        if (this.codecNeedsMonoChannelCountWorkaround) {
            outputFormat.setInteger("channel-count", 1);
        }
        this.codecOutputMediaFormat = outputFormat;
        this.codecOutputMediaFormatChanged = true;
    }

    private void processEndOfStream() throws ExoPlaybackException, MediaCryptoException {
        int i = this.codecDrainAction;
        if (i == 1) {
            flushCodec();
            return;
        }
        if (i == 2) {
            flushCodec();
            updateDrmSessionV23();
        } else if (i == 3) {
            reinitializeCodec();
        } else {
            this.outputStreamEnded = true;
            renderToEndOfStream();
        }
    }

    protected final void setPendingOutputEndOfStream() {
        this.pendingOutputEndOfStream = true;
    }

    protected final long getOutputStreamOffsetUs() {
        return this.outputStreamOffsetUs;
    }

    protected static boolean supportsFormatDrm(Format format) {
        return format.exoMediaCryptoType == null || FrameworkMediaCrypto.class.equals(format.exoMediaCryptoType);
    }

    private boolean drmNeedsCodecReinitialization(MediaCodecInfo codecInfo, Format newFormat, DrmSession oldSession, DrmSession newSession) throws ExoPlaybackException {
        FrameworkMediaCrypto frameworkMediaCrypto;
        if (oldSession == newSession) {
            return false;
        }
        if (newSession == null || oldSession == null || Util.SDK_INT < 23 || C.PLAYREADY_UUID.equals(oldSession.getSchemeUuid()) || C.PLAYREADY_UUID.equals(newSession.getSchemeUuid()) || (frameworkMediaCrypto = getFrameworkMediaCrypto(newSession)) == null) {
            return true;
        }
        return !codecInfo.secure && maybeRequiresSecureDecoder(frameworkMediaCrypto, newFormat);
    }

    private boolean maybeRequiresSecureDecoder(FrameworkMediaCrypto sessionMediaCrypto, Format format) {
        if (sessionMediaCrypto.forceAllowInsecureDecoderComponents) {
            return false;
        }
        try {
            MediaCrypto mediaCrypto = new MediaCrypto(sessionMediaCrypto.uuid, sessionMediaCrypto.sessionId);
            try {
                return mediaCrypto.requiresSecureDecoderComponent(format.sampleMimeType);
            } finally {
                mediaCrypto.release();
            }
        } catch (MediaCryptoException unused) {
            return true;
        }
    }

    private void reinitializeCodec() throws ExoPlaybackException {
        releaseCodec();
        maybeInitCodecOrBypass();
    }

    private boolean isDecodeOnlyBuffer(long presentationTimeUs) {
        int size = this.decodeOnlyPresentationTimestamps.size();
        for (int i = 0; i < size; i++) {
            if (this.decodeOnlyPresentationTimestamps.get(i).longValue() == presentationTimeUs) {
                this.decodeOnlyPresentationTimestamps.remove(i);
                return true;
            }
        }
        return false;
    }

    private void updateDrmSessionV23() throws ExoPlaybackException, MediaCryptoException {
        try {
            this.mediaCrypto.setMediaDrmSession(getFrameworkMediaCrypto(this.sourceDrmSession).sessionId);
            setCodecDrmSession(this.sourceDrmSession);
            this.codecDrainState = 0;
            this.codecDrainAction = 0;
        } catch (MediaCryptoException e) {
            throw createRendererException(e, this.inputFormat, PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR);
        }
    }

    private FrameworkMediaCrypto getFrameworkMediaCrypto(DrmSession drmSession) throws ExoPlaybackException {
        ExoMediaCrypto mediaCrypto = drmSession.getMediaCrypto();
        if (mediaCrypto != null && !(mediaCrypto instanceof FrameworkMediaCrypto)) {
            String strValueOf = String.valueOf(mediaCrypto);
            throw createRendererException(new IllegalArgumentException(new StringBuilder(String.valueOf(strValueOf).length() + 42).append("Expecting FrameworkMediaCrypto but found: ").append(strValueOf).toString()), this.inputFormat, PlaybackException.ERROR_CODE_DRM_SCHEME_UNSUPPORTED);
        }
        return (FrameworkMediaCrypto) mediaCrypto;
    }

    private boolean bypassRender(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException {
        boolean z;
        Assertions.checkState(!this.outputStreamEnded);
        if (!this.bypassBatchBuffer.hasSamples()) {
            z = false;
        } else {
            if (!processOutputBuffer(positionUs, elapsedRealtimeUs, null, this.bypassBatchBuffer.data, this.outputIndex, 0, this.bypassBatchBuffer.getSampleCount(), this.bypassBatchBuffer.getFirstSampleTimeUs(), this.bypassBatchBuffer.isDecodeOnly(), this.bypassBatchBuffer.isEndOfStream(), this.outputFormat)) {
                return false;
            }
            onProcessedOutputBuffer(this.bypassBatchBuffer.getLastSampleTimeUs());
            this.bypassBatchBuffer.clear();
            z = false;
        }
        if (this.inputStreamEnded) {
            this.outputStreamEnded = true;
            return z;
        }
        if (this.bypassSampleBufferPending) {
            Assertions.checkState(this.bypassBatchBuffer.append(this.bypassSampleBuffer));
            this.bypassSampleBufferPending = z;
        }
        if (this.bypassDrainAndReinitialize) {
            if (this.bypassBatchBuffer.hasSamples()) {
                return true;
            }
            disableBypass();
            this.bypassDrainAndReinitialize = z;
            maybeInitCodecOrBypass();
            if (!this.bypassEnabled) {
                return z;
            }
        }
        bypassRead();
        if (this.bypassBatchBuffer.hasSamples()) {
            this.bypassBatchBuffer.flip();
        }
        if (this.bypassBatchBuffer.hasSamples() || this.inputStreamEnded || this.bypassDrainAndReinitialize) {
            return true;
        }
        return z;
    }

    private void bypassRead() throws ExoPlaybackException {
        Assertions.checkState(!this.inputStreamEnded);
        FormatHolder formatHolder = getFormatHolder();
        this.bypassSampleBuffer.clear();
        do {
            this.bypassSampleBuffer.clear();
            int source = readSource(formatHolder, this.bypassSampleBuffer, 0);
            if (source == -5) {
                onInputFormatChanged(formatHolder);
                return;
            }
            if (source != -4) {
                if (source != -3) {
                    throw new IllegalStateException();
                }
                return;
            } else {
                if (this.bypassSampleBuffer.isEndOfStream()) {
                    this.inputStreamEnded = true;
                    return;
                }
                if (this.waitingForFirstSampleInFormat) {
                    Format format = (Format) Assertions.checkNotNull(this.inputFormat);
                    this.outputFormat = format;
                    onOutputFormatChanged(format, null);
                    this.waitingForFirstSampleInFormat = false;
                }
                this.bypassSampleBuffer.flip();
            }
        } while (this.bypassBatchBuffer.append(this.bypassSampleBuffer));
        this.bypassSampleBufferPending = true;
    }

    private static boolean isMediaCodecException(IllegalStateException error) {
        if (Util.SDK_INT >= 21 && isMediaCodecExceptionV21(error)) {
            return true;
        }
        StackTraceElement[] stackTrace = error.getStackTrace();
        return stackTrace.length > 0 && stackTrace[0].getClassName().equals("android.media.MediaCodec");
    }

    private static boolean isMediaCodecExceptionV21(IllegalStateException error) {
        return error instanceof MediaCodec.CodecException;
    }

    private static boolean isRecoverableMediaCodecExceptionV21(IllegalStateException error) {
        if (error instanceof MediaCodec.CodecException) {
            return ((MediaCodec.CodecException) error).isRecoverable();
        }
        return false;
    }

    private static boolean codecNeedsFlushWorkaround(String name) {
        return Util.SDK_INT < 18 || (Util.SDK_INT == 18 && ("OMX.SEC.avc.dec".equals(name) || "OMX.SEC.avc.dec.secure".equals(name))) || (Util.SDK_INT == 19 && Util.MODEL.startsWith("SM-G800") && ("OMX.Exynos.avc.dec".equals(name) || "OMX.Exynos.avc.dec.secure".equals(name)));
    }

    private int codecAdaptationWorkaroundMode(String name) {
        if (Util.SDK_INT <= 25 && "OMX.Exynos.avc.dec.secure".equals(name) && (Util.MODEL.startsWith("SM-T585") || Util.MODEL.startsWith("SM-A510") || Util.MODEL.startsWith("SM-A520") || Util.MODEL.startsWith("SM-J700"))) {
            return 2;
        }
        if (Util.SDK_INT >= 24) {
            return 0;
        }
        if ("OMX.Nvidia.h264.decode".equals(name) || "OMX.Nvidia.h264.decode.secure".equals(name)) {
            return ("flounder".equals(Util.DEVICE) || "flounder_lte".equals(Util.DEVICE) || "grouper".equals(Util.DEVICE) || "tilapia".equals(Util.DEVICE)) ? 1 : 0;
        }
        return 0;
    }

    private static boolean codecNeedsDiscardToSpsWorkaround(String name, Format format) {
        return Util.SDK_INT < 21 && format.initializationData.isEmpty() && "OMX.MTK.VIDEO.DECODER.AVC".equals(name);
    }

    private static boolean codecNeedsSosFlushWorkaround(String name) {
        return Util.SDK_INT == 29 && "c2.android.aac.decoder".equals(name);
    }

    private static boolean codecNeedsEosPropagationWorkaround(MediaCodecInfo codecInfo) {
        String str = codecInfo.name;
        return (Util.SDK_INT <= 25 && "OMX.rk.video_decoder.avc".equals(str)) || (Util.SDK_INT <= 17 && "OMX.allwinner.video.decoder.avc".equals(str)) || ((Util.SDK_INT <= 29 && ("OMX.broadcom.video_decoder.tunnel".equals(str) || "OMX.broadcom.video_decoder.tunnel.secure".equals(str))) || ("Amazon".equals(Util.MANUFACTURER) && "AFTS".equals(Util.MODEL) && codecInfo.secure));
    }

    private static boolean codecNeedsEosFlushWorkaround(String name) {
        return (Util.SDK_INT <= 23 && "OMX.google.vorbis.decoder".equals(name)) || (Util.SDK_INT <= 19 && (("hb2000".equals(Util.DEVICE) || "stvm8".equals(Util.DEVICE)) && ("OMX.amlogic.avc.decoder.awesome".equals(name) || "OMX.amlogic.avc.decoder.awesome.secure".equals(name))));
    }

    private static boolean codecNeedsEosBufferTimestampWorkaround(String codecName) {
        return Util.SDK_INT < 21 && "OMX.SEC.mp3.dec".equals(codecName) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("baffin") || Util.DEVICE.startsWith("grand") || Util.DEVICE.startsWith("fortuna") || Util.DEVICE.startsWith("gprimelte") || Util.DEVICE.startsWith("j2y18lte") || Util.DEVICE.startsWith("ms01"));
    }

    private static boolean codecNeedsEosOutputExceptionWorkaround(String name) {
        return Util.SDK_INT == 21 && "OMX.google.aac.decoder".equals(name);
    }

    private static boolean codecNeedsMonoChannelCountWorkaround(String name, Format format) {
        return Util.SDK_INT <= 18 && format.channelCount == 1 && "OMX.MTK.AUDIO.DECODER.MP3".equals(name);
    }
}
