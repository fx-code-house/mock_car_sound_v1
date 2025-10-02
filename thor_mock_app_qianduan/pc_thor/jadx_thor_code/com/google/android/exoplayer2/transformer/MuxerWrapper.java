package com.google.android.exoplayer2.transformer;

import android.util.SparseIntArray;
import android.util.SparseLongArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
final class MuxerWrapper {
    private static final long MAX_TRACK_WRITE_AHEAD_US = C.msToUs(500);
    private boolean isReady;
    private long minTrackTimeUs;
    private final Muxer muxer;
    private int trackCount;
    private int trackFormatCount;
    private final SparseIntArray trackTypeToIndex = new SparseIntArray();
    private final SparseLongArray trackTypeToTimeUs = new SparseLongArray();
    private int previousTrackType = 7;

    public MuxerWrapper(Muxer muxer) {
        this.muxer = muxer;
    }

    public void registerTrack() {
        Assertions.checkState(this.trackFormatCount == 0, "Tracks cannot be registered after track formats have been added.");
        this.trackCount++;
    }

    public boolean supportsSampleMimeType(String mimeType) {
        return this.muxer.supportsSampleMimeType(mimeType);
    }

    public void addTrackFormat(Format format) {
        Assertions.checkState(this.trackCount > 0, "All tracks should be registered before the formats are added.");
        Assertions.checkState(this.trackFormatCount < this.trackCount, "All track formats have already been added.");
        String str = format.sampleMimeType;
        boolean z = MimeTypes.isAudio(str) || MimeTypes.isVideo(str);
        String strValueOf = String.valueOf(str);
        Assertions.checkState(z, strValueOf.length() != 0 ? "Unsupported track format: ".concat(strValueOf) : new String("Unsupported track format: "));
        int trackType = MimeTypes.getTrackType(str);
        Assertions.checkState(this.trackTypeToIndex.get(trackType, -1) == -1, new StringBuilder(44).append("There is already a track of type ").append(trackType).toString());
        this.trackTypeToIndex.put(trackType, this.muxer.addTrack(format));
        this.trackTypeToTimeUs.put(trackType, 0L);
        int i = this.trackFormatCount + 1;
        this.trackFormatCount = i;
        if (i == this.trackCount) {
            this.isReady = true;
        }
    }

    public boolean writeSample(int trackType, ByteBuffer data, boolean isKeyFrame, long presentationTimeUs) {
        int i = this.trackTypeToIndex.get(trackType, -1);
        Assertions.checkState(i != -1, new StringBuilder(68).append("Could not write sample because there is no track of type ").append(trackType).toString());
        if (!canWriteSampleOfType(trackType)) {
            return false;
        }
        if (data == null) {
            return true;
        }
        this.muxer.writeSampleData(i, data, isKeyFrame, presentationTimeUs);
        this.trackTypeToTimeUs.put(trackType, presentationTimeUs);
        this.previousTrackType = trackType;
        return true;
    }

    public void endTrack(int trackType) {
        this.trackTypeToIndex.delete(trackType);
        this.trackTypeToTimeUs.delete(trackType);
    }

    public void release(boolean forCancellation) {
        this.isReady = false;
        this.muxer.release(forCancellation);
    }

    public int getTrackCount() {
        return this.trackCount;
    }

    private boolean canWriteSampleOfType(int trackType) {
        long j = this.trackTypeToTimeUs.get(trackType, C.TIME_UNSET);
        Assertions.checkState(j != C.TIME_UNSET);
        if (!this.isReady) {
            return false;
        }
        if (this.trackTypeToTimeUs.size() == 1) {
            return true;
        }
        if (trackType != this.previousTrackType) {
            this.minTrackTimeUs = Util.minValue(this.trackTypeToTimeUs);
        }
        return j - this.minTrackTimeUs <= MAX_TRACK_WRITE_AHEAD_US;
    }
}
