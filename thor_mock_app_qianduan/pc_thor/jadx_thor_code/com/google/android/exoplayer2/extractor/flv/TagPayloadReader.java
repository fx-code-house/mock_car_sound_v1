package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes.dex */
abstract class TagPayloadReader {
    protected final TrackOutput output;

    protected abstract boolean parseHeader(ParsableByteArray data) throws ParserException;

    protected abstract boolean parsePayload(ParsableByteArray data, long timeUs) throws ParserException;

    public abstract void seek();

    public static final class UnsupportedFormatException extends ParserException {
        public UnsupportedFormatException(String msg) {
            super(msg, null, false, 1);
        }
    }

    protected TagPayloadReader(TrackOutput output) {
        this.output = output;
    }

    public final boolean consume(ParsableByteArray data, long timeUs) throws ParserException {
        return parseHeader(data) && parsePayload(data, timeUs);
    }
}
