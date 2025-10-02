package com.google.android.exoplayer2.video;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;

/* loaded from: classes.dex */
public class VideoDecoderInputBuffer extends DecoderInputBuffer {
    public Format format;

    public VideoDecoderInputBuffer(int bufferReplacementMode) {
        super(bufferReplacementMode);
    }

    public VideoDecoderInputBuffer(int bufferReplacementMode, int paddingSize) {
        super(bufferReplacementMode, paddingSize);
    }
}
