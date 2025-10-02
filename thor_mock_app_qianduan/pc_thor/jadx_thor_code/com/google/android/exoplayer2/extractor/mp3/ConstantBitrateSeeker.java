package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;

/* loaded from: classes.dex */
final class ConstantBitrateSeeker extends ConstantBitrateSeekMap implements Seeker {
    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return -1L;
    }

    public ConstantBitrateSeeker(long inputLength, long firstFramePosition, MpegAudioUtil.Header mpegAudioHeader) {
        super(inputLength, firstFramePosition, mpegAudioHeader.bitrate, mpegAudioHeader.frameSize);
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getTimeUs(long position) {
        return getTimeUsAtPosition(position);
    }
}
