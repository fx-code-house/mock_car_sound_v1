package com.google.android.exoplayer2.source.dash;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.metadata.emsg.EventMessageEncoder;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.dash.manifest.EventStream;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes.dex */
final class EventSampleStream implements SampleStream {
    private int currentIndex;
    private EventStream eventStream;
    private boolean eventStreamAppendable;
    private long[] eventTimesUs;
    private boolean isFormatSentDownstream;
    private final Format upstreamFormat;
    private final EventMessageEncoder eventMessageEncoder = new EventMessageEncoder();
    private long pendingSeekPositionUs = C.TIME_UNSET;

    @Override // com.google.android.exoplayer2.source.SampleStream
    public boolean isReady() {
        return true;
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public void maybeThrowError() throws IOException {
    }

    public EventSampleStream(EventStream eventStream, Format upstreamFormat, boolean eventStreamAppendable) {
        this.upstreamFormat = upstreamFormat;
        this.eventStream = eventStream;
        this.eventTimesUs = eventStream.presentationTimesUs;
        updateEventStream(eventStream, eventStreamAppendable);
    }

    public String eventStreamId() {
        return this.eventStream.id();
    }

    public void updateEventStream(EventStream eventStream, boolean eventStreamAppendable) {
        int i = this.currentIndex;
        long j = i == 0 ? -9223372036854775807L : this.eventTimesUs[i - 1];
        this.eventStreamAppendable = eventStreamAppendable;
        this.eventStream = eventStream;
        long[] jArr = eventStream.presentationTimesUs;
        this.eventTimesUs = jArr;
        long j2 = this.pendingSeekPositionUs;
        if (j2 != C.TIME_UNSET) {
            seekToUs(j2);
        } else if (j != C.TIME_UNSET) {
            this.currentIndex = Util.binarySearchCeil(jArr, j, false, false);
        }
    }

    public void seekToUs(long positionUs) {
        int iBinarySearchCeil = Util.binarySearchCeil(this.eventTimesUs, positionUs, true, false);
        this.currentIndex = iBinarySearchCeil;
        if (!(this.eventStreamAppendable && iBinarySearchCeil == this.eventTimesUs.length)) {
            positionUs = C.TIME_UNSET;
        }
        this.pendingSeekPositionUs = positionUs;
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags) throws IOException {
        int i = this.currentIndex;
        boolean z = i == this.eventTimesUs.length;
        if (z && !this.eventStreamAppendable) {
            buffer.setFlags(4);
            return -4;
        }
        if ((readFlags & 2) != 0 || !this.isFormatSentDownstream) {
            formatHolder.format = this.upstreamFormat;
            this.isFormatSentDownstream = true;
            return -5;
        }
        if (z) {
            return -3;
        }
        this.currentIndex = i + 1;
        byte[] bArrEncode = this.eventMessageEncoder.encode(this.eventStream.events[i]);
        buffer.ensureSpaceForWrite(bArrEncode.length);
        buffer.data.put(bArrEncode);
        buffer.timeUs = this.eventTimesUs[i];
        buffer.setFlags(1);
        return -4;
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public int skipData(long positionUs) {
        int iMax = Math.max(this.currentIndex, Util.binarySearchCeil(this.eventTimesUs, positionUs, true, false));
        int i = iMax - this.currentIndex;
        this.currentIndex = iMax;
        return i;
    }
}
