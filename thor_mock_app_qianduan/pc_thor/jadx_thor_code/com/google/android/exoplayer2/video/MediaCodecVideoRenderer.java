package com.google.android.exoplayer2.video;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import android.view.Surface;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecDecoderException;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.gms.common.Scopes;
import com.google.firebase.messaging.Constants;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class MediaCodecVideoRenderer extends MediaCodecRenderer {
    private static final float INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR = 1.5f;
    private static final String KEY_CROP_BOTTOM = "crop-bottom";
    private static final String KEY_CROP_LEFT = "crop-left";
    private static final String KEY_CROP_RIGHT = "crop-right";
    private static final String KEY_CROP_TOP = "crop-top";
    private static final int[] STANDARD_LONG_EDGE_VIDEO_PX = {1920, 1600, 1440, 1280, 960, 854, 640, 540, 480};
    private static final String TAG = "MediaCodecVideoRenderer";
    private static final long TUNNELING_EOS_PRESENTATION_TIME_US = Long.MAX_VALUE;
    private static boolean deviceNeedsSetOutputSurfaceWorkaround;
    private static boolean evaluatedDeviceNeedsSetOutputSurfaceWorkaround;
    private final long allowedJoiningTimeMs;
    private int buffersInCodecCount;
    private boolean codecHandlesHdr10PlusOutOfBandMetadata;
    private CodecMaxValues codecMaxValues;
    private boolean codecNeedsSetOutputSurfaceWorkaround;
    private int consecutiveDroppedFrameCount;
    private final Context context;
    private int currentHeight;
    private float currentPixelWidthHeightRatio;
    private int currentUnappliedRotationDegrees;
    private int currentWidth;
    private final boolean deviceNeedsNoPostProcessWorkaround;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;
    private DummySurface dummySurface;
    private final VideoRendererEventListener.EventDispatcher eventDispatcher;
    private VideoFrameMetadataListener frameMetadataListener;
    private final VideoFrameReleaseHelper frameReleaseHelper;
    private boolean haveReportedFirstFrameRenderedForCurrentSurface;
    private long initialPositionUs;
    private long joiningDeadlineMs;
    private long lastBufferPresentationTimeUs;
    private long lastRenderRealtimeUs;
    private final int maxDroppedFramesToNotify;
    private boolean mayRenderFirstFrameAfterEnableIfNotStarted;
    private boolean renderedFirstFrameAfterEnable;
    private boolean renderedFirstFrameAfterReset;
    private VideoSize reportedVideoSize;
    private int scalingMode;
    private Surface surface;
    private long totalVideoFrameProcessingOffsetUs;
    private boolean tunneling;
    private int tunnelingAudioSessionId;
    OnFrameRenderedListenerV23 tunnelingOnFrameRenderedListener;
    private int videoFrameProcessingOffsetCount;

    private static boolean isBufferLate(long earlyUs) {
        return earlyUs < -30000;
    }

    private static boolean isBufferVeryLate(long earlyUs) {
        return earlyUs < -500000;
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, 0L);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs) {
        this(context, mediaCodecSelector, allowedJoiningTimeMs, null, null, 0);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs, Handler eventHandler, VideoRendererEventListener eventListener, int maxDroppedFramesToNotify) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, allowedJoiningTimeMs, false, eventHandler, eventListener, maxDroppedFramesToNotify);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs, boolean enableDecoderFallback, Handler eventHandler, VideoRendererEventListener eventListener, int maxDroppedFramesToNotify) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, allowedJoiningTimeMs, enableDecoderFallback, eventHandler, eventListener, maxDroppedFramesToNotify);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecAdapter.Factory codecAdapterFactory, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs, boolean enableDecoderFallback, Handler eventHandler, VideoRendererEventListener eventListener, int maxDroppedFramesToNotify) {
        super(2, codecAdapterFactory, mediaCodecSelector, enableDecoderFallback, 30.0f);
        this.allowedJoiningTimeMs = allowedJoiningTimeMs;
        this.maxDroppedFramesToNotify = maxDroppedFramesToNotify;
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        this.frameReleaseHelper = new VideoFrameReleaseHelper(applicationContext);
        this.eventDispatcher = new VideoRendererEventListener.EventDispatcher(eventHandler, eventListener);
        this.deviceNeedsNoPostProcessWorkaround = deviceNeedsNoPostProcessWorkaround();
        this.joiningDeadlineMs = C.TIME_UNSET;
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.scalingMode = 1;
        this.tunnelingAudioSessionId = 0;
        clearReportedVideoSize();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException {
        int i = 0;
        if (!MimeTypes.isVideo(format.sampleMimeType)) {
            return RendererCapabilities.create(0);
        }
        boolean z = format.drmInitData != null;
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(mediaCodecSelector, format, z, false);
        if (z && decoderInfos.isEmpty()) {
            decoderInfos = getDecoderInfos(mediaCodecSelector, format, false, false);
        }
        if (decoderInfos.isEmpty()) {
            return RendererCapabilities.create(1);
        }
        if (!supportsFormatDrm(format)) {
            return RendererCapabilities.create(2);
        }
        MediaCodecInfo mediaCodecInfo = decoderInfos.get(0);
        boolean zIsFormatSupported = mediaCodecInfo.isFormatSupported(format);
        int i2 = mediaCodecInfo.isSeamlessAdaptationSupported(format) ? 16 : 8;
        if (zIsFormatSupported) {
            List<MediaCodecInfo> decoderInfos2 = getDecoderInfos(mediaCodecSelector, format, z, true);
            if (!decoderInfos2.isEmpty()) {
                MediaCodecInfo mediaCodecInfo2 = decoderInfos2.get(0);
                if (mediaCodecInfo2.isFormatSupported(format) && mediaCodecInfo2.isSeamlessAdaptationSupported(format)) {
                    i = 32;
                }
            }
        }
        return RendererCapabilities.create(zIsFormatSupported ? 4 : 3, i2, i);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean requiresSecureDecoder) throws MediaCodecUtil.DecoderQueryException {
        return getDecoderInfos(mediaCodecSelector, format, requiresSecureDecoder, this.tunneling);
    }

    private static List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean requiresSecureDecoder, boolean requiresTunnelingDecoder) throws MediaCodecUtil.DecoderQueryException {
        Pair<Integer, Integer> codecProfileAndLevel;
        String str = format.sampleMimeType;
        if (str == null) {
            return Collections.emptyList();
        }
        List<MediaCodecInfo> decoderInfosSortedByFormatSupport = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(mediaCodecSelector.getDecoderInfos(str, requiresSecureDecoder, requiresTunnelingDecoder), format);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(str) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format)) != null) {
            int iIntValue = ((Integer) codecProfileAndLevel.first).intValue();
            if (iIntValue == 16 || iIntValue == 256) {
                decoderInfosSortedByFormatSupport.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.VIDEO_H265, requiresSecureDecoder, requiresTunnelingDecoder));
            } else if (iIntValue == 512) {
                decoderInfosSortedByFormatSupport.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.VIDEO_H264, requiresSecureDecoder, requiresTunnelingDecoder));
            }
        }
        return Collections.unmodifiableList(decoderInfosSortedByFormatSupport);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onEnabled(boolean joining, boolean mayRenderStartOfStream) throws ExoPlaybackException {
        super.onEnabled(joining, mayRenderStartOfStream);
        boolean z = getConfiguration().tunneling;
        Assertions.checkState((z && this.tunnelingAudioSessionId == 0) ? false : true);
        if (this.tunneling != z) {
            this.tunneling = z;
            releaseCodec();
        }
        this.eventDispatcher.enabled(this.decoderCounters);
        this.frameReleaseHelper.onEnabled();
        this.mayRenderFirstFrameAfterEnableIfNotStarted = mayRenderStartOfStream;
        this.renderedFirstFrameAfterEnable = false;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
        super.onPositionReset(positionUs, joining);
        clearRenderedFirstFrame();
        this.frameReleaseHelper.onPositionReset();
        this.lastBufferPresentationTimeUs = C.TIME_UNSET;
        this.initialPositionUs = C.TIME_UNSET;
        this.consecutiveDroppedFrameCount = 0;
        if (joining) {
            setJoiningDeadlineMs();
        } else {
            this.joiningDeadlineMs = C.TIME_UNSET;
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        DummySurface dummySurface;
        if (super.isReady() && (this.renderedFirstFrameAfterReset || (((dummySurface = this.dummySurface) != null && this.surface == dummySurface) || getCodec() == null || this.tunneling))) {
            this.joiningDeadlineMs = C.TIME_UNSET;
            return true;
        }
        if (this.joiningDeadlineMs == C.TIME_UNSET) {
            return false;
        }
        if (SystemClock.elapsedRealtime() < this.joiningDeadlineMs) {
            return true;
        }
        this.joiningDeadlineMs = C.TIME_UNSET;
        return false;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onStarted() {
        super.onStarted();
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
        this.lastRenderRealtimeUs = SystemClock.elapsedRealtime() * 1000;
        this.totalVideoFrameProcessingOffsetUs = 0L;
        this.videoFrameProcessingOffsetCount = 0;
        this.frameReleaseHelper.onStarted();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onStopped() {
        this.joiningDeadlineMs = C.TIME_UNSET;
        maybeNotifyDroppedFrames();
        maybeNotifyVideoFrameProcessingOffset();
        this.frameReleaseHelper.onStopped();
        super.onStopped();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onDisabled() {
        clearReportedVideoSize();
        clearRenderedFirstFrame();
        this.haveReportedFirstFrameRenderedForCurrentSurface = false;
        this.frameReleaseHelper.onDisabled();
        this.tunnelingOnFrameRenderedListener = null;
        try {
            super.onDisabled();
        } finally {
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    protected void onReset() {
        try {
            super.onReset();
            DummySurface dummySurface = this.dummySurface;
            if (dummySurface != null) {
                if (this.surface == dummySurface) {
                    this.surface = null;
                }
                dummySurface.release();
                this.dummySurface = null;
            }
        } catch (Throwable th) {
            if (this.dummySurface != null) {
                Surface surface = this.surface;
                DummySurface dummySurface2 = this.dummySurface;
                if (surface == dummySurface2) {
                    this.surface = null;
                }
                dummySurface2.release();
                this.dummySurface = null;
            }
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int messageType, Object message) throws ExoPlaybackException {
        if (messageType == 1) {
            setOutput(message);
            return;
        }
        if (messageType == 4) {
            this.scalingMode = ((Integer) message).intValue();
            MediaCodecAdapter codec = getCodec();
            if (codec != null) {
                codec.setVideoScalingMode(this.scalingMode);
                return;
            }
            return;
        }
        if (messageType == 6) {
            this.frameMetadataListener = (VideoFrameMetadataListener) message;
            return;
        }
        if (messageType == 102) {
            int iIntValue = ((Integer) message).intValue();
            if (this.tunnelingAudioSessionId != iIntValue) {
                this.tunnelingAudioSessionId = iIntValue;
                if (this.tunneling) {
                    releaseCodec();
                    return;
                }
                return;
            }
            return;
        }
        super.handleMessage(messageType, message);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.google.android.exoplayer2.video.MediaCodecVideoRenderer] */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.view.Surface] */
    private void setOutput(Object obj) throws ExoPlaybackException {
        DummySurface dummySurfaceNewInstanceV17 = obj instanceof Surface ? (Surface) obj : null;
        if (dummySurfaceNewInstanceV17 == null) {
            DummySurface dummySurface = this.dummySurface;
            if (dummySurface != null) {
                dummySurfaceNewInstanceV17 = dummySurface;
            } else {
                MediaCodecInfo codecInfo = getCodecInfo();
                if (codecInfo != null && shouldUseDummySurface(codecInfo)) {
                    dummySurfaceNewInstanceV17 = DummySurface.newInstanceV17(this.context, codecInfo.secure);
                    this.dummySurface = dummySurfaceNewInstanceV17;
                }
            }
        }
        if (this.surface != dummySurfaceNewInstanceV17) {
            this.surface = dummySurfaceNewInstanceV17;
            this.frameReleaseHelper.onSurfaceChanged(dummySurfaceNewInstanceV17);
            this.haveReportedFirstFrameRenderedForCurrentSurface = false;
            int state = getState();
            MediaCodecAdapter codec = getCodec();
            if (codec != null) {
                if (Util.SDK_INT >= 23 && dummySurfaceNewInstanceV17 != null && !this.codecNeedsSetOutputSurfaceWorkaround) {
                    setOutputSurfaceV23(codec, dummySurfaceNewInstanceV17);
                } else {
                    releaseCodec();
                    maybeInitCodecOrBypass();
                }
            }
            if (dummySurfaceNewInstanceV17 != null && dummySurfaceNewInstanceV17 != this.dummySurface) {
                maybeRenotifyVideoSizeChanged();
                clearRenderedFirstFrame();
                if (state == 2) {
                    setJoiningDeadlineMs();
                    return;
                }
                return;
            }
            clearReportedVideoSize();
            clearRenderedFirstFrame();
            return;
        }
        if (dummySurfaceNewInstanceV17 == null || dummySurfaceNewInstanceV17 == this.dummySurface) {
            return;
        }
        maybeRenotifyVideoSizeChanged();
        maybeRenotifyRenderedFirstFrame();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected boolean shouldInitCodec(MediaCodecInfo codecInfo) {
        return this.surface != null || shouldUseDummySurface(codecInfo);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected boolean getCodecNeedsEosPropagation() {
        return this.tunneling && Util.SDK_INT < 23;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected MediaCodecAdapter.Configuration getMediaCodecConfiguration(MediaCodecInfo codecInfo, Format format, MediaCrypto crypto, float codecOperatingRate) {
        DummySurface dummySurface = this.dummySurface;
        if (dummySurface != null && dummySurface.secure != codecInfo.secure) {
            this.dummySurface.release();
            this.dummySurface = null;
        }
        String str = codecInfo.codecMimeType;
        CodecMaxValues codecMaxValues = getCodecMaxValues(codecInfo, format, getStreamFormats());
        this.codecMaxValues = codecMaxValues;
        MediaFormat mediaFormat = getMediaFormat(format, str, codecMaxValues, codecOperatingRate, this.deviceNeedsNoPostProcessWorkaround, this.tunneling ? this.tunnelingAudioSessionId : 0);
        if (this.surface == null) {
            if (!shouldUseDummySurface(codecInfo)) {
                throw new IllegalStateException();
            }
            if (this.dummySurface == null) {
                this.dummySurface = DummySurface.newInstanceV17(this.context, codecInfo.secure);
            }
            this.surface = this.dummySurface;
        }
        return new MediaCodecAdapter.Configuration(codecInfo, mediaFormat, format, this.surface, crypto, 0);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected DecoderReuseEvaluation canReuseCodec(MediaCodecInfo codecInfo, Format oldFormat, Format newFormat) {
        DecoderReuseEvaluation decoderReuseEvaluationCanReuseCodec = codecInfo.canReuseCodec(oldFormat, newFormat);
        int i = decoderReuseEvaluationCanReuseCodec.discardReasons;
        if (newFormat.width > this.codecMaxValues.width || newFormat.height > this.codecMaxValues.height) {
            i |= 256;
        }
        if (getMaxInputSize(codecInfo, newFormat) > this.codecMaxValues.inputSize) {
            i |= 64;
        }
        int i2 = i;
        return new DecoderReuseEvaluation(codecInfo.name, oldFormat, newFormat, i2 != 0 ? 0 : decoderReuseEvaluationCanReuseCodec.result, i2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void resetCodecStateForFlush() {
        super.resetCodecStateForFlush();
        this.buffersInCodecCount = 0;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public void setPlaybackSpeed(float currentPlaybackSpeed, float targetPlaybackSpeed) throws ExoPlaybackException {
        super.setPlaybackSpeed(currentPlaybackSpeed, targetPlaybackSpeed);
        this.frameReleaseHelper.onPlaybackSpeed(currentPlaybackSpeed);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected float getCodecOperatingRateV23(float targetPlaybackSpeed, Format format, Format[] streamFormats) {
        float fMax = -1.0f;
        for (Format format2 : streamFormats) {
            float f = format2.frameRate;
            if (f != -1.0f) {
                fMax = Math.max(fMax, f);
            }
        }
        if (fMax == -1.0f) {
            return -1.0f;
        }
        return fMax * targetPlaybackSpeed;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onCodecInitialized(String name, long initializedTimestampMs, long initializationDurationMs) {
        this.eventDispatcher.decoderInitialized(name, initializedTimestampMs, initializationDurationMs);
        this.codecNeedsSetOutputSurfaceWorkaround = codecNeedsSetOutputSurfaceWorkaround(name);
        this.codecHandlesHdr10PlusOutOfBandMetadata = ((MediaCodecInfo) Assertions.checkNotNull(getCodecInfo())).isHdr10PlusOutOfBandMetadataSupported();
        if (Util.SDK_INT < 23 || !this.tunneling) {
            return;
        }
        this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23((MediaCodecAdapter) Assertions.checkNotNull(getCodec()));
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onCodecReleased(String name) {
        this.eventDispatcher.decoderReleased(name);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onCodecError(Exception codecError) {
        Log.e(TAG, "Video codec error", codecError);
        this.eventDispatcher.videoCodecError(codecError);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected DecoderReuseEvaluation onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        DecoderReuseEvaluation decoderReuseEvaluationOnInputFormatChanged = super.onInputFormatChanged(formatHolder);
        this.eventDispatcher.inputFormatChanged(formatHolder.format, decoderReuseEvaluationOnInputFormatChanged);
        return decoderReuseEvaluationOnInputFormatChanged;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onQueueInputBuffer(DecoderInputBuffer buffer) throws ExoPlaybackException {
        if (!this.tunneling) {
            this.buffersInCodecCount++;
        }
        if (Util.SDK_INT >= 23 || !this.tunneling) {
            return;
        }
        onProcessedTunneledBuffer(buffer.timeUs);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onOutputFormatChanged(Format format, MediaFormat mediaFormat) {
        int integer;
        int integer2;
        MediaCodecAdapter codec = getCodec();
        if (codec != null) {
            codec.setVideoScalingMode(this.scalingMode);
        }
        if (this.tunneling) {
            this.currentWidth = format.width;
            this.currentHeight = format.height;
        } else {
            Assertions.checkNotNull(mediaFormat);
            boolean z = mediaFormat.containsKey(KEY_CROP_RIGHT) && mediaFormat.containsKey(KEY_CROP_LEFT) && mediaFormat.containsKey(KEY_CROP_BOTTOM) && mediaFormat.containsKey(KEY_CROP_TOP);
            if (z) {
                integer = (mediaFormat.getInteger(KEY_CROP_RIGHT) - mediaFormat.getInteger(KEY_CROP_LEFT)) + 1;
            } else {
                integer = mediaFormat.getInteger("width");
            }
            this.currentWidth = integer;
            if (z) {
                integer2 = (mediaFormat.getInteger(KEY_CROP_BOTTOM) - mediaFormat.getInteger(KEY_CROP_TOP)) + 1;
            } else {
                integer2 = mediaFormat.getInteger("height");
            }
            this.currentHeight = integer2;
        }
        this.currentPixelWidthHeightRatio = format.pixelWidthHeightRatio;
        if (Util.SDK_INT >= 21) {
            if (format.rotationDegrees == 90 || format.rotationDegrees == 270) {
                int i = this.currentWidth;
                this.currentWidth = this.currentHeight;
                this.currentHeight = i;
                this.currentPixelWidthHeightRatio = 1.0f / this.currentPixelWidthHeightRatio;
            }
        } else {
            this.currentUnappliedRotationDegrees = format.rotationDegrees;
        }
        this.frameReleaseHelper.onFormatChanged(format.frameRate);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void handleInputBufferSupplementalData(DecoderInputBuffer buffer) throws ExoPlaybackException {
        if (this.codecHandlesHdr10PlusOutOfBandMetadata) {
            ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(buffer.supplementalData);
            if (byteBuffer.remaining() >= 7) {
                byte b = byteBuffer.get();
                short s = byteBuffer.getShort();
                short s2 = byteBuffer.getShort();
                byte b2 = byteBuffer.get();
                byte b3 = byteBuffer.get();
                byteBuffer.position(0);
                if (b == -75 && s == 60 && s2 == 1 && b2 == 4 && b3 == 0) {
                    byte[] bArr = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bArr);
                    byteBuffer.position(0);
                    setHdr10PlusInfoV29(getCodec(), bArr);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected boolean processOutputBuffer(long positionUs, long elapsedRealtimeUs, MediaCodecAdapter codec, ByteBuffer buffer, int bufferIndex, int bufferFlags, int sampleCount, long bufferPresentationTimeUs, boolean isDecodeOnlyBuffer, boolean isLastBuffer, Format format) throws ExoPlaybackException, InterruptedException {
        boolean z;
        long j;
        Assertions.checkNotNull(codec);
        if (this.initialPositionUs == C.TIME_UNSET) {
            this.initialPositionUs = positionUs;
        }
        if (bufferPresentationTimeUs != this.lastBufferPresentationTimeUs) {
            this.frameReleaseHelper.onNextFrame(bufferPresentationTimeUs);
            this.lastBufferPresentationTimeUs = bufferPresentationTimeUs;
        }
        long outputStreamOffsetUs = getOutputStreamOffsetUs();
        long j2 = bufferPresentationTimeUs - outputStreamOffsetUs;
        if (isDecodeOnlyBuffer && !isLastBuffer) {
            skipOutputBuffer(codec, bufferIndex, j2);
            return true;
        }
        double playbackSpeed = getPlaybackSpeed();
        boolean z2 = getState() == 2;
        long jElapsedRealtime = SystemClock.elapsedRealtime() * 1000;
        long j3 = (long) ((bufferPresentationTimeUs - positionUs) / playbackSpeed);
        if (z2) {
            j3 -= jElapsedRealtime - elapsedRealtimeUs;
        }
        if (this.surface == this.dummySurface) {
            if (!isBufferLate(j3)) {
                return false;
            }
            skipOutputBuffer(codec, bufferIndex, j2);
            updateVideoFrameProcessingOffsetCounters(j3);
            return true;
        }
        long j4 = jElapsedRealtime - this.lastRenderRealtimeUs;
        if (this.renderedFirstFrameAfterEnable ? this.renderedFirstFrameAfterReset : !(z2 || this.mayRenderFirstFrameAfterEnableIfNotStarted)) {
            j = j4;
            z = false;
        } else {
            z = true;
            j = j4;
        }
        if (this.joiningDeadlineMs == C.TIME_UNSET && positionUs >= outputStreamOffsetUs && (z || (z2 && shouldForceRenderOutputBuffer(j3, j)))) {
            long jNanoTime = System.nanoTime();
            notifyFrameMetadataListener(j2, jNanoTime, format);
            if (Util.SDK_INT >= 21) {
                renderOutputBufferV21(codec, bufferIndex, j2, jNanoTime);
            } else {
                renderOutputBuffer(codec, bufferIndex, j2);
            }
            updateVideoFrameProcessingOffsetCounters(j3);
            return true;
        }
        if (z2 && positionUs != this.initialPositionUs) {
            long jNanoTime2 = System.nanoTime();
            long jAdjustReleaseTime = this.frameReleaseHelper.adjustReleaseTime((j3 * 1000) + jNanoTime2);
            long j5 = (jAdjustReleaseTime - jNanoTime2) / 1000;
            boolean z3 = this.joiningDeadlineMs != C.TIME_UNSET;
            if (shouldDropBuffersToKeyframe(j5, elapsedRealtimeUs, isLastBuffer) && maybeDropBuffersToKeyframe(positionUs, z3)) {
                return false;
            }
            if (shouldDropOutputBuffer(j5, elapsedRealtimeUs, isLastBuffer)) {
                if (z3) {
                    skipOutputBuffer(codec, bufferIndex, j2);
                } else {
                    dropOutputBuffer(codec, bufferIndex, j2);
                }
                updateVideoFrameProcessingOffsetCounters(j5);
                return true;
            }
            if (Util.SDK_INT >= 21) {
                if (j5 < 50000) {
                    notifyFrameMetadataListener(j2, jAdjustReleaseTime, format);
                    renderOutputBufferV21(codec, bufferIndex, j2, jAdjustReleaseTime);
                    updateVideoFrameProcessingOffsetCounters(j5);
                    return true;
                }
            } else if (j5 < 30000) {
                if (j5 > 11000) {
                    try {
                        Thread.sleep((j5 - 10000) / 1000);
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                }
                notifyFrameMetadataListener(j2, jAdjustReleaseTime, format);
                renderOutputBuffer(codec, bufferIndex, j2);
                updateVideoFrameProcessingOffsetCounters(j5);
                return true;
            }
        }
        return false;
    }

    private void notifyFrameMetadataListener(long presentationTimeUs, long releaseTimeNs, Format format) {
        VideoFrameMetadataListener videoFrameMetadataListener = this.frameMetadataListener;
        if (videoFrameMetadataListener != null) {
            videoFrameMetadataListener.onVideoFrameAboutToBeRendered(presentationTimeUs, releaseTimeNs, format, getCodecOutputMediaFormat());
        }
    }

    protected void onProcessedTunneledBuffer(long presentationTimeUs) throws ExoPlaybackException {
        updateOutputFormatForTime(presentationTimeUs);
        maybeNotifyVideoSizeChanged();
        this.decoderCounters.renderedOutputBufferCount++;
        maybeNotifyRenderedFirstFrame();
        onProcessedOutputBuffer(presentationTimeUs);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProcessedTunneledEndOfStream() {
        setPendingOutputEndOfStream();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onProcessedOutputBuffer(long presentationTimeUs) {
        super.onProcessedOutputBuffer(presentationTimeUs);
        if (this.tunneling) {
            return;
        }
        this.buffersInCodecCount--;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onProcessedStreamChange() {
        super.onProcessedStreamChange();
        clearRenderedFirstFrame();
    }

    protected boolean shouldDropOutputBuffer(long earlyUs, long elapsedRealtimeUs, boolean isLastBuffer) {
        return isBufferLate(earlyUs) && !isLastBuffer;
    }

    protected boolean shouldDropBuffersToKeyframe(long earlyUs, long elapsedRealtimeUs, boolean isLastBuffer) {
        return isBufferVeryLate(earlyUs) && !isLastBuffer;
    }

    protected boolean shouldForceRenderOutputBuffer(long earlyUs, long elapsedSinceLastRenderUs) {
        return isBufferLate(earlyUs) && elapsedSinceLastRenderUs > 100000;
    }

    protected void skipOutputBuffer(MediaCodecAdapter codec, int index, long presentationTimeUs) {
        TraceUtil.beginSection("skipVideoBuffer");
        codec.releaseOutputBuffer(index, false);
        TraceUtil.endSection();
        this.decoderCounters.skippedOutputBufferCount++;
    }

    protected void dropOutputBuffer(MediaCodecAdapter codec, int index, long presentationTimeUs) {
        TraceUtil.beginSection("dropVideoBuffer");
        codec.releaseOutputBuffer(index, false);
        TraceUtil.endSection();
        updateDroppedBufferCounters(1);
    }

    protected boolean maybeDropBuffersToKeyframe(long positionUs, boolean treatDroppedBuffersAsSkipped) throws ExoPlaybackException {
        int iSkipSource = skipSource(positionUs);
        if (iSkipSource == 0) {
            return false;
        }
        this.decoderCounters.droppedToKeyframeCount++;
        int i = this.buffersInCodecCount + iSkipSource;
        if (treatDroppedBuffersAsSkipped) {
            this.decoderCounters.skippedOutputBufferCount += i;
        } else {
            updateDroppedBufferCounters(i);
        }
        flushOrReinitializeCodec();
        return true;
    }

    protected void updateDroppedBufferCounters(int droppedBufferCount) {
        this.decoderCounters.droppedBufferCount += droppedBufferCount;
        this.droppedFrames += droppedBufferCount;
        this.consecutiveDroppedFrameCount += droppedBufferCount;
        this.decoderCounters.maxConsecutiveDroppedBufferCount = Math.max(this.consecutiveDroppedFrameCount, this.decoderCounters.maxConsecutiveDroppedBufferCount);
        int i = this.maxDroppedFramesToNotify;
        if (i <= 0 || this.droppedFrames < i) {
            return;
        }
        maybeNotifyDroppedFrames();
    }

    protected void updateVideoFrameProcessingOffsetCounters(long processingOffsetUs) {
        this.decoderCounters.addVideoFrameProcessingOffset(processingOffsetUs);
        this.totalVideoFrameProcessingOffsetUs += processingOffsetUs;
        this.videoFrameProcessingOffsetCount++;
    }

    protected void renderOutputBuffer(MediaCodecAdapter codec, int index, long presentationTimeUs) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        codec.releaseOutputBuffer(index, true);
        TraceUtil.endSection();
        this.lastRenderRealtimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    protected void renderOutputBufferV21(MediaCodecAdapter codec, int index, long presentationTimeUs, long releaseTimeNs) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        codec.releaseOutputBuffer(index, releaseTimeNs);
        TraceUtil.endSection();
        this.lastRenderRealtimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    private boolean shouldUseDummySurface(MediaCodecInfo codecInfo) {
        return Util.SDK_INT >= 23 && !this.tunneling && !codecNeedsSetOutputSurfaceWorkaround(codecInfo.name) && (!codecInfo.secure || DummySurface.isSecureSupported(this.context));
    }

    private void setJoiningDeadlineMs() {
        this.joiningDeadlineMs = this.allowedJoiningTimeMs > 0 ? SystemClock.elapsedRealtime() + this.allowedJoiningTimeMs : C.TIME_UNSET;
    }

    private void clearRenderedFirstFrame() {
        MediaCodecAdapter codec;
        this.renderedFirstFrameAfterReset = false;
        if (Util.SDK_INT < 23 || !this.tunneling || (codec = getCodec()) == null) {
            return;
        }
        this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(codec);
    }

    void maybeNotifyRenderedFirstFrame() {
        this.renderedFirstFrameAfterEnable = true;
        if (this.renderedFirstFrameAfterReset) {
            return;
        }
        this.renderedFirstFrameAfterReset = true;
        this.eventDispatcher.renderedFirstFrame(this.surface);
        this.haveReportedFirstFrameRenderedForCurrentSurface = true;
    }

    private void maybeRenotifyRenderedFirstFrame() {
        if (this.haveReportedFirstFrameRenderedForCurrentSurface) {
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void clearReportedVideoSize() {
        this.reportedVideoSize = null;
    }

    private void maybeNotifyVideoSizeChanged() {
        if (this.currentWidth == -1 && this.currentHeight == -1) {
            return;
        }
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null && videoSize.width == this.currentWidth && this.reportedVideoSize.height == this.currentHeight && this.reportedVideoSize.unappliedRotationDegrees == this.currentUnappliedRotationDegrees && this.reportedVideoSize.pixelWidthHeightRatio == this.currentPixelWidthHeightRatio) {
            return;
        }
        VideoSize videoSize2 = new VideoSize(this.currentWidth, this.currentHeight, this.currentUnappliedRotationDegrees, this.currentPixelWidthHeightRatio);
        this.reportedVideoSize = videoSize2;
        this.eventDispatcher.videoSizeChanged(videoSize2);
    }

    private void maybeRenotifyVideoSizeChanged() {
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null) {
            this.eventDispatcher.videoSizeChanged(videoSize);
        }
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            this.eventDispatcher.droppedFrames(this.droppedFrames, jElapsedRealtime - this.droppedFrameAccumulationStartTimeMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = jElapsedRealtime;
        }
    }

    private void maybeNotifyVideoFrameProcessingOffset() {
        int i = this.videoFrameProcessingOffsetCount;
        if (i != 0) {
            this.eventDispatcher.reportVideoFrameProcessingOffset(this.totalVideoFrameProcessingOffsetUs, i);
            this.totalVideoFrameProcessingOffsetUs = 0L;
            this.videoFrameProcessingOffsetCount = 0;
        }
    }

    private static void setHdr10PlusInfoV29(MediaCodecAdapter codec, byte[] hdr10PlusInfo) {
        Bundle bundle = new Bundle();
        bundle.putByteArray("hdr10-plus-info", hdr10PlusInfo);
        codec.setParameters(bundle);
    }

    protected void setOutputSurfaceV23(MediaCodecAdapter codec, Surface surface) {
        codec.setOutputSurface(surface);
    }

    private static void configureTunnelingV21(MediaFormat mediaFormat, int tunnelingAudioSessionId) {
        mediaFormat.setFeatureEnabled("tunneled-playback", true);
        mediaFormat.setInteger("audio-session-id", tunnelingAudioSessionId);
    }

    protected MediaFormat getMediaFormat(Format format, String codecMimeType, CodecMaxValues codecMaxValues, float codecOperatingRate, boolean deviceNeedsNoPostProcessWorkaround, int tunnelingAudioSessionId) {
        Pair<Integer, Integer> codecProfileAndLevel;
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", codecMimeType);
        mediaFormat.setInteger("width", format.width);
        mediaFormat.setInteger("height", format.height);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "rotation-degrees", format.rotationDegrees);
        MediaFormatUtil.maybeSetColorInfo(mediaFormat, format.colorInfo);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format)) != null) {
            MediaFormatUtil.maybeSetInteger(mediaFormat, Scopes.PROFILE, ((Integer) codecProfileAndLevel.first).intValue());
        }
        mediaFormat.setInteger("max-width", codecMaxValues.width);
        mediaFormat.setInteger("max-height", codecMaxValues.height);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxValues.inputSize);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger(Constants.FirelogAnalytics.PARAM_PRIORITY, 0);
            if (codecOperatingRate != -1.0f) {
                mediaFormat.setFloat("operating-rate", codecOperatingRate);
            }
        }
        if (deviceNeedsNoPostProcessWorkaround) {
            mediaFormat.setInteger("no-post-process", 1);
            mediaFormat.setInteger("auto-frc", 0);
        }
        if (tunnelingAudioSessionId != 0) {
            configureTunnelingV21(mediaFormat, tunnelingAudioSessionId);
        }
        return mediaFormat;
    }

    protected CodecMaxValues getCodecMaxValues(MediaCodecInfo codecInfo, Format format, Format[] streamFormats) {
        int codecMaxInputSize;
        int iMax = format.width;
        int iMax2 = format.height;
        int maxInputSize = getMaxInputSize(codecInfo, format);
        if (streamFormats.length == 1) {
            if (maxInputSize != -1 && (codecMaxInputSize = getCodecMaxInputSize(codecInfo, format.sampleMimeType, format.width, format.height)) != -1) {
                maxInputSize = Math.min((int) (maxInputSize * INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR), codecMaxInputSize);
            }
            return new CodecMaxValues(iMax, iMax2, maxInputSize);
        }
        int length = streamFormats.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            Format formatBuild = streamFormats[i];
            if (format.colorInfo != null && formatBuild.colorInfo == null) {
                formatBuild = formatBuild.buildUpon().setColorInfo(format.colorInfo).build();
            }
            if (codecInfo.canReuseCodec(format, formatBuild).result != 0) {
                z |= formatBuild.width == -1 || formatBuild.height == -1;
                iMax = Math.max(iMax, formatBuild.width);
                iMax2 = Math.max(iMax2, formatBuild.height);
                maxInputSize = Math.max(maxInputSize, getMaxInputSize(codecInfo, formatBuild));
            }
        }
        if (z) {
            Log.w(TAG, new StringBuilder(66).append("Resolutions unknown. Codec max resolution: ").append(iMax).append("x").append(iMax2).toString());
            Point codecMaxSize = getCodecMaxSize(codecInfo, format);
            if (codecMaxSize != null) {
                iMax = Math.max(iMax, codecMaxSize.x);
                iMax2 = Math.max(iMax2, codecMaxSize.y);
                maxInputSize = Math.max(maxInputSize, getCodecMaxInputSize(codecInfo, format.sampleMimeType, iMax, iMax2));
                Log.w(TAG, new StringBuilder(57).append("Codec max resolution adjusted to: ").append(iMax).append("x").append(iMax2).toString());
            }
        }
        return new CodecMaxValues(iMax, iMax2, maxInputSize);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected MediaCodecDecoderException createDecoderException(Throwable cause, MediaCodecInfo codecInfo) {
        return new MediaCodecVideoDecoderException(cause, codecInfo, this.surface);
    }

    private static Point getCodecMaxSize(MediaCodecInfo codecInfo, Format format) {
        boolean z = format.height > format.width;
        int i = z ? format.height : format.width;
        int i2 = z ? format.width : format.height;
        float f = i2 / i;
        for (int i3 : STANDARD_LONG_EDGE_VIDEO_PX) {
            int i4 = (int) (i3 * f);
            if (i3 <= i || i4 <= i2) {
                break;
            }
            if (Util.SDK_INT >= 21) {
                int i5 = z ? i4 : i3;
                if (!z) {
                    i3 = i4;
                }
                Point pointAlignVideoSizeV21 = codecInfo.alignVideoSizeV21(i5, i3);
                if (codecInfo.isVideoSizeAndRateSupportedV21(pointAlignVideoSizeV21.x, pointAlignVideoSizeV21.y, format.frameRate)) {
                    return pointAlignVideoSizeV21;
                }
            } else {
                try {
                    int iCeilDivide = Util.ceilDivide(i3, 16) * 16;
                    int iCeilDivide2 = Util.ceilDivide(i4, 16) * 16;
                    if (iCeilDivide * iCeilDivide2 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                        int i6 = z ? iCeilDivide2 : iCeilDivide;
                        if (!z) {
                            iCeilDivide = iCeilDivide2;
                        }
                        return new Point(i6, iCeilDivide);
                    }
                } catch (MediaCodecUtil.DecoderQueryException unused) {
                }
            }
        }
        return null;
    }

    protected static int getMaxInputSize(MediaCodecInfo codecInfo, Format format) {
        if (format.maxInputSize != -1) {
            int size = format.initializationData.size();
            int length = 0;
            for (int i = 0; i < size; i++) {
                length += format.initializationData.get(i).length;
            }
            return format.maxInputSize + length;
        }
        return getCodecMaxInputSize(codecInfo, format.sampleMimeType, format.width, format.height);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int getCodecMaxInputSize(com.google.android.exoplayer2.mediacodec.MediaCodecInfo r5, java.lang.String r6, int r7, int r8) {
        /*
            r0 = -1
            if (r7 == r0) goto Lb2
            if (r8 != r0) goto L7
            goto Lb2
        L7:
            r6.hashCode()
            int r1 = r6.hashCode()
            r2 = 4
            r3 = 3
            r4 = 2
            switch(r1) {
                case -1851077871: goto L5e;
                case -1664118616: goto L52;
                case -1662541442: goto L46;
                case 1187890754: goto L3a;
                case 1331836730: goto L2e;
                case 1599127256: goto L22;
                case 1599127257: goto L16;
                default: goto L14;
            }
        L14:
            r6 = r0
            goto L69
        L16:
            java.lang.String r1 = "video/x-vnd.on2.vp9"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L20
            goto L14
        L20:
            r6 = 6
            goto L69
        L22:
            java.lang.String r1 = "video/x-vnd.on2.vp8"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L2c
            goto L14
        L2c:
            r6 = 5
            goto L69
        L2e:
            java.lang.String r1 = "video/avc"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L38
            goto L14
        L38:
            r6 = r2
            goto L69
        L3a:
            java.lang.String r1 = "video/mp4v-es"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L44
            goto L14
        L44:
            r6 = r3
            goto L69
        L46:
            java.lang.String r1 = "video/hevc"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L50
            goto L14
        L50:
            r6 = r4
            goto L69
        L52:
            java.lang.String r1 = "video/3gpp"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L5c
            goto L14
        L5c:
            r6 = 1
            goto L69
        L5e:
            java.lang.String r1 = "video/dolby-vision"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L68
            goto L14
        L68:
            r6 = 0
        L69:
            switch(r6) {
                case 0: goto L72;
                case 1: goto L6f;
                case 2: goto L6d;
                case 3: goto L6f;
                case 4: goto L72;
                case 5: goto L6f;
                case 6: goto L6d;
                default: goto L6c;
            }
        L6c:
            return r0
        L6d:
            int r7 = r7 * r8
            goto Lae
        L6f:
            int r7 = r7 * r8
        L70:
            r2 = r4
            goto Lae
        L72:
            java.lang.String r6 = "BRAVIA 4K 2015"
            java.lang.String r1 = com.google.android.exoplayer2.util.Util.MODEL
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto Lb2
            java.lang.String r6 = "Amazon"
            java.lang.String r1 = com.google.android.exoplayer2.util.Util.MANUFACTURER
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L9f
            java.lang.String r6 = "KFSOWI"
            java.lang.String r1 = com.google.android.exoplayer2.util.Util.MODEL
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto Lb2
            java.lang.String r6 = "AFTS"
            java.lang.String r1 = com.google.android.exoplayer2.util.Util.MODEL
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L9f
            boolean r5 = r5.secure
            if (r5 == 0) goto L9f
            goto Lb2
        L9f:
            r5 = 16
            int r6 = com.google.android.exoplayer2.util.Util.ceilDivide(r7, r5)
            int r7 = com.google.android.exoplayer2.util.Util.ceilDivide(r8, r5)
            int r6 = r6 * r7
            int r6 = r6 * r5
            int r7 = r6 * 16
            goto L70
        Lae:
            int r7 = r7 * r3
            int r2 = r2 * r4
            int r7 = r7 / r2
            return r7
        Lb2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.getCodecMaxInputSize(com.google.android.exoplayer2.mediacodec.MediaCodecInfo, java.lang.String, int, int):int");
    }

    private static boolean deviceNeedsNoPostProcessWorkaround() {
        return "NVIDIA".equals(Util.MANUFACTURER);
    }

    protected boolean codecNeedsSetOutputSurfaceWorkaround(String name) {
        if (name.startsWith("OMX.google")) {
            return false;
        }
        synchronized (MediaCodecVideoRenderer.class) {
            if (!evaluatedDeviceNeedsSetOutputSurfaceWorkaround) {
                deviceNeedsSetOutputSurfaceWorkaround = evaluateDeviceNeedsSetOutputSurfaceWorkaround();
                evaluatedDeviceNeedsSetOutputSurfaceWorkaround = true;
            }
        }
        return deviceNeedsSetOutputSurfaceWorkaround;
    }

    protected Surface getSurface() {
        return this.surface;
    }

    protected static final class CodecMaxValues {
        public final int height;
        public final int inputSize;
        public final int width;

        public CodecMaxValues(int width, int height, int inputSize) {
            this.width = width;
            this.height = height;
            this.inputSize = inputSize;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:610:0x0834  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean evaluateDeviceNeedsSetOutputSurfaceWorkaround() {
        /*
            Method dump skipped, instructions count: 3056
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.evaluateDeviceNeedsSetOutputSurfaceWorkaround():boolean");
    }

    private final class OnFrameRenderedListenerV23 implements MediaCodecAdapter.OnFrameRenderedListener, Handler.Callback {
        private static final int HANDLE_FRAME_RENDERED = 0;
        private final Handler handler;

        public OnFrameRenderedListenerV23(MediaCodecAdapter codec) {
            Handler handlerCreateHandlerForCurrentLooper = Util.createHandlerForCurrentLooper(this);
            this.handler = handlerCreateHandlerForCurrentLooper;
            codec.setOnFrameRenderedListener(this, handlerCreateHandlerForCurrentLooper);
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.OnFrameRenderedListener
        public void onFrameRendered(MediaCodecAdapter codec, long presentationTimeUs, long nanoTime) {
            if (Util.SDK_INT < 30) {
                this.handler.sendMessageAtFrontOfQueue(Message.obtain(this.handler, 0, (int) (presentationTimeUs >> 32), (int) presentationTimeUs));
            } else {
                handleFrameRendered(presentationTimeUs);
            }
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            handleFrameRendered(Util.toLong(message.arg1, message.arg2));
            return true;
        }

        private void handleFrameRendered(long presentationTimeUs) {
            if (this != MediaCodecVideoRenderer.this.tunnelingOnFrameRenderedListener) {
                return;
            }
            if (presentationTimeUs == Long.MAX_VALUE) {
                MediaCodecVideoRenderer.this.onProcessedTunneledEndOfStream();
                return;
            }
            try {
                MediaCodecVideoRenderer.this.onProcessedTunneledBuffer(presentationTimeUs);
            } catch (ExoPlaybackException e) {
                MediaCodecVideoRenderer.this.setPendingPlaybackException(e);
            }
        }
    }
}
