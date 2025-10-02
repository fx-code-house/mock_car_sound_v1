package com.google.android.exoplayer2.text.webvtt;

import com.google.android.exoplayer2.text.Cue;

/* loaded from: classes.dex */
public final class WebvttCueInfo {
    public final Cue cue;
    public final long endTimeUs;
    public final long startTimeUs;

    public WebvttCueInfo(Cue cue, long startTimeUs, long endTimeUs) {
        this.cue = cue;
        this.startTimeUs = startTimeUs;
        this.endTimeUs = endTimeUs;
    }
}
