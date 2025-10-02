package com.google.android.exoplayer2;

import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaClock;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class BaseRenderer implements Renderer, RendererCapabilities {
    private RendererConfiguration configuration;
    private int index;
    private long lastResetPositionUs;
    private int state;
    private SampleStream stream;
    private Format[] streamFormats;
    private boolean streamIsFinal;
    private long streamOffsetUs;
    private boolean throwRendererExceptionIsExecuting;
    private final int trackType;
    private final FormatHolder formatHolder = new FormatHolder();
    private long readingPositionUs = Long.MIN_VALUE;

    @Override // com.google.android.exoplayer2.Renderer
    public final RendererCapabilities getCapabilities() {
        return this;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public MediaClock getMediaClock() {
        return null;
    }

    @Override // com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int messageType, Object message) throws ExoPlaybackException {
    }

    protected void onDisabled() {
    }

    protected void onEnabled(boolean joining, boolean mayRenderStartOfStream) throws ExoPlaybackException {
    }

    protected void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
    }

    protected void onReset() {
    }

    protected void onStarted() throws ExoPlaybackException {
    }

    protected void onStopped() {
    }

    protected void onStreamChanged(Format[] formats, long startPositionUs, long offsetUs) throws ExoPlaybackException {
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsMixedMimeTypeAdaptation() throws ExoPlaybackException {
        return 0;
    }

    public BaseRenderer(int trackType) {
        this.trackType = trackType;
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public final int getTrackType() {
        return this.trackType;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void setIndex(int index) {
        this.index = index;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final int getState() {
        return this.state;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void enable(RendererConfiguration configuration, Format[] formats, SampleStream stream, long positionUs, boolean joining, boolean mayRenderStartOfStream, long startPositionUs, long offsetUs) throws ExoPlaybackException {
        Assertions.checkState(this.state == 0);
        this.configuration = configuration;
        this.state = 1;
        this.lastResetPositionUs = positionUs;
        onEnabled(joining, mayRenderStartOfStream);
        replaceStream(formats, stream, startPositionUs, offsetUs);
        onPositionReset(positionUs, joining);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void start() throws ExoPlaybackException {
        Assertions.checkState(this.state == 1);
        this.state = 2;
        onStarted();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void replaceStream(Format[] formats, SampleStream stream, long startPositionUs, long offsetUs) throws ExoPlaybackException {
        Assertions.checkState(!this.streamIsFinal);
        this.stream = stream;
        if (this.readingPositionUs == Long.MIN_VALUE) {
            this.readingPositionUs = startPositionUs;
        }
        this.streamFormats = formats;
        this.streamOffsetUs = offsetUs;
        onStreamChanged(formats, startPositionUs, offsetUs);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final SampleStream getStream() {
        return this.stream;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final boolean hasReadStreamToEnd() {
        return this.readingPositionUs == Long.MIN_VALUE;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final long getReadingPositionUs() {
        return this.readingPositionUs;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void setCurrentStreamFinal() {
        this.streamIsFinal = true;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final boolean isCurrentStreamFinal() {
        return this.streamIsFinal;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void maybeThrowStreamError() throws IOException {
        ((SampleStream) Assertions.checkNotNull(this.stream)).maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void resetPosition(long positionUs) throws ExoPlaybackException {
        this.streamIsFinal = false;
        this.lastResetPositionUs = positionUs;
        this.readingPositionUs = positionUs;
        onPositionReset(positionUs, false);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void stop() {
        Assertions.checkState(this.state == 2);
        this.state = 1;
        onStopped();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void disable() {
        Assertions.checkState(this.state == 1);
        this.formatHolder.clear();
        this.state = 0;
        this.stream = null;
        this.streamFormats = null;
        this.streamIsFinal = false;
        onDisabled();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void reset() {
        Assertions.checkState(this.state == 0);
        this.formatHolder.clear();
        onReset();
    }

    protected final long getLastResetPositionUs() {
        return this.lastResetPositionUs;
    }

    protected final FormatHolder getFormatHolder() {
        this.formatHolder.clear();
        return this.formatHolder;
    }

    protected final Format[] getStreamFormats() {
        return (Format[]) Assertions.checkNotNull(this.streamFormats);
    }

    protected final RendererConfiguration getConfiguration() {
        return (RendererConfiguration) Assertions.checkNotNull(this.configuration);
    }

    protected final int getIndex() {
        return this.index;
    }

    protected final ExoPlaybackException createRendererException(Throwable cause, Format format, int errorCode) {
        return createRendererException(cause, format, false, errorCode);
    }

    protected final ExoPlaybackException createRendererException(Throwable cause, Format format, boolean isRecoverable, int errorCode) {
        int formatSupport;
        if (format == null || this.throwRendererExceptionIsExecuting) {
            formatSupport = 4;
        } else {
            this.throwRendererExceptionIsExecuting = true;
            try {
                formatSupport = RendererCapabilities.getFormatSupport(supportsFormat(format));
            } catch (ExoPlaybackException unused) {
            } finally {
                this.throwRendererExceptionIsExecuting = false;
            }
        }
        return ExoPlaybackException.createForRenderer(cause, getName(), getIndex(), format, formatSupport, isRecoverable, errorCode);
    }

    protected final int readSource(FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags) {
        int data = ((SampleStream) Assertions.checkNotNull(this.stream)).readData(formatHolder, buffer, readFlags);
        if (data == -4) {
            if (buffer.isEndOfStream()) {
                this.readingPositionUs = Long.MIN_VALUE;
                return this.streamIsFinal ? -4 : -3;
            }
            buffer.timeUs += this.streamOffsetUs;
            this.readingPositionUs = Math.max(this.readingPositionUs, buffer.timeUs);
        } else if (data == -5) {
            Format format = (Format) Assertions.checkNotNull(formatHolder.format);
            if (format.subsampleOffsetUs != Long.MAX_VALUE) {
                formatHolder.format = format.buildUpon().setSubsampleOffsetUs(format.subsampleOffsetUs + this.streamOffsetUs).build();
            }
        }
        return data;
    }

    protected int skipSource(long positionUs) {
        return ((SampleStream) Assertions.checkNotNull(this.stream)).skipData(positionUs - this.streamOffsetUs);
    }

    protected final boolean isSourceReady() {
        return hasReadStreamToEnd() ? this.streamIsFinal : ((SampleStream) Assertions.checkNotNull(this.stream)).isReady();
    }
}
