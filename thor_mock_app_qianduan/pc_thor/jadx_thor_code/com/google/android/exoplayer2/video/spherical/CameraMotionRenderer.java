package com.google.android.exoplayer2.video.spherical;

import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class CameraMotionRenderer extends BaseRenderer {
    private static final int SAMPLE_WINDOW_DURATION_US = 100000;
    private static final String TAG = "CameraMotionRenderer";
    private final DecoderInputBuffer buffer;
    private long lastTimestampUs;
    private CameraMotionListener listener;
    private long offsetUs;
    private final ParsableByteArray scratch;

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return true;
    }

    public CameraMotionRenderer() {
        super(6);
        this.buffer = new DecoderInputBuffer(1);
        this.scratch = new ParsableByteArray();
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsFormat(Format format) {
        if (MimeTypes.APPLICATION_CAMERA_MOTION.equals(format.sampleMimeType)) {
            return RendererCapabilities.create(4);
        }
        return RendererCapabilities.create(0);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int messageType, Object message) throws ExoPlaybackException {
        if (messageType == 7) {
            this.listener = (CameraMotionListener) message;
        } else {
            super.handleMessage(messageType, message);
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onStreamChanged(Format[] formats, long startPositionUs, long offsetUs) {
        this.offsetUs = offsetUs;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onPositionReset(long positionUs, boolean joining) {
        this.lastTimestampUs = Long.MIN_VALUE;
        resetListener();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onDisabled() {
        resetListener();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long positionUs, long elapsedRealtimeUs) {
        while (!hasReadStreamToEnd() && this.lastTimestampUs < 100000 + positionUs) {
            this.buffer.clear();
            if (readSource(getFormatHolder(), this.buffer, 0) != -4 || this.buffer.isEndOfStream()) {
                return;
            }
            this.lastTimestampUs = this.buffer.timeUs;
            if (this.listener != null && !this.buffer.isDecodeOnly()) {
                this.buffer.flip();
                float[] metadata = parseMetadata((ByteBuffer) Util.castNonNull(this.buffer.data));
                if (metadata != null) {
                    ((CameraMotionListener) Util.castNonNull(this.listener)).onCameraMotion(this.lastTimestampUs - this.offsetUs, metadata);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return hasReadStreamToEnd();
    }

    private float[] parseMetadata(ByteBuffer data) {
        if (data.remaining() != 16) {
            return null;
        }
        this.scratch.reset(data.array(), data.limit());
        this.scratch.setPosition(data.arrayOffset() + 4);
        float[] fArr = new float[3];
        for (int i = 0; i < 3; i++) {
            fArr[i] = Float.intBitsToFloat(this.scratch.readLittleEndianInt());
        }
        return fArr;
    }

    private void resetListener() {
        CameraMotionListener cameraMotionListener = this.listener;
        if (cameraMotionListener != null) {
            cameraMotionListener.onCameraMotionReset();
        }
    }
}
