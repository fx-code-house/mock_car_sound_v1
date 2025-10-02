package com.google.android.exoplayer2.source.rtsp;

import android.os.SystemClock;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.source.rtsp.reader.DefaultRtpPayloadReaderFactory;
import com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* loaded from: classes.dex */
final class RtpExtractor implements Extractor {
    private boolean firstPacketRead;
    private boolean isSeekPending;
    private ExtractorOutput output;
    private final RtpPayloadReader payloadReader;
    private final int trackId;
    private final ParsableByteArray rtpPacketScratchBuffer = new ParsableByteArray(RtpPacket.MAX_SIZE);
    private final ParsableByteArray rtpPacketDataBuffer = new ParsableByteArray();
    private final Object lock = new Object();
    private final RtpPacketReorderingQueue reorderingQueue = new RtpPacketReorderingQueue();
    private volatile long firstTimestamp = C.TIME_UNSET;
    private volatile int firstSequenceNumber = -1;
    private long nextRtpTimestamp = C.TIME_UNSET;
    private long playbackStartTimeUs = C.TIME_UNSET;

    private static long getCutoffTimeMs(long packetArrivalTimeMs) {
        return packetArrivalTimeMs - 30;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    public RtpExtractor(RtpPayloadFormat payloadFormat, int trackId) {
        this.trackId = trackId;
        this.payloadReader = (RtpPayloadReader) Assertions.checkNotNull(new DefaultRtpPayloadReaderFactory().createPayloadReader(payloadFormat));
    }

    public void setFirstTimestamp(long firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public void setFirstSequenceNumber(int firstSequenceNumber) {
        this.firstSequenceNumber = firstSequenceNumber;
    }

    public boolean hasReadFirstRtpPacket() {
        return this.firstPacketRead;
    }

    public void preSeek() {
        synchronized (this.lock) {
            this.isSeekPending = true;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput input) {
        throw new UnsupportedOperationException("RTP packets are transmitted in a packet stream do not support sniffing.");
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput output) {
        this.payloadReader.createTracks(output, this.trackId);
        output.endTracks();
        output.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
        this.output = output;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        Assertions.checkNotNull(this.output);
        int i = input.read(this.rtpPacketScratchBuffer.getData(), 0, RtpPacket.MAX_SIZE);
        if (i == -1) {
            return -1;
        }
        if (i == 0) {
            return 0;
        }
        this.rtpPacketScratchBuffer.setPosition(0);
        this.rtpPacketScratchBuffer.setLimit(i);
        RtpPacket rtpPacket = RtpPacket.parse(this.rtpPacketScratchBuffer);
        if (rtpPacket == null) {
            return 0;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long cutoffTimeMs = getCutoffTimeMs(jElapsedRealtime);
        this.reorderingQueue.offer(rtpPacket, jElapsedRealtime);
        RtpPacket rtpPacketPoll = this.reorderingQueue.poll(cutoffTimeMs);
        if (rtpPacketPoll == null) {
            return 0;
        }
        if (!this.firstPacketRead) {
            if (this.firstTimestamp == C.TIME_UNSET) {
                this.firstTimestamp = rtpPacketPoll.timestamp;
            }
            if (this.firstSequenceNumber == -1) {
                this.firstSequenceNumber = rtpPacketPoll.sequenceNumber;
            }
            this.payloadReader.onReceivingFirstPacket(this.firstTimestamp, this.firstSequenceNumber);
            this.firstPacketRead = true;
        }
        synchronized (this.lock) {
            if (!this.isSeekPending) {
                do {
                    this.rtpPacketDataBuffer.reset(rtpPacketPoll.payloadData);
                    this.payloadReader.consume(this.rtpPacketDataBuffer, rtpPacketPoll.timestamp, rtpPacketPoll.sequenceNumber, rtpPacketPoll.marker);
                    rtpPacketPoll = this.reorderingQueue.poll(cutoffTimeMs);
                } while (rtpPacketPoll != null);
            } else if (this.nextRtpTimestamp != C.TIME_UNSET && this.playbackStartTimeUs != C.TIME_UNSET) {
                this.reorderingQueue.reset();
                this.payloadReader.seek(this.nextRtpTimestamp, this.playbackStartTimeUs);
                this.isSeekPending = false;
                this.nextRtpTimestamp = C.TIME_UNSET;
                this.playbackStartTimeUs = C.TIME_UNSET;
            }
        }
        return 0;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long nextRtpTimestamp, long playbackStartTimeUs) {
        synchronized (this.lock) {
            this.nextRtpTimestamp = nextRtpTimestamp;
            this.playbackStartTimeUs = playbackStartTimeUs;
        }
    }
}
