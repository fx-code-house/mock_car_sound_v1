package com.google.android.exoplayer2.transformer;

import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MimeTypes;

/* loaded from: classes.dex */
abstract class TransformerBaseRenderer extends BaseRenderer {
    protected boolean isRendererStarted;
    protected final TransformerMediaClock mediaClock;
    protected final MuxerWrapper muxerWrapper;
    protected final Transformation transformation;

    public TransformerBaseRenderer(int trackType, MuxerWrapper muxerWrapper, TransformerMediaClock mediaClock, Transformation transformation) {
        super(trackType);
        this.muxerWrapper = muxerWrapper;
        this.mediaClock = mediaClock;
        this.transformation = transformation;
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public final int supportsFormat(Format format) {
        String str = format.sampleMimeType;
        if (MimeTypes.getTrackType(str) != getTrackType()) {
            return RendererCapabilities.create(0);
        }
        if (this.muxerWrapper.supportsSampleMimeType(str)) {
            return RendererCapabilities.create(4);
        }
        return RendererCapabilities.create(1);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final boolean isReady() {
        return isSourceReady();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.Renderer
    public final MediaClock getMediaClock() {
        return this.mediaClock;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected final void onEnabled(boolean joining, boolean mayRenderStartOfStream) {
        this.muxerWrapper.registerTrack();
        this.mediaClock.updateTimeForTrackType(getTrackType(), 0L);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected final void onStarted() {
        this.isRendererStarted = true;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected final void onStopped() {
        this.isRendererStarted = false;
    }
}
