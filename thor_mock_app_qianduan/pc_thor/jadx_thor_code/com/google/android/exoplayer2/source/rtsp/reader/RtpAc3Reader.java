package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class RtpAc3Reader implements RtpPayloadReader {
    private static final int AC3_FRAME_TYPE_COMPLETE_FRAME = 0;
    private static final int AC3_FRAME_TYPE_INITIAL_FRAGMENT_A = 1;
    private static final int AC3_FRAME_TYPE_INITIAL_FRAGMENT_B = 2;
    private static final int AC3_FRAME_TYPE_NON_INITIAL_FRAGMENT = 3;
    private static final int AC3_PAYLOAD_HEADER_SIZE = 2;
    private int numBytesPendingMetadataOutput;
    private final RtpPayloadFormat payloadFormat;
    private long sampleTimeUsOfFramePendingMetadataOutput;
    private long startTimeOffsetUs;
    private TrackOutput trackOutput;
    private final ParsableBitArray scratchBitBuffer = new ParsableBitArray();
    private long firstReceivedTimestamp = C.TIME_UNSET;

    public RtpAc3Reader(RtpPayloadFormat payloadFormat) {
        this.payloadFormat = payloadFormat;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int trackId) {
        TrackOutput trackOutputTrack = extractorOutput.track(trackId, 1);
        this.trackOutput = trackOutputTrack;
        trackOutputTrack.format(this.payloadFormat.format);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long timestamp, int sequenceNumber) {
        Assertions.checkState(this.firstReceivedTimestamp == C.TIME_UNSET);
        this.firstReceivedTimestamp = timestamp;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray data, long timestamp, int sequenceNumber, boolean rtpMarker) {
        int unsignedByte = data.readUnsignedByte() & 3;
        int unsignedByte2 = data.readUnsignedByte() & 255;
        long sampleTimeUs = toSampleTimeUs(this.startTimeOffsetUs, timestamp, this.firstReceivedTimestamp, this.payloadFormat.clockRate);
        if (unsignedByte == 0) {
            maybeOutputSampleMetadata();
            if (unsignedByte2 == 1) {
                processSingleFramePacket(data, sampleTimeUs);
                return;
            } else {
                processMultiFramePacket(data, unsignedByte2, sampleTimeUs);
                return;
            }
        }
        if (unsignedByte == 1 || unsignedByte == 2) {
            maybeOutputSampleMetadata();
        } else if (unsignedByte != 3) {
            throw new IllegalArgumentException(String.valueOf(unsignedByte));
        }
        processFragmentedPacket(data, rtpMarker, unsignedByte, sampleTimeUs);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void seek(long nextRtpTimestamp, long timeUs) {
        this.firstReceivedTimestamp = nextRtpTimestamp;
        this.startTimeOffsetUs = timeUs;
    }

    private void processSingleFramePacket(ParsableByteArray data, long sampleTimeUs) {
        int iBytesLeft = data.bytesLeft();
        ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleData(data, iBytesLeft);
        ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleMetadata(sampleTimeUs, 1, iBytesLeft, 0, null);
    }

    private void processMultiFramePacket(ParsableByteArray data, int numOfFrames, long sampleTimeUs) {
        this.scratchBitBuffer.reset(data.getData());
        this.scratchBitBuffer.skipBytes(2);
        for (int i = 0; i < numOfFrames; i++) {
            Ac3Util.SyncFrameInfo ac3SyncframeInfo = Ac3Util.parseAc3SyncframeInfo(this.scratchBitBuffer);
            ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleData(data, ac3SyncframeInfo.frameSize);
            ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleMetadata(sampleTimeUs, 1, ac3SyncframeInfo.frameSize, 0, null);
            sampleTimeUs += (ac3SyncframeInfo.sampleCount / ac3SyncframeInfo.sampleRate) * 1000000;
            this.scratchBitBuffer.skipBytes(ac3SyncframeInfo.frameSize);
        }
    }

    private void processFragmentedPacket(ParsableByteArray data, boolean isFrameBoundary, int frameType, long sampleTimeUs) {
        int iBytesLeft = data.bytesLeft();
        ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleData(data, iBytesLeft);
        this.numBytesPendingMetadataOutput += iBytesLeft;
        this.sampleTimeUsOfFramePendingMetadataOutput = sampleTimeUs;
        if (isFrameBoundary && frameType == 3) {
            outputSampleMetadataForFragmentedPackets();
        }
    }

    private void maybeOutputSampleMetadata() {
        if (this.numBytesPendingMetadataOutput > 0) {
            outputSampleMetadataForFragmentedPackets();
        }
    }

    private void outputSampleMetadataForFragmentedPackets() {
        ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleMetadata(this.sampleTimeUsOfFramePendingMetadataOutput, 1, this.numBytesPendingMetadataOutput, 0, null);
        this.numBytesPendingMetadataOutput = 0;
    }

    private static long toSampleTimeUs(long startTimeOffsetUs, long rtpTimestamp, long firstReceivedRtpTimestamp, int sampleRate) {
        return startTimeOffsetUs + Util.scaleLargeTimestamp(rtpTimestamp - firstReceivedRtpTimestamp, 1000000L, sampleRate);
    }
}
