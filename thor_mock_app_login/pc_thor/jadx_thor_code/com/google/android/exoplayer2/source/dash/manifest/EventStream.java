package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.metadata.emsg.EventMessage;

/* loaded from: classes.dex */
public final class EventStream {
    public final EventMessage[] events;
    public final long[] presentationTimesUs;
    public final String schemeIdUri;
    public final long timescale;
    public final String value;

    public EventStream(String schemeIdUri, String value, long timescale, long[] presentationTimesUs, EventMessage[] events) {
        this.schemeIdUri = schemeIdUri;
        this.value = value;
        this.timescale = timescale;
        this.presentationTimesUs = presentationTimesUs;
        this.events = events;
    }

    public String id() {
        String str = this.schemeIdUri;
        String str2 = this.value;
        return new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length()).append(str).append("/").append(str2).toString();
    }
}
