package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;

/* loaded from: classes.dex */
final class RtpAacReader implements RtpPayloadReader {
    private static final String AAC_HIGH_BITRATE_MODE = "AAC-hbr";
    private static final String AAC_LOW_BITRATE_MODE = "AAC-lbr";
    private static final String TAG = "RtpAacReader";
    private final ParsableBitArray auHeaderScratchBit = new ParsableBitArray();
    private final int auIndexFieldBitSize;
    private final int auSizeFieldBitSize;
    private long firstReceivedTimestamp;
    private final int numBitsInAuHeader;
    private final RtpPayloadFormat payloadFormat;
    private final int sampleRate;
    private long startTimeOffsetUs;
    private TrackOutput trackOutput;

    public RtpAacReader(RtpPayloadFormat payloadFormat) {
        this.payloadFormat = payloadFormat;
        this.sampleRate = payloadFormat.clockRate;
        String str = (String) Assertions.checkNotNull(payloadFormat.fmtpParameters.get("mode"));
        if (Ascii.equalsIgnoreCase(str, AAC_HIGH_BITRATE_MODE)) {
            this.auSizeFieldBitSize = 13;
            this.auIndexFieldBitSize = 3;
        } else if (Ascii.equalsIgnoreCase(str, AAC_LOW_BITRATE_MODE)) {
            this.auSizeFieldBitSize = 6;
            this.auIndexFieldBitSize = 2;
        } else {
            throw new UnsupportedOperationException("AAC mode not supported");
        }
        this.numBitsInAuHeader = this.auIndexFieldBitSize + this.auSizeFieldBitSize;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int trackId) {
        TrackOutput trackOutputTrack = extractorOutput.track(trackId, 1);
        this.trackOutput = trackOutputTrack;
        trackOutputTrack.format(this.payloadFormat.format);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long timestamp, int sequenceNumber) {
        this.firstReceivedTimestamp = timestamp;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray data, long timestamp, int sequenceNumber, boolean rtpMarker) {
        Assertions.checkNotNull(this.trackOutput);
        short s = data.readShort();
        int i = s / this.numBitsInAuHeader;
        long sampleTimeUs = toSampleTimeUs(this.startTimeOffsetUs, timestamp, this.firstReceivedTimestamp, this.sampleRate);
        this.auHeaderScratchBit.reset(data);
        if (i == 1) {
            int bits = this.auHeaderScratchBit.readBits(this.auSizeFieldBitSize);
            this.auHeaderScratchBit.skipBits(this.auIndexFieldBitSize);
            this.trackOutput.sampleData(data, data.bytesLeft());
            if (rtpMarker) {
                outputSampleMetadata(this.trackOutput, sampleTimeUs, bits);
                return;
            }
            return;
        }
        data.skipBytes((s + 7) / 8);
        for (int i2 = 0; i2 < i; i2++) {
            int bits2 = this.auHeaderScratchBit.readBits(this.auSizeFieldBitSize);
            this.auHeaderScratchBit.skipBits(this.auIndexFieldBitSize);
            this.trackOutput.sampleData(data, bits2);
            outputSampleMetadata(this.trackOutput, sampleTimeUs, bits2);
            sampleTimeUs += Util.scaleLargeTimestamp(i, 1000000L, this.sampleRate);
        }
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void seek(long nextRtpTimestamp, long timeUs) {
        this.firstReceivedTimestamp = nextRtpTimestamp;
        this.startTimeOffsetUs = timeUs;
    }

    private static void outputSampleMetadata(TrackOutput trackOutput, long sampleTimeUs, int size) {
        trackOutput.sampleMetadata(sampleTimeUs, 1, size, 0, null);
    }

    private static long toSampleTimeUs(long startTimeOffsetUs, long rtpTimestamp, long firstReceivedRtpTimestamp, int sampleRate) {
        return startTimeOffsetUs + Util.scaleLargeTimestamp(rtpTimestamp - firstReceivedRtpTimestamp, 1000000L, sampleRate);
    }
}
