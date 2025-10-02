package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class DvbSubtitleReader implements ElementaryStreamReader {
    private int bytesToCheck;
    private final TrackOutput[] outputs;
    private int sampleBytesWritten;
    private long sampleTimeUs;
    private final List<TsPayloadReader.DvbSubtitleInfo> subtitleInfos;
    private boolean writingSample;

    public DvbSubtitleReader(List<TsPayloadReader.DvbSubtitleInfo> subtitleInfos) {
        this.subtitleInfos = subtitleInfos;
        this.outputs = new TrackOutput[subtitleInfos.size()];
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.writingSample = false;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        for (int i = 0; i < this.outputs.length; i++) {
            TsPayloadReader.DvbSubtitleInfo dvbSubtitleInfo = this.subtitleInfos.get(i);
            idGenerator.generateNewId();
            TrackOutput trackOutputTrack = extractorOutput.track(idGenerator.getTrackId(), 3);
            trackOutputTrack.format(new Format.Builder().setId(idGenerator.getFormatId()).setSampleMimeType(MimeTypes.APPLICATION_DVBSUBS).setInitializationData(Collections.singletonList(dvbSubtitleInfo.initializationData)).setLanguage(dvbSubtitleInfo.language).build());
            this.outputs[i] = trackOutputTrack;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long pesTimeUs, int flags) {
        if ((flags & 4) == 0) {
            return;
        }
        this.writingSample = true;
        this.sampleTimeUs = pesTimeUs;
        this.sampleBytesWritten = 0;
        this.bytesToCheck = 2;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
        if (this.writingSample) {
            for (TrackOutput trackOutput : this.outputs) {
                trackOutput.sampleMetadata(this.sampleTimeUs, 1, this.sampleBytesWritten, 0, null);
            }
            this.writingSample = false;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray data) {
        if (this.writingSample) {
            if (this.bytesToCheck != 2 || checkNextByte(data, 32)) {
                if (this.bytesToCheck != 1 || checkNextByte(data, 0)) {
                    int position = data.getPosition();
                    int iBytesLeft = data.bytesLeft();
                    for (TrackOutput trackOutput : this.outputs) {
                        data.setPosition(position);
                        trackOutput.sampleData(data, iBytesLeft);
                    }
                    this.sampleBytesWritten += iBytesLeft;
                }
            }
        }
    }

    private boolean checkNextByte(ParsableByteArray data, int expectedValue) {
        if (data.bytesLeft() == 0) {
            return false;
        }
        if (data.readUnsignedByte() != expectedValue) {
            this.writingSample = false;
        }
        this.bytesToCheck--;
        return this.writingSample;
    }
}
