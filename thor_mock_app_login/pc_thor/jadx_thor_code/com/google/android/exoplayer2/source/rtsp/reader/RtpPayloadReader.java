package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes.dex */
public interface RtpPayloadReader {

    public interface Factory {
        RtpPayloadReader createPayloadReader(RtpPayloadFormat payloadFormat);
    }

    void consume(ParsableByteArray data, long timestamp, int sequenceNumber, boolean rtpMarker) throws ParserException;

    void createTracks(ExtractorOutput extractorOutput, int trackId);

    void onReceivingFirstPacket(long timestamp, int sequenceNumber);

    void seek(long nextRtpTimestamp, long timeUs);
}
