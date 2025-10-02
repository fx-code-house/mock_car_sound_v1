package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.EOFException;
import java.io.IOException;

/* loaded from: classes.dex */
public final class DummyTrackOutput implements TrackOutput {
    private final byte[] readBuffer = new byte[4096];

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public void format(Format format) {
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public void sampleMetadata(long timeUs, int flags, int size, int offset, TrackOutput.CryptoData cryptoData) {
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public int sampleData(DataReader input, int length, boolean allowEndOfInput, int sampleDataPart) throws IOException {
        int i = input.read(this.readBuffer, 0, Math.min(this.readBuffer.length, length));
        if (i != -1) {
            return i;
        }
        if (allowEndOfInput) {
            return -1;
        }
        throw new EOFException();
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public void sampleData(ParsableByteArray data, int length, int sampleDataPart) {
        data.skipBytes(length);
    }
}
