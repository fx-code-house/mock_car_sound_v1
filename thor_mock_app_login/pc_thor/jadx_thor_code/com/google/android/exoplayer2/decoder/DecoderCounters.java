package com.google.android.exoplayer2.decoder;

/* loaded from: classes.dex */
public final class DecoderCounters {
    public int decoderInitCount;
    public int decoderReleaseCount;
    public int droppedBufferCount;
    public int droppedToKeyframeCount;
    public int inputBufferCount;
    public int maxConsecutiveDroppedBufferCount;
    public int renderedOutputBufferCount;
    public int skippedInputBufferCount;
    public int skippedOutputBufferCount;
    public long totalVideoFrameProcessingOffsetUs;
    public int videoFrameProcessingOffsetCount;

    public synchronized void ensureUpdated() {
    }

    public void merge(DecoderCounters other) {
        this.decoderInitCount += other.decoderInitCount;
        this.decoderReleaseCount += other.decoderReleaseCount;
        this.inputBufferCount += other.inputBufferCount;
        this.skippedInputBufferCount += other.skippedInputBufferCount;
        this.renderedOutputBufferCount += other.renderedOutputBufferCount;
        this.skippedOutputBufferCount += other.skippedOutputBufferCount;
        this.droppedBufferCount += other.droppedBufferCount;
        this.maxConsecutiveDroppedBufferCount = Math.max(this.maxConsecutiveDroppedBufferCount, other.maxConsecutiveDroppedBufferCount);
        this.droppedToKeyframeCount += other.droppedToKeyframeCount;
        addVideoFrameProcessingOffsets(other.totalVideoFrameProcessingOffsetUs, other.videoFrameProcessingOffsetCount);
    }

    public void addVideoFrameProcessingOffset(long processingOffsetUs) {
        addVideoFrameProcessingOffsets(processingOffsetUs, 1);
    }

    private void addVideoFrameProcessingOffsets(long totalProcessingOffsetUs, int count) {
        this.totalVideoFrameProcessingOffsetUs += totalProcessingOffsetUs;
        this.videoFrameProcessingOffsetCount += count;
    }
}
