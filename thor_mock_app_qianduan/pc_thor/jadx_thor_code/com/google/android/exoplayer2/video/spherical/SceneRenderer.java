package com.google.android.exoplayer2.video.spherical;

import android.graphics.SurfaceTexture;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
final class SceneRenderer implements VideoFrameMetadataListener, CameraMotionListener {
    private byte[] lastProjectionData;
    private SurfaceTexture surfaceTexture;
    private int textureId;
    private final AtomicBoolean frameAvailable = new AtomicBoolean();
    private final AtomicBoolean resetRotationAtNextFrame = new AtomicBoolean(true);
    private final ProjectionRenderer projectionRenderer = new ProjectionRenderer();
    private final FrameRotationQueue frameRotationQueue = new FrameRotationQueue();
    private final TimedValueQueue<Long> sampleTimestampQueue = new TimedValueQueue<>();
    private final TimedValueQueue<Projection> projectionQueue = new TimedValueQueue<>();
    private final float[] rotationMatrix = new float[16];
    private final float[] tempMatrix = new float[16];
    private volatile int defaultStereoMode = 0;
    private int lastStereoMode = -1;

    public void setDefaultStereoMode(int stereoMode) {
        this.defaultStereoMode = stereoMode;
    }

    public SurfaceTexture init() {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        GlUtil.checkGlError();
        this.projectionRenderer.init();
        GlUtil.checkGlError();
        this.textureId = GlUtil.createExternalTexture();
        SurfaceTexture surfaceTexture = new SurfaceTexture(this.textureId);
        this.surfaceTexture = surfaceTexture;
        surfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.google.android.exoplayer2.video.spherical.SceneRenderer$$ExternalSyntheticLambda0
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public final void onFrameAvailable(SurfaceTexture surfaceTexture2) {
                this.f$0.m225x44e5bf0(surfaceTexture2);
            }
        });
        return this.surfaceTexture;
    }

    /* renamed from: lambda$init$0$com-google-android-exoplayer2-video-spherical-SceneRenderer, reason: not valid java name */
    /* synthetic */ void m225x44e5bf0(SurfaceTexture surfaceTexture) {
        this.frameAvailable.set(true);
    }

    public void drawFrame(float[] viewProjectionMatrix, boolean rightEye) {
        GLES20.glClear(16384);
        GlUtil.checkGlError();
        if (this.frameAvailable.compareAndSet(true, false)) {
            ((SurfaceTexture) Assertions.checkNotNull(this.surfaceTexture)).updateTexImage();
            GlUtil.checkGlError();
            if (this.resetRotationAtNextFrame.compareAndSet(true, false)) {
                Matrix.setIdentityM(this.rotationMatrix, 0);
            }
            long timestamp = this.surfaceTexture.getTimestamp();
            Long lPoll = this.sampleTimestampQueue.poll(timestamp);
            if (lPoll != null) {
                this.frameRotationQueue.pollRotationMatrix(this.rotationMatrix, lPoll.longValue());
            }
            Projection projectionPollFloor = this.projectionQueue.pollFloor(timestamp);
            if (projectionPollFloor != null) {
                this.projectionRenderer.setProjection(projectionPollFloor);
            }
        }
        Matrix.multiplyMM(this.tempMatrix, 0, viewProjectionMatrix, 0, this.rotationMatrix, 0);
        this.projectionRenderer.draw(this.textureId, this.tempMatrix, rightEye);
    }

    public void shutdown() {
        this.projectionRenderer.shutdown();
    }

    @Override // com.google.android.exoplayer2.video.VideoFrameMetadataListener
    public void onVideoFrameAboutToBeRendered(long presentationTimeUs, long releaseTimeNs, Format format, MediaFormat mediaFormat) {
        this.sampleTimestampQueue.add(releaseTimeNs, Long.valueOf(presentationTimeUs));
        setProjection(format.projectionData, format.stereoMode, releaseTimeNs);
    }

    @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
    public void onCameraMotion(long timeUs, float[] rotation) {
        this.frameRotationQueue.setRotation(timeUs, rotation);
    }

    @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
    public void onCameraMotionReset() {
        this.sampleTimestampQueue.clear();
        this.frameRotationQueue.reset();
        this.resetRotationAtNextFrame.set(true);
    }

    private void setProjection(byte[] projectionData, int stereoMode, long timeNs) {
        byte[] bArr = this.lastProjectionData;
        int i = this.lastStereoMode;
        this.lastProjectionData = projectionData;
        if (stereoMode == -1) {
            stereoMode = this.defaultStereoMode;
        }
        this.lastStereoMode = stereoMode;
        if (i == stereoMode && Arrays.equals(bArr, this.lastProjectionData)) {
            return;
        }
        byte[] bArr2 = this.lastProjectionData;
        Projection projectionDecode = bArr2 != null ? ProjectionDecoder.decode(bArr2, this.lastStereoMode) : null;
        if (projectionDecode == null || !ProjectionRenderer.isSupported(projectionDecode)) {
            projectionDecode = Projection.createEquirectangular(this.lastStereoMode);
        }
        this.projectionQueue.add(timeNs, projectionDecode);
    }
}
