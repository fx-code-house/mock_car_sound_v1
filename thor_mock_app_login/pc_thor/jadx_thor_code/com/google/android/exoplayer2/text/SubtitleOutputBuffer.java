package com.google.android.exoplayer2.text;

import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.util.List;

/* loaded from: classes.dex */
public abstract class SubtitleOutputBuffer extends OutputBuffer implements Subtitle {
    private long subsampleOffsetUs;
    private Subtitle subtitle;

    public void setContent(long timeUs, Subtitle subtitle, long subsampleOffsetUs) {
        this.timeUs = timeUs;
        this.subtitle = subtitle;
        if (subsampleOffsetUs == Long.MAX_VALUE) {
            subsampleOffsetUs = this.timeUs;
        }
        this.subsampleOffsetUs = subsampleOffsetUs;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getEventTimeCount() {
        return ((Subtitle) Assertions.checkNotNull(this.subtitle)).getEventTimeCount();
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public long getEventTime(int index) {
        return ((Subtitle) Assertions.checkNotNull(this.subtitle)).getEventTime(index) + this.subsampleOffsetUs;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getNextEventTimeIndex(long timeUs) {
        return ((Subtitle) Assertions.checkNotNull(this.subtitle)).getNextEventTimeIndex(timeUs - this.subsampleOffsetUs);
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long timeUs) {
        return ((Subtitle) Assertions.checkNotNull(this.subtitle)).getCues(timeUs - this.subsampleOffsetUs);
    }

    @Override // com.google.android.exoplayer2.decoder.Buffer
    public void clear() {
        super.clear();
        this.subtitle = null;
    }
}
