package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes.dex */
public final class Id3Reader implements ElementaryStreamReader {
    private static final String TAG = "Id3Reader";
    private final ParsableByteArray id3Header = new ParsableByteArray(10);
    private TrackOutput output;
    private int sampleBytesRead;
    private int sampleSize;
    private long sampleTimeUs;
    private boolean writingSample;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.writingSample = false;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        TrackOutput trackOutputTrack = extractorOutput.track(idGenerator.getTrackId(), 5);
        this.output = trackOutputTrack;
        trackOutputTrack.format(new Format.Builder().setId(idGenerator.getFormatId()).setSampleMimeType(MimeTypes.APPLICATION_ID3).build());
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long pesTimeUs, int flags) {
        if ((flags & 4) == 0) {
            return;
        }
        this.writingSample = true;
        this.sampleTimeUs = pesTimeUs;
        this.sampleSize = 0;
        this.sampleBytesRead = 0;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray data) {
        Assertions.checkStateNotNull(this.output);
        if (this.writingSample) {
            int iBytesLeft = data.bytesLeft();
            int i = this.sampleBytesRead;
            if (i < 10) {
                int iMin = Math.min(iBytesLeft, 10 - i);
                System.arraycopy(data.getData(), data.getPosition(), this.id3Header.getData(), this.sampleBytesRead, iMin);
                if (this.sampleBytesRead + iMin == 10) {
                    this.id3Header.setPosition(0);
                    if (73 != this.id3Header.readUnsignedByte() || 68 != this.id3Header.readUnsignedByte() || 51 != this.id3Header.readUnsignedByte()) {
                        Log.w(TAG, "Discarding invalid ID3 tag");
                        this.writingSample = false;
                        return;
                    } else {
                        this.id3Header.skipBytes(3);
                        this.sampleSize = this.id3Header.readSynchSafeInt() + 10;
                    }
                }
            }
            int iMin2 = Math.min(iBytesLeft, this.sampleSize - this.sampleBytesRead);
            this.output.sampleData(data, iMin2);
            this.sampleBytesRead += iMin2;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
        int i;
        Assertions.checkStateNotNull(this.output);
        if (this.writingSample && (i = this.sampleSize) != 0 && this.sampleBytesRead == i) {
            this.output.sampleMetadata(this.sampleTimeUs, 1, i, 0, null);
            this.writingSample = false;
        }
    }
}
