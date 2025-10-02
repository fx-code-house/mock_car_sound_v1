package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
final class RtpH264Reader implements RtpPayloadReader {
    private static final int FU_PAYLOAD_OFFSET = 2;
    private static final long MEDIA_CLOCK_FREQUENCY = 90000;
    private static final int NAL_UNIT_TYPE_IDR = 5;
    private static final int RTP_PACKET_TYPE_FU_A = 28;
    private static final int RTP_PACKET_TYPE_STAP_A = 24;
    private static final String TAG = "RtpH264Reader";
    private int bufferFlags;
    private int fragmentedSampleSizeBytes;
    private final RtpPayloadFormat payloadFormat;
    private long startTimeOffsetUs;
    private TrackOutput trackOutput;
    private final ParsableByteArray nalStartCodeArray = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
    private final ParsableByteArray fuScratchBuffer = new ParsableByteArray();
    private long firstReceivedTimestamp = C.TIME_UNSET;
    private int previousSequenceNumber = -1;

    private static int getBufferFlagsFromNalType(int nalType) {
        return nalType == 5 ? 1 : 0;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long timestamp, int sequenceNumber) {
    }

    public RtpH264Reader(RtpPayloadFormat payloadFormat) {
        this.payloadFormat = payloadFormat;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int trackId) {
        TrackOutput trackOutputTrack = extractorOutput.track(trackId, 2);
        this.trackOutput = trackOutputTrack;
        ((TrackOutput) Util.castNonNull(trackOutputTrack)).format(this.payloadFormat.format);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray data, long timestamp, int sequenceNumber, boolean rtpMarker) throws ParserException {
        try {
            int i = data.getData()[0] & Ascii.US;
            Assertions.checkStateNotNull(this.trackOutput);
            if (i > 0 && i < 24) {
                processSingleNalUnitPacket(data);
            } else if (i == 24) {
                processSingleTimeAggregationPacket(data);
            } else if (i == 28) {
                processFragmentationUnitPacket(data, sequenceNumber);
            } else {
                throw ParserException.createForMalformedManifest(String.format("RTP H264 packetization mode [%d] not supported.", Integer.valueOf(i)), null);
            }
            if (rtpMarker) {
                if (this.firstReceivedTimestamp == C.TIME_UNSET) {
                    this.firstReceivedTimestamp = timestamp;
                }
                this.trackOutput.sampleMetadata(toSampleUs(this.startTimeOffsetUs, timestamp, this.firstReceivedTimestamp), this.bufferFlags, this.fragmentedSampleSizeBytes, 0, null);
                this.fragmentedSampleSizeBytes = 0;
            }
            this.previousSequenceNumber = sequenceNumber;
        } catch (IndexOutOfBoundsException e) {
            throw ParserException.createForMalformedManifest(null, e);
        }
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void seek(long nextRtpTimestamp, long timeUs) {
        this.firstReceivedTimestamp = nextRtpTimestamp;
        this.fragmentedSampleSizeBytes = 0;
        this.startTimeOffsetUs = timeUs;
    }

    @RequiresNonNull({"trackOutput"})
    private void processSingleNalUnitPacket(ParsableByteArray data) {
        int iBytesLeft = data.bytesLeft();
        this.fragmentedSampleSizeBytes += writeStartCode();
        this.trackOutput.sampleData(data, iBytesLeft);
        this.fragmentedSampleSizeBytes += iBytesLeft;
        this.bufferFlags = getBufferFlagsFromNalType(data.getData()[0] & Ascii.US);
    }

    @RequiresNonNull({"trackOutput"})
    private void processSingleTimeAggregationPacket(ParsableByteArray data) {
        data.readUnsignedByte();
        while (data.bytesLeft() > 4) {
            int unsignedShort = data.readUnsignedShort();
            this.fragmentedSampleSizeBytes += writeStartCode();
            this.trackOutput.sampleData(data, unsignedShort);
            this.fragmentedSampleSizeBytes += unsignedShort;
        }
        this.bufferFlags = 0;
    }

    @RequiresNonNull({"trackOutput"})
    private void processFragmentationUnitPacket(ParsableByteArray data, int packetSequenceNumber) {
        byte b = data.getData()[0];
        byte b2 = data.getData()[1];
        int i = (b & 224) | (b2 & Ascii.US);
        boolean z = (b2 & 128) > 0;
        boolean z2 = (b2 & SignedBytes.MAX_POWER_OF_TWO) > 0;
        if (z) {
            this.fragmentedSampleSizeBytes += writeStartCode();
            data.getData()[1] = (byte) i;
            this.fuScratchBuffer.reset(data.getData());
            this.fuScratchBuffer.setPosition(1);
        } else {
            int i2 = (this.previousSequenceNumber + 1) % 65535;
            if (packetSequenceNumber != i2) {
                Log.w(TAG, Util.formatInvariant("Received RTP packet with unexpected sequence number. Expected: %d; received: %d. Dropping packet.", Integer.valueOf(i2), Integer.valueOf(packetSequenceNumber)));
                return;
            } else {
                this.fuScratchBuffer.reset(data.getData());
                this.fuScratchBuffer.setPosition(2);
            }
        }
        int iBytesLeft = this.fuScratchBuffer.bytesLeft();
        this.trackOutput.sampleData(this.fuScratchBuffer, iBytesLeft);
        this.fragmentedSampleSizeBytes += iBytesLeft;
        if (z2) {
            this.bufferFlags = getBufferFlagsFromNalType(i & 31);
        }
    }

    private int writeStartCode() {
        this.nalStartCodeArray.setPosition(0);
        int iBytesLeft = this.nalStartCodeArray.bytesLeft();
        ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleData(this.nalStartCodeArray, iBytesLeft);
        return iBytesLeft;
    }

    private static long toSampleUs(long startTimeOffsetUs, long rtpTimestamp, long firstReceivedRtpTimestamp) {
        return startTimeOffsetUs + Util.scaleLargeTimestamp(rtpTimestamp - firstReceivedRtpTimestamp, 1000000L, MEDIA_CLOCK_FREQUENCY);
    }
}
